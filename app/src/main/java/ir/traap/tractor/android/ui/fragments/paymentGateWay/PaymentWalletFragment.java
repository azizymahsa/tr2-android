package ir.traap.tractor.android.ui.fragments.paymentGateWay;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import br.com.simplepass.loading_button_lib.interfaces.OnAnimationEndListener;
import ir.traap.tractor.android.R;
import ir.traap.tractor.android.apiServices.model.matchList.MatchItem;
import ir.traap.tractor.android.apiServices.model.paymentMatch.PaymentMatchRequest;
import ir.traap.tractor.android.apiServices.model.paymentWallet.ResponsePaymentWallet;
import ir.traap.tractor.android.ui.activities.main.MainActivity;
import ir.traap.tractor.android.ui.activities.ticket.ShowTicketActivity;
import ir.traap.tractor.android.ui.adapters.Leaguse.DataBean;
import ir.traap.tractor.android.ui.adapters.Leaguse.matchResult.MatchAdapter;
import ir.traap.tractor.android.ui.dialogs.MessageAlertDialog;
import ir.traap.tractor.android.ui.fragments.main.MainActionView;
import ir.traap.tractor.android.ui.fragments.paymentGateWay.paymentWallet.PaymentWalletImpl;
import ir.traap.tractor.android.ui.fragments.paymentGateWay.paymentWallet.PaymentWalletInteractor;
import ir.traap.tractor.android.ui.fragments.ticket.BuyTicketsFragment;
import ir.traap.tractor.android.utilities.Logger;
import ir.traap.tractor.android.utilities.Tools;
import ir.traap.tractor.android.utilities.Utility;

/**
 * Created by MahsaAzizi on 11/25/2019.
 */
public class PaymentWalletFragment extends Fragment implements OnAnimationEndListener, View.OnClickListener, MatchAdapter.ItemClickListener, PaymentWalletInteractor.OnFinishedPaymentWalletListener
{
    private View rootView;

    private MainActionView mainView;

    /*scroll view*/
    public List<DataBean> data = new ArrayList<>();
    private CircularProgressButton btnBuy;
    private TextView btnBack;
    private EditText etPin2;
    private MessageAlertDialog.OnConfirmListener listener = null;
    private View llConfirm, llInVisible;
    private PaymentMatchRequest paymentMatchRequest;
    private PaymentWalletImpl paymentWallet;


    public PaymentWalletFragment()
    {

    }


    public static PaymentWalletFragment newInstance(MainActionView mainActionView, PaymentMatchRequest paymentMatchRequest)
    {
        PaymentWalletFragment fragment = new PaymentWalletFragment();
        Bundle args = new Bundle();

        fragment.setMainView(mainActionView, paymentMatchRequest);
        return fragment;
    }


    private void setMainView(MainActionView mainView, PaymentMatchRequest paymentMatchRequest)
    {
        this.mainView = mainView;
        this.paymentMatchRequest = paymentMatchRequest;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }

    public void initView()
    {
        try
        {

            etPin2 = rootView.findViewById(R.id.etPin2);

            llConfirm = rootView.findViewById(R.id.llConfirm);
            llInVisible = rootView.findViewById(R.id.llInVisible);

            btnBuy = rootView.findViewById(R.id.btnBuy);
            btnBuy.setOnClickListener(clickListener);
            btnBack = rootView.findViewById(R.id.btnBack);
            btnBack.setOnClickListener(clickListener);


            llConfirm.setVisibility(View.VISIBLE);


        } catch (Exception e)
        {
            Logger.e("---Exception---", e.getMessage());
        }
    }

    View.OnClickListener clickListener = v ->
    {
        if (v.getId() == R.id.btnBuy)
        {


            if (etPin2.getText().toString() != null && etPin2.getText().toString().length()>3)
            {
                paymentMatchRequest.setPin2(etPin2.getText().toString());

                callPaymentWalletRequest();
            }else{
                Tools.showToast(getContext(), "رمز کارت وارد نشده است.", R.color.red);

            }


        } else if (v.getId() == R.id.btnBack)
        {
            MessageAlertDialog dialog = new MessageAlertDialog(getActivity(), "بازگشت به خانه", "آیا از بستن این صفحه مطمئن هستید؟",
                    false, "بله", "بستن", listener);
            dialog.show(getActivity().getFragmentManager(), "dialog");
        }
    };

    private void callPaymentWalletRequest()
    {
        mainView.showLoading();
        paymentWallet.paymentWalletRequest(this, paymentMatchRequest);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        if (rootView != null)
        {
            rootView = null;
        }

        rootView = inflater.inflate(R.layout.fragment_payment_wallet, container, false);

        listener = new MessageAlertDialog.OnConfirmListener()
        {


            @Override
            public void onConfirmClick()
            {
                mainView.backToMainFragment();

            }

            @Override
            public void onCancelClick()
            {

                mainView.backToMainFragment();
            }
        };
        initView();
        paymentWallet = new PaymentWalletImpl();
        addDataRecyclerList();

        return rootView;
    }

    private void addDataRecyclerList()
    {


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
           /* case R.id.rbMellat:
                rbMellat.setEnabled(true);
                llConfirm.setVisibility(View.VISIBLE);
                llInVisible.setVisibility(View.GONE);
                break;
           */
        }

    }


    @Override
    public void onItemClick(View view, int position, MatchItem matchItem)
    {

    }

    @Override
    public void onItemPredictClick(View view, int position, MatchItem matchItem)
    {

    }

    @Override
    public void onFinishedPaymentWallet(ResponsePaymentWallet response)
    {
        mainView.hideLoading();
        if( response.getMessage().contains("خطا")){
            Tools.showToast(getContext(), response.getMessage(), R.color.red);


        }else{
            Intent intent = new Intent(getContext(), ShowTicketActivity.class);

            intent.putExtra("RefrenceNumber",  response.getRefNumber());
            intent.putExtra("isTransactionList",false);

            startActivity(intent);
        }




    }

    @Override
    public void onErrorPaymentWallet(String error)
    {
        mainView.hideLoading();
        Tools.showToast(getContext(), error, R.color.red);
    }
}
