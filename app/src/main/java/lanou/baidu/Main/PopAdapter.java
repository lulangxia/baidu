package lanou.baidu.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import lanou.baidu.R;
import lanou.baidu.bean.MyMusicBean;

/**
 * Created by dllo on 16/10/14.
 */
public class PopAdapter extends BaseAdapter {
    Context context;
    private MyMusicBean myMusicBean;

    private OndeleteItemListener ondeleteItemListener;
    private PopViewHolder popViewHolder;


    public void setOndeleteItemListener(OndeleteItemListener ondeleteItemListener) {
        this.ondeleteItemListener = ondeleteItemListener;
    }

    public void setMyMusicBean(MyMusicBean myMusicBean) {
        this.myMusicBean = myMusicBean;
        notifyDataSetChanged();
    }

    public PopAdapter(Context context) {
        this.context = context;
    }


    @Override
    public int getCount() {
        return myMusicBean == null ? 1 : myMusicBean.getMusicBeen().size();
    }

    @Override
    public Object getItem(int position) {
        return myMusicBean == null ? null : myMusicBean.getMusicBeen().get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        popViewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_popwin, parent, false);
            popViewHolder = new PopViewHolder(convertView);
            convertView.setTag(popViewHolder);
        } else {
            popViewHolder = (PopViewHolder) convertView.getTag();
        }
        if (myMusicBean == null) {
            popViewHolder.song.setText("您的歌单还没有歌曲哦");
        } else {
            popViewHolder.song.setText(myMusicBean.getMusicBeen().get(position).getSongName());

            popViewHolder.singer.setText(myMusicBean.getMusicBeen().get(position).getSinger());
            // Log.d("PopAdapter", arrayList.get(position));
            popViewHolder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ondeleteItemListener.deleteListener(position);
                }
            });



        }
        return convertView;
    }



    class PopViewHolder {
        TextView song, singer;
        ImageView delete;

        public PopViewHolder(View convertView) {
            song = (TextView) convertView.findViewById(R.id.songname_popitem);

            singer = (TextView) convertView.findViewById(R.id.singer_poptime);
            delete = (ImageView) convertView.findViewById(R.id.delete_popwinitem);
        }
    }


}
