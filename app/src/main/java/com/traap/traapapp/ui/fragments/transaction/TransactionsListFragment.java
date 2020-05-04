package com.traap.traapapp.ui.fragments.transaction;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jakewharton.rxbinding3.view.RxView;
import com.jakewharton.rxbinding3.widget.RxTextView;
import com.jaygoo.widget.OnRangeChangedListener;
import com.jaygoo.widget.RangeSeekBar;
import com.pixplicity.easyprefs.library.Prefs;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.getTransaction.FilterTransactionSetting;
import com.traap.traapapp.apiServices.model.getTransaction.ResponseTransaction;
import com.traap.traapapp.apiServices.model.media.category.TypeCategory;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.models.otherModels.headerModel.HeaderModel;
import com.traap.traapapp.models.otherModels.newsFilterItem.FilterItem;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.utilities.ClearableEditText;
import com.traap.traapapp.utilities.TagGroup;
import com.traap.traapapp.ui.activities.myProfile.MyProfileActivity;
import com.traap.traapapp.ui.adapters.filterArchive.FilterArchiveAdapter;
import com.traap.traapapp.ui.adapters.media.HashTagMediaAdapter;
import com.traap.traapapp.ui.adapters.transaction.TransactionListAdapter;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.dialogs.MessageAlertDialog;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.utilities.KeyboardUtils;
import com.traap.traapapp.utilities.Logger;
import com.traap.traapapp.utilities.NestedScrollableViewHelper;
import com.traap.traapapp.utilities.ReplacePersianNumberToEnglish;
import com.traap.traapapp.utilities.Tools;
import com.traap.traapapp.utilities.Utility;
import com.traap.traapapp.utilities.calendar.mohamadamin_t.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.traap.traapapp.utilities.calendar.mohamadamin_t.persianmaterialdatetimepicker.utils.PersianCalendar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class TransactionsListFragment extends BaseFragment implements DatePickerDialog.OnDateSetListener,
        FilterArchiveAdapter.OnItemCheckedChangeListener, CompoundButton.OnCheckedChangeListener,
        OnServiceStatus<WebServiceClass<ResponseTransaction>>, OnRangeChangedListener
{
    private RangeSeekBar rangeBar;
    private TextView tvMaxPrice, tvMinPrice, tvEmpty;

    private int MAX_PRICE_DEFAULT = 10000000;
    private int MIN_PRICE_DEFAULT = 0;

    private Integer maxPrice = MAX_PRICE_DEFAULT;
    private Integer minPrice = MIN_PRICE_DEFAULT;
    private Integer priceInterval = 500000;

    private FilterTransactionSetting filterSetting;

    private CompositeDisposable disposable = new CompositeDisposable();
    private final int DELAY_TIME_TEXT_CHANGE = 200;

    private TransactionListAdapter fixTableAdapter;

    private RecyclerView rcHashTag;
    private HashTagMediaAdapter adapterHashTag;
    private Context context;

    private CheckBox chbSuccessPayment, chbFailedPayment;

    private String filterStartDate = "", filterEndDate = "";

    private String idFilteredList = "", titleFilteredList = "";

    private Toolbar mToolbar;

    private TextView tvUserName, tvHeaderPopularNo, tvCount;
    private SlidingUpPanelLayout slidingUpPanelLayout;

    private ImageView imgStartDateReset, imgEndDateReset, imgFilterClose, imgSearch;
    private TextView tvStartDate, tvEndDate;
    private EditText edtSearchFilter;
    private ClearableEditText edtSearchText;
    private CircularProgressButton btnConfirmFilter, btnDeleteFilter;

    private RecyclerView rcFilterCategory, rcTransactionList;

    private int endDay = 0, endMonth = 0, endYear = 0;
    private int startDay = 0, startMonth = 0, startYear = 0;

    private LinearLayout btnFilter, llDeleteFilter, llFilterHashTag;

    private View rootView;

    private MainActionView mainView;
    private ArrayList<TypeCategory> typeCategoryList = new ArrayList<>();
    private List<FilterItem> filteredCategoryList = new ArrayList<>();
    private ArrayList<FilterItem> filteredShowList = new ArrayList<>();
    private ArrayList<FilterItem> tempFilteredCategoryList = new ArrayList<>();
    private FilterArchiveAdapter adapter;
    private boolean isFilterEnable = false;

    private DatePickerDialog pickerDialogStartDate, pickerDialogEndDate;

    private PersianCalendar currentDate, startPersianDate, endPersianDate;
    private Integer startDateInt = 0, endDateInt = 0;
    private TagGroup tagGroup;

    private List<String> tags = new ArrayList<>();

    public TransactionsListFragment()
    {
    }

    public static TransactionsListFragment newInstance(MainActionView mainView)
    {
        TransactionsListFragment f = new TransactionsListFragment();

        Bundle args = new Bundle();
        f.setArguments(args);
        f.setMainView(mainView);
        return f;
    }

    private void setMainView(MainActionView mainView)
    {
        this.mainView = mainView;
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
//        if (getArguments() != null)
//        {
////            teamId = getArguments().getString("teamId");
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_transaction_list, container, false);

        getTypeTransactionList();

        initView();

        EventBus.getDefault().register(this);
        return rootView;
    }

    private void getTypeTransactionList()
    {
        mainView.showLoading();

        SingletonService.getInstance().getTransactionService().getTypeTransactionList(new OnServiceStatus<WebServiceClass<ArrayList<TypeCategory>>>()
        {
            @Override
            public void onReady(WebServiceClass<ArrayList<TypeCategory>> response)
            {
                try
                {
                    mainView.hideLoading();

                    if (response.info.statusCode != 200)
                    {
                        showAlertAndFinish(response.info.message);
                    }
                    else
                    {
                        if (response.data.isEmpty())
                        {
                            showAlertAndFinish("خطا در دریافت اطلاعات از سرور!");
                        }
                        else
                        {
                            typeCategoryList = response.data;
                            getData(false);
                        }
                    }
                }
                catch (Exception e)
                {
                    showAlertAndFinish(response.info.message);
                }
            }

            @Override
            public void onError(String message)
            {
                mainView.hideLoading();

                if (Tools.isNetworkAvailable(Objects.requireNonNull(getActivity())))
                {
                    TransactionsListFragment.this.onError(message);
                }
                else
                {
                    TransactionsListFragment.this.onError(getString(R.string.networkErrorMessage));
                }
            }
        });
    }

    public void initView()
    {
        try
        {
            mToolbar = rootView.findViewById(R.id.toolbar);

            try
            {
                ((TextView) mToolbar.findViewById(R.id.tvTitle)).setText(Prefs.getString("menu_transfer", "سوابق تراکنش"));
            }
            catch (Throwable tx)
            {
            }

            disposable.add(RxView.clicks(mToolbar.findViewById(R.id.imgBack))
                    .subscribe(v ->
                    {
//                        mainView.backToMainFragment();
                        if (slidingUpPanelLayout.getPanelState() != SlidingUpPanelLayout.PanelState.COLLAPSED)
                        {
                            slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                        }
                        else
                        {
                            getActivity().onBackPressed();
                        }
                    })
            );

            disposable.add(RxView.clicks(mToolbar.findViewById(R.id.flLogoToolbar))
                    .subscribe(v ->
                    {
                        mainView.backToMainFragment();
                    })
            );

            disposable.add(RxView.clicks(mToolbar.findViewById(R.id.rlShirt))
                    .subscribe(v ->
                    {
                        startActivityForResult(new Intent(SingletonContext.getInstance().getContext(), MyProfileActivity.class), 100);
                    })
            );

            disposable.add(RxView.clicks(mToolbar.findViewById(R.id.imgMenu))
                    .subscribe(v ->
                    {
                        mainView.openDrawer();
                    })
            );

            tvUserName = mToolbar.findViewById(R.id.tvUserName);
            tvUserName.setText(TrapConfig.HEADER_USER_NAME);
            tvHeaderPopularNo = mToolbar.findViewById(R.id.tvPopularPlayer);
            tvHeaderPopularNo.setText(String.valueOf(Prefs.getInt("popularPlayer", 12)));

            rangeBar = rootView.findViewById(R.id.rangeBar);
            tvMaxPrice = rootView.findViewById(R.id.tvMaxPrice);
            tvMinPrice = rootView.findViewById(R.id.tvMinPrice);
            tagGroup = rootView.findViewById(R.id.tagGroup);

            tvEmpty = rootView.findViewById(R.id.tvEmpty);

            rcHashTag = rootView.findViewById(R.id.rcHashTag);
            rcFilterCategory = rootView.findViewById(R.id.rcFilterCategory);
            rcTransactionList = rootView.findViewById(R.id.rcTransactionList);
            llDeleteFilter = rootView.findViewById(R.id.llDeleteFilter);
            llFilterHashTag = rootView.findViewById(R.id.llFilterHashTag);
            imgFilterClose = rootView.findViewById(R.id.imgFilterClose);
            imgSearch = rootView.findViewById(R.id.imgSearch);
            imgStartDateReset = rootView.findViewById(R.id.imgDateFromReset);
            imgEndDateReset = rootView.findViewById(R.id.imgDateToReset);
            tvStartDate = rootView.findViewById(R.id.tvStartDate);
            tvEndDate = rootView.findViewById(R.id.tvEndDate);
            edtSearchFilter = rootView.findViewById(R.id.edtSearchFilter);
            edtSearchText = rootView.findViewById(R.id.etSearchText);
            btnConfirmFilter = rootView.findViewById(R.id.btnConfirmFilter);
            btnDeleteFilter = rootView.findViewById(R.id.btnDeleteFilter);

            chbSuccessPayment = rootView.findViewById(R.id.chbSuccessPayment);
            chbFailedPayment = rootView.findViewById(R.id.chbFailedPayment);

//            edtSearchText.setInputType(InputType.TYPE_CLASS_NUMBER);
            edtSearchText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
            chbSuccessPayment.setOnCheckedChangeListener(this);
            chbFailedPayment.setOnCheckedChangeListener(this);

            tvCount = rootView.findViewById(R.id.tvCount);


//            tvMaxPrice.setText("10,000,000 ریال");
//            tvMinPrice.setText("0 ریال");

//            rangeBar.setRange(0f, filterSetting.getStepCount(), 1f);
//            rangeBar.setProgress(0f, filterSetting.getStepCount());
//            rangeBar.setIndicatorTextDecimalFormat("0");
//            rangeBar.setOnRangeChangedListener(this);

//            edtSearchText.requestFocus();

            hideKeyboard((Activity) context);

          //  rcHashTag.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));

            slidingUpPanelLayout = rootView.findViewById(R.id.slidingLayout);
            slidingUpPanelLayout.setScrollableViewHelper(new NestedScrollableViewHelper());

            btnFilter = rootView.findViewById(R.id.btnFilter);

            disposable.add(RxView.clicks(btnFilter)
                    .throttleFirst(200, TimeUnit.MILLISECONDS)
                    .subscribe(v ->
                    {
                        KeyboardUtils.forceCloseKeyboard(btnFilter);
                        slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                        if (isFilterEnable)
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

            slidingUpPanelLayout.setFadeOnClickListener(v ->
            {
                slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            });

            disposable.add(RxView.clicks(btnConfirmFilter)
                    .subscribe(v ->
                    {
                        KeyboardUtils.forceCloseKeyboard(btnConfirmFilter);
                        slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);

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
                        KeyboardUtils.forceCloseKeyboard(imgSearch);
                        if (!edtSearchText.getText().toString().equalsIgnoreCase(""))
                        {
                            if (edtSearchText.getText().toString().trim().length() > 2)
                            {
                                if (!titleFilteredList.contains("جستجو" + ","))
                                {
                                    titleFilteredList += "جستجو" + ",";
                                }
                                llFilterHashTag.setVisibility(View.VISIBLE);
                                setHashTag();

                                isFilterEnable = true;

//                                getData(true);
                                getSearchData();
                            }
                            else
                            {
                                showAlert(context, "تعداد کاراکترهای جستجو کافی نیست!", R.string.error);
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

                                    isFilterEnable = true;

//                                    setPager(true, false);
                                    getData(true);
                                }
                            }
                            else
                            {
                                resetAll();
                            }
                        }
                    })
            );

            edtSearchText.setListener(() ->
            {
                KeyboardUtils.forceCloseKeyboard(edtSearchText);
                if (getFilterAvailable())
                {
                    if (titleFilteredList.trim().length() > 0 && titleFilteredList.contains("جستجو" + ","))
                    {
                        titleFilteredList = titleFilteredList.substring(0, titleFilteredList.indexOf("جستجو" + ","));
                        Logger.e("-titleFilteredList-", titleFilteredList);
                        setHashTag();

                        isFilterEnable = true;

//                                    setPager(true, false);
                        getData(true);
                    }
                }
                else
                {
                    resetAll();
                }
            });

            disposable.add(RxView.clicks(imgFilterClose)
                    .doOnError(throwable -> Logger.e("-imgFilterClose-", "Error: " + throwable.getMessage()))
                    .subscribe(v ->
                    {
                        slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                        if (!isFilterEnable)
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

            disposable.add(RxView.clicks(btnDeleteFilter)
                    .subscribe(v ->
                    {
                        KeyboardUtils.forceCloseKeyboard(btnDeleteFilter);
                        slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
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

                        if (isFilterEnable)
                        {
                            chbSuccessPayment.setChecked(true);
                            chbFailedPayment.setChecked(true);

                            isFilterEnable = false;
//                            SingletonService.getInstance().getNewsService().getNewsArchiveCategory(this);
                            getData(false);
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

            initDatePicker();

//            tvStartDate.setOnClickListener(v -> pickerDialogStartDate.show(getFragmentManager(), "StartDate"));
//            tvEndDate.setOnClickListener(v -> pickerDialogEndDate.show(getFragmentManager(), "EndDate"));

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

        }
        catch (Exception e)
        {
            Logger.e("-Exception-", "message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void getSearchData()
    {
        SingletonService.getInstance().getTransactionService().getTransactionListBySearch(edtSearchText.getText().toString().trim(), this);
    }

    private void getData(boolean isFiltered)
    {
        mainView.showLoading();

        if (!isFiltered)
        {
            SingletonService.getInstance().getTransactionService().getTransactionList(this);
        }
        else
        {
            if (chbFailedPayment.isChecked() != chbSuccessPayment.isChecked())
            {
                boolean status = false;
                if (chbSuccessPayment.isChecked())
                {
                    status = true;
                }
                else if (chbFailedPayment.isChecked())
                {
                    status = false;
                }

                SingletonService.getInstance().getTransactionService().getTransactionListByFilter(
                        idFilteredList,
                        minPrice,
                        maxPrice,
                        tvStartDate.getText().toString().trim().equalsIgnoreCase("") ? "" : Utility.getGrgDate(tvStartDate.getText().toString()),
                        tvEndDate.getText().toString().trim().equalsIgnoreCase("") ? "" : Utility.getGrgDate(tvEndDate.getText().toString()),
                        status,
//                        edtSearchText.getText().toString().trim(),
                        this
                );
            }
            else
            {
                SingletonService.getInstance().getTransactionService().getTransactionListByFilterForAllStatus(
                        idFilteredList,
                        minPrice,
                        maxPrice,
                        tvStartDate.getText().toString().trim().equalsIgnoreCase("") ? "" : Utility.getGrgDate(tvStartDate.getText().toString()),
                        tvEndDate.getText().toString().trim().equalsIgnoreCase("") ? "" : Utility.getGrgDate(tvEndDate.getText().toString()),
//                        edtSearchText.getText().toString().trim(),
                        this
                );
            }
        }
    }


    private void initDatePicker()
    {
        try
        {
            currentDate = new PersianCalendar();

            int currentMonth = currentDate.getPersianMonth();
            int maxDayOfMount = 31;
            if (currentMonth == 0)
            {
                maxDayOfMount = 29;
            }
            else if (currentMonth == 6)
            {
                maxDayOfMount = 30;
            }

            pickerDialogStartDate = DatePickerDialog.newInstance(this,
                    currentMonth == 0 ? (currentDate.getPersianYear() - 1) : currentDate.getPersianYear(),
                    currentMonth == 0 ? 11 : currentMonth,
                    Math.min(maxDayOfMount , currentDate.getPersianDay())

            );
            pickerDialogStartDate.setTitle("انتخاب تاریخ شروع");

            startDay = Math.min(maxDayOfMount , currentDate.getPersianDay());
            startMonth = currentMonth == 0 ? 11 : currentMonth;
            startYear = currentMonth == 0 ? (currentDate.getPersianYear() - 1) : currentDate.getPersianYear();

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
        catch (Exception e)
        {
            Logger.e("--initDatePicker Ex--", "Exception: " + e.getMessage());
        }

    }

    private Integer getDateInt(int year, int month, int day)
    {
        return (year - year/100)*10000 + month*100 + day;
    }

    private void resetAll()
    {
        slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
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

//        maxPrice = MAX_PRICE_DEFAULT;
//        minPrice = 0;
        maxPrice = filterSetting.getMaxAmount();
        minPrice = filterSetting.getMinAmount();
//        tvMaxPrice.setText("10,000,000 ریال");
//        tvMinPrice.setText("0 ریال");
        tvMaxPrice.setText(Utility.priceFormat(filterSetting.getMaxAmount()) + " ریال");
        tvMinPrice.setText(Utility.priceFormat(filterSetting.getMinAmount()) + " ریال");
        rangeBar.setProgress(0f, filterSetting.getStepCount());

        chbSuccessPayment.setChecked(true);
        chbFailedPayment.setChecked(true);

        adapter = new FilterArchiveAdapter(getActivity(), filteredCategoryList);
        adapter.notifyDataSetChanged();
        rcFilterCategory.setAdapter(adapter);
        adapter.SetOnItemCheckedChangeListener(this);

        if (isFilterEnable)
        {
            isFilterEnable = false;

            getData(false);
        }
    }

    private void showAlertAndFinish(String message)
    {
        try
        {
            MessageAlertDialog dialog = new MessageAlertDialog((Activity) context, getString(R.string.error), message,
                    false, "تایید", "", MessageAlertDialog.TYPE_ERROR, new MessageAlertDialog.OnConfirmListener()
            {
                @Override
                public void onConfirmClick()
                {
                    mainView.backToMainFragment();
                }

                @Override
                public void onCancelClick()
                {

                }
            });
            dialog.setCancelable(false);
            dialog.show(((Activity) context).getFragmentManager(), "dialog");
        }
        catch (Exception e)
        {

        }
    }

    private boolean getFilterAvailable()
    {
        Boolean isAvailable = true;
        if (edtSearchText.getText().toString().equalsIgnoreCase("") &&
                tvStartDate.getText().toString().equalsIgnoreCase("") &&
                tvEndDate.getText().toString().equalsIgnoreCase("") &&
                idFilteredList.equalsIgnoreCase("") &&
                chbSuccessPayment.isChecked() == chbFailedPayment.isChecked() &&
                minPrice == MIN_PRICE_DEFAULT &&
                maxPrice == MAX_PRICE_DEFAULT )
        {
            isAvailable = false;
        }
        Logger.e("isFilterAvailable", String.valueOf(isAvailable));
        Logger.e("-rangeBar-", "left:" + minPrice + ", right:" + maxPrice);

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
                                if (!getFilterAvailable())
                                {
                                    isFilterEnable = false;
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
                                        if (!titleFilteredList.contains("جستجو" + ","))
                                        {
                                            titleFilteredList += "جستجو" + ",";
                                        }
                                    }

                                    if (chbSuccessPayment.isChecked() !=  chbFailedPayment.isChecked())
                                    {
                                        titleFilteredList += "نوع پرداخت" + ",";
                                    }

                                    if (minPrice != MIN_PRICE_DEFAULT || maxPrice != MAX_PRICE_DEFAULT)
                                    {
                                        titleFilteredList += "مبلغ" + ",";
                                    }

                                    isFilterEnable = true;
                                    llDeleteFilter.setVisibility(View.VISIBLE);
                                    llFilterHashTag.setVisibility(View.VISIBLE);
                                    edtSearchFilter.setText("");

                                    setHashTag();

//                                    setPager(true, false);
                                    getData(true);

                                    Logger.e("-id Filtered List-", idFilteredList);

                                }
                            }
                        })
        );
    }

    private void setHashTag()
    {
        try
        {
            String[] hashTag = titleFilteredList.substring(0, titleFilteredList.length()-1).split(",");
            List<String> values = new ArrayList<>();
            for (String item: hashTag)
            {
                values.add("#" + item);
            }
 /*           adapterHashTag = new HashTagMediaAdapter(values);
            rcHashTag.setAdapter(adapterHashTag);*/
            llFilterHashTag.setVisibility(View.VISIBLE);

//            tagGroup.setTags(hashTag);
            tagGroup.setTags(values);
        }
        catch (Exception e)
        {
            //Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();

        }
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
                adapter.SetOnItemCheckedChangeListener(TransactionsListFragment.this);
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
                .filter(fFilterItem -> fFilterItem.getId() == id)
                .doOnNext(fFilterItem ->
                {
                    int index = tempFilteredCategoryList.indexOf(fFilterItem);
                    Logger.e("-change-", "isChecked: " +  isChecked);
                    tempFilteredCategoryList.set(index, filterItem);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe());
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
    public void onReady(WebServiceClass<ResponseTransaction> response)
    {
        try
        {
            mainView.hideLoading();

            if (response.info.statusCode != 200)
            {
                showAlertAndFinish(response.info.message);
                tvEmpty.setVisibility(View.GONE);
            }
            else
            {
                if (response.data.getTransactionLists().isEmpty())
                {
                    tvEmpty.setVisibility(View.VISIBLE);
                    tvCount.setVisibility(View.INVISIBLE);
                    rcTransactionList.setAdapter(null);
                }
                else
                {
                    tvEmpty.setVisibility(View.GONE);

                    tvCount.setText(response.data.getTransactionLists().size() + " مورد تراکنش یافت شد.");
                    tvCount.setVisibility(View.VISIBLE);

                    rcTransactionList.setLayoutManager(new LinearLayoutManager(getContext()));
                    fixTableAdapter = new TransactionListAdapter(response.data.getTransactionLists(), context);
                    rcTransactionList.setAdapter(fixTableAdapter);
                }
                filterSetting = response.data.getTransactionSetting().getFilters();
                MIN_PRICE_DEFAULT = filterSetting.getMinAmount();
                MAX_PRICE_DEFAULT = filterSetting.getMaxAmount();
                priceInterval = (filterSetting.getMaxAmount() - filterSetting.getMinAmount()) / filterSetting.getStepCount();

                rangeBar.setRange(0f, filterSetting.getStepCount(), 1f);
                rangeBar.setProgress(0f, filterSetting.getStepCount());
                rangeBar.setIndicatorTextDecimalFormat("0");
                rangeBar.setOnRangeChangedListener(this);

                tvMaxPrice.setText(Utility.priceFormat(filterSetting.getMaxAmount()) + " ریال");
                tvMinPrice.setText(Utility.priceFormat(filterSetting.getMinAmount()) + " ریال");
            }
        }
        catch (Exception e)
        {
            Logger.e("-Exception GetData-", e.getMessage());
            e.printStackTrace();
        }
    }


    @Override
    public void onError(String message)
    {
        mainView.hideLoading();
        if (Tools.isNetworkAvailable((Activity) context))
        {
            Logger.e("-OnError-", "Error: " + message);
            showAlertAndFinish("خطا در دریافت اطلاعات از سرور!");
        }
        else
        {
            showAlertAndFinish(getString(R.string.networkErrorMessage));
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
    {
        if (chbFailedPayment.isChecked() != chbSuccessPayment.isChecked())
        {
            isFilterEnable = true;
        }
        else
        {
//            if (!getFilterAvailable())
//            {
//                isFilterEnable = false;
//            }
        }
    }

    @Override
    public void onRangeChanged(RangeSeekBar view, float leftValue, float rightValue, boolean isFromUser)
    {
        int left = (int) leftValue;
        int right = (int) rightValue;
        tvMaxPrice.setText(Utility.priceFormat(right * priceInterval + MIN_PRICE_DEFAULT) + " ریال");
        tvMinPrice.setText(Utility.priceFormat(left * priceInterval + MIN_PRICE_DEFAULT) + " ریال");
        maxPrice = right * priceInterval + MIN_PRICE_DEFAULT;
        minPrice = left * priceInterval + MIN_PRICE_DEFAULT;
        Logger.e("-onRangeChanged-", "left: " + leftValue + " ,right: " + rightValue);
        Logger.e("-onRangeChanged2-", "left: " + minPrice + " ,right: " + maxPrice);
    }

    @Override
    public void onStartTrackingTouch(RangeSeekBar view, boolean isLeft)
    {

    }

    @Override
    public void onStopTrackingTouch(RangeSeekBar view, boolean isLeft)
    {

    }

    @Override
    public void onDestroyView()
    {
        disposable.clear();
        super.onDestroyView();
    }

    @Override
    public void onDestroy()
    {
        disposable.clear();
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

}
