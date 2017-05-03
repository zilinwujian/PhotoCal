package com.example.liuxingyu.photocal.Notification;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.liuxingyu.photocal.R;

/**
 * Created by liuxingyu on 17/4/22.
 */

public class NotificationContent extends Activity {

    TextView title;
    TextView time;
    TextView content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_content);
        String id = getIntent().getStringExtra("id");
        title = (TextView) findViewById(R.id.notification_content_title);
        time = (TextView) findViewById(R.id.notification_content_time);
        content = (TextView) findViewById(R.id.notification_content_text);
        if (id.equals("2")){
           handle1();
        }else{
            handle2();
        }
    }

    public void handle1(){
        title.setText("Welcome to the PhotoCal");
        time.setText("3 March");
        content.setText("PhotoCal is an application focusing on diet health. In this application user can record their every diet, easy to know the calory of food, even the gradient, it is amasing , worth to try !");
        content.append("\n");
        content.append("\n");
        content.append("PhotoCal is an application focusing on diet health. In this application user can record their every diet, easy to know the calory of food, even the gradient, it is amasing , worth to try !");
    }

    public void handle2(){
        title.setText("Your first week report");
        time.setText("12 March");
        content.setText("This your first week report");
    }
}
