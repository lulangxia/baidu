package lanou.baidu.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import lanou.baidu.eventbus.MusicBean;

/**
 * Created by dllo on 16/10/15.
 */
public class DBTools {
    private SQLiteDatabase database;
    private Cursor cursor;

    public DBTools(Context context) {
        DataHelpter helper = new DataHelpter(context, DBValues.SQL_SONG, null, 1);
        database = helper.getWritableDatabase();
    }

    public void deleteSelectDB(String whereClause, String[] whereArgs) {
        database.delete(DBValues.TABLE_DOWNLOAD, whereClause + "= ?", whereArgs);
    }


    public void deleteAllDB() {
        database.delete(DBValues.TABLE_DOWNLOAD, null, null);
    }

    public void insertDB(MusicBean my) {
        ContentValues values = new ContentValues();
        values.put(DBValues.TABLE_RECORD_SONGNAME, my.getSongName());
        values.put(DBValues.TABLE_RECORD_SINGER, my.getSinger());
        values.put(DBValues.TABLE_RECORD_SONGURL, my.getMusicuri());
        database.insert(DBValues.TABLE_DOWNLOAD, null, values);

    }

    public ArrayList<MusicBean> queryALLDB() {
        ArrayList<MusicBean> musicBeanArrayList = new ArrayList<>();
        cursor = database.query(DBValues.TABLE_DOWNLOAD, null, null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                MusicBean musicBean = new MusicBean();
                musicBean.setSongName(cursor.getString(cursor.getColumnIndex(DBValues.TABLE_RECORD_SONGNAME)));
                musicBean.setSinger(cursor.getString(cursor.getColumnIndex(DBValues.TABLE_RECORD_SINGER)));
                musicBean.setMusicuri(cursor.getString(cursor.getColumnIndex(DBValues.TABLE_RECORD_SONGURL)));
                musicBeanArrayList.add(musicBean);
            }
        }
        return musicBeanArrayList;
    }


}
