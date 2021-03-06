package com.example.Magic_CnyangE.DAILY_JHJ;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Magic_CnyangE.PreferenceManager;
import com.example.Magic_CnyangE.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class daily extends Fragment {
    public String[][] arr = new String[15][5];
    String[] TMN_value = new String[2];
    String[] TMX_value = new String[2];

    Calendar now = Calendar.getInstance();
    int hour=now.get(Calendar.HOUR_OF_DAY);

    public String fn_Yesterday() {
        SimpleDateFormat Format = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = Calendar.getInstance();

        cal.add(Calendar.DATE, -1);
        String timedate = Format.format(cal.getTime());

        return timedate;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.daily, container, false);
        View view = inflater.inflate(R.layout.weather_item, container, false);

        TextView timeView = view.findViewById(R.id.time_view);
        TextView temperatureView = view.findViewById(R.id.temparature_view);
        TextView rainrateView = view.findViewById(R.id.rain_rate_view);

        for(int a=0; a<15;a++) {
            for (int m = 0; m < 5; m++) {
                arr[a][m] = " ";
            }
        }

        int locationX = PreferenceManager.getInt(getContext(), "locationX");
        int locationY = PreferenceManager.getInt(getContext(), "locationY");

        class ggetXML extends AsyncTask<String, Void, String> {

            protected String doInBackground(String... urls) {
                try {

                    int i=0;
                    int tm=0;
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
                                        TMN_value[tm]=text;
                                        TMN = Boolean.FALSE;
                                    }
                                    else if(TMX){
                                        TMX_value[tm]=text;
                                        TMX = Boolean.FALSE;
                                        tm++;
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
                RecyclerView recyclerView = v.findViewById(R.id.recyclerView);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setLayoutManager(layoutManager);
                WeatherAdapter adapter = new WeatherAdapter();
                recyclerView.setAdapter(adapter);

                int t=3;

                for(int a=0; a<15;a++) {
                    String PTY1=arr[a][3];
                    String SKY1=arr[a][4];

                    if(PTY1.equals("0")) arr[a][3]="맑음";
                    else if(PTY1.equals("3")||PTY1.equals("7")) arr[a][3]="눈";
                    else arr[a][3]="비";

                    if(SKY1.equals("1")) arr[a][4]="맑음";
                    else if(SKY1.equals("3")) arr[a][4]="구름많음";
                    else if(SKY1.equals("4")) arr[a][4]="흐림";

                    if(PTY1.equals("0")) {
                        if (arr[a][4].equals("맑음"))
                            adapter.addItem(arr[a][2] + "도", t + "시", arr[a][4], R.drawable.sunny);
                        else if (arr[a][4].equals("구름많음"))
                            adapter.addItem(arr[a][2] + "도", t + "시", arr[a][4], R.drawable.sun_cloud);
                        else
                            adapter.addItem(arr[a][2] + "도", t + "시", arr[a][4], R.drawable.cloud);
                    }
                    else if(PTY1.equals("3") || PTY1.equals("7"))
                        adapter.addItem(arr[a][2] + "도", t + "시", arr[a][3], R.drawable.snow);
                    else
                        adapter.addItem(arr[a][2] + "도", t + "시", arr[a][3], R.drawable.rain);

                    t=(t+3)%24;
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

        if(hour<23) {
            String url2 = "http://apis.data.go.kr/1360000/VilageFcstInfoService/getVilageFcst?" +
                    "serviceKey=kVYcCisbHyjiLHSoknw1iZbhenW6Glc2mM4hfGf1EeIHjXagq6P9g98eMXs6lFGtlksA74tis6Z677Ol%2FjiHrw%3D%3D&" +
                    "numOfRows=225&pageNo=1&base_date=" +
                    fn_Yesterday() +
                    "&base_time=2300&nx=" +
                    locationX+
                    "&ny=" +
                    locationY;
            new ggetXML().execute(url2);
        }
        else{
            String url2 = "http://apis.data.go.kr/1360000/VilageFcstInfoService/getVilageFcst?" +
                    "serviceKey=kVYcCisbHyjiLHSoknw1iZbhenW6Glc2mM4hfGf1EeIHjXagq6P9g98eMXs6lFGtlksA74tis6Z677Ol%2FjiHrw%3D%3D&" +
                    "numOfRows=225&pageNo=1&base_date=" +
                    fn_Yesterday() +
                    "&base_time=0200" +
                    locationX+
                    "&ny=" +
                    locationY;
            new ggetXML().execute(url2);
        }
        return v;
    }

}
