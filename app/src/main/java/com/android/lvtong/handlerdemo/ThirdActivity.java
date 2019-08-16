package com.android.lvtong.handlerdemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @author 22939
 */
public class ThirdActivity extends AppCompatActivity {

    private TextView tvFirst;
    private TextView tvSecond;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        tvFirst = findViewById(R.id.tv_first);
        tvSecond = findViewById(R.id.tv_second);

        final Handler mainHandler = new Handler();

        final HandlerThread downloadFirstThread = new HandlerThread("downloadFirstThread");
        downloadFirstThread.start();
        Handler downloadFirstHandler = new Handler(downloadFirstThread.getLooper());

        // 通过postDelayed模拟耗时操作
        downloadFirstHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "下载1完成", Toast.LENGTH_SHORT)
                     .show();
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        tvFirst.setText("任务1已经下载完成");
                    }
                });
            }
        }, 1000 * 5);

        final HandlerThread downloadSecondThread = new HandlerThread("downloadSecondThread");
        downloadSecondThread.start();
        Handler downloadSecondHandler = new Handler(downloadSecondThread.getLooper());

        // 通过postDelayed模拟耗时操作
        downloadSecondHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "下载2完成", Toast.LENGTH_SHORT)
                     .show();
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        tvSecond.setText("任务2已经下载完成");
                    }
                });

            }
        }, 1000 * 7);
    }

}
