package src;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class chatServerRet implements Runnable {

	Socket sckt;
	private Scanner ip;
	private PrintWriter op;
	String msg = "";

	public chatServerRet(Socket soc) {
		this.sckt = soc;
	}

	public void CheckConnection() throws IOException {
		if (!sckt.isConnected()) {
			for (int i = 1; i <= chatServer.ConnArray.size(); i++) {
				if (chatServer.ConnArray.get(i) == sckt) {
					chatServer.ConnArray.remove(i);
				}
			}
			for (int i = 1; i <= chatServer.ConnArray.size(); i++) {
				Socket soc1 = (Socket) chatServer.ConnArray.get(i - 1);
				PrintWriter pout = new PrintWriter(soc1.getOutputStream());
				pout.println(soc1.getLocalAddress().getHostName()
						+ " disconnected!");
			}

		}
	}

	public void run() {
		try {
			try {
				ip = new Scanner(sckt.getInputStream());
				op = new PrintWriter(sckt.getOutputStream());

				while (true) {
					CheckConnection();

					if (!ip.hasNext()) {
						return;
					}

					msg = ip.nextLine();

					System.out.println("Client : " + msg);
					//String input = msg.substring(msg.indexOf("#")+1, msg.lastIndexOf("#"));
					//System.out.println("Input is "+input);
					if(msg.startsWith("#")){
						System.out.println("in if part");
						String string = msg;
						String[] parts = string.split("#");
						String sender = parts[1]; // 004
						String receiver = parts[2]; // 034556
						String message = parts[3];

						
						for(int i=1; i<=chatServer.userSocketList.size();i++){
							userSocket usoc1 = chatServer.userSocketList.get(i-1);
							if(usoc1.getUser().equalsIgnoreCase(receiver)){
								Socket soc1 = usoc1.getSocket();
								PrintWriter pout = new PrintWriter(
										soc1.getOutputStream());
								pout.println(msg);
								pout.flush();
								System.out.println("Sent to: "
										+ usoc1.getUser() + "::"+usoc1.getSocket());
							}
							
							
						}
					}
					else if(msg.startsWith("G")){
						System.out.println("in if part of group");
						String string = msg;
						String[] parts = string.split("G");
						String sender = parts[1]; // 004
						String receiver = parts[2]; // 034556
						String message = parts[3];
						System.out.println("Sender is" +sender);
						System.out.println("Receiver are "+receiver);
						System.out.println("msg is"+message);
						String[] users = receiver.split(",");
						for(int k=0; k<users.length;k++){
							System.out.println("::::::::::::"+users[k]);
						}
						for (int k = 0; k < users.length; k++) {
							System.out.println("in for looop where user is"
									+ users[k]);
							for (int i = 1; i <= chatServer.userSocketList
									.size(); i++) {
								userSocket usoc1 = chatServer.userSocketList
										.get(i - 1);
								if (usoc1.getUser().equalsIgnoreCase(users[k])) {
									Socket soc1 = usoc1.getSocket();
									PrintWriter pout = new PrintWriter(
											soc1.getOutputStream());
									pout.println(msg);
									pout.flush();
									System.out.println("Sent to: "
											+ usoc1.getUser() + "::"
											+ usoc1.getSocket());
								}

							}
						}
						
						
					}
					
					
					else{

					for (int i = 1; i <= chatServer.ConnArray.size(); i++) {
						Socket soc1 = (Socket) chatServer.ConnArray
								.get(i - 1);
						PrintWriter pout = new PrintWriter(
								soc1.getOutputStream());
						pout.println(msg);
						pout.flush();
						System.out.println("Sent to: "
								+ soc1.getLocalAddress().getHostName());
					}}
				}
			} finally {
				sckt.close();
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
