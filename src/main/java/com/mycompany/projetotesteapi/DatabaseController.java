/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.projetotesteapi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author Joao
 */
public class DatabaseController {
    
    private String jdbcURL = "jdbc:postgresql://localhost:5432/databaseteste";
    private String db_username = "postgres";
    private String db_password = "password";
    
    private static DatabaseController instance;
    
    private Connection connection;
    
    public DatabaseController() {

    }
    
    public static DatabaseController getInstance() {
        if(instance == null) {
            instance = new DatabaseController();
        }
        return instance;
    }
    
    private void connectToDatabase() throws NamingException, Exception { 
        
//        try {          
//            connection = DriverManager.getConnection(jdbcURL, db_username, db_password);           
//            System.out.println("Connected to database.");      
//        } catch (SQLException e) {
//            System.out.println("Error in connecting to database server!");
//            e.printStackTrace();
//        }   
        
        InitialContext cxt = new InitialContext();
        if(cxt == null) {
            throw new Exception("No context!");
        }
        DataSource ds = (DataSource) cxt.lookup("java:/comp/env/jdbc/postgres");
        if(ds == null) {
            throw new Exception("Data source not found!");
        }
        try {
            connection = ds.getConnection();
            System.out.println("Connected to database.");
        } catch (SQLException e) {
            System.out.println("Error in connecting to database server!");
            e.printStackTrace();
        }       
    }
    
    
    public void closeConnection() {       
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error closing connection!");
            e.printStackTrace();
        }    
    }
    
    public int createUser(User user) {
        closeConnection();
        try {
            connectToDatabase();
        } catch (Exception ex) {
            Logger.getLogger(DatabaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Retorna o ID do novo registo na DB. 
        // Depois usa-se o ResultSet para saber qual e'       
        String sql = "INSERT INTO users (name, email) VALUES (?, ?) RETURNING id;";
        
        try {   
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            // O .executeUpdate() so' retorna um int (1 ou 0)
            // int rows = statement.executeUpdate();
            statement.execute();
            
            // Ve no result entrada gerada para conseguir saber o id.
            // Nao sei se retorna sempre o correto. confirmar.
            
            ResultSet generatedKeys = statement.getResultSet();
            if(generatedKeys.next()) {
                user.setId(generatedKeys.getInt(1));
            } else {
                throw new SQLException("Creating user failed, no ID obtained.");
            }           
        } catch (SQLException e) {
            e.printStackTrace();
        }       
        closeConnection();       
        return user.getId();
    }
    
    public List<User> listAll() throws Exception {
        connectToDatabase();
        try {
            String sql = "SELECT * FROM users";
            PreparedStatement statement = connection.prepareStatement(sql);
            // O .executeUpdate() so' retorna um int (1 ou 0)
            // int rows = statement.executeUpdate();
            statement.execute();
            ResultSet result = statement.getResultSet();
            List<User> list = new ArrayList<>();
            while(result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                String email = result.getString("email");
                list.add(new User(id, name, email));
            }
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        closeConnection();
        return null;
    }
}
