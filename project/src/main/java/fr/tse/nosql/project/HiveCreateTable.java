package fr.tse.nosql.project;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.DriverManager;


//hive.exec.dynamic.partition = true;
public class HiveCreateTable {

	   private static String driverName = "org.apache.hadoop.hive.jdbc.HiveDriver";
	   
	   public static void main(String[] args) throws SQLException {
	   
		   try {
			// Register driver and create driver instance
			      Class.forName(driverName);
			      
			      // get connection
			      Connection con = DriverManager.getConnection("jdbc:hive://localhost:10000/userdb", "", "");
			      
			      // create statement
			      Statement stmt = con.createStatement();
			      
			      // Création d'une table avec les informations sur les communes
			      stmt.executeQuery("CREATE EXTERNAL TABLE IF NOT EXISTS " +
			      		"commune ( id_commune STRING," + 
			      		" code_insee INT," + 
			      		" libelle_commune STRING," + 
			      		" id_departement STRING," + 
			      		" id_region STRING," + 
			      		" libelle_departement STRING," + 
			      		" libelle_region STRING)" + 
			      		" ROW FORMAT DELIMITED" + 
			      		" FIELDS TERMINATED BY ';'" + 
			      		" STORED AS TEXTFILE" + 
			      		" LOCATION '/tmp/project/communes';");
			         
			      System.out.println("Table commune created.");
			      // Chargement des informations après les traitemants de yarn et map reduce
			      stmt.executeQuery("LOAD DATA INPATH '/tmp/project/commune.csv' INTO TABLE project.commune; ");
			      
			      // Création d'une table avec les informations sur les événements
			      stmt.executeQuery("CREATE EXTERNAL TABLE IF NOT EXISTS " +
			      		"evenement ( id_evt STRING," + 
			      		" date_system_evt TIMESTAMP," + 
			      		" type_evt STRING," + 
			      		" type_equipement STRING," + 
			      		" id_equipement STRING," + 
			      		" infos_div STRING)" +
			      		" PARTITIONED BY (date_occur_evt TIMESTAMP)" +
			      		" ROW FORMAT DELIMITED" + 
			      		" FIELDS TERMINATED BY ';'" + 
			      		" STORED AS TEXTFILE" + 
			      		" LOCATION '/tmp/project/evenements';");
				         
			      System.out.println("Table evenement created.");
			      
			      // Chargement des informations après les traitemants de yarn et map reduce
			      stmt.executeQuery("LOAD DATA INPATH '/tmp/project/event.csv' INTO TABLE project.evenement; ");
			      System.out.println("Load Data into envent successful");
				      
			      // Création d'une table avec les informations sur les compteurs
			      stmt.executeQuery("CREATE EXTERNAL TABLE IF NOT EXISTS " +
			      		"installation_c ( pdc STRING," + 
			      		" id_compteur STRING," + 
			      		" date_debut DATE," + 
			      		" date_fin DATE," + 
			      		" id_constructeur STRING," + 
			      		" pdk STRING," + 
			      		" id_commune STRING)" + 
			      		" ROW FORMAT DELIMITED" + 
			      		" FIELDS TERMINATED BY ';'" + 
			      		" STORED AS TEXTFILE" + 
			      		" LOCATION '/tmp/project/installation_c';");
				         
			      System.out.println("Table installation_c created.");
			      // Chargement des informations après les traitemants de yarn et map reduce
			      stmt.executeQuery("LOAD DATA INPATH '/tmp/project/installation_c.csv' INTO TABLE project.installation_c; ");
			      
			      // Création d'une table avec les informations sur les concentrateurs
			      stmt.executeQuery("CREATE EXTERNAL TABLE IF NOT EXISTS " +
			      		"installation_k ( pdk STRING," + 
			      		" id_concentrateur STRING," + 
			      		" date_debut DATE," + 
			      		" date_fin DATE," + 
			      		" id_constructeur STRING," + 
			      		" id_commune STRING)" + 
			      		" ROW FORMAT DELIMITED" + 
			      		" FIELDS TERMINATED BY ';'" + 
			      		" STORED AS TEXTFILE" + 
			      		" LOCATION '/tmp/project/installation_k';");
				         
			      System.out.println("Table installation_k created.");
			      // Chargement des informations après les traitemants de yarn et map reduce
			      stmt.executeQuery("LOAD DATA INPATH '/tmp/project/event.csv' INTO TABLE project.evenement; ");
				      
			      con.close();
		   } catch (ClassNotFoundException e) {
			   e.printStackTrace();
		   }
	      
	 }
}

