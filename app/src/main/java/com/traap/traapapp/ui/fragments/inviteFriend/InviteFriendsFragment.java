package com.traap.traapapp.ui.fragments.inviteFriend;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.pixplicity.easyprefs.library.Prefs;
import com.traap.traapapp.R;

import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.getHistory.Image;
import com.traap.traapapp.apiServices.model.inviteFriend.InviteFriendResponse;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.models.otherModels.headerModel.HeaderModel;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.ui.activities.myProfile.MyProfileActivity;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.utilities.ScreenShot;
import com.traap.traapapp.utilities.Tools;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;

public class InviteFriendsFragment extends BaseFragment implements View.OnClickListener
{


    private View view;
    private Toolbar mToolbar;
    private LinearLayout llEditInvite;
    private View btnConfirm,rlShirt,imgMenu,imgBack;
    private MainActionView mainView;
    private TextView tvTitle , tvDesc ,tvInviteCode,tvLinkAddress,tvUserName,tvPopularPlayer,tvInviteTitle;
    private boolean isDescriptionEdited=false;
    private String linkAddres;
    private ImageView ivTraap;


    public static InviteFriendsFragment newInstance(MainActionView mainView)
    {
        InviteFriendsFragment f = new InviteFriendsFragment();
        f.setMainView(mainView);
        return f;
    }

    private void setMainView(MainActionView mainView)
    {
        this.mainView = mainView;
    }

    public InviteFriendsFragment()
    {
    }


    public static InviteFriendsFragment newInstance()
    {
        InviteFriendsFragment fragment = new InviteFriendsFragment();


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
            showLoading();
            llEditInvite = view.findViewById(R.id.llEditInvite);
            tvTitle = view.findViewById(R.id.tvTitle);
            tvDesc = view.findViewById(R.id.tvDesc);

            ivTraap=view.findViewById(R.id.ivTraap);
            tvInviteCode = view.findViewById(R.id.tvInviteCode);
            tvLinkAddress = view.findViewById(R.id.tvLinkAddress);
            btnConfirm=view.findViewById(R.id.btnConfirm);
            tvInviteTitle=view.findViewById(R.id.tvInviteTitle);

            btnConfirm.setOnClickListener(this);
            tvLinkAddress.setOnClickListener(this);
            llEditInvite.setOnClickListener(this);
            //toolbar
            mToolbar = view.findViewById(R.id.toolbar);
            tvUserName = mToolbar.findViewById(R.id.tvUserName);
            TextView tvTitle = mToolbar.findViewById(R.id.tvTitle);
            tvTitle.setText("دعوت از دوستان");
            mToolbar.findViewById(R.id.imgBack).setOnClickListener(v -> mainView.backToMainFragment());

            tvUserName.setText(TrapConfig.HEADER_USER_NAME);
            rlShirt = mToolbar.findViewById(R.id.rlShirt);
            rlShirt.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    startActivityForResult(new Intent(SingletonContext.getInstance().getContext(), MyProfileActivity.class),100);
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
            flLogoToolbar.setOnClickListener(v -> {
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

            requestGetInviteFriendInfo();
        } catch (Exception e)
        {
            e.getMessage();
        }
    }

    private void requestGetInviteFriendInfo() {
        SingletonService.getInstance().getInviteFriendService().GetInviteFriendService(new OnServiceStatus<WebServiceClass<InviteFriendResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<InviteFriendResponse> response)
            {
                try
                {
                    hideLoading();
                    if (response.info.statusCode == 200)
                    {

                        onGetInviteFriendSuccess(response.data);

                    } else
                    {
                        showToast(getActivity(), response.info.message, R.color.red);
                    }
                } catch (Exception e)
                {
                    showToast(getActivity(), e.getMessage(), R.color.red);

                }
            }

            @Override
            public void onError(String message)
            {
                hideLoading();
                if (Tools.isNetworkAvailable(getActivity()))
                {
                    showError(getActivity(), "خطا در دریافت اطلاعات از سرور!");
                } else
                {
                    showAlert(getActivity(), R.string.networkErrorMessage, R.string.networkError);
                }
            }
        });
    }

    private void onGetInviteFriendSuccess(InviteFriendResponse data) {

        tvDesc.setText(data.getBody());
        tvInviteCode.setText("کد معرف: "+data.getInviteKey());
        tvLinkAddress.setText(data.getUrl());
        linkAddres=data.getUrl();
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
        view = inflater.inflate(R.layout.fragment_invite_friends, container, false);

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

            case R.id.btnConfirm:

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String stringBuilder = tvInviteTitle.getText().toString() +
                        "\n" +
                        tvTitle.getText().toString() +
                        "\n" +
                        tvDesc.getText().toString() +
                        "\n" +
                        tvInviteCode.getText().toString() +
                        "\n" +
                        "لینک دانلود اپلیکیشن: " + linkAddres;
                sharingIntent.putExtra(Intent.EXTRA_TEXT, stringBuilder);
             //   sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareText);
                startActivity(Intent.createChooser(sharingIntent, "ارسال با"));

              /*  Intent shareIntent;
                Uri bmpUri = Uri.parse("android.resource://" + getActivity().getPackageName()
                        + "/drawable/" + "traap_home_logo");

                shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
               shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
              *//*  shareIntent.putExtra(Intent.EXTRA_STREAM,
                        Uri.parse( Environment.getExternalStorageDirectory()+ File.separator+"traap_home_logo.png"));*//*
                String stringBuilder = tvInviteTitle.getText().toString() +
                        "\n" +
                        tvTitle.getText().toString() +
                        "\n" +
                        tvDesc.getText().toString() +
                        "\n" +
                        tvInviteCode.getText().toString() +
                        "\n" +
                        "لینک دانلود اپلیکیشن: " + linkAddres;
                shareIntent.putExtra(Intent.EXTRA_TEXT, stringBuilder);
                shareIntent.setType("text/plain");

                shareIntent.setType("image/png");
                startActivity(Intent.createChooser(shareIntent, "Share with"));*/

                break;
            case R.id.tvLinkAddress:

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(linkAddres));
                startActivity(browserIntent);

                break;
            case R.id.llEditInvite:

                isDescriptionEdited=true;
                tvDesc.setVisibility(View.GONE);
                break;

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