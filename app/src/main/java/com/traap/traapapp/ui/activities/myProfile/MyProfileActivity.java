package com.traap.traapapp.ui.activities.myProfile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.pixplicity.easyprefs.library.Prefs;

import com.squareup.picasso.Picasso;
import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.categoryByIdVideo.CategoryByIdVideosRequest;
import com.traap.traapapp.apiServices.model.invite.InviteResponse;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.models.otherModels.headerModel.HeaderModel;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.ui.activities.login.LoginActivity;
import com.traap.traapapp.ui.activities.myPredicts.MyPredictsActivity;
import com.traap.traapapp.ui.activities.mySupport.MySupportActivity;
import com.traap.traapapp.ui.activities.userProfile.UserProfileActivity;
import com.traap.traapapp.ui.base.BaseActivity;
import com.traap.traapapp.ui.dialogs.MessageAlertDialog;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.ui.fragments.suggestions.SuggestionsFragment;
import com.traap.traapapp.utilities.Logger;
import com.traap.traapapp.utilities.Tools;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


public class MyProfileActivity extends BaseActivity
{
    private MainActionView mainView;
    private CardView cardView;
    private TextView tvUserName, tvHeaderPopularNo;

    private Toolbar mToolbar;
    private TextView tvFullName, tvMobile, tvInviteCode;

    private ImageView imgProfile;

    private RelativeLayout rlEditProfile, rlMyPredict, rlMyFavorite, rlExit,rlMySupport;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //rootView = inflater.inflate(R.layout.activity_my_profile, container, false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        mToolbar = findViewById(R.id.toolbar);

        mToolbar.findViewById(R.id.imgMenu).setVisibility(View.GONE);
        mToolbar.findViewById(R.id.imgBack).setOnClickListener(rootView -> finish()
        );
        tvUserName = mToolbar.findViewById(R.id.tvUserName);
        tvHeaderPopularNo = mToolbar.findViewById(R.id.tvPopularPlayer);
        TextView tvTitle = mToolbar.findViewById(R.id.tvTitle);
        tvTitle.setText("حساب کاربری من");
        tvUserName.setText(TrapConfig.HEADER_USER_NAME);

        initView();

        EventBus.getDefault().register(this);
    }


    public void initView()
    {
        cardView = findViewById(R.id.card);
        imgProfile = findViewById(R.id.imgProfile);

        tvFullName = findViewById(R.id.tvFullName);
        tvMobile = findViewById(R.id.tvMobile);
        tvInviteCode = findViewById(R.id.tvInviteCode);
        rlEditProfile = findViewById(R.id.rlEditProfile);
        rlMyPredict = findViewById(R.id.rlMyPredict);
        rlMySupport = findViewById(R.id.rlMySupport);
        rlMyFavorite = findViewById(R.id.rlMyFavorite);
        rlExit = findViewById(R.id.rlExit);
        FrameLayout flLogoToolbar = findViewById(R.id.flLogoToolbar);

        flLogoToolbar.setOnClickListener(v -> finish());
        if (Prefs.contains("profileImage"))
        {
            try
            {
                if (!Prefs.getString("profileImage", "").contains("default_avatar.png"))
                {
                    Picasso.with(this).load(Prefs.getString("profileImage", "")).into(imgProfile);
                }
                else
                {
                    Picasso.with(this).load(R.drawable.ic_user_default).into(imgProfile);
                }
            } catch (Exception e)
            {
                Logger.e("-Exception photo-", e.getMessage());
                e.printStackTrace();
                Picasso.with(this).load(R.drawable.ic_user_default).into(imgProfile);
            }
        }

        if (Prefs.getString("FULLName", "").trim().equalsIgnoreCase(""))
        {
            tvFullName.setText("+"+Prefs.getString("Country_Code","")+" "+Prefs.getString("mobile", ""));
            tvMobile.setVisibility(View.GONE);
        }
        else
        {
            tvFullName.setText(Prefs.getString("FULLName", ""));
        }
        tvMobile.setText("+"+Prefs.getString("Country_Code","")+" "+ Prefs.getString("mobile", ""));
        tvInviteCode.setText(Prefs.getString("keyInvite", ""));

        tvHeaderPopularNo.setText(String.valueOf(Prefs.getInt("popularPlayer", 12)));

        rlEditProfile.setOnClickListener(v ->
        {
            startActivityForResult(new Intent(SingletonContext.getInstance().getContext(), UserProfileActivity.class), 100);
        });

        rlMyPredict.setOnClickListener(v ->
        {
            startActivityForResult(new Intent(SingletonContext.getInstance().getContext(), MyPredictsActivity.class), 100);
        });
        rlMySupport.setOnClickListener(v ->
        {
            startActivityForResult(new Intent(SingletonContext.getInstance().getContext(), MySupportActivity.class), 100);
        });

        rlMyFavorite.setOnClickListener(v ->
        {
          //  startActivityForResult(new Intent(SingletonContext.getInstance().getContext(), SuggestionsFragment.class), 100);

        });
        tvInviteCode.setOnClickListener(v ->
        {
//Prefs.getString("keyInvite", "")
            sendRequestInvite();

        });

        rlExit.setOnClickListener(v ->
        {
            MessageAlertDialog dialog = new MessageAlertDialog(this, "", "آیا می خواهید از حساب کاربری خود خارج شوید؟",
                    true, "خروج", "انصراف", MessageAlertDialog.TYPE_MESSAGE, new MessageAlertDialog.OnConfirmListener()
            {
                @Override
                public void onConfirmClick()
                {
                    Intent intent = new Intent();
                    String mobile = Prefs.getString("mobile", "");
                    Prefs.clear();
                    Prefs.putString("mobile", mobile);
                    Prefs.putBoolean("intro", false);
                    finish();
                    intent.setClass(getApplication(), LoginActivity.class);
                    startActivity(intent);
                }

                @Override
                public void onCancelClick()
                {

                }
            });
            dialog.show(getFragmentManager(), "messageDialog");
        });

    }

    private void sendRequestInvite()
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
                        if (Tools.isNetworkAvailable(MyProfileActivity.this))
                        {
                            showError(MyProfileActivity.this, "خطا در دریافت اطلاعات از سرور!");
                        }
                        else
                        {
                            showAlert(MyProfileActivity.this, R.string.networkErrorMessage, R.string.networkError);
                        }
                    }
                } catch (Exception e)
                {
                    if (Tools.isNetworkAvailable(MyProfileActivity.this))
                    {
                        Logger.e("-OnError-", "response.info.statusCode: " + response.info.statusCode);
                        showError(MyProfileActivity.this, "خطا در دریافت اطلاعات از سرور!");
                    }
                    else
                    {
                        // showError(MyProfileActivity.this,String.valueOf(R.string.networkErrorMessage));

                        showAlert(MyProfileActivity.this, R.string.networkErrorMessage, R.string.networkError);
                    }

                }
            }

            @Override
            public void onError(String message)
            {
                // mainView.hideLoading();
                if (Tools.isNetworkAvailable(MyProfileActivity.this))
                {
                    Logger.e("-OnError-", "Error: " + message);
                    showError(MyProfileActivity.this, "خطا در دریافت اطلاعات از سرور!");
                }
                else
                {
                    // showError(MyProfileActivity.this,String.valueOf(R.string.networkErrorMessage));

                    showAlert(MyProfileActivity.this, R.string.networkErrorMessage, R.string.networkError);
                }
            }
        });
    }

    @Subscribe
    public void getHeaderContent(HeaderModel headerModel)
    {
        if (headerModel.getPopularNo() != 0)
        {
            tvHeaderPopularNo.setText(String.valueOf(headerModel.getPopularNo()));
        }
        tvUserName.setText(TrapConfig.HEADER_USER_NAME);
        if (Prefs.getString("FULLName", "").trim().equalsIgnoreCase(""))
        {
            tvFullName.setText(Prefs.getString("mobile", ""));
            tvMobile.setVisibility(View.GONE);
        }
        else
        {
            tvFullName.setText(Prefs.getString("FULLName", ""));
        }

        try
        {
            Logger.e("EventBus ImageLink", headerModel.getProfileUrl());
            if (headerModel.getProfileUrl() != null)
            {
                if (!Prefs.getString("profileImage", "").contains("default_avatar.png"))
                {
                    Picasso.with(this).load(headerModel.getProfileUrl()).into(imgProfile);
                }
                else
                {
                    Picasso.with(this).load(R.drawable.ic_user_default).into(imgProfile);
                }
            }
        } catch (Exception e)
        {
            Logger.e("-Exception photo-", e.getMessage());
            e.printStackTrace();
        }
    }


    @Override
    public void onDestroy()
    {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
        {
            Intent returnIntent = new Intent();

            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }
    }
}
