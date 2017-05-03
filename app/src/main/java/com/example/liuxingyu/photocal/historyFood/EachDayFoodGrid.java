package com.example.liuxingyu.photocal.historyFood;

import java.util.List;

/**
 * Created by liuxingyu on 17/4/20.
 */

public class EachDayFoodGrid {
    private String time;
    private String cal;
    private List<EachDayFood> eachDayFoodList;

    public EachDayFoodGrid(String time,String cal, List<EachDayFood> eachDayFoodList){
        this.time=time;
        this.cal=cal;
        this.eachDayFoodList=eachDayFoodList;
    }

    public String getTime(){
        return time;
    }

    public String getCal(){
        return cal;
    }

    public List<EachDayFood> getEachDayFoodList(){
        return eachDayFoodList;
    }
}
