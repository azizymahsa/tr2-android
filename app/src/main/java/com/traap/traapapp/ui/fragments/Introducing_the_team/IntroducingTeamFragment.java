package com.traap.traapapp.ui.fragments.Introducing_the_team;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.moeidbannerlibrary.banner.BannerLayout;
import com.google.android.material.tabs.TabLayout;
import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.topPlayers.Result;
import com.traap.traapapp.apiServices.model.topPlayers.TopPlayerResponse;
import com.traap.traapapp.apiServices.model.tractorTeam.TractorTeamResponse;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.Introducing_the_team.adapter.IntroduceFragmentPagerAdapter;
import com.traap.traapapp.ui.fragments.Introducing_the_team.adapter.PlayerSearchAdapter;
import com.traap.traapapp.ui.fragments.Introducing_the_team.adapter.TeamPhotoAdapter;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.utilities.ClearableEditText;
import com.traap.traapapp.utilities.Logger;
import com.traap.traapapp.utilities.Tools;
import com.traap.traapapp.utilities.WrapContentHeightViewPager;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import static com.traap.traapapp.utilities.Utility.changeFontInViewGroup;

/**
 * Authors:
 * Reza Nejati <reza.n.j.t.i@gmail.com>
 * Copyright © 2017
 */
public class IntroducingTeamFragment extends BaseFragment implements PlayerSearchAdapter.PlayerSearchAdapterEvent
{

    private View rootView;
    private MainActionView mainView;
    private BannerLayout blTeam;
    private TabLayout tabLayout;
    private WrapContentHeightViewPager view_pager;
    private IntroduceFragmentPagerAdapter adapter;
    private NestedScrollView nestedScrollView;
    private ClearableEditText etSearchText;
    private CardView cvSearch;
    private ProgressBar pdSearch;
    private PlayerSearchAdapter playerSearchAdapter;
    private RecyclerView rvSearch;
    private TextView tvCreateDate, tvTeamAddress, tvPhone, tvEmail;
    private ImageView ivTeamLogo;
    private List<Result> getResults = new ArrayList<>();
    private Handler handler;

    public IntroducingTeamFragment()
    {
    }

    public static IntroducingTeamFragment newInstance(MainActionView mainView)
    {
        IntroducingTeamFragment f = new IntroducingTeamFragment();
        f.setMainView(mainView);
        return f;
    }

    private void setMainView(MainActionView mainView)
    {
        this.mainView = mainView;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        if (rootView != null)
        {
            return rootView;

        }
        rootView = inflater.inflate(R.layout.introducing_team_fragment, container, false);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        initView();
        initViewPager();
        initSearch();
        getTraktorTeam();

    }


    private void initViewPager()
    {
        addTabs(view_pager);
/*        Fragment childFragment = new TeamHistoryFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.child_fragment_container, childFragment).commit();*/

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
                if (position == 4)
                {
                    //    setViewPagerHeight(((TeamHistoryFragment)adapter.getItem(position)).getHeight());
                    //  setViewPagerHeight(TeamHistoryFragment.height);

                }
                if (position == 3)
                {
                    //   setViewPagerHeight(1750);
                    //setViewPagerHeight(((PositionInLeaguesFragment)adapter.getItem(position)).getHeight());
                    //   setViewPagerHeight(PositionInLeaguesFragment.height);
                    Log.e("onPageSelected", PositionInLeaguesFragment.height + "");

                }
            }
        });

    }

    private void initView()
    {
        blTeam = rootView.findViewById(R.id.blTeam);
        ivTeamLogo = rootView.findViewById(R.id.ivTeamLogo);
        nestedScrollView = rootView.findViewById(R.id.nestedScroll);
        tabLayout = rootView.findViewById(R.id.tabLayout);
        view_pager = rootView.findViewById(R.id.view_pager);
        etSearchText = rootView.findViewById(R.id.etSearchText);
        pdSearch = rootView.findViewById(R.id.pdSearch);
        cvSearch = rootView.findViewById(R.id.cvSearch);
        rvSearch = rootView.findViewById(R.id.rvSearch);
        tvCreateDate = rootView.findViewById(R.id.tvCreateDate);
        tvTeamAddress = rootView.findViewById(R.id.tvTeamAddress);
        tvPhone = rootView.findViewById(R.id.tvPhone);
        tvEmail = rootView.findViewById(R.id.tvEmail);

        TextView tvTitle = rootView.findViewById(R.id.tvTitle);
        tvTitle.setText("معرفی تیم تراکتور");
        TextView tvUserName = rootView.findViewById(R.id.tvUserName);
        tvUserName.setText(TrapConfig.HEADER_USER_NAME);
        View imgMenu = rootView.findViewById(R.id.imgMenu);
        imgMenu.setOnClickListener(v -> mainView.openDrawer());
        View imgBack = rootView.findViewById(R.id.imgBack);
        imgBack.setOnClickListener(rootView -> getActivity().onBackPressed());
        blTeam.setAdapter(new TeamPhotoAdapter());
        blTeam.setAutoPlaying(true);
        ViewCompat.setNestedScrollingEnabled(rvSearch, true);
        playerSearchAdapter = new PlayerSearchAdapter(getResults, getActivity(), this);
        rvSearch.setAdapter(playerSearchAdapter);

    }

    public void initSearch()
    {

        etSearchText.addTextChangedListener(new TextWatcher()
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

                if (etSearchText.getText().toString().length() > 2)
                {
                    cvSearch.setVisibility(View.VISIBLE);
                    pdSearch.setVisibility(View.VISIBLE);
                    if (handler != null)
                        handler.removeCallbacksAndMessages(null);

                    handler = new Handler();
                    handler.postDelayed(() -> search(etSearchText.getText().toString()), 500);


                } else if (etSearchText.getText().toString().length() == 0)
                {
                    cvSearch.setVisibility(View.GONE);
                    pdSearch.setVisibility(View.GONE);
                    playerSearchAdapter.notifyDataSetChanged();
                    getResults.clear();
                    if (handler != null)
                        handler.removeCallbacksAndMessages(null);
                }

            }
        });
        //vSearch.setAdapter(new PlayerSearchAdapter(response.data.getResults()));
 /*       etSearchText.setListener(() -> YoYo.with(Techniques.FadeOutDown)
                .duration(500)
                .playOn(cvSearch));*/
    }

    /*
    *     public void initSearch(){
        ArrayList<ProductDataModel> productDataModels= new ArrayList<>();
        productDataModels.add(new ProductDataModel(1,"test"));
        productDataModels.add(new ProductDataModel(1,"test"));
        productDataModels.add(new ProductDataModel(1,"test"));
        productDataModels.add(new ProductDataModel(1,"test"));
        productDataModels.add(new ProductDataModel(1,"test"));
        productDataModels.add(new ProductDataModel(1,"test"));
        PlayerSearchAdapter adapter = new PlayerSearchAdapter(getActivity(), R.layout.player_search_adapter, productDataModels);

        etSearchText.setAdapter( adapter);
        etSearchText.setDropDownBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.background_border_main));

    }*/

    private void addTabs(ViewPager viewPager)
    {
        adapter = new IntroduceFragmentPagerAdapter(getChildFragmentManager());

        adapter.addFrag("بازیکنان فعلی", new CurrentPlayersFragment(mainView));
        adapter.addFrag("کادر فنی", new TechnicalTeamFragment(mainView));
        adapter.addFrag("برترین بازیکن ها", new TopPlayersFragment());
        adapter.addFrag("جایگاه در لیگ ها", new PositionInLeaguesFragment());
        adapter.addFrag("تاریخچه", new TeamHistoryFragment());

        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(5);
    }

    @Override
    public void onResume()
    {
        super.onResume();

    }

    public void getTraktorTeam()
    {
        mainView.showLoading();

        SingletonService.getInstance().tractorTeamService().traktor(new OnServiceStatus<WebServiceClass<TractorTeamResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<TractorTeamResponse> response)
            {


                try
                {
                    mainView.hideLoading();

                    if (response.info.statusCode == 200)
                    {
                        tvCreateDate.setText(response.data.getCreateDate());
                        tvTeamAddress.setText(response.data.getAddress());
                        tvPhone.setText(response.data.getPhone());
                        tvEmail.setText(response.data.getEmail());

                        Glide.with(getActivity()).load(response.data.getLogo()).into(ivTeamLogo);


                    } else
                    {

                        showToast(getActivity(), response.info.message, R.color.red);

                    }
                } catch (Exception e)
                {
                    showError(getActivity(), "خطا در دریافت اطلاعات از سرور!");
                }

            }

            @Override
            public void onError(String message)
            {
                try
                {
                    mainView.hideLoading();

                    if (Tools.isNetworkAvailable(getActivity()))
                    {
                        Logger.e("-OnError-", "Error: " + message);
                        showError(getActivity(), "خطا در دریافت اطلاعات از سرور!");
                    } else
                    {
                        showAlert(getActivity(), R.string.networkErrorMessage, R.string.networkError);
                    }
                } catch (Exception e)
                {
                }

            }
        });
    }

    public void search(String name)
    {
        if (getResults.size() > 0)
        {
            rvSearch.setVisibility(View.VISIBLE);
            getResults.clear();
        } else
        {
            cvSearch.setVisibility(View.VISIBLE);

            pdSearch.setVisibility(View.VISIBLE);
            rvSearch.setVisibility(View.GONE);
        }


        SingletonService.getInstance().tractorTeamService().getSearchTech(new OnServiceStatus<WebServiceClass<TopPlayerResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<TopPlayerResponse> response)
            {
                try
                {
                    mainView.hideLoading();

                    if (response.info.statusCode == 200)
                    {
                        getResults.addAll(response.data.getResults());
                        playerSearchAdapter.notifyDataSetChanged();
                        pdSearch.setVisibility(View.GONE);
                        rvSearch.setVisibility(View.VISIBLE);
                    } else
                    {

                    }
                } catch (Exception e)
                {
                }

            }

            @Override
            public void onError(String message)
            {
                pdSearch.setVisibility(View.GONE);
                rvSearch.setVisibility(View.VISIBLE);
            }
        }, name);

    }

    @Override
    public void onPlayerSearchClick(Result result)
    {

        if (result.getRole().contains("مربی"))
            mainView.onHeadCoach(result.getId(),"معرفی سرمربی");


    }
}
