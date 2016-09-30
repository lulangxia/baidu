package lanou.baidu.album;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by dllo on 16/9/19.
 */
public class AlbumAdapter extends FragmentPagerAdapter {
    Context mcontext;
    ArrayList<Fragment> arrayList = new ArrayList<>();
    ArrayList<String> titles = new ArrayList<>();

    public void setArrayList(ArrayList<Fragment> arrayList) {
        this.arrayList = arrayList;
        titles.add("推荐");
        titles.add("歌单");
        titles.add("榜单");
        titles.add("视频");
        titles.add("K歌");
    }

    public AlbumAdapter(FragmentManager fm) {
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
