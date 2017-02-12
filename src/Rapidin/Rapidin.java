/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Rapidin;

import Models.Email;
import Models.User;
import Repositories.EmailRepository;
import Repositories.UserRepository;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Diego Jacobs
 */
public class Rapidin {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Email email = new Email("djacobs@rapidin.com", "carlos.ruiz@rapidin.com", "Eshta", "Eshta");
        try {
            EmailRepository emailRepository = new EmailRepository();
            email = emailRepository.GetEmailById(2);
            email = new Email(email.getEmailId(), "djacobs@rapidin.com", "carlos.ruiz@rapidin.com", "Eshta", "Eshta");
            emailRepository.UpdateEmail(email);
            emailRepository.DeleteEmailById(1);
            email = emailRepository.GetEmailById(2);
            System.out.println(email.toString());
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Rapidin.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
    
}
