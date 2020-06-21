package fr.tse.nosql.project;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.DriverManager;

public class HiveCreateAggregaTable {
	   private static String driverName = "org.apache.hadoop.hive.jdbc.HiveDriver";
	   
	   public static void main(String[] args) throws SQLException {
	   
		   try {
			// Register driver and create driver instance
			      Class.forName(driverName);
			      
			      // get connection
			      Connection con = DriverManager.getConnection("jdbc:hive://localhost:10000/userdb", "", "");
			      
			      // create statement
			      Statement stmt = con.createStatement();
			      
			      // execute statement
			      ResultSet resLinkyByType = stmt.executeQuery("SELECT event.type_evt, event.date_occur_evt, COUNT(*) FROM event group by event.date_occur_evt, event.type_evt; ");
			      
			      while (resLinkyByType.next()) {
			         System.out.println(resLinkyByType.getString(1) + " "  + resLinkyByType.getString(2) + " " + resLinkyByType.getInt(3)); 
			      }
			      
			      ResultSet resLinkyByTypeAndEquip = stmt.executeQuery("SELECT event.type_evt, event.date_occur_evt, event.id_equipement, COUNT(*) FROM event group by event.type_evt, event.date_occur_evt, event.id_equipement; ");
			      
			      while (resLinkyByTypeAndEquip.next()) {
			         System.out.println(resLinkyByTypeAndEquip.getString(1) + " "  + resLinkyByTypeAndEquip.getString(2) + " "  + resLinkyByTypeAndEquip.getString(3) + " "+ resLinkyByTypeAndEquip.getInt(3)); 
			      }
			      
			      ResultSet resLinkyByTypeAndTown = stmt.executeQuery("SELECT event.type_evt, event.date_occur_evt, installation_c.id_commune, COUNT(*) FROM event, installation_c "+
			    		  " where event.id_equipement = installation_c.pdc"+
			    		  " group by event.type_evt, event.date_occur_evt, installation_c.id_commune; ");
			      
			      while (resLinkyByTypeAndTown.next()) {
			         System.out.println(resLinkyByTypeAndTown.getString(1) + " "  + resLinkyByTypeAndTown.getString(2) +" "  + resLinkyByTypeAndTown.getString(3) + " "+ resLinkyByTypeAndTown.getInt(4)); 
			      }
			      
			      ResultSet resLinkyByTypeAndPdk = stmt.executeQuery("SELECT event.type_evt, event.date_occur_evt, installation_k.pdk, COUNT(*) FROM event, installation_k "+
			    		  " where event.id_equipement = installation_k.pdk"+
			    		  " group by event.type_evt, event.date_occur_evt, installation_c.pdk; ");
			      
			      while (resLinkyByTypeAndPdk.next()) {
			         System.out.println(resLinkyByTypeAndPdk.getString(1) + " "  + resLinkyByTypeAndPdk.getString(2) + " "  + resLinkyByTypeAndPdk.getString(3) + " "+ resLinkyByTypeAndPdk.getInt(4)); 
			      }
			      
			      ResultSet resLinkyByTypeAndDept = stmt.executeQuery("SELECT event.type_evt, event.date_occur_evt, commune.id_departement, COUNT(*) FROM event, installation_c, commune "+
			    		  " where event.id_equipement = installation_c.pdc"+
			    		  " and commune.id_commune = installation_c.id_commune"+
			    		  " group by event.type_evt, event.date_occur_evt, installation_c.id_departement; ");
			      
			      while (resLinkyByTypeAndDept.next()) {
			         System.out.println(resLinkyByTypeAndDept.getString(1) + " "  + resLinkyByTypeAndDept.getString(2) + " "  + resLinkyByTypeAndDept.getString(3) + " "+ resLinkyByTypeAndDept.getInt(4)); 
			      }
			      
			      // Requête pour récupérer l'indicateur en fonction du type et de la région
			      ResultSet resLinkyByTypeAndRegion = stmt.executeQuery("SELECT event.type_evt, event.date_occur_evt, commune.id_region, COUNT(*) " + 
			    		  " FROM event, installation_c, commune "+			      
			    		  " where event.id_equipement = installation_c.pdk"+
			    		  " and commune.id_commune = installation_c.id_commune"+
			    		  " group by event.type_evt, event.date_occur_evt, installation_c.id_region, ; ");
			      
			      while (resLinkyByTypeAndRegion.next()) {
			         System.out.println(resLinkyByTypeAndRegion.getString(1) + " "  + resLinkyByTypeAndRegion.getString(2) + " "+ resLinkyByTypeAndRegion.getString(3) + " "+ resLinkyByTypeAndRegion.getInt(4)); 
			      }
						      
			      con.close();
		   } catch (ClassNotFoundException e) {
			   e.printStackTrace();
		   }
	      
	 }
}

