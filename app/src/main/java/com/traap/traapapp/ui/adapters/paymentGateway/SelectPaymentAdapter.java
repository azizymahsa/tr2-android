package com.traap.traapapp.ui.adapters.paymentGateway;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.model.matchList.MatchItem;
import com.traap.traapapp.apiServices.model.paymentMatch.PaymentMatchRequest;
import com.traap.traapapp.models.otherModels.paymentInstance.SimChargePaymentInstance;
import com.traap.traapapp.models.otherModels.paymentInstance.SimPackPaymentInstance;
import com.traap.traapapp.models.otherModels.predict.PredictTabModel;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.ui.fragments.payment.PaymentFragment;
import com.traap.traapapp.ui.fragments.payment.PaymentParentActionView;
import com.traap.traapapp.ui.fragments.paymentGateWay.PaymentGatewayFragment;
import com.traap.traapapp.ui.fragments.paymentGateWay.PaymentWalletFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MahsaAzizi on 11/20/2019.
 */
public class SelectPaymentAdapter extends FragmentStatePagerAdapter implements PaymentParentActionView
{
    private Context context;
    private final MainActionView mainActionView;
    private List<PredictTabModel> tabList;
    private final String amount;
    private final String title;
    private int idBill = 0;
    private String url;
    private final int imageDrawable;
    private final String mobile;
    private PaymentMatchRequest paymentMatchRequest;
    private List<MatchItem> nextMatchesList = new ArrayList<>();
    private List<MatchItem> pastMatchesList = new ArrayList<>();
//    private int numTabs;
    private int PAYMENT_STATUS = 0;
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
//    public SelectPaymentAdapter(FragmentManager fm, int numTabs, MainActionView mainActionView,
//                                String amount, String title, int imageDrawable,
//                                String mobile, SimChargePaymentInstance simPackPaymentInstance, String url, PaymentMatchRequest paymentMatchRequest)
//    {
//        super(fm);
//        this.numTabs = numTabs;
//
//        this.mainActionView = mainActionView;
//        this.amount = amount;
//        this.title = title;
//        this.imageDrawable = imageDrawable;
//        this.mobile = mobile;
//        this.url = url;
//        this.paymentMatchRequest = paymentMatchRequest;
//
//        this.paymentInstance = simPackPaymentInstance;
//    }


    public SelectPaymentAdapter(Context context,
                                FragmentManager fragmentManager,
                                List<PredictTabModel> tabList,
                                MainActionView mainView,
                                String amount,
                                String title,
                                int imageDrawable,
                                String mobile,
                                String url,
                                SimChargePaymentInstance simChargePaymentInstance,
                                SimPackPaymentInstance simPackPaymentInstance,
                                int payment_status,
                                int idBill
    )
    {
        super(fragmentManager);
        this.url = url;
        this.tabList = tabList;
        this.mainActionView = mainView;
        this.amount = amount;
        this.context = context;
        this.title = title;
        this.imageDrawable = imageDrawable;
        this.mobile = mobile;
        this.simChargePaymentInstance = simChargePaymentInstance;
        this.simPackPaymentInstance = simPackPaymentInstance;
        this.PAYMENT_STATUS = payment_status;
        this.idBill = idBill;

    }


    private void getPaymentStatus()
    {
        if (simChargePaymentInstance != null)
        {
            PAYMENT_STATUS = simChargePaymentInstance.getPAYMENT_STATUS();
        }
        else if (simPackPaymentInstance != null)
        {
            PAYMENT_STATUS = simPackPaymentInstance.getPAYMENT_STATUS();
        }
    }

    public View getTabView(int position)
    {
        // Given you have a custom layout in `res/layout/tab_category_content.xml` with a TextView
        View v = LayoutInflater.from(context).inflate(R.layout.tab_category_content, null);

        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/iran_sans_normal.ttf");

        TextView tv = v.findViewById(R.id.textView);
        tv.setText(getPageTitle(position));
        tv.setGravity(Gravity.CENTER_HORIZONTAL);
        tv.setTextColor(context.getResources().getColorStateList(R.color.textColorSecondary));
        tv.setTypeface(font);
        return v;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position)
    {
        try
        {
            return tabList.get(position).getTitle();
        }
        catch (NullPointerException e)
        {
            return "";
        }
    }

    @Override
    public Fragment getItem(int position)
    {
        switch (position)
        {
            case 2:
            {
                PaymentWalletFragment tab2 = PaymentWalletFragment.newInstance(mainActionView, imageDrawable, simChargePaymentInstance, amount, mobile, title, simPackPaymentInstance, PAYMENT_STATUS, idBill);
                return tab2;
            }
            case 0:
            {
                PaymentGatewayFragment tab1 = PaymentGatewayFragment.newInstance(mainActionView, url, imageDrawable, amount, title, PAYMENT_STATUS, idBill);
                return tab1;
            }
            case 1:
            {
                PaymentFragment tab3 = PaymentFragment.newInstance(this, amount, title, imageDrawable, mobile, paymentInstance, idBill);

                return tab3;
            }
            default:
                throw new RuntimeException("Tab position invalid " + position);
        }
    }


    @Override
    public int getCount()
    {
        return tabList.size();
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

