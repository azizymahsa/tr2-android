package ir.traap.tractor.android.ui.fragments.matchSchedule;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.pixplicity.easyprefs.library.Prefs;

import br.com.simplepass.loading_button_lib.interfaces.OnAnimationEndListener;
import ir.traap.tractor.android.R;
import ir.traap.tractor.android.ui.adapters.matchSchedule.MatchScheduleAdapter;
import ir.traap.tractor.android.ui.adapters.ticket.PagerAdapter;
import ir.traap.tractor.android.ui.base.BaseFragment;
import ir.traap.tractor.android.ui.fragments.main.MainActionView;
import ir.traap.tractor.android.ui.fragments.ticket.OnClickContinueBuyTicket;
import ir.traap.tractor.android.utilities.CustomViewPager;

/**
 * Created by MahtabAzizi on 11/16/2019.
 */
public class MatchScheduleFragment extends BaseFragment implements OnClickContinueBuyTicket, OnAnimationEndListener, View.OnClickListener
{

    private static MatchScheduleFragment matchScheduleFragment;
    private MainActionView mainView;
    private View rootView;
    private TabLayout tabLayout;
    private CustomViewPager viewPager;
    private TextView tvTitle,tvUserName,tvPopularPlayer;

    private View imgBack,imgMenu;





    public MatchScheduleFragment()
    {

    }


    public static MatchScheduleFragment newInstance(MainActionView mainView)
    {
        matchScheduleFragment = new MatchScheduleFragment();
        matchScheduleFragment.setMainView(mainView);
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


        rootView = inflater.inflate(R.layout.fragment_match_schedule, container, false);
        initView();
        // define TabLayout
        tabLayout.addTab(tabLayout.newTab().setText("بازی های پیش رو"));
        tabLayout.addTab(tabLayout.newTab().setText("بازی های گذشته"));
        tabLayout.addTab(tabLayout.newTab().setText("جدول لیگ برتر"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final MatchScheduleAdapter adapter = new MatchScheduleAdapter
                (getFragmentManager(), tabLayout.getTabCount(),this,mainView);

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


            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setupWithViewPager(viewPager);
        return rootView;
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

            tvTitle.setText("برنامه بازی ها");
        }catch (Exception e){

        }
        tabLayout = rootView.findViewById(R.id.tab_layout);
        viewPager = rootView.findViewById(R.id.pager);


    }

    private void setMainView(MainActionView mainView)
    {
        this.mainView = mainView;
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

    public void showLoading(){
        mainView.showLoading();
    }
    public void hideLoading(){
        mainView.hideLoading();
    }

    @Override
    public void onClick(View v)
    {

    }

    @Override
    public void onAnimationEnd()
    {

    }

    @Override
    public void onBackClicked()
    {

    }

    @Override
    public void onContinueClicked()
    {

    }

    @Override
    public void goBuyTicket()
    {

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
