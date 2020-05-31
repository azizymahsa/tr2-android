package com.traap.traapapp.ui.fragments.competitions;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
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
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.getAllCompations.ResponseAllCompations;
import com.traap.traapapp.apiServices.model.getAllCompations.Result;
import com.traap.traapapp.apiServices.model.matchList.MatchItem;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.enums.LeagueTableParent;
import com.traap.traapapp.enums.MatchScheduleParent;
import com.traap.traapapp.models.otherModels.headerModel.HeaderModel;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.singleton.SingletonMatchBuyable;
import com.traap.traapapp.ui.activities.myProfile.MyProfileActivity;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.Introducing_the_team.IntroducingTeamFragment;
import com.traap.traapapp.ui.fragments.leagueTable.LeagueTableActionView;
import com.traap.traapapp.ui.fragments.leagueTable.LeagueTableFragment;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.ui.fragments.matchSchedule.NextMatchesFragment;
import com.traap.traapapp.ui.fragments.matchSchedule.PastMatchesFragment;
import com.traap.traapapp.utilities.CustomViewPager;
import com.traap.traapapp.utilities.Logger;
import com.traap.traapapp.utilities.Tools;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mahsa.Azizi on 05/16/2020.
 */
public class CompationsFragment extends BaseFragment implements LeagueTableActionView
{
    private Context context;

    private static CompationsFragment matchScheduleFragment2;
    private MainActionView mainView;
    private View rootView, rlShirt;
    private TabLayout tabLayout;
    private Toolbar mToolbar;
    private CustomViewPager viewPager;
    private TextView tvTitle, tvUserName, tvPopularPlayer;
    List<MatchItem> deActiveCompations, activeCompations;
    private View imgBack, imgMenu;
    private ArrayList<MatchItem> matchBuyable;
    private Integer selectedTab;

    private MatchScheduleParent parent;

    public static CompationsFragment newInstance(MainActionView mainView)
    {
        CompationsFragment f = new CompationsFragment();
        f.setMainView(mainView);
        return f;
    }

    public CompationsFragment()
    {
    }

    @Override
    public void onAttach(@NonNull Context context)
    {
        super.onAttach(context);
        this.context = context;
    }

    public static CompationsFragment newInstance(MainActionView mainView, MatchScheduleParent parent, ArrayList<MatchItem> matchBuyable, Integer selectedTab)
    {
        matchScheduleFragment2 = new CompationsFragment();
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
        selectedTab = 0;
     /* //  selectedTab = getArguments().getInt("selectedTab", 0);

      //  matchBuyable = getArguments().getParcelableArrayList("MatchList");
        if (matchBuyable != null)
        {
            SingletonMatchBuyable.getInstance().setMatchBuyable(matchBuyable);
        }
        else
        {
            matchBuyable = SingletonMatchBuyable.getInstance().getMatchBuyable();
        }

        Logger.e("-MatchSchedule-", "size: " + matchBuyable.size() + ", selectedTab: " + selectedTab);

        EventBus.getDefault().register(this);*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        if (rootView != null)
        {
            return rootView;
        }

        rootView = inflater.inflate(R.layout.fragment_compation_base, container, false);
        mToolbar = rootView.findViewById(R.id.toolbar);

        initView();

        sendRequest();

        return rootView;
    }

    private void sendRequest()
    {
        deActiveCompations = new ArrayList<>();
        activeCompations = new ArrayList<>();
        requestGetAllCompations();
        Logger.e("-+MatchSchedule+-", "past: " + deActiveCompations.size() + ", next: " + activeCompations.size());

    }

    private void requestGetAllCompations()
    {
        SingletonService.getInstance().getSurveyDetailService().getAllCompations(new OnServiceStatus<WebServiceClass<ResponseAllCompations>>()
        {
            @Override
            public void onReady(WebServiceClass<ResponseAllCompations> response)
            {
                try
                {
                    List<Result> activeCompations = new ArrayList<>();
                    List<Result> deActiveCompations = new ArrayList<>();

                    if (response.info.statusCode == 200)
                    {
                        for (int i = 0; i < response.data.getResults().size(); i++)
                        {
                            Result surveyList = response.data.getResults().get(i);
                            if (surveyList.getIsActive())
                            {
                                activeCompations.add(surveyList);

                            } else
                            {
                                deActiveCompations.add(surveyList);

                            }
                        }
                        setPager(deActiveCompations, activeCompations);

                    } else
                    {

                    }
                } catch (Exception e)
                {
                    e.getMessage();
                }
            }

            @Override
            public void onError(String message)
            {
                hideLoading();
                if (!Tools.isNetworkAvailable(getActivity()))
                {
                    Logger.e("-OnError-", "Error: " + message);
                    showError(getContext(), "خطا در دریافت اطلاعات از سرور!");
                } else
                {
                    showAlert(getContext(), R.string.networkErrorMessage, R.string.networkError);
                }
            }
        });
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

            tvTitle.setText("مسابقات");

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
                {
                }

                @Override
                public void onTabReselected(TabLayout.Tab tab)
                {
                }
            });

            sendRequest();

        } catch (Exception e)
        {
            Logger.d("--Exception--", e.getMessage());
        }

    }

    private void setPager(List<Result> deActive, List<Result> active)
    {
        viewPager = rootView.findViewById(R.id.viewPager);
        List<String> titleList = new ArrayList<>(3);

        titleList.add("مسابقات\u200cغیر\u200cفعال");
        titleList.add("مسابقات\u200cفعال");

        SamplePagerAdapter adapter = new SamplePagerAdapter(
                getFragmentManager(),
                titleList,
                deActive,
                active

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
        private List<Result> deActiveCompations, activeCompations;
        private List<String> titleList;

        private Context context = SingletonContext.getInstance().getContext();

        @SuppressLint("WrongConstant")
        public SamplePagerAdapter(@NonNull FragmentManager fm,
                                  List<String> titleList,
                                  List<Result> activeCompations,
                                  List<Result> deActiveCompations)
        {
            super(fm, 0);
            this.titleList = titleList;
            this.deActiveCompations = deActiveCompations;
            this.activeCompations = activeCompations;
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
                   /* return LeagueTableFragment.newInstance(LeagueTableParent.MatchScheduleFragment,
                            "0", false,
                            TrapConfig.TRACTOR_LIVE_SCORE_ID,
                            CompationsFragment.this
                    );*/
                    return ActiveMatchesFragment.newInstance(activeCompations, mainView);

                }
                case 1:
                {
//                    Prefs.putInt("selectedTab", 2);
                    return DeActiveMatchesFragment.newInstance(deActiveCompations, mainView);

                  //  return WinMatchesFragment.newInstance(activeCompations, mainView);

                }
               /* case 2:
                {
//                    Prefs.putInt("selectedTab", 1);
                    return PastMatchesFragment.newInstance(deActiveCompations, mainView);

                }*/
                default:
                {
                    return LeagueTableFragment.newInstance(LeagueTableParent.MatchScheduleFragment,
                            "0", false,
                            TrapConfig.TRACTOR_LIVE_SCORE_ID,
                            CompationsFragment.this
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
    public void onResume()
    {
        super.onResume();


    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Log.e("testt", "onDestroy");
        EventBus.getDefault().unregister(this);
    }
}
