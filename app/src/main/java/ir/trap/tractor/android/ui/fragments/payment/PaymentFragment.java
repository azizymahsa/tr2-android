package ir.trap.tractor.android.ui.fragments.payment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import de.hdodenhof.circleimageview.CircleImageView;
import ir.trap.tractor.android.R;
import ir.trap.tractor.android.apiServices.model.card.getCardList.Result;
import ir.trap.tractor.android.conf.TrapConfig;
import ir.trap.tractor.android.ui.fragments.favoriteCard.FavoriteCardFragment;
import ir.trap.tractor.android.ui.fragments.favoriteCard.FavoriteCardParentActionView;
import ir.trap.tractor.android.utilities.ClearableEditText;
import ir.trap.tractor.android.utilities.Utility;

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

    private int PAYMENT_STATUS;
    private String price;
    private String title;
    private int drawableIcon;


    public PaymentFragment()
    {
        // Required empty public constructor
    }

    public static PaymentFragment newInstance(int PAYMENT_STATUS,
                                              String price,
                                              String title,
                                              int imgLogo,
                                              PaymentParentActionView paymentParentActionView,
                                              Object response)
    {
        PaymentFragment fragment = new PaymentFragment();
        fragment.setParentActionView(paymentParentActionView);

        Bundle args = new Bundle();

        args.putInt("PAYMENT_STATUS", PAYMENT_STATUS);
        args.putInt("imgLogo", imgLogo);
        args.putString("price", price);
        args.putString("title", title);
        args.putParcelable("response", (Parcelable) response);

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
            price = getArguments().getString("price");
            title = getArguments().getString("title");
            PAYMENT_STATUS = getArguments().getInt("PAYMENT_STATUS", 0);
            drawableIcon = getArguments().getInt("imgLogo", 0);
            getObjectTypeArg(getArguments().getParcelable("response"));
        }

        EventBus.getDefault().register(this);
    }

    private void getObjectTypeArg(Parcelable response)
    {
        switch (PAYMENT_STATUS)
        {
            case TrapConfig.PAYMENT_STAUS_GDS_FLIGHT:
            {

                break;
            }
            case TrapConfig.PAYMENT_STAUS_GDS_HOTEL:
            {

                break;
            }
            case TrapConfig.PAYMENT_STAUS_GDS_BUS:
            {

                break;
            }
            case TrapConfig.PAYMENT_STAUS_ChargeSimCard:
            {

                break;
            }
            case TrapConfig.PAYMENT_STAUS_PackSimCard:
            {

                break;
            }
            case TrapConfig.PAYMENT_STAUS_TransferMoney:
            {

                break;
            }
            case TrapConfig.PAYMENT_STAUS_WithoutCard:
            {

                break;
            }
            case TrapConfig.PAYMENT_STAUS_BillTicket:
            {

                break;
            }
        }
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

        btnBack.setOnClickListener(clickListener);
        btnConfirmPayment.setOnClickListener(clickListener);

        setContent();



    }

    private void setContent()
    {
        tvAmount.setText(price);
        tvTitle.setText(title);

        if (drawableIcon == 0)
        {
            imgLogo.setVisibility(View.GONE);
        }
        else
        {
            Picasso.with(getActivity()).load(drawableIcon).into(imgLogo);
        }
    }

    View.OnClickListener clickListener = v ->
    {
        if (v.getId() == R.id.btnBack)
        {

        }
        else if (v.getId() == R.id.btnBuy)
        {
            switch (PAYMENT_STATUS)
            {
                case TrapConfig.PAYMENT_STAUS_GDS_FLIGHT:
                {

                    break;
                }
                case TrapConfig.PAYMENT_STAUS_GDS_HOTEL:
                {

                    break;
                }
                case TrapConfig.PAYMENT_STAUS_GDS_BUS:
                {

                    break;
                }
                case TrapConfig.PAYMENT_STAUS_ChargeSimCard:
                {

                    break;
                }
                case TrapConfig.PAYMENT_STAUS_PackSimCard:
                {

                    break;
                }
                case TrapConfig.PAYMENT_STAUS_TransferMoney:
                {

                    break;
                }
                case TrapConfig.PAYMENT_STAUS_WithoutCard:
                {

                    break;
                }
                case TrapConfig.PAYMENT_STAUS_BillTicket:
                {

                    break;
                }
            }
        }
    };

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
        if (mCard.getBankBin().equalsIgnoreCase(TrapConfig.HappyBaseCardNo))
        {
            if (llCvv2.getVisibility() == View.VISIBLE)
            {
                YoYo.with(Techniques.SlideOutLeft).withListener(new AnimatorListenerAdapter()
                {
                    @Override
                    public void onAnimationEnd(Animator animation)
                    {
                        super.onAnimationEnd(animation);
                        llCvv2.setVisibility(View.GONE);

                    }
                }).duration(200)
                        .playOn(llCvv2);
            }
            else
            {
                llCvv2.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.SlideInLeft)
                        .duration(200)
                        .playOn(llCvv2);
            }
        }
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
