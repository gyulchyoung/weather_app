package com.example.main_w;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.example.main_w.alarm.AlarmFragment;


public class CountryDialogFragment extends DialogFragment implements View.OnClickListener{
    public static String DIALOG_TAG = "countryDialog";

    private TextView gyeonggi;
    private TextView gyeongnam;
    private TextView gyeongbuk;
    private TextView jeonnam;
    private TextView jeonbuk;
    private TextView chungnam;
    private TextView chungbuk;
    private TextView gangwon;
    private TextView jeju;
    private AlarmFragment alarmFragment;

    private String prevCountry;
    private String curCountry;
    private boolean checked;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.Dialog);
        prevCountry = PreferenceManager.getString(getContext(), "locationCountry");

        checked = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_country, container, false);

        gyeonggi = (TextView) view.findViewById(R.id.gyeonggi);
        gyeongnam = (TextView) view.findViewById(R.id.gyeongnam);
        gyeongbuk = (TextView) view.findViewById(R.id.gyeongbuk);
        jeonnam = (TextView) view.findViewById(R.id.jeonnam);
        jeonbuk = (TextView) view.findViewById(R.id.jeonbuk);
        chungnam = (TextView) view.findViewById(R.id.chungnam);
        chungbuk = (TextView) view.findViewById(R.id.chungbuk);
        gangwon = (TextView) view.findViewById(R.id.gangwon);
        jeju = (TextView) view.findViewById(R.id.jeju);

        gyeonggi.setOnClickListener(this);
        gyeongnam.setOnClickListener(this);
        gyeongbuk.setOnClickListener(this);
        jeonnam.setOnClickListener(this);
        jeonbuk.setOnClickListener(this);
        chungnam.setOnClickListener(this);
        chungbuk.setOnClickListener(this);
        gangwon.setOnClickListener(this);
        jeju.setOnClickListener(this);

        return view;
    }

    //dialog 해제 시 해당 지역의 도시 선택하는 dialog 생성
    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if(checked) {
            PreferenceManager.setString(getContext(), "locationCountry", curCountry);
            Bundle bundle = new Bundle();
            bundle.putString("prevCountry", prevCountry);

            FragmentManager fm = getActivity().getSupportFragmentManager();
            DialogFragment cityDialog = new CityDialogFragment();
            cityDialog.setArguments(bundle);
            cityDialog.show(fm, CityDialogFragment.DIALOG_TAG);
        }
    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        super.onCancel(dialog);
        Toast.makeText(getContext(), "cancel", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        checked = true;

        switch (v.getId()){
            case R.id.gyeonggi:
                curCountry = gyeonggi.getText().toString();
                dismiss();
                break;
            case R.id.gyeongnam:
                curCountry = gyeongnam.getText().toString();
                dismiss();
                break;
            case R.id.gyeongbuk:
                curCountry = gyeongbuk.getText().toString();
                dismiss();
                break;
            case R.id.jeonnam:
                curCountry = jeonnam.getText().toString();
                dismiss();
                break;
            case R.id.jeonbuk:
                curCountry = jeonbuk.getText().toString();
                dismiss();
                break;
            case R.id.chungnam:
                curCountry = chungnam.getText().toString();
                dismiss();
                break;
            case R.id.chungbuk:
                curCountry = chungbuk.getText().toString();
                dismiss();
                break;
            case R.id.gangwon:
                curCountry = gangwon.getText().toString();
                dismiss();
                break;
            case R.id.jeju:
                curCountry = jeju.getText().toString();
                dismiss();
                break;
        }
    }
}
