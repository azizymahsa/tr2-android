package ir.traap.tractor.android.ui.fragments.moneyTransfer;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.material.textfield.TextInputLayout;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.Objects;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import br.com.simplepass.loading_button_lib.interfaces.OnAnimationEndListener;
import ir.traap.tractor.android.R;
import ir.traap.tractor.android.apiServices.model.doTransferCard.request.DoTransferRequest;
import ir.traap.tractor.android.enums.TransferType;
import ir.traap.tractor.android.ui.base.BaseFragment;
import ir.traap.tractor.android.ui.fragments.favoriteCard.FavoriteCardFragment;
import ir.traap.tractor.android.ui.fragments.favoriteCard.FavoriteCardParentActionView;
import ir.traap.tractor.android.ui.fragments.main.MainActionView;
import ir.traap.tractor.android.utilities.ClearableEditText;
import ir.traap.tractor.android.utilities.NumberTextWatcher;
import ir.traap.tractor.android.utilities.Utility;

/**
 * Created by Javad.Abadi on 7/10/2018.
 */
@SuppressLint("ValidFragment")
public class MoneyTransferFragment extends BaseFragment implements OnAnimationEndListener, View.OnClickListener,
        TextWatcher, View.OnFocusChangeListener, FavoriteCardParentActionView
//        ,OnServiceStatus<HappyDoTransferResponse>
{
    private Fragment currentFragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    private View v;
    private CircularProgressButton btnConfirm, btnContinue, btnPassConfirm, btnBackToTransfer, btnBackToDetail, btnContactTransfer, btnDestinationHistory;
    private LinearLayout llTransfer, llConfirmTransfer, llCardDetail, llCvv2, llKeshavarszi;
    private TextView tvTitle, tvCardNum, tvCardName, tvAmount, tvBankName;
    private RelativeLayout rlShare;
    private ClearableEditText etDestination, etAmount, etPass, etDestination2, etCvv2, etKeshavarszi;
    private int len = 0;
    private String pass, toUser;
    private int amount;
//    private ArchiveCardDBModel archiveCardDBModels;
    private TextInputLayout etLayoutPass, etLayoutDestination, etLayoutDestination2, etLayoutCvv, tilKeshavarszi;
    private TransferType transferType;
    private boolean needrequest = true;
    private String cardNumberCheck, holderTransactionId, cardImage, cardColor,destinationCardHolderName;
    private ImageView ivCard,tvChangeInput,tvBarcode;
    private ImageView ivLogo;
    private CheckBox cbAddFavoriteCard;
//    private List<TransferLogoVm> transferLogoVm;
    private String stna;
    private MainActionView mainView;


//    public void setTransferLogoVm(List<TransferLogoVm> transferLogoVm)
//    {
//        this.transferLogoVm = transferLogoVm;
//    }


    public MoneyTransferFragment()
    {

    }

    public static MoneyTransferFragment newInstance(MainActionView mainView)
    {
        MoneyTransferFragment f = new MoneyTransferFragment();
        f.setMainView(mainView);
        return f;
    }

    private void setMainView(MainActionView mainView)
    {
        this.mainView = mainView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
//        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        if (v != null)
        {
            v = null;
        }


        v = inflater.inflate(R.layout.fragment_transfer_money, container, false);
        initView();


        return v;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();

//        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onStop()
    {
        super.onStop();
//        EventBus.getDefault().unregister(this);


    }

    @Override
    public void onResume()
    {
        super.onResume();
        etCvv2.setText("");
        etAmount.setText("");
        etPass.setText("");
        etKeshavarszi.setText("");
    }

    @Override
    public void onPause()
    {
        super.onPause();
    }

    public void initView()
    {
        //----------------card fragment----------------
        fragmentManager = getChildFragmentManager();
        currentFragment = FavoriteCardFragment.newInstance(this);

        transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        //----------------card fragment----------------


        btnConfirm = v.findViewById(R.id.btnConfirm);
        tvBarcode = v.findViewById(R.id.tvBarcode);
        etKeshavarszi = v.findViewById(R.id.etKeshavarszi);
        tilKeshavarszi = v.findViewById(R.id.tilKeshavarszi);
        llKeshavarszi = v.findViewById(R.id.llKeshavarszi);
        cbAddFavoriteCard = v.findViewById(R.id.cbAddFavoriteCard);
        etCvv2 = v.findViewById(R.id.etCvv2);
        llCvv2 = v.findViewById(R.id.llCvv2);
        tvChangeInput = v.findViewById(R.id.tvChangeInput);
        tvTitle = v.findViewById(R.id.tvTitle);
        btnContactTransfer = v.findViewById(R.id.btnContactTransfer);
        btnDestinationHistory = v.findViewById(R.id.btnDestinationHistory);
        etDestination = v.findViewById(R.id.etDestination);
        etDestination2 = v.findViewById(R.id.etDestination2);
        etAmount = v.findViewById(R.id.etAmount);
        etPass = v.findViewById(R.id.etPass);
        ivCard = v.findViewById(R.id.ivCard);
        etLayoutPass = v.findViewById(R.id.etLayoutPass);
        tvAmount = v.findViewById(R.id.tvAmount);
        etLayoutDestination = v.findViewById(R.id.etLayoutDestination);
        etLayoutDestination2 = v.findViewById(R.id.etLayoutDestination2);

        llConfirmTransfer = v.findViewById(R.id.llConfirmTransfer);
        llCardDetail = v.findViewById(R.id.llCardDetail);
        etLayoutCvv = v.findViewById(R.id.etLayoutCvv);
        tvCardNum = v.findViewById(R.id.tvCardNum);
        tvCardName = v.findViewById(R.id.tvCardName);

        btnContinue = v.findViewById(R.id.btnContinue);
        ivLogo = v.findViewById(R.id.ivLogo);
        llTransfer = v.findViewById(R.id.llTransfer);
        btnPassConfirm = v.findViewById(R.id.btnPassConfirm);
        btnBackToTransfer = v.findViewById(R.id.btnBackToTransfer);
        btnBackToDetail = v.findViewById(R.id.btnBackToDetail);
        tvBankName = v.findViewById(R.id.tvBankName);
        btnContactTransfer.setEnabled(false);


        btnPassConfirm.setText("ادامه...");
        btnContinue.setText("تائید نهایی");
        btnBackToTransfer.setText("بازگشت");
        btnBackToDetail.setText("بازگشت");


        etAmount.addTextChangedListener(new NumberTextWatcher(etAmount));
        etDestination.addTextChangedListener(this);
        etAmount.setOnFocusChangeListener(focusListener);
        // etDestination.setOnFocusChangeListener(focusListener);


        btnConfirm.setOnClickListener(this);
        btnContinue.setOnClickListener(this);
        btnPassConfirm.setOnClickListener(this);
        btnBackToTransfer.setOnClickListener(this);
        btnBackToDetail.setOnClickListener(this);
        tvChangeInput.setOnClickListener(this);
        tvBarcode.setOnClickListener(this);
        btnContactTransfer.setOnClickListener(this);
        tvBankName.setOnClickListener(this);
        btnDestinationHistory.setOnClickListener(this);
        transferType = null;


        etLayoutPass.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/iran_sans_normal.ttf"));
        etLayoutCvv.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/iran_sans_normal.ttf"));
        tilKeshavarszi.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/iran_sans_normal.ttf"));
        btnContactTransfer.setEnabled(true);
        btnContactTransfer.setTextColor(Color.WHITE);


        try
        {

            if (!cardNumberCheck.equals("003725"))
            {
                llCvv2.setVisibility(View.VISIBLE);
                tvChangeInput.setVisibility(View.GONE);
                tvBarcode.setVisibility(View.VISIBLE);

            }
            else
            {
                etPass.setImeOptions(EditorInfo.IME_ACTION_DONE);

            }
        }
        catch (NullPointerException e)
        {

        }
        stna = "";
        etCvv2.setText("");
        etAmount.setText("");
        etPass.setText("");
        etKeshavarszi.setText("");
        btnContactTransfer.setBackgroundDrawable(getResources().getDrawable(R.drawable.copy_icon));
        btnDestinationHistory.setBackgroundDrawable(getResources().getDrawable(R.drawable.wallet_n));
    }

    @Override
    public void onAnimationEnd()
    {
/*        btnContinue.setBackground(ContextCompat.getDrawable(Objects.requireNonNull(getActivity()), R.drawable.background40));
        btnPassConfirm.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.background40));
        btnContactTransfer.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.background40));
        btnDestinationHistory.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.background40));*/


    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.btnConfirm:
                if (transferType == null)
                {
                    if (Objects.requireNonNull(etDestination.getText()).toString().replaceAll("-", "").replaceAll("_", "").length() != 16)
                    {
                        mainView.showError("لطفا شماره کارت را صحیح وارد نمایید.");
                        return;
                    }
                    if (!Utility.CheckCartDigit(etDestination.getText().toString().replaceAll("-", "").replaceAll("_", "")))
                    {
                        mainView.showError("لطفا شماره کارت را صحیح وارد نمایید.");

                        return;
                    }
                    toUser = etDestination.getText().toString().replaceAll("-", "");
                }
                else
                {
                    switch (transferType)
                    {
                        case ACCOUNT:
                            if (TextUtils.isEmpty(Objects.requireNonNull(etDestination2.getText()).toString()))
                            {
                                mainView.showError("لطفا شماره مشتری را وارد نمایید.");
                                return;
                            }
//                            if (!Utility.CheckCartDigit(etDestination2.getText().toString().replaceAll("-", "").replaceAll("_", "")))
//                            {
//                                mainView.showError("لطفا شماره کارت را صحیح وارد نمایید.", this.getClass().getSimpleName(), DibaConfig.showClassNameInMessage);
//
//                                return;
//                            }
                            toUser = etDestination2.getText().toString();


                            break;
                        case CARDNUMBER:
                            if (etDestination.getText().toString().replaceAll("-", "").replaceAll("_", "").length() != 16)
                            {
                                mainView.showError("لطفا شماره کارت را صحیح وارد نمایید.");
                                return;
                            }
                            if (!Utility.CheckCartDigit(etDestination.getText().toString().replaceAll("-", "").replaceAll("_", "")))
                            {
                                mainView.showError("لطفا شماره کارت را صحیح وارد نمایید.");

                                return;
                            }
                            toUser = etDestination.getText().toString().replaceAll("-", "");


                            break;
                        case MOBILENUMBER:
                            if (etDestination2.getText().toString().length() < 11)
                            {
                                mainView.showError("لطفا شماره تلفن همراه را صحیح وارد نمایید.");
                                return;
                            } else
                            {
                                toUser = etDestination2.getText().toString();


                            }


                            break;

                    }

                }


                if (TextUtils.isEmpty(etAmount.getText().toString()))
                {
                    mainView.showError("لطفا مبلغ را وارد نمایید.");
                    return;
                }

          /*      if (Integer.valueOf(etAmount.getText().toString().replaceAll(",", "")) < 20000) {
                    mainView.showError("حداقل مبلغ برای انتقال 20,000 ریال می باشد.");
                    return;
                }*/


                amount = Integer.valueOf(etAmount.getText().toString().replaceAll(",", ""));

                llTransfer.setVisibility(View.GONE);
                llCardDetail.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.SlideInRight)
                        .duration(200)
                        .playOn(llCardDetail);

//                if (archiveCardDBModels.isTransferSe())
//                {
//                    try
//                    {
//                        etPass.setText(AESCrypt.decrypt(Prefs.getString(SingletonDiba.getInstance().getPASS_KEY(), ""), archiveCardDBModels.getPassSe()));
//                        transferRequest(view);
//
//                    } catch (GeneralSecurityException e)
//                    {
//                        e.printStackTrace();
//                    }
//
//
//                }


                break;
            case R.id.btnPassConfirm:
                if (TextUtils.isEmpty(etPass.getText().toString()))
                {
                    Utility.hideSoftKeyboard(view, getActivity());
                    mainView.showError(getString(R.string.enter_pass));
                    return;
                }

                if (!cardNumberCheck.equals("003725"))
                    if (TextUtils.isEmpty(etCvv2.getText().toString()))
                    {
                        Utility.hideSoftKeyboard(view, getActivity());
                        mainView.showError("لطفا cvv2 را وارد نمایید");
                        return;
                    }


                if (!Utility.isNetworkAvailable())
                {
                    mainView.onInternetAlert();
                    return;
                }
                pass = etPass.getText().toString();

                transferRequest(view);

                break;
            case R.id.btnContinue:
                if (!Utility.isNetworkAvailable())
                {
                    mainView.onInternetAlert();
                    return;

                }
                if (llKeshavarszi.getVisibility() == View.VISIBLE)
                {
                    if (TextUtils.isEmpty(etKeshavarszi.getText().toString())){
                        Utility.hideSoftKeyboard(view, getActivity());
                        mainView.showError("لطفا معتبر سازی از بانک کشاورزی را وارد نمایید");
                        return;

                    }

                }

                requestDoTransfer(view);

                break;
            case R.id.btnBackToTransfer:
                llCardDetail.setVisibility(View.GONE);
                llTransfer.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.SlideInLeft)
                        .duration(200)
                        .playOn(llTransfer);

                break;
            case R.id.btnBackToDetail:
                llConfirmTransfer.setVisibility(View.GONE);
                llCardDetail.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.SlideInLeft)
                        .duration(200)
                        .playOn(llCardDetail);
                etCvv2.setText("");
               // etAmount.setText("");
                etPass.setText("");
                etKeshavarszi.setText("");
                break;
            case R.id.tvChangeInput:
//                mainView.onChangeInput();
                Utility.hideSoftKeyboard(etDestination, getActivity());
                break;
            case R.id.btnContactTransfer:
                if (!Utility.isNetworkAvailable())
                {
                    mainView.onInternetAlert();
                    return;

                }
                if (transferType == null)
                {
                    transactionsRequest();
                    return;

                }

                switch (transferType)
                {
                    case ACCOUNT:
                        break;
                    case CARDNUMBER:
                        transactionsRequest();

                        break;
                    case MOBILENUMBER:
                        mainView.onContact();

                        break;
                    default:
                        if (!needrequest)
                        {
//                            mainView.transactionsExpand();
                            return;
                        }
                        transactionsRequest();


                }
                break;
            case R.id.btnDestinationHistory:
                if (!Utility.isNetworkAvailable())
                {
                    mainView.onInternetAlert();
                    return;
                }
                destinationHistoryRequest(view);
                break;


            case R.id.tvBankName:
//                if (transferLogoVm != null)
//                {
//                    getActivity().startActivity(new Intent(getActivity(), AvailableBankActivity.class)
//                            .putExtra("transferLogoVm", new Gson().toJson(transferLogoVm)));
//                }
                break;

            case R.id.tvBarcode:
//                mainView.onBarcodeRequest();

                break;

        }

    }

    public void transferRequest(View view)
    {
//        btnPassConfirm.startAnimation();
//        btnPassConfirm.setClickable(false);
//
//        if (!cardNumberCheck.equals("003725"))
//        {
//            ShetabCardInfoRequest request = new ShetabCardInfoRequest();
//            request.setAmount(amount + "");
//            request.setPin(pass);
////            request.setPan(archiveCardDBModels.getNumber());
//            request.setExpDate(archiveCardDBModels.getExpireYear() + archiveCardDBModels.getExpireMonth());
//            request.setUserId(Prefs.getInt("userId", 0));
//            request.setDestinationPan(toUser);
//            request.setCvv2(etCvv2.getText().toString());
//
//
//            SingletonService.getInstance().shetabCardService().getShetabCardInfo(new OnServiceStatus<ShetabCardInfoResponse>()
//            {
//                @Override
//                public void onReady(ShetabCardInfoResponse response)
//                {
//
//                    try
//                    {
//                        Utility.hideSoftKeyboard(view, getActivity());
//
//                        btnPassConfirm.revertAnimation(MoneyTransferFragment.this);
//                        btnPassConfirm.setClickable(true);
//
//                        if (response.getServiceMessage().getCode() == 200)
//                        {
//
//                            GlideApp.with(getActivity()).load(response.getCardImage()).into(ivCard);
//                            //tvCardNum.setText(Utility.cardFormat(response.getCardNo()));
//
//                            try
//                            {
//                                cardImage = response.getCardImage();
//                            } catch (Exception e)
//                            {
//                            }
//
//                            try
//                            {
//                                holderTransactionId = response.getTransactionId();
//                            } catch (Exception e)
//                            {
//                            }
//
//                            try
//                            {
//                                pass = etPass.getText().toString();
//                            } catch (Exception e)
//                            {
//                            }
//
//                            try
//                            {
//                                tvCardNum.setText(Utility.cardFormat(toUser));
//                            } catch (Exception e)
//                            {
//                            }
//
//
//                            try
//                            {
//                                String firstName = "";
//                                String lastName = "";
//                                if (response.getFirstName() != null)
//                                    firstName = response.getFirstName();
//                                if (response.getLastName() != null)
//                                    lastName = response.getLastName();
//                                tvCardName.setText(firstName + " " + lastName);
//                                destinationCardHolderName=firstName + " " + lastName;
//                            } catch (Exception e)
//                            {
//                            }
//
//                            try
//                            {
//                                tvAmount.setText(etAmount.getText().toString() + " ریال");
//                            } catch (Exception e)
//                            {
//                            }
//
//                            if (archiveCardDBModels.getNumber().substring(0, 6).equals("603770"))
//                            {
//                                llKeshavarszi.setVisibility(View.VISIBLE);
//                                mainView.needExpanded(false);
//
//
//                            } else
//                            {
//                                llKeshavarszi.setVisibility(View.GONE);
//                                mainView.needExpanded(true);
//                            }
//
//
//                            llConfirmTransfer.setVisibility(View.VISIBLE);
//                            llCardDetail.setVisibility(View.GONE);
//                            YoYo.with(Techniques.SlideInRight)
//                                    .duration(200)
//                                    .playOn(llConfirmTransfer);
//                            try
//                            {
//                                tvCardNum.setTextColor(Color.parseColor(response.getColorNumber()));
//                                tvCardName.setTextColor(Color.parseColor(response.getColorText()));
//                                cardColor = response.getColorText()+"-"+response.getColorNumber();
//                            } catch (Exception e)
//                            {
//                                cardColor = "#fff";
//                            }
//
//
//                            if (response.getStna() != null)
//                                stna = response.getStna();
//
//
//                        } else
//                        {
//                            mainView.showError(response.getServiceMessage().getMessage(), this.getClass().getSimpleName(), DibaConfig.showClassNameInMessage);
//                        }
//                    } catch (Exception e)
//                    {
//                        mainView.showError(e.getMessage(), this.getClass().getSimpleName(), DibaConfig.showClassNameInException);
//                    }
//
//                }
//
//                @Override
//                public void onError(String message)
//                {
//                    Utility.hideSoftKeyboard(view, getActivity());
//
//                    mainView.showError(message, this.getClass().getSimpleName(), DibaConfig.showClassNameInException);
//                    btnPassConfirm.revertAnimation(MoneyTransferFragment.this);
//                    btnPassConfirm.setClickable(true);
//                }
//            }, request);
//
//
//        } else
//        {
//            GetCardInfoRequest request = new GetCardInfoRequest();
//            request.setPan(toUser);
//            request.setUserId(Prefs.getInt("userId", 0) + "");
//            SingletonService.getInstance().getCardInfoService().GetCardInfoService(new OnServiceStatus<GetCardInfoResponse>()
//            {
//                @Override
//                public void onReady(GetCardInfoResponse getCardResponse)
//                {
//                    try
//                    {
//                        Utility.hideSoftKeyboard(view, getActivity());
//                        mainView.needExpanded(true);
//                        btnPassConfirm.revertAnimation(MoneyTransferFragment.this);
//                        btnPassConfirm.setClickable(true);
//                        if (getCardResponse.getServiceMessage().getCode() == 200)
//                        {
//
//
//                            pass = etPass.getText().toString();
//                            tvCardNum.setText(Utility.cardFormat(getCardResponse.getCardNo()));
//                            tvCardName.setText(getCardResponse.getFullname());
//                            tvAmount.setText(etAmount.getText().toString() + " ریال");
//                            llConfirmTransfer.setVisibility(View.VISIBLE);
//                            llCardDetail.setVisibility(View.GONE);
//                            GlideApp.with(getActivity()).load(getCardResponse.getCardImage()).into(ivCard);
//                            cardImage = getCardResponse.getCardImage();
//                            cardColor = getCardResponse.getColorText()+"|"+getCardResponse.getColorNumber();
//                            YoYo.with(Techniques.SlideInRight)
//                                    .duration(200)
//                                    .playOn(llConfirmTransfer);
//                            tvCardNum.setTextColor(Color.parseColor(getCardResponse.getColorNumber()));
//                            tvCardName.setTextColor(Color.parseColor(getCardResponse.getColorText()));
//
//                        } else
//                        {
//                            mainView.showError(getCardResponse.getServiceMessage().getMessage(), this.getClass().getSimpleName(), DibaConfig.showClassNameInMessage);
//                        }
//                    } catch (Exception e)
//                    {
//                        mainView.showError(e.getMessage(), this.getClass().getSimpleName(), DibaConfig.showClassNameInException);
//                    }
//                }
//
//                @Override
//                public void onError(String message)
//                {
//                    Utility.hideSoftKeyboard(view, getActivity());
//
//                    mainView.showError(message, this.getClass().getSimpleName(), DibaConfig.showClassNameInException);
//                    btnPassConfirm.revertAnimation(MoneyTransferFragment.this);
//                    btnPassConfirm.setClickable(true);
//
//
//                }
//            }, request);
//
//        }


    }


    private void transactionsRequest()
    {
//        btnContactTransfer.startAnimation();
//        btnContactTransfer.setClickable(false);
//        GetHistoryTransactionsRequest request = new GetHistoryTransactionsRequest();
//        request.setTypeTransaction(2);
//        request.setUserId(Prefs.getInt("userId", 0));
//        SingletonService.getInstance().getHistoryTransactionsService().GetHistoryTransactionsService(new OnServiceStatus<GetHistoryTransactionsResponse>()
//        {
//            @Override
//            public void onReady(GetHistoryTransactionsResponse response)
//            {
//                btnContactTransfer.revertAnimation(MoneyTransferFragment.this);
//                btnContactTransfer.setClickable(true);
//
//                try
//                {
//                    if (response.getServiceMessage().getCode() == 200)
//                    {
//                        mainView.transactionsExpand(response.getTransactions());
//
//                    } else
//                    {
//                        mainView.showError(response.getServiceMessage().getMessage(), this.getClass().getSimpleName(), DibaConfig.showClassNameInMessage);
//
//                    }
//
//                } catch (Exception e)
//                {
//                    mainView.showError(e.getMessage(), this.getClass().getSimpleName(), DibaConfig.showClassNameInException);
//                }
//            }
//
//            @Override
//            public void onError(String message)
//            {
//                mainView.showError(message, this.getClass().getSimpleName(), DibaConfig.showClassNameInException);
//                btnContactTransfer.revertAnimation(MoneyTransferFragment.this);
//                btnContactTransfer.setClickable(true);
//
//
//            }
//        }, request);


    }


    public void onSelectContact(String number, String name)
    {
        //todo change this
        try
        {
            etDestination2.setText(number.replaceAll(" ", ""));
            etLayoutDestination.setHint(name);

        } catch (Exception e)
        {
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
    {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
    {
        String bankDetect = etDestination.getText().toString().replaceAll("-", "").
                replaceAll("_", "").replaceAll(" ", "");
        if (etDestination.isFocused() && bankDetect.length() == 16)
        {
            etAmount.requestFocus();

        }

    }

    @Override
    public void afterTextChanged(Editable editable)
    {

        String bankDetect = etDestination.getText().toString().replaceAll("-", "").
                replaceAll("_", "").replaceAll(" ", "");
        if (etDestination.isFocused() && bankDetect.length() < 6)
        {

            ivLogo.setImageDrawable(null);


        }


//        for (SingletoneBankDetail.BankDetail detail : SingletoneBankDetail.getInstance().getArray())
//        {
//            if (detail.getCode().equals(bankDetect))
//            {
//                ivLogo.setImageDrawable(getResources().getDrawable(detail.getImage()));
//
//            }
//        }

    }


    public void requestDoTransfer(View view)
    {
        if (!Utility.isNetworkAvailable())
        {
            mainView.onInternetAlert();
            return;
        }
        btnContinue.startAnimation();
        btnContinue.setClickable(false);

        if (!cardNumberCheck.equals("003725"))
        {
            DoTransferRequest request = new DoTransferRequest();
            request.setAmount(amount);
//            request.setPan(archiveCardDBModels.getNumber());
//            request.setExpDate(archiveCardDBModels.getExpireYear() + archiveCardDBModels.getExpireMonth());
            request.setUserId(Prefs.getInt("userId", 0));
            request.setDestinationPan(toUser);
            request.setCvv2(etCvv2.getText().toString());
            request.setHolderTransactionId(holderTransactionId);
            request.setPassword(pass);
            request.setStna(stna);
            request.setDestinationCardHolderName(destinationCardHolderName);
            request.setVerificationCodeKeshavarzi(etKeshavarszi.getText().toString());

            if (cbAddFavoriteCard.isChecked())
                addFavoriteCardRequest(toUser, tvCardName.getText().toString());

//            SingletonService.getInstance().shetabCardService().getDoTransfer(new OnServiceStatus<DoTransferResponse>()
//            {
//                @Override
//                public void onReady(DoTransferResponse response)
//                {
//
//
//                    try
//                    {
//
//                        btnContinue.revertAnimation(MoneyTransferFragment.this);
//                        btnContinue.setClickable(true);
//                        if (response.getServiceMessage().getCode() == 200)
//                        {
//
//                            ResultTransferCardDialog dialogView = new ResultTransferCardDialog(getActivity(),
//                                    archiveCardDBModels.getNumber(), amount + "",
//                                    tvCardName.getText().toString(), tvCardNum.getText().toString().replaceAll("-", ""), cardImage,
//                                    response.getCreateDate(), response.getSwitchResponserrn(), cardColor, archiveCardDBModels.getFullName());
//                            dialogView.show(getActivity().getFragmentManager(), "result");
//                            llTransfer.setVisibility(View.VISIBLE);
//                            llConfirmTransfer.setVisibility(View.GONE);
//                            etCvv2.setText("");
//                            etAmount.setText("");
//                            etKeshavarszi.setText("");
//                            etDestination2.setText("");
//                            etDestination.setText("");
//                            etPass.setText("");
//                            toUser = "";
//                            amount = 0;
//                            mainView.onHome();
//
//                        } else
//                        {
//                            mainView.showError(response.getServiceMessage().getMessage(), this.getClass().getSimpleName(), DibaConfig.showClassNameInMessage);
//
//                        }
//                    } catch (Exception e)
//                    {
//                        mainView.showError(e.getMessage(), this.getClass().getSimpleName(), DibaConfig.showClassNameInException);
//
//                    }
//
//
//                }
//
//                @Override
//                public void onError(String message)
//                {
//                    Utility.hideSoftKeyboard(view, getActivity());
//
//                    mainView.showError(message, this.getClass().getSimpleName(), DibaConfig.showClassNameInException);
//                    btnPassConfirm.revertAnimation(MoneyTransferFragment.this);
//                    btnPassConfirm.setClickable(true);
//                }
//            }, request);

        }
        else
        {

//            HappyDoTransferRequest request = new HappyDoTransferRequest();
//            request.setAmount(amount);
//            request.setFromUsername(archiveCardDBModels.getNumber());
//            request.setToUsername(toUser);
//            request.setPassword(pass);
//            request.setUserId(Prefs.getInt("userId", 0));
//            request.setToCardHolder(tvCardName.getText().toString());
//
//            if (cbAddFavoriteCard.isChecked())
//                addFavoriteCardRequest(toUser, tvCardName.getText().toString());
//
//            SingletonService.getInstance().getHappyDoTransfer().GetHappyBalanceService(this, request);

        }


    }

//    @Override
//    public void onReady(HappyDoTransferResponse response)
//    {
//        btnContinue.revertAnimation(MoneyTransferFragment.this);
//        btnContinue.setClickable(true);
//
//        try
//        {
//            if (response.getServiceMessage().getCode() == 200)
//            {
//                ResultTransferCardDialog dialogView = new ResultTransferCardDialog(getActivity(),
//                        archiveCardDBModels.getNumber(), amount + "",
//                        tvCardName.getText().toString(), tvCardNum.getText().toString().replaceAll("-", ""),
//                        cardImage, response.getServerTime(), response.getTrnBizKey(), cardColor, archiveCardDBModels.getFullName());
//                dialogView.show(getActivity().getFragmentManager(), "result");
//                llTransfer.setVisibility(View.VISIBLE);
//                llConfirmTransfer.setVisibility(View.GONE);
//                etCvv2.setText("");
//                etAmount.setText("");
//                etKeshavarszi.setText("");
//                etDestination2.setText("");
//                etDestination.setText("");
//
//                etPass.setText("");
//                toUser = "";
//                amount = 0;
//                mainView.onHome();
//            } else
//            {
//                mainView.showError(response.getServiceMessage().getMessage(), this.getClass().getSimpleName(), DibaConfig.showClassNameInMessage);
//
//            }
//        } catch (Exception e)
//        {
//            mainView.showError(e.getMessage(), this.getClass().getSimpleName(), DibaConfig.showClassNameInException);
//
//
//        }
//
//
//    }
//
//    @Override
//    public void onError(String message)
//    {
//        btnContinue.revertAnimation(MoneyTransferFragment.this);
//        btnContinue.setClickable(true);
//        mainView.showError(message, this.getClass().getSimpleName(), DibaConfig.showClassNameInException);
//    }


//    public void cardModel(ArchiveCardDBModel archiveCardDBModels)
//    {
//        this.archiveCardDBModels = archiveCardDBModels;
//        cardNumberCheck = archiveCardDBModels.getNumber().substring(0, 6);
//
//
//        if (v != null)
//            if (!cardNumberCheck.equals("003725"))
//            {
//
//                llCvv2.setVisibility(View.VISIBLE);
//                YoYo.with(Techniques.SlideInLeft)
//                        .duration(200)
//                        .playOn(llCvv2);
//                tvChangeInput.setVisibility(View.GONE);
//                tvBarcode.setVisibility(View.VISIBLE);
//
//
//
//             /*   YoYo.with(Techniques.SlideOutLeft).withListener(new AnimatorListenerAdapter() {
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        super.onAnimationEnd(animation);
//
//                    }
//                })
//                        .duration(200)
//                        .playOn(tvChangeInput);*/
//                etPass.setImeOptions(EditorInfo.IME_ACTION_NEXT);
//
//            } else
//            {
//                tvChangeInput.setVisibility(View.VISIBLE);
//                tvBarcode.setVisibility(View.GONE);
//                YoYo.with(Techniques.SlideOutLeft).withListener(new AnimatorListenerAdapter() {
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        super.onAnimationEnd(animation);
//                        llCvv2.setVisibility(View.GONE);
//
//                    }
//                })
//                        .duration(200)
//                        .playOn(llCvv2);
//       /*         tvChangeInput.setVisibility(View.VISIBLE);
//                YoYo.with(Techniques.SlideInLeft)
//                        .duration(200)
//                        .playOn(tvChangeInput);
//                YoYo.with(Techniques.SlideOutLeft).withListener(new AnimatorListenerAdapter() {
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        super.onAnimationEnd(animation);
//                        llCvv2.setVisibility(View.GONE);
//
//                    }
//                })
//                        .duration(200)
//                        .playOn(llCvv2)*/
//                ;
//
//                etPass.setImeOptions(EditorInfo.IME_ACTION_DONE);
//
//
//            }
//    }

    public void inputType(TransferType transferType)
    {
        this.transferType = transferType;
        switch (transferType)
        {
            case ACCOUNT:
                etDestination2.setText("");
                btnContactTransfer.setEnabled(false);
                etLayoutDestination2.setHint("شماره مشتری مقصد را وارد نمایید");
                etLayoutDestination.setVisibility(View.GONE);
                etLayoutDestination2.setVisibility(View.VISIBLE);
                btnContactTransfer.setTextColor(getResources().getColor(R.color.colorPrimary));
                break;
            case CARDNUMBER:
                etLayoutDestination2.setVisibility(View.GONE);
                etLayoutDestination.setVisibility(View.VISIBLE);
                btnContactTransfer.setEnabled(true);
                btnContactTransfer.setTextColor(Color.WHITE);

                break;
            case MOBILENUMBER:
                etDestination2.setText("");
                btnContactTransfer.setEnabled(true);
                etLayoutDestination2.setHint("شماره همراه مقصد");
                etLayoutDestination.setVisibility(View.GONE);
                etLayoutDestination2.setVisibility(View.VISIBLE);
                btnContactTransfer.setTextColor(Color.WHITE);

                break;

        }
    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onMessageEvent(ListCard event)
//    {
//        String bankNumber = Objects.requireNonNull(TrippleDes.decrypt(event.getCardNumber(),
//                Utility.decryption(Prefs.getString(Utility.encryption(SingletonDiba.getInstance().getPASS_KEY()), ""))));
//        etDestination.setText(bankNumber);
//    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onMessageEvent(Transaction event)
//    {
//        if (event.getTypeTransactionId() != 2)
//            return;
//        if (event.getTransferVm().getDestinationCard().replaceAll("-", "").replaceAll("_", "").length() == 16)
//        {
//            etDestination.setText(event.getTransferVm().getDestinationCard());
//            etLayoutDestination.setVisibility(View.VISIBLE);
//            etLayoutDestination2.setVisibility(View.GONE);
//            transferType = TransferType.CARDNUMBER;
//        } else
//        {
//            etDestination2.setText(event.getTransferVm().getDestinationCard());
//            etLayoutDestination.setVisibility(View.GONE);
//            etLayoutDestination2.setVisibility(View.VISIBLE);
//            transferType = TransferType.MOBILENUMBER;
//            etLayoutDestination2.setHint("شماره همراه مقصد");
//
//
//        }
//
//        etAmount.setText(Utility.priceFormat(event.getAmount() + ""));
//
//
//    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        YoYo.with(Techniques.FadeIn)
                .duration(700)
                .playOn(v);
    }

    public void destinationHistoryRequest(View view)
    {
        btnDestinationHistory.startAnimation();
        btnDestinationHistory.setClickable(false);
        if (view != null)
            Utility.hideSoftKeyboard(view, getActivity());
//        GetCardRequest request = new GetCardRequest();
//        request.setUserId(Prefs.getInt("userId", 0));
//        SingletonService.getInstance().getFavoriteCardsService().getFavoriteCardsService(new OnServiceStatus<GetFavoriteCardsResponse>()
//        {
//            @Override
//            public void onReady(GetFavoriteCardsResponse response)
//            {
//                try
//                {
//
//                    btnDestinationHistory.revertAnimation(MoneyTransferFragment.this);
//                    btnDestinationHistory.setClickable(true);
//                    if (response.getServiceMessage().getCode() == 200)
//                    {
//                        mainView.destinationHistoryExpand(response.getListCards());
//
//
//                    } else
//                    {
//                        mainView.showError(response.getServiceMessage().getMessage(), this.getClass().getSimpleName(), DibaConfig.showClassNameInMessage);
//
//                    }
//                } catch (Exception e)
//                {
//                    mainView.showError(e.getMessage(), this.getClass().getSimpleName(), DibaConfig.showClassNameInException);
//
//                }
//
//
//            }
//
//            @Override
//            public void onError(String message)
//            {
//                Utility.hideSoftKeyboard(view, getActivity());
//                mainView.showError(message, this.getClass().getSimpleName(), DibaConfig.showClassNameInException);
//                btnDestinationHistory.revertAnimation(MoneyTransferFragment.this);
//                btnDestinationHistory.setClickable(true);
//            }
//        }, request);


    }

    public void addFavoriteCardRequest(String cardNumber, String fullName)
    {
//        AddFavoriteCardRequest request = new AddFavoriteCardRequest();
//        request.setCardNumber(cardNumber);
//        request.setFullName(fullName);
//        request.setUserId(Prefs.getInt("userId", 0));
//        SingletonService.getInstance().getFavoriteCardsService().addFavoriteCardsService(new OnServiceStatus<GlobalResponse2>()
//        {
//            @Override
//            public void onReady(GlobalResponse2 response2)
//            {
//
//            }
//
//            @Override
//            public void onError(String message)
//            {
//
//            }
//        }, request);

    }

    @Override
    public void onFocusChange(View view, boolean b)
    {
    }

    private View.OnFocusChangeListener focusListener = (v, hasFocus) -> {
        if (hasFocus)
        {
//            new Handler().postDelayed(() -> {
//                mainView.needExpanded(false);
//
//            }, 150);

        }
    };


    public void barcode(String barcode)
    {
        try
        {
            etDestination.setText(barcode);
        } catch (Exception e)
        {
        }
    }

    @Override
    public void showFavoriteCardParentLoading()
    {
        mainView.showLoading();
    }

    @Override
    public void hideFavoriteCardParentLoading()
    {
        mainView.hideLoading();
    }

    @Override
    public void startAddCardActivity()
    {

    }
}