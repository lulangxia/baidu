package lanou.baidu.my.localfragment;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import lanou.baidu.R;
import lanou.baidu.base.BaseFragment;
import lanou.baidu.bean.MusicBean;
import lanou.baidu.bean.MyMusicBean;

/**
 * Created by dllo on 16/10/13.
 */
public class MySongFragment extends BaseFragment {

    private ListView listView;
    private ArrayList<MusicBean> arrayList;
    private MyMusicBean myMusicBean;


    @Override
    protected int setLayout() {

        return R.layout.mymusicfragment;
    }

    @Override
    protected void initView() {
        listView = bindView(R.id.mymusic_mulocal);


    }

    @Override
    protected void initData() {
        myMusicBean = new MyMusicBean();
        arrayList = new ArrayList<>();
        ContentResolver cr = mContext.getContentResolver();
        Uri localUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = cr.query(localUri, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            while (cursor.moveToNext()) {
                int indexSongName = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
                int indexSinger = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
                int indexPath = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
                int indexSize = cursor.getColumnIndex(MediaStore.Audio.Media.SIZE);
                int indexDuration = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION);

                String songName = cursor.getString(indexSongName);
                String singer = cursor.getString(indexSinger);
                String uri = cursor.getString(indexPath);
                int duration = cursor.getInt(indexDuration);
                Log.d("MySongFragment", "indexSongName:" + songName);

                MusicBean musicBean = new MusicBean();
                musicBean.setSongName(songName);
                musicBean.setSinger(singer);
                musicBean.setMusicuri(uri);
                musicBean.setDuration(duration);
                arrayList.add(musicBean);


            }
            myMusicBean.setMusicBeen(arrayList);
            myMusicBean.setLOCAL(true);
            MyMyAdapter myMuAdapter = new MyMyAdapter(getContext());
            myMuAdapter.setMyMusicBean(myMusicBean);
            listView.setAdapter(myMuAdapter);
        }
        cursor.close();
        View view = LayoutInflater.from(getContext()).inflate(R.layout.media_foot, null);
        TextView textView = (TextView) view.findViewById(R.id.num_song);
        textView.setText("共" + myMusicBean.getMusicBeen().size() + "首歌曲");
        listView.addFooterView(view);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                myMusicBean.setPosition(position);
                EventBus.getDefault().post(myMusicBean);
            }
        });


    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
