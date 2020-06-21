package com.traap.traapapp.ui.fragments.barcodeReader;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.traap.traapapp.R;
import com.traap.traapapp.models.otherModels.qrModel.QrModel;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.utilities.Tools;

import java.util.regex.Pattern;

public class QrCodeReader extends BaseFragment
{
    private View rootView;

    private MainActionView mainView;

    private CodeScanner codeScanner;
    private Activity activity;

    public static QrCodeReader newInstance(MainActionView mainView)
    {
        QrCodeReader f = new QrCodeReader();
        f.setMainView(mainView);

        return f;
    }

    private void setMainView(MainActionView mainView)
    {
        this.mainView = mainView;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_qr_code_reader, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        CodeScannerView scannerView  = view.findViewById(R.id.scanner_view);
        activity = requireActivity();

        codeScanner = new CodeScanner(activity, scannerView);

        codeScanner.setDecodeCallback(result ->
        {
            sendData(result.getText());
            activity.runOnUiThread(() -> Tools.showToast(activity, result.getText() + " was added!"));
        });

        codeScanner.startPreview();
    }

    private void sendData(String text)
    {
        if (isValidation(text))
        {
            QrModel model = new QrModel(text.split("\\|")[0], Integer.parseInt(text.split("\\|")[1]));
//            EventBus.getDefault().post(model);
            activity.runOnUiThread(() -> mainView.onShowPaymentWithoutCardFragment(model));
        }
        else
        {
            Tools.showToast(activity, "کد نامعتبر!");
        }
    }

    private boolean isValidation(String text)
    {
        Pattern pattern = Pattern.compile(
//                "[a-zA-Z0-9]{1,256}" + "\\|" + "[0-9]{1,13}"
                "[0-9]{15}" + "\\|" + "[0-9]{3,13}"
        );

        if (pattern.matcher(text).matches())
        {
            return true;
        }

        return false;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        codeScanner.startPreview();
    }

    @Override
    public void onPause()
    {
        codeScanner.releaseResources();
        super.onPause();
    }
}
