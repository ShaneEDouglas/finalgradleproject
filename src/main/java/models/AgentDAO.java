/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import core.DB;
import entity.Agent;
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
public class AgentDAO implements DAO<Agent>  {
    List<Agent> agents;
    
    
     /**
     * Get a single contact entity as a contact object
     * @param id
     * @return 
     */
    @Override
public Optional<Agent> get(int id) {
    DB db = DB.getInstance();
    ResultSet rs = null;
    try {
        String sql = "SELECT * FROM Agents WHERE AgentID = ?";
        PreparedStatement stmt = db.getPreparedStatement(sql);
        stmt.setInt(1, id);
        rs = stmt.executeQuery();
        Agent agent = null;
        if (rs.next()) {
            agent = new Agent(rs.getInt("AgentID"), rs.getString("FirstName"), rs.getString("LastName"), rs.getString("ContactNumber"), rs.getString("Email"));
        }
        return Optional.ofNullable(agent);
    } catch (SQLException ex) {
        System.err.println(ex.toString());
        return Optional.empty();
    }
}
    
    /**
     * Get all contact entities as a List
     * @return 
     */
    @Override
public List<Agent> getAll() {
    DB db = DB.getInstance();
    ResultSet rs = null;
    agents = new ArrayList<>();
    try {
        String sql = "SELECT * FROM Agents ORDER BY AgentID";
        rs = db.executeQuery(sql);
        Agent agent = null;
        while (rs.next()) {
            agent = new Agent(
                rs.getInt("AgentID"), 
                rs.getString("FirstName"), 
                rs.getString("LastName"), 
                rs.getString("ContactNumber"), 
                rs.getString("Email")
            );
            agents.add(agent);
        }
        return agents;
    } catch (SQLException ex) {
        System.err.println(ex.toString());
        return null;
    }
}
    
    /**
     * Insert a contact object into contact table
     * @param contact 
     */
  @Override
    public void insert(Agent agent) {
        DB db = DB.getInstance();
        try {
            String sql = "INSERT INTO Agent (id, firstName, lastName, phoneNumber, email) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = db.getPreparedStatement(sql);
            stmt.setInt(1, agent.getAgentID());
            stmt.setString(2, agent.getFirstName());
            stmt.setString(3, agent.getLastName());
            stmt.setString(4, agent.getContactNumber());
            stmt.setString(5, agent.getEmail());
            int rowInserted = stmt.executeUpdate();
            if (rowInserted > 0) {
                System.out.println("A new agent was inserted successfully!");
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
    public void update(Agent agent) {
        DB db = DB.getInstance();
        try {
            String sql = "UPDATE Agent SET firstName=?, lastName=?, phoneNumber=?, email=? WHERE id=?";
            PreparedStatement stmt = db.getPreparedStatement(sql);
            stmt.setString(1, agent.getFirstName());
            stmt.setString(2, agent.getLastName());
            stmt.setString(3, agent.getContactNumber());
            stmt.setString(4, agent.getEmail());
            stmt.setInt(5, agent.getAgentID());
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("An existing agent was updated successfully!");
            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }
    
    /**
     * Delete a contact from contact table if the entity exists
     * @param contact 
     */
    @Override
    public void delete(Agent agent) {
        DB db = DB.getInstance();
        try {
            String sql = "DELETE FROM Agent WHERE id = ?";
            PreparedStatement stmt = db.getPreparedStatement(sql);
            stmt.setInt(1, agent.getAgentID());
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("An agent was deleted successfully!");
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
            String sql = "SELECT * FROM Agent WHERE id = -1"; // Dummy query to get column names
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
