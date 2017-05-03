package com.example.liuxingyu.photocal.historyFood;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.liuxingyu.photocal.R;
import com.example.liuxingyu.photocal.ui.MyGridView;

import java.util.List;

/**
 * Created by liuxingyu on 17/4/20.
 */

public class EachDayFoodListAdapter extends ArrayAdapter<EachDayFoodGrid> {
    private int resourceId;

    public EachDayFoodListAdapter(Context context, int resource, List<EachDayFoodGrid> objects) {
        super(context, resource, objects);
        resourceId=resource;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        EachDayFoodGrid foodGrid = getItem(position); // 获取当前项的FoodGrid实例
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.time = (TextView) view.findViewById(R.id.each_day_food_time);
            viewHolder.cal = (TextView) view.findViewById(R.id.each_day_food_cal);
            viewHolder.gridView = (MyGridView) view.findViewById(R.id.each_day_food_gridview);
            view.setTag(viewHolder);         // 将ViewHolder存储在View中
        }else{
            view = convertView;
            viewHolder = (ViewHolder) view.getTag(); // 重新获取ViewHolder
        }
        EachDayFoodGridAdapter adapter = new EachDayFoodGridAdapter(getContext(), R.layout.each_day_food_element,foodGrid.getEachDayFoodList());
        viewHolder.gridView.setAdapter(adapter);
        viewHolder.time.setText(foodGrid.getTime());
        viewHolder.cal.setText(foodGrid.getCal());
        return view;
    }


    class ViewHolder {
        TextView time;
        TextView cal;
        MyGridView gridView;
    }

}
