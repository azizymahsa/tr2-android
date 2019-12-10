package com.traap.traapapp.utilities.font;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.traap.traapapp.R;


/**
 * Created by JavadAbadi on 7/2/2018.
 */

@SuppressLint("AppCompatCustomView")
public class TextField extends TextView {

    public TextField(final Context context, final AttributeSet attrs) {
        super(context, attrs, R.attr.fontPathCalligraphyConfig);
    }

}
