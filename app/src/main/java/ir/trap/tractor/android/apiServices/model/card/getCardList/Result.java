package ir.trap.tractor.android.apiServices.model.card.getCardList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class Result
{
    @SerializedName("id")
    @Expose @Getter @Setter
    private Integer cardId;


    @SerializedName("bank")
    @Expose @Getter @Setter
    private Integer bankId;
    
    @SerializedName("card_number")
    @Expose @Getter @Setter
    private String cardNumber;
    
    @SerializedName("full_name")
    @Expose @Getter @Setter
    private String fullName;
    
    @SerializedName("expiration_date_year")
    @Expose @Getter @Setter
    private String expirationDateYear;
    
    @SerializedName("expiration_date_month")
    @Expose @Getter @Setter
    private String expirationDateMonth;
    
    @SerializedName("create_date")
    @Expose @Getter @Setter
    private String createDate;
    
    @SerializedName("update_date")
    @Expose @Getter @Setter
    private String updateDate;
    
    @SerializedName("order_list")
    @Expose @Getter @Setter
    private Integer orderList;
    
    @SerializedName("is_deleted")
    @Expose @Getter @Setter
    private Boolean isDeleted;
    
    @SerializedName("is_favorite")
    @Expose @Getter @Setter
    private Boolean isFavorite;
    
    @SerializedName("is_main_card")
    @Expose @Getter @Setter
    private Boolean isMainCard;
    
    @SerializedName("bank_bin")
    @Expose @Getter @Setter
    private String bankBin;
    
    @SerializedName("bank_name")
    @Expose @Getter @Setter
    private String bankName;
    
    @SerializedName("card_image")
    @Expose @Getter @Setter
    private String cardImage;
    
    @SerializedName("card_image_back")
    @Expose @Getter @Setter
    private String cardImageBack;
    
    @SerializedName("color_text")
    @Expose @Getter @Setter
    private String colorText;
    
    @SerializedName("color_number")
    @Expose @Getter @Setter
    private String colorNumber;
}
