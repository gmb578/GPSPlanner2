package com.gmbtech.wg.gpsplanner;

import android.content.Context;

import java.util.ArrayList;


public class TaskSaver {
	private TaskSaver instance = null;
	private Context mContext;
	private ArrayList<Task> mTasks;

	private TaskSaver(Context context){
		//defeat external instantiation
		mContext = context;
		mTasks = new ArrayList<>();
	}

	public TaskSaver getInstance(Context context){
		if(instance == null){
			instance = new TaskSaver(context);
		}
		return instance;
	}

	public void addTask(Task task){
		mTasks.add(task);
	}

	public ArrayList<Task> getTasks(){
		return mTasks;
	}
}
