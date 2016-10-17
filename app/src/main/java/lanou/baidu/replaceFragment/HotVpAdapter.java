package lanou.baidu.replacefragment;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import lanou.baidu.R;
import lanou.baidu.base.MyImageLoader;
import lanou.baidu.bean.HotSIngerBean;

/**
 * Created by dllo on 16/9/26.
 */
public class HotVpAdapter extends PagerAdapter {
    Context context;
    ArrayList<HotSIngerBean> arrayList = new ArrayList<>();

    public HotVpAdapter(Context context) {
        this.context = context;
    }

    public void setArrayList(ArrayList<HotSIngerBean> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_vp_singerhot,container,false);
        ImageView imageView1 = (ImageView) view.findViewById(R.id.img_item_singervp1);
        TextView textView1 = (TextView) view.findViewById(R.id.te_singername_hot1);
        ImageView imageView2 = (ImageView) view.findViewById(R.id.img_item_singervp2);
        TextView textView2 = (TextView)view.findViewById(R.id.te_singername_hot2);
        ImageView imageView3 = (ImageView) view.findViewById(R.id.img_item_singervp3);
        TextView textView3 = (TextView) view.findViewById(R.id.te_singername_hot3);
        MyImageLoader.myImageLoader(arrayList.get(0).getArtist().get(position*3).getAvatar_big(),imageView1);
        MyImageLoader.myImageLoader(arrayList.get(0).getArtist().get(position*3+1).getAvatar_big(),imageView2);
        MyImageLoader.myImageLoader(arrayList.get(0).getArtist().get(position*3+2).getAvatar_big(),imageView3);


        textView1.setText(arrayList.get(0).getArtist().get(position*3).getName());
        textView2.setText(arrayList.get(0).getArtist().get(position*3+1).getName());
        textView3.setText(arrayList.get(0).getArtist().get(position*3+2).getName());
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
       // super.destroyItem(container, position, object);
        View view = LayoutInflater.from(context).inflate(R.layout.item_vp_singerhot,container,false);
        container.removeView(view);
    }
}
