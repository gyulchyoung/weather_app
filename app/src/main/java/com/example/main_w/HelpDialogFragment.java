package com.example.main_w;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.DialogFragment;

public class HelpDialogFragment extends DialogFragment {
    public static String DIALOG_TAG = "helpDialog";
    private static int DIALOG_INDEX = 1;

    private LinearLayout content;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.Dialog);
        setCancelable(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_dialog_help, container, false);
        content = (LinearLayout) view.findViewById(R.id.help_content);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (DIALOG_INDEX){
                    case 1:
                        content.setBackground(getResources().getDrawable(R.drawable.help_dialog_second));
                        DIALOG_INDEX = 2;
                        break;
                    case 2:
                        content.setBackground(getResources().getDrawable(R.drawable.help_dialog_last));
                        DIALOG_INDEX = 3;
                        break;
                    case 3:
                        dismiss();
                        DIALOG_INDEX = 1;
                        break;
                }
                return false;
            }
        });

        return view;
    }
}