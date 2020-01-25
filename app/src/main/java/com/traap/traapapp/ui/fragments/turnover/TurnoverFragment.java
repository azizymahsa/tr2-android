package com.traap.traapapp.ui.fragments.turnover;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.traap.traapapp.R;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.gateWay.WalletTitle;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.utilities.ClearableEditText;
import com.traap.traapapp.utilities.KeyboardUtils;

import org.greenrobot.eventbus.EventBus;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import uk.co.chrisjenx.calligraphy.CalligraphyUtils;

import static com.traap.traapapp.utilities.Utility.changeFontInViewGroup;

/**
 * Authors:
 * Reza Nejati <reza.n.j.t.i@gmail.com>
 * Copyright © 2017
 */
public class TurnoverFragment extends BaseFragment
{


    private View rootView;
    private MainActionView mainView;
    private ViewPager vp;
    private TabLayout tabLayout;
    private LinearLayout btnFilter;
    private ImageView imgSearch;
    private ClearableEditText edtSearchText;


    public static TurnoverFragment newInstance(MainActionView mainView)
    {
        TurnoverFragment f = new TurnoverFragment();
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
        setRetainInstance(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_turn_over, container, false);
        initView();

        return rootView;
    }


    private void initView()
    {
        vp = rootView.findViewById(R.id.vp);
        tabLayout = rootView.findViewById(R.id.tabLayout);
        btnFilter = rootView.findViewById(R.id.btnFilter);
        imgSearch = rootView.findViewById(R.id.imgSearch);
        edtSearchText = rootView.findViewById(R.id.edtSearchText);


        vp.setAdapter(new TurnOverPagerAdapter(getActivity().getSupportFragmentManager(), mainView));
        vp.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(vp);
        changeFontInViewGroup(tabLayout, "fonts/iran_sans_normal.ttf");
        vp.setCurrentItem(2);

        btnFilter.setOnClickListener(v ->
        {
            ClickTurnOverEvent clickTurnOverEvent = new ClickTurnOverEvent();
            clickTurnOverEvent.setFilterClick(true);
            EventBus.getDefault().post(clickTurnOverEvent);
        });



        imgSearch.setOnClickListener(v ->
        {
            KeyboardUtils.forceCloseKeyboard(edtSearchText);

            ClickTurnOverEvent clickTurnOverEvent = new ClickTurnOverEvent();
            clickTurnOverEvent.setSearchClick(true);
            if (TextUtils.isEmpty(edtSearchText.getText().toString()))
            {
                return;
            }
            clickTurnOverEvent.setSearch(edtSearchText.getText().toString());

            EventBus.getDefault().post(clickTurnOverEvent);


        });

        changeTitle();

        edtSearchText.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                if (TextUtils.isEmpty(edtSearchText.getText().toString())){

                    KeyboardUtils.forceCloseKeyboard(edtSearchText);
                    ClickTurnOverEvent clickTurnOverEvent = new ClickTurnOverEvent();
                    clickTurnOverEvent.setSearch("");

                    EventBus.getDefault().post(clickTurnOverEvent);

                }

            }
        });

    }

    private void changeTitle()
    {
        WalletTitle walletTitle = new WalletTitle();
        walletTitle.setTitle("گردش حساب");

        EventBus.getDefault().post(walletTitle);

    }

    @Override
    public void onStop()
    {
        super.onStop();
        WalletTitle walletTitle = new WalletTitle();
        walletTitle.setTitle("کیف پول");

        EventBus.getDefault().post(walletTitle);
    }


}
