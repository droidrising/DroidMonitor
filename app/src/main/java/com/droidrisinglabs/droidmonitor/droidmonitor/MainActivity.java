package com.droidrisinglabs.droidmonitor.droidmonitor;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Environment;
import android.os.StatFs;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);




    }
    /*public int batteryLevel(){
        BatteryManager bm = (BatteryManager)getSystemService(BATTERY_SERVICE);
        int batLevel = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
        return batLevel;
    }*/
    public int batteryLevel(){
        Intent batteryIntent=registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
       return batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL,-1);

    }
    public boolean isConnected(Context context){
        Intent intent = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        int plugged=intent.getIntExtra(BatteryManager.EXTRA_PLUGGED,-1);
        return plugged == BatteryManager.BATTERY_PLUGGED_AC|| plugged == BatteryManager.BATTERY_PLUGGED_USB || plugged == BatteryManager.BATTERY_PLUGGED_WIRELESS;
    }
    public float voltage(){
        Intent batteryIntent=registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        return (float) ((batteryIntent.getIntExtra(BatteryManager.EXTRA_VOLTAGE,0))*0.001);
    }

    public int temp(){
        Intent batteryIntent=registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        return (batteryIntent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1));
    }
    public String tech (){
        Intent batteryIntent=registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        return batteryIntent.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY);
    }
    public String health(){
        Intent batteryIntent=registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        int health= batteryIntent.getIntExtra(BatteryManager.EXTRA_HEALTH, -1);
        switch(health){
            case BatteryManager.BATTERY_HEALTH_COLD:
                return "Battery Health: Cold";
            case BatteryManager.BATTERY_HEALTH_DEAD:
                return "Battery Health: Dead";
            case BatteryManager.BATTERY_HEALTH_GOOD:
                return "Battery Health: Good";
            case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                return "Battery Health: Over Voltage";
            case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                return "Battery Health: Overheat";
            case BatteryManager.BATTERY_HEALTH_UNKNOWN:
                return "Battery Health: Unknown";
            case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
                return "Battery Health: Failure";
            default:
                return "";

        }
    }

    public  String memAvailable(){
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long availableBlocks = stat.getAvailableBlocksLong();
        return Formatter.formatFileSize(this, availableBlocks * blockSize);
    }
    public String memTotal(){
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        return Formatter.formatFileSize(this, stat.getTotalBytes());
    }
    public String memoryUsed(){
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long availableBlocks = stat.getAvailableBlocksLong();
        long totalAvailableBytes=stat.getTotalBytes();
        return Formatter.formatFileSize(this, totalAvailableBytes-(availableBlocks*blockSize));

    }

    public float memoryInfo(){
        int mem = 0;
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long availableBlocks = stat.getAvailableBlocksLong();
        long totalAvailableBytes=stat.getTotalBytes();
        long memAvailable = availableBlocks * blockSize;

        memAvailable/=1073741824;
        totalAvailableBytes/=1073741824;
        long memused=totalAvailableBytes-memAvailable;
       float mempercentage = (float)memused/totalAvailableBytes;
        return mempercentage*100;


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
           //Returns current tabs
            switch(position){
                case 0:
                    BatteryTab tab1= new BatteryTab();
                    return tab1;
                case 1:
                    StorageTab tab2=new StorageTab();
                    return tab2;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "BATTERY";
                case 1:
                    return "STORAGE";

            }
            return null;
        }
    }
}
