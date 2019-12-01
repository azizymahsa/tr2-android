package com.traap.traapapp.ui.fragments.news.archive;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Collections;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.news.category.response.NewsArchiveCategory;
import com.traap.traapapp.apiServices.model.news.category.response.NewsArchiveCategoryResponse;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.enums.MediaPosition;
import com.traap.traapapp.enums.NewsParent;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.news.NewsArchiveActionView;
import com.traap.traapapp.utilities.Logger;
import com.traap.traapapp.utilities.MyCustomViewPager;

public class NewsArchiveFragment extends BaseFragment implements OnServiceStatus<WebServiceClass<NewsArchiveCategoryResponse>>
{
    private Toolbar mToolbar;

    private View rootView;

    private NewsArchiveActionView mainNewsView;
    private ArrayList<NewsArchiveCategory> newsArchiveCategoryList;
    private boolean pagerWithFilter = false;
    private boolean pagerFromFavorite = false;
    private NewsParent parent;
    private MediaPosition mediaPosition;

    public NewsArchiveFragment()
    {
    }

    public static NewsArchiveFragment newInstance(NewsParent parent, MediaPosition mediaPosition, boolean pagerFromFavorite, NewsArchiveActionView mainNewsView)
    {
        NewsArchiveFragment fragment = new NewsArchiveFragment();
        fragment.setMainNewsView(mainNewsView);
        fragment.setParent(parent);
        fragment.setMediaPosition(mediaPosition);

        Bundle arg = new Bundle();

        arg.putBoolean("pagerFromFavorite", pagerFromFavorite);
//        arg.putBoolean("isPredictable", isPredictable);
        fragment.setArguments(arg);

        return fragment;
    }

    private void setParent(NewsParent parent) { this.parent = parent; }

    private void setMediaPosition(MediaPosition mediaPosition) { this.mediaPosition = mediaPosition; }

    private void setMainNewsView(NewsArchiveActionView mainNewsView)
    {
        this.mainNewsView = mainNewsView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            pagerFromFavorite = getArguments().getBoolean("pagerFromFavorite");
//            isPredictable = getArguments().getBoolean("isPredictable");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        if (rootView != null)
        {
            return rootView;
        }

        rootView = inflater.inflate(R.layout.fragment_news_archive, container, false);

        mToolbar = rootView.findViewById(R.id.toolbar);

        ((TextView) mToolbar.findViewById(R.id.tvTitle)).setText("آرشیو اخبار");
        rootView.findViewById(R.id.imgBack).setOnClickListener(v ->
        {
            if (parent == NewsParent.MainFragment)
            {
//                mainNewsView.backToMainFragment();
                mainNewsView.backToMainNewsFragment();
            }
            else
            {
                mainNewsView.backToMediaFragment(mediaPosition);
            }
        });

        mToolbar.findViewById(R.id.imgMenu).setOnClickListener(v -> mainNewsView.openDrawerNews());
        TextView tvUserName = mToolbar.findViewById(R.id.tvUserName);
        tvUserName.setText(TrapConfig.HEADER_USER_NAME);


        if (!pagerFromFavorite)
        {
            SingletonService.getInstance().getNewsService().getNewsArchiveCategory(this);
        }
        else
        {
            setPager(false, true);
        }

        return rootView;
    }

    private void setPager(boolean pagerWithFilter, boolean pagerFromFavorite)
    {
        MyCustomViewPager pager = rootView.findViewById(R.id.view_pager);

        if (pagerFromFavorite)
        {
            SamplePagerAdapter adapter = new SamplePagerAdapter(getFragmentManager(), null, pagerFromFavorite, pagerWithFilter);

            pager.setAdapter(adapter);

            pager.setCurrentItem(0);
        }
        else
        {
            Collections.reverse(newsArchiveCategoryList);

            SamplePagerAdapter adapter = new SamplePagerAdapter(getFragmentManager(), newsArchiveCategoryList, pagerFromFavorite, pagerWithFilter);

            pager.setAdapter(adapter);

            TabLayout tabLayout = rootView.findViewById(R.id.tabLayout);
            tabLayout.setupWithViewPager(pager);

            for (int i = 0; i < tabLayout.getTabCount(); i++)
            {
                TabLayout.Tab tab = tabLayout.getTabAt(i);
                tab.setCustomView(adapter.getTabView(i));
            }

            pager.setCurrentItem(newsArchiveCategoryList.size()-1);
        }

    }


    @Override
    public void onReady(WebServiceClass<NewsArchiveCategoryResponse> response)
    {
        if (response.info.statusCode != 200)
        {
            showError(getActivity(), response.info.message);
        }
        else
        {
            newsArchiveCategoryList = response.data.getNewsArchiveCategoryList();
            setPager(pagerWithFilter, pagerFromFavorite);
        }
    }

    @Override
    public void onError(String message)
    {
//        if (Tools.isNetworkAvailable(getActivity()))
//        {
            Logger.e("-OnError-", "Error: " + message);
            showError(getActivity(), "خطا در دریافت اطلاعات از سرور!");
//        }
//        else
//        {
//            showAlert(getActivity(), R.string.networkErrorMessage, R.string.networkError);
//        }
    }

    private class SamplePagerAdapter extends FragmentStatePagerAdapter
    {
        private ArrayList<NewsArchiveCategory> newsArchiveCategories;
        private boolean pagerWithFilter = false;
        private boolean pagerFromFavorite = false;
        private Context context = SingletonContext.getInstance().getContext();

        @SuppressLint("WrongConstant")
        public SamplePagerAdapter(@NonNull FragmentManager fm,
                                  ArrayList<NewsArchiveCategory> newsArchiveCategories,
                                  boolean pagerFromFavorite,
                                  boolean pagerWithFilter)
        {
            super(fm, 0);
            this.newsArchiveCategories = newsArchiveCategories;
            this.pagerWithFilter = pagerWithFilter;
            this.pagerFromFavorite = pagerFromFavorite;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position)
        {
            try
            {
                return newsArchiveCategories.get(position).getTitle();
            }
            catch (NullPointerException e)
            {
                return "";
            }
        }

        @NonNull
        @Override
        public Fragment getItem(int position)
        {
            if (pagerWithFilter)
            {
                return NewsArchiveCategoryFragment.newInstance(pagerWithFilter);
            }
            else if (pagerFromFavorite)
            {
                rootView.findViewById(R.id.llFilterAndTab).setVisibility(View.GONE);

                return NewsArchiveCategoryFragment.newInstance(0, false, true, null);
            }
            else
            {
                int Id =  newsArchiveCategoryList.get(position).getId();
                Logger.e("--nID--", "pos: " + position + ", ID:" + Id);
                return NewsArchiveCategoryFragment.newInstance(Id, true, false, null);
            }
        }

        @Override
        public int getCount()
        {
            try
            {
                return newsArchiveCategories.size();
            }
            catch (NullPointerException e)
            {
                return 1;
            }
        }

        public View getTabView(int position)
        {
            // Given you have a custom layout in `res/layout/tab_category_content.xml` with a TextView
            View v = LayoutInflater.from(context).inflate(R.layout.tab_category_content, null);

            Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/iran_sans_normal.ttf");

            TextView tv = v.findViewById(R.id.textView);
            tv.setText(getPageTitle(position));
            tv.setGravity(Gravity.CENTER_HORIZONTAL);
            tv.setTextColor(context.getResources().getColorStateList(R.color.textColorSecondary));
            tv.setTypeface(font);
            return v;
        }

    }

}
