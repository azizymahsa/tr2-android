package ir.trap.tractor.android.ui.activities.login.user;

import ir.trap.tractor.android.base.BaseView;
import ir.trap.tractor.android.base.GoToActivity;

/**
 * Created by MahtabAzizi on 10/7/2019.
 */
public interface UserView extends BaseView {
    void startActivity(GoToActivity activity);

    void openImageChooser();

    void uploadImage();
}
