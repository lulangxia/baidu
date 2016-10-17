package lanou.baidu.bean;

/**
 * Created by dllo on 16/10/11.
 */
public class MusicBean {

    private String songName;
    private String singer;
    private String songid;
    private int duration;

    private String lrcurl;
    private String musicuri;
    private String imgurl;

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getLrcurl() {
        return lrcurl;
    }

    public void setLrcurl(String lrcurl) {
        this.lrcurl = lrcurl;
    }

    public String getMusicuri() {
        return musicuri;
    }

    public void setMusicuri(String musicuri) {
        this.musicuri = musicuri;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public MusicBean() {
    }

    public MusicBean(String songName, String singer, String songid) {
        this.songName = songName;
        this.singer = singer;
        this.songid = songid;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getSongid() {
        return songid;
    }

    public void setSongid(String songid) {
        this.songid = songid;
    }
}
