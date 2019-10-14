package com.e.finalexamtestapp.Database;

import android.provider.BaseColumns;

public final class UsersMaster {

    //Developers Link
    //https://developer.android.com/training/data-storage/sqlite

    private UsersMaster() {}

    /* Inner class that defines the table contents */
    public static class Users implements BaseColumns {

        public static final String TABLE_USER = "Users";
        public static final String USER_TABLE_COLUMN_NAME_ID = "user_id";
        public static final String USER_TABLE_COLUMN_NAME_USERNAME = "user_name";
        public static final String USER_TABLE_COLUMN_NAME_PASSWORD = "user_password";
        public static final String USER_TABLE_COLUMN_NAME_GENDER = "user_gender";
        public static final String USER_TABLE_COLUMN_NAME_TYPE = "user_type";
        public static final String USER_TABLE_COLUMN_NAME_STAR = "user_star";
    }
}
