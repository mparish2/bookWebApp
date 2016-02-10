/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Matthew_2
 */
public class MySqlDBStrategy implements DBStrategy{
    private Connection conn;
    
    //what would be better this or bring it in from the construct. think about/research
    /**
     * 
     * @param driverClassName
     * @param url
     * @param username
     * @param password
     * @throws IllegalArgumentException
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    @Override
    public void openConnection(String driverClassName, String url, String username, String password) 
	throws IllegalArgumentException, ClassNotFoundException, SQLException
	{
		Class.forName (driverClassName);
		conn = DriverManager.getConnection(url, username, password);
	}
    /**
     * 
     * @throws SQLException 
     */
    @Override
    public void closeConnection()throws SQLException{
        conn.close();
    }
}
