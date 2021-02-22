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
import android.widget.Button;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.main_w.R;
import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

public class AlarmFragment extends Fragment implements View.OnClickListener{
    private TimePicker timePicker;
    private AlarmManager alarmManager;
    Intent intent;
    private int hour, minute;

    private static final int ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE = 1;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        ViewGroup rootView=(ViewGroup) inflater.inflate(R.layout.set_alarm, container, false);

        timePicker = rootView.findViewById(R.id.main_alarm_timepicker);
        alarmManager = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);
        intent = new Intent(rootView.getContext(), AlarmReceiver.class);

        getPermission();

        Button regBtn=rootView.findViewById(R.id.time_reg_button);
        Button unregBtn = rootView.findViewById(R.id.time_unreg_button);
        regBtn.setOnClickListener(this);
        unregBtn.setOnClickListener(this);
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

    @Override
    public void onClick(View view) {


        if(view.getId()==R.id.time_reg_button)
            regist(view);
        else if (view.getId()==R.id.time_unreg_button)
            unregist(view);
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