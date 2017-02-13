/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebServer;

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
        System.out.println("Yuju");
        try{
            String request = "";
            System.out.println("\n220 rapidin.com\n");
            while(request.compareTo("QUIT")!=0){
                DataInputStream in = new DataInputStream(socket.getInputStream());
                byte[] request_bytes = new byte[size];
                in.read(request_bytes);
                request = new String(request_bytes);
                //System.out.println("\nRequest del cliente: \n" + request + "\n");
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                String response = requestParser.parse(request);
                out.write(response.getBytes());
                //System.out.println("Response del server: \n" + response);
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