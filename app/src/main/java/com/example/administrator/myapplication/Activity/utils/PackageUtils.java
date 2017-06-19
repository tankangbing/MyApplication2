package com.example.administrator.myapplication.Activity.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

public class PackageUtils {

	// 获取版本名字
	public static String getVersionName(Context context){
		// 1 PackageManager
		PackageManager pm=context.getPackageManager();
		// 2 getPackageInfo
		try {
			PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
			
			// 3 versionName
			return packageInfo.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	// 获取版本号
	public static int getVersionCode(Context context){
		// 1 PackageManager
		PackageManager pm=context.getPackageManager();
		// 2 getPackageInfo
		try {
			PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
			
			// 3 versionCode
			return packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return -1;
	}
}
