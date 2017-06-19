package com.example.administrator.myapplication.Activity.ui;

import android.app.Activity;
import android.content.Intent;
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

import com.example.administrator.myapplication.Activity.been.RemindEatInfo;
import com.example.administrator.myapplication.Activity.db.RemindEatDao;
import com.example.administrator.myapplication.Activity.utils.AlarmUtil;
import com.example.administrator.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/18 0018.
 */

public class RemindEatActivity extends Activity implements View.OnClickListener{
    private ImageButton ibEatPre;
    private ImageButton ibEatCreat;

    private RemindEatDao remindEatDao;
    public RemindEatAdapter remindEatAdapter;
    private ListView lvRemindEat;
    private List<RemindEatInfo> infos = new ArrayList<RemindEatInfo>();
    public static final int EAT_BLACK_ADD = 201;
    protected static final int EAT_BLACK_UPDATE = 202;
    public static final String UPDATE = "update";
    private RemindEatInfo remindEatInfo;
    public static final String ALARM_ACTION = "com.eatt.alarm.clock";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        inintEvent();

    }

    private void initView() {
        setContentView(R.layout.activity_eat_remind);
        ibEatPre = (ImageButton)findViewById(R.id.im_eat_pre) ;
        ibEatCreat = (ImageButton)findViewById(R.id.im_eat_crea);
        lvRemindEat = (ListView)findViewById(R.id.lv_remind_eat );

        ibEatPre.setOnClickListener(this);
        ibEatCreat.setOnClickListener(this);
    }

    private void initData() {
        remindEatDao = new RemindEatDao(this);
        new Thread(new Runnable() {

            @Override
            public void run() {
                infos = remindEatDao.AllRemindEatInfos();
                SystemClock.sleep(100);
                // 更新UI
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        remindEatAdapter = new RemindEatAdapter();
                        lvRemindEat.setAdapter(remindEatAdapter);
                    }
                });
            }
        }).start();
    }

    private void inintEvent() {
        lvRemindEat.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 添加编辑页面
                Intent intent=new Intent(RemindEatActivity.this,RemindEatCreatActivity.class);
                RemindEatInfo blackInfo = infos.get(position);
                intent.putExtra("id", blackInfo._id);
                intent.putExtra("time", blackInfo.time);
                intent.putExtra("repeat", blackInfo.repeat);
                intent.putExtra("yao", blackInfo.yao);
                intent.putExtra("ci", blackInfo.ci);
                intent.putExtra("position", position);
                intent.setAction(UPDATE);// 用于区分是添加还是更新
                startActivityForResult(intent, EAT_BLACK_UPDATE);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.im_eat_pre:
                Brack();
                break;
            case R.id.im_eat_crea:
                EatCreat();
                break;
            default:
                break;
        }
    }

    private void EatCreat() {
        Intent intent=new Intent(RemindEatActivity.this,RemindEatCreatActivity.class);
        startActivityForResult(intent,EAT_BLACK_ADD);
    }

    private void Brack() {
       finish();
    }
    class RemindEatAdapter extends BaseAdapter {



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
            RemindEatActivity.ViewHolder holder = null;
            if (convertView == null) {
                holder = new RemindEatActivity.ViewHolder();
                convertView = View.inflate(getApplicationContext(), R.layout.view_lv_remind_eat, null);
                convertView.setTag(holder);
                holder.tvTime = (TextView) convertView.findViewById(R.id.tv_view_remind_eat_time);
                holder.tvWeek = (TextView) convertView.findViewById(R.id.tv_view_remind_eat_week);
                holder.tvYao = (TextView) convertView.findViewById(R.id.tv_view_remind_eat_yao);
                holder.ci = (TextView)convertView.findViewById(R.id.tv_view_remind_eat_ci) ;
                holder.ibDelet = (ImageButton)convertView.findViewById(R.id.ib_eat_delet);
            } else {
                holder = (RemindEatActivity.ViewHolder) convertView.getTag();
            }

            remindEatInfo = infos.get(position);
            holder.tvTime.setText(remindEatInfo.time);
            holder.tvWeek.setText(remindEatInfo.repeat);
            holder.tvYao.setText(remindEatInfo.yao);
            holder.ci.setText(remindEatInfo.ci+"个/次");
            holder.ibDelet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    remindEatInfo = infos.get(position);
                    //如果先删了数据库，那么删除闹钟的取消没什么效果，一定先取消闹钟再删数据库
                    AlarmUtil.cancelAlarm(getApplicationContext(),ALARM_ACTION,remindEatInfo._id);//remindEatInfo._id
                    // 从数据库中删除
                    remindEatDao.delete(remindEatInfo._id+"");
                    // 从集合中删除
                    infos.remove(remindEatDao);
//                    Toast.makeText(getApplication(),"已删除",Toast.LENGTH_SHORT).show();
//					更新UI
                    infos.clear();
                    infos.addAll(remindEatDao.AllRemindEatInfos());
                    if(remindEatAdapter!=null){
                        remindEatAdapter.notifyDataSetChanged();
                    }
                }
            });
            return convertView;
        }
    }
    class ViewHolder {
        public TextView tvTime;
        public TextView tvWeek;
        public TextView tvYao;
        public TextView ci;
        public ImageButton ibDelet;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == EAT_BLACK_ADD){
            if(resultCode==RemindEatCreatActivity.EAT_BLACK){
                RemindEatInfo remindEatInfo=new RemindEatInfo();
                remindEatInfo.time = data.getStringExtra("getTime");
                remindEatInfo.repeat=data.getStringExtra("getReprtition");
                remindEatInfo.yao = data.getStringExtra("getYao");
                remindEatInfo.ci=data.getStringExtra("getNumber");

                // 添加到集合
                infos.add(0,remindEatInfo);
                // 更新UI：刷新列表（数据发生改变，通知ListView的UI进行改变）
                remindEatAdapter.notifyDataSetChanged();
            }
        }else if(requestCode==EAT_BLACK_UPDATE){
            if(resultCode==RemindEatCreatActivity.EAT_BLACK){
                // 初始更新会，刷新列表
                String time=data.getStringExtra("getTime");
                String reprtition=data.getStringExtra("getReprtition");
                String yao = data.getStringExtra("getYao");
                String ci=data.getStringExtra("getNumber");
                int position=data.getIntExtra("position", -1);
                RemindEatInfo blackInfo = infos.get(position);
                blackInfo.time = time;
                blackInfo.repeat = reprtition;
                blackInfo.yao = yao;
                blackInfo.ci = ci;
                // 更新UI：刷新列表（数据发生改变，通知ListView的UI进行改变）
                remindEatAdapter.notifyDataSetChanged();
            }
        }
    }
}
