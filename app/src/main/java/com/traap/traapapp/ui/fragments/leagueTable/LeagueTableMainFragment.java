package com.traap.traapapp.ui.fragments.leagueTable;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.pixplicity.easyprefs.library.Prefs;
import com.traap.traapapp.R;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.enums.LeagueTableParent;
import com.traap.traapapp.enums.SubMediaParent;
import com.traap.traapapp.models.otherModels.headerModel.HeaderModel;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.ui.activities.myProfile.MyProfileActivity;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.ui.fragments.news.NewsActionView;
import com.traap.traapapp.ui.fragments.news.NewsMainActionView;
import com.traap.traapapp.ui.fragments.news.mainNews.NewsMainContentFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


@SuppressLint("newsMainFragment")
public class LeagueTableMainFragment extends BaseFragment implements LeagueTableActionView
{
    private View rootView;
    private MainActionView mainView;
    private Integer matchId, teamId;
    private Boolean isPredictable;

    private Toolbar mToolbar;
    private TextView tvUserName, tvHeaderPopularNo;
    private Fragment fragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    public LeagueTableMainFragment()
    {

    }

    public static LeagueTableMainFragment newInstance(MainActionView mainView, Integer teamId, Integer matchId, Boolean isPredictable)
    {
        LeagueTableMainFragment f = new LeagueTableMainFragment();
        Bundle args = new Bundle();
        args.putInt("matchId", matchId);
        args.putInt("teamId", teamId);
        args.putBoolean("isPredictable", isPredictable);
        f.setArguments(args);
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

        if (getArguments() != null)
        {
            teamId = getArguments().getInt("teamId");
            matchId = getArguments().getInt("matchId");
            isPredictable = getArguments().getBoolean("isPredictable");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_league_table_main, container, false);

        mToolbar = rootView.findViewById(R.id.toolbar);

        mToolbar.findViewById(R.id.imgMenu).setOnClickListener(v -> mainView.openDrawer());
        mToolbar.findViewById(R.id.imgBack).setOnClickListener(rootView ->
        {
            mainView.onPredict(matchId, isPredictable);
        });
        tvUserName = mToolbar.findViewById(R.id.tvUserName);
        tvHeaderPopularNo = mToolbar.findViewById(R.id.tvPopularPlayer);
        tvHeaderPopularNo.setText(String.valueOf(Prefs.getInt("popularPlayer", 12)));
        TextView tvTitle = mToolbar.findViewById(R.id.tvTitle);
        tvTitle.setText("جدول لیگ برتر");

        FrameLayout flLogoToolbar = mToolbar.findViewById(R.id.flLogoToolbar);
        flLogoToolbar.setOnClickListener(v ->
        {
            mainView.backToMainFragment();
        });

        rootView.findViewById(R.id.rlShirt).setOnClickListener(v -> startActivityForResult(new Intent(SingletonContext.getInstance().getContext(),
                MyProfileActivity.class), 100));
        tvUserName.setText(TrapConfig.HEADER_USER_NAME);

        fragmentManager = getChildFragmentManager();

        fragment = LeagueTableFragment.newInstance(LeagueTableParent.PredictFragment, String.valueOf(matchId), isPredictable, teamId, this);
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, fragment, "leagueTableFragment")
                .commit();


        EventBus.getDefault().register(this);
        return rootView;
    }


    @Override
    public void backToPredictFragment(Integer matchId)
    {
    }

    @Override
    public void openPastResultFragment(LeagueTableParent parent, String matchId, Boolean isPredictable, String teamId, String imageLogo, String logoTitle)
    {
        mainView.openPastResultFragment(parent, String.valueOf(matchId), isPredictable, teamId, imageLogo, logoTitle);
    }

    @Override
    public void onMatchResultFragment()
    {
//        mainView.onNewsArchiveFragment(parent);
    }

    @Override
    public void openDrawerLeagueTable()
    {
        mainView.openDrawer();
    }

    @Override
    public void closeDrawerLeagueTable()
    {
        mainView.closeDrawer();
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
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
