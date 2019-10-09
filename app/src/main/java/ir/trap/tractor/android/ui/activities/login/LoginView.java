package ir.trap.tractor.android.ui.activities.login;


import ir.trap.tractor.android.ui.base.BaseView;
import ir.trap.tractor.android.ui.base.GoToActivity;

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
