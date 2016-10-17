package lanou.baidu.rankplayer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import lanou.baidu.R;
import lanou.baidu.base.MyImageLoader;
import lanou.baidu.musicmedia.OnRecyclerItemClickListener;

/**
 * Created by dllo on 16/10/15.
 */
public class RankPlayAdapter extends RecyclerView.Adapter {
    Context context;

    public RankPlayAdapter(Context context) {
        this.context = context;
    }

    RankPlayBean rankBean;

    public void setRankBean(RankPlayBean rankBean) {
        this.rankBean = rankBean;
    }

    OnRecyclerItemClickListener onRecyclerItemClickListener;

    public void setOnRecyclerItemClickListener(OnRecyclerItemClickListener onRecyclerItemClickListener) {
        this.onRecyclerItemClickListener = onRecyclerItemClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < rankBean.getSong_list().size()) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                return new RankViewHolder(LayoutInflater.from(context).inflate(R.layout.item_rankplay, parent, false));
            case 1:
                return new FootViewHolder(LayoutInflater.from(context).inflate(R.layout.media_foot, parent, false));

        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        switch (getItemViewType(position)) {
            case 0:
                final RankViewHolder rankViewHolder = (RankViewHolder) holder;
                rankViewHolder.song.setText(rankBean.getSong_list().get(position).getTitle());
                rankViewHolder.singer.setText(rankBean.getSong_list().get(position).getAuthor());
                MyImageLoader.myImageLoader(rankBean.getSong_list().get(position).getPic_big(), rankViewHolder.mainimg);
                if (rankBean.getSong_list().get(position).getHas_mv_mobile() != 0) {
                    rankViewHolder.mv.setVisibility(View.VISIBLE);
                }
                if (rankBean.getSong_list().get(position).getHavehigh() != 0) {
                    rankViewHolder.sq.setVisibility(View.VISIBLE);
                }
                if (rankBean.getSong_list().get(position).getLearn() != 0) {
                    rankViewHolder.ksong.setVisibility(View.VISIBLE);
                }
                rankViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("MediaListAdapter", "回调");
                        onRecyclerItemClickListener.onItemClick(v, rankViewHolder, position);
                    }
                });
                break;
            case 1:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return rankBean.getSong_list().size() + 1;
    }


    class FootViewHolder extends RecyclerView.ViewHolder {
        public FootViewHolder(View itemView) {
            super(itemView);
        }
    }

    class RankViewHolder extends RecyclerView.ViewHolder {
        TextView song, singer;
        ImageView mv, sq, ksong, more, mainimg;

        public RankViewHolder(View itemView) {
            super(itemView);
            song = (TextView) itemView.findViewById(R.id.song_medialist_item);
            singer = (TextView) itemView.findViewById(R.id.singer_medialist_item);
            mv = (ImageView) itemView.findViewById(R.id.mv_show_item);
            sq = (ImageView) itemView.findViewById(R.id.sq_show_item);
            ksong = (ImageView) itemView.findViewById(R.id.ksong_medialist_item);
            more = (ImageView) itemView.findViewById(R.id.more_medialist_item);
            mainimg = (ImageView) itemView.findViewById(R.id.rankimg_item);
        }
    }

}
