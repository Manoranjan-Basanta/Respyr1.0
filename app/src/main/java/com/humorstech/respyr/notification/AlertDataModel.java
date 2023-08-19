package com.humorstech.respyr.notification;

public class AlertDataModel {

    int idNotification;
    String typeNotification;
    String titleNotification;
    String subTitle;
    String dateTime;

    AlertDataModel(int idNotification,String typeNotification,String titleNotification,String subTitle,String dateTime){
        this.idNotification=idNotification;
        this.typeNotification=typeNotification;
        this.titleNotification=titleNotification;
        this.subTitle=subTitle;
        this.dateTime=dateTime;
    }

}
