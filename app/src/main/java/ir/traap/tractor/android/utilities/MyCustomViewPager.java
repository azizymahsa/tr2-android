package ir.traap.tractor.android.utilities;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

public class MyCustomViewPager extends ViewPager
{
    private boolean enableSwipe;

    public MyCustomViewPager(Context context)
    {
        super(context);
        enableSwipe = true;
    }

    public MyCustomViewPager(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        enableSwipe = true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event)
    {
        // Never allow swiping to switch between pages
//        return enableSwipe && super.onInterceptTouchEvent(event);
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        // Never allow swiping to switch between pages
        return enableSwipe && super.onTouchEvent(event);
//        return false;
    }

    public void setEnableSwipe(boolean enableSwipe)
    {
        this.enableSwipe = enableSwipe;
    }


}
