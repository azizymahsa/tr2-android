package com.traap.traapapp.ui.activities.paymentResult;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.getTransaction.TransactionDetailResponse;
import com.traap.traapapp.ui.base.BaseActivity;
import com.traap.traapapp.ui.dialogs.MessageAlertDialog;
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

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_result_charge);
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
                new ScreenShot(llResult, this);
                break;
            case R.id.btnSaveResult:
                new ScreenShot(llResult, this,true);
                showDialog();
                break;
            case R.id.tvBackHome:
                finish();
                break;
        }
    }

    private void showDialog()
    {
        MessageAlertDialog dialog = new MessageAlertDialog(this, "", "رسید شما با موفقیت در گالری ذخیره شد.", false,
                MessageAlertDialog.TYPE_SUCCESS, new MessageAlertDialog.OnConfirmListener()
                {
                    @Override
                    public void onConfirmClick()
                    {

                    }

                    @Override
                    public void onCancelClick()
                    {

                    }
                });

        dialog.setCancelable(false);
        dialog.show(getFragmentManager(), "messageDialog");
    }
}
