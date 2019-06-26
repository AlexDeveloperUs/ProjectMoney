package com.flexsoft.cardpassword.data.models;

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
    public byte mCardNumber;

    @ColumnInfo(name = "cardCVC")
    public byte mCVC;

    @ColumnInfo(name = "cardValidity")
    public byte mValidity;

    @ColumnInfo(name = "cardHoldersName")
    public String mCardHolderName;

    @ColumnInfo(name = "cardHolderSurname")
    public String mCardHolderSurname;

    @ColumnInfo(name = "cardType")
    public String mCardType;

    @ColumnInfo(name = "cardPIN")
    public short mPin;

    public Card() {
    }

    public Card(byte pCardNumber, byte pCVC, byte pValidity, String pCardHolderName, String pCardHolderSurname, String pCardType, short pPin) {

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

    public void setCardNumber(byte pCardNumber) {

        mCardNumber = pCardNumber;
    }

    public byte getCardNumber() {

        return mCardNumber;
    }

    public void setCVC(byte pCVC) {

        mCVC = pCVC;
    }

    public byte getCVC() {

        return mCVC;
    }

    public byte getValidity() {

        return mValidity;
    }

    public void setValidity(byte pValidity) {

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

    public short getPin() {

        return mPin;
    }

    public void setPin(short pPin) {

        mPin = pPin;
    }

}
