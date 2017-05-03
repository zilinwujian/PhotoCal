package com.example.liuxingyu.photocal.photo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.liuxingyu.photocal.MainActivity;
import com.example.liuxingyu.photocal.R;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;

/**
 * Created by liuxingyu on 17/4/18.
 */

public class Recognition extends Activity implements View.OnClickListener{


    private ImageView imageView;
    private ImageButton confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recognition_food);
        initViews();
    }

    private void initViews(){
        imageView = (ImageView) findViewById(R.id.recognition_image);
        String order = getIntent().getStringExtra("order");
        if (order.equals("photo")){
            String picPath = getIntent().getStringExtra("picPath");
            imageView.setImageBitmap(getBitmapFromPath(picPath));
        }
        if (order.equals("album")){
            String uri = getIntent().getStringExtra("uri");
            Uri imageUri= Uri.parse(uri);
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        confirm = (ImageButton) findViewById(R.id.recognition_confirm);
        confirm.setOnClickListener(this);
    }


    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.recognition_confirm:
                Intent intent = new Intent(Recognition.this, MainActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }





    private Bitmap getBitmapFromPath(String path) {
        FileInputStream fis = null;
        Bitmap bitmap = null;
        try {
            fis = new FileInputStream(path);
            bitmap = BitmapFactory.decodeStream(fis);
            Matrix matrix = new Matrix();
            matrix.setRotate(90);
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

}