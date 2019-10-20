package ir.trap.tractor.android.ui.fragments.main;

import androidx.fragment.app.Fragment;

import ir.trap.tractor.android.ui.base.BaseView;

public interface MainActionView extends BaseView
{
    void onBill();

    void onChargeSimCard();

    void onPackSimCard();

    void doTransferMoney();

    void onContact();

    void onInternetAlert();

    void showError(String message);

    void backToMainFragment();

    void openDrawer();

    void closeDrawer();
}
