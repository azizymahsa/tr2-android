package com.traap.traapapp.ui.activities.web;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.pixplicity.easyprefs.library.Prefs;

import com.traap.traapapp.R;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.ui.activities.userProfile.UserProfileActivity;
import com.traap.traapapp.ui.base.BaseActivity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class WebActivity extends BaseActivity
{
    private WebView webView;

    private Toolbar mToolbar;
    private TextView tvTitle, tvUserName, tvPopularPlayer;
    private View imgBack, imgMenu;
    private View rlShirt;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

       /* mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        tvTitle = mToolbar.findViewById(R.id.tvTitle);
        tvUserName = mToolbar.findViewById(R.id.tvUserName);
        imgBack = mToolbar.findViewById(R.id.imgBack);
        imgMenu = mToolbar.findViewById(R.id.imgMenu);

        tvTitle.setText("مشاهده تغییرات");

        tvUserName.setText(Prefs.getString("mobile", ""));

        imgMenu.setVisibility(View.GONE);
        imgBack.setOnClickListener(v ->
        {
            super.onBackPressed();
        });*/

        initView();
        try
        {
            webView = findViewById(R.id.webView);
            String url = getIntent().getStringExtra("URL");
            String authToken = getIntent().getStringExtra("TOKEN");

            String postData = "auth=" + URLEncoder.encode(authToken, "UTF-8");

            WebSettings settings = webView.getSettings();
            settings.setJavaScriptEnabled(true);
            settings.setJavaScriptCanOpenWindowsAutomatically(true);
            settings.setDefaultTextEncodingName("utf-8");
            webView.getSettings().setDomStorageEnabled(true);
            webView.getSettings().setSaveFormData(true);
            webView.getSettings().setAllowContentAccess(true);
            webView.getSettings().setAllowFileAccess(true);
            webView.getSettings().setAllowFileAccessFromFileURLs(true);
            webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
            webView.getSettings().setSupportZoom(true);
            webView.setWebViewClient(new WebViewClient());
            webView.setClickable(true);
            webView.setWebChromeClient(new WebChromeClient());
            webView.postUrl(url, postData.getBytes());


        } catch (
                UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
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
            tvTitle.setText("گردشگری");

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
}
