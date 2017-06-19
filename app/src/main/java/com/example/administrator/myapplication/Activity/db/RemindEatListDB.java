package com.example.administrator.myapplication.Activity.db;

public interface RemindEatListDB {
	String DB_NAME = "remingeat.db";
	int VERSION = 1;

	public interface RemindEatList {
		String _ID="_id";
		String TIME="time";
		String REPEAT="repeat";
		String YAO = "yao";
		String CI = "ci";
		String TABLE_NAME="remindeatlist";
		String CREATE_TABLE_SQL = "create table remindeatlist(_id integer,time text,repeat text,yao text,ci text)";
	}
}
