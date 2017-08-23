package mappers;
import com.tylersuehr.sql.ContentValues;
import java.sql.ResultSet;
import java.sql.SQLException;
import models.User;
import static repositories.DatabaseContract.Users.*;

/**
 * Copyright 2017 Tyler Suehr
 * Created by tyler on 8/23/2017.
 */
public class UserMapper implements IEntityMapper<User> {
    @Override
    public User map(ResultSet r) {
        try {
            User user = new User();
            user.setId(r.getString(COL_ID));
            user.setFirstName(r.getString(COL_FIRST_NAME));
            user.setLastName(r.getString(COL_LAST_NAME));
            user.setUsername(r.getString(COL_USERNAME));
            return user;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public ContentValues map(User model) {
        ContentValues values = new ContentValues();
        values.put(COL_ID, model.getId());
        values.put(COL_FIRST_NAME, model.getFirstName());
        values.put(COL_LAST_NAME, model.getLastName());
        values.put(COL_USERNAME, model.getUsername());
        return values;
    }
}