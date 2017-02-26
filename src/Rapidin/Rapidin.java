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
import SMTP.SMTP;
import Server.WebServer;
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
       SMTP rapidin_server = new SMTP();
       WebServer rapidin_web_server = new WebServer();
       
       Thread thread = new Thread(rapidin_web_server);
       Thread thread2 = new Thread(rapidin_server);
       
       thread.start();
       thread2.start();
       
        try {
            Thread.sleep(20 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
}
