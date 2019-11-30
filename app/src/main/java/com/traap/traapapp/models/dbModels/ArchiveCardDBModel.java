package com.traap.traapapp.models.dbModels;

import io.realm.RealmObject;
import lombok.Getter;
import lombok.Setter;

public class ArchiveCardDBModel extends RealmObject
{
    @Getter @Setter
    private Long _Id;

    @Getter @Setter
    private String fullName;

    @Getter @Setter
    private String cardNo;

    @Getter @Setter
    private String expireMonth;

    @Getter @Setter
    private String expireYear;

    @Getter @Setter
    private int cardId;

    @Getter @Setter
    private boolean isFavorite;

    @Getter @Setter
    private String cardImage;

    @Getter @Setter
    private String cardName;

    @Getter @Setter
    private String cardNumberColor;

    @Getter @Setter
    private String backCardImage;

    @Getter @Setter
    private boolean isMainCard = false;

    @Getter @Setter
    private String bankName;

    @Getter @Setter
    private String textColor;
}
