package com.example.administrator.myapplication.Activity.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.example.administrator.myapplication.Activity.constants.Constants;
import com.example.administrator.myapplication.Activity.utils.PreferencesUtils;
import com.example.administrator.myapplication.R;

public class MainActivity extends AppCompatActivity implements OnClickListener {
    Button btn1, btn2,btn3, btn4,btn5, btn6,btn7, btn8;
//    Toast tst;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       initView();
        initData();
    }

    private void initData() {

    }

    private void initView() {
        setContentView(R.layout.activity_main);
        btn1 = (Button) findViewById(R.id.btn_mreasure);
        btn2 = (Button) findViewById(R.id.btn_remind);
        btn3 = (Button) findViewById(R.id.btn_curve);
        btn4 = (Button) findViewById(R.id.btn_show);
        btn5 = (Button) findViewById(R.id.btn_set);
        btn6 = (Button) findViewById(R.id.btn_ii);
        btn7 = (Button) findViewById(R.id.btn_powerkm);
        btn8 = (Button) findViewById(R.id.btn_guide);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_mreasure:
//                Toast.makeText(getApplicationContext(), "Hello,I come from other thread!",Toast.LENGTH_SHORT ).show();
                //测量血压
                Intent intent1=new Intent(MainActivity.this,MeasureActivity.class);
                startActivity(intent1);
                break;
            case R.id.btn_remind:
                //用药提醒
                Intent intent2=new Intent(MainActivity.this,RemindActivity.class);
                startActivity(intent2);
                break;
            case R.id.btn_curve:
                //曲线图
                boolean isQu = PreferencesUtils.getBoolean(getApplicationContext(), Constants.IS_QU);
                if(isQu){
                    Intent intent3=new Intent(MainActivity.this,CurveActivity.class);
                    startActivity(intent3);
                }else {
                    Toast.makeText(getApplicationContext(), "亲！还没数据，赶紧先去测量吧",Toast.LENGTH_SHORT ).show();
                    return;
                }


                break;
            case R.id.btn_show:
                //展示数据
                boolean isCe = PreferencesUtils.getBoolean(getApplicationContext(), Constants.IS_CE);
                if(isCe){
                    Intent intent4=new Intent(MainActivity.this,ShowActivity.class);
                    startActivity(intent4);
                }else {
                    Toast.makeText(getApplicationContext(), "亲！还没数据，赶紧先去测量吧",Toast.LENGTH_SHORT ).show();
                    return;
                }
                break;
            case R.id.btn_set:
                Intent intent5=new Intent(MainActivity.this,SetActivity.class);
                startActivity(intent5);
                break;
            case R.id.btn_ii:
                //个人信息
                Intent intent6=new Intent(MainActivity.this,IiActivity.class);
                startActivity(intent6);
                break;
            case R.id.btn_powerkm:
                Intent intent7=new Intent(MainActivity.this,PowerActivity.class);
                startActivity(intent7);
                break;
            case R.id.btn_guide:
                Intent intent8=new Intent(MainActivity.this,GuideActivity.class);
                startActivity(intent8);
                break;
            default:
                break;
        }
    }


}
