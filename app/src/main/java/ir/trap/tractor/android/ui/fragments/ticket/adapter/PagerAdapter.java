package ir.trap.tractor.android.ui.fragments.ticket.adapter;

import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import ir.trap.tractor.android.R;
import ir.trap.tractor.android.ui.fragments.main.MainActionView;
import ir.trap.tractor.android.ui.fragments.payment.PaymentFragment;
import ir.trap.tractor.android.ui.fragments.payment.PaymentParentActionView;
import ir.trap.tractor.android.ui.fragments.ticket.CompeletInfoFragment;
import ir.trap.tractor.android.ui.fragments.ticket.BuyTickets;
import ir.trap.tractor.android.ui.fragments.ticket.CountTicketFragment;
import ir.trap.tractor.android.ui.fragments.ticket.OnClickContinueBuyTicket;
import ir.trap.tractor.android.ui.fragments.ticket.SelectPositionFragment;
import ir.trap.tractor.android.ui.fragments.ticket.ShowTicketsFragment;
import ir.trap.tractor.android.utilities.Tools;

public class PagerAdapter
        extends FragmentStatePagerAdapter implements  PaymentParentActionView
{

    private final MainActionView mainActionView;
    private final OnClickContinueBuyTicket onClickContinueBuyTicket;
    private BuyTickets buyTickets;
    private int numTabs;
   /* private SubMenuModel[] gdsModel1;
    private SubMenuModel[] insuranceModel2;
    private SubMenuModel[] aloparkModel3;
    private SubMenuModel[] shargModel4;
    private SubMenuModel[] ghabzModel6;*/

    public PagerAdapter(FragmentManager fm, int numTabs, BuyTickets buyTickets, MainActionView mainActionView, OnClickContinueBuyTicket onClickContinueBuyTicket)
    {
        super(fm);
        this.numTabs = numTabs;

        this.buyTickets=buyTickets;
        this.mainActionView=mainActionView;
        this.onClickContinueBuyTicket=onClickContinueBuyTicket;
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
                SelectPositionFragment tab1 = SelectPositionFragment.newInstance("TAB2",buyTickets);

                return tab1;
            case 1:
                CompeletInfoFragment tab2 = CompeletInfoFragment.newInstance("TAB3",buyTickets,mainActionView);

                return tab2;
            case 2://پرداخت
               PaymentFragment tab3 = PaymentFragment.newInstance(8,buyTickets,this,onClickContinueBuyTicket);
               // ShowTicketsFragment tab3 = ShowTicketsFragment.newInstance("TAB4",buyTickets,mainActionView);

                return tab3;
            case 3:
                ShowTicketsFragment tab4 = ShowTicketsFragment.newInstance("TAB4",buyTickets,mainActionView);
                return tab4;


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

}
