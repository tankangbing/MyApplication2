package com.example.administrator.myapplication.Activity.View;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.myapplication.R;

/**
 * Created by Administrator on 2017/6/20 0020.
 */

public class ResultPopupWindow extends PopupWindow {


    private View mMenuView;
    private  TextView mTvPpDay;
    private  TextView mTvPpTime;
    private  TextView mTvPpHigt;
    private  TextView mTvPpLight;
    private  TextView mTvPpXin;
    private  TextView mTvPpOne;
    private  TextView mTvPpTwo;
    private  TextView mTvPpThree;
    private  TextView mTvPpFoure;
    private  TextView mTvPpFri;
    private  Button mBtn_cancel;
    private  Button mBtn_save;

    public ResultPopupWindow(Activity context, View.OnClickListener itemsOnClick) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mMenuView = inflater.inflate(R.layout.popup_measure, null);
        mTvPpDay = (TextView) mMenuView.findViewById(R.id.pp_day);
        mTvPpTime = (TextView) mMenuView.findViewById(R.id.pp_time);
        mTvPpHigt = (TextView) mMenuView.findViewById(R.id.pp_higt);
        mTvPpLight = (TextView) mMenuView.findViewById(R.id.pp_light);
        mTvPpXin = (TextView) mMenuView.findViewById(R.id.pp_xin);
        mTvPpOne = (TextView) mMenuView.findViewById(R.id.pp_one);
        mTvPpTwo = (TextView) mMenuView.findViewById(R.id.pp_two);
        mTvPpThree = (TextView) mMenuView.findViewById(R.id.pp_three);
        mTvPpFoure = (TextView) mMenuView.findViewById(R.id.pp_foure);
        mTvPpFri = (TextView) mMenuView.findViewById(R.id.pp_fri);
//        mBtn_cancel = (Button) mMenuView.findViewById(R.id.pp_cancel);
//        mBtn_save = (Button) mMenuView.findViewById(R.id.pp_save);
        //取消按钮
//        mBtn_cancel.setOnClickListener(new View.OnClickListener() {
//
//            public void onClick(View v) {
//                //销毁弹出框
//                dismiss();
//            }
//        });
        // 设置外部可点击
        this.setOutsideTouchable(true);
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        this.mMenuView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = mMenuView.findViewById(R.id.pop_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });


    /* 设置弹出窗口特征 */
        // 设置视图
        this.setContentView(this.mMenuView);
        // 设置弹出窗体的宽和高
        this.setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
        this.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);

        // 设置弹出窗体可点击
        this.setFocusable(true);

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置弹出窗体的背景
        this.setBackgroundDrawable(dw);

        // 设置弹出窗体显示时的动画，从底部向上弹出
        this.setAnimationStyle(R.style.take_photo_anim);

    }

}
