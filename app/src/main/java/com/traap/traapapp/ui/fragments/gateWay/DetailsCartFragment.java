package com.traap.traapapp.ui.fragments.gateWay;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.pixplicity.easyprefs.library.Prefs;
import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.model.contact.OnSelectContact;
import com.traap.traapapp.apiServices.model.getBalancePasswordLess.SettingBalance;
import com.traap.traapapp.models.otherModels.headerModel.HeaderModel;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.ui.fragments.turnover.TurnoverFragment;

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
    private SettingBalance settingsData;


    public DetailsCartFragment()
    {

    }

    public static DetailsCartFragment newInstance(MainActionView mainView, SettingBalance settingsData)
    {
        DetailsCartFragment f = new DetailsCartFragment();
        f.setMainView(mainView);
        f.setSettingData(settingsData);
        return f;
    }

    private void setSettingData(SettingBalance settingsData)
    {
        this.settingsData = settingsData;
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
        Prefs.putBoolean("isMainWalletFragment", true);

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
                fragment = WithdrawAccountFragment.newInstance(mainView, settingsData);
                showFragment(fragment);
                Prefs.putBoolean("isMainWalletFragment", false);
                break;
            case R.id.lnrGetReport:
                fragment = TurnoverFragment.newInstance(mainView);
                showFragment(fragment);
                Prefs.putBoolean("isMainWalletFragment", false);

                break;
            case R.id.lnrManageWallet:
                fragment = ManageWalletFragment.newInstance(mainView);
                showFragment(fragment);
                Prefs.putBoolean("isMainWalletFragment", false);

                break;
            case R.id.lnrIncreaseInveronment:
                fragment = IncreaseInventoryFragment.newInstance(mainView, settingsData);
                showFragment(fragment);
                Prefs.putBoolean("isMainWalletFragment", false);

                break;
            case R.id.lnrDoTransfer:
                fragment = MoneyTransferFragment.newInstance(mainView);
                showFragment(fragment);
                Prefs.putBoolean("isMainWalletFragment", false);


                break;


        }
    }

    public void onSelectContact(OnSelectContact onSelectContact)
    {
        if (fragment instanceof MoneyTransferFragment)
        {
        }
        ((MoneyTransferFragment) fragment).onSelectContact(onSelectContact);
    }
}
