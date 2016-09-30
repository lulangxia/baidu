package lanou.baidu.base;

import android.media.MediaPlayer;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import lanou.baidu.R;
import lanou.baidu.album.AlbumFragment;
import lanou.baidu.my.MyFragment;
import lanou.baidu.testFragment;

public class MainActivity extends BaseAty {


    private ViewPager viewPager;
    private TabLayout tabLayout;
    private static FragmentManager manager;
    private ImageView headimg, next, list;
    private TextView song, singer;
    private CheckBox playrpause;
    private MediaPlayer player;
    RelativeLayout relativeLayout;

    @Override
    protected int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        tabLayout = bindView(R.id.tb_main);
        viewPager = bindView(R.id.vp_main);
        headimg = bindView(R.id.img_media);
        song = bindView(R.id.song_media);
        singer = bindView(R.id.singer_media);
        playrpause = bindView(R.id.start_pause_media);
        next = bindView(R.id.next_media);
        list = bindView(R.id.list_media);
        relativeLayout = bindView(R.id.title_main);
    }

    @Override
    protected void initData() {
        manager = getSupportFragmentManager();
        ArrayList<Fragment> arrayList = new ArrayList<>();
        arrayList.add(new MyFragment());
        arrayList.add(new AlbumFragment());
        arrayList.add(new testFragment());
        arrayList.add(new testFragment());


        MainAdapter mainadapter = new MainAdapter(getSupportFragmentManager());
        mainadapter.setArrayList(arrayList);
        viewPager.setAdapter(mainadapter);
        tabLayout.setupWithViewPager(viewPager);



    }

    public static void replacefrag(Fragment fragment) {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(R.anim.activityin, R.anim.activityout);
        transaction.replace(R.id.replace, fragment);
        transaction.commit();

    }

    public static void retrnfrag(Fragment fragment) {

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(R.anim.activityin, R.anim.activityout);
        transaction.remove(fragment);
        transaction.commit();

    }


}
