/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Rapidin;

import Models.User;
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
        User user = new User();
        try {
            UserRepository userRepository = new UserRepository();
            user = userRepository.GetUserById(1);
            user.setFirstName("Diego");
            user.setLastName("Jacobs");
            user.setPassword("1234");
            userRepository.UpdateUser(user);
            user = userRepository.GetUserById(1);
            System.out.println(user.toString());
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Rapidin.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
    
}
