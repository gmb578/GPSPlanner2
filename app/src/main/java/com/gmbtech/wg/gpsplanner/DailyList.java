package com.gmbtech.wg.gpsplanner;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.plus.model.people.Person;

import java.util.ArrayList;


public class DailyList extends Fragment {


    private LinearLayout openMap;



    //Gemberling 4/8/15, Conversion from Activity to Fragment for NavMenu
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_daily_list, container, false);
        ListView listView = (ListView) v.findViewById(R.id.lv_daily_list_main);
        ArrayList<Task> taskArrayList = TaskSaver.getInstance(getActivity()).getTasks();
        listView.setAdapter(new TaskAdapter(getActivity(), R.layout.task_item, taskArrayList));



        return v;
    }




        public class TaskAdapter extends ArrayAdapter<Task> {

            Context context;
            int layoutResourceId;
            ArrayList<Task> data = null;

            public TaskAdapter(Context context, int layoutResourceId, ArrayList<Task> data) {
                super(context, layoutResourceId, data);
                this.layoutResourceId = layoutResourceId;
                this.context = context;
                this.data = data;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View row = convertView;
                TaskHolder holder = null;

                if (row == null) {
                    LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                    row = inflater.inflate(layoutResourceId, parent, false);

                    holder = new TaskHolder();

                    row.setTag(holder);
                } else {
                    holder = (TaskHolder) row.getTag();
                }

                final Task task = data.get(position);
                EditText editText = (EditText) row.findViewById(R.id.et_task_item);
                editText.setText(task.getTitle());

                return row;
            }

            class TaskHolder {
                ImageView imgIcon;
                TextView txtTitle;
            }
        }


    }





