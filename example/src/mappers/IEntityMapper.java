package mappers;
import com.tylersuehr.sql.ContentValues;
import java.sql.ResultSet;

/**
 * Copyright 2017 Tyler Suehr
 * Created by tyler on 8/23/2017.
 */
public interface IEntityMapper<T> {
    T map(ResultSet r);
    ContentValues map(T model);
}