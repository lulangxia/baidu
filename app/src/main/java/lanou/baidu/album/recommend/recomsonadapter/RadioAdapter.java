package lanou.baidu.album.recommend.recomsonadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import lanou.baidu.R;
import lanou.baidu.album.recommend.recommendbean.RecommainBean;
import lanou.baidu.album.recommend.recommendbean.RecommainBeanSpare;
import lanou.baidu.base.MyImageLoader;

/**
 * Created by dllo on 16/9/21.
 */
public class RadioAdapter extends BaseAdapter {
    Context context;
    RecommainBean recommainBean;
    RecommainBeanSpare recommainBeanSpare;

    public void setRecommainBeanSpare(RecommainBeanSpare recommainBeanSpare) {
        this.recommainBeanSpare = recommainBeanSpare;
    }

    public RadioAdapter(Context context) {
        this.context = context;
    }

    public void setRecommainBean(RecommainBean recommainBean) {
        this.recommainBean = recommainBean;
    }

    @Override
    public int getCount() {
        return 4;
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
        RadioViewHolder radioViewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_radio, parent, false);
            radioViewHolder = new RadioViewHolder(convertView);
            convertView.setTag(radioViewHolder);
        } else {
            radioViewHolder = (RadioViewHolder) convertView.getTag();
        }
        switch (position){
            case 0:
                radioViewHolder.imageView.setBackgroundResource(R.mipmap.img_recommend_lebo_cyan);
                break;
            case 1:
                radioViewHolder.imageView.setBackgroundResource(R.mipmap.img_recommend_lebo_orange);
                break;
            case 2:
                radioViewHolder.imageView.setBackgroundResource(R.mipmap.img_recommend_lebo_blue);
                break;
            case 3:
                radioViewHolder.imageView.setBackgroundResource(R.mipmap.img_recommend_lebo_green);
                break;
        }


        radioViewHolder.textView.setText(recommainBeanSpare.getResult().getScene().getResultscene().getAction().get(position).getScene_name());
        MyImageLoader.myImageLoader(recommainBeanSpare.getResult().getScene().getResultscene().getAction().get(position).getIcon_android(),radioViewHolder.imageView);

        //Picasso.with(context).load(recommainBeanSpare.getResult().getScene().getResultscene().getAction().get(position).getIcon_android()).into(radioViewHolder.imageView);

        return convertView;
    }

    class RadioViewHolder {
        ImageView imageView;
        TextView textView;

        public RadioViewHolder(View convertView) {
            imageView = (ImageView) convertView.findViewById(R.id.img_radio);
            textView = (TextView) convertView.findViewById(R.id.text_radio);

        }
    }
}

