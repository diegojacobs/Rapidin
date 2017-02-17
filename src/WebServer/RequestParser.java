/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebServer;

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
                if(!request.startsWith("HELO ")) return "500 Mal educado!\n";
                hostname = request.substring(5);
                this.fase++;
                return "250 Que diesel " + hostname;
            // From
            case 1:
                if(!request.startsWith("MAIL FROM ")) return "500 Rapidin no entiende\n";
                source = request.substring(9);
                User userFrom = _userRepository.GetUserByEmail(source);
                if(userFrom == null){
                    return "421 Not a Rapidin user\n";
                }
                
                this.fase++;
                return "250 Suave\n";
            // To
            case 2:
                if((!request.startsWith("RCPT TO ")) && (!request.startsWith("DATA"))) return "500 Rapidin no entiende\n";
                if(request.startsWith("RCPT TO ")){
                    String to = request.substring(8);
                    destinos.add(to);
                    User userTo = _userRepository.GetUserByEmail(to);
                    if (userTo == null){
                        return "251 User not local; will forward to <forward-path>";
                    }
                    return "250 Suave\n";
                }
                
                if(destinos.isEmpty()){
                    return "500 Rapidin no entiende\n";
                }
                this.fase++;
                return "354 Dejala venir. Para terminar data usa el simbolo \".\"\n";
            // Data
            case 3:
                if(request.startsWith(".")){
                    this.fase = 1;
                    System.out.println(toString());
                    // Guardar en DB
                    String content = new String();
                    for(String line : this.data.subList(1, this.data.size())){
                        content += line + "\n";
                    }
                    for(String to : this.destinos){
                        emails.add(new Email(source, to, this.data.get(0), content));
                    }
                    
                    this.destinos = new ArrayList<>();
                    this.data = new ArrayList<>();
                    return "250 Data recibida\n";
                }
                this.data.add(request);
                return "";
            default: return "500 Rapidin no entiende\n";
        }
    }

    @Override
    public String toString() {
        return "Hostname: " + hostname + "\nSource:" + source + "\nDestinos:\n" + destinos.toString() + "\nData:\n" + data.toString();
    }
    
    
}
