package com.traap.traapapp.ui.fragments.paymentGateWay;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import br.com.simplepass.loading_button_lib.interfaces.OnAnimationEndListener;

import com.squareup.picasso.Picasso;
import com.traap.traapapp.R;
import com.traap.traapapp.singleton.SingletonPaymentPlace;
import com.traap.traapapp.ui.adapters.leagues.DataBean;
import com.traap.traapapp.ui.dialogs.MessageAlertDialog;
import com.traap.traapapp.ui.fragments.events.PersonEvent;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.ui.fragments.payment.PaymentParentActionView;
import com.traap.traapapp.utilities.Utility;
import com.uncopt.android.widget.text.justify.JustifiedTextView;

/**
 * Created by MahsaAzizi on 11/20/2019.
 */
public class PaymentGatewayFragment extends Fragment implements OnAnimationEndListener, View.OnClickListener
{
    private View rootView;

    private PaymentParentActionView mainView;

    /*scroll view*/
    public List<DataBean> data = new ArrayList<>();
    private String url;
    private CircularProgressButton btnBuy;
    private TextView btnBack;
    private RadioButton rbMellat, rbSaman, rbTejarat;
    private MessageAlertDialog.OnConfirmListener listener = null;
    private View llConfirm, llInVisible;
    private String amount = "";
    private String title = "پرداخت";
    private int imageDrawable = 1;
    private TextView tvWallet, tvCardsShetab, tvGateway, tvAmount;
    private JustifiedTextView tvTitlePay;
    private ImageView imgLogo;
    private int PAYMENT_STATUS =0;
    private int idBill=0;
    private ArrayList<PersonEvent> personEvents=new ArrayList<>();

    public PaymentGatewayFragment()
    {

    }

    private void setContent()
    {
        try
        {
            tvAmount.setText(Utility.priceFormat(amount));

        }catch (Exception e){
            tvAmount.setText(amount);

        }
        tvTitlePay.setText(title);

        if (imageDrawable == 0)
        {
            imgLogo.setVisibility(View.GONE);
        } else
        {
            Picasso.with(getActivity()).load(imageDrawable).into(imgLogo);
        }
    }


    public static PaymentGatewayFragment newInstance(PaymentParentActionView mainActionView, String url, int imageDrawable, String amount, String title, int PAYMENT_STATUS, int idBill, ArrayList<PersonEvent> personEvents)
    {
        PaymentGatewayFragment fragment = new PaymentGatewayFragment();
        fragment.setIdBill(idBill);
        fragment.setPersonEvents(personEvents);
        Bundle args = new Bundle();

        fragment.setMainView(mainActionView, url, imageDrawable, amount, title,PAYMENT_STATUS);
        return fragment;
    }

    private void setPersonEvents(ArrayList<PersonEvent> personEvents)
    {

        this.personEvents=personEvents;
    }

    private void setIdBill(int idBill)
    {
        this.idBill=idBill;
    }


    private void setMainView(PaymentParentActionView mainView, String url, int imageDrawable, String amount, String title, int PAYMENT_STATUS)
    {
        this.imageDrawable = imageDrawable;
        this.title = title;
        this.amount = amount;
        this.mainView = mainView;
        this.url = url;
        this.PAYMENT_STATUS=PAYMENT_STATUS;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        if (rootView != null)
        {
            rootView = null;
        }

        rootView = inflater.inflate(R.layout.fragment_payment_gateway, container, false);

//        listener = new MessageAlertDialog.OnConfirmListener()
//        {
//
//
//            @Override
//            public void onConfirmClick()
//            {
//                mainView.backToMainFragment();
//
//            }
//
//            @Override
//            public void onCancelClick()
//            {
//
//                //   mainView.backToMainFragment();
//            }
//        };
        initView();
        setContent();
        addDataRecyclerList();

        return rootView;
    }


    public void initView()
    {
        try
        {
            rbMellat = rootView.findViewById(R.id.rbMellat);
            rbMellat.setEnabled(true);
            rbMellat.setChecked(true);
            rbSaman = rootView.findViewById(R.id.rbSaman);
            rbTejarat = rootView.findViewById(R.id.rbTejarat);

            llConfirm = rootView.findViewById(R.id.llConfirm);
            llInVisible = rootView.findViewById(R.id.llInVisible);

            tvAmount = rootView.findViewById(R.id.tvAmount);
            tvTitlePay = rootView.findViewById(R.id.tvTitlePay);
            imgLogo = rootView.findViewById(R.id.imgLogo);

            btnBuy = rootView.findViewById(R.id.btnBuy);
        //    background_button_login
            btnBuy.setOnClickListener(clickListener);
            btnBack = rootView.findViewById(R.id.btnBack);
            btnBack.setOnClickListener(clickListener);

            rbMellat.setOnClickListener(this);
            rbSaman.setOnClickListener(this);
            rbTejarat.setOnClickListener(this);


            llConfirm.setVisibility(View.VISIBLE);
            llInVisible.setVisibility(View.GONE);

            rbSaman.setEnabled(false);
            rbSaman.setSelected(false);
            rbSaman.setChecked(false);
            rbTejarat.setEnabled(false);
            rbTejarat.setSelected(false);
            rbTejarat.setChecked(false);

/*

            tvTitlePay.setTypeFace(Typeface.createFromAsset(getActivity().getAssets(), "fonts/iran_sans_normal.ttf"));
            tvTitlePay.setLineSpacing(10);
            tvTitlePay.setTextSize(getResources().getDimension(R.dimen.textSize_14dp));
*/

//            FrameLayout flLogoToolbar = rootView.findViewById(R.id.flLogoToolbar);
//            flLogoToolbar.setOnClickListener(v -> {
//                mainView.backToMainFragment();
//
//            });

        } catch (Exception e)
        {
          //  Logger.e("---Exception---", e.getMessage());
        }
    }

    View.OnClickListener clickListener = v ->
    {
        if (v.getId() == R.id.btnBuy)
        {
            if (rbMellat.isChecked())
            {
                // BuyTicketsFragment.buyTicketsFragment.openWebPayment(url);
               /* Utility.openUrlCustomTab(getActivity(), url);
                getActivity().finish();*/

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivityForResult(intent,100);

                //  BuyTicketsFragment.buyTicketsFragment.openWebPayment(response.getUrl());

                //onClickContinueBuyTicketListener.onContinueClicked();
                // getActivity().finish();
                llConfirm.setVisibility(View.VISIBLE);
                llInVisible.setVisibility(View.GONE);

            } else
            {
                llConfirm.setVisibility(View.GONE);
                llInVisible.setVisibility(View.VISIBLE);
            }

        } else if (v.getId() == R.id.btnBack)
        {

           // mainView.onBackToChargFragment(PAYMENT_STATUS, PAYMENT_STATUS, idBill, personEvents);
//            mainView.onBackToChargFragment(PAYMENT_STATUS, idBill);
            getActivity().onBackPressed();
           /* MessageAlertDialog dialog = new MessageAlertDialog(getActivity(), "بازگشت به خانه", "آیا از بستن این صفحه مطمئن هستید؟",
                    true, "بله", "خیر", listener);
            dialog.show(getActivity().getFragmentManager(), "dialog");*/
        }
    };

    private void addDataRecyclerList()
    {

       /* mAdapter = new MatchAdapter(pastMatchesList,getContext(),this);
        recyclerView.setAdapter(mAdapter);


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new MatchAdapter(pastMatchesList, getActivity(),this);
        recyclerView.setAdapter(mAdapter);*/
    }


    @Override
    public void onDestroy()
    {
        super.onDestroy();

    }

    @Override
    public void onStop()
    {
        super.onStop();


    }

    @Override
    public void onResume()
    {
        super.onResume();
//        if (SingletonPaymentPlace.getInstance().getPaymentPlace()==1){
//            mainView.backToMainFragment();
//            mainView.onChargeSimCard(0);
//
//            SingletonPaymentPlace.getInstance().setPaymentPlace(0);
//
//        }else if (SingletonPaymentPlace.getInstance().getPaymentPlace()==2){
//            mainView.backToMainFragment();
//            mainView.onPackSimCard(0);
//
//            SingletonPaymentPlace.getInstance().setPaymentPlace(0);
//        }

    }

    @Override
    public void onPause()
    {
        super.onPause();
    }


    @Override
    public void onAnimationEnd()
    {


    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.rbMellat:
                rbMellat.setEnabled(true);
                llConfirm.setVisibility(View.VISIBLE);
                llInVisible.setVisibility(View.GONE);
                break;
            case R.id.rbSaman:
                rbSaman.setEnabled(false);
                rbSaman.setSelected(false);
                rbSaman.setChecked(false);
                break;
            case R.id.rbTejarat:
                rbTejarat.setEnabled(false);
                rbTejarat.setSelected(false);
                rbTejarat.setChecked(false);
                break;


        }

    }
}
