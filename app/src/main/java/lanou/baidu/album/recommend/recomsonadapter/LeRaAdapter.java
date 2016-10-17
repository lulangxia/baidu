package lanou.baidu.album.recommend.recomsonadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import lanou.baidu.R;
import lanou.baidu.bean.recommendbean.RecommendNewBean;
import lanou.baidu.base.MyImageLoader;

/**
 * Created by dllo on 16/9/22.
 */
public class LeRaAdapter extends BaseAdapter{
    Context mcontext;

    public LeRaAdapter(Context mcontext) {
        this.mcontext = mcontext;
    }
    RecommendNewBean recommendNewBean;

    public void setRecommendNewBean(RecommendNewBean recommendNewBean) {
        this.recommendNewBean = recommendNewBean;
    }

    @Override
    public int getCount() {
        return recommendNewBean.getResult().getRadio().getResult().size();
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
        LRViewHolder lrViewHolder = null;
        if(convertView==null){
            convertView = LayoutInflater.from(mcontext).inflate(R.layout.item_gridresonglist,parent,false);
            lrViewHolder = new LRViewHolder(convertView);
            convertView.setTag(lrViewHolder);
        }else {
            lrViewHolder = (LRViewHolder)convertView.getTag();
        }
        lrViewHolder.textView.setText(recommendNewBean.getResult().getRadio().getResult().get(position).getTitle());
        MyImageLoader.myImageLoader(recommendNewBean.getResult().getRadio().getResult().get(position).getPic(),lrViewHolder.imageView);
        lrViewHolder.num.setVisibility(View.INVISIBLE);
        return convertView;
    }

    class LRViewHolder{
        ImageView imageView;
        TextView textView,num;
        public LRViewHolder(View convertView){
            imageView = (ImageView) convertView.findViewById(R.id.show_picture_songl);
            textView = (TextView) convertView.findViewById(R.id.title_songl);
            num = (TextView) convertView.findViewById(R.id.lisnum);
        }
    }
}
