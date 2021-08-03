/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.projetotesteapi;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
        List<User> list = dbcontroller.listAll();
        return list;
    }
    
    public int add(User user) {
        
//        int newId = data.size() + 1;
//        user.setId(newId);
//        data.add(user);       
//        return newId;

        int newId = dbcontroller.createUser(user);
        return newId;
    }
    
    public User get(int id) {
        User userToFind = new User(id);
        int index = data.indexOf(userToFind);
        if(index >= 0) {
            return data.get(index);
        }
        return null;
    }
    
    public boolean update(User user) {
        int index = data.indexOf(user);
        if(index >= 0) {
            data.set(index, user);
            return true;
        }
        return false;
    }
    
    public boolean delete(int id) {
        User userToFind = new User(id);
        int index = data.indexOf(userToFind);
        if(index >= 0) {
            data.remove(index);
            return true;
        }
        return false;
    }
    
}
