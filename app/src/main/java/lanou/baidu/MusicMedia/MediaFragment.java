package lanou.baidu.MusicMedia;


import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
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

import java.io.IOException;
import java.net.URL;

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
    private CoordinatorLayout coordinatorLayout;


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

                        meidaListAdapter.setOnRecyclerItemClickListener(new MediaListAdapter.OnRecyclerItemClickListener() {
                            @Override
                            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {

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
            Drawable drawable =new BitmapDrawable(bitmap);
            coordinatorLayout.setBackground(drawable);
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


}
