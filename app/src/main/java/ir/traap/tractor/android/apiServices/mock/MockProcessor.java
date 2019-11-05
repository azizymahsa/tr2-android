package ir.traap.tractor.android.apiServices.mock;



import android.util.Log;

import com.example.type.TypeResolver;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

import ir.traap.tractor.android.apiServices.generator.SingletonService;
import ir.traap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.traap.tractor.android.apiServices.model.WebServiceClass;
import ir.traap.tractor.android.apiServices.part.BasePart;

/**
 * Created by Javad.Abadi on 4/11/2018.
 */
public class MockProcessor<T>
{
    private OnServiceStatus<WebServiceClass<T>> listener;
    private BasePart part;
    Gson gson;


    public MockProcessor(OnServiceStatus<WebServiceClass<T>> listener, BasePart part)
    {
        this.listener = listener;
        this.part = part;
    }

    public Mock getRawRes()
    {
        Method[] methods = part.getClass().getDeclaredMethods();
        Class<?> selectedType = TypeResolver.resolveRawArgument(OnServiceStatus.class, listener.getClass());

        for (Method method : methods)
        {
            try
            {
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

    public WebServiceClass<T> getMockModel()
    {
        Mock mock = getRawRes();
        if (mock == null)
            return null;
        gson = new Gson();
        return gson.fromJson(loadJSONFromAsset(), (Class<WebServiceClass<T>>) mock.response());
    }
}