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
    
    private ArrayList<String> to = new ArrayList<>();
    private ArrayList<String> cc = new ArrayList<>();
    private ArrayList<String> bcc = new ArrayList<>();
    
    private ArrayList<String> data = new ArrayList<>();
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
                                source = from;
                            }
                            
                            isContent = false;
                        }
                        
                        //Si escribe to, miramos si aun no esta en el listado, si no esta lo incluimos
                        if(line.startsWith("TO:")){
                            if(line.contains("<") && line.contains(">")){
                                String to = line.substring(line.indexOf('<') + 1, line.indexOf('>'));
                                User userTo = _userRepository.GetUserByEmail(to);

                                if(userTo.getUserId() != 0 && !userTo.getEmail().equals(null)){
                                    if(!destinos.contains(userTo.getEmail()))
                                        destinos.add(userTo.getEmail());
                                    
                                    if(!this.to.contains(to))
                                        this.to.add(userTo.getEmail());
                                }
                                else{
                                    if(!forward.contains(to))
                                        forward.add(to);
                                    
                                    if(!this.to.contains(to))
                                        this.to.add(to);
                                }
                            }
                            isContent = false;
                        }
                        
                        //Si escribe cc, miramos si aun no esta en el listado, si no esta lo incluimos
                        if(line.startsWith("CC:")){
                            if(line.contains("<") && line.contains(">")){
                                String cc = line.substring(line.indexOf('<') + 1, line.indexOf('>'));
                                User userCc = _userRepository.GetUserByEmail(cc);

                                if(userCc.getUserId() != 0 && !userCc.getEmail().equals(null)){
                                    if(!destinos.contains(userCc.getEmail()))
                                        destinos.add(userCc.getEmail());
                                    
                                    if(!this.cc.contains(cc))
                                        this.cc.add(userCc.getEmail());
                                }
                                else{
                                    if(!forward.contains(cc))
                                        forward.add(cc);
                                    
                                    if(!this.cc.contains(cc))
                                        this.cc.add(userCc.getEmail());
                                }
                            }
                            isContent = false;
                        }
                        
                        //Si escribe bcc, miramos si aun no esta en el listado, si no esta lo incluimos
                        if(line.startsWith("BCC:")){
                            if(line.contains("<") && line.contains(">")){
                                String bcc = line.substring(line.indexOf('<') + 1, line.indexOf('>'));
                                User userBcc = _userRepository.GetUserByEmail(bcc);

                                if(userBcc.getUserId() != 0 && !userBcc.getEmail().equals(null)){
                                    if(!destinos.contains(userBcc.getEmail()))
                                        destinos.add(userBcc.getEmail());
                                    
                                    if(!this.bcc.contains(bcc))
                                        this.bcc.add(userBcc.getEmail());
                                }
                                else{
                                    if(!forward.contains(bcc))
                                        forward.add(bcc);
                                    
                                    if(!this.bcc.contains(bcc))
                                        this.bcc.add(bcc);
                                }
                            }
                            isContent = false;
                        }
                        
                        if(isContent)
                            content += line + "\n";

                    }
                    
                    for(String to : this.destinos){
                        Email email = new Email(this.source, to, this.source, this.to, this.cc, this.bcc, subject, content);
                        _emailRepository.AddEmail(email);
                    }
                    
                    for(String to : this.forward){
                        Email email = new Email(this.source, to, this.source, this.to, this.cc, this.bcc, subject, content);
                        emails.add(email);
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
        return "RequestParser{" + "hostname=" + hostname + ", source=" + source + ", destinos=" + destinos + ", forward=" + forward + ", to=" + to + ", cc=" + cc + ", bcc=" + bcc + ", data=" + data + ", emails=" + emails + '}';
    }

    
}
