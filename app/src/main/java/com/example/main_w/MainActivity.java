package com.example.main_w;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.main_w.location.LocationDatabase;
import com.google.android.material.navigation.NavigationView;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton help = findViewById(R.id.help);
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getSupportFragmentManager();
                DialogFragment helpDialog = new HelpDialogFragment();
                helpDialog.show(fm, HelpDialogFragment.DIALOG_TAG);
            }
        });

        ImageButton clock = findViewById(R.id.clock);
        clock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(view.getContext(), clock.class);
                //startActivity(intent);

                //데이터가 원활하게 작동하는지 확인하기 위한 임시 토스트 (삭제 예정)
                String code = PreferenceManager.getString(view.getContext(), "locationCode");
                int x = PreferenceManager.getInt(view.getContext(), "locationX");
                int y = PreferenceManager.getInt(view.getContext(), "locationY");
                String test = code + ", " + String.valueOf(x) + ", " + String.valueOf(y);
                Toast.makeText(view.getContext(), test, Toast.LENGTH_SHORT).show();
            }
        });

        ImageButton specific_weather = findViewById(R.id.specific_weather);
        specific_weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), specific_weather.class);
                startActivity(intent);
            }
        });
        //drawer view 버튼 클릭시
        ImageView drawerBtn = (ImageView) findViewById(R.id.drawer_btn);
        drawerBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                if (!drawer.isDrawerOpen(Gravity.LEFT)) {
                    drawer.openDrawer(Gravity.LEFT);
                }
            }
        });

    }

}