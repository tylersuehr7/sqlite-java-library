package com.tylersuehr.sql;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Copyright 2017 Tyler Suehr
 * Created by tyler on 8/23/2017.
 *
 * This represents the SQLite database itself. With this object, we will be able to easily query
 * or execute commands on it.
 *
 * This handles the establishing the connection, managing statements, and closing connections to
 * our database by utilizing the SQLite JDBC drivers.
 *
 * We are able to perform the following operations with this object:
 * (1) Insert data into the database. {@link #insert(String, ContentValues)}
 * (2) Update data in the database. {@link #update(String, ContentValues, String)}
 * (3) Delete data in the database. {@link #delete(String, String)}
 * (4) Query data in the database. {@link #query(String, String, String, String)}
 * (5) Raw query data in the database. {@link #rawQuery(String)}
 * (6) Raw command on the database. {@link #execSql(String)}
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
            Log.i(this, "All references released!");
        } catch (SQLException ex) {
            Log.wtf(this, ex);
        }
    }

    /**
     * Queries data from the SQLite database.
     * @param table Name of table
     * @param selection Where clause (Ex: "[id]=12")
     * @param order OrderBy clause (Ex: "[timestamp ASC]")
     * @param limit Limit clause (Ex: "4")
     * @return {@link ResultSet}
     */
    public ResultSet query(String table, String selection, String order, String limit) {
        acquireReference();
        try {
            final String SQL = builder.createQuery(table, selection, order, limit);
            return statement.executeQuery(SQL);
        } catch (SQLException ex) {
            Log.wtf(this, ex);
            return null;
        } finally {
            releaseReference();
        }
    }

    /**
     * Convenience method for inserting data into the SQLite database.
     * @param table Name of table
     * @param values {@link ContentValues}
     */
    public void insert(String table, ContentValues values) {
        acquireReference();
        try {
            final String SQL = builder.createInsert(table, values);
            this.statement.executeUpdate(SQL);
            this.connection.commit();
        } catch (SQLException ex) {
            Log.wtf(this, ex);
        } finally {
            releaseReference();
        }
    }

    /**
     * Convenience method for updating data in the SQLite database.
     * @param table Name of table
     * @param values {@link ContentValues}
     * @param selection Where clause
     */
    public void update(String table, ContentValues values, String selection) {
        acquireReference();
        try {
            final String SQL = builder.createUpdate(table, values, selection);
            this.statement.executeUpdate(SQL);
            this.connection.commit();
        } catch (SQLException ex) {
            Log.wtf(this, ex);
        } finally {
            releaseReference();
        }
    }

    /**
     * Convenience method for deleting data in the SQLite database.
     * @param table Name of table
     * @param selection Where clause
     */
    public void delete(String table, String selection) {
        acquireReference();
        try {
            final String SQL = builder.createDelete(table, selection);
            this.statement.executeUpdate(SQL);
            this.connection.commit();
        } catch (SQLException ex) {
            Log.wtf(this, ex);
        } finally {
            releaseReference();
        }
    }

    /**
     * Queries data from the SQLite database using a raw SQL query.
     * @param sql SQL query
     * @return {@link ResultSet}
     */
    public ResultSet rawQuery(String sql) {
        acquireReference();
        try {
            return statement.executeQuery(sql);
        } catch (SQLException ex) {
            Log.wtf(this, ex);
            return null;
        } finally {
            releaseReference();
        }
    }

    /**
     * Executes a command on the SQLite database using a raw SQL query.
     * @param sql SQL query
     */
    public void execSql(String sql) {
        acquireReference();
        try {
            this.statement.executeUpdate(sql);
            this.connection.commit();
        } catch (SQLException ex) {
            Log.wtf(this, ex);
        } finally {
            releaseReference();
        }
    }

    /**
     * Sets the user version of the SQLite database.
     * @param version User database version
     */
    void setVersion(int version) {
        acquireReference();
        try {
            final String SQL = "PRAGMA user_version=" + version;
            this.statement.executeUpdate(SQL);
            this.connection.commit();
        } catch (SQLException ex) {
            Log.wtf(this, ex);
        } finally {
            releaseReference();
        }
    }

    /**
     * Gets the user version of the SQLite database.
     * @return User database version
     */
    int getVersion() {
        acquireReference();
        try {
            final String SQL = "PRAGMA user_version";
            ResultSet c = statement.executeQuery(SQL);
            return c.getInt("user_version");
        } catch (SQLException ex) {
            Log.wtf(this, ex);
            return -1;
        } finally {
            releaseReference();
        }
    }

    /**
     * Opens a connection to the SQLite database.
     * @param dbName Name of the database file (don't include file extension).
     */
    private void openConnection(String dbName) {
        try {
            Class.forName(DRIVER);
            this.connection = DriverManager.getConnection(PATH + dbName);
            this.connection.setAutoCommit(false);
            this.statement = connection.createStatement();
            acquireReference();
        } catch (ClassNotFoundException|SQLException ex) {
            Log.wtf(this, ex);
        }
    }
}