package lanou.baidu.base;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by dllo on 16/9/20.
 */
public class VolleySingleton {

    //吧请求队列,放到单例类里,这样整个项目就只有一个requestQueue
    private static VolleySingleton mVolleySingleton;
    private RequestQueue mRequestQueue;
//    private RequestQueue imageQueue;
//    private ImageLoader imageLoader;

    private VolleySingleton() {
        mRequestQueue = Volley.newRequestQueue(MyApp.getMcontext());
       // imageQueue = Volley.newRequestQueue(MyApp.getMcontext());
    }


    public static VolleySingleton getInstance() {

        if (mVolleySingleton == null) {
            synchronized (VolleySingleton.class) {
                if (mVolleySingleton == null) {
                    mVolleySingleton = new VolleySingleton();
                }
            }
        }
        return mVolleySingleton;

    }

//    public RequestQueue getImageQueue() {
//        return imageQueue;
//    }

    public RequestQueue getmRequestQueue() {
        return mRequestQueue;
    }

    /**
     * 把请求 加到请求队列里去
     *
     * @param request 各种网络请求
     */
    public void addRequest(Request request) {
        mRequestQueue.add(request);
    }


//    //url作为imageview的标签
//    public void myImageLoader(String url, ImageView imageView) {
//        imageLoader = new ImageLoader(imageQueue, new MemoryCache());
//        imageView.setTag(url);
//        imageLoader.get(url, new MyImageListener(url, imageView));
//
//    }
//
//
//    //如果再次请求时,url与imageview的url匹配则请求,不匹配则请求imageview的tag
//    class MyImageListener implements ImageLoader.ImageListener {
//        private String url;
//        private ImageView imageView;
//
//        public MyImageListener(String url, ImageView imageView) {
//            this.url = url;
//            this.imageView = imageView;
//        }
//
//        @Override
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
//    }
}
