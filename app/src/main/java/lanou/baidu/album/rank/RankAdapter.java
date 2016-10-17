package lanou.baidu.album.rank;

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
import lanou.baidu.base.GsonRequest;
import lanou.baidu.base.MyImageLoader;
import lanou.baidu.base.URLVlaues;
import lanou.baidu.base.VolleySingleton;
import lanou.baidu.eventbus.MusicBean;
import lanou.baidu.eventbus.MyMusicBean;
import lanou.baidu.rankplayer.RankPlayBean;
import lanou.baidu.rankplayer.RankPlayFragment;

/**
 * Created by dllo on 16/9/20.
 */
public class RankAdapter extends BaseAdapter {
    Context context;
    private MusicBean musicBean;
    private MyMusicBean myMusicBean;

    public RankAdapter(Context context) {
        this.context = context;
    }

    ArrayList<RankBean> arrayList = new ArrayList<>();

    public void setArrayList(ArrayList<RankBean> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.get(0).getContent().size();
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
        RankViewHolder rankViewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_rank, null);
            rankViewHolder = new RankViewHolder(convertView);
            convertView.setTag(rankViewHolder);
        } else {
            rankViewHolder = (RankViewHolder) convertView.getTag();
        }

        MyImageLoader.myImageLoader(arrayList.get(0).getContent().get(position).getPic_s192(),rankViewHolder.imageView);
        //Picasso.with(context).load(arrayList.get(0).getContent().get(position).getPic_s192()).into(rankViewHolder.imageView);

        rankViewHolder.title.setText(arrayList.get(0).getContent().get(position).getName());
        for (int i = 0; i < 3; i++) {
            String song = arrayList.get(0).getContent().get(position).getRankContentBean().get(i).getTitle();
            String singer = arrayList.get(0).getContent().get(position).getRankContentBean().get(i).getAuthor();
            switch (i) {
                case 0:
                    rankViewHolder.text1.setText(1+" " + song + "-" + singer);
                    break;
                case 1:
                    rankViewHolder.text2.setText(2+" " + song + "-" + singer);
                    break;
                case 2:
                    rankViewHolder.text3.setText(3+" " + song + "-" + singer);
                    break;
            }
        }

        rankViewHolder.rankplayall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               int type = arrayList.get(0).getContent().get(position).getType();
               String url = URLVlaues.TOP_SONG_FRONT + type + URLVlaues.TOP_SONG_BEHIND;
                RankPlayFragment rankPlayFragment = new RankPlayFragment();
                rankPlayFragment.setUrl(url);
                GsonRequest<RankPlayBean> requestmedialist = new GsonRequest<RankPlayBean>(url, RankPlayBean.class,
                        new Response.Listener<RankPlayBean>() {
                            @Override
                            public void onResponse(final RankPlayBean response) {

                                ArrayList<MusicBean> arraylist = new ArrayList<>();
                                for (int i = 0; i < response.getSong_list().size(); i++) {
                                    musicBean = new MusicBean(response.getSong_list().get(i).getTitle(), response.getSong_list().get(i).getAuthor(), response.getSong_list().get(i).getSong_id());
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

    class RankViewHolder {
        ImageView imageView,rankplayall;
        TextView title, text1, text2, text3;

        public RankViewHolder(View convertView) {
            imageView = (ImageView) convertView.findViewById(R.id.rank_image);
            title = (TextView) convertView.findViewById(R.id.rankname);
            text1 = (TextView) convertView.findViewById(R.id.rankone);
            text2 = (TextView) convertView.findViewById(R.id.ranktwo);
            text3 = (TextView) convertView.findViewById(R.id.rankthree);
            rankplayall = (ImageView) convertView.findViewById(R.id.rank_play);
        }
    }



}
