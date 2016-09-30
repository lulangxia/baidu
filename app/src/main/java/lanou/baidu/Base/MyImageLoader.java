package lanou.baidu.base;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import lanou.baidu.R;

/**
 * Created by dllo on 16/9/22.
 */
public class MyImageLoader {

    public static void myImageLoader(String url, ImageView imageView) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .showImageOnLoading(R.mipmap.default_recommend)
                .showImageForEmptyUri(R.mipmap.default_recommend)
                .showImageOnFail(R.mipmap.default_recommend)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();


        ImageLoader.getInstance().displayImage(url, imageView, options);
    }


}
//        public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
//            Bitmap bitmap = response.getBitmap();
//            if (bitmap == null) {
//                imageView.setImageResource(R.mipmap.ic_launcher);
//            } else {
//                if (url.equals(imageView.getTag())) {
//                    imageView.setImageBitmap(bitmap);
//                } else {
//                    myImageLoader((String) imageView.getTag(), imageView);
//                }
//            }
//        }
//
//        @Override
//        public void onErrorResponse(VolleyError error) {
//            imageView.setImageResource(R.mipmap.ic_launcher);
//        }


