package com.example.liuxingyu.photocal;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.liuxingyu.photocal.Notification.NotificationHome;
import com.example.liuxingyu.photocal.historyFood.MainFood;
import com.example.liuxingyu.photocal.historyFood.MainFoodAdapter;
import com.example.liuxingyu.photocal.historyFood.MonthFood;
import com.example.liuxingyu.photocal.photo.CustomCamera;
import com.example.liuxingyu.photocal.userHome.userHome;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements OnClickListener,AdapterView.OnItemClickListener{

    RelativeLayout head = null;
    RelativeLayout foot = null;
    ImageButton takePhoto;
    ImageButton back;
    ImageButton user;
    ImageButton note;
    private List<MainFood> foodList = new ArrayList<MainFood>();
    private static int CAMERA_CODE1 = 1;
    private static int CAMERA_CODE2 = 2;
    private String mFilePath = "";
    private ImageView mImageViewShow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initView(savedInstanceState);
    }


    private void initView(Bundle savedInstanceState){
        head = (RelativeLayout) findViewById(R.id.main_head);
        foot = (RelativeLayout) findViewById(R.id.main_foot);
        takePhoto = (ImageButton) foot.findViewById(R.id.foot_photo);
        back = (ImageButton) head.findViewById(R.id.head_back);
        user = (ImageButton) foot.findViewById(R.id.foot_user);
        note = (ImageButton) head.findViewById(R.id.head_info);


        initFood();
        MainFoodAdapter adapter = new MainFoodAdapter(MainActivity.this, R.layout.main_history_food, foodList);
        ListView listView = (ListView) findViewById(R.id.main_listview);
        listView.setAdapter(adapter);

        back.setImageAlpha(0);

        takePhoto.setOnClickListener(this);
        user.setOnClickListener(this);
        note.setOnClickListener(this);
        listView.setOnItemClickListener(this);




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
            case R.id.head_info:
                startActivity(new Intent(this, NotificationHome.class));
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MainFood food = foodList.get(position);
        //Toast.makeText(MainActivity.this, fruit.getTime(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, MonthFood.class);
        intent.putExtra("Time",food.getTime());
        startActivity(intent);
    }



    private void initFood(){
        MainFood food = new MainFood("Mar 2017","75432Kcal",R.drawable.main_history_example1);
        foodList.add(food);
        food = new MainFood("Feb 2017","753Kcal",R.drawable.main_history_example2);
        foodList.add(food);
        food = new MainFood("Jan 2017","132Kcal",R.drawable.main_history_example3);
        foodList.add(food);

    }




//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK) {
//            if (requestCode == CAMERA_CODE1) {
//                /**
//                 * 通过暂存路径取得图片
//                 */
//                FileInputStream fis = null;
//                Bitmap bitmap = null;
//                try {
//                    fis = new FileInputStream(mFilePath);
//                    bitmap = BitmapFactory.decodeStream(fis);
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                } finally {
//                    if (fis != null) {
//                        try {
//                            fis.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//                mImageViewShow.setImageBitmap(bitmap);
//            } else if (requestCode == CAMERA_CODE2) {
//                /**
//                 * 通过data取得图片
//                 */
//                Bundle extras = data.getExtras();
//                Bitmap bitmap = (Bitmap) extras.get("data");
//                mImageViewShow.setImageBitmap(bitmap);
//            }
//        }
//    }

}
