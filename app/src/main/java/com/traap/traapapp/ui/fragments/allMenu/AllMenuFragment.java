package com.traap.traapapp.ui.fragments.allMenu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Browser;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

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

import com.pixplicity.easyprefs.library.Prefs;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.com.simplepass.loading_button_lib.interfaces.OnAnimationEndListener;

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
import com.traap.traapapp.ui.activities.userProfile.UserProfileActivity;
import com.traap.traapapp.ui.activities.web.WebActivity;
import com.traap.traapapp.ui.adapters.allMenu.AllMenuServiceModelAdapter;
//import com.traap.traapapp.ui.adapters.AllMenuServiceModelAdapter;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.adapters.allMenu.ItemRecyclerViewAdapter;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.ui.fragments.main.onConfirmUserPassGDS;
import com.traap.traapapp.ui.fragments.webView.WebFragment;
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

public class AllMenuFragment extends BaseFragment implements OnAnimationEndListener, View.OnClickListener,
        ItemRecyclerViewAdapter.OnItemClickListenerItem, TextWatcher,
        OnServiceStatus<WebServiceClass<GetAllMenuResponse>>,
        onConfirmUserPassGDS, AllMenuServiceModelAdapter.OnItemAllMenuClickListener
//        ,
//        HotelReservationData, BusLockSeat, FlightReservationData
{
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private AllMenuServiceModelAdapter adapter;
    private View rootView;
    private List<MainServiceModelItem> list = new ArrayList<>();
    private ArrayList<GetMenuItemResponse> chosenServiceList;
    private MainActionView mainView;
    Context context = getContext();
    private RecyclerView rvGrid;
    private String unicCode = "";
    private Toolbar mToolbar;
    private TextView tvTitle, tvUserName, tvPopularPlayer;
    private View imgBack, imgMenu;
    private View rlShirt;
    private Fragment fragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    public AllMenuFragment()
    {

    }


    public static AllMenuFragment newInstance(MainActionView mainView, ArrayList<GetMenuItemResponse> allServicesList)
    {
        AllMenuFragment f = new AllMenuFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("allServicesList", allServicesList);
//        args.putParcelableArrayList("footballServiceList", footballServiceList);

        f.setArguments(args);
        f.setMainView(mainView);
        return f;
    }

    private void setMainView(MainActionView mainView)
    {
        this.mainView = mainView;
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
            tvUserName = mToolbar.findViewById(R.id.tvUserName);

            tvUserName.setText(TrapConfig.HEADER_USER_NAME);
            rlShirt = rootView.findViewById(R.id.rlShirt);
            rlShirt.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    startActivity(new Intent(SingletonContext.getInstance().getContext(), UserProfileActivity.class));
                }
            });
            mToolbar.findViewById(R.id.imgMenu).setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    mainView.openDrawer();
                }
            });
            tvTitle = rootView.findViewById(R.id.tvTitle);
            tvTitle.setText("سرویس ها");
            imgMenu = rootView.findViewById(R.id.imgMenu);

            imgMenu.setOnClickListener(v -> mainView.openDrawer());
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
        }


        return rootView;
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
                    // item.setId(itemResponse.getKeyId());
                    //item.setTitle(itemResponse.getTitle());
                    item.setTitle(itemResponse.getTitle());
                    // item.setImageLink(itemResponse.getImageName());
                    item.setImageLink(itemResponse.getLogo());
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
                // startActivity(new Intent(this, A.class));
                return;
            }
            if (response.info.statusCode != 200)
            {
                // startActivity(new Intent(this, LoginActivity.class));
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
        mainView.showError(message);

        mainView.hideLoading();

    }


    @Override
    public void onStop()
    {
        super.onStop();


    }

    @Override
    public void onResume()
    {
        super.onResume();

    }

    @Override
    public void onPause()
    {
        super.onPause();
    }


    @Override
    public void onAnimationEnd()
    {


    }

    @Override
    public void onClick(View view)
    {
       /* switch (view.getId())
        {
            case R.id.btnConfirm:

                break;


        }*/

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

        Tools.showToast(getActivity(), message, R.color.red);
    }


//    @Override
//    public void hotelReserveListener(HotelItem hotelItem, String s)
//    {
//
//    }

//    @Override
//    public void hotelConfirmToSendSmsListener(Boolean aBoolean)
//    {
//
//    }

//    @Override
//    public void LockSeatListener(LockSeatResponse lockSeatResponse, String s, String s1, List<Company> list)
//    {
//
//    }

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

        switch (id)
        {
            case 1://گردشگری
            {
                loadSubMenu(list);

                break;
            }
            case 2://بیمه
            {
                loadSubMenu(list);

                break;
            }
            case 3://الوپارک
            {
                loadSubMenu(list);

                break;
            }
            case 4://شارژو بسته
            {
                loadSubMenu(list);

                break;
            }
            case 6://قبوض
            {
                loadSubMenu(list);

                break;
            }
        }

    }

    private void loadSubMenu(List<SubMenu> list)
    {
        rvGrid.setLayoutManager(new GridLayoutManager(getContext(), 3));
        rvGrid.setAdapter(new ItemRecyclerViewAdapter(getContext(), list, this));//, interactionListener));
    }

    @Override
    public void onChosenItemClickk(View view, Integer id, String URl)
    {
        switch (id)
        {


            case 11://Flight ata
            {
//
                // Utility.openUrlCustomTab(getActivity(), "https://tourism.traap.com/fa/ata-flights");

              /*  Intent intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("URL", URl);//"https://tourism.traap.com/fa/ata-flights");
                intent.putExtra("TOKEN", Prefs.getString("gds_token", ""));
                startActivity(intent);*/
                /*fragment = WebFragment.newInstance(mainView,URl,Prefs.getString("gds_token", ""));
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_container, fragment, "findThisFragment")
                        .addToBackStack(null)
                        .commit();*/
                Intent intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("URL", URl);
                intent.putExtra("Title", "گردشگری");

                intent.putExtra("TOKEN", Prefs.getString("gds_token", ""));
                startActivity(intent);

                break;
            }
            case 14://Flight all
            {


              /*  fragment = WebFragment.newInstance(mainView,URl,Prefs.getString("gds_token", ""));
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_container, fragment, "findThisFragment")
                        .addToBackStack(null)
                        .commit();*/
                Intent intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("URL", URl);
                intent.putExtra("Title", "گردشگری");

                intent.putExtra("TOKEN", Prefs.getString("gds_token", ""));
                startActivity(intent);
                break;
            }
            case 12: //Hotel
            {
//
               /* fragment = WebFragment.newInstance(mainView,URl,Prefs.getString("gds_token", ""));
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_container, fragment, "findThisFragment")
                        .addToBackStack(null)
                        .commit();*/
                Intent intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("URL", URl);
                intent.putExtra("Title", "گردشگری");

                intent.putExtra("TOKEN", Prefs.getString("gds_token", ""));
                startActivity(intent);
                break;
            }

            case 13: //Bus
            {

            /*  Intent intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("URL", URl);
               intent.putExtra("Title", "گردشگری");

                intent.putExtra("TOKEN", Prefs.getString("gds_token", ""));
                startActivity(intent);*/

                mainView.onPackSimCard();

                break;
            }

            case 4:
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
            {
                Utility.openUrlCustomTab(getActivity(), URl);
                break;
            }
            case 22: //بیمه بدنه
            {
                Utility.openUrlCustomTab(getActivity(), URl);
                break;
            }
            case 29: //بیمه موتورسیکلت
            {
                Utility.openUrlCustomTab(getActivity(), URl);
                break;
            }
            case 23: //بیمه مسافرتی ویژه
            {
                Utility.openUrlCustomTab(getActivity(), URl);
                break;
            }
            case 24: //بیمه مسافرتی
            {
                Utility.openUrlCustomTab(getActivity(), URl);
                break;
            }
            case 25: //بیمه آتش سوزی
            {
                Utility.openUrlCustomTab(getActivity(), URl);
                break;
            }
            case 26: //بیمه تجهیزات الکترونیکی
            {
                Utility.openUrlCustomTab(getActivity(), URl);
                break;
            }
            case 27: //بیمه زلزله
            {
                Utility.openUrlCustomTab(getActivity(), URl);
                break;
            }
            case 28: //بیمه درمان
            {
                Utility.openUrlCustomTab(getActivity(), URl);
                break;
            }
            //الوپارک
            case 31: //  پارکینگ عمومی
            {
                Utility.openUrlCustomTab(getActivity(), URl);
                break;
            }
            case 32: //  پارک حاشیه ای
            {
                Utility.openUrlCustomTab(getActivity(), URl);
                break;
            }
        }
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