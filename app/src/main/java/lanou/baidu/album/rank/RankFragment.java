package lanou.baidu.album.rank;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;

import lanou.baidu.R;
import lanou.baidu.base.BaseFragment;
import lanou.baidu.base.GsonRequest;
import lanou.baidu.base.URLVlaues;
import lanou.baidu.base.VolleySingleton;

/**
 * Created by dllo on 16/9/20.
 */
public class RankFragment extends BaseFragment {
    ArrayList<RankBean> arrayList;
    private ListView listView;
    private RankAdapter rankAdapter;

    @Override
    protected int setLayout() {
        return R.layout.rankfragment;
    }

    @Override
    protected void initView() {
        listView = bindView(R.id.rank_list);
    }

    @Override
    protected void initData() {
        rankAdapter = new RankAdapter(getContext());
        arrayList = new ArrayList<>();
        GsonRequest<RankBean> gsonRequest = new GsonRequest<>(URLVlaues.MUSICSTORE_TOP, RankBean.class,
                new Response.Listener<RankBean>() {
            @Override
            public void onResponse(RankBean response) {


                arrayList.add(response);
                rankAdapter.setArrayList(arrayList);
                View footView = LayoutInflater.from(mContext).inflate(R.layout.listview_foot,null);
                listView.addFooterView(footView);
                listView.setAdapter(rankAdapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("RankFragment", "error");
            }
        });
       VolleySingleton.getInstance().addRequest(gsonRequest);





    }
}
