/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repositories;

import Models.Email;
import Models.User;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Date;
import java.sql.Timestamp;

/**
 *
 * @author Diego Jacobs
 */
public class EmailRepository {
    private final DBConnector dbContext;

    public EmailRepository(){
        dbContext = new DBConnector();
    }
    
    public void AddEmail(Email email){
        try {
            if(dbContext.Connection() == null)
                dbContext.Connect();
            
            String stm = "INSERT INTO email (from_email, to_email, subject, content, labelFrom, labelTo, cc, bcc, date) VALUES(?, ?, ?, ?, ?, ?, ?, ?, now())";
            dbContext.PreparedStatement(stm);
            dbContext.PreparedStatement().setString(1, email.getFrom());
            dbContext.PreparedStatement().setString(2, email.getTo());
            dbContext.PreparedStatement().setString(3, email.getSubject()); 
            dbContext.PreparedStatement().setString(4, email.getContent());
            dbContext.PreparedStatement().setString(5, email.getLabelFrom());
            dbContext.PreparedStatement().setString(6, email.getLabelTo().toString());
            dbContext.PreparedStatement().setString(7, email.getCc().toString()); 
            dbContext.PreparedStatement().setString(8, email.getBcc().toString());
            dbContext.SaveChanges();

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(EmailRepository.class.getName());
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
                Logger lgr = Logger.getLogger(EmailRepository.class.getName());
                lgr.log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
    }
    
    public void UpdateEmail(Email email){
        try {
            if(dbContext.Connection() == null)
                dbContext.Connect();
            
            String stm = "UPDATE email SET ";
            stm += "from_email = '"+ email.getFrom() + "'";
            stm += ", to_email = '" + email.getTo() +"'";
            stm += ", subject = '" + email.getSubject() + "'";
            stm += ", content = '" + email.getContent() + "'";
            stm += "WHERE email_id = " + email.getEmailId() + ";";    
            dbContext.CreateStatement();
            dbContext.SaveChanges(stm);

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(EmailRepository.class.getName());
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
                Logger lgr = Logger.getLogger(EmailRepository.class.getName());
                lgr.log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
    }
    
    public Email GetEmailById(int Emaild){
        Email email = new Email();
            
        try {
            if(dbContext.Connection() == null)
                dbContext.Connect();
            
            String stm = "SELECT * FROM email WHERE email_id = ?";
            dbContext.PreparedStatement(stm);
            dbContext.PreparedStatement().setInt(1, Emaild);                   
            dbContext.ExecutePreparedStatement();
            
            while (dbContext.ResultSet().next()) {
                email.setEmailId(dbContext.ResultSet().getInt(1));
                email.setFrom(dbContext.ResultSet().getString(2));
                email.setTo(dbContext.ResultSet().getString(3));
                email.setSubject(dbContext.ResultSet().getString(4));
                email.setContent(dbContext.ResultSet().getString(5));
            }
            
            return email;

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(EmailRepository.class.getName());
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
                Logger lgr = Logger.getLogger(EmailRepository.class.getName());
                lgr.log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
        
        return email;
    }
    
    public ArrayList<Email> GetEmailByUser(User user, Timestamp date){
        Email email = new Email();
        ArrayList<Email> emails = new ArrayList<Email>();
        
        try {
            if(dbContext.Connection() == null)
                dbContext.Connect();
            
            String stm = "SELECT * FROM email WHERE to_email = ? && createddate > ?";
            dbContext.PreparedStatement(stm);
            dbContext.PreparedStatement().setString(1, user.getEmail());  
            dbContext.PreparedStatement().setTimestamp(2, date);  
            dbContext.ExecutePreparedStatement();
            
            while (dbContext.ResultSet().next()) {
                email = new Email();
                
                email.setEmailId(dbContext.ResultSet().getInt(1));
                email.setFrom(dbContext.ResultSet().getString(2));
                email.setTo(dbContext.ResultSet().getString(3));
                email.setSubject(dbContext.ResultSet().getString(4));
                email.setContent(dbContext.ResultSet().getString(5));
                email.setDate(dbContext.ResultSet().getDate(6));
                email.setLabelFrom(dbContext.ResultSet().getString(7));
                email.setLabelTo(parseToArrayList(dbContext.ResultSet().getString(8)));
                email.setCc(parseToArrayList(dbContext.ResultSet().getString(9)));
                email.setBcc(parseToArrayList(dbContext.ResultSet().getString(10)));
                
                emails.add(email);
            }
            
            return emails;

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(EmailRepository.class.getName());
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
                Logger lgr = Logger.getLogger(EmailRepository.class.getName());
                lgr.log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
        
        return emails;
    }
    
    public void DeleteEmail(Email email){   
        try {
            if(dbContext.Connection() == null)
                dbContext.Connect();
            
            String stm = "DELETE FROM email WHERE email_id = ?";
            dbContext.PreparedStatement(stm);
            dbContext.PreparedStatement().setInt(1, email.getEmailId());                   
            dbContext.SaveChanges();

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(EmailRepository.class.getName());
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
                Logger lgr = Logger.getLogger(EmailRepository.class.getName());
                lgr.log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
    }
    
    public ArrayList<String> parseToArrayList(String data){
        ArrayList<String> array = new ArrayList<String>();
        if(data == null || data.isEmpty())
            return array;
        
        List<String> list = Arrays.asList(data.substring(1, data.length() - 1).split(", ")); 
        array = new ArrayList<String>(list);
        
        return array;
    }
}
