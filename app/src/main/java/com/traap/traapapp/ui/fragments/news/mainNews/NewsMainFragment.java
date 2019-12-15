package com.traap.traapapp.ui.fragments.news.mainNews;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.pixplicity.easyprefs.library.Prefs;
import com.traap.traapapp.R;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.enums.NewsParent;
import com.traap.traapapp.models.otherModels.headerModel.HeaderModel;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.ui.activities.main.MainActivity;
import com.traap.traapapp.ui.activities.userProfile.UserProfileActivity;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.news.NewsActionView;
import com.traap.traapapp.ui.fragments.news.NewsMainActionView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


@SuppressLint("newsMainFragment")
public class NewsMainFragment extends BaseFragment implements NewsActionView
{
    private View rootView;
    private NewsMainActionView mainView;

    private Toolbar mToolbar;
    private TextView tvUserName, tvHeaderPopularNo;
    private Fragment fragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    public NewsMainFragment()
    {

    }

    public static NewsMainFragment newInstance(NewsMainActionView mainView)
    {
        NewsMainFragment f = new NewsMainFragment();
        f.setMainView(mainView);
        return f;
    }

    private void setMainView(NewsMainActionView mainView)
    {
        this.mainView = mainView;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_news_main, container, false);

        mToolbar = rootView.findViewById(R.id.toolbar);

        mToolbar.findViewById(R.id.imgMenu).setOnClickListener(v -> mainView.openDrawerNews());
        mToolbar.findViewById(R.id.imgBack).setOnClickListener(rootView -> mainView.backToMainFragment());
        tvUserName = mToolbar.findViewById(R.id.tvUserName);
        tvHeaderPopularNo = mToolbar.findViewById(R.id.tvPopularPlayer);
        tvHeaderPopularNo.setText(String.valueOf(Prefs.getInt("popularPlayer", 12)));
        TextView tvTitle = mToolbar.findViewById(R.id.tvTitle);
        tvTitle.setText("اخبار");
        rootView.findViewById(R.id.rlShirt).setOnClickListener(v -> startActivity(new Intent(SingletonContext.getInstance().getContext(), UserProfileActivity.class)));
        tvUserName.setText(TrapConfig.HEADER_USER_NAME);

        fragmentManager = getChildFragmentManager();

        fragment = NewsMainContentFragment.newInstance(NewsParent.MainFragment, null, this);
        transaction = fragmentManager.beginTransaction();
//                        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);

        transaction.replace(R.id.container, fragment, "newsMainContentFragment")
                .commit();


        EventBus.getDefault().register(this);
        return rootView;
    }


    @Override
    public void backToMainNewsFragment()
    { }

    @Override
    public void onNewsArchiveFragment(NewsParent parent)
    {
        mainView.onNewsArchiveFragment(parent);
    }

    @Override
    public void onNewsFavoriteFragment(NewsParent parent)
    {
        mainView.onNewsFavoriteFragment(parent);
    }

    @Override
    public void openDrawerNews()
    {
        mainView.openDrawerNews();
    }

    @Override
    public void closeDrawerNews()
    {
        mainView.closeDrawerNews();
    }

    @Override
    public void showLoading()
    {
        mainView.showLoading();
    }

    @Override
    public void hideLoading()
    {
        mainView.hideLoading();
    }

    @Subscribe
    public void getHeaderContent(HeaderModel headerModel)
    {
        if (headerModel.getPopularNo() != 0)
        {
            tvHeaderPopularNo.setText(String.valueOf(headerModel.getPopularNo()));
        }
        tvUserName.setText(TrapConfig.HEADER_USER_NAME);
    }


    @Override
    public void onDestroy()
    {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
