/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.projetotesteapi;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Joao
 */
public class ProductDAO {
    
    private static ProductDAO instance;
    private static List<Product> data = new ArrayList<>();
    
    static {
        data.add(new Product(1, "iPad Pro 2021", "Apple", "Tablet", 1000));
        data.add(new Product(2, "UE32T4005", "Samsung", "TV", 250));
    }
    
    private ProductDAO() {
        
    }
    
    public static ProductDAO getInstance() {
        if(instance == null) {
            instance = new ProductDAO();
        }
        return instance;
    }
    
    public ArrayList<Product> listAll() {
        return new ArrayList<Product>(data);
    }
    
    public int add(Product prod) {
        int newId = data.size() + 1;
        prod.setId(newId);
        data.add(prod);       
        return newId;
    }
    
    public Product get(int id) {
        Product productToFind = new Product(id);
        int index = data.indexOf(productToFind);
        if(index >= 0) {
            return data.get(index);
        }
        return null;
    }
    
    public boolean update(Product prod) {
        int index = data.indexOf(prod);
        if(index >= 0) {
            data.set(index, prod);
            return true;
        }
        return false;
    }
    
    public boolean delete(int id) {
        Product productToFind = new Product(id);
        int index = data.indexOf(productToFind);
        if(index >= 0) {
            data.remove(index);
            return true;
        }
        return false;
    }
    
}
