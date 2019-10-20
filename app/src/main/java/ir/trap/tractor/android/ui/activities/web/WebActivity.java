package ir.trap.tractor.android.ui.activities.web;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import ir.trap.tractor.android.R;

public class  WebActivity extends AppCompatActivity {
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        webView = (WebView) findViewById(R.id.webView);
        webView.loadUrl("https://bimeh.com/thirdparty");
    }
}