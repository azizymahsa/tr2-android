package ir.traap.tractor.android.ui.fragments.matchSchedule;

import android.graphics.Color;
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

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.material.tabs.TabLayout;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.List;

import br.com.simplepass.loading_button_lib.interfaces.OnAnimationEndListener;
import ir.traap.tractor.android.R;
import ir.traap.tractor.android.apiServices.generator.SingletonService;
import ir.traap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.traap.tractor.android.apiServices.model.WebServiceClass;
import ir.traap.tractor.android.apiServices.model.matchList.MachListResponse;
import ir.traap.tractor.android.apiServices.model.matchList.MatchItem;
import ir.traap.tractor.android.ui.adapters.matchSchedule.MatchScheduleAdapter;
import ir.traap.tractor.android.ui.adapters.ticket.PagerAdapter;
import ir.traap.tractor.android.ui.base.BaseFragment;
import ir.traap.tractor.android.ui.fragments.main.MainActionView;
import ir.traap.tractor.android.ui.fragments.ticket.BuyTicketsFragment;
import ir.traap.tractor.android.ui.fragments.ticket.OnClickContinueBuyTicket;
import ir.traap.tractor.android.utilities.CustomViewPager;
import ir.traap.tractor.android.utilities.Logger;

/**
 * Created by MahtabAzizi on 11/16/2019.
 */
public class MatchScheduleFragment extends BaseFragment implements OnAnimationEndListener, View.OnClickListener
{

    private static MatchScheduleFragment matchScheduleFragment;
    private MainActionView mainView;
    private View rootView;
    private TabLayout tabLayout;
    private CustomViewPager viewPager;
    private TextView tvTitle, tvUserName, tvPopularPlayer, tvLastSchecdule, tvNowSchedule, tvTableLeage;
    List<MatchItem> pastMatchesList,nextMatchesList;
    private View imgBack, imgMenu;
    private ArrayList<MatchItem> matchBuyable;


    public MatchScheduleFragment()
    {

    }


    public static MatchScheduleFragment newInstance(MainActionView mainView, ArrayList<MatchItem> matchBuyable)
    {
        matchScheduleFragment = new MatchScheduleFragment();
        matchScheduleFragment.setMainView(mainView,matchBuyable);
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
        sendRequest();


        return rootView;
    }

    private void createTabLayout()
    {
        // define TabLayout
        tabLayout.addTab(tabLayout.newTab().setText("بازی های پیش رو"));
        tabLayout.addTab(tabLayout.newTab().setText("بازی های گذشته"));
        tabLayout.addTab(tabLayout.newTab().setText("جدول لیگ برتر"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final MatchScheduleAdapter adapter = new MatchScheduleAdapter
                (getFragmentManager(), tabLayout.getTabCount(), mainView,nextMatchesList,pastMatchesList);

        viewPager.setAdapter(adapter);
        //viewPager.beginFakeDrag();
        viewPager.setPagingEnabled(false);


   /*     viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
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
        });*/
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setupWithViewPager(viewPager);
    }

    private void sendRequest()
    {
       // mainView.showLoading();
        ArrayList<MatchItem> result = matchBuyable;
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
            }
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

            tvTitle.setText("برنامه بازی ها");
        } catch (Exception e)
        {
            Logger.d("--Exception--",e.getMessage());
        }
        tabLayout = rootView.findViewById(R.id.tab_layout);
        viewPager = rootView.findViewById(R.id.pager);

        tvLastSchecdule = rootView.findViewById(R.id.tvLastSchecdule);
        tvNowSchedule = rootView.findViewById(R.id.tvNowSchedule);
        tvTableLeage = rootView.findViewById(R.id.tvTableLeage);
        tvLastSchecdule.setOnClickListener(this);
        tvNowSchedule.setOnClickListener(this);
        tvTableLeage.setOnClickListener(this);


    }

    private void setMainView(MainActionView mainView,ArrayList<MatchItem> matchBuyable)
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
            case R.id.tvTableLeage:
                new Handler().postDelayed(() ->
                {
                    tvTableLeage.setVisibility(View.VISIBLE);
                    YoYo.with(Techniques.FadeIn)
                            .duration(500)
                            .playOn(tvTableLeage);

                    tvTableLeage.setBackgroundResource(R.drawable.background_border_a);
                    tvLastSchecdule.setBackgroundColor(Color.TRANSPARENT);
                    tvNowSchedule.setBackgroundColor(Color.TRANSPARENT);
                    tvLastSchecdule.setTextColor(getResources().getColor(R.color._disable_color));
                    tvTableLeage.setTextColor(getResources().getColor(R.color.borderColorRed));
                    tvNowSchedule.setTextColor(getResources().getColor(R.color._disable_color));
                    viewPager.setCurrentItem(2, true);

                }, 1000);


                break;
            case R.id.tvLastSchecdule:
                new Handler().postDelayed(() ->
                {
                    tvLastSchecdule.setVisibility(View.VISIBLE);
                    YoYo.with(Techniques.FadeIn)
                            .duration(500)
                            .playOn(tvLastSchecdule);
                    tvLastSchecdule.setBackgroundResource(R.drawable.background_border_a);
                    tvTableLeage.setBackgroundColor(Color.TRANSPARENT);
                    tvNowSchedule.setBackgroundColor(Color.TRANSPARENT);
                    tvLastSchecdule.setTextColor(getResources().getColor(R.color.borderColorRed));
                    tvTableLeage.setTextColor(getResources().getColor(R.color._disable_color));
                    tvNowSchedule.setTextColor(getResources().getColor(R.color._disable_color));

                    viewPager.setCurrentItem(0, true);

                }, 1000);


                break;
            case R.id.tvNowSchedule:
                new Handler().postDelayed(() ->
                {
                    tvNowSchedule.setVisibility(View.VISIBLE);
                    YoYo.with(Techniques.FadeIn)
                            .duration(500)
                            .playOn(tvNowSchedule);

                    tvNowSchedule.setBackgroundResource(R.drawable.background_border_a);
                    tvLastSchecdule.setBackgroundColor(Color.TRANSPARENT);
                    tvTableLeage.setBackgroundColor(Color.TRANSPARENT);

                    tvLastSchecdule.setTextColor(getResources().getColor(R.color._disable_color));
                    tvTableLeage.setTextColor(getResources().getColor(R.color._disable_color));
                    tvNowSchedule.setTextColor(getResources().getColor(R.color.borderColorRed));

                    viewPager.setCurrentItem(1, true);

                }, 1000);

                break;
        }
    }

    @Override
    public void onAnimationEnd()
    {

    }


}
