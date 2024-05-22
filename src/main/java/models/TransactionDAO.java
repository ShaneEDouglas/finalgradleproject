/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import core.DB;
import entity.DAO;
import entity.Transaction;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Shane D
 */
public class TransactionDAO implements DAO<Transaction> {
    List<Transaction> transactions;
    
    @Override
    public Optional<Transaction> get(int id) {
        DB db = DB.getInstance();
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM Transaction WHERE transactionId = ?";
            PreparedStatement stmt = db.getPreparedStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            Transaction transaction = null;
            if (rs.next()) {
                transaction = new Transaction(
                    rs.getInt("transactionId"), 
                    rs.getInt("propertyId"), 
                    rs.getInt("clientId"), 
                    rs.getDate("transactionDate"), 
                    rs.getDouble("salePrice")
                );
            }
            return Optional.ofNullable(transaction);
        } catch (SQLException ex) {
            System.err.println(ex.toString());
            return null;
        }
    }

    @Override
    public List<Transaction> getAll() {
        DB db = DB.getInstance();
        ResultSet rs = null;
        transactions.clear();
        try {
            String sql = "SELECT * FROM Transaction ORDER BY transactionId";
            rs = db.executeQuery(sql);
            while (rs.next()) {
                transactions.add(new Transaction(
                    rs.getInt("transactionId"), 
                    rs.getInt("propertyId"), 
                    rs.getInt("clientId"), 
                    rs.getDate("transactionDate"), 
                    rs.getDouble("salePrice")
                ));
            }
            return transactions;
        } catch (SQLException ex) {
            System.err.println(ex.toString());
            return null;
        }
    }

    @Override
    public void insert(Transaction transaction) {
        DB db = DB.getInstance();
        try {
            String sql = "INSERT INTO Transaction (transactionId, propertyId, clientId, transactionDate, salePrice) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = db.getPreparedStatement(sql);
            stmt.setInt(1, transaction.getTransactionId());
            stmt.setInt(2, transaction.getPropertyId());
            stmt.setInt(3, transaction.getClientId());
            stmt.setDate(4, transaction.getTransactionDate());
            stmt.setDouble(5, transaction.getSalePrice());
            int rowInserted = stmt.executeUpdate();
            if (rowInserted > 0) {
                System.out.println("A new transaction was inserted successfully!");
            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }

    @Override
    public void update(Transaction transaction) {
        DB db = DB.getInstance();
        try {
            String sql = "UPDATE Transaction SET propertyId=?, clientId=?, transactionDate=?, salePrice=? WHERE transactionId=?";
            PreparedStatement stmt = db.getPreparedStatement(sql);
            stmt.setInt(1, transaction.getPropertyId());
            stmt.setInt(2, transaction.getClientId());
            stmt.setDate(3, transaction.getTransactionDate());
            stmt.setDouble(4, transaction.getSalePrice());
            stmt.setInt(5, transaction.getTransactionId());
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("An existing transaction was updated successfully!");
            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }

    @Override
    public void delete(Transaction transaction) {
        DB db = DB.getInstance();
        try {
            String sql = "DELETE FROM Transaction WHERE transactionId = ?";
            PreparedStatement stmt = db.getPreparedStatement(sql);
            stmt.setInt(1, transaction.getTransactionId());
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("A transaction was deleted successfully!");
            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }

    @Override
    public List<String> getColumnNames() {
        DB db = DB.getInstance();
        ResultSet rs = null;
        List<String> headers = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Transaction WHERE transactionId = -1"; // Dummy query to get column names
            rs = db.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();
            int numberCols = rsmd.getColumnCount();
            for (int i = 1; i <= numberCols; i++) {
                headers.add(rsmd.getColumnLabel(i));
            }
            return headers;
        } catch (SQLException ex) {
            System.err.println(ex.toString());
            return null;
        } 
    }
    
}
