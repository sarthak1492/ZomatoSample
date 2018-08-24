package com.sarthaksharma.zomato;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Neha on 7/3/2017.
 */

public class MyCustomMTextViewSemiBold extends TextView {
    public MyCustomMTextViewSemiBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "Montserrat-SemiBold.otf"));
    }
}
