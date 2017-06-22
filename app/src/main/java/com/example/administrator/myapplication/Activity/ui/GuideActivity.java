package com.example.administrator.myapplication.Activity.ui;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.administrator.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/18 0018.
 */

public class GuideActivity extends Activity implements View.OnClickListener{

    private ImageButton mPre;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initView();
        initEvent();
    }

    private void initView() {
        mPre = (ImageButton)findViewById(R.id.im_guide_pre);
        mPre.setOnClickListener(this);
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
            case R.id.im_guide_pre:
                Pre();
                break;
            default:
                break;
        }
    }

    private void Pre() {
        finish();
    }

}
