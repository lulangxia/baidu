package lanou.baidu.replacefragment;

import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;

import lanou.baidu.R;
import lanou.baidu.base.BaseFragment;
import lanou.baidu.base.GsonRequest;
import lanou.baidu.main.MainActivity;
import lanou.baidu.base.URLVlaues;
import lanou.baidu.base.VolleySingleton;

/**
 * Created by dllo on 16/9/26.
 */
public class SingerFragment extends BaseFragment {

    private ListView listView;
    private ViewPager viewPager;
    private HotVpAdapter hotVpAdapter;
    private TextView textView;

    @Override
    protected int setLayout() {
        return R.layout.singer_fragment;
    }

    @Override
    protected void initView() {
        listView = bindView(R.id.singer_list);
        View headView = LayoutInflater.from(getContext()).inflate(R.layout.singerfrag_head, listView,false);
        viewPager = bindView(R.id.vp_singerhot, headView);
        listView.addHeaderView(headView);
        View footView = LayoutInflater.from(getContext()).inflate(R.layout.footview, listView,false);
        listView.addFooterView(footView);
        textView = bindView(R.id.return_singerhot);
    }

    @Override
    protected void initData() {

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.retrnfrag(SingerFragment.this);
            }
        });

        hotVpAdapter = new HotVpAdapter(getContext());
        GsonRequest<HotSIngerBean> gsonRequestvp = new GsonRequest<>(URLVlaues.HOT_SINGER, HotSIngerBean.class,
                new Response.Listener<HotSIngerBean>() {
                    @Override
                    public void onResponse(HotSIngerBean response) {
                        ArrayList<HotSIngerBean> arrayList = new ArrayList<>();
                        arrayList.add(response);
                        hotVpAdapter.setArrayList(arrayList);
                        viewPager.setAdapter(hotVpAdapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.getMessage();
            }
        });
        VolleySingleton.getInstance().addRequest(gsonRequestvp);


        HotSingerAdapter hotSingerAdapter = new HotSingerAdapter(getContext());
        ArrayList<String> strings = new ArrayList<>();
        strings.add("华语男歌手");
        strings.add("华语女歌手");
        strings.add("华语组合");
        strings.add("欧美男歌手");
        strings.add("欧美女歌手");
        strings.add("欧美组合");
        strings.add("韩国男歌手");
        strings.add("韩国女歌手");
        strings.add("韩国组合");
        strings.add("日本男歌手");
        strings.add("日本女歌手");
        strings.add("日本组合");
        strings.add("其他歌手");
        hotSingerAdapter.setArrayList(strings);
        listView.setAdapter(hotSingerAdapter);
    }



}
