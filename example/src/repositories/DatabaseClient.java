/*
 * MIT License
 *
 * Copyright (c) Tyler Suehr 2019.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package repositories;
import com.tylersuehr.sql.SQLiteDatabase;
import com.tylersuehr.sql.SQLiteOpenHelper;
import static repositories.DatabaseContract.*;

/**
 * @author Tyler Suehr
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