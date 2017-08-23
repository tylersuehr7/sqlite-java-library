# SQLite Java Library
A library to help simplify using the SQLite JDBC when using the Java JDK. Supports code-first and database-first SQLite databases!

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
