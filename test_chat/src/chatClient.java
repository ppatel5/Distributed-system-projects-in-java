package src;

import java.net.*;
import java.io.*;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class chatClient implements Runnable {

	Socket sckt;
	Scanner ip;
	Scanner SEND = new Scanner(System.in);
	PrintWriter op;

	public chatClient(Socket X) {
		this.sckt = X;
	}

	public void run() {
		try {
			try {
				ip = new Scanner(sckt.getInputStream());
				op = new PrintWriter(sckt.getOutputStream());
				op.flush();
				CheckStream();
			} finally {
				sckt.close();
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void discon() throws IOException {
		op.println(clientChatWindow.UserName + " disconnected.");
		op.flush();
		sckt.close();
		JOptionPane.showMessageDialog(null, "you are no more connected");
		System.exit(0);
	}

	public void CheckStream() {
		while (true) {
			rcv();
		}
	}

	public void rcv() {
		if (ip.hasNext()) {
			String msg = ip.nextLine();

			if (msg.contains("#?!")) {
				String temp = msg.substring(3);
				temp = temp.replace("[", "");
				temp = temp.replace("]", "");

				String[] CurrentUsers = temp.split(", ");

				clientChatWindow.listonline.setListData(CurrentUsers);
				clientChatWindow.listonlineforGroup.setListData(CurrentUsers);
			} else {
				if(msg.startsWith("#")){
					clientChatWindow.personalChatMsg(msg);
				}
				else if(msg.startsWith("G")){
					clientChatWindow.GroupChatMsg(msg);
				}
				clientChatWindow.txtareaconv.append(msg + "\n");
			}
		}
	}

	public void send(String x) {
		if(x.startsWith("#")){
		op.println("#"+clientChatWindow.UserName+x);}
		else if(x.startsWith("G")){
		op.println("G"+clientChatWindow.UserName+x);}
		else{
			op.println(clientChatWindow.UserName+x);}
		op.flush();
		clientChatWindow.textfieldMessage.setText("");
	}

}
