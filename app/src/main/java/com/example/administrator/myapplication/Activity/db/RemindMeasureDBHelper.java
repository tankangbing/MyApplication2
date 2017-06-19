package com.example.administrator.myapplication.Activity.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class RemindMeasureDBHelper extends SQLiteOpenHelper{
	Context context1;
	public RemindMeasureDBHelper(Context context) {
		super(context, RemindMeasureListDB.DB_NAME, null, RemindMeasureListDB.VERSION);
		 context1 = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		db.execSQL(RemindMeasureListDB.RemindMeasureList.CREATE_TABLE_SQL);
//		Toast.makeText(context1, "创建数据库成功",Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}

}
