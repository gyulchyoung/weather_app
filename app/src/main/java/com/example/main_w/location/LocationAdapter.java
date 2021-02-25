package com.example.main_w.location;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.main_w.CityDialogFragment;
import com.example.main_w.MainActivity;
import com.example.main_w.PreferenceManager;
import com.example.main_w.R;

import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.itemViewHolder> {
    //울릉도, 독도의 지역 코드
    private static String ULLEUNG_CODE = "11E00101";
    private static String DOKDO_CODE = "11E00102";

    private CityDialogFragment cityDialog;
    private List<Location> locationList;
    private Context context;

    public LocationAdapter(Context context, CityDialogFragment cityDialog, List<Location> locationList) {
        this.context = context;
        this.cityDialog = cityDialog;
        this.locationList = locationList;
    }
    
    @Override
    public itemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_location_item, parent, false);
        return new itemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull itemViewHolder viewHolder, final int position){
        viewHolder.onBind(locationList.get(position));
    }

    @Override
    public int getItemCount(){
        return locationList.size();
    }

    public class itemViewHolder extends RecyclerView.ViewHolder{

        public TextView city;
        private Location location;

        public itemViewHolder(@NonNull View itemView){
            super(itemView);
            city = (TextView) itemView.findViewById(R.id.city);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    //db 내에서 문자열 깨짐으로 인해 직접 적용
                    if(location.id == 78) {
                        PreferenceManager.setString(view.getContext(),"locationCode", ULLEUNG_CODE);
                        PreferenceManager.setInt(view.getContext(), "locationX", location.axisX);
                        PreferenceManager.setInt(view.getContext(), "locationY", location.axisY);
                    }
                    else if(location.id == 79) {
                        PreferenceManager.setString(view.getContext(),"locationCode", DOKDO_CODE);
                        PreferenceManager.setInt(view.getContext(), "locationX", location.axisX);
                        PreferenceManager.setInt(view.getContext(), "locationY", location.axisY);
                    }
                    else {
                        PreferenceManager.setString(view.getContext(),"locationCode", location.code);
                        PreferenceManager.setInt(view.getContext(), "locationX", location.axisX);
                        PreferenceManager.setInt(view.getContext(), "locationY", location.axisY);
                    }

                    cityDialog.dismiss();
                }
            });
        }

        public void onBind(Location location){
            this.location = location;
            city.setText(location.city);
        }
    }
}