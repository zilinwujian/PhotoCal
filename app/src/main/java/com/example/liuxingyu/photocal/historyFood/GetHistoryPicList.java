package com.example.liuxingyu.photocal.historyFood;

import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuxingyu on 17/5/14.
 */

public class GetHistoryPicList {

    // 从sd卡获取图片资源
    public List<String> getImagePathFromSD() {
        // 图片列表
        List<String> picList = new ArrayList<String>();
        // 得到sd卡内路径
        String imagePath = Environment.getExternalStorageDirectory().toString() + "/picture";

        // 得到该路径文件夹下所有的文件
        File mfile = new File(imagePath);
        File[] files = mfile.listFiles();

        // 将所有的文件存入ArrayList中,并过滤所有图片格式的文件
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            if (checkIsImageFile(file.getPath())) {
                picList.add(file.getPath());
            }
        }
        // 返回得到的图片列表
        return picList;

    }

    // 检查扩展名，得到图片格式的文件
    private boolean checkIsImageFile(String fName) {
        boolean isImageFile = false;
        // 获取扩展名
        String FileEnd = fName.substring(fName.lastIndexOf(".") + 1, fName.length()).toLowerCase();
//        if (FileEnd.equals("jpg") || FileEnd.equals("gif") || FileEnd.equals("png") || FileEnd.equals("jpeg")
//                || FileEnd.equals("bmp")) {
        //仅仅考虑png的情况
        if(FileEnd.equals("png")){
            isImageFile = true;
        } else {
            isImageFile = false;
        }
        return isImageFile;
    }

}
