package com.example.datn_tranvantruong.DBHandler;

import com.example.datn_tranvantruong.Database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SignupHandler {

    private final DBConnection dbConnection;

    public SignupHandler() {
        this.dbConnection = new DBConnection();
    }

    public boolean register(String email, String fullname, String password, String phone) {
        try (Connection connection = dbConnection.createConection()) {
            if (connection != null && !connection.isClosed()) {
                String sql = "INSERT INTO customers (email, fullname, password, phone) VALUES (?, ?, ?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setString(1, email);
                    preparedStatement.setString(2, fullname);
                    preparedStatement.setString(3, password);
                    preparedStatement.setString(4, phone);

                    int rowsInserted = preparedStatement.executeUpdate();
                    return (rowsInserted > 0);
                }
            } else {
                // Handle when the connection is not successful
                return false;
            }
        } catch (SQLException e) {
            // Handle SQL exceptions (log or throw as needed)
            e.printStackTrace();
            return false;
        }
    }
}
