package com.traap.traapapp.ui.fragments.gateWay;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.pixplicity.easyprefs.library.Prefs;
import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.getBalancePasswordLess.ForgetPasswordWalletResponse;
import com.traap.traapapp.apiServices.model.getBalancePasswordLess.GetBalancePasswordLessRequest;
import com.traap.traapapp.apiServices.model.getBalancePasswordLess.GetBalancePasswordLessResponse;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.ui.activities.userProfile.UserProfileActivity;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.main.MainActionView;

import library.android.eniac.utility.Utility;

/**
 * Created by MahtabAzizi on 12/8/2019.
 */
public class WalletFragment extends BaseFragment
{
    private MainActionView mainView;
    private View rootView;
    private TextView tvBalance,tvDate;
    private View rlShirt;
    private View imgBack, imgMenu;
    private TextView tvTitle, tvUserName, tvHeaderPopularNo;
    private View btnForgetPass;
    private View btnBack;


    public WalletFragment()
    {

    }


    public static WalletFragment newInstance(MainActionView mainView)
    {
        WalletFragment fragment = new WalletFragment();
        fragment.setMainView(mainView);
        return fragment;
    }

    private void setMainView(MainActionView mainView)
    {
        this.mainView = mainView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        YoYo.with(Techniques.FadeIn)
                .duration(700)
                .playOn(rootView);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        if (rootView != null)
            return rootView;
        rootView = inflater.inflate(R.layout.fragment_wallet, container, false);


        try
        {
            rlShirt = rootView.findViewById(R.id.rlShirt);

            tvTitle = rootView.findViewById(R.id.tvTitle);
            tvUserName = rootView.findViewById(R.id.tvUserName);
            tvUserName.setText(Prefs.getString("mobile", ""));
            tvHeaderPopularNo = rootView.findViewById(R.id.tvPopularPlayer);
            tvHeaderPopularNo.setText(String.valueOf(Prefs.getInt("popularPlayer", 12)));
            imgMenu = rootView.findViewById(R.id.imgMenu);

            btnForgetPass =rootView.findViewById(R.id.btnForgetPass);
            btnForgetPass.setOnClickListener(v -> {
                requestForgetPassword();
            });
            imgMenu.setOnClickListener(v -> mainView.openDrawer());

            rlShirt.setOnClickListener(v -> {
                startActivity(new Intent(SingletonContext.getInstance().getContext(), UserProfileActivity.class));

            });
            imgBack = rootView.findViewById(R.id.imgBack);
            btnBack=rootView.findViewById(R.id.btnBack);
            btnBack.setOnClickListener(v -> {
                getActivity().onBackPressed();
            });
            imgBack.setOnClickListener(v ->
            {
                getActivity().onBackPressed();
            });

            tvTitle.setText("کیف پول");
        } catch (Exception e)
        {

        }


        mainView.showLoading();
        tvBalance=rootView.findViewById(R.id.tvBalance);
        tvDate=rootView.findViewById(R.id.tvDate);
        requestGetBalance();

        return rootView;
    }

    private void requestForgetPassword()
    {
        GetBalancePasswordLessRequest request = new GetBalancePasswordLessRequest();
        request.setIsWallet(true);
        SingletonService.getInstance().forgetPasswordWalletService().ForgetPasswordWalletService(new OnServiceStatus<WebServiceClass<ForgetPasswordWalletResponse>>()
        {


            @Override
            public void onReady(WebServiceClass<ForgetPasswordWalletResponse> response)
            {
                mainView.hideLoading();

                try
                {
                    if (response.info.statusCode == 200)
                    {
                        //setBalanceData(response.data);
                        Toast.makeText(getContext(), "yees", Toast.LENGTH_SHORT).show();

                    } else
                    {

                        mainView.showError(response.info.message);

                    }
                } catch (Exception e)
                {
                    mainView.showError(e.getMessage());

                }


            }

            @Override
            public void onError(String message)
            {

                mainView.showError(message);
                mainView.hideLoading();

            }
        }, request);
    }

    private void requestGetBalance()
    {
        GetBalancePasswordLessRequest request = new GetBalancePasswordLessRequest();
        request.setIsWallet(true);
        SingletonService.getInstance().getBalancePasswordLessService().GetBalancePasswordLessService(new OnServiceStatus<WebServiceClass<GetBalancePasswordLessResponse>>()
        {


            @Override
            public void onReady(WebServiceClass<GetBalancePasswordLessResponse> response)
            {
                mainView.hideLoading();

                try
                {
                    if (response.info.statusCode == 200)
                    {
                        setBalanceData(response.data);

                    } else
                    {

                        mainView.showError(response.info.message);

                    }
                } catch (Exception e)
                {
                    mainView.showError(e.getMessage());

                }


            }

            @Override
            public void onError(String message)
            {

                mainView.showError(message);
                mainView.hideLoading();

            }
        }, request);
    }

    private void setBalanceData(GetBalancePasswordLessResponse data)
    {
        tvBalance.setText(Utility.priceFormat(data.getBalanceAmount()));
        tvDate.setText(data.getDateTime());
    }
}
