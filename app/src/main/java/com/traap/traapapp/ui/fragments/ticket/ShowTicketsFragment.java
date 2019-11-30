package com.traap.traapapp.ui.fragments.ticket;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.model.getTicketInfo.GetTicketInfoResponse;
import com.traap.traapapp.apiServices.model.showTicket.ShowTicketItem;
import com.traap.traapapp.ui.adapters.ticket.ShowTicketAdapter;
import com.traap.traapapp.ui.dialogs.MessageAlertDialog;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.ui.fragments.ticket.ticketInfo.TicketInfoImpl;
import com.traap.traapapp.ui.fragments.ticket.ticketInfo.TicketInfoInteractor;
import com.traap.traapapp.utilities.ScreenShot;
import com.traap.traapapp.utilities.Tools;

/**
 * Created by MahtabAzizi on 10/28/2019.
 */
public class ShowTicketsFragment extends Fragment implements View.OnClickListener, TicketInfoInteractor.OnFinishedTicketInfoListener
{

    private View view;
    private OnClickContinueBuyTicket onClickContinueBuyTicketListener;
    private View btnShareTicket, btnPaymentConfirm, btnBackToHome;
    private TextView tvDescTicket;
    private String firstName, lastName;
    private RecyclerView rvTickets;
    private LinearLayoutManager linearLayoutManager;
    private ShowTicketAdapter showTicketAdapter;
    private ArrayList<ShowTicketItem> ticketItems = new ArrayList<>();
    private MainActionView mainView;
    private MessageAlertDialog.OnConfirmListener listener = null;
    private LinearLayout llFram;
    private TicketInfoImpl ticketInfo;


    public ShowTicketsFragment()
    {
    }

    /**
     * Receive the model list
     */
    public static ShowTicketsFragment newInstance(String s, OnClickContinueBuyTicket onClickContinueBuyTicket, MainActionView mainActionView)
    {
        ShowTicketsFragment fragment = new ShowTicketsFragment();
        fragment.setOnClickContinueBuyTicket(onClickContinueBuyTicket);

        fragment.setMainView(mainActionView);

        return fragment;
    }
    public static ShowTicketsFragment newInstance(MainActionView mainActionView)
    {
        ShowTicketsFragment fragment = new ShowTicketsFragment();
       // fragment.setOnClickContinueBuyTicket(onClickContinueBuyTicket);

        fragment.setMainView(mainActionView);

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


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.show_tickets_fragment, container, false);
        listener = new MessageAlertDialog.OnConfirmListener()
        {


            @Override
            public void onConfirmClick()
            {
                mainView.backToMainFragment();

            }

            @Override
            public void onCancelClick()
            {
                mainView.backToMainFragment();
            }
        };
        ticketInfo = new TicketInfoImpl();
        initView();
        return view;
    }

    private void initView()
    {
        llFram = view.findViewById(R.id.llFram);
        tvDescTicket = view.findViewById(R.id.tvDescTicket);
        btnShareTicket = view.findViewById(R.id.btnShareTicket);
        btnPaymentConfirm = view.findViewById(R.id.btnPaymentConfirm);
        btnBackToHome = view.findViewById(R.id.btnBackToHome);
        rvTickets = view.findViewById(R.id.rvTickets);
        if (Prefs.getString("firstName", "").isEmpty())
        {
            firstName = "کاربر";
        } else
        {
            firstName = Prefs.getString("firstName", "");
        }
        lastName = Prefs.getString("lastName", "");
        tvDescTicket.setText(firstName + " " + lastName + "عزیز؛");
        btnShareTicket.setOnClickListener(this);
        btnPaymentConfirm.setOnClickListener(this);
        btnBackToHome.setOnClickListener(this);

        linearLayoutManager = new LinearLayoutManager(getActivity());
        rvTickets.setLayoutManager(linearLayoutManager);

       /* Prefs.getInt("PositionId",1);
        Prefs.getInt("CountTicket",1);
        Prefs.getString("etNationalCode_1","");
        Prefs.getString("etFamily_1","");
        Prefs.getString("etName_1","");*/


    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);

    }

    @Override
    public void onDetach()
    {
        super.onDetach();
    }

    private void setOnClickContinueBuyTicket(OnClickContinueBuyTicket onClickContinueBuyTicket)
    {
        this.onClickContinueBuyTicketListener = onClickContinueBuyTicket;
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btnPaymentConfirm:

                onClickContinueBuyTicketListener.goBuyTicket();

                break;
            case R.id.btnBackToHome:

                //MessageAlertDialog dialog = new MessageAlertDialog(getActivity(), "بازگشت به خانه", "آیا از بستن این صفحه مطمئن هستید؟");
                MessageAlertDialog dialog = new MessageAlertDialog(getActivity(), "بازگشت به خانه", "آیا از بستن این صفحه مطمئن هستید؟",
                        false, "بله", "بستن", listener);
                dialog.show(getActivity().getFragmentManager(), "dialog");


                break;
            case R.id.btnShareTicket:
                new ScreenShot(llFram, getActivity());
                // Tools.showToast(getContext(), "share");
                break;
        }
    }


    public void setSharedData()
    {
        BuyTicketsFragment.buyTicketsFragment.showLoading();
        ticketInfo.reservationRequest(this, 1212);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        rvTickets.setLayoutManager(linearLayoutManager);

      /*  for (int i = 0; i <Prefs.getInt("CountTicket",1) ; i++)
        {
            if(i==0)
                ticketItems.add(new ShowTicketItem(Prefs.getString("etFamily_1","")+" "+Prefs.getString("etName_1",""), Prefs.getString("etNationalCode_1",""),"جایگاه "+Prefs.getInt("PositionId",1)));

            if(i==1)
                ticketItems.add(new ShowTicketItem(Prefs.getString("etFamily_2","")+" "+Prefs.getString("etName_2",""), Prefs.getString("etNationalCode_2",""),"جایگاه "+Prefs.getInt("PositionId",1)));

            if(i==2)
                ticketItems.add(new ShowTicketItem(Prefs.getString("etFamily_3","")+" "+Prefs.getString("etName_3",""), Prefs.getString("etNationalCode_3",""),"جایگاه "+Prefs.getInt("PositionId",1)));

            if(i==3)
                ticketItems.add(new ShowTicketItem(Prefs.getString("etFamily_4","")+" "+Prefs.getString("etName_4",""), Prefs.getString("etNationalCode_4",""),"جایگاه "+Prefs.getInt("PositionId",1)));

            if(i==4)
                ticketItems.add(new ShowTicketItem(Prefs.getString("etFamily_5","")+" "+Prefs.getString("etName_4",""), Prefs.getString("etNationalCode_5",""),"جایگاه "+Prefs.getInt("PositionId",1)));


        }*/
        //showTicketAdapter = new ShowTicketAdapter(ticketItems);
        //rvTickets.setAdapter(showTicketAdapter);


    }

    @Override
    public void onFinishedTicketInfo(GetTicketInfoResponse response)
    {

        showTicketAdapter = new ShowTicketAdapter(response.getResults(), mainView);
        rvTickets.setAdapter(showTicketAdapter);
        BuyTicketsFragment.buyTicketsFragment.hideLoading();


    }

    @Override
    public void onErrorTicketInfo(String error)
    {

        Tools.showToast(getContext(), error, R.color.red);
        BuyTicketsFragment.buyTicketsFragment.hideLoading();

    }
}
