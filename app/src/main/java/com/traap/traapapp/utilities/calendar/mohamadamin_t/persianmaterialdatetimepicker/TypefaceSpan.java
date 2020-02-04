package com.traap.traapapp.utilities.calendar.mohamadamin_t.persianmaterialdatetimepicker;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import androidx.collection.LruCache;

/**
 * Created by Amir H on 2/21/2015.
 */
public class TypefaceSpan extends MetricAffectingSpan {
    /** An <code>LruCache</code> for previously loaded typefaces. */
    private static LruCache<String, Typeface> sTypefaceCache =
            new LruCache<String, Typeface>(12);

    private Typeface mTypeface;
    private DisplayMetrics dm;
    private int size = 14;

    /**
     * Load the {@link Typeface} and apply to a {@link android.text.Spannable}.
     */
    public TypefaceSpan(Context context, String typefaceName, int size) {
        mTypeface = sTypefaceCache.get(typefaceName);
        dm = context.getApplicationContext().getResources().getDisplayMetrics();
        if (mTypeface == null) {
            mTypeface = Typeface.createFromAsset(context.getApplicationContext()
                    .getAssets(), String.format("fonts/%s", typefaceName));

            // Cache the loaded Typeface
            sTypefaceCache.put(typefaceName, mTypeface);
        }
        this.size = size;
    }

    @Override
    public void updateMeasureState(TextPaint p) {
        p.setTypeface(mTypeface);
        p.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                size, dm));

        // Note: This flag is required for proper typeface rendering
        p.setFlags(p.getFlags() | Paint.SUBPIXEL_TEXT_FLAG);
    }

    @Override
    public void updateDrawState(TextPaint tp) {
        tp.setTypeface(mTypeface);
        tp.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                size, dm));
        // Note: This flag is required for proper typeface rendering
        tp.setFlags(tp.getFlags() | Paint.SUBPIXEL_TEXT_FLAG);
    }
}