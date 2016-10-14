package lanou.baidu.my;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import lanou.baidu.R;
import lanou.baidu.base.BaseFragment;
import lanou.baidu.main.MainActivity;
import lanou.baidu.testFragment;

/**
 * Created by dllo on 16/10/12.
 */
public class MylocalFragment extends BaseFragment {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TextView returnback;

    @Override
    protected int setLayout() {
        return R.layout.mylocalfragent;
    }

    @Override
    protected void initView() {
        viewPager = bindView(R.id.vp_mylocal);
        tabLayout = bindView(R.id.tab_mylocal);
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new MySongFragment());
        fragments.add(new testFragment());
        fragments.add(new testFragment());
        fragments.add(new testFragment());
        MyLoAdapter myLoAdapter = new MyLoAdapter(getChildFragmentManager(), getContext());
        myLoAdapter.setArrayList(fragments);
        viewPager.setAdapter(myLoAdapter);
        tabLayout.setupWithViewPager(viewPager);
        returnback = bindView(R.id.return_mylocalmusic);
    }

    @Override
    protected void initData() {
        returnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.retrnfrag(MylocalFragment.this);
            }
        });


    }
}
