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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.administrator.myapplication.Activity.been.MyApplication;
import com.example.administrator.myapplication.Activity.constants.Constants;
import com.example.administrator.myapplication.Activity.db.RemindMeasureDao;
import com.example.administrator.myapplication.Activity.utils.AlarmUtil;
import com.example.administrator.myapplication.Activity.utils.PreferencesUtils;
import com.example.administrator.myapplication.R;

import java.util.Calendar;

/**
 * Created by Administrator on 2017/5/18 0018.
 */

public class RemindMreasureCreadActivity extends Activity implements View.OnClickListener {
    private Button btnCreatCancel,btnCreatSave;
    private ImageButton imageButton;
    private TextView tvCreatTime,tvCreatReprtition,tvCreatMeasure;
    private String[] areas = new String[]{"每天","周一", "周二", "周三", "周四", "周五", "周六" ,"周日"};
    private boolean[] areaState=new boolean[]{false,false, false, false, false, false, false,false };
    private ListView areaCheckListView;
    private RemindMeasureDao remindMeasureDao;
    public static final int REP_BLACK = 200;
    private int mPosition;
    private int id ;
    private  String action ;
    private String minute1;
    private String hour1;
    private int mWitch;
    private int flag;
//    public static final String ALARM_ACTION = "com.loonggg.alarm.clock";
    public static final String ALARM_ACTION = "com.mearsure.alarm.clock";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initEvent();

    }

    private void initView() {
        setContentView(R.layout.activity_remind_measure_cread);
        tvCreatTime = (TextView) findViewById(R.id.tv_creat_time);
        tvCreatReprtition = (TextView) findViewById(R.id.tv_creat_repetition);
        btnCreatCancel = (Button) findViewById(R.id.btn_creat_cancel);
        tvCreatMeasure = (TextView)findViewById(R.id.tv_creatmeasrue);
        btnCreatSave = (Button) findViewById(R.id.btn_creat_save);
        imageButton = (ImageButton)findViewById(R.id.im_cmr_pre) ;

        tvCreatMeasure.setOnClickListener(this);
        imageButton.setOnClickListener(this);
        tvCreatReprtition.setOnClickListener(new CheckBoxClickListener());
        tvCreatTime.setOnClickListener(this);
        btnCreatSave.setOnClickListener(this);
        btnCreatCancel.setOnClickListener(this);

        remindMeasureDao = new RemindMeasureDao(this);
    }

    private void initData() {
        // 知道是更新操作
         action = getIntent().getAction();
        if (RemindMreasureActivity.UPDATE.equals(action)) {// 更新逻辑
            tvCreatMeasure.setText("更新测量提醒");
            id =getIntent().getIntExtra("id", -1);
            String time = getIntent().getStringExtra("rmtime");
            String repeat = getIntent().getStringExtra("repeat");
            tvCreatTime.setText(time);
            tvCreatReprtition.setText(repeat);
            mPosition=getIntent().getIntExtra("position", -1);
            btnCreatSave.setText("更新");
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
            case R.id.im_cmr_pre:
                Pre();
                break;
            case R.id.tv_creat_time:
                //设置时间
                GetTime();
                break;
            case R.id.btn_creat_cancel:
                Pre();//取消
                break;
            case R.id.btn_creat_save:
                Save();
                break;
            default:
                break;
        }
    }

    private void Save() {
        //标记为已经创建好数据库
        PreferencesUtils.putBoolean(getApplicationContext(), Constants.IS_MEASURE,true);
        String getTime = tvCreatTime.getText().toString();
        String getReprtition = tvCreatReprtition.getText().toString();
//        int h = Integer.parseInt(hour1);
//        int m = Integer.parseInt(minute1);

        if (RemindMreasureActivity.UPDATE.equals(action)) {
            int h = Integer.parseInt(getTime.split(":")[0]);
            int m = Integer.parseInt(getTime.split(":")[1]);
            int week = getWeek(getReprtition);
            if(week == 0){
                flag = 1;
            }else {
                flag = 2;
            }
            // 更新数据库
            remindMeasureDao.update(id,getTime, getReprtition);
            AlarmUtil.cancelAlarm(this,ALARM_ACTION,id);//先删
            AlarmUtil.setAlarm(this, flag,h, m, id, week, "亲！该测测量血压了！", 2,ALARM_ACTION);//再添加
        }else {
            if(TextUtils.isEmpty(getTime)){
                Toast.makeText(getApplicationContext(), "请选择提醒时间", Toast.LENGTH_SHORT).show();
                return ;
            }
            if(TextUtils.isEmpty(getReprtition)){
                Toast.makeText(getApplicationContext(), "请选择重复的时间", Toast.LENGTH_SHORT).show();
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
            int id = PreferencesUtils.getInt(this, Constants.MEASURE_ID);
            AlarmUtil.setAlarm(this, flag,h, m, id, week, "亲！该测测量血压了！", 2,ALARM_ACTION);
            remindMeasureDao.add(id,getTime,getReprtition);
            id++;
            PreferencesUtils.putInt(this,Constants.MEASURE_ID,id);
        }

        // 5 回传数据，finish
        Intent data = new Intent();
        data.putExtra("getTime", getTime);
        data.putExtra("getReprtition", getReprtition);
        data.putExtra("position", mPosition);
        setResult(REP_BLACK, data);
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

    private void Pre() {
        finish();
    }

    private void GetTime() {
        Calendar c = Calendar.getInstance();
        // 创建一个TimePickerDialog实例，并把它显示出来
        new TimePickerDialog(RemindMreasureCreadActivity.this,
                // 绑定监听器
                new TimePickerDialog.OnTimeSetListener() {
                    //设置时间
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        minute1 = minute + "";
                        hour1 = hourOfDay + "";
                        if(minute<10){
                            minute1 = "0" + minute;
                        }
                        if(hourOfDay<10){
                            hour1 = "0" + hourOfDay;
                        }
                        tvCreatTime.setText( hour1 +":" + minute1);
                    }
                }
                // 设置初始时间
                , c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE),
                // true表示采用24小时制
                true).show();
    }

    class AlertClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            new AlertDialog.Builder(RemindMreasureCreadActivity.this).setTitle("请选择日期").setItems(areas,new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which){
                    Toast.makeText(RemindMreasureCreadActivity.this, "您已经选择了: " + which + ":" + areas[which],Toast.LENGTH_LONG).show();

                    dialog.dismiss();
                }
            }).show();
        }

    }
    class CheckBoxClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
//            AlertDialog ad = new AlertDialog.Builder(RemindMreasureCreadActivity.this)
//                    .setTitle("选择日期")
//                    .setMultiChoiceItems(areas,areaState,new DialogInterface.OnMultiChoiceClickListener(){
//                        public void onClick(DialogInterface dialog,int whichButton, boolean isChecked){
//                            //点击某个区域
//                        }
//                    }).setPositiveButton("确定",new DialogInterface.OnClickListener(){
//                        public void onClick(DialogInterface dialog,int whichButton){
//                            String s = "";//选择的日期
//                            for (int i = 0; i < areas.length; i++){
//                                if (areaCheckListView.getCheckedItemPositions().get(i)){
//                                    s += areaCheckListView.getAdapter().getItem(i)+ " ";
//                                }else{
//                                    areaCheckListView.getCheckedItemPositions().get(i,false);
//                                }
//                            }
//                            if (areaCheckListView.getCheckedItemPositions().size() > 0){
//                                tvCreatReprtition.setText(s);
//                            }else{
//                                //没有选择
//                            }
//                            dialog.dismiss();
//                        }
//                    }).setNegativeButton("取消", null).create();
//            areaCheckListView = ad.getListView();
//            ad.show();

            AlertDialog.Builder builder = new AlertDialog.Builder(RemindMreasureCreadActivity.this);
            builder.setTitle("请选择日期");
            builder.setSingleChoiceItems(areas, -1, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    tvCreatReprtition.setText(areas[which]);
                    dialog.dismiss();
                }
            });
            builder.show();
        }



    }
}
