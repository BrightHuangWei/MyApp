package com.huangwei.myapp.business.database;

import android.content.Context;

import org.greenrobot.greendao.database.Database;

public class DatabaseManager {

    private DaoSession mDaoSession = null;
    private UserProfileDao mUserProfileDao = null;

    private DatabaseManager() {
    }

    public DatabaseManager init(Context context) {
        initDao(context);
        return this;
    }

    private static final class Holder {
        private static final DatabaseManager INSTANCE = new DatabaseManager();
    }

    public static DatabaseManager getInstance() {
        return Holder.INSTANCE;
    }

    private void initDao(Context context) {
        final ReleaseOpenHelper helper = new ReleaseOpenHelper(context, "my_app.db");
        final Database db = helper.getWritableDb();
        mDaoSession = new DaoMaster(db).newSession();
        mUserProfileDao = mDaoSession.getUserProfileDao();
    }

    public final UserProfileDao getUserProfileDao() {
        return mUserProfileDao;
    }
}
