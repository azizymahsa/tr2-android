package com.traap.traapapp.ui.fragments.main;


import com.traap.traapapp.ui.base.BaseView;
import com.traap.traapapp.ui.base.GoToActivity;

/**
 * Created by Javad.Abadi on 7/2/2018.
 */
public interface CountDownTimerView
{
    void onFinishTimer();

    void onTickTimer(String time);

    void onErrorTimer(String message);

}
