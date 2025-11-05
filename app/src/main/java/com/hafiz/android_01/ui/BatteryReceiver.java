package com.hafiz.android_01.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.widget.Toast;

public class BatteryReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL,-1);
        int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE,-1);
        int batteryPct = (int) ((level /(float) scale) *100);

        int currentStep = batteryPct /10;

        int lastShownStep = -1;

        if(currentStep != lastShownStep){
            lastShownStep =currentStep;

            switch (currentStep){
                case 10:
                    Toast.makeText(context, "battery full", Toast.LENGTH_SHORT).show();
                    break;
                case 8:
                    Toast.makeText(context, "battery is at 80%", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(context, "battery is at "+ batteryPct +"%", Toast.LENGTH_SHORT).show();
                    break;

            }
        }

    }
}
