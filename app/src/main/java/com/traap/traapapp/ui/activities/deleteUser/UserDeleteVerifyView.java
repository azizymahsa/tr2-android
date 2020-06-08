package com.traap.traapapp.ui.activities.deleteUser;


import com.traap.traapapp.ui.base.BaseView;
import com.traap.traapapp.ui.base.GoToActivity;

/**
 * Created by Mahsa.azizi
 */
public interface UserDeleteVerifyView extends BaseView
{
    void onButtonActions(boolean canEnter, GoToActivity goToActivity);

    void onFinishTimer();

    void onTick(String second);

    void showErrorMessage(String message, String name, boolean showClassName);
    void showErrorMessage(String message);

}
