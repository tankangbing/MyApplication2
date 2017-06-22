package com.example.administrator.myapplication.Activity.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.administrator.myapplication.Activity.constants.Constants;
import com.example.administrator.myapplication.Activity.utils.PreferencesUtils;
import com.example.administrator.myapplication.R;

/**
 * Created by Administrator on 2017/5/18 0018.
 */

public class RemindActivity extends Activity implements View.OnClickListener {
    TextView tv1,tv2;
    private ImageButton ibRemind,ibRemindMeasureSwitch,ibRemindEatOnSwitch;
    public static final String ALARM_ACTION = "com.eat.alarm.on.clock";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initEvent();
    }

    private void initEvent() {
        //toobsr的兼容
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.colorPrimaryDark);
        }
    }

    private void initData() {
        boolean isMeasureSwitch = PreferencesUtils.getBoolean(getApplicationContext(), Constants.IS_SWITCH_MEASURE);
        if(isMeasureSwitch){
            //初始化
            ibRemindMeasureSwitch.setImageDrawable(getResources().getDrawable(R.drawable.on));
        }else {
            ibRemindMeasureSwitch.setImageDrawable(getResources().getDrawable(R.drawable.off));
        }
        boolean isEatSwitch = PreferencesUtils.getBoolean(getApplicationContext(), Constants.IS_SWITCH_EAT);
        if(isEatSwitch){
            ibRemindEatOnSwitch.setImageDrawable(getResources().getDrawable(R.drawable.on));
        }else {
            ibRemindEatOnSwitch.setImageDrawable(getResources().getDrawable(R.drawable.off));
        }
    }

    private void initView() {
        setContentView(R.layout.activity_remind);
        tv1 = (TextView) findViewById(R.id.tv_mreasure_remind);
        tv2 = (TextView) findViewById(R.id.tv_eat_remind);
        ibRemind = (ImageButton)findViewById(R.id.im_remind_pre) ;
        ibRemindMeasureSwitch = (ImageButton)findViewById(R.id.im_remindmeasure_on) ;
        ibRemindEatOnSwitch = (ImageButton)findViewById(R.id.im_remindeat_on);

        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        ibRemind.setOnClickListener(this);
        ibRemindMeasureSwitch.setOnClickListener(this);
        ibRemindEatOnSwitch.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_mreasure_remind:
//                Toast.makeText(getApplicationContext(), "Hello,I come from other thread!",Toast.LENGTH_SHORT ).show();
              Measure();
                break;
            case R.id.tv_eat_remind:
               Eat();
                break;
            case R.id.im_remind_pre:
               finish();
                break;
            case R.id.im_remindmeasure_on:
                MeasureoSwitch();
                break;
            case R.id.im_remindeat_on:
                Eatswitch();
                break;
            default:
                break;
        }
    }

    private void MeasureoSwitch() {
        boolean isMeasureSwitch = PreferencesUtils.getBoolean(getApplicationContext(), Constants.IS_SWITCH_MEASURE);
        if(isMeasureSwitch){
            //表示开
            PreferencesUtils.putBoolean(getApplicationContext(), Constants.IS_SWITCH_MEASURE,false);
            ibRemindMeasureSwitch.setImageDrawable(getResources().getDrawable(R.drawable.off));
        }else {
            //表示关
            PreferencesUtils.putBoolean(getApplicationContext(), Constants.IS_SWITCH_MEASURE,true);
            ibRemindMeasureSwitch.setImageDrawable(getResources().getDrawable(R.drawable.on));
        }
//        EventBus.getDefault().post(new MeasureEvent(!isMeasureSwitch));

    }

    private void Eatswitch() {
        boolean isEatSwitch = PreferencesUtils.getBoolean(getApplicationContext(), Constants.IS_SWITCH_EAT);
        if(isEatSwitch){
            //表示开
            PreferencesUtils.putBoolean(getApplicationContext(), Constants.IS_SWITCH_EAT,false);
            ibRemindEatOnSwitch.setImageDrawable(getResources().getDrawable(R.drawable.off));
        }else {
            //表示关
            PreferencesUtils.putBoolean(getApplicationContext(), Constants.IS_SWITCH_EAT,true);
            ibRemindEatOnSwitch.setImageDrawable(getResources().getDrawable(R.drawable.on));
        }
        boolean b = !isEatSwitch;
        Intent intent = new Intent(ALARM_ACTION);
        intent.putExtra("isMeasureSwitch",b);
        sendBroadcast(intent);
    }

    private void Eat() {
        //还没创建好数据库
        boolean isMeasure = PreferencesUtils.getBoolean(getApplicationContext(), Constants.IS_EAT);
        if(isMeasure){
            Intent intent2 = new Intent(RemindActivity.this, RemindEatActivity.class);
            startActivity(intent2);
        }else {
            Intent intent2 = new Intent(RemindActivity.this, RemindEatCreatActivity.class);
            startActivity(intent2);
        }
    }

    private void Measure() {
        //测量提醒  还没创建好数据库
        boolean isEat = PreferencesUtils.getBoolean(getApplicationContext(), Constants.IS_MEASURE);
        if(isEat){
            Intent intent1 = new Intent(RemindActivity.this, RemindMreasureActivity.class);
            startActivity(intent1);
        }else {
            Intent intent1 = new Intent(RemindActivity.this, RemindMreasureCreadActivity.class);
            startActivity(intent1);

        }
    }
}
