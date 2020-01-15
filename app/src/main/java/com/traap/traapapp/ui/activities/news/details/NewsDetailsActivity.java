package com.traap.traapapp.ui.activities.news.details;

import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.traap.traapapp.models.otherModels.mediaModel.MediaDetailsPositionIdsModel;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.ui.activities.myProfile.MyProfileActivity;
import com.traap.traapapp.ui.fragments.news.details.commentNews.NewsDetailsCommentFragment;
import com.wang.avi.AVLoadingIndicatorView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.news.details.getContent.response.GetNewsDetailsResponse;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.models.otherModels.newsModel.NewsDetailsFromRelatedNews;
import com.traap.traapapp.ui.activities.news.NewsDetailsAction;
import com.traap.traapapp.ui.base.BaseActivity;
import com.traap.traapapp.ui.fragments.news.details.contentNews.NewsDetailsContentFragment;
import com.traap.traapapp.ui.fragments.news.details.relatedNews.NewsRelatedContentFragment;
import com.traap.traapapp.utilities.Logger;
import com.traap.traapapp.utilities.Tools;

public class NewsDetailsActivity extends BaseActivity implements OnServiceStatus<WebServiceClass<GetNewsDetailsResponse>>,
        NewsDetailsAction
{
    private Toolbar mToolbar;
    private View rlShirt;
    private List<MediaDetailsPositionIdsModel> positionIdsList;
    private Integer currentId = 0, currentPosition = 0;

    private NestedScrollView nestedScroll;

    private RelativeLayout rlPrevNews, rlNextNews;

    private Fragment newsDetailFragment, newsRelatedFragment, newsCommentFragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    private AVLoadingIndicatorView aviPrev, aviNext;

    private GetNewsDetailsResponse currentContent, prevContent, nextContent;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        initView();

        EventBus.getDefault().register(this);
    }

    private void initView()
    {
        mToolbar = findViewById(R.id.toolbar);
        rlShirt = findViewById(R.id.rlShirt);
        rlShirt.setOnClickListener(v -> startActivityForResult(new Intent(SingletonContext.getInstance().getContext(), MyProfileActivity.class),100)
        );
        aviPrev = findViewById(R.id.aviPrev);
        aviNext = findViewById(R.id.aviNext);

        nestedScroll = findViewById(R.id.nestedScroll);

        mToolbar.findViewById(R.id.imgMenu).setVisibility(View.INVISIBLE);
        mToolbar.findViewById(R.id.imgBack).setOnClickListener(rootView -> finish());
        TextView tvUserName = mToolbar.findViewById(R.id.tvUserName);
        TextView tvTitle = mToolbar.findViewById(R.id.tvTitle);
        tvTitle.setText("جزئیات خبر");
        tvUserName.setText(TrapConfig.HEADER_USER_NAME);
        FrameLayout flLogoToolbar = mToolbar.findViewById(R.id.flLogoToolbar);
        flLogoToolbar = mToolbar.findViewById(R.id.flLogoToolbar);
        flLogoToolbar.setOnClickListener(v -> {
            Intent returnIntent = new Intent();

            setResult(Activity.RESULT_OK,returnIntent);
            finish();
        });

        mToolbar.findViewById(R.id.rlShirt).setOnClickListener(v -> startActivityForResult(new Intent(SingletonContext.getInstance().getContext(), MyProfileActivity.class),100));

        rlNextNews = findViewById(R.id.rlNextNews);
        rlPrevNews = findViewById(R.id.rlPrevNews);

        fragmentManager = getSupportFragmentManager();

        currentId = getIntent().getIntExtra("currentId", 0);
        currentPosition = getIntent().getIntExtra("currentPosition", 0);
        positionIdsList = getIntent().getParcelableArrayListExtra("positionIdsList");

        getData(currentId);

        rlPrevNews.setOnClickListener(v ->
        {
            if (currentPosition != 0)
            {
                currentPosition--;
                currentId = positionIdsList.get(currentPosition).getId();

                nextContent = currentContent;
                currentContent = prevContent;
                enableBtnNext();
                if (currentPosition == 0)
                {
                    disableBtnPrev();
                }

                getPrevData(currentId); // for set "prevContent" data
                setContentData();
            }
            nestedScroll.smoothScrollTo(0, 0);
        });

        rlNextNews.setOnClickListener(v ->
        {
            if (currentPosition != positionIdsList.size()-1)
            {
                currentPosition++;
                currentId = positionIdsList.get(currentPosition).getId();

                prevContent = currentContent;
                currentContent = nextContent;
                enableBtnPrev();
                if (currentPosition == positionIdsList.size()-1)
                {
                    disableBtnNext();
                }

                getNextData(currentId); // for set "nextContent" data
                setContentData();
            }
            nestedScroll.smoothScrollTo(0, 0);
        });
    }

    private void getData(Integer currentId)
    {
        //show Loading
        showLoading();
        if (currentPosition == 0)
        {
            disableBtnPrev();
        }
        else
        {
            enableBtnPrev();
        }

        if (currentPosition == positionIdsList.size()-1)
        {
            disableBtnNext();
        }
        else
        {
            enableBtnNext();
        }

        SingletonService.getInstance().getNewsService().getNewsDetails(currentId, this);
    }

    private void enableBtnNext()
    {
        rlNextNews.setAlpha(1f);
        rlNextNews.setClickable(true);
        rlNextNews.setEnabled(true);
        rlNextNews.setActivated(true);
    }

    private void disableBtnNext()
    {
        rlNextNews.setAlpha(0.3f);
        rlNextNews.setClickable(false);
        rlNextNews.setEnabled(false);
        rlNextNews.setActivated(false);
    }

    private void enableBtnPrev()
    {
        rlPrevNews.setAlpha(1f);
        rlPrevNews.setClickable(true);
        rlPrevNews.setEnabled(true);
        rlPrevNews.setActivated(true);
    }

    private void disableBtnPrev()
    {
        rlPrevNews.setAlpha(0.3f);
        rlPrevNews.setClickable(false);
        rlPrevNews.setEnabled(false);
        rlPrevNews.setActivated(false);
    }

    @Override
    public void onReady(WebServiceClass<GetNewsDetailsResponse> response)
    {
        try{
            hideLoading();

            if (response.info.statusCode != 200)
            {
                showError(this, response.info.message);
            }
            else
            {
                try
                {
                    currentContent = response.data;
                    setContentData();

                    if (currentPosition > 0)
                    {
                        Integer prevPosition = currentPosition - 1;

                        getPrevData(positionIdsList.get(prevPosition).getId());
                    }
                    if (currentPosition < positionIdsList.size()-1)
                    {
                        Integer nextPosition = currentPosition + 1;
                        getNextData(positionIdsList.get(nextPosition).getId());
                    }
                }
                catch (Exception e)
                {
                    showError(this, "خطای دریافت اطلاعات از سرور!");
                    finish();
                }
            }
        }catch (Exception e){}
//            hideLoading();

    }

    @Override
    public void onError(String message)
    {
        hideLoading();

        if (Tools.isNetworkAvailable(this))
        {
            Logger.e("-OnError Current-", "Error: " + message);
            showError(this, "خطا در دریافت اطلاعات از سرور!");
        }
        else
        {
            showAlert(this, R.string.networkErrorMessage, R.string.networkError);
        }
        finish();
    }

    private void getNextData(Integer nextId)
    {
        aviNext.setVisibility(View.VISIBLE);
        rlNextNews.setVisibility(View.GONE);

        SingletonService.getInstance().getNewsService().getNewsDetails(nextId, new OnServiceStatus<WebServiceClass<GetNewsDetailsResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<GetNewsDetailsResponse> response)
            {
                try{
                    if (response.info.statusCode != 200)
                    {
                        disableBtnNext();
                        Logger.e("-OnFailure Next-", "Error: " + response.info.statusCode + "#" + response.info.message);
                    }
                    else
                    {
                        nextContent = response.data;
                    }
                    aviNext.setVisibility(View.GONE);
                    rlNextNews.setVisibility(View.VISIBLE);
                }catch (Exception e){}

            }

            @Override
            public void onError(String message)
            {
                Logger.e("-OnError Next-", "Error: " + message);
                disableBtnNext();
                aviNext.setVisibility(View.GONE);
                rlNextNews.setVisibility(View.VISIBLE);
            }
        });
    }

    private void getPrevData(Integer prevId)
    {
        aviPrev.setVisibility(View.VISIBLE);
        rlPrevNews.setVisibility(View.GONE);

        SingletonService.getInstance().getNewsService().getNewsDetails(prevId, new OnServiceStatus<WebServiceClass<GetNewsDetailsResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<GetNewsDetailsResponse> response)
            {
                try{
                    if (response.info.statusCode != 200)
                    {
                        disableBtnPrev();
                        Logger.e("-OnFailure Prev-", "Error: " + response.info.statusCode + "#" + response.info.message);
                    }
                    else
                    {
                        prevContent = response.data;
                    }
                    aviPrev.setVisibility(View.GONE);
                    rlPrevNews.setVisibility(View.VISIBLE);
                }catch (Exception e){}

            }

            @Override
            public void onError(String message)
            {
                Logger.e("-OnError Prev-", "Error: " + message);
                disableBtnPrev();
                aviPrev.setVisibility(View.GONE);
                rlPrevNews.setVisibility(View.VISIBLE);
            }
        });

    }

    private void setContentData()
    {
        if (currentContent != null)
        {
            newsDetailFragment = NewsDetailsContentFragment.newInstance(this, currentContent);
            transaction = fragmentManager.beginTransaction();
//                        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);

            transaction.replace(R.id.newsDetailContainer, newsDetailFragment, "newsDetailsContentFragment")
                    .commit();



            newsRelatedFragment = NewsRelatedContentFragment.newInstance(this, currentContent.getRelatedNews());
            transaction = fragmentManager.beginTransaction();
//                        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);

            transaction.replace(R.id.newsRelatedContainer, newsRelatedFragment, "newsRelatedContentFragment")
                    .commit();



            newsCommentFragment = NewsDetailsCommentFragment.newInstance(this, currentContent.getId());
            transaction = fragmentManager.beginTransaction();
//                        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
            transaction.replace(R.id.newsCommentContainer, newsCommentFragment, "newsCommentFragment")
                    .commit();

        }
        else
        {
            showError(this, "خطا در دریافت اطلاعات از سرور!");
            finish();
        }
    }

    @Subscribe
    public void getDataFromRelatedNews(NewsDetailsFromRelatedNews relatedNews)
    {
        currentId = relatedNews.getCurrentId();
        currentPosition = relatedNews.getCurrentPosition();
        positionIdsList = relatedNews.getPositionIdsList();

        //show Loading
        showLoading();

        getData(currentId);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void showLoading()
    {
        findViewById(R.id.rlLoading).setVisibility(View.VISIBLE);
        findViewById(R.id.root).setVisibility(View.GONE);
    }

    @Override
    public void hideLoading()
    {
        findViewById(R.id.rlLoading).setVisibility(View.GONE);
        findViewById(R.id.root).setVisibility(View.VISIBLE);
    }

}
