package schema;

import java.sql.*;

public class saveSQL {

	public saveSQL(){
		//Empty
	}
	
	Connection myConn;
	Statement myStmt;
	ResultSet myRes;
	
	public void myConnection(){
		try {
			myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/markup", "root", "password");
			myStmt = myConn.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void myQuery(String query) throws SQLException{
		myRes = myStmt.executeQuery(query);
	}

	public void myAverage() throws SQLException {
		myRes = myStmt.executeQuery("SELECT keyname, AVG(score) FROM scores GROUP BY keyname;");
	}
}