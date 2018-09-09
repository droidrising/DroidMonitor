package com.droidrisinglabs.droidmonitor.droidmonitor;

/**
 * Created by Khush on 1/15/2018.
 */
import android.content.Intent;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import io.netopen.hotbitmapgg.library.view.RingProgressBar;

import static android.content.Intent.ACTION_POWER_USAGE_SUMMARY;
/*
Things to add:
- Refresh button for battery
 */
public class BatteryTab extends Fragment {
    RingProgressBar ringProgress;
    TextView isCharging, voltage, temp, tech, health;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.battery, container, false);
        
        //View Declarations
        ringProgress = (RingProgressBar)rootView.findViewById(R.id.progressBarBattery);
        isCharging=(TextView)rootView.findViewById(R.id.isCharging);
        voltage=(TextView)rootView.findViewById(R.id.voltage);
        Button button = (Button)rootView.findViewById(R.id.detailsButton);
        Button refresh = (Button)rootView.findViewById(R.id.refresh);
        temp=(TextView)rootView.findViewById(R.id.temp);
        tech=(TextView)rootView.findViewById(R.id.tech);
        health=(TextView)rootView.findViewById(R.id.health);

        //Battery Info
        int batteryLevel=  ((MainActivity)getActivity()).batteryLevel();
        float v= (((MainActivity) getActivity()).voltage());
        int temperature = ((MainActivity) getActivity()).temp();
        temperature/=10;
        String technology = ((MainActivity) getActivity()).tech();
        String bHealth=((MainActivity) getActivity()).health();

        //Settings
        ringProgress.setProgress(batteryLevel);
        voltage.setText("Voltage (V): "+v);
        temp.setText("Temperature (C): "+temperature);
        tech.setText("Battery type: "+technology);
        health.setText(bHealth);
        boolean isPlugged=((MainActivity) getActivity()).isConnected(getContext());
        if(isPlugged==true)
            isCharging.setText("Plugged in.");
        else
            isCharging.setText("Not plugged in.");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ACTION_POWER_USAGE_SUMMARY);
                startActivity(intent);
            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int batteryLevel_updated=  ((MainActivity)getActivity()).batteryLevel();
                float v_updated= (((MainActivity) getActivity()).voltage());
                int temperature_updated = ((MainActivity) getActivity()).temp()/10;
                String technology_updated = ((MainActivity) getActivity()).tech();
                String bHealth_updated=((MainActivity) getActivity()).health();

                ringProgress.setProgress(batteryLevel_updated);
                voltage.setText("Voltage (V): "+v_updated);
                temp.setText("Temperature (C): "+temperature_updated);
                tech.setText("Battery type: "+technology_updated);
                health.setText(bHealth_updated);
                boolean isPlugged_updated=((MainActivity) getActivity()).isConnected(getContext());
                if(isPlugged_updated==true)
                    isCharging.setText("Plugged in.");
                else
                    isCharging.setText("Not plugged in");
            }
        });
        return rootView;
    }
}
