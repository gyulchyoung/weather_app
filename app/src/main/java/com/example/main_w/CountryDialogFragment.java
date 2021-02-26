package com.example.main_w;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;


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

    private String country;
    private boolean checked;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.Dialog);

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
            Bundle bundle = new Bundle();
            bundle.putString("country", country);

            FragmentManager fm = getActivity().getSupportFragmentManager();
            DialogFragment cityDialog = new CityDialogFragment();
            cityDialog.setArguments(bundle);
            cityDialog.show(fm, CityDialogFragment.DIALOG_TAG);
        }
    }

    @Override
    public void onClick(View v) {
        checked = true;

        switch (v.getId()){
            case R.id.gyeonggi:
                country = gyeonggi.getText().toString();
                dismiss();
                break;
            case R.id.gyeongnam:
                country = gyeongnam.getText().toString();
                dismiss();
                break;
            case R.id.gyeongbuk:
                country = gyeongbuk.getText().toString();
                dismiss();
                break;
            case R.id.jeonnam:
                country = jeonnam.getText().toString();
                dismiss();
                break;
            case R.id.jeonbuk:
                country = jeonbuk.getText().toString();
                dismiss();
                break;
            case R.id.chungnam:
                country = chungnam.getText().toString();
                dismiss();
                break;
            case R.id.chungbuk:
                country = chungbuk.getText().toString();
                dismiss();
                break;
            case R.id.gangwon:
                country = gangwon.getText().toString();
                dismiss();
                break;
            case R.id.jeju:
                country = jeju.getText().toString();
                dismiss();
                break;
        }
    }
}
