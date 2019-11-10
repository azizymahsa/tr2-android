package ir.traap.tractor.android.ui.activities.userProfile;

import ir.traap.tractor.android.ui.base.BaseView;
import ir.traap.tractor.android.ui.base.GoToActivity;

/**
 * Created by MahtabAzizi on 10/7/2019.
 */
public interface UserProfileActionView extends BaseView
{
    void openImageChooser();

    void uploadImage();
}
