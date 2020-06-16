package com.traap.traapapp.ui.fragments.events;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pixplicity.easyprefs.library.Prefs;
import com.traap.traapapp.R;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.ui.activities.myProfile.MyProfileActivity;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.about.AboutFragment;
import com.traap.traapapp.ui.fragments.events.adapter.DetailEventAdapter;
import com.traap.traapapp.ui.fragments.main.MainActionView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

/**
 * Authors:
 * Reza Nejati <reza.n.j.t.i@gmail.com>
 * Copyright © 2017
 */
public class MyDetailEventFragment extends BaseFragment implements View.OnClickListener
{
    private View view;
    private MainActionView mainView;
    private Toolbar mToolbar;
    private View imgBack, imgMenu, rlShirt;
    private TextView tvUserName, tvPopularPlayer;


    public static MyDetailEventFragment newInstance(MainActionView mainView)
    {
        MyDetailEventFragment f = new MyDetailEventFragment();
        f.setMainView(mainView);
        return f;
    }

    private void setMainView(MainActionView mainView)
    {
        this.mainView = mainView;
    }

    public MyDetailEventFragment()
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
        view = inflater.inflate(R.layout.fragment_my_detail_event, container, false);
        initViews();
        return view;
    }

    private void initViews()
    {
        mToolbar = view.findViewById(R.id.toolbar);
        tvUserName = mToolbar.findViewById(R.id.tvUserName);

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

    }

    @Override
    public void onClick(View v)
    {

    }

}


