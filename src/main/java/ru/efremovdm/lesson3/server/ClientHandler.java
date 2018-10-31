package ru.efremovdm.lesson3.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ClientHandler {

    private Server server;
    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;
    private String nick;

    public String getNick() {
        return nick;
    }

    public ClientHandler(Server server, Socket socket) {
        try {
            this.server = server;
            this.socket = socket;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());

            ClientHandler thisObj = this;

            /**
             * 2. На серверной стороне сетевого чата реализовать управление потоками через ExecutorService.
             */
            ExecutorService executorService = Executors.newFixedThreadPool(2);
            Future future = executorService.submit(new Callable<Object>(){
                public Object call() {

                    try {
                        while (true) {
                            String msg = in.readUTF();
                            if (msg.startsWith("/auth ")){
                                String[] data = msg.split("\\s");
                                String newNick = server.getAuthService().getNickByLoginAndPass(data[1], data[2]);
                                if (newNick != null){
                                    nick = newNick;
                                    sendMsg("/authok");
                                    server.subscribe(thisObj);
                                    break;
                                } else {
                                    sendMsg("Неверный логин/пароль");
                                }
                            }
                        }
                        while (true) {
                            String msg = in.readUTF();
                            if (msg.startsWith("/w")){
                                String[] data = msg.split("\\s");
                                String Mess="";
                                for (String Mes : data) {
                                    if (Mes == data[0]) {

                                    }
                                    else if (Mes == data[1]) {
                                        Mess = "<" + Mes + "> ";
                                    }
                                    else {
                                        Mess += " " + Mes;
                                    }
                                }
                                System.out.println(nick + ": " + Mess);
                                sendMsg(nick+": "+Mess);
                                server.forNick(data[1],Mess);

                            }else {
                                System.out.println(nick + ": " + msg);
                                if (msg.equals("/end")) break;
                                server.broadcastMsg(nick + ": " + msg);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        server.unsubscribe(thisObj);
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    return "";
                }
            });
            executorService.shutdown();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(String msg){
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
