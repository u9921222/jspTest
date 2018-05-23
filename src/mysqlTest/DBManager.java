package mysqlTest;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBManager {
    private static Logger logger = Logger.getLogger(DBManager.class.getName());
    public static Connection getConn() {
        Connection conn = null;
        try {
            conn = DBPool.getConnPool().getConnection();
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        return conn;
    }

    public static void closeConn(ResultSet rs, Statement ps, Connection conn) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (ps != null) {
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    public static void closeConn(Statement ps, Connection conn) {
        try {
            if (ps != null) {
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (null != conn) {
                conn.close();
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    public static void closeConn(Connection conn) {
        try {
            if (null != conn) {
                conn.close();
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }
}