package com.example.liuxingyu.photocal.historyFood;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.liuxingyu.photocal.R;
import com.example.liuxingyu.photocal.photo.Recognition;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
        OnClick listener=null;
        if (convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.foodImage = (ImageView) view.findViewById(R.id.each_day_food_element);
            view.setTag(viewHolder);         // 将ViewHolder存储在View中
        }else{
            view = convertView;
            viewHolder = (ViewHolder) view.getTag(); // 重新获取ViewHolder
        }
        //viewHolder.foodImage.setImageResource(food.getImageId());
        viewHolder.foodImage.setImageBitmap(getBitmapFromPath(food.getImagePath()));
        listener = new OnClick(position,food.getImagePath());
        viewHolder.foodImage.setOnClickListener(listener);
        return view;
    }

    class ViewHolder {
        ImageView foodImage;
    }

    private Bitmap getBitmapFromPath(String path) {
        FileInputStream fis = null;
        Bitmap bitmap = null;
        try {
            fis = new FileInputStream(path);
            bitmap = BitmapFactory.decodeStream(fis);
            Matrix matrix = new Matrix();
            bitmap = Bitmap.createBitmap(bitmap, 0, 0,
                    bitmap.getWidth(), bitmap.getHeight(),
                    matrix, true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bitmap;
    }

    class OnClick implements View.OnClickListener {
        int position;
        String imagePath;

        public OnClick(int position,String imagePath){
            this.position = position;
            this.imagePath=imagePath;
        }

        @Override
        public void onClick(View v) {
            Context context=v.getContext();  //运行上下文
            Intent intent = new Intent(context, Recognition.class);
            intent.putExtra("order","photo");
            intent.putExtra("picPath",this.imagePath);
            context.startActivity(intent);
        }
    }





}
