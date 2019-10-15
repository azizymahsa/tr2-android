package ir.trap.tractor.android.ui.fragments.main;

import android.content.Context;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;

import java.util.ArrayList;
import java.util.List;

import ir.trap.tractor.android.R;
import ir.trap.tractor.android.apiServices.model.getMenu.response.GetMenuItemResponse;
import ir.trap.tractor.android.models.otherModels.MainServiceModelItem;
import ir.trap.tractor.android.apiServices.model.tourism.GetUserPassResponse;
import ir.trap.tractor.android.singleton.SingletonContext;
import ir.trap.tractor.android.ui.adapters.MainServiceModelAdapter;
import ir.trap.tractor.android.ui.base.BaseFragment;
import ir.trap.tractor.android.ui.others.MyCustomSliderView;
import ir.trap.tractor.android.utilities.Tools;
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

public class MainFragment extends BaseFragment implements onConfirmUserPassGDS, MainServiceModelAdapter.OnItemClickListener,
        FlightReservationData, BusLockSeat, HotelReservationData, BaseSliderView.OnSliderClickListener
{
//    private CircularProgressButton btnBus, btnFlight, btnHotel, btnDoTransfer, btnChargeSimCard, btnPackSimCard, btnBill;

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private MainServiceModelAdapter adapter;

    private List<MainServiceModelItem> list = new ArrayList<>();

    private SliderLayout mDemoSlider;

    private ArrayList<GetMenuItemResponse> footballServiceList, chosenServiceList;

    private MainActionView mainView;

    public MainFragment()
    {
        // Required empty public constructor
    }

    public static MainFragment newInstance(MainActionView mainActionView,
                                           ArrayList<GetMenuItemResponse> footballServiceList,
                                           ArrayList<GetMenuItemResponse> chosenServiceList
    )
    {
        MainFragment fragment = new MainFragment();
        fragment.setMainView(mainActionView);

        Bundle args = new Bundle();
        args.putParcelableArrayList("chosenServiceList", chosenServiceList);
        args.putParcelableArrayList("footballServiceList", footballServiceList);

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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        mDemoSlider = rootView.findViewById(R.id.slider);

        setSlider();

        recyclerView = rootView.findViewById(R.id.recyclerView);

        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, true);
        recyclerView.setLayoutManager(layoutManager);

        list = fillMenuRecyclerList();

        adapter = new MainServiceModelAdapter(getActivity(), list, this);
        recyclerView.setAdapter(adapter);


        return rootView;
    }

    private List<MainServiceModelItem> fillMenuRecyclerList()
    {
        List<MainServiceModelItem> newList = new ArrayList<>();

        for (GetMenuItemResponse itemResponse : chosenServiceList)
        {
            if (itemResponse.getIsVisible())
            {
                MainServiceModelItem item = new MainServiceModelItem();

                item.setId(itemResponse.getKeyId());
                item.setTitle(itemResponse.getTitle());
                item.setImageLink(itemResponse.getImageName());
                item.setKeyName(itemResponse.getKeyName());

                newList.add(item);
            }
        }

//        MainServiceModelItem item = new MainServiceModelItem();
//
//        item.setId(1);
//        item.setTitle("بلیط هواپیما");
//        newList.add(item);
//
//        item = new MainServiceModelItem();
//        item.setId(2);
//        item.setTitle("رزرو هتل");
//        newList.add(item);
//
//        item = new MainServiceModelItem();
//        item.setId(3);
//        item.setTitle("بلیط اتوبوس");
//        newList.add(item);
//
//        item = new MainServiceModelItem();
//        item.setId(4);
//        item.setTitle("پرداخت قبض");
//        newList.add(item);
//
//        item = new MainServiceModelItem();
//        item.setId(5);
//        item.setTitle("بسته");
//        newList.add(item);
//
//        item = new MainServiceModelItem();
//        item.setId(6);
//        item.setTitle("شارژ");
//        newList.add(item);
//
//        item = new MainServiceModelItem();
//        item.setId(7);
//        item.setTitle("کارت به کارت");
//        newList.add(item);

        return newList;
    }

    private void setSlider()
    {
        for (int i = 1 ; i < 4 ; i++)
        {
            MyCustomSliderView textSliderView = new MyCustomSliderView(getActivity());
//            textSliderView.setStadiumName("ورزشگاه یادگار امام تبریز");
//            textSliderView.setDateTime("یکشنبه 12 آبان 1398 - 18:00");
//            textSliderView.setColorDateTime("#000");
//            textSliderView.setColorStadiumName("#aaa");
            textSliderView.setHeaderText(String.valueOf(i));
//            textSliderView.setImgBackgroundLink();
//            textSliderView.setImgGuestLink();;
//            textSliderView.setImgHostLink();;
            textSliderView.setOnSliderClickListener(this);

            mDemoSlider.addSlider(textSliderView);


        }
//            mDemoSlider.setPresetTransformer(SliderLayout.Transformer.RotateDown);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        PagerIndicator pagerIndicator = new PagerIndicator(getActivity());
        pagerIndicator.setDefaultIndicatorColor(R.color.currentColor, R.color.grayColor);
        mDemoSlider.setCustomIndicator(pagerIndicator);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(10000);
//            mDemoSlider.addOnPageChangeListener(this);
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener)
//        {
//            mListener = (OnFragmentInteractionListener) context;
//        } else
//        {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
//        mListener = null;
    }


    @Override
    public void onGdsFlight(GetUserPassResponse response)
    {
        mainView.hideLoading();

        StartEniacFlightActivity flightActivity = new StartEniacFlightActivity(response.getUniqeCode(),
                response.getUsername(), response.getPassword(), this, 0);

        flightActivity.startMainFlight();
    }


    @Override
    public void onGdsBus(GetUserPassResponse response)
    {
        mainView.hideLoading();

        StartEniacBusActivity busActivity = new StartEniacBusActivity(response.getUniqeCode(),
                response.getUsername(), response.getPassword(), getActivity(), this, 0);

        busActivity.startMainBusActivity();
    }


    @Override
    public void onGdsHotel(GetUserPassResponse response)
    {
        mainView.hideLoading();

        StartEniacHotelActivity hotelActivity = new StartEniacHotelActivity(response.getUniqeCode(),
                response.getUsername(), response.getPassword(), SingletonContext.getInstance().getContext(),
                this, 0);

        hotelActivity.startMainHotelActivity();
    }


    @Override
    public void onGdsError(String message)
    {
        mainView.hideLoading();

        Tools.showToast(getActivity(), message, R.color.red);
    }



    @Override
    public void LockSeatListener(LockSeatResponse lockSeatResponse, String s, String s1, List<Company> list)
    {

    }

    @Override
    public void issueBusReservation(SaleVerifyResponse saleVerifyResponse, boolean b)
    {

    }



    @Override
    public void flightReservationListener(ReservationResponse reservationResponse, FlightReservation flightReservation, String s, String s1)
    {

    }

    @Override
    public void flightConfirmToSendSms(Boolean aBoolean)
    {

    }



    @Override
    public void hotelReserveListener(HotelItem hotelItem, String s)
    {

    }

    @Override
    public void hotelConfirmToSendSmsListener(Boolean aBoolean)
    {

    }

    @Override
    public void onSliderClick(BaseSliderView slider)
    {

    }

    @Override
    public void onChosenItemClick(View view, Integer id)
    {
        switch (id)
        {
            case 11://Flight
            {
                mainView.showLoading();
                GetUserPassGdsImp.getUserPassGds(GetUserPassGdsImp.GDS_TYPE_FLIGHT, MainFragment.this);
                break;
            }

            case 12: //Hotel
            {
                mainView.showLoading();
                GetUserPassGdsImp.getUserPassGds(GetUserPassGdsImp.GDS_TYPE_HOTEL, MainFragment.this);
                break;
            }

            case 13: //Bus
            {
                mainView.showLoading();
                GetUserPassGdsImp.getUserPassGds(GetUserPassGdsImp.GDS_TYPE_BUS, MainFragment.this);
                break;
            }

            case 4:
            {
//                mainView.onBill();
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

        }
    }
}
