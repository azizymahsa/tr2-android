package com.traap.traapapp.utilities;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;

import androidx.annotation.NonNull;

import com.traap.traapapp.R;

import java.util.Objects;

import ru.kolotnev.formattedittext.MaskedEditText;


/**
 * To clear icon can be changed via
 *
 * <pre>
 * android:drawable(Right|Left)="@drawable/custom_icon"
 * </pre>
 */
public class ClearableEditText extends MaskedEditText implements OnTouchListener, OnFocusChangeListener,
        TextWatcherAdapter.TextWatcherListener
{

    public ClearableEditText(Context context)
    {
        super(context);
    }

    public ClearableEditText(Context context, String mask)
    {
        super(context, mask);
        init();
    }

    public ClearableEditText(Context context, String mask, char placeholder)
    {
        super(context, mask, placeholder);
        init();
    }

    public ClearableEditText(Context context, AttributeSet attr)
    {
        super(context, attr);
        init();
    }

    public ClearableEditText(Context context, AttributeSet attr, String mask)
    {
        super(context, attr, mask);
        init();
    }

    public ClearableEditText(Context context, AttributeSet attr, @NonNull String mask, char placeholder)
    {
        super(context, attr, mask, placeholder);
        init();
    }

    @Override
    public void onTextChanged(MaskedEditText view, String text)
    {
        if (isFocused())
        {
            setClearIconVisible(!TextUtils.isEmpty(text.replaceAll("-", "").replaceAll("_", "")));
        }
    }


    public static enum Location
    {
        LEFT(0), RIGHT(2);

        final int idx;

        private Location(int idx)
        {
            this.idx = idx;
        }
    }

    public interface Listener
    {
        void didClearText();
    }


    public void setListener(Listener listener)
    {
        this.listener = listener;
    }

    /**
     * null disables the icon
     */
    public void setIconLocation(Location loc)
    {
        this.loc = loc;
        initIcon();
    }

    @Override
    public void setOnTouchListener(OnTouchListener l)
    {
        this.l = l;
    }

    @Override
    public void setOnFocusChangeListener(OnFocusChangeListener f)
    {
        this.f = f;
    }

    private Location loc = Location.LEFT;

    private Drawable xD;
    private Listener listener;

    private OnTouchListener l;
    private OnFocusChangeListener f;

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        if (getDisplayedDrawable() != null)
        {
            int x = (int) event.getX();
            int y = (int) event.getY();
            int left = (loc == Location.LEFT) ? 0 : getWidth() - getPaddingRight() - xD.getIntrinsicWidth();
            int right = (loc == Location.LEFT) ? getPaddingLeft() + xD.getIntrinsicWidth() : getWidth();
            boolean tappedX = x >= left && x <= right && y >= 0 && y <= (getBottom() - getTop());
            if (tappedX)
            {
                if (event.getAction() == MotionEvent.ACTION_UP)
                {
                    setText("");
                    if (listener != null)
                    {
                        listener.didClearText();
                    }
                }
                return true;
            }
        }
//            if (l != null)
//            {
//                return l.onTouch(v, event);
//            }
        return false;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus)
    {
        if (hasFocus)
        {
            setClearIconVisible(!TextUtils.isEmpty(getText().toString().replaceAll("-", "").replaceAll("_", "")));
        } else
        {
            setClearIconVisible(false);
        }
        if (f != null)
        {
            f.onFocusChange(v, hasFocus);
        }
    }


    @Override
    public void setCompoundDrawables(Drawable left, Drawable top, Drawable right, Drawable bottom)
    {
        super.setCompoundDrawables(left, top, right, bottom);
        initIcon();
    }

    private void init()
    {
        super.setOnTouchListener(this);
        super.setOnFocusChangeListener(this);
        addTextChangedListener(new TextWatcherAdapter(this, this));
        initIcon();
        setClearIconVisible(false);
        setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});


    }

    public void setLength(int length)
    {
        setFilters(new InputFilter[]{new InputFilter.LengthFilter(length)});

    }

    private void initIcon()
    {
        xD = null;
        if (loc != null)
        {
            xD = getCompoundDrawables()[loc.idx];
        }
        if (xD == null)
        {
            xD = getResources().getDrawable(R.drawable.ic_clear);
            xD.setColorFilter(getResources().getColor(R.color.textColorPrimary), PorterDuff.Mode.MULTIPLY);
        }
        xD.setBounds(0, 0, xD.getIntrinsicWidth(), xD.getIntrinsicHeight());
        int min = getPaddingTop() + xD.getIntrinsicHeight() + getPaddingBottom();
        if (getSuggestedMinimumHeight() < min)
        {
            setMinimumHeight(min);
        }
    }

    private Drawable getDisplayedDrawable()
    {
        return (loc != null) ? getCompoundDrawables()[loc.idx] : null;
    }

    protected void setClearIconVisible(boolean visible)
    {
        Drawable[] cd = getCompoundDrawables();
        Drawable displayed = getDisplayedDrawable();
        boolean wasVisible = (displayed != null);
        if (visible != wasVisible)
        {
            Drawable x = visible ? xD : null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            {
//                Objects.requireNonNull(x).setTint(getResources().getColor(R.color.clearButtonColor));
            }
            super.setCompoundDrawables((loc == Location.LEFT) ? x : cd[0], cd[1], (loc == Location.RIGHT) ? x : cd[2],
                    cd[3]);
        }
    }
}