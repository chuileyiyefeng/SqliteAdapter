package baoza.sqliteadapter;

import android.content.Context;

/**
 * Created by PanYi on 2017/3/2.
 */

public class DbMangager {
    private static MySqliteOpenHelper mySqliteOpenHelper;

    public static MySqliteOpenHelper getMyOpenHelper(Context context) {
        if (mySqliteOpenHelper == null) {
            mySqliteOpenHelper = new MySqliteOpenHelper(context);
        }
        return mySqliteOpenHelper;
    }
}
