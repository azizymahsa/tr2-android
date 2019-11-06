package ir.traap.tractor.android.ui.fragments.payment;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.List;

import ir.traap.tractor.android.R;
import ir.traap.tractor.android.apiServices.model.buyTicket.InfoViewer;
import ir.traap.tractor.android.ui.base.BaseFragment;
import ir.traap.tractor.android.ui.fragments.main.MainActionView;
import ir.traap.tractor.android.ui.fragments.ticket.BuyTickets;
import ir.traap.tractor.android.utilities.Tools;

/**
 * Created by MahtabAzizi on 11/5/2019.
 */
public class WebViewFragment  extends BaseFragment implements  View.OnKeyListener
{
    private View v;
    private WebView webView;
    private String url, webUrl;


    private MainActionView mainView;


    public WebViewFragment()
    {

    }

    public static WebViewFragment newInstance(MainActionView mainView)
    {
        WebViewFragment f = new WebViewFragment();
        f.setMainView(mainView);
        return f;
    }

    public static WebViewFragment newInstance(String tab3, BuyTickets buyTickets, MainActionView mainActionView)
    {

        WebViewFragment f = new WebViewFragment();
        f.setMainView(mainActionView);
        return f;
    }

    private void setMainView(MainActionView mainView)
    {
        this.mainView = mainView;
    }


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message message)
        {
            switch (message.what)
            {
                case 1:
                {
                    webViewGoBack();
                }
                break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        if (v != null)
            return v;
        v = inflater.inflate(R.layout.web_fragment, container, false);
        initView();
        return v;
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initView()
    {
        webView = v.findViewById(R.id.webView);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.getSettings().setJavaScriptEnabled(true);

        webView.setOnKeyListener(this);
        //mainView.showProgressWebView();

        webView.loadUrl(url);


        webView.setWebViewClient(new WebViewClient()
        {

            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String urlNewString)
            {
                if (url.toLowerCase().contains("CancelPayment"))
                    webUrl = url;

                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon)
            {
                Log.e("urlStart", url);
                webUrl = url;


            }

            @Override
            public void onPageFinished(WebView view, String url)
            {
                webUrl = url;
              //  mainView.hideProgressWebView();
                Log.e("url", url);

                if (url.toLowerCase().contains("login"))
                    mainView.onCash();

                if (url.toLowerCase().contains("billlink"))
                {
                    //  mainView.onHome();
                }

            }
        });
    }



    @Override
    public void onDestroy()
    {
        super.onDestroy();

    }

    @Override
    public void onStop()
    {
        super.onStop();
        v = null;
    }

    private void webViewGoBack()
    {
        webView.goBack();
    }

    @Override
    public boolean onKey(View view, int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && webUrl.toLowerCase().contains("CancelPayment"))
        {
            mainView.onCash();
           // mainView.onError("خطا در پرداخت", this.getClass().getSimpleName(), DibaConfig.showClassNameInException);

            Tools.showToast(getContext(),"خطا در پرداخت",R.color.red);
            return false;
        }

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == MotionEvent.ACTION_UP
                && webView.canGoBack())
        {

            if (webUrl.toLowerCase().contains("CancelPayment"))

                handler.sendEmptyMessage(1);
            return true;

        }

        return false;
    }
    public void webUrl(String url)
    {
        this.url = url;
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient()
        {

            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String urlNewString)
            {
                if (url.toLowerCase().contains("CancelPayment"))
                    webUrl = url;

                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon)
            {
                Log.e("urlStart", url);
                webUrl = url;


            }

            @Override
            public void onPageFinished(WebView view, String url)
            {
                webUrl = url;
                //  mainView.hideProgressWebView();
                Log.e("url", url);

                if (url.toLowerCase().contains("login"))
                    mainView.onCash();

                if (url.toLowerCase().contains("billlink"))
                {
                    //  mainView.onHome();
                }

            }
        });
    }

}
