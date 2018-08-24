package com.sarthaksharma.zomato;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Neha on 9/11/2017.
 */

public class MyCustomOSTextViewItalic extends TextView {
    public MyCustomOSTextViewItalic(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "OpenSans-Italic.ttf"));
    }
}
