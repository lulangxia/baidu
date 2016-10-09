package lanou.baidu.musicMedia;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.RemoteViews;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;

import lanou.baidu.R;
import lanou.baidu.base.URLVlaues;
import lanou.baidu.base.VolleySingleton;
import lanou.baidu.eventBus.SendSongMessage;


/**
 * Created by dllo on 16/10/8.
 */
public class PlayService extends Service {
    Context context;
    private MediaPlayer mMediaPlayer;
    private boolean isBind;
    MediaLIstBean mediaLIstBean;
    String Songid;
    ArrayList<String> Songlist;
    private NotificationManager notificationManager;
    private NotificationReceiver notificationReceiver;
    private Handler pictureHandler;
    private Bitmap mBitmap;
    private RemoteViews remo;

    @Override
    public void onCreate() {
        notificationReceiver = new NotificationReceiver();
        IntentFilter fil = new IntentFilter();
        fil.addAction("EXIT");
        fil.addAction("PLAY");
        fil.addAction("NEXT");
        fil.addAction("LAST");
        registerReceiver(notificationReceiver, fil);
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        MainActivity.setPlayOrPause(new MainActivity.Playorpause() {
//            @Override
//            public void playorpause() {
//                if (mMediaPlayer.isPlaying()) {
//                    mMediaPlayer.pause();
//                } else {
//                    mMediaPlayer.start();
//                }
//            }
//        });
        return super.onStartCommand(intent, flags, startId);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return new MusicBinder();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(notificationReceiver);
    }


    private void showNotification(String song, String singer, String url) {

        //获取开启通知的Manager
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.mipmap.icon_tips_login);
        builder.setTicker(song);

        //创建一个RemoteViews
        Log.d("sssss", getPackageName());
        remo = new RemoteViews(getPackageName(), R.layout.notify_custom_music);


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
        Notification not = builder.build();
        not.bigContentView = remo;
        manager.notify(210, not);
    }




    private class NotificationReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case "PLAY":
                    if (mMediaPlayer.isPlaying()) {
                        mMediaPlayer.pause();
                        remo.setImageViewResource(R.id.playorpause_notify, R.mipmap.bt_notificationbar_pause);
                    } else {
                        mMediaPlayer.start();
                        remo.setImageViewResource(R.id.playorpause_notify, R.mipmap.bt_notificationbar_play);
                    }
                    break;
            }
        }
    }

    public class MusicBinder extends Binder {
        public void setsongid(String songid) {
            Songid = songid;
        }
        public void setsonglist(ArrayList<String> songlist) {
            Songlist = songlist;
        }
        public boolean isPlaying() {
            if (mMediaPlayer.isPlaying()) {
                return true;
            } else {
                return false;
            }
        }

        public void stopPlay() {
            if (mMediaPlayer.isPlaying())
                mMediaPlayer.pause();
        }


        public void restartMusic() {
            if (!mMediaPlayer.isPlaying()) {
                mMediaPlayer.start();
            }
        }

        public void play() {
            if (mMediaPlayer == null) {
                mMediaPlayer = new MediaPlayer();
            } else if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.pause();
                mMediaPlayer.stop();
            }
            mMediaPlayer.reset();
            //mMediaPlayer.setOnPreparedListener(this);
            String url = URLVlaues.PLAY_FRONT + Songid + URLVlaues.PLAY_BEHIND;
            Log.d("PlayService", url);
            StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    String urlnew = response.substring(1, response.length() - 2);
                    Gson gson = new Gson();
                    PlayBean playBean = gson.fromJson(urlnew, PlayBean.class);
                    try {
                        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                        Log.d("PlayService", playBean.getBitrate().getFile_link());
                        mMediaPlayer.setDataSource(playBean.getBitrate().getFile_link());
                        mMediaPlayer.prepare();


                        SendSongMessage event = new SendSongMessage(playBean.getSonginfo().getTitle(), playBean.getSonginfo().getAuthor(), playBean.getSonginfo().getPic_big());
                        //发布事件类
                        EventBus.getDefault().post(event);


                        showNotification(playBean.getSonginfo().getTitle(), playBean.getSonginfo().getAuthor(), playBean.getSonginfo().getPic_premium());
                        mMediaPlayer.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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
