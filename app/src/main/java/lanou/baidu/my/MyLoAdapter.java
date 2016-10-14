package lanou.baidu.my;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by dllo on 16/10/13.
 */
public class MyLoAdapter extends FragmentPagerAdapter {
    Context context;
    ArrayList<Fragment> arrayList;
    ArrayList<String> titles = new ArrayList<>();

    public void setArrayList(ArrayList<Fragment> arrayList) {
        this.arrayList = arrayList;
        titles.add("歌曲");
        titles.add("文件夹");
        titles.add("歌手");
        titles.add("专辑");
    }

    public MyLoAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public int getCount() {
        return arrayList == null ? 0 : arrayList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
