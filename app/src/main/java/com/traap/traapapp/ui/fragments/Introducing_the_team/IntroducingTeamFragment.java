package com.traap.traapapp.ui.fragments.Introducing_the_team;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.moeidbannerlibrary.banner.BannerLayout;
import com.google.android.material.tabs.TabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.traap.traapapp.R;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.Introducing_the_team.adapter.IntroduceFragmentPagerAdapter;
import com.traap.traapapp.ui.fragments.Introducing_the_team.adapter.TeamPhotoAdapter;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.utilities.MyCustomViewPager;
import com.traap.traapapp.utilities.WrapContentHeightViewPager;
import com.traap.traapapp.utilities.WrappingViewPager;

import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import static com.traap.traapapp.utilities.Utility.changeFontInViewGroup;

/**
 * Authors:
 * Reza Nejati <reza.n.j.t.i@gmail.com>
 * Copyright © 2017
 */
public class IntroducingTeamFragment extends BaseFragment
{

    private View rootView;
    private MainActionView mainView;
    private BannerLayout blTeam;
    private TabLayout tabLayout;
    private MyCustomViewPager view_pager;
    private FragmentPagerItemAdapter adapter;

    public IntroducingTeamFragment()
    {
    }

    public static IntroducingTeamFragment newInstance(MainActionView mainView)
    {
        IntroducingTeamFragment f = new IntroducingTeamFragment();
        f.setMainView(mainView);
        return f;
    }

    private void setMainView(MainActionView mainView)
    {
        this.mainView = mainView;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        if (rootView != null)
        {
            return rootView;

        }
        rootView = inflater.inflate(R.layout.introducing_team_fragment, container, false);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        initView();
        initViewPager();
    }

    private void initViewPager()
    {
        addTabs(view_pager);
        view_pager.setOffscreenPageLimit(4);
/*        Fragment childFragment = new TeamHistoryFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.child_fragment_container, childFragment).commit();*/

        tabLayout.setupWithViewPager(view_pager);
        tabLayout.setTabTextColors(getResources().getColor(R.color.black), getResources().getColor(R.color.borderColorRed));
        //tabLayout.getTabAt(4).select();
        view_pager.setCurrentItem(4);

        changeFontInViewGroup(tabLayout, "fonts/iran_sans_normal.ttf");
        view_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            public void onPageScrollStateChanged(int state)
            {
            }

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {
            }

            public void onPageSelected(int position)
            {
                if (position == 4)
                {
                    setViewPagerHeight(550);

                }
                if (position==3){
                    setViewPagerHeight(1750);

                }
            }
        });
    }

    private void initView()
    {
        blTeam = rootView.findViewById(R.id.blTeam);
        tabLayout = rootView.findViewById(R.id.tabLayout);
        view_pager = rootView.findViewById(R.id.view_pager);

        TextView tvTitle = rootView.findViewById(R.id.tvTitle);
        tvTitle.setText("معرفی تیم تراکتور");
        TextView tvUserName = rootView.findViewById(R.id.tvUserName);
        tvUserName.setText(TrapConfig.HEADER_USER_NAME);
        View imgMenu = rootView.findViewById(R.id.imgMenu);
        imgMenu.setOnClickListener(v -> mainView.openDrawer());
        View imgBack = rootView.findViewById(R.id.imgBack);
        imgBack.setOnClickListener(rootView -> getActivity().onBackPressed());
        blTeam.setAdapter(new TeamPhotoAdapter());
        blTeam.setAutoPlaying(true);
        //     ViewCompat.setNestedScrollingEnabled(view_pager,false);
    }

    private void addTabs(ViewPager viewPager)
    {
        IntroduceFragmentPagerAdapter adapter = new IntroduceFragmentPagerAdapter(getChildFragmentManager());

        adapter.addFrag("بازیکنان فعلی", new CurrentPlayersFragment());
        adapter.addFrag("کادر فنی", new TechnicalTeamFragment());
        adapter.addFrag("برترین بازیکن ها", new TopPlayersFragment());
        adapter.addFrag("جایگاه در لیگ ها", new PositionInLeaguesFragment());
        adapter.addFrag("تاریخچه", new TeamHistoryFragment());

        viewPager.setAdapter(adapter);
    }

    public void setViewPagerHeight(int height)
    {
        ViewTreeObserver viewTreeObserver = view_pager.getViewTreeObserver();
        viewTreeObserver
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
                {

                    @Override
                    public void onGlobalLayout()
                    {

                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT);

                        int viewPagerWidth = view_pager.getWidth();
                        float viewPagerHeight = (float) (height);

                        layoutParams.width = viewPagerWidth;
                        layoutParams.height = (int) viewPagerHeight;

                        view_pager.setLayoutParams(layoutParams);
                        view_pager.getViewTreeObserver()
                                .removeGlobalOnLayoutListener(this);
                    }
                });
        view_pager.setLayoutParams(new LinearLayout.LayoutParams(ViewPager.LayoutParams.MATCH_PARENT, 550));

    }

}
