package nswi116.helloworld;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLSelectMBrainz {

	public static void printHallo(PrintWriter out) throws SQLException
	{
		
		/*
		MySQL Connector/J is the official JDBC driver for MySQL.
		http://www.mysql.com/downloads/connector/j/
		*/

		
		String query_str = 
			"SELECT " +
			"artist2.name, artist2.gid " +
			"FROM " +
			"artist as artist1, artist as artist2, artist_relation " +
			"WHERE " +
			"artist1.name='The Beatles' " +
			"AND " +
			"artist_relation.artist = artist1.id " +
			"AND " +
			"artist_relation.ref = artist2.id";

		out.println(query_str);
		
		String url = "jdbc:mysql://dedekj.heliohost.org/dedekj_musicbrainz";

		Connection con = DriverManager.getConnection(url, "dedekj_student", "nswi116");
		Statement stmt = con.createStatement();
		ResultSet result = stmt.executeQuery(query_str);
		while (result.next())
		{
			out.print(result.getString(1));
			out.print(" - ");
			out.println(result.getString(2));
		}
		
		result.close();	stmt.close(); con.close();
		
		out.flush();
	}

	
	public static void main(String[] args) throws SQLException
	{
		printHallo(new PrintWriter(System.out));		
	}
}
