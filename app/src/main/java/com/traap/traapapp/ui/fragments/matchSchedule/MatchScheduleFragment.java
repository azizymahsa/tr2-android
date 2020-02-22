package com.traap.traapapp.ui.fragments.matchSchedule;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.material.tabs.TabLayout;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.List;

import br.com.simplepass.loading_button_lib.interfaces.OnAnimationEndListener;
import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.model.matchList.MatchItem;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.enums.MatchScheduleParent;
import com.traap.traapapp.models.otherModels.headerModel.HeaderModel;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.ui.adapters.matchSchedule.MatchScheduleAdapter;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.ui.activities.myProfile.MyProfileActivity;
import com.traap.traapapp.utilities.CustomViewPager;
import com.traap.traapapp.utilities.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by MahtabAzizi on 11/16/2019.
 */
public class MatchScheduleFragment extends BaseFragment implements OnAnimationEndListener, View.OnClickListener
{
    private Context context;

    private static MatchScheduleFragment matchScheduleFragment;
    private MainActionView mainView;
    private View rootView,rlShirt;
    private TabLayout tabLayout;
    private Toolbar mToolbar;
    private CustomViewPager viewPager;
    private TextView tvTitle, tvUserName, tvPopularPlayer, tvLastSchecdule, tvNowSchedule, tvTableLeage;
    List<MatchItem> pastMatchesList,nextMatchesList;
    private View imgBack, imgMenu;
    private ArrayList<MatchItem> matchBuyable;
    private Integer selectedTab;

    private MatchScheduleParent parent;


    public MatchScheduleFragment()
    { }

    @Override
    public void onAttach(@NonNull Context context)
    {
        super.onAttach(context);
        this.context = context;
    }

    public static MatchScheduleFragment newInstance(MainActionView mainView, MatchScheduleParent parent, ArrayList<MatchItem> matchBuyable, Integer selectedTab)
    {
        matchScheduleFragment = new MatchScheduleFragment();
        matchScheduleFragment.setMainView(mainView);
        matchScheduleFragment.setParent(parent);

        Bundle args = new Bundle();
        args.putInt("selectedTab", selectedTab);
        args.putParcelableArrayList("MatchList", matchBuyable);

        matchScheduleFragment.setArguments(args);
        return matchScheduleFragment;
    }

    private void setParent(MatchScheduleParent parent)
    {
        this.parent = parent;
    }

    private void setMainView(MainActionView mainView)
    {
        this.mainView = mainView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        matchBuyable = getArguments().getParcelableArrayList("MatchList");
        selectedTab = getArguments().getInt("selectedTab");
        EventBus.getDefault().register(this);
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
        mToolbar = rootView.findViewById(R.id.toolbar);

        initView();

        sendRequest();


        return rootView;
    }

    private void createTabLayout()
    {
        // define TabLayout
        tabLayout.addTab(tabLayout.newTab().setText("بازی های گذشته"));
        tabLayout.addTab(tabLayout.newTab().setText("بازی های پیش رو"));
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
                  /*  if (result.get(i).getResult() != null)
                    {
                        pastMatchesList.add(result.get(i));
                    } else
                    {
                        nextMatchesList.add(result.get(i));

                    }*/
                  if(result.get(i).getDateTimeNow()>=result.get(i).getMatchDatetime()){
                      pastMatchesList.add(result.get(i));

                  }else{
                      nextMatchesList.add(result.get(i));

                  }
                }
            }
       // mainView.hideLoading();
        createTabLayout();
            if (selectedTab==0)
        onClick(tvTableLeage);

    }

    private void initView()
    {
        try
        {
            mToolbar.findViewById(R.id.rlShirt).setOnClickListener(v -> startActivityForResult(new Intent(SingletonContext.getInstance().getContext(), MyProfileActivity.class),100));

            tvTitle = rootView.findViewById(R.id.tvTitle);
            tvUserName = rootView.findViewById(R.id.tvUserName);
            tvUserName.setText(TrapConfig.HEADER_USER_NAME);
            imgMenu = rootView.findViewById(R.id.imgMenu);

            imgMenu.setOnClickListener(v -> mainView.openDrawer());

            tvPopularPlayer = rootView.findViewById(R.id.tvPopularPlayer);
            tvPopularPlayer.setText(String.valueOf(Prefs.getInt("popularPlayer", 12)));

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
        FrameLayout flLogoToolbar = mToolbar.findViewById(R.id.flLogoToolbar);
        flLogoToolbar.setOnClickListener(v -> {
            mainView.backToMainFragment();

        });


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
                    tvLastSchecdule.setTextColor(context.getResources().getColor(R.color._disable_color));
                    tvTableLeage.setTextColor(context.getResources().getColor(R.color.borderColorRed));
                    tvNowSchedule.setTextColor(context.getResources().getColor(R.color._disable_color));
                    viewPager.setCurrentItem(2, true);

                }, 200);


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

                    viewPager.setCurrentItem(1, true);

                }, 200);


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

                    viewPager.setCurrentItem(0, true);

                }, 200);

                break;
        }
    }

    @Override
    public void onAnimationEnd()
    {

    }

    @Subscribe
    public void getHeaderContent(HeaderModel headerModel)
    {
        if (headerModel.getPopularNo() != 0)
        {
            tvPopularPlayer.setText(String.valueOf(headerModel.getPopularNo()));
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
