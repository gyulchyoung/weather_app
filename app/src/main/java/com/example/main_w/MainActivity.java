package com.example.main_w;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.main_w.location.LocationDatabase;
import com.example.main_w.weather_alarm.AlarmListActivity;
import com.google.android.material.navigation.NavigationView;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    String[][] arr = new String[15][5];
    Calendar now = Calendar.getInstance();

    int year=now.get(Calendar.YEAR);
    int month=now.get(Calendar.MONTH)+1;
    int day=now.get(Calendar.DAY_OF_MONTH);
    int hour=now.get(Calendar.HOUR_OF_DAY);

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton help = findViewById(R.id.help);
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getSupportFragmentManager();
                DialogFragment helpDialog = new HelpDialogFragment();
                helpDialog.show(fm, HelpDialogFragment.DIALOG_TAG);
            }
        });

        ImageButton clock = findViewById(R.id.clock);
        clock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AlarmListActivity.class);
                startActivity(intent);
            }
        });

        ImageButton specific_weather = findViewById(R.id.specific_weather);
        specific_weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int locationX = PreferenceManager.getInt(view.getContext(), "locationX");
                int locationY = PreferenceManager.getInt(view.getContext(), "locationY");

                Intent intent = new Intent(view.getContext(), specific_weather.class);
                intent.putExtra("locationX",locationX);
                intent.putExtra("locationY",locationY);
                startActivity(intent);
            }
        });
        //drawer view 버튼 클릭시
        ImageView drawerBtn = (ImageView) findViewById(R.id.drawer_btn);
        drawerBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                if (!drawer.isDrawerOpen(Gravity.LEFT)) {
                    drawer.openDrawer(Gravity.LEFT);
                }
            }
        });

        class ggetXML extends AsyncTask<String, Void, String> {

            protected String doInBackground(String... urls) {
                try {
                    for(int n=0;n<15;n++)
                        for(int l=0;l<5;l++)
                            arr[n][l]="0";

                    int i=0;
                    String text = null;
                    Boolean T3H=Boolean.FALSE;
                    Boolean SKY=Boolean.FALSE;
                    Boolean PTY=Boolean.FALSE;

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
                int t=3;

                int now_TIME=hour;
                String now_T3H="0";
                String now_PTY="0";
                String now_SKY="1";

                int k=0;
                for(int num=0;num<24;num=num+3){
                    if(hour>=num && hour<(num+3)){
                        now_TIME = num;
                        now_T3H = arr[k][2];
                        now_PTY = arr[k][3];
                        now_SKY = arr[k][4];
                    }
                    k++;
                }

                if(now_PTY.equals("0")){
                    findViewById(R.id.umbrella).setVisibility(View.INVISIBLE);
                    findViewById(R.id.snowcat).setVisibility(View.INVISIBLE);
                }
                else if(now_PTY.equals("3") || now_PTY.equals("7")){
                    findViewById(R.id.umbrella).setVisibility(View.INVISIBLE);
                    findViewById(R.id.snowcat).setVisibility(View.VISIBLE);
                }
                else{
                    findViewById(R.id.umbrella).setVisibility(View.VISIBLE);
                    findViewById(R.id.snowcat).setVisibility(View.INVISIBLE);
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

        Button button = findViewById(R.id.F5);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                int locationX = PreferenceManager.getInt(view.getContext(), "locationX");
                int locationY = PreferenceManager.getInt(view.getContext(), "locationY");

                if(hour<23){
                    if(locationX ==0)
                        locationX=60;
                    if(locationY == 0)
                        locationY=127;

                    String today = String.valueOf(year)+"0"+String.valueOf(month)+String.valueOf(day-1);
                    String url2 = "http://apis.data.go.kr/1360000/VilageFcstInfoService/getVilageFcst?" +
                            "serviceKey=kVYcCisbHyjiLHSoknw1iZbhenW6Glc2mM4hfGf1EeIHjXagq6P9g98eMXs6lFGtlksA74tis6Z677Ol%2FjiHrw%3D%3D&" +
                            "numOfRows=225&pageNo=1&base_date=" +
                            "20210225" +
                            "&base_time=0200&nx=" +
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

                    String today = String.valueOf(year)+"0"+String.valueOf(month)+String.valueOf(day);
                    String url2 = "http://apis.data.go.kr/1360000/VilageFcstInfoService/getVilageFcst?" +
                            "serviceKey=kVYcCisbHyjiLHSoknw1iZbhenW6Glc2mM4hfGf1EeIHjXagq6P9g98eMXs6lFGtlksA74tis6Z677Ol%2FjiHrw%3D%3D&" +
                            "numOfRows=225&pageNo=1&base_date=" +
                            today +
                            "&base_time=" +
                            "&nx=" +
                            locationX +
                            "&ny=" +
                            locationY;
                    new ggetXML().execute(url2);

                }
            }
        });
    }

}