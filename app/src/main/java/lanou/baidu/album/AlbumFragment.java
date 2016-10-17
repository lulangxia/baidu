package lanou.baidu.album;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;

import java.util.ArrayList;

import lanou.baidu.R;
import lanou.baidu.album.rank.RankFragment;
import lanou.baidu.album.recommend.RecommendFragment;
import lanou.baidu.album.songlist.SongListFragment;
import lanou.baidu.album.video.VideoFragment;
import lanou.baidu.base.BaseFragment;
import lanou.baidu.TestFragment;

/**
 * Created by dllo on 16/9/19.
 */
public class AlbumFragment extends BaseFragment {

    private TabLayout tabLayout;
    private static ViewPager viewPager;
    private static FragmentManager manager;
    private LinearLayout linearLayout;

    @Override
    protected int setLayout() {
        return R.layout.albumfragment;
    }

    @Override
    protected void initView() {
        tabLayout = bindView(R.id.tb_album);
        viewPager = bindView(R.id.vp_album);
        linearLayout = bindView(R.id.recom_frag);
    }

    @Override
    protected void initData() {
        manager = getChildFragmentManager();
        ArrayList<Fragment> arrayList = new ArrayList<>();

        arrayList.add(new RecommendFragment());
        arrayList.add(new SongListFragment());
        arrayList.add(new RankFragment());
        arrayList.add(new VideoFragment());
        arrayList.add(new TestFragment());

        AlbumAdapter albumadapter = new AlbumAdapter(getChildFragmentManager());
        albumadapter.setArrayList(arrayList);
        viewPager.setAdapter(albumadapter);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public static void replacefrag() {
        AlbumFragment.viewPager.setCurrentItem(1);
    }
}
