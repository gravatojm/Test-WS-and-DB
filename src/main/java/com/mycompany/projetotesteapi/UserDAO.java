/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.projetotesteapi;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Joao
 */

// Data Access Object
public class UserDAO {
    
    private static UserDAO instance;
    private static List<User> data = new ArrayList<>();
    
    private DatabaseController dbcontroller = DatabaseController.getInstance();
    
    static { 
        data.add(new User(1, "Cristiano Ronaldo", "cristiano@ronaldo.com"));
        data.add(new User(2, "Rui Patricio", "rui@patricio.com"));
    }
    
    private UserDAO() {
        
    }
    
    public static UserDAO getInstance() {
        if(instance == null) {
            instance = new UserDAO();
        }
        return instance;
    }
    
    public List<User> listAll() {
        // return new ArrayList<User>(data);
        List<User> list = new ArrayList<>();
        try {
            list = dbcontroller.listAll();
        } catch (Exception ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    public int add(User user) {
        int newId = dbcontroller.createUser(user);
        return newId;
    }
    
    public User get(int id) {
          User userToFind = dbcontroller.getUser(id);
          return userToFind;
    }
    
    public boolean update(User user) {
        return dbcontroller.editUser(user);
    }
    
    public boolean delete(int id) {
        boolean res = dbcontroller.deleteUser(id);
        return res;
    }
    
}
