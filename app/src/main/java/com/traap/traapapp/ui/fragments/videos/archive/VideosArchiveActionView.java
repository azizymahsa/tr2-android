package com.traap.traapapp.ui.fragments.videos.archive;

import com.traap.traapapp.enums.MediaPosition;
import com.traap.traapapp.ui.fragments.videos.VideosActionView;

public interface VideosArchiveActionView extends VideosActionView
{
    void backToMediaFragment(MediaPosition mediaPosition);
}
