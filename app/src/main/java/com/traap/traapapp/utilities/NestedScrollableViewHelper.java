package com.traap.traapapp.utilities;

import android.view.View;

import androidx.core.widget.NestedScrollView;

import com.sothree.slidinguppanel.ScrollableViewHelper;

public class NestedScrollableViewHelper extends ScrollableViewHelper
{
    public int getScrollableViewScrollPosition(View mScrollableView, boolean isSlidingUp)
    {
        if (mScrollableView instanceof NestedScrollView)
        {
            if (isSlidingUp)
            {
                return mScrollableView.getScrollY();
            }
            else
            {
                NestedScrollView nsv = ((NestedScrollView) mScrollableView);
                View child = nsv.getChildAt(0);
                return (child.getBottom() - (nsv.getHeight() + nsv.getScrollY()));
            }
        } else
        {
            return 0;
        }
    }
}
