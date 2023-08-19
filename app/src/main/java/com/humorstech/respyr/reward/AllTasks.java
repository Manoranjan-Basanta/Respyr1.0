package com.humorstech.respyr.reward;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.humorstech.respyr.R;

import java.util.ArrayList;
import java.util.List;

public class AllTasks extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_tasks);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.white));


        RecyclerView rcLockedTask=findViewById(R.id.rc_locked_task_list);
        RecyclerView rcLockedTask2=findViewById(R.id.rc_locked_task_list2);



        List<LockedTaskDataModel> data = lockedTaskList1();
        LockedTaskAdapter adapter = new LockedTaskAdapter(data, getApplication());

        rcLockedTask.setAdapter(adapter);
        rcLockedTask.setLayoutManager(new LinearLayoutManager(AllTasks.this));



        rcLockedTask2.setAdapter(adapter);
        rcLockedTask2.setLayoutManager(new LinearLayoutManager(AllTasks.this));

        rcLockedTask.setNestedScrollingEnabled(false);
        rcLockedTask2.setNestedScrollingEnabled(false);


        Button buttonViewMore =findViewById(R.id.button_locked_task_view_more);
        buttonViewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rcLockedTask2.getVisibility() == View.VISIBLE) {
                    rcLockedTask2.setVisibility(View.GONE);
                } else {
                    rcLockedTask2.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    public List<LockedTaskDataModel> lockedTaskList1() {
        List<LockedTaskDataModel> data = new ArrayList<>();
        data.add(new LockedTaskDataModel(1,"7 times. 7 days.","Sub Title"));
        data.add(new LockedTaskDataModel(2,"7 times. 7 days.","Sub Title"));
        data.add(new LockedTaskDataModel(3,"7 times. 7 days.","Sub Title"));
        return data;
    }

    public List<LockedTaskDataModel> lockedTaskList2() {
        List<LockedTaskDataModel> data = new ArrayList<>();
        data.add(new LockedTaskDataModel(1,"7 times. 7 days.","Sub Title"));
        data.add(new LockedTaskDataModel(2,"7 times. 7 days.","Sub Title"));
        data.add(new LockedTaskDataModel(3,"7 times. 7 days.","Sub Title"));
        return data;
    }


}