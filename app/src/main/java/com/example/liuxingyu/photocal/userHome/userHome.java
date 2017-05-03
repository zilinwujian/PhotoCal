package com.example.liuxingyu.photocal.userHome;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.example.liuxingyu.photocal.MainActivity;
import com.example.liuxingyu.photocal.Notification.NotificationHome;
import com.example.liuxingyu.photocal.R;
import com.example.liuxingyu.photocal.photo.CustomCamera;

/**
 * Created by liuxingyu on 17/4/20.
 */

public class userHome extends Activity implements View.OnClickListener {

    //RelativeLayout head = null;
    RelativeLayout foot = null;
    ImageButton takePhoto;
    ImageButton user;
    ImageButton home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_home);
        initView(savedInstanceState);

    }

    private void initView(Bundle savedInstanceState){
        //head = (RelativeLayout) findViewById(R.id.main_head);
        foot = (RelativeLayout) findViewById(R.id.main_foot);

        takePhoto = (ImageButton) foot.findViewById(R.id.foot_photo);
        user = (ImageButton) foot.findViewById(R.id.foot_user);
        home = (ImageButton) foot.findViewById(R.id.foot_home);

        //user.setBackground(R.drawable.foot_home_white);
        user.setImageResource(R.drawable.foot_user_red);
        home.setImageResource(R.drawable.foot_home_white);

        takePhoto.setOnClickListener(this);
        //user.setOnClickListener(this);
        home.setOnClickListener(this);
    }



    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.foot_photo:
                startActivity(new Intent(this, CustomCamera.class));
                break;
            case R.id.foot_home:
                startActivity(new Intent(this, MainActivity.class));
                break;
            default:
                break;
        }
    }


}
