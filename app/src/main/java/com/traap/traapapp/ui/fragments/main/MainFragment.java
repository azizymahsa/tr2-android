package com.traap.traapapp.ui.fragments.main;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.os.CountDownTimer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.pixplicity.easyprefs.library.Prefs;
import com.squareup.picasso.Picasso;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.getMenu.response.GetMenuItemResponse;
import com.traap.traapapp.apiServices.model.getMenuHelp.GetMenuHelpRequest;
import com.traap.traapapp.apiServices.model.getMenuHelp.GetMenuHelpResponse;
import com.traap.traapapp.apiServices.model.getMenuHelp.ResultHelpMenu;
import com.traap.traapapp.apiServices.model.matchList.MachListResponse;
import com.traap.traapapp.apiServices.model.matchList.MatchItem;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.models.otherModels.headerModel.HeaderModel;
import com.traap.traapapp.models.otherModels.mainService.MainServiceModelItem;
import com.traap.traapapp.apiServices.model.tourism.GetUserPassResponse;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.ui.activities.login.LoginActivity;
import com.traap.traapapp.ui.activities.main.MainActivity;
import com.traap.traapapp.ui.adapters.mainServiceModel.MainServiceModelAdapter;
import com.traap.traapapp.ui.adapters.mainSlider.MainSliderAdapter;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.activities.myProfile.MyProfileActivity;
import com.traap.traapapp.utilities.CountDownTimerPredict;
import com.traap.traapapp.utilities.Logger;
import com.traap.traapapp.utilities.Tools;
import com.traap.traapapp.utilities.Utility;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

//import library.android.eniac.StartEniacBusActivity;
//import library.android.eniac.StartEniacFlightActivity;
//import library.android.eniac.StartEniacHotelActivity;
//import library.android.eniac.interfaces.BusLockSeat;
//import library.android.eniac.interfaces.FlightReservationData;
//import library.android.eniac.interfaces.HotelReservationData;
//import library.android.eniac.model.FlightReservation;
//import library.android.service.model.Hotel.getBookingInfo.subModel.HotelItem;
//import library.android.service.model.bus.lockSeat.response.LockSeatResponse;
//import library.android.service.model.bus.saleVerify.response.SaleVerifyResponse;
//import library.android.service.model.bus.searchBus.response.Company;
//import library.android.service.model.flight.reservation.response.ReservationResponse;
import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import br.com.simplepass.loading_button_lib.interfaces.OnAnimationEndListener;
import ru.tinkoff.scrollingpagerindicator.ScrollingPagerIndicator;
import smartdevelop.ir.eram.showcaseviewlib.GuideView;
import smartdevelop.ir.eram.showcaseviewlib.config.DismissType;

public class MainFragment extends BaseFragment implements onConfirmUserPassGDS, MainServiceModelAdapter.OnItemClickListener,
//        FlightReservationData, BusLockSeat, HotelReservationData,
        View.OnClickListener, MainSliderAdapter.OnSliderItemClickListener
        , OnServiceStatus<WebServiceClass<MachListResponse>>, CountDownTimerView, OnAnimationEndListener
{
    private View rootView;

    private NestedScrollView nestedScroll;
    private RecyclerView recyclerView, sliderRecyclerView;
    //    private MultiSnapRecyclerView recyclerView;
    private LinearLayoutManager layoutManager, sliderLayoutManager;
    private MainServiceModelAdapter adapter;
    private MainSliderAdapter sliderAdapter;
    private CountDownTimer countDownTimer;
    private TextView tvShowIntro, tvCancelIntro, tvIntroTitle, tvTimePredict;
    private View rlIntro;
    private RelativeLayout llRoot;
    private ScrollingPagerIndicator indicator;
    private BottomNavigationView bottomNavigationView;

    private Boolean isPredictable = true;

    private Toolbar mToolbar;

    private RelativeLayout rlF1, rlF2, rlF3, rlF4, rlF5, rlF6, rlPredict;
    private TextView tvF1, tvF2, tvF3, tvF4, tvF5, tvF6, tvUserName;
    private ImageView imgF1, imgF2, imgF3, imgF4, imgF5, imgF6;

    private List<MainServiceModelItem> list = new ArrayList<>();

    private ArrayList<GetMenuItemResponse> footballServiceList, chosenServiceList;

    private MainActionView mainView;
    private CircularProgressButton btnBuyTicket;

    private ArrayList<MatchItem> matchList;
    private MatchItem matchCurrent, matchBuyable, matchPredict;
    private TextView tvPopularPlayer;

//    private CountdownView countdownView;
    private int matchCurrentPos = 0;
    private boolean isFirstLoad = true;
    private ImageView imgMenu;
    private List<ResultHelpMenu> helpMenuResult;
    private View rlShirt;

    public MainFragment()
    {
        // Required empty public constructor
    }

    public static MainFragment newInstance(MainActionView mainActionView,
                                           ArrayList<GetMenuItemResponse> footballServiceList,
                                           ArrayList<GetMenuItemResponse> chosenServiceList,
                                           ArrayList<MatchItem> matchList
    )
    {
        MainFragment fragment = new MainFragment();
        fragment.setMainView(mainActionView);

        Bundle args = new Bundle();
        args.putParcelableArrayList("chosenServiceList", chosenServiceList);
        args.putParcelableArrayList("footballServiceList", footballServiceList);
        args.putParcelableArrayList("matchList", matchList);

        fragment.setArguments(args);

        return fragment;
    }


    private void setMainView(MainActionView mainView)
    {
        this.mainView = mainView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            chosenServiceList = getArguments().getParcelableArrayList("chosenServiceList");
            footballServiceList = getArguments().getParcelableArrayList("footballServiceList");
            matchList = getArguments().getParcelableArrayList("matchList");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_main, container, false);

        initView(rootView);

        EventBus.getDefault().register(this);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        if (matchList == null)
        {
            getSliderData();
        } else
        {
            setSlider();

//            sliderAdapter = new MainSliderAdapter(getActivity(), matchList, this);
//            sliderAdapter.notifyDataSetChanged();
//
//            sliderRecyclerView.setAdapter(sliderAdapter);
        }

        list = fillMenuRecyclerList();

        adapter = new MainServiceModelAdapter(getActivity(), list, this);
        recyclerView.setAdapter(adapter);
    }

    private void initView(View rootView)
    {
        mToolbar = rootView.findViewById(R.id.toolbar);

        llRoot = rootView.findViewById(R.id.rlRoot);
        tvTimePredict = rootView.findViewById(R.id.tvTimePredict);
        tvShowIntro = rootView.findViewById(R.id.tvShowIntro);
        tvCancelIntro = rootView.findViewById(R.id.tvCancelIntro);
        rlIntro = rootView.findViewById(R.id.rlIntro);
        tvIntroTitle = rootView.findViewById(R.id.tvIntroTitle);
        bottomNavigationView = getActivity().findViewById(R.id.bottom_navigation);

        nestedScroll = rootView.findViewById(R.id.nestedScroll);
        imgMenu = mToolbar.findViewById(R.id.imgMenu);
        imgMenu.setOnClickListener(v -> mainView.openDrawer());
        tvUserName = mToolbar.findViewById(R.id.tvUserName);
        rlShirt=mToolbar.findViewById(R.id.rlShirt);
        tvUserName.setText(TrapConfig.HEADER_USER_NAME);
        tvPopularPlayer = mToolbar.findViewById(R.id.tvPopularPlayer);
        tvPopularPlayer.setText(String.valueOf(Prefs.getInt("popularPlayer", 12)));

        recyclerView = rootView.findViewById(R.id.recyclerView);
        sliderRecyclerView = rootView.findViewById(R.id.sliderRecyclerView);

        btnBuyTicket = rootView.findViewById(R.id.btnBuyTicket);

        rlPredict = rootView.findViewById(R.id.rlPredict);

//        countdownView = rootView.findViewById(R.id.countDown);

        indicator = rootView.findViewById(R.id.indicator);

        rlF1 = rootView.findViewById(R.id.rlF1);
        rlF2 = rootView.findViewById(R.id.rlF2);
        rlF3 = rootView.findViewById(R.id.rlF3);
        rlF4 = rootView.findViewById(R.id.rlF4);
        rlF5 = rootView.findViewById(R.id.rlF5);
        rlF6 = rootView.findViewById(R.id.rlF6);

        imgF1 = rootView.findViewById(R.id.imgF1);
        imgF2 = rootView.findViewById(R.id.imgF2);
        imgF3 = rootView.findViewById(R.id.imgF3);
        imgF4 = rootView.findViewById(R.id.imgF4);
        imgF5 = rootView.findViewById(R.id.imgF5);
        imgF6 = rootView.findViewById(R.id.imgF6);

        tvF1 = rootView.findViewById(R.id.tvF1);
        tvF2 = rootView.findViewById(R.id.tvF2);
        tvF3 = rootView.findViewById(R.id.tvF3);
        tvF4 = rootView.findViewById(R.id.tvF4);
        tvF5 = rootView.findViewById(R.id.tvF5);
        tvF6 = rootView.findViewById(R.id.tvF6);

        tvF2.setText(footballServiceList.get(0).getTitle());
        tvF1.setText(footballServiceList.get(1).getTitle());
        tvF4.setText(footballServiceList.get(2).getTitle());
        tvF3.setText(footballServiceList.get(3).getTitle());
        tvF6.setText(footballServiceList.get(4).getTitle());
        tvF5.setText(footballServiceList.get(5).getTitle());

        rlF1.setOnClickListener(this);
        rlF2.setOnClickListener(this);
        rlF3.setOnClickListener(this);
        rlF4.setOnClickListener(this);
        rlF5.setOnClickListener(this);
        rlF6.setOnClickListener(this);
        rlShirt.setOnClickListener(this);

        setImageIntoIV(imgF2, footballServiceList.get(0).getImageName().replace(" ", "%20"));
        setImageIntoIV(imgF1, footballServiceList.get(1).getImageName().replace(" ", "%20"));
        setImageIntoIV(imgF4, footballServiceList.get(2).getImageName().replace(" ", "%20"));
        setImageIntoIV(imgF3, footballServiceList.get(3).getImageName().replace(" ", "%20"));
        setImageIntoIV(imgF6, footballServiceList.get(4).getImageName().replace(" ", "%20"));
        setImageIntoIV(imgF5, footballServiceList.get(5).getImageName().replace(" ", "%20"));

        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, true);
        recyclerView.setLayoutManager(layoutManager);
        sliderLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        sliderRecyclerView.setLayoutManager(sliderLayoutManager);

//        SnapHelper snapHelper = new StartSnapHelper();
//        SnapHelper snapHelper = new LinearSnapHelper();
//        snapHelper.attachToRecyclerView(sliderRecyclerView);

        btnBuyTicket.setOnClickListener(this);

        rlPredict.setOnClickListener(v ->
        {
            mainView.onPredict(matchPredict, isPredictable);

        });
    }

    private void getSliderData()
    {
        mainView.showLoading();

        SingletonService.getInstance().getMatchListService().getMatchList(this);
    }

    public void startTimer(long time)
    {
        countDownTimer = new CountDownTimerPredict(time, 1, this);
        countDownTimer.start();
//        countdownView.start(time);
//        countdownView.setOnCountdownIntervalListener(1, new CountdownView.OnCountdownIntervalListener()
//        {
//            @Override
//            public void onInterval(CountdownView cv, long remainTime)
//            {
//
//            }
//        });
//        countdownView.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener()
//        {
//            @Override
//            public void onEnd(CountdownView cv)
//            {
//
//            }
//        });
    }

    private void setImageIntoIV(ImageView imageView, String link)
    {
        Picasso.with(getActivity()).load(link).into(imageView);
    }

    private List<MainServiceModelItem> fillMenuRecyclerList()
    {
        List<MainServiceModelItem> newList = new ArrayList<>();

        try
        {
            for (GetMenuItemResponse itemResponse : chosenServiceList)
            {
                if (itemResponse.getIsVisible())
                {
                    MainServiceModelItem item = new MainServiceModelItem();

                    item.setId(itemResponse.getKeyId());
                    item.setTitle(itemResponse.getTitle());
                    item.setImageLink(itemResponse.getImageName());
                    item.setKeyName(itemResponse.getKeyName());
                    item.setBase_url(itemResponse.getBaseUrl());

                    newList.add(item);
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return newList;
    }

    private void setSlider()
    {
        int index = -1;
        for (MatchItem matchItem : matchList)
        {
            index++;
            if (matchItem.getIsCurrent())
            {
                matchCurrentPos = index;
                this.matchCurrent = matchItem;
                Logger.e("--matchCurrentPos--", "pos: " + matchCurrentPos);
            }

            if (matchItem.getIsPredict())
            {
                this.matchPredict = matchItem;

                Timestamp myTimestamp = new Timestamp(System.currentTimeMillis());
                long myTime = myTimestamp.getTime();
                long matchTime = matchPredict.getMatchDatetime().longValue() * 1000;
                Logger.e("--Time--", "myTime:" + myTime + ", MatchTime: " + matchTime);
                long time = matchTime - myTime;
                Logger.e("--diff Time--", "Time: " + time);

                long predictTime = matchPredict.getPredictTime().longValue() * 1000;
                long remainPredictTime = predictTime - myTime;
                Logger.e("--diff PredictTime--", "remainPredictTime: " + remainPredictTime);

                if (remainPredictTime > 0)
                {
                    isPredictable = true;
                    startTimer(remainPredictTime);
                }
                else
                {
                    isPredictable = false;
                    rootView.findViewById(R.id.llTimer).setVisibility(View.INVISIBLE);
                    ((TextView) rootView.findViewById(R.id.tvPredictText)).setText("هیچ بازی جهت پیش بینی وجود ندارد!");
                }

//                Timestamp timestamp = matchPredict.getMatchDatetime().intValue();
            }
//            else
//            {
//                isPredictable = false;
//                rootView.findViewById(R.id.llTimer).setVisibility(View.INVISIBLE);
//                ((TextView) rootView.findViewById(R.id.tvPredictText)).setText("هیچ بازی جهت پیشبینی وجود ندارد!");
//            }

            if (matchItem.getBuyEnable())
            {
                this.matchBuyable = matchItem;
            }

        }

        //---------------------new---------------------------
        sliderAdapter = new MainSliderAdapter(getActivity(), matchList, this);
        sliderRecyclerView.setAdapter(sliderAdapter);

        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(sliderRecyclerView);
        indicator.attachToRecyclerView(sliderRecyclerView);
//        sliderRecyclerView.setOnFlingListener(snapHelper);

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                sliderRecyclerView.smoothScrollToPosition(matchCurrentPos);
//                sliderRecyclerView.scrollToPosition(matchCurrentPos);
            }
        }, 200);

        mainView.hideLoading();
    }

    @Override
    public void onGdsFlight(GetUserPassResponse response)
    {
        mainView.hideLoading();

//        StartEniacFlightActivity flightActivity = new StartEniacFlightActivity(response.getUniqeCode(),
//                response.getUsername(), response.getPassword(), this, 0);
//        StartEniacFlightActivity flightActivity = new StartEniacFlightActivity(TrapConfig.UNIQUE_CODE_Flight_All,
//                "0037250100293610", "1397", this, 0);
//
//        flightActivity.startMainFlight();
    }


    @Override
    public void onGdsBus(GetUserPassResponse response)
    {
        mainView.hideLoading();

//        StartEniacBusActivity busActivity = new StartEniacBusActivity(response.getUniqeCode(),
//                response.getUsername(), response.getPassword(), getActivity(), this, 0);

//        StartEniacBusActivity busActivity = new StartEniacBusActivity(TrapConfig.UNIQUE_CODE_BUS,
//                "0037250100293610", "1397", getActivity(), this, 0);
//
//        busActivity.startMainBusActivity();
    }


    @Override
    public void onGdsHotel(GetUserPassResponse response)
    {
        mainView.hideLoading();

//        StartEniacHotelActivity hotelActivity = new StartEniacHotelActivity(response.getUniqeCode(),
//                response.getUsername(), response.getPassword(), SingletonContext.getInstance().getContext(),
//                this, 0);

//        StartEniacHotelActivity hotelActivity = new StartEniacHotelActivity(TrapConfig.UNIQUE_CODE_HOTEL,
//                "0037250100293610", "1397", SingletonContext.getInstance().getContext(),
//                this, 0);
//
//        hotelActivity.startMainHotelActivity();
    }


    @Override
    public void onGdsError(String message)
    {
        mainView.hideLoading();

        Tools.showToast(getActivity(), message, R.color.red);
    }


//    @Override
//    public void LockSeatListener(LockSeatResponse lockSeatResponse, String s, String s1, List<Company> list)
//    {
//
//    }
//
//    @Override
//    public void issueBusReservation(SaleVerifyResponse saleVerifyResponse, boolean b)
//    {
//
//    }
//
//
//    @Override
//    public void flightReservationListener(ReservationResponse reservationResponse, FlightReservation flightReservation, String s, String s1)
//    {
//
//    }
//
//    @Override
//    public void flightConfirmToSendSms(Boolean aBoolean)
//    {
//
//    }


//    @Override
//    public void hotelReserveListener(HotelItem hotelItem, String s)
//    {
//
//    }
//
//    @Override
//    public void hotelConfirmToSendSmsListener(Boolean aBoolean)
//    {
//
//    }

    @Override
    public void onChosenItemClick(View view, Integer id,String URl)
    {
        switch (id)
        {
            case 11: //Flight ATA
            {

                mainView.openWebView(mainView,URl,Prefs.getString("gds_token", ""));

                break;
            }

            case 14://Flight
            {
                mainView.openWebView(mainView,URl,Prefs.getString("gds_token", ""));


                break;
            }

            case 12: //Hotel
            {
                mainView.openWebView(mainView,URl,Prefs.getString("gds_token", ""));


                break;
            }

            case 13: //Bus
            {
                mainView.openWebView(mainView,URl,Prefs.getString("gds_token", ""));

                break;
            }

            case 65: //Bill
            {
                mainView.onBill();
                break;
            }

            case 42: //Pack
            {
                mainView.onPackSimCard();
                break;
            }

            case 41: //Charge
            {
                mainView.onChargeSimCard();
                break;
            }

            case 72: //خرید بدون کارت
            {
                mainView.doTransferMoney();
                break;
            }

            case 43: //خرید بدون کارت
            {
//                mainView.doTransferMoney();
                break;
            }

        }
    }

    private void onGdsFlightAta(GetUserPassResponse response)
    {
        mainView.hideLoading();

//        StartEniacFlightActivity flightActivity = new StartEniacFlightActivity(response.getUniqeCode(),
//                response.getUsername(), response.getPassword(), this, 0);
//        StartEniacFlightActivity flightActivity = new StartEniacFlightActivity(TrapConfig.UNIQUE_CODE_Flight_Ata,
//                "0037250100293610", "1397", this, 0);
//
//        flightActivity.startMainFlight();
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {

            case R.id.rlShirt:
                startActivity(new Intent(SingletonContext.getInstance().getContext(), MyProfileActivity.class));
                break;
            case R.id.btnBuyTicket:
            {
                btnBuyTicket.startAnimation();
                btnBuyTicket.setClickable(false);

                mainView.getBuyEnable(() ->
                {
                    btnBuyTicket.revertAnimation();
                    btnBuyTicket.setClickable(true);
                });

                break;
            }
            case R.id.rlF1:
            {
                mainView.onFootBallServiceOne();
                break;
            }

            case R.id.rlF2:
            {

                mainView.onMainVideoClick();
                break;
            }

            case R.id.rlF3:
            {

                break;
            }

            case R.id.rlF4:
            {

                break;
            }

            case R.id.rlF5:
            {

                break;
            }

            case R.id.rlF6:
            {

                break;
            }
        }
    }


    @Override
    public void onReady(WebServiceClass<MachListResponse> responseMatchList)
    {
        mainView.hideLoading();

        if (responseMatchList == null || responseMatchList.info == null)
        {
            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();

            return;
        }
        if (responseMatchList.info.statusCode != 200)
        {
            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();

            return;
        }
        else
        {
            matchList = responseMatchList.data.getMatchList();
            MainActivity.matchList = matchList;

            setSlider();
        }
    }

    @Override
    public void onError(String message)
    {
        mainView.hideLoading();
        if (Tools.isNetworkAvailable(getActivity()))
        {
            showError(getActivity(), "خطا در دریافت اطلاعات از سرور!");
            Logger.e("--onError--", message);
        }
        else
        {
            showAlert(getActivity(), R.string.networkErrorMessage, R.string.networkError);
        }

    }

    @Override
    public void onSliderItemClick(View view, Integer id, Integer position)
    {
        mainView.onLeageClick(matchList);
    }

    public void showIntro(List<ResultHelpMenu> results)
    {
        try
        {
            nestedScroll.scrollTo(0, 0);
        }
        catch (Exception e)
        {

        }

        helpMenuResult = results;

        YoYo.with(Techniques.SlideOutLeft).withListener(new AnimatorListenerAdapter()
        {
            @Override
            public void onAnimationEnd(Animator animation)
            {
                super.onAnimationEnd(animation);
                rlIntro.setVisibility(View.GONE);
                Utility.disableEnableControls(true, llRoot);

                for (int i = 0; i < results.size(); i++)
                {
                    if (results.get(i).getCode() == 1)
                    {
                        intro(imgMenu, results.get(i).getTitle(), results.get(i).getDescription(), 1);
                    }
                }
            }
        })
                .duration(500)
                .playOn(rlIntro);
    }

    public void requestGetHelpMenu()
    {
        GetMenuHelpRequest request = new GetMenuHelpRequest();
        SingletonService.getInstance().getMenuHelpService().getMenuHelpService(new OnServiceStatus<WebServiceClass<GetMenuHelpResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<GetMenuHelpResponse> response)
            {
                try
                {
                    if (response.info.statusCode == 200)
                    {
                        showIntro(response.data.getResults());
                    }
                    else
                    {
                        Utility.disableEnableControls(true, llRoot);
                        showError(getActivity(), response.info.message);
                    }
                }
                catch (Exception e)
                {
                    showError(getActivity(), e.getMessage());
                }
            }

            @Override
            public void onError(String message)
            {
                Utility.disableEnableControls(true, llRoot);

                showError(getActivity(), message);
            }
        }, request);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (Prefs.getBoolean("intro", true))
        {
          requestShowTutorialIntro();

        }
        else
        {
            Utility.disableEnableControls(true, llRoot);
        }
    }

    private void requestShowTutorialIntro()
    {
        if (Prefs.getBoolean("intro", true))
        {
            GetMenuHelpRequest request = new GetMenuHelpRequest();
            SingletonService.getInstance().getMenuHelpService().getMenuHelpService(new OnServiceStatus<WebServiceClass<GetMenuHelpResponse>>()
            {
                @Override
                public void onReady(WebServiceClass<GetMenuHelpResponse> response)
                {
                    try
                    {

                        if (response.info.statusCode == 200)
                        {

                            startIntro(response.data.getResults());

                        } else
                        {
                            Utility.disableEnableControls(true, llRoot);
                            Tools.showToast(getContext(), response.info.message, R.color.red);
                        }
                    } catch (Exception e)
                    {
                        Tools.showToast(getContext(), e.getMessage(), R.color.red);

                    }
                }

                @Override
                public void onError(String message)
                {
                    Utility.disableEnableControls(true, llRoot);

                    Tools.showToast(getActivity(), message, R.color.red);
                }
            }, request);

        }

    }


    private void startIntro(List<ResultHelpMenu> results)
    {
        // onHome();

        Prefs.putBoolean("intro", false);
        helpMenuResult = results;
        tvShowIntro.setOnClickListener(view ->
        {
            showIntro(results);
        });
        tvCancelIntro.setOnClickListener(view ->
        {
            YoYo.with(Techniques.SlideOutLeft).withListener(new AnimatorListenerAdapter()
            {
                @Override
                public void onAnimationEnd(Animator animation)
                {
                    super.onAnimationEnd(animation);
                    Utility.disableEnableControls(true, llRoot);
                    rlIntro.setVisibility(View.GONE);
                }
            })
                    .duration(500)
                    .playOn(rlIntro);
        });


        tvIntroTitle.setText("سلام، " + TrapConfig.HEADER_USER_NAME + " خوش آمدید");
        new Handler().postDelayed(() ->
        {
            rlIntro.setVisibility(View.VISIBLE);
            YoYo.with(Techniques.FadeIn)
                    .duration(500)
                    .playOn(rlIntro);
        }, 1000);
    }

    private void intro(View view, String title, String text, final int type)
    {
        new GuideView.Builder(getContext())
                .setTitle(title)
                .setContentText(text)
                .setTargetView(view)
                /* .setTitleTypeFace(Typeface.createFromAsset(getActivity().getAssets(), "fonts/iran_sans_normal.ttf"))
                 .setContentTypeFace(Typeface.createFromAsset(getActivity().getAssets(), "fonts/iran_sans_normal.ttf"))*/
                .setDismissType(DismissType.anywhere)
                .setContentTextSize(12)//optional
                .setTitleTextSize(14)//optional
                .setGuideListener(view1 ->
                {
                    try
                    {
                        for (int i = 0; i < helpMenuResult.size(); i++)
                        {
                            if (type == 1)
                            {
                                if (helpMenuResult.get(i).getCode() == 2)
                                {
                                    intro(btnBuyTicket, helpMenuResult.get(i).getTitle(), helpMenuResult.get(i).getDescription(), 2);
                                }
                            } else if (type == 2)
                            {
                                if (helpMenuResult.get(i).getCode() == 3)
                                {
                                    intro(sliderRecyclerView, helpMenuResult.get(i).getTitle(), helpMenuResult.get(i).getDescription(), 3);
                                }
                            } else if (type == 3)
                            {
                                if (helpMenuResult.get(i).getCode() == 4)
                                {
                                    intro(getActivity().findViewById(R.id.tab_all_services), helpMenuResult.get(i).getTitle(), helpMenuResult.get(i).getDescription(), 4);
                                }
                            } else if (type == 4)
                            {
                                if (helpMenuResult.get(i).getCode() == 5)
                                {
                                    intro(getActivity().findViewById(R.id.tab_media), helpMenuResult.get(i).getTitle(), helpMenuResult.get(i).getDescription(), 5);
                                }
                            } else if (type == 5)
                            {
//                                if (helpMenuResult.get(i).getCode() == 6)
//                                {
//                                    intro(getActivity().findViewById(R.id.tab_payment), helpMenuResult.get(i).getTitle(), helpMenuResult.get(i).getDescription(), 6);
//                                }
                            } else if (type == 6)
                            {
//                                if (helpMenuResult.get(i).getCode() == 7)
//                                {
//                                    intro(getActivity().findViewById(R.id.tab_market), helpMenuResult.get(i).getTitle(), helpMenuResult.get(i).getDescription(), 7);
//                                }
                            } else if (type == 7)
                            {
                                if (helpMenuResult.get(i).getCode() == 8)
                                {
                                    intro(rlPredict, helpMenuResult.get(i).getTitle(), helpMenuResult.get(i).getDescription(), 8);
                                }
                            } else if (type == 8)
                            {
                                if (helpMenuResult.get(i).getCode() == 9)
                                {
                                    int iFromService = i;
                                    try
                                    {
                                        focusOnServiceViewList();
                                        new Handler().postDelayed(() ->
                                        {
                                            intro(recyclerView, helpMenuResult.get(iFromService).getTitle(), helpMenuResult.get(iFromService).getDescription(), 9);

                                        }, 1000);
                                    } catch (Exception e)
                                    {

                                    }


                                }
                            }

                        }

                    } catch (Exception e)
                    {

                    }

                })
                .build()
                .show();
    }

    private final void focusOnServiceViewList()
    {
        nestedScroll.post(new Runnable()
        {
            @Override
            public void run()
            {
                nestedScroll.scrollTo(0, recyclerView.getBottom());
            }
        });
    }

    @Override
    public void onFinishTimer()
    {

    }

    @Override
    public void onTickTimer(String time)
    {
//        tvTimePredict.setTypeface();
        tvTimePredict.setText(time);
    }

    @Override
    public void onErrorTimer(String message)
    {
        showError(getActivity(), message);
    }

    @Subscribe
    public void getHeaderContent(HeaderModel headerModel)
    {
        if (headerModel.getPopularNo() != 0)
        {
            tvPopularPlayer.setText(String.valueOf(headerModel.getPopularNo()));
        }
        tvUserName.setText(TrapConfig.HEADER_USER_NAME);
    }


    @Override
    public void onDestroy()
    {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onAnimationEnd()
    {
        btnBuyTicket.setBackgroundResource(R.drawable.button_buy_ticket);
    }
}
