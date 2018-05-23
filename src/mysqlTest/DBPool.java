package mysqlTest;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DBPool {
    private static DataSource pool = null;
    static {
        Context env = null;
        try {
            env = (Context) new InitialContext().lookup("java:comp/env");
            pool = (DataSource) env.lookup("jdbc/javabase");
            if (pool == null) {
                System.out.println("'DBPool' is an unknown DataSource");
            }
        } catch (NamingException ne) {
            ne.printStackTrace();
        }
    }

    public static DataSource getConnPool() {
        return pool;
    }
}
