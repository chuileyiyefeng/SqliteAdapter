package baoza.sqliteadapter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import baoza.sqliteadapter.bean.Constant;

//Sqlite与Listview结合显示
public class MainActivity extends AppCompatActivity {
    Button sql_insert, bt_query;
    ListView listview;
    private MySqliteOpenHelper mySqliteOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sql_insert = (Button) findViewById(R.id.insert);
        bt_query = (Button) findViewById(R.id.query);
        listview = (ListView) findViewById(R.id.listview);
        mySqliteOpenHelper = DbMangager.getMyOpenHelper(this);
        sql_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase database = mySqliteOpenHelper.getWritableDatabase();
//                dataBase使用事务操作，增加效率
//                1.数据库显式开启事务
                database.beginTransaction();
                for (int i = 0; i < 30; i++) {
                    database.execSQL("insert into person(_id,name,age) values(?,?,?)", new Object[]{i, "张三", i + 2});
                }
//                2.提交当前事务
                database.setTransactionSuccessful();
//                3.关闭事务
                database.endTransaction();
                database.close();
            }
        });
        bt_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase database = mySqliteOpenHelper.getWritableDatabase();
                Cursor cursor = database.rawQuery("select*from person", null);
                while (cursor.moveToNext()) {
                    int id_cusor = cursor.getColumnIndex(Constant._ID);
                    int _id = cursor.getInt(id_cusor);
                    String name = cursor.getString(cursor.getColumnIndex(Constant.NAME));
                    int age = cursor.getInt(cursor.getColumnIndex(Constant.AGE));
                    Log.e("query", "onClick: " + _id + " " + name + " " + age);
                }
                cursor.close();
                database.close();
            }
        });
        SQLiteDatabase database = mySqliteOpenHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("select*from person", null);
        MySqlAdatper cursorAdapter = new MySqlAdatper(this, cursor, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        listview.setAdapter(cursorAdapter);
    }

    class MySqlAdatper extends CursorAdapter {
        Context context;

        public MySqlAdatper(Context context, Cursor c, int flags) {
            super(context, c, flags);
            this.context = context;
        }

        //            cursor:数据源cursor对象
//            parent：当前item父布局
        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            View v = LayoutInflater.from(context).inflate(R.layout.sqlitem, null);
            return v;
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            TextView tv_id = (TextView) view.findViewById(R.id.id);
            TextView tv_name = (TextView) view.findViewById(R.id.name);
            TextView tv_age = (TextView) view.findViewById(R.id.age);
            if (cursor != null) {
                tv_id.setText(cursor.getInt(cursor.getColumnIndex(Constant._ID)) + "");
                tv_name.setText(cursor.getString(cursor.getColumnIndex(Constant.NAME)) + "");
                tv_age.setText(cursor.getInt(cursor.getColumnIndex(Constant.AGE)) + "");
            }
        }
    }
}
