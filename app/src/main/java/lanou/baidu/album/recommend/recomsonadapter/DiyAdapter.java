package lanou.baidu.album.recommend.recomsonadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import lanou.baidu.R;
import lanou.baidu.album.recommend.recommendbean.RecommainBeanSpare;
import lanou.baidu.base.MyImageLoader;

/**
 * Created by dllo on 16/9/21.
 */
public class DiyAdapter extends BaseAdapter{
    Context context;

    public DiyAdapter(Context context) {
        this.context = context;
    }
    RecommainBeanSpare recommainBeanSpare;

    public void setRecommainBeanSpare(RecommainBeanSpare recommainBeanSpare) {
        this.recommainBeanSpare = recommainBeanSpare;
    }

    @Override
    public int getCount() {
        return recommainBeanSpare.getResult().getMix_9().getResultmix9().size();
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
        DiyViewHolder diyViewHolder = null;
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_gridresonglist,parent,false);
            diyViewHolder = new DiyViewHolder(convertView);
            convertView.setTag(diyViewHolder);
        }else {
            diyViewHolder = (DiyViewHolder)convertView.getTag();
        }
        diyViewHolder.invisible.setVisibility(View.INVISIBLE);
        diyViewHolder.title.setText(recommainBeanSpare.getResult().getMix_9().getResultmix9().get(position).getTitle());
        //Picasso.with(context).load(recommainBeanSpare.getResult().getMix_9().getResultmix9().get(position).getPic()).into(diyViewHolder.imageView);
        MyImageLoader.myImageLoader(recommainBeanSpare.getResult().getMix_9().getResultmix9().get(position).getPic(),diyViewHolder.imageView);


        return convertView;
    }

    class DiyViewHolder{
        ImageView imageView;
        TextView title,invisible;
        public DiyViewHolder(View convertView){
            imageView = (ImageView) convertView.findViewById(R.id.show_picture_songl);
            title = (TextView) convertView.findViewById(R.id.title_songl);
            invisible = (TextView) convertView.findViewById(R.id.lisnum);
        }
    }
}
