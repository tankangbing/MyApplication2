package com.example.administrator.myapplication.Activity.ui;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;

import com.example.administrator.myapplication.R;

/**
 * Created by Administrator on 2017/5/18 0018.
 */

public class PowerActivity extends Activity implements View.OnClickListener{
    private ImageButton ibPower;
    private WebView mWebView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_power);
        initView();
//        initData();
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
    private void initView() {
        ibPower = (ImageButton)findViewById(R.id.im_power_pre);
//        mWebView = (WebView) findViewById(R.id.powe_webView);
        ibPower.setOnClickListener(this);
    }

    private void initData() {
        //WebView加载web资源
        mWebView.loadUrl("http://baidu.com");
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
    }

    //点击返回上一页面而不是退出浏览器
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
//            mWebView.goBack();
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.im_power_pre:
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
