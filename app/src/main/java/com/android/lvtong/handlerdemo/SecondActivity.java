package com.android.lvtong.handlerdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author 22939
 */
public class SecondActivity extends AppCompatActivity {

    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Button btnLoad = findViewById(R.id.btn_load);
        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // 在Runnable中进行网络读取操作，返回bitmap
                        final Bitmap bitmap = loadPicFromInternet();
                        // 在子线程中实例化Handler同样是可以的，只要在构造函数的参数中传入主线程的Looper即可
                        Handler handler = new Handler(Looper.getMainLooper());
                        // 通过Handler的post Runnable到UI线程的MessageQueue中去即可
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                // 在MessageQueue出队该Runnable时进行的操作
                                iv.setImageBitmap(bitmap);
                            }
                        });
                    }
                }).start();
            }
        });
        Button btnReset = findViewById(R.id.btn_reset);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.scan));
            }
        });
        iv = findViewById(R.id.iv);
    }
    /***
     * HttpUrlConnection加载图片
     * @return
     */
    public Bitmap loadPicFromInternet() {
        Bitmap bitmap = null;
        int respondCode = 0;
        InputStream is = null;
        try {
            URL
                    url = new URL("https://www.baidu.com/img/bd_logo1.png");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(10 * 1000);
            connection.setReadTimeout(5 * 1000);
            connection.connect();
            respondCode = connection.getResponseCode();
            if (respondCode == 200) {
                is = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(is);
            }
        } catch (MalformedURLException e) {
            Log.d("SecondActivity", "e:" + e);
            Toast.makeText(getApplicationContext(), "访问失败", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.d("SecondActivity", "e:" + e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    Log.d("SecondActivity", "e:" + e);
                }
            }
        }
        return bitmap;
    }

}
