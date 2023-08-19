package com.humorstech.respyr.reward;

public class LockedTaskDataModel {

    int id;
    String taskTitle;
    String taskSubTitle;
    LockedTaskDataModel(int id, String taskTitle, String taskSubTitle){
        this.id = id;
        this.taskTitle = taskTitle;
        this.taskSubTitle = taskSubTitle;
    }
}
