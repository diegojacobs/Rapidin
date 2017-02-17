/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Rapidin;

import Models.Attachment;
import Models.Email;
import Models.User;
import Repositories.AttachmentRepository;
import Repositories.EmailRepository;
import Repositories.UserRepository;
import WebServer.WebServer;
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
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
       UserRepository userRepo = new UserRepository();
       User user = userRepo.GetUserById(1);
       System.out.println(user.toString());
        //WebServer rapidin_server = new WebServer();
        //rapidin_server.run();
    }
    
}
