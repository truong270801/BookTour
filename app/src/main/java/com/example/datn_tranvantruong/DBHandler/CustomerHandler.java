package com.example.datn_tranvantruong.DBHandler;

import com.example.datn_tranvantruong.Database.DBConnection;
import com.example.datn_tranvantruong.Model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerHandler {

    private DBConnection dbConnection;

    public CustomerHandler() {
        this.dbConnection = new DBConnection();
    }

    public List<Customer> getAllCustomer() {
        List<Customer> ls = new ArrayList<>();
        try (Connection connection = dbConnection.createConection()) {
            String query = "SELECT * FROM customers";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Customer customer = new Customer();
                        customer.setId(resultSet.getInt("id"));
                        customer.setEmail(resultSet.getString("email"));
                        customer.setFullname(resultSet.getString("fullname"));
                        customer.setAddress(resultSet.getString("address"));
                        customer.setImage_avatar(resultSet.getBytes("image_avatar"));
                        customer.setPhone(resultSet.getString("phone"));

                        ls.add(customer);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ls;
    }

    public Customer getCustomerInfo(String id) {
        Customer user = new Customer();
        try (Connection connection = dbConnection.createConection()) {
            String query = "SELECT fullname, email, address, phone, image_avatar FROM customers WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, Integer.parseInt(id));
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        user.setFullname(resultSet.getString("fullname"));
                        user.setEmail(resultSet.getString("email"));
                        user.setAddress(resultSet.getString("address"));
                        user.setPhone(resultSet.getString("phone"));
                        user.setImage_avatar(resultSet.getBytes("image_avatar"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public void updateCustomerPassword(String id, String newPassword) {
        try (Connection connection = dbConnection.createConection()) {
            String sql = "UPDATE customers SET password = ? WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, newPassword);
                preparedStatement.setInt(2, Integer.parseInt(id));

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateCustomerForgotPassword(String email, String newPassword) {
        try (Connection connection = dbConnection.createConection()) {
            String sql = "UPDATE customers SET password = ? WHERE email = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, newPassword);
                preparedStatement.setString(2, email);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getPasswordFromDB(String id) {
        String password = null;
        try (Connection connection = dbConnection.createConection()) {
            String query = "SELECT password FROM customers WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, Integer.parseInt(id));
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        password = resultSet.getString("password");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return password;
    }

    public String getForgotPasswordFromDB(String email) {
        String password = null;
        try (Connection connection = dbConnection.createConection()) {
            String query = "SELECT password FROM customers WHERE email = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, email);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        password = resultSet.getString("password");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return password;
    }

    public void updateCustomerInfo(String id, String fullname, String address, String phone, byte[] avatar) {
        try (Connection connection = dbConnection.createConection()) {
            String sql = "UPDATE customers SET fullname = ?, address = ?, phone = ?, image_avatar = ? WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, fullname);
                preparedStatement.setString(2, address);
                preparedStatement.setString(3, phone);
                preparedStatement.setBytes(4, avatar);
                preparedStatement.setInt(5, Integer.parseInt(id));

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCustomer(int id) {
        try (Connection connection = dbConnection.createConection()) {
            String sql = "DELETE FROM customers WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
