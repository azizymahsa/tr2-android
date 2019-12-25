package com.traap.traapapp.ui.fragments.support;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.pixplicity.easyprefs.library.Prefs;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
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
import com.traap.traapapp.ui.fragments.billPay.BillFragment;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.utilities.Logger;
import com.traap.traapapp.utilities.Tools;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;


public class SupportFragment
        extends BaseFragment implements View.OnClickListener
{


    private View view;
    private Toolbar mToolbar;
    private LinearLayout tvPhone, tvSms, tvEmail;
    private TextView tvTitle, tvUserName, tvPopularPlayer;
    private View imgBack, imgMenu, rlShirt;
    private MainActionView mainView;
    private String KEY_PHONE = "phone";
    private String KEY_SMS = "sms";
    private String KEY_EMAIL = "email";
    private TextView txPhone, txSms, txEmail;
    private String keyContact;

    @Nullable
    private String phone;
    private String sms, email;
    private ResultContactInfo item;


    public static SupportFragment newInstance(MainActionView mainView)
    {
        SupportFragment f = new SupportFragment();
        f.setMainView(mainView);
        return f;
    }

    private void setMainView(MainActionView mainView)
    {
        this.mainView = mainView;
    }

    public SupportFragment()
    {
    }


    public static SupportFragment newInstance()
    {
        SupportFragment fragment = new SupportFragment();


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
            tvPhone = view.findViewById(R.id.tvPhone);
            tvSms = view.findViewById(R.id.tvSms);
            tvEmail = view.findViewById(R.id.tvEmail);

            txPhone = view.findViewById(R.id.txPhone);
            txEmail = view.findViewById(R.id.txEmail);
            txSms = view.findViewById(R.id.txSms);


            tvPhone.setOnClickListener(this);
            tvSms.setOnClickListener(this);
            tvEmail.setOnClickListener(this);
            //toolbar
            mToolbar = view.findViewById(R.id.toolbar);
            tvUserName = mToolbar.findViewById(R.id.tvUserName);
            TextView tvTitle = mToolbar.findViewById(R.id.tvTitle);
            tvTitle.setText("ارتباط با پشتیبانی");
            mToolbar.findViewById(R.id.imgBack).setOnClickListener(v -> mainView.backToMainFragment());

            tvUserName.setText(TrapConfig.HEADER_USER_NAME);
            rlShirt = mToolbar.findViewById(R.id.rlShirt);
            rlShirt.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    startActivity(new Intent(SingletonContext.getInstance().getContext(), MyProfileActivity.class));
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

            imgMenu = view.findViewById(R.id.imgMenu);

            imgMenu.setOnClickListener(v -> mainView.openDrawer());
            imgBack = view.findViewById(R.id.imgBack);
            imgBack.setOnClickListener(v ->
            {
                getActivity().onBackPressed();
            });

            tvPopularPlayer = mToolbar.findViewById(R.id.tvPopularPlayer);
            tvPopularPlayer.setText(String.valueOf(Prefs.getInt("popularPlayer", 12)));

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
                //    Tools.showToast(getActivity(), message, R.color.red);
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

            if (keyContact.equals(KEY_PHONE))
            {
                txPhone.setText(item.getTitle());
                phone = item.getValue();

            } else if (keyContact.equals(KEY_SMS))
            {

                txSms.setText(item.getTitle());
                sms = item.getValue();

            } else if (keyContact.equals(KEY_EMAIL))
            {

                txEmail.setText(item.getTitle());
                email = item.getValue();

            }
        }

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
        view = inflater.inflate(R.layout.fragment_support, container, false);


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

            case R.id.tvPhone:

//                Intent intent2 = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                Intent intent2;
                try
                {
                    if (phone == null)
                    {
                        phone = "021-4855";
                    }
                }
                catch (NullPointerException e)
                {
                    phone = "021-4855";
                }
                intent2 = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
                startActivity(intent2);

                break;
            case R.id.tvEmail:


                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:" + email));
                //                emailIntent.setData(Uri.parse("mailto:info@traap.com"));
                startActivity(emailIntent);

                break;
            case R.id.tvSms:
                try
                {

                    Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                    smsIntent.setType("vnd.android-dir/mms-sms");
                    smsIntent.putExtra("address", sms);
                    smsIntent.putExtra("sms_body", "");
                    startActivity(smsIntent);
                } catch (android.content.ActivityNotFoundException anfe)
                {
                    Log.d("Error", "Error");
                }

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

