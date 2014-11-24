package sebcel.genealogia.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Map;

public class DBHelper {

	public static void main(String[] args) throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/genealogia", "genealogia", "genealogia");
		inspectTable(connection, "zwiazki");
		
		Statement statement = connection.createStatement();
		statement.execute("update osoby set id_zwiazku_rodzicow = 261 where id = 491");
		
		statement = connection.createStatement();
		ResultSet rs = statement.executeQuery("select id, imiona, nazwisko, id_zwiazku_rodzicow from osoby");
		while (rs.next())
		{
			int id = rs.getInt(1);
			String imiona = rs.getString(2);
			String nazwisko = rs.getString(3);
			int idRodzicow = rs.getInt(4);
			
			String rodzice = "-";
			if (idRodzicow > 0)
			{
				rodzice = getZwiazek(connection, idRodzicow);
			}
			System.out.println(id+" "+imiona+" "+nazwisko+" "+idRodzicow+" "+rodzice);
		}
		
		statement = connection.createStatement();
		rs = statement.executeQuery("select * from zwiazki");
		while (rs.next())
		{
			int id = rs.getInt(1);
			int idM = rs.getInt(2);
			int idK = rs.getInt(3);
			System.out.println(id+" m:"+idM+" k:"+idK);
		}
	}
	
	private static String getZwiazek(Connection connection, int idZwiazku) throws Exception {
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery("select id, id_mezczyzny, id_kobiety from zwiazki where id = "+idZwiazku);
		while (rs.next())
		{
			int id = rs.getInt(1);
			int idM = rs.getInt(2);
			int idK = rs.getInt(3);
			return id+" m:"+idM+" k:"+idK;
		}
		System.out.println("*** Zwiazek "+idZwiazku+" nie istnieje.");
		return "?";
	}
	
	private static void inspectTable(Connection connection, String tableName) throws Exception {
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery("SELECT COLUMN_NAME ,DATA_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = '"+tableName+"' ORDER BY  ORDINAL_POSITION ASC; ");
		while (rs.next())
		{
			String name = rs.getString(1);
			String dataType = rs.getString(2);
			System.out.println(name+" "+dataType);
		}
	}
}
