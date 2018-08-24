package com.sarthaksharma.zomato;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Sarthak.Sharma on 6/30/2017.
 */

public class MyCustomOSTextViewSemiBold extends TextView {

    public MyCustomOSTextViewSemiBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "OpenSans-Semibold.ttf"));
    }
}
