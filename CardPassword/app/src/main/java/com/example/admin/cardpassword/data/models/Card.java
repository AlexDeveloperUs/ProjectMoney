package com.example.admin.cardpassword.data.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "cards")
public class Card implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "cardId")
    public long mId;

    @ColumnInfo(name = "cardNumber")
    public long mCardNumber;

    @ColumnInfo(name = "cardCVC")
    public int mCVC;

    @ColumnInfo(name = "cardValidity")
    public int mValidity;

    @ColumnInfo(name = "cardHoldersName")
    public String mCardHolderName;

    @ColumnInfo(name = "cardHolderSurname")
    public String mCardHolderSurname;

    @ColumnInfo(name = "cardType")
    public String mCardType;

    @ColumnInfo(name = "cardPIN")
    public int mPin;

    public Card() {
    }

    public Card(long pCardNumber, int pCVC, int pValidity, String pCardHolderName, String pCardHolderSurname, String pCardType, int pPin) {

        mCardNumber = pCardNumber;
        mCVC = pCVC;
        mValidity = pValidity;
        mCardHolderName = pCardHolderName;
        mCardHolderSurname = pCardHolderSurname;
        mCardType = pCardType;
        mPin = pPin;
    }

    public String getCardType() {

        return mCardType;
    }

    public void setCardType(String pCardType) {

        mCardType = pCardType;
    }

    public void setId(long pId) {

        mId = pId;
    }

    public long getId() {

        return mId;
    }

    public void setCardNumber(long pCardNumber) {

        mCardNumber = pCardNumber;
    }

    public long getCardNumber() {

        return mCardNumber;
    }

    public void setCVC(int pCVC) {

        mCVC = pCVC;
    }

    public int getCVC() {

        return mCVC;
    }

    public int getValidity() {

        return mValidity;
    }

    public void setValidity(int pValidity) {

        mValidity = pValidity;
    }

    public String getCardHolderName() {

        return mCardHolderName;
    }

    public void setCardHolderName(String pCardHolderName) {

        mCardHolderName = pCardHolderName;
    }

    public String getCardHolderSurname() {

        return mCardHolderSurname;
    }

    public void setCardHolderSurname(String pCardHolderSurname) {

        mCardHolderSurname = pCardHolderSurname;
    }

    public int getPin() {

        return mPin;
    }

    public void setPin(int pPin) {

        mPin = pPin;
    }

}
