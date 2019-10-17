package ir.trap.tractor.android.ui.fragments.payment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import de.hdodenhof.circleimageview.CircleImageView;
import ir.trap.tractor.android.R;
import ir.trap.tractor.android.apiServices.model.card.getCardList.Result;
import ir.trap.tractor.android.ui.fragments.favoriteCard.FavoriteCardFragment;
import ir.trap.tractor.android.ui.fragments.favoriteCard.FavoriteCardParentActionView;
import ir.trap.tractor.android.utilities.ClearableEditText;

public class PaymentFragment extends Fragment implements FavoriteCardParentActionView, PaymentParentActionView, PaymentActionView
{

    private Fragment cardFragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    private Result mCard;

    private TextView tvAmount, tvTitle;
    private CircleImageView imgLogo;
    private View btnBack;
    private CircularProgressButton btnConfirmPayment;
    private ClearableEditText etCvv2, etPass;

    private LinearLayout llCvv2;
    private RelativeLayout rlTitle;

    private PaymentParentActionView pActionView;


    public PaymentFragment()
    {
        // Required empty public constructor
    }

    public static PaymentFragment newInstance(int PAYMENT_STAUS,
                                              String price,
                                              String title,
                                              int imgLogo,
                                              PaymentParentActionView paymentParentActionView,
                                              Object object)
    {
        PaymentFragment fragment = new PaymentFragment();
        fragment.setParentActionView(paymentParentActionView);

        Bundle args = new Bundle();

        args.putInt("PAYMENT_STAUS", PAYMENT_STAUS);
        args.putInt("imgLogo", imgLogo);
        args.putString("price", price);
        args.putString("title", title);

        fragment.setArguments(args);

        return fragment;
    }

    private void setParentActionView(PaymentParentActionView pActionView)
    {
        this.pActionView = pActionView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        mCard = new Result();

        if (getArguments() != null)
        {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_payment, container, false);

        initView(rootView);

        return rootView;
    }

    private void initView(View rootView)
    {

        //----------------card fragment----------------
        fragmentManager = getChildFragmentManager();
        cardFragment = FavoriteCardFragment.newInstance(this);

        transaction = fragmentManager.beginTransaction();

        if (transaction.isEmpty())
        {
            transaction.add(R.id.container, cardFragment)
                    .commit();
        }
        else
        {
            transaction.replace(R.id.container, cardFragment)
                    .commit();
        }
        //----------------card fragment----------------

        tvAmount = rootView.findViewById(R.id.tvAmount);
        tvTitle = rootView.findViewById(R.id.tvTitle);
        imgLogo = rootView.findViewById(R.id.imgLogo);
        btnBack = rootView.findViewById(R.id.btnBack);
        btnConfirmPayment = rootView.findViewById(R.id.btnBuy);
        etCvv2 = rootView.findViewById(R.id.etCvv2);
        etPass = rootView.findViewById(R.id.etPass);
        llCvv2 = rootView.findViewById(R.id.llCvv2);
        rlTitle = rootView.findViewById(R.id.rlTitle);

        setContent();

    }

    private void setContent()
    {

    }


    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener)
//        {
//            mListener = (OnFragmentInteractionListener) context;
//        } else
//        {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
//        mListener = null;

        EventBus.getDefault().unregister(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetCardDetails(Result card)
    {
        mCard = card;
    }


    @Override
    public void showFavoriteCardParentLoading()
    {

    }

    @Override
    public void hideFavoriteCardParentLoading()
    {

    }

    @Override
    public void showPaymentParentLoading()
    {

    }

    @Override
    public void hidePaymentParentLoading()
    {

    }

    @Override
    public void onPaymentGDSFlight()
    {

    }

    @Override
    public void onPaymentGDSHotel()
    {

    }

    @Override
    public void onPaymentGDSBus()
    {

    }

    @Override
    public void onPaymentChargeSimCard()
    {

    }

    @Override
    public void onPaymentPackSimCard()
    {

    }

    @Override
    public void onPaymentTransferMoney()
    {

    }

    @Override
    public void onPaymentWithoutCard()
    {

    }

    @Override
    public void onPaymentBillTicket()
    {

    }
}
