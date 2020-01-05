package com.traap.traapapp.ui.fragments.videos;

import com.traap.traapapp.enums.SubMediaParent;
import com.traap.traapapp.ui.base.BaseView;

public interface VideosActionView extends BaseView
{
    void backToMainVideosFragment();

    void onVideosArchiveFragment(SubMediaParent parent);

    void onVideosFavoriteFragment(SubMediaParent parent);

    void openDrawerVideos();

    void closeDrawerVideos();

}
