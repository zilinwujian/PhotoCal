package com.example.liuxingyu.photocal.Notification;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.liuxingyu.photocal.R;
import com.example.liuxingyu.photocal.historyFood.MainFood;
import com.example.liuxingyu.photocal.historyFood.MainFoodAdapter;

import java.util.List;


/**
 * Created by liuxingyu on 17/4/22.
 */

public class SimpleNotificationAdapter extends ArrayAdapter<SimpleNotification> {

    private int resourceId;

    public SimpleNotificationAdapter(Context context, int resource, List<SimpleNotification> objects) {
        super(context, resource, objects);
        resourceId=resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SimpleNotification note = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.image = (ImageView) view.findViewById(R.id.notification_simple_image);
            viewHolder.title = (TextView) view.findViewById(R.id.notification_simple_title);
            viewHolder.state = (TextView)  view.findViewById(R.id.notification_simple_state);
            view.setTag(viewHolder);                               // 将ViewHolder存储在View中
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();               // 重新获取ViewHolder
        }
        viewHolder.image.setImageResource(note.getImageId());
        viewHolder.title.setText(note.getNotificationTitle());
        viewHolder.state.setText(note.getNotificationState());
        return view;
    }

    class ViewHolder {
        ImageView image;
        TextView title;
        TextView state;
    }

}
