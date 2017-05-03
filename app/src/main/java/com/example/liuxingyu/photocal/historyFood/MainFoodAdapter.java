package com.example.liuxingyu.photocal.historyFood;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.liuxingyu.photocal.R;

import java.util.List;

/**
 * Created by liuxingyu on 17/4/19.
 */

public class MainFoodAdapter extends ArrayAdapter<MainFood> {
    private int resourceId;

    public MainFoodAdapter(Context context, int textViewResourceId, List<MainFood> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MainFood food = getItem(position); // 获取当前项的Fruit实例
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.foodImage = (ImageView) view.findViewById(R.id.main_first_food_photo);
            viewHolder.foodTime = (TextView) view.findViewById(R.id.main_first_food_time);
            viewHolder.foodCal = (TextView)  view.findViewById(R.id.main_first_food_cal);
            view.setTag(viewHolder);         // 将ViewHolder存储在View中
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag(); // 重新获取ViewHolder
        }
        viewHolder.foodImage.setImageResource(food.getImageID());
        viewHolder.foodTime.setText(food.getTime());
        viewHolder.foodCal.setText(food.getCal());
        return view;
    }

    class ViewHolder {
        ImageView foodImage;
        TextView foodTime;
        TextView foodCal;
    }
}
