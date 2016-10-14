package lanou.baidu.eventBus;

/**
 * Created by dllo on 16/10/8.
 */
public class SendSongMessage {
    private String song;
    private String singer;
    private String imgurl;
    private int time;
    private String lyrics;
    private String playurl;
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getPlayurl() {
        return playurl;
    }

    public void setPlayurl(String playurl) {
        this.playurl = playurl;
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    public SendSongMessage(String song, String singer, String imgurl, String lyrics, int time, int position) {
        this.song = song;
        this.singer = singer;
        this.imgurl = imgurl;
        this.lyrics = lyrics;
        this.time = time;
        this.position = position;
    }

    public SendSongMessage(String song, String singer, String imgurl, String lyrics, int time) {
        this.song = song;
        this.singer = singer;
        this.imgurl = imgurl;
        this.lyrics = lyrics;
        this.time = time;

    }

    public SendSongMessage(String song, String singer, String imgurl, String lyrs) {
        this.song = song;
        this.singer = singer;
        this.imgurl = imgurl;
        this.lyrics = lyrs;
    }



    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public SendSongMessage(String song, String singer, String imgurl) {
        this.song = song;
        this.singer = singer;
        this.imgurl = imgurl;

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

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }
}
