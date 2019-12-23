package com.traap.traapapp.ui.activities.web;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
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


        initView();
        try
        {
            String postData = "";
            webView = findViewById(R.id.webView);
            String url = getIntent().getStringExtra("URL");
            if (getIntent().getStringExtra("Title").contains("بیمه"))
            {
                postData = "userToken=" + URLEncoder.encode(getIntent().getStringExtra("TOKEN"), "UTF-8")
                        + "&businessToken=" + URLEncoder.encode(getIntent().getStringExtra("bimeh_api_key"), "UTF-8")
                        + "&redirectUrl=" + URLEncoder.encode(getIntent().getStringExtra("bimeh_call_back"), "UTF-8")
                ;

            } else if (getIntent().getStringExtra("Title").contains("الو"))
            {

            } else
            {
                postData = "auth=" + URLEncoder.encode(getIntent().getStringExtra("TOKEN"), "UTF-8");

            }


            WebSettings settings = webView.getSettings();
            settings.setJavaScriptEnabled(true);
            settings.setJavaScriptCanOpenWindowsAutomatically(true);
            settings.setDefaultTextEncodingName("utf-8");

            webView.postUrl(url, postData.getBytes());

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
//        catch (NullPointerException e)
//        {
//            e.printStackTrace();
//            hideLoading();
//            showError( this, "خطا در دریافت اطلاعات از سرور!");
//            finish();
//        }
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
}
