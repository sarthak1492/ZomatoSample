package com.sarthaksharma.zomato;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by Neha on 7/6/2017.
 */

public class MyCustomMButtonSemiBold extends Button {

    public MyCustomMButtonSemiBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "Montserrat-SemiBold.otf"));
    }
}
