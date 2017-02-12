/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repositories;

import Models.User;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Diego Jacobs
 */
public class UserRepository {
    private final DBConnector dbContext;

    public UserRepository() throws SQLException {
        dbContext = new DBConnector();
    }
    
    public void AddUser(User user){
        try {
            if(dbContext.Connection() == null)
                dbContext.Connect();
            
            String stm = "INSERT INTO my_user (first_name, last_name, email, password) VALUES(?, ?, ?, ?)";
            dbContext.PreparedStatement(stm);
            dbContext.PreparedStatement().setString(1, user.getFirstName());
            dbContext.PreparedStatement().setString(2, user.getLastName());
            dbContext.PreparedStatement().setString(3, user.getEmail()); 
            dbContext.PreparedStatement().setString(4, user.getPassword());                     
            dbContext.SaveChanges();

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(UserRepository.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        } finally {

            try {
                if (dbContext.PreparedStatement() != null) {
                    dbContext.ClosePreparedStatement();
                }
                if (dbContext.Connection() != null) {
                    dbContext.CloseConnection();
                }

            } catch (SQLException ex) {
                Logger lgr = Logger.getLogger(UserRepository.class.getName());
                lgr.log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
    }
    
    public void UpdateUser(User user){
        try {
            if(dbContext.Connection() == null)
                dbContext.Connect();
            
            String stm = "UPDATE my_user SET ";
            stm += "first_name = '"+ user.getFirstName() + "'";
            stm += ", last_name = '" + user.getLastName() +"'";
            stm += ", email = '" + user.getEmail() + "'";
            stm += ", password = '" + user.getPassword() + "'";
            stm += "WHERE my_user_id = " + user.getUserId() + ";";    
            dbContext.CreateStatement();
            dbContext.SaveChanges(stm);

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(UserRepository.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        } finally {

            try {
                if (dbContext.Statement() != null) {
                    dbContext.CloseStatement();
                }
                if (dbContext.Connection() != null) {
                    dbContext.CloseConnection();
                }

            } catch (SQLException ex) {
                Logger lgr = Logger.getLogger(UserRepository.class.getName());
                lgr.log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
    }
    
    public User GetUserById(int UserId){
        User user = new User();
            
        try {
            if(dbContext.Connection() == null)
                dbContext.Connect();
            
            String stm = "SELECT * FROM my_user WHERE my_user_id = ?";
            dbContext.PreparedStatement(stm);
            dbContext.PreparedStatement().setInt(1, UserId);                   
            dbContext.ExecutePreparedStatement();
            while (dbContext.ResultSet().next()) {
                user.setUserId(dbContext.ResultSet().getInt(1));
                user.setFirstName(dbContext.ResultSet().getString(2));
                user.setLastName(dbContext.ResultSet().getString(3));
                user.setEmail(dbContext.ResultSet().getString(4));
                user.setPassword(dbContext.ResultSet().getString(5));
            }
            
            return user;

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(UserRepository.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        } finally {

            try {
                if (dbContext.PreparedStatement() != null) {
                    dbContext.ClosePreparedStatement();
                }
                if (dbContext.Connection() != null) {
                    dbContext.CloseConnection();
                }

            } catch (SQLException ex) {
                Logger lgr = Logger.getLogger(UserRepository.class.getName());
                lgr.log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
        
        return user;
    }
}
