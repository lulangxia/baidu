package lanou.baidu.eventBus;

import java.util.List;

/**
 * Created by dllo on 16/10/11.
 */
public class MyMusicBean {

    boolean LOCAL;

    public boolean isLOCAL() {
        return LOCAL;
    }

    public void setLOCAL(boolean LOCAL) {
        this.LOCAL = LOCAL;
    }

    List<MusicBean> musicBeen;
    int position;

    public List<MusicBean> getMusicBeen() {
        return musicBeen;
    }

    public void setMusicBeen(List<MusicBean> musicBeen) {
        this.musicBeen = musicBeen;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
