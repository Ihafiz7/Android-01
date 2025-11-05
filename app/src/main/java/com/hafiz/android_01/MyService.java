package com.hafiz.android_01;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Service Started", Toast.LENGTH_SHORT).show();
        new Thread(() -> {
            for (int i = 0; i <= 15 ; i++) {
                try {
                    Thread.sleep(3000)
                    ;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Service Running: " + i);
            }
        }).start();
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "Service Started on create", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Service Ended", Toast.LENGTH_SHORT).show();

    }
}


