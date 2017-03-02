/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SMTP;

import Rapidin.Rapidin;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

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
            System.out.println("SOCKET INITIATE");
            String request = "";
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.writeInt("\n220 rapidin.com\n".length());
            out.write("\n220 rapidin.com\n".getBytes());
            while(!request.startsWith("QUIT")){          
                System.out.println("Esperando escritura");
                int length = in.readInt();
                byte[] request_bytes = new byte[length];
                in.read(request_bytes);
                request = new String(request_bytes, "UTF-8");                
                String user = this.socket.getInetAddress().getHostName() + ":" + this.socket.getPort();
                System.out.println(user +": " + request);
                String response = requestParser.parse(request);
                String server = this.socket.getLocalAddress().getHostName() + ":" + this.socket.getPort();
                System.out.println(server +": " + response);
                out.writeInt(response.length());
                out.write(response.getBytes());
                out.flush();
            }
            System.out.println("SOCKET CLOSE");
            socket.close();
            wq.setFree();
        }
        catch (Exception e){
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}
