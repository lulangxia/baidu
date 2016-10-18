package lanou.baidu.album.recommend.recomsonadapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import lanou.baidu.R;
import lanou.baidu.tools.MyImageLoader;
import lanou.baidu.bean.recommendbean.RecommendNewBean;

/**
 * Created by dllo on 16/9/20.
 */
public class HeadAdapter extends PagerAdapter {
    Context context;

    public HeadAdapter(Context context) {
        this.context = context;
    }

    private RecommendNewBean recommendNew;

    public void setRecommendNew(RecommendNewBean recommendNew) {
        this.recommendNew = recommendNew;
    }



    @Override
    public int getCount() {
        return recommendNew.getResult().getFocus().getResult().size() * 1000;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_head, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.image_head);


        MyImageLoader.myImageLoader(recommendNew.getResult().getFocus().getResult().get(position % recommendNew.getResult().getFocus().getResult().size()).getRandpic(),imageView);
        //Picasso.with(context).load(recommendNew.getResult().getFocus().getResult().get(position % recommendNew.getResult().getFocus().getResult().size()).getRandpic()).into(imageView);
        //Log.d("HeadAdapter", arrayList.get(0).getPic().get(position).getRandpic());
        container.addView(view);

        return view;


    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }
}
