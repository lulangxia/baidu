package lanou.baidu.musicMedia;

import android.media.MediaPlayer;

/**
 * Created by dllo on 16/10/9.
 */
public class PlayerSingleton {

    private static PlayerSingleton mPlayerSingleton;
    private MediaPlayer mMediaPlayer;

    private PlayerSingleton() {
        mMediaPlayer = new MediaPlayer();
    }

    public static PlayerSingleton getInstance() {

        if (mPlayerSingleton == null) {
            synchronized (PlayerSingleton.class) {
                if (mPlayerSingleton == null) {
                    mPlayerSingleton = new PlayerSingleton();
                }
            }
        }
        return mPlayerSingleton;

    }

    public MediaPlayer getmMediaPlayer() {
        return mMediaPlayer;
    }
}

