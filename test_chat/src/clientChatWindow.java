package src;

import javax.swing.*;


import java.awt.event.*;
import java.io.PrintWriter;
import java.net.*;
import java.util.ArrayList;

public class clientChatWindow {

	private static chatClient ChatClient;
	public static String UserName = "UTArlington";
	public static String Password = "";

	public static JFrame mainWindow = new JFrame();
	private static JButton btnconnect = new JButton();
	private static JButton btndisconnect = new JButton();
	private static JLabel lblconv = new JLabel();
	public static JTextArea txtareaconv = new JTextArea();
	private static JScrollPane scrlpanconv = new JScrollPane();
	public static JTextArea txtareaconv2 = new JTextArea();
	private static JScrollPane scrlpanconv2 = new JScrollPane();
	private static JButton btnsend = new JButton();
	private static JLabel lblmsg = new JLabel("Your Message: ");
	public static JTextField textfieldMessage = new JTextField(30);
	private static JLabel lblonline = new JLabel();
	public static JList listonline = new JList();
	public static JList listonlineforGroup = new JList();
	//public static JList<String> selectedUser = new JList<String>();
	public static DefaultListModel<String> listModel = new DefaultListModel<>();
	public static JList<String> selectedUser = new JList<>(listModel);
	private static JScrollPane scrlpaneonline = new JScrollPane();
	private static JLabel lblloginas = new JLabel();
	private static JLabel lblloginasbx = new JLabel();
	private static JButton signUpButton = new JButton();
	

	public static JFrame loginWin = new JFrame();
	public static JTextField tfielduname = new JTextField(15);
	//public static JTextField tfieldpwd = new JTextField(15);
	public static JPasswordField tfieldpwd = new JPasswordField(15);
	private static JLabel lbluserpaswd = new JLabel("Password ");
	private static JButton btnenter = new JButton("Enter Chatroom");
	private static JLabel lblusername = new JLabel("Username ");
	private static JPanel pnllogin = new JPanel();
	private static ArrayList<UserChatWindows> usersChatWindowList = new ArrayList<UserChatWindows>();
	private static ArrayList<UserGroupWindows> usersGroupWindowList = new ArrayList<UserGroupWindows>();


	public static void main(String[] args) {
		mainWindowGUI();
		init();
	}

	public static boolean validateUser() {

		sqlconn dbObject = new sqlconn();
		if (dbObject.checkLoginDetails(UserName, Password)) {
			return true;
		}
		return false;
	}

	public static void connectClient() {
		try {
			final int PORT = 1201;
			final String HOST = "localhost";
			Socket SOCK = new Socket(HOST, PORT);
			System.out.println("You connected to: " + HOST);

			ChatClient = new chatClient(SOCK);

			PrintWriter OUT = new PrintWriter(SOCK.getOutputStream());
			OUT.println(UserName);
			OUT.flush();

			Thread X = new Thread(ChatClient);
			X.start();

		} catch (Exception e) {
			System.out.print(e);
			JOptionPane.showMessageDialog(null, "No response from Server.");
			System.exit(0);
		}
	}

	public static void init() {
		btndisconnect.setEnabled(false);
		btnconnect.setEnabled(true);
	}

	public static void loginGUI() {
		
		loginWin.setTitle("What's your name?");
		loginWin.setSize(300, 150);
		loginWin.setLocation(150, 200);
		loginWin.setResizable(false);
		pnllogin = new JPanel();
		pnllogin.add(lblusername);
		lblusername.setBounds(300, 550, 300, 66);
		pnllogin.add(tfielduname);
		tfielduname.setBounds(350, 550, 300, 66);
		pnllogin.add(lbluserpaswd);
		lbluserpaswd.setBounds(300, 750, 260, 66);
		pnllogin.add(tfieldpwd);
		tfielduname.setBounds(350, 750, 260, 66);
		pnllogin.add(btnenter);
		btnenter.setBounds(400, 475, 260, 66);
		loginWin.getContentPane().add(pnllogin);
		loginWin.setVisible(true);
		actLogin();
		loginWin.addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent e) {
		    	System.out.println("in listener");
		    	pnllogin.setVisible(false);
		    }
		});
	}
	
	public static void personalChatMsg(String msg){
		String part1=null,part2=null,part3=null;
		if(msg.startsWith("#")){
			
				System.out.println("message is: "+msg);
				
				String[] parts = msg.split("#");
				part1 = parts[1]; 
				part2 = parts[2]; 
				part3 = parts[3];
				int flag =0;
				for(int i=0; i<usersChatWindowList.size();i++){
					UserChatWindows ucw = usersChatWindowList.get(i);
					if(ucw.getUserName().equalsIgnoreCase(part1)){
						System.out.println("in if of personal chat");
						ucw.getTxtarea().append(part1+" :"+part3+"\n\r");
						flag=1;
					}
					
				}
				if(flag==0){
					System.out.println("call create chat room now");
					createChatRoom(msg);
				}
		}
	}
	
	
	public static void GroupChatMsg(String msg){

		String part1=null,part2=null,part3=null;
		if(msg.startsWith("G")){
			
				System.out.println("message is: "+msg);
				
				String[] parts = msg.split("G");
				part1 = parts[1]; 
				part2 = parts[2]; 
				part3 = parts[3];
				int flag =0;
				for(int i=0; i<usersGroupWindowList.size();i++){
					UserGroupWindows ugw = usersGroupWindowList.get(i);
					if(ugw.getUserName().equalsIgnoreCase(part2)){
						System.out.println("in if of group chat");
						ugw.getTxtarea().append(part1+" :"+part3+"\n\r");
						flag=1;
					}
					
				}
				if(flag==0){
					System.out.println("call create group now");
					createGroupPopUp(msg);
				}
		}
	
	}
	
	public static void createChatRoom(String username)
	{
		System.out.println("in create chat room function"+username);
		String part1=null,part2=null,part3=null;
		if(username.startsWith("#")){
			String[] parts = username.split("#");
			part1 = parts[1]; 
			part2 = parts[2]; 
			part3 = parts[3];
		}
		
	
		final String uName;
		if(part1!=null){
			uName = part1;
		}else{
		uName = username;}
		
		JScrollPane scrlpanconv1 = new JScrollPane();
		
		JFrame chatwindow = new JFrame();
		UserChatWindows ucw = new UserChatWindows();
		chatwindow.setTitle(uName);
		ucw.setUserName(uName);
		chatwindow.setSize(450, 400);
		chatwindow.setLocation(150, 200);
		chatwindow.setResizable(true);
		
		
		
		System.out.println("username is " + uName);
		JPanel pnlChatBX = new JPanel();

		final JTextField textfieldMessage1 = new JTextField(20);
		final JTextArea txtareaconv1 = new JTextArea();
		JButton btnsend1 = new JButton();

		pnlChatBX.add(txtareaconv1);
		if(part3!=null){
			txtareaconv1.setText(part1+" :"+part3+"\n\r");
		}else{
		txtareaconv1.setText("");}
		// txtareaconv.setBounds(580, 500, 300, 60);
		txtareaconv1.setName("txtareaconv_" + uName);
		ucw.setTxtarea(txtareaconv1);

		txtareaconv1.setColumns(35);
		txtareaconv1.setLineWrap(true);
		txtareaconv1.setRows(10);
		txtareaconv1.setEditable(false);

		scrlpanconv1
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrlpanconv1
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrlpanconv1.setViewportView(txtareaconv1);
		chatwindow.getContentPane().add(scrlpanconv1);
		scrlpanconv1.setBounds(20, 60, 330, 180);

		lblmsg.setText("Type here:");
		chatwindow.getContentPane().add(lblmsg);
		lblmsg.setBounds(10, 268, 60, 20);

		// textfieldMessage.setBounds(80, 295, 260, 30);

		textfieldMessage1.requestFocus();
		chatwindow.getContentPane().add(textfieldMessage1);
		textfieldMessage1.setBounds(80, 268, 260, 30);
		ucw.setTxtbox(textfieldMessage1);

		// pnlChatBX.add(btnsend);
		// btnsend.setName("btnsend"+username);
		// btnsend.setText("Send message");
		// btnsend.setBounds(700, 450, 120, 25);

		btnsend1.setText("Send");
		chatwindow.getContentPane().add(btnsend1);
		btnsend1.setBounds(360, 268, 70, 25);
		btnsend1.addActionListener(new java.awt.event.ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("clicked on send button in chat window");
				txtareaconv1.append(textfieldMessage1.getText());
				
				if (!textfieldMessage1.getText().equals("")) {
				actbtnSendChatWindow(uName,textfieldMessage1.getText() );
			}
				textfieldMessage1.setText("");}
		});
		

		chatwindow.getContentPane().add(pnlChatBX);
		chatwindow.setVisible(true);
		usersChatWindowList.add(ucw);

	}
	
	public static void createGroupPopUp(String username){

		System.out.println("in create group function"+username);
		String part1=null,part2=null,part3=null;
		if(username.startsWith("G")){
			String[] parts = username.split("G");
			part1 = parts[1]; 
			part2 = parts[2]; 
			part3 = parts[3];
		}
		
	
		final String uName;
		if(part2!=null){
			uName = part2;
			System.out.println("in create group function::"+uName);
		}else{
		 uName = username;}
		
		JScrollPane scrlpanconv1 = new JScrollPane();
		
		JFrame chatwindow = new JFrame();
		UserGroupWindows ugw = new UserGroupWindows();
		chatwindow.setTitle(uName);
		ugw.setUserName(uName);
		chatwindow.setSize(450, 400);
		chatwindow.setLocation(150, 200);
		chatwindow.setResizable(true);
		
		
		
		System.out.println("username is " + uName);
		JPanel pnlChatBX = new JPanel();

		final JTextField textfieldMessage1 = new JTextField(20);
		final JTextArea txtareaconv1 = new JTextArea();
		JButton btnsend1 = new JButton();

		pnlChatBX.add(txtareaconv1);
		if(part3!=null){
			txtareaconv1.setText(part1+": "+part3+"\n\r");
		}else{
		txtareaconv1.setText("");}
		// txtareaconv.setBounds(580, 500, 300, 60);
		txtareaconv1.setName("txtareaconv_" + uName);
		ugw.setTxtarea(txtareaconv1);

		txtareaconv1.setColumns(35);
		txtareaconv1.setLineWrap(true);
		txtareaconv1.setRows(10);
		txtareaconv1.setEditable(false);

		scrlpanconv1
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrlpanconv1
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrlpanconv1.setViewportView(txtareaconv1);
		chatwindow.getContentPane().add(scrlpanconv1);
		scrlpanconv1.setBounds(20, 60, 330, 180);

		lblmsg.setText("Type here:");
		chatwindow.getContentPane().add(lblmsg);
		lblmsg.setBounds(10, 268, 60, 20);

		// textfieldMessage.setBounds(80, 295, 260, 30);

		textfieldMessage1.requestFocus();
		chatwindow.getContentPane().add(textfieldMessage1);
		textfieldMessage1.setBounds(80, 268, 260, 30);
		ugw.setTxtbox(textfieldMessage1);

		// pnlChatBX.add(btnsend);
		// btnsend.setName("btnsend"+username);
		// btnsend.setText("Send message");
		// btnsend.setBounds(700, 450, 120, 25);

		btnsend1.setText("Send");
		chatwindow.getContentPane().add(btnsend1);
		btnsend1.setBounds(360, 268, 70, 25);
		btnsend1.addActionListener(new java.awt.event.ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("clicked on send button in chat window");
				//txtareaconv1.setText(textfieldMessage1.getText());
				if (!textfieldMessage1.getText().equals("")) {
					actbtnSendGroupWindow(uName,textfieldMessage1.getText() );
			}
				textfieldMessage1.setText("");}
		});
		System.out.println("in create group function::::::::::::::completed");

		chatwindow.getContentPane().add(pnlChatBX);
		chatwindow.setVisible(true);
		usersGroupWindowList.add(ugw);

	
	}

	public static void mainWindowGUI() {
		mainWindow.setTitle(UserName + "'s chat window");
		mainWindow.setSize(548, 351);
		mainWindow.setLocation(220, 180);
		mainWindow.setResizable(false);
		constMainWin();
		MainWindow_Action();
		mainWindow.setVisible(true);
	}

	public static void constMainWin() {

		mainWindow.setSize(550, 400);
		mainWindow.getContentPane().setLayout(null);

		btndisconnect.setText("Disconnect");
		mainWindow.getContentPane().add(btndisconnect);
		btndisconnect.setBounds(140, 40, 110, 25);

		btnconnect.setText("Connect room");
		btnconnect.setToolTipText("");
		mainWindow.getContentPane().add(btnconnect);
		btnconnect.setBounds(10, 40, 120, 25);

		lblconv.setHorizontalAlignment(SwingConstants.CENTER);
		lblconv.setText("Select user to add to group");
		mainWindow.getContentPane().add(lblconv);
		lblconv.setBounds(0, 111, 180, 16);

//		txtareaconv.setColumns(20);
//		txtareaconv.setLineWrap(true);
//		txtareaconv.setRows(5);
//		txtareaconv.setEditable(false);

		scrlpanconv
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrlpanconv
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrlpanconv.setViewportView(listonlineforGroup);
		mainWindow.getContentPane().add(scrlpanconv);
		scrlpanconv.setBounds(10, 138, 130, 160);
		
//		txtareaconv2.setColumns(20);
//		txtareaconv2.setLineWrap(true);
//		txtareaconv2.setRows(5);
//		txtareaconv2.setEditable(false);

		scrlpanconv2
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrlpanconv2
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		//scrlpanconv2.setViewportView(txtareaconv2);
		mainWindow.getContentPane().add(scrlpanconv2);
		scrlpanconv2.setBounds(220, 138, 130, 160);

		lblonline.setHorizontalAlignment(SwingConstants.CENTER);
		lblonline.setText("Current Online Users");
		lblonline.setToolTipText("");
		mainWindow.getContentPane().add(lblonline);
		lblonline.setBounds(362, 99, 130, 16);

		// String [] TestNames = {"Arpan","Prachi"};
		// JL_ONLINE.setListData(TestNames);

		scrlpaneonline
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrlpaneonline
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrlpaneonline.setViewportView(listonline);
		listonline.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				createChatRoom((String) listonline.getSelectedValue());
				
			}
		});
		mainWindow.getContentPane().add(scrlpaneonline);
		scrlpaneonline.setBounds(370, 126, 110, 172);

		lblloginas.setText("You Logged In As");
		mainWindow.getContentPane().add(lblloginas);
		lblloginas.setBounds(10, 17, 140, 15);

		signUpButton.setText("Sign up here");
		mainWindow.getContentPane().add(signUpButton);
		signUpButton.setBounds(424, 11, 110, 21);

		lblloginasbx.setHorizontalAlignment(SwingConstants.CENTER);
		mainWindow.getContentPane().add(lblloginasbx);
		lblloginasbx.setBounds(65, 17, 140, 15);
		
		JButton btnAdd = new JButton();
		btnAdd.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				int index = listonlineforGroup.getSelectedIndex();
				System.out.println("Index Selected: " + index);
				String s = (String) listonlineforGroup.getSelectedValue();
				 System.out.println("Value Selected: " + listonlineforGroup.getSelectedValuesList());
				 
				 for(int i=0; i<listonlineforGroup.getSelectedValuesList().size();i++){
					 String str = (String) listonlineforGroup.getSelectedValuesList().get(i);
					
					 listModel.addElement(str);
				 }
				 
				 scrlpanconv2.setViewportView(selectedUser);
				 listModel.removeAllElements();
				 }

			
		});
		btnAdd.setToolTipText("");
		btnAdd.setText("Add");
		btnAdd.setEnabled(true);
		btnAdd.setBounds(150, 201, 60, 25);
		mainWindow.getContentPane().add(btnAdd);
		
		JButton btnCreateGroup = new JButton();
		btnCreateGroup.setToolTipText("");
		btnCreateGroup.setText("Create Group");
		btnCreateGroup.setEnabled(true);
		btnCreateGroup.setBounds(230, 309, 120, 25);
		mainWindow.getContentPane().add(btnCreateGroup);
		btnCreateGroup.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				String strGroup = null;;
				 System.out.println("User selected for group creation are");
				 for(int i=0; i<listModel.getSize();i++){
					 if(i==0){ strGroup = listModel.get(i);}
					 else{
					  strGroup = strGroup + "," +listModel.get(i);}
					 
				 }
				 System.out.println("::::::::::::"+strGroup);
				 createGroupPopUp(strGroup);
				 scrlpanconv2.setViewportView(null);
				 }
		});

	}

	public static void actLogin() {
		btnenter.addActionListener(new java.awt.event.ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				actbtnEnter();
			}
		});
	}

	public static void actbtnEnter() {
		if (!tfielduname.getText().equals("")
				|| !tfieldpwd.getText().equals("")) {
			UserName = tfielduname.getText().trim();
			Password = tfieldpwd.getText().trim();
			
			
			if (validateUser()) {
				lblloginasbx.setText(UserName);
				chatServer.currUser.add(UserName);
				mainWindow.setTitle(UserName + "'s Chat Box");
				loginWin.setVisible(false);
				btnsend.setEnabled(true);
				btndisconnect.setEnabled(true);
				btnconnect.setEnabled(false);
				signUpButton.setEnabled(false);
				connectClient();
			} else {
				JOptionPane.showMessageDialog(null,
						"Username or password incorrect");
				
			}

		} else {
			JOptionPane.showMessageDialog(null, "Enter username and password!");
		}
	}

	public static void MainWindow_Action() {
	

		btndisconnect.addActionListener(new java.awt.event.ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				actbtndisconnect();
			}
		});

		signUpButton.addActionListener(new java.awt.event.ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				pnllogin.setVisible(false);
				UserRegistration ureg = new UserRegistration(
						"New User Registration");
			}
		});

		btnconnect.addActionListener(new java.awt.event.ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				loginGUI();
			}
		});
	}
	
	public static void actbtnSendGroupWindow(String username, String msg) {
		
		ChatClient.send("G"+username+"G"+msg);
		textfieldMessage.requestFocus();
	
}
	
	public static void actbtnSendChatWindow(String username, String msg) {
		
			ChatClient.send("#"+username+"#"+msg);
			textfieldMessage.requestFocus();
		
	}

	public static void actbtndisconnect() {
		try {
			ChatClient.discon();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
