package lanou.baidu.album.video;

import android.graphics.Color;
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

/**
 * Created by dllo on 16/9/28.
 */
public class VideoFragment extends BaseFragment implements View.OnClickListener {

    private TextView hotvideo;
    private TextView newvideo;
    private PullToRefreshRecyclerView recyclerView;


    private static final int MSG_CODE_NEW = 100;
    private static final int MSG_CODE_HOT = 200;

    private static final int TIME = 2000;
    private VideoAdapter videoAdapter;
    private int i = 2;
    private int k = 2;

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
                videoAdapter.setArrayList(arrayList);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);

                recyclerView.setAdapter(videoAdapter);
                recyclerView.setLayoutManager(gridLayoutManager);
                recyclerView.setPagingableListener(new PullToRefreshRecyclerView.PagingableListener() {
                    @Override
                    public void onLoadMoreItems() {
                        Toast.makeText(mContext, "aa", Toast.LENGTH_SHORT).show();
                        recyclerView.onFinishLoading(false, false);
                    }
                });

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.getMessage();
            }
        });
        VolleySingleton.getInstance().addRequest(requestVideoList);

        recyclerView.onFinishLoading(true, false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.video_new:
                GsonRequest<VideoBean> requestVideoList = new GsonRequest<VideoBean>(URLVlaues.VIDEO_NEW, VideoBean.class, new Response.Listener<VideoBean>() {
                    @Override
                    public void onResponse(VideoBean response) {
                        ArrayList<VideoBean> arrayList = new ArrayList<>();
                        arrayList.add(response);
                        VideoAdapter videoAdapter = new VideoAdapter(getContext());
                        videoAdapter.setArrayList(arrayList);
                        Log.d("VideoFragment", "arrayList.get(0).getResult().getMv_list().size():" + arrayList.get(0).getResult().getMv_list().size());
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
                        recyclerView.setAdapter(videoAdapter);
                        recyclerView.setLayoutManager(gridLayoutManager);

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
                GsonRequest<VideoBean> requestVideoListHot = new GsonRequest<VideoBean>(URLVlaues.VIDEO_HOT, VideoBean.class, new Response.Listener<VideoBean>() {
                    @Override
                    public void onResponse(VideoBean response) {
                        ArrayList<VideoBean> arrayList = new ArrayList<>();
                        arrayList.add(response);
                        VideoAdapter videoAdapter = new VideoAdapter(getContext());
                        videoAdapter.setArrayList(arrayList);
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
                        recyclerView.setAdapter(videoAdapter);
                        recyclerView.setLayoutManager(gridLayoutManager);
//                        recyclerView.setPagingableListener(new PullToRefreshRecyclerView.PagingableListener() {
//                            @Override
//                            public void onLoadMoreItems() {
//                                mHandler.sendEmptyMessageDelayed(MSG_CODE_HOT, TIME);
//                            }
//                        });

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.getMessage();
                    }
                });
                VolleySingleton.getInstance().addRequest(requestVideoListHot);
                hotvideo.setTextColor(Color.BLACK);
                newvideo.setTextColor(Color.GRAY);
                break;
        }

    }

//    Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//
//            switch (msg.what) {
//                case MSG_CODE_NEW:
//                    Log.d("VideoFragment", "aa");
//                    GsonRequest<VideoBean> requestVideoListnew = new GsonRequest<VideoBean>(URLVlaues.VIDEO_LOAD(1, i), VideoBean.class, new Response.Listener<VideoBean>() {
//                        @Override
//                        public void onResponse(VideoBean response) {
//                            ArrayList<VideoBean> arrayList = new ArrayList<>();
//                            arrayList.add(response);
//                            videoAdapter.setDown(true);
//                            videoAdapter.setArrayList(arrayList);
//                            i++;
//                            recyclerView.onFinishLoading(true, false);
//                        }
//                    }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            error.getMessage();
//                        }
//                    });
//                    VolleySingleton.getInstance().addRequest(requestVideoListnew);
//                    break;
//                case MSG_CODE_HOT:
//                    GsonRequest<VideoBean> requestVideoListhot = new GsonRequest<VideoBean>(URLVlaues.VIDEO_LOAD(0, k), VideoBean.class, new Response.Listener<VideoBean>() {
//                        @Override
//                        public void onResponse(VideoBean response) {
//                            ArrayList<VideoBean> arrayList = new ArrayList<>();
//                            arrayList.add(response);
//                            videoAdapter.setDown(true);
//                            videoAdapter.setArrayList(arrayList);
//                            k++;
//                            recyclerView.onFinishLoading(true, false);
//                        }
//                    }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            error.getMessage();
//                        }
//                    });
//                    VolleySingleton.getInstance().addRequest(requestVideoListhot);
//
//
//            }
//
//        }
//    };
}
