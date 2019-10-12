package ir.trap.tractor.android.ui.fragments.main;

import ir.trap.tractor.android.apiServices.generator.SingletonService;
import ir.trap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.trap.tractor.android.apiServices.model.tourism.GetUserPassResponse;

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
        SingletonService.getInstance().getHotelService().hotelUserPass(new OnServiceStatus<GetUserPassResponse>()
        {
            @Override
            public void onReady(GetUserPassResponse response)
            {
                try
                {
                    if (response.getServiceMessage().getCode() == 200)
                    {
                        listener.onGdsHotel(response);
                    }
                    else
                    {
                        listener.onGdsError(response.getServiceMessage().getMessage());
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
        SingletonService.getInstance().getBusService().userPass(new OnServiceStatus<GetUserPassResponse>()
        {
            @Override
            public void onReady(GetUserPassResponse response)
            {
                try
                {
                    if (response.getServiceMessage().getCode() == 200)
                    {
                        listener.onGdsBus(response);
                    }
                    else
                    {
                        listener.onGdsError(response.getServiceMessage().getMessage());
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
        SingletonService.getInstance().getFlightService().userPass(new OnServiceStatus<GetUserPassResponse>()
        {
            @Override
            public void onReady(GetUserPassResponse response)
            {
                try
                {
                    if (response.getServiceMessage().getCode() == 200)
                    {
                        listener.onGdsFlight(response);
                    }
                    else
                    {
                        listener.onGdsError(response.getServiceMessage().getMessage());
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
