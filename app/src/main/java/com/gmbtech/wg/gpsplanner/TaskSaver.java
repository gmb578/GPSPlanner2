package com.gmbtech.wg.gpsplanner;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;


public class TaskSaver {
	private static TaskSaver instance = null;
	private Context mContext;
	private ArrayList<Task> mTasks;

	private TaskSaver(Context context){
		//defeat external instantiation
		mContext = context;
		mTasks = new ArrayList<>();
	}

	public static TaskSaver getInstance(Context context){
		if(instance == null){
			instance = new TaskSaver(context);
		}
		return instance;
	}

	public void addTask(Task task){
		Log.i("TaskSaver","Added task");
		mTasks.add(task);
	}

	public ArrayList<Task> getTasks(){
		return mTasks;
	}
}
