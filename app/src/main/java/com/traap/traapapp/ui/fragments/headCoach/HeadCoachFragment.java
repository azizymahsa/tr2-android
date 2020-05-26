package com.traap.traapapp.ui.fragments.headCoach;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.pixplicity.easyprefs.library.Prefs;
import com.traap.traapapp.R;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.models.otherModels.headerModel.HeaderModel;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.ui.activities.myProfile.MyProfileActivity;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.Introducing_the_team.PositionInLeaguesFragment;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.utilities.WrapContentHeightViewPager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import static com.traap.traapapp.utilities.Utility.changeFontInViewGroup;


/**
 * Created by MahtabAzizi on 5/26/2020.
 */
public class HeadCoachFragment extends BaseFragment implements View.OnClickListener
{
    private View view;
    private Toolbar mToolbar;
    private View  rlShirt, imgMenu, imgBack;
    private MainActionView mainView;
    private TextView tvUserName, tvPopularPlayer;
    private TabLayout tabLayout;
    private WrapContentHeightViewPager view_pager;



    public static HeadCoachFragment newInstance(MainActionView mainView)
    {
        HeadCoachFragment f = new HeadCoachFragment();
        f.setMainView(mainView);
        return f;
    }

    private void setMainView(MainActionView mainView)
    {
        this.mainView = mainView;
    }

    public HeadCoachFragment()
    {
    }


    public static HeadCoachFragment newInstance()
    {
        HeadCoachFragment fragment = new HeadCoachFragment();


        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }


    private void initViews()
    {
        try
        {
          //  showLoading();

            //toolbar
            mToolbar = view.findViewById(R.id.toolbar);
            tvUserName = mToolbar.findViewById(R.id.tvUserName);
            TextView tvTitle = mToolbar.findViewById(R.id.tvTitle);
            tvTitle.setText("معرفی سرمربی");
            mToolbar.findViewById(R.id.imgBack).setOnClickListener(v -> mainView.backToMainFragment());

            tvUserName.setText(TrapConfig.HEADER_USER_NAME);
            rlShirt = mToolbar.findViewById(R.id.rlShirt);
            rlShirt.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    startActivityForResult(new Intent(SingletonContext.getInstance().getContext(), MyProfileActivity.class), 100);
                }
            });
            mToolbar.findViewById(R.id.imgMenu).setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    mainView.openDrawer();
                }
            });
            FrameLayout flLogoToolbar = mToolbar.findViewById(R.id.flLogoToolbar);
            flLogoToolbar.setOnClickListener(v ->
            {
                mainView.backToMainFragment();

            });
            imgMenu = view.findViewById(R.id.imgMenu);

            imgMenu.setOnClickListener(v -> mainView.openDrawer());
            imgBack = view.findViewById(R.id.imgBack);
            imgBack.setOnClickListener(v ->
            {
                getActivity().onBackPressed();
            });

            tvPopularPlayer = mToolbar.findViewById(R.id.tvPopularPlayer);
            tvPopularPlayer.setText(String.valueOf(Prefs.getInt("popularPlayer", 12)));


            tabLayout = view.findViewById(R.id.tabLayout);
            view_pager = view.findViewById(R.id.view_pager);

        } catch (Exception e)
        {
            e.getMessage();
        }
    }

    private void hideLoading()
    {
        mainView.hideLoading();
    }


    private void showLoading()
    {
        mainView.showLoading();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_head_coach, container, false);

        initViews();

        initViewPager();
        return view;
    }

    private void initViewPager()
    {
        addTabs(view_pager);
        view_pager.setOffscreenPageLimit(4);

        tabLayout.setupWithViewPager(view_pager);
        tabLayout.setTabTextColors(getResources().getColor(R.color.black), getResources().getColor(R.color.borderColorRed));
        //tabLayout.getTabAt(4).select();
        new Handler().postDelayed(() -> view_pager.setCurrentItem(4, false), 50);

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

            }
        });

    }

    private void addTabs(ViewPager viewPager)
    {
      /*  adapter = new IntroduceFragmentPagerAdapter(getChildFragmentManager());

        adapter.addFrag("بازیکنان فعلی", new CurrentPlayersFragment());
        adapter.addFrag("کادر فنی", new TechnicalTeamFragment());
        adapter.addFrag("برترین بازیکن ها", new TopPlayersFragment());
        adapter.addFrag("جایگاه در لیگ ها", new PositionInLeaguesFragment());
        adapter.addFrag("تاریخچه", new TeamHistoryFragment());

        viewPager.setAdapter(adapter);*/
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);

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

           /* case R.id.btnConfirm:
                break;*/
        }

    }
}
