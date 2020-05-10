package com.traap.traapapp.ui.fragments.allMenu;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Browser;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import library.android.eniac.StartEniacBusActivity;
import library.android.eniac.StartEniacFlightActivity;
import library.android.eniac.StartEniacHotelActivity;
import library.android.eniac.interfaces.BusLockSeat;
import library.android.eniac.interfaces.FlightReservationData;
import library.android.eniac.interfaces.HotelReservationData;
import library.android.eniac.model.FlightReservation;
import library.android.service.model.Hotel.getBookingInfo.subModel.HotelItem;
import library.android.service.model.bus.lockSeat.response.LockSeatResponse;
import library.android.service.model.bus.saleVerify.response.SaleVerifyResponse;
import library.android.service.model.bus.searchBus.response.Company;
import library.android.service.model.flight.reservation.response.ReservationResponse;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.pixplicity.easyprefs.library.Prefs;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.allService.response.SubMenu;
import com.traap.traapapp.apiServices.model.getAllMenuServices.response.GetAllMenuResponse;
import com.traap.traapapp.apiServices.model.getMenu.request.GetMenuRequest;
import com.traap.traapapp.apiServices.model.getMenu.response.GetMenuItemResponse;
import com.traap.traapapp.apiServices.model.tourism.GetUserPassResponse;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.models.otherModels.headerModel.HeaderModel;
import com.traap.traapapp.models.otherModels.mainService.MainServiceModelItem;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.ui.activities.main.MainActivity;
import com.traap.traapapp.ui.activities.web.WebActivity;
import com.traap.traapapp.ui.adapters.allMenu.AllMenuServiceModelAdapter;
//import com.traap.traapapp.ui.adapters.AllMenuServiceModelAdapter;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.adapters.allMenu.ItemRecyclerViewAdapter;
import com.traap.traapapp.ui.dialogs.MessageAlertDialog;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.ui.fragments.main.onConfirmUserPassGDS;
import com.traap.traapapp.ui.activities.myProfile.MyProfileActivity;
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

/**
 * Created by MahsaAzizi
 */

public class AllMenuFragment extends BaseFragment implements
        ItemRecyclerViewAdapter.OnItemClickListenerItem, TextWatcher,
        OnServiceStatus<WebServiceClass<GetAllMenuResponse>>,
        onConfirmUserPassGDS, AllMenuServiceModelAdapter.OnItemAllMenuClickListener
//        , HotelReservationData, BusLockSeat, FlightReservationData
{
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private AllMenuServiceModelAdapter adapter;
    private View rootView;
    private List<MainServiceModelItem> list = new ArrayList<>();
    private ArrayList<GetMenuItemResponse> chosenServiceList;
    private MainActionView mainView;
    private Context context;
    private RecyclerView rvGrid;
    private String unicCode = "";
    private Toolbar mToolbar;
    private TextView tvTitle, tvUserName, tvPopularPlayer;
    private View imgBack, imgMenu;
    private View rlShirt;
    private Fragment fragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private FrameLayout flLogoToolbar;
    private StartEniacFlightActivity startEniacFlightActivity;

    Integer backState;

    public AllMenuFragment()
    {

    }


    public static AllMenuFragment newInstance(MainActionView mainView, ArrayList<GetMenuItemResponse> allServicesList, Integer backState)
    {
        AllMenuFragment f = new AllMenuFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("allServicesList", allServicesList);
//        args.putParcelableArrayList("footballServiceList", footballServiceList);

        f.setArguments(args);
        f.setMainView(mainView);
        f.setBackState(backState);
        return f;
    }

    private void setMainView(MainActionView mainView)
    {
        this.mainView = mainView;
    }

    private void setBackState(Integer backState)
    {
        this.backState = backState;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            chosenServiceList = getArguments().getParcelableArrayList("allServicesList");
        }
        // View rootView = inflater.inflate(R.layout.fragment_all_menu, container, false);


        EventBus.getDefault().register(this);
    }

    public void initView()
    {
        try
        {
            fragmentManager = getChildFragmentManager();
            recyclerView = rootView.findViewById(R.id.recyclerView);
            rvGrid = rootView.findViewById(R.id.rvGrid);

            //toolbar
            mToolbar = rootView.findViewById(R.id.toolbar);
            flLogoToolbar = rootView.findViewById(R.id.flLogoToolbar);
            tvUserName = mToolbar.findViewById(R.id.tvUserName);

            tvUserName.setText(TrapConfig.HEADER_USER_NAME);
            rlShirt = rootView.findViewById(R.id.rlShirt);
            rlShirt.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    startActivityForResult(new Intent(SingletonContext.getInstance().getContext(), MyProfileActivity.class), 100);
                }
            });

            tvTitle = rootView.findViewById(R.id.tvTitle);
            tvTitle.setText("سرویس ها");
            imgMenu = rootView.findViewById(R.id.imgMenu);
            imgMenu = rootView.findViewById(R.id.imgMenu);

            imgMenu.setOnClickListener(v -> mainView.openDrawer());
            flLogoToolbar.setOnClickListener(v -> mainView.backToMainFragment());
            imgBack = rootView.findViewById(R.id.imgBack);
            imgBack.setOnClickListener(v ->
            {
                getActivity().onBackPressed();
            });

            tvPopularPlayer = mToolbar.findViewById(R.id.tvPopularPlayer);
            tvPopularPlayer.setText(String.valueOf(Prefs.getInt("popularPlayer", 12)));
        } catch (Exception e)
        {
            e.getMessage();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        if (rootView != null)
        {
            rootView = null;
        }


        rootView = inflater.inflate(R.layout.fragment_all_menu, container, false);
        initView();

        if (chosenServiceList == null)
        {
            mainView.showLoading();

            GetMenuRequest request = new GetMenuRequest();
            request.setDeviceType(TrapConfig.AndroidDeviceType);
            request.setDensity(SingletonContext.getInstance().getContext().getResources().getDisplayMetrics().density);
            SingletonService.getInstance().getMenuService().getMenuAll(AllMenuFragment.this, request);
        } else
        {
            layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, true);
            recyclerView.setLayoutManager(layoutManager);

            list = fillMenuRecyclerList();

            adapter = new AllMenuServiceModelAdapter(getActivity(), list, this);
            recyclerView.setAdapter(adapter);
            Log.e("backStateAllMenu2", backState + "");

            if (list != null && backState == 2)
            {
                Log.e("injaaaaaa", "salaaaam");

                for (int i = 0; i < list.size(); i++)
                {
                    Log.e("teeeeeeeeeeeeeeest", list.get(i).getId() + "");
                    if (list.get(i).getId() == 4)
                    {
                        Log.e("teeeeeeeeeeeeeeest", "ok");

                        adapter.setRow_index(i);
                        OnItemAllMenuClick(null, list.get(i).getId(), list.get(i).getSubMenu());
                        adapter.notifyDataSetChanged();
                    }
                }

            }


        }


        return rootView;
    }

    @Override
    public void onAttach(@NonNull Context context)
    {
        super.onAttach(context);
        this.context = context;
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

                    item.setId(itemResponse.getId());
                    item.setKeyId(itemResponse.getKeyId());
                    //item.setTitle(itemResponse.getTitle());
                    item.setTitle(itemResponse.getTitle());
                    // item.setImageLink(itemResponse.getImageName());
                    item.setImageLink(itemResponse.getLogo());
                    item.setLogo(itemResponse.getLogo_());
                    item.setLogo_selected(itemResponse.getLogoSelected());
                    item.setKeyName(itemResponse.getKeyName());
                    item.setSubMenu(itemResponse.getSubMenu());
                    newList.add(item);
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }


        return newList;
    }

    @Override
    public void onReady(WebServiceClass<GetAllMenuResponse> response)
    {
        try
        {
            mainView.hideLoading();


            if (response == null || response.info == null)
            {
                // startActivityForResult(new Intent(this, A.class));
                return;
            }
            if (response.info.statusCode != 200)
            {
                // startActivityForResult(new Intent(this, LoginActivity.class));
                //  finish();

                return;
            }
            if (response.info.statusCode == 200)
            {
                chosenServiceList = response.data.getResults();
                MainActivity.allServiceList = chosenServiceList;

                layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, true);
                recyclerView.setLayoutManager(layoutManager);

                list = fillMenuRecyclerList();

                adapter = new AllMenuServiceModelAdapter(getActivity(), list, this);
                recyclerView.setAdapter(adapter);


            }
        } catch (Exception e)
        {
            mainView.showError(e.getMessage());
            mainView.hideLoading();

        }
    }

    @Override
    public void onError(String message)
    {
        // mainView.showError(message);

        mainView.hideLoading();
        if (Tools.isNetworkAvailable(getActivity()))
        {
            Logger.e("-OnError-", "Error: " + message);
            showError(getActivity(), "خطا در دریافت اطلاعات از سرور!");
        } else
        {
            // showError(getApplicationContext(),String.valueOf(R.string.networkErrorMessage));

            showAlert(getActivity(), R.string.networkErrorMessage, R.string.networkError);
        }
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
    {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
    {


    }

    @Override
    public void afterTextChanged(Editable editable)
    {


    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
//        YoYo.with(Techniques.FadeIn)
//                .duration(700)
//                .playOn(rootView);
    }


    @Override
    public void onGdsFlight(GetUserPassResponse response)
    {
        mainView.hideLoading();


//        StartEniacFlightActivity flightActivity = new StartEniacFlightActivity(unicCode,
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

//        StartEniacBusActivity busActivity = new StartEniacBusActivity("ZWQzNzkwYjctYzBmMy00MTc0LWFmMjYtYTc0NWE0ZTM1OGRh",
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

//        StartEniacHotelActivity hotelActivity = new StartEniacHotelActivity("ZWQzNzkwYjctYzBmMy00MTc0LWFmMjYtYTc0NWE0ZTM1OGRh",
//                "0037250100293610", "1397", SingletonContext.getInstance().getContext(),
//                this, 0);
//
//        hotelActivity.startMainHotelActivity();
    }


    @Override
    public void onGdsError(String message)
    {
        mainView.hideLoading();

        showToast(getActivity(), message, R.color.red);
    }


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
//
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


    @Override
    public void OnItemAllMenuClick(View view, Integer id, List<SubMenu> list)
    {
        loadSubMenu(list);
//        switch (id)
//        {
//            case 1://گردشگری
//            {
//                loadSubMenu(list);
//
//                break;
//            }
//            case 2://بیمه
//            {
//                loadSubMenu(list);
//
//                break;
//            }
//            case 3://الوپارک
//            {
//                loadSubMenu(list);
//
//                break;
//            }
//            case 4://شارژو بسته
//            {
//                loadSubMenu(list);
//
//                break;
//            }
//            case 6://قبوض
//            {
//                loadSubMenu(list);
//
//                break;
//            }
//        }

    }

    private void loadSubMenu(List<SubMenu> list)
    {
        rvGrid.setLayoutManager(new GridLayoutManager(getContext(), 3));
        rvGrid.setAdapter(new ItemRecyclerViewAdapter(getContext(), list, this,getActivity()));//, interactionListener));
    }

    @Override
    public void onChosenItemClickk(View view, Integer id, String URl,String baseuUrl)
    {


        switch (id)
        {
            case 11://Flight ata
            {
                Intent intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("URL", URl);
                intent.putExtra("Title", "گردشگری");

                intent.putExtra("TOKEN", Prefs.getString("gds_token", ""));
                startActivityForResult(intent, 100);
              /*  startEniacFlightActivity =new StartEniacFlightActivity(
                        "ZWQzNzkwYjctYzBmMy00MTc0LWFmMjYtYTc0NWE0ZTM1OGRh", "0037250100293610", "1397",
                        new FlightReservationData()
                        {
                            @Override
                            public void flightReservationListener(ReservationResponse reservationResponse, FlightReservation flightReservation, String s, String s1)
                            {

                            }

                            @Override
                            public void flightConfirmToSendSms(Boolean aBoolean)
                            {

                            }
                        }, 2);
                startEniacFlightActivity.startMainFlight();*/
                break;
            }
            case 14://Flight all
            {
        /*        startEniacFlightActivity =new StartEniacFlightActivity(
                        "ZWQzNzkwYjctYzBmMy00MTc0LWFmMjYtYTc0NWE0ZTM1OGRh", "0037250100293610", "1397",
                        new FlightReservationData()
                        {
                            @Override
                            public void flightReservationListener(ReservationResponse reservationResponse, FlightReservation flightReservation, String s, String s1)
                            {

                            }

                            @Override
                            public void flightConfirmToSendSms(Boolean aBoolean)
                            {

                            }
                        }, 2);
                startEniacFlightActivity.startMainFlight();*/
                Intent intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("URL", URl);
                intent.putExtra("Title", "گردشگری");

                intent.putExtra("TOKEN", Prefs.getString("gds_token", ""));
                startActivityForResult(intent, 100);
                break;
            }
            case 12: //Hotel
            {
 /*               StartEniacHotelActivity   eniacHotelActivity =new StartEniacHotelActivity("ZWQzNzkwYjctYzBmMy00MTc0LWFmMjYtYTc0NWE0ZTM1OGRh",
                        "0037250100293610", "1397", getActivity(), new HotelReservationData()
                {
                    @Override
                    public void hotelReserveListener(HotelItem hotelItem, String s)
                    {

                    }

                    @Override
                    public void hotelConfirmToSendSmsListener(Boolean aBoolean)
                    {

                    }
                }, 2);
                eniacHotelActivity.startMainHotelActivity();*/
                Intent intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("URL", URl);
                intent.putExtra("Title", "گردشگری");

                intent.putExtra("TOKEN", Prefs.getString("gds_token", ""));
                startActivityForResult(intent, 100);
                break;
            }

            case 13: //Bus
            {
              /*  StartEniacBusActivity eniacBusActivity = new StartEniacBusActivity("ZWQzNzkwYjctYzBmMy00MTc0LWFmMjYtYTc0NWE0ZTM1OGRh",
                        "0037250100293610", "1397", getActivity(), new BusLockSeat()
                {
                    @Override
                    public void LockSeatListener(LockSeatResponse lockSeatResponse, String s, String s1, List<Company> list)
                    {

                    }

                    @Override
                    public void issueBusReservation(SaleVerifyResponse saleVerifyResponse, boolean b)
                    {

                    }
                }, 2);
                eniacBusActivity.startMainBusActivity();*/

                Intent intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("URL", URl);
                intent.putExtra("Title", "گردشگری");

                intent.putExtra("TOKEN", Prefs.getString("gds_token", ""));
                startActivityForResult(intent, 100);


                //mainView.onPackSimCard();

                break;
            }

            case 4:
            {
                mainView.onBill();
                break;
            }

            case 42: //Pack
            {
                mainView.onPackSimCard(2);
                break;
            }

            case 41: //Charge
            {
                mainView.onChargeSimCard(2);
                break;
            }

            case 7:
            {
//                mainView.doTransferMoney();
                break;
            }

            case 43: //خرید بدون کارت
            {
//                mainView.doTransferMoney();
                break;
            }

            //بیمه
            case 21: //بیمه شخص ثالث
            case 22: //بیمه بدنه
            case 29: //بیمه موتورسیکلت
            case 23: //بیمه مسافرتی ویژه
            case 24: //بیمه مسافرتی
            case 25: //بیمه آتش سوزی
            case 26: //بیمه تجهیزات الکترونیکی
            case 27: //بیمه زلزله
            case 28: //بیمه درمان
            {
                getPermissionAndOpenInsurance(URl);

                break;
            }
            //الوپارک
            case 31: //  پارکینگ عمومی
            {
/*

                Bundle headers = new Bundle();
                headers.putString("Authorization", Prefs.getString("accessToken",""));

                Uri uri = Uri.parse(Prefs.getString("alopark_token", ""));
                CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();
                intentBuilder.setToolbarColor(ContextCompat.getColor(Objects.requireNonNull(getActivity()), R.color.colorPrimaryDark));
                intentBuilder.setSecondaryToolbarColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));

                CustomTabsIntent customTabsIntent = intentBuilder.build();
                customTabsIntent.intent.putExtra(Browser.EXTRA_HEADERS, headers);

                customTabsIntent.launchUrl(getActivity(), uri);
*/

/*
                Intent intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("URL", URl);
                intent.putExtra("Title", "الو پارک");

                intent.putExtra("TOKEN", Prefs.getString("alopark_token", ""));
                startActivityForResult(intent,100);*/

                Intent intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("URL", Prefs.getString("alopark_token", ""));
                intent.putExtra("Title", "الوپارک");

                intent.putExtra("TOKEN", "");
                startActivityForResult(intent, 100);


                // Utility.openUrlCustomTab(getActivity(), Prefs.getString("alopark_token", ""));
                break;
            }
            case 32: //  پارک حاشیه ای
            {
                Intent intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("URL", URl);
                intent.putExtra("Title", "الوپارک");

                intent.putExtra("TOKEN", "");
                startActivityForResult(intent, 100);

                // Utility.openUrlCustomTab(getActivity(), URl);
                break;
            }
            case 73: //  پارک حاشیه ای
            {
                Utility.openUrlCustomTab(getActivity(), baseuUrl);
                break;
            }
        }
    }

    private void getPermissionAndOpenInsurance(String URl)
    {
        new TedPermission(SingletonContext.getInstance().getContext())
                .setPermissionListener(new PermissionListener()
                {
                    @Override
                    public void onPermissionGranted()
                    {

                        // Utility.openUrlCustomTab(getActivity(), URl);
                        Intent intent = new Intent(getActivity(), WebActivity.class);
                        intent.putExtra("URL", URl);
                        intent.putExtra("Title", "بیمه");

                        intent.putExtra("bimeh_api_key", Prefs.getString("bimeh_api_key", ""));
                        intent.putExtra("bimeh_call_back", Prefs.getString("bimeh_call_back", ""));
                        intent.putExtra("TOKEN", Prefs.getString("bimeh_token", ""));
                        intent.putExtra("bimeh_base_url", Prefs.getString("bimeh_base_url", ""));
                        startActivityForResult(intent, 100);
                    }

                    @Override
                    public void onPermissionDenied(ArrayList<String> deniedPermissions)
                    {
                        MessageAlertDialog dialog = new MessageAlertDialog(getActivity(), "",
                                "برای ارسال تصویر اسناد بیمه، اخذ این مجوز الزامی است. ",
                                true, MessageAlertDialog.TYPE_MESSAGE, new MessageAlertDialog.OnConfirmListener()
                        {
                            @Override
                            public void onConfirmClick()
                            {
                                getPermissionAndOpenInsurance(URl);
                            }

                            @Override
                            public void onCancelClick()
                            {

                            }
                        }
                        );
                        dialog.show(((Activity) context).getFragmentManager(), "dialogMessage");
                    }
                })
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
                .check();
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

}