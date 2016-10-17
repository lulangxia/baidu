package lanou.baidu.my.downloadfragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import lanou.baidu.R;
import lanou.baidu.base.BaseFragment;
import lanou.baidu.main.MainActivity;
import lanou.baidu.TestFragment;

/**
 * Created by dllo on 16/10/17.
 */
public class DownloadFragment extends BaseFragment {

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
        fragments.add(new HasDownFragment());
        fragments.add(new TestFragment());

        MyDownloadAdapter myDownloadAdapter = new MyDownloadAdapter(getChildFragmentManager(), getContext());
        myDownloadAdapter.setFragments(fragments);
        viewPager.setAdapter(myDownloadAdapter);
        tabLayout.setupWithViewPager(viewPager);
        returnback = bindView(R.id.return_mylocalmusic);

    }

    @Override
    protected void initData() {
        returnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.retrnfrag(DownloadFragment.this);
            }
        });
    }
}
