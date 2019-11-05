package ir.traap.tractor.android.ui.fragments.main;

import ir.traap.tractor.android.apiServices.model.tourism.GetUserPassResponse;

public interface onConfirmUserPassGDS
{
    void onGdsFlight(GetUserPassResponse response);

    void onGdsBus(GetUserPassResponse response);

    void onGdsHotel(GetUserPassResponse response);

    void onGdsError(String message);
}
