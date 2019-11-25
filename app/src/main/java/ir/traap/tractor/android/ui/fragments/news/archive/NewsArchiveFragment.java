package ir.traap.tractor.android.ui.fragments.news.archive;

import android.graphics.Typeface;
import android.os.Build;
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
import androidx.viewpager.widget.ViewPager;

import com.daimajia.slider.library.Transformers.RotateUpTransformer;
import com.google.android.material.tabs.TabLayout;
import com.orm.util.Collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ir.traap.tractor.android.R;
import ir.traap.tractor.android.apiServices.generator.SingletonService;
import ir.traap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.traap.tractor.android.apiServices.model.WebServiceClass;
import ir.traap.tractor.android.apiServices.model.news.category.response.NewsArchiveCategory;
import ir.traap.tractor.android.apiServices.model.news.category.response.NewsArchiveCategoryResponse;
import ir.traap.tractor.android.conf.TrapConfig;
import ir.traap.tractor.android.ui.activities.userProfile.UserProfileActivity;
import ir.traap.tractor.android.ui.base.BaseFragment;
import ir.traap.tractor.android.ui.fragments.news.NewsActionView;
import ir.traap.tractor.android.utilities.Logger;
import ir.traap.tractor.android.utilities.MyCustomViewPager;
import ir.traap.tractor.android.utilities.Tools;

public class NewsArchiveFragment extends BaseFragment implements OnServiceStatus<WebServiceClass<NewsArchiveCategoryResponse>>
{
    private Toolbar mToolbar;

    private View rootView;

    private NewsActionView mainNewsView;
    private ArrayList<NewsArchiveCategory> newsArchiveCategoryList;
    private boolean pagerWithFilter = false;

    public NewsArchiveFragment()
    {
    }

    public static NewsArchiveFragment newInstance(NewsActionView mainNewsView)
    {
        NewsArchiveFragment fragment = new NewsArchiveFragment();
        fragment.setMainNewsView(mainNewsView);

        Bundle arg = new Bundle();
//        arg.putParcelable("matchPredict", matchPredict);
//        arg.putBoolean("isPredictable", isPredictable);

        return fragment;
    }


    private void setMainNewsView(NewsActionView mainNewsView)
    {
        this.mainNewsView = mainNewsView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
//            matchPredict = getArguments().getParcelable("matchPredict");
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
            mainNewsView.backToMainNewsFragment();
        });

        mToolbar.findViewById(R.id.imgMenu).setOnClickListener(v -> mainNewsView.openDrawer());
        TextView tvUserName = mToolbar.findViewById(R.id.tvUserName);
        tvUserName.setText(TrapConfig.HEADER_USER_NAME);

        SingletonService.getInstance().getNewsService().getNewsArchiveCategory(this);

        return rootView;
    }

    private void setPager(boolean pagerWithFilter)
    {
        MyCustomViewPager pager = rootView.findViewById(R.id.view_pager);
        SamplePagerAdapter adapter = new SamplePagerAdapter(getFragmentManager(), newsArchiveCategoryList, pagerWithFilter, 0);

        pager.setAdapter(adapter);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
//        {
//            pager.setPageTransformer(true, (ViewPager.PageTransformer) new RotateUpTransformer());
//        }

        TabLayout tabLayout = rootView.findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(pager);
        pager.setCurrentItem(newsArchiveCategoryList.size()-1);


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
            Collections.reverse(newsArchiveCategoryList);

            setPager(pagerWithFilter);
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

        public SamplePagerAdapter(@NonNull FragmentManager fm, ArrayList<NewsArchiveCategory> newsArchiveCategories, boolean pagerWithFilter, int behavior)
        {
//            super(fm);
            super(fm, behavior);
            this.newsArchiveCategories = newsArchiveCategories;
            this.pagerWithFilter = pagerWithFilter;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position)
        {
            return newsArchiveCategories.get(position).getTitle();
        }

        @NonNull
        @Override
        public Fragment getItem(int position)
        {
            if (pagerWithFilter)
            {
                return NewsArchiveCategoryFragment.newInstance(pagerWithFilter);
            }
            else
            {
                Logger.e("--nID--", String.valueOf(newsArchiveCategoryList.get(position).getId()));
                return NewsArchiveCategoryFragment.newInstance(newsArchiveCategoryList.get(position).getId());
            }
        }

        @Override
        public int getCount()
        {
            return newsArchiveCategories.size();
        }

        public View getTabView(int position)
        {
            // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView
            View v = LayoutInflater.from(getActivity()).inflate(R.layout.tab_category_content, null);
            Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/iran_sans_bold.ttf");

            TextView tv = (TextView) v.findViewById(R.id.textView);
            tv.setText(getPageTitle(position));
            tv.setGravity(Gravity.CENTER_HORIZONTAL);
            tv.setTextColor(getResources().getColorStateList(R.color.textHint));
            tv.setTypeface(font);
            return v;
        }

    }

}
