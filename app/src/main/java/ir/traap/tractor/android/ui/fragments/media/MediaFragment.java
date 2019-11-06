package ir.traap.tractor.android.ui.fragments.media;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.pixplicity.easyprefs.library.Prefs;

import ir.traap.tractor.android.R;
import ir.traap.tractor.android.apiServices.generator.SingletonService;
import ir.traap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.traap.tractor.android.apiServices.model.WebServiceClass;
import ir.traap.tractor.android.apiServices.model.getDecQrCode.DecryptQrRequest;
import ir.traap.tractor.android.apiServices.model.getDecQrCode.DecryptQrResponse;
import ir.traap.tractor.android.apiServices.model.paymentPrintPos.PaymentPrintPosRequest;
import ir.traap.tractor.android.apiServices.model.paymentPrintPos.PaymentPrintPosResponse;
import ir.traap.tractor.android.enums.BarcodeType;
import ir.traap.tractor.android.singleton.SingletonContext;
import ir.traap.tractor.android.ui.base.BaseFragment;
import ir.traap.tractor.android.ui.dialogs.MessageAlertDialog;
import ir.traap.tractor.android.ui.dialogs.PaymentResultDialog;
import ir.traap.tractor.android.ui.fragments.main.MainActionView;
import ir.traap.tractor.android.utilities.Logger;
import ir.traap.tractor.android.utilities.NumberTextWatcher;
import ir.traap.tractor.android.utilities.Utility;


@SuppressLint("ValidFragment")
public class MediaFragment extends BaseFragment
{
    private View rootView;
    private MainActionView mainView;

    private Toolbar mToolbar;


    public MediaFragment()
    {

    }

    public static MediaFragment newInstance(MainActionView mainView)
    {
        MediaFragment f = new MediaFragment();
        f.setMainView(mainView);
        return f;
    }

    private void setMainView(MainActionView mainView)
    {
        this.mainView = mainView;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_media, container, false);

        mToolbar = rootView.findViewById(R.id.toolbar);

        mToolbar.findViewById(R.id.imgMenu).setOnClickListener(v -> mainView.openDrawer());
//        mToolbar.findViewById(R.id.imgBack).setOnClickListener(rootView -> mainView.backToMainFragment());
        TextView tvUserName = mToolbar.findViewById(R.id.tvUserName);
//        TextView tvTitle = mToolbar.findViewById(R.id.tvTitle);
//        tvTitle.setText("رسانه");
        tvUserName.setText(Prefs.getString("mobile", ""));

        initView();

        return rootView;
    }


    public void initView()
    {
        MessageAlertDialog dialog = new MessageAlertDialog(getActivity(), "", "این سرویس بزودی راه اندازی میگردد", false,
                new MessageAlertDialog.OnConfirmListener()
                {
                    @Override
                    public void onConfirmClick()
                    {
                        mainView.backToMainFragment();
                    }

                    @Override
                    public void onCancelClick()
                    {

                    }
                });
        dialog.setCancelable(false);
        dialog.show(getActivity().getFragmentManager(), "messageDialog");


    }

}
