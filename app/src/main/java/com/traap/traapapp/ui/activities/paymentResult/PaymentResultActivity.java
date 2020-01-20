package com.traap.traapapp.ui.activities.paymentResult;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.getTransaction.TransactionDetailResponse;
import com.traap.traapapp.ui.base.BaseActivity;
import com.traap.traapapp.utilities.Tools;

public class PaymentResultActivity extends BaseActivity implements View.OnClickListener
{

    private TextView txRefrenceNumber;
    private ImageView imgPaymentStatus;
    private TextView txDescriptionPayment;
    private View btnBackToHome;
    private String refrenceNumber;
    private boolean statusPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_result);
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
                        checkStatusAndSetData();


                    }
                } catch (Exception e)
                {
                    showToast(PaymentResultActivity.this, e.getMessage(), R.color.red);
                    hideLoading();
                }
            }

            @Override
            public void onError(String message)
            {
                showToast(PaymentResultActivity.this, message, R.color.red);
                hideLoading();
            }
        });
    }

    private void initView()
    {
        imgPaymentStatus=findViewById(R.id.imgPaymentStatus);
        txDescriptionPayment=findViewById(R.id.txDescriptionPayment);
        txRefrenceNumber=findViewById(R.id.txRefrenceNumber);
        btnBackToHome=findViewById(R.id.btnBackToHome);
        txRefrenceNumber.setText(" کد پیگیری : "+refrenceNumber);
        btnBackToHome.setOnClickListener(this);


    }

    private void checkStatusAndSetData()
    {
        if (statusPayment){
            txDescriptionPayment.setTextColor(getResources().getColor(R.color.kellyGreen));
            txDescriptionPayment.setText("پرداخت شما با موفقیت انجام شده است.");
            imgPaymentStatus.setImageResource(R.drawable.check_mark);

        }else {
            txDescriptionPayment.setTextColor(getResources().getColor(R.color.aviColor));
            txDescriptionPayment.setText("پرداخت شما ناموفق بوده است.");
            imgPaymentStatus.setImageResource(R.drawable.un_check_mark);


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
            case R.id.btnBackToHome:
                finish();
                break;
        }
    }
}
