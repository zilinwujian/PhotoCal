package com.example.liuxingyu.photocal.historyFood;

/**
 * Created by liuxingyu on 17/4/20.
 */

public class EachDayFood  {
    private int imageId;
    private String imagePath;

    public EachDayFood(int imageId){
        this.imageId=imageId;
    }

    public EachDayFood(String imagePath){
        this.imagePath=imagePath;
    }

    public int getImageId(){
        return imageId;
    }

    public String getImagePath(){
        return imagePath;
    }
}
