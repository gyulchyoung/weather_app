package com.example.main_w.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.main_w.HelpDialogFragment;
import com.example.main_w.MainActivity;
import com.example.main_w.R;
import com.example.main_w.help;
import com.example.main_w.clock;
import com.example.main_w.specific_weather;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        ImageButton help = root.findViewById(R.id.help);
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment helpDialog = new HelpDialogFragment();
                helpDialog.show(getChildFragmentManager(), HelpDialogFragment.DIALOG_TAG);
            }
        });

        ImageButton clock = root.findViewById(R.id.clock);
        clock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), clock.class);
                startActivity(intent);
            }
        });

        ImageButton specific_weather = root.findViewById(R.id.specific_weather);
        specific_weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), specific_weather.class);
                startActivity(intent);
            }
        });

        return root;
    }

}