package lanou.baidu.tools;

/**
 * Created by dllo on 16/5/23.
 */
public class URLVlaues {
    public static final String RECOMMAND_MAIN_ALL = "http://tingapi.ting.baidu.com/v1/restserver/ting?from=android&version=5.9.0.0&channel=1413c&operator=1&method=baidu.ting.plaza.index&cuid=8ADCB33F64DBB1F5314036551C922491";
    public static final String RECOMMAND_MAIN_NEW = "http://tingapi.ting.baidu.com/v1/restserver/ting?from=android&version=5.9.0.0&channel=1413c&operator=1&method=baidu.ting.plaza.index&cuid=8ADCB33F64DBB1F5314036551C922491%20HTTP/1.1cuid:%208ADCB33F64DBB1F5314036551C922491";
    public static final String RECOMMAND_MAIN = "http://url.cn/28a65ZG";
    public static final String RECOMMAND_CAROUSE = "http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.plaza.getFocusPic&format=json&from=ios&version=5.2.3&from=ios&channel=appstore";
    public static final String RECOMMAND_HOT = "http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.diy.getHotGeDanAndOfficial&num=4&version=5.2.3&from=ios&channel=appstore";
    public static final String MUSICSTORE_TOP = "http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.billboard.billCategory&format=json&from=ios&version=5.2.1&from=ios&channel=appstore";
    public static final String ALL_SONGLIST = "http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.diy.gedan&page_no=1&page_size=30&from=ios&version=5.2.3&from=ios&channel=appstore";
    public static final String SONGLIST_DETAIL_Front = "http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.diy.gedanInfo&from=ios&listid=";
    public static final String SONGLIST_DETAIL_BEHIND = "&format=json&offset=0&size=50&from=ios&fields=title,song_id,author,resource_type,havehigh,is_new,has_mv_mobile,album_title,ting_uid,album_id,charge,all_rate&version=5.2.1&from=ios&channel=appstore";
    public static final String TOP_SONG_FRONT = "http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.billboard.billList&type=";
    public static final String TOP_SONG_BEHIND = "&format=json&offset=0&from=ios&fields=title,song_id,author,resource_type,havehigh,is_new,has_mv_mobile,album_title,ting_uid,album_id,charge,all_rate&version=5.2.1&from=ios&channel=appstore";
    public static final String RECOMMAND_CAROUSE_SONG_FRONT = "http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.album.getAlbumInfo&album_id=";
    public static final String RECOMMAND_CAROUSE_SONG_BEHIND = "&format=json&from=ios&version=5.2.5&from=ios&channel=appstore";
    public static final String PLAY_FRONT = "http://tingapi.ting.baidu.com/v1/restserver/ting?from=webapp_music&method=baidu.ting.song.play&format=json&callback=&songid=";
    public static final String PLAY_BEHIND = "&_=1413017198449";
    public static final String SCENCE = "http://tingapi.ting.baidu.com/v1/restserver/ting?from=android&version=5.9.0.0&channel=1413c&operator=1&method=baidu.ting.scene.getCategoryScene&category_id=";
    //http://tingapi.ting.baidu.com/v1/restserver/ting?from=android&version=5.9.0.0&channel=1413c&operator=1&method=baidu.ting.ugcdiy.getBaseInfo
    // http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.ugcdiy.getBaseInfo&from=android&listid=354518970&version=5.9.0.0&channel=1413c&operator=1
    // http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.diy.gedanInfo&&from=android&listid=354518970&version=5.9.0&from=ios&channel=1413c
    //http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.diy.gedanInfo&from=ios&listid=5717&version=5.2.3&from=ios&channel=appstore
    public static final String SONGLIST_HOT = "http://tingapi.ting.baidu.com/v1/restserver/ting?from=android&version=5.9.0.0&channel=1413c&operator=1&method=baidu.ting.ugcdiy.getChanneldiy&param=WRZ7j5ADhYE2v%2FhAlCEMZJuRtGFeSHxB840ojko%2BVB3M958LQOsDjNPKT8JoHm9ckU%2BVzNAwTxM71nmU81LatTzNKU7xNSo95v3Whi%2Fx13yos1LbiWUzKHCue3iuop7J&timestamp=1474614530&sign=83866625313e32a12e8c9bbb7677cff5";
    public static final String HOT_SINGER = "http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.artist.getList&format=json&order=1&limit=12&offset=0&area=0&sex=0&abc=&from=ios&version=5.2.1&from=ios&channel=appstore";

    public static final String SONGLIST_NEW = "http://tingapi.ting.baidu.com/v1/restserver/ting?from=android&version=5.9.0.0&channel=1413c&operator=1&method=baidu.ting.ugcdiy.getChanneldiy&param=W%2BiDuPq2yxc8wGDCVz7TPOiKkuHyOjYEPlgwbQ3%2FZ4VSxCySIEXMorEKwts9lPNpqqk2VsJtlN664uU27R4vz2NQ%2BYy17fKd1wMJ%2FDrQVpAWeEvzDktR%2FJdLlW%2BsGxna&timestamp=1474614518&sign=ff2cc05fc2063921014aada124a26c8b";

    public static String ALL_SONGLIST_REFRESH(int i) {
        String ALL_SONGLIST_REFRESH = "http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.diy.gedan&page_no=" + i + "&page_size=30&from=ios&version=5.2.3&from=ios&channel=appstore";
        return ALL_SONGLIST_REFRESH;
    }

    public static final String VIDEO_HOT = "http://tingapi.ting.baidu.com/v1/restserver/ting?from=android&version=5.9.0.0&channel=1413c&operator=1&provider=11%2C12&method=baidu.ting.mv.searchMV&format=json&order=0&page_num=1&page_size=20&query=%E5%85%A8%E9%83%A8";
    public static final String VIDEO_NEW = "http://tingapi.ting.baidu.com/v1/restserver/ting?from=android&version=5.9.0.0&channel=1413c&operator=1&provider=11%2C12&method=baidu.ting.mv.searchMV&format=json&order=1&page_num=1&page_size=20&query=%E5%85%A8%E9%83%A8";

    public static String VIDEO_LOAD(int j, int i) {
        String VIDEO_LOAD_URL = "http://tingapi.ting.baidu.com/v1/restserver/ting?from=android&version=5.9.0.0&channel=1413c&operator=1&provider=11%2C12&method=baidu.ting.mv.searchMV&format=json&order=" + j + "&page_num=" + i + "&page_size=20&query=%E5%85%A8%E9%83%A8";
        return VIDEO_LOAD_URL;
    }
    public static final int SHUNXU = 701;

    public static final int XUNHUAN = 702;

    public static final int SUIJI = 703;

    public static final int DANQU = 704;

}
