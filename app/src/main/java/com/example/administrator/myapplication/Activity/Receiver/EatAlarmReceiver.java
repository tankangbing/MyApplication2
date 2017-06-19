package com.example.administrator.myapplication.Activity.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.administrator.myapplication.Activity.ui.AlarmActivity;
import com.example.administrator.myapplication.Activity.constants.Constants;
import com.example.administrator.myapplication.Activity.utils.AlarmUtil;
import com.example.administrator.myapplication.Activity.utils.PreferencesUtils;


public class EatAlarmReceiver extends BroadcastReceiver {
    private  String msg;
    private long intervalMillis;
    private int flag;
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
             msg = intent.getStringExtra("msg");
             intervalMillis = intent.getLongExtra("intervalMillis", 0);
            if (intervalMillis != 0) {
                AlarmUtil.setAlarmTime(context, System.currentTimeMillis() + intervalMillis,
                        intent);
            }
            flag = intent.getIntExtra("soundOrVibrator", 0);
        boolean isEatSwitch = PreferencesUtils.getBoolean(context, Constants.IS_SWITCH_EAT);
        if(isEatSwitch){
            Intent clockIntent = new Intent(context,AlarmActivity.class);
            clockIntent.putExtra("msg", msg);
            clockIntent.putExtra("flag", flag);
            clockIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(clockIntent);
        }
    }


}
