package com.example.administrator.myapplication.Activity.ui;

import android.app.Activity;
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
    private ImageButton ibGuiderPer;
    private ListView mListView;
    protected static final int SUCCESS_GET_DATA = 0;
    private DataService service;
    private List<String> data;// 加载的总数据
    private ArrayAdapter<String> adapter;

    private boolean finish = true;// 是否加载完成

    private View footer;

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case SUCCESS_GET_DATA:
                    @SuppressWarnings("unchecked")
                    ArrayList<String> result = ((ArrayList<String>) msg.obj);
                    data.addAll(result);
                    // 让listview自动刷新
                    adapter.notifyDataSetChanged();
                    finish = true;
                    // 将页脚删除掉
                    if (mListView.getFooterViewsCount() > 0) {
                        mListView.removeFooterView(footer);
                    }
                    break;

                default:
                    break;
            }
        };
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initView();

        service = new DataService();
        data = new ArrayList<String>();
        List<String> result = service.getData(0, 30);
        data.addAll(result);
        adapter = new ArrayAdapter<String>(this, R.layout.item, R.id.tv_info,
                data);
        footer = View.inflate(this, R.layout.footer, null);
        // 在增加listview的页脚之前，需要提前设置一次
        mListView.addFooterView(footer);
        mListView.setAdapter(adapter);
        // 然后再次将页脚删除掉
        mListView.removeFooterView(footer);
        // 滚动监听事件
        mListView.setOnScrollListener(new MyOnScrollListener());
    }

    private void initView() {
        ibGuiderPer = (ImageButton)findViewById(R.id.im_guide_pre);
        mListView = (ListView)findViewById(R.id.listview);
        ibGuiderPer.setOnClickListener(this);
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

    private final class MyOnScrollListener implements AbsListView.OnScrollListener {
        // 加载的总页数
        private int countPage = 50;
        // 每页加载20条数据
        private int pageSize = 30;

        // 滚动状态发生改变时
        public void onScrollStateChanged(AbsListView view, int scrollState) {
        }

        // 当list开始滚动时
        public void onScroll(AbsListView view, int firstVisibleItem,
                             int visibleItemCount, int totalItemCount) {
            // 最下面的条目数
            final int totalCount = firstVisibleItem + visibleItemCount;
            int currentPage = totalCount / pageSize;// 当前页
            int nextPage = currentPage + 1;// 下一页
            // 当翻到最后一条数据时
            if (totalCount == totalItemCount && nextPage <= countPage && finish) {
                // 已经移动到了listview的最后
                finish = false;
                // 添加页脚
                mListView.addFooterView(footer);
                new Thread() {
                    public void run() {
                        SystemClock.sleep(3000);
                        List<String> result = service.getData(totalCount + 1,
                                pageSize);
                        Message msg = new Message();
                        msg.what = SUCCESS_GET_DATA;
                        msg.obj = result;
                        mHandler.sendMessage(msg);
                    };
                }.start();
            }
        }
    }
    public class DataService {

        //加载数据
        public List<String> getData(int startPosition , int offset){
            List<String> data = new ArrayList<String>();
            for(int i = 0;i< 30;i++){
                data.add("分页加载的数据   " + i);
            }
            return data;
        }
    }
}
