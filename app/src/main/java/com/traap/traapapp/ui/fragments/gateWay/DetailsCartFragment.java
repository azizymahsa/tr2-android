package com.traap.traapapp.ui.fragments.gateWay;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.pixplicity.easyprefs.library.Prefs;
import com.traap.traapapp.R;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.models.otherModels.headerModel.HeaderModel;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.ui.activities.myProfile.MyProfileActivity;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.ui.fragments.videos.VideosFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by MahsaAzizi on 25/12/2019.
 */
public class DetailsCartFragment extends BaseFragment implements View.OnClickListener
{


    private View rootView;
    private MainActionView mainView;
    private Fragment fragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private LinearLayout lnrPayment, lnrGetReport, lnrManageWallet, lnrIncreaseInveronment, lnrDoTransfer, lnrGrid;

    public DetailsCartFragment()
    {

    }

    public static DetailsCartFragment newInstance(MainActionView mainView)
    {
        DetailsCartFragment f = new DetailsCartFragment();
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
        rootView = inflater.inflate(R.layout.fragment_details_cart, container, false);


        initView();


        return rootView;
    }

    private void initView()
    {

        lnrPayment = rootView.findViewById(R.id.lnrPayment);
        lnrGetReport = rootView.findViewById(R.id.lnrGetReport);
        lnrManageWallet = rootView.findViewById(R.id.lnrManageWallet);
        lnrIncreaseInveronment = rootView.findViewById(R.id.lnrIncreaseInveronment);
        lnrDoTransfer = rootView.findViewById(R.id.lnrDoTransfer);
        lnrGrid = rootView.findViewById(R.id.lnrGrid);

        lnrPayment.setOnClickListener(this);
        lnrGetReport.setOnClickListener(this);
        lnrManageWallet.setOnClickListener(this);
        lnrIncreaseInveronment.setOnClickListener(this);
        lnrDoTransfer.setOnClickListener(this);
        lnrGrid.setOnClickListener(this);

    }

    @Subscribe
    public void getHeaderContent(HeaderModel headerModel)
    {

    }


    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

    void showFragment(Fragment fragment)
    {
        lnrGrid.setVisibility(View.GONE);
        fragmentManager = getChildFragmentManager();


        transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.container, fragment, "WalletFragment").commit();
    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.lnrPayment:
                fragment = DetailsCartFragment.newInstance(mainView);
                showFragment(fragment);
                break;
            case R.id.lnrGetReport:
                fragment = DetailsCartFragment.newInstance(mainView);
                showFragment(fragment);
                break;
            case R.id.lnrManageWallet:
                fragment = DetailsCartFragment.newInstance(mainView);
                showFragment(fragment);
                break;
            case R.id.lnrIncreaseInveronment:
                fragment = IncreaseInventoryFragment.newInstance(mainView);
                showFragment(fragment);
                break;
            case R.id.lnrDoTransfer:
                fragment = DetailsCartFragment.newInstance(mainView);
                showFragment(fragment);
                break;


        }
    }
}
