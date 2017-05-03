package com.example.liuxingyu.photocal.historyFood;

/**
 * Created by liuxingyu on 17/4/19.
 */

public class MainFood {
    private String time;
    private String cal;
    private int imageID;

    public MainFood(String time,String cal,int imageID){
        this.time=time;
        this.cal=cal;
        this.imageID=imageID;
    }

    public String getTime(){
        return time;
    }

    public String getCal(){
        return cal;
    }

    public int getImageID(){
        return imageID;
    }
}
