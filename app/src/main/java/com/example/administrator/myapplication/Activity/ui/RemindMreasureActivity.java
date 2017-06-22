package com.example.administrator.myapplication.Activity.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myapplication.Activity.been.RemindMeasureInfo;
import com.example.administrator.myapplication.Activity.db.RemindMeasureDao;
import com.example.administrator.myapplication.Activity.utils.AlarmUtil;
import com.example.administrator.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/18 0018.
 */

public class RemindMreasureActivity extends Activity implements View.OnClickListener {
    private ImageButton ibPre;
    private TextView ibCreat;

    private RemindMeasureDao remindMeasureDao;
    public RemindMeasurAdapter remindMeasurAdapter;
    private ListView lvRemindMeasure;
    private List<RemindMeasureInfo> infos = new ArrayList<RemindMeasureInfo>();
    public static final int REQ_BLACK_ADD = 201;
    protected static final int REQ_BLACK_UPDATE = 202;
    public static final String UPDATE = "update";
    private RemindMeasureInfo remindMeasureInfo;
    private TextView mTvPre;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        inintEvent();

    }

    private void initView() {
        setContentView(R.layout.activity_measure_remind);
        ibPre = (ImageButton)findViewById(R.id.im_mr_pre) ;
        ibCreat = (TextView)findViewById(R.id.im_mr_crea);
        lvRemindMeasure = (ListView)findViewById(R.id.lv_remind_measure );
        mTvPre = (TextView)findViewById(R.id.tv_mreasure_remind_pre);

        mTvPre.setOnClickListener(this);
        ibPre.setOnClickListener(this);
        ibCreat.setOnClickListener(this);
    }

    private void initData() {
        remindMeasureDao = new RemindMeasureDao(this);
        new Thread(new Runnable() {

            @Override
            public void run() {
                infos = remindMeasureDao.AllRemindMeasureInfos();
                SystemClock.sleep(100);
                // 更新UI
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        remindMeasurAdapter = new RemindMeasurAdapter();
                        lvRemindMeasure.setAdapter(remindMeasurAdapter);
                    }
                });
            }
        }).start();
    }

    private void inintEvent() {
        lvRemindMeasure.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 添加编辑页面
                Intent intent=new Intent(RemindMreasureActivity.this,RemindMreasureCreadActivity.class);
                RemindMeasureInfo blackInfo = infos.get(position);
                intent.putExtra("id", blackInfo._id);
                intent.putExtra("rmtime", blackInfo.rmtime);
                intent.putExtra("repeat", blackInfo.repeat);
                intent.putExtra("position", position);
                intent.setAction(UPDATE);// 用于区分是添加还是更新
                startActivityForResult(intent, REQ_BLACK_UPDATE);
            }
        });
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
            case R.id.tv_mreasure_remind_pre:
                Brack();
                break;
            case R.id.im_mr_pre:
               Brack();
                break;
            case R.id.im_mr_crea:
                Creat();
                break;
            default:
                break;
        }
    }

    private void Creat() {
        Intent intent=new Intent(RemindMreasureActivity.this,RemindMreasureCreadActivity.class);
        startActivityForResult(intent,REQ_BLACK_ADD);
    }

    private void Brack() {
       finish();
    }
    class RemindMeasurAdapter extends BaseAdapter {



        @Override
        public int getCount() {
            return infos.size();
        }

        @Override
        public Object getItem(int position) {
            return infos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            RemindMreasureActivity.ViewHolder holder = null;
            if (convertView == null) {
                holder = new RemindMreasureActivity.ViewHolder();
                convertView = View.inflate(getApplicationContext(), R.layout.view_lv_remind_measure, null);
                convertView.setTag(holder);
                holder.tvTime = (TextView) convertView.findViewById(R.id.tv_view_remind_measure_time);
                holder.tvWeek = (TextView) convertView.findViewById(R.id.tv_view_remind_measure_week);
                holder.ibDelet = (ImageButton)convertView.findViewById(R.id.ib_delet);
            } else {
                holder = (RemindMreasureActivity.ViewHolder) convertView.getTag();
            }
//            int h = Integer.parseInt(remindMeasureInfo.rmtime.split(":")[0]);
//            int m = Integer.parseInt(remindMeasureInfo.rmtime.split(":")[1]);
            remindMeasureInfo = infos.get(position);
            holder.tvTime.setText(remindMeasureInfo.rmtime);
            holder.tvWeek.setText(remindMeasureInfo.repeat);
            holder.ibDelet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    remindMeasureInfo = infos.get(position);
                    AlarmUtil.cancelAlarm(getApplicationContext(),"com.loonggg.alarm.clock",remindMeasureInfo._id);
                    // 从数据库中删除
                    remindMeasureDao.delete(remindMeasureInfo._id+"");
                    // 从集合中删除
                    infos.remove(remindMeasureDao);
//                    Toast.makeText(getApplication(),"已删除",Toast.LENGTH_SHORT).show();
                    infos.clear();
                    infos.addAll(remindMeasureDao.AllRemindMeasureInfos());
//					更新UI
                    if(remindMeasurAdapter!=null){
                        remindMeasurAdapter.notifyDataSetChanged();
                    }
                }
            });
            return convertView;
        }
    }
    class ViewHolder {
        public TextView tvTime;
        public TextView tvWeek;
        public ImageButton ibDelet;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQ_BLACK_ADD){
            if(resultCode==RemindMreasureCreadActivity.REP_BLACK){
                RemindMeasureInfo remindMeasureInfo=new RemindMeasureInfo();
                remindMeasureInfo.rmtime = data.getStringExtra("getTime");
                remindMeasureInfo.repeat=data.getStringExtra("getReprtition");
                // 添加到集合
                infos.add(0,remindMeasureInfo);
                // 更新UI：刷新列表（数据发生改变，通知ListView的UI进行改变）
                remindMeasurAdapter.notifyDataSetChanged();
            }
        }else if(requestCode==REQ_BLACK_UPDATE){
            if(resultCode==RemindMreasureCreadActivity.REP_BLACK){
                // 初始更新会，刷新列表
                String time=data.getStringExtra("getTime");
                String reprtition=data.getStringExtra("getReprtition");
                int position=data.getIntExtra("position", -1);
                RemindMeasureInfo blackInfo = infos.get(position);
                blackInfo.rmtime = time;
                blackInfo.repeat = reprtition;
                // 更新UI：刷新列表（数据发生改变，通知ListView的UI进行改变）
                remindMeasurAdapter.notifyDataSetChanged();
            }
        }
    }

}
