package com.traap.traapapp.ui.activities.deleteUser;

import android.widget.EditText;

import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.traap.traapapp.ui.base.BasePresenter;

/**
 * Created by  Mahsa.azizi
 */
public interface UserDeleteVerifyPresenter extends BasePresenter {
    void getCode(PinEntryEditText codeView);
   void getMobile(EditText mobile);
    void setScreenSize(int height, int width);

}
