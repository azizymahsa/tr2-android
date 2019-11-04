package ir.trap.tractor.android.ui.fragments.ticket;

import ir.trap.tractor.android.ui.fragments.payment.PaymentParentActionView;

/**
 * Created by MahtabAzizi on 10/28/2019.
 */
public interface OnClickContinueBuyTicket extends PaymentParentActionView
{
    void onBackClicked();
    void onContinueClicked();
    void goBuyTicket();

    void onContinueSelectPosiotionClicked(int count, Integer selectPositionId);

}
