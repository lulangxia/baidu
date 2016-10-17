package lanou.baidu.bean;

/**
 * Created by dllo on 16/9/20.
 */
public class RecomBean {
    int type;
    String url;

    public void setType(int type) {
        this.type = type;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public RecomBean(String url, int type) {
        this.url = url;
        this.type = type;
    }

    public RecomBean() {
    }
}
