package com.flexsoft.cardpassword.data.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "cards")
public class Card implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "cardId")
    public int mId;

    @ColumnInfo(name = "cardName")
    public String mCardName;

    @ColumnInfo(name = "cardNumber")
    public String mCardNumber;

    @ColumnInfo(name = "cardCVC")
    public String mCVC;

    @ColumnInfo(name = "cardValidity")
    public String mValidity;

    @ColumnInfo(name = "cardHoldersName")
    public String mCardHolderName;

    @ColumnInfo(name = "cardHolderSurname")
    public String mCardHolderSurname;

    @ColumnInfo(name = "cardType")
    public String mCardType;

    @ColumnInfo(name = "cardPIN")
    public String mPin;

    public Card() {
    }

    public Card(Parcel in) {

        mCardName = in.readString();
        mCardNumber = in.readString();
        mCVC = in.readString();
        mValidity = in.readString();
        mCardHolderName = in.readString();
        mCardHolderSurname = in.readString();
        mCardType = in.readString();
        mPin = in.readString();
        mId = in.readInt();
    }

    public Card(String pCardName, String pCardNumber, String pCVC, String pValidity, String pCardHolder, String pCardType, String pPin) {

        mCardName = pCardName;
        mCardNumber = pCardNumber;
        mCVC = pCVC;
        mValidity = pValidity;
        mCardHolderName = pCardHolder;
        mCardType = pCardType;
        mPin = pPin;
    }

    public Card(String pCardName, String pMByteCardNumber, String pMByteCvc, String pMValidityContains, String pCardHolder, String pCardType, String pMBytePin, int pId) {

        mCardName = pCardName;
        mCardNumber = pMByteCardNumber;
        mCVC = pMByteCvc;
        mValidity = pMValidityContains;
        mCardHolderName = pCardHolder;
        mCardType = pCardType;
        mPin = pMBytePin;
        mId = pId;
    }

    public String getCardName() {

        return mCardName;
    }

    public void setCardName(String pCardName) {

        mCardName = pCardName;
    }

    public String getCardType() {

        return mCardType;
    }

    public void setCardType(String pCardType) {

        mCardType = pCardType;
    }

    public void setId(int pId) {

        mId = pId;
    }

    public int getId() {

        return mId;
    }

    public void setCardNumber(String pCardNumber) {

        mCardNumber = pCardNumber;
    }

    public String getCardNumber() {

        return mCardNumber;
    }

    public void setCVC(String pCVC) {

        mCVC = pCVC;
    }

    public String getCVC() {

        return mCVC;
    }

    public String getValidity() {

        return mValidity;
    }

    public void setValidity(String pValidity) {

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

    public String getPin() {

        return mPin;
    }

    public void setPin(String pPin) {

        mPin = pPin;
    }

    @Override
    public int describeContents() {

        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(mCardName);
        dest.writeString(mCardNumber);
        dest.writeString(mCVC);
        dest.writeString(mValidity);
        dest.writeString(mCardHolderName);
        dest.writeString(mCardHolderSurname);
        dest.writeString(mCardType);
        dest.writeString(mPin);
        dest.writeInt(mId);
    }

    public static final Creator<Card> CREATOR = new Creator<Card>() {

        @Override
        public Card createFromParcel(Parcel in) {

            return new Card(in);
        }

        @Override
        public Card[] newArray(int size) {

            return new Card[size];
        }
    };
}
