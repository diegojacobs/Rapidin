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
    private ArrayList<String> data = new ArrayList<>();
    private ArrayList<Email> emails = new ArrayList<Email>(); 
    
    public RequestParser(){
        _emailRepository = new EmailRepository();
        _userRepository = new UserRepository();
    }
    public String parse(String request){
        if (request.startsWith("QUIT"))
            return "Orale\n";
        System.out.println(request);
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
                User userFrom = _userRepository.GetUserByEmail(source);
                
                if(userFrom.getUserId() == 0){
                    return "421 Not a Rapidin user\n";
                }
                
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
                        return "251 User not local; will forward to " + to.substring(to.indexOf("@")) +"\n";
                    }
                    
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
                    String subject = new String();
                    String from = new String();
                    Boolean isContent = true;
                    this.fase = 1;
                    System.out.println(toString());
                    
                    // Guardar en DB
                    String content = new String();
                    for(String line : this.data){
                        //Hasta el cambio de linea
                        line = line.substring(0, line.indexOf(13));
                        isContent = true;
                        
                        //Si escribio subject lo guardamos
                        if(line.startsWith("SUBJECT:")){
                            subject = line.substring(8);
                            isContent = false;
                        }
                        
                        //Si volivo a escribir from, lo cambiamos
                        if(line.startsWith("FROM:")){
                            if(line.contains("<")&& line.contains(">")){
                                from = line.substring(line.indexOf('<') + 1, line.indexOf('>'));
                                userFrom = _userRepository.GetUserByEmail(from);
                                
                                if(userFrom.getUserId() == 0){
                                    source = userFrom.getEmail();
                                }
                            }
                            
                            isContent = false;
                        }
                        
                        //Si escribe to, miramos si aun no esta en el listado, si no esta lo incluimos
                        if(line.startsWith("TO:")){
                            if(line.contains("<") && line.contains(">")){
                                String to = line.substring(line.indexOf('<') + 1, line.indexOf('>'));
                                User userTo = _userRepository.GetUserByEmail(to);

                                if(userTo.getUserId() == 0 && !userTo.getEmail().equals(null)){
                                    if(!destinos.contains(userTo.getEmail()))
                                        destinos.add(userTo.getEmail());
                                }
                                else{
                                    if(!forward.contains(to))
                                        forward.add(to);
                                }
                            }
                            isContent = false;
                        }
                        
                        if(isContent)
                            content += line + "\n";

                    }
                    
                    for(String to : this.destinos){
                        Email email = new Email(source, to, subject, content);
                        emails.add(email);
                        _emailRepository.AddEmail(email);
                    }
                    
                    this.destinos = new ArrayList<>();
                    this.data = new ArrayList<>();
                    return "250 Data recibida\n";
                }
                this.data.add(request);
                return "";
            default: 
                return "500 Rapidin no entiende\n";
        }
    }

    @Override
    public String toString() {
        return "Hostname: " + hostname + "\nSource:" + source + "\nDestinos:\n" + destinos.toString() + "\nData:\n" + data.toString();
    }
}
