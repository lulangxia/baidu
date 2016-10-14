package lanou.baidu.album.video;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import lanou.baidu.R;
import lanou.baidu.base.MyImageLoader;

/**
 * Created by dllo on 16/10/9.
 */
public class VIdeoAdapter2 extends BaseAdapter {
    Context context;
    VideoBean videoBean;
    boolean down = false;


    public void setDown(boolean down) {
        this.down = down;
    }

    public VIdeoAdapter2(Context context) {
        this.context = context;
    }

    public void setVideoBean(VideoBean videoBean1) {
        if (down) {
            videoBean.getResult().getMv_list().addAll(videoBean1.getResult().getMv_list());
        } else {
            this.videoBean = videoBean1;
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return videoBean.getResult().getMv_list().size();
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
        VideoHolder videoHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_videofragment, parent, false);
            videoHolder = new VideoHolder(convertView);
            convertView.setTag(videoHolder);
        } else {
            videoHolder = (VideoHolder) convertView.getTag();
        }
        MyImageLoader.myImageLoader(videoBean.getResult().getMv_list().get(position).getThumbnail2(), videoHolder.imageView);
        videoHolder.song.setText(videoBean.getResult().getMv_list().get(position).getTitle());
        videoHolder.singer.setText(videoBean.getResult().getMv_list().get(position).getArtist());


        return convertView;
    }

    public class VideoHolder {
        ImageView imageView;
        TextView song, singer;

        public VideoHolder(View convertView) {
            imageView = (ImageView) convertView.findViewById(R.id.img_video_item);
            song = (TextView) convertView.findViewById(R.id.song_video_item);
            singer = (TextView) convertView.findViewById(R.id.singer_video_item);
        }
    }
}
