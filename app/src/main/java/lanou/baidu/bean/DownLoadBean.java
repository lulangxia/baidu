package lanou.baidu.bean;

/**
 * Created by dllo on 16/10/17.
 */
public class DownLoadBean {
    private long id;
    private String song;
    private String singer;
    private String musicuri;
    private String songid;
    private int duration;

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getSongid() {
        return songid;
    }

    public void setSongid(String songid) {
        this.songid = songid;
    }

    public DownLoadBean(String song, String singer, String musicuri) {
        this.song = song;
        this.singer = singer;
        this.musicuri = musicuri;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public DownLoadBean() {
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getMusicuri() {
        return musicuri;
    }

    public void setMusicuri(String musicuri) {
        this.musicuri = musicuri;
    }
}
