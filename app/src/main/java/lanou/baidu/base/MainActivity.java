package lanou.baidu.base;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import lanou.baidu.R;
import lanou.baidu.album.AlbumFragment;
import lanou.baidu.eventBus.SendSongMessage;
import lanou.baidu.musicMedia.PlayService;
import lanou.baidu.my.MyFragment;
import lanou.baidu.testFragment;

public class MainActivity extends BaseAty {


    private ViewPager viewPager;
    private TabLayout tabLayout;
    private static FragmentManager manager;
    private ImageView headimg, next, list;
    private TextView song, singer;
    private CheckBox playrpause;
    private MediaPlayer player;
    RelativeLayout relativeLayout;

    private PlayService.MusicBinder musicBinder;
    private Intent mIntent;


    @Override
    protected int setLayout() {
        EventBus.getDefault().register(this);
        return R.layout.activity_main;
    }


    @Override
    protected void initView() {
        tabLayout = bindView(R.id.tb_main);
        viewPager = bindView(R.id.vp_main);
        headimg = bindView(R.id.img_media);
        song = bindView(R.id.song_media);
        singer = bindView(R.id.singer_media);
        playrpause = bindView(R.id.start_pause_media);
        next = bindView(R.id.next_media);
        list = bindView(R.id.list_media);
        relativeLayout = bindView(R.id.title_main);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void sentText(SendSongMessage sendSongMessage) {
        String getsong = sendSongMessage.getSong();
        String getsinger = sendSongMessage.getSinger();
        String geturl = sendSongMessage.getImgurl();
        MyImageLoader.myImageLoader(geturl, headimg);
        song.setText(getsong);
        singer.setText(getsinger);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void initData() {
        manager = getSupportFragmentManager();
        ArrayList<Fragment> arrayList = new ArrayList<>();
        arrayList.add(new MyFragment());
        arrayList.add(new AlbumFragment());
        arrayList.add(new testFragment());
        arrayList.add(new testFragment());


        MainAdapter mainadapter = new MainAdapter(getSupportFragmentManager());
        mainadapter.setArrayList(arrayList);
        viewPager.setAdapter(mainadapter);
        tabLayout.setupWithViewPager(viewPager);
//        playrpause.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (musicBinder.isPlaying()) {
//                    musicBinder.stopPlay();
//                } else {
//                    musicBinder.restartMusic();
//                }
//            }
//        });

        playrpause.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isChecked = musicBinder.isPlaying();
                if (isChecked){
                    musicBinder.stopPlay();
                }else {
                    musicBinder.restartMusic();
                }
            }
        });
        mIntent = new Intent(this, PlayService.class);
        startService(mIntent);


    }

    public static void replacefrag(Fragment fragment) {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(R.anim.activityin, R.anim.activityout);
        transaction.replace(R.id.replace, fragment);
        transaction.commit();

    }

    public static void retrnfrag(Fragment fragment) {

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(R.anim.activityin, R.anim.activityout);
        transaction.remove(fragment);
        transaction.commit();

    }


    class PlayConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("SmsConnection", "onServiceConnected");
            musicBinder = (PlayService.MusicBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }

}
