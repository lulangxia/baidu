package lanou.baidu.album.recommend;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import lanou.baidu.R;
import lanou.baidu.base.BaseFragment;
import lanou.baidu.base.URLVlaues;

/**
 * Created by dllo on 16/9/19.
 */
public class RecommendFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecomAdapter recomAdapter;
    private ArrayList<RecomBean> arrayList;

    @Override
    protected int setLayout() {
        return R.layout.recommendfragment;
    }

    @Override
    protected void initView() {
        recyclerView = bindView(R.id.rv_recom);
        swipeRefreshLayout = bindView(R.id.swip_recom);
    }

    @Override
    protected void initData() {
        recomAdapter = new RecomAdapter(getContext());
        arrayList = new ArrayList<>();
        arrayList.add(new RecomBean(URLVlaues.RECOMMAND_MAIN_NEW, 0));
        arrayList.add(new RecomBean(URLVlaues.RECOMMAND_MAIN, 1));
        arrayList.add(new RecomBean(URLVlaues.RECOMMAND_MAIN, 2));
        arrayList.add(new RecomBean(URLVlaues.RECOMMAND_MAIN, 3));
        arrayList.add(new RecomBean(URLVlaues.RECOMMAND_MAIN_NEW, 4));
        arrayList.add(new RecomBean(URLVlaues.RECOMMAND_MAIN, 5));
        arrayList.add(new RecomBean(URLVlaues.RECOMMAND_MAIN, 6));
        arrayList.add(new RecomBean(URLVlaues.RECOMMAND_MAIN, 7));
        arrayList.add(new RecomBean(URLVlaues.RECOMMAND_MAIN, 8));
        arrayList.add(new RecomBean(URLVlaues.RECOMMAND_MAIN_NEW, 9));
        arrayList.add(new RecomBean(URLVlaues.RECOMMAND_MAIN_NEW, 10));


        //设置刷新时动画的颜色，可以设置4个
        //设置刷新时动画的颜色，可以设置4个
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light, android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                arrayList = new ArrayList<>();
                arrayList.add(new RecomBean(URLVlaues.RECOMMAND_MAIN_NEW, 0));
                arrayList.add(new RecomBean(URLVlaues.RECOMMAND_MAIN, 1));
                arrayList.add(new RecomBean(URLVlaues.RECOMMAND_MAIN, 2));
                arrayList.add(new RecomBean(URLVlaues.RECOMMAND_MAIN, 3));
                arrayList.add(new RecomBean(URLVlaues.RECOMMAND_MAIN_NEW, 4));
                arrayList.add(new RecomBean(URLVlaues.RECOMMAND_MAIN, 5));
                arrayList.add(new RecomBean(URLVlaues.RECOMMAND_MAIN, 6));
                arrayList.add(new RecomBean(URLVlaues.RECOMMAND_MAIN, 7));
                arrayList.add(new RecomBean(URLVlaues.RECOMMAND_MAIN, 8));
                arrayList.add(new RecomBean(URLVlaues.RECOMMAND_MAIN_NEW, 9));
                arrayList.add(new RecomBean(URLVlaues.RECOMMAND_MAIN_NEW, 10));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        recomAdapter.setArrayList(arrayList);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);

            }
        });
        recomAdapter.setArrayList(arrayList);
        recyclerView.setAdapter(recomAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);




    }
}
