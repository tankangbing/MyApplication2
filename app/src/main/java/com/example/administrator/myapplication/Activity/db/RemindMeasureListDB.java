package com.example.administrator.myapplication.Activity.db;

public interface RemindMeasureListDB {
	String DB_NAME = "remingmeasure.db";
	int VERSION = 1;

	public interface RemindMeasureList {
		String _ID="_id";
		String RMTIME="rmtime";
		String REPEAT="repeat";
		String TABLE_NAME="remindmeasurelist";
		String CREATE_TABLE_SQL = "create table remindmeasurelist(_id integer,rmtime text,repeat text)";
	}
}
