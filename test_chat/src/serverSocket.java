package src;

import java.io.*;
import java.net.*;

public class serverSocket {

	public static void main(String[] args) throws Exception {
		serverSocket srvr = new serverSocket();
		srvr.run();
	}

	public void run() throws Exception {
		ServerSocket srvrSocket = new ServerSocket(1201);
		Socket sckt = srvrSocket.accept();
		InputStreamReader ir = new InputStreamReader(sckt.getInputStream());
		BufferedReader br = new BufferedReader(ir);

		String msg = br.readLine();
		System.out.println(msg);

		if (msg != null) {
			PrintStream P = new PrintStream(sckt.getOutputStream());
			P.println("message recieved");
		}
	}
}
