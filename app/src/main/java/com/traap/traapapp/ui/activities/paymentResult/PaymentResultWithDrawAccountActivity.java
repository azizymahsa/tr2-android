package com.traap.traapapp.ui.activities.paymentResult;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.TextView;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.getTransaction.TransactionDetailResponse;
import com.traap.traapapp.apiServices.model.withdrawWallet.WithdrawWalletResponse;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.ui.base.BaseActivity;
import com.traap.traapapp.ui.dialogs.MessageAlertDialog;
import com.traap.traapapp.utilities.ScreenShot;
import com.traap.traapapp.utilities.Tools;
import com.traap.traapapp.utilities.Utility;

public class PaymentResultWithDrawAccountActivity extends BaseActivity implements View.OnClickListener
{

    private String refrenceNumber;
    private boolean statusPayment;
    private TextView tvTitle,tvStatusPayment,tvDate,tvFrom,tvAmount,tvSheba,tvRefrenceNumber;
    private View btnShare,tvBackHome,llResult;
    private View btnSaveResult;
    private WithdrawWalletResponse response;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_result_with_draw_account);
        showLoading();

        if (savedInstanceState == null)
        {
            Bundle extras = getIntent().getExtras();
            if (extras == null)
            {

            } else
            {
                response = extras.getParcelable("response");
                // statusPayment=extras.getBoolean(            "StatusPayment",false);
            }
        }
        initView();

        checkStatusAndSetData(response);

    }

    private void initView()
    {
        tvTitle=findViewById(R.id.tvTitle);
        tvStatusPayment=findViewById(R.id.tvStatusPayment);
        tvDate=findViewById(R.id.tvDate);
        tvFrom=findViewById(R.id.tvFrom);
        tvAmount=findViewById(R.id.tvAmount);
        tvSheba=findViewById(R.id.tvSheba);
        tvRefrenceNumber=findViewById(R.id.tvRefrenceNumber);

        btnShare=findViewById(R.id.btnShare);
        btnShare.setOnClickListener(this);
        tvBackHome=findViewById(R.id.tvBackHome);
        tvBackHome.setOnClickListener(this);
        llResult=findViewById(R.id.llResult);
        btnSaveResult=findViewById(R.id.btnSaveResult);
        btnSaveResult.setOnClickListener(this);
    }


    private void checkStatusAndSetData(WithdrawWalletResponse response)
    {
      /*  if (statusPayment){
            tvStatusPayment.setTextColor(getResources().getColor(R.color.kellyGreen));
            tvStatusPayment.setText("پرداخت موفق");
            //imgPaymentStatus.setImageResource(R.drawable.check_mark);

        }else {
            tvStatusPayment.setTextColor(getResources().getColor(R.color.aviColor));
            tvStatusPayment.setText("پرداخت ناموفق");
            //imgPaymentStatus.setImageResource(R.drawable.un_check_mark);
        }*/

        tvStatusPayment.setTextColor(getResources().getColor(R.color.kellyGreen));
        tvStatusPayment.setText("پرداخت موفق");
        tvTitle.setText("رسید برداشت از کیف پول");
        tvFrom.setText("کارت مبدا: "+response.getFrom());
        tvDate.setText(response.getDateTime());
        tvAmount.setText("مبلغ: "+ Utility.priceFormat(response.getAmount().toString())+" ریال" );
        tvSheba.setText("شماره شبا: " + response.getShebaNumber());
        tvRefrenceNumber.setText("کد پیگیری: "+response.getRefNumber());

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

    @Override
    public void onClick(View v)
    {
        switch (v.getId()){
            case R.id.btnShare:
                new ScreenShot(llResult, this,false,() ->
                {

                });
                break;
            case R.id.btnSaveResult:
                new ScreenShot(llResult, this,true,() ->
                {

                });
                //showDialog();
                break;
            case R.id.tvBackHome:
                finish();
                break;
        }
    }


}
