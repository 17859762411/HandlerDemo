package com.android.lvtong.handlerdemo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @author 22939
 * @date 2019/8/15 10:47
 */
public class MainActivity extends AppCompatActivity {

    private TextView tvTime;

    private Handler mExampleHandler;

    /** 方法一，通过Handler + Message的方式实现倒计时。 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_jump_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,SecondActivity.class));
            }
        });
        findViewById(R.id.btn_jump_third).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,ThirdActivity.class));
            }
        });

        tvTime = findViewById(R.id.tv_time);

        Button btnTime1 = findViewById(R.id.btn_time1);
        btnTime1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 1; i <= 10; i++) {
                    Message message = Message.obtain(mExampleHandler);
                    message.what = 10 - i;
                    //通过延迟发送消息，每隔一秒发送一条消息
                    mExampleHandler.sendMessageDelayed(message, 1000 * i);
                }
            }
        });
        mExampleHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                tvTime.setText(msg.what + "");   //在handleMessage中处理消息队列中的消息
            }
        };
        /** 方法二，通过Handler + Runnable的方式实现倒计时。 */
        Button btnTime2 = findViewById(R.id.btn_time2);
        btnTime2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 1; i <= 10; i++) {
                    final int fadedSecond = i;
                    //每延迟一秒，发送一个Runnable对象
                    mExampleHandler = new Handler();
                    mExampleHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            tvTime.setText((10 - fadedSecond) + "");
                        }
                    }, 1000 * i);
                }
            }
        });
    }
}
