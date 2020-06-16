package com.traap.traapapp.ui.fragments.events;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.pixplicity.easyprefs.library.Prefs;
import com.traap.traapapp.R;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.ui.activities.myProfile.MyProfileActivity;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.Introducing_the_team.adapter.FragmentsPagerAdapter;
import com.traap.traapapp.ui.fragments.about.AboutFragment;
import com.traap.traapapp.ui.fragments.events.subFragments.MyEventsFragment;
import com.traap.traapapp.ui.fragments.events.subFragments.NextEventFragment;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.utilities.CustomViewPager;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import static com.traap.traapapp.utilities.Utility.changeFontInViewGroup;

/**
 * Authors:
 * Reza Nejati <reza.n.j.t.i@gmail.com>
 * Copyright © 2017
 */
public class EventsFragment extends BaseFragment implements View.OnClickListener
{
    private View view;
    private MainActionView mainView;
    private Toolbar mToolbar;
    private View imgBack, imgMenu, rlShirt;
    private TextView tvUserName, tvPopularPlayer;
    private TabLayout tabLayout;
    private FragmentsPagerAdapter adapter;
    private CustomViewPager viewPager;

    public static EventsFragment newInstance(MainActionView mainView)
    {
        EventsFragment f = new EventsFragment();
        f.setMainView(mainView);
        return f;
    }

    private void setMainView(MainActionView mainView)
    {
        this.mainView = mainView;
    }

    public EventsFragment()
    {
    }


    public static AboutFragment newInstance()
    {
        AboutFragment fragment = new AboutFragment();


        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_events, container, false);
        initViews();
        addTabs(viewPager);
        return view;
    }

    private void initViews()
    {
        mToolbar = view.findViewById(R.id.toolbar);
        viewPager = view.findViewById(R.id.viewPager);
        tvUserName = mToolbar.findViewById(R.id.tvUserName);
        TextView tvTitle = mToolbar.findViewById(R.id.tvTitle);
        tvTitle.setText("رویدادها");
        FrameLayout flLogoToolbar = mToolbar.findViewById(R.id.flLogoToolbar);
        flLogoToolbar.setOnClickListener(v ->
        {
            mainView.backToMainFragment();

        });
        tabLayout = view.findViewById(R.id.tabLayout);

        mToolbar.findViewById(R.id.imgBack).setOnClickListener(v -> mainView.backToMainFragment());

        tvUserName.setText(TrapConfig.HEADER_USER_NAME);

        mToolbar.findViewById(R.id.imgMenu).setOnClickListener(v -> mainView.openDrawer());

        imgBack = view.findViewById(R.id.imgBack);
        imgBack.setOnClickListener(v ->
        {
            getActivity().onBackPressed();
        });

        tvPopularPlayer = mToolbar.findViewById(R.id.tvPopularPlayer);
        tvPopularPlayer.setText(String.valueOf(Prefs.getInt("popularPlayer", 12)));
        rlShirt = mToolbar.findViewById(R.id.rlShirt);
        rlShirt.setOnClickListener(v -> startActivityForResult(new Intent(SingletonContext.getInstance().getContext(), MyProfileActivity.class), 100));
    }

    @Override
    public void onClick(View v)
    {

    }

    private void addTabs(ViewPager viewPager)
    {
        adapter = new FragmentsPagerAdapter(getChildFragmentManager());
        adapter.addFrag("رویدادها من", MyEventsFragment.newInstance(mainView));

        adapter.addFrag("رویدادها پیش رو", NextEventFragment.newInstance(mainView));

        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabTextColors(getResources().getColor(R.color.black), getResources().getColor(R.color.borderColorRed));
        //tabLayout.getTabAt(4).select();

        changeFontInViewGroup(tabLayout, "fonts/iran_sans_normal.ttf");
        viewPager.setCurrentItem(1);
    }

}
