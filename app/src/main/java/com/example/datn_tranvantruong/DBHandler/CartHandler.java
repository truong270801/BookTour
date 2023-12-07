package com.example.datn_tranvantruong.DBHandler;

import com.example.datn_tranvantruong.Database.DBConnection;
import com.example.datn_tranvantruong.Model.Cart;
import com.example.datn_tranvantruong.Model.CartStatistic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CartHandler {

    private DBConnection dbConnection;

    public CartHandler() {
        this.dbConnection = new DBConnection();
    }

    public void addToCart(Cart cartItem) {
        try (Connection connection = dbConnection.createConection()) {
            String sql = "INSERT INTO carts (product_id, user_id, quantity, price) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, cartItem.getProduct_id());
                preparedStatement.setInt(2, cartItem.getUser_id());
                preparedStatement.setInt(3, cartItem.getQuatity());
                preparedStatement.setInt(4, cartItem.getPrice());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<CartStatistic> getCartByUserID(int id) {
        List<CartStatistic> cartList = new ArrayList<>();
        try (Connection connection = dbConnection.createConection()) {
            String query = "SELECT * FROM carts WHERE user_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, id);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        ProductHandler productHandler = new ProductHandler();
                        CartStatistic cartItem = new CartStatistic(
                        resultSet.getInt("id"),
                                resultSet.getInt("product_id"),
                                productHandler.getProductNameById(resultSet.getInt("product_id")),
                                resultSet.getInt("quantity"),
                                resultSet.getInt("price"),
                                productHandler.getProductImageById(resultSet.getInt("product_id")));

                        cartList.add(cartItem);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cartList;
    }

    public void deleteCart(int id) {
        try (Connection connection = dbConnection.createConection()) {
            String sql = "DELETE FROM carts WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
