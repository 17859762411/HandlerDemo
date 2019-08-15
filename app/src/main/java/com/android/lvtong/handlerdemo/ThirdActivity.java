package com.android.lvtong.handlerdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author 22939
 */
public class ThirdActivity extends AppCompatActivity {

    private TextView tvA;
    private TextView tvB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        tvA = (TextView) findViewById(R.id.tv_a);
        tvB = (TextView) findViewById(R.id.tv_b);

        final Handler mainHandler = new Handler();

        final HandlerThread downloadAThread = new HandlerThread("downloadAThread");
        downloadAThread.start();
        Handler downloadAHandler = new Handler(downloadAThread.getLooper());

        // 通过postDelayed模拟耗时操作
        downloadAHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "下载A完成", Toast.LENGTH_SHORT).show();
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        tvA.setText("A任务已经下载完成");
                    }
                });
            }
        }, 1000 * 5);


        final HandlerThread downloadBThread = new HandlerThread("downloadBThread");
        downloadBThread.start();
        Handler downloadBHandler = new Handler(downloadBThread.getLooper());

        // 通过postDelayed模拟耗时操作
        downloadBHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "下载B完成", Toast.LENGTH_SHORT).show();
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        tvB.setText("B任务已经下载完成");
                    }
                });

            }
        }, 1000 * 7);
    }

}
