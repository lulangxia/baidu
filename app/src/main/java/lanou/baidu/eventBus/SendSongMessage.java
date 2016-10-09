package lanou.baidu.eventBus;

/**
 * Created by dllo on 16/10/8.
 */
public class SendSongMessage {
    private String song;
    private String singer;
    private String imgurl;

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
