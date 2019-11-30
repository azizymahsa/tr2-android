package com.traap.traapapp.ui.fragments.main;

import com.traap.traapapp.apiServices.model.tourism.GetUserPassResponse;

public interface onConfirmUserPassGDS
{
    void onGdsFlight(GetUserPassResponse response);

    void onGdsBus(GetUserPassResponse response);

    void onGdsHotel(GetUserPassResponse response);

    void onGdsError(String message);
}
