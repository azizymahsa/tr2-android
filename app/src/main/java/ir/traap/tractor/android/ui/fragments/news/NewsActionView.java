package ir.traap.tractor.android.ui.fragments.news;

import ir.traap.tractor.android.enums.NewsParent;
import ir.traap.tractor.android.ui.base.BaseView;

public interface NewsActionView extends BaseView
{
    void backToMainNewsFragment();

    void onNewsArchiveFragment(NewsParent parent);

    void openDrawerNews();

    void closeDrawerNews();

}
