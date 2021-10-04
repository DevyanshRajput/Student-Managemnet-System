package smsystem.general;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * Title : DataBaseConnection.java
 * Purpose : For database connection
 */

public class DatabaseConnection {
	
	static Connection con=null;
	static final String url="jdbc:mysql://localhost:3306/studentmanagement";
	static final String uname="root";
	static final String password="";
	
	
	public static Connection getConnection()
	{
		if(con!=null)
		{
			return con;
		}
		else
		{
			try
			{
				
				
				con=DriverManager.getConnection(url,uname,password);
				return con;
			}
			catch(Exception exp)
			{
				exp.printStackTrace();
				return con;
			}
		}
	}
	public static boolean checkconnection() 
	{
		
		try
		{
			
	
			con=DriverManager.getConnection(url,uname,password);
			return true;
		}
		catch(Exception exp)
		{
			exp.printStackTrace();
			return false;
		}
	}
	public static void  closeConnection() 
	{
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}