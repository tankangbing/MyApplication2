package com.example.administrator.myapplication.Activity.utils;

import android.util.Log;

public class L {

	private static final String TAG = "heima12";
	private static boolean DEBUG = true;

	public static void d(String msg) {
		if (DEBUG) {
			Log.d(TAG, msg);
		}
	}
}
