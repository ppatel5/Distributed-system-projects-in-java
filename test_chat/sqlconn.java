package src;


import src.User;

import java.sql.*;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class sqlconn {

	public static Connection conn = null;

	public boolean checkLoginDetails(String username, String password) {
		System.out.println("in sql conn");
		//String dbURL = "jdbc:sqlserver://127.0.0.1;databaseName=ChatRoom;integratedSecurity=true;";

		
		//start of MS Server connection string code
		/*try {

			conn = DriverManager.getConnection(dbURL, null, null);
			System.out.println("in sql conn");
			System.out.println("in sql conn"+username);
			if (conn != null) {
				DatabaseMetaData dm = (DatabaseMetaData) conn.getMetaData();
				System.out.println("Driver name: " + dm.getDriverName());
				System.out.println("Driver version: " + dm.getDriverVersion());
				System.out.println("Product name: "
						+ dm.getDatabaseProductName());
				System.out.println("Product version: "
						+ dm.getDatabaseProductVersion());
			}

			String query = "select * from reg_details where user_name ="+"'"+username+"'" +" and password="+"'"+password+"'";
			System.out.println(query);
			PreparedStatement stmt = conn.prepareStatement(query);

			ResultSet rs = stmt.executeQuery();
			int count=0;
			while(rs.next()){
				count++;
			}
			if(count>0){
				System.out.println("login correct");
				return true;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}*/
		////end of MS Server connection string code
		
		//start of mysql connection code
		//Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
//		new com.mysql.jdbc.Driver();
			Class.forName("com.mysql.jdbc.Driver").newInstance();
// conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdatabase?user=testuser&password=testpassword");
			String connectionUrl = "jdbc:mysql://localhost:3306/user_registration";
			String connectionUser = "root";
			String connectionPassword = "root";
			conn = DriverManager.getConnection(connectionUrl, connectionUser, connectionPassword);
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM user_reg");
			while (rs.next()) {
				//String id = rs.getString("id");
				String firstName = rs.getString("username");
				String lastName = rs.getString("password");
				System.out.println("First Name: " + username
						+ ", Last Name: " + password);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
			try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
			try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
		}
		//end of mysql connection code
		
		return false;

	}

	public void debugmethod() {
		System.out.println("in sql conn class");
		
	}

	public boolean registerUser(ArrayList<User> user_data) {
		String dbURL = "jdbc:sqlserver://127.0.0.1;databaseName=ChatRoom;integratedSecurity=true;";

		try {

			conn = DriverManager.getConnection(dbURL, null, null);
			System.out.println("in sql conn");
			//System.out.println("in sql conn"+username);
			if (conn != null) {
				DatabaseMetaData dm = (DatabaseMetaData) conn.getMetaData();
				System.out.println("Driver name: " + dm.getDriverName());
				System.out.println("Driver version: " + dm.getDriverVersion());
				System.out.println("Product name: "
						+ dm.getDatabaseProductName());
				System.out.println("Product version: "
						+ dm.getDatabaseProductVersion());
			}
			User user1 = user_data.get(0);
			String checkUsername = "select * from reg_details where user_name ="+"'"+user1.getUsername()+"'";
			System.out.println(checkUsername);
			PreparedStatement stmt1 = conn.prepareStatement(checkUsername);
			int flag =0;
			ResultSet rs = stmt1.executeQuery();
			while(rs.next()){
				flag++;
			}
			if(flag==0){
			String insertQuery = "INSERT INTO reg_details "
			             		+"VALUES"
			                   +"('"+user1.getUsername()+"',"
			                   +"'"+user1.getPassword()+"',"
			                   +"'"+user1.getFname()+"',"
			                   +"'"+user1.getLname()+"',"
			                   +"'"+user1.getEmail()+"',"
			                   +"'pwd')";
			System.out.println(insertQuery);
			PreparedStatement stmt = conn.prepareStatement(insertQuery);

			int update = stmt.executeUpdate();
			int count=0;
			if(update==1){
				return true;
			}
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		

		return true;	
	}
}
