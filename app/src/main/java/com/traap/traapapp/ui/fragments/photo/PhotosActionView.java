package com.traap.traapapp.ui.fragments.photo;

import com.traap.traapapp.enums.NewsParent;
import com.traap.traapapp.ui.base.BaseView;

public interface PhotosActionView extends BaseView
{
    void backToMainPhotosFragment();

    void onPhotosArchiveFragment();

    void onPhotosFavoriteFragment();

    void openDrawerPhotos();

    void closeDrawerPhotos();

}
