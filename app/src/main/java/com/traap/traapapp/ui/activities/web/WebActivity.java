package com.traap.traapapp.ui.activities.web;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.pixplicity.easyprefs.library.Prefs;

import com.traap.traapapp.R;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.ui.base.BaseActivity;
import com.traap.traapapp.ui.activities.myProfile.MyProfileActivity;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;

public class WebActivity extends BaseActivity
{
    private WebView webView;

    private Toolbar mToolbar;
    private TextView tvTitle, tvUserName, tvPopularPlayer;
    private View imgBack, imgMenu;
    private View rlShirt;
    private String TAG = "WebActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        initView();
        try
        {
            String postData = "";
            webView = findViewById(R.id.webView);
            if (Build.VERSION.SDK_INT >= 19)
            {
                webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            } else
            {
                webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            }


            String url = getIntent().getStringExtra("URL");
            if (getIntent().getStringExtra("Title").contains("بیمه"))
            {
                postData = "userToken=" + URLEncoder.encode(getIntent().getStringExtra("TOKEN"), "UTF-8")
                        + "&businessToken=" + URLEncoder.encode(getIntent().getStringExtra("bimeh_api_key"), "UTF-8")
                        + "&redirectUrl=" + URLEncoder.encode(getIntent().getStringExtra("bimeh_call_back"), "UTF-8")
                        + "&redirectSuccess=" + URLEncoder.encode(url, "UTF-8")
                ;
                url = getIntent().getStringExtra("bimeh_base_url");
            } else if (getIntent().getStringExtra("Title").contains("الو"))
            {

            } else
            {
                postData = "auth=" + URLEncoder.encode(getIntent().getStringExtra("TOKEN"), "UTF-8");

            }
            webView.setInitialScale(0);
            webView.setVerticalScrollBarEnabled(false);

            webView.postUrl(url, postData.getBytes());

            WebSettings settings = webView.getSettings();
            initWebViewSettings();
            // Enable AppCache
            // Fix for CB-2282
            webView.getSettings().setAppCacheMaxSize(5 * 1048576);
            //   settings.setAppCachePath(databasePath);
            webView.getSettings().setAppCacheEnabled(true);

            // Enable responsive layout
            webView.getSettings().setUseWideViewPort(true);
            // Zoom out if the content width is greater than the width of the viewport
            webView.getSettings().setLoadWithOverviewMode(true);
            webView.setWebViewClient(new WebViewClient()
            {

                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon)
                {
                    super.onPageStarted(view, url, favicon);
                    showLoading();

                }

                @Override
                public void onPageCommitVisible(WebView view, String url)
                {
                    super.onPageCommitVisible(view, url);
                    hideLoading();

                }

                @Override
                public void onPageFinished(WebView view, String url)
                {

                    hideLoading();

                    super.onPageFinished(view, url);

                }

            });

        }

        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
            hideLoading();
        } catch (Exception e)
        {
            e.printStackTrace();
            hideLoading();
            showError(this, "خطا در دریافت اطلاعات از سرور!");
            finish();
        }
    }

    public void showLoading()
    {
        findViewById(R.id.rlLoading).setVisibility(View.VISIBLE);
    }

    public void hideLoading()
    {
        findViewById(R.id.rlLoading).setVisibility(View.GONE);
    }

    private void initView()
    {
        try
        {
            //toolbar
            tvTitle = findViewById(R.id.tvTitle);


            tvUserName = findViewById(R.id.tvUserName);
            tvUserName.setText(TrapConfig.HEADER_USER_NAME);

            imgMenu = findViewById(R.id.imgMenu);
            imgMenu.setVisibility(View.GONE);

            tvPopularPlayer = findViewById(R.id.tvPopularPlayer);
            tvPopularPlayer.setText(Prefs.getString("PopularPlayer", "12"));

            imgBack = findViewById(R.id.imgBack);
            imgBack.setOnClickListener(v ->
            {
                finish();
            });
            rlShirt = findViewById(R.id.rlShirt);
            rlShirt.setOnClickListener(v -> startActivity(new Intent(SingletonContext.getInstance().getContext(), MyProfileActivity.class))
            );
            String title = getIntent().getStringExtra("Title");

            tvTitle.setText(title);

        } catch (Exception e)
        {
            e.getMessage();
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void html(String html)
    {


        WebSettings settings = webView.getSettings();
        settings.setDefaultTextEncodingName("utf-8");

        webView.loadData(html, "text/html; charset=utf-8", "utf-8");
    }

    @SuppressLint({"NewApi", "SetJavaScriptEnabled"})
    @SuppressWarnings("deprecation")
    private void initWebViewSettings()
    {
        webView.setInitialScale(0);
        webView.setVerticalScrollBarEnabled(false);
        // Enable JavaScript
        final WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);

        // Set the nav dump for HTC 2.x devices (disabling for ICS, deprecated entirely for Jellybean 4.2)
        try
        {
            Method gingerbread_getMethod = WebSettings.class.getMethod("setNavDump", new Class[]{boolean.class});

            String manufacturer = android.os.Build.MANUFACTURER;
            // Log.d(TAG, "CordovaWebView is running on device made by: " + manufacturer);
            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB &&
                    android.os.Build.MANUFACTURER.contains("HTC"))
            {
                gingerbread_getMethod.invoke(settings, true);
            }
        } catch (NoSuchMethodException e)
        {
            Log.d(TAG, "We are on a modern version of Android, we will deprecate HTC 2.3 devices in 2.8");
        } catch (IllegalArgumentException e)
        {
            Log.d(TAG, "Doing the NavDump failed with bad arguments");
        } catch (IllegalAccessException e)
        {
            Log.d(TAG, "This should never happen: IllegalAccessException means this isn't Android anymore");
        } catch (InvocationTargetException e)
        {
            Log.d(TAG, "This should never happen: InvocationTargetException means this isn't Android anymore.");
        }

        //We don't save any form data in the application
        settings.setSaveFormData(false);
        settings.setSavePassword(false);

        // Jellybean rightfully tried to lock this down. Too bad they didn't give us a whitelist
        // while we do this
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN)
        {
            settings.setAllowUniversalAccessFromFileURLs(true);
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1)
        {
            settings.setMediaPlaybackRequiresUserGesture(false);
        }
        // Enable database
        // We keep this disabled because we use or shim to get around DOM_EXCEPTION_ERROR_16
        String databasePath = webView.getContext().getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
        settings.setDatabaseEnabled(true);
        settings.setDatabasePath(databasePath);


        //Determine whether we're in debug or release mode, and turn on Debugging!
        ApplicationInfo appInfo = webView.getContext().getApplicationContext().getApplicationInfo();
        if ((appInfo.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0 &&
                android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT)
        {
            // enableRemoteDebugging();
        }

        settings.setGeolocationDatabasePath(databasePath);

        // Enable DOM storage
        settings.setDomStorageEnabled(true);

        // Enable built-in geolocation
        settings.setGeolocationEnabled(true);

        // Enable AppCache
        // Fix for CB-2282
        settings.setAppCacheMaxSize(5 * 1048576);
        settings.setAppCachePath(databasePath);
        settings.setAppCacheEnabled(true);

        // Fix for CB-1405
        // Google issue 4641
        String defaultUserAgent = settings.getUserAgentString();


        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_CONFIGURATION_CHANGED);

    }
}
