/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import core.DB;
import entity.DAO;
import entity.Viewing;
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
public class ViewingDAO implements DAO<Viewing> {
    private List<Viewing> viewings;
    
        @Override
    public Optional<Viewing> get(int id) {
        DB db = DB.getInstance();
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM Viewing WHERE viewingId = ?";
            PreparedStatement stmt = db.getPreparedStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            Viewing viewing = null;
            if (rs.next()) {
                viewing = new Viewing(
                    rs.getInt("viewingId"), 
                    rs.getInt("propertyId"), 
                    rs.getInt("clientId"), 
                    rs.getDate("viewingDate"), 
                    rs.getInt("agentId")
                );
            }
            return Optional.ofNullable(viewing);
        } catch (SQLException ex) {
            System.err.println(ex.toString());
            return null;
        }
    }

    @Override
    public List<Viewing> getAll() {
        DB db = DB.getInstance();
        ResultSet rs = null;
        viewings.clear();
        try {
            String sql = "SELECT * FROM Viewing ORDER BY viewingId";
            rs = db.executeQuery(sql);
            while (rs.next()) {
                viewings.add(new Viewing(
                    rs.getInt("viewingId"), 
                    rs.getInt("propertyId"), 
                    rs.getInt("clientId"), 
                    rs.getDate("viewingDate"), 
                    rs.getInt("agentId")
                ));
            }
            return viewings;
        } catch (SQLException ex) {
            System.err.println(ex.toString());
            return null;
        }
    }

    @Override
    public void insert(Viewing viewing) {
        DB db = DB.getInstance();
        try {
            String sql = "INSERT INTO Viewing (viewingId, propertyId, clientId, viewingDate, agentId) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = db.getPreparedStatement(sql);
            stmt.setInt(1, viewing.getViewingId());
            stmt.setInt(2, viewing.getPropertyId());
            stmt.setInt(3, viewing.getClientId());
            stmt.setDate(4, viewing.getViewingDate());
            stmt.setInt(5, viewing.getAgentId());
            int rowInserted = stmt.executeUpdate();
            if (rowInserted > 0) {
                System.out.println("A new viewing was inserted successfully!");
            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }

    @Override
    public void update(Viewing viewing) {
        DB db = DB.getInstance();
        try {
            String sql = "UPDATE Viewing SET propertyId=?, clientId=?, viewingDate=?, agentId=? WHERE viewingId=?";
            PreparedStatement stmt = db.getPreparedStatement(sql);
            stmt.setInt(1, viewing.getPropertyId());
            stmt.setInt(2, viewing.getClientId());
            stmt.setDate(3, viewing.getViewingDate());
            stmt.setInt(4, viewing.getAgentId());
            stmt.setInt(5, viewing.getViewingId());
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("An existing viewing was updated successfully!");
            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }

    @Override
    public void delete(Viewing viewing) {
        DB db = DB.getInstance();
        try {
            String sql = "DELETE FROM Viewing WHERE viewingId = ?";
            PreparedStatement stmt = db.getPreparedStatement(sql);
            stmt.setInt(1, viewing.getViewingId());
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("A viewing was deleted successfully!");
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
            String sql = "SELECT * FROM Viewing WHERE viewingId = -1"; // Dummy query to get column names
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

