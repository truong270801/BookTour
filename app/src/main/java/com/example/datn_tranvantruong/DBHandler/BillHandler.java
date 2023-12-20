package com.example.datn_tranvantruong.DBHandler;

import android.content.Context;

import com.example.datn_tranvantruong.Database.DBConnection;
import com.example.datn_tranvantruong.Model.Bill;
import com.example.datn_tranvantruong.Model.BillRevenue;
import com.example.datn_tranvantruong.Model.BillStatistic;
import com.example.datn_tranvantruong.Model.Cart;
import com.example.datn_tranvantruong.Model.Customer;
import com.example.datn_tranvantruong.Model.Pay;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BillHandler {

    private DBConnection dbConnection;
Context context;
    public BillHandler() {
        this.dbConnection = new DBConnection();
    }


    public void CreateBill(Pay pay) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateTimeString = sdf.format(new Date(System.currentTimeMillis()));
        try (Connection connection = dbConnection.createConection()) {
            String sql = "INSERT INTO bills (user_id,product_id,quatity,total_price,description,date_created, userEmail, userName, userPhone,userAddress) VALUES (?, ?, ?, ?,?,?,?,?,?,?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, pay.getUser_id());
                preparedStatement.setInt(2, pay.getProduct_id());
                preparedStatement.setInt(3, pay.getQuatity());
                preparedStatement.setInt(4, pay.getPrice());
                preparedStatement.setString(5, pay.getDescription());
                preparedStatement.setString(6, currentDateTimeString);
                preparedStatement.setString(7, pay.getUserEmail());
                preparedStatement.setString(8, pay.getUserName());
                preparedStatement.setString(9, pay.getUserPhone());
                preparedStatement.setString(10, pay.getUserAddress());


                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<BillStatistic> getBillByUserID(int id) {
        List<BillStatistic> billStatisticList = new ArrayList<>();
        try (Connection connection = dbConnection.createConection()) {
            String query = "SELECT * FROM bills WHERE user_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, id);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        BillStatistic billStatistic = new BillStatistic();
                        billStatistic.setBill_id(resultSet.getInt("id"));
                        billStatistic.setProduct_id(resultSet.getInt("product_id"));
                        billStatistic.setDate(resultSet.getString("date_created"));
                        billStatistic.setDescription(resultSet.getString("description"));
                        billStatistic.setPrice(resultSet.getInt("total_price"));

                        billStatisticList.add(billStatistic);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return billStatisticList;
    }
    public String getBillQuantityById(int id) {
        String billQuantity = null;
        try (Connection connection = dbConnection.createConection()) {
            String sql = "SELECT quatity FROM bills WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, id);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        billQuantity = resultSet.getString("quatity");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return billQuantity;
    }


    public List<BillStatistic> getAllBill() {
        List<BillStatistic> ls = new ArrayList<>();
        try (Connection connection = dbConnection.createConection()) {
            String query = "SELECT * FROM bills";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        BillStatistic bill = new BillStatistic();
                        bill.setBill_id(resultSet.getInt("id"));
                        bill.setProduct_id(resultSet.getInt("product_id"));
                        bill.setPrice(resultSet.getInt("total_price"));
                        bill.setDescription(resultSet.getString("description"));
                        bill.setDate(resultSet.getString("date_created"));
                        ls.add(bill);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ls;
    }

    public List<BillRevenue> getAllBillRevenue(String start_date, String end_date) {
        List<BillRevenue> ls = new ArrayList<>();
        try (Connection connection = dbConnection.createConection()) {
            String query = "SELECT id, quatity, total_price FROM bills WHERE date_created BETWEEN ? AND ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, start_date);
                preparedStatement.setString(2, end_date);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        BillRevenue billRevenue = new BillRevenue();
                        billRevenue.setBill_id(resultSet.getInt("id"));
                        billRevenue.setQuatity(resultSet.getInt("quatity"));
                        billRevenue.setPrice(String.valueOf(resultSet.getInt("total_price")));
                        ls.add(billRevenue);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ls;
    }

    public int getTotalBill(String start_date, String end_date) {
        int total = 0;
        try (Connection connection = dbConnection.createConection()) {
            String query = "SELECT SUM(total_price) FROM bills WHERE date_created BETWEEN ? AND ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, start_date);
                preparedStatement.setString(2, end_date);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        total = resultSet.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return total;
    }
    public Pay getBillInfo(int id) {
        Pay pay = new Pay();
        try (Connection connection = dbConnection.createConection()) {
            String query = "SELECT userEmail, userName, userPhone,userAddress FROM bills WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, id);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        pay.setUserEmail(resultSet.getString("userEmail"));
                        pay.setUserName(resultSet.getString("userName"));
                        pay.setUserPhone(resultSet.getString("userPhone"));
                        pay.setUserAddress(resultSet.getString("userAddress"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pay;
    }
}
