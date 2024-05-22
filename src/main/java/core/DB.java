/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.flywaydb.core.Flyway;

/**
 *
 * @author Shane D
 */
public class DB {
    private static final String DB_URL = "jdbc:derby://localhost:1527/AirportDataBase;create=true;user=shane;password=douglas";    
    private static final String MIGRATION_DIR = "classpath:db/migration";
    private static DB instance = null;
    private final Connection mConnection;
    
    private DB() throws SQLException
    {
        migrateDb();//Migrate DB first before doing anything
        mConnection = DriverManager.getConnection(DB_URL);
    }
    
    /**
     * This is the Singleton method that returns only one database instance
     * @return 
     */
    public static DB getInstance() {
        if (instance == null) {
            try {
                instance = new DB();
            } catch (SQLException ex) {
                System.err.println("Database Error: " + ex.toString());
                System.exit(1);
            }
        }

        return instance;
    }
    
    /**
     * Use this method for INSERT, UPDATE, DELETE
     * @param sql
     * @return
     * @throws SQLException 
     */
    public int executeUpdate(String sql) throws SQLException {
        return mConnection.createStatement().executeUpdate(sql);
    }

    /**
     * Use this method to return ResultSet for SELECT SQL queries with no parameters
     * @param sql
     * @return
     * @throws SQLException 
     */
    public ResultSet executeQuery(String sql) throws SQLException {
        return mConnection.createStatement().executeQuery(sql);
    }

    /**
     * Use this method to return PreparedStatement for SELECT SQL queries with parameters 
     * @param sql
     * @return
     * @throws SQLException 
     */
    public PreparedStatement getPreparedStatement(String sql) throws SQLException {
        return mConnection.prepareStatement(sql);
    }
    
    /**
     * This migrateDB method is used to create tables under db.migrations package
     * if they are not created before
     * We use FlyWay class from flyway library to perform the migration in our code
     */
    private void migrateDb() {
        Flyway flyway = new Flyway();
        flyway.setDataSource(DB_URL, "shane", "douglas");//Set the database
        flyway.setLocations("classpath:db/migrations");//It will run these SQL scripts under Migration Directory
        flyway.setBaselineOnMigrate(true);
        flyway.migrate();//This performs the migration, basically creating tables and inserting values into tables if necessary
    }
}
