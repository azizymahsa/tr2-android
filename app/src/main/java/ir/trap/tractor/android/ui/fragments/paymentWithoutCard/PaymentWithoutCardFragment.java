package ir.trap.tractor.android.ui.fragments.paymentWithoutCard;

import android.Manifest;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import ir.trap.tractor.android.R;
import ir.trap.tractor.android.apiServices.generator.SingletonService;
import ir.trap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.trap.tractor.android.apiServices.model.WebServiceClass;
import ir.trap.tractor.android.apiServices.model.getDecQrCode.DecryptQrRequest;
import ir.trap.tractor.android.apiServices.model.getDecQrCode.DecryptQrResponse;
import ir.trap.tractor.android.conf.TrapConfig;
import ir.trap.tractor.android.enums.BarcodeType;
import ir.trap.tractor.android.singleton.SingletonContext;
import ir.trap.tractor.android.ui.base.BaseFragment;
import ir.trap.tractor.android.ui.fragments.allMenu.AllMenuFragment;
import ir.trap.tractor.android.ui.fragments.main.MainActionView;

public class PaymentWithoutCardFragment extends BaseFragment implements View.OnClickListener
{

    private Toolbar mToolbar;

    private MainActionView mainView;
    private LinearLayout llQrScan,llPayment,llDetailPayment,llBtnConfirm;
    private CircularProgressButton btnConfirm;
    private EditText etAmountPayment, etQR;
    View rootView;
    private boolean isDetailPaymentList = false, isDetailPaymentBarcode = false;
    public PaymentWithoutCardFragment()
    {
    }
    private void initView()
    {
        llQrScan = rootView.findViewById(R.id.llQrScan);
        llDetailPayment = rootView.findViewById(R.id.llDetailPayment);
        llPayment = rootView.findViewById(R.id.llPayment);
        llBtnConfirm = rootView.findViewById(R.id.llBtnConfirm);
        btnConfirm = rootView.findViewById(R.id.btnConfirm);
        etAmountPayment = rootView.findViewById(R.id.etAmountPayment);
        etQR = rootView.findViewById(R.id.etQR);

        llQrScan.setOnClickListener(this);
    }
    public static PaymentWithoutCardFragment newInstance(MainActionView mainActionView)
    {
        PaymentWithoutCardFragment fragment = new PaymentWithoutCardFragment();
        fragment.setMainView(mainActionView);

        Bundle args = new Bundle();

        fragment.setArguments(args);


        return fragment;
    }

    private void setMainView(MainActionView mainView)
    {
        this.mainView = mainView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {

        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        if (rootView != null)
        {
            if (isDetailPaymentList)
            {
                isDetailPaymentList = false;
                return rootView;

            } else
            {
                if (isDetailPaymentBarcode)
                {
                    isDetailPaymentBarcode = false;
                    return rootView;

                } else
                    rootView = null;

            }

        }
         rootView = inflater.inflate(R.layout.fragment_payment_without_card, container, false);

        mToolbar = rootView.findViewById(R.id.toolbar);
        initView();

        mToolbar.findViewById(R.id.imgMenu).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mainView.openDrawer();
            }
        });
if( Prefs.getString("qrCode","").length()>5){
    DecryptQrRequest request = new DecryptQrRequest();
    request.setDecryptQr(Prefs.getString("qrCode",""));
    SingletonService.getInstance().getMerchantService().GetdecryptQrService(new OnServiceStatus<WebServiceClass<DecryptQrResponse>>()
    {
        @Override
        public void onReady(WebServiceClass<DecryptQrResponse> decryptQrResponseWebServiceClass)
        {

        }

        @Override
        public void onError(String message)
        {

        }
    }, request);
}



        return rootView;
    }



    public void openBarcode(BarcodeType bill) {
        new TedPermission(getContext())
                .setPermissionListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        onBarcodReader(bill);
                    }

                    @Override
                    public void onPermissionDenied(ArrayList<String> deniedPermissions) {

                    }
                })
                .setDeniedMessage("If you reject permission,you can not use this application, Please turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.CAMERA)
                .check();

    }
    public void onBarcodReader(BarcodeType barcodeType) {
       // changeFragment(fragments.get(13), "13");
        Prefs.putString("qrCode","");
        mainView.onBarcodeReader();
        //    mFragNavController.switchTab(13);
       /* new Handler().postDelayed(() -> {
            layoutBehavior();
            appBar.setExpanded(false, true);

        }, 200);
        presenter.barcodeType(barcodeType);*/
        Log.d("barcode:", Prefs.getString("qrCode",""));
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.llQrScan:
               openBarcode(BarcodeType.Payment);
               // isDetailPaymentBarcode = true;


                break;
            case R.id.btnConfirm:
                // mainView.message("بزودی ...");

                if (TextUtils.isEmpty(etQR.getText().toString()))
                {
                    mainView.showError("لطفا کد را وارد نمایید.");
                    return;
                }
                if (TextUtils.isEmpty(etAmountPayment.getText().toString()))
                {
                   // mainView.onError("لطفا مبلغ را وارد نمایید.", this.getClass().getSimpleName(), DibaConfig.showClassNameInMessage);
                    mainView.showError("لطفا مبلغ را وارد نمایید.");
                    return;
                }

                /*if (continue_)
                {
                    llDetailPayment.setVisibility(View.GONE);
                    llPricePeyment.setVisibility(View.VISIBLE);
                    YoYo.with(Techniques.SlideInRight)
                            .duration(200)
                            .playOn(llPricePeyment);
                    llPayment.setVisibility(View.GONE);
                } else
                    getMerchantByCode(etQR.getText().toString());
*/




                break;
        }
    }
    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

    @Override
    public void onStop()
    {
        super.onStop();
        if (isDetailPaymentList)
            isDetailPaymentList = false;

    }

    @Override
    public void onResume()
    {
        super.onResume();
       // continue_ = false;
      //  etPassPayment.setText("");
        etQR.setText("");
       // btnConfirm.revertAnimation(PaymentWithoutCardFragment.this);
        btnConfirm.setClickable(true);
      //  etCvv2.setText("");
        etAmountPayment.setText("");




    }


}
