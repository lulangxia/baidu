package lanou.baidu.main;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Random;

import lanou.baidu.R;
import lanou.baidu.album.AlbumFragment;
import lanou.baidu.base.BaseAty;
import lanou.baidu.base.MyImageLoader;
import lanou.baidu.base.URLVlaues;
import lanou.baidu.base.VolleySingleton;
import lanou.baidu.eventBus.MusicBean;
import lanou.baidu.eventBus.MyMusicBean;
import lanou.baidu.eventBus.SendSongMessage;
import lanou.baidu.musicMedia.PlayBean;
import lanou.baidu.my.MyFragment;
import lanou.baidu.playmusic.PlayActivity;
import lanou.baidu.testFragment;

public class MainActivity extends BaseAty {


    private ViewPager viewPager;
    private TabLayout tabLayout;
    private static FragmentManager manager;
    private ImageView headimg, next, list;
    private CheckBox playrpause;
    private TextView song, singer;
    private MediaPlayer player;
    private RelativeLayout relativeLayout;


    private PlayService.MusicBinder musicBinder;
    private Intent service;
    private PlayConnection connection;
    private LinearLayout linearLayout;
    private SharedPreferences sp;
    private SharedPreferences.Editor spET;
    private String getsong;
    private String getsinger;
    private String geturl;
    private int position;
    private MyMusicBean myMusicBean;
    private NotificationReceiver notificationReceiver;
    private RemoteViews remo;
    private NotificationManager notificationManager;
    private Notification not;
    private String getlrc;
    private String getsongsp;
    private String getsingersp;
    private String geturlsp;
    private String getlrcsp;
    private int gettime;
    private int gettimesp;
    private int playmode;

    private boolean Local = false;


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
        linearLayout = bindView(R.id.media_actions);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMusicBean(MyMusicBean myMusicBean) {
        this.myMusicBean = myMusicBean;
        position = myMusicBean.getPosition();
        playSong();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void sentText(SendSongMessage sendSongMessage) {
        getsong = sendSongMessage.getSong();
        getsinger = sendSongMessage.getSinger();
        geturl = sendSongMessage.getImgurl();
        getlrc = sendSongMessage.getLyrics();
        gettime = sendSongMessage.getTime();

        spET.putString("song", getsong);
        spET.putString("singer", getsinger);
        spET.putString("img", geturl);
        spET.putString("lrc", getlrc);
        spET.putInt("time", gettime);
        spET.commit();

        MyImageLoader.myImageLoader(geturl, headimg);
        song.setText(getsong);
        singer.setText(getsinger);
        playrpause.setChecked(true);

        getsongsp = sp.getString("song", null);
        getsingersp = sp.getString("singer", null);
        geturlsp = sp.getString("img", null);
        getlrcsp = sp.getString("lrc", null);
        gettimesp = sp.getInt("time", 0);

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getNoLNext(Integer i) {
        switch (i) {
            case 100:
                playNext();
                break;
            case 200:
                playLast();
                break;
            case 701:
                playmode = 0;
                spET.putInt("mplaymode", playmode);
                break;
            case 702:
                playmode = 702;
                spET.putInt("mplaymode", playmode);
                break;
            case 703:
                playmode = 703;
                spET.putInt("mplaymode", playmode);
                break;
            case 704:
                playmode = 704;
                spET.putInt("mplaymode", playmode);
                break;
        }
        spET.commit();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (connection != null) {
            unbindService(connection);
        }
        unregisterReceiver(notificationReceiver);
    }

    @Override
    protected void initData() {
        notificationReceiver = new NotificationReceiver();
        IntentFilter fil = new IntentFilter();
        fil.addAction("EXIT");
        fil.addAction("PLAY");
        fil.addAction("NEXT");
        fil.addAction("LAST");
        registerReceiver(notificationReceiver, fil);


        sp = getSharedPreferences("shared", MODE_PRIVATE);
        spET = sp.edit();
        getsongsp = sp.getString("song", null);
        getsingersp = sp.getString("singer", null);
        geturlsp = sp.getString("img", null);
        getlrcsp = sp.getString("lrc", null);
        gettimesp = sp.getInt("time", 0);


        MyImageLoader.myImageLoader(geturlsp, headimg);
        song.setText(getsongsp);
        singer.setText(getsingersp);
        playrpause.setChecked(false);

        manager = getSupportFragmentManager();
        ArrayList<Fragment> arrayList = new ArrayList<>();
        arrayList.add(new MyFragment());
        arrayList.add(new AlbumFragment());
        arrayList.add(new testFragment());
        arrayList.add(new testFragment());


        MainAdapter mainadapter = new MainAdapter(getSupportFragmentManager());
        mainadapter.setArrayList(arrayList);
        viewPager.setAdapter(mainadapter);
        viewPager.setCurrentItem(1);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setSelectedTabIndicatorColor(Color.TRANSPARENT);
        tabLayout.setTabTextColors(0xffDCDCDC, Color.WHITE);

        playrpause.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isChecked = musicBinder.isPlaying();
                if (isChecked == false) {
                    musicBinder.restartMusic();
                } else {
                    musicBinder.stopPlay();
                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playNext();
            }
        });

        service = new Intent(this, PlayService.class);
        connection = new PlayConnection();
        startService(service);
        this.bindService(service, connection, this.BIND_AUTO_CREATE);


        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentplay = new Intent(MainActivity.this, PlayActivity.class);
                intentplay.putExtra("img", geturlsp);
                intentplay.putExtra("song", getsongsp);
                intentplay.putExtra("singer", getsingersp);
                intentplay.putExtra("lrc", getlrcsp);
                intentplay.putExtra("time", gettimesp);
                intentplay.putExtra("playorpause", musicBinder.isPlaying());
                startActivityForResult(intentplay, 200);
                overridePendingTransition(R.anim.activityinplay, R.anim.activityoutplay);
            }
        });


        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (200 == requestCode && 100 == resultCode) {
            boolean play = data.getBooleanExtra("playing", true);
            playrpause.setChecked(play);
            if (play) {
                musicBinder.restartMusic();
            } else {
                musicBinder.stopPlay();
            }
        }
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


    public void playSong() {

        if (myMusicBean.isLOCAL()) {
            MusicBean musicbean = new MusicBean();
            musicbean = myMusicBean.getMusicBeen().get(position);
            musicBinder.setMusicBean(musicbean);
            showNotification(musicbean.getSongName(), musicbean.getSinger(), musicbean.getImgurl());
            musicBinder.playMusic();
        } else {
            String url = URLVlaues.PLAY_FRONT + myMusicBean.getMusicBeen().get(position).getSongid() + URLVlaues.PLAY_BEHIND;

            if (url != null) {
                StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String urlnew = response.substring(1, response.length() - 2);

                        Gson gson = new Gson();
                        PlayBean playBean = gson.fromJson(urlnew, PlayBean.class);
                        MusicBean musicbean = new MusicBean();
                        musicbean.setSongName(playBean.getSonginfo().getTitle());
                        musicbean.setSinger(playBean.getSonginfo().getAuthor());
                        musicbean.setImgurl(playBean.getSonginfo().getPic_big());
                        musicbean.setLrcurl(playBean.getSonginfo().getLrclink());
                        musicbean.setMusicuri(playBean.getBitrate().getFile_link());

                        musicBinder.setMusicBean(musicbean);
                        showNotification(musicbean.getSongName(), musicbean.getSinger(), musicbean.getImgurl());
                        musicBinder.playMusic();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.getMessage();
                    }
                });
                VolleySingleton.getInstance().addRequest(stringRequest);
            }
        }
    }

    private void showNotification(String song, String singer, final String url) {

        //获取开启通知的Manager
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.mipmap.icon_tips_login);
        builder.setTicker(song);

        //创建一个RemoteViews
        remo = new RemoteViews(getPackageName(), R.layout.notify_custom_music);
        ImageLoader.getInstance().loadImage(url, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                super.onLoadingComplete(imageUri, view, loadedImage);
                remo.setImageViewBitmap(R.id.img_notify, loadedImage);
                if (not != null) {
                    notificationManager.notify(210, not);
                }
            }
        });

        remo.setTextViewText(R.id.tv_title_notify, song);
        remo.setTextViewText(R.id.tv_singer_notify, singer);

//        设置自定义通知栏内button的点击事件
        Intent notificationIntentexit = new Intent("EXIT");
        PendingIntent pendingIntentexit = PendingIntent.getBroadcast(this, 0, notificationIntentexit, PendingIntent.FLAG_UPDATE_CURRENT);
        remo.setOnClickPendingIntent(R.id.exit_notify, pendingIntentexit);

        Intent notificationIntentlast = new Intent("LAST");
        PendingIntent pendingIntentlast = PendingIntent.getBroadcast(this, 0, notificationIntentlast, PendingIntent.FLAG_UPDATE_CURRENT);
        remo.setOnClickPendingIntent(R.id.last_notify, pendingIntentlast);

        Intent notificationIntentnext = new Intent("NEXT");
        PendingIntent pendingIntentnext = PendingIntent.getBroadcast(this, 0, notificationIntentnext, PendingIntent.FLAG_UPDATE_CURRENT);
        remo.setOnClickPendingIntent(R.id.next_notify, pendingIntentnext);

        Intent notificationIntentplay = new Intent("PLAY");
        PendingIntent pendingIntentplay = PendingIntent.getBroadcast(this, 0, notificationIntentplay, PendingIntent.FLAG_UPDATE_CURRENT);
        remo.setOnClickPendingIntent(R.id.playorpause_notify, pendingIntentplay);


        builder.setContent(remo);
        not = builder.build();
        not.bigContentView = remo;
        notificationManager.notify(210, not);
    }

    public void playNext() {
        Log.d("MainActivity", "next");
        if (myMusicBean == null) {
            return;
        }
        int newplaymode = sp.getInt("mplaymode", 0);
        switch (newplaymode) {
            case URLVlaues.XUNHUAN:
                if (position == myMusicBean.getMusicBeen().size() - 1) {
                    position = 0;
                } else {
                    position++;
                }
                break;
            case URLVlaues.SUIJI:
                position = new Random().nextInt(myMusicBean.getMusicBeen().size() - 1);
                break;
            case URLVlaues.DANQU:

                break;
            default:
                if (position == myMusicBean.getMusicBeen().size() - 1) {
                    return;
                } else {
                    position++;
                }
                break;
        }

        playSong();
    }

    public void playLast() {
        Log.d("MainActivity", "last");
//        if (myMusicBean == null || position == 0) {
//            return;
//        }
//        position--;


        if (myMusicBean == null) {
            return;
        }
        switch (playmode) {
            case URLVlaues.XUNHUAN:
                if (position == 0) {
                    position = myMusicBean.getMusicBeen().size() - 1;
                } else {
                    position--;
                }
                break;
            case URLVlaues.SUIJI:

                position = new Random().nextInt(myMusicBean.getMusicBeen().size() - 1);

                break;
            case URLVlaues.DANQU:

                break;
            default:
                if (position == 0) {
                    return;
                } else {
                    position--;
                }
                break;
        }
        playSong();

    }

    private class NotificationReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case "PLAY":
                    if (musicBinder.isPlaying()) {
                        musicBinder.stopPlay();
                        remo.setImageViewResource(R.id.playorpause_notify, R.mipmap.bt_notificationbar_pause);
                    } else {
                        musicBinder.restartMusic();
                        remo.setImageViewResource(R.id.playorpause_notify, R.mipmap.bt_notificationbar_play);
                    }
                    break;
                case "NEXT":
                    playNext();
                    break;
                case "LAST":
                    playLast();
                    break;
                case "EXIT":
                    notificationManager.cancelAll();
                    break;
            }
        }
    }

    private void showPopupWindow() {

        View contentView = LayoutInflater.from(this).inflate(R.layout.popwindow, null);
//        RelativeLayout re = (RelativeLayout) contentView.findViewById(R.id.pop_pup);
//        Button button = (Button) re.findViewById(R.id.btn_pop);
//        Button button2 = (Button) re.findViewById(R.id.btn_serv);
//        //View parent = LayoutInflater.from(getContext()).inflate(R.layout.smslayout, null);
//        //View title = parent.findViewById(R.id.sms_title);
        final PopupWindow mPopWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
//        mPopWindow.setBackgroundDrawable(new BitmapDrawable());

        ListView listView = (ListView) contentView.findViewById(R.id.list_popwin);

//        ArrayList<MusicBean> arraylist = new ArrayList<>();
//        for (int i = 0; i < myMusicBean.getMusicBeen().size(); i++) {
//            MusicBean musicBean = new MusicBean();
//            musicBean.setSongName(myMusicBean.getMusicBeen().get(i).getSongName());
//            musicBean.setSinger(myMusicBean.getMusicBeen().get(i).getSinger());
//            musicBean.setSongid(myMusicBean.getMusicBeen().get(i).getSongid());
//            arraylist.add(musicBean);
//        }
        //   mymusicBean.setMusicBeen(arraylist);
        PopAdapter popadapter = new PopAdapter(this);
        popadapter.setMyMusicBean(myMusicBean);
        listView.setAdapter(popadapter);


        ImageView ima = (ImageView) contentView.findViewById(R.id.back_popwin);

        ima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mPopWindow.isShowing()) {
                    mPopWindow.dismiss();
                }
            }

        });

        mPopWindow.setFocusable(true);
        mPopWindow.setOutsideTouchable(true);
        mPopWindow.setAnimationStyle(R.style.animationup);
        mPopWindow.setContentView(contentView);

//        //mPopWindow.showAtLocation(parent, Gravity.RIGHT, 0, -400);
        mPopWindow.showAtLocation(LayoutInflater.from(this).inflate(R.layout.activity_main, null), Gravity.BOTTOM, 0, 0);


    }
}



