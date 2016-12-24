package src;


import src.User;


import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class sqlconn {

	public static Connection conn = null;

	public boolean checkLoginDetails(String username, String password) {
		System.out.println("in sql conn");
		String dbURL = "jdbc:sqlserver://127.0.0.1;databaseName=ChatRoom;integratedSecurity=true;";

		try {

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
		}
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
		

		return false;	
	}
}
