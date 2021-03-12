package com.example.Magic_CnyangE.alarm;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.Magic_CnyangE.PreferenceManager;
import com.example.Magic_CnyangE.R;
import com.example.Magic_CnyangE.specific_weather;
import com.example.Magic_CnyangE.weather_alarm.AlarmListActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AlarmActivity extends Service {
    String TAG = "TAG+Service";
    private WindowManager windowManager;
    private View view;
    private ImageView cat_view;
    private ImageView bubble_img;
    private ImageView A_umbrella;
    private ImageView A_snowcat;
    private TextView bubble_temp;
    private TextView bubble_weather;
    private View bubble_view;

    Calendar now = Calendar.getInstance();
    int hour=now.get(Calendar.HOUR_OF_DAY);
    String now_Tem;
    String now_weather;
    String[][] arr = new String[8][5];
    private int locationX;
    private int locationY;
    String[] TMN_value = new String[2];
    String[] TMX_value = new String[2];

    public String fn_Yesterday() {
        SimpleDateFormat Format = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = Calendar.getInstance();

        cal.add(Calendar.DATE, -1);
        String timedate = Format.format(cal.getTime());

        return timedate;
    }

    @Override
    public void onCreate( ) {
        super.onCreate();
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.activity_alarm, null);

        cat_view=view.getRootView().findViewById(R.id.cat_img);
        bubble_img=view.getRootView().findViewById(R.id.bubble_weather_imgView);
        bubble_temp = view.getRootView().findViewById(R.id.bubble_temp_view);
        bubble_weather = view.getRootView().findViewById(R.id.bubble_weather_txt);
        bubble_view=view.getRootView().findViewById(R.id.bubble);
        A_snowcat = view.getRootView().findViewById(R.id.A_snowcat);
        A_umbrella = view.getRootView().findViewById(R.id.A_umbrella);

//      cat_view.setImageResource(R.drawable.ic_magic_cat);
        bubble_img.setImageResource(R.drawable.sunny);

        locationX= PreferenceManager.getInt(getApplicationContext(), "locationX");
        locationY=PreferenceManager.getInt(getApplicationContext(), "locationY");

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
//        bubble_weather.setText("어쩌구");
//        bubble_temp.setText("저쩌구");

        cat_view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                stopSelf();
                return false;
            }
        });

        cat_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent clock = new Intent(getApplicationContext(), AlarmListActivity.class);
                clock.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(clock);
                stopSelf();
            }
        });

        bubble_view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent sp_weather = new Intent(getApplicationContext(), specific_weather.class);
                sp_weather.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(sp_weather);
                stopSelf();
                return false;
            }
        });

//        setResources();

        return super.onStartCommand(intent, flags, startId);
    }

    class ggetXML extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... urls) {
            try {
                for(int n=0;n<8;n++)
                    for(int l=0;l<5;l++)
                        arr[n][l]="0";

                int i=0;
                int k=0;
                String text = null;
                Boolean T3H=Boolean.FALSE;
                Boolean SKY=Boolean.FALSE;
                Boolean PTY=Boolean.FALSE;
                Boolean TMX=Boolean.FALSE;
                Boolean TMN=Boolean.FALSE;

                Boolean category = Boolean.FALSE;
                Boolean fcstTime = Boolean.FALSE;
                Boolean fcstValue = Boolean.FALSE;
                Boolean fcstDate = Boolean.FALSE;

                InputStream stream = downloadUrl(urls[0]);
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                XmlPullParser parser = factory.newPullParser();
                parser.setInput(stream, "UTF-8");

                int eventType = parser.getEventType();

                while(eventType != XmlPullParser.END_DOCUMENT){
                    switch (eventType){
                        case XmlPullParser.START_DOCUMENT:
                            break;
                        case XmlPullParser.END_DOCUMENT:
                            break;
                        case XmlPullParser.START_TAG:
                            if(parser.getName().equals("category"))
                                category=Boolean.TRUE;
                            else if(parser.getName().equals("fcstDate"))
                                fcstDate = Boolean.TRUE;
                            else if(parser.getName().equals("fcstTime"))
                                fcstTime = Boolean.TRUE;
                            else if(parser.getName().equals("fcstValue"))
                                fcstValue = Boolean.TRUE;
                            break;
                        case XmlPullParser.TEXT:
                            text=parser.getText();
                            if(category) {
                                if(parser.getText().equals("T3H")) {
                                    T3H = Boolean.TRUE;
                                }
                                else if(parser.getText().equals("SKY")) {
                                    SKY = Boolean.TRUE;
                                }
                                else if(parser.getText().equals("PTY")) {
                                    PTY = Boolean.TRUE;
                                }
                                else if(parser.getText().equals("TMX")){
                                    TMX = Boolean.TRUE;
                                }
                                else if(parser.getText().equals("TMN")){
                                    TMN = Boolean.TRUE;
                                }
                                category =Boolean.FALSE;
                            }
                            else if(fcstDate) {
                                arr[i][0]=text;
                                fcstDate =Boolean.FALSE;
                            }
                            else if(fcstTime) {
                                arr[i][1]=text;
                                fcstTime =Boolean.FALSE;
                            }
                            else if(fcstValue) {
                                if(T3H){
                                    arr[i][2]=text;
                                    if(i<14) i++;
                                    T3H=Boolean.FALSE;
                                }
                                else if(PTY){
                                    arr[i][3]=text;
                                    PTY=Boolean.FALSE;
                                }
                                else if(SKY) {
                                    arr[i][4]=text;
                                    SKY=Boolean.FALSE;
                                }
                                else if(TMN){
                                    TMN_value[k]=text;
                                    TMN = Boolean.FALSE;
                                }
                                else if(TMX){
                                    TMX_value[k]=text;
                                    TMX = Boolean.FALSE;
                                    k++;
                                }
                                fcstValue = Boolean.FALSE;
                            }
                            break;
                        case XmlPullParser.END_TAG:
                            if(parser.getName().equals("response"))
                                break;
                    }
                    eventType = parser.next();
                }
                stream.close();

                return "a";
            } catch (IOException e) {
                e.printStackTrace();
                return "IOException error";
            } catch (XmlPullParserException e) {
                return "XmlPullParserException error";
            }
        }

        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Boolean SNOW=Boolean.FALSE;
            Boolean RAIN=Boolean.FALSE;

            String now_T3H="0";
            String now_PTY="0";
            String now_SKY="1";

            int k=0;
            for(int num=0;num<8;num=num+3){
                if(hour>=num && hour<(num+3)){
                    now_T3H = arr[k][2];
                    now_PTY = arr[k][3];
                    now_SKY = arr[k][4];
                }
                k++;
            }

            now_Tem = now_T3H;
            bubble_temp.setText("기온 "+TMX_value[0]+"/"+TMN_value[0]);

            Log.d("now",now_Tem);

            if(now_PTY.equals("0")) {
                if (now_SKY.equals("맑음")) {
                    now_weather = "현재 날씨는 맑아요!";
                    bubble_img.setImageResource(R.drawable.sunny);
                }
                else if (now_SKY.equals("구름많음")) {
                    now_weather = "지금 구름이 많아요!";
                    bubble_img.setImageResource(R.drawable.cloud);
                }
                else {
                    now_weather = "현재 날씨는 흐려요!";
                    bubble_img.setImageResource(R.drawable.sun_cloud);
                }
            }
            else if(now_PTY.equals("3") || now_PTY.equals("7")) {
                now_weather = "지금 눈이 와요!";
                bubble_img.setImageResource(R.drawable.snow);
            }
            else {
                now_weather = "지금 비가 와요!";
                bubble_img.setImageResource(R.drawable.rain);
            }
            bubble_weather.setText(now_weather);

            for(int u=(hour/3); u<7;u++) {
                if (arr[u][4].equals("1"))
                    continue;
                else {
                    if (arr[u][3].equals("3") || arr[u][3].equals("7")) {
                        SNOW = Boolean.TRUE;
                        break;
                    }
                    else {
                        RAIN = Boolean.TRUE;
                        break;
                    }
                }
            }

            if(SNOW){ //눈
                A_umbrella.setVisibility(View.INVISIBLE);
                A_snowcat.setVisibility(View.VISIBLE);
                SNOW = Boolean.FALSE;
            }
            else if(RAIN){ //비
                A_umbrella.setVisibility(View.VISIBLE);
                A_snowcat.setVisibility(View.INVISIBLE);
                RAIN = Boolean.FALSE;
            }
            else{
                A_umbrella.setVisibility(View.INVISIBLE);
                A_snowcat.setVisibility(View.INVISIBLE);
            }

        }

        private InputStream downloadUrl(String urlString) throws IOException {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            return conn.getInputStream();
        }
    }
    private void setResources() {
        if(hour<23){
            if(locationX ==0)
                locationX=60;
            if(locationY == 0)
                locationY=127;

            String url2 = "http://apis.data.go.kr/1360000/VilageFcstInfoService/getVilageFcst?" +
                    "serviceKey=kVYcCisbHyjiLHSoknw1iZbhenW6Glc2mM4hfGf1EeIHjXagq6P9g98eMXs6lFGtlksA74tis6Z677Ol%2FjiHrw%3D%3D&" +
                    "numOfRows=71&pageNo=1&base_date=" +
                    fn_Yesterday() +
                    "&base_time=2300&nx=" +
                    locationX + //x좌표
                    "&ny=" +
                    locationY; //y좌표
            new ggetXML().execute(url2);
        }
        else{
            if(locationX ==0)
                locationX=60;
            if(locationY == 0)
                locationY=127;

            String url2 = "http://apis.data.go.kr/1360000/VilageFcstInfoService/getVilageFcst?" +
                    "serviceKey=kVYcCisbHyjiLHSoknw1iZbhenW6Glc2mM4hfGf1EeIHjXagq6P9g98eMXs6lFGtlksA74tis6Z677Ol%2FjiHrw%3D%3D&" +
                    "numOfRows=71&pageNo=1&base_date=" +
                    fn_Yesterday() +
                    "&base_time=0200" +
                    "&nx=" +
                    locationX +
                    "&ny=" +
                    locationY;
            new ggetXML().execute(url2);

        }
    }

}