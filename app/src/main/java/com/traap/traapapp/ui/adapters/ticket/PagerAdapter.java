package com.traap.traapapp.ui.adapters.ticket;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

import com.traap.traapapp.apiServices.model.matchList.MatchItem;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.ui.fragments.payment.PaymentParentActionView;
import com.traap.traapapp.ui.fragments.ticket.CompeletInfoFragment;
import com.traap.traapapp.ui.fragments.ticket.BuyTicketsFragment;
import com.traap.traapapp.ui.fragments.ticket.OnClickContinueBuyTicket;
import com.traap.traapapp.ui.fragments.ticket.SelectPositionFragment;
import com.traap.traapapp.ui.fragments.ticket.ShowTicketsFragment;

public class PagerAdapter
        extends FragmentStatePagerAdapter implements  PaymentParentActionView
{

    private final MainActionView mainActionView;
    private final OnClickContinueBuyTicket onClickContinueBuyTicket;
    private final MatchItem matchBuyable;
    private BuyTicketsFragment buyTicketsFragment;
    private int numTabs;
    private CompeletInfoFragment tab2;
    private ShowTicketsFragment tab4;
    private ShowTicketsFragment tab3;


    public PagerAdapter(FragmentManager fm, int numTabs, BuyTicketsFragment buyTicketsFragment, MainActionView mainActionView
            , OnClickContinueBuyTicket onClickContinueBuyTicket , MatchItem matchBuyable)
    {
        super(fm);
        this.numTabs = numTabs;

        this.buyTicketsFragment = buyTicketsFragment;
        this.mainActionView = mainActionView;
        this.onClickContinueBuyTicket = onClickContinueBuyTicket;

        this.matchBuyable=matchBuyable;
      /*  this.gdsModel1 = gdsModel1;
        this.insuranceModel2 = insuranceModel2;
        this.aloparkModel3 = aloparkModel3;
        this.shargModel4 = shargModel4;
        this.ghabzModel6 = ghabzModel6;*/
    }

    @Override
    public Fragment getItem(int position)
    {
        switch (position)
        {
            case 0:
                SelectPositionFragment tab1 = SelectPositionFragment.newInstance("TAB1", buyTicketsFragment,matchBuyable);

                return tab1;
            case 1:
                 tab2 = CompeletInfoFragment.newInstance("TAB2", buyTicketsFragment,mainActionView);

               // createInstance();
                return tab2;
            case 2:
                SelectPositionFragment tab3 = SelectPositionFragment.newInstance("TAB3", buyTicketsFragment,matchBuyable);

                return tab3;


            default:
                throw new RuntimeException("Tab position invalid " + position);
        }
    }

    public void compeletInfoFragmentData(String selectPositionId, Integer count, Integer amountForPay,Integer amountOneTicket, List<Integer> ticketIdList,Integer stadiumId)
    {
        tab2.getDataFormBefore(selectPositionId,count,amountForPay,amountOneTicket,ticketIdList,stadiumId);

    }


    @Override
    public int getCount()
    {
        return numTabs;
    }


    @Override
    public void showPaymentParentLoading()
    {
        mainActionView.showLoading();
    }

    @Override
    public void hidePaymentParentLoading()
    {
        mainActionView.hideLoading();
    }


    @Override
    public void startAddCardActivity()
    {
        mainActionView.startAddCardActivity();
    }


    @Override
    public void onPaymentCancelAndBack()
    {

    }

    public void createShareShowTicket()
    {
        tab3.setSharedData();
    }
}
