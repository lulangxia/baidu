package lanou.baidu.album.recommend.recomsonadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import lanou.baidu.R;
import lanou.baidu.album.recommend.recommendBean.RecommainBean;
import lanou.baidu.base.MyImageLoader;

/**
 * Created by dllo on 16/9/21.
 */
public class HotAdapter extends BaseAdapter{
    RecommainBean recommainBean;
    Context context;

    public HotAdapter(Context context) {
        this.context = context;
    }

    public void setRecommainBean(RecommainBean recommainBean) {
        this.recommainBean = recommainBean;
    }

    @Override
    public int getCount() {
        return recommainBean.getResult().getMix_22().getResult().size();
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

        HotViewHolder hotViewHolder = null;
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_newdisk,parent,false);
            hotViewHolder = new HotViewHolder(convertView);
            convertView.setTag(hotViewHolder);
        }else {
            hotViewHolder = (HotViewHolder)convertView.getTag();
        }
        hotViewHolder.disk.setText(recommainBean.getResult().getMix_22().getResult().get(position).getTitle());
        hotViewHolder.singer.setText(recommainBean.getResult().getMix_22().getResult().get(position).getAuthor());
        //Picasso.with(context).load(recommainBean.getResult().getMix_22().getResult().get(position).getPic()).into(hotViewHolder.imageView);
        MyImageLoader.myImageLoader(recommainBean.getResult().getMix_22().getResult().get(position).getPic(),hotViewHolder.imageView);
        return convertView;
    }

    class HotViewHolder{
        ImageView imageView;
        TextView disk,singer;
        public HotViewHolder(View convertView){
            imageView = (ImageView) convertView.findViewById(R.id.show_picture_newdisk);
            disk = (TextView) convertView.findViewById(R.id.song_newdisk);
            singer = (TextView) convertView.findViewById(R.id.singer_newdisk);
        }
    }
}
