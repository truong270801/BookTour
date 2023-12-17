package com.example.datn_tranvantruong.DBHandler;

import com.example.datn_tranvantruong.Database.DBConnection;
import com.example.datn_tranvantruong.Model.Cart;
import com.example.datn_tranvantruong.Model.Evaluate;
import com.example.datn_tranvantruong.Model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EvaluateHandler {
    private DBConnection dbConnection;

    public EvaluateHandler() {
        this.dbConnection = new DBConnection();
    }
    public void rating(Evaluate evaluate) {
        try (Connection connection = dbConnection.createConection()) {
            String sql = "INSERT INTO evaluate (user_id, product_id, ratingValue, comment, createdAt) VALUES (?, ?, ?,?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                preparedStatement.setInt(1, evaluate.getUser_id());
                preparedStatement.setInt(2, evaluate.getProduct_id());
                preparedStatement.setFloat(3, evaluate.getRating());
                preparedStatement.setString(4, evaluate.getComment());
                preparedStatement.setString(5, evaluate.getDate());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean hasUserRatedTour(int userId, int productId) {
        try (Connection connection = dbConnection.createConection()) {
            String query = "SELECT COUNT(*) FROM evaluate WHERE user_id = ? AND product_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, userId);
                preparedStatement.setInt(2, productId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int count = resultSet.getInt(1);
                        // If count is greater than 0, it means the user has already rated the tour
                        return count > 0;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public List<Evaluate> getAllEvaluateByProductId(int product_id) {
        List<Evaluate> evaluateList = new ArrayList<>();
        try (Connection connection = dbConnection.createConection()) {
            String query = "SELECT * FROM evaluate WHERE product_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, product_id);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Evaluate evaluate = new Evaluate();
                        evaluate.setId(resultSet.getInt("id"));
                        evaluate.setUser_id(resultSet.getInt("user_id"));
                        evaluate.setProduct_id(resultSet.getInt("product_id"));
                        evaluate.setRating(resultSet.getInt("ratingValue"));
                        evaluate.setComment(resultSet.getString("comment"));
                        evaluate.setDate(resultSet.getString("createdAt"));
                        evaluateList.add(evaluate);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return evaluateList;
    }
}
