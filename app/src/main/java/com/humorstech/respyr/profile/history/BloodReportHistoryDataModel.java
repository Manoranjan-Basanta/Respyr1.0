package com.humorstech.respyr.profile.history;

public class BloodReportHistoryDataModel {
    int id;
    int bloodReportHealthScore;
    String bloodReportTitle;
    String bloodReportDateAndTime;
    long tmStamp;

    BloodReportHistoryDataModel(int id, int bloodReportHealthScore, String bloodReportTitle, String bloodReportDateAndTime, long tmStamp){
        this.id = id;
        this.bloodReportHealthScore = bloodReportHealthScore;
        this.bloodReportTitle = bloodReportTitle;
        this.bloodReportDateAndTime = bloodReportDateAndTime;
        this.tmStamp = tmStamp;
    }
}
