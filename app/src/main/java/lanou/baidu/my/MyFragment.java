package lanou.baidu.my;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import lanou.baidu.R;
import lanou.baidu.base.BaseFragment;
import lanou.baidu.main.MainActivity;
import lanou.baidu.eventBus.MusicBean;
import lanou.baidu.eventBus.MyMusicBean;

/**
 * Created by dllo on 16/9/30.
 */
public class MyFragment extends BaseFragment {

    private RelativeLayout mylocalMusic;
    private ImageView mylocalImage;
    private TextView textView;
    private ImageView imageView;
    private MyMusicBean myMusicBean;
    private ArrayList<MusicBean> arrayList;

    @Override
    protected int setLayout() {
        return R.layout.myfragment;
    }

    @Override
    protected void initView() {
        textView = bindView(R.id.num_local_my);
        imageView = bindView(R.id.img_my_playlocal);
        mylocalMusic = bindView(R.id.relativeLayout_my_local);
        mylocalImage = bindView(R.id.img_my_localMusic);
    }

    @Override
    protected void initData() {
        findSong();
        if (myMusicBean.getMusicBeen() != null) {
            textView.setText(myMusicBean.getMusicBeen().size() + "首");
        }
        mylocalMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.replacefrag(new MylocalFragment());
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myMusicBean.setPosition(0);
                EventBus.getDefault().post(myMusicBean);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    public void findSong() {
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
        }
    }
}
