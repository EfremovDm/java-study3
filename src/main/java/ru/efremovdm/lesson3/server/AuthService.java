package ru.efremovdm.lesson3.server;

import java.sql.*;

public class AuthService {

    private Connection connection;
    private Statement stmt;

    public void connect() throws ClassNotFoundException, SQLException{
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:main.db");
        stmt = connection.createStatement();
    }

    public String getNickByLoginAndPass(String login, String pass) {

        try {
            String query = "SELECT nick FROM users WHERE login = '" + login + "' AND password = '" + pass + "';";
            ResultSet rs = stmt.executeQuery(query);
            if(rs.next()){
                return rs.getString("nick");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void disconnect(){
        try {
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
