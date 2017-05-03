package com.example.liuxingyu.photocal.Notification;

/**
 * Created by liuxingyu on 17/4/22.
 */

public class SimpleNotification {
    private String id;
    private int imageId;
    private String notificationTitle;
    private String notificationState;

    public SimpleNotification(String id,int imageId,String notificationTitle,String notificationState){
        this.id=id;
        this.imageId=imageId;
        this.notificationTitle=notificationTitle;
        this.notificationState=notificationState;
    }

    public String getId(){
        return id;
    }

    public int getImageId(){
        return imageId;
    }

    public String getNotificationTitle(){
        return notificationTitle;
    }

    public String getNotificationState(){
        return notificationState;
    }
}
