package com.example.administrator.myapplication.Activity.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.administrator.myapplication.Activity.been.RemindMeasureInfo;

import java.util.ArrayList;
import java.util.List;


public class RemindMeasureDao {

	private RemindMeasureDBHelper mRemindDbHelper;

	public RemindMeasureDao(Context context){
		mRemindDbHelper = new RemindMeasureDBHelper(context);
	}
	// 增删改查的方法
	// 增
	public boolean add(int id,String rmtime,String repeat){
		SQLiteDatabase db = mRemindDbHelper.getWritableDatabase();
		ContentValues values=new ContentValues();
		values.put(RemindMeasureListDB.RemindMeasureList._ID,id);
		values.put(RemindMeasureListDB.RemindMeasureList.RMTIME,rmtime);
		values.put(RemindMeasureListDB.RemindMeasureList.REPEAT, repeat);
		long insert=db.insert(RemindMeasureListDB.RemindMeasureList.TABLE_NAME, null, values);
		db.close();
		return insert!=-1;
	}
	
	// 删除
	public boolean delete(String id){
		SQLiteDatabase db = mRemindDbHelper.getWritableDatabase();
		int delete=db.delete(RemindMeasureListDB.RemindMeasureList.TABLE_NAME, "_id=?", new String[]{id});// delete from blacklist where phone=?
		db.close();
		return delete!=0;
	}
	
	// 改
	public void update(int id,String time,String Reprtition){
        ContentValues values = new ContentValues();
        SQLiteDatabase db = mRemindDbHelper.getWritableDatabase();
        values.put("rmtime", time);
        values.put("repeat", Reprtition);
        String whereClause = "_id=?";
        String[] whereArgs = new String[] { String.valueOf(id) };
        db.update(RemindMeasureListDB.RemindMeasureList.TABLE_NAME, values, whereClause, whereArgs);
        db.close();
	}

	// 查询所有的信息
	public List<RemindMeasureInfo> queryAllRemindMeasureInfos(){
		SQLiteDatabase db = mRemindDbHelper.getWritableDatabase();
		Cursor cursor = db.rawQuery("select _id,rmtime,repeat from RemindMeasureList", null);
		List<RemindMeasureInfo> info=new ArrayList<RemindMeasureInfo>();
		if(cursor!=null){
			while(cursor.moveToNext()){
				RemindMeasureInfo remindMeasureInfo=new RemindMeasureInfo();
				remindMeasureInfo._id=cursor.getInt(0);
				remindMeasureInfo.rmtime=cursor.getString(1);
				remindMeasureInfo.repeat=cursor.getString(2);
				info.add(remindMeasureInfo);
			}
			cursor.close();
		}
		db.close();
		return info;
	}
	// 查询所有的信息降序
	public List<RemindMeasureInfo> AllRemindMeasureInfos(){
		SQLiteDatabase db = mRemindDbHelper.getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from RemindMeasureList order by _id desc", null);
		List<RemindMeasureInfo> info=new ArrayList<RemindMeasureInfo>();
		if(cursor!=null){
			while(cursor.moveToNext()){
				RemindMeasureInfo remindMeasureInfo=new RemindMeasureInfo();
				remindMeasureInfo._id=cursor.getInt(0);
				remindMeasureInfo.rmtime=cursor.getString(1);
				remindMeasureInfo.repeat=cursor.getString(2);
				info.add(remindMeasureInfo);
			}
			cursor.close();
		}
		db.close();
		return info;
	}
	//是否存在此数据库中
	public boolean isExistsPhone(String phone){
		SQLiteDatabase db = mRemindDbHelper.getWritableDatabase();
		Cursor cursor = db.rawQuery("select _id,phone,type from blacklist where phone=?", new String[]{phone});
		int count=cursor.getCount();
		cursor.close();
		db.close();
		return count>0;
	}
	
	// 清除所有
	public void deleteAll(){
		SQLiteDatabase db = mRemindDbHelper.getWritableDatabase();
		db.execSQL("delete from RemindMeasureList");
		db.close();
	}
	
	// 查询一部分
//	public List<MeasureInfo> queryPartBlackInfos(int offset,int count){
//		SQLiteDatabase db = mRemindDbHelper.getWritableDatabase();
//		Cursor cursor = db.rawQuery("select _id,phone,type from blacklist limit ?,?", new String[]{String.valueOf(offset),String.valueOf(count)});
//		List<MeasureInfo> infos=new ArrayList<MeasureInfo>();
//		if(cursor!=null){
//			while(cursor.moveToNext()){
//				MeasureInfo blackInfo=new MeasureInfo();
//				blackInfo._id=cursor.getInt(0);
//				blackInfo.phone=cursor.getString(1);
//				blackInfo.type=cursor.getInt(2);
//				infos.add(blackInfo);
//			}
//			cursor.close();
//		}
//		db.close();
//		return infos;
//	}

	
}
