package com.traap.traapapp.ui.activities.web;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.pixplicity.easyprefs.library.Prefs;

import com.traap.traapapp.R;

public class WebActivity extends AppCompatActivity
{
    private WebView webView;
    private Toolbar mToolbar;

    private TextView tvTitle, tvUserName;
    private ImageView imgBack, imgMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        mToolbar = findViewById(R.id.toolbar);
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
        });



        webView = findViewById(R.id.webView);

        html(getIntent().getStringExtra("description"));
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void html(String html)
    {


        WebSettings settings = webView.getSettings();
        settings.setDefaultTextEncodingName("utf-8");

        webView.loadData(html, "text/html; charset=utf-8", "utf-8");
    }
}
