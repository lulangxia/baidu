package lanou.baidu.playmusic;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
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

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import lanou.baidu.main.MainActivity;
import lanou.baidu.main.PlayService;
import lanou.baidu.R;
import lanou.baidu.base.BaseAty;
import lanou.baidu.eventBus.NextorLast;
import lanou.baidu.eventBus.PlayMode;
import lanou.baidu.testFragment;

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

    }

    @Override
    protected void initData() {
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


        PlayerAdapter playerAdapter = new PlayerAdapter(getSupportFragmentManager(), this);
        final ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new testFragment());
        fragments.add(new PlayMainFragment());
        fragments.add(new PlayThirdFragment());

        playerAdapter.setFragmentList(fragments);
        vp.setAdapter(playerAdapter);
        vp.setCurrentItem(1);
        nol = new NextorLast();
        next = nol.Next();
        last = nol.Last();


        Intent intent1 = getIntent();
        int time = intent1.getIntExtra("time", 0);
        playtime.setText(changetime(time / 1000));

        String positionnow = sharedPreferences.getString("nowtime", "0:00");
        playing.setText(positionnow);

        boolean playing = intent1.getBooleanExtra("playorpause", false);
        playorpause.setChecked(!playing);


        myBroadcastReceiver = new MyBroadcastReceiver();


        playservice = new Intent(this, PlayService.class);
        connection = new PlayConnection();
        startService(playservice);

        this.bindService(playservice, connection, this.BIND_AUTO_CREATE);

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
                } else if (flag % 4 == 1) {
                    mode.setImageResource(R.mipmap.bt_list_random_normal);

                    EventBus.getDefault().post(playMode.SUIJI());
                } else if (flag % 4 == 2) {
                    mode.setImageResource(R.mipmap.bt_list_roundsingle_normal);

                    EventBus.getDefault().post(playMode.DANQU());
                } else if (flag % 4 == 3) {
                    mode.setImageResource(R.mipmap.bt_list_order_normal);

                    EventBus.getDefault().post(playMode.SHUNXU());
                }
                flag = flag + 1;//其余得到循环执行上面3个不同的功能
                shareEt.putInt("musicplaymode", flag);
                shareEt.commit();
                Log.d("PlayActivity11", "flag:" + flag);
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


    private void showPopupWindow() {

//        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.popup, null);
//        RelativeLayout re = (RelativeLayout) contentView.findViewById(R.id.pop_pup);
//        Button button = (Button) re.findViewById(R.id.btn_pop);
//        Button button2 = (Button) re.findViewById(R.id.btn_serv);
//        //View parent = LayoutInflater.from(getContext()).inflate(R.layout.smslayout, null);
//        //View title = parent.findViewById(R.id.sms_title);
//        final PopupWindow mPopWindow = new PopupWindow(contentView, RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.MATCH_PARENT, true);
//        mPopWindow.setBackgroundDrawable(new BitmapDrawable());
//        mPopWindow.setFocusable(true);
//        mPopWindow.setOutsideTouchable(true);
//        //mPopWindow.setContentView(contentView);
//        //mPopWindow.showAtLocation(parent, Gravity.RIGHT, 0, -400);
//        mPopWindow.showAsDropDown(inview);


//        contentView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (mPopWindow.isShowing()) {
//                    mPopWindow.dismiss();
//                }
//            }
//        });
    }


}
