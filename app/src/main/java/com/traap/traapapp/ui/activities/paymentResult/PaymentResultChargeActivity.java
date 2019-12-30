package com.traap.traapapp.ui.activities.paymentResult;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.getTransaction.TransactionDetailResponse;
import com.traap.traapapp.ui.base.BaseActivity;
import com.traap.traapapp.utilities.ScreenShot;
import com.traap.traapapp.utilities.Tools;

public class PaymentResultChargeActivity extends BaseActivity implements View.OnClickListener
{

    private String refrenceNumber;
    private boolean statusPayment;
    private TextView tvTitle,tvStatusPayment,tvDate,tvPayment,tvAmount,tvPhoneNumber,tvRefrenceNumber;
    private View btnShareTicket,tvBackHome,llResult;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_result_charge);

        if (savedInstanceState == null)
        {
            Bundle extras = getIntent().getExtras();
            if (extras == null)
            {

            } else
            {
                refrenceNumber = extras.getString("RefrenceNumber");
                // statusPayment=extras.getBoolean(            "StatusPayment",false);
            }
        }
        initView();
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

        btnShareTicket=findViewById(R.id.btnShareTicket);
        btnShareTicket.setOnClickListener(this);
        tvBackHome=findViewById(R.id.tvBackHome);
        tvBackHome.setOnClickListener(this);
        llResult=findViewById(R.id.llResult);
    }

    private void requestGetDetailTransaction(String refrenceNumber)
    {
        showLoading();

        SingletonService.getInstance().getTransactionDetailService().getTransactionDetail(Long.parseLong(refrenceNumber),new OnServiceStatus<WebServiceClass<TransactionDetailResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<TransactionDetailResponse> response)
            {
                try
                {
                    hideLoading();

                    if (response.info.statusCode == 200)
                    {

                        statusPayment =response.data.getStatus();
                        checkStatusAndSetData(response);


                    }
                } catch (Exception e)
                {
                    Tools.showToast(getApplicationContext(), e.getMessage(), R.color.red);
                    hideLoading();
                }
            }

            @Override
            public void onError(String message)
            {
                Tools.showToast(getApplicationContext(), message, R.color.red);
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

        //tvTitle.setText(response.data.);
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
            case R.id.btnShareTicket:
                new ScreenShot(llResult, this);
                break;
            case R.id.tvBackHome:
                finish();
                break;
        }
    }
}
