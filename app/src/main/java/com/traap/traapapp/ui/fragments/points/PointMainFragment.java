package com.traap.traapapp.ui.fragments.points;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.invite.InviteResponse;
import com.traap.traapapp.apiServices.model.media.category.TypeCategory;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.enums.MediaArchiveCategoryCall;
import com.traap.traapapp.models.otherModels.points.PointScoreModel;
import com.traap.traapapp.models.otherModels.points.PointTabModel;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.ui.activities.myProfile.MyProfileActivity;
import com.traap.traapapp.ui.activities.points.PointActionView;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.news.archive.NewsArchiveCategoryFragment;
import com.traap.traapapp.ui.fragments.news.archive.NewsArchiveFragment;
import com.traap.traapapp.ui.fragments.points.groupBy.PointsGroupByFragment;
import com.traap.traapapp.ui.fragments.points.guide.PointsGuideFragment;
import com.traap.traapapp.ui.fragments.points.records.PointsRecordFragment;
import com.traap.traapapp.utilities.CustomViewPager;
import com.traap.traapapp.utilities.Logger;
import com.traap.traapapp.utilities.Tools;
import com.traap.traapapp.utilities.Utility;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;


@SuppressLint("ValidFragment")
public class PointMainFragment extends BaseFragment implements PointActionView
{
    private List<PointTabModel> tabList;
    private View rootView;
    private Context context;
    private ViewPager viewPager;
//    private MainActionView mainView;

    private Toolbar mToolbar;
    private TextView tvPointScore;


    public PointMainFragment()
    {

    }

    public static PointMainFragment newInstance()
    {
        PointMainFragment f = new PointMainFragment();
//        f.setMainView(mainView);
        return f;
    }
//
//    private void setMainView(MainActionView mainView)
//    {
//        this.mainView = mainView;
//    }


    @Override
    public void onAttach(@NonNull Context context)
    {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_points_main, container, false);

        mToolbar = rootView.findViewById(R.id.toolbar);

        mToolbar.findViewById(R.id.imgMenu).setVisibility(View.GONE);
//        mToolbar.findViewById(R.id.imgMenu).setOnClickListener(v -> mainView.openDrawer());
        mToolbar.findViewById(R.id.imgBack).setOnClickListener(rootView -> ((Activity) context).finish());
        mToolbar.findViewById(R.id.flLogoToolbar).setOnClickListener(rootView -> ((Activity) context).finish());
        TextView tvUserName = mToolbar.findViewById(R.id.tvUserName);
        TextView tvTitle = mToolbar.findViewById(R.id.tvTitle);
        tvTitle.setText("امتیازات");
        tvUserName.setText(TrapConfig.HEADER_USER_NAME);

        tvPointScore = rootView.findViewById(R.id.tvPointScore);

        rootView.findViewById(R.id.rlShirt).setOnClickListener(v -> startActivityForResult(new Intent(SingletonContext.getInstance().getContext(),
                MyProfileActivity.class), 100)
        );
        
        tabList = new ArrayList<>(3);

        PointTabModel tabItem = new PointTabModel(2, "راهنمای امتیازات");
        tabList.add(tabItem);
        tabItem = new PointTabModel(1, "دسته بندی امتیازات");
        tabList.add(tabItem);
        tabItem = new PointTabModel(0, "سوابق امتیازات");
        tabList.add(tabItem);

        setPager();

        EventBus.getDefault().register(this);
        return rootView;
    }


    public void setPager()
    {
        viewPager = rootView.findViewById(R.id.view_pager);
        SamplePagerAdapter adapter = new SamplePagerAdapter(getFragmentManager());

        viewPager.setAdapter(adapter);

        TabLayout tabLayout = rootView.findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        for (int i = 0; i < tabLayout.getTabCount(); i++)
        {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(adapter.getTabView(i));
        }

        viewPager.setCurrentItem(tabList.size()-1);
    }

    @Override
    public void onInviteFriend()
    {
        SingletonService.getInstance().getProfileService().getInviteService(new OnServiceStatus<WebServiceClass<InviteResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<InviteResponse> response)
            {
                // mainView.hideLoading();
                try
                {

                    if (response.info.statusCode == 200)
                    {
                        //share text
                        String shareBody = response.data.getInvite_text();
                        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                        sharingIntent.setType("text/plain");
                        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "دعوت از دوستان");
                        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                        startActivity(Intent.createChooser(sharingIntent, "کد عضویت خود را به اشتراک بگذارید: "));
                    }
                    else
                    {
                        if (Tools.isNetworkAvailable((Activity) context))
                        {
                            showError(context, "خطا در دریافت اطلاعات از سرور!");
                        }
                        else
                        {
                            showAlert(context, R.string.networkErrorMessage, R.string.networkError);
                        }
                    }
                }
                catch (Exception e)
                {
                    if (Tools.isNetworkAvailable((Activity) context))
                    {
                        Logger.e("-OnError-", "response.info.statusCode: " + response.info.statusCode);
                        showError(context, "خطا در دریافت اطلاعات از سرور!");
                    }
                    else
                    {
                        // showError(MyProfileActivity.this,String.valueOf(R.string.networkErrorMessage));

                        showAlert(context, R.string.networkErrorMessage, R.string.networkError);
                    }

                }
            }

            @Override
            public void onError(String message)
            {
                // mainView.hideLoading();
                if (Tools.isNetworkAvailable((Activity) context))
                {
                    Logger.e("-OnError-", "Error: " + message);
                    showError(context, "خطا در دریافت اطلاعات از سرور!");
                }
                else
                {
                    // showError(MyProfileActivity.this,String.valueOf(R.string.networkErrorMessage));

                    showAlert(context, R.string.networkErrorMessage, R.string.networkError);
                }
            }
        });
    }

    private class SamplePagerAdapter extends FragmentStatePagerAdapter
    {
        private Context context = SingletonContext.getInstance().getContext();

        @SuppressLint("WrongConstant")
        public SamplePagerAdapter(@NonNull FragmentManager fm)
        {
            super(fm, 0);
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
                case 2:
                {
                    return PointsGuideFragment.newInstance(PointMainFragment.this);
                }
                case 1:
                {
                    return PointsGroupByFragment.newInstance(PointMainFragment.this);
                }
                case 0:
                {
                    return PointsRecordFragment.newInstance(PointMainFragment.this);
                }
                default:
                {
                    return PointsRecordFragment.newInstance(PointMainFragment.this);
                }
            }
        }

        @Override
        public int getCount()
        {
            try
            {
                return tabList.size();
            }
            catch (NullPointerException e)
            {
                return 0;
            }
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
    public void getData(PointScoreModel model)
    {
        try
        {
            tvPointScore.setText(model.getScore());

            ViewPager.LayoutParams params = new ViewPager.LayoutParams();
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.height = model.getMaxHeight();

//            viewPager.setLayoutParams(params);
            Logger.e("---height---", model.getMaxHeight().toString());
        }
        catch (Exception e)
        {

        }

    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
