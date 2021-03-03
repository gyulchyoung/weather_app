package com.example.main_w.alarm;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.main_w.CityDialogFragment;
import com.example.main_w.CountryDialogFragment;
import com.example.main_w.PreferenceManager;
import com.example.main_w.R;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

public class AlarmFragment extends Fragment implements View.OnClickListener{
    public static final String DEFAULT_COUNTRY = "서울.인천.경기도";
    public static final String DEFAULT_CITY = "서울특별시";

    private TimePicker timePicker;
    private AlarmManager alarmManager;
    private TextView currentCountry;
    private TextView currentCity;
    Intent intent;
    private int hour, minute;

    private static final int ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE = 1;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        ViewGroup rootView=(ViewGroup) inflater.inflate(R.layout.set_alarm, container, false);

        timePicker = rootView.findViewById(R.id.main_alarm_timepicker);
        timePicker.setIs24HourView(true);
        alarmManager = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);
        intent = new Intent(rootView.getContext(), AlarmReceiver.class);

        getPermission();

        TextView regBtn = (TextView) rootView.findViewById(R.id.time_reg_button);
        TextView unregBtn = (TextView) rootView.findViewById(R.id.time_unreg_button);
        currentCountry = (TextView) rootView.findViewById(R.id.current_country);
        currentCity = (TextView) rootView.findViewById(R.id.current_city);

        regBtn.setOnClickListener(this);
        unregBtn.setOnClickListener(this);
        currentCountry.setOnClickListener(this);
        currentCity.setOnClickListener(this);

        CityDialogFragment.setAlarmFragment(this);
        setLocation();
        return rootView;
    }

    public void regist(View view) {
        //Intent intent = new Intent(view.getContext(), AlarmReceiver.class);
        intent.setAction(AlarmReceiver.ACTION_RESTART_SERVICE);
        PendingIntent pIntent = PendingIntent.getBroadcast(view.getContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            hour = timePicker.getHour();
            minute = timePicker.getMinute();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        // 알람 설정 시각이 현재 시각보다 이전일 경우 다음날 울리도록 설정
        if(calendar.before(Calendar.getInstance()))
            calendar.add(Calendar.DATE, 1);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pIntent);
        }
    }

    public void unregist(View view) {
        PendingIntent pIntent = PendingIntent.getBroadcast(view.getContext(), 0, intent, 0);
        System.out.println(pIntent);
        alarmManager.cancel(pIntent);
    }

    // 지역 설정 dialog 생성
    public void setCountry(){
        FragmentManager fm = getActivity().getSupportFragmentManager();
        DialogFragment countryDialog = new CountryDialogFragment();
        countryDialog.show(fm, CountryDialogFragment.DIALOG_TAG);
    }

    // 도시 설정 dialog 생성
    public void setCity(){
        FragmentManager fm = getActivity().getSupportFragmentManager();
        DialogFragment cityDialog = new CityDialogFragment();
        cityDialog.show(fm, CityDialogFragment.DIALOG_TAG);
    }

    // 현 위치 초기화
    public void setLocation(){
        String country = PreferenceManager.getString(getContext(), "locationCountry");
        String city = PreferenceManager.getString(getContext(), "locationCity");
        if(country.equals("")) {
            currentCountry.setText(DEFAULT_COUNTRY);
            currentCity.setText(DEFAULT_CITY);
        }
        else{
            currentCountry.setText(country);
            currentCity.setText(city);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.time_reg_button:
                regist(view);
                break;
            case R.id.time_unreg_button:
                unregist(view);
                break;
            case R.id.current_country:
                setCountry();
                break;
            case R.id.current_city:
                setCity();
                break;
        }
    }

    public void getPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {   // 마시멜로우 이상일 경우
            if (!Settings.canDrawOverlays(getContext())) {              // 체크
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getContext().getPackageName()));
                startActivityForResult(intent, ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE);
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE) {
            if (!Settings.canDrawOverlays(getContext())) {
                // TODO 동의를 얻지 못했을 경우의 처리

            }
        }
    }

}