package com.traap.traapapp.apiServices.model.card.getCardList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import com.traap.traapapp.apiServices.model.card.CardBankItem;

import lombok.Getter;
import lombok.Setter;

public class GetCardListResponse
{
    @SerializedName("count")
    @Expose @Getter @Setter
    private Integer count;

    @SerializedName("next")
    @Expose @Getter @Setter
    private Object next;

    @SerializedName("previous")
    @Expose @Getter @Setter
    private Object previous;

    @SerializedName("results")
    @Expose @Getter @Setter
    private List<CardBankItem> cardBankItems = null;
}
