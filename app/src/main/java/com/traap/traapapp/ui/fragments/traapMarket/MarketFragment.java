package com.traap.traapapp.ui.fragments.traapMarket;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.traap.traapapp.R;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.dialogs.MessageAlertDialog;
import com.traap.traapapp.ui.fragments.main.MainActionView;


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
        rootView = inflater.inflate(R.layout.fragment_traap_market, container, false);

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
                MessageAlertDialog.TYPE_MESSAGE, new MessageAlertDialog.OnConfirmListener()
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
