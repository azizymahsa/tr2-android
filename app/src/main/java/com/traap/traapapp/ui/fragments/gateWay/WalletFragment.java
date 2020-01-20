package com.traap.traapapp.ui.fragments.gateWay;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.jaygoo.widget.OnRangeChangedListener;
import com.jaygoo.widget.RangeSeekBar;

import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.pixplicity.easyprefs.library.Prefs;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;

import com.traap.traapapp.apiServices.model.contact.OnSelectContact;
import com.traap.traapapp.apiServices.model.getBalancePasswordLess.GetBalancePasswordLessRequest;
import com.traap.traapapp.apiServices.model.getBalancePasswordLess.GetBalancePasswordLessResponse;
import com.traap.traapapp.apiServices.model.getInfoWallet.GetInfoWalletResponse;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.singleton.SingletonContext;

import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.ui.activities.myProfile.MyProfileActivity;
import com.traap.traapapp.ui.fragments.turnover.ClickTurnOverEvent;
import com.traap.traapapp.ui.fragments.turnover.EventTurnoverModel;
import com.traap.traapapp.utilities.Logger;
import com.traap.traapapp.utilities.Tools;
import com.traap.traapapp.utilities.Utility;
import com.traap.traapapp.utilities.calendar.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.traap.traapapp.utilities.calendar.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Objects;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

/**
 * Created by MahtabAzizi on 12/8/2019.
 */
public class WalletFragment extends BaseFragment implements View.OnClickListener, OnRangeChangedListener, DatePickerDialog.OnDateSetListener {
    private static int MAX_PRICE_DEFAULT = 10000000;
    private MainActionView mainView;
    private View rootView;
    private TextView tvBalance, tvDate;
    private View rlShirt;
    private ImageView imgCart;
    private View imgBack, imgMenu;
    private TextView tvTitle, tvUserName, tvHeaderPopularNo;
    private TextView txtFullName, customNo, cartNo;
    private View btnForgetPass;
    private View btnBack, rlImageProfile, incBackCart, incFrontCart, lnrInventory;
    private NestedScrollView scrollView;

    private Fragment fragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private int instanceBack = 0;
    private SlidingUpPanelLayout slidingLayout;
    private Integer maxPrice = MAX_PRICE_DEFAULT;
    private Integer minPrice = 0;
    private TextView tvMaxPrice, tvMinPrice, tvEmpty;
    private RangeSeekBar rangeBar;
    private TextView tvStartDate, tvEndDate;
    private DatePickerDialog pickerDialogStartDate, pickerDialogEndDate;
    private PersianCalendar currentDate, startPersianDate, endPersianDate;
    private Integer startDateInt = 0, endDateInt = 0;
    private int endDay = 0, endMonth = 0, endYear = 0;
    private int startDay = 0, startMonth = 0, startYear = 0;
    private String filterStartDate = "", filterEndDate = "";
    private ImageView imgStartDateReset, imgEndDateReset, imgFilterClose, imgSearch,ivRefreshing;
    private CircularProgressButton btnConfirmFilter, btnDeleteFilter;
    private String idFilteredList = "", titleFilteredList = "";
    private String TitleFragment="کیف پول";

    public WalletFragment() {

    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    public static WalletFragment newInstance(MainActionView mainView) {
        WalletFragment fragment = new WalletFragment();
        fragment.setMainView(mainView);
        return fragment;
    }

    public static Fragment newInstance(MainActionView mainView, int i) {

        WalletFragment fragment = new WalletFragment();
        fragment.setMainView(mainView);
        fragment.setInstance(i);
        return fragment;
    }

    private void setInstance(int i) {
        this.instanceBack = i;

    }

    private void setMainView(MainActionView mainView) {
        this.mainView = mainView;
    }
    public  void setTitleFragmentWallet(String Title, MainActionView mainView) {
        //this.TitleFragment = Title;
        try
        {
            tvTitle.setText(Title);

        }catch (Exception e){
            e.getMessage();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        YoYo.with(Techniques.FadeIn)
                .duration(700)
                .playOn(rootView);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView != null)
            return rootView;
        rootView = inflater.inflate(R.layout.fragment_wallet, container, false);
        initView();

        mainView.showLoading();

        requestGetBalance();
        requestGetInfo();
        initDatePicker();

        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY - oldScrollY > 0) {
                    Animation animHide = AnimationUtils.loadAnimation(getContext(), R.anim.hide_button);
                    rootView.findViewById(R.id.rlImageProfile).startAnimation(animHide);
                    rootView.findViewById(R.id.rlImageProfile).setVisibility(View.GONE);
                } else {
                    Animation animShow = AnimationUtils.loadAnimation(getContext(), R.anim.show_button);
                    rootView.findViewById(R.id.rlImageProfile).startAnimation(animShow);
                    rootView.findViewById(R.id.rlImageProfile).setVisibility(View.VISIBLE);
                }
            }
        });
        if (instanceBack == 0) {
            fragmentManager = getChildFragmentManager();

            fragment = DetailsCartFragment.newInstance(mainView);
            transaction = fragmentManager.beginTransaction();

            transaction.replace(R.id.container, fragment, "DetailsCartFragment")
                    .commit();
        }
        if (instanceBack == 1) {
            fragmentManager = getChildFragmentManager();

            fragment = IncreaseInventoryFragment.newInstance(mainView);
            transaction = fragmentManager.beginTransaction();

            transaction.replace(R.id.container, fragment, "IncreaseInventoryFragment")
                    .commit();
        }
        return rootView;
    }


    private void initView() {
        try {
            rlShirt = rootView.findViewById(R.id.rlShirt);
            slidingLayout = rootView.findViewById(R.id.slidingLayout);
            rangeBar = rootView.findViewById(R.id.rangeBar);
            imgEndDateReset = rootView.findViewById(R.id.imgDateToReset);
            imgStartDateReset = rootView.findViewById(R.id.imgDateFromReset);
            ivRefreshing = rootView.findViewById(R.id.ivRefreshing);
            tvMaxPrice = rootView.findViewById(R.id.tvMaxPrice);
            tvMinPrice = rootView.findViewById(R.id.tvMinPrice);
            tvEmpty = rootView.findViewById(R.id.tvEmpty);
            btnConfirmFilter = rootView.findViewById(R.id.btnConfirmFilter);
            btnDeleteFilter = rootView.findViewById(R.id.btnDeleteFilter);
            btnConfirmFilter = rootView.findViewById(R.id.btnConfirmFilter);

            tvTitle = rootView.findViewById(R.id.tvTitle);
            tvUserName = rootView.findViewById(R.id.tvUserName);
            tvUserName.setText(TrapConfig.HEADER_USER_NAME);
            tvHeaderPopularNo = rootView.findViewById(R.id.tvPopularPlayer);
            tvHeaderPopularNo.setText(String.valueOf(Prefs.getInt("popularPlayer", 12)));
            imgMenu = rootView.findViewById(R.id.imgMenu);
            tvStartDate = rootView.findViewById(R.id.tvStartDate);
            tvEndDate = rootView.findViewById(R.id.tvEndDate);

            imgMenu.setOnClickListener(v -> mainView.openDrawer());
            FrameLayout flLogoToolbar = rootView.findViewById(R.id.flLogoToolbar);
            flLogoToolbar.setOnClickListener(v -> {
                mainView.backToMainFragment();

            });
            rlShirt.setOnClickListener(v ->
            {
                startActivityForResult(new Intent(SingletonContext.getInstance().getContext(), MyProfileActivity.class), 100);

            });
            imgBack = rootView.findViewById(R.id.imgBack);
            btnBack = rootView.findViewById(R.id.btnBack);
            btnBack.setOnClickListener(v ->
            {
                getActivity().onBackPressed();
            });
            imgBack.setOnClickListener(v ->
            {
                getActivity().onBackPressed();
            });

            tvTitle.setText("کیف پول");

        } catch (Exception e) {

        }

        imgCart = rootView.findViewById(R.id.imgCart);
        lnrInventory = rootView.findViewById(R.id.lnrInventory);
        rlImageProfile = rootView.findViewById(R.id.rlImageProfile);
        incBackCart = rootView.findViewById(R.id.incBackCart);
        incFrontCart = rootView.findViewById(R.id.incFrontCart);
        scrollView = rootView.findViewById(R.id.nested);
        tvBalance = rootView.findViewById(R.id.tvBalance);
        tvDate = rootView.findViewById(R.id.tvDate);
        customNo = rootView.findViewById(R.id.customNo);
        cartNo = rootView.findViewById(R.id.cartNo);
        txtFullName = rootView.findViewById(R.id.txtFullName);

        rlImageProfile.setOnClickListener(this);
        ivRefreshing.setOnClickListener(this);

        tvStartDate.setOnClickListener(v -> {
            pickerDialogStartDate.show(getFragmentManager(), "StartDate");

        });
        tvEndDate.setOnClickListener(v -> {
            pickerDialogEndDate.show(getFragmentManager(), "EndDate");

        });
        btnDeleteFilter.setOnClickListener(v -> {
            resetAll();

            EventTurnoverModel turnoverModel = new EventTurnoverModel();
            turnoverModel.setRemove(true);

            EventBus.getDefault().post(turnoverModel);
        });
        btnConfirmFilter.setOnClickListener(v -> {
            slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);

            EventTurnoverModel turnoverModel = new EventTurnoverModel();
            turnoverModel.setTo_date(tvEndDate.getText().toString());
            turnoverModel.setRemove(false);

            turnoverModel.setFrom_date(tvStartDate.getText().toString());
            turnoverModel.setTo_amount(maxPrice);
            turnoverModel.setFrom_amount(minPrice);
            EventBus.getDefault().post(turnoverModel);
        });

        rangeBar.setRange(0f, 20f, 1f);
        rangeBar.setProgress(0f, 20f);
        rangeBar.setIndicatorTextDecimalFormat("0");
        rangeBar.setOnRangeChangedListener(this);
        tvMaxPrice.setText("10,000,000 ریال");
        tvMinPrice.setText("0 زیال");
    }


    /*----------------------------------------------------------------------------------------------------*/

    private void requestGetBalance() {
        GetBalancePasswordLessRequest request = new GetBalancePasswordLessRequest();
        request.setIsWallet(true);
        SingletonService.getInstance().getBalancePasswordLessService().GetBalancePasswordLessService(new OnServiceStatus<WebServiceClass<GetBalancePasswordLessResponse>>() {


            @Override
            public void onReady(WebServiceClass<GetBalancePasswordLessResponse> response) {
                mainView.hideLoading();

                try {
                    if (response.info.statusCode == 200) {
                        setBalanceData(response.data);

                    } else {

                        mainView.showError(response.info.message);

                    }
                } catch (Exception e) {
                    mainView.showError(e.getMessage());

                }


            }

            @Override
            public void onError(String message) {
                mainView.showError(message);
                mainView.hideLoading();
                if (Tools.isNetworkAvailable(Objects.requireNonNull(getActivity())))
                {
                    showAlert(getActivity(), "خطای ارتباط با سرور!", R.string.error);
                }
                else
                {
                    showAlert(getActivity(), R.string.networkErrorMessage, R.string.networkError);
                }


            }
        }, request);
    }

    private void setBalanceData(GetBalancePasswordLessResponse data) {
        tvBalance.setText(Utility.priceFormat(data.getBalanceAmount()));
        tvDate.setText(data.getDateTime());
    }

    /*----------------------------------------------------------------------------------------------------*/
    private void requestGetInfo() {
        GetBalancePasswordLessRequest request = new GetBalancePasswordLessRequest();
        request.setIsWallet(true);
        SingletonService.getInstance().getBalancePasswordLessService().GetInfoWalletService(new OnServiceStatus<WebServiceClass<GetInfoWalletResponse>>() {


            @Override
            public void onReady(WebServiceClass<GetInfoWalletResponse> response) {
                mainView.hideLoading();

                try {
                    if (response.info.statusCode == 200) {
                        setInfoData(response.data);

                    } else {

                        mainView.showError(response.info.message);

                    }
                } catch (Exception e) {
                    mainView.showError(e.getMessage());

                }


            }

            @Override
            public void onError(String message) {

                mainView.showError(message);
                mainView.hideLoading();
                if (Tools.isNetworkAvailable(Objects.requireNonNull(getActivity())))
                {
                    mainView.showError("خطای ارتباط با سرور!");

                }
                else
                {
                    mainView.showError(getString(R.string.networkErrorMessage));

                }

            }
        }, request);
    }

    private void setInfoData(GetInfoWalletResponse data) {
        try {
            Iterable<String> result = Splitter.fixedLength(4).split(data.getCard_no());
            String[] parts = Iterables.toArray(result, String.class);


            cartNo.setText(parts[0] + "-" + parts[1] + "-" + parts[2] + "-" + parts[3]);
        } catch (Exception e) {
            cartNo.setText(data.getCard_no() + "");

        }
        customNo.setText("کدمشتری: " + data.getCustomer_code());
        txtFullName.setText(data.getFull_name() + "");
    }
    /*----------------------------------------------------------------------------------------------------*/

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivRefreshing:
                mainView.showLoading();

                requestGetBalance();
                requestGetInfo();
                break;
            case R.id.rlImageProfile:
                if (incBackCart.getVisibility() == View.GONE) {
                    incBackCart.setVisibility(View.VISIBLE);
                    incFrontCart.setVisibility(View.GONE);
                    // lnrInventory.setVisibility(View.GONE);
                } else {
                    incBackCart.setVisibility(View.GONE);
                    incFrontCart.setVisibility(View.VISIBLE);
                    //lnrInventory.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ClickTurnOverEvent event) {
        if (event.getFilterClick()){
            slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);

        }
    }

    @Override
    public void onRangeChanged(RangeSeekBar view, float leftValue, float rightValue, boolean isFromUser) {
        int left = (int) leftValue;
        int right = (int) rightValue;
        tvMaxPrice.setText(Utility.priceFormat(right * 500000) + " ریال");
        tvMinPrice.setText(Utility.priceFormat(left * 500000) + " ریال");
        maxPrice = right * 500000;
        minPrice = left * 500000;
        Logger.e("-onRangeChanged-", "left: " + leftValue + " ,right: " + rightValue);
        Logger.e("-onRangeChanged2-", "left: " + minPrice + " ,right: " + maxPrice);
    }

    @Override
    public void onStartTrackingTouch(RangeSeekBar view, boolean isLeft) {

    }

    @Override
    public void onStopTrackingTouch(RangeSeekBar view, boolean isLeft) {

    }


    private void initDatePicker() {
        currentDate = new PersianCalendar();

        pickerDialogStartDate = DatePickerDialog.newInstance(this,
                currentDate.getPersianYear(),
                currentDate.getPersianMonth() - 1,
                currentDate.getPersianDay()
        );
        pickerDialogStartDate.setTitle("انتخاب تاریخ شروع");

        startDay = currentDate.getPersianDay();
        startMonth = currentDate.getPersianMonth() - 1;
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

    private Integer getDateInt(int year, int month, int day) {
        return (year - year / 100) * 10000 + month * 100 + day;
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth, int endYear, int endMonth, int endDay) {

        if (view.getTag().equals("StartDate")) {
            startPersianDate.set(year, monthOfYear, dayOfMonth);
            pickerDialogEndDate.setMinDate(startPersianDate);
            pickerDialogEndDate.setMaxDate(currentDate);
            startDateInt = getDateInt(year, monthOfYear, dayOfMonth);

            if (startDateInt > endDateInt) {
                tvEndDate.setText("");
                filterEndDate = "";
                imgEndDateReset.setVisibility(View.GONE);
            }

            filterStartDate = year + "/" + Utility.getFormatDateMonth(monthOfYear + 1) + "/" + Utility.getFormatDateMonth(dayOfMonth);
            tvStartDate.setText(filterStartDate);
            imgStartDateReset.setVisibility(View.VISIBLE);
        } else if (view.getTag().equals("EndDate")) {
            endPersianDate.set(year, monthOfYear, dayOfMonth);
            pickerDialogStartDate.setMaxDate(endPersianDate);
            endDateInt = getDateInt(year, monthOfYear, dayOfMonth);

            if (startDateInt > endDateInt) {
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

    private void resetAll() {
        slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);

        tvStartDate.setText("");
        tvEndDate.setText("");
        filterEndDate = "";
        filterStartDate = "";
        idFilteredList = "";
        titleFilteredList = "";
        imgStartDateReset.setVisibility(View.GONE);
        imgEndDateReset.setVisibility(View.GONE);


        maxPrice = MAX_PRICE_DEFAULT;
        minPrice = 0;
        tvMaxPrice.setText("10,000,000 زیال");
        tvMinPrice.setText("0 زیال");
        rangeBar.setProgress(0f, 20f);


    }

    public void onSelectContact(OnSelectContact onSelectContact)
    {
        if(fragment instanceof DetailsCartFragment){}
        ((DetailsCartFragment) fragment).onSelectContact(onSelectContact);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(WalletTitle event) {
        tvTitle.setText(event.title);



    }

}
