/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import core.DB;
import entity.Agent;
import entity.Client;
import entity.DAO;
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
public class ClientDAO implements DAO<Client> {
    
    private List<Client> clients;
    
    
    @Override
    public Optional<Client> get(int id) {
        DB db = DB.getInstance();
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM Clients WHERE id = ?";
            PreparedStatement stmt = db.getPreparedStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            Client client = null;
            if (rs.next()) {
                client = new Client(rs.getInt("id"), rs.getString("firstName"), rs.getString("lastName"), rs.getString("phoneNumber"), rs.getString("email"));
            }
            return Optional.ofNullable(client);
        } catch (SQLException ex) {
            System.err.println(ex.toString());
            return null;
        }
    }
    
    /**
     * Get all contact entities as a List
     * @return 
     */
    @Override
    public List<Client> getAll() {
        DB db = DB.getInstance();
        ResultSet rs = null;
        clients = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Clients ORDER BY ClientID";
            rs = db.executeQuery(sql);
            Client client = null;
            while (rs.next()) {
                clients.add(new Client(rs.getInt("ClientID"), rs.getString("FirstName"), rs.getString("LastName"), rs.getString("ContactNumber"), rs.getString("Email")));
                clients.add(client);
            }
            return clients;
        } catch (SQLException ex) {
            System.err.println(ex.toString());
            return null;
        }
    }
    
     
    
    /**
     * Insert a contact object into contact table
     * @param client 
     */
  @Override
    public void insert(Client client) {
        DB db = DB.getInstance();
        try {
            String sql = "INSERT INTO Clients (firstName, lastName, phoneNumber, email) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = db.getPreparedStatement(sql);
            stmt.setString(1, client.getFirstName());
            stmt.setString(2, client.getLastName());
            stmt.setString(3, client.getContactNumber());
            stmt.setString(4, client.getEmail());
            int rowInserted = stmt.executeUpdate();
            if (rowInserted > 0) {
                System.out.println("A new client was inserted successfully!");
            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }
    
    /**
     * Update a contact entity in database if it exists using a contact object
     * @param contact
     */
@Override
    public void update(Client client) {
        DB db = DB.getInstance();
        try {
            String sql = "UPDATE Clients SET firstName=?, lastName=?, phoneNumber=?, email=? WHERE id=?";
            PreparedStatement stmt = db.getPreparedStatement(sql);
            stmt.setString(1, client.getFirstName());
            stmt.setString(2, client.getLastName());
            stmt.setString(3, client.getContactNumber());
            stmt.setString(4, client.getEmail());
            stmt.setInt(5, client.getClientId());
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("An existing client was updated successfully!");
            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }
    
    /**
     * Delete a contact from contact table if the entity exists
     * @param Client 
     */
    @Override
    public void delete(Client client) {
        DB db = DB.getInstance();
        try {
            String sql = "DELETE FROM Clients WHERE id = ?";
            PreparedStatement stmt = db.getPreparedStatement(sql);
            stmt.setInt(1, client.getClientId());
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("A client was deleted successfully!");
            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }
    
    /**
     * Get all column names in a list array
     * @return 
     */
     @Override
    public List<String> getColumnNames() {
        DB db = DB.getInstance();
        ResultSet rs = null;
        List<String> headers = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Clients WHERE id = -1"; // Dummy query to get column names
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
