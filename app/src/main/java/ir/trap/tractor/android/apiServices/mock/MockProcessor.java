package ir.trap.tractor.android.apiServices.mock;



import android.util.Log;

import com.example.type.TypeResolver;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

import ir.trap.tractor.android.apiServices.generator.SingletonService;
import ir.trap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.trap.tractor.android.apiServices.part.BasePart;

/**
 * Created by Javad.Abadi on 4/11/2018.
 */
public class MockProcessor<T>
{
    private OnServiceStatus<T> listener;
    private BasePart part;
    Gson gson;


    public MockProcessor(OnServiceStatus<T> listener, BasePart part)
    {
        this.listener = listener;
        this.part = part;
    }

    public Mock getRawRes()
    {
        Method[] methods = part.getClass().getDeclaredMethods();
        Class<?> selectedType = TypeResolver.resolveRawArgument(OnServiceStatus.class, listener.getClass());
        Log.e("teeeeeeeeeest1", "teeeeeeeeeest2");

        for (Method method : methods)
        {
            try
            {
                Log.e("teeeeeeeeeest1", "teeeeeeeeeest1");

                if (method.isAnnotationPresent(Mock.class))
                {

                    Mock mock = method.getAnnotation(Mock.class);
                    Log.e("teeeeeeeeeest1", mock.response().getName()+ "");
                    Log.e("teeeeeeeeeest2", selectedType.getName()+ "");
                    if (mock.response().getName().equals(selectedType.getName()))
                    {
                        return mock;
                    }
                }
            } catch (Exception e)
            {

            }

        }
        return null;
    }

    private InputStream getFromString(String rawName)
    {
        return SingletonService.getInstance().getContext().getResources().openRawResource(
                SingletonService.getInstance().getContext().getResources().getIdentifier(rawName,
                        "raw", SingletonService.getInstance().getContext().getPackageName()));
    }

    public String loadJSONFromAsset()
    {
        String json = null;
        try
        {
            InputStream is = getFromString(getRawRes().jsonName());
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex)
        {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public T getMockModel()
    {
        Mock mock = getRawRes();
        if (mock == null)
            return null;
        gson = new Gson();
        return gson.fromJson(loadJSONFromAsset(), (Class<T>) mock.response());
    }
}