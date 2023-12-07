package com.example.datn_tranvantruong.DBHandler;

import com.example.datn_tranvantruong.Database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginHandler {

    private final DBConnection dbConnection;

    public LoginHandler() {
        this.dbConnection = new DBConnection();
    }

    public String checkLogin(String email, String password) {
        try (Connection connection = dbConnection.createConection()) {
            String adminQuery = "SELECT email FROM admin WHERE email = ? AND password = ?";
            if (checkUser(connection, adminQuery, email, password)) {
                return "admin";
            }

            String customerQuery = "SELECT email FROM customers WHERE email = ? AND password = ?";
            if (checkUser(connection, customerQuery, email, password)) {
                return "customers";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "false";
    }

    public String checkForgotPassword(String email, String phone) {
        try (Connection connection = dbConnection.createConection()) {
            String query = "SELECT email FROM customers WHERE email = ? AND phone = ?";
            if (checkUser(connection, query, email, phone)) {
                return "customers";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "false";
    }

    public int getUserId(String email) {
        int userId = -1;
        try (Connection connection = dbConnection.createConection()) {
            String query = "SELECT id FROM customers WHERE email = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, email);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        userId = resultSet.getInt("id");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userId;
    }

    private boolean checkUser(Connection connection, String query, String param1, String param2) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, param1);
            preparedStatement.setString(2, param2);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
