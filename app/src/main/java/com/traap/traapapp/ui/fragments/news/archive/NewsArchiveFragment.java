package com.traap.traapapp.ui.fragments.news.archive;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.jakewharton.rxbinding3.view.RxView;
import com.jakewharton.rxbinding3.widget.RxTextView;
import com.pixplicity.easyprefs.library.Prefs;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState;
import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.media.category.TypeCategory;
import com.traap.traapapp.apiServices.model.media.category.MediaArchiveCategoryResponse;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.enums.MediaPosition;
import com.traap.traapapp.enums.MediaArchiveCategoryCall;
import com.traap.traapapp.enums.SubMediaParent;
import com.traap.traapapp.models.otherModels.headerModel.HeaderModel;
import com.traap.traapapp.models.otherModels.newsFilterItem.FilterItem;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.ui.adapters.media.HashTagMediaAdapter;
import com.traap.traapapp.ui.adapters.filterArchive.FilterArchiveAdapter;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.activities.myProfile.MyProfileActivity;
import com.traap.traapapp.ui.fragments.news.NewsArchiveActionView;
import com.traap.traapapp.utilities.Logger;
import com.traap.traapapp.utilities.MyCustomViewPager;
import com.traap.traapapp.utilities.ReplacePersianNumberToEnglish;
import com.traap.traapapp.utilities.TagGroup;
import com.traap.traapapp.utilities.Tools;
import com.traap.traapapp.utilities.Utility;

import com.traap.traapapp.utilities.calendar.mohamadamin_t.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.traap.traapapp.utilities.calendar.mohamadamin_t.persianmaterialdatetimepicker.utils.PersianCalendar;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class NewsArchiveFragment extends BaseFragment implements OnServiceStatus<WebServiceClass<MediaArchiveCategoryResponse>>
    , DatePickerDialog.OnDateSetListener, FilterArchiveAdapter.OnItemCheckedChangeListener
{
    private CompositeDisposable disposable = new CompositeDisposable();
    private final int DELAY_TIME_TEXT_CHANGE = 200;

    private RecyclerView rcHashTag;
    private HashTagMediaAdapter adapterHashTag;
    private TagGroup tagGroup;

    private Context context;

    private String filterStartDate = "", filterEndDate = "";

    private String idFilteredList = "", titleFilteredList = "";

    private Toolbar mToolbar;

    private TextView tvUserName, tvHeaderPopularNo;
    private SlidingUpPanelLayout slidingUpPanelLayout;

    private ImageView imgStartDateReset, imgEndDateReset, imgFilterClose, imgSearch;
    private TextView tvStartDate, tvEndDate;
    private EditText edtSearchFilter, edtSearchText;
    private CircularProgressButton btnConfirmFilter, btnDeleteFilter;

    private RecyclerView rcFilterCategory;

    private int endDay = 0, endMonth = 0, endYear = 0;
    private int startDay = 0, startMonth = 0, startYear = 0;

    private LinearLayout btnFilter, llDeleteFilter, llFilterHashTag;

    private View rootView;

    private NewsArchiveActionView mainNewsView;
    private ArrayList<TypeCategory> typeCategoryList = new ArrayList<>();
    private List<FilterItem> filteredCategoryList = new ArrayList<>();
    private ArrayList<FilterItem> filteredShowList = new ArrayList<>();
    private ArrayList<FilterItem> tempFilteredCategoryList = new ArrayList<>();
    private FilterArchiveAdapter adapter;
    private boolean pagerWithFilter = false;
    private boolean pagerFromFavorite = false;
    private SubMediaParent parent;
    private MediaPosition mediaPosition;

    private DatePickerDialog pickerDialogStartDate, pickerDialogEndDate;

    private PersianCalendar currentDate, startPersianDate, endPersianDate;
    private Integer startDateInt = 0, endDateInt = 0;

    public NewsArchiveFragment()
    {
    }

    public static NewsArchiveFragment newInstance(SubMediaParent parent, MediaPosition mediaPosition, boolean pagerFromFavorite,
                                                  NewsArchiveActionView mainNewsView)
    {
        NewsArchiveFragment fragment = new NewsArchiveFragment();
        fragment.setMainNewsView(mainNewsView);
        fragment.setParent(parent);
        fragment.setMediaPosition(mediaPosition);

        Bundle arg = new Bundle();

        arg.putBoolean("pagerFromFavorite", pagerFromFavorite);
        fragment.setArguments(arg);

        return fragment;
    }

    private void setParent(SubMediaParent parent) { this.parent = parent; }

    private void setMediaPosition(MediaPosition mediaPosition) { this.mediaPosition = mediaPosition; }

    private void setMainNewsView(NewsArchiveActionView mainNewsView)
    {
        this.mainNewsView = mainNewsView;
    }

    @Override
    public void onAttach(@NonNull Context context)
    {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            pagerFromFavorite = getArguments().getBoolean("pagerFromFavorite");
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
                    if (parent == SubMediaParent.MainFragment)
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
                    startActivityForResult(new Intent(SingletonContext.getInstance().getContext(), MyProfileActivity.class),100);
                })
        );

        disposable.add(RxView.clicks(mToolbar.findViewById(R.id.flLogoToolbar))
                .subscribe(v ->
                {
                    mainNewsView.backToMainFragment();
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
        slidingUpPanelLayout = rootView.findViewById(R.id.slidingLayout);
        btnFilter = rootView.findViewById(R.id.btnFilter);

        rcHashTag = rootView.findViewById(R.id.rcHashTag);
        tagGroup = rootView.findViewById(R.id.tagGroup);
        rcFilterCategory = rootView.findViewById(R.id.rcFilterCategory);
        llDeleteFilter = rootView.findViewById(R.id.llDeleteFilter);
        llFilterHashTag = rootView.findViewById(R.id.llFilterHashTag);
        imgFilterClose = rootView.findViewById(R.id.imgFilterClose);
        imgSearch = rootView.findViewById(R.id.imgSearch);
        imgStartDateReset = rootView.findViewById(R.id.imgDateFromReset);
        imgEndDateReset = rootView.findViewById(R.id.imgDateToReset);
        tvStartDate = rootView.findViewById(R.id.tvStartDate);
        tvEndDate = rootView.findViewById(R.id.tvEndDate);
        edtSearchFilter = rootView.findViewById(R.id.edtSearchFilter);
        edtSearchText = rootView.findViewById(R.id.edtSearchText);
        btnConfirmFilter = rootView.findViewById(R.id.btnConfirmFilter);
        btnDeleteFilter = rootView.findViewById(R.id.btnDeleteFilter);

        edtSearchText.requestFocus();
        hideKeyboard((Activity) context);

        rcHashTag.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));

        disposable.add(RxView.clicks(btnFilter)
                .throttleFirst(200, TimeUnit.MILLISECONDS)
                .subscribe(v ->
                {
                    slidingUpPanelLayout.setPanelState(PanelState.EXPANDED);
                    if (pagerWithFilter)
                    {
                        Logger.e("getFilterId", idFilteredList + " #List size:" + filteredCategoryList.size());
                        llDeleteFilter.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        Logger.e("getFilterId", "Empty, " + idFilteredList);
                        llDeleteFilter.setVisibility(View.GONE);
                        filteredCategoryList = new ArrayList<>();
                    }
                    if (filteredCategoryList.isEmpty())
                    {
                        filteredCategoryList = new ArrayList<>();
                        for (TypeCategory item: typeCategoryList)
                        {
                            FilterItem filterItem = new FilterItem();
                            filterItem.setId(item.getId());
                            filterItem.setTitle(item.getTitle());
                            filterItem.setChecked(false);

                            filteredCategoryList.add(filterItem);
                        }
                        Collections.reverse(filteredCategoryList);
                    }

                    tempFilteredCategoryList = new ArrayList<>();
                    tempFilteredCategoryList.addAll(filteredCategoryList);
                    adapter = new FilterArchiveAdapter(getActivity(), tempFilteredCategoryList);
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

                    if (!getFilterAvailable())
                    {
                        resetAll();
                    }
                })
        );

        disposable.add(RxView.clicks(imgSearch)
                .subscribe(v ->
                {
                    if (!edtSearchText.getText().toString().equalsIgnoreCase(""))
                    {
                        if (edtSearchText.getText().toString().trim().length() > 2)
                        {
                            titleFilteredList += "جستجو" + ",";
                            llFilterHashTag.setVisibility(View.VISIBLE);
                            setHashTag();

                            pagerWithFilter = true;

                            rootView.findViewById(R.id.tabLayout).setVisibility(View.GONE);
                            rootView.findViewById(R.id.separatorTop).setVisibility(View.GONE);
                            rootView.findViewById(R.id.separatorBottom).setVisibility(View.GONE);

                            setPager(true, false);
                        }
                        else
                        {
                            showError(getActivity(), "تعداد کاراکترهای جستجو کافی نیست!");
                        }
                    }
                    else
                    {
                        if (getFilterAvailable())
                        {
                            if (titleFilteredList.trim().length() > 0 && titleFilteredList.contains("جستجو" + ","))
                            {
                                titleFilteredList = titleFilteredList.substring(0, titleFilteredList.indexOf("جستجو" + ","));
                                Logger.e("-titleFilteredList-", titleFilteredList);
                                setHashTag();

                                pagerWithFilter = true;

                                rootView.findViewById(R.id.tabLayout).setVisibility(View.GONE);
                                rootView.findViewById(R.id.separatorTop).setVisibility(View.GONE);
                                rootView.findViewById(R.id.separatorBottom).setVisibility(View.GONE);

                                setPager(true, false);
                            }
                        }
                        else
                        {
                            resetAll();
                        }
                    }
                })
        );

        disposable.add(RxView.clicks(imgFilterClose)
                .subscribe(v ->
                {
                    slidingUpPanelLayout.setPanelState(PanelState.COLLAPSED);
                    if (!pagerWithFilter)
                    {
                        edtSearchFilter.setText("");
                        tvStartDate.setText("");
                        tvEndDate.setText("");
                        filterEndDate = "";
                        filterStartDate = "";
                        imgStartDateReset.setVisibility(View.GONE);
                        imgEndDateReset.setVisibility(View.GONE);
                        llFilterHashTag.setVisibility(View.GONE);
                    }
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
                    filterStartDate = "";
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
                    filterEndDate = "";
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

        disposable.add(RxView.clicks(btnDeleteFilter)
                .subscribe(v ->
                {
                    slidingUpPanelLayout.setPanelState(PanelState.COLLAPSED);
                    edtSearchFilter.setText("");
                    edtSearchText.setText("");
                    tvStartDate.setText("");
                    tvEndDate.setText("");
                    filterEndDate = "";
                    filterStartDate = "";
                    idFilteredList = "";
                    titleFilteredList = "";
                    imgStartDateReset.setVisibility(View.GONE);
                    imgEndDateReset.setVisibility(View.GONE);
                    llDeleteFilter.setVisibility(View.GONE);
                    llFilterHashTag.setVisibility(View.GONE);

                    adapter = new FilterArchiveAdapter(getActivity(), filteredCategoryList);
                    adapter.notifyDataSetChanged();
                    rcFilterCategory.setAdapter(adapter);
                    adapter.SetOnItemCheckedChangeListener(this);

                    rootView.findViewById(R.id.tabLayout).setVisibility(View.VISIBLE);
                    rootView.findViewById(R.id.separatorTop).setVisibility(View.VISIBLE);
                    rootView.findViewById(R.id.separatorBottom).setVisibility(View.VISIBLE);

                    if (pagerWithFilter)
                    {
                        pagerWithFilter = false;
                        SingletonService.getInstance().getNewsService().getNewsArchiveCategory(this);
                    }
                })
        );

        disposable.add(RxTextView.textChanges(edtSearchFilter)
                        .skipInitialValue()
                        .filter(charSequence ->
                        {
                            Logger.e("-text-", charSequence.length() + ": " + charSequence);

                            if (charSequence.length() < 3)
                            {
                                adapter = new FilterArchiveAdapter(getActivity(), filteredCategoryList);
                                adapter.notifyDataSetChanged();
                                rcFilterCategory.setAdapter(adapter);
                                adapter.SetOnItemCheckedChangeListener(this);
                            }
                            return charSequence.length() > 2;
                        })
                        .debounce(DELAY_TIME_TEXT_CHANGE, TimeUnit.MILLISECONDS)
                        //--------------------------
                        .flatMap((Function<CharSequence, ObservableSource<FilterItem>>) charSequence ->
                        {
                            return getNewsArchiveCategoryObservable(ReplacePersianNumberToEnglish.getEnglishChar(charSequence));

                        })
                        //--------------------------
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(getFilteredArchiveIDs())
        );

    }

    private void resetAll()
    {
        slidingUpPanelLayout.setPanelState(PanelState.COLLAPSED);
        edtSearchFilter.setText("");
        edtSearchText.setText("");
        tvStartDate.setText("");
        tvEndDate.setText("");
        filterEndDate = "";
        filterStartDate = "";
        idFilteredList = "";
        titleFilteredList = "";
        imgStartDateReset.setVisibility(View.GONE);
        imgEndDateReset.setVisibility(View.GONE);
        llDeleteFilter.setVisibility(View.GONE);
        llFilterHashTag.setVisibility(View.GONE);

        adapter = new FilterArchiveAdapter(getActivity(), filteredCategoryList);
        adapter.notifyDataSetChanged();
        rcFilterCategory.setAdapter(adapter);
        adapter.SetOnItemCheckedChangeListener(this);

        rootView.findViewById(R.id.tabLayout).setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.separatorTop).setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.separatorBottom).setVisibility(View.VISIBLE);

        if (pagerWithFilter)
        {
            pagerWithFilter = false;
            SingletonService.getInstance().getNewsService().getNewsArchiveCategory(this);
        }
    }

    private boolean getFilterAvailable()
    {
        Boolean isAvailable = true;
        if (edtSearchText.getText().toString().equalsIgnoreCase("") &&
                tvStartDate.getText().toString().equalsIgnoreCase("") &&
                tvEndDate.getText().toString().equalsIgnoreCase("") &&
                idFilteredList.equalsIgnoreCase("")
        )
        {
            isAvailable = false;
        }
        Logger.e("isFilterAvailable", String.valueOf(isAvailable));

        return isAvailable;
    }

    private void createItemFilterData()
    {
        idFilteredList = "";
        titleFilteredList = "";
        disposable.add(Observable.fromIterable(tempFilteredCategoryList)
                .filter(FilterItem::isChecked)
                .distinct()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<FilterItem>()
                {
                    @Override
                    public void onNext(FilterItem filterItem)
                    {
                        idFilteredList += filterItem.getId() + ",";
                        titleFilteredList += filterItem.getTitle() + ",";
                        filteredCategoryList.set(filteredCategoryList.indexOf(filterItem), filterItem);
                        Logger.e("-createItemFilterData-", filterItem.isChecked() + "");
//                        Logger.e("OnNext title", titleFilteredList);
                    }

                    @Override
                    public void onError(Throwable e)
                    {
                        Logger.e("showErrorMessage ", "showErrorMessage");
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
                            llFilterHashTag.setVisibility(View.GONE);

                            filteredCategoryList = new ArrayList<>();
                            for (TypeCategory item: typeCategoryList)
                            {
                                FilterItem filterItem = new FilterItem();
                                filterItem.setId(item.getId());
                                filterItem.setTitle(item.getTitle());
                                filterItem.setChecked(false);

                                filteredCategoryList.add(filterItem);
                            }

                            Logger.e("-id Filtered List-", "Empty");
                        }
                        else
                        {
                            if (!tvStartDate.getText().toString().equalsIgnoreCase("") ||
                                    !tvEndDate.getText().toString().equalsIgnoreCase(""))
                            {
                                titleFilteredList += "تاریخ" + ",";
                            }

                            if (!edtSearchText.getText().toString().equalsIgnoreCase(""))
                            {
                                titleFilteredList += "جستجو" + ",";
                            }

                            pagerWithFilter = true;
                            llDeleteFilter.setVisibility(View.VISIBLE);
                            llFilterHashTag.setVisibility(View.VISIBLE);
                            edtSearchFilter.setText("");

                            setHashTag();

                            pagerWithFilter = true;
                            setPager(true, false);

                            rootView.findViewById(R.id.tabLayout).setVisibility(View.GONE);
                            rootView.findViewById(R.id.separatorTop).setVisibility(View.GONE);
                            rootView.findViewById(R.id.separatorBottom).setVisibility(View.GONE);

                            Logger.e("-id Filtered List-", idFilteredList);

                        }
                    }
                })
        );
    }

    private void setHashTag()
    {
        String[] hashTag = titleFilteredList.substring(0, titleFilteredList.length()-1).split(",");
        List<String> values = new ArrayList<>();
        for (String item: hashTag)
        {
            values.add("#" + item);
        }
        adapterHashTag = new HashTagMediaAdapter(values);
        rcHashTag.setAdapter(adapterHashTag);

        tagGroup.setTags(values);
    }

    private Observable<FilterItem> getNewsArchiveCategoryObservable(final CharSequence sequence)
    {
        filteredShowList = new ArrayList<>();
        Observable<FilterItem> observable = Observable.fromIterable(filteredCategoryList)
                .filter(newsArchiveCategory ->
                {
                    Logger.e("-Observable-","text: " + newsArchiveCategory.getTitle().contains(sequence));
                    return newsArchiveCategory.getTitle().contains(sequence);
                })
                .subscribeOn(Schedulers.io());

        if (observable.toList().blockingGet().size() == 0)
        {
            Logger.e("-Observable-", "List is Empty");
        }

        return observable;
    }

    private DisposableObserver<FilterItem> getFilteredArchiveIDs()
    {
        rcFilterCategory = rootView.findViewById(R.id.rcFilterCategory);
        filteredShowList = new ArrayList<>();

        return new DisposableObserver<FilterItem>()
        {
            @Override
            public void onNext(FilterItem newsArchiveCategory)
            {
                filteredShowList.add(newsArchiveCategory);
                Logger.e("--searchCategory--", "Search query: " + newsArchiveCategory.getTitle());
                Logger.e("--searchCategory--", "Query size: " + filteredShowList.size());

                Collections.reverse(filteredShowList);
                adapter = new FilterArchiveAdapter(getActivity(), filteredShowList);
                adapter.notifyDataSetChanged();
                rcFilterCategory.setAdapter(adapter);
                adapter.SetOnItemCheckedChangeListener(NewsArchiveFragment.this);
            }

            @Override
            public void onError(Throwable e)
            {
                Logger.e("--searchCategory--", "showErrorMessage: " + e.getMessage());
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
                currentDate.getPersianMonth() ,
                currentDate.getPersianDay()
        );
        pickerDialogStartDate.setTitle("انتخاب تاریخ شروع");

        startDay = currentDate.getPersianDay();
        startMonth = currentDate.getPersianMonth() ;
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
            SamplePagerAdapter adapter = new SamplePagerAdapter(getFragmentManager(),
                    null,
                    true,
                    pagerWithFilter
            );

            pager.setAdapter(adapter);

            pager.setCurrentItem(0);
        }
        else
        {
            Collections.reverse(typeCategoryList);

            SamplePagerAdapter adapter = new SamplePagerAdapter(getFragmentManager(),
                    typeCategoryList,
                    false,
                    pagerWithFilter
            );

            pager.setAdapter(adapter);

            TabLayout tabLayout = rootView.findViewById(R.id.tabLayout);
            tabLayout.setupWithViewPager(pager);

            for (int i = 0; i < tabLayout.getTabCount(); i++)
            {
                TabLayout.Tab tab = tabLayout.getTabAt(i);
                tab.setCustomView(adapter.getTabView(i));
            }

            pager.setCurrentItem(typeCategoryList.size()-1);
        }

    }


    @Override
    public void onReady(WebServiceClass<MediaArchiveCategoryResponse> response)
    {
        try{
            if (response.info.statusCode != 200)
            {
                showError(getActivity(), response.info.message);
            }
            else
            {
                typeCategoryList = response.data.getTypeCategoryList();

                setPager(pagerWithFilter, pagerFromFavorite);
            }
        }catch (Exception e){}

    }

    @Override
    public void onError(String message)
    {
        if (Tools.isNetworkAvailable((Activity) context))
        {
            Logger.e("-OnError-", "Error: " + message);
            showError(getActivity(), "خطا در دریافت اطلاعات از سرور!");
        }
        else
        {
            showAlert(getActivity(), R.string.networkErrorMessage, R.string.networkError);
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth, int endYear, int endMonth, int endDay)
    {
//        llDeleteFilter.setVisibility(View.VISIBLE);

        if (view.getTag().equals("StartDate"))
        {
            startPersianDate.set(year, monthOfYear, dayOfMonth);
            pickerDialogEndDate.setMinDate(startPersianDate);
            pickerDialogEndDate.setMaxDate(currentDate);
            startDateInt = getDateInt(year, monthOfYear, dayOfMonth);

            if (startDateInt > endDateInt)
            {
                tvEndDate.setText("");
                filterEndDate = "";
                imgEndDateReset.setVisibility(View.GONE);
            }

            filterStartDate = year + "/" + Utility.getFormatDateMonth(monthOfYear + 1) + "/" + Utility.getFormatDateMonth(dayOfMonth);
            tvStartDate.setText(filterStartDate);
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
                filterStartDate = "";
                imgStartDateReset.setVisibility(View.GONE);
                startDay = 0;
                startMonth = 0;
                startYear = 0;
            }

            filterEndDate = year + "/" + Utility.getFormatDateMonth(monthOfYear + 1) + "/" + Utility.getFormatDateMonth(dayOfMonth);
            tvEndDate.setText(filterEndDate);
            imgEndDateReset.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onItemCheckedChange(Integer id, boolean isChecked, FilterItem filterItem)
    {

        Logger.e("-filter Selected-", id + ", " + filterItem.getTitle() + ", " + !isChecked);

        disposable.add(Observable.fromIterable(tempFilteredCategoryList)
                .filter(new Predicate<FilterItem>()
                {
                    @Override
                    public boolean test(FilterItem fFilterItem) throws Exception
                    {
                        return fFilterItem.getId() == id;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<FilterItem>()
                {
                    @Override
                    public void onNext(FilterItem fFilterItem)
                    {
                        int index = tempFilteredCategoryList.indexOf(fFilterItem);
                        Logger.e("-change-", "isChecked: " +  isChecked);
                        tempFilteredCategoryList.set(index, filterItem);
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
        private ArrayList<TypeCategory> newsArchiveCategories;
        private boolean pagerWithFilter = false;
        private boolean pagerFromFavorite = false;
        private Context context = SingletonContext.getInstance().getContext();

        @SuppressLint("WrongConstant")
        public SamplePagerAdapter(@NonNull FragmentManager fm,
                                  ArrayList<TypeCategory> newsArchiveCategories,
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
            if (pagerWithFilter)
            {
                return "";
            }
            else
            {
                try
                {
                    return newsArchiveCategories.get(position).getTitle();
                } catch (NullPointerException e)
                {
                    return "";
                }
            }
        }

        @NonNull
        @Override
        public Fragment getItem(int position)
        {
            if (pagerWithFilter)
            {
                String startDate = filterStartDate.equalsIgnoreCase("") ? "" : Utility.getGrgDate(filterStartDate);
                String endDate = filterEndDate.equalsIgnoreCase("") ? "" : Utility.getGrgDate(filterEndDate);

                return NewsArchiveCategoryFragment.newInstance(idFilteredList.trim(), //Id Filter List
                        MediaArchiveCategoryCall.FROM_FILTER_IDs,
                        startDate,
                        endDate,
                        edtSearchText.getText().toString().trim(),
                        null
                );
            }
            else if (pagerFromFavorite)
            {
                rootView.findViewById(R.id.llFilterAndTab).setVisibility(View.GONE);

//                return NewsArchiveCategoryFragment.newInstance(0, false, true, null);
                return NewsArchiveCategoryFragment.newInstance("",
                        MediaArchiveCategoryCall.FROM_FAVORITE,
                        null,
                        null,
                        null,
                        null
                );
            }
            else
            {
                int Id =  typeCategoryList.get(position).getId();
                Logger.e("--nID--", "pos: " + position + ", ID:" + Id);
                return NewsArchiveCategoryFragment.newInstance(String.valueOf(Id),
                        MediaArchiveCategoryCall.FROM_ID,
                        null,
                        null,
                        null,
                        null
                );
            }
        }

        @Override
        public int getCount()
        {
            if (pagerWithFilter)
            {
                return 1;
            }
            else
            {
                try
                {
                    return newsArchiveCategories.size();
                } catch (NullPointerException e)
                {
                    return 1;
                }
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
