package com.traap.traapapp.ui.fragments.suggestions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;

import com.google.android.material.textfield.TextInputLayout;
import com.pixplicity.easyprefs.library.Prefs;
import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.contact.OnSelectContact;
import com.traap.traapapp.apiServices.model.doTransfer.DoTransferWalletRequest;
import com.traap.traapapp.apiServices.model.doTransfer.DoTransferWalletResponse;
import com.traap.traapapp.apiServices.model.getBalancePasswordLess.GetBalancePasswordLessRequest;
import com.traap.traapapp.apiServices.model.getInfoWallet.GetInfoWalletResponse;
import com.traap.traapapp.apiServices.model.suggestions.RequestSuggestions;
import com.traap.traapapp.apiServices.model.suggestions.ResponseSuggestions;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.models.otherModels.headerModel.HeaderModel;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.ui.activities.myProfile.MyProfileActivity;
import com.traap.traapapp.ui.activities.paymentResult.PaymentResultIncreaseInventoryActivity;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.dialogs.MoneyTransferAlertDialog;
import com.traap.traapapp.ui.fragments.gateWay.WalletTitle;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.utilities.ClearableEditText;
import com.traap.traapapp.utilities.ConvertPersianNumberToString;
import com.traap.traapapp.utilities.Tools;
import com.traap.traapapp.utilities.Utility;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Objects;

/**
 * Created by MahsaAzizi on 26/05/2020.
 */
public class SuggestionsFragment extends BaseFragment implements View.OnClickListener
{


    private View rootView, btnConfirm;
    private MainActionView mainView;

    private Spinner spinnerType;
    private String[] ListType = {"انتقادات", "پیشنهادات", "درخواست", "شکایات"};
    private String strType = "";
    private Integer type = 0;
    
    private Integer TYPE_Critic = 0;
    private Integer TYPE_recommandation = 1;
    private Integer TYPE_request = 2;
    private Integer TYPE_complaint = 3;
    private String userName;
    private TextInputLayout inputLayout3;
    private AppCompatEditText etComment;
    private View view;
    private Toolbar mToolbar;
    private LinearLayout llEditInvite;
    private View rlShirt, imgMenu, imgBack;
    private TextView tvTraapTitle, tvUserName, tvPopularPlayer;

    public SuggestionsFragment()
    {

    }

    public static SuggestionsFragment newInstance(MainActionView mainView)
    {
        SuggestionsFragment f = new SuggestionsFragment();
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
        rootView = inflater.inflate(R.layout.fragment_suggestions, container, false);

        initView();

        // WalletFragment.setTitleFragmentWallet("انتقال وجه");
        //spinner
        onGetBoutForSuccess();

       /* WalletTitle walletTitle = new WalletTitle();
        walletTitle.setTitle("انتقادات و پیشنهادات");

        EventBus.getDefault().post(walletTitle);*/


        return rootView;
    }

    @Override
    public void onStop()
    {
        super.onStop();

    }

    private void initView()
    {

        //toolbar
        mToolbar = rootView.findViewById(R.id.toolbar);
        tvUserName = mToolbar.findViewById(R.id.tvUserName);
        TextView tvTitle = mToolbar.findViewById(R.id.tvTitle);
        tvTitle.setText("انتقادوپیشنهاد");
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
        imgMenu = mToolbar.findViewById(R.id.imgMenu);

        imgMenu.setOnClickListener(v -> mainView.openDrawer());
        imgBack = mToolbar.findViewById(R.id.imgBack);
        imgBack.setOnClickListener(v ->
        {
            getActivity().onBackPressed();
        });

        tvPopularPlayer = mToolbar.findViewById(R.id.tvPopularPlayer);
        tvPopularPlayer.setText(String.valueOf(Prefs.getInt("popularPlayer", 12)));


        //     ViewCompat.setLayoutDirection(inputLayout3, ViewCompat.LAYOUT_DIRECTION_RTL);

        spinnerType = rootView.findViewById(R.id.spinnerType);
        etComment = rootView.findViewById(R.id.etComment);


        btnConfirm = rootView.findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(this);


    }

    private void onGetBoutForSuccess()
    {
        ArrayAdapter<String> adapterAmount = new ArrayAdapter<String>(getActivity(),
                R.layout.simple_spinner_item, ListType);
        adapterAmount.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        spinnerType.setAdapter(adapterAmount);
        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {

                strType = adapterAmount.getItem(position);
                setTypeLayout(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });
    }


    @Subscribe
    public void getHeaderContent(HeaderModel headerModel)
    {

    }


    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {

            case R.id.btnConfirm:

                onConfirmClicked();
                break;

        }
    }

    private void onConfirmClicked()
    {
        if (etComment.getText().toString().length() > 3)
        {
            requestDoTransfer(etComment.getText().toString(), strType);
        } else
        {
            showError(getContext(), "متن نظر یا پیشنهادات نباید خالی باشد!");

        }

    }


    private void requestDoTransfer(String description, String type)
    {
        mainView.showLoading();
        RequestSuggestions request = new RequestSuggestions();

        request.setDescription(description);
        request.setType(type);
        SingletonService.getInstance().tractorTeamService().postSuggestions(new OnServiceStatus<WebServiceClass<ResponseSuggestions>>()
        {


            @Override
            public void onReady(WebServiceClass<ResponseSuggestions> response)
            {

                try
                {
                    mainView.hideLoading();

                    if (response.info.statusCode == 200)
                    {

                        showAlertSuccess(getContext(), response.info.message, "", false);
                    } else
                    {
                        showAlertFailure(getContext(), response.info.message, "", false);


                    }
                } catch (Exception e)
                {
                    mainView.hideLoading();
                    showAlertFailure(getContext(), e.getMessage(), "", false);


                }


            }

            @Override
            public void onError(String message)
            {

                mainView.hideLoading();

                if (Tools.isNetworkAvailable(Objects.requireNonNull(getActivity())))
                {
                    showAlertFailure(getContext(), message, "", false);


                } else
                {
                    showAlertFailure(getContext(), getString(R.string.networkErrorMessage), "", false);


                }

            }
        }, request);
    }


    private void setTypeLayout(Integer position)
    {

        if (position == TYPE_Critic)
        {

            type = TYPE_Critic;
            strType = "critic";


        } else if (position == TYPE_recommandation)
        {

            type = TYPE_recommandation;
            strType = "recommandation";


        } else if (position == TYPE_request)
        {

            type = TYPE_request;
            strType = "request";


        } else if (position == TYPE_complaint)
        {

            type = TYPE_complaint;
            strType = "complaint";


        }
    }


}
