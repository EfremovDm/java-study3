package ru.efremovdm.lesson4;

/**
 * 1. Создать три потока, каждый из которых выводит определенную букву (A, B и C) 5 раз (порядок – ABСABСABС).
 * Используйте wait/notify/notifyAll.
 */
public class Application extends Thread{

    private final Object monitor = new Object();
    private volatile char letter = 'A';
    private static String result = "";

    public static void main(String[] args) {
        String result = getResult();
        System.out.print(result);
    }

    public static String getResult() {

        Application app = new Application();

        Thread t1 = new Thread(() -> { app.print('A'); });
        Thread t2 = new Thread(() -> { app.print('B'); });
        Thread t3 = new Thread(() -> { app.print('C'); });

        t1.start();
        t2.start();
        t3.start();

        try {
            while (t1.isAlive() && t2.isAlive() && t3.isAlive()) {
                Thread.sleep(100);
            }
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        return result;
    }

    private void print(char chr) {
        synchronized (monitor) {
            try {
                for (int i = 0; i < 5; i++) {
                    while (letter != chr) {
                        monitor.wait();
                    }
                    result += letter;
                    letter = 'A' == chr ? 'B' : ('B' == chr ? 'C' : 'A');
                    monitor.notifyAll();
                }
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
