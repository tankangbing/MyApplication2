package com.example.administrator.myapplication.Activity.been;

import android.app.Application;

/**
 * Created by Administrator on 2017/6/1 0001.
 */

public class MyApplication extends Application{
    private int i = 0;
    private int j = 0;
    public int getI(){
        return this.i;
    }
    public void setI(int c){
        this.i = c;
    }
    public int getJ(){
        return this.j;
    }
    public void setJ(int c){
        this.j = c;
    }
}
