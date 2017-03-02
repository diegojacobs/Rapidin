/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Models.Email;
import Models.User;
import Repositories.EmailRepository;
import Repositories.UserRepository;
import com.google.gson.Gson;
import java.sql.Timestamp;
import java.util.ArrayList;
        
/**
 *
 * @author Diego Jacobs
 */
public class RequestParser {
    private UserRepository _userRepository;
    private EmailRepository _emailRepository;
    private User user;
    private Email email;
    private int fase = 0;
    private String password;
    private Timestamp date;
    private String source;
    private Gson gson = new Gson();
    
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
                
                this.fase++;
                return "200 Que diesel\n";
            // From
            case 1:
                if(!request.startsWith("USER:") || !request.contains("<") || !request.contains(">")) 
                    return "500 Rapidin no entiende\n";
                
                source = request.substring(request.indexOf('<') + 1, request.indexOf('>'));
                user = _userRepository.GetUserByEmail(source);

                
                this.fase++;
                return "200 Suave\n";
            // To
            case 2:
                if((!request.startsWith("PASSWORD:")) && (!request.startsWith("DATE:"))) 
                    return "500 Rapidin no entiende\n";
                
                if(request.startsWith("PASSWORD:") && request.contains("<") && request.contains(">")){
                    password = request.substring(request.indexOf('<') + 1, request.indexOf('>'));      
                    this.fase++;
                    return "200 Suave\n";
                }
                
                if(request.startsWith("DATE:") && request.contains("<") && request.contains(">")){
                    date = Timestamp.valueOf(request.substring(request.indexOf('<') + 1, request.indexOf('>')));  
                    this.fase++;
                    return "200 Suave\n";
                }
            // Action
            case 3:
                if(request.startsWith("VALIDATE")){
                    if(user.getUserId() == 0)
                        return "404 No es Rapidin\n";
                    
                    if(!user.getPassword().equals(password))
                        return "501 Password Invalido\n";
                    
                    this.fase = 0;
                    return "200 Rapidin user\n";
                }
                
                if(request.startsWith("GET")){
                    if(user.getUserId() != 0 && !user.getEmail().equals(null)){
                        ArrayList<Email> list = _emailRepository.GetEmailByUser(user, date);
                        
                        if(list.isEmpty())
                            return "202 No hay emails\n";
                        Email[] Emails = new Email[list.size()];
                            for(int i=0; i<list.size(); i++){
                                Emails[i] = list.get(i);
                            }; 
                            
                        String json = gson.toJson(Emails);
                        
                        this.fase = 0;
                        return "200 " + json + "\n";
                    }
                    this.fase = 1;
                    return "404 No pertenece a Rapidin\n";
                }
                
                return "500 Rapidin no entiende\n";
            default: 
                return "500 Rapidin no entiende\n";
        }
    }
}
