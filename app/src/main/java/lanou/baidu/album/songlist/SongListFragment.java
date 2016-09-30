package lanou.baidu.album.songlist;

import android.graphics.Color;
import android.view.View;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;

import java.util.ArrayList;

import lanou.baidu.R;
import lanou.baidu.base.BaseFragment;
import lanou.baidu.base.GsonRequest;
import lanou.baidu.base.URLVlaues;
import lanou.baidu.base.VolleySingleton;

/**
 * Created by dllo on 16/9/23.
 */
public class SongListFragment extends BaseFragment implements View.OnClickListener {

    private PullToRefreshGridView pullToRefreshGridView;
    private int next = -1;
    private RelativeLayout relativeLayout;
    private SongListAdapter songLAdapter;
    //private GridView pullToRefreshGridView;
    //int i = 2;
    private TextView songlist_hot, songlist_new;

    @Override
    protected int setLayout() {
        return R.layout.songlistfragment_albumfragment;
    }

    @Override
    protected void initView() {
        pullToRefreshGridView = bindView(R.id.songlist_gridview);
        relativeLayout = bindView(R.id.titlelan_songlist);
        songlist_hot = bindView(R.id.songlist_hot);
        songlist_new = bindView(R.id.songlist_new);


    }

    @Override
    protected void initData() {
        songlist_hot.setTextColor(Color.BLACK);
        songlist_hot.setOnClickListener(this);
        songlist_new.setOnClickListener(this);


        //Log.d("SongListFragmentuuu", URLVlaues.ALL_SONGLIST_REFRESH(i));
        pullMode();
        GsonRequest<SongLIstHotBean> requestSongList = new GsonRequest<SongLIstHotBean>(URLVlaues.SONGLIST_HOT, SongLIstHotBean.class, new Response.Listener<SongLIstHotBean>() {
            @Override
            public void onResponse(final SongLIstHotBean response) {
                ArrayList<SongLIstHotBean> arrayList = new ArrayList<>();
                songLAdapter = new SongListAdapter(getContext());

                arrayList.add(response);
                songLAdapter.setArrayList(arrayList);
                pullToRefreshGridView.setAdapter(songLAdapter);

//                pullToRefreshGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        Log.d("SongListFragment", "aaa");
//                        String listid = response.getDiyInfo().get(position).getList_id();
//                        String url = URLVlaues.SONGLIST_DETAIL_Front + listid + URLVlaues.SONGLIST_DETAIL_BEHIND;
//                        MediaFragment mediaFragment = new MediaFragment();
//                        mediaFragment.setUrl(url);
//                        MainActivity.replacefrag(mediaFragment);
//                    }
//                });
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.getMessage();
            }
        });
        VolleySingleton.getInstance().addRequest(requestSongList);

        pullToRefreshGridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {


                if (firstVisibleItem != next) {
                    //如果后一个值减去前一个值为正数,则是向上滑动
                    //向上滑动未超过第一个item显示
                    if (firstVisibleItem - next >= 0 && firstVisibleItem == 0) {
                        relativeLayout.setVisibility(View.VISIBLE);
                    }
                    //向上滑动超过一个item消失
                    if (firstVisibleItem - next >= 0 && firstVisibleItem > 0) {
                        relativeLayout.setVisibility(View.GONE);
                    }
                    //向下滑动就显示
                    if (firstVisibleItem - next < 0) {
                        relativeLayout.setVisibility(View.VISIBLE);
                    }

                    next = firstVisibleItem;
                }
            }

        });

        pullToRefreshGridView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
//                GsonRequest<SongListOldBean> requestSongListx = new GsonRequest<SongListOldBean>(URLVlaues.ALL_SONGLIST_REFRESH(i), SongListOldBean.class, new Response.Listener<SongListOldBean>() {
//                    @Override
//                    public void onResponse(SongListOldBean response) {
//
//                        ArrayList<SongListOldBean> arrayListnew = new ArrayList<>();
//                        arrayListnew.add(response);
//                        songLAdapter.setDown(true);
//                        songLAdapter.setArrayListold(arrayListnew);
//                        i++;
                pullToRefreshGridView.onRefreshComplete();
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        error.getMessage();
//                    }
//                });
//                VolleySingleton.getInstance().addRequest(requestSongListx);


            }
        });
    }

    public void pullMode() {
        // 设置PullToRefreshListView的模式
        pullToRefreshGridView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        // 设置PullRefreshListView上提加载时的加载提示
        pullToRefreshGridView.getLoadingLayoutProxy(false, true).setPullLabel("上拉更多");
        pullToRefreshGridView.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在获取请稍后");
        pullToRefreshGridView.getLoadingLayoutProxy(false, true).setReleaseLabel("松开后获取");


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.songlist_hot:

                GsonRequest<SongLIstHotBean> requestSongList = new GsonRequest<SongLIstHotBean>(URLVlaues.SONGLIST_HOT, SongLIstHotBean.class, new Response.Listener<SongLIstHotBean>() {
                    @Override
                    public void onResponse(SongLIstHotBean response) {
                        ArrayList<SongLIstHotBean> arrayList = new ArrayList<>();
                        songLAdapter = new SongListAdapter(getContext());
                        arrayList.add(response);
                        songLAdapter.setArrayList(arrayList);
                        pullToRefreshGridView.setAdapter(songLAdapter);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.getMessage();
                    }
                });
                VolleySingleton.getInstance().addRequest(requestSongList);
                songlist_hot.setTextColor(Color.BLACK);
                songlist_new.setTextColor(Color.GRAY);
                break;
            case R.id.songlist_new:

                GsonRequest<SongLIstHotBean> requestSongListNew = new GsonRequest<SongLIstHotBean>(URLVlaues.SONGLIST_NEW, SongLIstHotBean.class, new Response.Listener<SongLIstHotBean>() {
                    @Override
                    public void onResponse(SongLIstHotBean response) {
                        ArrayList<SongLIstHotBean> arrayList = new ArrayList<>();
                        songLAdapter = new SongListAdapter(getContext());
                        arrayList.add(response);
                        songLAdapter.setArrayList(arrayList);
                        pullToRefreshGridView.setAdapter(songLAdapter);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.getMessage();
                    }
                });
                VolleySingleton.getInstance().addRequest(requestSongListNew);
                songlist_new.setTextColor(Color.BLACK);
                songlist_hot.setTextColor(Color.GRAY);
                break;
        }
    }
}
