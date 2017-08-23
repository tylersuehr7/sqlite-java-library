package repositories;
import com.tylersuehr.sql.SQLiteDatabase;
import com.tylersuehr.sql.SQLiteOpenHelper;
import static repositories.DatabaseContract.*;

/**
 * Copyright 2017 Tyler Suehr
 * Created by tyler on 8/23/2017.
 */
public final class DatabaseClient extends SQLiteOpenHelper implements IDatabaseClient {
    private static final String DB_NAME = "example_database";
    private static final int DB_VERSION = 1;
    private static volatile DatabaseClient instance;
    private final SQLiteDatabase db;


    private DatabaseClient() {
        super(DB_NAME, DB_VERSION);
        this.db = getWritableInstance();
    }

    public static synchronized DatabaseClient getInstance() {
        if (instance == null) {
            instance = new DatabaseClient();
        }
        return instance;
    }

    @Override
    protected void onCreate(SQLiteDatabase db) {
        createUsersTable(db);
    }

    @Override
    protected void onUpdate(SQLiteDatabase db, int oldV, int newV) {
        db.execSql("DROP TABLE IF EXISTS [" + Users.NAME + "];");
        onCreate(db);
    }

    @Override
    public SQLiteDatabase getDb() {
        return db;
    }

    private void createUsersTable(SQLiteDatabase db) {
        db.execSql("CREATE TABLE [" + Users.NAME + "]([" +
                Users.COL_ID + "] TEXT UNIQUE PRIMARY KEY,[" +
                Users.COL_FIRST_NAME + "] TEXT NOT NULL,[" +
                Users.COL_LAST_NAME + "] TEXT NOT NULL,[" +
                Users.COL_USERNAME + "] TEXT NOT NULL);");
    }
}