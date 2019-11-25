package ir.traap.tractor.android.ui.fragments.ticket;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;

import ir.traap.tractor.android.R;
import ir.traap.tractor.android.apiServices.generator.SingletonService;
import ir.traap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.traap.tractor.android.apiServices.model.WebServiceClass;
import ir.traap.tractor.android.apiServices.model.matchList.MachListResponse;
import ir.traap.tractor.android.apiServices.model.paymentMatch.PaymentMatchResponse;
import ir.traap.tractor.android.apiServices.model.paymentMatch.Viewers;
import ir.traap.tractor.android.apiServices.model.stadium_rules.ResponseStadiumRules;
import ir.traap.tractor.android.ui.dialogs.MessageAlertDialog;
import ir.traap.tractor.android.ui.fragments.main.MainActionView;
import ir.traap.tractor.android.ui.fragments.paymentGateWay.SelectPaymentGateWayFragment;
import ir.traap.tractor.android.ui.fragments.ticket.paymentTicket.PaymentTicketImpl;
import ir.traap.tractor.android.ui.fragments.ticket.paymentTicket.PaymentTicketInteractor;
import ir.traap.tractor.android.ui.fragments.ticket.rulesStadium.RulesStadiumImpl;
import ir.traap.tractor.android.ui.fragments.ticket.rulesStadium.RulesStadiumInteractor;
import ir.traap.tractor.android.utilities.Tools;
import library.android.eniac.utility.Utility;

public class CompeletInfoFragment
        extends Fragment implements View.OnClickListener, View.OnFocusChangeListener, PaymentTicketInteractor.OnFinishedPaymentTicketListener, RulesStadiumInteractor.OnFinishedRulesStadiumListener
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
    private LinearLayout llBoxTicket1, llBoxTicket2, llBoxTicket3, llBoxTicket4, llBoxTicket5;
    private TextView tvStation_1, tvStation_2, tvStation_3, tvStation_4, tvStation_5;
    private ImageView imgDelete1, imgDelete2, imgDelete3, imgDelete4, imgDelete5;
    private CheckBox cbCondition;
    private View llConfirm, llInVisible;
    private MessageAlertDialog.OnConfirmListener listener;
    private String textStation = "";
    ArrayList<String> numbers = new ArrayList<String>();
    private int countRepetitive = 0;
    public String namePosition;
    String positionName, selectPositionId;
    Integer amountForPay;
    List<Viewers> infoViewers = new ArrayList<>();
    private List<Integer> ticketIdList;
    private PaymentTicketImpl paymentTicket;
    private RulesStadiumImpl rulesStadium;
    private Integer stadiumId;
    private Integer amountOneTicket;
    private boolean flagDelete = false;
    private int flagNumberDelete = 0;

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


    }

    @Override
    public void onResume()
    {
        super.onResume();
        // initView();
    }


    public void initView()
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

        if (textStation != null)
        {
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
        } else if (count == 2)
        {
            llBoxTicket1.setVisibility(View.VISIBLE);
            llBoxTicket2.setVisibility(View.VISIBLE);
            llBoxTicket3.setVisibility(View.GONE);
            llBoxTicket4.setVisibility(View.GONE);
            llBoxTicket5.setVisibility(View.GONE);
        } else if (count == 3)
        {
            llBoxTicket1.setVisibility(View.VISIBLE);
            llBoxTicket2.setVisibility(View.VISIBLE);
            llBoxTicket3.setVisibility(View.VISIBLE);
            llBoxTicket4.setVisibility(View.GONE);
            llBoxTicket5.setVisibility(View.GONE);
        } else if (count == 4)
        {
            llBoxTicket1.setVisibility(View.VISIBLE);
            llBoxTicket2.setVisibility(View.VISIBLE);
            llBoxTicket3.setVisibility(View.VISIBLE);
            llBoxTicket4.setVisibility(View.VISIBLE);
            llBoxTicket5.setVisibility(View.GONE);
        } else if (count == 5)
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
        imgDelete1 = view.findViewById(R.id.imgDelete1);
        imgDelete2 = view.findViewById(R.id.imgDelete2);
        imgDelete3 = view.findViewById(R.id.imgDelete3);
        imgDelete4 = view.findViewById(R.id.imgDelete4);
        imgDelete5 = view.findViewById(R.id.imgDelete5);
        llConfirm.setVisibility(View.GONE);
        llInVisible.setVisibility(View.VISIBLE);


        btnBackToDetail = view.findViewById(R.id.btnBackToDetail);
        btnPaymentConfirm = view.findViewById(R.id.btnPaymentConfirm);
        imgDelete1.setOnClickListener(this);
        imgDelete2.setOnClickListener(this);
        imgDelete3.setOnClickListener(this);
        imgDelete4.setOnClickListener(this);
        imgDelete5.setOnClickListener(this);
        btnBackToDetail.setOnClickListener(this);
        btnPaymentConfirm.setOnClickListener(this);
        cbCondition.setOnClickListener(this);
        etNationalCode_1.setOnFocusChangeListener(this);
        etFamily_1.setOnFocusChangeListener(this);
        etName_1.setOnFocusChangeListener(this);

        etNationalCode_2.setOnFocusChangeListener(this);
        etFamily_2.setOnFocusChangeListener(this);
        etName_2.setOnFocusChangeListener(this);

        etNationalCode_3.setOnFocusChangeListener(this);
        etFamily_3.setOnFocusChangeListener(this);
        etName_3.setOnFocusChangeListener(this);

        etNationalCode_4.setOnFocusChangeListener(this);
        etFamily_4.setOnFocusChangeListener(this);
        etName_4.setOnFocusChangeListener(this);

        etNationalCode_5.setOnFocusChangeListener(this);
        etFamily_5.setOnFocusChangeListener(this);
        etName_5.setOnFocusChangeListener(this);

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
                if (flagDelete)
                {
                    //check condition for delete  Different modes
                    checkCondition();
                } else
                {
                    cbCondition.setChecked(true);
                    llConfirm.setVisibility(View.VISIBLE);
                    llInVisible.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelClick()
            {
                if (flagDelete)
                {

                } else
                {
                    cbCondition.setChecked(false);
                    llConfirm.setVisibility(View.GONE);
                    llInVisible.setVisibility(View.VISIBLE);
                }
                // mainView.backToMainFragment();
            }
        };
        paymentTicket = new PaymentTicketImpl();
        rulesStadium = new RulesStadiumImpl();
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
            case R.id.imgDelete1:
                if (count == 1)
                {
                   // imgDelete1.setVisibility(View.GONE);

                } else
                {
                    flagDelete = true;
                    flagNumberDelete = 1;
                    showDialogDelete();

                }
                break;
            case R.id.imgDelete2:
                if (count == 1)
                {
                  //  imgDelete2.setVisibility(View.GONE);

                } else
                {
                    flagDelete = true;
                    flagNumberDelete = 2;
                    showDialogDelete();
                }
                break;
            case R.id.imgDelete3:
                if (count == 1)
                {
                   // imgDelete3.setVisibility(View.GONE);

                } else
                {
                    flagDelete = true;
                    flagNumberDelete = 3;
                    showDialogDelete();
                }
                break;
            case R.id.imgDelete4:
                if (count == 1)
                {
                  //  imgDelete4.setVisibility(View.GONE);
                } else
                {
                    flagDelete = true;
                    flagNumberDelete = 4;
                    showDialogDelete();
                }
                break;
            case R.id.imgDelete5:
                if (count == 1)
                {
                    //imgDelete5.setVisibility(View.GONE);

                } else
                {
                    flagDelete = true;
                    flagNumberDelete = 5;
                    showDialogDelete();
                }
                break;
            case R.id.btnPaymentConfirm:
                checkValidation();
                break;
            case R.id.btnBackToDetail:
                infoViewers.clear();
                onClickContinueBuyTicketListener.onBackClicked();

                break;

            case R.id.txtCondition:
                callRulsStadiumRequest();

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

    private void showDialogDelete()
    {
        MessageAlertDialog dialog = new MessageAlertDialog(getActivity(), "حذف بلیت", "آیا از حذف این بلیت مطمئن هستید؟", true, "بله", "خیر", listener);
        dialog.show(getActivity().getFragmentManager(), "dialog");
    }

    private void checkCondition()
    {

        if (flagNumberDelete == 1)
        {

            llBoxTicket1.setVisibility(View.GONE);
            count = count - 1;
            amountForPay = amountForPay - amountOneTicket;
            if (count == 1)
            {
                goneImgDelete();

            }
        }
        if (flagNumberDelete == 2)
        {

            llBoxTicket2.setVisibility(View.GONE);
            count = count - 1;
            amountForPay = amountForPay - amountOneTicket;
            if (count == 1)
            {
                goneImgDelete();

            }
        }
        if (flagNumberDelete == 3)
        {

            llBoxTicket3.setVisibility(View.GONE);
            count = count - 1;
            amountForPay = amountForPay - amountOneTicket;
            if (count == 1)
            {
                goneImgDelete();

            }
        }
        if (flagNumberDelete == 4)
        {

            llBoxTicket4.setVisibility(View.GONE);
            count = count - 1;
            amountForPay = amountForPay - amountOneTicket;
            if (count == 1)
            {
                goneImgDelete();

            }
        }
        if (flagNumberDelete == 5)
        {

            llBoxTicket5.setVisibility(View.GONE);
            count = count - 1;
            amountForPay = amountForPay - amountOneTicket;
            if (count == 1)
            {
                goneImgDelete();

            }
        }

    }

    private void goneImgDelete()
    {
        if (llBoxTicket1.getVisibility() == View.VISIBLE)
        {
            imgDelete1.setVisibility(View.GONE);
            return;
        } else if (llBoxTicket2.getVisibility() == View.VISIBLE)
        {
            imgDelete2.setVisibility(View.GONE);
            return;
        } else if (llBoxTicket3.getVisibility() == View.VISIBLE)
        {
            imgDelete3.setVisibility(View.GONE);
            return;
        } else if (llBoxTicket4.getVisibility() == View.VISIBLE)
        {
            imgDelete4.setVisibility(View.GONE);
            return;
        } else if (llBoxTicket5.getVisibility() == View.VISIBLE)
        {
            imgDelete5.setVisibility(View.GONE);
            return;
        }


    }

    private void clearAllEditText()
    {
        etNationalCode_1.setText("");
        etFamily_1.setText("");
        etName_1.setText("");

        etNationalCode_2.setText("");
        etFamily_2.setText("");
        etName_2.setText("");

        etNationalCode_3.setText("");
        etFamily_3.setText("");
        etName_3.setText("");

        etNationalCode_4.setText("");
        etFamily_4.setText("");
        etName_4.setText("");

        etNationalCode_5.setText("");
        etFamily_5.setText("");
        etName_5.setText("");
    }

    private void checkValidation()
    {
        String flagValidations = "";
        countRepetitive = 0;
        numbers = new ArrayList<String>();
        if (count == 1)
        {
            if (llBoxTicket1.getVisibility() == View.VISIBLE)
            {
                flagValidations = flagValidations + PassengerOne();
            }
            if (llBoxTicket2.getVisibility() == View.VISIBLE)
            {
                flagValidations = flagValidations + PassengerSecond();
            }
            if (llBoxTicket3.getVisibility() == View.VISIBLE)
            {
                flagValidations = flagValidations + PassengerThird();
            }
            if (llBoxTicket4.getVisibility() == View.VISIBLE)
            {
                flagValidations = flagValidations + PassengerFourth();
            }
            if (llBoxTicket5.getVisibility() == View.VISIBLE)
            {
                flagValidations = flagValidations + PassengerFifth();
            }

        } else if (count == 2)
        {
            if (llBoxTicket1.getVisibility() == View.VISIBLE)
            {
                flagValidations = flagValidations + PassengerOne();
            }
            if (llBoxTicket2.getVisibility() == View.VISIBLE)
            {
                flagValidations = flagValidations + PassengerSecond();
            }
            if (llBoxTicket3.getVisibility() == View.VISIBLE)
            {
                flagValidations = flagValidations + PassengerThird();
            }
            if (llBoxTicket4.getVisibility() == View.VISIBLE)
            {
                flagValidations = flagValidations + PassengerFourth();
            }
            if (llBoxTicket5.getVisibility() == View.VISIBLE)
            {
                flagValidations = flagValidations + PassengerFifth();
            }

        } else if (count == 3)
        {

            if (llBoxTicket1.getVisibility() == View.VISIBLE)
            {
                flagValidations = flagValidations + PassengerOne();
            }
            if (llBoxTicket2.getVisibility() == View.VISIBLE)
            {
                flagValidations = flagValidations + PassengerSecond();
            }
            if (llBoxTicket3.getVisibility() == View.VISIBLE)
            {
                flagValidations = flagValidations + PassengerThird();
            }
            if (llBoxTicket4.getVisibility() == View.VISIBLE)
            {
                flagValidations = flagValidations + PassengerFourth();
            }
            if (llBoxTicket5.getVisibility() == View.VISIBLE)
            {
                flagValidations = flagValidations + PassengerFifth();
            }
        } else if (count == 4)
        {

            if (llBoxTicket1.getVisibility() == View.VISIBLE)
            {
                flagValidations = flagValidations + PassengerOne();
            }
            if (llBoxTicket2.getVisibility() == View.VISIBLE)
            {
                flagValidations = flagValidations + PassengerSecond();
            }
            if (llBoxTicket3.getVisibility() == View.VISIBLE)
            {
                flagValidations = flagValidations + PassengerThird();
            }
            if (llBoxTicket4.getVisibility() == View.VISIBLE)
            {
                flagValidations = flagValidations + PassengerFourth();
            }
            if (llBoxTicket5.getVisibility() == View.VISIBLE)
            {
                flagValidations = flagValidations + PassengerFifth();
            }
        } else if (count == 5)
        {

            if (llBoxTicket1.getVisibility() == View.VISIBLE)
            {
                flagValidations = flagValidations + PassengerOne();
            }
            if (llBoxTicket2.getVisibility() == View.VISIBLE)
            {
                flagValidations = flagValidations + PassengerSecond();
            }
            if (llBoxTicket3.getVisibility() == View.VISIBLE)
            {
                flagValidations = flagValidations + PassengerThird();
            }
            if (llBoxTicket4.getVisibility() == View.VISIBLE)
            {
                flagValidations = flagValidations + PassengerFourth();
            }
            if (llBoxTicket5.getVisibility() == View.VISIBLE)
            {
                flagValidations = flagValidations + PassengerFifth();
            }
        }


        if (flagValidations.contains("F"))
        {
            mainView.showError(getString(R.string.Error_edit_input));
            cbCondition.setChecked(true);
            llConfirm.setVisibility(View.VISIBLE);
            llInVisible.setVisibility(View.GONE);
            infoViewers.clear();
        } else if (countRepetitive > count)
        {
            mainView.showError(getString(R.string.Error_nationall_code_input));
            cbCondition.setChecked(true);
            llConfirm.setVisibility(View.VISIBLE);
            llInVisible.setVisibility(View.GONE);
            infoViewers.clear();

        } else
        {
            callPaymentTicketRequest();
            clearAllEditText();

            //BuyTicketsFragment.buyTicketsFragment.setInfoViewers(infoViewers);


        }
    }

    private void callRulsStadiumRequest()
    {
        BuyTicketsFragment.buyTicketsFragment.showLoading();
        rulesStadium.rulesStadiumRequest(this, stadiumId);
    }

    private void callPaymentTicketRequest()
    {
        BuyTicketsFragment.buyTicketsFragment.showLoading();
        paymentTicket.paymentTicketRequest(this, infoViewers, amountForPay);
    }

    private String PassengerOne()
    {
        String flagValidations = "";
        if (etNationalCode_1.getText().toString() != null)
            if (isValidNationalCode(etNationalCode_1.getText().toString()))
            {
                flagValidations = flagValidations + "T";
                etNationalCode_1.setTextColor(Color.parseColor("#4d4d4d"));
                Prefs.putString("etNationalCode_1", etNationalCode_1.getText().toString());
                numbers.add(etNationalCode_1.getText().toString());
                countRepetitive = countRepetitive + Collections.frequency(numbers, etNationalCode_1.getText().toString());

            } else
            {
                flagValidations = flagValidations + "F";
                etNationalCode_1.setError(getString(R.string.Please_enter_the_national_code));
            }
        if (etFamily_1.getText().toString() != null)
        {

            if (etFamily_1.getText().toString().length() > 1 && !(etFamily_1.getText().toString().toLowerCase().matches("[0-9]")))
            {
                flagValidations = flagValidations + "T";
                etFamily_1.setTextColor(Color.parseColor("#4d4d4d"));
                Prefs.putString("etFamily_1", etFamily_1.getText().toString());

            } else
            {
                flagValidations = flagValidations + "F";
                etFamily_1.setError(getString(R.string.Please_enter_last_name_in_Persian));
            }

        }
        if (etName_1.getText().toString() != null)
        {

            if (etName_1.getText().toString().length() > 1 && !(etName_1.getText().toString().toLowerCase().matches("[0-9]")))
            {
                flagValidations = flagValidations + "T";
                etName_1.setTextColor(Color.parseColor("#4d4d4d"));
                Prefs.putString("etName_1", etName_1.getText().toString());

            } else
            {
                flagValidations = flagValidations + "F";
                etName_1.setError(getString(R.string.Please_enter_name_in_Persian));
            }

        }
        if (flagValidations.contains("F"))
        {
            mainView.showError("اطلاعات ورودی نامعتبر است");
            return "F";
        } else
        {
            Viewers viewer = new Viewers();
            viewer.setFirstName(etName_1.getText().toString());
            viewer.setLastName(etFamily_1.getText().toString());
            viewer.setNationalCode(etNationalCode_1.getText().toString());
            if (!ticketIdList.isEmpty())
                viewer.setTicketId(ticketIdList.get(0));
            infoViewers.add(viewer);
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
                Prefs.putString("etNationalCode_2", etNationalCode_2.getText().toString());
                numbers.add(etNationalCode_2.getText().toString());
                countRepetitive = countRepetitive + Collections.frequency(numbers, etNationalCode_2.getText().toString());

            } else
            {
                flagValidations = flagValidations + "F";
                etNationalCode_2.setError(getString(R.string.Please_enter_the_national_code));
            }
        if (etFamily_2.getText().toString() != null)
        {

            if (etFamily_2.getText().toString().length() > 1 && !(etFamily_2.getText().toString().toLowerCase().matches("[0-9]")))
            {
                flagValidations = flagValidations + "T";
                etFamily_2.setTextColor(Color.parseColor("#4d4d4d"));
                Prefs.putString("etFamily_2", etFamily_2.getText().toString());

            } else
            {
                flagValidations = flagValidations + "F";
                etFamily_2.setError(getString(R.string.Please_enter_last_name_in_Persian));
            }

        }
        if (etName_2.getText().toString() != null)
        {

            if (etName_2.getText().toString().length() > 1 && !(etName_2.getText().toString().toLowerCase().matches("[0-9]")))
            {
                flagValidations = flagValidations + "T";
                etName_2.setTextColor(Color.parseColor("#4d4d4d"));
                Prefs.putString("etName_2", etName_2.getText().toString());

            } else
            {
                flagValidations = flagValidations + "F";
                etName_2.setError(getString(R.string.Please_enter_name_in_Persian));
            }

        }
        if (flagValidations.contains("F"))
        {
            mainView.showError("اطلاعات ورودی نامعتبر است");
            return "F";
        } else
        {
            Viewers viewer = new Viewers();
            viewer.setFirstName(etName_2.getText().toString());
            viewer.setLastName(etFamily_2.getText().toString());
            viewer.setNationalCode(etNationalCode_2.getText().toString());
            if (!ticketIdList.isEmpty() && ticketIdList.size() >= 2)
                viewer.setTicketId(ticketIdList.get(1));
            infoViewers.add(viewer);
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
                Prefs.putString("etNationalCode_3", etNationalCode_3.getText().toString());
                numbers.add(etNationalCode_3.getText().toString());
                countRepetitive = countRepetitive + Collections.frequency(numbers, etNationalCode_3.getText().toString());

            } else
            {
                flagValidations = flagValidations + "F";
                etNationalCode_3.setError(getString(R.string.Please_enter_the_national_code));
            }
        if (etFamily_3.getText().toString() != null)
        {

            if (etFamily_3.getText().toString().length() > 1 && !(etFamily_3.getText().toString().toLowerCase().matches("[0-9]")))
            {
                flagValidations = flagValidations + "T";
                etFamily_3.setTextColor(Color.parseColor("#4d4d4d"));
                Prefs.putString("etFamily_3", etFamily_3.getText().toString());

            } else
            {
                flagValidations = flagValidations + "F";
                etFamily_3.setError(getString(R.string.Please_enter_last_name_in_Persian));
            }

        }
        if (etName_3.getText().toString() != null)
        {

            if (etName_3.getText().toString().length() > 1 && !(etName_3.getText().toString().toLowerCase().matches("[0-9]")))
            {
                flagValidations = flagValidations + "T";
                etName_3.setTextColor(Color.parseColor("#4d4d4d"));
                Prefs.putString("etName_3", etName_3.getText().toString());

            } else
            {
                flagValidations = flagValidations + "F";
                etName_3.setError(getString(R.string.Please_enter_name_in_Persian));
            }

        }
        if (flagValidations.contains("F"))
        {
            mainView.showError("اطلاعات ورودی نامعتبر است");
            return "F";
        } else
        {
            Viewers viewer = new Viewers();
            viewer.setFirstName(etName_3.getText().toString());
            viewer.setLastName(etFamily_3.getText().toString());
            viewer.setNationalCode(etNationalCode_3.getText().toString());
            if (!ticketIdList.isEmpty() && ticketIdList.size() >= 3)
                viewer.setTicketId(ticketIdList.get(2));
            infoViewers.add(viewer);
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
                Prefs.putString("etNationalCode_4", etNationalCode_4.getText().toString());
                numbers.add(etNationalCode_4.getText().toString());
                countRepetitive = countRepetitive + Collections.frequency(numbers, etNationalCode_4.getText().toString());

            } else
            {
                flagValidations = flagValidations + "F";
                etNationalCode_4.setError(getString(R.string.Please_enter_the_national_code));
            }
        if (etFamily_4.getText().toString() != null)
        {

            if (etFamily_4.getText().toString().length() > 1 && !(etFamily_4.getText().toString().toLowerCase().matches("[0-9]")))
            {
                flagValidations = flagValidations + "T";
                etFamily_4.setTextColor(Color.parseColor("#4d4d4d"));
                Prefs.putString("etFamily_4", etFamily_4.getText().toString());

            } else
            {
                flagValidations = flagValidations + "F";
                etFamily_4.setError(getString(R.string.Please_enter_last_name_in_Persian));
            }

        }
        if (etName_4.getText().toString() != null)
        {

            if (etName_4.getText().toString().length() > 1 && !(etName_4.getText().toString().toLowerCase().matches("[0-9]")))
            {
                flagValidations = flagValidations + "T";
                etName_4.setTextColor(Color.parseColor("#4d4d4d"));
                Prefs.putString("etName_4", etName_4.getText().toString());

            } else
            {
                flagValidations = flagValidations + "F";
                etName_4.setError(getString(R.string.Please_enter_name_in_Persian));
            }

        }
        if (flagValidations.contains("F"))
        {
            mainView.showError("اطلاعات ورودی نامعتبر است");
            return "F";
        } else
        {
            Viewers viewer = new Viewers();
            viewer.setFirstName(etName_4.getText().toString());
            viewer.setLastName(etFamily_4.getText().toString());
            viewer.setNationalCode(etNationalCode_4.getText().toString());
            if (!ticketIdList.isEmpty() && ticketIdList.size() >= 4)
                viewer.setTicketId(ticketIdList.get(3));
            infoViewers.add(viewer);
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
                Prefs.putString("etNationalCode_5", etNationalCode_5.getText().toString());
                numbers.add(etNationalCode_5.getText().toString());
                countRepetitive = countRepetitive + Collections.frequency(numbers, etNationalCode_5.getText().toString());

            } else
            {
                flagValidations = flagValidations + "F";
                etNationalCode_5.setError(getString(R.string.Please_enter_the_national_code));
            }
        if (etFamily_5.getText().toString() != null)
        {

            if (etFamily_5.getText().toString().length() > 1 && !(etFamily_5.getText().toString().toLowerCase().matches("[0-9]")))
            {
                flagValidations = flagValidations + "T";
                etFamily_5.setTextColor(Color.parseColor("#4d4d4d"));
                Prefs.putString("etFamily_5", etFamily_5.getText().toString());

            } else
            {
                flagValidations = flagValidations + "F";
                etFamily_5.setError(getString(R.string.Please_enter_last_name_in_Persian));
            }

        }
        if (etName_5.getText().toString() != null)
        {

            if (etName_5.getText().toString().length() > 1 && !(etName_5.getText().toString().toLowerCase().matches("[0-9]")))
            {
                flagValidations = flagValidations + "T";
                etName_5.setTextColor(Color.parseColor("#4d4d4d"));
                Prefs.putString("etName_5", etName_5.getText().toString());

            } else
            {
                flagValidations = flagValidations + "F";
                etName_5.setError(getString(R.string.Please_enter_name_in_Persian));
            }

        }
        if (flagValidations.contains("F"))
        {
            mainView.showError("اطلاعات ورودی نامعتبر است");
            return "F";
        } else
        {
            Viewers viewer = new Viewers();
            viewer.setFirstName(etName_5.getText().toString());
            viewer.setLastName(etFamily_5.getText().toString());
            viewer.setNationalCode(etNationalCode_5.getText().toString());
            if (!ticketIdList.isEmpty() && ticketIdList.size() >= 5)
                viewer.setTicketId(ticketIdList.get(4));
            infoViewers.add(viewer);
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

                    if (etFamily_1.getText().toString().length() > 1 && !(etFamily_1.getText().toString().toLowerCase().matches("[0-9]")))
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

                    if (etName_1.getText().toString().length() > 1 && !(etName_1.getText().toString().toLowerCase().matches("[0-9]")))
                    {

                        etName_1.setTextColor(Color.parseColor("#4d4d4d"));

                    } else
                    {

                        etName_1.setError(getString(R.string.Please_enter_name_in_Persian));
                    }

                }
                break;
            /*PassengetSecond*/
            case R.id.etNationalCode_2:
                if (etNationalCode_2.getText().toString() != null)
                    if (isValidNationalCode(etNationalCode_2.getText().toString()))
                    {
                        etNationalCode_2.setTextColor(Color.parseColor("#4d4d4d"));

                    } else
                    {

                        etNationalCode_2.setError(getString(R.string.Please_enter_the_national_code));
                    }
                break;
            case R.id.etFamily_2:
                if (etFamily_2.getText().toString() != null)
                {

                    if (etFamily_2.getText().toString().length() > 1 && !(etFamily_2.getText().toString().toLowerCase().matches("[0-9]")))
                    {

                        etFamily_2.setTextColor(Color.parseColor("#4d4d4d"));

                    } else
                    {

                        etFamily_2.setError(getString(R.string.Please_enter_last_name_in_Persian));
                    }

                }
                break;
            case R.id.etName_2:
                if (etName_2.getText().toString() != null)
                {

                    if (etName_2.getText().toString().length() > 1 && !(etName_2.getText().toString().toLowerCase().matches("[0-9]")))
                    {

                        etName_2.setTextColor(Color.parseColor("#4d4d4d"));

                    } else
                    {

                        etName_2.setError(getString(R.string.Please_enter_name_in_Persian));
                    }

                }
                break;
            /*PassengetThird*/
            case R.id.etNationalCode_3:
                if (etNationalCode_3.getText().toString() != null)
                    if (isValidNationalCode(etNationalCode_3.getText().toString()))
                    {
                        etNationalCode_3.setTextColor(Color.parseColor("#4d4d4d"));

                    } else
                    {

                        etNationalCode_3.setError(getString(R.string.Please_enter_the_national_code));
                    }
                break;
            case R.id.etFamily_3:
                if (etFamily_3.getText().toString() != null)
                {

                    if (etFamily_3.getText().toString().length() > 1 && !(etFamily_3.getText().toString().toLowerCase().matches("[0-9]")))
                    {

                        etFamily_3.setTextColor(Color.parseColor("#4d4d4d"));

                    } else
                    {

                        etFamily_3.setError(getString(R.string.Please_enter_last_name_in_Persian));
                    }

                }
                break;
            case R.id.etName_3:
                if (etName_3.getText().toString() != null)
                {

                    if (etName_3.getText().toString().length() > 1 && !(etName_3.getText().toString().toLowerCase().matches("[0-9]")))
                    {

                        etName_3.setTextColor(Color.parseColor("#4d4d4d"));

                    } else
                    {

                        etName_3.setError(getString(R.string.Please_enter_name_in_Persian));
                    }

                }
                break;
            /*PassengetForthi*/
            case R.id.etNationalCode_4:
                if (etNationalCode_4.getText().toString() != null)
                    if (isValidNationalCode(etNationalCode_4.getText().toString()))
                    {
                        etNationalCode_4.setTextColor(Color.parseColor("#4d4d4d"));

                    } else
                    {

                        etNationalCode_4.setError(getString(R.string.Please_enter_the_national_code));
                    }
                break;
            case R.id.etFamily_4:
                if (etFamily_4.getText().toString() != null)
                {

                    if (etFamily_4.getText().toString().length() > 1 && !(etFamily_4.getText().toString().toLowerCase().matches("[0-9]")))
                    {

                        etFamily_4.setTextColor(Color.parseColor("#4d4d4d"));

                    } else
                    {

                        etFamily_4.setError(getString(R.string.Please_enter_last_name_in_Persian));
                    }

                }
                break;
            case R.id.etName_4:
                if (etName_4.getText().toString() != null)
                {

                    if (etName_4.getText().toString().length() > 1 && !(etName_4.getText().toString().toLowerCase().matches("[0-9]")))
                    {

                        etName_4.setTextColor(Color.parseColor("#4d4d4d"));

                    } else
                    {

                        etName_4.setError(getString(R.string.Please_enter_name_in_Persian));
                    }

                }
                break;
            /*PassengetFive*/
            case R.id.etNationalCode_5:
                if (etNationalCode_5.getText().toString() != null)
                    if (isValidNationalCode(etNationalCode_5.getText().toString()))
                    {
                        etNationalCode_5.setTextColor(Color.parseColor("#4d4d4d"));

                    } else
                    {

                        etNationalCode_5.setError(getString(R.string.Please_enter_the_national_code));
                    }
                break;
            case R.id.etFamily_5:
                if (etFamily_5.getText().toString() != null)
                {

                    if (etFamily_5.getText().toString().length() > 1 && !(etFamily_5.getText().toString().toLowerCase().matches("[0-9]")))
                    {

                        etFamily_5.setTextColor(Color.parseColor("#4d4d4d"));

                    } else
                    {

                        etFamily_5.setError(getString(R.string.Please_enter_last_name_in_Persian));
                    }

                }
                break;
            case R.id.etName_5:
                if (etName_5.getText().toString() != null)
                {

                    if (etName_5.getText().toString().length() > 1 && !(etName_5.getText().toString().toLowerCase().matches("[0-9]")))
                    {

                        etName_5.setTextColor(Color.parseColor("#4d4d4d"));

                    } else
                    {

                        etName_5.setError(getString(R.string.Please_enter_name_in_Persian));
                    }

                }
                break;
        }
    }

    public void getDataFormBefore(String selectPositionId, Integer count, Integer amountForPay, Integer amountOneTicket, List<Integer> ticketIdList, Integer stadiumId)
    {
        this.selectPositionId = selectPositionId;
        this.textStation = "جایگاه " + selectPositionId;
        this.count = count;
        this.amountForPay = amountForPay;
        this.amountOneTicket = amountOneTicket;
        this.ticketIdList = ticketIdList;
        this.stadiumId = stadiumId;

        if (view == null)
            return;


        tvStation_1.setText(textStation);
        tvStation_2.setText(textStation);
        tvStation_3.setText(textStation);
        tvStation_4.setText(textStation);
        tvStation_5.setText(textStation);
        if (count == 1)
        {
            imgDelete1.setVisibility(View.GONE);

            llBoxTicket1.setVisibility(View.VISIBLE);
            llBoxTicket2.setVisibility(View.GONE);
            llBoxTicket3.setVisibility(View.GONE);
            llBoxTicket4.setVisibility(View.GONE);
            llBoxTicket5.setVisibility(View.GONE);
        } else if (count == 2)
        {
            imgDelete1.setVisibility(View.VISIBLE);
            imgDelete2.setVisibility(View.VISIBLE);

            llBoxTicket1.setVisibility(View.VISIBLE);
            llBoxTicket2.setVisibility(View.VISIBLE);
            llBoxTicket3.setVisibility(View.GONE);
            llBoxTicket4.setVisibility(View.GONE);
            llBoxTicket5.setVisibility(View.GONE);
        } else if (count == 3)
        {
            imgDelete1.setVisibility(View.VISIBLE);
            imgDelete2.setVisibility(View.VISIBLE);
            imgDelete3.setVisibility(View.VISIBLE);

            llBoxTicket1.setVisibility(View.VISIBLE);
            llBoxTicket2.setVisibility(View.VISIBLE);
            llBoxTicket3.setVisibility(View.VISIBLE);
            llBoxTicket4.setVisibility(View.GONE);
            llBoxTicket5.setVisibility(View.GONE);
        } else if (count == 4)
        {
            imgDelete1.setVisibility(View.VISIBLE);
            imgDelete2.setVisibility(View.VISIBLE);
            imgDelete3.setVisibility(View.VISIBLE);
            imgDelete4.setVisibility(View.VISIBLE);

            llBoxTicket1.setVisibility(View.VISIBLE);
            llBoxTicket2.setVisibility(View.VISIBLE);
            llBoxTicket3.setVisibility(View.VISIBLE);
            llBoxTicket4.setVisibility(View.VISIBLE);
            llBoxTicket5.setVisibility(View.GONE);
        } else if (count == 5)
        {
            imgDelete1.setVisibility(View.VISIBLE);
            imgDelete2.setVisibility(View.VISIBLE);
            imgDelete3.setVisibility(View.VISIBLE);
            imgDelete4.setVisibility(View.VISIBLE);
            imgDelete5.setVisibility(View.VISIBLE);

            llBoxTicket1.setVisibility(View.VISIBLE);
            llBoxTicket2.setVisibility(View.VISIBLE);
            llBoxTicket3.setVisibility(View.VISIBLE);
            llBoxTicket4.setVisibility(View.VISIBLE);
            llBoxTicket5.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public void onFinishedPaymentTicket(PaymentMatchResponse response)
    {
        BuyTicketsFragment.buyTicketsFragment.hideLoading();

  //  public SelectPaymentGateWayFragment(String url,MainAc,tionView mainView,int imageDrawable,String title,String amount)count
//String title="با انجام این پرداخت، مبلغ 250,000 ریال  بابت \"خرید 5 بلیت بازی تیم های تراکتور و استقلال\" از حساب شما کسر خواهد شد";
String title="با انجام این پرداخت ، مبلغ"+Utility.priceFormat(Integer.toString(amountForPay))+"ریال بابت خرید"+" "+count+" "+"بلیت بازی ازحساب شما کسر خواهد شد.";
        SelectPaymentGateWayFragment fragment2 = new SelectPaymentGateWayFragment(response.getUrl(),mainView, R.drawable.icon_payment_ticket,title , Utility.priceFormat(Integer.toString(amountForPay)));
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_container, fragment2);
        fragmentTransaction.commit();
      /* BuyTicketsFragment.buyTicketsFragment.openWebPayment(response.getUrl());

       // onClickContinueBuyTicketListener.onContinueClicked();
        getActivity().finish();*/
    }

    @Override
    public void onErrorPaymentTicket(String error)
    {
        infoViewers.clear();
        BuyTicketsFragment.buyTicketsFragment.hideLoading();
        Tools.showToast(getContext(), error, R.color.red);
    }

    @Override
    public void onFinishedStadiumRules(ResponseStadiumRules response)
    {
        BuyTicketsFragment.buyTicketsFragment.hideLoading();

        flagDelete = false;
        MessageAlertDialog dialog = new MessageAlertDialog(getActivity(), "قوانین و مقررات", response.getRules(), true,
                "تایید", "بستن", true, listener);

        dialog.show(getActivity().getFragmentManager(), "dialog");
    }

    @Override
    public void onErrorStadiumRules(String error)
    {
        BuyTicketsFragment.buyTicketsFragment.hideLoading();
        Tools.showToast(getContext(), error, R.color.red);
    }
}

