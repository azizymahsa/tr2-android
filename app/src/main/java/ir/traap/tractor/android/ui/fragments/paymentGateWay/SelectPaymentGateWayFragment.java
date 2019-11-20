package ir.traap.tractor.android.ui.fragments.paymentGateWay;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.List;

import br.com.simplepass.loading_button_lib.interfaces.OnAnimationEndListener;
import ir.traap.tractor.android.R;
import ir.traap.tractor.android.apiServices.model.matchList.MatchItem;
import ir.traap.tractor.android.conf.TrapConfig;
import ir.traap.tractor.android.models.otherModels.paymentInstance.SimChargePaymentInstance;
import ir.traap.tractor.android.ui.adapters.paymentGateway.SelectPaymentAdapter;
import ir.traap.tractor.android.ui.base.BaseFragment;
import ir.traap.tractor.android.ui.fragments.main.MainActionView;
import ir.traap.tractor.android.utilities.CustomViewPager;

/**
 * Created by MahsaAzizi on 11/20/2019.
 */
public class SelectPaymentGatewayFragment extends BaseFragment implements OnAnimationEndListener, View.OnClickListener
{

    private static SelectPaymentGatewayFragment matchScheduleFragment;
    private MainActionView mainView;
    private View rootView;
    private TabLayout tabLayout;
    private CustomViewPager viewPager;
    private TextView tvTitle, tvUserName, tvPopularPlayer;
    List<MatchItem> pastMatchesList, nextMatchesList;
    private View imgBack, imgMenu;
    private ArrayList<MatchItem> matchBuyable;

    private  String amount="20000";
    private  String title="پرداخت";
    private  int imageDrawable=1;
    private  String mobile="09029262658";
    private TextView tvWallet,tvCardsShetab,tvGateway;

    public SelectPaymentGatewayFragment()
    {

    }


    public static SelectPaymentGatewayFragment newInstance(MainActionView mainView)
    {
        matchScheduleFragment = new SelectPaymentGatewayFragment();
        return matchScheduleFragment;
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
        if (rootView != null)
        {
            rootView = null;
        }


        rootView = inflater.inflate(R.layout.select_payment_fragment, container, false);
        initView();
        sendRequest();


        return rootView;
    }

    private void createTabLayout()
    {
        // define TabLayout
        tabLayout.addTab(tabLayout.newTab().setText("درگاه بانکی"));
        tabLayout.addTab(tabLayout.newTab().setText("کارت"));
        tabLayout.addTab(tabLayout.newTab().setText("کیف پول"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        SimChargePaymentInstance paymentInstance = new SimChargePaymentInstance();
        paymentInstance.setPAYMENT_STATUS(TrapConfig.PAYMENT_STAUS_ChargeSimCard);
        paymentInstance.setOperatorType(12);
        paymentInstance.setSimcardType(12);
        paymentInstance.setTypeCharge(Integer.valueOf(1));

        final SelectPaymentAdapter adapter = new SelectPaymentAdapter
                (getFragmentManager(), tabLayout.getTabCount(), mainView, amount,title,imageDrawable,mobile,paymentInstance);

        viewPager.setAdapter(adapter);
        //viewPager.beginFakeDrag();
        viewPager.setPagingEnabled(false);


        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setupWithViewPager(viewPager);
    }

    private void sendRequest()
    {
        // mainView.showLoading();
       /* ArrayList<MatchItem> result = matchBuyable;
        // if (response.info.statusCode == 200)
        if (result.size() > 0)
        {
            pastMatchesList = new ArrayList<>();
            nextMatchesList = new ArrayList<>();
            for (int i = 0; i < result.size(); i++)
            {
                if (result.get(i).getResult() != null)
                {
                    pastMatchesList.add(result.get(i));
                } else
                {
                    nextMatchesList.add(result.get(i));

                }
            }
        }*/
        // mainView.hideLoading();
        createTabLayout();
    }

    private void initView()
    {

        try
        {
            tvTitle = rootView.findViewById(R.id.tvTitle);
            tvUserName = rootView.findViewById(R.id.tvUserName);
            tvUserName.setText(Prefs.getString("mobile", ""));
            imgMenu = rootView.findViewById(R.id.imgMenu);

            imgMenu.setOnClickListener(v -> mainView.openDrawer());

            tvPopularPlayer = rootView.findViewById(R.id.tvPopularPlayer);
            tvPopularPlayer.setText(Prefs.getString("PopularPlayer", ""));

            imgBack = rootView.findViewById(R.id.imgBack);
            imgBack.setOnClickListener(v ->
            {
                getActivity().onBackPressed();
            });

            tvTitle.setText("پرداخت");
        } catch (Exception e)
        {

        }
        tabLayout = rootView.findViewById(R.id.tab_layout);
        viewPager = rootView.findViewById(R.id.pager);

        tvWallet = rootView.findViewById(R.id.tvWallet);
        tvCardsShetab = rootView.findViewById(R.id.tvCardsShetab);
        tvGateway = rootView.findViewById(R.id.tvGateway);
        tvWallet.setOnClickListener(this);
        tvCardsShetab.setOnClickListener(this);
        tvGateway.setOnClickListener(this);


    }

    private void setMainView(MainActionView mainView, ArrayList<MatchItem> matchBuyable)
    {
        this.mainView = mainView;
        this.matchBuyable = matchBuyable;
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
                viewPager.setCurrentItem(2, true);
                tvGateway.setBackgroundResource(R.drawable.background_border_a);
                tvWallet.setBackgroundColor(Color.TRANSPARENT);
                tvCardsShetab.setBackgroundColor(Color.TRANSPARENT);
                tvWallet.setTextColor(getResources().getColor(R.color._disable_color));
                tvGateway.setTextColor(getResources().getColor(R.color.borderColorRed));
                tvCardsShetab.setTextColor(getResources().getColor(R.color._disable_color));

                break;
            case R.id.tvWallet:
                viewPager.setCurrentItem(0, true);
                tvWallet.setBackgroundResource(R.drawable.background_border_a);
                tvCardsShetab.setBackgroundColor(Color.TRANSPARENT);
                tvGateway.setBackgroundColor(Color.TRANSPARENT);
                tvWallet.setTextColor(getResources().getColor(R.color.borderColorRed));
                tvCardsShetab.setTextColor(getResources().getColor(R.color._disable_color));
                tvGateway.setTextColor(getResources().getColor(R.color._disable_color));

                break;
            case R.id.tvCardsShetab:
                viewPager.setCurrentItem(1, true);
                tvCardsShetab.setBackgroundResource(R.drawable.background_border_a);
                tvWallet.setBackgroundColor(Color.TRANSPARENT);
                tvGateway.setBackgroundColor(Color.TRANSPARENT);

                tvGateway.setTextColor(getResources().getColor(R.color._disable_color));
                tvWallet.setTextColor(getResources().getColor(R.color._disable_color));
                tvCardsShetab.setTextColor(getResources().getColor(R.color.borderColorRed));
                break;
        }
    }

    @Override
    public void onAnimationEnd()
    {

    }


}
