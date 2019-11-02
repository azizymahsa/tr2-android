package ir.trap.tractor.android.ui.fragments.ticket;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.pixplicity.easyprefs.library.Prefs;

import java.util.Arrays;

import ir.trap.tractor.android.R;
import ir.trap.tractor.android.apiServices.generator.SingletonService;
import ir.trap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.trap.tractor.android.apiServices.model.WebServiceClass;
import ir.trap.tractor.android.apiServices.model.matchList.MachListResponse;
import ir.trap.tractor.android.ui.dialogs.MessageAlertDialog;
import ir.trap.tractor.android.ui.fragments.main.MainActionView;

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
    private EditText etNationalCode_2, etFamily_2, etName_2;
    private EditText etNationalCode_3, etFamily_3, etName_3;
    private EditText etNationalCode_4, etFamily_4, etName_4;
    private EditText etNationalCode_5, etFamily_5, etName_5;
    private MainActionView mainView;
    private LinearLayout llBoxTicket1,llBoxTicket2,llBoxTicket3,llBoxTicket4,llBoxTicket5;
    private TextView tvStation_1,tvStation_2,tvStation_3,tvStation_4,tvStation_5;
    private CheckBox cbCondition;
    private View llConfirm, llInVisible;
    private MessageAlertDialog.OnConfirmListener listener;
    private String textStation="";

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
            SingletonService.getInstance().getMatchListService().getMatchList(new OnServiceStatus<WebServiceClass<MachListResponse>>()
            {
                @Override
                public void onReady(WebServiceClass<MachListResponse> response)
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
        tvStation_1 = view.findViewById(R.id.tvStation_1);

        etNationalCode_2 = view.findViewById(R.id.etNationalCode_2);
        etFamily_2 = view.findViewById(R.id.etFamily_2);
        etName_2 = view.findViewById(R.id.etName_2);
        tvStation_2 = view.findViewById(R.id.tvStation_2);

        etNationalCode_3 = view.findViewById(R.id.etNationalCode_3);
        etFamily_3 = view.findViewById(R.id.etFamily_3);
        etName_3 = view.findViewById(R.id.etName_3);
        tvStation_3 = view.findViewById(R.id.tvStation_3);

        etNationalCode_4 = view.findViewById(R.id.etNationalCode_4);
        etFamily_4 = view.findViewById(R.id.etFamily_4);
        etName_4 = view.findViewById(R.id.etName_4);
        tvStation_4 = view.findViewById(R.id.tvStation_4);

        etNationalCode_5 = view.findViewById(R.id.etNationalCode_5);
        etFamily_5 = view.findViewById(R.id.etFamily_5);
        etName_5 = view.findViewById(R.id.etName_5);
        tvStation_5 = view.findViewById(R.id.tvStation_5);

        llBoxTicket1 = view.findViewById(R.id.llBoxTicket1);
        llBoxTicket2 = view.findViewById(R.id.llBoxTicket2);
        llBoxTicket3 = view.findViewById(R.id.llBoxTicket3);
        llBoxTicket4 = view.findViewById(R.id.llBoxTicket4);
        llBoxTicket5 = view.findViewById(R.id.llBoxTicket5);

        if(textStation != null){
            tvStation_1.setText(textStation);
            tvStation_2.setText(textStation);
            tvStation_3.setText(textStation);
            tvStation_4.setText(textStation);
            tvStation_5.setText(textStation);
        }
        if (count == 1)
        {
            llBoxTicket1.setVisibility(View.VISIBLE);
            llBoxTicket2.setVisibility(View.GONE);
            llBoxTicket3.setVisibility(View.GONE);
            llBoxTicket4.setVisibility(View.GONE);
            llBoxTicket5.setVisibility(View.GONE);
        }else
        if (count == 2)
        {
            llBoxTicket1.setVisibility(View.VISIBLE);
            llBoxTicket2.setVisibility(View.VISIBLE);
            llBoxTicket3.setVisibility(View.GONE);
            llBoxTicket4.setVisibility(View.GONE);
            llBoxTicket5.setVisibility(View.GONE);
        }else
        if (count == 3)
        {
            llBoxTicket1.setVisibility(View.VISIBLE);
            llBoxTicket2.setVisibility(View.VISIBLE);
            llBoxTicket3.setVisibility(View.VISIBLE);
            llBoxTicket4.setVisibility(View.GONE);
            llBoxTicket5.setVisibility(View.GONE);
        }else
        if (count == 4)
        {
            llBoxTicket1.setVisibility(View.VISIBLE);
            llBoxTicket2.setVisibility(View.VISIBLE);
            llBoxTicket3.setVisibility(View.VISIBLE);
            llBoxTicket4.setVisibility(View.VISIBLE);
            llBoxTicket5.setVisibility(View.GONE);
        }else
        if (count == 5)
        {
            llBoxTicket1.setVisibility(View.VISIBLE);
            llBoxTicket2.setVisibility(View.VISIBLE);
            llBoxTicket3.setVisibility(View.VISIBLE);
            llBoxTicket4.setVisibility(View.VISIBLE);
            llBoxTicket5.setVisibility(View.VISIBLE);
        }


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
                if (count == 1)
                {
                    flagValidations = flagValidations + PassengerOne();
                }else
                if (count == 2)
                {
                    flagValidations = flagValidations + PassengerOne();
                    flagValidations = flagValidations + PassengerSecond();
                }else
                if (count == 3)
                {
                    flagValidations = flagValidations + PassengerOne();
                    flagValidations = flagValidations + PassengerSecond();
                    flagValidations = flagValidations + PassengerThird();
                }else
                if (count == 4)
                {
                    flagValidations = flagValidations + PassengerOne();
                    flagValidations = flagValidations + PassengerSecond();
                    flagValidations = flagValidations + PassengerThird();
                    flagValidations = flagValidations + PassengerFourth();
                }else
                if (count == 5)
                {
                    flagValidations = flagValidations + PassengerOne();
                    flagValidations = flagValidations + PassengerSecond();
                    flagValidations = flagValidations + PassengerThird();
                    flagValidations = flagValidations + PassengerFourth();
                    flagValidations = flagValidations + PassengerFifth();
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
    }

    private String PassengerOne()
    {
        String flagValidations = "";
        if (etNationalCode_1.getText().toString() != null)
            if (isValidNationalCode(etNationalCode_1.getText().toString()))
            {
                flagValidations = flagValidations + "T";
                etNationalCode_1.setTextColor(Color.parseColor("#4d4d4d"));

            } else
            {
                flagValidations = flagValidations + "F";
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
                etFamily_1.setError(getString(R.string.Please_enter_last_name_in_Persian));
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
                etName_1.setError(getString(R.string.Please_enter_name_in_Persian));
            }

        }
        if (flagValidations.contains("F"))
        {
            mainView.showError("اطلاعات ورودی مسافر اول نامعتبر است");
            return "F";
        } else
        {
            return "T";

        }
    }

    private String PassengerSecond()
    {
        String flagValidations = "";
        if (etNationalCode_2.getText().toString() != null)
            if (isValidNationalCode(etNationalCode_2.getText().toString()))
            {
                flagValidations = flagValidations + "T";
                etNationalCode_2.setTextColor(Color.parseColor("#4d4d4d"));

            } else
            {
                flagValidations = flagValidations + "F";
                etNationalCode_2.setError(getString(R.string.Please_enter_the_national_code));
            }
        if (etFamily_2.getText().toString() != null)
        {

            if (etFamily_2.getText().toString().length() > 2 && !(etFamily_2.getText().toString().toLowerCase().matches("^[a-zA-Z]+(\\s[a-zA-Z]+)?$")))
            {
                flagValidations = flagValidations + "T";
                etFamily_2.setTextColor(Color.parseColor("#4d4d4d"));

            } else
            {
                flagValidations = flagValidations + "F";
                etFamily_2.setError(getString(R.string.Please_enter_last_name_in_Persian));
            }

        }
        if (etName_2.getText().toString() != null)
        {

            if (etName_2.getText().toString().length() > 2 && !(etName_2.getText().toString().toLowerCase().matches("^[a-zA-Z]+(\\s[a-zA-Z]+)?$")))
            {
                flagValidations = flagValidations + "T";
                etName_2.setTextColor(Color.parseColor("#4d4d4d"));

            } else
            {
                flagValidations = flagValidations + "F";
                etName_2.setError(getString(R.string.Please_enter_name_in_Persian));
            }

        }
        if (flagValidations.contains("F"))
        {
            mainView.showError("اطلاعات ورودی مسافر دوم نامعتبر است");
            return "F";
        } else
        {
            return "T";

        }
    }

    private String PassengerThird()
    {
        String flagValidations = "";
        if (etNationalCode_3.getText().toString() != null)
            if (isValidNationalCode(etNationalCode_3.getText().toString()))
            {
                flagValidations = flagValidations + "T";
                etNationalCode_3.setTextColor(Color.parseColor("#4d4d4d"));

            } else
            {
                flagValidations = flagValidations + "F";
                etNationalCode_3.setError(getString(R.string.Please_enter_the_national_code));
            }
        if (etFamily_3.getText().toString() != null)
        {

            if (etFamily_3.getText().toString().length() > 2 && !(etFamily_3.getText().toString().toLowerCase().matches("^[a-zA-Z]+(\\s[a-zA-Z]+)?$")))
            {
                flagValidations = flagValidations + "T";
                etFamily_3.setTextColor(Color.parseColor("#4d4d4d"));

            } else
            {
                flagValidations = flagValidations + "F";
                etFamily_3.setError(getString(R.string.Please_enter_last_name_in_Persian));
            }

        }
        if (etName_3.getText().toString() != null)
        {

            if (etName_3.getText().toString().length() > 2 && !(etName_3.getText().toString().toLowerCase().matches("^[a-zA-Z]+(\\s[a-zA-Z]+)?$")))
            {
                flagValidations = flagValidations + "T";
                etName_3.setTextColor(Color.parseColor("#4d4d4d"));

            } else
            {
                flagValidations = flagValidations + "F";
                etName_3.setError(getString(R.string.Please_enter_name_in_Persian));
            }

        }
        if (flagValidations.contains("F"))
        {
            mainView.showError("اطلاعات ورودی مسافر سوم نامعتبر است");
            return "F";
        } else
        {
            return "T";

        }
    }

    private String PassengerFourth()
    {
        String flagValidations = "";
        if (etNationalCode_4.getText().toString() != null)
            if (isValidNationalCode(etNationalCode_4.getText().toString()))
            {
                flagValidations = flagValidations + "T";
                etNationalCode_4.setTextColor(Color.parseColor("#4d4d4d"));

            } else
            {
                flagValidations = flagValidations + "F";
                etNationalCode_4.setError(getString(R.string.Please_enter_the_national_code));
            }
        if (etFamily_4.getText().toString() != null)
        {

            if (etFamily_4.getText().toString().length() > 2 && !(etFamily_4.getText().toString().toLowerCase().matches("^[a-zA-Z]+(\\s[a-zA-Z]+)?$")))
            {
                flagValidations = flagValidations + "T";
                etFamily_4.setTextColor(Color.parseColor("#4d4d4d"));

            } else
            {
                flagValidations = flagValidations + "F";
                etFamily_4.setError(getString(R.string.Please_enter_last_name_in_Persian));
            }

        }
        if (etName_4.getText().toString() != null)
        {

            if (etName_4.getText().toString().length() > 2 && !(etName_4.getText().toString().toLowerCase().matches("^[a-zA-Z]+(\\s[a-zA-Z]+)?$")))
            {
                flagValidations = flagValidations + "T";
                etName_4.setTextColor(Color.parseColor("#4d4d4d"));

            } else
            {
                flagValidations = flagValidations + "F";
                etName_4.setError(getString(R.string.Please_enter_name_in_Persian));
            }

        }
        if (flagValidations.contains("F"))
        {
            mainView.showError("اطلاعات ورودی مسافر چهارم نامعتبر است");
            return "F";
        } else
        {
            return "T";

        }
    }

    private String PassengerFifth()
    {
        String flagValidations = "";
        if (etNationalCode_5.getText().toString() != null)
            if (isValidNationalCode(etNationalCode_5.getText().toString()))
            {
                flagValidations = flagValidations + "T";
                etNationalCode_5.setTextColor(Color.parseColor("#4d4d4d"));

            } else
            {
                flagValidations = flagValidations + "F";
                etNationalCode_5.setError(getString(R.string.Please_enter_the_national_code));
            }
        if (etFamily_5.getText().toString() != null)
        {

            if (etFamily_5.getText().toString().length() > 2 && !(etFamily_5.getText().toString().toLowerCase().matches("^[a-zA-Z]+(\\s[a-zA-Z]+)?$")))
            {
                flagValidations = flagValidations + "T";
                etFamily_5.setTextColor(Color.parseColor("#4d4d4d"));

            } else
            {
                flagValidations = flagValidations + "F";
                etFamily_5.setError(getString(R.string.Please_enter_last_name_in_Persian));
            }

        }
        if (etName_5.getText().toString() != null)
        {

            if (etName_5.getText().toString().length() > 2 && !(etName_5.getText().toString().toLowerCase().matches("^[a-zA-Z]+(\\s[a-zA-Z]+)?$")))
            {
                flagValidations = flagValidations + "T";
                etName_5.setTextColor(Color.parseColor("#4d4d4d"));

            } else
            {
                flagValidations = flagValidations + "F";
                etName_5.setError(getString(R.string.Please_enter_name_in_Persian));
            }

        }
        if (flagValidations.contains("F"))
        {
            mainView.showError("اطلاعات ورودی مسافر پنجم نامعتبر است");
            return "F";
        } else
        {
            return "T";

        }
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

                        etName_1.setError(getString(R.string.Please_enter_name_in_Persian));
                    }

                }
                break;

        }
    }
}

