package mysqlTest;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MySQLHelper {

	public static boolean MySQLexecuteUpdate(String mySQLSyntax) {
		Connection con = DBManager.getConn();
		Statement stat = null;
		boolean success = true;
		if(con != null) {
			try {
				stat = con.createStatement();
				stat.executeUpdate(mySQLSyntax);
				System.out.println(mySQLSyntax+" OK");
			} catch (SQLException e) {
				success = false;
				System.out.println(mySQLSyntax+" MySQL Exception :" + e.toString());
			} finally {
				DBManager.close(stat, con);
			}
		}
		return success;
	}

	public static ArrayList<ArrayList<String>> MySQLexecuteQuery(String mySQLSyntax, ArrayList<String> resultArgs) {
		Connection con = DBManager.getConn();
		Statement stat = null;
		ResultSet rs = null;
		ArrayList<ArrayList<String>> results = new ArrayList<>();
		con = DBManager.getConn();
		try {
			stat = con.createStatement();
			rs = stat.executeQuery(mySQLSyntax);
			if (rs != null) {
				while (rs.next()) {				
					ArrayList<String> column = new ArrayList<>();
					for (int i = 0; i < resultArgs.size(); i++) {
						column.add(rs.getString(resultArgs.get(i)));
					}
					results.add(column);
				}
			}
		} catch (SQLException e) {
			results = null;
			System.out.println(mySQLSyntax+" MySQL Exception :" + e.toString());
		} finally {
			DBManager.close(rs, stat, con);
		}
			return results;
	}

}
