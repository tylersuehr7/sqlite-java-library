package repositories.users;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import models.User;
import repositories.ListCallback;
import repositories.SingleCallback;

/**
 * Copyright 2017 Tyler Suehr
 * Created by tyler on 8/23/2017.
 */
public final class UserRepository implements IUserRepository {
    private static volatile UserRepository instance;
    private final IUserRepository local;
    private Map<String, User> cache;
    private boolean invalidCache = false;


    private UserRepository(IUserRepository local) {
        this.local = local;
    }

    public static synchronized UserRepository getInstance(IUserRepository local) {
        if (instance == null) {
            instance = new UserRepository(local);
        }
        return instance;
    }

    @Override
    public void saveUser(User user) {
        this.local.saveUser(user);
        refreshCache(user);
    }

    @Override
    public void updateUser(User user) {
        this.local.updateUser(user);
        refreshCache(user);
    }

    @Override
    public void removeUser(User user) {
        this.local.removeUser(user);
        if (cache != null) {
            this.cache.remove(user.getId());
        }
    }

    @Override
    public void findUserById(String userId, SingleCallback<User> callback) {
        // Attempt to find in cache first
        if (isCacheValid()) {
            User found = cache.get(userId);
            if (found != null) {
                callback.onAvailable(found);
                return;
            }
        }

        // Find in the database and then add to cache
        this.local.findUserById(userId, new SingleCallback<User>() {
            @Override
            public void onAvailable(User value) {
                refreshCache(value);
                callback.onAvailable(value);
            }

            @Override
            public void onNotAvailable(Exception ex) {
                callback.onNotAvailable(ex);
            }
        });
    }

    @Override
    public void findAllUsers(ListCallback<User> callback) {
        // Attempt to find in cache first
        if (isCacheValid()) {
            callback.onAvailable(new LinkedList<>(cache.values()));
            return;
        }

        // Find in the database and then add to cache
        this.local.findAllUsers(new ListCallback<User>() {
            @Override
            public void onAvailable(List<User> values) {
                refreshCache(values);
                callback.onAvailable(values);
            }

            @Override
            public void onNotAvailable(Exception ex) {
                callback.onNotAvailable(ex);
            }
        });
    }

    private void refreshCache(User user) {
        if (cache == null) {
            this.cache = new LinkedHashMap<>();
        }
        if (invalidCache) {
            this.cache.clear();
        }
        this.cache.put(user.getId(), user);
    }

    private void refreshCache(List<User> users) {
        if (cache == null) {
            this.cache = new LinkedHashMap<>();
        }
        if (invalidCache) {
            this.cache.clear();
        }
        for (User user : users) {
            this.cache.put(user.getId(), user);
        }
    }

    private boolean isCacheValid() {
        return !(invalidCache || cache == null || cache.isEmpty());
    }
}