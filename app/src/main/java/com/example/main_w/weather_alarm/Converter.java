package com.example.main_w.weather_alarm;

import androidx.room.TypeConverter;

import java.util.List;
import java.util.ArrayList;

public class Converter {
    @TypeConverter
    public static String booleanListToString(List<Boolean> list){
        String str = "";
        for(boolean i : list){
            if(i)   str += "T";
            else    str += "F";
        }
        return str;
    }

    @TypeConverter
    public static List<Boolean> stringToBooleanList(String str){
        List<Boolean> list = new ArrayList();
        for(int i = 0; i < str.length(); i++){
            if(str.charAt(i) == 'T')    list.add(true);
            else                        list.add(false);
        }
        return list;
    }
}
