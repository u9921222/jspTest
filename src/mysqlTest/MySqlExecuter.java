package mysqlTest;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MySqlExecuter {
    public static boolean executeUpdate(String mySQLSyntax) {
        Connection con = DBManager.getConn();
        Statement stat = null;
        boolean success = true;
        if (con != null) {
            try {
                stat = con.createStatement();
                stat.executeUpdate(mySQLSyntax);
                System.out.println(mySQLSyntax + " OK");
            } catch (SQLException e) {
                success = false;
                System.out.println(mySQLSyntax + " MySQL Exception :" + e.toString());
            } finally {
                DBManager.closeConn(stat, con);
            }
        }
        return success;
    }

    public static ArrayList<ArrayList<String>> executeQuery(String mySqlCmd, ArrayList<String> result) {
        Connection con = DBManager.getConn();
        Statement stat = null;
        ResultSet rs = null;
        ArrayList<ArrayList<String>> results = new ArrayList<>();
        con = DBManager.getConn();
        try {
            stat = con.createStatement();
            rs = stat.executeQuery(mySqlCmd);
            if (rs != null) {
                while (rs.next()) {
                    ArrayList<String> column = new ArrayList<>();
                    for (int i = 0; i < result.size(); i++) {
                        column.add(rs.getString(result.get(i)));
                    }
                    results.add(column);
                }
            }
        } catch (SQLException e) {
            results = null;
            System.out.println(mySqlCmd + " MySQL Exception :" + e.toString());
        } finally {
            DBManager.closeConn(rs, stat, con);
        }
        return results;
    }

    public static ResultSet executeQuery(String mySqlCmd) {
        Connection con = DBManager.getConn();
        Statement stat = null;
        ResultSet rs = null;
        con = DBManager.getConn();
        try {
            stat = con.createStatement();
            rs = stat.executeQuery(mySqlCmd);
        } catch (SQLException e) {
            System.out.println(mySqlCmd + " MySQL Exception :" + e.toString());
        } finally {
            DBManager.closeConn(rs, stat, con);
        }
        return rs;
    }

}
