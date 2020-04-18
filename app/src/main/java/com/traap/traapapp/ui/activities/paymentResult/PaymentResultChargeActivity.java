package com.traap.traapapp.ui.activities.paymentResult;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.getTransaction.TransactionDetailResponse;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.ui.activities.main.MainActivity;
import com.traap.traapapp.ui.base.BaseActivity;
import com.traap.traapapp.ui.dialogs.MessageAlertDialog;
import com.traap.traapapp.ui.fragments.ticket.ShowTicketsFragment;
import com.traap.traapapp.utilities.ScreenShot;
import com.traap.traapapp.utilities.Tools;
import com.traap.traapapp.utilities.Utility;

public class PaymentResultChargeActivity extends BaseActivity implements View.OnClickListener
{

    private String refrenceNumber;
    private boolean statusPayment;
    private TextView tvTitle,tvStatusPayment,tvDate,tvPayment,tvAmount,tvPhoneNumber,tvRefrenceNumber,tvPackageTitle;
    private View btnShare,tvBackHome,llResult;
    private View btnSaveResult;
    private String typeTransaction;
    private boolean hasPaymentCharge = false;
    private boolean hasPaymentPackageSimcard = false;
    private int PAYMENT_STATUS = 0;
    private boolean hasPaymentIncreaseWallet = false;
    private boolean hasPaymentTicket = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_result_charge);
        showLoading();

        if (savedInstanceState == null)
        {
     /*       Bundle extras = getIntent().getExtras();
            if (extras == null)
            {

            } else
            {
                refrenceNumber = extras.getString("RefrenceNumber");
                // statusPayment=extras.getBoolean(            "StatusPayment",false);
            }*/
        }
        initView();
        Intent intent = getIntent();

        if (intent.getData()!=null) {
            Uri uri = intent.getData();
            refrenceNumber = uri.getQueryParameter("refrencenumber").replace("/", "");
            typeTransaction = uri.getQueryParameter("typetransaction").replace("/", "");
        }else {
            Bundle extras = getIntent().getExtras();
            if (extras == null)
            {

            } else
            {
                refrenceNumber = extras.getString("RefrenceNumber");
                PAYMENT_STATUS=extras.getInt("PaymentStatus",0);

                // statusPayment=extras.getBoolean(            "StatusPayment",false);
            }
        }

        try
        {
            if (Integer.valueOf(typeTransaction) == TrapConfig.PAYMENT_STAUS_ChargeSimCard)
            {
                hasPaymentCharge = true;
            }
            else if (Integer.valueOf(typeTransaction) == TrapConfig.PAYMENT_STAUS_PackSimCard)
            {
                hasPaymentPackageSimcard = true;
            }
            else if (Integer.valueOf(typeTransaction) == TrapConfig.PAYMENT_STATUS_STADIUM_TICKET)
            {
                hasPaymentTicket = true;
            }
            else if (Integer.valueOf(typeTransaction) == TrapConfig.PAYMENT_STATUS_INCREASE_WALLET)
            {
                hasPaymentIncreaseWallet = true;
            }

        } catch (Exception e)
        {
           // showToast(PaymentResultChargeActivity.this, "شماره پیگیری: " + refrenceNumber, 0);
        }

        if (hasPaymentTicket)
        {


            Intent intent2 = new Intent(this, ShowTicketsFragment.class);
            intent2.putExtra("RefrenceNumber", refrenceNumber);
            startActivity(intent2);
            finish();
            return;


        }

        requestGetDetailTransaction(refrenceNumber);
    }

    private void initView()
    {
        tvTitle=findViewById(R.id.tvTitle);
        tvStatusPayment=findViewById(R.id.tvStatusPayment);
        tvDate=findViewById(R.id.tvDate);
        tvPayment=findViewById(R.id.tvPayment);
        tvAmount=findViewById(R.id.tvAmount);
        tvPhoneNumber=findViewById(R.id.tvPhoneNumber);
        tvRefrenceNumber=findViewById(R.id.tvRefrenceNumber);

        tvPackageTitle=findViewById(R.id.tvPackageTitle);

        btnShare=findViewById(R.id.btnShare);
        btnShare.setOnClickListener(this);
        tvBackHome=findViewById(R.id.tvBackHome);
        tvBackHome.setOnClickListener(this);
        llResult=findViewById(R.id.llResult);
        btnSaveResult=findViewById(R.id.btnSaveResult);
        btnSaveResult.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        Intent resultIntent = new Intent();

        resultIntent.putExtra("PaymentStatus", PAYMENT_STATUS);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    private void requestGetDetailTransaction(String refrenceNumber)
    {

        SingletonService.getInstance().getTransactionDetailService().getTransactionDetail(Long.parseLong(refrenceNumber),new OnServiceStatus<WebServiceClass<TransactionDetailResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<TransactionDetailResponse> response)
            {
                try
                {

                    if (response.info.statusCode == 200)
                    {

                        statusPayment =response.data.getStatus();
                        checkStatusAndSetData(response);
                        hideLoading();


                    }
                } catch (Exception e)
                {
                    showToast(PaymentResultChargeActivity.this, e.getMessage(), R.color.red);
                    hideLoading();
                }
            }

            @Override
            public void onError(String message)
            {
                showToast(PaymentResultChargeActivity.this, message, R.color.red);
                hideLoading();
            }
        });
    }

    private void checkStatusAndSetData(WebServiceClass<TransactionDetailResponse> response)
    {
        if (statusPayment){
            tvStatusPayment.setTextColor(getResources().getColor(R.color.kellyGreen));
            tvStatusPayment.setText("پرداخت موفق");
            //imgPaymentStatus.setImageResource(R.drawable.check_mark);

        }else {
            tvStatusPayment.setTextColor(getResources().getColor(R.color.aviColor));
            tvStatusPayment.setText("پرداخت ناموفق");
            //imgPaymentStatus.setImageResource(R.drawable.un_check_mark);
        }

        tvTitle.setText("رسید "+response.data.getTypeTransaction());
        tvPayment.setText(response.data.getTypePayment());
        tvDate.setText(response.data.getCreate_date_formatted());
        tvAmount.setText("مبلغ: "+Utility.priceFormat(response.data.getAmount().toString())+" ریال" );
        tvPhoneNumber.setText("شماره موبایل: "+response.data.getDetailTransaction().getMobileNumber());
        tvRefrenceNumber.setText("کد پیگیری: "+response.data.getId());
        if (response.data.getDetailTransaction().getTitlePackage()!=null)
        {
            tvPackageTitle.setText(response.data.getDetailTransaction().getTitlePackage());
            tvPackageTitle.setVisibility(View.VISIBLE);
        }else {
            tvPackageTitle.setVisibility(View.GONE);

        }


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

    @Override
    public void onClick(View v)
    {
        switch (v.getId()){
            case R.id.btnShare:
                new ScreenShot(llResult, this, false, "برای ارسال تصویر رسید، اخذ این مجوز الزامی است.");

                break;
            case R.id.btnSaveResult:
                new ScreenShot(llResult, this, true, "برای ذخیره تصویر رسید، اخذ این مجوز الزامی است.");

                // showDialog();
                break;
            case R.id.tvBackHome:
                Intent resultIntent = new Intent();
                resultIntent.putExtra("PaymentStatus", PAYMENT_STATUS);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
                break;
        }
    }


}
