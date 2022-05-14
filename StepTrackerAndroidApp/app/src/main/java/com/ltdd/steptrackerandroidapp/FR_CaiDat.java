package com.ltdd.steptrackerandroidapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class FR_CaiDat extends Fragment {
    Switch switchDL;
    View view;
    boolean check=false;
    CaiDatFragmentListener activityCallback;

    public interface CaiDatFragmentListener {
        public void onSwicthChange(boolean bl);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            activityCallback = (CaiDatFragmentListener) context;

        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()
                    + " You must implement FirstFragmentListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_f_r__cai_dat, container, false);
        AnhXa();
        if (check) {
            switchDL.toggle();
            view.findViewById(R.id.img_tieude).setBackgroundResource(R.drawable.trang_chu_background_dark);
        }

        switchDL.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                switchChange(view);
            }
        });
        return view;
    }


    private void AnhXa() {
        switchDL = (Switch) view.findViewById(R.id.switch_darkMode);
    }

    public void switchChange (View view){
        activityCallback.onSwicthChange(this.switchDL.getAutofillValue().getToggleValue());
    }

    public void autoFillSwitch (boolean b){
        this.check=b;

    }

}
