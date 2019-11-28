package ir.traap.tractor.android.ui.fragments.news.mainNews;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import ir.traap.tractor.android.R;
import ir.traap.tractor.android.conf.TrapConfig;
import ir.traap.tractor.android.enums.NewsParent;
import ir.traap.tractor.android.ui.activities.main.MainActivity;
import ir.traap.tractor.android.ui.base.BaseFragment;
import ir.traap.tractor.android.ui.fragments.news.NewsActionView;
import ir.traap.tractor.android.ui.fragments.news.NewsMainActionView;


@SuppressLint("newsMainFragment")
public class NewsMainFragment extends BaseFragment implements NewsActionView
{
    private View rootView;
    private NewsMainActionView mainView;

    private Toolbar mToolbar;
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
        TextView tvUserName = mToolbar.findViewById(R.id.tvUserName);
        TextView tvTitle = mToolbar.findViewById(R.id.tvTitle);
        tvTitle.setText("اخبار");
        tvUserName.setText(TrapConfig.HEADER_USER_NAME);

        fragmentManager = getChildFragmentManager();

        fragment = NewsMainContentFragment.newInstance(NewsParent.MainFragment, MainActivity.newsMainResponse, this);
        transaction = fragmentManager.beginTransaction();
//                        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);

        transaction.replace(R.id.container, fragment, "newsMainContentFragment")
                .commit();


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
        mainView.showLoading();
    }
}
