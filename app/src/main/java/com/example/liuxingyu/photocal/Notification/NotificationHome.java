package com.example.liuxingyu.photocal.Notification;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

import com.example.liuxingyu.photocal.R;
import com.example.liuxingyu.photocal.photo.CustomCamera;
import com.example.liuxingyu.photocal.userHome.userHome;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuxingyu on 17/4/22.
 */

public class NotificationHome extends Activity implements OnClickListener,AdapterView.OnItemClickListener{

    RelativeLayout head = null;
    ImageButton back;
    ImageButton note;
    TextView title;
    private List<SimpleNotification> noteList = new ArrayList<SimpleNotification>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_home);
        init();




    }

    private void init(){
        head = (RelativeLayout) findViewById(R.id.main_head);
        back = (ImageButton) head.findViewById(R.id.head_back);
        note = (ImageButton) head.findViewById(R.id.head_info);
        title= (TextView) head.findViewById(R.id.head_title);

        note.setVisibility(View.INVISIBLE);
        title.setText("Notification");
        title.setTextSize(20);    //默认类型是sp

        initdata();
        ListView listView = (ListView) findViewById(R.id.notification_listview);
        SimpleNotificationAdapter noteAdapter = new SimpleNotificationAdapter(NotificationHome.this,R.layout.notification_simple_item,noteList);
        listView.setAdapter(noteAdapter);


        back.setOnClickListener(this);
        listView.setOnItemClickListener(this);

    }


    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.head_back:
                this.finish();
                break;
            default:
                break;
        }
    }

    private void initdata(){
        SimpleNotification s=new SimpleNotification("1",R.drawable.notification_simple_image,
                "Your first week report","Already read");
        noteList.add(s);

        s=new SimpleNotification("2",R.drawable.notification_simple_image2,
                "Welcomt to PhotoCal","Already read");
        noteList.add(s);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SimpleNotification note = noteList.get(position);
        //Toast.makeText(NotificationHome.this, note.getNotificationTitle(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(NotificationHome.this, NotificationContent.class);
        intent.putExtra("id",note.getId());
        startActivity(intent);
    }
}
