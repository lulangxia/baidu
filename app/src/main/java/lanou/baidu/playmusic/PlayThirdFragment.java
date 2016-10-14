package lanou.baidu.playmusic;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import lanou.baidu.R;
import lanou.baidu.base.BaseFragment;
import lanou.baidu.eventBus.SendSongMessage;
import lanou.baidu.main.PlayService;

/**
 * Created by dllo on 16/10/12.
 */
public class PlayThirdFragment extends BaseFragment {

    private LrcView mLrcView;
    private MyBroadcastReceiver mReceiver;
    //更新歌词的频率，每秒更新一次
    private int mPalyTimerDuration = 1000;
    //更新歌词的定时器
    private Timer mTimer;
    //更新歌词的定时任务
    private TimerTask mTask;

    private Handler handler;
    private String lrc;
    private ILrcBuilder builder;

    private PlayService.MusicBinder musicBinder;
    private Intent service;
    private PlayConnection connection;
    private int progress;
    private int position;
    private String getlrc1;

    @Override
    protected int setLayout() {
        return R.layout.playthridfragment;
    }

    @Override
    protected void initView() {
        mLrcView = bindView(R.id.lrc_play);
    }

    @Override
    protected void initData() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        service = new Intent(getContext(), PlayService.class);
        connection = new PlayConnection();
        getContext().startService(service);
        getContext().bindService(service, connection, getContext().BIND_AUTO_CREATE);


        mReceiver = new MyBroadcastReceiver();

        Intent getIntent = getActivity().getIntent();
        getlrc1 = getIntent.getStringExtra("lrc");
        builder = new DefaultLrcBuilder();

        if (getlrc1 != null) {
            //解析歌词返回LrcRow集合
            getlrc(getlrc1);
            handler = new Handler(new Handler.Callback() {
                @Override
                public boolean handleMessage(Message msg) {

                    if (msg.what == 102) {
                        lrc = (String) msg.obj;


                        List<LrcRow> rows = builder.getLrcRows(lrc);
                        //将得到的歌词集合传给mLrcView用来展示
                        mLrcView.setLrc(rows);
                    }
                    return false;
                }
            });
        }
        //设置自定义的LrcView上下拖动歌词时监听
        mLrcView.setListener(new ILrcViewListener() {
            //当歌词被用户上下拖动的时候回调该方法,从高亮的那一句歌词开始播放
            public void onLrcSeeked(int newPosition, LrcRow row) {
                Log.d("PlayThirdFragment", "row.time:" + row.time);
                musicBinder.seekToLrc((int) (row.time));
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        IntentFilter filter = new IntentFilter();
        filter.addAction("jindu");
        mContext.registerReceiver(mReceiver, filter);
    }

    class MyBroadcastReceiver extends BroadcastReceiver {

        private int duration;

        @Override
        public void onReceive(Context context, Intent intent) {

            progress = intent.getIntExtra("progress", 0);
            position = intent.getIntExtra("position", 0);
            duration = intent.getIntExtra("duration", 0);
            //滚动歌词
            mLrcView.seekLrcToTime(position);


        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);



    }


    @Override
    public void onPause() {
        super.onPause();
        getContext().unregisterReceiver(mReceiver);
        getContext().unbindService(connection);

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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void sentText(SendSongMessage sendSongMessage) {
        getlrc1 = sendSongMessage.getLyrics();
        Log.d("PlayThirdFragment", getlrc1);
        if (getlrc1 != null) {
            getlrc(getlrc1);
            handler = new Handler(new Handler.Callback() {
                @Override
                public boolean handleMessage(Message msg) {
                    if (msg.what == 102) {
                        lrc = (String) msg.obj;
                        List<LrcRow> rows = builder.getLrcRows(lrc);
                        Log.d("PlayThirdFragment", lrc);
                        //将得到的歌词集合传给mLrcView用来展示
                        mLrcView.setLrc(rows);

                    }
                    return false;
                }
            });
        }
        //设置自定义的LrcView上下拖动歌词时监听
        mLrcView.setListener(new ILrcViewListener() {
            //当歌词被用户上下拖动的时候回调该方法,从高亮的那一句歌词开始播放
            public void onLrcSeeked(int newPosition, LrcRow row) {
                Log.d("PlayThirdFragment", "row.time:" + row.time);
                musicBinder.seekToLrc((int) row.time);
            }
        });
    }


    public void getlrc(final String path) {
        new Thread(new Runnable() {
            public void run() {
                String result = new String();
                try {
                    URL url = new URL(path);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    int code = connection.getResponseCode();
                    if (HttpURLConnection.HTTP_OK == code) {
                        InputStream inputStream = connection.getInputStream();
                        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                        String line = "";
                        while ((line = bufferedReader.readLine()) != null) {
                            if (line.trim().equals(""))
                                continue;
                            result += line + "\r\n";
                        }

                        bufferedReader.close();
                        inputStreamReader.close();
                        inputStream.close();
                        connection.disconnect();

                        Log.d("PlayThirdFragment", "Thread.currentThread():" + Thread.currentThread());

                    }
                } catch (MalformedURLException e) {
                    // TODO: handle exception
                } catch (IOException e) {
                    // TODO: handle exception
                }

                Message msg = new Message();
                msg.what = 102;
                msg.obj = result;
                if(msg!=null) {
                    handler.sendMessage(msg);
                }
            }
        }).start();
    }


}

