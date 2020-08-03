package com.traap.traapapp.ui.fragments.paymentGateWay;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.pixplicity.easyprefs.library.Prefs;
import com.squareup.picasso.Picasso;
import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.model.matchList.MatchItem;
import com.traap.traapapp.apiServices.model.paymentMatch.PaymentMatchRequest;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.models.otherModels.paymentInstance.SimChargePaymentInstance;
import com.traap.traapapp.models.otherModels.paymentInstance.SimPackPaymentInstance;
import com.traap.traapapp.models.otherModels.predict.PredictTabModel;
import com.traap.traapapp.ui.adapters.paymentGateway.SelectPaymentAdapter;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.ui.fragments.simcardCharge.OnClickContinueSelectPayment;
import com.traap.traapapp.utilities.CustomViewPager;
import com.traap.traapapp.utilities.Logger;

import java.util.ArrayList;
import java.util.List;

import br.com.simplepass.loading_button_lib.interfaces.OnAnimationEndListener;

/**
 * Created by MahsaAzizi on 11/20/2019.
 */
public class SelectPaymentGatewayFragment extends BaseFragment implements OnAnimationEndListener, View.OnClickListener, PaymentGateWayParentActionView
{
    private Context context;
    private List<PredictTabModel> tabList;
    private static SelectPaymentGatewayFragment fragment;
    private PaymentMatchRequest paymentMatchRequest;
    private String url = "";
    private MainActionView mainView;
    private View rootView;
    private TabLayout tabLayout;
    private CustomViewPager viewPager;
    private TextView tvTitle, tvUserName, tvPopularPlayer;
    private View imgBack, imgMenu;
    private ArrayList<MatchItem> matchBuyable;

    private String amount = "";
    private String title = "";
    private int imageDrawable = 1;
    private String mobile = "";
    private TextView tvWallet, tvCardsShetab, tvGateway, tvAmount, tvTitlePay;
    private ImageView imgLogo;

    private SimChargePaymentInstance simChargePaymentInstance;
    private int PAYMENT_STATUS;
    private SimPackPaymentInstance simPackPaymentInstance;
    private PaymentGateWayParentActionView pActionView;
    private Integer idBill = 0;

    public SelectPaymentGatewayFragment()
    {
    }

//    public SelectPaymentGatewayFragment(String url, MainActionView mainView, int imageDrawable, String title, String amount, PaymentMatchRequest paymentMatchRequest)
//    {
//        this.url = url;
//        this.mainView = mainView;
//        this.imageDrawable = imageDrawable;
//        this.title = title;
//        this.amount = amount;
//        this.paymentMatchRequest = paymentMatchRequest;
//    }

//    public SelectPaymentGatewayFragment(String url, MainActionView mainView, int imageDrawable, String title, String amount, int PAYMENT_STATUS)
//    {
//        this.url = url;
//        this.mainView = mainView;
//        this.imageDrawable = imageDrawable;
//        this.title = title;
//        this.amount = amount;
//        this.PAYMENT_STATUS = PAYMENT_STATUS;
//
//    }

//    public SelectPaymentGatewayFragment(String url, MainActionView mainView, int imageDrawable, String title, String amount)
//    {
//        this.url = url;
//        this.mainView = mainView;
//        this.imageDrawable = imageDrawable;
//        this.title = title;
//        this.amount = amount;
//
//    }

//    public static SelectPaymentGatewayFragment newInstance(String urlPayment, MainActionView mainView, int imageDrawable, String title, String amount, SimChargePaymentInstance paymentInstance)
//    {
//        fragment = new SelectPaymentGatewayFragment();
//        fragment.setParentActionView(mainView);
//        Bundle args = new Bundle();
//        args.putString("url", urlPayment);
//        args.putString("amount", amount);
//        args.putInt("imageDrawable", imageDrawable);
//        args.putString("mobile", mobile);
//        args.putString("title", title);
//        args.putParcelable("paymentInstance", (Parcelable) paymentInstance);
//
//        fragment.setArguments(args);
//
//        return fragment;
//    }

//       public static Fragment newInstance(String url,MainActionView mainView, String amount, String title, int imageDrawable, SimChargePaymentInstance paymentInstance,String mobile)
//       {
//
//
//           SelectPaymentGatewayFragment fragment = new SelectPaymentGatewayFragment();
//           fragment.setParentActionView(mainView);
//
//           Bundle args = new Bundle();
//           args.putString("url", url);
//           args.putString("amount", amount);
//           args.putInt("imageDrawable", imageDrawable);
//           args.putString("mobile", mobile);
//           args.putString("title", title);
//           args.putParcelable("paymentInstance", (Parcelable) paymentInstance);
//
//           fragment.setArguments(args);
//
//           return fragment;
//
//       }

///* public static <T, I extends PaymentParentActionView> PaymentFragment newInstance(I paymentParentActionView,
//                                                                                  String price,
//                                                                                  String title,
//                                                                                  int imgLogo,
//                                                                                  String mobile,
//                                                                                  T response)
// {
//     PaymentFragment fragment = new PaymentFragment();
//     fragment.setParentActionView(paymentParentActionView);
//
//     Bundle args = new Bundle();*/

//    private void setMainView(MainActionView mainView, ArrayList<MatchItem> matchBuyable)
//    {
//        this.mainView = mainView;
//        this.matchBuyable = matchBuyable;
//    }

    public static <I extends PaymentGateWayParentActionView> SelectPaymentGatewayFragment newInstance(int PAYMENT_STATUS, OnClickContinueSelectPayment paymentParentActionView, String urlPayment, MainActionView mainView, int imageDrawable, String title, String amount, SimPackPaymentInstance paymentInstance, String mobile)
    {
        SelectPaymentGatewayFragment fragment = new SelectPaymentGatewayFragment();
        //  fragment.setParentActionView(mainView);
        fragment.setParentActionView(paymentParentActionView, mainView);
        fragment.setStatus(PAYMENT_STATUS);
        Bundle args = new Bundle();
        args.putString("url", urlPayment);
        args.putString("amount", amount);
        args.putInt("imageDrawable", imageDrawable);
        args.putString("mobile", mobile);
        args.putString("title", title);
        // args.putParcelable("paymentInstance", paymentInstance);
        args.putParcelable("paymentPackInstance", paymentInstance);

        fragment.setArguments(args);

        return fragment;
    }

    public static <I extends PaymentGateWayParentActionView> SelectPaymentGatewayFragment newInstance(int PAYMENT_STATUS, I paymentParentActionView,
                                                                                                      String urlPayment,
                                                                                                      MainActionView mainView,
                                                                                                      int imageDrawable,
                                                                                                      String title,
                                                                                                      String amount,
                                                                                                      SimChargePaymentInstance paymentInstance,
                                                                                                      String mobile)
    {
        SelectPaymentGatewayFragment fragment = new SelectPaymentGatewayFragment();
        // fragment.setParentActionView(mainView);
        fragment.setParentActionView(paymentParentActionView, mainView);
        fragment.setStatus(PAYMENT_STATUS);

        Bundle args = new Bundle();
        args.putString("url", urlPayment);
        args.putString("amount", amount);
        args.putInt("imageDrawable", imageDrawable);
        args.putString("mobile", mobile);
        args.putString("title", title);
        // args.putParcelable("paymentPackInstance", paymentInstance);
        args.putParcelable("paymentInstance", paymentInstance);

        fragment.setArguments(args);

        return fragment;
    }

    public static Fragment newInstance(String url, MainActionView mainView, String textBillPayment, String number, Integer idSelectedBillType, String amount, int PAYMENT_STATUS)
    {
        SelectPaymentGatewayFragment fragment = new SelectPaymentGatewayFragment();
        fragment.setParentActionView(mainView);
        fragment.setStatus(PAYMENT_STATUS);
        fragment.setIdBill(idSelectedBillType);

        Bundle args = new Bundle();
        args.putString("url", url);
        args.putString("amount", amount);
        args.putString("mobile", number);
        args.putString("title", textBillPayment);

        fragment.setArguments(args);

        return fragment;
    }

    private void setIdBill(Integer idSelectedBillType)
    {
        this.idBill = idSelectedBillType;
    }


    private void setParentActionView(MainActionView mainView)
    {
        this.mainView = mainView;
    }

    private void setStatus(int payment_status)
    {
        this.PAYMENT_STATUS = payment_status;

    }

    private void setParentActionView(PaymentGateWayParentActionView pActionView, MainActionView mainView)
    {
        this.pActionView = pActionView;
        this.mainView = mainView;
    }

    @Override
    public void onAttach(@NonNull Context context)
    {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if (getArguments() != null)
        {
            amount = getArguments().getString("amount");
            title = getArguments().getString("title");
            imageDrawable = getArguments().getInt("imageDrawable", 0);
            mobile = getArguments().getString("mobile", "");
            url = getArguments().getString("url", "");
            simChargePaymentInstance = getArguments().getParcelable("paymentInstance");
            simPackPaymentInstance = getArguments().getParcelable("paymentPackInstance");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        if (rootView != null)
        {
            rootView = null;
        }

        rootView = inflater.inflate(R.layout.select_payment_fragment, container, false);

        initView();

        if (PAYMENT_STATUS == 13)//در پرداخت کیف پول تب کیف پول باید غیر فعال باشد
        {
            tvWallet.setTextColor(getResources().getColor(R.color.gray));
        }

        setContent();

        createTabLayout(amount, title, imageDrawable, mobile, simChargePaymentInstance, simPackPaymentInstance);

        return rootView;
    }

    private void setContent()
    {
        tvAmount.setText(amount);
        tvTitlePay.setText(title);

        if (imageDrawable == 0)
        {
            imgLogo.setVisibility(View.GONE);
        }
        else
        {
            Picasso.with(getActivity()).load(imageDrawable).into(imgLogo);
        }
    }

    private void createTabLayout(String amount, String title, int imageDrawable, String mobile, SimChargePaymentInstance simChargePaymentInstance, SimPackPaymentInstance simPackPaymentInstance)
    {
        // define TabLayout
//        tabLayout.addTab(tabLayout.newTab().setText("درگاه پرداخت"));
//        tabLayout.addTab(tabLayout.newTab().setText("کارت های شتابی"));
//        tabLayout.addTab(tabLayout.newTab().setText("کیف پول"));
//        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        tabList = new ArrayList<>(3);

        PredictTabModel tabItem = new PredictTabModel(2, "درگاه پرداخت");
        tabList.add(tabItem);
        PredictTabModel tabItem2 = new PredictTabModel(1, "کارت های شتابی");
        tabList.add(tabItem2);
        PredictTabModel tabItem3 = new PredictTabModel(0, "کیف پول");
        tabList.add(tabItem3);



       /* SimChargePaymentInstance paymentInstance = new SimChargePaymentInstance();
        paymentInstance.setPAYMENT_STATUS(TrapConfig.PAYMENT_STAUS_ChargeSimCard);
        paymentInstance.setOperatorType(12);
        paymentInstance.setSimcardType(12);
        paymentInstance.setTypeCharge(Integer.valueOf(1));*/

        final SelectPaymentAdapter adapter = new SelectPaymentAdapter
                (context, getFragmentManager(), tabList, mainView, amount, title, imageDrawable, mobile,
                        url, this.simChargePaymentInstance, simPackPaymentInstance, PAYMENT_STATUS, idBill);

        viewPager.setAdapter(adapter);
        //viewPager.beginFakeDrag();
        viewPager.setPagingEnabled(false);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setupWithViewPager(viewPager);

        for (int i = 0; i < tabLayout.getTabCount(); i++)
        {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(adapter.getTabView(i));
        }

        viewPager.setCurrentItem(0);

    }

    private void initView()
    {
        try
        {
            tvTitle = rootView.findViewById(R.id.tvTitle);
            tvUserName = rootView.findViewById(R.id.tvUserName);
            tvUserName.setText(TrapConfig.HEADER_USER_NAME);
            imgMenu = rootView.findViewById(R.id.imgMenu);

            imgMenu.setOnClickListener(v -> mainView.openDrawer());

            tvPopularPlayer = rootView.findViewById(R.id.tvPopularPlayer);
            tvPopularPlayer.setText(Prefs.getString("PopularPlayer", "12"));

            imgBack = rootView.findViewById(R.id.imgBack);
            imgBack.setOnClickListener(v ->
            {
                getActivity().onBackPressed();
//                mainView.onBackToChargFragment(PAYMENT_STATUS, idBill);

                // pActionView.onPaymentCancelAndBack();

            });

            tvTitle.setText("پرداخت");
            tvAmount = rootView.findViewById(R.id.tvAmount);
            tvTitlePay = rootView.findViewById(R.id.tvTitlePay);
            imgLogo = rootView.findViewById(R.id.imgLogo);
        }
        catch (Exception e)
        {
            Logger.e(getActivity().getPackageName(), e.getMessage());
        }
        tabLayout = rootView.findViewById(R.id.tab_layout);
        viewPager = rootView.findViewById(R.id.pager);

        tvWallet = rootView.findViewById(R.id.tvWallet);
        tvCardsShetab = rootView.findViewById(R.id.tvCardsShetab);
        tvGateway = rootView.findViewById(R.id.tvGateway);
        tvWallet.setOnClickListener(this);
        tvCardsShetab.setOnClickListener(this);
        tvGateway.setOnClickListener(this);

        mainView.hideLoading();
    }

    /**
     * Listener for tab selected
     *
     * @param viewPager
     * @return
     */
    @NonNull
    private TabLayout.OnTabSelectedListener getOnTabSelectedListener(final ViewPager viewPager)
    {
        return new TabLayout.OnTabSelectedListener()
        {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab)
            {
                // nothing now
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab)
            {
                // nothing now
            }
        };
    }


    private int getItem(int i)
    {
        return viewPager.getCurrentItem() + i;
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.tvGateway:
            {
                viewPager.setCurrentItem(0, true);
                tvGateway.setBackgroundResource(R.drawable.background_border_a);
                tvWallet.setBackgroundColor(Color.TRANSPARENT);
                tvCardsShetab.setBackgroundColor(Color.TRANSPARENT);
                tvWallet.setTextColor(getResources().getColor(R.color.returnButtonColor));
                tvGateway.setTextColor(getResources().getColor(R.color.borderColorRed));
                tvCardsShetab.setTextColor(getResources().getColor(R.color.gray));

                break;
            }
            case R.id.tvWallet:
            {
                if (tvWallet.getCurrentTextColor() == getResources().getColor(R.color.returnButtonColor))
                {
                    viewPager.setCurrentItem(2, true);
                    tvWallet.setBackgroundResource(R.drawable.background_border_a);
                    tvCardsShetab.setBackgroundColor(Color.TRANSPARENT);
                    tvGateway.setBackgroundColor(Color.TRANSPARENT);
                    tvWallet.setTextColor(getResources().getColor(R.color.borderColorRed));
                    tvCardsShetab.setTextColor(getResources().getColor(R.color.gray));
                    tvGateway.setTextColor(getResources().getColor(R.color.returnButtonColor));
                }
                break;
            }
            case R.id.tvCardsShetab:
            {
                viewPager.setCurrentItem(1, true);
                tvCardsShetab.setBackgroundResource(R.drawable.background_border_a);
                tvWallet.setBackgroundColor(Color.TRANSPARENT);
                tvGateway.setBackgroundColor(Color.TRANSPARENT);

                tvGateway.setTextColor(getResources().getColor(R.color.returnButtonColor));
                tvWallet.setTextColor(getResources().getColor(R.color.returnButtonColor));
                tvCardsShetab.setTextColor(getResources().getColor(R.color.borderColorRed));
                break;
            }
        }
    }

    @Override
    public void onAnimationEnd()
    {

    }

    public CustomViewPager getViewpager()
    {
        return viewPager;
    }

    @Override
    public void showPaymentParentLoading()
    {

    }

    @Override
    public void hidePaymentParentLoading()
    {

    }

    @Override
    public void onPaymentCancelAndBack()
    {

    }
   /* @Override
    public void onBackClicked()
    {
        viewPager.setCurrentItem(getItem(-1), true);
        checkPositionFromSetSelected();
    }*/
}
