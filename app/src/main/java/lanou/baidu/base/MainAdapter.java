package lanou.baidu.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by dllo on 16/9/19.
 */
public class MainAdapter extends FragmentPagerAdapter {
    ArrayList<Fragment> arrayList = new ArrayList<>();
    ArrayList<String> titles = new ArrayList<>();

    public void setArrayList(ArrayList<Fragment> arrayList) {
        this.arrayList = arrayList;
        titles.add("我的");
        titles.add("音乐");
        titles.add("动态");
        titles.add("直播");
    }

    public MainAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
