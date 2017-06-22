package com.example.administrator.myapplication.Activity.ui;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myapplication.Activity.constants.Constants;
import com.example.administrator.myapplication.Activity.utils.PreferencesUtils;
import com.example.administrator.myapplication.R;

import java.util.Calendar;

/**
 * Created by Administrator on 2017/5/18 0018.
 */

public class IiActivity extends Activity{
    private EditText etName,etSex,etHight,etWight,etPath,etAdvice;
    private Calendar calendar; //通过Calendar获取系统时间
    private int mYear;
    private int mMonth;
    private int mDay;
    final int DATE_DIALOG = 1;
    private String getName ,getHight,getWight,getPath,getAdvice;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initData();
        initEvent();

    }

    private void initView() {
        setContentView(R.layout.activity_ii);
        etName=(EditText)findViewById(R.id.edt_ge_name);
        etHight = (EditText)findViewById(R.id.edt_ge_height);
        etWight = (EditText)findViewById(R.id.edt_ge_weight);
        etPath = (EditText)findViewById(R.id.edt_ge_path);
        etAdvice = (EditText)findViewById(R.id.edt_ge_advice);

    }

    private void initData() {
        SetPath();//设置日期管理器
        getName = PreferencesUtils.getString(getApplicationContext(),Constants.IS_NAME,getName);
        getHight = PreferencesUtils.getString(getApplicationContext(),Constants.IS_HIGHT,getHight);
        getWight = PreferencesUtils.getString(getApplicationContext(),Constants.IS_WIGHT,getWight);
        getPath = PreferencesUtils.getString(getApplicationContext(),Constants.IS_PATH,getPath);
        getAdvice = PreferencesUtils.getString(getApplicationContext(),Constants.IS_ADVICE,getAdvice);
        etName.setText(getName);
        etHight.setText(getHight);
        etWight.setText(getWight);
        etPath.setText(getPath);
        etAdvice.setText(getAdvice);
    }

    private void initEvent() {
        //toobsr的兼容
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.colorPrimaryDark);
        }
    }

    //------------------------------------------
    private void SetPath() {
        etPath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG);
            }
        });
        final Calendar ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);
    }

        @Override
        protected Dialog onCreateDialog(int id) {
            switch (id) {
                case DATE_DIALOG:
                    return new DatePickerDialog(this, mdateListener, mYear, mMonth, mDay);
            }
            return null;
        }
    public void display() {
        etPath.setText(new StringBuffer().append(mYear).append("-").append(mMonth + 1).append("-").append(mDay));
    }

    private DatePickerDialog.OnDateSetListener mdateListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            display();
        }
    };
//----------------------------s时间管理器的设定-----------------------
    public void gebacke(View view){
       finish();
    }

    public void save(View view){
        getName = etName.getText().toString();
        getHight = etHight.getText().toString();
        getWight = etWight.getText().toString();
        getPath = etPath.getText().toString();
        getAdvice = etAdvice.getText().toString();
        PreferencesUtils.putString(getApplicationContext(),Constants.IS_NAME,getName);
        PreferencesUtils.putString(getApplicationContext(),Constants.IS_HIGHT,getHight);
        PreferencesUtils.putString(getApplicationContext(),Constants.IS_WIGHT,getWight);
        PreferencesUtils.putString(getApplicationContext(),Constants.IS_PATH,getPath);
        PreferencesUtils.putString(getApplicationContext(),Constants.IS_ADVICE,getAdvice);
        finish();

    }

}
