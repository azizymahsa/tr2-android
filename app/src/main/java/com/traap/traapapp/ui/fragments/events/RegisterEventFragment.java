package com.traap.traapapp.ui.fragments.events;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.pixplicity.easyprefs.library.Prefs;
import com.traap.traapapp.R;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.ui.activities.myProfile.MyProfileActivity;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.about.AboutFragment;
import com.traap.traapapp.ui.fragments.events.adapter.MyEventAdapter;
import com.traap.traapapp.ui.fragments.events.adapter.RegisterEventAdapter;
import com.traap.traapapp.ui.fragments.main.MainActionView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Authors:
 * Reza Nejati <reza.n.j.t.i@gmail.com>
 * Copyright © 2017
 */
public class RegisterEventFragment extends BaseFragment implements View.OnClickListener
{
    private View view;
    private MainActionView mainView;
    private Integer count;
    private Toolbar mToolbar;
    private View imgBack, imgMenu, rlShirt;
    private TextView tvUserName, tvPopularPlayer;
    private RecyclerView rvRegister;


    public static RegisterEventFragment newInstance(MainActionView mainView,Integer count)
    {
        RegisterEventFragment f = new RegisterEventFragment();
        f.setMainView(mainView);
        f.setCount(count);
        return f;
    }

    private void setMainView(MainActionView mainView)
    {
        this.mainView = mainView;
    }

    public void setCount(Integer count)
    {
        this.count = count;
    }

    public RegisterEventFragment()
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
        view = inflater.inflate(R.layout.fragment_register_event, container, false);
        initViews();
        return view;
    }

    private void initViews()
    {
        mToolbar = view.findViewById(R.id.toolbar);
        tvUserName = mToolbar.findViewById(R.id.tvUserName);
        rvRegister = view.findViewById(R.id.rvRegister);
        rvRegister.setAdapter(new RegisterEventAdapter(getActivity(),count));
        TextView tvTitle = mToolbar.findViewById(R.id.tvTitle);
        tvTitle.setText("رویدادها");
        FrameLayout flLogoToolbar = mToolbar.findViewById(R.id.flLogoToolbar);
        flLogoToolbar.setOnClickListener(v ->
        {
            mainView.backToMainFragment();

        });

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
        ViewCompat.setNestedScrollingEnabled(rvRegister,true);

    }

    @Override
    public void onClick(View v)
    {

    }

}



