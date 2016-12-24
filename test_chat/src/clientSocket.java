package src;

import java.io.*;
import java.net.*;
 
public class clientSocket
{
 
        public static void main(String[] args) throws Exception
        {
        	clientSocket client = new clientSocket();
        	client.run();
        }
       
        public void run() throws Exception
        {
            Socket sckt = new Socket("localhost",1201);
            PrintStream P = new PrintStream(sckt.getOutputStream());
            P.println("from client to server message");
           
            InputStreamReader ir = new InputStreamReader(sckt.getInputStream());
            BufferedReader br = new BufferedReader(ir);
           
            String msg = br.readLine();
            System.out.println(msg);
        }
    }
