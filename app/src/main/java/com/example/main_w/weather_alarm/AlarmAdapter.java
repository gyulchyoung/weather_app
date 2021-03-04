package com.example.main_w.weather_alarm;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.main_w.R;
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState;

import java.util.List;
import java.util.ArrayList;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.itemViewHolder> {

    private List<Alarm> alarmList = new ArrayList<>();
    private Context context;
    private AlarmDatabase db;

    private List<itemViewHolder> itemList = new ArrayList<>();

    private boolean isDelete = false;

    public AlarmAdapter(Context context, AlarmDatabase db){
        this.context = context;
        this.db = db;
    }

    @Override
    public itemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_list_item, parent, false);

        itemViewHolder viewHolder = new itemViewHolder(view);
        itemList.add(viewHolder);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull itemViewHolder viewHolder, final int position){
        viewHolder.onBind(alarmList.get(position), position);

        if(isDelete)
            viewHolder.onDelete();
        else
            viewHolder.onDone();
    }

    @Override
    public int getItemCount(){
        return alarmList.size();
    }

    public void setItem(List<Alarm> data){
        alarmList = data;
        notifyDataSetChanged();
    }

    public List<Alarm> getItem(){
        return alarmList;
    }

    public List<itemViewHolder> getItemHolder(){
        return itemList;
    }

    public void setIsDelete(boolean isDelete){
        this.isDelete = isDelete;
    }

    public class itemViewHolder extends RecyclerView.ViewHolder{

        private Alarm alarm;
        private TextView time, name, repeat;
        private ImageView deleteBtn;
        private Switch onOff;

        private int index;
        private boolean isCanClick;

        public itemViewHolder(@NonNull View itemView){
            super(itemView);
            time = (TextView) itemView.findViewById(R.id.time);
            name = (TextView) itemView.findViewById(R.id.name);
            repeat = (TextView) itemView.findViewById(R.id.repeat);
            deleteBtn = (ImageView) itemView.findViewById(R.id.delete_btn);
            onOff = (Switch) itemView.findViewById(R.id.switch_enable);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    if(isCanClick){
                        ((AlarmListActivity) context).setIsNew(false);
                        ((AlarmListActivity) context).setAlarm(alarm);
                        ((AlarmListActivity) context).getSlidingPanel().setPanelState(PanelState.EXPANDED);
                    }
                }
            });

            deleteBtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    alarm.cancelAlarm(context);

                    new Thread(() -> {
                        db.alarmDao().delete(alarm);
                    }).start();

                    if(getItemCount() == 0)
                        ((AlarmListActivity) context).clickDone(view);
                }
            });

        }

        public void onBind(Alarm alarm, int position){
            index = position;

            this.alarm = alarm;

            time.setText(alarm.getTime());
            name.setText(alarm.getName());
            repeat.setText(alarm.getRepeat());
            onOff.setChecked(alarm.getIsEnabled());

            if(!isDelete){
                deleteBtn.setVisibility(View.GONE);
                deleteBtn.setX(-200.0f);
            }

            if(alarm.getIsEnabled())
                alarm.setAlarm(context);

            onOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                    if(isChecked){
                        alarm.setIsEnabled(isChecked);
                        new Thread(() -> {
                            db.alarmDao().update(alarm);
                        }).start();
                        alarm.setAlarm(context);
                    }
                    else{
                        alarm.setIsEnabled(isChecked);
                        new Thread(() -> {
                            db.alarmDao().update(alarm);
                        }).start();
                        alarm.cancelAlarm(context);
                    }
                }
            });
        }

        public void onDelete(){
            isCanClick = false;
            deleteBtn.setVisibility(View.VISIBLE);

            deleteBtn.animate().translationX(0).setDuration(300);
            time.animate().translationX(100.0f).setDuration(300);
            name.animate().translationX(100.0f).setDuration(300);
            repeat.animate().translationX(100.0f).setDuration(300);
            onOff.animate().alpha(0.0f).translationX(-50.0f).setDuration(300)
                .withEndAction(new Runnable(){
                    @Override
                    public void run(){
                        onOff.setVisibility(View.GONE);
                    }
                }).start();
        }

        public void onDone(){
            isCanClick = true;
            onOff.setVisibility(View.VISIBLE);

            deleteBtn.animate().translationX(-200.0f).setDuration(300);
            time.animate().translationX(0).setDuration(300);
            name.animate().translationX(0).setDuration(300);
            repeat.animate().translationX(0).setDuration(300);
            onOff.animate().alpha(1.0f).translationX(0).setDuration(300);
        }

        public int getIndex(){
            return index;
        }
    }
}
