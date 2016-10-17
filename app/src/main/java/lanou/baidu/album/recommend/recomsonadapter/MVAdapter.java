package lanou.baidu.album.recommend.recomsonadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import lanou.baidu.R;
import lanou.baidu.bean.recommendbean.RecommainBeanSpare;
import lanou.baidu.base.MyImageLoader;

/**
 * Created by dllo on 16/9/21.
 */
public class MVAdapter extends BaseAdapter{
    Context context;

    public MVAdapter(Context context) {
        this.context = context;
    }
    RecommainBeanSpare recommainBeanSpare;

    public void setRecommainBeanSpare(RecommainBeanSpare recommainBeanSpare) {
        this.recommainBeanSpare = recommainBeanSpare;
    }

    @Override
    public int getCount() {
        return recommainBeanSpare.getResult().getMix_5().getResultmix5().size();
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
        MVViewHolder mvViewHolder = null;
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_newdisk,parent,false);
            mvViewHolder = new MVViewHolder(convertView);
            convertView.setTag(mvViewHolder);
        }else {
            mvViewHolder = (MVViewHolder)convertView.getTag();
        }
        mvViewHolder.song.setText(recommainBeanSpare.getResult().getMix_5().getResultmix5().get(position).getTitle());
        mvViewHolder.singer.setText(recommainBeanSpare.getResult().getMix_5().getResultmix5().get(position).getAuthor());
        //Picasso.with(context).load(recommainBeanSpare.getResult().getMix_5().getResultmix5().get(position).getPic()).into(mvViewHolder.imageView);
        MyImageLoader.myImageLoader(recommainBeanSpare.getResult().getMix_5().getResultmix5().get(position).getPic(),mvViewHolder.imageView);
        return convertView;
    }

    class MVViewHolder{
        ImageView imageView;
        TextView song,singer;
        public MVViewHolder(View convertView){
            imageView = (ImageView) convertView.findViewById(R.id.show_picture_newdisk);
            song = (TextView) convertView.findViewById(R.id.song_newdisk);
            singer = (TextView) convertView.findViewById(R.id.singer_newdisk);
        }
    }
}
