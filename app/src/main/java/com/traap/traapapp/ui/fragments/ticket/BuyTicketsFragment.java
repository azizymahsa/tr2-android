package com.traap.traapapp.ui.fragments.ticket;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.List;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import br.com.simplepass.loading_button_lib.interfaces.OnAnimationEndListener;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.model.buyTicket.InfoViewer;
import com.traap.traapapp.apiServices.model.matchList.MatchItem;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.models.otherModels.headerModel.HeaderModel;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.ui.adapters.ticket.PagerAdapter;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.ui.activities.myProfile.MyProfileFragment;
import com.traap.traapapp.utilities.CustomViewPager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class BuyTicketsFragment extends BaseFragment implements OnClickContinueBuyTicket, OnAnimationEndListener, View.OnClickListener
{
    public static BuyTicketsFragment buyTicketsFragment;
    private static boolean paymentIsComplete = false;
    private View rootView;

    private MainActionView mainView;
    private TabLayout tabLayout;
    private CustomViewPager viewPager;
    private LinearLayout llPrintTicket, llFullInfo, llSelectPosition;
    private TextView btnBackToDetail, tvCountTicket, tvSelectPosition, tvFullInfo, tvPrintTicket;
    private CircularProgressButton btnPaymentConfirm;
    private ImageView ivCountTicket, ivSelectPosition, ivFullInfo, ivPrintTicket;
    private View vOneToTow, vZeroToOne, vTowToThree;
    private TextView tvTitle, tvUserName, tvHeaderPopularNo;
    public String namePosition, selectPositionId;
    Integer count, amountForPay, amountOneTicket;
    private MatchItem matchBuyable;
    private List<InfoViewer> infoViewers;
    private List<Integer> ticketIdList;
    private View imgBack, imgMenu;
    private TextView tvPopularPlayer;
    private String url = "";
    private Integer stadiumId;
    private View rlShirt;


    public BuyTicketsFragment()
    {

    }


    public static BuyTicketsFragment newInstance(MainActionView mainView, MatchItem matchBuyable)
    {
        buyTicketsFragment = new BuyTicketsFragment();
        Bundle args = new Bundle();
        args.putParcelable("matchBuyable", matchBuyable);

        buyTicketsFragment.setArguments(args);
        buyTicketsFragment.setMainView(mainView);
        return buyTicketsFragment;
    }

    public static BuyTicketsFragment newInstance(MainActionView mainView, MatchItem matchBuyable, String refrenceNumber)
    {

        buyTicketsFragment = new BuyTicketsFragment();
        Bundle args = new Bundle();
        if (matchBuyable != null)
            args.putParcelable("matchBuyable", matchBuyable);

        buyTicketsFragment.setArguments(args);
        buyTicketsFragment.setMainView(mainView);
        paymentIsComplete = true;
        return buyTicketsFragment;
    }

    private void setMainView(MainActionView mainView)
    {
        this.mainView = mainView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        matchBuyable = getArguments().getParcelable("matchBuyable");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        if (rootView != null)
        {
            rootView = null;
        }


        rootView = inflater.inflate(R.layout.fragment_buy_ticket, container, false);
        initView();
        // define TabLayout
        tabLayout.addTab(tabLayout.newTab().setText("انتخاب جایگاه"));
        tabLayout.addTab(tabLayout.newTab().setText("تکمیل اطلاعات"));
        //tabLayout.addTab(tabLayout.newTab().setText("پرداخت"));
        // tabLayout.addTab(tabLayout.newTab().setText("صدور بلیت"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //  ViewPager need a PagerAdapter
        final PagerAdapter adapter = new PagerAdapter
                (getFragmentManager(), tabLayout.getTabCount(), this, mainView, this, matchBuyable);

        viewPager.setAdapter(adapter);
        //viewPager.beginFakeDrag();
        viewPager.setPagingEnabled(false);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            public void onPageScrollStateChanged(int state)
            {
            }

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {
            }

            public void onPageSelected(int position)
            {


                if (position == 1)
                {

                    new Handler().postDelayed(() -> adapter.compeletInfoFragmentData(selectPositionId, count, amountForPay, amountOneTicket, ticketIdList, stadiumId), 200);
                    //setDataToCompleteInfoFragment(namePosition);
                }
                if (position == 2)
                    adapter.createShareShowTicket();

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setupWithViewPager(viewPager);
        if (paymentIsComplete)
        {

            //   viewPager.setCurrentItem(3, true);

        }

        EventBus.getDefault().register(this);
        return rootView;
    }

    public void setDataToCompleteInfoFragment(String name)
    {
        this.namePosition = name;
    }

    private void initView()
    {
        try
        {
            rlShirt = rootView.findViewById(R.id.rlShirt);

            tvTitle = rootView.findViewById(R.id.tvTitle);
            tvUserName = rootView.findViewById(R.id.tvUserName);

            tvHeaderPopularNo = rootView.findViewById(R.id.tvPopularPlayer);
            tvHeaderPopularNo.setText(String.valueOf(Prefs.getInt("popularPlayer", 12)));
            tvUserName.setText(TrapConfig.HEADER_USER_NAME);

            imgMenu = rootView.findViewById(R.id.imgMenu);

            imgMenu.setOnClickListener(v -> mainView.openDrawer());

            rlShirt.setOnClickListener(this);
            imgBack = rootView.findViewById(R.id.imgBack);
            imgBack.setOnClickListener(v ->
            {
                getActivity().onBackPressed();
            });

            tvTitle.setText("خرید بلیت");
        } catch (Exception e)
        {

        }

        if (paymentIsComplete)
        {

            //   viewPager.setCurrentItem(3, true);

        }
        btnPaymentConfirm = rootView.findViewById(R.id.btnPaymentConfirm);
        btnBackToDetail = rootView.findViewById(R.id.btnBackToDetail);
        tabLayout = rootView.findViewById(R.id.tab_layout);
        viewPager = rootView.findViewById(R.id.pager);
        llPrintTicket = rootView.findViewById(R.id.llPrintTicket);
        llFullInfo = rootView.findViewById(R.id.llFullInfo);
        ivCountTicket = rootView.findViewById(R.id.ivCountTicket);
        tvCountTicket = rootView.findViewById(R.id.tvCountTicket);
        ivSelectPosition = rootView.findViewById(R.id.ivSelectPosition);
        tvSelectPosition = rootView.findViewById(R.id.tvSelectPosition);
        ivFullInfo = rootView.findViewById(R.id.ivFullInfo);
        tvFullInfo = rootView.findViewById(R.id.tvFullInfo);
        ivPrintTicket = rootView.findViewById(R.id.ivPrintTicket);
        tvPrintTicket = rootView.findViewById(R.id.tvPrintTicket);
        vZeroToOne = rootView.findViewById(R.id.vZeroToOne);
        vOneToTow = rootView.findViewById(R.id.vOneToTow);
        vTowToThree = rootView.findViewById(R.id.vTowToThree);

        llPrintTicket.setOnClickListener(this);
        llFullInfo.setOnClickListener(this);
//        btnPaymentConfirm.setOnClickListener(this);
        //  btnBackToDetail.setOnClickListener(this);

    }

    private int getItem(int i)
    {
        return viewPager.getCurrentItem() + i;
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.btnPaymentConfirm:
                viewPager.setCurrentItem(getItem(+1), true);
                break;
            case R.id.btnBackToDetail:
                viewPager.setCurrentItem(getItem(-1), true);
                break;
            case R.id.llPrintTicket:
                // viewPager.setCurrentItem(getItem(+1), true);
                break;
            case R.id.llFullInfo:
                // mainView.onPackSimCard();
                break;
            case R.id.rlShirt:
                 startActivity(new Intent(SingletonContext.getInstance().getContext(), MyProfileFragment.class));

                break;


        }
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
                Toast.makeText(getActivity(), "Tab selected " + tab.getPosition(), Toast.LENGTH_SHORT).show();
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


    @Override
    public void onAnimationEnd()
    {

    }

    @Override
    public void onBackClicked()
    {

        if (viewPager.getCurrentItem() == 1)
        {

           SelectPositionFragment.fragment.getAllBoxesRequest(true);
        }

        viewPager.setCurrentItem(getItem(-1), true);
        checkPositionFromSetSelected();
    }

    @Override
    public void onContinueClicked()
    {

        viewPager.setCurrentItem(getItem(+1), true);
        checkPositionFromSetSelected();

    }

    @Override
    public void goBuyTicket()
    {
        viewPager.setCurrentItem(0, true);
        checkPositionFromSetSelected();
    }


    private void checkPositionFromSetSelected()
    {
        if (viewPager.getCurrentItem() == 0)
        {
            ivCountTicket.setImageResource(R.drawable.select_step_non);
            tvCountTicket.setTextColor(getResources().getColor(R.color.textColorPrimary));

            ivSelectPosition.setImageResource(R.drawable.un_select_step);
            tvSelectPosition.setTextColor(getResources().getColor(R.color._disable_color));

            ivFullInfo.setImageResource(R.drawable.un_select_step);
            tvFullInfo.setTextColor(getResources().getColor(R.color._disable_color));

            ivPrintTicket.setImageResource(R.drawable.un_select_step);
            tvPrintTicket.setTextColor(getResources().getColor(R.color._disable_color));

            vZeroToOne.setBackgroundColor(getResources().getColor(R.color._disable_color));
            vOneToTow.setBackgroundColor(getResources().getColor(R.color._disable_color));
            vTowToThree.setBackgroundColor(getResources().getColor(R.color._disable_color));

        } else if (viewPager.getCurrentItem() == 1)
        {
            ivCountTicket.setImageResource(R.drawable.select_step);
            tvCountTicket.setTextColor(getResources().getColor(R.color.textColorPrimary));

            ivSelectPosition.setImageResource(R.drawable.select_step_non);
            tvSelectPosition.setTextColor(getResources().getColor(R.color.textColorPrimary));

            ivFullInfo.setImageResource(R.drawable.un_select_step);
            tvFullInfo.setTextColor(getResources().getColor(R.color._disable_color));

            ivPrintTicket.setImageResource(R.drawable.un_select_step);
            tvPrintTicket.setTextColor(getResources().getColor(R.color._disable_color));

            vZeroToOne.setBackgroundColor(getResources().getColor(R.color.textColorPrimary));
            vOneToTow.setBackgroundColor(getResources().getColor(R.color._disable_color));
            vTowToThree.setBackgroundColor(getResources().getColor(R.color._disable_color));

        } else if (viewPager.getCurrentItem() == 2)
        {
            ivCountTicket.setImageResource(R.drawable.select_step);
            tvCountTicket.setTextColor(getResources().getColor(R.color.textColorPrimary));

            ivSelectPosition.setImageResource(R.drawable.select_step);
            tvSelectPosition.setTextColor(getResources().getColor(R.color.textColorPrimary));

            ivFullInfo.setImageResource(R.drawable.select_step);
            tvFullInfo.setTextColor(getResources().getColor(R.color.textColorPrimary));

            ivPrintTicket.setImageResource(R.drawable.select_step);
            tvPrintTicket.setTextColor(getResources().getColor(R.color.textColorPrimary));

            vZeroToOne.setBackgroundColor(getResources().getColor(R.color.textColorPrimary));
            vOneToTow.setBackgroundColor(getResources().getColor(R.color.textColorPrimary));
            vTowToThree.setBackgroundColor(getResources().getColor(R.color.textColorPrimary));
          /*  ivFullInfo.setImageResource(R.drawable.select_step_non);
            tvFullInfo.setTextColor(getResources().getColor(R.color.textColorPrimary));

            ivPrintTicket.setImageResource(R.drawable.un_select_step);
            tvPrintTicket.setTextColor(getResources().getColor(R.color._disable_color));

            vZeroToOne.setBackgroundColor(getResources().getColor(R.color.textColorPrimary));
            vOneToTow.setBackgroundColor(getResources().getColor(R.color.textColorPrimary));
            vTowToThree.setBackgroundColor(getResources().getColor(R.color._disable_color));
*/
        } else if (viewPager.getCurrentItem() == 3)
        {

            ivCountTicket.setImageResource(R.drawable.select_step);
            tvCountTicket.setTextColor(getResources().getColor(R.color.textColorPrimary));

            ivSelectPosition.setImageResource(R.drawable.select_step);
            tvSelectPosition.setTextColor(getResources().getColor(R.color.textColorPrimary));


            ivFullInfo.setImageResource(R.drawable.select_step);
            tvFullInfo.setTextColor(getResources().getColor(R.color.textColorPrimary));

            ivPrintTicket.setImageResource(R.drawable.select_step);
            tvPrintTicket.setTextColor(getResources().getColor(R.color.textColorPrimary));

            vZeroToOne.setBackgroundColor(getResources().getColor(R.color.textColorPrimary));
            vOneToTow.setBackgroundColor(getResources().getColor(R.color.textColorPrimary));
            vTowToThree.setBackgroundColor(getResources().getColor(R.color.textColorPrimary));
        }
    }

    public void setData(String selectPositionId, int count, int amountForPay, int amountOneTicket, List<Integer> results, Integer stadiumId)
    {

        this.selectPositionId = selectPositionId;
        this.count = count;
        this.amountForPay = amountForPay;
        this.amountOneTicket = amountOneTicket;
        this.ticketIdList = results;
        this.stadiumId = stadiumId;

    }

    public void showLoading()
    {
        mainView.showLoading();
    }

    public void hideLoading()
    {
        mainView.hideLoading();
    }

    public void setInfoViewers(List<InfoViewer> infoViewers)
    {
        this.infoViewers = infoViewers;

    }

    public void setUrlFromWebFragment(String url)
    {

        this.url = url;

    }

    public void openWebPayment(String url)
    {
//        Utility.openUrlCustomTab(getActivity(), url);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        getActivity().startActivity(intent);
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

    @Override
    public void startAddCardActivity()
    {

    }

    public CustomViewPager getViewpager()
    {
        return viewPager;
    }


    @Subscribe
    public void getHeaderContent(HeaderModel headerModel)
    {
        if (headerModel.getPopularNo() != 0)
        {
            tvHeaderPopularNo.setText(String.valueOf(headerModel.getPopularNo()));
        }
        tvUserName.setText(TrapConfig.HEADER_USER_NAME);
    }


    @Override
    public void onDestroy()
    {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}