package fr.tse.nosql.project;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.DriverManager;

public class HiveCreateDb {
   private static String driverName = "org.apache.hadoop.hive.jdbc.HiveDriver";
   
   public static void main(String[] args) throws SQLException {
   
	   try {
		   // Register driver and create driver instance
		   Class.forName(driverName);
	
		   // get connection
		   Connection con = DriverManager.getConnection("jdbc:hive://localhost:10000/default", "", "");
		   Statement stmt = con.createStatement();
	      
		   // Création de la base de données
		   stmt.executeQuery("CREATE DATABASE project");
		   System.out.println("Database project created successfully.");
	      
		   con.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   }
}