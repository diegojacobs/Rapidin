/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SMTP;

import Models.Email;
import Models.User;
import Repositories.EmailRepository;
import Repositories.UserRepository;
import java.sql.SQLException;
import java.text.Normalizer;
import java.util.ArrayList;

/**
 *
 * @author DanielAlejandro
 */
public class RequestParser {
    private UserRepository _userRepository;
    private EmailRepository _emailRepository;
    private int fase = 0;
    private String hostname;
    private String source;
    
    private ArrayList<String> destinos = new ArrayList<>();
    private ArrayList<String> forward = new ArrayList<>();
    private ArrayList<String> local = new ArrayList<>();

    private String data = new String();
    private ArrayList<Email> emails = new ArrayList<Email>(); 
    
    public RequestParser(){
        _emailRepository = new EmailRepository();
        _userRepository = new UserRepository();
    }
    public String parse(String request){
        if (request.startsWith("QUIT"))
            return "Orale\n";
        switch(this.fase){
            // Presentacion
            case 0:
                if(!request.startsWith("HELO")) 
                    return "500 Mal educado!\n";
                
                hostname = request.substring(5);
                this.fase++;
                return "250 Que diesel " + hostname;
            // From
            case 1:
                if(!request.startsWith("MAIL FROM:") || !request.contains("<") || !request.contains(">")) 
                    return "500 Rapidin no entiende\n";
                
                source = request.substring(request.indexOf('<') + 1, request.indexOf('>'));
                
                this.fase++;
                return "250 Suave\n";
            // To
            case 2:
                if((!request.startsWith("RCPT TO:")) && (!request.startsWith("DATA"))) 
                    return "500 Rapidin no entiende\n";
                
                if(request.startsWith("RCPT TO:") && request.contains("<") && request.contains(">")){
                    String to = request.substring(request.indexOf('<') + 1, request.indexOf('>'));
                    User userTo = _userRepository.GetUserByEmail(to);
                    
                    if (userTo.getUserId() == 0){
                        forward.add(to);
                        destinos.add(to);
                        return "251 User not local; will forward to " + to.substring(to.indexOf("@")+1) +"\n";
                    }
                    local.add(userTo.getEmail());
                    destinos.add(userTo.getEmail());
                    return "250 Suave\n";
                }
                
                if(destinos.isEmpty() && forward.isEmpty()){
                    return "500 Rapidin no entiende\n";
                }
                this.fase++;
                return "354 Dejala venir. Para terminar data usa el simbolo \".\"\n";
            // Data
            case 3:
                if(request.startsWith(".")){
                    for(String to : this.local){
                        Character c = 0;
                        data = data.replaceAll(c.toString(), "");
                        c = 13;
                        data = data.replaceAll(c.toString(), "");
                        Email email = new Email(this.source, to, data);
                        _emailRepository.AddEmail(email);
                    }

                    for(String to : this.forward){
                        Email email = new Email(this.source, to, this.data);
                        emails.add(email);
                    }

                    this.destinos = new ArrayList<>();
                    this.local = new ArrayList<String>();
                    this.forward = new ArrayList<String>();
                    this.data = new String();
                    return "250 Data recibida\n";
                }
                
                this.data += request;
                return "ok";
            default: 
                return "500 Rapidin no entiende\n";
        }
    }

    @Override
    public String toString() {
        return "RequestParser{" + "hostname=" + hostname + ", source=" + source + ", destinos=" + destinos + ", forward=" + forward + ", local=" + local + ", data=" + data + '}';
    }
}
