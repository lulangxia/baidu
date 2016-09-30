package lanou.baidu.base;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;

import lanou.baidu.R;
import lanou.baidu.album.AlbumFragment;
import lanou.baidu.testFragment;

public class MainActivity extends BaseAty {


    private ViewPager viewPager;
    private TabLayout tabLayout;
    private static FragmentManager manager;

    @Override
    protected int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        tabLayout = bindView(R.id.tb_main);
        viewPager = bindView(R.id.vp_main);
    }

    @Override
    protected void initData() {
        manager = getSupportFragmentManager();
        ArrayList<Fragment> arrayList = new ArrayList<>();
        arrayList.add(new testFragment());
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
        transaction.setCustomAnimations(R.anim.activityin,R.anim.activityout );
        transaction.replace(R.id.replace,fragment);
        transaction.commit();

    }

    public static void retrnfrag(Fragment fragment) {

            FragmentTransaction transaction = manager.beginTransaction();
            transaction.setCustomAnimations(R.anim.activityin, R.anim.activityout);
            transaction.remove(fragment);
            transaction.commit();

    }



}
