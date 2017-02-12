/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Diego Jacobs
 */
public class DBConnector {
    private Connection Connection;
    private String url = "jdbc:postgresql://localhost/SMTP";
    private String user = "postgres";
    private String password = "root";
    private PreparedStatement PreparedStatement;
    private Statement Statement;
    private ResultSet ResultSet;
    
    public DBConnector(){
        
    }
    
    public void Connect() throws SQLException{
        Connection = DriverManager.getConnection(url, user, password);
    }
    
    public Connection Connection() {
        return Connection;
    }
    
    public PreparedStatement PreparedStatement() {
        return PreparedStatement;
    }

    public void PreparedStatement(String stm) throws SQLException {
        this.PreparedStatement = Connection.prepareStatement(stm);
    }
    
    public Statement Statement() {
        return Statement;
    }
    
     public void CreateStatement() throws SQLException {
        this.Statement = Connection.createStatement();
    }
    
    public void CloseConnection() throws SQLException{
        Connection.close();
        Connection = null;
    }
    
    public void ClosePreparedStatement() throws SQLException{
        PreparedStatement.close();
        PreparedStatement = null;
    }
    
    public void CloseStatement() throws SQLException{
        Statement.close();
        Statement = null;
    }
    
    public void SaveChanges() throws SQLException{
        PreparedStatement.executeUpdate();
    }
    
    public void SaveChanges(String stm) throws SQLException{
        Statement.executeUpdate(stm);
    }
    
    public void ExecutePreparedStatement() throws SQLException{
        ResultSet = PreparedStatement.executeQuery();
    }

    public ResultSet ResultSet() {
        return ResultSet;
    }
}
