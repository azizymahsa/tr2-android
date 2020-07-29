package com.traap.traapapp.ui.activities.card.add;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
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
import com.squareup.picasso.Picasso;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import br.com.simplepass.loading_button_lib.interfaces.OnAnimationEndListener;
import io.realm.Realm;
import com.traap.traapapp.R;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.models.dbModels.BankDB;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.ui.activities.card.add.request.AddCardServiceImpl;
import com.traap.traapapp.ui.activities.myProfile.MyProfileActivity;
import com.traap.traapapp.ui.base.BaseActivity;
import com.traap.traapapp.utilities.Logger;
import com.traap.traapapp.utilities.Tools;

import java.util.Objects;

import ru.kolotnev.formattedittext.MaskedEditText;

public class AddCardActivity extends BaseActivity implements OnAnimationEndListener, AddCardView
{
//    private Realm realm;
    private Toolbar mToolbar;

    private TextView tvTitle, tvUserName;
    private ImageView imgMenu;
    private RelativeLayout imgBack;

    private CircularProgressButton btnConfirm;
    private EditText edtFullName, edtNumberCardEdit, edtExpYear, edtExpMound;
    private AddCardPresenterImpl presenter;
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

        presenter = new AddCardPresenterImpl(this, new AddCardServiceImpl(), rlRoot);

//        btnConfirm.setOnClickListener(presenter);
        btnConfirm.setOnClickListener(view ->
        {
           if (setError())
           {

           }
        });
        imgBack.setVisibility(View.VISIBLE);
        btnConfirm.setText("ثبت کارت");

//        llLoading.setVisibility(View.GONE);
    }

    private boolean setError()
    {
        return false;
    }

    @Override
    public void onAnimationEnd()
    {
        btnConfirm.setBackground(ContextCompat.getDrawable(this, R.drawable.background_button_login));
    }


    @Override
    public void onFinish(int resultCode)
    {
        if (resultCode == Activity.RESULT_OK)
        {
            showToast(this, "کارت با موفقیت افزوده شد.", R.color.green);
        }
        Intent returnIntent = new Intent();
        setResult(resultCode, returnIntent);
        finish();
    }

    @Override
    public void getData()
    {
//        presenter.setCardDetail(etNumberAddCard.getText().toString().replaceAll("-", "").replaceAll("_", "").replaceAll(" ", ""),
//                etFullName.getText().toString(), false);
    }

    @Override
    public void onSuccess(String message)
    {
        showToast(this, message, R.color.green);
    }

    @Override
    public void onError(String message)
    {
        if (Tools.isNetworkAvailable(Objects.requireNonNull(AddCardActivity.this)))
        {
            showToast(this, "خطای ارتباط با سرور!", R.color.red);

        }
        else
        {
            Tools.showToast(this, getString(R.string.networkErrorMessage), R.color.red);


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
