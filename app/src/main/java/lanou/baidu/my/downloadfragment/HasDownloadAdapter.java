package lanou.baidu.my.downloadfragment;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import lanou.baidu.R;
import lanou.baidu.bean.MyMusicBean;

/**
 * Created by dllo on 16/10/17.
 */
public class HasDownloadAdapter extends BaseAdapter {
    Context mContext;

    public HasDownloadAdapter(Context mContext) {
        this.mContext = mContext;
    }

    MyMusicBean myMusicBean;

    public void setMyMusicBean(MyMusicBean myMusicBean) {
        this.myMusicBean = myMusicBean;
    }


    @Override
    public int getCount() {
        return myMusicBean == null ? 0 : myMusicBean.getMusicBeen().size();
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
        MyViewHolder myViewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_myloaclmusic, null);
            myViewHolder = new MyViewHolder(convertView);
            convertView.setTag(myViewHolder);
        } else {
            myViewHolder = (MyViewHolder) convertView.getTag();
        }
        Log.d("MyMyAdapter", myMusicBean.getMusicBeen().get(position).getSongName());
        myViewHolder.song.setText(myMusicBean.getMusicBeen().get(position).getSongName());
        myViewHolder.singer.setText(myMusicBean.getMusicBeen().get(position).getSinger());


        return convertView;
    }

    class MyViewHolder {
        TextView song, singer;

        public MyViewHolder(View convertView) {
            song = (TextView) convertView.findViewById(R.id.song_mylocal);
            singer = (TextView) convertView.findViewById(R.id.singer_mylocal);
        }
    }
}
