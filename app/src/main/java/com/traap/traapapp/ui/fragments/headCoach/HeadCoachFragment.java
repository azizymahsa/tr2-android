package com.traap.traapapp.ui.fragments.headCoach;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.makeramen.roundedimageview.RoundedImageView;
import com.pixplicity.easyprefs.library.Prefs;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.techs.GetTechsIdResponse;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.models.otherModels.headerModel.HeaderModel;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.ui.activities.myProfile.MyProfileActivity;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.Introducing_the_team.PositionInLeaguesFragment;
import com.traap.traapapp.ui.fragments.Introducing_the_team.adapter.IntroduceFragmentPagerAdapter;
import com.traap.traapapp.ui.fragments.headCoach.adapter.CommentAdapter;
import com.traap.traapapp.ui.fragments.headCoach.profileHeadCoach.ProfileHeadCoahFragment;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.utilities.Logger;
import com.traap.traapapp.utilities.Tools;
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
    private com.traap.traapapp.utilities.WrapContentHeightViewPager view_pager;
    private IntroduceFragmentPagerAdapter adapter;
    private Integer coachId=4;
    private GetTechsIdResponse headProfileData;
    private RoundedImageView imgProfile;
    private ProfileHeadCoahFragment profileHeadCoahFragment;
    private RecyclerView rvComment;

    public void setCoachId(Integer coachId)
    {
        this.coachId = coachId;
    }

    public static HeadCoachFragment newInstance(MainActionView mainView, Integer coachId)
    {
        HeadCoachFragment f = new HeadCoachFragment();
        f.setMainView(mainView);
        f.setCoachId(coachId);
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
            rvComment = view.findViewById(R.id.rvComment);
            rvComment.setAdapter(new CommentAdapter());
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
            imgProfile=view.findViewById(R.id.imgProfile);
            ViewCompat.setNestedScrollingEnabled(rvComment, true);



        } catch (Exception e)
        {
            e.getMessage();
        }
    }
    public void getTechsId(){

        SingletonService.getInstance().tractorTeamService().getTechsId(coachId,new OnServiceStatus<WebServiceClass<GetTechsIdResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<GetTechsIdResponse> response)
            {

                    if (response.info.statusCode==200)
                    {
                        headProfileData=response.data;
                        setImageProfile(response.data.getImage());
                        profileHeadCoahFragment.setData(headProfileData);

                    }else{
                        showToast(getActivity(), response.info.message, R.color.red);

                    }
                }

            @Override
            public void onError(String message)
            {
                mainView.hideLoading();
                try{

                    if (Tools.isNetworkAvailable(getActivity()))
                    {
                        Logger.e("-OnError-", "Error: " + message);
                        showError(getActivity(), "خطا در دریافت اطلاعات از سرور!");
                    }
                    else
                    {
                        showAlert(getActivity(), R.string.networkErrorMessage, R.string.networkError);
                    }
                }catch (Exception e){}
            }
        });

    }

    private void setImageProfile(String image)
    {
        try
        {
            Picasso.with(getContext()).load(image).into(imgProfile, new Callback()
            {
                @Override
                public void onSuccess()
                {

                }

                @Override
                public void onError()
                {
                    Picasso.with(getContext()).load(R.drawable.ic_user_default).into(imgProfile);
                }
            });
        }
        catch (Exception e)
        {
            Picasso.with(getContext()).load(R.drawable.ic_user_default).into(imgProfile);
        }
    }

    private void hideLoading()
    {
;

    }


    private void showLoading()
    {


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_head_coach, container, false);

        initViews();
        profileHeadCoahFragment =new ProfileHeadCoahFragment();
        getTechsId();
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
        adapter = new IntroduceFragmentPagerAdapter(getChildFragmentManager());

       /* adapter.addFrag("امتیازات", new CurrentPlayersFragment());
        adapter.addFrag("اتفاقات مهم", new TechnicalTeamFragment());
        adapter.addFrag("قراردادها", new TopPlayersFragment());*/
        adapter.addFrag("سابقه", new PositionInLeaguesFragment());
        adapter.addFrag("مشخصات",profileHeadCoahFragment);

        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(5);

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
