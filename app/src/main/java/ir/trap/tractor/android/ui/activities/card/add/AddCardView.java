package ir.trap.tractor.android.ui.activities.card.add;


import ir.trap.tractor.android.ui.base.BaseView;

/**
 * Created by Javad.Abadi on 7/16/2018.
 */
public interface AddCardView extends BaseView
{
    void getData();
    void onSuccess(String message);
    void onError(String message);
    void onFinish(int code);

}
