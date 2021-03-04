package com.example.main_w;

import com.example.main_w.alarm.AlarmFragment;
import com.example.main_w.location.Location;
import com.example.main_w.location.LocationAdapter;
import com.example.main_w.location.LocationDatabase;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CityDialogFragment extends DialogFragment {
    public static String DIALOG_TAG = "cityDialog";

    private static LocationDatabase locationDB;
    private static AlarmFragment alarmFragment;
    private String prevCountry;
    private String curCountry;
    private RecyclerView recyclerView;
    private LocationAdapter locationAdapter;

    public static void setLocationDB(LocationDatabase db){
        locationDB = db;
    }

    public static void setAlarmFragment(AlarmFragment alarmFrag){
        alarmFragment = alarmFrag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.Dialog);

        Bundle bundle = getArguments();
        if(bundle != null)
            prevCountry = bundle.getString("prevCountry");
        else
            prevCountry = PreferenceManager.getString(getContext(), "locationCountry");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Context context = getContext();
        View view = inflater.inflate(R.layout.fragment_dialog_city, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.location_list);

        curCountry = PreferenceManager.getString(context, "locationCountry");
        if(curCountry.equals(""))
            curCountry = AlarmFragment.DEFAULT_COUNTRY;

        //db 접근하기 위해 스레드 사용
        new Thread(() -> {
            List<Location> locationList = locationDB.locationDao().findLocationsWithCountry(curCountry);
            ((MainActivity) context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    locationAdapter = new LocationAdapter(getContext(),CityDialogFragment.this, locationList);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(locationAdapter);
                }
            });
        }).start();

        return view;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        alarmFragment.setLocation();
    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        super.onCancel(dialog);
        PreferenceManager.setString(getContext(), "locationCountry", prevCountry);
    }
}
