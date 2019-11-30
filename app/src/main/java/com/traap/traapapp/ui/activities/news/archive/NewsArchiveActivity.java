package com.traap.traapapp.ui.activities.news.archive;

import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.traap.traapapp.R;
import com.traap.traapapp.ui.base.BaseActivity;

public class NewsArchiveActivity extends BaseActivity
{
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_archive);
    }
}
