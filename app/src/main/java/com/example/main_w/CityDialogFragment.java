package com.example.main_w;

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
    public static LocationDatabase locationDB;

    private RecyclerView recyclerView;
    private LocationAdapter locationAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.Dialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_city, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.location_list);

        Context context = getContext();
        Bundle bundle = getArguments();
        String country = bundle.getString("country");

        //db 접근하기 위해 스레드 사용
        new Thread(() -> {
            List<Location> locationList = locationDB.locationDao().findLocationsWithCountry(country);
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
    }
}
