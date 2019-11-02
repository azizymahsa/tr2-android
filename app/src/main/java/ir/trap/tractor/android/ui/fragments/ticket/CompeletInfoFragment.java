package ir.trap.tractor.android.ui.fragments.ticket;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.Arrays;

import ir.trap.tractor.android.R;
import ir.trap.tractor.android.apiServices.generator.SingletonService;
import ir.trap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.trap.tractor.android.apiServices.model.WebServiceClass;
import ir.trap.tractor.android.apiServices.model.match.ResponseMatch;
import ir.trap.tractor.android.ui.dialogs.MessageAlertDialog;
import ir.trap.tractor.android.ui.fragments.main.MainActionView;
import ir.trap.tractor.android.ui.fragments.paymentWithoutCard.PaymentWithoutCardFragment;

public class CompeletInfoFragment
        extends Fragment implements View.OnClickListener, View.OnFocusChangeListener
{

    private static final String KEY_MODEL = "KEY_MODEL";
    private View view;
    private TextView txtCondition;
    private View btnBackToDetail, btnPaymentConfirm;
    private int count = 1;
    private OnClickContinueBuyTicket onClickContinueBuyTicketListener;
    private EditText etNationalCode_1, etFamily_1, etName_1;
    private MainActionView mainView;
    private CheckBox cbCondition;
    private View llConfirm, llInVisible;
    private MessageAlertDialog.OnConfirmListener listener;

    public CompeletInfoFragment()
    {
    }

    public static CompeletInfoFragment newInstance(MainActionView mainActionView
    )
    {
        CompeletInfoFragment fragment = new CompeletInfoFragment();
        fragment.setMainView(mainActionView);


        return fragment;
    }


    private void setMainView(MainActionView mainView)
    {
        this.mainView = mainView;
    }

    /**
     * Receive the model list
     */
    public static CompeletInfoFragment newInstance(String s, OnClickContinueBuyTicket onClickContinueBuyTicket, MainActionView mainActionView)
    {
        CompeletInfoFragment fragment = new CompeletInfoFragment();
        fragment.setOnClickContinueBuyTicket(onClickContinueBuyTicket);

        fragment.setMainView(mainActionView);
        return fragment;
    }

    private void setOnClickContinueBuyTicket(OnClickContinueBuyTicket onClickContinueBuyTicket)
    {
        this.onClickContinueBuyTicketListener = onClickContinueBuyTicket;
    }


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        try
        {
            SingletonService.getInstance().getTicketServices().getMatch(new OnServiceStatus<WebServiceClass<ResponseMatch>>()
            {
                @Override
                public void onReady(WebServiceClass<ResponseMatch> response)
                {

                }

                @Override
                public void onError(String message)
                {

                }
            });
        } catch (Exception e)
        {
            e.getMessage();
        }


    }


    private void initView()
    {
        etNationalCode_1 = view.findViewById(R.id.etNationalCode_1);
        etFamily_1 = view.findViewById(R.id.etFamily_1);
        etName_1 = view.findViewById(R.id.etName_1);
        txtCondition = view.findViewById(R.id.txtCondition);
        cbCondition = view.findViewById(R.id.cbCondition);
        llConfirm = view.findViewById(R.id.llConfirm);
        llInVisible = view.findViewById(R.id.llInVisible);
        llConfirm.setVisibility(View.GONE);
        llInVisible.setVisibility(View.VISIBLE);


        btnBackToDetail = view.findViewById(R.id.btnBackToDetail);
        btnPaymentConfirm = view.findViewById(R.id.btnPaymentConfirm);
        btnBackToDetail.setOnClickListener(this);
        btnPaymentConfirm.setOnClickListener(this);
        cbCondition.setOnClickListener(this);
        etNationalCode_1.setOnFocusChangeListener(this);
        etFamily_1.setOnFocusChangeListener(this);
        etName_1.setOnFocusChangeListener(this);

        txtCondition.setOnClickListener(this);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.complete_info_fragment, container, false);
        listener = new MessageAlertDialog.OnConfirmListener()
        {


            @Override
            public void onConfirmClick()
            {
                cbCondition.setChecked(true);
                llConfirm.setVisibility(View.VISIBLE);
                llInVisible.setVisibility(View.GONE);
            }

            @Override
            public void onCancelClick()
            {
                //  mainView.backToMainFragment();
            }
        };
        initView();

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
            case R.id.btnPaymentConfirm:
                String flagValidations = "";
                if (etNationalCode_1.getText().toString() != null)
                    if (isValidNationalCode(etNationalCode_1.getText().toString()))
                    {
                        flagValidations = flagValidations + "T";
                        etNationalCode_1.setTextColor(Color.parseColor("#4d4d4d"));

                    } else
                    {
                        flagValidations = flagValidations + "F";

                        //((EditText)findViewById(R.id.txtfamilyP)).setTextColor(Color.parseColor("#ff3300"));
                        etNationalCode_1.setError(getString(R.string.Please_enter_the_national_code));
                    }
                if (etFamily_1.getText().toString() != null)
                {

                    if (etFamily_1.getText().toString().length() > 2 && !(etFamily_1.getText().toString().toLowerCase().matches("^[a-zA-Z]+(\\s[a-zA-Z]+)?$")))
                    {
                        flagValidations = flagValidations + "T";

                        etFamily_1.setTextColor(Color.parseColor("#4d4d4d"));

                    } else
                    {
                        flagValidations = flagValidations + "F";

                        //((EditText)findViewById(R.id.txtfamilyP)).setTextColor(Color.parseColor("#ff3300"));
                        etFamily_1.setError(getString(R.string.Please_enter_first_name_in_Persian));
                    }

                }
                if (etName_1.getText().toString() != null)
                {

                    if (etName_1.getText().toString().length() > 2 && !(etName_1.getText().toString().toLowerCase().matches("^[a-zA-Z]+(\\s[a-zA-Z]+)?$")))
                    {
                        flagValidations = flagValidations + "T";

                        etName_1.setTextColor(Color.parseColor("#4d4d4d"));

                    } else
                    {
                        flagValidations = flagValidations + "F";

                        //((EditText)findViewById(R.id.txtfamilyP)).setTextColor(Color.parseColor("#ff3300"));
                        etName_1.setError(getString(R.string.Please_enter_last_name_in_Persian));
                    }

                }
                if (flagValidations.contains("F"))
                {
                    mainView.showError(getString(R.string.Error_edit_input));
                } else
                {
                    onClickContinueBuyTicketListener.onContinueClicked();

                }
                break;
            case R.id.btnBackToDetail:
                onClickContinueBuyTicketListener.onBackClicked();

                break;

            case R.id.txtCondition:

                //MessageAlertDialog dialog = new MessageAlertDialog(getActivity(), "قوانین و مقررات", "شرکت تراکتور سازی ...");
                MessageAlertDialog dialog = new MessageAlertDialog(getActivity(), "قوانین و مقررات", "شرکت تراکتور سازی ...",
                        true, "تایید", "بستن", listener);
                dialog.show(getActivity().getFragmentManager(), "dialog");
                break;
            case R.id.cbCondition:
                if (((CheckBox) view).isChecked())
                {
                    llConfirm.setVisibility(View.VISIBLE);
                    llInVisible.setVisibility(View.GONE);

                } else
                {
                    llConfirm.setVisibility(View.GONE);
                    llInVisible.setVisibility(View.VISIBLE);
                }
                break;
        }
        // tvCount.setText(String.valueOf(count));
    }

    private boolean isValidNationalCode(String nationalCode)
    {
        if (nationalCode.length() != 10)
        {
            return false;
        } else
        {
            //Check for equal numbers
            String[] allDigitEqual = {"0000000000", "1111111111", "2222222222", "3333333333",
                    "4444444444", "5555555555", "6666666666", "7777777777", "8888888888", "9999999999"};
            if (Arrays.asList(allDigitEqual).contains(nationalCode))
            {
                return false;
            } else
            {
                int sum = 0;
                int lenght = 10;
                for (int i = 0; i < lenght - 1; i++)
                {
                    sum += Integer.parseInt(String.valueOf(nationalCode.charAt(i))) * (lenght - i);
                }

                int r = Integer.parseInt(String.valueOf(nationalCode.charAt(9)));

                int c = sum % 11;

                return (((c < 2) && (r == c)) || ((c >= 2) && ((11 - c) == r)));
            }

        }
    }

    @Override
    public void onFocusChange(View v, boolean b)
    {

        switch (v.getId())
        {
            case R.id.etNationalCode_1:
            if (etNationalCode_1.getText().toString() != null)
                if (isValidNationalCode(etNationalCode_1.getText().toString()))
                {
                    etNationalCode_1.setTextColor(Color.parseColor("#4d4d4d"));

                } else
                {

                    etNationalCode_1.setError(getString(R.string.Please_enter_the_national_code));
                }
            break;
            case R.id.etFamily_1:
            if (etFamily_1.getText().toString() != null)
            {

                if (etFamily_1.getText().toString().length() > 2 && !(etFamily_1.getText().toString().toLowerCase().matches("^[a-zA-Z]+(\\s[a-zA-Z]+)?$")))
                {

                    etFamily_1.setTextColor(Color.parseColor("#4d4d4d"));

                } else
                {

                    etFamily_1.setError(getString(R.string.Please_enter_last_name_in_Persian));
                }

            }
            break;
            case R.id.etName_1:
            if (etName_1.getText().toString() != null)
            {

                if (etName_1.getText().toString().length() > 2 && !(etName_1.getText().toString().toLowerCase().matches("^[a-zA-Z]+(\\s[a-zA-Z]+)?$")))
                {

                    etName_1.setTextColor(Color.parseColor("#4d4d4d"));

                } else
                {

                    etName_1.setError(getString(R.string.Please_enter_last_name_in_Persian));
                }

            }
            break;

        }
    }
}

