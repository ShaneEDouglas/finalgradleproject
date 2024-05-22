/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import core.DB;
import entity.Agent;
import entity.Client;
import entity.DAO;
import entity.Property;
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
public class PropertyDAO implements DAO<Property> {
    List<Property> properties;
    
    @Override
    public Optional<Property> get(int id) {
        DB db = DB.getInstance();
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM Property WHERE propertyId = ?";
            PreparedStatement stmt = db.getPreparedStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            Property property = null;
            if (rs.next()) {
                property = new Property(rs.getInt("propertyId"), rs.getString("address"), rs.getDouble("price"), rs.getString("propertyType"), rs.getString("status"), rs.getInt("agentId"));
            }
            return Optional.ofNullable(property);
        } catch (SQLException ex) {
            System.err.println(ex.toString());
            return null;
        }
    }

    @Override
    public List<Property> getAll() {
        DB db = DB.getInstance();
        ResultSet rs = null;
        properties.clear(); // Clear the list each time to prevent duplication
        try {
            String sql = "SELECT * FROM Property ORDER BY propertyId";
            rs = db.executeQuery(sql);
            while (rs.next()) {
                Property property = new Property(rs.getInt("propertyId"), rs.getString("address"), rs.getDouble("price"), rs.getString("propertyType"), rs.getString("status"), rs.getInt("agentId"));
                properties.add(property);
            }
            return properties;
        } catch (SQLException ex) {
            System.err.println(ex.toString());
            return null;
        }
    }
    
    @Override
    public void insert(Property property) {
        DB db = DB.getInstance();
        try {
            String sql = "INSERT INTO Property (propertyId, address, price, propertyType, status, agentId) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = db.getPreparedStatement(sql);
            stmt.setInt(1, property.getPropertyId());
            stmt.setString(2, property.getAddress());
            stmt.setDouble(3, property.getPrice());
            stmt.setString(4, property.getPropertyType());
            stmt.setString(5, property.getStatus());
            stmt.setInt(6, property.getAgentId());
            int rowInserted = stmt.executeUpdate();
            if (rowInserted > 0) {
                System.out.println("A new property was inserted successfully!");
            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }

    @Override
    public void update(Property property) {
        DB db = DB.getInstance();
        try {
            String sql = "UPDATE Property SET address=?, price=?, propertyType=?, status=?, agentId=? WHERE propertyId=?";
            PreparedStatement stmt = db.getPreparedStatement(sql);
            stmt.setString(1, property.getAddress());
            stmt.setDouble(2, property.getPrice());
            stmt.setString(3, property.getPropertyType());
            stmt.setString(4, property.getStatus());
            stmt.setInt(5, property.getAgentId());
            stmt.setInt(6, property.getPropertyId());
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("An existing property was updated successfully!");
            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }

    @Override
    public void delete(Property property) {
        DB db = DB.getInstance();
        try {
            String sql = "DELETE FROM Property WHERE propertyId = ?";
            PreparedStatement stmt = db.getPreparedStatement(sql);
            stmt.setInt(1, property.getPropertyId());
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("A property was deleted successfully!");
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
            String sql = "SELECT * FROM Property WHERE propertyId = -1"; // Dummy query to get column names
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
