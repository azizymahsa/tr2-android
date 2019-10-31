package ir.trap.tractor.android.ui.activities.splash;

public interface UpdateAppAction
{
    void onCancel();

    void onDetailUpdate();

    void onUpdate();

    void onUpdateFromCafeBazaar();

    void onUpdateFromGooglePlay();

    void onUpdateFromWebSite(String downloadUrl);
}
