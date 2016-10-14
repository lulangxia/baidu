package lanou.baidu.main;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.RemoteViews;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;

import lanou.baidu.eventBus.MusicBean;
import lanou.baidu.eventBus.NextorLast;
import lanou.baidu.eventBus.SendSongMessage;
import lanou.baidu.musicMedia.PlayBean;
import lanou.baidu.musicMedia.PlayerSingleton;


/**
 * Created by dllo on 16/10/8.
 */
public class PlayService extends Service {
    Context context;
    private MediaPlayer mMediaPlayer;
    private boolean isBind;

    String Songid;
    ArrayList<String> Songlist;


    private Bitmap mBitmap;
    private RemoteViews remo;
    private NotificationManager manager;
    private Notification not;
    private Intent sendIntent;
    private PlayBean playBean;
    private MusicBean musicBean;

    @Override
    public void onCreate() {
        sendIntent = new Intent("jindu");
        super.onCreate();

        mMediaPlayer = PlayerSingleton.getInstance().getmMediaPlayer();
        //设置歌曲播放完事之后的监听
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                NextorLast nol = new NextorLast();
                Integer next = nol.Next();
                EventBus.getDefault().post(next);
            }
        });

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
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
    }


    public void play(MusicBean musicBean) {
        if (mMediaPlayer == null) {
            return;
        } else if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            mMediaPlayer.stop();
        }
        mMediaPlayer.reset();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        try {
            mMediaPlayer.setDataSource(musicBean.getMusicuri());
            mMediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        SendSongMessage event = new SendSongMessage(musicBean.getSongName(), musicBean.getSinger(), musicBean.getImgurl(),
                musicBean.getLrcurl(),mMediaPlayer.getDuration(),mMediaPlayer.getCurrentPosition());
        EventBus.getDefault().post(event);


        mMediaPlayer.start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (mMediaPlayer.isPlaying()) {
                        //获取当前歌曲播放进度
                        int position = mMediaPlayer.getCurrentPosition();
                        int duration = mMediaPlayer.getDuration();
                        int progress = (int) (position * 100f / duration);

                        sendIntent.putExtra("progress", progress);
                        sendIntent.putExtra("position", position);//在MainJumpLurocsFragment里接收,用于设置歌词的滚动
                        sendIntent.putExtra("boolean", mMediaPlayer.isPlaying());
                        sendIntent.putExtra("duration", duration);  //发送总时间用于设置seekbar的时间
                        sendBroadcast(sendIntent);
                    }
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        ).start();
    }


    private void seekTo(int progress) {
        //获取当前播放歌曲的总时长
        int duration = mMediaPlayer.getDuration();
        //计算出进度条拖动的比例
        float scale = progress / 100f;
        //得出歌曲应该播放的位置
        int position = (int) (duration * scale);
        mMediaPlayer.seekTo(position);
    }

    private void seeLrcTo(int time) {
        mMediaPlayer.seekTo(time);
    }


    public class MusicBinder extends Binder {
        public void setPlaybean(PlayBean playbean1) {
            playBean = playbean1;
        }

        public void setMusicBean(MusicBean musicBean1) {
            musicBean = musicBean1;
        }

        public void setsongid(String songid) {
            Songid = songid;
        }

        public void setsonglist(ArrayList<String> songlist) {
            Songlist = songlist;
        }


        public boolean isPlaying() {
            if (mMediaPlayer != null) {
                if (mMediaPlayer.isPlaying()) {
                    return true;
                }
            }
            return false;
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

        public void playMusic() {
            play(musicBean);
        }

        public void seekTo(int progress) {
            PlayService.this.seekTo(progress);
        }

        public void seekToLrc(int time) {
            PlayService.this.seeLrcTo(time);
        }

    }
}
