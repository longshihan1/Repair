package com.longshihan.repair.db;

import android.content.Context;

import com.longshihan.repair.db.dao.DaoMaster;
import com.longshihan.repair.db.dao.DaoSession;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * @author Administrator
 * @time 2016/7/26 14:37
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public class DBCore {
    private static final String DEFAULT_DB_NAME = "green_dao.db";
    private static DaoMaster sDaoMaster;
    private static DaoSession sDaoSession;

    private static Context sContext;
    private static String sDbName;

    public static void init(Context context) {
        init(context, DEFAULT_DB_NAME);
    }

    public static void init(Context context, String dbName) {
        if (context == null) {
            throw new IllegalArgumentException("Init DBCore failed, context can't be null.");
        }
        sContext = context.getApplicationContext();
        sDbName = dbName;
    }

    public static DaoMaster getDaoMaster() {
        if (sDaoMaster == null) {
            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(sContext, sDbName, null);
            sDaoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return sDaoMaster;
    }

    public static DaoSession getDaoSession() {
        if (sDaoSession == null) {
            if (sDaoMaster == null) {
                sDaoMaster = getDaoMaster();
            }
            sDaoSession = sDaoMaster.newSession();
        }
        return sDaoSession;
    }

    public static void enableQueryBuilderLog() {
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;
    }
}
