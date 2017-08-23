package repositories;

/**
 * Copyright 2017 Tyler Suehr
 * Created by tyler on 8/23/2017.
 */
public final class DatabaseContract {
    public static abstract class Users {
        public static final String NAME = "users";
        public static final String COL_ID = "userId";
        public static final String COL_FIRST_NAME = "userFirstName";
        public static final String COL_LAST_NAME = "userLastName";
        public static final String COL_USERNAME = "userUsername";
    }
}