package ir.traap.tractor.android.ui.activities.news.details;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wang.avi.AVLoadingIndicatorView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import ir.traap.tractor.android.R;
import ir.traap.tractor.android.apiServices.generator.SingletonService;
import ir.traap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.traap.tractor.android.apiServices.model.WebServiceClass;
import ir.traap.tractor.android.apiServices.model.news.details.getContent.response.GetNewsDetailsResponse;
import ir.traap.tractor.android.conf.TrapConfig;
import ir.traap.tractor.android.models.otherModels.newsModel.NewsDetailsFromRelatedNews;
import ir.traap.tractor.android.models.otherModels.newsModel.NewsDetailsPositionIdsModel;
import ir.traap.tractor.android.ui.activities.news.NewsDetailsAction;
import ir.traap.tractor.android.ui.base.BaseActivity;
import ir.traap.tractor.android.ui.fragments.news.details.contentNews.NewsDetailsContentFragment;
import ir.traap.tractor.android.ui.fragments.news.details.relatedNews.NewsRelatedContentFragment;
import ir.traap.tractor.android.utilities.Logger;
import ir.traap.tractor.android.utilities.Tools;

public class NewsDetailsActivity extends BaseActivity implements OnServiceStatus<WebServiceClass<GetNewsDetailsResponse>>,
        NewsDetailsAction
{
    private Toolbar mToolbar;
    private List<NewsDetailsPositionIdsModel> positionIdsList;
    private Integer currentId = 0, currentPosition = 0;

    private TextView tvPrevNews, tvNextNews;

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

        aviPrev = findViewById(R.id.aviPrev);
        aviNext = findViewById(R.id.aviNext);

//        mToolbar.findViewById(R.id.imgMenu).setOnClickListener(v -> mainView.openDrawerNews());
        mToolbar.findViewById(R.id.imgMenu).setVisibility(View.INVISIBLE);
        mToolbar.findViewById(R.id.imgBack).setOnClickListener(rootView -> finish());
        TextView tvUserName = mToolbar.findViewById(R.id.tvUserName);
        TextView tvTitle = mToolbar.findViewById(R.id.tvTitle);
        tvTitle.setText("جزئیات خبر");
        tvUserName.setText(TrapConfig.HEADER_USER_NAME);

        tvPrevNews = findViewById(R.id.tvPrevNews);
        tvNextNews = findViewById(R.id.tvNextNews);

        fragmentManager = getSupportFragmentManager();

        currentId = getIntent().getIntExtra("currentId", 0);
        currentPosition = getIntent().getIntExtra("currentPosition", 0);
        positionIdsList = getIntent().getParcelableArrayListExtra("positionIdsList");

        getData(currentId);

        tvPrevNews.setOnClickListener(v ->
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

        });

        tvNextNews.setOnClickListener(v ->
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
        tvNextNews.setAlpha(1f);
        tvNextNews.setClickable(true);
        tvNextNews.setEnabled(true);
        tvNextNews.setActivated(true);
    }

    private void disableBtnNext()
    {

        tvNextNews.setAlpha(0.3f);
        tvNextNews.setClickable(false);
        tvNextNews.setEnabled(false);
        tvNextNews.setActivated(false);
    }

    private void enableBtnPrev()
    {
        tvPrevNews.setAlpha(1f);
        tvPrevNews.setClickable(true);
        tvPrevNews.setEnabled(true);
        tvPrevNews.setActivated(true);
    }

    private void disableBtnPrev()
    {
        tvPrevNews.setAlpha(0.3f);
        tvPrevNews.setClickable(false);
        tvPrevNews.setEnabled(false);
        tvPrevNews.setActivated(false);
    }

    @Override
    public void onReady(WebServiceClass<GetNewsDetailsResponse> response)
    {
//            hideLoading();
        hideLoading();

        if (response.info.statusCode != 200)
        {
            showError(this, response.info.message);
        }
        else
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
    }

    @Override
    public void onError(String message)
    {
//            hideLoading();
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
        tvNextNews.setVisibility(View.GONE);

        SingletonService.getInstance().getNewsService().getNewsDetails(nextId, new OnServiceStatus<WebServiceClass<GetNewsDetailsResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<GetNewsDetailsResponse> response)
            {
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
                tvNextNews.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(String message)
            {
                Logger.e("-OnError Next-", "Error: " + message);
                disableBtnNext();
                aviNext.setVisibility(View.GONE);
                tvNextNews.setVisibility(View.VISIBLE);
            }
        });
    }

    private void getPrevData(Integer prevId)
    {
        aviPrev.setVisibility(View.VISIBLE);
        tvPrevNews.setVisibility(View.GONE);

        SingletonService.getInstance().getNewsService().getNewsDetails(prevId, new OnServiceStatus<WebServiceClass<GetNewsDetailsResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<GetNewsDetailsResponse> response)
            {
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
                tvPrevNews.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(String message)
            {
                Logger.e("-OnError Prev-", "Error: " + message);
                disableBtnPrev();
                aviPrev.setVisibility(View.GONE);
                tvPrevNews.setVisibility(View.VISIBLE);
            }
        });

    }

    private void setContentData()
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


//        newsCommentFragment = NewsRelatedContentFragment.newInstance(currentContent.getRelatedNews());
//        transaction = fragmentManager.beginTransaction();
////                        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
//
//        transaction.replace(R.id.newsRelatedContainer, newsRelatedFragment, "newsCommentFragment")
//                .commit();
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
