package com.traap.traapapp.ui.activities.paymentResult;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.getTransaction.TransactionDetailResponse;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.ui.base.BaseActivity;
import com.traap.traapapp.ui.dialogs.MessageAlertDialog;
import com.traap.traapapp.ui.dialogs.MessageAlertPermissionDialog;
import com.traap.traapapp.utilities.ScreenShot;
import com.traap.traapapp.utilities.Tools;
import com.traap.traapapp.utilities.Utility;

public class PaymentResultIncreaseInventoryActivity extends BaseActivity implements View.OnClickListener, ScreenShot.DenyAction
{

    private String refrenceNumber;
    private boolean statusPayment;
    private TextView tvTitle,tvStatusPayment,tvDate,tvPayment,tvAmount,tvCardNumDestination,tvRefrenceNumber,tvPackageTitle,tvPhoneNumber;
    private View btnShare,tvBackHome,llResult;
    private View btnSaveResult;
    private     MessageAlertPermissionDialog dialog;
    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        //No call for super(). Bug on API Level > 11.
        super.onSaveInstanceState(outState);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_result_increase_inventory);
        showLoading();

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
        tvCardNumDestination=findViewById(R.id.tvCardNumDestination);
        tvRefrenceNumber=findViewById(R.id.tvRefrenceNumber);

        tvPhoneNumber=findViewById(R.id.tvPhoneNumber);
        tvPackageTitle=findViewById(R.id.tvPackageTitle);

        btnShare=findViewById(R.id.btnShare);
        btnShare.setOnClickListener(this);
        tvBackHome=findViewById(R.id.tvBackHome);
        tvBackHome.setOnClickListener(this);
        llResult=findViewById(R.id.llResult);
        btnSaveResult=findViewById(R.id.btnSaveResult);
        btnSaveResult.setOnClickListener(this);
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
                    showToast(PaymentResultIncreaseInventoryActivity.this, e.getMessage(), R.color.red);
                    hideLoading();
                }
            }

            @Override
            public void onError(String message)
            {
                hideLoading();


                if (Tools.isNetworkAvailable(PaymentResultIncreaseInventoryActivity.this))
                {
                    showToast(PaymentResultIncreaseInventoryActivity.this, message, R.color.red);


                } else
                {
                    showToast(PaymentResultIncreaseInventoryActivity.this, getString(R.string.networkErrorMessage), R.color.red);



                }

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
        tvAmount.setText("مبلغ: "+ Utility.priceFormat(response.data.getAmount().toString())+" ریال" );




        if (response.data.getTypeTransactionId() == TrapConfig.PAYMENT_STATUS_TRANSFER_MONEY_WALLET)
        {
            tvCardNumDestination.setText("مقصد: " + response.data.getDetailTransaction().getDestinationCard());
            tvPhoneNumber.setText( "شماره تلفن همراه واریز کننده: " + response.data.getMobile());

        }else if (response.data.getTypeTransactionId() == TrapConfig.PAYMENT_STATUS_INCREASE_WALLET){
            tvPhoneNumber.setText( "شماره تلفن همراه: " + response.data.getMobile());
            tvCardNumDestination.setVisibility(View.GONE);
        }

        tvRefrenceNumber.setText("کد پیگیری: "+response.data.getId());
       /* if (response.data.getDetailTransaction().getTitlePackage()!=null)
        {
            tvPackageTitle.setText(response.data.getDetailTransaction().getTitlePackage());
            tvPackageTitle.setVisibility(View.VISIBLE);
        }else {
            tvPackageTitle.setVisibility(View.GONE);

        }*/


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
                new ScreenShot(llResult, this, false, this);
             ;
                break;
            case R.id.btnSaveResult:

                new ScreenShot(llResult, this, false, () -> {

                    MessageAlertPermissionDialog    dialog = new MessageAlertPermissionDialog(PaymentResultIncreaseInventoryActivity.this, "",
                            "برای ذخیره تصویر رسید، اخذ این مجوز الزامی است.",
                            true, "نمایش دوباره دسترسی", "انصراف", MessageAlertDialog.TYPE_MESSAGE, new MessageAlertDialog.OnConfirmListener()
                    {
                        @Override
                        public void onConfirmClick()
                        {
                            new ScreenShot(llResult, PaymentResultIncreaseInventoryActivity.this, false, PaymentResultIncreaseInventoryActivity.this);

                          //  dialog.dismiss();

                        }

                        @Override
                        public void onCancelClick()
                        {

                        }
                    }
                    );


                    dialog.show(getFragmentManager(), "dialogMessage");

                });

                break;
            case R.id.tvBackHome:
                finish();
                break;
        }
    }

    @Override
    public void onDeny()
    {



    }
}
