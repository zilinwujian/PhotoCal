package com.example.liuxingyu.photocal.photo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liuxingyu.photocal.MainActivity;
import com.example.liuxingyu.photocal.R;
import com.example.liuxingyu.photocal.recognition.AddDishName;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by liuxingyu on 17/4/18.
 */

public class Recognition extends Activity implements View.OnClickListener{


    private ImageView imageView;
    private ImageButton confirm;
    private TextView dishName;

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

        dishName = (TextView)findViewById(R.id.recognition_name_text);
        dishName.setOnClickListener(this);

        confirm = (ImageButton) findViewById(R.id.recognition_confirm);
        confirm.setOnClickListener(this);
    }


    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.recognition_confirm:
                saveImageView();
                Intent intent = new Intent(Recognition.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.recognition_name_text:
               // Toast.makeText(Recognition.this,"dish name",Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(Recognition.this, AddDishName.class);
                startActivity(intent2);

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

    /*
    保存图片
     */
    public File saveImageView(){
        imageView.buildDrawingCache();
        Bitmap bitmap=imageView.getDrawingCache();
        //将Bitmap 转换成二进制，写入本地
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG , 100 , stream);
        byte[] byteArray = stream.toByteArray();
        File dir=new File(Environment.getExternalStorageDirectory ().getAbsolutePath()+"/picture" );
        if(!dir.isFile()){
            dir.mkdir();
        }

        File file=new File(dir,getTime()+".png" );

        try {
            FileOutputStream fos=new FileOutputStream(file);
            fos.write(byteArray, 0 , byteArray.length);
            fos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public String getTime() {
        Date date=new Date();
        SimpleDateFormat df=new SimpleDateFormat("yyyyMMddhhmmss");
        return df.format(date);
    }

}
