package lanou.baidu.album.recommend.recomsonadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import lanou.baidu.R;
import lanou.baidu.bean.recommendbean.RecommainBeanSpare;
import lanou.baidu.myview.CircleImageView;
import lanou.baidu.tools.MyImageLoader;

/**
 * Created by dllo on 16/9/21.
 */
public class TodayAdapter extends BaseAdapter {
    Context context;

    public TodayAdapter(Context context) {
        this.context = context;
    }

    private RecommainBeanSpare recommainBeanSpare;

    public void setRecommainBeanSpare(RecommainBeanSpare recommainBeanSpare) {
        this.recommainBeanSpare = recommainBeanSpare;
    }

    @Override
    public int getCount() {
        return 3;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        TodayViewHolder todayViewHolder = null;
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_today,parent,false);
            todayViewHolder = new TodayViewHolder(convertView);
            convertView.setTag(todayViewHolder);
        }else {
            todayViewHolder = (TodayViewHolder)convertView.getTag();
        }
        todayViewHolder.song.setText(recommainBeanSpare.getResult().getRecsong().getResultrecsong().get(position).getTitle());
        todayViewHolder.singer.setText(recommainBeanSpare.getResult().getRecsong().getResultrecsong().get(position).getAuthor());
        //Picasso.with(context).load(recommainBeanSpare.getResult().getRecsong().getResultrecsong().get(position).getPic_premium()).into(todayViewHolder.imageView);

        MyImageLoader.myImageLoader(recommainBeanSpare.getResult().getRecsong().getResultrecsong().get(position).getPic_premium(),todayViewHolder.imageView);

        return convertView;
    }

    class TodayViewHolder {
        CircleImageView imageView;
        TextView song, singer;
        public TodayViewHolder(View convertView) {
            imageView = (CircleImageView) convertView.findViewById(R.id.img_today);
            song = (TextView)convertView.findViewById(R.id.song_today);
            singer = (TextView) convertView.findViewById(R.id.singer_today);

        }
    }
}
