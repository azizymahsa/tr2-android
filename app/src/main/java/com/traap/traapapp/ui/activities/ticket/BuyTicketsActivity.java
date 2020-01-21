package com.traap.traapapp.ui.activities.ticket;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.traap.traapapp.ui.base.BaseActivity;
import com.traap.traapapp.ui.activities.myProfile.MyProfileActivity;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.ui.fragments.simcardCharge.ChargeFragment;
import com.traap.traapapp.ui.fragments.ticket.CompeletInfoFragment;
import com.traap.traapapp.ui.fragments.ticket.OnClickContinueBuyTicket;
import com.traap.traapapp.ui.fragments.ticket.SelectPositionFragment;
import com.traap.traapapp.utilities.CustomViewPager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class BuyTicketsActivity extends BaseActivity implements OnClickContinueBuyTicket, OnAnimationEndListener, View.OnClickListener {
    public static BuyTicketsActivity buyTicketsFragment;
    private static boolean paymentIsComplete = false;
    private View rootView;

    private TabLayout tabLayout;
    private CustomViewPager viewPager;
    private LinearLayout llPrintTicket, llFullInfo, llSelectPosition;
    private TextView btnBackToDetail, tvCountTicket, tvSelectPosition, tvFullInfo, tvPrintTicket;
    private CircularProgressButton btnPaymentConfirm;
    private ImageView ivCountTicket, ivSelectPosition, ivFullInfo, ivPrintTicket, imgHome;
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
     PagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_ticket);
        //  showLoading();
        if (savedInstanceState == null) {
            try {
                Bundle extras = getIntent().getExtras();
                if (extras == null) {

                } else {
                    matchBuyable = extras.getParcelable("MatchBuyable");
                }
            } catch (Exception e) {

            }

        }


        initView();
        // define TabLayout
        tabLayout.addTab(tabLayout.newTab().setText("انتخاب جایگاه"));
        tabLayout.addTab(tabLayout.newTab().setText("تکمیل اطلاعات"));
        //tabLayout.addTab(tabLayout.newTab().setText("پرداخت"));
        // tabLayout.addTab(tabLayout.newTab().setText("صدور بلیت"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //  ViewPager need a PagerAdapter
        adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount(), this, matchBuyable);

        viewPager.setAdapter(adapter);
        //viewPager.beginFakeDrag();
        viewPager.setPagingEnabled(false);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {
            }

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            public void onPageSelected(int position) {


                if (position == 1) {
                    new Handler().postDelayed(adapter::compeletInfoFragmentData, 200);

                    new Handler().postDelayed(() -> adapter.compeletInfoFragmentData(selectPositionId, count, amountForPay, amountOneTicket, ticketIdList, stadiumId), 50);
                    //setDataToCompleteInfoFragment(namePosition);
                }

               /* if (position == 2)
                    adapter.createShareShowTicket();*/

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setupWithViewPager(viewPager);
        if (paymentIsComplete) {

            //   viewPager.setCurrentItem(3, true);

        }

        EventBus.getDefault().register(this);
    }

    public void setDataToCompleteInfoFragment(String name) {
        this.namePosition = name;
    }

    public void showLoading() {
        findViewById(R.id.rlLoading).setVisibility(View.VISIBLE);
    }

    public void hideLoading() {
        findViewById(R.id.rlLoading).setVisibility(View.GONE);
        runOnUiThread(() ->
        {
        });
    }

    private void initView() {
        try {
            rlShirt = findViewById(R.id.rlShirt);
            imgHome = findViewById(R.id.imgHome);
            imgHome.setOnClickListener(v -> {
                finish();
            });
            tvTitle = findViewById(R.id.tvTitle);
            tvUserName = findViewById(R.id.tvUserName);

            tvHeaderPopularNo = findViewById(R.id.tvPopularPlayer);
            tvHeaderPopularNo.setText(String.valueOf(Prefs.getInt("popularPlayer", 12)));
            tvUserName.setText(TrapConfig.HEADER_USER_NAME);

            imgMenu = findViewById(R.id.imgMenu);
            imgMenu.setVisibility(View.GONE);

            /*  imgMenu.setOnClickListener(v -> mainView.openDrawer());*/

            rlShirt.setOnClickListener(this);
            imgBack = findViewById(R.id.imgBack);
            imgBack.setOnClickListener(v ->
            {
                onBackClicked();
                // finish();
            });

            tvTitle.setText("خرید بلیت");
        } catch (Exception e) {

        }

        if (paymentIsComplete) {

            //   viewPager.setCurrentItem(3, true);

        }
        // btnPaymentConfirm = rootView.findViewById(R.id.btnPaymentConfirm);
        //  btnBackToDetail = rootView.findViewById(R.id.btnBackToDetail);
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.pager);
        llPrintTicket = findViewById(R.id.llPrintTicket);
        llFullInfo = findViewById(R.id.llFullInfo);
        ivCountTicket = findViewById(R.id.ivCountTicket);
        tvCountTicket = findViewById(R.id.tvCountTicket);
        ivSelectPosition = findViewById(R.id.ivSelectPosition);
        tvSelectPosition = findViewById(R.id.tvSelectPosition);
        ivFullInfo = findViewById(R.id.ivFullInfo);
        tvFullInfo = findViewById(R.id.tvFullInfo);
        ivPrintTicket = findViewById(R.id.ivPrintTicket);
        tvPrintTicket = findViewById(R.id.tvPrintTicket);
        vZeroToOne = findViewById(R.id.vZeroToOne);
        vOneToTow = findViewById(R.id.vOneToTow);
        vTowToThree = findViewById(R.id.vTowToThree);

        llPrintTicket.setOnClickListener(this);
        llFullInfo.setOnClickListener(this);
//        btnPaymentConfirm.setOnClickListener(this);
        //  btnBackToDetail.setOnClickListener(this);

    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
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
                startActivityForResult(new Intent(SingletonContext.getInstance().getContext(), MyProfileActivity.class), 100);

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
    private TabLayout.OnTabSelectedListener getOnTabSelectedListener(final ViewPager viewPager) {
        return new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                //Toast.makeText(getApplicationContext(), "Tab selected " + tab.getPosition(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // nothing now
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // nothing now
            }
        };
    }


    @Override
    public void onAnimationEnd() {

    }

    @Override
    public void onBackClicked() {

        if (viewPager.getCurrentItem() == 0) {
            finish();

        }else if(viewPager.getCurrentItem() == 1 &&adapter.getLlGateWaye().getVisibility()==View.VISIBLE){
           adapter.compeletInfoFragmentData();

            return;
        }

        else if (viewPager.getCurrentItem() == 1) {

            SelectPositionFragment.fragment.getAllBoxesRequest(true);
        }
        viewPager.setCurrentItem(getItem(-1), true);
        checkPositionFromSetSelected();
    }

    @Override
    public void onContinueClicked() {

        viewPager.setCurrentItem(getItem(+1), true);
        checkPositionFromSetSelected();

    }

    @Override
    public void goBuyTicket() {
        viewPager.setCurrentItem(0, true);
        checkPositionFromSetSelected();
    }


    private void checkPositionFromSetSelected() {
        if (viewPager.getCurrentItem() == 0) {
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

        } else if (viewPager.getCurrentItem() == 1) {
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

        } else if (viewPager.getCurrentItem() == 2) {
            ivCountTicket.setImageResource(R.drawable.select_step);
            tvCountTicket.setTextColor(getResources().getColor(R.color.textColorPrimary));

            ivSelectPosition.setImageResource(R.drawable.select_step);
            tvSelectPosition.setTextColor(getResources().getColor(R.color.textColorPrimary));


            vZeroToOne.setBackgroundColor(getResources().getColor(R.color.textColorPrimary));
            vOneToTow.setBackgroundColor(getResources().getColor(R.color.textColorPrimary));
            vTowToThree.setBackgroundColor(getResources().getColor(R.color.textColorPrimary));
            ivFullInfo.setImageResource(R.drawable.select_step_non);
            tvFullInfo.setTextColor(getResources().getColor(R.color.textColorPrimary));

            ivPrintTicket.setImageResource(R.drawable.un_select_step);
            tvPrintTicket.setTextColor(getResources().getColor(R.color._disable_color));

            vZeroToOne.setBackgroundColor(getResources().getColor(R.color.textColorPrimary));
            vOneToTow.setBackgroundColor(getResources().getColor(R.color.textColorPrimary));
            vTowToThree.setBackgroundColor(getResources().getColor(R.color._disable_color));

        } else if (viewPager.getCurrentItem() == 3) {

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

    public void setData(String selectPositionId, int count, int amountForPay, int amountOneTicket, List<Integer> results, Integer stadiumId) {

        this.selectPositionId = selectPositionId;
        this.count = count;
        this.amountForPay = amountForPay;
        this.amountOneTicket = amountOneTicket;
        this.ticketIdList = results;
        this.stadiumId = stadiumId;

    }

    public void setInfoViewers(List<InfoViewer> infoViewers) {
        this.infoViewers = infoViewers;

    }

    public void setUrlFromWebFragment(String url) {

        this.url = url;

    }

    public void showError(String message) {
        showToast(this, message, R.color.red);
    }

    public void openWebPayment(String url) {
//        Utility.openUrlCustomTab(getActivity(), url);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivityForResult(intent, 100);
    }


    @Override
    public void showPaymentParentLoading() {

    }

    @Override
    public void hidePaymentParentLoading() {

    }

    @Override
    public void onPaymentCancelAndBack() {

    }

    @Override
    public void startAddCardActivity() {

    }

    public CustomViewPager getViewpager() {
        return viewPager;
    }


    @Subscribe
    public void getHeaderContent(HeaderModel headerModel) {
        if (headerModel.getPopularNo() != 0) {
            tvHeaderPopularNo.setText(String.valueOf(headerModel.getPopularNo()));
        }
        tvUserName.setText(TrapConfig.HEADER_USER_NAME);
    }

    @Override
    public void onBackPressed() {
        try {
            onBackClicked();

        } catch (Exception e) {

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void onPayment() {
        ivCountTicket.setImageResource(R.drawable.select_step);
        tvCountTicket.setTextColor(getResources().getColor(R.color.textColorPrimary));

        ivSelectPosition.setImageResource(R.drawable.select_step);
        tvSelectPosition.setTextColor(getResources().getColor(R.color.textColorPrimary));


        vZeroToOne.setBackgroundColor(getResources().getColor(R.color.textColorPrimary));
        vOneToTow.setBackgroundColor(getResources().getColor(R.color.textColorPrimary));
        vTowToThree.setBackgroundColor(getResources().getColor(R.color.textColorPrimary));
        ivFullInfo.setImageResource(R.drawable.select_step_non);
        tvFullInfo.setTextColor(getResources().getColor(R.color.textColorPrimary));

        ivPrintTicket.setImageResource(R.drawable.un_select_step);
        tvPrintTicket.setTextColor(getResources().getColor(R.color._disable_color));

        vZeroToOne.setBackgroundColor(getResources().getColor(R.color.textColorPrimary));
        vOneToTow.setBackgroundColor(getResources().getColor(R.color.textColorPrimary));
        vTowToThree.setBackgroundColor(getResources().getColor(R.color._disable_color));
    }
    public void onBackPayment() {
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
    }


}