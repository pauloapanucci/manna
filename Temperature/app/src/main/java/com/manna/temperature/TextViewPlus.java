package com.manna.temperature;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by admin on 30/08/2017.
 */

public class TextViewPlus extends android.support.v7.widget.AppCompatTextView {
    public TextViewPlus(Context context) {
        super(context);
    }

    public TextViewPlus(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        CustomFontHelper.setCustomFont(this, context, attrs);
    }

    public TextViewPlus(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        CustomFontHelper.setCustomFont(this, context, attrs);
    }

}
