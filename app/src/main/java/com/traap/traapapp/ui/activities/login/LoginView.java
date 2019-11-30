package com.traap.traapapp.ui.activities.login;


import com.traap.traapapp.ui.base.BaseView;
import com.traap.traapapp.ui.base.GoToActivity;

/**
 * Created by Javad.Abadi on 7/2/2018.
 */
public interface LoginView extends BaseView
{
    void onButtonActions(boolean canEnter, GoToActivity goToActivity);

    void onFinishTimer();

    void onTick(String second);

    void onError(String message, String name, boolean showClassName);

}
