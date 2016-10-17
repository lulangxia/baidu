package lanou.baidu.album.recommend.recomsonadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import lanou.baidu.R;
import lanou.baidu.bean.recommendbean.RecommainBean;
import lanou.baidu.base.MyImageLoader;

/**
 * Created by dllo on 16/9/21.
 */
public class NewDAdapter extends BaseAdapter {
    Context mContext;

    public NewDAdapter(Context mContext) {
        this.mContext = mContext;
    }

    RecommainBean recommainBean;

    public void setRecommainBean(RecommainBean recommainBean) {
        this.recommainBean = recommainBean;
    }

    @Override
    public int getCount() {
        return 6;
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
        NewDViewHolder newDViewHolder = null;
        if(convertView==null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_newdisk,parent,false);
            newDViewHolder = new NewDViewHolder(convertView);
            convertView.setTag(newDViewHolder);
        }else {
            newDViewHolder = (NewDViewHolder)convertView.getTag();
        }
        newDViewHolder.song.setText(recommainBean.getResult().getMix_1().getResult().get(position).getTitle());
        newDViewHolder.singer.setText(recommainBean.getResult().getMix_1().getResult().get(position).getAuthor());
        //Picasso.with(mContext).load(recommainBean.getResult().getMix_1().getResult().get(position).getPic()).into(newDViewHolder.imageView);
        MyImageLoader.myImageLoader(recommainBean.getResult().getMix_1().getResult().get(position).getPic(),newDViewHolder.imageView);


        return convertView;
    }

    class NewDViewHolder{
        ImageView imageView;
        TextView song,singer;
        public NewDViewHolder(View convertView){
            imageView = (ImageView) convertView.findViewById(R.id.show_picture_newdisk);
            song = (TextView) convertView.findViewById(R.id.song_newdisk);
            singer = (TextView) convertView.findViewById(R.id.singer_newdisk);
        }
    }
}
