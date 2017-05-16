package com.example.liuxingyu.photocal.historyFood;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.liuxingyu.photocal.MainActivity;
import com.example.liuxingyu.photocal.Notification.NotificationHome;
import com.example.liuxingyu.photocal.R;
import com.example.liuxingyu.photocal.photo.CustomCamera;
import com.example.liuxingyu.photocal.userHome.userHome;
import com.example.liuxingyu.photocal.historyFood.GetHistoryPicList;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuxingyu on 17/4/19.
 */

public class MonthFood extends Activity implements View.OnClickListener {

    RelativeLayout head = null;
    RelativeLayout foot = null;
    ImageButton takePhoto;
    ImageButton back;
    ImageButton user;
    ImageButton note;
    ImageButton home;
    TextView title;
    private ListView listView;
    List<EachDayFood> list = new ArrayList<EachDayFood>();
    List<EachDayFoodGrid> gridList = new ArrayList<EachDayFoodGrid>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.each_month_food);

        head = (RelativeLayout) findViewById(R.id.main_head);
        foot = (RelativeLayout) findViewById(R.id.main_foot);
        takePhoto = (ImageButton) foot.findViewById(R.id.foot_photo);
        back = (ImageButton) head.findViewById(R.id.head_back);
        user = (ImageButton) foot.findViewById(R.id.foot_user);
        note = (ImageButton) head.findViewById(R.id.head_info);
        home = (ImageButton) foot.findViewById(R.id.foot_home);
        title= (TextView)    head.findViewById(R.id.head_title);

        note.setVisibility(View.INVISIBLE);
        home.setImageResource(R.drawable.foot_home_white);

        String time = getIntent().getStringExtra("Time");
//        if (time.equals("Mar 2017")){
//            initdata();
//            title.setText("Mar 2017");
//        }else{
//            initdata2();
//            title.setText("Feb 2017");
//        }
        initdata3();


        listView = (ListView) findViewById(R.id.each_mooth_food_listview);
        EachDayFoodListAdapter adapter = new EachDayFoodListAdapter(MonthFood.this,R.layout.each_day_food_gridview,gridList);
        listView.setAdapter(adapter);

        home.setOnClickListener(this);
        takePhoto.setOnClickListener(this);
        user.setOnClickListener(this);
        back.setOnClickListener(this);
    }


    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.foot_photo:
                startActivity(new Intent(this, CustomCamera.class));
                break;
            case R.id.foot_user:
                startActivity(new Intent(this, userHome.class));
                break;
            case R.id.foot_home:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.head_back:
                this.finish();
                break;
            default:
                break;
        }
    }


//    public void initdata(){
//        EachDayFood food = new EachDayFood(R.drawable.each_food_example1);
//        list.add(food);
//        food = new EachDayFood(R.drawable.each_food_example2);
//        list.add(food);
//        food = new EachDayFood(R.drawable.each_food_example3);
//        list.add(food);
//        food = new EachDayFood(R.drawable.each_food_example4);
//        list.add(food);
//        EachDayFoodGrid foodGrid = new EachDayFoodGrid("01 Mar","2300Kcal",list);
//        gridList.add(foodGrid);
//
//        list = new ArrayList<EachDayFood>();
//
//        food = new EachDayFood(R.drawable.each_food_example1);
//        list.add(food);
//        food = new EachDayFood(R.drawable.each_food_example2);
//        list.add(food);
//        food = new EachDayFood(R.drawable.each_food_example3);
//        list.add(food);
//        food = new EachDayFood(R.drawable.each_food_example4);
//        list.add(food);
//        food = new EachDayFood(R.drawable.each_food_example5);
//        list.add(food);
//        food = new EachDayFood(R.drawable.each_food_example6);
//        list.add(food);
//        foodGrid = new EachDayFoodGrid("02 Mar","2500Kcal",list);
//        gridList.add(foodGrid);
//
//    }
//
//    public void initdata2(){
//        EachDayFood food = new EachDayFood(R.drawable.each_food_example1);
//        list.add(food);
//        food = new EachDayFood(R.drawable.each_food_example2);
//        list.add(food);
//        food = new EachDayFood(R.drawable.each_food_example3);
//        list.add(food);
//        food = new EachDayFood(R.drawable.each_food_example4);
//        list.add(food);
//        EachDayFoodGrid foodGrid = new EachDayFoodGrid("01 Feb","2300Kcal",list);
//        gridList.add(foodGrid);
//
//    }

    public void initdata3(){
        GetHistoryPicList gp=new GetHistoryPicList();
        List<String> picList=gp.getImagePathFromSD("2017","05");
        String path;
        String FileName = null;
        String lastFileDay = "-1";
        String Month="May";
        if (picList.size() > 0){           //获取第一个文件的天数,生成第一个文件，初始化
            path = picList.get(0);
            EachDayFood food = new EachDayFood(path);
            list.add(food);
            FileName = path.substring(path.lastIndexOf("/") + 1, path.length()-4).toLowerCase();
            lastFileDay =FileName.substring(6,8);
            EachDayFoodGrid foodGrid = new EachDayFoodGrid(lastFileDay+" "+Month,"2300Kcal",list);
            gridList.add(foodGrid);
        }
        for(int i=1;i<picList.size();i++){
            path=picList.get(i);
            FileName = path.substring(path.lastIndexOf("/") + 1, path.length()-4).toLowerCase();
            String FileDay = FileName.substring(6,8);
            EachDayFood food = new EachDayFood(path);
            if (FileDay.equals(lastFileDay)){
                list.add(food);
            }else{
                list = new ArrayList<EachDayFood>();
                list.add(food);
                lastFileDay=FileDay;
                EachDayFoodGrid foodGrid = new EachDayFoodGrid(lastFileDay+" "+Month,"2300Kcal",list);
                gridList.add(foodGrid);
            }

        }


    }
}
