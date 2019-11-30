package com.traap.traapapp.ui.activities.ticket;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.traap.traapapp.ui.adapters.ticket.ShowTicketAdapter;
import com.traap.traapapp.ui.base.BaseActivity;
import com.traap.traapapp.ui.dialogs.MessageAlertDialog;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.ui.fragments.ticket.OnClickContinueBuyTicket;
import com.traap.traapapp.ui.fragments.ticket.ticketInfo.TicketInfoImpl;
import com.traap.traapapp.ui.fragments.ticket.ticketInfo.TicketInfoInteractor;
import com.traap.traapapp.utilities.ScreenShot;
import com.traap.traapapp.utilities.Tools;

public class ShowTicketActivity extends BaseActivity implements View.OnClickListener, TicketInfoInteractor.OnFinishedTicketInfoListener
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

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_ticket);

        if (savedInstanceState == null)
        {
            Bundle extras = getIntent().getExtras();
            if (extras == null)
            {

            } else
            {
                refrenceNumber = extras.getString("RefrenceNumber");
                isTransactionList=extras.getBoolean(            "isTransactionList",false);
            }
        }
        ticketInfo = new TicketInfoImpl();

        initView();
        setSelectedLayoutChecked();
    }

    private void initView()
    {
        showLoading();
        try
        {
            tvTitle = findViewById(R.id.tvTitle);
            tvUserName = findViewById(R.id.tvUserName);
            tvUserName.setText(TrapConfig.HEADER_USER_NAME);
            imgMenu = findViewById(R.id.imgMenu);

            imgMenu.setVisibility(View.GONE);
            //imgMenu.setOnClickListener(v -> mainView.openDrawerNews());

            tvPopularPlayer = findViewById(R.id.tvPopularPlayer);
            tvPopularPlayer.setText(Prefs.getString("PopularPlayer", ""));

            imgBack = findViewById(R.id.imgBack);
            imgBack.setOnClickListener(v ->
            {
               finish();
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
                finish();

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

        btnReturn=findViewById(R.id.btnReturn);
        btnReturn.setOnClickListener(this);

        btnBackToHome = findViewById(R.id.btnBackToHome);
        rvTickets = findViewById(R.id.rvTickets);
        btnShareTicket.setOnClickListener(this);
        btnPaymentConfirm.setOnClickListener(this);
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
            ticketInfo.reservationRequest(this, Integer.valueOf(refrenceNumber.trim()));

        } catch (Exception e)
        {

        }

    }

    private void setSelectedLayoutChecked()
    {
        ivCountTicket.setImageResource(R.drawable.select_step);
        tvCountTicket.setTextColor(getResources().getColor(R.color.g_btn_gradient_lighter));

        ivSelectPosition.setImageResource(R.drawable.select_step);
        tvSelectPosition.setTextColor(getResources().getColor(R.color.g_btn_gradient_lighter));


        ivFullInfo.setImageResource(R.drawable.select_step);
        tvFullInfo.setTextColor(getResources().getColor(R.color.g_btn_gradient_lighter));

        ivPrintTicket.setImageResource(R.drawable.select_step);
        tvPrintTicket.setTextColor(getResources().getColor(R.color.g_btn_gradient_lighter));

        vZeroToOne.setBackgroundColor(getResources().getColor(R.color.g_btn_gradient_lighter));
        vOneToTow.setBackgroundColor(getResources().getColor(R.color.g_btn_gradient_lighter));
        vTowToThree.setBackgroundColor(getResources().getColor(R.color.g_btn_gradient_lighter));
    }

    @Override
    public void onClick(View v)
    {

        switch (v.getId())
        {
            case R.id.btnPaymentConfirm:

                // onClickContinueBuyTicketListener.goBuyTicket();

                break;
            case R.id.btnBackToHome:

                finish();

              /*  MessageAlertDialog dialog = new MessageAlertDialog(this, "بازگشت به خانه", "آیا از بستن این صفحه مطمئن هستید؟",
                        false, "بله", "بستن", listener);
                dialog.show(this.getFragmentManager(), "dialog");*/


                break;
            case R.id.btnShareTicket:
                new ScreenShot(llSuccessPayment, this);
                // Tools.showToast(getContext(), "share");
                break;

            case R.id.btnReturn:
                finish();
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
        hideLoading();


    }

    @Override
    public void onErrorTicketInfo(String error)
    {
      //  ((TextView) findViewById(R.id.tvMessage)).setText("کاربر گرامی؛ متاسفانه خرید شما ناموفق بوده است.");
        llSuccessPayment.setVisibility(View.GONE);
        llErrorPayment.setVisibility(View.VISIBLE);
        Tools.showToast(this, error, R.color.red);
        hideLoading();
    }


    public void showLoading()
    {
        findViewById(R.id.rlLoading).setVisibility(View.VISIBLE);
    }

    public void hideLoading()
    {
        findViewById(R.id.rlLoading).setVisibility(View.GONE);
        runOnUiThread(() ->
        {
        });
    }
}
