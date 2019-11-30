package com.traap.traapapp.ui.fragments.main;

import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.tourism.GetUserPassResponse;

public abstract class GetUserPassGdsImp
{
    public static int GDS_TYPE_FLIGHT = 0;
    public static int GDS_TYPE_BUS = 1;
    public static int GDS_TYPE_HOTEL = 2;

    public static void getUserPassGds(Integer type, onConfirmUserPassGDS listener)
    {
        if (type == GDS_TYPE_FLIGHT)
        {
            callFlightAPI(listener);
        }
        else if (type == GDS_TYPE_BUS)
        {
            callBusAPI(listener);
        }
        else if (type == GDS_TYPE_HOTEL)
        {
            callHotelAPI(listener);
        }
    }

    private static void callHotelAPI(onConfirmUserPassGDS listener)
    {
        SingletonService.getInstance().getHotelService().hotelUserPass(new OnServiceStatus<WebServiceClass<GetUserPassResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<GetUserPassResponse >response)
            {
                try
                {
                    if (response.info.statusCode == 200)
                    {
                        listener.onGdsHotel(response.data);
                    }
                    else
                    {
                        listener.onGdsError(response.info.message);
                    }

                }
                catch (Exception e)
                {
                    listener.onGdsError(e.getMessage());
                }
            }

            @Override
            public void onError(String message)
            {
                listener.onGdsError(message);
            }
        });
    }

    private static void callBusAPI(onConfirmUserPassGDS listener)
    {
        SingletonService.getInstance().getBusService().userPass(new OnServiceStatus<WebServiceClass<GetUserPassResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<GetUserPassResponse> response)
            {
                try
                {
                    if (response.info.statusCode == 200)
                    {
                        listener.onGdsBus(response.data);
                    }
                    else
                    {
                        listener.onGdsError(response.info.message);
                    }

                }
                catch (Exception e)
                {
                    listener.onGdsError(e.getMessage());
                }

            }

            @Override
            public void onError(String message)
            {
                listener.onGdsError(message);
            }
        });
    }

    private static void callFlightAPI(onConfirmUserPassGDS listener)
    {
        SingletonService.getInstance().getFlightService().userPass(new OnServiceStatus<WebServiceClass<GetUserPassResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<GetUserPassResponse> response)
            {
                try
                {
                    if (response.info.statusCode == 200)
                    {
                        listener.onGdsFlight(response.data);
                    }
                    else
                    {
                        listener.onGdsError(response.info.message);
                    }

                }
                catch (Exception e)
                {
                    listener.onGdsError(e.getMessage());
                }

            }

            @Override
            public void onError(String message)
            {
                listener.onGdsError(message);
            }
        });
    }
}
