package com.traap.traapapp.ui.fragments.news.archive;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.pixplicity.easyprefs.library.Prefs;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState;
import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.news.category.response.NewsArchiveCategory;
import com.traap.traapapp.apiServices.model.news.category.response.NewsArchiveCategoryResponse;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.enums.MediaPosition;
import com.traap.traapapp.enums.NewsParent;
import com.traap.traapapp.models.otherModels.headerModel.HeaderModel;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.ui.activities.userProfile.UserProfileActivity;
import com.traap.traapapp.ui.adapters.news.NewsArchiveFilterAdapter;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.news.NewsArchiveActionView;
import com.traap.traapapp.utilities.Logger;
import com.traap.traapapp.utilities.MyCustomViewPager;
import com.traap.traapapp.utilities.calendar.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.traap.traapapp.utilities.calendar.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class NewsArchiveFragment extends BaseFragment implements OnServiceStatus<WebServiceClass<NewsArchiveCategoryResponse>>
    , DatePickerDialog.OnDateSetListener
{
    private CompositeDisposable disposable = new CompositeDisposable();
    public static int DELAY_TIME_TEXT_CHANGE = 500;

    private Toolbar mToolbar;

    private TextView tvUserName, tvHeaderPopularNo;
    private SlidingUpPanelLayout slidingUpPanelLayout;

    private ImageView imgStartDateReset, imgEndDateReset;
    private TextView tvStartDate, tvEndDate;
    private EditText edtSearchFilter;
    private CircularProgressButton btnConfirm;

    private RecyclerView rcFilterCategory;

    private int endDay = 0, endMonth = 0, endYear = 0;
    private int startDay = 0, startMonth = 0, startYear = 0;

    private LinearLayout btnFilter;

    private View rootView;

    private NewsArchiveActionView mainNewsView;
    private ArrayList<NewsArchiveCategory> newsArchiveCategoryList = new ArrayList<>();
    private NewsArchiveFilterAdapter adapter;
    private boolean pagerWithFilter = false;
    private boolean pagerFromFavorite = false;
    private NewsParent parent;
    private MediaPosition mediaPosition;

    private DatePickerDialog pickerDialogStartDate, pickerDialogEndDate;

    private PersianCalendar currentDate, startPersianDate, endPersianDate;
    private Integer startDateInt = 0, endDateInt = 0;

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
        mToolbar.findViewById(R.id.rlShirt).setOnClickListener(v -> startActivity(new Intent(SingletonContext.getInstance().getContext(), UserProfileActivity.class)));
        mToolbar.findViewById(R.id.imgMenu).setOnClickListener(v -> mainNewsView.openDrawerNews());
        tvUserName = mToolbar.findViewById(R.id.tvUserName);
        tvUserName.setText(TrapConfig.HEADER_USER_NAME);
        tvHeaderPopularNo = mToolbar.findViewById(R.id.tvPopularPlayer);
        tvHeaderPopularNo.setText(String.valueOf(Prefs.getInt("popularPlayer", 12)));

        initDatePicker();
        initView();

        if (!pagerFromFavorite)
        {
            SingletonService.getInstance().getNewsService().getNewsArchiveCategory(this);
        }
        else
        {
            setPager(false, true);
        }

        EventBus.getDefault().register(this);
        return rootView;
    }

    private void initView()
    {
        rcFilterCategory = rootView.findViewById(R.id.rcFilterCategory);
        slidingUpPanelLayout = rootView.findViewById(R.id.slidingLayout);
        btnFilter = rootView.findViewById(R.id.btnFilter);
        imgStartDateReset = rootView.findViewById(R.id.imgDateFromReset);
        imgEndDateReset = rootView.findViewById(R.id.imgDateToReset);
        tvStartDate = rootView.findViewById(R.id.tvTimeFrom);
        tvEndDate = rootView.findViewById(R.id.tvTimeUntil);
        edtSearchFilter = rootView.findViewById(R.id.edtSearchFilter);
        btnConfirm = rootView.findViewById(R.id.btnConfirm);

//        adapter = new NewsArchiveFilterAdapter(getActivity(), newsArchiveCategoryList);

        btnFilter.setOnClickListener(v ->
        {
            new Handler().postDelayed(() -> slidingUpPanelLayout.setPanelState(PanelState.EXPANDED), 200);
        });

        slidingUpPanelLayout.setFadeOnClickListener(v ->
        {
            slidingUpPanelLayout.setPanelState(PanelState.COLLAPSED);
        });

        tvStartDate.setOnClickListener(v ->
        {
            pickerDialogStartDate.show(getFragmentManager(), "StartDate");
        });

        tvEndDate.setOnClickListener(v ->
        {
            pickerDialogEndDate.show(getFragmentManager(), "EndDate");
        });

        imgStartDateReset.setOnClickListener(v ->
        {
            tvStartDate.setText("");
            startDay = 0;
            startMonth = 0;
            startYear = 0;
            imgStartDateReset.setVisibility(View.GONE);
        });

        imgEndDateReset.setOnClickListener(v ->
        {
            tvEndDate.setText("");
            endDay = 0;
            endMonth = 0;
            endYear = 0;
            imgEndDateReset.setVisibility(View.GONE);
        });

//        edtSearchFilter.settextc
        disposable.add(RxTextView.textChanges(edtSearchFilter)
                .skipInitialValue()
                .debounce(DELAY_TIME_TEXT_CHANGE, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(searchFilter())
        );

    }

    private void initDatePicker()
    {
        currentDate = new PersianCalendar();

        pickerDialogStartDate = DatePickerDialog.newInstance(this,
                currentDate.getPersianYear(),
                currentDate.getPersianMonth() - 1,
                currentDate.getPersianDay()
        );
        pickerDialogStartDate.setTitle("انتخاب تاریخ شروع");

        startDay = currentDate.getPersianDay();
        startMonth = currentDate.getPersianMonth() - 1 ;
        startYear = currentDate.getPersianYear();

        endPersianDate = new PersianCalendar();
        endDay = currentDate.getPersianDay();
        endMonth = currentDate.getPersianMonth();
        endYear = currentDate.getPersianYear();

        startPersianDate = new PersianCalendar();
        startPersianDate.set(startYear, startMonth, startDay);
        endPersianDate.set(endYear, endMonth, endDay);
        pickerDialogStartDate.setMaxDate(endPersianDate);

        startDateInt = getDateInt(startYear, startMonth, startDay);
        endDateInt = getDateInt(endYear, endMonth, endDay);

        pickerDialogEndDate = DatePickerDialog.newInstance(this,
                currentDate.getPersianYear(),
                currentDate.getPersianMonth(),
                currentDate.getPersianDay()
        );
        pickerDialogEndDate.setTitle("انتخاب تاریخ پایان");

        pickerDialogEndDate.setMinDate(startPersianDate);
        pickerDialogEndDate.setMaxDate(endPersianDate);
    }

    private Integer getDateInt(int year, int month, int day)
    {
        return (year - year/100)*10000 + month*100 + day;
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

            adapter = new NewsArchiveFilterAdapter(getActivity(), newsArchiveCategoryList);
            rcFilterCategory.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            rcFilterCategory.setLayoutManager(new GridLayoutManager(getActivity(), 5, RecyclerView.HORIZONTAL, true));

            adapter.SetOnItemCheckedChangeListener((id, position) ->
            {

            });
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

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth, int endYear, int endMonth, int endDay)
    {
        if (view.getTag().equals("StartDate"))
        {
            startPersianDate.set(year, monthOfYear, dayOfMonth);
            pickerDialogEndDate.setMinDate(startPersianDate);
            pickerDialogEndDate.setMaxDate(currentDate);
            startDateInt = getDateInt(year, monthOfYear, dayOfMonth);

            if (startDateInt > endDateInt)
            {
                tvEndDate.setText("");
                imgEndDateReset.setVisibility(View.GONE);
                endDay = 0;
                endMonth = 0;
                endYear = 0;
            }

            String startDate = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
            tvStartDate.setText(startDate);
            imgStartDateReset.setVisibility(View.VISIBLE);
        }
        else if (view.getTag().equals("EndDate"))
        {
            endPersianDate.set(year, monthOfYear, dayOfMonth);
            pickerDialogStartDate.setMaxDate(endPersianDate);
            endDateInt = getDateInt(year, monthOfYear, dayOfMonth);

            if (startDateInt > endDateInt)
            {
                tvStartDate.setText("");
                imgStartDateReset.setVisibility(View.GONE);
                startDay = 0;
                startMonth = 0;
                startYear = 0;
            }

            String endDate = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
            tvEndDate.setText(endDate);
            imgEndDateReset.setVisibility(View.VISIBLE);
        }
    }

    private DisposableObserver<CharSequence> searchFilter()
    {
        return new DisposableObserver<CharSequence>()
        {
            @Override
            public void onNext(CharSequence charSequence)
            {
                Log.e("--searchContacts--", "Search query: " + charSequence);
                getFilter(charSequence.toString());
            }

            @Override
            public void onError(Throwable e)
            {
                Log.e("--searchContacts--", "onError: " + e.getMessage());
            }

            @Override
            public void onComplete()
            {}
        };
    }

    private void getFilter(String text)
    {
        if (text.length() > 2)
        {

        }
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
        disposable.clear();
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
