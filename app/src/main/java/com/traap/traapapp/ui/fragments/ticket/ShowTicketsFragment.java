package com.traap.traapapp.ui.fragments.ticket;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.model.getTicketInfo.GetTicketInfoResponse;
import com.traap.traapapp.apiServices.model.showTicket.ShowTicketItem;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.singleton.SingletonNeedGetAllBoxesRequest;
import com.traap.traapapp.ui.activities.main.MainActivity;
import com.traap.traapapp.ui.adapters.ticket.ShowTicketAdapter;
import com.traap.traapapp.ui.base.BaseActivity;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.dialogs.MessageAlertDialog;
import com.traap.traapapp.ui.fragments.main.BuyTicketAction;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.ui.fragments.ticket.ticketInfo.TicketInfoImpl;
import com.traap.traapapp.ui.fragments.ticket.ticketInfo.TicketInfoInteractor;
import com.traap.traapapp.utilities.Logger;
import com.traap.traapapp.utilities.ScreenShot;
import com.traap.traapapp.utilities.Tools;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by MahtabAzizi on 10/28/2019.
 */
public class ShowTicketsFragment extends BaseActivity implements View.OnClickListener, TicketInfoInteractor.OnFinishedTicketInfoListener
{

    private TextView btnBackToDetail, tvCountTicket, tvSelectPosition, tvFullInfo, tvPrintTicket;
    private ImageView ivCountTicket, ivSelectPosition, ivFullInfo, ivPrintTicket;
    private View vOneToTow, vZeroToOne, vTowToThree;
    private TicketInfoImpl ticketInfo;
    private TextView tvTitle, tvUserName;
    private View imgBack, imgMenu;

    private Context context;

    private TextView tvPopularPlayer;
    private OnClickContinueBuyTicket onClickContinueBuyTicketListener;
    private View btnShareTicket, btnPaymentConfirm, btnBackToHome,btnReturn,btnSaveResult;
    private TextView tvDescTicket;
    private String firstName, lastName;
    private RecyclerView rvTickets;
    private LinearLayoutManager linearLayoutManager;
    private ShowTicketAdapter showTicketAdapter;
    private ArrayList<ShowTicketItem> ticketItems = new ArrayList<>();
   // private MainActionView mainView;
    private MessageAlertDialog.OnConfirmListener listener = null;
    private LinearLayout llSuccessPayment,llErrorPayment;
    private String refrenceNumber="";
    private TextView txRefrenceNumber,tvRefrenceNumberFromErrorPayment;
    private boolean isTransactionList=false;
    private View llSelect;
    private LinearLayout rlLoading;


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
/*
    public static ShowTicketsFragment newInstance(MainActionView mainActionView, String refrenceNumber)
    {
        ShowTicketsFragment fragment = new ShowTicketsFragment();
       // fragment.setOnClickContinueBuyTicket(onClickContinueBuyTicket);

        fragment.setMainView(mainActionView,refrenceNumber);

        return fragment;
    }
*/


/*
    private void setMainView(MainActionView mainView, String refrenceNumber)
    {
        this.mainView = mainView;
        this.refrenceNumber=refrenceNumber;
    }
*/





    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_tickets_fragment);

        ticketInfo = new TicketInfoImpl();

        initView();
        setSelectedLayoutChecked();
    }
    public void showLoading(){
        rlLoading.setVisibility(View.VISIBLE);
    }
    public void hideLoading(){
        rlLoading.setVisibility(View.GONE);
    }
    private void initView()
    {
        refrenceNumber=getIntent().getExtras().getString("RefrenceNumber");

        try
        {
            tvTitle = findViewById(R.id.tvTitle);
            tvUserName = findViewById(R.id.tvUserName);
            tvUserName.setText(TrapConfig.HEADER_USER_NAME);
            imgMenu = findViewById(R.id.imgMenu);
            rlLoading = findViewById(R.id.rlLoading);

            imgMenu.setVisibility(View.GONE);
            //imgMenu.setOnClickListener(v -> openDrawerVideos());

            tvPopularPlayer = findViewById(R.id.tvPopularPlayer);
            tvPopularPlayer.setText(Prefs.getString("PopularPlayer", "12"));

            imgBack = findViewById(R.id.imgBack);
            imgBack.setOnClickListener(v ->
            {
                onBackPressed();
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
            onBackPressed();

            }

            @Override
            public void onCancelClick()
            {
            }

        };
        ivCountTicket = findViewById(R.id.ivCountTicket);
        tvCountTicket = findViewById(R.id.tvCountTicket);
        ivSelectPosition = findViewById(R.id.ivSelectPosition);
        tvSelectPosition = findViewById(R.id.tvSelectPosition);
        ivFullInfo = findViewById(R.id.ivFullInfo);
        tvFullInfo = findViewById(R.id.tvFullInfo);
        ivPrintTicket = findViewById(R.id.ivPrintTicket);
        tvPrintTicket = findViewById(R.id.tvPrintTicket);
        vZeroToOne = findViewById(R.id.vZeroToOne);
        vOneToTow = findViewById(R.id.vOneToTow);
        vTowToThree = findViewById(R.id.vTowToThree);
        llSelect=findViewById(R.id.llSelect);

        txRefrenceNumber=findViewById(R.id.txRefrenceNumber);
        txRefrenceNumber.setText(" کد پیگیری پرداخت: "+refrenceNumber);
        tvRefrenceNumberFromErrorPayment=findViewById(R.id.tvRefrenceNumberFromErrorPayment);
        tvRefrenceNumberFromErrorPayment.setText(" کد پیگیری پرداخت: "+refrenceNumber);

        llSuccessPayment = findViewById(R.id.llSuccessPayment);
        llErrorPayment=findViewById(R.id.llErrorPayment);
        btnShareTicket = findViewById(R.id.btnShareTicket);
        btnPaymentConfirm = findViewById(R.id.btnPaymentConfirm);
        btnSaveResult=findViewById(R.id.btnSaveResult);

        btnReturn=findViewById(R.id.btnReturn);
        btnReturn.setOnClickListener(this);

        btnBackToHome = findViewById(R.id.btnBackToHome);
        rvTickets = findViewById(R.id.rvTickets);
        btnShareTicket.setOnClickListener(this);
        btnPaymentConfirm.setOnClickListener(this);
        btnSaveResult.setOnClickListener(this);
        btnBackToHome.setOnClickListener(this);

        if(isTransactionList){
            llSelect.setVisibility(View.GONE);
        }else {
            llSelect.setVisibility(View.VISIBLE);
        }

        linearLayoutManager = new LinearLayoutManager(this);
        rvTickets.setLayoutManager(linearLayoutManager);
        try
        {
            ticketInfo.reservationRequest(this, Long.parseLong(refrenceNumber.trim()));

        }
        catch (Exception e)
    {

    }
        showLoading();

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
    public void onClick(View v)
    {

        switch (v.getId())
        {
            case R.id.btnPaymentConfirm:
                SingletonNeedGetAllBoxesRequest.getInstance().setNeedRequest(true);

                // onClickContinueBuyTicketListener.goBuyTicket();
             //   getBuyEnable(() -> { });
             //   getActivity().finish();

                break;

            case R.id.btnSaveResult:
                new ScreenShot(rvTickets, (Activity) context, true, "برای ذخیره تصویر رسید، اخذ این مجوز الزامی است.");
                // showDialog();
                break;
            case R.id.btnBackToHome:
                SingletonNeedGetAllBoxesRequest.getInstance().setNeedRequest(true);

               onBackPressed();

                break;
            case R.id.btnShareTicket:
                SingletonNeedGetAllBoxesRequest.getInstance().setNeedRequest(true);

                new ScreenShot(rvTickets, (Activity) context);
                // showToast(getContext(), "share");
                break;

            case R.id.btnReturn:
                SingletonNeedGetAllBoxesRequest.getInstance().setNeedRequest(true);

              onBackPressed();
                break;
        }
    }

    @Override
    public void onFinishedTicketInfo(GetTicketInfoResponse response)
    {

        showTicketAdapter = new ShowTicketAdapter(response.getResults());
        rvTickets.setAdapter(showTicketAdapter);
        llSuccessPayment.setVisibility(View.VISIBLE);
        llErrorPayment.setVisibility(View.GONE);
        hideLoading();

        ivPrintTicket.setImageResource(R.drawable.select_step);
        tvPrintTicket.setTextColor(getResources().getColor(R.color.textColorPrimary));




        ivFullInfo.setImageResource(R.drawable.select_step);
        tvFullInfo.setTextColor(getResources().getColor(R.color.textColorPrimary));





    }

    @Override
    public void onErrorTicketInfo(String error)
    {
        llSuccessPayment.setVisibility(View.GONE);
        llErrorPayment.setVisibility(View.VISIBLE);
        showToast(context, error, R.color.red);
        hideLoading();
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
        hideLoading();
        ivPrintTicket.setImageResource(R.drawable.un_check_mark);
        tvPrintTicket.setTextColor(getResources().getColor(R.color.textColorPrimary));


        ivFullInfo.setImageResource(R.drawable.un_check_mark);
        tvFullInfo.setTextColor(getResources().getColor(R.color.textColorPrimary));

    }

}
