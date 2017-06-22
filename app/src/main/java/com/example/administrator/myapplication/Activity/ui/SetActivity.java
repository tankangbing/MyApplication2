package com.example.administrator.myapplication.Activity.ui;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.administrator.myapplication.Activity.constants.Constants;
import com.example.administrator.myapplication.Activity.db.MeasureDao;
import com.example.administrator.myapplication.Activity.db.RemindEatDao;
import com.example.administrator.myapplication.Activity.db.RemindMeasureDao;
import com.example.administrator.myapplication.Activity.utils.PreferencesUtils;
import com.example.administrator.myapplication.R;

/**
 * Created by Administrator on 2017/5/18 0018.
 */

public class SetActivity extends Activity implements View.OnClickListener {
    private ImageButton ibSetPer,ibSound;
    private TextView mTvClear;
    private MeasureDao mMeasureDao;
    private RemindMeasureDao mRemindMeasureDao;
    private RemindEatDao mRemindEatDao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
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
        mMeasureDao = new MeasureDao(this);
        mRemindMeasureDao = new RemindMeasureDao(this);
        mRemindEatDao = new RemindEatDao(this);


        //默认为开的
        boolean isSound = PreferencesUtils.getBoolean(getApplicationContext(), Constants.SOUND);
        if(isSound){
                //初始化 代表关
            ibSound.setImageDrawable(getResources().getDrawable(R.drawable.off));
        }else {//代表开
            ibSound.setImageDrawable(getResources().getDrawable(R.drawable.on));
        }
    }

    private void initView() {
        ibSetPer = (ImageButton)findViewById(R.id.im_set_pre);
        ibSound = (ImageButton)findViewById(R.id.im_set_sound);
        mTvClear = (TextView)findViewById(R.id.tv_set_clear);

        ibSound.setOnClickListener(this);
        ibSetPer.setOnClickListener(this);
        mTvClear.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.im_set_pre:
                Pre();
                break;
            case R.id.im_set_sound:
                setSound();
                break;
            case R.id.tv_set_clear:
                clear();
                break;
            default:
                break;
        }
    }

    private void setSound() {
        boolean isSound = PreferencesUtils.getBoolean(getApplicationContext(), Constants.SOUND);
        if(isSound){
            //初始化 代表关
            ibSound.setImageDrawable(getResources().getDrawable(R.drawable.on));
            PreferencesUtils.putBoolean(getApplicationContext(), Constants.SOUND,false);
        }else {//代表开
            ibSound.setImageDrawable(getResources().getDrawable(R.drawable.off));
            PreferencesUtils.putBoolean(getApplicationContext(), Constants.SOUND,true);
        }
    }


    private void Pre() {
        finish();
    }

    private void clear() {
        new AlertDialog.Builder(SetActivity.this).setTitle("注意")//设置对话框标题
                 .setMessage("清除数据会清除您本地的数据及个人信息，您确定要清除数据吗！")//设置显示的内容
                .setPositiveButton("确定",new DialogInterface.OnClickListener() {//添加确定按钮
                    @Override
                    public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                        // TODO Auto-generated method stub
                        clearAll();
                    }
                })
                .setNegativeButton("取消",new DialogInterface.OnClickListener() {//添加返回按钮
                    @Override
                    public void onClick(DialogInterface dialog, int which) {//响应事件
                        // TODO Auto-generated method stub
                    }
                }).show();//在按键响应事件中显示此对话框

    }

    //清除所有数据
    private void clearAll() {
        mMeasureDao.deleteAll();
        mRemindMeasureDao.deleteAll();
        mRemindEatDao.deleteAll();
        PreferencesUtils.putString(getApplicationContext(),Constants.IS_NAME,null);
        PreferencesUtils.putString(getApplicationContext(),Constants.IS_HIGHT,null);
        PreferencesUtils.putString(getApplicationContext(),Constants.IS_WIGHT,null);
        PreferencesUtils.putString(getApplicationContext(),Constants.IS_PATH,null);
        PreferencesUtils.putString(getApplicationContext(),Constants.IS_ADVICE,null);
//        PreferencesUtils.putInt(getApplicationContext(),Constants.GET_ID,0);
    }
}
