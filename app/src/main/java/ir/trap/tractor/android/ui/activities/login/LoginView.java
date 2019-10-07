package ir.trap.tractor.android.ui.activities.login;


import ir.trap.tractor.android.base.BaseView;
import ir.trap.tractor.android.base.GoToActivity;

/**
 * Created by RezaNejati on 7/2/2018.
 */
public interface LoginView extends BaseView {
    void onButtonActions(boolean canEnter, GoToActivity goToActivity);
    void onFinishTimer();
    void onTick(String second);

}
