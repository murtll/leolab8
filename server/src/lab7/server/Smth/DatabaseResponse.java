package lab7.server.Smth;

import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.Callable;

public class DatabaseResponse {
    private final ResultSet resultSet;
    private final Runnable cleanup;

    public DatabaseResponse(ResultSet resultSet, Runnable cleanup) {
        this.resultSet = resultSet;
        this.cleanup = cleanup;
    }

    public ResultSet getResultSet() {
        return resultSet;
    }

    public void executeCleanup() {
        cleanup.run();
    }
}
