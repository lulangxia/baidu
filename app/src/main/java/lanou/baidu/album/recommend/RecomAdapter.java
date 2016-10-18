package lanou.baidu.album.recommend;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;

import lanou.baidu.bean.RecomBean;
import lanou.baidu.musicmedia.MediaFragment;
import lanou.baidu.R;
import lanou.baidu.bean.recommendbean.RecommainBean;
import lanou.baidu.bean.recommendbean.RecommainBeanSpare;
import lanou.baidu.bean.recommendbean.RecommendNewBean;
import lanou.baidu.album.recommend.recomsonadapter.ColumAdapter;
import lanou.baidu.album.recommend.recomsonadapter.DiyAdapter;
import lanou.baidu.album.recommend.recomsonadapter.HeadAdapter;
import lanou.baidu.album.recommend.recomsonadapter.HotAdapter;
import lanou.baidu.album.recommend.recomsonadapter.LeRaAdapter;
import lanou.baidu.album.recommend.recomsonadapter.MVAdapter;
import lanou.baidu.album.recommend.recomsonadapter.NewDAdapter;
import lanou.baidu.album.recommend.recomsonadapter.RadioAdapter;
import lanou.baidu.album.recommend.recomsonadapter.SongLAdapter;
import lanou.baidu.album.recommend.recomsonadapter.TodayAdapter;
import lanou.baidu.request.GsonRequest;
import lanou.baidu.main.MainActivity;
import lanou.baidu.view.MyGridView;
import lanou.baidu.tools.MyImageLoader;
import lanou.baidu.tools.URLVlaues;
import lanou.baidu.request.VolleySingleton;
import lanou.baidu.replacefragment.SingerFragment;

/**
 * Created by dllo on 16/9/20.
 */
public class RecomAdapter extends RecyclerView.Adapter {
    Context mContext;
    private ArrayList<RecomBean> arrayList;

    private SongLAdapter songLAdapter;
    private Handler mHandler;

    private Changed changed;

    public void setChanged(Changed changed) {
        this.changed = changed;
    }

    public void setArrayList(ArrayList<RecomBean> arrayList) {
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }

    public RecomAdapter(Context mContext) {
        this.mContext = mContext;
    }


    private HeadAdapter headAdapter;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                return new HeadViewHolder(LayoutInflater.from(mContext).inflate(R.layout.headrecycle_recommend, parent, false));
            case 1:
                return new SongLViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_recommend, parent, false));
            case 2:
                return new NewDiskViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_recommend, parent, false));
            case 3:
                return new HotViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_recommend, parent, false));
            case 4:
                return new AdViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_head, parent, false));
            case 5:
                return new RadioViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_recommend, parent, false));
            case 6:
                return new TodayViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_recommend, parent, false));
            case 7:
                return new DiyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_recommend, parent, false));
            case 8:
                return new MVViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_recommend, parent, false));
            case 9:
                return new LeRadioViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_recommend, parent, false));
            case 10:
                return new ColumPageViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_recommend_last, parent, false));

        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        switch (type) {
            case 0:
                final HeadViewHolder headViewHolder = (HeadViewHolder) holder;
                headAdapter = new HeadAdapter(mContext);
                GsonRequest<RecommendNewBean> gsonRequest = new GsonRequest<>(arrayList.get(0).getUrl(), RecommendNewBean.class,
                        new Response.Listener<RecommendNewBean>() {
                            @Override
                            public void onResponse(RecommendNewBean response) {

                                headAdapter.setRecommendNew(response);
                                headViewHolder.viewPager.setAdapter(headAdapter);
                                headViewHolder.viewPager.setCurrentItem(2);
                                roating(headViewHolder);
                                MyImageLoader.myImageLoader(response.getResult().getEntry().getResult().get(0).getIcon(), headViewHolder.imageView1);
                                MyImageLoader.myImageLoader(response.getResult().getEntry().getResult().get(1).getIcon(), headViewHolder.imageView2);
                                MyImageLoader.myImageLoader(response.getResult().getEntry().getResult().get(2).getIcon(), headViewHolder.imageView3);
                                MyImageLoader.myImageLoader(response.getResult().getEntry().getResult().get(3).getIcon(), headViewHolder.imageView4);

                                //Picasso.with(mContext).load(response.getResult().getEntry().getResult().get(0).getIcon()).into(headViewHolder.imageView1);
                                //Log.d("RecomAdapter", headarrayList.get(0).getResult().getEntry().getResult().get(0).getIcon());
                                //Picasso.with(mContext).load(response.getResult().getEntry().getResult().get(1).getIcon()).into(headViewHolder.imageView2);
                                //Log.d("RecomAdapter", headarrayList.get(0).getResult().getEntry().getResult().get(1).getIcon());
                                //Picasso.with(mContext).load(response.getResult().getEntry().getResult().get(2).getIcon()).into(headViewHolder.imageView3);
                                //Log.d("RecomAdapter", headarrayList.get(0).getResult().getEntry().getResult().get(2).getIcon());
                                //Picasso.with(mContext).load(response.getResult().getEntry().getResult().get(3).getIcon()).into(headViewHolder.imageView4);
                                headViewHolder.textView1.setText(response.getResult().getEntry().getResult().get(0).getTitle());
                                headViewHolder.textView2.setText(response.getResult().getEntry().getResult().get(1).getTitle());
                                headViewHolder.textView3.setText(response.getResult().getEntry().getResult().get(2).getTitle());
                                headViewHolder.textView4.setText(response.getResult().getEntry().getResult().get(3).getTitle());

                                headViewHolder.ll1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        MainActivity.replacefrag(new SingerFragment());
                                    }
                                });


                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.getMessage();
                    }
                });
                VolleySingleton.getInstance().addRequest(gsonRequest);


                break;
            case 1:
                final SongLViewHolder songLViewHolder = (SongLViewHolder) holder;

                songLAdapter = new SongLAdapter(mContext);
                GsonRequest<RecommainBean> gsonRequestsonglist = new GsonRequest<>(arrayList.get(1).getUrl(), RecommainBean.class,
                        new Response.Listener<RecommainBean>() {
                            @Override
                            public void onResponse(final RecommainBean response) {

                                MyImageLoader.myImageLoader(response.getModule().get(3).getPicurl(), songLViewHolder.titleleft);
                                //VolleySingleton.getInstance().myImageLoader(response.getModule().get(3).getPicurl(), songLViewHolder.titleleft);
                                songLViewHolder.title.setText(response.getModule().get(3).getTitle());
                                //Picasso.with(mContext).load(response.getModule().get(3).getPicurl()).into(songLViewHolder.titleleft);
                                //Log.d("RecomAdapter", response.getModule().get(3).getPicurl());
                                songLViewHolder.more.setText(response.getModule().get(3).getTitle_more());
                                songLAdapter.setRecommainBean(response);
                                songLViewHolder.gridView.setNumColumns(3);
                                songLViewHolder.gridView.setAdapter(songLAdapter);
                                songLViewHolder.more.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Log.d("RecomAdapter", "aa");
                                        changed.change();
                                    }
                                });

                                songLViewHolder.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        String listid = response.getResult().getDiy().getResult().get(position).getListid();
                                        String url = URLVlaues.SONGLIST_DETAIL_Front + listid + URLVlaues.SONGLIST_DETAIL_BEHIND;
                                        MediaFragment mediaFragment = new MediaFragment();
                                        mediaFragment.setUrl(url);
                                        MainActivity.replacefrag(mediaFragment);

                                    }
                                });

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.getMessage();
                    }
                });
                VolleySingleton.getInstance().addRequest(gsonRequestsonglist);
                break;
            case 2:
                final NewDiskViewHolder newDiskViewHolder = (NewDiskViewHolder) holder;
                GsonRequest<RecommainBean> gsonRequestnewDisk = new GsonRequest<>(arrayList.get(2).getUrl(), RecommainBean.class,
                        new Response.Listener<RecommainBean>() {
                            @Override
                            public void onResponse(RecommainBean response) {
                                newDiskViewHolder.title.setText(response.getModule().get(5).getTitle());
                                MyImageLoader.myImageLoader(response.getModule().get(5).getPicurl(), newDiskViewHolder.titleleft);
                                //Picasso.with(mContext).load(response.getModule().get(5).getPicurl()).into(newDiskViewHolder.titleleft);
                                newDiskViewHolder.more.setText(response.getModule().get(5).getTitle_more());

                                NewDAdapter newDAdapter = new NewDAdapter(mContext);
                                newDAdapter.setRecommainBean(response);
                                newDiskViewHolder.gridView.setNumColumns(3);
                                newDiskViewHolder.gridView.setAdapter(newDAdapter);


                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.getMessage();
                    }
                });
                VolleySingleton.getInstance().addRequest(gsonRequestnewDisk);
                break;

            case 3:
                final HotViewHolder hotViewHolder = (HotViewHolder) holder;
                GsonRequest<RecommainBean> gsonRequestHot = new GsonRequest<>(arrayList.get(3).getUrl(), RecommainBean.class,
                        new Response.Listener<RecommainBean>() {
                            @Override
                            public void onResponse(RecommainBean response) {
                                hotViewHolder.title.setText(response.getModule().get(6).getTitle());
                                //Picasso.with(mContext).load(response.getModule().get(6).getPicurl()).into(hotViewHolder.titleleft);
                                MyImageLoader.myImageLoader(response.getModule().get(6).getPicurl(), hotViewHolder.titleleft);
                                hotViewHolder.more.setVisibility(View.INVISIBLE);

                                HotAdapter hotAdapter = new HotAdapter(mContext);
                                hotAdapter.setRecommainBean(response);
                                hotViewHolder.gridView.setNumColumns(3);
                                hotViewHolder.gridView.setAdapter(hotAdapter);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.getMessage();
                    }
                });
                VolleySingleton.getInstance().addRequest(gsonRequestHot);
                break;
            case 4:
                final AdViewHolder adViewHolder = (AdViewHolder) holder;
                GsonRequest<RecommendNewBean> gsonRequestAD = new GsonRequest<>(arrayList.get(4).getUrl(), RecommendNewBean.class,
                        new Response.Listener<RecommendNewBean>() {
                            @Override
                            public void onResponse(RecommendNewBean response) {
                                adViewHolder.line.setVisibility(View.VISIBLE);
                                MyImageLoader.myImageLoader(response.getResult().getAd_small().getResult().get(0).getPic(), adViewHolder.imageView);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.getMessage();
                    }
                });
                VolleySingleton.getInstance().addRequest(gsonRequestAD);
                break;
            case 5:
                final RadioViewHolder radioViewHolder = (RadioViewHolder) holder;
                GsonRequest<RecommainBeanSpare> gsonRequestRadio = new GsonRequest<>(arrayList.get(5).getUrl(), RecommainBeanSpare.class,
                        new Response.Listener<RecommainBeanSpare>() {
                            @Override
                            public void onResponse(RecommainBeanSpare response) {
                                radioViewHolder.title.setText(response.getModule().get(8).getTitle());
                                MyImageLoader.myImageLoader(response.getModule().get(8).getPicurl(), radioViewHolder.titleleft);
                                //Picasso.with(mContext).load(response.getModule().get(8).getPicurl()).into(radioViewHolder.titleleft);
                                radioViewHolder.more.setText(response.getModule().get(8).getTitle_more());


                                RadioAdapter radioAdapter = new RadioAdapter(mContext);
                                radioAdapter.setRecommainBeanSpare(response);
                                radioViewHolder.gridView.setNumColumns(4);
                                radioViewHolder.gridView.setAdapter(radioAdapter);

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.getMessage();
                    }
                });
                VolleySingleton.getInstance().addRequest(gsonRequestRadio);
                break;
            case 6:
                final TodayViewHolder todayViewHolder = (TodayViewHolder) holder;
                GsonRequest<RecommainBeanSpare> gsonRequestToday = new GsonRequest<>(arrayList.get(6).getUrl(), RecommainBeanSpare.class,
                        new Response.Listener<RecommainBeanSpare>() {
                            @Override
                            public void onResponse(RecommainBeanSpare response) {
                                todayViewHolder.title.setText(response.getModule().get(9).getTitle());
                                MyImageLoader.myImageLoader(response.getModule().get(9).getPicurl(), todayViewHolder.titleleft);
                                //Picasso.with(mContext).load(response.getModule().get(9).getPicurl()).into(todayViewHolder.titleleft);
                                todayViewHolder.more.setText(response.getModule().get(9).getTitle_more());

                                TodayAdapter todayAdapter = new TodayAdapter(mContext);
                                todayAdapter.setRecommainBeanSpare(response);
                                todayViewHolder.gridView.setAdapter(todayAdapter);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.getMessage();
                    }
                });
                VolleySingleton.getInstance().addRequest(gsonRequestToday);
                break;
            case 7:
                final DiyViewHolder diyViewHolder = (DiyViewHolder) holder;
                GsonRequest<RecommainBeanSpare> gsonRequestDiy = new GsonRequest<>(arrayList.get(7).getUrl(), RecommainBeanSpare.class,
                        new Response.Listener<RecommainBeanSpare>() {
                            @Override
                            public void onResponse(RecommainBeanSpare response) {
                                diyViewHolder.title.setText(response.getModule().get(10).getTitle());
                                MyImageLoader.myImageLoader(response.getModule().get(10).getPicurl(), diyViewHolder.titleleft);
                                //Picasso.with(mContext).load(response.getModule().get(10).getPicurl()).into(diyViewHolder.titleleft);
                                diyViewHolder.more.setVisibility(View.INVISIBLE);

                                DiyAdapter diyAdapter = new DiyAdapter(mContext);
                                diyAdapter.setRecommainBeanSpare(response);
                                diyViewHolder.gridView.setNumColumns(3);
                                diyViewHolder.gridView.setAdapter(diyAdapter);

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.getMessage();
                    }
                });
                VolleySingleton.getInstance().addRequest(gsonRequestDiy);
                break;
            case 8:
                final MVViewHolder mvViewHolder = (MVViewHolder) holder;
                GsonRequest<RecommainBeanSpare> gsonRequestMV = new GsonRequest<>(arrayList.get(8).getUrl(), RecommainBeanSpare.class,
                        new Response.Listener<RecommainBeanSpare>() {
                            @Override
                            public void onResponse(RecommainBeanSpare response) {
                                mvViewHolder.title.setText(response.getModule().get(11).getTitle());
                                MyImageLoader.myImageLoader(response.getModule().get(11).getPicurl(), mvViewHolder.titleleft);
                                //Picasso.with(mContext).load(response.getModule().get(11).getPicurl()).into(mvViewHolder.titleleft);
                                mvViewHolder.more.setVisibility(View.INVISIBLE);

                                MVAdapter mvAdapter = new MVAdapter(mContext);
                                mvAdapter.setRecommainBeanSpare(response);
                                mvViewHolder.gridView.setNumColumns(3);
                                mvViewHolder.gridView.setAdapter(mvAdapter);

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.getMessage();
                    }
                });
                VolleySingleton.getInstance().addRequest(gsonRequestMV);
                break;
            case 9:
                final LeRadioViewHolder leRadioViewHolder = (LeRadioViewHolder) holder;
                GsonRequest<RecommendNewBean> gsonRequestLR = new GsonRequest<>(arrayList.get(9).getUrl(), RecommendNewBean.class,
                        new Response.Listener<RecommendNewBean>() {
                            @Override
                            public void onResponse(RecommendNewBean response) {
                                leRadioViewHolder.title.setText(response.getModule().get(12).getTitle());
                                MyImageLoader.myImageLoader(response.getModule().get(12).getPicurl(), leRadioViewHolder.titleleft);
                                leRadioViewHolder.more.setText(response.getModule().get(12).getTitle_more());

                                LeRaAdapter leRaAdapter = new LeRaAdapter(mContext);
                                leRaAdapter.setRecommendNewBean(response);
                                leRadioViewHolder.gridView.setNumColumns(3);
                                leRadioViewHolder.gridView.setAdapter(leRaAdapter);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                error.getMessage();
                            }
                        });
                VolleySingleton.getInstance().addRequest(gsonRequestLR);
                break;
            case 10:
                final ColumPageViewHolder columPageViewHolder = (ColumPageViewHolder) holder;
                GsonRequest<RecommendNewBean> gsonRequestPage = new GsonRequest<>(arrayList.get(10).getUrl(), RecommendNewBean.class,
                        new Response.Listener<RecommendNewBean>() {
                            @Override
                            public void onResponse(RecommendNewBean response) {
                                columPageViewHolder.title.setText(response.getModule().get(13).getTitle());
                                MyImageLoader.myImageLoader(response.getModule().get(13).getPicurl(), columPageViewHolder.titleleft);
                                columPageViewHolder.more.setVisibility(View.INVISIBLE);

                                ColumAdapter columAdapter = new ColumAdapter(mContext);
                                columAdapter.setRecommendNewBean(response);
                                columPageViewHolder.gridView.setNumColumns(1);
                                columPageViewHolder.gridView.setAdapter(columAdapter);


                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                error.getMessage();
                            }
                        });
                VolleySingleton.getInstance().addRequest(gsonRequestPage);
                break;


        }
    }

    @Override
    public int getItemViewType(int position) {
        return arrayList.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    class AdViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        View line;

        public AdViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image_head);
            line = (View) itemView.findViewById(R.id.line_ad);
        }
    }

    class HeadViewHolder extends RecyclerView.ViewHolder {
        ViewPager viewPager;
        ImageView imageView1, imageView2, imageView3, imageView4;
        TextView textView1, textView2, textView3, textView4;
        LinearLayout ll1, ll2, ll3, ll4;

        public HeadViewHolder(View itemView) {
            super(itemView);
            viewPager = (ViewPager) itemView.findViewById(R.id.Carousel_recommend);
            imageView1 = (ImageView) itemView.findViewById(R.id.btn_head1);
            imageView2 = (ImageView) itemView.findViewById(R.id.btn_head2);
            imageView3 = (ImageView) itemView.findViewById(R.id.btn_head3);
            imageView4 = (ImageView) itemView.findViewById(R.id.btn_head4);
            textView1 = (TextView) itemView.findViewById(R.id.te_head1);
            textView2 = (TextView) itemView.findViewById(R.id.te_head2);
            textView3 = (TextView) itemView.findViewById(R.id.te_head3);
            textView4 = (TextView) itemView.findViewById(R.id.te_head4);

            ll1 = (LinearLayout) itemView.findViewById(R.id.singger);
            ll2 = (LinearLayout) itemView.findViewById(R.id.songtype);
            ll3 = (LinearLayout) itemView.findViewById(R.id.radiofm);
            ll4 = (LinearLayout) itemView.findViewById(R.id.viparea);

        }
    }

    class SongLViewHolder extends RecyclerView.ViewHolder {
        ImageView titleleft;
        TextView title, more;
        MyGridView gridView;

        public SongLViewHolder(View itemView) {
            super(itemView);
            titleleft = (ImageView) itemView.findViewById(R.id.img_recommend1);
            title = (TextView) itemView.findViewById(R.id.title_recommend1);
            more = (TextView) itemView.findViewById(R.id.more_recommend1);
            gridView = (MyGridView) itemView.findViewById(R.id.content_recommend1);
        }
    }

    class NewDiskViewHolder extends RecyclerView.ViewHolder {
        ImageView titleleft;
        TextView title, more;
        MyGridView gridView;

        public NewDiskViewHolder(View itemView) {
            super(itemView);
            titleleft = (ImageView) itemView.findViewById(R.id.img_recommend1);
            title = (TextView) itemView.findViewById(R.id.title_recommend1);
            more = (TextView) itemView.findViewById(R.id.more_recommend1);
            gridView = (MyGridView) itemView.findViewById(R.id.content_recommend1);
        }
    }

    class HotViewHolder extends RecyclerView.ViewHolder {
        ImageView titleleft;
        TextView title, more;
        MyGridView gridView;

        public HotViewHolder(View itemView) {
            super(itemView);
            titleleft = (ImageView) itemView.findViewById(R.id.img_recommend1);
            title = (TextView) itemView.findViewById(R.id.title_recommend1);
            more = (TextView) itemView.findViewById(R.id.more_recommend1);
            gridView = (MyGridView) itemView.findViewById(R.id.content_recommend1);
        }
    }

    class RadioViewHolder extends RecyclerView.ViewHolder {
        ImageView titleleft;
        TextView title, more;
        MyGridView gridView;

        public RadioViewHolder(View itemView) {
            super(itemView);
            titleleft = (ImageView) itemView.findViewById(R.id.img_recommend1);
            title = (TextView) itemView.findViewById(R.id.title_recommend1);
            more = (TextView) itemView.findViewById(R.id.more_recommend1);
            gridView = (MyGridView) itemView.findViewById(R.id.content_recommend1);
        }
    }

    class TodayViewHolder extends RecyclerView.ViewHolder {
        ImageView titleleft;
        TextView title, more;
        MyGridView gridView;

        public TodayViewHolder(View itemView) {
            super(itemView);
            titleleft = (ImageView) itemView.findViewById(R.id.img_recommend1);
            title = (TextView) itemView.findViewById(R.id.title_recommend1);
            more = (TextView) itemView.findViewById(R.id.more_recommend1);
            gridView = (MyGridView) itemView.findViewById(R.id.content_recommend1);
        }
    }

    class DiyViewHolder extends RecyclerView.ViewHolder {
        ImageView titleleft;
        TextView title, more;
        MyGridView gridView;

        public DiyViewHolder(View itemView) {
            super(itemView);
            titleleft = (ImageView) itemView.findViewById(R.id.img_recommend1);
            title = (TextView) itemView.findViewById(R.id.title_recommend1);
            more = (TextView) itemView.findViewById(R.id.more_recommend1);
            gridView = (MyGridView) itemView.findViewById(R.id.content_recommend1);
        }
    }

    class MVViewHolder extends RecyclerView.ViewHolder {
        ImageView titleleft;
        TextView title, more;
        MyGridView gridView;

        public MVViewHolder(View itemView) {
            super(itemView);
            titleleft = (ImageView) itemView.findViewById(R.id.img_recommend1);
            title = (TextView) itemView.findViewById(R.id.title_recommend1);
            more = (TextView) itemView.findViewById(R.id.more_recommend1);
            gridView = (MyGridView) itemView.findViewById(R.id.content_recommend1);
        }
    }


    class LeRadioViewHolder extends RecyclerView.ViewHolder {
        ImageView titleleft;
        TextView title, more;
        MyGridView gridView;

        public LeRadioViewHolder(View itemView) {
            super(itemView);
            titleleft = (ImageView) itemView.findViewById(R.id.img_recommend1);
            title = (TextView) itemView.findViewById(R.id.title_recommend1);
            more = (TextView) itemView.findViewById(R.id.more_recommend1);
            gridView = (MyGridView) itemView.findViewById(R.id.content_recommend1);
        }
    }

    class ColumPageViewHolder extends RecyclerView.ViewHolder {
        ImageView titleleft;
        TextView title, more;
        MyGridView gridView;

        public ColumPageViewHolder(View itemView) {
            super(itemView);
            titleleft = (ImageView) itemView.findViewById(R.id.img_recommendl);
            title = (TextView) itemView.findViewById(R.id.title_recommendl);
            more = (TextView) itemView.findViewById(R.id.more_recommendl);
            gridView = (MyGridView) itemView.findViewById(R.id.content_recommendl);
        }
    }


    //轮播图
    private boolean startin = true;
    private boolean flag = true;

    private void roating(final HeadViewHolder headViewHolder) {
        mHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                headViewHolder.viewPager.setCurrentItem(headViewHolder.viewPager.getCurrentItem() + 1);
                return false;
            }
        });
        if (startin) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (flag) {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        mHandler.sendEmptyMessage(0);
                    }
                }
            }).start();
            startin = false;
        }
    }

    public interface Changed {
        void change();
    }

}

