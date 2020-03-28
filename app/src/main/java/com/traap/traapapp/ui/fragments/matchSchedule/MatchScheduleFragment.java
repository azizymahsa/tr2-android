package com.traap.traapapp.ui.fragments.matchSchedule;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.google.android.material.tabs.TabLayout;
import com.pixplicity.easyprefs.library.Prefs;
import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.model.matchList.MatchItem;
import com.traap.traapapp.apiServices.model.media.category.TypeCategory;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.enums.LeagueTableParent;
import com.traap.traapapp.enums.MatchScheduleParent;
import com.traap.traapapp.enums.MediaArchiveCategoryCall;
import com.traap.traapapp.models.otherModels.headerModel.HeaderModel;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.singleton.SingletonMatchBuyable;
import com.traap.traapapp.ui.activities.myProfile.MyProfileActivity;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.leagueTable.LeagueTableActionView;
import com.traap.traapapp.ui.fragments.leagueTable.LeagueTableFragment;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.ui.fragments.news.archive.NewsArchiveCategoryFragment;
import com.traap.traapapp.ui.fragments.news.archive.NewsArchiveFragment;
import com.traap.traapapp.utilities.CustomViewPager;
import com.traap.traapapp.utilities.Logger;
import com.traap.traapapp.utilities.Utility;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Javad.Abadi on 11/16/2019.
 */
public class MatchScheduleFragment extends BaseFragment implements LeagueTableActionView
{
    private Context context;

    private static MatchScheduleFragment matchScheduleFragment2;
    private MainActionView mainView;
    private View rootView, rlShirt;
    private TabLayout tabLayout;
    private Toolbar mToolbar;
    private CustomViewPager viewPager;
    private TextView tvTitle, tvUserName, tvPopularPlayer;
    List<MatchItem> pastMatchesList, nextMatchesList;
    private View imgBack, imgMenu;
    private ArrayList<MatchItem> matchBuyable;
    private Integer selectedTab;

    private MatchScheduleParent parent;


    public MatchScheduleFragment()
    {
    }

    @Override
    public void onAttach(@NonNull Context context)
    {
        super.onAttach(context);
        this.context = context;
    }

    public static MatchScheduleFragment newInstance(MainActionView mainView, MatchScheduleParent parent, ArrayList<MatchItem> matchBuyable, Integer selectedTab)
    {
        matchScheduleFragment2 = new MatchScheduleFragment();
        matchScheduleFragment2.setMainView(mainView);
        matchScheduleFragment2.setParent(parent);

        Bundle args = new Bundle();
        args.putInt("selectedTab", selectedTab);
        args.putParcelableArrayList("MatchList", matchBuyable);

        matchScheduleFragment2.setArguments(args);
        return matchScheduleFragment2;
    }

    private void setParent(MatchScheduleParent parent)
    {
        this.parent = parent;
    }

    private void setMainView(MainActionView mainView)
    {
        this.mainView = mainView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        selectedTab = getArguments().getInt("selectedTab", 0);

        matchBuyable = getArguments().getParcelableArrayList("MatchList");
        if (matchBuyable != null)
        {
            SingletonMatchBuyable.getInstance().setMatchBuyable(matchBuyable);
        }
        else
        {
            matchBuyable = SingletonMatchBuyable.getInstance().getMatchBuyable();
        }

        Logger.e("-MatchSchedule-", "size: " + matchBuyable.size() + ", selectedTab: " + selectedTab);

        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        if (rootView != null)
        {
            rootView = null;
        }

        rootView = inflater.inflate(R.layout.fragment_match_schedule, container, false);
        mToolbar = rootView.findViewById(R.id.toolbar);

        initView();

//        sendRequest();

        return rootView;
    }

    private void sendRequest()
    {
        pastMatchesList = new ArrayList<>();
        nextMatchesList = new ArrayList<>();
        for (int i = 0; i < matchBuyable.size(); i++)
        {
            if (matchBuyable.get(i).getDateTimeNow() >= matchBuyable.get(i).getMatchDatetime())
            {
                pastMatchesList.add(matchBuyable.get(i));

            }
            else
            {
                nextMatchesList.add(matchBuyable.get(i));

            }
        }

        Logger.e("-+MatchSchedule+-", "past: " + pastMatchesList.size() + ", next: " + nextMatchesList.size());

        setPager(pastMatchesList, nextMatchesList);
    }

    private void initView()
    {
        try
        {
            mToolbar.findViewById(R.id.rlShirt).setOnClickListener(v -> startActivityForResult(new Intent(SingletonContext.getInstance().getContext(), MyProfileActivity.class), 100));

            tvTitle = rootView.findViewById(R.id.tvTitle);
            tvUserName = rootView.findViewById(R.id.tvUserName);
            tvUserName.setText(TrapConfig.HEADER_USER_NAME);
            imgMenu = rootView.findViewById(R.id.imgMenu);

            imgMenu.setOnClickListener(v -> mainView.openDrawer());

            tvPopularPlayer = rootView.findViewById(R.id.tvPopularPlayer);
            tvPopularPlayer.setText(String.valueOf(Prefs.getInt("popularPlayer", 12)));

            imgBack = rootView.findViewById(R.id.imgBack);
            imgBack.setOnClickListener(v ->
            {
                getActivity().onBackPressed();
            });

            tvTitle.setText("برنامه بازی ها");

            FrameLayout flLogoToolbar = mToolbar.findViewById(R.id.flLogoToolbar);

            flLogoToolbar.setOnClickListener(v ->
            {
                mainView.backToMainFragment();
            });

            tabLayout = rootView.findViewById(R.id.tabLayout);
//            viewPager = rootView.findViewById(R.id.viewPager);

            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
            {
                @Override
                public void onTabSelected(TabLayout.Tab tab)
                {
//                    Prefs.putInt("selectedTab", tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab)
                { }

                @Override
                public void onTabReselected(TabLayout.Tab tab)
                { }
            });

            sendRequest();

        }
        catch (Exception e)
        {
            Logger.d("--Exception--", e.getMessage());
        }

    }

    private void setPager(List<MatchItem> pastMatchesList, List<MatchItem> nextMatchesList)
    {
        viewPager = rootView.findViewById(R.id.viewPager);
        List<String> titleList = new ArrayList<>(3);

        titleList.add("جدول لیگ برتر");
        titleList.add("بازی\u200cهای پیش\u200cرو");
        titleList.add("بازی\u200cهای گذشته");

        SamplePagerAdapter adapter = new SamplePagerAdapter(
                getFragmentManager(),
                titleList,
                pastMatchesList,
                nextMatchesList
        );

        viewPager.setAdapter(adapter);

//        TabLayout tabLayout = rootView.findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        for (int i = 0; i < tabLayout.getTabCount(); i++)
        {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(adapter.getTabView(i));
        }

        viewPager.setCurrentItem(selectedTab);
    }

    @Override
    public void backToPredictFragment(Integer matchId)
    {

    }

    @Override
    public void openPastResultFragment(LeagueTableParent parent, String matchId, Boolean isPredictable, String teamId, String imageLogo, String logoTitle)
    {
        mainView.openPastResultFragment(parent, matchId, isPredictable, teamId, imageLogo, logoTitle);
    }

    @Override
    public void onMatchResultFragment()
    {
//        mainView.ma
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


    private class SamplePagerAdapter extends FragmentStatePagerAdapter
    {
        private List<MatchItem> pastMatchesList, nextMatchesList;
        private List<String> titleList;

        private Context context = SingletonContext.getInstance().getContext();

        @SuppressLint("WrongConstant")
        public SamplePagerAdapter(@NonNull FragmentManager fm,
                                  List<String> titleList,
                                  List<MatchItem> pastMatchesList,
                                  List<MatchItem> nextMatchesList)
        {
            super(fm, 0);
            this.titleList = titleList;
            this.pastMatchesList = pastMatchesList;
            this.nextMatchesList = nextMatchesList;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position)
        {
            return titleList.get(position);
        }

        @NonNull
        @Override
        public Fragment getItem(int position)
        {
            switch (position)
            {
                case 0:
                {
                    Prefs.putInt("selectedTab", 0);
                    return LeagueTableFragment.newInstance(LeagueTableParent.MatchScheduleFragment,
                            "0", false,
                            TrapConfig.TRACTOR_LIVE_SCORE_ID,
                            MatchScheduleFragment.this
                    );
                }
                case 1:
                {
//                    Prefs.putInt("selectedTab", 2);
                    return PastMatchesFragment.newInstance(pastMatchesList, mainView);
                }
                case 2:
                {
//                    Prefs.putInt("selectedTab", 1);
                    return NextMatchesFragment.newInstance(nextMatchesList, mainView);
                }
                default:
                {
                    return LeagueTableFragment.newInstance(LeagueTableParent.MatchScheduleFragment,
                            "0", false,
                            TrapConfig.TRACTOR_LIVE_SCORE_ID,
                            MatchScheduleFragment.this
                    );
                }
            }

        }

        @Override
        public int getCount()
        {
            return titleList.size();
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
            tv.setTextSize(12);
            tv.setTypeface(font);
            return v;
        }

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
}
