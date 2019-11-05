package ir.traap.tractor.android.ui.activities.login;

import android.widget.EditText;

import com.alimuzaffar.lib.pin.PinEntryEditText;

import ir.traap.tractor.android.ui.base.BasePresenter;

/**
 * Created by Javad.Abadi on 7/2/2018.
 */
public interface LoginPresenter extends BasePresenter {
    void getCode(PinEntryEditText codeView);
    void getMobile(EditText mobile);
    void setScreenSize(int height, int width);

}
