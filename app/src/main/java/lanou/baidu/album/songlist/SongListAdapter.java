package lanou.baidu.album.songlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import lanou.baidu.R;
import lanou.baidu.request.GsonRequest;
import lanou.baidu.tools.MyImageLoader;
import lanou.baidu.tools.URLVlaues;
import lanou.baidu.request.VolleySingleton;
import lanou.baidu.bean.MediaLIstBean;
import lanou.baidu.bean.MusicBean;
import lanou.baidu.bean.MyMusicBean;
import lanou.baidu.bean.SongLIstHotBean;
import lanou.baidu.main.MainActivity;
import lanou.baidu.musicmedia.MediaFragment;

/**
 * Created by dllo on 16/9/23.
 */
public class SongListAdapter extends BaseAdapter {


    Context context;
    boolean down = false;
    private MusicBean musicBean;
    private MyMusicBean myMusicBean;

    ArrayList<SongLIstHotBean> arrayList = new ArrayList<>();




    public void setDown(boolean down) {
        this.down = down;
    }

    public void setArrayList(ArrayList<SongLIstHotBean> arrayListnew) {
        if (down) {
            arrayList.addAll(arrayListnew);
        } else {
            this.arrayList = arrayListnew;
        }
        notifyDataSetChanged();

    }


    public SongListAdapter(Context context) {
        this.context = context;
    }


    @Override
    public int getCount() {
        return arrayList.get(0).getDiyInfo().size();
        //return arrayListold.get(0).getContent().size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        SLitemViewHolder sLitemViewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.songlist_item, parent, false);
            sLitemViewHolder = new SLitemViewHolder(convertView);
            convertView.setTag(sLitemViewHolder);
        } else {
            sLitemViewHolder = (SLitemViewHolder) convertView.getTag();
        }
        //Picasso.with(context).load(arrayList.get(0).getDiyInfo().get(position).getList_pic()).into(sLitemViewHolder.imageView);
        MyImageLoader.myImageLoader(arrayList.get(0).getDiyInfo().get(position).getList_pic(), sLitemViewHolder.imageView);
        sLitemViewHolder.textView.setText(arrayList.get(0).getDiyInfo().get(position).getTitle());
        sLitemViewHolder.num.setText(arrayList.get(0).getDiyInfo().get(position).getListen_num() + "");
        sLitemViewHolder.auther.setText("by " + arrayList.get(0).getDiyInfo().get(position).getUsername());

        sLitemViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String listid = arrayList.get(0).getDiyInfo().get(position).getList_id();
                String url = URLVlaues.SONGLIST_DETAIL_Front + listid + URLVlaues.SONGLIST_DETAIL_BEHIND;
                MediaFragment mediaFragment = new MediaFragment();
                mediaFragment.setUrl(url);
                MainActivity.replacefrag(mediaFragment);
            }
        });

        sLitemViewHolder.playall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String listid = arrayList.get(0).getDiyInfo().get(position).getList_id();
                String url = URLVlaues.SONGLIST_DETAIL_Front + listid + URLVlaues.SONGLIST_DETAIL_BEHIND;
                MediaFragment mediaFragment = new MediaFragment();
                mediaFragment.setUrl(url);
                GsonRequest<MediaLIstBean> requestmedialist = new GsonRequest<MediaLIstBean>(url, MediaLIstBean.class,
                        new Response.Listener<MediaLIstBean>() {
                            @Override
                            public void onResponse(final MediaLIstBean response) {

                                ArrayList<MusicBean> arraylist = new ArrayList<>();
                                for (int i = 0; i < response.getContent().size(); i++) {
                                    musicBean = new MusicBean(response.getContent().get(i).getTitle(), response.getContent().get(i).getAuthor(), response.getContent().get(i).getSong_id());
                                    arraylist.add(musicBean);
                                }
                                myMusicBean = new MyMusicBean();
                                myMusicBean.setMusicBeen(arraylist);
                                myMusicBean.setPosition(0);
                                EventBus.getDefault().post(myMusicBean);


                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.getMessage();
                    }
                });
                VolleySingleton.getInstance().addRequest(requestmedialist);
            }
        });
        return convertView;
    }

    class SLitemViewHolder {
        ImageView imageView, playall;
        TextView textView, num, auther;

        public SLitemViewHolder(View convertView) {
            imageView = (ImageView) convertView.findViewById(R.id.show_picture_songlist);
            textView = (TextView) convertView.findViewById(R.id.title_songlist);
            num = (TextView) convertView.findViewById(R.id.lisnumlist);
            auther = (TextView) convertView.findViewById(R.id.by_auther);
            playall = (ImageView) convertView.findViewById(R.id.songlist_play);
        }
    }


}
