package com.traap.traapapp.ui.fragments.moneyTransfer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import java.util.Objects;

import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.mainPage.MainPageResponse;
import com.traap.traapapp.ui.adapters.MoneyTransferBankLogoAdapter;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.utilities.TextViewEx;
import com.traap.traapapp.utilities.TraapMaskedEditText;
import com.traap.traapapp.utilities.Utility;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Javad.Abadi on 7/10/2018.
 */
@SuppressLint("ValidFragment")
public class MainMoneyTransferFragment extends BaseFragment implements FragmentTransfer{

    private View v;

    private MainActionView mainView;




    public MainMoneyTransferFragment()
    {

    }

    public static MainMoneyTransferFragment newInstance(MainActionView mainView)
    {
        MainMoneyTransferFragment f = new MainMoneyTransferFragment();
        f.setMainView(mainView);
        return f;
    }

    private void setMainView(MainActionView mainView)
    {
        this.mainView = mainView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {


        v = inflater.inflate(R.layout.main_fragment_transfer_money, container, false);

        onMain();


        return v;
    }




    @Override
    public void onDestroy()
    {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onOriginCard()
    {
        OriginCardFragment originCardFragment =  OriginCardFragment.newInstance(mainView,this);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

        transaction.replace(R.id.MoneyTransferFragment, originCardFragment);
        // transaction.add(moneyTransferFragment, "moneyTransferFragment");

        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onMain()
    {
        MoneyTransferFragment moneyTransferFragment =  MoneyTransferFragment.newInstance(mainView,this);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

        transaction.replace(R.id.MoneyTransferFragment, moneyTransferFragment);
        // transaction.add(moneyTransferFragment, "moneyTransferFragment");

        transaction.addToBackStack(null);
        transaction.commit();

    }
}