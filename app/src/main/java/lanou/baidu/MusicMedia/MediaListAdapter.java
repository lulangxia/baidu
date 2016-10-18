package lanou.baidu.musicmedia;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import lanou.baidu.R;
import lanou.baidu.bean.MediaLIstBean;

/**
 * Created by dllo on 16/9/29.
 */
public class MediaListAdapter extends RecyclerView.Adapter {
    Context context;

    public MediaListAdapter(Context context) {
        this.context = context;
    }

    private OnRecyclerItemClickListener onRecyclerItemClickListener;

    public void setOnRecyclerItemClickListener(OnRecyclerItemClickListener onRecyclerItemClickListener) {
        this.onRecyclerItemClickListener = onRecyclerItemClickListener;
    }

    private MediaLIstBean mediaLIstBean;

    public void setMediaLIstBean(MediaLIstBean mediaLIstBean) {
        this.mediaLIstBean = mediaLIstBean;
    }

    @Override
    public int getItemViewType(int position) {


        if (mediaLIstBean.getDesc().length() != 0) {
            if (position == 0) {
                return 0;
            } else if (position > 0 && position <= mediaLIstBean.getContent().size()) {
                return 1;
            } else {
                return 2;
            }
        } else {
            if (position < mediaLIstBean.getContent().size()) {
                return 1;
            } else {
                return 2;
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                return new FirstViewHolder(LayoutInflater.from(context).inflate(R.layout.item_first_medialist, parent, false));
            case 1:
                return new MedialistViewHolder(LayoutInflater.from(context).inflate(R.layout.item_medialist, parent, false));
            case 2:
                return new FootViewHolder(LayoutInflater.from(context).inflate(R.layout.media_foot, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        switch (getItemViewType(position)) {
            case 0:
                if (mediaLIstBean.getDesc().length() != 0) {
                    FirstViewHolder firstViewHolder = (FirstViewHolder) holder;
                    firstViewHolder.desc.setText(mediaLIstBean.getDesc());
                }
                break;
            case 1:
                final MedialistViewHolder medialistViewHolder = (MedialistViewHolder) holder;
                if (mediaLIstBean.getDesc().length() == 0) {
                    medialistViewHolder.song.setText(mediaLIstBean.getContent().get(position).getTitle());
                    medialistViewHolder.singer.setText(mediaLIstBean.getContent().get(position).getAuthor());
                    if (mediaLIstBean.getContent().get(position).getHas_mv_mobile() != 0) {
                        medialistViewHolder.mv.setVisibility(View.VISIBLE);
                    }
                    if (mediaLIstBean.getContent().get(position).getHavehigh() != 0) {
                        medialistViewHolder.sq.setVisibility(View.VISIBLE);
                    }
                    if (!mediaLIstBean.getContent().get(position).getIs_ksong().equals("0")) {
                        medialistViewHolder.ksong.setVisibility(View.VISIBLE);
                    }


                } else {
                    medialistViewHolder.song.setText(mediaLIstBean.getContent().get(position - 1).getTitle());
                    medialistViewHolder.singer.setText(mediaLIstBean.getContent().get(position - 1).getAuthor());
                    if (mediaLIstBean.getContent().get(position - 1).getHas_mv_mobile() != 0) {
                        medialistViewHolder.mv.setVisibility(View.VISIBLE);
                    }
                    if (mediaLIstBean.getContent().get(position - 1).getHavehigh() != 0) {
                        medialistViewHolder.sq.setVisibility(View.VISIBLE);
                    }
                    if (!mediaLIstBean.getContent().get(position - 1).getIs_ksong().equals("0")) {
                        medialistViewHolder.ksong.setVisibility(View.VISIBLE);
                    }

                }
                medialistViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("MediaListAdapter", "回调");
                        onRecyclerItemClickListener.onItemClick(v, medialistViewHolder, medialistViewHolder.getAdapterPosition());
                    }
                });
                break;
            case 2:
                FootViewHolder footViewHolder = (FootViewHolder) holder;
                break;
        }
    }

    @Override
    public int getItemCount() {
        if (mediaLIstBean.getDesc().length() == 0) {
            return mediaLIstBean.getContent().size() + 1;
        } else {
            return mediaLIstBean.getContent().size() + 2;
        }
    }

    class MedialistViewHolder extends RecyclerView.ViewHolder {
        TextView song, singer;
        ImageView mv, sq, ksong, more;

        public MedialistViewHolder(View itemView) {
            super(itemView);
            song = (TextView) itemView.findViewById(R.id.song_medialist_item);
            singer = (TextView) itemView.findViewById(R.id.singer_medialist_item);
            mv = (ImageView) itemView.findViewById(R.id.mv_show_item);
            sq = (ImageView) itemView.findViewById(R.id.sq_show_item);
            ksong = (ImageView) itemView.findViewById(R.id.ksong_medialist_item);
            more = (ImageView) itemView.findViewById(R.id.more_medialist_item);
        }
    }

    class FirstViewHolder extends RecyclerView.ViewHolder {
        TextView desc;

        public FirstViewHolder(View itemView) {
            super(itemView);
            desc = (TextView) itemView.findViewById(R.id.decs_item_medialist);

        }
    }



    class FootViewHolder extends RecyclerView.ViewHolder {
        public FootViewHolder(View itemView) {
            super(itemView);
        }
    }



}
