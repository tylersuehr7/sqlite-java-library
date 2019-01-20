package com.tylersuehr.sql;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * The SQLite database itself.
 * This object allows ease of querying or executing commands.
 *
 * Will handle establishing the connection, managing statements, and closing the connection
 * to the database by utilizing the SQLite JDBC drivers.
 *
 * The following operations are supported:
 * (1) Insert data into the database. {@link #insert(String, ContentValues)}
 * (2) Update data in the database. {@link #update(String, ContentValues, String)}
 * (3) Delete data in the database. {@link #delete(String, String)}
 * (4) Query data in the database. {@link #query(String, String, String, String)}
 * (5) Raw query data in the database. {@link #rawQuery(String)}
 * (6) Raw command on the database. {@link #execSql(String)}
 *
 * @author Tyler Suehr
 */
public final class SQLiteDatabase extends SQLiteCloseable {
    private static final String DRIVER = "org.sqlite.JDBC";
    private static final String PATH = "jdbc:sqlite:";
    private final SQLBuilder builder;
    private Connection connection;
    private Statement statement;


    SQLiteDatabase(String dbName) {
        this.builder = new SQLBuilder();
        openConnection(dbName);
    }

    @Override
    protected void onAllReferencesReleased() {
        try {
            if (connection != null) {
                this.connection.close();
            }
            if (statement != null) {
                this.statement.close();
            }
            System.out.println("All references released!");
        } catch (SQLException ex) {
            logException(ex);
        }
    }

    /**
     * Queries data from the SQLite database.
     *
     * @param table the name of the table to query
     * @param selection the WHERE clause (i.e. "[id]=12")
     * @param order the ORDER BY clause (i.e. "[timestamp ASC]")
     * @param limit the LIMIT clause (i.e. "4")
     * @return the results
     */
    public ResultSet query(String table, String selection, String order, String limit) {
        acquireReference();
        try {
            final String SQL = builder.createQuery(table, selection, order, limit);
            return statement.executeQuery(SQL);
        } catch (SQLException ex) {
            logException(ex);
            return null;
        } finally {
            releaseReference();
        }
    }

    /**
     * Convenience method for inserting data into the SQLite database.
     *
     * @param table the name of the table
     * @param values the content to be inserted
     */
    public void insert(String table, ContentValues values) {
        acquireReference();
        try {
            final String SQL = builder.createInsert(table, values);
            this.statement.executeUpdate(SQL);
            this.connection.commit();
        } catch (SQLException ex) {
            logException(ex);
        } finally {
            releaseReference();
        }
    }

    /**
     * Convenience method for updating data in the SQLite database.
     *
     * @param table the name of the table
     * @param values the content to be updated
     * @param selection the WHERE clause
     */
    public void update(String table, ContentValues values, String selection) {
        acquireReference();
        try {
            final String SQL = builder.createUpdate(table, values, selection);
            this.statement.executeUpdate(SQL);
            this.connection.commit();
        } catch (SQLException ex) {
            logException(ex);
        } finally {
            releaseReference();
        }
    }

    /**
     * Convenience method for deleting data in the SQLite database.
     *
     * @param table the name of the table
     * @param selection the WHERE clause
     */
    public void delete(String table, String selection) {
        acquireReference();
        try {
            final String SQL = builder.createDelete(table, selection);
            this.statement.executeUpdate(SQL);
            this.connection.commit();
        } catch (SQLException ex) {
            logException(ex);
        } finally {
            releaseReference();
        }
    }

    /**
     * Queries data from the SQLite database using a raw SQL query.
     *
     * @param sql the SQL query to run
     * @return the results
     */
    public ResultSet rawQuery(String sql) {
        acquireReference();
        try {
            return statement.executeQuery(sql);
        } catch (SQLException ex) {
            logException(ex);
            return null;
        } finally {
            releaseReference();
        }
    }

    /**
     * Executes a command on the SQLite database using a raw SQL query.
     * @param sql the SQL query to run
     */
    public void execSql(String sql) {
        acquireReference();
        try {
            this.statement.executeUpdate(sql);
            this.connection.commit();
        } catch (SQLException ex) {
            logException(ex);
        } finally {
            releaseReference();
        }
    }

    /**
     * Sets the user version of the SQLite database.
     * @param version the user version to be set
     */
    void setVersion(int version) {
        acquireReference();
        try {
            final String SQL = "PRAGMA user_version=" + version;
            this.statement.executeUpdate(SQL);
            this.connection.commit();
        } catch (SQLException ex) {
            logException(ex);
        } finally {
            releaseReference();
        }
    }

    /**
     * Gets the user version of the SQLite database.
     * @return the user version of the database
     */
    int getVersion() {
        acquireReference();
        try {
            final String SQL = "PRAGMA user_version";
            ResultSet c = statement.executeQuery(SQL);
            return c.getInt("user_version");
        } catch (SQLException ex) {
            logException(ex);
            return -1;
        } finally {
            releaseReference();
        }
    }

    /**
     * Opens a connection to the SQLite database.
     * @param dbName the name of the database file (don't include file extension)
     */
    private void openConnection(String dbName) {
        try {
            Class.forName(DRIVER);
            this.connection = DriverManager.getConnection(PATH + dbName);
            this.connection.setAutoCommit(false);
            this.statement = connection.createStatement();
            acquireReference();
        } catch (ClassNotFoundException|SQLException ex) {
            logException(ex);
        }
    }

    /**
     * Convenience method to log an exception and print its stacktrace.
     * @param ex the exception
     */
    private void logException(final Exception ex) {
        System.err.println("SQLite > " + ex.getMessage());
        ex.printStackTrace();
    }
}