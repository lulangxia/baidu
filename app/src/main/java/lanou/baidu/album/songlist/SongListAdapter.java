package lanou.baidu.album.songlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import lanou.baidu.MusicMedia.MediaFragment;
import lanou.baidu.R;
import lanou.baidu.base.MainActivity;
import lanou.baidu.base.MyImageLoader;
import lanou.baidu.base.URLVlaues;

/**
 * Created by dllo on 16/9/23.
 */
public class SongListAdapter extends BaseAdapter {

    ArrayList<SongListOldBean> arrayListold = new ArrayList<>();

    Context context;
    boolean down = false;

    ArrayList<SongLIstHotBean> arrayList = new ArrayList<>();


    public void setArrayListold(ArrayList<SongListOldBean> arrayListold1) {
        if (down) {
            arrayListold.addAll(arrayListold1);
        } else {
            this.arrayListold = arrayListold1;
        }
        notifyDataSetChanged();
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setArrayList(ArrayList<SongLIstHotBean> arrayListnew) {
        if (down) {
            arrayList.addAll(arrayListnew);
        } else {
            this.arrayList = arrayListnew;
        }
        notifyDataSetChanged();

    }


    public SongListAdapter(Context context) {
        this.context = context;
    }


    @Override
    public int getCount() {
        return arrayList.get(0).getDiyInfo().size();
        //return arrayListold.get(0).getContent().size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        SLitemViewHolder sLitemViewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.songlist_item, parent, false);
            sLitemViewHolder = new SLitemViewHolder(convertView);
            convertView.setTag(sLitemViewHolder);
        } else {
            sLitemViewHolder = (SLitemViewHolder) convertView.getTag();
        }
        //Picasso.with(context).load(arrayList.get(0).getDiyInfo().get(position).getList_pic()).into(sLitemViewHolder.imageView);
        MyImageLoader.myImageLoader(arrayList.get(0).getDiyInfo().get(position).getList_pic(), sLitemViewHolder.imageView);
        sLitemViewHolder.textView.setText(arrayList.get(0).getDiyInfo().get(position).getTitle());
        sLitemViewHolder.num.setText(arrayList.get(0).getDiyInfo().get(position).getListen_num() + "");
        sLitemViewHolder.auther.setText("by " + arrayList.get(0).getDiyInfo().get(position).getUsername());

        sLitemViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String listid = arrayList.get(0).getDiyInfo().get(position).getList_id();
                String url = URLVlaues.SONGLIST_DETAIL_Front + listid + URLVlaues.SONGLIST_DETAIL_BEHIND;
                MediaFragment mediaFragment = new MediaFragment();
                mediaFragment.setUrl(url);
                MainActivity.replacefrag(mediaFragment);
            }
        });

//
//        MyImageLoader.myImageLoader(arrayListold.get(0).getContent().get(position).getPic_300(), sLitemViewHolder.imageView);
//        sLitemViewHolder.textView.setText(arrayListold.get(0).getContent().get(position).getTitle());
//        sLitemViewHolder.num.setVisibility(View.INVISIBLE);
//        sLitemViewHolder.auther.setText(arrayListold.get(0).getContent().get(position).getDesc());
        return convertView;
    }

    class SLitemViewHolder {
        ImageView imageView;
        TextView textView, num, auther;

        public SLitemViewHolder(View convertView) {
            imageView = (ImageView) convertView.findViewById(R.id.show_picture_songlist);
            textView = (TextView) convertView.findViewById(R.id.title_songlist);
            num = (TextView) convertView.findViewById(R.id.lisnumlist);
            auther = (TextView) convertView.findViewById(R.id.by_auther);
        }
    }
}
