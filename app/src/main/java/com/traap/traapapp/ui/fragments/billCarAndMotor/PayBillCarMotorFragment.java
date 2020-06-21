package com.traap.traapapp.ui.fragments.billCarAndMotor;

import android.Manifest;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.pixplicity.easyprefs.library.Prefs;
import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.allService.response.SubMenu;
import com.traap.traapapp.apiServices.model.billCar.RequestBillCar;
import com.traap.traapapp.apiServices.model.billCar.ResponseBillCar;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.enums.BarcodeType;
import com.traap.traapapp.enums.MatchScheduleParent;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.ui.adapters.billCarAndMotor.AllBillCarAdapter;
import com.traap.traapapp.ui.adapters.compations.CompationsDeActiveAdapter;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.utilities.Tools;
import com.traap.traapapp.utilities.Utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import br.com.simplepass.loading_button_lib.interfaces.OnAnimationEndListener;

public class PayBillCarMotorFragment extends BaseFragment implements View.OnClickListener, OnAnimationEndListener,AllBillCarAdapter.OnItemAllBillClickListener

{

    private Toolbar mToolbar;

    private MainActionView mainView;
    private CircularProgressButton btnConfirm;
    private EditText etQR;
    TextInputLayout etLayoutCode;
    View rootView;
    private boolean isDetailPaymentList = false, isDetailPaymentBarcode = false;
    private boolean continue_ = false;
    private TextView tvUserName, txtSumBill, txtSizeBill, txtZero, txtOne, txttwe, txtthree;
    private ImageView ivShowBalance;
    private RecyclerView recyclerView;
    private LinearLayout llGetBill, llNumCar;
    private AllBillCarAdapter mAdapter;
AllBillCarAdapter.OnItemAllBillClickListener mItemClickListener;
    private Integer billType=9;

    public PayBillCarMotorFragment()
    {
    }

    private void initView()
    {

        tvUserName = mToolbar.findViewById(R.id.tvUserName);

        tvUserName.setText(TrapConfig.HEADER_USER_NAME);

        ivShowBalance = rootView.findViewById(R.id.ivShowBalance);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        btnConfirm = rootView.findViewById(R.id.btnConfirm);
        etQR = rootView.findViewById(R.id.etQR);
        etLayoutCode = rootView.findViewById(R.id.etLayoutCode);
        llGetBill = rootView.findViewById(R.id.llGetBill);
        llGetBill.setVisibility(View.VISIBLE);
        llNumCar = rootView.findViewById(R.id.llNumCar);
        llNumCar.setVisibility(View.GONE);

        txtSizeBill = rootView.findViewById(R.id.txtSizeBill);
        txtSumBill = rootView.findViewById(R.id.txtSumBill);

        txtZero = rootView.findViewById(R.id.txtZero);
        txtOne = rootView.findViewById(R.id.txtOne);
        txttwe = rootView.findViewById(R.id.txttwe);
        txtthree = rootView.findViewById(R.id.txtthree);


        ivShowBalance.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);
        btnConfirm.setText("تایید");


    }

    public static PayBillCarMotorFragment newInstance(MainActionView mainActionView,Integer billType)
    {
        PayBillCarMotorFragment fragment = new PayBillCarMotorFragment();
        fragment.setMainView(mainActionView,billType);

        Bundle args = new Bundle();

        fragment.setArguments(args);


        return fragment;
    }

    private void setMainView(MainActionView mainView,Integer billType)
    {
        this.mainView = mainView;
        this.billType = billType;
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
        rootView = inflater.inflate(R.layout.fragment_bill_car_motor, container, false);

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
        if (Prefs.getString("qrCode", "").length() > 5 || etQR.getText().toString().length() > 5)
        {
          //  sendRequest();
        }

        return rootView;
    }

    private void sendRequest()
    {
        RequestBillCar request = new RequestBillCar();
        request.setBillCode(etQR.getText().toString());//Prefs.getString("qrCode", ""));
        request.setType(billType+"");
        SingletonService.getInstance().getAllBillsService().getAllBillCar(new OnServiceStatus<WebServiceClass<ResponseBillCar>>()
        {
            @Override
            public void onReady(WebServiceClass<ResponseBillCar> decryptQrResponse)
            {


                try
                {
                    new Handler().postDelayed(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            btnConfirm.revertAnimation(PayBillCarMotorFragment.this);
                            btnConfirm.setClickable(true);
                        }
                    }, 200);

                    if (decryptQrResponse.info.statusCode == 200)
                    {

                        btnConfirm.setText("تایید");
                        llGetBill.setVisibility(View.GONE);
                        llNumCar.setVisibility(View.VISIBLE);
                        txtSizeBill.setText(decryptQrResponse.data.getDetails().size()+"");
                        txtSumBill.setText(Utility.priceFormat(String.valueOf(decryptQrResponse.data.getTotalAmount())) + " ریال");

                        txtZero.setText(decryptQrResponse.data.getPlate().get(0));
                        txtOne.setText(decryptQrResponse.data.getPlate().get(1));
                        txttwe.setText(decryptQrResponse.data.getPlate().get(2));
                        txtthree.setText(decryptQrResponse.data.getPlate().get(3));

                       // mAdapter = new AllBillCarAdapter(getActivity(),decryptQrResponse.data.getDetails(),this);
                        mAdapter = new AllBillCarAdapter(getActivity(),decryptQrResponse.data.getDetails());
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        recyclerView.setAdapter(mAdapter);
                    } else
                    {
                        //   backToHome();
                        mainView.showError(decryptQrResponse.info.message);

                    }
                } catch (Exception e)
                {
                    //  mainView.showError(e.getMessage());
                  /*  llPayment.setVisibility(View.VISIBLE);
                    llDetailPayment.setVisibility(View.GONE);*/
                    //   backToHome();

                }


            }

            @Override
            public void onError(String message)
            {
//...
/*                llPayment.setVisibility(View.VISIBLE);
                llDetailPayment.setVisibility(View.GONE);*/
                //  backToHome();


                new Handler().postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        btnConfirm.revertAnimation(PayBillCarMotorFragment.this);
                        btnConfirm.setClickable(true);
                    }
                }, 200);
                if (Tools.isNetworkAvailable(Objects.requireNonNull(getActivity())))
                {
                    mainView.showError(message);
                } else
                {

                    showAlert(getActivity(), R.string.networkErrorMessage, R.string.networkError);
                }


            }
        }, request);


    }

    public void backToHome()
    {
        continue_ = false;
        etQR.setEnabled(true);


        btnConfirm.revertAnimation(PayBillCarMotorFragment.this);
        btnConfirm.setClickable(true);

        etQR.setText("");
        etLayoutCode.setHint(getString(R.string._txt_cod_car));

    }


    public void openBarcode(BarcodeType bill)
    {
        try
        {
            new TedPermission(getContext())
                    .setPermissionListener(new PermissionListener()
                    {
                        @Override
                        public void onPermissionGranted()
                        {
                            onBarcodReader();
                        }

                        @Override
                        public void onPermissionDenied(ArrayList<String> deniedPermissions)
                        {

                        }
                    })
                    .setDeniedMessage("If you reject permission,you can not use this application, Please turn on permissions at [Setting] > [Permission]")
                    .setPermissions(Manifest.permission.CAMERA)
                    .check();

        } catch (Exception e)
        {
            e.getMessage();
        }


    }

    public void onBarcodReader()
    {
        Prefs.putString("qrCode", "");
        mainView.onBarcodeReader();

        Log.d("barcode:", Prefs.getString("qrCode", ""));
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.btnPaymentConfirm:


                break;
            case R.id.ivShowBalance:
                openBarcode(BarcodeType.Payment);
                // isDetailPaymentBarcode = true;


                break;

            case R.id.btnConfirm:

                if (TextUtils.isEmpty(etQR.getText().toString()))
                {
                    mainView.showError("لطفا کد را وارد نمایید.");
                    return;
                } else
                {
                    sendRequest();
                }


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
        continue_ = false;
        etQR.setText("");
        btnConfirm.revertAnimation(PayBillCarMotorFragment.this);
        btnConfirm.setClickable(true);


    }


    @Override
    public void onAnimationEnd()
    {
        //  btnPaymentConfirm.setBackground(ContextCompat.getDrawable(SingletonContext.getInstance().getContext(), R.drawable.background_border_red));
        btnConfirm.setBackground(ContextCompat.getDrawable(SingletonContext.getInstance().getContext(), R.drawable.background_border_red));
    }

    @Override
    public void OnItemAllBillClick(View view, String id,String amount)
    {

    }
}
