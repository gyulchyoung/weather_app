package com.example.main_w.weather_alarm;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;
import android.widget.CompoundButton;

import com.example.main_w.R;
import com.example.main_w.Utility;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState;

import java.util.List;
import java.util.Calendar;
import java.util.ArrayList;

public class AlarmListActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener,
        RadioGroup.OnCheckedChangeListener {

    private SlidingUpPanelLayout slidingLayout;
    private RecyclerView recyclerView;
    private AlarmAdapter alarmAdapter;
    private AlarmDatabase alarmDB;
    private Alarm alarm;

    private StringBuilder repeatStr;
    private TimePicker timePicker;
    private EditText nameEdit;
    private ToggleButton monTog;
    private ToggleButton tueTog;
    private ToggleButton wedTog;
    private ToggleButton thuTog;
    private ToggleButton friTog;
    private ToggleButton satTog;
    private ToggleButton sunTog;
    private RadioGroup weatherRadio;
    private Button deleteBtn;
    private Button doneBtn;

    private boolean isNew = false;
    private boolean isDelete = false;
    private int mHour, mMinute;
    private int mWeather = -1;
    private String mName, mRepeat, mTime;
    private List<Boolean> isDay = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_list);
        alarmDB = AlarmDatabase.getDatabases(this);
        alarmAdapter = new AlarmAdapter(this, alarmDB);

        nameEdit = (EditText) findViewById(R.id.edit_name);
        recyclerView = (RecyclerView) findViewById(R.id.list);
        timePicker = (TimePicker) findViewById(R.id.time_picker);
        slidingLayout = (SlidingUpPanelLayout) findViewById(R.id.alarm_list_layout);

        doneBtn = (Button) findViewById(R.id.done);
        deleteBtn = (Button) findViewById(R.id.delete);
        weatherRadio = (RadioGroup) findViewById(R.id.radio_weather);
        monTog = (ToggleButton) findViewById(R.id.tog_mon);
        tueTog = (ToggleButton) findViewById(R.id.tog_tue);
        wedTog = (ToggleButton) findViewById(R.id.tog_wed);
        thuTog = (ToggleButton) findViewById(R.id.tog_thu);
        friTog = (ToggleButton) findViewById(R.id.tog_fri);
        satTog = (ToggleButton) findViewById(R.id.tog_sat);
        sunTog = (ToggleButton) findViewById(R.id.tog_sun);

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(alarmAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        alarmDB.alarmDao().getAll().observe(this, new Observer<List<Alarm>>(){
            @Override
            public void onChanged(List<Alarm> data){
                alarmAdapter.setItem(data);
            }
        });

        slidingLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener(){
            @Override
            public void onPanelSlide(View panel, float slideOffset){

            }

            @Override
            public void onPanelStateChanged(View panel, PanelState previousState, PanelState newState){
                if(previousState == PanelState.COLLAPSED){
                    clearSetting();
                    initTimePicker();
                    initRepeat();
                    initName();
                    initWeather();
                }
            }
        });

        for(int i = 0; i < 7; i++)
            isDay.add(false);
    }

    @Override
    public void onBackPressed(){
        if (slidingLayout != null &&
                (slidingLayout.getPanelState() == PanelState.EXPANDED || slidingLayout.getPanelState() == PanelState.ANCHORED)) {
            slidingLayout.setPanelState(PanelState.COLLAPSED);
        }
        else if(isDelete){
            clickDone(this.getWindow().getDecorView());
        }
        else {
            super.onBackPressed();
        }
    }

    public void initTimePicker(){
        timePicker.setIs24HourView(false);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener(){
            @Override
            public void onTimeChanged(TimePicker tp, int hourOfDay, int minute){
                mHour = hourOfDay;
                mMinute = minute;
            }
        });

    }

    public void initRepeat(){
        repeatStr = new StringBuilder();

        monTog.setOnCheckedChangeListener(this);
        tueTog.setOnCheckedChangeListener(this);
        wedTog.setOnCheckedChangeListener(this);
        thuTog.setOnCheckedChangeListener(this);
        friTog.setOnCheckedChangeListener(this);
        satTog.setOnCheckedChangeListener(this);
        sunTog.setOnCheckedChangeListener(this);
    }

    public void initName(){
        mName = getString(R.string.alarm_name);

        nameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (!s.toString().equals("")) {
                    mName = s.toString();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public void initWeather(){
        weatherRadio.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked){
        switch(compoundButton.getId()){
            case R.id.tog_mon:
                isDay.set(1, isChecked);
                makeRepeatText();
                break;
            case R.id.tog_tue:
                isDay.set(2, isChecked);
                makeRepeatText();
                break;
            case R.id.tog_wed:
                isDay.set(3, isChecked);
                makeRepeatText();
                break;
            case R.id.tog_thu:
                isDay.set(4, isChecked);
                makeRepeatText();
                break;
            case R.id.tog_fri:
                isDay.set(5, isChecked);
                makeRepeatText();
                break;
            case R.id.tog_sat:
                isDay.set(6, isChecked);
                makeRepeatText();
                break;
            case R.id.tog_sun:
                isDay.set(0, isChecked);
                makeRepeatText();
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId){
        switch (checkedId){
            case R.id.radio_rain:
                mWeather = 1;
                break;
            case R.id.radio_snow:
                mWeather = 2;
                break;
            case R.id.radio_typhoon:
                mWeather = 3;
                break;
        }
    }

    // 요일 반복 선택 경우에 따라 문자열 변경
    public void makeRepeatText(){
        repeatStr.setLength(0);

        if(isDay.get(1) & isDay.get(2) & isDay.get(3) & isDay.get(4) & isDay.get(5) & isDay.get(6) & isDay.get(0))
            mRepeat = getString(R.string.repeat_everyday);
        else if(isDay.get(1) & isDay.get(2) & isDay.get(3) & isDay.get(4) & isDay.get(5) & !isDay.get(6) & !isDay.get(0))
            mRepeat = getString(R.string.repeat_weekday);
        else if(!isDay.get(1) & !isDay.get(2) & !isDay.get(3) & !isDay.get(4) & !isDay.get(5) & isDay.get(6) & isDay.get(0))
            mRepeat = getString(R.string.repeat_weekend);
        else if(!isDay.get(1) & !isDay.get(2) & !isDay.get(3) & !isDay.get(4) & !isDay.get(5) & !isDay.get(6) & !isDay.get(0))
            mRepeat = "";
        else{
            repeatStr.setLength(0);
            if(isDay.get(1))   repeatStr.append(", 월");
            if(isDay.get(2))   repeatStr.append(", 화");
            if(isDay.get(3))   repeatStr.append(", 수");
            if(isDay.get(4))   repeatStr.append(", 목");
            if(isDay.get(5))   repeatStr.append(", 금");
            if(isDay.get(6))   repeatStr.append(", 토");
            if(isDay.get(0))   repeatStr.append(", 일");

            mRepeat = repeatStr.toString();
        }
    }

    protected void clearSetting(){
        // 새 알람 생성
        // timepicker 현재 시각으로 초기화
        // 나머지 view 초기화
        if(isNew){
            Calendar calendar = Calendar.getInstance();
            int curHour = calendar.get(Calendar.HOUR_OF_DAY);
            int curMinute = calendar.get(Calendar.MINUTE);

            mHour = curHour;
            mMinute = curMinute;

            if(Build.VERSION.SDK_INT >= 23){
                timePicker.setHour(curHour);
                timePicker.setMinute(curMinute);
            }
            else{
                timePicker.setCurrentHour(curHour);
                timePicker.setCurrentMinute(curMinute);
            }

            nameEdit.setText(getString(R.string.alarm_name));
            monTog.setChecked(false);
            tueTog.setChecked(false);
            wedTog.setChecked(false);
            thuTog.setChecked(false);
            friTog.setChecked(false);
            satTog.setChecked(false);
            sunTog.setChecked(false);
            weatherRadio.clearCheck();

            for(int i = 0; i < 7; i++)
                isDay.set(i, false);
            mWeather = -1;
        }
        // 기존 알람 수정
        // 기존 알람의 정보로 view 초기화
        else{
            mHour = alarm.getHour();
            mMinute = alarm.getMinute();
            isDay = alarm.getDays();
            mWeather = alarm.getWeather();

            if(Build.VERSION.SDK_INT >= 23){
                timePicker.setHour(mHour);
                timePicker.setMinute(mMinute);
            }
            else{
                timePicker.setCurrentHour(mHour);
                timePicker.setCurrentMinute(mMinute);
            }

            nameEdit.setText(alarm.getName());
            monTog.setChecked(isDay.get(1));
            tueTog.setChecked(isDay.get(2));
            wedTog.setChecked(isDay.get(3));
            thuTog.setChecked(isDay.get(4));
            friTog.setChecked(isDay.get(5));
            satTog.setChecked(isDay.get(6));
            sunTog.setChecked(isDay.get(0));

            switch (mWeather){
                case 1:
                    weatherRadio.check(R.id.radio_rain);
                    break;
                case 2:
                    weatherRadio.check(R.id.radio_snow);
                    break;
                case 3:
                    weatherRadio.check(R.id.radio_typhoon);
                    break;
            }
        }
    }

    public void clickAdd(View view){
        isNew = true;
        slidingLayout.setPanelState(PanelState.EXPANDED);

        if(isDelete)
            clickDone(this.getWindow().getDecorView());
    }

    public void clickDelete(View view){
        isDelete = true;
        alarmAdapter.setIsDelete(isDelete);

        List<AlarmAdapter.itemViewHolder> itemList = alarmAdapter.getItemHolder();
        
        for(AlarmAdapter.itemViewHolder a : itemList)
            a.onDelete();

        doneBtn.setVisibility(View.VISIBLE);
        deleteBtn.setVisibility(View.GONE);
    }

    public void clickDone(View view){
        isDelete = false;
        alarmAdapter.setIsDelete(isDelete);

        List<AlarmAdapter.itemViewHolder> itemList = alarmAdapter.getItemHolder();
        
        for(AlarmAdapter.itemViewHolder a : itemList)
            a.onDone();

        doneBtn.setVisibility(View.GONE);
        deleteBtn.setVisibility(View.VISIBLE);
    }

    public void clickSave(View view){
        // 날씨 선택 안 한 경우
        if(mWeather == -1){
            makeWarning();
            return;
        }

        mTime = Utility.timeToText(mHour, mMinute);

        // 새 알람 설정 후 저장
        if(isNew){
            alarm = new Alarm(mHour, mMinute, mName, mRepeat, mTime, true, isDay, mWeather);
            new Thread(() -> {
                    alarmDB.alarmDao().insert(alarm);
            }).start();
        }
        // 기존 알람 수정 후 저장
        else{
            alarm.setHour(mHour);
            alarm.setMinute(mMinute);
            alarm.setName(mName);
            alarm.setRepeat(mRepeat);
            alarm.setTime(mTime);
            alarm.setIsEnabled(true);
            alarm.setDays(isDay);
            alarm.setWeather(mWeather);
            new Thread(() -> {
                    alarmDB.alarmDao().update(alarm);
            }).start();
        }

        slidingLayout.setPanelState(PanelState.COLLAPSED);
    }

    // 날씨 선택 없이 알람 저장 시 경고창 생성
    public void makeWarning(){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this, R.style.AlarmWarning);
        AlertDialog alertDialog = alertBuilder.create();
        alertDialog.setMessage(getString(R.string.alarm_warning_msg));
        alertDialog.show();

        // 경고 메세지 폰트 적용
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/neo_dgm.ttf");
        TextView message = (TextView) alertDialog.findViewById(android.R.id.message);
        message.setTextSize(20);
        message.setTypeface(typeface, Typeface.NORMAL);
        message.setTextColor(getResources().getColor(R.color.navy));
    }

    public void clickCancle(View view){
        slidingLayout.setPanelState(PanelState.COLLAPSED);
    }

    public void setIsNew(boolean isNew){
        this.isNew = isNew;
    }

    public void setAlarm(Alarm alarm){
        this.alarm = alarm;
    }

    public SlidingUpPanelLayout getSlidingPanel(){
        return slidingLayout;
    }
}
