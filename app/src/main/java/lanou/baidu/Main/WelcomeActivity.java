package lanou.baidu.main;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import lanou.baidu.R;
import lanou.baidu.base.BaseAty;

public class WelcomeActivity extends BaseAty{


    private ImageView mImageView;
    private TextView mTextView;
    int time=5;
    Timer timer = new Timer();

    @Override
    protected int setLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initView() {
        mImageView = bindView(R.id.ima_wel);

        mTextView = bindView(R.id.time_wel);

    }

    @Override
    protected void initData() {
        mImageView.setBackgroundResource(R.mipmap.welcome_5820_1);
        timer.schedule(task,0,1000);
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    time--;
                    mTextView.setText("跳过 " + time + "s");
                    if (time < 0) {
                        timer.cancel();
                        mTextView.setVisibility(View.GONE);
                        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            });
        }
    };
}
