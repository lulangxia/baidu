package lanou.baidu.playmusic;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import lanou.baidu.R;
import lanou.baidu.base.BaseFragment;
import lanou.baidu.tools.MyImageLoader;
import lanou.baidu.eventbus.SendSongMessage;

/**
 * Created by dllo on 16/10/11.
 */
public class PlayMainFragment extends BaseFragment {

    private ImageView imageMain;
    private ImageView imageBg;
    private ImageView imageSrc;
    private TextView song;
    private TextView singer;
    private String img;
    private String songget;
    private String singerget;
    private FrameLayout frameLayout;

    @Override
    protected int setLayout() {
        EventBus.getDefault().register(this);
        return R.layout.fragment_playmain;
    }

    @Override
    protected void initView() {
        imageMain = bindView(R.id.img_mainplay);
        imageBg = bindView(R.id.img_mainplay_bg);
        imageSrc = bindView(R.id.img_mainplay_src);
        song = bindView(R.id.song_mainplay);
        singer = bindView(R.id.singer_mainplay);

        frameLayout = bindView(R.id.back_mainplay);
    }


    @Override
    protected void initData() {

        Intent getIntent = getActivity().getIntent();
        img = getIntent.getStringExtra("img");
        songget = getIntent.getStringExtra("song");
        singerget = getIntent.getStringExtra("singer");

        new AsyncTask<String, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(String... params) {
                Bitmap bit = null;
                try {
                    URL url = new URL(params[0]);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
                        InputStream inputStream = connection.getInputStream();
                        bit = BitmapFactory.decodeStream(inputStream);
                        inputStream.close();
                        connection.disconnect();
                    }
                } catch (MalformedURLException e) {
                    // TODO: handle exception
                } catch (IOException e) {
                    // TODO: handle exception
                }
                return bit;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                Bitmap newbit = changeBackgroundImage(bitmap);
                imageSrc.setImageBitmap(newbit);
            }
        }.execute(img);
        song.setText(songget);
        singer.setText(singerget);
        MyImageLoader.myImageLoader(img, imageMain);
        song.setText(songget);
        singer.setText(singerget);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void sentText(SendSongMessage sendSongMessage) {
        Log.d("PlayMainFragment", "aaaa");
        songget = sendSongMessage.getSong();
        singerget = sendSongMessage.getSinger();
        img = sendSongMessage.getImgurl();

        Bitmap newbit = null;
        MyImageLoader.myImageLoader(img, imageMain);
        // MyImageLoader.myImageLoader(img, imageBg);
        new AsyncTask<String, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(String... params) {
                Bitmap bit = null;
                try {
                    URL url = new URL(params[0]);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
                        InputStream inputStream = connection.getInputStream();
                        bit = BitmapFactory.decodeStream(inputStream);
                        inputStream.close();
                        connection.disconnect();
                    }
                } catch (MalformedURLException e) {
                    // TODO: handle exception
                } catch (IOException e) {
                    // TODO: handle exception
                }
                return bit;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                Bitmap newbit = changeBackgroundImage(bitmap);
                imageSrc.setImageBitmap(newbit);
            }
        }.execute(img);


        song.setText(songget);
        singer.setText(singerget);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public Bitmap changeBackgroundImage(Bitmap sentBitmap) {
        if (sentBitmap != null) {
            if (Build.VERSION.SDK_INT > 16) {
                Bitmap bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);
                final RenderScript rs = RenderScript.create(getContext());
                final Allocation input = Allocation.createFromBitmap(rs, sentBitmap, Allocation.MipmapControl.MIPMAP_NONE,
                        Allocation.USAGE_SCRIPT);
                final Allocation output = Allocation.createTyped(rs, input.getType());
                final ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
                script.setRadius(20.0f);
                script.setInput(input);
                script.forEach(output);
                output.copyTo(bitmap);
                return bitmap;
            }
        }
        return null;
    }


}
