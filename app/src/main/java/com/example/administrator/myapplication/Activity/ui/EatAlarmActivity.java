package com.example.administrator.myapplication.Activity.ui;

import android.app.Activity;
import android.app.Service;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.myapplication.Activity.View.SimpleDialogg;
import com.example.administrator.myapplication.Activity.constants.Constants;
import com.example.administrator.myapplication.Activity.utils.PreferencesUtils;
import com.example.administrator.myapplication.R;

public class EatAlarmActivity extends Activity {
    private MediaPlayer mediaPlayer;
    private Vibrator vibrator;
    private boolean isSound;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock_alarm);
        //注册EventBus
//        EventBus.getDefault().register(this);
        String message = this.getIntent().getStringExtra("msg");
        int flag = this.getIntent().getIntExtra("flag", 0);
        isSound = PreferencesUtils.getBoolean(getApplicationContext(), Constants.SOUND);
        showDialogInBroadcastReceiver(message, flag);
    }
    private void showDialogInBroadcastReceiver(String message, final int flag) {
        if ((flag == 1 || flag == 2) && isSound ) {
            mediaPlayer = MediaPlayer.create(this,R.raw.in_call_alarm);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
            Toast.makeText(getApplicationContext(), "Hello,I come from other thread!",Toast.LENGTH_SHORT ).show();
        }
        //数组参数意义：第一个参数为等待指定时间后开始震动，震动时间为第二个参数。后边的参数依次为等待震动和震动的时间
        //第二个参数为重复次数，-1为不重复，0为一直震动
        if (flag == 0 || flag == 2) {
            vibrator = (Vibrator) this.getSystemService(Service.VIBRATOR_SERVICE);
            vibrator.vibrate(new long[]{100, 10, 100, 600}, 0);
        }

        final SimpleDialogg dialog = new SimpleDialogg(this, R.style.Theme_dialog);
        dialog.show();
        dialog.setTitle("闹钟提醒");
        dialog.setMessage(message);
        dialog.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog.bt_confirm == v || dialog.bt_cancel == v) {
                    if (flag == 1 || flag == 2) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                    }
                    if (flag == 0 || flag == 2) {
                        vibrator.cancel();
                    }
                    dialog.dismiss();
                    finish();
                }
            }
        });
    }

}
