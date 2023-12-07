package com.example.datn_tranvantruong.DBHandler;

import com.example.datn_tranvantruong.Database.DBConnection;
import com.example.datn_tranvantruong.Model.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryHandler {

    private DBConnection dbConnection;

    public CategoryHandler() {
        this.dbConnection = new DBConnection();
    }

    public int insertCategory(Category category) {
        try (Connection connection = dbConnection.createConection()) {
            String sql = "INSERT INTO categories (name) VALUES (?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, category.getNameCategory());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
        return 1;
    }

    public List<String> getAllCategory() {
        List<String> ls = new ArrayList<>();
        try (Connection connection = dbConnection.createConection()) {
            String query = "SELECT * FROM categories";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Category category = new Category();
                        category.setIdCategory(resultSet.getInt("id"));
                        category.setNameCategory(resultSet.getString("name"));
                        String chuoi = category.getIdCategory() + " - " + category.getNameCategory();
                        ls.add(chuoi);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ls;
    }

    public List<String> getAllNameCategoryForCreateProduct() {
        List<String> listCategory = new ArrayList<>();
        try (Connection connection = dbConnection.createConection()) {
            String query = "SELECT name FROM categories";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        listCategory.add(resultSet.getString("name"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listCategory;
    }

    public Integer getCategoryIdByName(String categoryName) {
        try (Connection connection = dbConnection.createConection()) {
            String query = "SELECT id FROM categories WHERE name = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, categoryName);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getInt("id");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getCategoryNameById(int id) {
        try (Connection connection = dbConnection.createConection()) {
            String query = "SELECT name FROM categories WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, id);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getString("name");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int deleteCategory(String _id) {
        try (Connection connection = dbConnection.createConection()) {
            String sql = "DELETE FROM categories WHERE id=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, Integer.parseInt(_id));
                return preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int updateCategory(Category category) {
        try (Connection connection = dbConnection.createConection()) {
            String sql = "UPDATE categories SET name=? WHERE id=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, category.getNameCategory());
                preparedStatement.setInt(2, category.getIdCategory());
                return preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public List<Category> getAllCategoriesWithIdCategory() {
        List<Category> ls = new ArrayList<>();
        try (Connection connection = dbConnection.createConection()) {
            String query = "SELECT * FROM categories";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Category category = new Category();
                        category.setIdCategory(resultSet.getInt("id"));
                        category.setNameCategory(resultSet.getString("name"));

                        ls.add(category);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ls;
    }

}
