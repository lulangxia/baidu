package lanou.baidu.album.rank;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import lanou.baidu.R;
import lanou.baidu.base.MyImageLoader;

/**
 * Created by dllo on 16/9/20.
 */
public class RankAdapter extends BaseAdapter {
    Context context;

    public RankAdapter(Context context) {
        this.context = context;
    }

    ArrayList<RankBean> arrayList = new ArrayList<>();

    public void setArrayList(ArrayList<RankBean> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.get(0).getContent().size();
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
        RankViewHolder rankViewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_rank, null);
            rankViewHolder = new RankViewHolder(convertView);
            convertView.setTag(rankViewHolder);
        } else {
            rankViewHolder = (RankViewHolder) convertView.getTag();
        }

        MyImageLoader.myImageLoader(arrayList.get(0).getContent().get(position).getPic_s192(),rankViewHolder.imageView);
        //Picasso.with(context).load(arrayList.get(0).getContent().get(position).getPic_s192()).into(rankViewHolder.imageView);

        rankViewHolder.title.setText(arrayList.get(0).getContent().get(position).getName());
        for (int i = 0; i < 3; i++) {
            String song = arrayList.get(0).getContent().get(position).getRankContentBean().get(i).getTitle();
            String singer = arrayList.get(0).getContent().get(position).getRankContentBean().get(i).getAuthor();
            switch (i) {
                case 0:
                    rankViewHolder.text1.setText(1+" " + song + "-" + singer);
                    break;
                case 1:
                    rankViewHolder.text2.setText(2+" " + song + "-" + singer);
                    break;
                case 2:
                    rankViewHolder.text3.setText(3+" " + song + "-" + singer);
                    break;
            }
        }


        return convertView;
    }

    class RankViewHolder {
        ImageView imageView;
        TextView title, text1, text2, text3;

        public RankViewHolder(View convertView) {
            imageView = (ImageView) convertView.findViewById(R.id.rank_image);
            title = (TextView) convertView.findViewById(R.id.rankname);
            text1 = (TextView) convertView.findViewById(R.id.rankone);
            text2 = (TextView) convertView.findViewById(R.id.ranktwo);
            text3 = (TextView) convertView.findViewById(R.id.rankthree);
        }
    }



}
