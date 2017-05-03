package com.example.liuxingyu.photocal.controller;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.example.liuxingyu.photocal.R;

/**
 * Created by liuxingyu on 17/4/19.
 */

public class TitleLayout extends LinearLayout {

    public TitleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.head, this);
    }
}
