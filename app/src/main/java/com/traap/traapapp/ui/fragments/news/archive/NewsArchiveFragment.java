package com.traap.traapapp.ui.fragments.news.archive;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
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
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.jakewharton.rxbinding2.view.RxView;
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
import com.traap.traapapp.enums.NewsArchiveCategoryCall;
import com.traap.traapapp.enums.NewsParent;
import com.traap.traapapp.models.otherModels.headerModel.HeaderModel;
import com.traap.traapapp.models.otherModels.newsFilterItem.NewsFilterItem;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.ui.adapters.news.NewsArchiveFilterAdapter;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.activities.myProfile.MyProfileActivity;
import com.traap.traapapp.ui.fragments.news.NewsArchiveActionView;
import com.traap.traapapp.utilities.Logger;
import com.traap.traapapp.utilities.MyCustomViewPager;
import com.traap.traapapp.utilities.ReplacePersianNumberToEnglish;
import com.traap.traapapp.utilities.calendar.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.traap.traapapp.utilities.calendar.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class NewsArchiveFragment extends BaseFragment implements OnServiceStatus<WebServiceClass<NewsArchiveCategoryResponse>>
    , DatePickerDialog.OnDateSetListener, NewsArchiveFilterAdapter.OnItemCheckedChangeListener
{
    private CompositeDisposable disposable = new CompositeDisposable();
    private final int DELAY_TIME_TEXT_CHANGE = 200;

    private String idFilteredList = "", titleFilteredList = "";

    private Toolbar mToolbar;

    private TextView tvUserName, tvHeaderPopularNo;
    private SlidingUpPanelLayout slidingUpPanelLayout;

    private ImageView imgStartDateReset, imgEndDateReset, imgFilterClose;
    private TextView tvStartDate, tvEndDate;
    private EditText edtSearchFilter;
    private CircularProgressButton btnConfirmFilter, btnDeleteFilter;

    private RecyclerView rcFilterCategory;

    private int endDay = 0, endMonth = 0, endYear = 0;
    private int startDay = 0, startMonth = 0, startYear = 0;

    private LinearLayout btnFilter, llDeleteFilter;

    private View rootView;

    private NewsArchiveActionView mainNewsView;
    private ArrayList<NewsArchiveCategory> newsArchiveCategoryList = new ArrayList<>();
    private List<NewsFilterItem> filteredNewsArchiveCategoryList = new ArrayList<>();
    private ArrayList<NewsFilterItem> filteredShowList = new ArrayList<>();
    private ArrayList<NewsFilterItem> tempFilteredCategoryList = new ArrayList<>();
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
        disposable.add(RxView.clicks(mToolbar.findViewById(R.id.imgBack))
                .subscribe(v ->
                {
                    if (parent == NewsParent.MainFragment)
                    {
//                        mainNewsView.backToMainFragment();
                        mainNewsView.backToMainNewsFragment();
                    }
                    else
                    {
                        mainNewsView.backToMediaFragment(mediaPosition);
                    }
                })
        );

        disposable.add(RxView.clicks(mToolbar.findViewById(R.id.rlShirt))
                .subscribe(v ->
                {
                    startActivity(new Intent(SingletonContext.getInstance().getContext(), MyProfileActivity.class));
                })
        );

        disposable.add(RxView.clicks(mToolbar.findViewById(R.id.imgMenu))
                .subscribe(v ->
                {
                    mainNewsView.openDrawerNews();
                })
        );

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
        llDeleteFilter = rootView.findViewById(R.id.llDeleteFilter);
        imgFilterClose = rootView.findViewById(R.id.imgFilterClose);
        imgStartDateReset = rootView.findViewById(R.id.imgDateFromReset);
        imgEndDateReset = rootView.findViewById(R.id.imgDateToReset);
        tvStartDate = rootView.findViewById(R.id.tvTimeFrom);
        tvEndDate = rootView.findViewById(R.id.tvTimeUntil);
        edtSearchFilter = rootView.findViewById(R.id.edtSearchFilter);
        btnConfirmFilter = rootView.findViewById(R.id.btnConfirmFilter);
        btnDeleteFilter = rootView.findViewById(R.id.btnDeleteFilter);

        disposable.add(RxView.clicks(btnFilter)
                .throttleFirst(200, TimeUnit.MILLISECONDS)
                .subscribe(v ->
                {
                    slidingUpPanelLayout.setPanelState(PanelState.EXPANDED);
                    if (pagerWithFilter)
                    {
                        Logger.e("getFilterId", idFilteredList);
                        llDeleteFilter.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        Logger.e("getFilterId", "Empty, " + idFilteredList);
                        llDeleteFilter.setVisibility(View.GONE);
                        filteredNewsArchiveCategoryList = new ArrayList<>();

                        for (NewsArchiveCategory item: newsArchiveCategoryList)
                        {
                            NewsFilterItem filterItem = new NewsFilterItem();
                            filterItem.setId(item.getId());
                            filterItem.setTitle(item.getTitle());
                            filterItem.setChecked(false);

                            filteredNewsArchiveCategoryList.add(filterItem);
                        }
                        Collections.reverse(filteredNewsArchiveCategoryList);
                    }
                    tempFilteredCategoryList = new ArrayList<>();
                    tempFilteredCategoryList.addAll(filteredNewsArchiveCategoryList);
                    adapter = new NewsArchiveFilterAdapter(getActivity(), tempFilteredCategoryList);
                    adapter.notifyDataSetChanged();
                    rcFilterCategory.setAdapter(adapter);
                    adapter.SetOnItemCheckedChangeListener(this);
                    rcFilterCategory.setLayoutManager(new GridLayoutManager(getActivity(), 5, RecyclerView.HORIZONTAL, true));
                })
        );
//        btnFilter.setOnClickListener(v ->
//        {
//            new Handler().postDelayed(() -> slidingUpPanelLayout.setPanelState(PanelState.EXPANDED), 200);
//        });

        disposable.add(RxView.clicks(btnConfirmFilter)
                .subscribe(v ->
                {
                    slidingUpPanelLayout.setPanelState(PanelState.COLLAPSED);
                    createItemFilterData();
                })
        );

        disposable.add(RxView.clicks(imgFilterClose)
                .subscribe(v ->
                {
                    slidingUpPanelLayout.setPanelState(PanelState.COLLAPSED);
//                    if ()
                })
        );

        disposable.add(RxView.clicks(tvStartDate)
                .subscribe(v ->
                {
                    pickerDialogStartDate.show(getFragmentManager(), "StartDate");
                })
        );

        disposable.add(RxView.clicks(tvEndDate)
                .subscribe(v ->
                {
                    pickerDialogEndDate.show(getFragmentManager(), "EndDate");
                })
        );

        disposable.add(RxView.clicks(imgStartDateReset)
                .subscribe(v ->
                {
                    tvStartDate.setText("");
                    startDay = 0;
                    startMonth = 0;
                    startYear = 0;
                    imgStartDateReset.setVisibility(View.GONE);
                })
        );

        disposable.add(RxView.clicks(imgEndDateReset)
                .subscribe(v ->
                {
                    tvEndDate.setText("");
                    endDay = 0;
                    endMonth = 0;
                    endYear = 0;
                    imgEndDateReset.setVisibility(View.GONE);
                })
        );

        slidingUpPanelLayout.setFadeOnClickListener(v ->
        {
            slidingUpPanelLayout.setPanelState(PanelState.COLLAPSED);
        });

        disposable.add(RxTextView.textChanges(edtSearchFilter)
                .skipInitialValue()
                .filter(charSequence ->
                {
                    if (charSequence.length() < 3)
                    {
                        adapter = new NewsArchiveFilterAdapter(getActivity(), filteredNewsArchiveCategoryList);
                        adapter.notifyDataSetChanged();
                        rcFilterCategory.setAdapter(adapter);
                        adapter.SetOnItemCheckedChangeListener(this);
                    }
                    return charSequence.length() > 2;
                })
                .debounce(DELAY_TIME_TEXT_CHANGE, TimeUnit.MILLISECONDS)
                //--------------------------
                .flatMap((Function<CharSequence, ObservableSource<NewsFilterItem>>) charSequence ->
                {
                    return getNewsArchiveCategoryObservable(ReplacePersianNumberToEnglish.getEnglishChar(charSequence));
//                        return getNewsArchiveCategoryObservable(charSequence);
                })
                //--------------------------
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getFilteredNewsArchiveIDs())
        );

        disposable.add(RxView.clicks(btnDeleteFilter)
                .subscribe(v ->
                {
                    slidingUpPanelLayout.setPanelState(PanelState.COLLAPSED);
                    pagerWithFilter = false;
                    edtSearchFilter.setText("");
                    tvStartDate.setText("");
                    tvEndDate.setText("");
                    idFilteredList = "";
                    titleFilteredList = "";
                    imgStartDateReset.setVisibility(View.GONE);
                    imgEndDateReset.setVisibility(View.GONE);
                    llDeleteFilter.setVisibility(View.GONE);

                    adapter = new NewsArchiveFilterAdapter(getActivity(), filteredNewsArchiveCategoryList);
                    adapter.notifyDataSetChanged();
                    rcFilterCategory.setAdapter(adapter);
                    adapter.SetOnItemCheckedChangeListener(this);
                })
        );
    }

    private void createItemFilterData()
    {
        disposable.add(Observable.fromIterable(tempFilteredCategoryList)
                .filter(new Predicate<NewsFilterItem>()
                {
                    @Override
                    public boolean test(NewsFilterItem newsFilterItem) throws Exception
                    {
                        return newsFilterItem.isChecked();
                    }
                })
                .distinct()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<NewsFilterItem>()
                {
                    @Override
                    public void onNext(NewsFilterItem newsFilterItem)
                    {
                        idFilteredList += idFilteredList + newsFilterItem.getId() + ",";
                        titleFilteredList += titleFilteredList + newsFilterItem.getTitle() + ",";
                        filteredNewsArchiveCategoryList.set(filteredNewsArchiveCategoryList.indexOf(newsFilterItem), newsFilterItem);
                        Logger.e("-createItemFilterData-", newsFilterItem.isChecked() + "");
//                        Logger.e("OnNext title", titleFilteredList);
                    }

                    @Override
                    public void onError(Throwable e)
                    {
                        Logger.e("onError ", "onError");
                    }

                    @Override
                    public void onComplete()
                    {
                        Logger.e("onComplete ", "onComplete");
                        Logger.e("idFilteredList", idFilteredList);
                        if (tvStartDate.getText().toString().equalsIgnoreCase("") &&
                                tvEndDate.getText().toString().equalsIgnoreCase("") &&
                                idFilteredList.equalsIgnoreCase("")
                        )
                        {
                            pagerWithFilter = false;
                            llDeleteFilter.setVisibility(View.GONE);

                            filteredNewsArchiveCategoryList = new ArrayList<>();
                            for (NewsArchiveCategory item: newsArchiveCategoryList)
                            {
                                NewsFilterItem filterItem = new NewsFilterItem();
                                filterItem.setId(item.getId());
                                filterItem.setTitle(item.getTitle());
                                filterItem.setChecked(false);

                                filteredNewsArchiveCategoryList.add(filterItem);
                            }

                            Logger.e("-id Filtered List-", "Empty");
                        }
                        else
                        {
                            pagerWithFilter = true;
                            llDeleteFilter.setVisibility(View.VISIBLE);
                            edtSearchFilter.setText("");

//                            filteredNewsArchiveCategoryList = new ArrayList<>();
//                            filteredNewsArchiveCategoryList.addAll(tempFilteredCategoryList);

                            // ToDo cal api filter with idList and date;
                            Logger.e("-id Filtered List-", idFilteredList);

                        }
                    }
                })
        );
    }

    private Observable<NewsFilterItem> getNewsArchiveCategoryObservable(final CharSequence sequence)
    {
        filteredShowList = new ArrayList<>();
        Observable<NewsFilterItem> observable = Observable.fromIterable(filteredNewsArchiveCategoryList)
                .filter(new Predicate<NewsFilterItem>()
                {
                    @Override
                    public boolean test(NewsFilterItem newsArchiveCategory) throws Exception
                    {
                        Logger.e("-Observable-","text: " + newsArchiveCategory.getTitle().contains(sequence));
                        return newsArchiveCategory.getTitle().contains(sequence);
                    }
                })
                .subscribeOn(Schedulers.io());

        if (observable.toList().blockingGet().size() == 0)
        {
            Logger.e("-Observable-", "List is Empty");
        }

        return observable;
    }

    private DisposableObserver<NewsFilterItem> getFilteredNewsArchiveIDs()
    {
        rcFilterCategory = rootView.findViewById(R.id.rcFilterCategory);
        filteredShowList = new ArrayList<>();

        return new DisposableObserver<NewsFilterItem>()
        {
            @Override
            public void onNext(NewsFilterItem newsArchiveCategory)
            {
                filteredShowList.add(newsArchiveCategory);
                Logger.e("--searchCategory--", "Search query: " + newsArchiveCategory.getTitle());
                Logger.e("--searchCategory--", "Query size: " + filteredShowList.size());

//                filteredNewsArchiveCategoryList = filteredShowList;
                Collections.reverse(filteredShowList);
                adapter = new NewsArchiveFilterAdapter(getActivity(), filteredShowList);
                adapter.notifyDataSetChanged();
                rcFilterCategory.setAdapter(adapter);
                adapter.SetOnItemCheckedChangeListener(NewsArchiveFragment.this);
            }

            @Override
            public void onError(Throwable e)
            {
                Logger.e("--searchCategory--", "onError: " + e.getMessage());
            }

            @Override
            public void onComplete()
            {
                Logger.e("--searchCategory--", "onComplete");
            }
        };
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
//            for (NewsArchiveCategory item: newsArchiveCategoryList)
//            {
//                NewsFilterItem filterItem = new NewsFilterItem();
//                filterItem.setId(item.getId());
//                filterItem.setTitle(item.getTitle());
//                filterItem.setChecked(false);
//
//                filteredNewsArchiveCategoryList.add(filterItem);
//            }

            setPager(pagerWithFilter, pagerFromFavorite);

//            Collections.reverse(filteredNewsArchiveCategoryList);

//            adapter = new NewsArchiveFilterAdapter(getActivity(), filteredNewsArchiveCategoryList);
//            adapter.notifyDataSetChanged();
//            rcFilterCategory.setAdapter(adapter);
//            adapter.SetOnItemCheckedChangeListener(this);
//            rcFilterCategory.setLayoutManager(new GridLayoutManager(getActivity(), 5, RecyclerView.HORIZONTAL, true));
//
//            adapter.SetOnItemCheckedChangeListener((id, isChecked, newsFilterItem) ->
//            {
//                NewsFilterItem oldFilterItem = new NewsFilterItem();
//                oldFilterItem.setId(id);
//                oldFilterItem.setTitle(newsFilterItem.getTitle());
//                oldFilterItem.setChecked(!isChecked);
//
//                Logger.e("-filter Selected-", id + ", " + newsFilterItem.getTitle() + ", " + !isChecked);
//
//                tempFilteredCategoryList.set(tempFilteredCategoryList.indexOf(oldFilterItem), newsFilterItem);
//            });
//
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
        llDeleteFilter.setVisibility(View.VISIBLE);

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

    @Override
    public void onItemCheckedChange(Integer id, boolean isChecked, NewsFilterItem newsFilterItem)
    {

        Logger.e("-filter Selected-", id + ", " + newsFilterItem.getTitle() + ", " + !isChecked);

        disposable.add(Observable.fromIterable(tempFilteredCategoryList)
                .filter(new Predicate<NewsFilterItem>()
                {
                    @Override
                    public boolean test(NewsFilterItem fNewsFilterItem) throws Exception
                    {
                        return fNewsFilterItem.getId() == id;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<NewsFilterItem>()
                {
                    @Override
                    public void onNext(NewsFilterItem fNewsFilterItem)
                    {
                        int index = tempFilteredCategoryList.indexOf(fNewsFilterItem);
                        Logger.e("-change-", "isChecked: " +  isChecked);
                        tempFilteredCategoryList.set(index, newsFilterItem);
                    }

                    @Override
                    public void onError(Throwable e)
                    {

                    }

                    @Override
                    public void onComplete()
                    {

                    }
                }));
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
                return NewsArchiveCategoryFragment.newInstance("", //Id Filter List
                        NewsArchiveCategoryCall.FROM_FILTER_IDs_DATE, //or FROM_FILTER_IsS
                        "", // dateFilter
                        null);
            }
            else if (pagerFromFavorite)
            {
                rootView.findViewById(R.id.llFilterAndTab).setVisibility(View.GONE);

//                return NewsArchiveCategoryFragment.newInstance(0, false, true, null);
                return NewsArchiveCategoryFragment.newInstance("", NewsArchiveCategoryCall.FROM_FAVORITE, null, null);
            }
            else
            {
                int Id =  newsArchiveCategoryList.get(position).getId();
                Logger.e("--nID--", "pos: " + position + ", ID:" + Id);
                return NewsArchiveCategoryFragment.newInstance(String.valueOf(Id), NewsArchiveCategoryCall.FROM_ID, null, null);
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
