package com.traap.traapapp.ui.fragments.predict;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.pixplicity.easyprefs.library.Prefs;
import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.model.lottery.Winner;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.enums.PredictPosition;
import com.traap.traapapp.models.otherModels.headerModel.HeaderModel;
import com.traap.traapapp.models.otherModels.predict.PredictTabModel;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.ui.activities.myProfile.MyProfileActivity;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.ui.fragments.predict.predictResult.PredictResultResultFragment;
import com.traap.traapapp.ui.fragments.predict.predictSystemTeam.PredictSystemTeamFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;


@SuppressLint("ValidFragment")
public class PredictFragment extends BaseFragment implements PredictActionView
{
    private final int PREDICT_RESULT_ID = 0;
    private final int PREDICT_SYSTEM_TEAM_ID = 1;

    private List<PredictTabModel> tabList;

    private View rootView;
    private MainActionView mainView;
    private PredictPosition parent;

    private Toolbar mToolbar;
    private ViewPager viewPager;

    private Context context;
    private Integer matchId;
    private Boolean isPredictable;
    private TextView tvTitle, tvUserName, tvHeaderPopularNo;


    public PredictFragment()
    {

    }

    public static PredictFragment newInstance(MainActionView mainView, PredictPosition parent, Integer matchId, Boolean isPredictable)
    {
        PredictFragment f = new PredictFragment();
        f.setMainView(mainView);
        f.setParent(parent);

        Bundle arg = new Bundle();
        arg.putInt("matchId", matchId);
        arg.putBoolean("isPredictable", isPredictable);

        f.setArguments(arg);

        return f;
    }

    private void setMainView(MainActionView mainView)
    {
        this.mainView = mainView;
    }

    private void setParent(PredictPosition parent)
    {
        this.parent = parent;
    }

    @Override
    public void onAttach(@NonNull Context context)
    {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            matchId = getArguments().getInt("matchId");
            isPredictable = getArguments().getBoolean("isPredictable");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_predict, container, false);

        mToolbar = rootView.findViewById(R.id.toolbar);

        mToolbar.findViewById(R.id.imgMenu).setOnClickListener(v -> mainView.openDrawer());
        mToolbar.findViewById(R.id.imgBack).setOnClickListener(rootView -> mainView.backToMainFragment());
        tvUserName = mToolbar.findViewById(R.id.tvUserName);
        tvTitle = mToolbar.findViewById(R.id.tvTitle);
        tvTitle.setText("پیش بینی نتیجه");
        tvUserName.setText(TrapConfig.HEADER_USER_NAME);tvHeaderPopularNo = mToolbar.findViewById(R.id.tvPopularPlayer);
        tvHeaderPopularNo.setText(String.valueOf(Prefs.getInt("popularPlayer", 12)));

        rootView.findViewById(R.id.rlShirt).setOnClickListener(v ->
                startActivityForResult(new Intent(SingletonContext.getInstance().getContext(),
                        MyProfileActivity.class),100)
        );

        mToolbar.findViewById(R.id.flLogoToolbar).setOnClickListener(v ->
        {
            mainView.backToMainFragment();
        });

//        initView();

        EventBus.getDefault().register(this);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        initView();
    }

    public void initView()
    {
        tabList = new ArrayList<>(2);

 /*       PredictTabModel tabItem = new PredictTabModel(PREDICT_SYSTEM_TEAM_ID, "پیش بینی ترکیب");
       tabList.add(tabItem);*/
        PredictTabModel tabItem = new PredictTabModel(PREDICT_RESULT_ID, "پیش بینی نتیجه");
        tabList.add(tabItem);

        setPager();

    }

    private void setPager()
    {
        viewPager = rootView.findViewById(R.id.viewPager);
        SamplePagerAdapter adapter = new SamplePagerAdapter(getFragmentManager(), this);

        viewPager.setAdapter(adapter);

        TabLayout tabLayout = rootView.findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        for (int i = 0; i < tabLayout.getTabCount(); i++)
        {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(adapter.getTabView(i));
        }

        viewPager.setCurrentItem(tabList.size()-1);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
        {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                tvTitle.setText(tab.getText());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab)
            {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab)
            {

            }
        });
    }

    @Override
    public void backToMainFragment()
    {
        mainView.backToMainFragment();
    }

    @Override
    public void onShowLast5PastMatch(Integer teamLiveScoreId)
    {
        mainView.onShowLast5PastMatch(teamLiveScoreId);
    }

    @Override
    public void onPredictLeagueTable(Integer teamId, Integer matchId, Boolean isPredictable)
    {
        mainView.onPredictLeagueTable(teamId, matchId, isPredictable);
    }

    @Override
    public void onSetPredictCompleted(Integer matchId, Boolean isPredictable, String message)
    {
        mainView.onSetPredictCompleted(matchId, isPredictable, message);
    }

    @Override
    public void onShowDetailWinnerList(List<Winner> winnerList)
    {
        mainView.onShowDetailWinnerList(winnerList);
    }

    @Override
    public void showLoading()
    {
        mainView.showLoading();
    }

    @Override
    public void hideLoading()
    {
        mainView.hideLoading();
    }


    private class SamplePagerAdapter extends FragmentStatePagerAdapter
    {
        private Context context = SingletonContext.getInstance().getContext();
        private PredictActionView actionView;

        @SuppressLint("WrongConstant")
        public SamplePagerAdapter(@NonNull FragmentManager fm, PredictActionView actionView)
        {
            super(fm, 0);
            this.actionView = actionView;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position)
        {
            try
            {
                return tabList.get(position).getTitle();
            }
            catch (NullPointerException e)
            {
                return "";
            }
        }

        @NonNull
        @Override
        public Fragment getItem(int position)
        {
            switch (tabList.get(position).getId())
            {
                case PREDICT_SYSTEM_TEAM_ID:
                {
                    return PredictSystemTeamFragment.newInstance(this.actionView, matchId, isPredictable);
                }
                case PREDICT_RESULT_ID:
                default:
                {
                    return PredictResultResultFragment.newInstance(this.actionView, matchId, isPredictable);
                }
            }
        }

        @Override
        public int getCount()
        {
            return tabList.size();
        }

        public View getTabView(int position)
        {
            // Given you have a custom layout in `res/layout/tab_category_content.xml` with a TextView
            View v = LayoutInflater.from(context).inflate(R.layout.tab_category_content, null);

            Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/iran_sans_normal.ttf");

            TextView tv = v.findViewById(R.id.textView);
            tv.setText(getPageTitle(position));
            tv.setGravity(Gravity.CENTER_HORIZONTAL);
            tv.setTextColor(context.getResources().getColorStateList(R.color.textColorSecondary));
            tv.setTypeface(font);
            return v;
        }
    }


    @Subscribe
    public void getHeaderContent(HeaderModel headerModel)
    {
        if (headerModel.getPopularNo() != 0)
        {
            tvHeaderPopularNo.setText(String.valueOf(headerModel.getPopularNo()));
        }
        tvUserName.setText(TrapConfig.HEADER_USER_NAME);
    }

    @Override
    public void onDestroy()
    {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }


}
