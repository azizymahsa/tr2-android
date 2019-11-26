package ir.traap.tractor.android.ui.activities.news.archive;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import ir.traap.tractor.android.R;
import ir.traap.tractor.android.ui.base.BaseActivity;

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
