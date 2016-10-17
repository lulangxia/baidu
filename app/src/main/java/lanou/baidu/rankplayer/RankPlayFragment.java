package lanou.baidu.rankplayer;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import lanou.baidu.R;
import lanou.baidu.base.BaseFragment;
import lanou.baidu.base.GsonRequest;
import lanou.baidu.base.VolleySingleton;
import lanou.baidu.bean.MusicBean;
import lanou.baidu.bean.MyMusicBean;
import lanou.baidu.bean.RankPlayBean;
import lanou.baidu.main.MainActivity;
import lanou.baidu.musicmedia.OnRecyclerItemClickListener;

/**
 * Created by dllo on 16/10/15.
 */
public class RankPlayFragment extends BaseFragment {

    private ImageView mainimg, userimage, returnbtn, sharebtn;
    private TextView tagtext, username, collectnum, commentnum, sharenum, playnum, title, listennum, daterankplay;
    private RecyclerView recyclerView;
    private AppBarLayout appBarLayout;
    private RelativeLayout relativeLayout;
    private LinearLayout linearLayout;
    private String url;
    private CoordinatorLayout coordinatorLayout;
    private MusicBean musicBean;
    private MyMusicBean myMusicBean;

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    protected int setLayout() {
        return R.layout.rankplayfragment;
    }

    @Override
    protected void initView() {


        daterankplay = bindView(R.id.data_rankplay);
        playnum = bindView(R.id.tv_songlist_count_medialist);
        returnbtn = bindView(R.id.return_meidalist);
        sharebtn = bindView(R.id.more_medialist);
        title = bindView(R.id.tv_title_medialist);
        recyclerView = bindView(R.id.rv_medialist);
        appBarLayout = bindView(R.id.appbar_medialist);
        relativeLayout = bindView(R.id.relativeLayout_rank);

        coordinatorLayout = bindView(R.id.coor_back);

        returnbtn = bindView(R.id.return_meidalist);


    }

    @Override
    protected void initData() {
        ShareSDK.initSDK(getContext());

        returnbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.retrnfrag(RankPlayFragment.this);
            }
        });

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int maxScroll = appBarLayout.getTotalScrollRange();
                float percentage = (float) Math.abs(verticalOffset) / (float) maxScroll;
                if (percentage > 0.5f) {
                    relativeLayout.setVisibility(View.INVISIBLE);
                    title.setVisibility(View.VISIBLE);
                } else {
                    relativeLayout.setVisibility(View.VISIBLE);
                    title.setVisibility(View.INVISIBLE);
                }
            }
        });


        GsonRequest<RankPlayBean> requestmedialist = new GsonRequest<RankPlayBean>(url, RankPlayBean.class,
                new Response.Listener<RankPlayBean>() {
                    @Override
                    public void onResponse(final RankPlayBean response) {
                        title.setText(response.getBillboard().getName());
                        new DownloadImageTask().execute(response.getBillboard().getPic_s640());
                        daterankplay.setText("更新日期: " + response.getBillboard().getUpdate_date());
                        int count = response.getSong_list().size();
                        playnum.setText("/" + count + "首歌");

                        RankPlayAdapter rankPlayAdapter = new RankPlayAdapter(getContext());
                        rankPlayAdapter.setRankBean(response);
                        recyclerView.setAdapter(rankPlayAdapter);
                        LinearLayoutManager manager = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(manager);

                        ArrayList<MusicBean> arraylist = new ArrayList<>();
                        for (int i = 0; i < response.getSong_list().size(); i++) {
                            musicBean = new MusicBean(response.getSong_list().get(i).getTitle(), response.getSong_list().get(i).getAuthor(), response.getSong_list().get(i).getSong_id());
                            arraylist.add(musicBean);
                        }
                        myMusicBean = new MyMusicBean();
                        myMusicBean.setMusicBeen(arraylist);

                        rankPlayAdapter.setOnRecyclerItemClickListener(new OnRecyclerItemClickListener() {
                            @Override
                            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                                myMusicBean.setPosition(position);
                                EventBus.getDefault().post(myMusicBean);
                            }
                        });

                        sharebtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                OnekeyShare oks = new OnekeyShare();
                                //关闭sso授权
                                oks.disableSSOWhenAuthorize();

                                // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
                                //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
                                // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
                                oks.setTitle(response.getBillboard().getName());
                                // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
                                oks.setTitleUrl(response.getBillboard().getWeb_url());
                                // text是分享文本，所有平台都需要这个字段
                                oks.setText("分享这个的音乐榜单");
                                //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
                                oks.setImageUrl(response.getBillboard().getPic_s640());
                                // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
                                //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
                                // url仅在微信（包括好友和朋友圈）中使用
                                oks.setUrl(response.getBillboard().getWeb_url());
                                // comment是我对这条分享的评论，仅在人人网和QQ空间使用
                                oks.setComment(response.getBillboard().getComment());
                                // site是分享此内容的网站名称，仅在QQ空间使用
                                oks.setSite("来自百度音乐");
                                // siteUrl是分享此内容的网站地址，仅在QQ空间使用
                                oks.setSiteUrl(response.getBillboard().getWeb_url());

                                // 启动分享GUI
                                oks.show(getContext());
                            }
                        });
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.getMessage();
            }
        });
        VolleySingleton.getInstance().addRequest(requestmedialist);


    }

    private class DownloadImageTask extends AsyncTask<String, Void, Drawable> {

        protected Drawable doInBackground(String... urls) {
            return loadImageFromNetwork(urls[0]);
        }

        protected void onPostExecute(Drawable result) {
            BitmapDrawable bd = (BitmapDrawable) result;
            if (bd != null) {
                Bitmap bitmap = bd.getBitmap();
                Drawable drawable = new BitmapDrawable(bitmap);
                appBarLayout.setBackground(drawable);
            }
        }
    }

    private Drawable loadImageFromNetwork(String imageUrl) {
        Drawable drawable = null;
        try {
            // 可以在这里通过第二个参数(文件名)来判断，是否本地有此图片
            drawable = Drawable.createFromStream(new URL(imageUrl).openStream(), null);
        } catch (IOException e) {
            Log.d("skythinking", e.getMessage());
        }
        if (drawable == null) {
            Log.d("skythinking", "null drawable");
        } else {
            Log.d("skythinking", "not null drawable");
        }

        return drawable;
    }
}
