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

import static android.provider.Settings.ACTION_INTERNAL_STORAGE_SETTINGS;

public class StorageTab extends Fragment {
    RingProgressBar storageProgress;
    TextView mem;
    Button storageB;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.storage, container, false);


        storageProgress=(RingProgressBar)rootView.findViewById(R.id.progressBarStorage);
        storageB=(Button)rootView.findViewById(R.id.storageDetails);
        mem = (TextView)rootView.findViewById(R.id.mem);

        String memLeft=((MainActivity)getActivity()).memAvailable();
        String totalMemory=((MainActivity)getActivity()).memTotal();
        String memoryUsed= ((MainActivity)getActivity()).memoryUsed();
        float memInfo = ((MainActivity)getActivity()).memoryInfo();

         storageProgress.setProgress((int)memInfo);
        mem.setText("Memory Used: "+memoryUsed+"" +
                "\n\nMemory Left: "+memLeft+
                "\n\nTotal Memory: "+totalMemory);
        storageB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ACTION_INTERNAL_STORAGE_SETTINGS);
                startActivity(intent);
            }
        });

        return rootView;
    }
}