package com.example.main_w;

public class Utility {
    // int로 저장된 시각과 분을 String으로 변환
    public static String timeToText(int hour, int minute){
        
        String stringHour, stringMinute;

        if(String.valueOf(hour).length() == 1)
            stringHour = "0" + hour;
        else    stringHour = String.valueOf(hour);

        if(String.valueOf(minute).length() == 1)
            stringMinute = "0" + minute;
        else    stringMinute = String.valueOf(minute);
        
        return stringHour + ":" + stringMinute;
    }

}
