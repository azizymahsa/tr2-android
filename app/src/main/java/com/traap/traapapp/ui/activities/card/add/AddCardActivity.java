package com.traap.traapapp.ui.activities.card.add;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.alimuzaffar.lib.pin.PinEntryEditText;
//import com.farazpardazan.accubin.AccubinActivity;
//import com.farazpardazan.accubin.AccubinConfiguration;
//import com.farazpardazan.accubin.core.scanResult.BarcodeScanResult;
//import com.farazpardazan.accubin.core.scanResult.CardScanResult;
//import com.farazpardazan.accubin.core.scanResult.ScanResult;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import br.com.simplepass.loading_button_lib.interfaces.OnAnimationEndListener;
import io.realm.Realm;
import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.card.CardBankItem;
import com.traap.traapapp.apiServices.model.card.addCard.request.AddCardRequest;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.models.dbModels.BankDB;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.ui.activities.myProfile.MyProfileActivity;
import com.traap.traapapp.ui.base.BaseActivity;
import com.traap.traapapp.ui.base.BaseView;
import com.traap.traapapp.ui.dialogs.MessageAlertDialog;
import com.traap.traapapp.utilities.Logger;
import com.traap.traapapp.utilities.Tools;
import com.traap.traapapp.utilities.Utility;

import org.greenrobot.eventbus.EventBus;

import java.util.Objects;

import ru.kolotnev.formattedittext.MaskedEditText;

public class AddCardActivity extends BaseActivity implements OnAnimationEndListener,
//        AddCardView,
        BaseView,
        OnServiceStatus<WebServiceClass<CardBankItem>>
{
//    private Realm realm;
    private Toolbar mToolbar;

    private TextView tvTitle, tvUserName;
    private ImageView imgMenu;
    private RelativeLayout imgBack;

    private CircularProgressButton btnConfirm;
    private EditText edtFullName, edtNumberCardEdit, edtExpYear, edtExpMound;
//    private LinearLayout llLoading;
    private LinearLayout rlRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

//        realm = Realm.getDefaultInstance();

        initView();
    }

    private void initView()
    {
        tvTitle = mToolbar.findViewById(R.id.tvTitle);
        tvUserName = mToolbar.findViewById(R.id.tvUserName);
        imgBack = mToolbar.findViewById(R.id.imgBack);
        imgMenu = mToolbar.findViewById(R.id.imgMenu);

        tvTitle.setText("اضافه کردن کارت");

        tvUserName.setText(TrapConfig.HEADER_USER_NAME);

        imgMenu.setVisibility(View.GONE);
        imgBack.setOnClickListener(v ->
        {
            super.onBackPressed();
        });

        FrameLayout flLogoToolbar = findViewById(R.id.flLogoToolbar);

        flLogoToolbar.setOnClickListener(v ->
        {
            Intent returnIntent = new Intent();

            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        });

        mToolbar.findViewById(R.id.rlShirt).setOnClickListener(v ->
                startActivityForResult(new Intent(SingletonContext.getInstance().getContext(),
                MyProfileActivity.class), 100)
        );

//        llLoading = findViewById(R.id.rlLoading);
        edtNumberCardEdit = findViewById(R.id.edtNumberCardEdit);
        edtFullName = findViewById(R.id.edtFullName);
        edtExpYear = findViewById(R.id.edtExpYear);
        edtExpMound = findViewById(R.id.edtExpMound);
        btnConfirm = findViewById(R.id.btnConfirm);
        rlRoot = findViewById(R.id.rlRoot);

        btnConfirm.setOnClickListener(view ->
        {
           if (setError())
           {
               btnConfirm.startAnimation();
               btnConfirm.setClickable(false);

               AddCardRequest request = new AddCardRequest();
               request.setCardNumber(edtNumberCardEdit.getText().toString().replaceAll("-", "").replaceAll("_", ""));
               request.setFullName(edtFullName.getText().toString().trim());
               SingletonService.getInstance().addCardService().addCardService(request, this);
           }
        });
        imgBack.setVisibility(View.VISIBLE);
        btnConfirm.setText("ثبت کارت");

//        llLoading.setVisibility(View.GONE);
    }

    private boolean setError()
    {
        boolean err = true;
        String message = "";
        if (edtNumberCardEdit.getText().toString().replaceAll("-", "").replaceAll("_", "").length() != 16)
        {
            message = message + "شماره کارت نامعتبر است." + '\n';
            err = false;
        }
        else if (!Utility.CheckCartDigit(edtNumberCardEdit.getText().toString().replaceAll("-", "").replaceAll("_", "").trim()))
        {
            message = message + "شماره کارت صحیح نمی باشد." + '\n';
            err = false;
        }
        if (TextUtils.isEmpty(edtFullName.getText().toString()))
        {
            message = message + "نام و نام خانوادگی نمیتواند خالی باشد." + '\n';
            err = false;
        }
        else if (edtFullName.getText().toString().length() < 2 || Utility.containsNumber(edtFullName.getText().toString()))
        {
            message = message + "نام و نام خانوادگی نامعتبر است." + '\n';
            err = false;
        }

        if (!err)
        {
            showError(this, message);
        }

        return err;
    }

    @Override
    public void onAnimationEnd()
    {
        btnConfirm.setBackground(ContextCompat.getDrawable(this, R.drawable.background_button_login));
    }


//    @Override
//    public void onFinish(int resultCode)
//    {
//        if (resultCode == Activity.RESULT_OK)
//        {
//            showToast(this, "کارت با موفقیت افزوده شد.", R.color.green);
//        }
//        Intent returnIntent = new Intent();
//        setResult(resultCode, returnIntent);
//        finish();
//    }

//    @Override
//    public void getData()
//    {
////        presenter.setCardDetail(etNumberAddCard.getText().toString().replaceAll("-", "").replaceAll("_", "").replaceAll(" ", ""),
////                etFullName.getText().toString(), false);
//    }

//    @Override
//    public void onSuccess(String message)
//    {
//        showToast(this, message, R.color.green);
//    }

    @Override
    public void onReady(WebServiceClass<CardBankItem> response)
    {
        btnConfirm.revertAnimation();
        btnConfirm.setClickable(true);
        if (response.info.statusCode != 201)
        {
            showAlertFailure(this, response.info.message, getString(R.string.error), false);
        }
        else
        {
            MessageAlertDialog dialog = new MessageAlertDialog(this, "", response.info.message, false,
                    "تایید", "", true,
                    MessageAlertDialog.TYPE_SUCCESS, new MessageAlertDialog.OnConfirmListener()
            {
                @Override
                public void onConfirmClick()
                {
//                    Intent intent = new Intent();
//                    intent.putExtra("CardBankItem", new Gson().toJson(response.data));
//                    setResult(Activity.RESULT_OK, intent);
                    EventBus.getDefault().post(response.data);
                    finish();
                }

                @Override
                public void onCancelClick()
                {

                }
            });
            dialog.setCancelable(false);
            dialog.show(getFragmentManager(), "dialog");
        }
    }

    @Override
    public void onError(String message)
    {
        btnConfirm.revertAnimation();
        btnConfirm.setClickable(true);
        if (Tools.isNetworkAvailable(Objects.requireNonNull(AddCardActivity.this)))
        {
            showAlertFailure(this, "خطای ارتباط با سرور!", getString(R.string.error), false);
        }
        else
        {
            showAlertFailure(this, getString(R.string.networkErrorMessage), getString(R.string.error), false);
        }
    }

    @Override
    public void showLoading()
    {
        btnConfirm.startAnimation();
        btnConfirm.setClickable(false);
    }

    @Override
    public void hideLoading()
    {
        btnConfirm.revertAnimation(AddCardActivity.this);
        btnConfirm.setClickable(true);
    }
}
