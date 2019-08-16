package com.android.lvtong.handlerdemo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.lvtong.implementationlibary.DialogOption;

import java.lang.ref.WeakReference;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @author 22939
 * @date 2019/8/15 10:47
 */
public class MainActivity extends AppCompatActivity {

    private TextView tvTime;

    private Handler mExampleHandler;

    private static final int TIME = 10;

    private static class MyHandler extends Handler {

        private WeakReference<MainActivity> mMainActivityWeakReference;

        MyHandler(MainActivity activity) {
            mMainActivityWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MainActivity mainActivity = mMainActivityWeakReference.get();
            mainActivity.tvTime.setText(String.valueOf(msg.what));
        }
    }

    /** 方法一，通过Handler + Message的方式实现倒计时。 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        Button btnTime1 = findViewById(R.id.btn_time1);
        mExampleHandler = new MyHandler(this);
        btnTime1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 1; i <= TIME; i++) {
                    Message message = Message.obtain(mExampleHandler);
                    message.what = 10 - i;
                    //通过延迟发送消息，每隔一秒发送一条消息
                    mExampleHandler.sendMessageDelayed(message, 1000 * i);
                }
            }
        });



        /* 方法二，通过Handler + Runnable的方式实现倒计时。*/
        Button btnTime2 = findViewById(R.id.btn_time2);
        btnTime2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 1; i <= TIME; i++) {
                    final int fadedSecond = i;
                    //每延迟一秒，发送一个Runnable对象
                    mExampleHandler = new Handler();
                    mExampleHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            tvTime.setText(String.valueOf(10 - fadedSecond));
                        }
                    }, 1000 * i);
                }
            }
        });
    }

    private void initView() {
        findViewById(R.id.btn_jump_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SecondActivity.class));
            }
        });
        findViewById(R.id.btn_jump_third).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ThirdActivity.class));
            }
        });
        findViewById(R.id.btn_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogOption.with(MainActivity.this)
                            .create()
                            .show();
            }
        });
        tvTime = findViewById(R.id.tv_time);
    }
}
