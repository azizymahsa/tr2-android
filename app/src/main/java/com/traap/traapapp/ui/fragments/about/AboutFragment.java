package com.traap.traapapp.ui.fragments.about;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.pixplicity.easyprefs.library.Prefs;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.helper.Const;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.contactInfo.GetContactInfoRequest;
import com.traap.traapapp.apiServices.model.contactInfo.GetContactInfoResponse;
import com.traap.traapapp.apiServices.model.contactInfo.ResultContactInfo;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.models.otherModels.headerModel.HeaderModel;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.ui.activities.myProfile.MyProfileActivity;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.utilities.Logger;
import com.traap.traapapp.utilities.Tools;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;


public class AboutFragment
        extends BaseFragment implements View.OnClickListener
{


    private View view;
    private Toolbar mToolbar;
    private TextView tvTitle, tvUserName, tvPhone, tvPopularPlayer;
    private View imgBack, imgMenu;
    private MainActionView mainView;
    private ImageView ivInsta, ivTwit, ivTele, ivWeb;
    private CircularProgressButton btnHistory;
    private String KEY_PHONE = "phone";
    private String KEY_SMS = "sms";
    private String KEY_EMAIL = "email";
    private String KEY_TWITTER = "twitter";
    private String KEY_INSTAGRAM = "instagram";
    private String KEY_WEBSITE = "website";
    private String KEY_TELEGRAM = "telegram";
    private ResultContactInfo item;
    private String keyContact;
    private String website;
    private String twitter;
    private String telegram;
    private String instagram;
    private int count=0;
    private View ivImage,rlShirt;


    public static AboutFragment newInstance(MainActionView mainView)
    {
        AboutFragment f = new AboutFragment();
        f.setMainView(mainView);
        return f;
    }

    private void setMainView(MainActionView mainView)
    {
        this.mainView = mainView;
    }

    public AboutFragment()
    {
    }


    public static AboutFragment newInstance()
    {
        AboutFragment fragment = new AboutFragment();


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
            ivWeb = view.findViewById(R.id.ivWeb);
            ivTwit = view.findViewById(R.id.ivTwit);
            ivTele = view.findViewById(R.id.ivTele);
            ivInsta = view.findViewById(R.id.ivInsta);
            btnHistory = view.findViewById(R.id.btnHistory);
            tvPhone = view.findViewById(R.id.tvPhone);
            ivImage=view.findViewById(R.id.ivImage);
            ivImage.setOnClickListener(this);
            ivWeb.setOnClickListener(this);
            ivTwit.setOnClickListener(this);
            ivTele.setOnClickListener(this);
            ivInsta.setOnClickListener(this);
            btnHistory.setOnClickListener(this);
            tvPhone.setOnClickListener(this);
            //toolbar
            mToolbar = view.findViewById(R.id.toolbar);
            tvUserName = mToolbar.findViewById(R.id.tvUserName);
            TextView tvTitle = mToolbar.findViewById(R.id.tvTitle);
            tvTitle.setText("درباره ما");
            FrameLayout flLogoToolbar = mToolbar.findViewById(R.id.flLogoToolbar);
            flLogoToolbar.setOnClickListener(v -> {
                mainView.backToMainFragment();

            });

            mToolbar.findViewById(R.id.imgBack).setOnClickListener(v -> mainView.backToMainFragment());

            tvUserName.setText(TrapConfig.HEADER_USER_NAME);

            mToolbar.findViewById(R.id.imgMenu).setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    mainView.openDrawer();
                }
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
            rlShirt = mToolbar.findViewById(R.id.rlShirt);
            rlShirt.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    startActivityForResult(new Intent(SingletonContext.getInstance().getContext(), MyProfileActivity.class),100);
                }
            });
            requestGetContactInfo();


        } catch (Exception e)
        {
            e.getMessage();
        }
    }

    private void requestGetContactInfo()
    {
        GetContactInfoRequest request = new GetContactInfoRequest();

        SingletonService.getInstance().getContactInfoService().getContactInfoService(new OnServiceStatus<WebServiceClass<GetContactInfoResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<GetContactInfoResponse> response)
            {
                try
                {
                    hideLoading();
                    if (response.info.statusCode == 200)
                    {

                        onGetContactInfoSuccess(response.data.getResultContactInfos());

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
                hideLoading();
                if (Tools.isNetworkAvailable(getActivity()))
                {
                    Logger.e("-OnError-", "Error: " + message);
                    showError(getActivity(), "خطا در دریافت اطلاعات از سرور!");
                } else
                {
                    showAlert(getActivity(), R.string.networkErrorMessage, R.string.networkError);
                }
            }
        }, request);

    }

    private void onGetContactInfoSuccess(List<ResultContactInfo> resultContactInfos)
    {
        for (int i = 0; i < resultContactInfos.size(); i++)
        {
            item = resultContactInfos.get(i);
            keyContact = resultContactInfos.get(i).getKey();

            if (keyContact.equals(KEY_WEBSITE))
            {
                website = item.getValue();

            } else if (keyContact.equals(KEY_TWITTER))
            {

                twitter = item.getValue();

            } else if (keyContact.equals(KEY_TELEGRAM))
            {

                telegram = item.getValue();

            } else if (keyContact.equals(KEY_INSTAGRAM))
            {

                instagram = item.getValue();

            }
        }
    }

    private void showLoading()
    {
        mainView.showLoading();
    }

    private void hideLoading()
    {
        mainView.hideLoading();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_about, container, false);
        PackageInfo pInfo = null;
        try
        {
            pInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }

        ((TextView) view.findViewById(R.id.tvVersion)).setText("نسخه " + pInfo.versionName);

        // initializing the views
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

            case R.id.ivImage:
                count++;
                if (count==7){
                    Tools.showToast(getActivity(), Const.BASEURL, Toast.LENGTH_LONG);
                    count=0;
                }
                break;

            case R.id.ivWeb:

                try
                {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(website));
                    startActivityForResult(browserIntent,100);
                } catch (Exception e)
                {
                    e.getMessage();
                }
                break;
            case R.id.ivTwit:
                try
                {
                    //  startActivityForResult(new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=" + "traapapp")));
                    startActivityForResult(new Intent(Intent.ACTION_VIEW, Uri.parse(twitter)),100);
                } catch (Exception e)
                {
                    //startActivityForResult(new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/" + "traapapp")));
                    // startActivityForResult(new Intent(Intent.ACTION_VIEW, Uri.parse(twitter)));
                }
                break;
            case R.id.ivTele:
                try
                {
                    // Intent telegram = new Intent(Intent.ACTION_VIEW, Uri.parse("https://telegram.me/@traapapp"));
                    Intent telegramIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(telegram));
                    startActivityForResult(telegramIntent,100);
                } catch (Exception e)
                {
                    e.getMessage();
                }
                break;
            case R.id.ivInsta:
                // Uri uri = Uri.parse("http://instagram.com/traapapp");
                Uri uri = Uri.parse(instagram);
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

                likeIng.setPackage("com.instagram.android");

                try
                {
                    startActivityForResult(likeIng,100);
                } catch (ActivityNotFoundException e)
                {
                   /* startActivityForResult(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://instagram.com/traapapp")));*/
                    startActivityForResult(new Intent(Intent.ACTION_VIEW,
                            Uri.parse(instagram)),100);
                }
                break;
            case R.id.btnHistory:
                HistoryFragment historyFragment = new HistoryFragment(mainView);

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_container, historyFragment).commit();
                break;
            case R.id.tvPhone:
                if (tvPhone.getText().toString() == null)
                {//44890412
                    Intent intent2 = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", "021-44890412", null));
                    startActivityForResult(intent2,100);
                } else
                {
                    Intent intent2 = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", "021-44890412", null));
                    startActivityForResult(intent2,100);
                }/*else {
                    String[] phone = tvPhone.getText().toString().split("\r\n");
                    String phoneCall = phone[0];
                    Log.e("phone", phoneCall+"" );
                    Intent intent2 = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel",phoneCall, null));
                    startActivityForResult(intent2);
                }*/
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

