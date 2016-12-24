package src;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class chatServer {
	public static ArrayList<Socket> ConnArray = new ArrayList<Socket>();
	public static ArrayList<String> currUser = new ArrayList<String>();
	public static ArrayList<userSocket> userSocketList = new ArrayList<userSocket>();

	public static void main(String[] args) throws IOException {

		try {
			final int PORT = 1201;
			ServerSocket SERVER = new ServerSocket(PORT);
			System.out.println("Client to be initiated");

			while (true) {
				Socket SOCK = SERVER.accept();
				ConnArray.add(SOCK);

				System.out.println("Client address: "
						+ SOCK.getLocalAddress().getHostName());

				String uname = AddUserName(SOCK);
				userSocket usocket = new userSocket();
				usocket.setSocket(SOCK);
				usocket.setUser(uname);
				userSocketList.add(usocket);

				chatServerRet CHAT = new chatServerRet(SOCK);
				Thread X = new Thread(CHAT);
				X.start();

			}
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public static String AddUserName(Socket X) throws IOException {
		Scanner INPUT = new Scanner(X.getInputStream());
		String UserName = INPUT.nextLine();
		currUser.add(UserName);

		for (int i = 1; i <= chatServer.ConnArray.size(); i++) {
			Socket TEMP_SOCK = (Socket) chatServer.ConnArray.get(i - 1);
			PrintWriter OUT = new PrintWriter(TEMP_SOCK.getOutputStream());
			OUT.println("#?!" + currUser);
			OUT.flush();
		}
		return UserName;
	}

}
