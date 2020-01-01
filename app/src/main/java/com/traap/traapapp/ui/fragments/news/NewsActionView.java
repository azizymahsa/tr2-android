package com.traap.traapapp.ui.fragments.news;

import com.traap.traapapp.enums.SubMediaParent;
import com.traap.traapapp.ui.base.BaseView;

public interface NewsActionView extends BaseView
{
    void backToMainNewsFragment();

    void onNewsArchiveFragment(SubMediaParent parent);

    void onNewsFavoriteFragment(SubMediaParent parent);

    void openDrawerNews();

    void closeDrawerNews();

}
