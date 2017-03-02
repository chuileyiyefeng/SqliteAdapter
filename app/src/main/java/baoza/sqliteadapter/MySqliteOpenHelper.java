package baoza.sqliteadapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import baoza.sqliteadapter.bean.Constant;

/**
 * Created by PanYi on 2017/3/2.
 */

public class MySqliteOpenHelper extends SQLiteOpenHelper {

    public MySqliteOpenHelper(Context context) {
        super(context, Constant.NAME, null, Constant.Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL = "create table person(_id integer primary key,name varchar(10),age Integer)";
        db.execSQL(SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
