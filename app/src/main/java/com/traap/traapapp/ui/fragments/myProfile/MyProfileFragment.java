package com.traap.traapapp.ui.fragments.myProfile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.pixplicity.easyprefs.library.Prefs;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.categoryByIdVideo.CategoryByIdVideosRequest;
import com.traap.traapapp.apiServices.model.categoryByIdVideo.CategoryByIdVideosResponse;
import com.traap.traapapp.apiServices.model.invite.InviteResponse;
import com.traap.traapapp.apiServices.model.profile.getProfile.response.GetProfileResponse;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.ui.activities.login.LoginActivity;
import com.traap.traapapp.ui.activities.userProfile.UserProfileActivity;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.dialogs.MessageAlertDialog;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.utilities.Tools;


@SuppressLint("ValidFragment")
public class MyProfileFragment extends BaseFragment
{
    private View rootView;
    private MainActionView mainView;
    private CardView cardView;

    private Toolbar mToolbar;
    private TextView tvFullName, tvMobile, tvInviteCode;


    private RelativeLayout rlEditProfile, rlMyPredict, rlMyFavorite, rlExit;


    public MyProfileFragment()
    {

    }

    public static MyProfileFragment newInstance(MainActionView mainView)
    {
        MyProfileFragment f = new MyProfileFragment();
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
        rootView = inflater.inflate(R.layout.fragment_my_profile, container, false);

        mToolbar = rootView.findViewById(R.id.toolbar);

        mToolbar.findViewById(R.id.imgMenu).setOnClickListener(v -> mainView.openDrawer());
        mToolbar.findViewById(R.id.imgBack).setOnClickListener(rootView -> mainView.backToMainFragment());
        TextView tvUserName = mToolbar.findViewById(R.id.tvUserName);
        TextView tvTitle = mToolbar.findViewById(R.id.tvTitle);
        tvTitle.setText("حساب کاربری من");
        tvUserName.setText(TrapConfig.HEADER_USER_NAME);

        initView();

        return rootView;
    }


    public void initView()
    {
        cardView = rootView.findViewById(R.id.card);

        tvFullName = rootView.findViewById(R.id.tvFullName);
        tvMobile = rootView.findViewById(R.id.tvMobile);
        tvInviteCode = rootView.findViewById(R.id.tvInviteCode);
        rlEditProfile = rootView.findViewById(R.id.rlEditProfile);
        rlMyPredict = rootView.findViewById(R.id.rlMyPredict);
        rlMyFavorite = rootView.findViewById(R.id.rlMyFavorite);
        rlExit = rootView.findViewById(R.id.rlExit);

        if (Prefs.getString("FULLName", "").trim().equalsIgnoreCase(""))
        {
            tvFullName.setText(Prefs.getString("mobile", ""));
            tvMobile.setVisibility(View.GONE);
        } else
        {
            tvFullName.setText(Prefs.getString("FULLName", ""));
        }
        tvMobile.setText(Prefs.getString("mobile", ""));
        tvInviteCode.setText(Prefs.getString("keyInvite", ""));

        rlEditProfile.setOnClickListener(v ->
        {
            startActivity(new Intent(SingletonContext.getInstance().getContext(), UserProfileActivity.class));
        });

        rlMyPredict.setOnClickListener(v ->
        {

        });

        rlMyFavorite.setOnClickListener(v ->
        {

        });
        tvInviteCode.setOnClickListener(v ->
        {
//Prefs.getString("keyInvite", "")
            sendRequestInvite();

        });

        rlExit.setOnClickListener(v ->
        {
            MessageAlertDialog dialog = new MessageAlertDialog(getActivity(), "", "آیا می خواهید از حساب کاربری خود خارج شوید؟",
                    true, "خروج", "انصراف", new MessageAlertDialog.OnConfirmListener()
            {
                @Override
                public void onConfirmClick()
                {
                    Intent intent = new Intent();
                    String mobile = Prefs.getString("mobile", "");
                    Prefs.clear();
                    Prefs.putString("mobile", mobile);
                    getActivity().finish();
                    intent.setClass(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }

                @Override
                public void onCancelClick()
                {

                }
            });
            dialog.show(getActivity().getFragmentManager(), "messageDialog");
        });

    }

    private void sendRequestInvite()
    {
        CategoryByIdVideosRequest request = new CategoryByIdVideosRequest();
       // SingletonService.getInstance().getProfileService().getProfileService(this);

        SingletonService.getInstance().getProfileService().getInviteService(new OnServiceStatus<WebServiceClass<InviteResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<InviteResponse> response)
            {
                mainView.hideLoading();
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
                        startActivity(Intent.createChooser(sharingIntent, response.data.getInvite_text()));

                    } else
                    {
                        Tools.showToast(getContext(), response.info.message, R.color.red);
                    }
                } catch (Exception e)
                {
                    Tools.showToast(getContext(), e.getMessage(), R.color.red);

                }
            }

            @Override
            public void onError(String message)
            {
                mainView.hideLoading();
                Tools.showToast(getActivity(), message, R.color.red);
            }
        });
    }

}
