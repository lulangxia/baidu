package lanou.baidu.my.downloadfragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import java.util.ArrayList;

import lanou.baidu.R;
import lanou.baidu.base.BaseFragment;
import lanou.baidu.testFragment;

/**
 * Created by dllo on 16/10/17.
 */
public class DownloadFragment extends BaseFragment{

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TextView returnback;

    @Override
    protected int setLayout() {
        return R.layout.downloadfragment;
    }

    @Override
    protected void initView() {
        viewPager = bindView(R.id.vp_download);
        tabLayout = bindView(R.id.tab_download);
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new testFragment());
        fragments.add(new testFragment());

        MyDownloadAdapter myDownloadAdapter = new MyDownloadAdapter(getChildFragmentManager(),getContext());
//        myLoAdapter.setArrayList(fragments);
//       viewPager.setAdapter(myLoAdapter);
        tabLayout.setupWithViewPager(viewPager);
        returnback = bindView(R.id.return_mylocalmusic);

    }

    @Override
    protected void initData() {

    }
}
