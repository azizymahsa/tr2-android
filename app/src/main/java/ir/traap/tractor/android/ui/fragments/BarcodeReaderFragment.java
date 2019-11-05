package ir.traap.tractor.android.ui.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.zxing.Result;
import com.pixplicity.easyprefs.library.Prefs;


import ir.traap.tractor.android.R;
import ir.traap.tractor.android.enums.BarcodeType;
import ir.traap.tractor.android.ui.base.BaseFragment;
import ir.traap.tractor.android.ui.fragments.main.MainActionView;
import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class BarcodeReaderFragment extends BaseFragment implements ZXingScannerView.ResultHandler {

    private View v;
    private ZXingScannerView mScannerView;
    private TextView tvTitle;
    private BarcodeType barcodeType;

    private MainActionView mainView;


    public BarcodeReaderFragment() {

    }
    public static BarcodeReaderFragment newInstance(MainActionView mainView) {
        BarcodeReaderFragment f = new BarcodeReaderFragment();
        f.setMainView(mainView);


        return f;
    }

    private void setMainView(MainActionView mainView) {
        this.mainView=mainView;
    }


    @Override
    public void handleResult(Result result) {
        //presenter.barcode(result.getText());
        Prefs.putString("qrCode",result.getText());
        mainView.onPaymentWithoutCard();
    }

    public void setBarcodeType(BarcodeType barcodeType) {
        this.barcodeType = barcodeType;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (v != null) {
            v = null;

        }


        v = inflater.inflate(R.layout.activity_barcode_reader, container, false);
        ViewGroup contentFrame = (ViewGroup) v.findViewById(R.id.content_frame);
       // tvTitle = getActivity().findViewById(R.id.tvTitle);
       // tvTitle.setText("بار کد خوان");

        mScannerView = new ZXingScannerView(getActivity());
        contentFrame.addView(mScannerView);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        new Handler().postDelayed(() -> {
            mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
            mScannerView.startCamera();          // Start camera on resume

        }, 200);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        v = null;
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
        // Stop camera on pause
    }

}
