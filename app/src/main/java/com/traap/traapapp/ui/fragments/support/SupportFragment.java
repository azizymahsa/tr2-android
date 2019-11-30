package com.traap.traapapp.ui.fragments.support;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.pixplicity.easyprefs.library.Prefs;

import com.traap.traapapp.R;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.main.MainActionView;


public class SupportFragment
        extends BaseFragment implements View.OnClickListener
{


    private View view;
    private Toolbar mToolbar;
    private LinearLayout tvPhone;
    private TextView tvTitle, tvUserName,tvPopularPlayer;
    private View imgBack, imgMenu;
    private MainActionView mainView;


    public static SupportFragment newInstance(MainActionView mainView)
    {
        SupportFragment f = new SupportFragment();
        f.setMainView(mainView);
        return f;
    }

    private void setMainView(MainActionView mainView)
    {
        this.mainView = mainView;
    }

    public SupportFragment()
    {
    }


    public static SupportFragment newInstance()
    {
        SupportFragment fragment = new SupportFragment();


        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }


    private void initViews()
    {
        try
        {

            tvPhone = view.findViewById(R.id.tvPhone);

            tvPhone.setOnClickListener(this);
            //toolbar
            mToolbar = view.findViewById(R.id.toolbar);
            tvUserName = mToolbar.findViewById(R.id.tvUserName);
            TextView tvTitle = mToolbar.findViewById(R.id.tvTitle);
            tvTitle.setText("ارتباط با پشتیبانی");
            mToolbar.findViewById(R.id.imgBack).setOnClickListener(v -> mainView.backToMainFragment());

            tvUserName.setText(TrapConfig.HEADER_USER_NAME);

            mToolbar.findViewById(R.id.imgMenu).setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    mainView.openDrawer();
                }
            });

            imgMenu=view.findViewById(R.id.imgMenu);

            imgMenu.setOnClickListener(v -> mainView.openDrawer());
            imgBack = view.findViewById(R.id.imgBack);
            imgBack.setOnClickListener(v ->
            {
                getActivity().onBackPressed();
            });

            tvPopularPlayer = mToolbar.findViewById(R.id.tvPopularPlayer);
            tvPopularPlayer.setText(Prefs.getString("PopularPlayer", ""));
        } catch (Exception e)
        {
            e.getMessage();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_support, container, false);



        // initializing the views
        initViews();


        return view;
    }


    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);

    }

    @Override
    public void onDetach()
    {
        super.onDetach();
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {

            case R.id.tvPhone:

                    Intent intent2 = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", " 041-1546", null));
                    startActivity(intent2);

                break;

        }
    }
}

