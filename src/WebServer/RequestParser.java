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
                this.fase++;
                return "250 Suave\n";
            // To
            case 2:
                if((!request.startsWith("RCPT TO ")) && (!request.startsWith("DATA"))) return "500 Rapidin no entiende\n";
                if(request.startsWith("RCPT TO ")){
                    destinos.add(request.substring(8));
                    return "250 Suave\n";
                }
                this.fase++;
                return "250 Dejala venir. Para terminar data usa el simbolo \".\"\n";
            // Data
            case 3:
                if(request.startsWith(".")){
                    this.fase = 1;
                    System.out.println(toString());
                    // Guardar en DB
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
