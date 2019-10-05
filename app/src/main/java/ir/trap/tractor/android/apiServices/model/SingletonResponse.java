package ir.trap.tractor.android.apiServices.model;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

/**
 * Created by Javad.Abadi on 4/23/2018.
 */
public class SingletonResponse
{
    private List<Response<?>> responseList;
    private static final SingletonResponse ourInstance = new SingletonResponse();

    public static SingletonResponse getInstance()
    {
        return ourInstance;
    }

    private SingletonResponse()
    {
        responseList = new ArrayList<>();
    }

    public List<Response<?>> getResponseList()
    {
        return responseList;
    }

    public void addResponse(Response<?> value)
    {
        responseList.add(value);
    }

    public void clearResponse()
    {
        responseList.clear();
    }


}
