package com.traap.traapapp.ui.fragments.ticket;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.model.getTicketInfo.GetTicketInfoResponse;
import com.traap.traapapp.apiServices.model.showTicket.ShowTicketItem;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.ui.activities.main.MainActivity;
import com.traap.traapapp.ui.adapters.ticket.ShowTicketAdapter;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.dialogs.MessageAlertDialog;
import com.traap.traapapp.ui.fragments.main.BuyTicketAction;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.ui.fragments.ticket.ticketInfo.TicketInfoImpl;
import com.traap.traapapp.ui.fragments.ticket.ticketInfo.TicketInfoInteractor;
import com.traap.traapapp.utilities.Logger;
import com.traap.traapapp.utilities.ScreenShot;
import com.traap.traapapp.utilities.Tools;

/**
 * Created by MahtabAzizi on 10/28/2019.
 */
public class ShowTicketsFragment extends BaseFragment implements View.OnClickListener, TicketInfoInteractor.OnFinishedTicketInfoListener
{

    private TextView btnBackToDetail, tvCountTicket, tvSelectPosition, tvFullInfo, tvPrintTicket;
    private ImageView ivCountTicket, ivSelectPosition, ivFullInfo, ivPrintTicket;
    private View vOneToTow, vZeroToOne, vTowToThree;
    private TicketInfoImpl ticketInfo;
    private TextView tvTitle, tvUserName;
    private View imgBack, imgMenu;

    private TextView tvPopularPlayer;
    private View view;
    private OnClickContinueBuyTicket onClickContinueBuyTicketListener;
    private View btnShareTicket, btnPaymentConfirm, btnBackToHome,btnReturn;
    private TextView tvDescTicket;
    private String firstName, lastName;
    private RecyclerView rvTickets;
    private LinearLayoutManager linearLayoutManager;
    private ShowTicketAdapter showTicketAdapter;
    private ArrayList<ShowTicketItem> ticketItems = new ArrayList<>();
    private MainActionView mainView;
    private MessageAlertDialog.OnConfirmListener listener = null;
    private LinearLayout llSuccessPayment,llErrorPayment;
    private String refrenceNumber="";
    private TextView txRefrenceNumber,tvRefrenceNumberFromErrorPayment;
    private boolean isTransactionList=false;
    private View llSelect;


    public ShowTicketsFragment()
    {
    }

    /**
     * Receive the model list
     */
 /*   public static ShowTicketsFragment newInstance(String s, OnClickContinueBuyTicket onClickContinueBuyTicket, MainActionView mainActionView)
    {
        ShowTicketsFragment fragment = new ShowTicketsFragment();
        fragment.setOnClickContinueBuyTicket(onClickContinueBuyTicket);

        fragment.setMainView(mainActionView, refrenceNumber);

        return fragment;
    }*/
    public static ShowTicketsFragment newInstance(MainActionView mainActionView, String refrenceNumber)
    {
        ShowTicketsFragment fragment = new ShowTicketsFragment();
       // fragment.setOnClickContinueBuyTicket(onClickContinueBuyTicket);

        fragment.setMainView(mainActionView,refrenceNumber);

        return fragment;
    }


    private void setMainView(MainActionView mainView, String refrenceNumber)
    {
        this.mainView = mainView;
        this.refrenceNumber=refrenceNumber;
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

        ticketInfo = new TicketInfoImpl();

        initView();
        setSelectedLayoutChecked();
        return view;
    }

    private void initView()
    {
        mainView.showLoading();
        try
        {
            tvTitle = view.findViewById(R.id.tvTitle);
            tvUserName = view.findViewById(R.id.tvUserName);
            tvUserName.setText(TrapConfig.HEADER_USER_NAME);
            imgMenu = view.findViewById(R.id.imgMenu);

            imgMenu.setVisibility(View.GONE);
            //imgMenu.setOnClickListener(v -> mainView.openDrawerVideos());

            tvPopularPlayer = view.findViewById(R.id.tvPopularPlayer);
            tvPopularPlayer.setText(Prefs.getString("PopularPlayer", "12"));

            imgBack = view.findViewById(R.id.imgBack);
            imgBack.setOnClickListener(v ->
            {
                getActivity().onBackPressed();
            });


            tvTitle.setText("صدور بلیت");
        } catch (Exception e)
        {

        }
        listener = new MessageAlertDialog.OnConfirmListener()
        {


            @Override
            public void onConfirmClick()
            {
                getActivity().onBackPressed();

            }

            @Override
            public void onCancelClick()
            {
            }

        };
        ivCountTicket = view.findViewById(R.id.ivCountTicket);
        tvCountTicket = view.findViewById(R.id.tvCountTicket);
        ivSelectPosition = view.findViewById(R.id.ivSelectPosition);
        tvSelectPosition = view.findViewById(R.id.tvSelectPosition);
        ivFullInfo = view.findViewById(R.id.ivFullInfo);
        tvFullInfo = view.findViewById(R.id.tvFullInfo);
        ivPrintTicket = view.findViewById(R.id.ivPrintTicket);
        tvPrintTicket = view.findViewById(R.id.tvPrintTicket);
        vZeroToOne = view.findViewById(R.id.vZeroToOne);
        vOneToTow = view.findViewById(R.id.vOneToTow);
        vTowToThree = view.findViewById(R.id.vTowToThree);
        llSelect=view.findViewById(R.id.llSelect);

        txRefrenceNumber=view.findViewById(R.id.txRefrenceNumber);
        txRefrenceNumber.setText(" کد پیگیری پرداخت: "+refrenceNumber);
        tvRefrenceNumberFromErrorPayment=view.findViewById(R.id.tvRefrenceNumberFromErrorPayment);
        tvRefrenceNumberFromErrorPayment.setText(" کد پیگیری پرداخت: "+refrenceNumber);

        llSuccessPayment = view.findViewById(R.id.llSuccessPayment);
        llErrorPayment=view.findViewById(R.id.llErrorPayment);
        btnShareTicket = view.findViewById(R.id.btnShareTicket);
        btnPaymentConfirm = view.findViewById(R.id.btnPaymentConfirm);

        btnReturn=view.findViewById(R.id.btnReturn);
        btnReturn.setOnClickListener(this);

        btnBackToHome = view.findViewById(R.id.btnBackToHome);
        rvTickets = view.findViewById(R.id.rvTickets);
        btnShareTicket.setOnClickListener(this);
        btnPaymentConfirm.setOnClickListener(this);
        btnBackToHome.setOnClickListener(this);

        if(isTransactionList){
            llSelect.setVisibility(View.GONE);
        }else {
            llSelect.setVisibility(View.VISIBLE);
        }

        linearLayoutManager = new LinearLayoutManager(getContext());
        rvTickets.setLayoutManager(linearLayoutManager);
        try
        {
            ticketInfo.reservationRequest(this, Long.parseLong(refrenceNumber.trim()));

        } catch (Exception e)
        {

        }

    }

    private void setSelectedLayoutChecked()
    {
        ivCountTicket.setImageResource(R.drawable.select_step);
        tvCountTicket.setTextColor(getResources().getColor(R.color.textColorPrimary));

        ivSelectPosition.setImageResource(R.drawable.select_step);
        tvSelectPosition.setTextColor(getResources().getColor(R.color.textColorPrimary));





        vZeroToOne.setBackgroundColor(getResources().getColor(R.color.textColorPrimary));
        vOneToTow.setBackgroundColor(getResources().getColor(R.color.textColorPrimary));
        vTowToThree.setBackgroundColor(getResources().getColor(R.color.textColorPrimary));
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







    @Override
    public void onClick(View v)
    {

        switch (v.getId())
        {
            case R.id.btnPaymentConfirm:

                // onClickContinueBuyTicketListener.goBuyTicket();
                mainView.getBuyEnable(() -> { });

                break;
            case R.id.btnBackToHome:

                getActivity().onBackPressed();

                break;
            case R.id.btnShareTicket:
                new ScreenShot(rvTickets, getActivity());
                // Tools.showToast(getContext(), "share");
                break;

            case R.id.btnReturn:
                getActivity().onBackPressed();
                break;
        }
    }

    @Override
    public void onFinishedTicketInfo(GetTicketInfoResponse response)
    {

        showTicketAdapter = new ShowTicketAdapter(response.getResults(), mainView);
        rvTickets.setAdapter(showTicketAdapter);
        llSuccessPayment.setVisibility(View.VISIBLE);
        llErrorPayment.setVisibility(View.GONE);
        mainView.hideLoading();


    }

    @Override
    public void onErrorTicketInfo(String error)
    {
        llSuccessPayment.setVisibility(View.GONE);
        llErrorPayment.setVisibility(View.VISIBLE);
        Tools.showToast(getContext(), error, R.color.red);
        mainView.hideLoading();
        ivPrintTicket.setImageResource(R.drawable.un_check_mark);
        tvPrintTicket.setTextColor(getResources().getColor(R.color.textColorPrimary));




        ivFullInfo.setImageResource(R.drawable.un_check_mark);
        tvFullInfo.setTextColor(getResources().getColor(R.color.textColorPrimary));
    }

    @Override
    public void onErrorTicketInfo()
    {
        llSuccessPayment.setVisibility(View.GONE);
        llErrorPayment.setVisibility(View.VISIBLE);
        mainView.hideLoading();
        ivPrintTicket.setImageResource(R.drawable.un_check_mark);
        tvPrintTicket.setTextColor(getResources().getColor(R.color.textColorPrimary));


        ivFullInfo.setImageResource(R.drawable.un_check_mark);
        tvFullInfo.setTextColor(getResources().getColor(R.color.textColorPrimary));

    }

}
