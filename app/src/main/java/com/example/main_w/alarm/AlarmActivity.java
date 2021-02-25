package com.example.main_w.alarm;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.main_w.R;

public class AlarmActivity extends Service {
    String TAG = "TAG+Service";
    private WindowManager windowManager;
    private View view;
    private ImageView cat_view;
    private ImageView bubble_img;
    private TextView bubble_temp;
    private TextView bubble_weather;

    @Override
    public void onCreate( ) {
        super.onCreate();
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.activity_alarm, null);

        cat_view=view.getRootView().findViewById(R.id.cat_img);
        bubble_img=view.getRootView().findViewById(R.id.bubble_weather_imgView);
        bubble_temp = view.getRootView().findViewById(R.id.bubble_temp_view);
        bubble_weather = view.getRootView().findViewById(R.id.bubble_weather_txt);

        setResources();
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
        );
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        windowManager.addView(view, layoutParams);

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy(){
        if (windowManager != null) {
            if(view !=null)
                windowManager.removeView(view);
        }
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "AlarmService");

        cat_view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                stopSelf();
                return false;
            }
        });


        return super.onStartCommand(intent, flags, startId);
    }

    private void setResources() {
        cat_view.setImageResource(R.drawable.ic_magic_cat);
        bubble_img.setImageResource(R.drawable.sunny);
        bubble_weather.setText("바뀐/날씨");
        bubble_temp.setText("바뀐/온도");

    }
}