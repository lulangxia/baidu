package lanou.baidu.musicmedia;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by dllo on 16/10/15.
 */
public interface OnRecyclerItemClickListener {
    void onItemClick(View view, RecyclerView.ViewHolder holder, int position);
}
