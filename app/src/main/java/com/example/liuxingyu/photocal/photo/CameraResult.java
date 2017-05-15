package com.example.liuxingyu.photocal.photo;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.example.liuxingyu.photocal.MainActivity;
import com.example.liuxingyu.photocal.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;

import static android.content.ContentValues.TAG;

/**
 * Created by liuxingyu on 17/4/16.
 */

public class CameraResult extends Activity implements View.OnClickListener{

    public static final int TAKE_PHOTO = 1;
    public static final int CROP_PHOTO = 2;
    public static final int CHOOSE_PICTURE = 3;
    private Uri imageUri;
    private ImageView imageView;
    private ImageButton albumReset;
    private ImageButton takePhoto;
    private ImageButton confirm;
    String order;
    String picPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_camera_result);
        imageView = (ImageView) findViewById(R.id.iv_camera_result);

        picPath = getIntent().getStringExtra("picPath");
        order = getIntent().getStringExtra("order");

        if (order.equals("photo")){
            imageView.setImageBitmap(getBitmapFromPath(picPath));
        }
        if (order.equals("album")){
            chooseFromAblum();
//            int degree= readPictureDegree(picPath);
//            imageUri = Uri.parse(getIntent().getStringExtra("imageUri"));
//            Bitmap bm = getSmallBitmap(picPath,400,200);
//            bm = rotaingImageView(270,bm);
//            imageView.setImageBitmap(bm);
        }

        albumReset = (ImageButton) findViewById(R.id.album_reset);
        takePhoto  = (ImageButton) findViewById(R.id.album_takephoto);
        confirm    = (ImageButton) findViewById(R.id.ablum_photo_confirm);

        albumReset.setOnClickListener(this);
        takePhoto.setOnClickListener(this);
        confirm.setOnClickListener(this);

}


    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.album_reset:
                chooseFromAblum();
                break;
            case R.id.album_takephoto:
                Intent intent = new Intent(CameraResult.this,CustomCamera.class);
                startActivity(intent);
                this.finish();
                break;
            case R.id.ablum_photo_confirm:
                Intent confirmIntent = new Intent(this, Recognition.class);
                if (order.equals("photo")){
                    confirmIntent.putExtra("picPath", picPath);
                    confirmIntent.putExtra("order","photo");
                }
                if (order.equals("album")){
                    confirmIntent.putExtra("uri",imageUri.toString());
                    confirmIntent.putExtra("order","album");
                }



                startActivity(confirmIntent);
                break;
            default:
                break;
        }
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

        File file=new File(dir,"food.png" );

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


    /*
      从相册中选择图片
     */
    public void chooseFromAblum(){
        CustomCamera camera = new CustomCamera();
        File outputImage = camera.getOutputMediaFile();
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageUri = Uri.fromFile(outputImage);
        Intent intent = new Intent(Intent.ACTION_PICK,null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, CROP_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CROP_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        imageUri = data.getData();
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        imageView.setImageBitmap(bitmap); // 将裁剪后的照片显示出来
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;

            case CHOOSE_PICTURE:
                if (resultCode == RESULT_OK) {
                    Bitmap bm = null;
                    //外界的程序访问ContentProvider所提供数据 可以通过ContentResolver接口
                    ContentResolver resolver = getContentResolver();
                    //此处的用于判断接收的Activity是不是你想要的那个
                    try {
                        Uri originalUri = data.getData();        //获得图片的uri
                        bm = MediaStore.Images.Media.getBitmap(resolver, originalUri);        //显得到bitmap图片
                        String[] proj = {MediaStore.Images.Media.DATA};
                        //好像是android多媒体数据库的封装接口，具体的看Android文档
                        Cursor cursor = managedQuery(originalUri, proj, null, null, null);
                        //按我个人理解 这个是获得用户选择的图片的索引值
                        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        //将光标移至开头 ，这个很重要，不小心很容易引起越界
                        cursor.moveToFirst();
                        //最后根据索引值获取图片路径
                        String path = cursor.getString(column_index);

//                        imageUri = data.getData();
//                        String text = imageUri.getPath();
//                        Toast.makeText(CustomCamera.this, path, Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(CustomCamera.this,CameraResult.class);
//                        intent.putExtra("picPath", path);
//                        intent.putExtra("type","1");
//                        intent.putExtra("imageUri", imageUri.toString());
//                        startActivity(intent);
                    }catch (IOException e) {
                        Log.e(TAG,e.toString());
                    }

//                    Intent intent = new Intent("com.android.camera.action.CROP");
//                    intent.setDataAndType(data.getData(), "image/*");
//                    // 设置裁剪
//                    intent.putExtra("crop", "true");
//                    // aspectX aspectY 是宽高的比例
//                    intent.putExtra("aspectX", 1);
//                    intent.putExtra("aspectY", 1);
//                    // outputX outputY 是裁剪图片宽高
//                    intent.putExtra("outputX", 600);
//                    intent.putExtra("outputY", 600);
//                    //如果为true,则通过 Bitmap bmap = data.getParcelableExtra("data")取出数据
//                    intent.putExtra("return-data", false);
//                    intent.putExtra(MediaStore.EXTRA_OUTPUT, pictureFile.getAbsolutePath());
//                    startActivityForResult(intent, i);


                }
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

    /**
     * 计算图片的缩放值
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options,int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }
    /**
     * 根据路径获得突破并压缩返回bitmap用于显示
     *
     * @param filePath
     * @return
     */
    public static Bitmap getSmallBitmap(String filePath,int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;  //只返回图片的大小信息
        BitmapFactory.decodeFile(filePath, options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }





    /**
     * 读取图片属性：旋转的角度
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    public static int readPictureDegree(String path) {
        int degree  = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }
    /*
     * 旋转图片
     * @param angle
     * @param bitmap
     * @return Bitmap
     */
    public static Bitmap rotaingImageView(int angle , Bitmap bitmap) {
        //旋转图片 动作
        Matrix matrix = new Matrix();;
        matrix.postRotate(angle);
        System.out.println("angle2=" + angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }


}
