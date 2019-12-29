package com.traap.traapapp.ui.fragments.gateWay;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.pixplicity.easyprefs.library.Prefs;
import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.allService.response.SubMenu;
import com.traap.traapapp.apiServices.model.getBalancePasswordLess.ForgetPasswordWalletResponse;
import com.traap.traapapp.apiServices.model.getBalancePasswordLess.GetBalancePasswordLessRequest;
import com.traap.traapapp.apiServices.model.getBalancePasswordLess.GetBalancePasswordLessResponse;
import com.traap.traapapp.apiServices.model.getInfoWallet.GetInfoWalletResponse;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.ui.activities.userProfile.UserProfileActivity;
import com.traap.traapapp.ui.adapters.allMenu.ItemRecyclerViewAdapter;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.ui.activities.myProfile.MyProfileActivity;
import com.traap.traapapp.ui.fragments.videos.VideosFragment;
import com.traap.traapapp.utilities.Utility;

import java.util.List;

import io.realm.Case;

/**
 * Created by MahtabAzizi on 12/8/2019.
 */
public class WalletFragment extends BaseFragment implements View.OnClickListener
{
    private MainActionView mainView;
    private View rootView;
    private TextView tvBalance, tvDate;
    private View rlShirt;
    private ImageView imgCart;
    private View imgBack, imgMenu;
    private TextView tvTitle, tvUserName, tvHeaderPopularNo;
    private TextView txtFullName, customNo, cartNo;
    private View btnForgetPass;
    private View btnBack, rlImageProfile, incBackCart, incFrontCart, lnrInventory;
    private NestedScrollView scrollView;

    private Fragment fragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

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

        initView();

        mainView.showLoading();

        requestGetBalance();
        requestGetInfo();

        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener()
        {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY)
            {
                if (scrollY - oldScrollY > 0)
                {
                    Animation animHide = AnimationUtils.loadAnimation(getContext(), R.anim.hide_button);
                    rootView.findViewById(R.id.rlImageProfile).startAnimation(animHide);
                    rootView.findViewById(R.id.rlImageProfile).setVisibility(View.GONE);
                } else
                {
                    Animation animShow = AnimationUtils.loadAnimation(getContext(), R.anim.show_button);
                    rootView.findViewById(R.id.rlImageProfile).startAnimation(animShow);
                    rootView.findViewById(R.id.rlImageProfile).setVisibility(View.VISIBLE);
                }
            }
        });
        fragmentManager = getChildFragmentManager();

        fragment = DetailsCartFragment.newInstance(mainView);
        transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.container, fragment, "DetailsCartFragment")
                .commit();

        return rootView;
    }

    private void initView()
    {
        try
        {
            rlShirt = rootView.findViewById(R.id.rlShirt);

            tvTitle = rootView.findViewById(R.id.tvTitle);
            tvUserName = rootView.findViewById(R.id.tvUserName);
            tvUserName.setText(Prefs.getString("mobile", ""));
            tvHeaderPopularNo = rootView.findViewById(R.id.tvPopularPlayer);
            tvHeaderPopularNo.setText(String.valueOf(Prefs.getInt("popularPlayer", 12)));
            imgMenu = rootView.findViewById(R.id.imgMenu);


            imgMenu.setOnClickListener(v -> mainView.openDrawer());

            rlShirt.setOnClickListener(v ->
            {
                startActivity(new Intent(SingletonContext.getInstance().getContext(), MyProfileActivity.class));

            });
            imgBack = rootView.findViewById(R.id.imgBack);
            btnBack = rootView.findViewById(R.id.btnBack);
            btnBack.setOnClickListener(v ->
            {
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

        imgCart = rootView.findViewById(R.id.imgCart);
        lnrInventory = rootView.findViewById(R.id.lnrInventory);
        rlImageProfile = rootView.findViewById(R.id.rlImageProfile);
        incBackCart = rootView.findViewById(R.id.incBackCart);
        incFrontCart = rootView.findViewById(R.id.incFrontCart);
        scrollView = rootView.findViewById(R.id.nested);
        tvBalance = rootView.findViewById(R.id.tvBalance);
        tvDate = rootView.findViewById(R.id.tvDate);
        customNo = rootView.findViewById(R.id.customNo);
        cartNo = rootView.findViewById(R.id.cartNo);
        txtFullName = rootView.findViewById(R.id.txtFullName);

        rlImageProfile.setOnClickListener(this);

    }


    /*----------------------------------------------------------------------------------------------------*/

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

    /*----------------------------------------------------------------------------------------------------*/
    private void requestGetInfo()
    {
        GetBalancePasswordLessRequest request = new GetBalancePasswordLessRequest();
        request.setIsWallet(true);
        SingletonService.getInstance().getBalancePasswordLessService().GetInfoWalletService(new OnServiceStatus<WebServiceClass<GetInfoWalletResponse>>()
        {


            @Override
            public void onReady(WebServiceClass<GetInfoWalletResponse> response)
            {
                mainView.hideLoading();

                try
                {
                    if (response.info.statusCode == 200)
                    {
                        setInfoData(response.data);

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

    private void setInfoData(GetInfoWalletResponse data)
    {
        try
        {
            Iterable<String> result = Splitter.fixedLength(4).split(data.getCard_no());
            String[] parts = Iterables.toArray(result, String.class);



        cartNo.setText(parts[0]+"-"+parts[1]+"-"+parts[2]+"-"+parts[3]);
        }catch (Exception e){
            cartNo.setText(data.getCard_no()+"");

        }
        customNo.setText("کدمشتری: "+data.getCustomer_code());
        txtFullName.setText(data.getFull_name()+"");
    }
    /*----------------------------------------------------------------------------------------------------*/

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.rlImageProfile:
                if (incBackCart.getVisibility() == View.GONE)
                {
                    incBackCart.setVisibility(View.VISIBLE);
                    incFrontCart.setVisibility(View.GONE);
                    // lnrInventory.setVisibility(View.GONE);
                } else
                {
                    incBackCart.setVisibility(View.GONE);
                    incFrontCart.setVisibility(View.VISIBLE);
                    //lnrInventory.setVisibility(View.VISIBLE);
                }
                break;
        }
    }
}
