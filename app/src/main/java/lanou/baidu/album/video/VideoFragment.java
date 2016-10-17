package lanou.baidu.album.video;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lhh.ptrrv.library.PullToRefreshRecyclerView;

import java.util.ArrayList;

import lanou.baidu.R;
import lanou.baidu.base.BaseFragment;
import lanou.baidu.base.GsonRequest;
import lanou.baidu.base.URLVlaues;
import lanou.baidu.base.VolleySingleton;
import lanou.baidu.bean.VideoBean;

/**
 * Created by dllo on 16/9/28.
 */
public class VideoFragment extends BaseFragment implements View.OnClickListener {

    private TextView hotvideo;
    private TextView newvideo;
    private PullToRefreshRecyclerView recyclerView;


    private static final int MSG_CODE_NEW = 100;
    private static final int MSG_CODE_HOT = 200;

    private static final int TIME = 1000;
    private VideoAdapter videoAdapter;
    private int i = 2;
    private int k = 2;
    private VIdeoAdapter2 vIdeoAdapter2;

    @Override
    protected int setLayout() {
        return R.layout.video_fragment;
    }

    @Override
    protected void initView() {
        hotvideo = bindView(R.id.video_hot);
        newvideo = bindView(R.id.video_new);
        recyclerView = bindView(R.id.video_content);
    }

    @Override
    protected void initData() {

        //  pullMode();
        hotvideo.setTextColor(Color.GRAY);
        newvideo.setTextColor(Color.BLACK);
        hotvideo.setOnClickListener(this);
        newvideo.setOnClickListener(this);

        GsonRequest<VideoBean> requestVideoList = new GsonRequest<VideoBean>(URLVlaues.VIDEO_NEW, VideoBean.class, new Response.Listener<VideoBean>() {
            @Override
            public void onResponse(VideoBean response) {

                ArrayList<VideoBean> arrayList = new ArrayList<>();
                arrayList.add(response);
                videoAdapter = new VideoAdapter(getContext());
                videoAdapter.setVideoBean(response);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);

                recyclerView.setAdapter(videoAdapter);
                recyclerView.setLayoutManager(gridLayoutManager);
                recyclerView.setPagingableListener(new PullToRefreshRecyclerView.PagingableListener() {
                    @Override
                    public void onLoadMoreItems() {
                        // Toast.makeText(mContext, "aa", Toast.LENGTH_SHORT).show();
                        mHandler.sendEmptyMessageDelayed(MSG_CODE_NEW, TIME);

                    }
                });
                recyclerView.onFinishLoading(true, false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.getMessage();
            }
        });
        VolleySingleton.getInstance().addRequest(requestVideoList);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.video_new:
                i = 2;
                GsonRequest<VideoBean> requestVideoList = new GsonRequest<VideoBean>(URLVlaues.VIDEO_NEW, VideoBean.class, new Response.Listener<VideoBean>() {
                    @Override
                    public void onResponse(VideoBean response) {
                        videoAdapter = new VideoAdapter(getContext());
                        videoAdapter.setVideoBean(response);
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);

                        recyclerView.setAdapter(videoAdapter);
                        recyclerView.setLayoutManager(gridLayoutManager);
                        recyclerView.setPagingableListener(new PullToRefreshRecyclerView.PagingableListener() {
                            @Override
                            public void onLoadMoreItems() {
                                // Toast.makeText(mContext, "aa", Toast.LENGTH_SHORT).show();
                                mHandler.sendEmptyMessageDelayed(MSG_CODE_NEW, TIME);
                            }
                        });
                        recyclerView.onFinishLoading(true, false);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.getMessage();
                    }
                });
                VolleySingleton.getInstance().addRequest(requestVideoList);

                hotvideo.setTextColor(Color.GRAY);
                newvideo.setTextColor(Color.BLACK);
                break;
            case R.id.video_hot:
                k = 2;
                GsonRequest<VideoBean> requestVideoListhot = new GsonRequest<VideoBean>(URLVlaues.VIDEO_HOT, VideoBean.class, new Response.Listener<VideoBean>() {
                    @Override
                    public void onResponse(VideoBean response) {

                        videoAdapter = new VideoAdapter(getContext());
                        videoAdapter.setVideoBean(response);
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);

                        recyclerView.setAdapter(videoAdapter);
                        recyclerView.setLayoutManager(gridLayoutManager);
                        recyclerView.setPagingableListener(new PullToRefreshRecyclerView.PagingableListener() {
                            @Override
                            public void onLoadMoreItems() {
                                mHandler.sendEmptyMessageDelayed(MSG_CODE_HOT, TIME);

                            }
                        });


                        recyclerView.onFinishLoading(true, false);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.getMessage();
                    }
                });
                VolleySingleton.getInstance().addRequest(requestVideoListhot);

                hotvideo.setTextColor(Color.BLACK);
                newvideo.setTextColor(Color.GRAY);
                break;
        }

    }

//    public void pullMode() {
//        // 设置PullToRefreshListView的模式
//        recyclerView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
//        // 设置PullRefreshListView上提加载时的加载提示
//        recyclerView.getLoadingLayoutProxy(false, true).setPullLabel("上拉更多");
//        recyclerView.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在获取请稍后");
//        recyclerView.getLoadingLayoutProxy(false, true).setReleaseLabel("松开后获取");
//
//
//    }


    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case MSG_CODE_NEW:
                    Log.d("VideoFragment", i + "");
                    if (i >= 8) {
                        Toast.makeText(mContext, "没有更多", Toast.LENGTH_SHORT).show();
                        recyclerView.onFinishLoading(false, false);
                        return;
                    }
                    GsonRequest<VideoBean> requestVideoListnew = new GsonRequest<VideoBean>(URLVlaues.VIDEO_LOAD(1, i), VideoBean.class, new Response.Listener<VideoBean>() {
                        @Override
                        public void onResponse(VideoBean response) {

                            ArrayList<VideoBean> arrayList = new ArrayList<>();
                            arrayList.add(response);
                            videoAdapter.setDown(true);
                            videoAdapter.setVideoBean(response);
                            i++;
                            recyclerView.onFinishLoading(true, false);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.getMessage();
                        }
                    });
                    VolleySingleton.getInstance().addRequest(requestVideoListnew);
                    break;
                case MSG_CODE_HOT:
                    if (k >= 8) {
                        Toast.makeText(mContext, "没有更多", Toast.LENGTH_SHORT).show();
                        recyclerView.onFinishLoading(false, false);
                        return;
                    }
                    GsonRequest<VideoBean> requestVideoListhot = new GsonRequest<VideoBean>(URLVlaues.VIDEO_LOAD(0, k), VideoBean.class, new Response.Listener<VideoBean>() {
                        @Override
                        public void onResponse(VideoBean response) {
                            ArrayList<VideoBean> arrayList = new ArrayList<>();
                            arrayList.add(response);
                            videoAdapter.setDown(true);
                            videoAdapter.setVideoBean(response);
                            k++;
                            recyclerView.onFinishLoading(true, false);


//                            ArrayList<VideoBean> arrayList = new ArrayList<>();
//                            arrayList.add(response);
//                            Log.d("VideoFragmentaaa", arrayList.get(0).getResult().getMv_list().get(1).getArtist());
//                            vIdeoAdapter2.setDown(true);
//                            vIdeoAdapter2.setVideoBean(response);
//                            k++;
//                            recyclerView.onRefreshComplete();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.getMessage();
                        }
                    });
                    VolleySingleton.getInstance().addRequest(requestVideoListhot);


            }

        }
    };
}
