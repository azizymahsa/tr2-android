package ir.trap.tractor.android.ui.activities.web;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;

import ir.trap.tractor.android.R;

public class WebActivity extends AppCompatActivity
{
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        try {
            Intent intent = getIntent();
            String url=intent.getStringExtra("URL");
            Uri uri = Uri.parse(url);
            CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();
            intentBuilder.setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary));
            intentBuilder.setSecondaryToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary));
            CustomTabsIntent customTabsIntent = intentBuilder.build();
            customTabsIntent.launchUrl(this, uri);

        } catch (Exception e) {
        }
    }
        /*webView = (WebView) findViewById(R.id.webView);
        try
        {
            Intent intent = getIntent();
            webView.loadUrl(intent.getStringExtra("URL"));
        } catch (Exception e)
        {
            e.getMessage();
        }*/
    }
