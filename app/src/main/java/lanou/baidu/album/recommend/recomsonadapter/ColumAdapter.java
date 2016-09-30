package lanou.baidu.album.recommend.recomsonadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import lanou.baidu.R;
import lanou.baidu.album.recommend.recommendBean.RecommendNewBean;
import lanou.baidu.base.MyImageLoader;

/**
 * Created by dllo on 16/9/22.
 */
public class ColumAdapter extends BaseAdapter {
    Context context;
    RecommendNewBean recommendNewBean;

    public ColumAdapter(Context context) {
        this.context = context;
    }

    public void setRecommendNewBean(RecommendNewBean recommendNewBean) {
        this.recommendNewBean = recommendNewBean;
    }

    @Override
    public int getCount() {
        return recommendNewBean.getResult().getMod_7().getResult().size();
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
        ColumViewHolder columViewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_column, parent, false);
            columViewHolder = new ColumViewHolder(convertView);
            convertView.setTag(columViewHolder);
        } else {
            columViewHolder = (ColumViewHolder) convertView.getTag();
        }
        columViewHolder.title.setText(recommendNewBean.getResult().getMod_7().getResult().get(position).getTitle());
        columViewHolder.comefrom.setText(recommendNewBean.getResult().getMod_7().getResult().get(position).getDesc());
        //Picasso.with(context).load(recommainBeanSpare.getResult().getRecsong().getResultrecsong().get(position).getPic_premium()).into(todayViewHolder.imageView);
        MyImageLoader.myImageLoader(recommendNewBean.getResult().getMod_7().getResult().get(position).getPic(), columViewHolder.imageView);
        columViewHolder.right.setBackgroundResource(R.mipmap.ktv_myksong__details_icon_details);
        return convertView;
    }

    class ColumViewHolder {
        ImageView imageView;
        TextView title, comefrom;
        RadioButton right;

        public ColumViewHolder(View convertView) {
            imageView = (ImageView) convertView.findViewById(R.id.img_today);
            title = (TextView) convertView.findViewById(R.id.song_today);
            comefrom = (TextView) convertView.findViewById(R.id.singer_today);
            right = (RadioButton) convertView.findViewById(R.id.btn_radio);
        }
    }

}
