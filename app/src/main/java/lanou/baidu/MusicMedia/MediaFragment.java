package lanou.baidu.MusicMedia;


import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import lanou.baidu.R;
import lanou.baidu.base.BaseFragment;
import lanou.baidu.base.GsonRequest;
import lanou.baidu.base.MainActivity;
import lanou.baidu.base.MyImageLoader;
import lanou.baidu.base.VolleySingleton;

public class MediaFragment extends BaseFragment {


    private ImageView mainimg, userimage, returnbtn, morebtn;
    private TextView tagtext, username, collectnum, commentnum, sharenum, playnum, title, listennum;
    private RecyclerView recyclerView;
    private AppBarLayout appBarLayout;
    private RelativeLayout relativeLayout;
    private LinearLayout linearLayout;
    private String url;


    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    protected int setLayout() {
        return R.layout.fragment_medialist;
    }

    @Override
    protected void initView() {
        mainimg = bindView(R.id.img_medialist);
        tagtext = bindView(R.id.tv_tag_medialist);
        userimage = bindView(R.id.img_user_medialist);
        username = bindView(R.id.te_user_name);
        listennum = bindView(R.id.tv_listennum_media);
        collectnum = bindView(R.id.tv_shoucang_count);
        commentnum = bindView(R.id.tv_pinglun_count);
        sharenum = bindView(R.id.tv_fenxiang_count);
        playnum = bindView(R.id.tv_songlist_count_medialist);
        returnbtn = bindView(R.id.return_meidalist);
        morebtn = bindView(R.id.more_medialist);
        title = bindView(R.id.tv_title_medialist);
        recyclerView = bindView(R.id.rv_medialist);
        appBarLayout = bindView(R.id.appbar_medialist);
        relativeLayout = bindView(R.id.user_medialist);
        linearLayout = bindView(R.id.make_medialist);
    }


    @Override
    protected void initData() {
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int maxScroll = appBarLayout.getTotalScrollRange();
                float percentage = (float) Math.abs(verticalOffset) / (float) maxScroll;
                if (percentage > 0.5f) {
                    relativeLayout.setVisibility(View.INVISIBLE);
                    linearLayout.setVisibility(View.INVISIBLE);
                } else {
                    relativeLayout.setVisibility(View.VISIBLE);
                    linearLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        returnbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.retrnfrag(MediaFragment.this);
            }
        });

        GsonRequest<MediaLIstBean> requestmedialist = new GsonRequest<MediaLIstBean>(url, MediaLIstBean.class,
                new Response.Listener<MediaLIstBean>() {
                    @Override
                    public void onResponse(MediaLIstBean response) {
                        title.setText(response.getTitle());
                        listennum.setText(response.getListenum());
                        tagtext.setText("标签: " + response.getTag());
                        collectnum.setText(response.getCollectnum());
                        MyImageLoader.myImageLoader(response.getPic_300(), mainimg);
                        int count = response.getContent().size();
                        playnum.setText("/" + count + "首歌");
                        commentnum.setText("评论");
                        sharenum.setText("分享");
                        MediaListAdapter meidaListAdapter = new MediaListAdapter(getContext());
                        meidaListAdapter.setMediaLIstBean(response);
                        recyclerView.setAdapter(meidaListAdapter);
                        LinearLayoutManager manager = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(manager);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.getMessage();
            }
        });
        VolleySingleton.getInstance().addRequest(requestmedialist);

    }


}
