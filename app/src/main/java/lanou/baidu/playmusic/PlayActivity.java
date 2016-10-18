package lanou.baidu.playmusic;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;

import lanou.baidu.R;
import lanou.baidu.TestFragment;
import lanou.baidu.base.BaseAty;
import lanou.baidu.base.MyApp;
import lanou.baidu.bean.DownLoadBean;
import lanou.baidu.bean.PlayBean;
import lanou.baidu.database.DBTools;
import lanou.baidu.eventbus.NextorLast;
import lanou.baidu.eventbus.PlayMode;
import lanou.baidu.main.MainActivity;
import lanou.baidu.service.PlayService;

public class PlayActivity extends BaseAty {


    private ImageView backlast;
    private ImageView nextsong;
    private ImageView lastsong;
    private CheckBox playorpause;
    private TextView playtime;
    private TextView playing;
    private SeekBar seekBar;
    private PlayService.MusicBinder musicBinder;
    private Intent playservice;
    private PlayConnection connection;
    private MyBroadcastReceiver myBroadcastReceiver;
    private ViewPager vp;
    private NextorLast nol;
    private Integer next;
    private Integer last;
    private ImageView mode;
    private ImageView more;
    private int flag;
    private PlayMode playMode;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor shareEt;
    private ImageView share;
    private ImageView download;

    private PlayBean playBean;
    private DBTools mDBTools;


    @Override
    protected int setLayout() {
        return R.layout.activity_play;
    }

    @Override
    protected void initView() {
        vp = bindView(R.id.vp_player);
        seekBar = bindView(R.id.seekbar_player);
        playing = bindView(R.id.playing_player);
        playtime = bindView(R.id.alltime_player);
        playorpause = bindView(R.id.playorpause_player);
        lastsong = bindView(R.id.last_player);
        nextsong = bindView(R.id.next_player);
        backlast = bindView(R.id.return_player);
        mode = bindView(R.id.mode_player);
        more = bindView(R.id.moresong_player);

        share = bindView(R.id.share_player);
        download = bindView(R.id.download_player);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void sentText(PlayBean playbean) {
        this.playBean = playbean;
        Log.d("PlayActivity", "aaa");
    }

    @Override
    protected void initData() {
        mDBTools = new DBTools(this);

        EventBus.getDefault().register(this);
        sharedPreferences = getSharedPreferences("playmode", MODE_PRIVATE);
        shareEt = sharedPreferences.edit();

        flag = sharedPreferences.getInt("musicplaymode", 0);
        Log.d("PlayActivity", "flag:" + flag);


        if (flag % 4 == 0) {
            mode.setImageResource(R.mipmap.bt_list_order_normal);
        } else if (flag % 4 == 1) {
            mode.setImageResource(R.mipmap.bt_list_button_roundplay_normal);
        } else if (flag % 4 == 2) {
            mode.setImageResource(R.mipmap.bt_list_random_normal);
        } else if (flag % 4 == 3) {
            mode.setImageResource(R.mipmap.bt_list_roundsingle_normal);
        }


        final PlayerAdapter playerAdapter = new PlayerAdapter(getSupportFragmentManager(), this);
        final ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new TestFragment());
        fragments.add(new PlayMainFragment());
        fragments.add(new PlayThirdFragment());

        playerAdapter.setFragmentList(fragments);
        vp.setAdapter(playerAdapter);
        vp.setCurrentItem(1);
        vp.setOffscreenPageLimit(2);
        vp.setPageTransformer(true, new ViwePagerAni());
        nol = new NextorLast();
        next = nol.Next();
        last = nol.Last();


        Intent intent1 = getIntent();
        int time = intent1.getIntExtra("time", 0);
        playtime.setText(changetime(time / 1000));


        String positionnow = sharedPreferences.getString("nowtime", "0:00");
        playing.setText(positionnow);

        final boolean playing = intent1.getBooleanExtra("playorpause", false);
        playorpause.setChecked(!playing);


        myBroadcastReceiver = new MyBroadcastReceiver();


        playservice = new Intent(this, PlayService.class);
        connection = new PlayConnection();
        startService(playservice);
        this.bindService(playservice, connection, this.BIND_AUTO_CREATE);


        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<DownLoadBean> downLoadBeanArrayList = mDBTools.queryALLDB();

                if (playBean != null && playBean.getBitrate() != null && playBean.getBitrate().getFile_link() != null) {
                    DownloadManager downloadManager = (DownloadManager) MyApp.getMcontext().getSystemService(Context.DOWNLOAD_SERVICE);
                    //Log.d("Tools", playBean.getBitrate().getFile_link());
                    for (int i = 0; i <downLoadBeanArrayList.size() ; i++) {
                        Log.d("PlayActivity", "aaasscas");
                        Log.d("PlayActivity", playBean.getSonginfo().getSong_id());
                        Log.d("PlayActivity", downLoadBeanArrayList.get(i).getSongid());

                        if(playBean.getSonginfo().getSong_id().equals(downLoadBeanArrayList.get(i).getSongid())){
                            Log.d("PlayActivity", "aaasscasdasdas");
                            Toast.makeText(PlayActivity.this, "该歌曲已下载", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    Log.d("PlayActivity", "aaaddd");
                    Uri mDownloadUri = Uri.parse(playBean.getBitrate().getFile_link());
                    DownloadManager.Request request = new DownloadManager.Request(mDownloadUri);
                    File folder = new File("/sdcard/AAAAAAAA");
                    if (!(folder.exists() && folder.isDirectory())) {
                        folder.mkdirs();
                    }
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_MUSIC,
                            playBean.getSonginfo().getTitle() + "-" + playBean.getSonginfo().getAuthor() + ".mp3");
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

                    long downloadId = downloadManager.enqueue(request);
                    DownLoadBean downLoadBean = new DownLoadBean();
                    downLoadBean.setId(downloadId);
                    downLoadBean.setSinger(playBean.getSonginfo().getAuthor());
                    downLoadBean.setSong(playBean.getSonginfo().getTitle());
                    downLoadBean.setMusicuri(playBean.getBitrate().getFile_link());
                    downLoadBean.setSongid(playBean.getSonginfo().getSong_id());
                    downLoadBean.setDuration(playBean.getBitrate().getFile_duration()*1000);
                    mDBTools.insertDB(downLoadBean);
                }
            }
        });


        playorpause.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isChecked = musicBinder.isPlaying();
                if (isChecked) {
                    musicBinder.stopPlay();
                } else {
                    musicBinder.restartMusic();
                }
            }
        });


        nextsong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(next);
                Log.d("PlayActivity", "next");
                Log.d("PlayActivity", "next:" + next);
                playorpause.setChecked(false);
            }
        });

        lastsong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(last);
                Log.d("PlayActivity", "last:" + last);
                Log.d("PlayActivity", "last");
                playorpause.setChecked(false);
            }
        });


        backlast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(PlayActivity.this, MainActivity.class);
                intent2.putExtra("playing", musicBinder.isPlaying());
                Log.d("PlayActivity", "musicBinder.isPlaying():" + musicBinder.isPlaying());
                setResult(100, intent2);
                finish();
                overridePendingTransition(R.anim.activityoutplay, R.anim.alha);
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    musicBinder.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        playMode = new PlayMode();
        mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag % 4 == 0) {
                    mode.setImageResource(R.mipmap.bt_list_button_roundplay_normal);

                    EventBus.getDefault().post(playMode.XUNHUAN());
                    Toast.makeText(PlayActivity.this, "循环播放", Toast.LENGTH_SHORT).show();
                } else if (flag % 4 == 1) {
                    mode.setImageResource(R.mipmap.bt_list_random_normal);
                    Toast.makeText(PlayActivity.this, "随机播放", Toast.LENGTH_SHORT).show();

                    EventBus.getDefault().post(playMode.SUIJI());
                } else if (flag % 4 == 2) {
                    mode.setImageResource(R.mipmap.bt_list_roundsingle_normal);
                    Toast.makeText(PlayActivity.this, "单曲循环", Toast.LENGTH_SHORT).show();

                    EventBus.getDefault().post(playMode.DANQU());
                } else if (flag % 4 == 3) {
                    mode.setImageResource(R.mipmap.bt_list_order_normal);
                    Toast.makeText(PlayActivity.this, "顺序播放", Toast.LENGTH_SHORT).show();

                    EventBus.getDefault().post(playMode.SHUNXU());
                }
                flag = flag + 1;//其余得到循环执行上面3个不同的功能
                shareEt.putInt("musicplaymode", flag);
                shareEt.commit();
                Log.d("PlayActivity11", "flag:" + flag);
            }
        });

        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //        showPopupWindow();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction("jindu");
        registerReceiver(myBroadcastReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myBroadcastReceiver);
        unbindService(connection);
        EventBus.getDefault().unregister(this);
    }

    public static String changetime(int time) {
        if (time <= 0) {
            return "0:00";
        }
        int minute = (time) / 60;
        int seconds = (time) % 60;
        String m = String.valueOf(minute);
        String s = seconds >= 10 ? String.valueOf(seconds) : "0" + String.valueOf(seconds);
        return m + ":" + s;
    }


    class PlayConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            musicBinder = (PlayService.MusicBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }

    class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            int progress = intent.getIntExtra("progress", 0);
            int position = intent.getIntExtra("position", 0);
            int duration = intent.getIntExtra("duration", 0);
            seekBar.setProgress(progress);
            String realtiming = changetime(position / 1000);
            playing.setText(realtiming);
            shareEt.putString("nowtime", realtiming);

            shareEt.commit();
            String realtime = changetime(duration / 1000);
            //Log.d("PlayActivity", realtime);
            playtime.setText(realtime);

        }

    }



}



