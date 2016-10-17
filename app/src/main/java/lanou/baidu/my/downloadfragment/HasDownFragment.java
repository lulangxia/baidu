package lanou.baidu.my.downloadfragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import lanou.baidu.R;
import lanou.baidu.base.BaseFragment;
import lanou.baidu.bean.DownLoadBean;
import lanou.baidu.bean.MusicBean;
import lanou.baidu.bean.MyMusicBean;
import lanou.baidu.database.DBTools;

/**
 * Created by dllo on 16/10/17.
 */
public class HasDownFragment extends BaseFragment {

    private ListView mListView;
    private ArrayList<DownLoadBean> mArrayList;
    private DBTools mDbTools;
    private MusicBean mMusicBean;
    private MyMusicBean mMyMusicBean;

    @Override
    protected int setLayout() {
        return R.layout.hasdownfragment;
    }

    @Override
    protected void initView() {
        mListView = bindView(R.id.mymusic_hasdownload);

    }

    @Override
    protected void initData() {
        mDbTools = new DBTools(getContext());
        mArrayList = new ArrayList<>();
        mArrayList = mDbTools.queryALLDB();
        mMyMusicBean = new MyMusicBean();
        ArrayList<MusicBean> musics = new ArrayList<>();
        for (int i = 0; i < mArrayList.size(); i++) {
            mMusicBean = new MusicBean();
            mMusicBean.setMusicuri(mArrayList.get(i).getMusicuri());
            mMusicBean.setSongName(mArrayList.get(i).getSong());
            mMusicBean.setSinger(mArrayList.get(i).getSinger());
            mMusicBean.setDuration(mArrayList.get(i).getDuration());

            musics.add(mMusicBean);
        }
        mMyMusicBean.setMusicBeen(musics);
        mMyMusicBean.setLOCAL(true);
        HasDownloadAdapter hasdownadapter = new HasDownloadAdapter(getContext());
        hasdownadapter.setMyMusicBean(mMyMusicBean);
        mListView.setAdapter(hasdownadapter);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.media_foot, null);
        TextView textView = (TextView) view.findViewById(R.id.num_song);
        textView.setText("共" + mMyMusicBean.getMusicBeen().size() + "首歌曲");
        mListView.addFooterView(view);





        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mMyMusicBean.setPosition(position);
                EventBus.getDefault().post(mMyMusicBean);
            }
        });

    }
}
