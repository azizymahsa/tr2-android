package ir.trap.tractor.android.ui.fragments.paymentWithoutCard;

import android.Manifest;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;

import ir.trap.tractor.android.R;
import ir.trap.tractor.android.enums.BarcodeType;
import ir.trap.tractor.android.ui.base.BaseFragment;
import ir.trap.tractor.android.ui.fragments.main.MainActionView;

public class PaymentWithoutCardFragment extends BaseFragment implements View.OnClickListener
{

    private Toolbar mToolbar;

    private MainActionView mainView;
    private LinearLayout llQrScan;
    View rootView;
    public PaymentWithoutCardFragment()
    {
    }

    public static PaymentWithoutCardFragment newInstance(MainActionView mainActionView)
    {
        PaymentWithoutCardFragment fragment = new PaymentWithoutCardFragment();
        fragment.setMainView(mainActionView);

        Bundle args = new Bundle();
//        args.putParcelableArrayList("chosenServiceList", chosenServiceList);
//        args.putParcelableArrayList("footballServiceList", footballServiceList);

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




        return rootView;
    }

    private void initView()
    {
        llQrScan = rootView.findViewById(R.id.llQrScan);
        llQrScan.setOnClickListener(this);
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

        //    mFragNavController.switchTab(13);
      /*  new Handler().postDelayed(() -> {
            layoutBehavior();
            appBar.setExpanded(false, true);

        }, 200);
        presenter.barcodeType(barcodeType);*/
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
        }
    }
}
