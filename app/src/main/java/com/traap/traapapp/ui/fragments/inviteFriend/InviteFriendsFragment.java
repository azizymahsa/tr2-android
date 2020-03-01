package com.traap.traapapp.ui.fragments.inviteFriend;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.pixplicity.easyprefs.library.Prefs;
import com.traap.traapapp.R;

import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.models.otherModels.headerModel.HeaderModel;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.ui.activities.myProfile.MyProfileActivity;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.main.MainActionView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class InviteFriendsFragment extends BaseFragment implements View.OnClickListener
{


    private View view;
    private Toolbar mToolbar;
    private LinearLayout llEditInvite;
    private View btnConfirm,rlShirt,imgMenu,imgBack;
    private EditText etEditDesc;
    private MainActionView mainView;
    private TextView tvTitle , tvDesc ,tvInviteCode,tvLinkAddress,tvUserName,tvPopularPlayer;
    private boolean isDescriptionEdited=false;


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

            etEditDesc = view.findViewById(R.id.etEditDesc);
            tvInviteCode = view.findViewById(R.id.tvInviteCode);
            tvLinkAddress = view.findViewById(R.id.tvLinkAddress);
            btnConfirm=view.findViewById(R.id.btnConfirm);

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

                String shareText="";

                if (isDescriptionEdited){
                   shareText= etEditDesc.getText().toString();
                }else {
                  shareText=tvDesc.getText().toString();
                }

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, tvTitle.getText().toString());
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareText);
                startActivity(Intent.createChooser(sharingIntent, "ارسال با"));

                break;
            case R.id.tvLinkAddress:


                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.traap.com"));
                startActivity(browserIntent);

                break;
            case R.id.llEditInvite:

                isDescriptionEdited=true;
                tvDesc.setVisibility(View.GONE);
                etEditDesc.setVisibility(View.VISIBLE);
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