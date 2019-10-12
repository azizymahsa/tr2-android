package ir.trap.tractor.android.apiServices.part;

import android.content.Intent;
import android.os.Handler;

import java.io.IOException;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import ir.trap.tractor.android.BuildConfig;
import ir.trap.tractor.android.apiServices.generator.ServiceGenerator;
import ir.trap.tractor.android.apiServices.helper.Const;
import ir.trap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.trap.tractor.android.apiServices.mock.MockProcessor;
import ir.trap.tractor.android.apiServices.model.SingletonResponse;
import ir.trap.tractor.android.singleton.SingletonContext;
import ir.trap.tractor.android.ui.activities.login.LoginActivity;
import ir.trap.tractor.android.utilities.Logger;
import okhttp3.RequestBody;
import okio.Buffer;
import retrofit2.Response;


/**
 * Created by Javad.Abadi on 3/26/2018.
 */

public abstract class BasePart
{
    private ServiceGenerator serviceGenerator;
//    private CompositeDisposable disposable = new CompositeDisposable();

    public BasePart(ServiceGenerator serviceGenerator)
    {
        this.serviceGenerator = serviceGenerator;
    }

    protected abstract BasePart getPart();

//    public CompositeDisposable getDisposable()
//    {
//        return disposable;
//    }
//
//    public void setDisposable(CompositeDisposable disposable)
//    {
//        this.disposable = disposable;
//    }

    public ServiceGenerator getServiceGenerator()
    {
        return serviceGenerator;
    }

    public <T> void start(Single<Response<T>> single, OnServiceStatus<T> listener)
    {
        if (!BuildConfig.DEBUG)
        {
            call(single, listener);
            return;
        }
        MockProcessor<T> mockProcessor = new MockProcessor<>(listener, getPart());

        if (Const.MOCK && mockProcessor.getRawRes() != null && mockProcessor.loadJSONFromAsset() != null)
        {
            T model = mockProcessor.getMockModel();
            if (model == null)
            {
                call(single, listener);
                return;
            }
            new Handler().postDelayed(() -> listener.onReady(model), 50);
            return;
        }
        call(single, listener);
    }


    private <T> void call(Single<Response<T>> observable, OnServiceStatus<T> listener)
    {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<Response<T>>()
                {
                    @Override
                    public void onSubscribe(Disposable d)
                    {
//                        disposable = (CompositeDisposable) d;
                    }

                    @Override
                    public void onSuccess(Response<T> value)
                    {
                        if (BuildConfig.DEBUG && Const.TEST)
                        {
                            SingletonResponse.getInstance().addResponse(value);
                        }
                        if (BuildConfig.DEBUG)
                        {
                            Logger.e("--URL--", value.raw().request().url().toString());
                            Logger.e("--Body--", bodyToString(value.raw().request().body()));
                        }
//                        if (value.code() == 403)
//                        {
//                            SingletonContext.getInstance().getContext().startActivity(new Intent(SingletonContext.getInstance().getContext(),
//                                    LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
//                            System.exit(0);
//
//                        }

                        listener.onReady(value.body());
                    }

                    @Override
                    public void onError(Throwable e)
                    {
                        e.printStackTrace();
                        listener.onError(e.getMessage());
                    }
                });
    }


    private String bodyToString(final RequestBody request)
    {
        try
        {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            if (copy != null)
                copy.writeTo(buffer);
            else
                return "";
            return buffer.readUtf8();
        }
        catch (final IOException e)
        {
            return "did not work";
        }
    }

}
