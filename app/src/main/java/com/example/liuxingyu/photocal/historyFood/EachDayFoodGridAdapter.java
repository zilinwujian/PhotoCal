package com.example.liuxingyu.photocal.historyFood;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.liuxingyu.photocal.R;

import java.util.List;

/**
 * Created by liuxingyu on 17/4/20.
 */

public class EachDayFoodGridAdapter extends ArrayAdapter<EachDayFood> {

    public int resourceId;

    public EachDayFoodGridAdapter(Context context, int resource, List<EachDayFood> objects){
        super(context, resource, objects);
        resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        EachDayFood food = getItem(position); // 获取当前项的Food实例
        View view;
        ViewHolder viewHolder;
        if (convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.foodImage = (ImageView) view.findViewById(R.id.each_day_food_element);
            view.setTag(viewHolder);         // 将ViewHolder存储在View中
        }else{
            view = convertView;
            viewHolder = (ViewHolder) view.getTag(); // 重新获取ViewHolder
        }
        viewHolder.foodImage.setImageResource(food.getImageId());
        return view;
    }

    class ViewHolder {
        ImageView foodImage;
    }





}
