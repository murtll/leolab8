package lab7.server.Db;

import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;

public class DatabaseWrapper {
    private final Logger logger;

    public DatabaseWrapper(Logger logger) {
        this.logger = logger;
    }

    public DatabaseResponse executeQuery(String q, Object... args) throws SQLException {
        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement st = createStatement(conn, q, args);

        try {
            ResultSet rs = st.executeQuery();

            Runnable callback = () -> {
                try {
                    conn.close();
                    st.close();
                } catch (SQLException e) {
                    logger.error("Got error while executing cleanup - " + e.getMessage());
                }
            };

            return new DatabaseResponse(rs, callback);
        } catch (SQLException e) {
            conn.close();
            st.close();
            throw new SQLException(e);
        }
    }

    public void executeUpdate(String q, Object... args) throws SQLException {
        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement st = createStatement(conn, q, args);

        try {
            st.executeUpdate();
        } finally {
            conn.close();
            st.close();
        }
    }

    private PreparedStatement createStatement(Connection conn, String q, Object... args) throws SQLException {
        PreparedStatement st = conn.prepareStatement(q);
        for (int i = 0; i < args.length; i++) {
            Object o = args[i];
            if (o instanceof Integer) {
                st.setInt(i + 1, (Integer) o);
            } else if (o instanceof Long) {
                st.setLong(i + 1, (Long) o);
            } else if (o instanceof String) {
                st.setString(i + 1, (String) o);
            } else if (o instanceof LocalDate) {
                st.setTimestamp(i + 1, Timestamp.from(((LocalDate) o).atStartOfDay(ZoneId.systemDefault()).toInstant()));
            } else if (o instanceof Float) {
                st.setFloat(i + 1, (Float) o);
            } else if (o.getClass().isEnum()) {
                st.setString(i + 1, o.toString());
            }

    }
        return st;
    }
}
