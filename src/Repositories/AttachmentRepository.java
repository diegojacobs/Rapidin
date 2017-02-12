/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repositories;

import Models.Attachment;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Diego Jacobs
 */
public class AttachmentRepository {
    private final DBConnector dbContext;

    public AttachmentRepository() throws SQLException {
        dbContext = new DBConnector();
    }
    
    public void AddAttachment(Attachment attachment){
        try {
            if(dbContext.Connection() == null)
                dbContext.Connect();
            
            String stm = "INSERT INTO attachment (email_id, filepath) VALUES(?, ?)";
            dbContext.PreparedStatement(stm);
            dbContext.PreparedStatement().setInt(1, attachment.getEmailId());
            dbContext.PreparedStatement().setString(2, attachment.getFilePath());                   
            dbContext.SaveChanges();

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(AttachmentRepository.class.getName());
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
                Logger lgr = Logger.getLogger(AttachmentRepository.class.getName());
                lgr.log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
    }
    
    public void UpdateAttachment(Attachment attachment){
        try {
            if(dbContext.Connection() == null)
                dbContext.Connect();
            
            String stm = "UPDATE attachment SET ";
            stm += "email_id = '"+ attachment.getEmailId() + "'";
            stm += ", filepath = '" + attachment.getFilePath() +"'";
            stm += "WHERE attachment_id = " + attachment.getAttachmentId() + ";";    
            dbContext.CreateStatement();
            dbContext.SaveChanges(stm);

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(AttachmentRepository.class.getName());
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
                Logger lgr = Logger.getLogger(AttachmentRepository.class.getName());
                lgr.log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
    }
    
    public Attachment GetAttachmentById(int AttachmentId){
        Attachment attachemnt = new Attachment();
            
        try {
            if(dbContext.Connection() == null)
                dbContext.Connect();
            
            String stm = "SELECT * FROM attachment WHERE attachment_id = ?";
            dbContext.PreparedStatement(stm);
            dbContext.PreparedStatement().setInt(1, AttachmentId);                   
            dbContext.ExecutePreparedStatement();
            
            while (dbContext.ResultSet().next()) {
                attachemnt.setAttachmentId(dbContext.ResultSet().getInt(1));
                attachemnt.setEmailId(dbContext.ResultSet().getInt(2));
                attachemnt.setFilePath(dbContext.ResultSet().getString(3));
            }
            
            return attachemnt;

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(AttachmentRepository.class.getName());
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
                Logger lgr = Logger.getLogger(AttachmentRepository.class.getName());
                lgr.log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
        
        return attachemnt;
    }
    
    public void DeleteAttachment(Attachment attachment){   
        try {
            if(dbContext.Connection() == null)
                dbContext.Connect();
            
            String stm = "DELETE FROM attachment WHERE attachment_id = ?";
            dbContext.PreparedStatement(stm);
            dbContext.PreparedStatement().setInt(1, attachment.getAttachmentId());                   
            dbContext.SaveChanges();

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(AttachmentRepository.class.getName());
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
                Logger lgr = Logger.getLogger(AttachmentRepository.class.getName());
                lgr.log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
    }
    
    public ArrayList<Attachment> GetAttachmentByEmailId(int EmailId){
        ArrayList<Attachment> attachments = new ArrayList<Attachment>();
        Attachment attachemnt = new Attachment();
            
        try {
            if(dbContext.Connection() == null)
                dbContext.Connect();
            
            String stm = "SELECT * FROM attachment WHERE email_id = ?";
            dbContext.PreparedStatement(stm);
            dbContext.PreparedStatement().setInt(1, EmailId);                   
            dbContext.ExecutePreparedStatement();
            
            while (dbContext.ResultSet().next()) {
                attachemnt.setAttachmentId(dbContext.ResultSet().getInt(1));
                attachemnt.setEmailId(dbContext.ResultSet().getInt(2));
                attachemnt.setFilePath(dbContext.ResultSet().getString(3));
                
                attachments.add(attachemnt);
            }
            
            return attachments;

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(AttachmentRepository.class.getName());
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
                Logger lgr = Logger.getLogger(AttachmentRepository.class.getName());
                lgr.log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
        
        return attachments;
    }
}
