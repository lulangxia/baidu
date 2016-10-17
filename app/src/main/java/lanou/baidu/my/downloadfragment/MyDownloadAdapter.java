package lanou.baidu.my.downloadfragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by dllo on 16/10/17.
 */
public class MyDownloadAdapter extends FragmentPagerAdapter {
    Context context;
    ArrayList<Fragment> mFragments;
    ArrayList<String> titles = new ArrayList<>();

    public void setFragments(ArrayList<Fragment> fragments) {
        mFragments = fragments;
        titles.add("已下载");
        titles.add("正在下载");
    }

    public MyDownloadAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments == null ? 0 : mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
