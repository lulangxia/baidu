package lanou.baidu.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by dllo on 16/9/19.
 */
public abstract class BaseAty extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayout());

        initView();
        initData();
    }

    /**
     * 设置布局
     *
     * @return 布局文件的id
     */
    protected abstract int setLayout();

    /**
     * 初始化View 执行FindViewById等操作
     */
    protected abstract void initView();

    /**
     * 初始化数据,例如拉取网络数据,或者一些逻辑代码
     */
    protected abstract void initData();


    /**
     * 简化FindViewById的操作,不需要强转
     *
     * @param id  组件的id
     * @param <T>
     * @return
     */

    protected <T extends View> T bindView(int id) {
        return (T) findViewById(id);
    }


}
