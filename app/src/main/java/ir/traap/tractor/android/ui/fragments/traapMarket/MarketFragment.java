package ir.traap.tractor.android.ui.fragments.traapMarket;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import ir.traap.tractor.android.R;
import ir.traap.tractor.android.conf.TrapConfig;
import ir.traap.tractor.android.ui.base.BaseFragment;
import ir.traap.tractor.android.ui.dialogs.MessageAlertDialog;
import ir.traap.tractor.android.ui.fragments.main.MainActionView;
import ir.traap.tractor.android.ui.dialogs.paymentGateway.PaymentGateWayDialog;


@SuppressLint("ValidFragment")
public class MarketFragment extends BaseFragment
{
    private View rootView;
    private MainActionView mainView;

    private Toolbar mToolbar;


    public MarketFragment()
    {

    }

    public static MarketFragment newInstance(MainActionView mainView)
    {
        MarketFragment f = new MarketFragment();
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
        mToolbar.findViewById(R.id.imgBack).setOnClickListener(rootView -> mainView.backToMainFragment());
        TextView tvUserName = mToolbar.findViewById(R.id.tvUserName);
        TextView tvTitle = mToolbar.findViewById(R.id.tvTitle);
        tvTitle.setText("تراپ مارکت");
        tvUserName.setText(TrapConfig.HEADER_USER_NAME);

        initView();

        return rootView;
    }


    public void initView()
    {
        MessageAlertDialog dialog = new MessageAlertDialog(getActivity(), "", "این سرویس بزودی راه اندازی میگردد.", false,
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
