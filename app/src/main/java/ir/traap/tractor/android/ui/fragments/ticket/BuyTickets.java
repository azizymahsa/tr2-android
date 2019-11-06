package ir.traap.tractor.android.ui.fragments.ticket;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.List;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import br.com.simplepass.loading_button_lib.interfaces.OnAnimationEndListener;
import ir.traap.tractor.android.R;
import ir.traap.tractor.android.apiServices.model.buyTicket.InfoViewer;
import ir.traap.tractor.android.apiServices.model.matchList.MatchItem;
import ir.traap.tractor.android.ui.activities.main.MainActivity;
import ir.traap.tractor.android.ui.adapters.ticket.PagerAdapter;
import ir.traap.tractor.android.ui.base.BaseFragment;
import ir.traap.tractor.android.ui.fragments.main.MainActionView;
import ir.traap.tractor.android.ui.adapters.ticket.PagerAdapter;
import ir.traap.tractor.android.utilities.CustomViewPager;
import ir.traap.tractor.android.utilities.Utility;

public class BuyTickets extends BaseFragment implements OnClickContinueBuyTicket,OnAnimationEndListener, View.OnClickListener
{
    public static BuyTickets buyTickets;
    private static boolean paymentIsComplete=false;
    private View rootView;

    private MainActionView mainView;
    private TabLayout tabLayout;
    private CustomViewPager viewPager;
    private LinearLayout llPrintTicket, llFullInfo, llSelectPosition;
    private TextView btnBackToDetail,tvCountTicket,tvSelectPosition,tvFullInfo,tvPrintTicket;
    private CircularProgressButton btnPaymentConfirm;
    private ImageView ivCountTicket,ivSelectPosition,ivFullInfo,ivPrintTicket;
    private View vOneToTow,vZeroToOne,vTowToThree;
    private TextView tvTitle,tvUserName;
    public String namePosition;
    Integer selectPositionId,count,amountForPay;
    private MatchItem matchBuyable;
    private List<InfoViewer> infoViewers;
    private List<Integer> ticketIdList;
    private View imgBack,imgMenu;
    private TextView tvPopularPlayer;
    private String url="";


    public BuyTickets()
    {

    }


    public static BuyTickets newInstance(MainActionView mainView, MatchItem matchBuyable)
    {
        buyTickets = new BuyTickets();
        Bundle args = new Bundle();
        args.putParcelable("matchBuyable", matchBuyable);

        buyTickets.setArguments(args);
        buyTickets.setMainView(mainView);
        return buyTickets;
    }

    public static BuyTickets newInstance(MainActionView mainView, MatchItem matchBuyable, String refrenceNumber)
    {

        buyTickets = new BuyTickets();
        Bundle args = new Bundle();
        if (matchBuyable!=null)
        args.putParcelable("matchBuyable", matchBuyable);

        buyTickets.setArguments(args);
        buyTickets.setMainView(mainView);
        paymentIsComplete = true;
        return buyTickets;
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
                (getFragmentManager(), tabLayout.getTabCount(),this,mainView,this,matchBuyable);

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


                if (position == 1) {

                    new Handler().postDelayed(() -> adapter.compeletInfoFragmentData(selectPositionId, count, amountForPay,ticketIdList), 200);
                    //setDataToCompleteInfoFragment(namePosition);
                }
                if (position == 2)
                    adapter.createShareShowTicket();
            /*    if (position ==2){

                    new Handler().postDelayed(() -> adapter.webFragmentData(url), 200);
                }*/
             /*   if (position == 2) {

                    new Handler().postDelayed(() -> adapter.paymentFragmentData(infoViewers), 200);
                }
                if (position == 3)
                    adapter.createShareShowTicket();*/

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setupWithViewPager(viewPager);
        if (paymentIsComplete){

             //   viewPager.setCurrentItem(3, true);

        }
        return rootView;
    }

    public void setDataToCompleteInfoFragment(String name)
    {
        this.namePosition=name;
    }
    private void initView()
    {
        try
        {
            tvTitle=rootView.findViewById(R.id.tvTitle);
            tvUserName=rootView.findViewById(R.id.tvUserName);
            tvUserName.setText(Prefs.getString("mobile", ""));
            imgMenu=rootView.findViewById(R.id.imgMenu);

            imgMenu.setOnClickListener(v -> mainView.openDrawer());

            tvPopularPlayer=rootView.findViewById(R.id.tvPopularPlayer);
            tvPopularPlayer.setText(Prefs.getString("PopularPlayer",""));

            imgBack=rootView.findViewById(R.id.imgBack);
            imgBack.setOnClickListener(v ->
            {
                getActivity().onBackPressed();
            });

            tvTitle.setText("خرید بلیت");
        }catch (Exception e){

        }

        if (paymentIsComplete){

         //   viewPager.setCurrentItem(3, true);

        }
        btnPaymentConfirm = rootView.findViewById(R.id.btnPaymentConfirm);
        btnBackToDetail = rootView.findViewById(R.id.btnBackToDetail);
        tabLayout = rootView.findViewById(R.id.tab_layout);
        viewPager = rootView.findViewById(R.id.pager);
        llPrintTicket = rootView.findViewById(R.id.llPrintTicket);
        llFullInfo = rootView.findViewById(R.id.llFullInfo);
        ivCountTicket=rootView.findViewById(R.id.ivCountTicket);
        tvCountTicket=rootView.findViewById(R.id.tvCountTicket);
        ivSelectPosition=rootView.findViewById(R.id.ivSelectPosition);
        tvSelectPosition=rootView.findViewById(R.id.tvSelectPosition);
        ivFullInfo=rootView.findViewById(R.id.ivFullInfo);
        tvFullInfo=rootView.findViewById(R.id.tvFullInfo);
        ivPrintTicket=rootView.findViewById(R.id.ivPrintTicket);
        tvPrintTicket=rootView.findViewById(R.id.tvPrintTicket);
        vZeroToOne=rootView.findViewById(R.id.vZeroToOne);
        vOneToTow=rootView.findViewById(R.id.vOneToTow);
        vTowToThree=rootView.findViewById(R.id.vTowToThree);

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
        if (viewPager.getCurrentItem()==0){
            ivCountTicket.setImageResource(R.drawable.select_step_non);
            tvCountTicket.setTextColor(getResources().getColor(R.color.g_btn_gradient_lighter));

            ivSelectPosition.setImageResource(R.drawable.un_select_step);
            tvSelectPosition.setTextColor(getResources().getColor(R.color._disable_color));

            ivFullInfo.setImageResource(R.drawable.un_select_step);
            tvFullInfo.setTextColor(getResources().getColor(R.color._disable_color));

            ivPrintTicket.setImageResource(R.drawable.un_select_step);
            tvPrintTicket.setTextColor(getResources().getColor(R.color._disable_color));

            vZeroToOne.setBackgroundColor(getResources().getColor(R.color._disable_color));
            vOneToTow.setBackgroundColor(getResources().getColor(R.color._disable_color));
            vTowToThree.setBackgroundColor(getResources().getColor(R.color._disable_color));

        }else if (viewPager.getCurrentItem()==1){
            ivCountTicket.setImageResource(R.drawable.select_step);
            tvCountTicket.setTextColor(getResources().getColor(R.color.g_btn_gradient_lighter));

            ivSelectPosition.setImageResource(R.drawable.select_step_non);
            tvSelectPosition.setTextColor(getResources().getColor(R.color.g_btn_gradient_lighter));

            ivFullInfo.setImageResource(R.drawable.un_select_step);
            tvFullInfo.setTextColor(getResources().getColor(R.color._disable_color));

            ivPrintTicket.setImageResource(R.drawable.un_select_step);
            tvPrintTicket.setTextColor(getResources().getColor(R.color._disable_color));

            vZeroToOne.setBackgroundColor(getResources().getColor(R.color.g_btn_gradient_lighter));
            vOneToTow.setBackgroundColor(getResources().getColor(R.color._disable_color));
            vTowToThree.setBackgroundColor(getResources().getColor(R.color._disable_color));

        }else if (viewPager.getCurrentItem()==2){
            ivCountTicket.setImageResource(R.drawable.select_step);
            tvCountTicket.setTextColor(getResources().getColor(R.color.g_btn_gradient_lighter));

            ivSelectPosition.setImageResource(R.drawable.select_step);
            tvSelectPosition.setTextColor(getResources().getColor(R.color.g_btn_gradient_lighter));

            ivFullInfo.setImageResource(R.drawable.select_step);
            tvFullInfo.setTextColor(getResources().getColor(R.color.g_btn_gradient_lighter));

            ivPrintTicket.setImageResource(R.drawable.select_step);
            tvPrintTicket.setTextColor(getResources().getColor(R.color.g_btn_gradient_lighter));

            vZeroToOne.setBackgroundColor(getResources().getColor(R.color.g_btn_gradient_lighter));
            vOneToTow.setBackgroundColor(getResources().getColor(R.color.g_btn_gradient_lighter));
            vTowToThree.setBackgroundColor(getResources().getColor(R.color.g_btn_gradient_lighter));
          /*  ivFullInfo.setImageResource(R.drawable.select_step_non);
            tvFullInfo.setTextColor(getResources().getColor(R.color.g_btn_gradient_lighter));

            ivPrintTicket.setImageResource(R.drawable.un_select_step);
            tvPrintTicket.setTextColor(getResources().getColor(R.color._disable_color));

            vZeroToOne.setBackgroundColor(getResources().getColor(R.color.g_btn_gradient_lighter));
            vOneToTow.setBackgroundColor(getResources().getColor(R.color.g_btn_gradient_lighter));
            vTowToThree.setBackgroundColor(getResources().getColor(R.color._disable_color));
*/
        }else if(viewPager.getCurrentItem()==3){

            ivCountTicket.setImageResource(R.drawable.select_step);
            tvCountTicket.setTextColor(getResources().getColor(R.color.g_btn_gradient_lighter));

            ivSelectPosition.setImageResource(R.drawable.select_step);
            tvSelectPosition.setTextColor(getResources().getColor(R.color.g_btn_gradient_lighter));


            ivFullInfo.setImageResource(R.drawable.select_step);
            tvFullInfo.setTextColor(getResources().getColor(R.color.g_btn_gradient_lighter));

            ivPrintTicket.setImageResource(R.drawable.select_step);
            tvPrintTicket.setTextColor(getResources().getColor(R.color.g_btn_gradient_lighter));

            vZeroToOne.setBackgroundColor(getResources().getColor(R.color.g_btn_gradient_lighter));
            vOneToTow.setBackgroundColor(getResources().getColor(R.color.g_btn_gradient_lighter));
            vTowToThree.setBackgroundColor(getResources().getColor(R.color.g_btn_gradient_lighter));
        }
    }
    public void setData(Integer selectPositionId, int count, int amountForPay, List<Integer> results) {

        this.selectPositionId = selectPositionId;
        this.count = count;
        this.amountForPay = amountForPay;
        this.ticketIdList=results;

    }

    public void showLoading(){
        mainView.showLoading();
    }
    public void hideLoading(){
        mainView.hideLoading();
    }

    public void setInfoViewers(List<InfoViewer> infoViewers) {
        this.infoViewers=infoViewers;

    }
    public void setUrlFromWebFragment(String url){

        this.url=url;

    }
    public void openWebPayment(String url){
        Utility.openUrlCustomTab(getActivity(), url);

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
}