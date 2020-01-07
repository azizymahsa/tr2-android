package com.traap.traapapp.ui.fragments.photo.archive;

import com.traap.traapapp.enums.MediaPosition;
import com.traap.traapapp.ui.fragments.photo.PhotosActionView;

public interface PhotosArchiveActionView extends PhotosActionView
{
    void backToMediaFragment(MediaPosition mediaPosition);
}
