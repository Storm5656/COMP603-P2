/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment2.Models;

import java.sql.Connection;

/**
 *
 * @author ren
 */
public abstract class AbstractHandler<T> {
    protected Connection conn;
    
    public AbstractHandler(){
        conn = DatabaseManager.getConnection();
    }
    
    public abstract void create(T obj);
    public abstract void edit(T obj);
    public abstract void delete(T obj);
    public abstract T get(String n);
    public abstract T get(int i);
}
