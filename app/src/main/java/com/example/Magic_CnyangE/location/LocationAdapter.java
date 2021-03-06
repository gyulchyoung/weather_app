package com.example.Magic_CnyangE.location;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Magic_CnyangE.CityDialogFragment;
import com.example.Magic_CnyangE.PreferenceManager;
import com.example.Magic_CnyangE.R;

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
                    if(location.id == 78)
                        PreferenceManager.setString(view.getContext(),"locationCityCode", ULLEUNG_CODE);
                    else if(location.id == 79)
                        PreferenceManager.setString(view.getContext(),"locationCityCode", DOKDO_CODE);
                    else
                        PreferenceManager.setString(view.getContext(),"locationCityCode", location.cityCode);
                    
                    PreferenceManager.setString(view.getContext(), "locationCountryCode", location.countryCode);
                    PreferenceManager.setString(view.getContext(), "locationCountry", location.country);
                    PreferenceManager.setString(view.getContext(), "locationCity", location.city);
                    PreferenceManager.setInt(view.getContext(), "locationX", location.axisX);
                    PreferenceManager.setInt(view.getContext(), "locationY", location.axisY);

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
