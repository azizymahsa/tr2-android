package ir.trap.tractor.android.ui.fragments.ticket;

import android.os.Bundle;
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
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import br.com.simplepass.loading_button_lib.interfaces.OnAnimationEndListener;
import ir.trap.tractor.android.R;
import ir.trap.tractor.android.ui.base.BaseFragment;
import ir.trap.tractor.android.ui.fragments.allMenu.AllMenuFragment;
import ir.trap.tractor.android.ui.fragments.main.MainActionView;
import ir.trap.tractor.android.ui.fragments.ticket.adapter.PagerAdapter;
import ir.trap.tractor.android.utilities.Tools;

public class BuyTickets extends BaseFragment implements OnClickContinueBuyTicket,OnAnimationEndListener, View.OnClickListener
{
    private static BuyTickets f;
    private View rootView;

    private MainActionView mainView;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private LinearLayout llPrintTicket, llFullInfo, llSelectPosition;
    private TextView btnBackToDetail,tvCountTicket,tvSelectPosition,tvFullInfo,tvPrintTicket;
    private CircularProgressButton btnPaymentConfirm;
    private ImageView ivCountTicket,ivSelectPosition,ivFullInfo,ivPrintTicket;
    private View vOneToTow,vZeroToOne,vTowToThree;

    public BuyTickets()
    {

    }


    public static BuyTickets newInstance(MainActionView mainView)
    {
        f = new BuyTickets();
        Bundle args = new Bundle();


        f.setArguments(args);
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
        // View rootView = inflater.inflate(R.layout.fragment_all_menu, container, false);

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
        tabLayout.addTab(tabLayout.newTab().setText("تعداد بلیط"));
        tabLayout.addTab(tabLayout.newTab().setText("انتخاب جایگاه"));
        tabLayout.addTab(tabLayout.newTab().setText("تکمیل اطلاعات"));
        tabLayout.addTab(tabLayout.newTab().setText("صدور بلیط"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //  ViewPager need a PagerAdapter
        final PagerAdapter adapter = new PagerAdapter
                (getFragmentManager(), tabLayout.getTabCount(),this);

        viewPager.setAdapter(adapter);
        viewPager.beginFakeDrag();


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
               /* if(position==0) {
                    backButton.setVisibility(View.INVISIBLE);
                }else  {
                    backButton.setVisibility(View.VISIBLE);
                }
                if(position < viewPager.getAdapter().getCount()-1 ) {
                    nextButton.setVisibility(View.VISIBLE);
                }else  {
                    nextButton.setVisibility(View.INVISIBLE);
                }*/
              /*  Log.i("TAG", "position: " + position);
                switch (position) {
                    case 0:
                        addingMarker(LocationData.find(LocationData.class, "Category = ? ", "1"));
                        break;
                    case 1:
                        addingMarker(LocationData.find(LocationData.class, "Category = ? ", "2"));
                        break;
                    case 2:
                        addingMarker(LocationData.find(LocationData.class, "Category = ? ", "3"));
                        break;
                }*/
            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setupWithViewPager(viewPager);
        return rootView;
    }

    private void initView()
    {
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


            ivFullInfo.setImageResource(R.drawable.select_step_non);
            tvFullInfo.setTextColor(getResources().getColor(R.color.g_btn_gradient_lighter));

            ivPrintTicket.setImageResource(R.drawable.un_select_step);
            tvPrintTicket.setTextColor(getResources().getColor(R.color._disable_color));

            vZeroToOne.setBackgroundColor(getResources().getColor(R.color.g_btn_gradient_lighter));
            vOneToTow.setBackgroundColor(getResources().getColor(R.color.g_btn_gradient_lighter));
            vTowToThree.setBackgroundColor(getResources().getColor(R.color._disable_color));

        }else if(viewPager.getCurrentItem()==3){

            ivCountTicket.setImageResource(R.drawable.select_step);
            tvCountTicket.setTextColor(getResources().getColor(R.color.g_btn_gradient_lighter));

            ivSelectPosition.setImageResource(R.drawable.select_step);
            tvSelectPosition.setTextColor(getResources().getColor(R.color.g_btn_gradient_lighter));


            ivFullInfo.setImageResource(R.drawable.select_step);
            tvFullInfo.setTextColor(getResources().getColor(R.color.g_btn_gradient_lighter));

            ivPrintTicket.setImageResource(R.drawable.select_step_non);
            tvPrintTicket.setTextColor(getResources().getColor(R.color.g_btn_gradient_lighter));

            vZeroToOne.setBackgroundColor(getResources().getColor(R.color.g_btn_gradient_lighter));
            vOneToTow.setBackgroundColor(getResources().getColor(R.color.g_btn_gradient_lighter));
            vTowToThree.setBackgroundColor(getResources().getColor(R.color.g_btn_gradient_lighter));
        }
    }
}
