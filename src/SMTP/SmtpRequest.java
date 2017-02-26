/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SMTP;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author DanielAlejandro
 */
public class SmtpRequest implements Runnable{
    private RequestParser requestParser = new RequestParser();
    private Socket socket;
    private int size = 256;
    private WorkingQueue wq;
    private int id;

    public SmtpRequest(Socket socket, WorkingQueue wq, int id) {
        this.socket = socket;
        this.wq = wq;
        this.id = id;
    } 

    @Override
    public void run(){
        try{
            String request = "";
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.write("\n220 rapidin.com\n".getBytes());
            while(!request.startsWith("QUIT")){                
                byte[] request_bytes = new byte[size];
                in.read(request_bytes);
                request = new String(request_bytes);                
                String response = requestParser.parse(request);
                out.write(response.getBytes());
            }
            socket.close();
            wq.setFree();
        }
        catch (Exception e){
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}
