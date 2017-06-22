package com.example.administrator.myapplication.Activity.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.administrator.myapplication.Activity.been.MyApplication;
import com.example.administrator.myapplication.Activity.constants.Constants;
import com.example.administrator.myapplication.Activity.db.RemindEatDao;
import com.example.administrator.myapplication.Activity.utils.AlarmUtil;
import com.example.administrator.myapplication.Activity.utils.PreferencesUtils;
import com.example.administrator.myapplication.R;

import java.util.Calendar;

/**
 * Created by Administrator on 2017/5/18 0018.
 */

public class RemindEatCreatActivity extends Activity implements View.OnClickListener{
    private Button btn1, btn2,btn3,btn4,btn5;
    private ImageButton ibEat;
    private TextView tvTime,tvReprtition,tvYao,tvTitlEat;
    private TextView etNunber;
    private String[] areas = new String[]{"每天","周一", "周二", "周三", "周四", "周五", "周六","周日" };
    private boolean[] areaState=new boolean[]{false,false, false, false, false, false, false,false };
    private int i = 0 ,id ,mPosition;
    private RemindEatDao remindEatDao;
    private  String action ;
    public static final int EAT_BLACK = 200;
    public static final String ALARM_ACTION = "com.eatt.alarm.clock";
    private int flag;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initEvent();

    }

    private void initView() {
        setContentView(R.layout.activity_remind_eat_creat);
        tvTitlEat = (TextView)findViewById(R.id.tv_title_eat);
        tvTime = (TextView) findViewById(R.id.tvy_time);
        tvReprtition = (TextView) findViewById(R.id.tvy_repetition);
        tvYao = (TextView) findViewById(R.id.tvy_yao);
        btn1 = (Button) findViewById(R.id.btny_cancel);
        btn3 = (Button) findViewById(R.id.btny_save);
        btn4 = (Button) findViewById(R.id.btny_minus);
        btn5 = (Button) findViewById(R.id.btny_add);
        etNunber = (TextView) findViewById(R.id.edty_number);
        ibEat = (ImageButton)findViewById(R.id.im_er_pre);

        ibEat.setOnClickListener(this);
        tvTime.setOnClickListener(this);
        tvReprtition.setOnClickListener(new RemindEatCreatActivity.CheckBoxClickListener());
        btn1.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);

    }

    private void initData() {
        remindEatDao = new RemindEatDao(this);
        // 知道是更新操作
        action = getIntent().getAction();
        if (RemindMreasureActivity.UPDATE.equals(action)) {// 更新逻辑
            tvTitlEat.setText("更新吃药提醒");
            id =getIntent().getIntExtra("id", -1);
            String time = getIntent().getStringExtra("time");
            String repeat = getIntent().getStringExtra("repeat");
            String yao = getIntent().getStringExtra("yao");
            String ci = getIntent().getStringExtra("ci");

            tvTime.setText(time);
            tvReprtition.setText(repeat);
            tvYao.setText(yao);
            etNunber.setText(ci);
            mPosition=getIntent().getIntExtra("position", -1);
            btn3.setText("更新");
        }
    }

    private void initEvent() {
        //toobsr的兼容
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.colorPrimaryDark);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.im_er_pre:
                Pre();
                break;
            case R.id.tvy_time:
                //设置时间
                GetTime();
                break;
            case R.id.btny_minus:
                //次数减
                Minus();
                break;
            case R.id.btny_add:
                //次数加
                Add();
                break;
            case R.id.btny_cancel:
                Pre();
                break;
            case R.id.btny_save:
                Save();
                break;
            default:
                break;
        }
    }

    private void Save() {
        //标记已经创建数据库
        PreferencesUtils.putBoolean(getApplicationContext(), Constants.IS_EAT,true);
        String getTime = tvTime.getText().toString();
        String getReprtition = tvReprtition.getText().toString();
        String getYao = tvYao.getText().toString();
        String getNumber = etNunber.getText().toString();


        if (RemindEatActivity.UPDATE.equals(action)){
            int h = Integer.parseInt(getTime.split(":")[0]);
            int m = Integer.parseInt(getTime.split(":")[1]);
            int week = getWeek(getReprtition);
            if(week == 0){
                flag = 1;
            }else {
                flag = 2;
            }
            remindEatDao.update(id,getTime,getReprtition,getYao,getNumber);
            AlarmUtil.cancelAlarm(this,ALARM_ACTION,id);//先删
            AlarmUtil.setAlarm(this, flag,h, m, id, week, getYao, 2,ALARM_ACTION);//再添加
        }else {
            if(TextUtils.isEmpty(getTime)){
                Toast.makeText(getApplicationContext(), "提醒信息没填完", Toast.LENGTH_SHORT).show();
                return ;
            }
            if(TextUtils.isEmpty(getReprtition)){
                Toast.makeText(getApplicationContext(), "提醒信息没填完", Toast.LENGTH_SHORT).show();
                return ;
            }
            if(TextUtils.isEmpty(getYao)){
                Toast.makeText(getApplicationContext(), "提醒信息没填完", Toast.LENGTH_SHORT).show();
                return ;
            }
            int h = Integer.parseInt(getTime.split(":")[0]);
            int m = Integer.parseInt(getTime.split(":")[1]);
            int week = getWeek(getReprtition);
            if(week == 0){
                flag = 1;
            }else {
                flag = 2;
            }
            int id = PreferencesUtils.getInt(this, Constants.GET_ID);
            AlarmUtil.setAlarm(this, flag,h, m, id, week,getYao, 2,ALARM_ACTION);
            remindEatDao.add(id,getTime,getReprtition,getYao,getNumber);
            id++;
            PreferencesUtils.putInt(this,Constants.GET_ID,id);
        }

        // 5 回传数据，finish
        Intent data = new Intent();
        data.putExtra("getTime", getTime);
        data.putExtra("getReprtition", getReprtition);
        data.putExtra("getYao", getYao);
        data.putExtra("getNumber", getNumber);
        data.putExtra("position", mPosition);
        setResult(EAT_BLACK, data);
        finish();

    }
    private int getWeek(String getReprtition) {
        int week = 0;
        switch (getReprtition) {
            case "每天":
                week = 0;
                break;
            case "周一":
                week = 1;
                break;
            case "周二":
                week = 2;
                break;
            case "周三":
                week = 3;
                break;
            case "周四":
                week = 4;
                break;
            case "周五":
                week = 5;
                break;
            case "周六":
                week = 6;
                break;
            case "周日":
                week = 7;
                break;
            default:
                break;
        }
        return week;
    }
    //返回
    private void Pre() {
        finish();
    }

    private void GetTime() {
        Calendar c = Calendar.getInstance();
        // 创建一个TimePickerDialog实例，并把它显示出来
        new TimePickerDialog(RemindEatCreatActivity.this,
                // 绑定监听器
                new TimePickerDialog.OnTimeSetListener() {
                    //设置时间
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String s = minute + "";
                        String hour = hourOfDay + "";
                        if(minute<10){
                            s = "0" + minute;
                        }
                        if(hourOfDay<10){
                            hour = "0" + hourOfDay;
                        }
                        tvTime.setText( hour +":" + s);
                    }
                }
                // 设置初始时间
                , c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE),
                // true表示采用24小时制
                true).show();
    }
    private void Minus() {
        if(i > 1){
            i--;
        }
        etNunber.setText(i+"");
    }
    private void Add() {
        if(i < 10){
            i++;
        }
        etNunber.setText(i+"");
    }
    class AlertClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            new AlertDialog.Builder(RemindEatCreatActivity.this).setTitle("请选择日期").setItems(areas,new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which){
                    Toast.makeText(RemindEatCreatActivity.this, "您已经选择了: " + which + ":" + areas[which],Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }
            }).show();
        }
    }
     class CheckBoxClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {

            AlertDialog.Builder builder = new AlertDialog.Builder(RemindEatCreatActivity.this);
            builder.setTitle("请选择日期");
            builder.setSingleChoiceItems(areas, -1, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    tvReprtition.setText(areas[which]);
                    dialog.dismiss();
                }
            });
            builder.show();
        }

    }
}
