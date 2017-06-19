package com.example.administrator.myapplication.Activity.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.administrator.myapplication.Activity.been.RemindEatInfo;
import com.example.administrator.myapplication.Activity.been.RemindMeasureInfo;

import java.util.ArrayList;
import java.util.List;


public class RemindEatDao {

	private RemindEatDBHelper remindEatDBHelper;

	public RemindEatDao(Context context){
		remindEatDBHelper = new RemindEatDBHelper(context);
	}
	// 增删改查的方法
	// 增
	public boolean add(int id,String time,String repeat,String yao ,String ci){
		SQLiteDatabase db = remindEatDBHelper.getWritableDatabase();
		ContentValues values=new ContentValues();
		values.put(RemindEatListDB.RemindEatList._ID, id);
		values.put(RemindEatListDB.RemindEatList.TIME, time);
		values.put(RemindEatListDB.RemindEatList.REPEAT, repeat);
		values.put(RemindEatListDB.RemindEatList.YAO, yao);
		values.put(RemindEatListDB.RemindEatList.CI, ci);
		long insert=db.insert(RemindEatListDB.RemindEatList.TABLE_NAME, null, values);
		db.close();
		return insert!=-1;
	}
	
	// 删除
	public boolean delete(String id){
		SQLiteDatabase db = remindEatDBHelper.getWritableDatabase();
		int delete=db.delete(RemindEatListDB.RemindEatList.TABLE_NAME, "_id=?", new String[]{id});// delete from blacklist where phone=?
		db.close();
		return delete!=0;
	}
	
	// 改
	public void update(int id,String time,String Reprtition,String yao,String ci){
        ContentValues values = new ContentValues();
        SQLiteDatabase db = remindEatDBHelper.getWritableDatabase();
        values.put("time", time);
        values.put("repeat", Reprtition);
		values.put("yao", yao);
		values.put("ci", ci);
        String whereClause = "_id=?";
        String[] whereArgs = new String[] { String.valueOf(id) };
        db.update(RemindEatListDB.RemindEatList.TABLE_NAME, values, whereClause, whereArgs);
        db.close();
	}

	// 查询所有的信息
	public List<RemindEatInfo> queryAllRemindEatInfos(){
		SQLiteDatabase db = remindEatDBHelper.getWritableDatabase();
		Cursor cursor = db.rawQuery("select _id,time,repeat,yao,ci from RemindEatList", null);
		List<RemindEatInfo> infos=new ArrayList<RemindEatInfo>();
		if(cursor!=null){
			while(cursor.moveToNext()){
				RemindEatInfo remindEatInfo=new RemindEatInfo();
				remindEatInfo._id=cursor.getInt(0);
				remindEatInfo.time=cursor.getString(1);
				remindEatInfo.repeat=cursor.getString(2);
				remindEatInfo.yao = cursor.getString(3);
				remindEatInfo.ci=cursor.getString(4);
				infos.add(remindEatInfo);
			}
			cursor.close();
		}
		db.close();
		return infos;
	}
	// 查询所有的信息降序
	public List<RemindEatInfo> AllRemindEatInfos(){
		SQLiteDatabase db = remindEatDBHelper.getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from RemindEatList order by _id desc", null);
		List<RemindEatInfo> infos=new ArrayList<RemindEatInfo>();
		if(cursor!=null){
			while(cursor.moveToNext()){
				RemindEatInfo remindEatInfo=new RemindEatInfo();
				remindEatInfo._id=cursor.getInt(0);
				remindEatInfo.time=cursor.getString(1);
				remindEatInfo.repeat=cursor.getString(2);
				remindEatInfo.yao = cursor.getString(3);
				remindEatInfo.ci=cursor.getString(4);
				infos.add(remindEatInfo);
			}
			cursor.close();
		}
		db.close();
		return infos;
	}
	//是否存在此数据库中
	public boolean isExistsPhone(String phone){
		SQLiteDatabase db = remindEatDBHelper.getWritableDatabase();
		Cursor cursor = db.rawQuery("select _id,phone,type from blacklist where phone=?", new String[]{phone});
		int count=cursor.getCount();
		cursor.close();
		db.close();
		return count>0;
	}
	
	// 清除所有
	public void deleteAll(){
		SQLiteDatabase db = remindEatDBHelper.getWritableDatabase();
		db.execSQL("delete from RemindEatList");
		db.close();
	}
	

	
}
