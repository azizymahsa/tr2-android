package com.traap.traapapp.ui.fragments.webView;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.pixplicity.easyprefs.library.Prefs;
import com.traap.traapapp.R;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.ui.activities.myProfile.MyProfileActivity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.DOWNLOAD_SERVICE;

/**
 * Created by MahsaAzizi .
 */
@SuppressLint("ValidFragment")
public class WebFragment extends BaseFragment
{

    private MainActionView mainView;
    private View view;
    // private View view;
    private Toolbar mToolbar;
    private TextView tvTitle, tvUserName, tvPopularPlayer;
    private View imgBack, imgMenu;
    private WebView webView;
    private String URL = "";
    private String Token = "";
    private View rlShirt;
    private String title;

    public WebFragment()
    {

    }


    public static WebFragment newInstance(MainActionView mainView, String URL, String Token, String title)
    {
        WebFragment fragment = new WebFragment();
        fragment.setMainView(mainView, URL, Token,title);
        return fragment;
    }

    private void setMainView(MainActionView mainView, String URL, String Token, String title)
    {
        this.mainView = mainView;
        this.URL = URL;
        this.Token = Token;
        this.title = title;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        YoYo.with(Techniques.FadeIn)
                .duration(700)
                .playOn(view);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        if (view != null)
            return view;
        view = inflater.inflate(R.layout.activity_web, container, false);


        initView();
        try
        {


            String postData = "auth=" + URLEncoder.encode(Token, "UTF-8");
            webView = view.findViewById(R.id.webView);
            WebSettings settings = webView.getSettings();
            settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
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
          //  webView.postUrl(URL, postData.getBytes());
            Map<String, String> extraHeaders = new HashMap<String, String>();
            extraHeaders.put("Authorization",Prefs.getString("accessToken",""));
            webView.loadUrl(URL,extraHeaders);
            System.out.println("URLGDSSSSSSSSSSS: "+URL);
            System.out.println("URLGDSSSSSSSSSSSTOKEN : "+Prefs.getString("accessToken",""));
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
                    mainView.showLoading();

                }

                @Override
                public void onPageCommitVisible(WebView view, String url)
                {
                    super.onPageCommitVisible(view, url);
                    mainView.hideLoading();

                }

                @Override
                public void onPageFinished(WebView view, String url)
                {

                    mainView.hideLoading();

                    super.onPageFinished(view, url);

                }
            });

        } catch (
                UnsupportedEncodingException e)
        {
            e.printStackTrace();
            mainView.hideLoading();

        }

        webView.setDownloadListener(new DownloadListener() {

            @Override
            public void onDownloadStart(String url, String userAgent,
                                        String contentDisposition, String mimetype,
                                        long contentLength) {
                Log.e("test11222333", url );
                /*    if (url.contains(".pdf")){
                        Utility.openUrlCustomTab(WebActivity.this,"docs.google.com/viewer?url="+url.replace("https://",""));
                    }*/

                try {
                    Intent i = new Intent("android.intent.action.MAIN");
                    i.setComponent(ComponentName.unflattenFromString("com.android.chrome/com.android.chrome.Main"));
                    i.addCategory("android.intent.category.LAUNCHER");
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }
                catch(ActivityNotFoundException e) {
                    // Chrome is not installed
                    DownloadManager.Request request = new DownloadManager.Request(
                            Uri.parse(url));

                    request.allowScanningByMediaScanner();
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED); //Notify client once download is completed!
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "Name of your downloadble file goes here, example: Mathematics II ");
                    DownloadManager dm = (DownloadManager) getActivity().getSystemService(DOWNLOAD_SERVICE);
                    dm.enqueue(request);
                    Toast.makeText(getActivity().getApplicationContext(), "Downloading File", //To notify the Client that the file is being downloaded
                            Toast.LENGTH_LONG).show();
                }
                   /* DownloadManager.Request request = new DownloadManager.Request(
                            Uri.parse(url));

                    request.allowScanningByMediaScanner();
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED); //Notify client once download is completed!
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "Name of your downloadble file goes here, example: Mathematics II ");
                    DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                    dm.enqueue(request);
                    Toast.makeText(getApplicationContext(), "Downloading File", //To notify the Client that the file is being downloaded
                            Toast.LENGTH_LONG).show();*/

            }
        });
        return view;
    }

    private void initView()
    {
        try
        {
            //toolbar
            mToolbar = view.findViewById(R.id.toolbar);
            rlShirt = view.findViewById(R.id.rlShirt);
            rlShirt.setOnClickListener(v -> startActivityForResult(new Intent(SingletonContext.getInstance().getContext(), MyProfileActivity.class),100));
            tvUserName = mToolbar.findViewById(R.id.tvUserName);
            TextView tvTitle = mToolbar.findViewById(R.id.tvTitle);
            tvTitle.setText(title);
            mToolbar.findViewById(R.id.imgBack).setOnClickListener(v -> mainView.backToMainFragment());

            tvUserName.setText(TrapConfig.HEADER_USER_NAME);

            mToolbar.findViewById(R.id.imgMenu).setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    mainView.openDrawer();
                }
            });

            imgMenu = view.findViewById(R.id.imgMenu);

            imgMenu.setOnClickListener(v -> mainView.openDrawer());
            imgBack = view.findViewById(R.id.imgBack);
            mToolbar.findViewById(R.id.imgBack).setOnClickListener(rootView -> mainView.backToMainFragment());


            tvPopularPlayer = mToolbar.findViewById(R.id.tvPopularPlayer);
            tvPopularPlayer.setText(String.valueOf(Prefs.getInt("popularPlayer", 12)));
        } catch (Exception e)
        {
            e.getMessage();
        }


        FrameLayout flLogoToolbar = mToolbar.findViewById(R.id.flLogoToolbar);
        flLogoToolbar.setOnClickListener(v -> {
            mainView.backToMainFragment();

        });




    }






   /* @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {



            case R.id.tvMyFavoritePhoto:
                Intent intent1 = new Intent(getActivity(), PhotoArchiveActivity.class);
                intent1.putExtra("FLAG_Favorite", true);
                startActivityForResult(intent1);
                break;
        }
    }*/


}
