package repositories;
import com.tylersuehr.sql.SQLiteDatabase;

/**
 * Copyright 2017 Tyler Suehr
 * Created by tyler on 8/23/2017.
 */
public interface IDatabaseClient {
    SQLiteDatabase getDb();
}