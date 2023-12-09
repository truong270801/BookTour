package com.example.datn_tranvantruong.DBHandler;


import com.example.datn_tranvantruong.Database.DBConnection;
import com.example.datn_tranvantruong.Model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductHandler {

    private DBConnection dbConnection;

    public ProductHandler() {
        this.dbConnection = new DBConnection();
    }

    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        try (Connection connection = dbConnection.createConection()) {
            String query = "SELECT * FROM products";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    CategoryHandler categoryHandler = new CategoryHandler();
                    Product product = new Product();
                    product.setId(resultSet.getInt("id"));
                    product.setCategoryName(categoryHandler.getCategoryNameById(resultSet.getInt("category_id")));
                    product.setName(resultSet.getString("name"));
                    product.setStartdate(resultSet.getString("startdate"));
                    product.setEnddate(resultSet.getString("enddate"));
                    product.setLocation(resultSet.getString("location"));
                    product.setDescription(resultSet.getString("description"));
                    product.setPrice(resultSet.getInt("price"));
                    product.setImage(resultSet.getBytes("image"));
                    productList.add(product);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    public void createProduct(int category_id, String name, String startdate, String enddate, String description, String location,
                              int price, byte[] image) {
        try (Connection connection = dbConnection.createConection()) {
            String sql = "INSERT INTO products (category_id, name, startdate, enddate, description, location, price, image) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, category_id);
                preparedStatement.setString(2, name);
                preparedStatement.setString(3, startdate);
                preparedStatement.setString(4, enddate);
                preparedStatement.setString(5, description);
                preparedStatement.setString(6, location);
                preparedStatement.setInt(7, price);
                preparedStatement.setBytes(8, image);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editProduct(int id, int category_id, String name, String startdate, String enddate, String description, String location,
                            int price, byte[] image) {
        try (Connection connection = dbConnection.createConection()) {
            String sql = "UPDATE products SET category_id = ?, name = ?, startdate = ?, enddate = ?, description = ?, location = ?, price = ?, image = ? WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, category_id);
                preparedStatement.setString(2, name);
                preparedStatement.setString(3, startdate);
                preparedStatement.setString(4, enddate);
                preparedStatement.setString(5, description);
                preparedStatement.setString(6, location);
                preparedStatement.setInt(7, price);
                preparedStatement.setBytes(8, image);
                preparedStatement.setInt(9, id);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteProduct(int id) {
        try (Connection connection = dbConnection.createConection()) {
            String sql = "DELETE FROM products WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getProductNameById(int id) {
        String name = null;
        try (Connection connection = dbConnection.createConection()) {
            String query = "SELECT name FROM products WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, id);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        name = resultSet.getString("name");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return name;
    }

    public int getCategoryIdById(int id) {
        int category_id = -1;
        try (Connection connection = dbConnection.createConection()) {
            String query = "SELECT category_id FROM products WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, id);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        category_id = resultSet.getInt("category_id");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return category_id;
    }

    public byte[] getProductImageById(int id) {
        byte[] image = null;
        try (Connection connection = dbConnection.createConection()) {
            String query = "SELECT image FROM products WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, id);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        image = resultSet.getBytes("image");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return image;
    }

    public Product findById(int id) {
        Product product = null;
        try (Connection connection = dbConnection.createConection()) {
            String query = "SELECT * FROM products WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, id);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        product = new Product();
                        product.setId(id);
                        product.setName(resultSet.getString("name"));
                        product.setStartdate(resultSet.getString("startdate"));
                        product.setEnddate(resultSet.getString("enddate"));
                        product.setLocation(resultSet.getString("location"));
                        product.setDescription(resultSet.getString("description"));
                        product.setPrice(resultSet.getInt("price"));
                        product.setImage(resultSet.getBytes("image"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    public List<Product> getAllProductByCategoryId(int category_id) {
        List<Product> productList = new ArrayList<>();
        try (Connection connection = dbConnection.createConection()) {
            String query = "SELECT * FROM products WHERE category_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, category_id);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Product product = new Product();
                        product.setId(resultSet.getInt("id"));
                        product.setName(resultSet.getString("name"));
                        product.setLocation(resultSet.getString("location"));
                        product.setPrice(resultSet.getInt("price"));
                        product.setImage(resultSet.getBytes("image"));
                        product.setRating(resultSet.getFloat("rating"));
                        productList.add(product);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }
    public float getRatingById(int id) {
        float rating = 1;
        try (Connection connection = dbConnection.createConection()) {
            String query = "SELECT rating FROM products WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, id);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                         rating = resultSet.getInt("rating");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rating;
    }
    public void updateAverageRating(int productId) {
        try (Connection connection = dbConnection.createConection()) {
            String updateQuery = "UPDATE products SET rating = (SELECT AVG(ratingValue) FROM evaluate WHERE product_id = ? GROUP BY product_id) WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setInt(1, productId);
                preparedStatement.setInt(2, productId);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Hoặc xử lý lỗi theo cách phù hợp
        }
    }
}
