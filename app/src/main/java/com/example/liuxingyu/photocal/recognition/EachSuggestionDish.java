package com.example.liuxingyu.photocal.recognition;

import com.example.liuxingyu.photocal.historyFood.EachDayFood;

/**
 * Created by liuxingyu on 17/5/16.
 */

public class EachSuggestionDish {
    private int dishNum;
    private String dishName;
    private String dishIngredient;

    public EachSuggestionDish(int dishNum,String dishName,String dishIngredient){
        this.dishNum=dishNum;
        this.dishName=dishName;
        this.dishIngredient=dishIngredient;
    }

    public int getDishNum(){
        return this.dishNum;
    }

    public String getDishName(){
        return this.dishName;
    }

    public String getDishIngredient(){
        return this.dishIngredient;
    }
}
