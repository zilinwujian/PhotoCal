package com.example.liuxingyu.photocal.controller;

import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by liuxingyu on 17/5/17.
 */

public class FileHandle {

    public static String getFileNameByPath(String FilePath){
        String result="notFile";
        result = FilePath.substring(FilePath.lastIndexOf("/") + 1, FilePath.length()-4).toLowerCase();
        return result;
    }
}
