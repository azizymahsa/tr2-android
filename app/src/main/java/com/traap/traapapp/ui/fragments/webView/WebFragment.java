package com.traap.traapapp.ui.fragments.webView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.moeidbannerlibrary.banner.BannerLayout;
import com.makeramen.roundedimageview.RoundedImageView;
import com.pixplicity.easyprefs.library.Prefs;
import com.squareup.picasso.Picasso;
import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.categoryByIdVideo.CategoryByIdVideosRequest;
import com.traap.traapapp.apiServices.model.categoryByIdVideo.CategoryByIdVideosResponse;
import com.traap.traapapp.apiServices.model.mainVideos.Category;
import com.traap.traapapp.apiServices.model.mainVideos.ListCategory;
import com.traap.traapapp.apiServices.model.mainVideos.MainVideoRequest;
import com.traap.traapapp.apiServices.model.mainVideos.MainVideosResponse;
import com.traap.traapapp.apiServices.model.photo.response.ImageName;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.ui.activities.photo.AlbumDetailActivity;
import com.traap.traapapp.ui.activities.photo.PhotoArchiveActivity;
import com.traap.traapapp.ui.adapters.photo.CategoryPhotosAdapter;
import com.traap.traapapp.ui.adapters.photo.NewestPhotosAdapter;
import com.traap.traapapp.ui.adapters.photo.PhotosArchiveAdapter;
import com.traap.traapapp.ui.adapters.photo.PhotosCategoryTitleAdapter;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.utilities.Tools;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by MahsaAzizi .
 */
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

    public WebFragment()
    {

    }


    public static WebFragment newInstance(MainActionView mainView, String URL, String Token)
    {
        WebFragment fragment = new WebFragment();
        fragment.setMainView(mainView, URL, Token);
        return fragment;
    }

    private void setMainView(MainActionView mainView, String URL, String Token)
    {
        this.mainView = mainView;
        this.URL = URL;
        this.Token = Token;
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


        mainView.showLoading();

        initView();
        try
        {


            String postData = "auth=" + URLEncoder.encode(Token, "UTF-8");
            webView = view.findViewById(R.id.webView);
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
            webView.postUrl(URL, postData.getBytes());
            mainView.hideLoading();


        } catch (
                UnsupportedEncodingException e)
        {
            e.printStackTrace();
            mainView.hideLoading();

        }
        return view;
    }

    private void initView()
    {
        try
        {


            //toolbar
            mToolbar = view.findViewById(R.id.toolbar);
            tvUserName = mToolbar.findViewById(R.id.tvUserName);
            TextView tvTitle = mToolbar.findViewById(R.id.tvTitle);
            tvTitle.setText("گردشگری");
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
    }






   /* @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {



            case R.id.tvMyFavoritePhoto:
                Intent intent1 = new Intent(getActivity(), PhotoArchiveActivity.class);
                intent1.putExtra("FLAG_Favorite", true);
                startActivity(intent1);
                break;
        }
    }*/


}