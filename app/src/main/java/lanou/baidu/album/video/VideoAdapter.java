package lanou.baidu.album.video;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import lanou.baidu.R;
import lanou.baidu.base.MyImageLoader;
import lanou.baidu.bean.VideoBean;

/**
 * Created by dllo on 16/9/28.
 */
public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoHolder> {
    Context context;
    ArrayList<VideoBean> arrayList;
    boolean down = false;
    VideoBean videoBean;



    public void setDown(boolean down) {
        this.down = down;
    }

    public VideoAdapter(Context context) {
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
    public VideoAdapter.VideoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_videofragment, parent, false);
        VideoHolder videoHolder = new VideoHolder(view);
        return videoHolder;
    }

    @Override
    public void onBindViewHolder(VideoAdapter.VideoHolder holder, int position) {
        MyImageLoader.myImageLoader(videoBean.getResult().getMv_list().get(position).getThumbnail2(), holder.imageView);
        holder.song.setText(videoBean.getResult().getMv_list().get(position).getTitle());
        holder.singer.setText(videoBean.getResult().getMv_list().get(position).getArtist());
    }

    @Override
    public int getItemCount() {
        return videoBean.getResult().getMv_list().size();
    }

    public class VideoHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView song, singer;

        public VideoHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.img_video_item);
            song = (TextView) itemView.findViewById(R.id.song_video_item);
            singer = (TextView) itemView.findViewById(R.id.singer_video_item);
        }
    }
}
