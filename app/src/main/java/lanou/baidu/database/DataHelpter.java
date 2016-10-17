package lanou.baidu.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by dllo on 16/10/15.
 */
public class DataHelpter extends SQLiteOpenHelper {
    public DataHelpter(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + DBValues.TABLE_DOWNLOAD + "(id integer primary key autoincrement,"
                + DBValues.TABLE_RECORD_SONGNAME + " text,"
                + DBValues.TABLE_RECORD_SINGER + " text,"
                + DBValues.TABLE_RECORD_SONGID + " text,"
                + DBValues.TABLE_RECORD_DURATION + " integer,"
                + DBValues.TABLE_RECORD_SONGURL + " text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
