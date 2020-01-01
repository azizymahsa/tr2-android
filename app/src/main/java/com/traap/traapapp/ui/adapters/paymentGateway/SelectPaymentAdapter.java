package com.traap.traapapp.ui.adapters.paymentGateway;

import android.os.Parcelable;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;
import com.traap.traapapp.apiServices.model.matchList.MatchItem;
import com.traap.traapapp.apiServices.model.paymentMatch.PaymentMatchRequest;
import com.traap.traapapp.models.otherModels.paymentInstance.SimChargePaymentInstance;
import com.traap.traapapp.models.otherModels.paymentInstance.SimPackPaymentInstance;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.ui.fragments.payment.PaymentFragment;
import com.traap.traapapp.ui.fragments.payment.PaymentParentActionView;
import com.traap.traapapp.ui.fragments.paymentGateWay.PaymentGatewayFragment;
import com.traap.traapapp.ui.fragments.paymentGateWay.PaymentWalletFragment;

/**
 * Created by MahsaAzizi on 11/20/2019.
 */
public class SelectPaymentAdapter extends FragmentStatePagerAdapter implements PaymentParentActionView
{

    private final MainActionView mainActionView;
    private final String amount;
    private final String title;
    private  String url;
    private final int imageDrawable;
    private final String mobile;
    private  PaymentMatchRequest paymentMatchRequest;
    private  List<MatchItem> nextMatchesList=new ArrayList<>();
    private  List<MatchItem> pastMatchesList=new ArrayList<>();
    private int numTabs;
    private int PAYMENT_STATUS=0;
  //  private T response;
    private SimChargePaymentInstance simChargePaymentInstance;
    private SimChargePaymentInstance paymentInstance;
    private SimPackPaymentInstance simPackPaymentInstance;


   /* public SelectPaymentAdapter(FragmentManager fm, int numTabs, MainActionView mainActionView,
                                String amount,String title,int imageDrawable,
                               String mobile,TicketPaymentInstance ticketPaymentInstance)
    {
        super(fm);
        this.numTabs = numTabs;

        this.mainActionView = mainActionView;
        this.amount = amount;
        this.title = title;
        this.imageDrawable = imageDrawable;
        this.mobile = mobile;


        this.paymentInstance = ticketPaymentInstance;
    }*/
    public SelectPaymentAdapter(FragmentManager fm, int numTabs, MainActionView mainActionView,
                                String amount,String title,int imageDrawable,
                                String mobile,SimChargePaymentInstance simPackPaymentInstance,String url,PaymentMatchRequest paymentMatchRequest)
    {
        super(fm);
        this.numTabs = numTabs;

        this.mainActionView = mainActionView;
        this.amount = amount;
        this.title = title;
        this.imageDrawable = imageDrawable;
        this.mobile = mobile;
        this.url = url;
        this.paymentMatchRequest = paymentMatchRequest;

        this.paymentInstance = simPackPaymentInstance;
    }


    public SelectPaymentAdapter(FragmentManager fragmentManager, int tabCount, MainActionView mainView, String amount, String title, int imageDrawable, String mobile, String url, SimChargePaymentInstance simChargePaymentInstance, SimPackPaymentInstance simPackPaymentInstance,int payment_status)
    {
        super(fragmentManager);
        this.url = url;
        this.numTabs = tabCount;
        this.mainActionView = mainView;
        this.amount = amount;
        this.title = title;
        this.imageDrawable = imageDrawable;
        this.mobile = mobile;
        this.simChargePaymentInstance = simChargePaymentInstance;
        this.simPackPaymentInstance=simPackPaymentInstance;
        this.PAYMENT_STATUS=payment_status;

    }


    private void getPaymentStatus()
    {
        if (simChargePaymentInstance!=null){
            PAYMENT_STATUS=simChargePaymentInstance.getPAYMENT_STATUS();
        }else if (simPackPaymentInstance!=null){
            PAYMENT_STATUS=simPackPaymentInstance.getPAYMENT_STATUS();
        }
    }


    @Override
    public Fragment getItem(int position)
    {
        switch (position)
        {
            case 2:
                PaymentWalletFragment tab2 = PaymentWalletFragment.newInstance(mainActionView,imageDrawable, simChargePaymentInstance,amount,mobile,title ,simPackPaymentInstance);

                return tab2;
            case 0:

                PaymentGatewayFragment tab1 = PaymentGatewayFragment.newInstance(mainActionView,url,imageDrawable,amount,title,PAYMENT_STATUS);

                return tab1;

            case 1:
                PaymentFragment tab3 = PaymentFragment.newInstance(this, amount, title, imageDrawable,
                        mobile, paymentInstance);

                return tab3;

            default:
                throw new RuntimeException("Tab position invalid " + position);
        }
    }



    @Override
    public int getCount()
    {
        return numTabs;
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
    public void onPaymentCancelAndBack()
    {

    }

    @Override
    public void startAddCardActivity()
    {

    }
}

