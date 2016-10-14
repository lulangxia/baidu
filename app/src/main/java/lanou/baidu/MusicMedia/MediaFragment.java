package lanou.baidu.musicMedia;


import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
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
import lanou.baidu.main.MainActivity;
import lanou.baidu.base.MyImageLoader;
import lanou.baidu.base.VolleySingleton;
import lanou.baidu.eventBus.MusicBean;
import lanou.baidu.eventBus.MyMusicBean;

public class MediaFragment extends BaseFragment {


    private ImageView mainimg, userimage, returnbtn, morebtn;
    private TextView tagtext, username, collectnum, commentnum, sharenum, playnum, title, listennum;
    private RecyclerView recyclerView;
    private AppBarLayout appBarLayout;
    private RelativeLayout relativeLayout;
    private LinearLayout linearLayout;
    private String url;
    private CoordinatorLayout coordinatorLayout;

    private MusicBean musicBean;
    private MyMusicBean myMusicBean;
    private Handler mHandler;
    private LinearLayout linearLayout1;

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
        coordinatorLayout = bindView(R.id.coor_back);
        linearLayout1 = bindView(R.id.ll_fenxiang);
    }


    @Override
    protected void initData() {

        ShareSDK.initSDK(getContext());
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
                    public void onResponse(final MediaLIstBean response) {
                        title.setText(response.getTitle());
                        listennum.setText(response.getListenum());
                        tagtext.setText("标签: " + response.getTag());
                        collectnum.setText(response.getCollectnum());
                        new DownloadImageTask().execute(response.getPic_300());
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
                        ArrayList<MusicBean> arraylist = new ArrayList<>();
                        for (int i = 0; i < response.getContent().size(); i++) {
                            musicBean = new MusicBean(response.getContent().get(i).getTitle(), response.getContent().get(i).getAuthor(), response.getContent().get(i).getSong_id());
                            arraylist.add(musicBean);
                        }
                        myMusicBean = new MyMusicBean();
                        myMusicBean.setMusicBeen(arraylist);

                        meidaListAdapter.setOnRecyclerItemClickListener(new MediaListAdapter.OnRecyclerItemClickListener() {
                            @Override
                            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                                myMusicBean.setPosition(position - 1);
                                EventBus.getDefault().post(myMusicBean);
                            }
                        });

                        linearLayout1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                OnekeyShare oks = new OnekeyShare();
                                //关闭sso授权
                                oks.disableSSOWhenAuthorize();

// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
                                //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
                                // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
                                oks.setTitle(response.getTitle());
                                // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
                                oks.setTitleUrl(response.getUrl());
                                // text是分享文本，所有平台都需要这个字段
                                oks.setText("分享这个动听的歌单");
                                //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
                                oks.setImageUrl(response.getPic_300());
                                // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
                                //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
                                // url仅在微信（包括好友和朋友圈）中使用
                                oks.setUrl(response.getUrl());
                                // comment是我对这条分享的评论，仅在人人网和QQ空间使用
                                oks.setComment(response.getTag());
                                // site是分享此内容的网站名称，仅在QQ空间使用
                                oks.setSite("来自百度音乐");
                                // siteUrl是分享此内容的网站地址，仅在QQ空间使用
                                oks.setSiteUrl(response.getUrl());

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

    private class DownloadImageTask extends AsyncTask<String, Void, Drawable> {

        protected Drawable doInBackground(String... urls) {
            return loadImageFromNetwork(urls[0]);
        }

        protected void onPostExecute(Drawable result) {
            BitmapDrawable bd = (BitmapDrawable) result;
            Bitmap bitmap = changeBackgroundImage(bd.getBitmap());
            Drawable drawable = new BitmapDrawable(bitmap);
            appBarLayout.setBackground(drawable);
        }
    }


    public Bitmap changeBackgroundImage(Bitmap sentBitmap) {
        if (Build.VERSION.SDK_INT > 16) {
            Bitmap bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);
            final RenderScript rs = RenderScript.create(getContext());
            final Allocation input = Allocation.createFromBitmap(rs, sentBitmap, Allocation.MipmapControl.MIPMAP_NONE,
                    Allocation.USAGE_SCRIPT);
            final Allocation output = Allocation.createTyped(rs, input.getType());
            final ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
            script.setRadius(20.0f);
            script.setInput(input);
            script.forEach(output);
            output.copyTo(bitmap);
            return bitmap;
        }
        return null;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
