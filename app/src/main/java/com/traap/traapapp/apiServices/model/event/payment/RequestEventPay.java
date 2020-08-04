
package com.traap.traapapp.apiServices.model.event.payment;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestEventPay implements Serializable
{

    @SerializedName("event_participants")
    @Expose
    private List<EventParticipant> eventParticipants = null;
    private final static long serialVersionUID = 6749667876017471383L;

    public List<EventParticipant> getEventParticipants() {
        return eventParticipants;
    }

    public void setEventParticipants(List<EventParticipant> eventParticipants) {
        this.eventParticipants = eventParticipants;
    }

}
