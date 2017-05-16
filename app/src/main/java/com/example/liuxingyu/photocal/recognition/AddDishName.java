package com.example.liuxingyu.photocal.recognition;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;

import com.example.liuxingyu.photocal.MainActivity;
import com.example.liuxingyu.photocal.R;
import com.example.liuxingyu.photocal.historyFood.MainFoodAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuxingyu on 17/5/16.
 */

public class AddDishName extends Activity{

    private List<EachSuggestionDish> dishList = new ArrayList<EachSuggestionDish>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.add_dish_name);
        initView(savedInstanceState);
    }

    private void initView(Bundle savedInstanceState){
        initDish();
        EachSuggestionDishAdapter adapter = new EachSuggestionDishAdapter(AddDishName.this, R.layout.each_suggestion_dish_name, dishList);
        ListView listView = (ListView) findViewById(R.id.dish_name_suggestion);
        listView.setAdapter(adapter);
    }

    private void initDish(){
        EachSuggestionDish dish= new EachSuggestionDish(1,"dish Name1" ,"noodle and rice");
        dishList.add(dish);
        dish= new EachSuggestionDish(2,"dish Name2" ,"noodle and rice");
        dishList.add(dish);
        dish= new EachSuggestionDish(3,"dish Name3" ,"noodle and rice");
        dishList.add(dish);
        dish= new EachSuggestionDish(4,"dish Name4" ,"noodle and rice");
        dishList.add(dish);
        dish= new EachSuggestionDish(5,"dish Name5" ,"noodle and rice");
        dishList.add(dish);
        dish= new EachSuggestionDish(6,"dish Name6" ,"noodle and rice");
        dishList.add(dish);
        dish= new EachSuggestionDish(7,"dish Name7" ,"noodle and rice");
        dishList.add(dish);
        dish= new EachSuggestionDish(8,"dish Name8" ,"noodle and rice");
        dishList.add(dish);
        dish= new EachSuggestionDish(9,"dish Name9" ,"noodle and rice");
        dishList.add(dish);
        dish= new EachSuggestionDish(10,"dish Name9" ,"noodle and rice");
        dishList.add(dish);
    }

}
