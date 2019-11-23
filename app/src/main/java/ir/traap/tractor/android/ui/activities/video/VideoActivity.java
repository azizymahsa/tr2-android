package ir.traap.tractor.android.ui.activities.video;

import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;


import ir.traap.tractor.android.R;
import ir.traap.tractor.android.ui.base.BaseActivity;
import ir.traap.tractor.android.utilities.Utility;

public class VideoActivity extends BaseActivity
{

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        webView = findViewById(R.id.webView);

        Utility.openUrlCustomTab(this,"https://www.aparat.com/video/video/embed/videohash/rzKus/vt/frame");

    }

}
