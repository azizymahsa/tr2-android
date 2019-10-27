package ir.trap.tractor.android.ui.activities.card.add;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.bumptech.glide.Glide;
import com.farazpardazan.accubin.AccubinActivity;
import com.farazpardazan.accubin.AccubinConfiguration;
import com.farazpardazan.accubin.core.scanResult.BarcodeScanResult;
import com.farazpardazan.accubin.core.scanResult.CardScanResult;
import com.farazpardazan.accubin.core.scanResult.ScanResult;
import com.squareup.picasso.Picasso;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import br.com.simplepass.loading_button_lib.interfaces.OnAnimationEndListener;
import io.realm.Realm;
import ir.trap.tractor.android.R;
import ir.trap.tractor.android.conf.TrapConfig;
import ir.trap.tractor.android.apiServices.model.getBankList.response.Bank;
import ir.trap.tractor.android.models.dbModels.BankDB;
import ir.trap.tractor.android.singleton.SingletoneBankDetail;
import ir.trap.tractor.android.ui.activities.card.add.request.AddCardServiceImpl;
import ir.trap.tractor.android.ui.base.BaseActivity;
import ir.trap.tractor.android.utilities.Logger;
import ir.trap.tractor.android.utilities.Tools;
import ru.kolotnev.formattedittext.MaskedEditText;

public class AddCardActivity extends BaseActivity implements View.OnClickListener, PinEntryEditText.OnPinEnteredListener,
        OnAnimationEndListener, AddCardView, TextWatcher
{
    private Realm realm;

    private ImageView imgBack;
    private CardView cvBarcode;
    private PinEntryEditText codeView1, codeView2, codeView3, codeView4, codeView5, codeView6;
    private CircularProgressButton btnConfirm;
    private String fullName, expireMonth, expireYear;
    private StringBuilder cardNumber = new StringBuilder();
    private EditText etFullName;
    private ImageView ivBankLogo,ivBackCard;
    private AddCardPresenterImpl presenter;
    private TextView tvBankName,tvExpireText;
    private MaskedEditText etNumberAddCard, etExpireDateAddCard;
    private boolean isFirstTimeChange = true;
    private CardView cvAddCard;
    boolean isHappy = false;
    private LinearLayout llExpireDate, llLoading;
    private LinearLayout rlRoot;
    private final int SCAN_REQUEST_CODE = 100;
    private AccubinConfiguration configuration;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        realm = Realm.getDefaultInstance();

        initView();
    }

    private void initAccubin()
    {
        configuration = new AccubinConfiguration();

        configuration.setDisplayDefaultMask(true);
        configuration.setDisplayHint(true);
        configuration.setCustomFlashIcon(R.drawable.flash);
    }

    private void initView()
    {
        ((TextView) findViewById(R.id.tvTitle)).setText("افزودن کارت");

        cvBarcode = findViewById(R.id.cvBarcode);
        tvExpireText = findViewById(R.id.tvExpireText);
        ivBackCard = findViewById(R.id.ivBackCard);
        llLoading = findViewById(R.id.llLoading);
        llExpireDate = findViewById(R.id.llExpireDate);
        etFullName = findViewById(R.id.etFullName);
        btnConfirm = findViewById(R.id.btnConfirm);
        etNumberAddCard = findViewById(R.id.etNumberAddCard);
        cvAddCard = findViewById(R.id.cvAddCard);
        etExpireDateAddCard = findViewById(R.id.etExpireDateAddCard);
        tvBankName = findViewById(R.id.tvBankName);
        ivBankLogo = findViewById(R.id.ivBankLogo);
        rlRoot = findViewById(R.id.rlRoot);
        imgBack = findViewById(R.id.imgBack);
        codeView1 = findViewById(R.id.codeView1);
        codeView2 = findViewById(R.id.codeView2);
        codeView3 = findViewById(R.id.codeView3);
        codeView4 = findViewById(R.id.codeView4);
        codeView5 = findViewById(R.id.codeView5);
        codeView6 = findViewById(R.id.codeView6);

        initAccubin();

        codeView1.setOnPinEnteredListener(this);
        codeView2.setOnPinEnteredListener(this);
        codeView3.setOnPinEnteredListener(this);
        codeView4.setOnPinEnteredListener(this);
        codeView5.setOnPinEnteredListener(this);
        codeView6.setOnPinEnteredListener(this);
        cvBarcode.setOnClickListener(this);
        etNumberAddCard.addTextChangedListener(this);
        etExpireDateAddCard.addTextChangedListener(this);
        presenter = new AddCardPresenterImpl(this, new AddCardServiceImpl(), rlRoot);

        btnConfirm.setOnClickListener(presenter);
        imgBack.setOnClickListener(presenter);
        imgBack.setVisibility(View.VISIBLE);
        btnConfirm.setText("ثبت کارت");

        llLoading.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.cvBarcode:

                Tools.showToast(this, "فعلا غیر فعال است!", R.color.red);

//                new TedPermission(AddCardActivity.this)
//                        .setPermissionListener(new PermissionListener()
//                        {
//                            @Override
//                            public void onPermissionGranted()
//                            {
//                                startActivityForResult(AccubinActivity.getIntent(getApplicationContext(),
//                                        "4GTR62HFVOPVKH9T0PMKYUY0OQIXCE4VGRHH458ALRP", configuration), SCAN_REQUEST_CODE);
//                            }
//
//                            @Override
//                            public void onPermissionDenied(ArrayList<String> deniedPermissions)
//                            {
//                            }
//
//
//                        })
//                        .setDeniedMessage("If you reject permission,you can not use this application, Please turn on permissions at [Setting] > [Permission]")
//                        .setPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                        .check();

                break;

        }

    }

    @Override
    public void onPinEntered(CharSequence str)
    {
        if (str.length() == 4 && codeView1.isFocused())
        {
            codeView2.requestFocus();
            cardNumber.append(codeView1.getText().toString());
            return;
        }

        if (str.length() == 4 && codeView2.isFocused())
        {
            codeView3.requestFocus();
            cardNumber.append(codeView2.getText().toString());
            return;
        }
        if (str.length() == 4 && codeView3.isFocused())
        {
            codeView4.requestFocus();
            cardNumber.append(codeView3.getText().toString());
            return;
        }
        if (str.length() == 4 && codeView4.isFocused())
        {
            codeView5.requestFocus();
            cardNumber.append(codeView4.getText().toString());
            return;
        }
        if (str.length() == 2 && codeView5.isFocused())
        {
            codeView6.requestFocus();
            expireYear = codeView5.getText().toString();
            return;
        }
        if (str.length() == 2 && codeView6.isFocused())
        {
            expireMonth = codeView6.getText().toString();
            etFullName.requestFocus();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {


        if (requestCode == SCAN_REQUEST_CODE)
        {
            if (data != null)
            {
                ScanResult scanResult = ((ScanResult) data.getSerializableExtra(AccubinActivity.EXTRA_SCAN_RESULT));

                if (scanResult instanceof BarcodeScanResult)
                {

                    // Do whatever you need with barcode data
                    String barcodeFormat = ((BarcodeScanResult) scanResult).getBarcodeFormat();
                    String barcodeText = ((BarcodeScanResult) scanResult).getStringValue();
                }
                else
                {
                    String cardPan = ((CardScanResult) scanResult).getNumber();
                    String date = ((CardScanResult) scanResult).getExpirationDate();

                    try
                    {
                        etNumberAddCard.setText(cardPan);
                        String bankDetect = cardPan.replaceAll("-", "").
                                replaceAll("_", "").replaceAll(" ", "").substring(0, 6);

                        if (bankDetect.equals("003725"))
                        {
                            llExpireDate.setVisibility(View.GONE);
                            isHappy = true;
                        } else
                        {
                            llExpireDate.setVisibility(View.VISIBLE);
                            isHappy = false;

                        }
                        isFirstTimeChange = false;
//                        for (Bank detail : SingletoneBankDetail.getInstance().getBanks())
//                        {
//                            if (detail.getBankBin().equals(bankDetect))
//                            {
//                                etNumberAddCard.setTextColor(Color.parseColor(detail.getColorText()));
//
//                                tvBankName.setText(detail.getBankName());
//                                tvBankName.setBackgroundColor(Color.WHITE);
//                                ivBankLogo.setBackgroundColor(Color.WHITE);
////                                Glide.with(this).load(detail.getImageLogo()).into(ivBankLogo);
//                                Glide.with(this).load(detail.getImageCard()).into(ivBackCard);
//                                etFullName.setTextColor(Color.parseColor(detail.getColorText()));
//                                etExpireDateAddCard.setTextColor(Color.parseColor(detail.getColorText()));
//                                tvExpireText.setTextColor(Color.parseColor(detail.getColorText()));
//                                etFullName.setHintTextColor(Color.parseColor(detail.getColorText()));
//                            }
//                        }
                    }
                    catch (Exception e)
                    {
                    }
                    try
                    {
                        etExpireDateAddCard.setText(date);


                    }
                    catch (Exception e)
                    {
                    }
                }
            }
        }
    }


    public void getFullName(String pan)
    {
//        llLoading.setVisibility(View.VISIBLE);
//
//        GetFullNameCardRequest request = new GetFullNameCardRequest();
//        request.setPan(pan);
//        request.setUserId(Prefs.getInt("userId", 0));
//        SingletonService.getInstance().getAddCard().getFullNameCard(new OnServiceStatus<GetFullNameCardResponse>()
//        {
//            @Override
//            public void onReady(GetFullNameCardResponse getFullNameCardResponse)
//            {
//                try
//                {
//                    llLoading.setVisibility(View.GONE);
//
//
//                    if (getFullNameCardResponse.getServiceMessage().getCode() == 200)
//                    {
//                        etFullName.setText(getFullNameCardResponse.getFirstName() + " " + getFullNameCardResponse.getLastName());
//
//                    } else
//                    {
//                        AddCardActivity.this.onError(getFullNameCardResponse.getServiceMessage().getMessage(), this.getClass().getSimpleName(), DibaConfig.showClassNameInMessage);
//
//                    }
//
//                } catch (Exception e)
//                {
//                    AddCardActivity.this.onError(e.getMessage(), this.getClass().getSimpleName(), DibaConfig.showClassNameInException);
//
//                }
//
//            }
//
//            @Override
//            public void onError(String message)
//            {
//                llLoading.setVisibility(View.GONE);
//                AddCardActivity.this.onError(message, this.getClass().getSimpleName(), DibaConfig.showClassNameInException);
//
//            }
//        }, request);


    }

    @Override
    public void onAnimationEnd()
    {
        btnConfirm.setBackground(ContextCompat.getDrawable(this, R.drawable.background_border_a));

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

        String[] date = etExpireDateAddCard.getText().toString().replaceAll(" ", "").split("/");

        presenter.setCardDetail(date[1], etNumberAddCard.getText().toString().replaceAll("-", "").replaceAll("_", "").replaceAll(" ", ""), date[0], etFullName.getText().toString(), 0, false);
    }

    @Override
    public void onSuccess(String message)
    {
        showToast(this, message, R.color.green);

    }

    @Override
    public void onError(String message)
    {
        Tools.showToast(this, message, R.color.red);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
    {


    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
    {

    }

    @Override
    public void afterTextChanged(Editable editable)
    {

        String bankDetect = etNumberAddCard.getText().toString().replaceAll("-", "").
                replaceAll("_", "").replaceAll(" ", "");

        Logger.e("--bankDetect--", bankDetect);

        if (etNumberAddCard.isFocused() && bankDetect.length() < 6)
        {
            isFirstTimeChange = true;

            tvBankName.setText("");
            tvBankName.setBackgroundColor(Color.TRANSPARENT);
            ivBankLogo.setBackgroundColor(Color.TRANSPARENT);
            llExpireDate.setVisibility(View.VISIBLE);
            ivBankLogo.setImageDrawable(null);
            ivBackCard.setImageDrawable(null);
            cvAddCard.setCardBackgroundColor(getResources().getColor(R.color.cardview_dark_background));
            etNumberAddCard.setTextColor(getResources().getColor(R.color.textColorTitle));
            etFullName.setTextColor(getResources().getColor(R.color.textColorTitle));
            etFullName.setHintTextColor(getResources().getColor(R.color.textColorTitle));
            etExpireDateAddCard.setTextColor(getResources().getColor(R.color.textColorTitle));
            tvExpireText.setTextColor(getResources().getColor(R.color.textColorTitle));
        }
        if (etNumberAddCard.isFocused() && bankDetect.length() == 6 && isFirstTimeChange)
        {
            if (bankDetect.equals(TrapConfig.HappyBaseCardNo))
            {
                llExpireDate.setVisibility(View.GONE);
                isHappy = true;
            }
            else
            {
                llExpireDate.setVisibility(View.VISIBLE);
                isHappy = false;
            }
            isFirstTimeChange = false;
            //-------------------------old-------------------------
//            for (Bank detail : SingletoneBankDetail.getInstance().getBanks())
//            {
//                if (detail.getBankBin().equals(bankDetect))
//                {
//
//                    tvBankName.setText(detail.getBankName());
//                    tvBankName.setBackgroundColor(Color.WHITE);
//                    ivBankLogo.setBackgroundColor(Color.WHITE);
////                    Glide.with(this).load(detail.getImageLogo()).into(ivBankLogo);
//                    Glide.with(this).load(detail.getImageCard()).into(ivBackCard);
//                    etNumberAddCard.setTextColor(Color.parseColor(detail.getColorNumber()));
//                    etFullName.setTextColor(Color.parseColor(detail.getColorText()));
//                    etExpireDateAddCard.setTextColor(Color.parseColor(detail.getColorText()));
//                    tvExpireText.setTextColor(Color.parseColor(detail.getColorText()));
//                    etFullName.setHintTextColor(Color.parseColor(detail.getColorText()));
//
//                }
//            }
            //-------------------------old-------------------------
            //-------------------------new-------------------------

            BankDB bankDB = realm.where(BankDB.class)
                    .equalTo("bankBin", bankDetect)
                    .findFirst();

            tvBankName.setText(bankDB.getBankName());
            tvBankName.setBackgroundColor(Color.TRANSPARENT);
            ivBankLogo.setBackgroundColor(Color.TRANSPARENT);
            cvAddCard.setCardBackgroundColor(Color.TRANSPARENT);
            Picasso.with(this).load(bankDB.getImageCard()).into(ivBackCard);

            etNumberAddCard.setTextColor(Color.parseColor(bankDB.getColorNumber()));
            etFullName.setTextColor(Color.parseColor(bankDB.getColorText()));
            etExpireDateAddCard.setTextColor(Color.parseColor(bankDB.getColorText()));
            tvExpireText.setTextColor(Color.parseColor(bankDB.getColorText()));
            etFullName.setHintTextColor(Color.parseColor(bankDB.getColorText()));
            //-------------------------new-------------------------
        }


        if (etNumberAddCard.isFocused() && bankDetect.length() == 16)
        {
            if (isHappy)
                etFullName.requestFocus();
            else
                etExpireDateAddCard.requestFocus();
        }
        if (etExpireDateAddCard.isFocused() && etExpireDateAddCard.getText().toString().replaceAll("_", "").replaceAll(" ", "").replaceAll("/", "").length() == 4)
        {
            etFullName.requestFocus();

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
