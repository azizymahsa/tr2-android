package com.traap.traapapp.ui.fragments.photo;

import com.traap.traapapp.enums.SubMediaParent;
import com.traap.traapapp.ui.base.BaseView;

public interface PhotosActionView extends BaseView
{
    void backToMainPhotosFragment();

    void onPhotosArchiveFragment(SubMediaParent parent);

    void onPhotosFavoriteFragment(SubMediaParent parent);

    void openDrawerPhotos();

    void closeDrawerPhotos();

}
