package repositories.users;
import com.tylersuehr.sql.SQLiteDatabase;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import mappers.IEntityMapper;
import models.User;
import repositories.IDatabaseClient;
import repositories.ListCallback;
import repositories.SingleCallback;
import static repositories.DatabaseContract.Users.*;

/**
 * Copyright 2017 Tyler Suehr
 * Created by tyler on 8/23/2017.
 */
public class LocalUserRepository implements IUserRepository {
    private final IEntityMapper<User> mapper;
    private final SQLiteDatabase db;


    public LocalUserRepository(IDatabaseClient client, IEntityMapper<User> mapper) {
        this.mapper = mapper;
        this.db = client.getDb();
    }

    @Override
    public void saveUser(User user) {
        this.db.insert(NAME, mapper.map(user));
    }

    @Override
    public void updateUser(User user) {
        String where = COL_ID + "='" + user.getId() + "'";
        this.db.update(NAME, mapper.map(user), where);
    }

    @Override
    public void removeUser(User user) {
        String where = COL_ID + "='" + user.getId() + "'";
        this.db.delete(NAME, where);
    }

    @Override
    public void findUserById(String userId, SingleCallback<User> callback) {
        ResultSet r = null;
        try {
            String where = COL_ID + "='" + userId + "'";
            r = db.query(NAME, where, null, null);
            if (r == null) {
                throw new NullPointerException("ResultSet was null!");
            }
            r.first();

            User user = mapper.map(r);
            if (user == null) {
                throw new NullPointerException("User was null!");
            }
            callback.onAvailable(user);
        } catch (Exception ex) {
            ex.printStackTrace();
            callback.onNotAvailable(ex);
        } finally {
            try {
                if (r != null) { r.close(); }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void findAllUsers(ListCallback<User> callback) {
        ResultSet r = null;
        try {
            r = db.query(NAME, null, null, null);
            if (r == null) {
                throw new NullPointerException("ResultSet was null!");
            }

            List<User> users = new ArrayList<>();
            while (r.next()) {
                users.add(mapper.map(r));
            }

            if (users.isEmpty()) {
                throw new IllegalStateException("Users list is empty!");
            }
            callback.onAvailable(users);
        } catch (Exception ex) {
            ex.printStackTrace();
            callback.onNotAvailable(ex);
        } finally {
            try {
                if (r != null) { r.close(); }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}