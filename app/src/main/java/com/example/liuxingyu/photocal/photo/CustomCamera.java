package com.example.liuxingyu.photocal.photo;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.liuxingyu.photocal.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.content.ContentValues.TAG;

/**
 * Created by liuxingyu on 17/4/16.
 */

public class CustomCamera extends Activity implements SurfaceHolder.Callback,View.OnClickListener {
    /**
     * Camera回调，通过data[]保持图片数据信息
     */
    Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            File pictureFile = getOutputMediaFile();
            if (pictureFile == null) {
                return;
            }
            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();
                Intent intent = new Intent(CustomCamera.this, CameraResult.class);
                intent.putExtra("picPath", pictureFile.getAbsolutePath());
                intent.putExtra("order","photo");
                startActivity(intent);
                CustomCamera.this.finish();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };
    public static final int TAKE_PHOTO = 1;
    public static final int CROP_PHOTO = 2;
    public static final int CHOOSE_PICTURE = 3;
    private SurfaceView mCameraPreview;
    private SurfaceHolder mSurfaceHolder;
    private Camera mCamera;
    private boolean isBackCameraOn = true;
    private ImageButton takephoto;
    private ImageButton photoAlbum;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.camera_preview);
        initViews();
    }

    private void initViews() {
        mCameraPreview = (SurfaceView) findViewById(R.id.sv_camera);
        mSurfaceHolder = mCameraPreview.getHolder();
        mSurfaceHolder.addCallback(this);
        mCameraPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCamera.autoFocus(null);
            }
        });
        takephoto = (ImageButton) findViewById(R.id.camera_takephoto);
        photoAlbum = (ImageButton)findViewById(R.id.camera_photo_album);
        takephoto.setOnClickListener(this);
        photoAlbum.setOnClickListener(this);
    }


    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.camera_takephoto:
                capture(v);
                break;
            case R.id.camera_photo_album:
                Intent intent = new Intent(CustomCamera.this,CameraResult.class);
                intent.putExtra("order","album");
                startActivity(intent);
                //chooseFromAblum(v);
                break;
            default:
                break;
        }
    }



    /*
      从相册中选择图片
     */
    public void chooseFromAblum(View v){
        // 创建File对象,用于存储选择的照片
        File outputImage = getOutputMediaFile();
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageUri = Uri.fromFile(outputImage);
//        String text = imageUri.getPath();
//        Toast.makeText(CustomCamera.this, text, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(Intent.ACTION_PICK,null);
        //此处调用了图片选择器
        //如果直接写intent.setDataAndType("image/*");
        //调用的是系统图库
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, CHOOSE_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {

            case CROP_PHOTO:
//                if (resultCode == RESULT_OK) {
//                    Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
//                    picture.setImageBitmap(bitmap); // 将裁剪后的照片显示出来
//                }
//                break;
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

                        imageUri = data.getData();
                        String text = imageUri.getPath();
                        Toast.makeText(CustomCamera.this, path, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(CustomCamera.this,CameraResult.class);
                        intent.putExtra("picPath", path);
                        intent.putExtra("type","1");
                        intent.putExtra("imageUri", imageUri.toString());
                        startActivity(intent);
                    }catch (IOException e) {
                        Log.e(TAG,e.toString());
                    }











//
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





//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        switch (requestCode) {
//            case CHOOSE_PICTURE:
//                if (resultCode == RESULT_OK) {
//                    File pictureFile=getOutputMediaFile();
//                    String text = pictureFile.getPath();
//                    Toast.makeText(CustomCamera.this, text, Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(CustomCamera.this,CameraResult.class);
//                    intent.putExtra("picPath", pictureFile.getAbsolutePath());
                    //startActivity(intent);
//                }
//                break;
//
//            default:
//                break;
//        }
//    }




    /**
     * 切换前后摄像头
     *
     * @param view view
     */
    public void switchCamera(View view) {
        int cameraCount;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        cameraCount = Camera.getNumberOfCameras();
        // 遍历可用摄像头
        for (int i = 0; i < cameraCount; i++) {
            Camera.getCameraInfo(i, cameraInfo);
            if (isBackCameraOn) {
                if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                    releaseCamera();
                    mCamera = Camera.open(i);
                    setStartPreview(mCamera, mSurfaceHolder);
                    isBackCameraOn = false;
                    break;
                }
            } else {
                if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                    releaseCamera();
                    mCamera = Camera.open(i);
                    setStartPreview(mCamera, mSurfaceHolder);
                    isBackCameraOn = true;
                    break;
                }
            }
        }
    }

    /**
     * 拍照
     *
     * @param view view
     */
    public void capture(View view) {
        Camera.Parameters params = mCamera.getParameters();
        params.setPictureFormat(ImageFormat.JPEG);
        params.setPreviewSize(800, 400);
        params.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        mCamera.setParameters(params);
        // 使用自动对焦功能
        mCamera.autoFocus(new Camera.AutoFocusCallback() {
            @Override
            public void onAutoFocus(boolean success, Camera camera) {
                if (success) {
                    mCamera.takePicture(null, null, mPictureCallback);
                }
            }
        });
    }

    /**
     * 获取图片保持路径
     *
     * @return pic Path
     */
    public File getOutputMediaFile() {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "MyCameraApp");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + "temp.png");
        return mediaFile;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        setStartPreview(mCamera, mSurfaceHolder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (mSurfaceHolder.getSurface() == null) {
            return;
        }
        try {
            mCamera.stopPreview();
        } catch (Exception e) {
            e.printStackTrace();
        }
        setStartPreview(mCamera, mSurfaceHolder);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        releaseCamera();
    }

    /**
     * 释放相机资源
     */
    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (this.checkCameraHardware(this) && (mCamera == null)) {
            mCamera = getCamera();
            if (mSurfaceHolder != null) {
                setStartPreview(mCamera, mSurfaceHolder);
            }
        }
    }

    /**
     * 初始化相机
     *
     * @return camera
     */
    private Camera getCamera() {
        Camera camera;
        try {
            camera = Camera.open();
        } catch (Exception e) {
            camera = null;
        }
        return camera;
    }

    /**
            * 检查是否具有相机功能
    *
            * @param context context
    * @return 是否具有相机功能
    */
    private boolean checkCameraHardware(Context context) {
        return context.getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA);
    }

    /**
     * 在SurfaceView中预览相机内容
     *
     * @param camera camera
     * @param holder SurfaceHolder
     */
    private void setStartPreview(Camera camera, SurfaceHolder holder) {
        try {
            camera.setPreviewDisplay(holder);
            camera.setDisplayOrientation(90);
            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();
    }
}
