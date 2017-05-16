package com.example.liuxingyu.photocal.recognition;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.liuxingyu.photocal.R;
import com.example.liuxingyu.photocal.historyFood.MainFood;
import com.example.liuxingyu.photocal.historyFood.MainFoodAdapter;

import java.util.List;

/**
 * Created by liuxingyu on 17/5/16.
 */

public class EachSuggestionDishAdapter extends ArrayAdapter<EachSuggestionDish> {
    private int resourceId;

    public EachSuggestionDishAdapter(Context context, int textViewResourceId, List<EachSuggestionDish> objects){
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        EachSuggestionDish dish = getItem(position); // 获取当前项的Fruit实例
        View view;
        EachSuggestionDishAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new EachSuggestionDishAdapter.ViewHolder();
            viewHolder.dishNum = (TextView) view.findViewById(R.id.each_suggestion_dish_number);
            viewHolder.dishName = (TextView) view.findViewById(R.id.each_suggestion_dish_name);
            viewHolder.dishIngredient = (TextView)  view.findViewById(R.id.each_suggestion_dish_ingredient);
            view.setTag(viewHolder);         // 将ViewHolder存储在View中
        } else {
            view = convertView;
            viewHolder = (EachSuggestionDishAdapter.ViewHolder) view.getTag(); // 重新获取ViewHolder
        }
        viewHolder.dishNum.setText(dish.getDishNum()+"、");
        viewHolder.dishName.setText(dish.getDishName());
        viewHolder.dishIngredient.setText(dish.getDishIngredient());
        return view;
    }

    class ViewHolder {
        TextView dishNum;
        TextView dishName;
        TextView dishIngredient;
    }


}
