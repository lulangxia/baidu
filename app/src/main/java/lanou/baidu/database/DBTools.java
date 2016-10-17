package lanou.baidu.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import lanou.baidu.bean.DownLoadBean;

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

    public void insertDB(DownLoadBean downLoadBean) {
        ContentValues values = new ContentValues();
        values.put(DBValues.TABLE_RECORD_SONGNAME, downLoadBean.getSong());
        values.put(DBValues.TABLE_RECORD_SINGER, downLoadBean.getSinger());
        values.put(DBValues.TABLE_RECORD_SONGURL, downLoadBean.getMusicuri());
        values.put(DBValues.TABLE_RECORD_SONGID, downLoadBean.getSongid());
        values.put(DBValues.TABLE_RECORD_DURATION, downLoadBean.getDuration());
        database.insert(DBValues.TABLE_DOWNLOAD, null, values);

    }

    public ArrayList<DownLoadBean> queryALLDB() {
        ArrayList<DownLoadBean> downLoadBeanArrayList = new ArrayList<>();
        cursor = database.query(DBValues.TABLE_DOWNLOAD, null, null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                DownLoadBean downLoadBean = new DownLoadBean();
                downLoadBean.setSong(cursor.getString(cursor.getColumnIndex(DBValues.TABLE_RECORD_SONGNAME)));
                downLoadBean.setSinger(cursor.getString(cursor.getColumnIndex(DBValues.TABLE_RECORD_SINGER)));
                downLoadBean.setMusicuri(cursor.getString(cursor.getColumnIndex(DBValues.TABLE_RECORD_SONGURL)));
                downLoadBean.setSongid(cursor.getString(cursor.getColumnIndex(DBValues.TABLE_RECORD_SONGID)));
                downLoadBean.setDuration(cursor.getInt(cursor.getColumnIndex(DBValues.TABLE_RECORD_DURATION)));
                downLoadBeanArrayList.add(downLoadBean);
            }
        }
        return downLoadBeanArrayList;
    }


}
