package ir.trap.tractor.android.base;

/**
 * Created by MahtabAzizi on 10/7/2019.
 */

public interface BaseView<T>
{
    void showProgress();

    void hideProgress();

    void onError(String message, String name, boolean showClassName);

    void onFinish(int resultCode);
}
