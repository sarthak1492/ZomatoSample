package com.sarthaksharma.zomato;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by Sarthak.Sharma on 12-10-2017.
 */

public class MyCustomMButtonRegular extends Button {
    public MyCustomMButtonRegular(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "Montserrat-Regular.otf"));
    }
}
