package com.traap.traapapp.ui.fragments.news;

import com.traap.traapapp.enums.NewsParent;
import com.traap.traapapp.ui.base.BaseView;

public interface NewsActionView extends BaseView
{
    void backToMainNewsFragment();

    void onNewsArchiveFragment(NewsParent parent);

    void onNewsFavoriteFragment(NewsParent parent);

    void openDrawerNews();

    void closeDrawerNews();

}
