package lanou.baidu.album.recommend.recomsonadapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import lanou.baidu.R;
import lanou.baidu.album.recommend.recommendbean.RecommainBean;
import lanou.baidu.base.MyImageLoader;

/**
 * Created by dllo on 16/9/21.
 */
public class SongLAdapter extends BaseAdapter{
    Context mcontext;

    public SongLAdapter(Context mcontext) {
        this.mcontext = mcontext;
    }
    RecommainBean recommainBean;

    public void setRecommainBean(RecommainBean recommainBean) {
        this.recommainBean = recommainBean;
    }

    @Override
    public int getCount() {
        return recommainBean.getResult().getDiy().getResult().size();
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
        SLitemViewHolder sLitemViewHolder = null;
        if(convertView==null){
            convertView = LayoutInflater.from(mcontext).inflate(R.layout.item_gridresonglist,parent,false);
            sLitemViewHolder = new SLitemViewHolder(convertView);
            convertView.setTag(sLitemViewHolder);
        }else {
            sLitemViewHolder = (SLitemViewHolder)convertView.getTag();
        }
        sLitemViewHolder.textView.setText(recommainBean.getResult().getDiy().getResult().get(position).getTitle());
        MyImageLoader.myImageLoader(recommainBean.getResult().getDiy().getResult().get(position).getPic(),sLitemViewHolder.imageView);
        //Picasso.with(mcontext).load(recommainBean.getResult().getDiy().getResult().get(position).getPic()).into(sLitemViewHolder.imageView);
        Log.d("SongLAdapter", "recommainBean.getResult().getDiy().getResult().get(position).getListenum():" + recommainBean.getResult().getDiy().getResult().get(position).getListenum());
        sLitemViewHolder.num.setText(recommainBean.getResult().getDiy().getResult().get(position).getListenum()+"");
        Log.d("SongLAdapter", recommainBean.getResult().getDiy().getResult().get(position).getTitle());
        return convertView;
    }

    class SLitemViewHolder{
        ImageView imageView;
        TextView textView,num;
        public SLitemViewHolder(View convertView){
            imageView = (ImageView) convertView.findViewById(R.id.show_picture_songl);
            textView = (TextView) convertView.findViewById(R.id.title_songl);
            num = (TextView) convertView.findViewById(R.id.lisnum);
        }
    }
}
