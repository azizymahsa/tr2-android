package com.traap.traapapp.ui.fragments.Introducing_the_team;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.moeidbannerlibrary.banner.BannerLayout;
import com.google.android.material.tabs.TabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.traap.traapapp.R;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.Introducing_the_team.adapter.TeamPhotoAdapter;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.utilities.MyCustomViewPager;
import com.traap.traapapp.utilities.WrappingViewPager;

import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.viewpager.widget.ViewPager;

import static com.traap.traapapp.utilities.Utility.changeFontInViewGroup;

/**
 * Authors:
 * Reza Nejati <reza.n.j.t.i@gmail.com>
 * Copyright © 2017
 */
public class IntroducingTeamFragment  extends BaseFragment
{

    private View rootView;
    private MainActionView mainView;
    private BannerLayout blTeam;
    private TabLayout tabLayout;
    private ViewPager view_pager;
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
        adapter = new FragmentPagerItemAdapter(
                getChildFragmentManager(), FragmentPagerItems.with(getActivity())
                .add("بازیکنان فعلی", TeamHistoryFragment.class)
                .add("بازیکنان فعلی", TeamHistoryFragment.class)
                .add("کادر فنی", TeamHistoryFragment.class)
                .add("برترین بازیکن ها", TeamHistoryFragment.class)
                .add("جایگاه در لیگ ها", PositionInLeaguesFragment.class)

                .add("تاریخچه", TeamHistoryFragment.class)
                .create());
        view_pager.setAdapter(adapter);


        tabLayout.setupWithViewPager(view_pager);
        tabLayout.setTabTextColors(getResources().getColor(R.color.black), getResources().getColor(R.color.borderColorRed));
        tabLayout.getTabAt(5).select();
        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabLayout.setTabTextColors(getResources().getColor(R.color.black), getResources().getColor(R.color.borderColorRed));
                changeFontInViewGroup(tabLayout,"fonts/iran_sans_normal.ttf");
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        changeFontInViewGroup(tabLayout,"fonts/iran_sans_normal.ttf");

    }

    private void initView()
    {
        blTeam=rootView.findViewById(R.id.blTeam);
        tabLayout=rootView.findViewById(R.id.tabLayout);
        view_pager=rootView.findViewById(R.id.view_pager);

        TextView tvTitle = rootView.findViewById(R.id.tvTitle);
        tvTitle.setText("معرفی تیم تراکتور");
        TextView  tvUserName = rootView.findViewById(R.id.tvUserName);
        tvUserName.setText(TrapConfig.HEADER_USER_NAME);
        View imgMenu = rootView.findViewById(R.id.imgMenu);
        imgMenu.setOnClickListener(v -> mainView.openDrawer());
        View imgBack = rootView.findViewById(R.id.imgBack);
        imgBack.setOnClickListener(rootView -> getActivity().onBackPressed());
        blTeam.setAdapter(new TeamPhotoAdapter());
        blTeam.setAutoPlaying(true);
        ViewCompat.setNestedScrollingEnabled(view_pager,false);
    }
}
