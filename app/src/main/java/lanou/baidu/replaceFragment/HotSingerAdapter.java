package lanou.baidu.replacefragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import lanou.baidu.R;

/**
 * Created by dllo on 16/9/26.
 */
public class HotSingerAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> arrayList;

    public HotSingerAdapter(Context context) {
        this.context = context;
    }

    public void setArrayList(ArrayList<String> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList == null ? 0 : arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HotSingerViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.singerfrag_item, parent, false);
            viewHolder = new HotSingerViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (HotSingerViewHolder) convertView.getTag();
        }
        viewHolder.textView.setText(arrayList.get(position));
        return convertView;
    }

    class HotSingerViewHolder {
        TextView textView;

        public HotSingerViewHolder(View convertView) {
            textView = (TextView) convertView.findViewById(R.id.singer_type);
        }
    }
}
