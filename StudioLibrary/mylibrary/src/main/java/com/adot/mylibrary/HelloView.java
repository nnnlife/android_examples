package com.adot.mylibrary;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

import android.util.AttributeSet;
import androidx.annotation.Nullable;


import com.adot.mylibrary.R;

public class HelloView extends TextView {
    public HelloView(Context context) {
        this(context, null);
    }

    public HelloView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HelloView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public HelloView(Context context, @Nullable AttributeSet attrs, int defStyleAttr,
                     int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setBackgroundColor(Color.BLACK);
        setText(context.getResources().getText(R.string.hello_greeting));
    }
}