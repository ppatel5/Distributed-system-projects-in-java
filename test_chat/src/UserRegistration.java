package src;

import javax.swing.*;

import src.User;
import src.sqlconn;

import java.awt.event.*;
import java.util.ArrayList;

public class UserRegistration extends JFrame implements ActionListener {


	private static final long serialVersionUID = 1L;

	JButton jbtnregister, jbtnreset, jbtnexit;
	JTextField jtfuser, jtffirstname, jtflastname, jtfemail;
	JPasswordField jpfpass, jpfconfpass;
	JLabel jlabuser, jlabpass, jlabconfpass, jlabfirstname, jlablastname,
			jlabemail;
	ArrayList<User> user_data = new ArrayList<User>();
	sqlconn dbobject = new sqlconn();

	public UserRegistration(String username) {
		super(username);
		// Button object initiation
		jbtnregister = new JButton("Register");
		jbtnreset = new JButton("Reset");
		jbtnexit = new JButton("Cancel");

		// text field object initiation
		jtfuser = new JTextField();
		jtffirstname = new JTextField();
		jtflastname = new JTextField();
		jtfemail = new JTextField();
		jpfpass = new JPasswordField();
		jpfconfpass = new JPasswordField();

		// label object initiation;
		jlabuser = new JLabel("Username *");
		jlabpass = new JLabel("Password *");
		jlabfirstname = new JLabel("First Name *");
		jlablastname = new JLabel("Last Name *");
		jlabemail = new JLabel("E-mail *");
		jlabconfpass = new JLabel("Confirm Password *");

		this.setLayout(null);
		//this.setDefaultCloseOperation(JFrame.)));

		// Setting boundaries
		jlabfirstname.setBounds(10, 30, 120, 20);
		jlablastname.setBounds(10, 70, 120, 20);
		jlabemail.setBounds(10, 110, 120, 20);
		jlabuser.setBounds(10, 150, 120, 20);
		jlabpass.setBounds(10, 190, 120, 20);
		jlabconfpass.setBounds(10, 230, 120, 20);

		jtffirstname.setBounds(140, 30, 120, 20);
		jtflastname.setBounds(140, 70, 120, 20);
		jtfemail.setBounds(140, 110, 120, 20);
		jtfuser.setBounds(140, 150, 120, 20);
		jpfpass.setBounds(140, 190, 120, 20);
		jpfconfpass.setBounds(140, 230, 120, 20);

		jbtnregister.setBounds(200, 300, 100, 20);
		jbtnreset.setBounds(300, 300, 100, 20);
		jbtnexit.setBounds(400, 300, 100, 20);

		this.add(jlabuser);
		this.add(jlabpass);
		this.add(jlabfirstname);
		this.add(jlablastname);
		this.add(jlabconfpass);
		this.add(jlabemail);

		this.add(jtfuser);
		this.add(jpfpass);
		this.add(jpfconfpass);
		this.add(jtffirstname);
		this.add(jtflastname);
		this.add(jtfemail);

		this.add(jbtnregister);
		this.add(jbtnreset);
		this.add(jbtnexit);

		this.setSize(600, 600);
		this.setVisible(true);

		// Action on button press
		jbtnregister.addActionListener(new java.awt.event.ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				registerUser();
			}
		});
		// Action on button press
		jbtnreset.addActionListener(new java.awt.event.ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				resetData();
			}
		});
		// Action on button press
		jbtnexit.addActionListener(new java.awt.event.ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cancelReg();
			}
		});

	}

	@SuppressWarnings("deprecation")
	public void registerUser() {

		String fname = jtffirstname.getText();
		String lname = jtflastname.getText();
		String username = jtfuser.getText();
		String password = jpfpass.getText();
		String confpass = jpfconfpass.getText();
		String email = jtfemail.getText();

		if (fname.equals("") || lname.equals("") || username.equals("")
				|| password.equals("") || confpass.equals("")
				|| email.equals("")) {
			JOptionPane.showMessageDialog(null,
					"Please enter required fields followed by '*' symbol");

		} else {

			User user1 = new User();
			user1.setUsername(username);
			user1.setPassword(password);
			user1.setFname(fname);
			user1.setLname(lname);
			user1.setEmail(email);
			user_data.add(user1);

			if (dbobject.registerUser(user_data)) {
				JOptionPane.showMessageDialog(null,
						"You are Successfully Registered !");

			} else {
				JOptionPane.showMessageDialog(null,
						"Username already exist!!");
			}

		}

	}

	public void resetData() {

		jtffirstname.setText(" ");
		jtflastname.setText(" ");
		jtfuser.setText(" ");
		jpfpass.setText("");
		jpfconfpass.setText("");
		jtfemail.setText("");

	}

	public void cancelReg() {
		this.setVisible(false);
		
		clientChatWindow.mainWindowGUI();
		clientChatWindow.init();

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		
	}

}
