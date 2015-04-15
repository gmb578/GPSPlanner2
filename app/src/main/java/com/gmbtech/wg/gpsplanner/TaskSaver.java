package com.gmbtech.wg.gpsplanner;

import android.content.Context;


public class TaskSaver {
	private TaskSaver instance = null;
	private Context mContext;

	private TaskSaver(Context context){
		//defeat external instantiation
		mContext = context;
	}

	public TaskSaver getInstance(Context context){
		if(instance == null){
			instance = new TaskSaver(context);
		}
		return instance;
	}
}
