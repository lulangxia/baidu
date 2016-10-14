package lanou.baidu.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import lanou.baidu.R;
import lanou.baidu.eventBus.MyMusicBean;

/**
 * Created by dllo on 16/10/14.
 */
public class PopAdapter extends BaseAdapter {
    Context context;
    MyMusicBean myMusicBean;

    public void setMyMusicBean(MyMusicBean myMusicBean) {
        this.myMusicBean = myMusicBean;
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
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PopViewHolder popViewHolder = null;
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
        }
        return convertView;
    }

    class PopViewHolder {
        TextView song, singer;

        public PopViewHolder(View convertView) {
            song = (TextView) convertView.findViewById(R.id.songname_popitem);

            singer = (TextView) convertView.findViewById(R.id.singer_poptime);
        }
    }
}
