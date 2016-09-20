package com.example.administrator.weathertest.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.administrator.weathertest.service.AutoUpdateService;

public class AutoUpdateReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
       Intent intent1=new Intent(context, AutoUpdateService.class);
        context.startService(intent1);
    }
}
