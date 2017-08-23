package com.tylersuehr.sql;
import java.io.Closeable;
import java.io.File;

/**
 * Copyright 2017 Tyler Suehr
 * Created by tyler on 8/23/2017.
 *
 * This manages the SQLite database file and allows it to be versioned.
 *
 * Using this helper, we can utilize SQLite for the following:
 * (1) Code-First: Create a database during runtime.
 * (2) Database-First: Use an existing SQLite database file during runtime.
 *
 * <b>Code-First Approach</b>
 * Create an object and inherit {@link SQLiteOpenHelper}. Implement the abstract methods.
 *
 * When {@link SQLiteOpenHelper} is instantiated, if the SQLite database file doesn't exist,
 * it will be created and then will call, {@link #onCreate(SQLiteDatabase)}. This can be used
 * to create all your tables, views, and insert statements if needed.
 *
 * If the SQLite database file already exists, it will compare the given version to the
 * user_version of the database and call, {@link #onUpdate(SQLiteDatabase, int, int)}, if your
 * given version was higher than the user_version. This can be used to drop all the tables and
 * re-create them if you've updated the table structure.
 *
 * <b>Database-First Approach</b>
 * Create an object and inherit {@link SQLiteOpenHelper}. Implement the abstract methods.
 *
 * You will not need to worry about using {@link #onCreate(SQLiteDatabase)} because the database
 * will already have been created because it already exists.
 *
 * When {@link SQLiteOpenHelper} is instantiated, if the SQLite database file already exists, it
 * will compare the given version to the user_version of the database and call,
 * {@link #onUpdate(SQLiteDatabase, int, int)}, if your given version was higher than the
 * user_version. This can be used to drop all the tables and re-create them if you've updated
 * the table structure.
 */
public abstract class SQLiteOpenHelper implements Closeable {
    private SQLiteDatabase database;
    private int version;
    private String name;


    public SQLiteOpenHelper(String dbName, int version) {
        this.name = (dbName.contains(".db") ? dbName : dbName.concat(".db"));
        this.version = version;
    }

    @Override
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    /**
     * Called when a new database is created.
     * @param db Database created
     */
    protected abstract void onCreate(SQLiteDatabase db);

    /**
     * Called when a database is updated.
     * @param db Updated database
     * @param oldV Old database user version
     * @param newV New database user version
     */
    protected abstract void onUpdate(SQLiteDatabase db, int oldV, int newV);

    /**
     * Lazy-loads our SQLite database and ensures that only one instance is available.
     * @return {@link SQLiteDatabase}
     */
    public SQLiteDatabase getWritableInstance() {
        if (database == null) {
            File file = new File(name);
            boolean alreadyExists = file.exists();

            // This creates our SQLite database file for us, so check if it
            // already exists before this call.
            this.database = new SQLiteDatabase(name);

            // Check if the database file already exists
            if (alreadyExists) {
                // Check if the database should be updated
                int curVersion = database.getVersion();
                if (version > curVersion) {
                    onUpdate(database, curVersion, version);
                    this.database.setVersion(version);
                    Log.i(this, "Database updated!");
                }
            } else {
                // Create our database, since it doesn't exist
                onCreate(database);
                this.database.setVersion(version);
                Log.i(this, "Database created!");
            }
        }
        return database;
    }
}