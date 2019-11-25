package ir.traap.tractor.android.ui.fragments.news.archive;

import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
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

import java.util.ArrayList;
import java.util.Collections;

import ir.traap.tractor.android.R;
import ir.traap.tractor.android.apiServices.generator.SingletonService;
import ir.traap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.traap.tractor.android.apiServices.model.WebServiceClass;
import ir.traap.tractor.android.apiServices.model.news.archive.response.NewsArchiveListByIdResponse;
import ir.traap.tractor.android.apiServices.model.news.category.response.NewsArchiveCategory;
//import ir.traap.tractor.android.apiServices.model.news.category.response.NewsArchiveCategoryResponse;
import ir.traap.tractor.android.conf.TrapConfig;
import ir.traap.tractor.android.ui.base.BaseFragment;
import ir.traap.tractor.android.ui.fragments.news.NewsActionView;
import ir.traap.tractor.android.utilities.Logger;
import ir.traap.tractor.android.utilities.MyCustomViewPager;
import ir.traap.tractor.android.utilities.Tools;

public class NewsArchiveCategoryFragment extends BaseFragment implements OnServiceStatus<WebServiceClass<NewsArchiveListByIdResponse>>
{
    private View rootView;
    private NewsArchiveCategory archiveCategory;
    private int Id;
    private boolean pagerWithFilter = false;

    private ProgressBar progressBar;

    private ArrayList<NewsArchiveListByIdResponse> newsArchiveCategoryList;

    public NewsArchiveCategoryFragment()
    {
    }

    public static NewsArchiveCategoryFragment newInstance(int Id)
    {
        NewsArchiveCategoryFragment fragment = new NewsArchiveCategoryFragment();

        Bundle arg = new Bundle();
        arg.putInt("Id", Id);
        arg.putBoolean("pagerWithFilter", false);

        return fragment;
    }

    public static NewsArchiveCategoryFragment newInstance(boolean pagerWithFilter)
    {
        NewsArchiveCategoryFragment fragment = new NewsArchiveCategoryFragment();

        Bundle arg = new Bundle();
//        arg.putParcelable("NewsArchiveCategory", archiveCategory);

        arg.putBoolean("pagerWithFilter", pagerWithFilter);

        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            Id = getArguments().getInt("Id");
            pagerWithFilter = getArguments().getBoolean("pagerWithFilter");

            Logger.e("--Id--", archiveCategory.getId() + " # " + archiveCategory.getTitle());
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

        progressBar = rootView.findViewById(R.id.progressbar);

        if (pagerWithFilter)
        {

        }
        else
        {
            Logger.e("-Id-", String.valueOf(Id));
            SingletonService.getInstance().getNewsService().getNewsArchiveCategoryById(String.valueOf(Id), this);
        }

        return rootView;
    }

    private void initContentView()
    {

    }

    private void initContentFilteredView()
    {

    }


    @Override
    public void onReady(WebServiceClass<NewsArchiveListByIdResponse> response)
    {
        if (response.info.statusCode != 200)
        {
            showError(getActivity(), response.info.message);
        }
        else
        {
            newsArchiveCategoryList = response.data.getNewsArchiveListById();

        }
    }

    @Override
    public void onError(String message)
    {
        if (!Tools.isNetworkAvailable(getActivity()))
        {
            Logger.e("-OnError-", "Error: " + message);
            showError(getActivity(), "خطا در دریافت اطلاعات از سرور!");
        }
        else
        {
            showAlert(getActivity(), R.string.networkErrorMessage, R.string.networkError);
        }
    }

    private class SamplePagerAdapter extends FragmentStatePagerAdapter
    {
        private ArrayList<NewsArchiveCategory> newsArchiveCategories;

        public SamplePagerAdapter(@NonNull FragmentManager fm, ArrayList<NewsArchiveCategory> newsArchiveCategories, int behavior)
        {
            super(fm, behavior);
            this.newsArchiveCategories = newsArchiveCategories;
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
            return null;
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
