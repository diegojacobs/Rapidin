/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebServer;

import java.util.ArrayList;

/**
 *
 * @author DanielAlejandro
 */
public class RequestParser {
    
    private int fase = 0;
    private String hostname;
    private String source;
    private ArrayList<String> destinos = new ArrayList<>();
    private ArrayList<String> data = new ArrayList<>();
    
    public String parse(String request){
        switch(this.fase){
            // Presentacion
            case 0:
                if(!request.startsWith("HELO ")) return "500 Mal educado!";
                hostname = request.substring(5);
                this.fase++;
                return "250 Que diesel " + hostname;
            // From
            case 1:
                if(!request.startsWith("MAIL FROM ")) return "500 Rapidin no entiende";
                source = request.substring(9);
                this.fase++;
                return "250 Suave";
            // To
            case 2:
                if((!request.startsWith("RCPT TO ")) || (!request.startsWith("DATA"))) return "500 Rapidin no entiende";
                if(request.startsWith("RCPT TO ")){
                    destinos.add(request.substring(7));
                    return "250 Suave";
                }
                this.fase++;
                return "250 Dejala venir. Para terminar data usa el simbolo \".\"";
            // Data
            case 3:
                if(request.equals(".")){
                    this.fase = 1;
                    return "250 Data recibida";
                }
                this.data.add(request);
                return "";
            default: return "500 Rapidin no entiende";
        }
    }
    
    public int getFase(){
        return this.fase;
    }
}
