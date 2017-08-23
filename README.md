# SQLite Java Library
A library to help simplify using the SQLite JDBC when using the Java JDK. Supports code-first and database-first SQLite databases!

### Code-First Approach
Use this approach if you want your database to be created during runtime. 

If you're using this approach, `SQLiteOpenHelper`, guarantees that `onCreate(SQLiteDatabase)` will be called only after the SQLite database file has first been created. This method can be used to create all the tables, views, and/or insert initial data.

Incrementing the database version, after it has been created, guarantees that `SQLiteOpenHelper` will call `onUpdate(SQLiteDatabase, int, int)`. This method can be used to update table structures or drop and re-create all tables.

### Database-First Approach
Use this approach if you already have a SQLite database file that you wish to use during runtime.

If you're using this approach, `SQLiteOpenHelper`, will NOT call `onCreate(SQLiteDatabase)`. However, you can still utilize the database version if you wish to update the database programmatically. Incrementing the database version will still guarentee that `SQLiteOpenHelper` will call `onUpdate(SQLiteDatabase, int, int)`.

### Code-First Example:
Step 1: Inherit `SQLiteOpenHelper` and implement the abtract methods.
```java
  public final class DatabaseClient extends SQLiteOpenHelper {
      @Override
      protected void onCreate(SQLiteDatabase db) {
          ...
      }
      
      @Override
      protected void onUpdate(SQLiteDatabase db, int oldVersion, int newVersion) {
          ...
      }
  }
```
Step 2: Create using a Singleton, add logic for your database structure, and expose the `SQLiteDatabase`.
```java
  public final class DatabaseClient extends SQLiteOpenHelper {
      private static final String DB_NAME = "johnny_vaughn_db";
      private static final int DB_VERSION = 1;
      private static volatile DatabaseClient instance;
      private final SQLiteDatabase db;
      
      
      private DatabaseClient() {
          super(DB_NAME, DB_VERSION);
          this.db = getWritableDatabase();
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
      protected void onUpdate(SQLiteDatabase db, int oldVersion, int newVersion) {
          db.execSql("DROP TABLE IF EXISTS [users];");
          onCreate(db);
      }
      
      public SQLiteDatabase getDatabase() {
          return db;
      }
      
      private void createUsersTable(SQLiteDatabase db) {
          db.execSql("CREATE TABLE [users]([id] INTEGER PRIMARY KEY, [name] TEXT NOT NULL);");
      }
  }
```
