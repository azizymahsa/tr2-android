package ir.traap.tractor.android.ui.adapters.ticket;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

import ir.traap.tractor.android.apiServices.model.matchList.MatchItem;
import ir.traap.tractor.android.ui.fragments.main.MainActionView;
import ir.traap.tractor.android.ui.fragments.payment.PaymentParentActionView;
import ir.traap.tractor.android.ui.fragments.ticket.CompeletInfoFragment;
import ir.traap.tractor.android.ui.fragments.ticket.BuyTicketsFragment;
import ir.traap.tractor.android.ui.fragments.ticket.OnClickContinueBuyTicket;
import ir.traap.tractor.android.ui.fragments.ticket.selectposition.SelectPositionFragment;
import ir.traap.tractor.android.ui.fragments.ticket.ShowTicketsFragment;

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
      /*      case 2://پرداخت


                tab3 = ShowTicketsFragment.newInstance("TAB4", buyTicketsFragment, mainActionView);

               // tab3=WebViewFragment.newInstance("TAB3", buyTicketsFragment,mainActionView);

                //  PaymentFragment tab3 = PaymentFragment.newInstance(8, buyTicketsFragment, this, onClickContinueBuyTicket);
               // CompeletInfoFragment tab3 = CompeletInfoFragment.newInstance("TAB3", buyTicketsFragment, mainActionView);
                //TicketPaymentInstance paymentInstance = new TicketPaymentInstance();
//                paymentInstance.setFirstName();
//                paymentInstance.setLastName();
//                paymentInstance.setNationalCode();
//                paymentInstance.setTicketId();
//                PaymentFragment tab3 = PaymentFragment.newInstance(this,
//                        "",
//                        "",
//                        0,
//                        "",
//                        paymentInstance);

                //ShowTicketsFragment tab3 =ShowTicketsFragment.newInstance("TAB3", buyTicketsFragment, mainActionView);
               // ShowTicketsFragment tab3 = ShowTicketsFragment.newInstance("TAB4",buyTicketsFragment,mainActionView);

                return tab3;
            case 3:
                tab4 = ShowTicketsFragment.newInstance("TAB4", buyTicketsFragment, mainActionView);
                return tab4;*/


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
