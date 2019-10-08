package ir.trap.tractor.android.ui.fragments.main;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import ir.trap.tractor.android.R;
import ir.trap.tractor.android.adapter.ImagePagerAdapter;
import ir.trap.tractor.android.apiServices.model.tourism.GetUserPassResponse;
import ir.trap.tractor.android.singleton.SingletonContext;
import ir.trap.tractor.android.utilities.Tools;
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
import ru.tinkoff.scrollingpagerindicator.ScrollingPagerIndicator;

public class MainFragment extends Fragment implements onConfirmUserPassGDS,
        FlightReservationData, BusLockSeat, HotelReservationData
{
    private CircularProgressButton btnBus, btnFlight, btnHotel;

    private RecyclerView rvList;
    private ImagePagerAdapter imagePagerAdapter;
    private ScrollingPagerIndicator indicator;
    private LinearLayoutManager linearLayoutManager;

    public MainFragment()
    {
        // Required empty public constructor
    }

    public static MainFragment newInstance()
    {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        btnHotel = rootView.findViewById(R.id.btnHotel);
        btnFlight = rootView.findViewById(R.id.btnFlight);
        btnBus = rootView.findViewById(R.id.btnBus);

        btnHotel.setOnClickListener(clickListener);
        btnFlight.setOnClickListener(clickListener);
        btnBus.setOnClickListener(clickListener);



        indicator = rootView.findViewById(R.id.indicator);
        rvList = rootView.findViewById(R.id.rvList);

        imagePagerAdapter = new ImagePagerAdapter(getActivity());
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rvList.setLayoutManager(linearLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvList.getContext(),
                linearLayoutManager.getOrientation());
        rvList.addItemDecoration(dividerItemDecoration);
        rvList.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState)
            {
                super.onScrollStateChanged(recyclerView, newState);


                if (linearLayoutManager.findFirstVisibleItemPosition() == 0)
                    if (newState == RecyclerView.SCROLL_STATE_IDLE)
                    {

                    }
            }
        });
        rvList.setAdapter(imagePagerAdapter);
        indicator.attachToRecyclerView(rvList);


        return rootView;
    }

    View.OnClickListener clickListener = view ->
    {
        switch (view.getId())
        {
            case R.id.btnHotel:
            {
                GetUserPassGdsImp.getUserPassGds(GetUserPassGdsImp.GDS_TYPE_HOTEL, MainFragment.this);
                break;
            }
            case R.id.btnFlight:
            {
                GetUserPassGdsImp.getUserPassGds(GetUserPassGdsImp.GDS_TYPE_FLIGHT, MainFragment.this);
                break;
            }
            case R.id.btnBus:
            {
                GetUserPassGdsImp.getUserPassGds(GetUserPassGdsImp.GDS_TYPE_BUS, MainFragment.this);
                break;
            }
        }
    };


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
        StartEniacFlightActivity flightActivity = new StartEniacFlightActivity(response.getUniqeCode(),
                response.getUsername(), response.getPassword(), this, 1);

        flightActivity.startMainFlight();
    }


    @Override
    public void onGdsBus(GetUserPassResponse response)
    {
        StartEniacFlightActivity busActivity = new StartEniacFlightActivity(response.getUniqeCode(),
                response.getUsername(), response.getPassword(), this, 1);

        busActivity.startMainFlight();
    }


    @Override
    public void onGdsHotel(GetUserPassResponse response)
    {
        StartEniacHotelActivity hotelActivity = new StartEniacHotelActivity(response.getUniqeCode(),
                response.getUsername(), response.getPassword(), SingletonContext.getInstance().getContext(),
                this, 1);

        hotelActivity.startMainHotelActivity();
    }


    @Override
    public void onGdsError(String message)
    {
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
}
