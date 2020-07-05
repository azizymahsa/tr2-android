package com.traap.traapapp.ui.fragments.predict.predictSystemTeam;

import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.predict.predictSystem.getSystem.response.GetPredictSystemFromIdResponse;
import com.traap.traapapp.apiServices.model.predict.predictSystem.sendPredictPlayers.request.PlayerPositioning;
import com.traap.traapapp.apiServices.model.predict.predictSystem.sendPredictPlayers.request.SendPredictSystemPlayersRequest;
import com.traap.traapapp.utilities.Logger;

import java.util.List;

public class PredictSendPlayersWithPositionImpl
{
    public static void SendPlayersWithPosition(Integer matchId, int formationId, List<PlayerPositioning> playerPositioning, onSendPlayersWithPositionListener listener)
    {
        SendPredictSystemPlayersRequest request = new SendPredictSystemPlayersRequest();
        request.setFormationId(formationId);
        request.setPlayerPositioning(playerPositioning);

        SingletonService.getInstance().getPredictService().sendPredictSystemPlayers(matchId, request, new OnServiceStatus<WebServiceClass<Object>>()
        {
            @Override
            public void onReady(WebServiceClass<Object> response)
            {
                if (response == null || response.data == null)
                {
                    listener.onSendPlayersWithPositionError("خطا در دریافت اطلاعات از سرور!");
                    Logger.e("-getPredictSystemFromId-", "null");
                    return;
                }
                if (response.info.statusCode != 200)
                {
                    listener.onSendPlayersWithPositionError(response.info.message);
                }
                else
                {
                    listener.onSendPlayersWithPositionCompleted(response.info.message);
                }
            }

            @Override
            public void onError(String message)
            {
                listener.onSendPlayersWithPositionError(message);
            }
        });
    }


    public interface onSendPlayersWithPositionListener
    {
        void onSendPlayersWithPositionCompleted(String message);

        void onSendPlayersWithPositionError(String message);
    }
}
