/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import SMTP.WorkingQueue;
import java.io.BufferedReader;
import java.io.FileReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Diego Jacobs
 */
public class WebServer implements Runnable{
    private int port = 8000;
    private String configFile = "config.txt";
    private int maxThreads = 0;
    
    public WebServer(){
        readConfigFile();
    }
    
    private void readConfigFile(){
        String base_path = System.getProperty("user.dir");
        String path = base_path + "/src/Server/" + configFile;
        HashMap<String, Integer> configs = new HashMap<String, Integer>();
        try(BufferedReader br = new BufferedReader(new FileReader(path))){
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                int separator = line.indexOf("=");
                if (separator != -1){
                    String key = line.substring(0,separator);
                    Integer value = Integer.parseInt(line.substring(separator+1, line.length()));
                    configs.put(key, value);
                }
                line = br.readLine();
            }            
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        maxThreads = configs.get("MaxThreads");        
        System.out.println("Server MaxThreads: " + String.valueOf(maxThreads));
    }
    
    @Override
    public void run(){
        System.out.println("--- RAPIDIN SERVER CONNECTION OPEN ---");        
        WorkingQueue wq;
        try {
            wq = new WorkingQueue(port, maxThreads);
            int id = 1;
        while (true){
            Socket socket = wq.getSocket();
            WebRequest request = new WebRequest(socket, wq, id);
            Thread thread = new Thread(request);
            thread.start();
            id++;
        }
        } catch (Exception ex) {
            Logger.getLogger(WebServer.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("--- RAPIDIN SERVER CONNECTION ERROR ---"); 
        }
        System.out.println("--- RAPIDIN SERVER CONNECTION CLOSE ---"); 
    }
}
