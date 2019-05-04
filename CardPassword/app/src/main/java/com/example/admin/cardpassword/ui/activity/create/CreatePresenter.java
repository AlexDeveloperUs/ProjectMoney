package com.example.admin.cardpassword.ui.activity.create;

import com.example.admin.cardpassword.data.models.Card;

public class CreatePresenter implements CreateContract.Presenter {

    private CreateContract.View mCreateActivity;
    private CreateContract.Model mModel;

    CreatePresenter(CreateActivity pCreateActivity) {

        mModel = new CreateModel();
        mCreateActivity = pCreateActivity;
    }

    @Override
    public void createCard(Card pCard) {

        mModel.createCard(pCard);
    }

    @Override
    public void updateCard(Card pCard) {

        mModel.editCard(pCard);
    }

    private void checkCardExistence(long pNumber) {

        long count = 0;
        long character;

        for (int i = 16; i > 0; i--) {

            character = pNumber % 10;
            pNumber /= 10;

            if (i % 2 == 1) {

                int check = (int) (character * 2);

                if (check > 9) {

                    count += check - 9;
                } else {

                    count += check;
                }
            } else {

                count += character;
            }
        }

        if (count % 10 == 0) {

            mCreateActivity.removeNumberError();
        } else {

            mCreateActivity.showExistenceError();
        }
    }

    void checkDataValidation(String pNumber, String pCVC, String pValidity, String pName, String pSurname,
                             String pPin, Boolean pRequestCode, int pI) {

        if (pNumber.contains("-")) {

            pNumber = pNumber.replaceAll("[^A-Za-zА-Яа-я0-9]", "");
        }

        if (pNumber.toLowerCase().contains("x")) {

            mCreateActivity.showNumberError();
            return;
        } else if (pNumber.matches("[\\d]+")) {

            checkCardExistence(Long.parseLong(pNumber.replaceAll("[^0-9]", "")));
        } else mCreateActivity.removeNumberError();

        if (pCVC.isEmpty()) {

            mCreateActivity.showCvcError();
            return;
        } else mCreateActivity.removeCvcError();

        if (pPin.isEmpty()) {

            mCreateActivity.showPinError();
            return;
        } else mCreateActivity.removePinError();

        if (pName.isEmpty()) pName = "";

        if (pSurname.isEmpty()) pSurname = "";


        long cardNumber = Long.parseLong(pNumber);
        int cvc = Integer.valueOf(pCVC);
        int pin = Integer.valueOf(pPin);

        if (pRequestCode) {

            createCard(new Card(cardNumber, cvc, pValidity, pName, pSurname, checkCardType(pNumber), pin));
        } else {

            updateCard(new Card(cardNumber, cvc, pValidity, pName, pSurname, checkCardType(pNumber), pin, pI));
        }

        mCreateActivity.createAndUpdateCard();
    }

    @Override
    public String checkCardType(String pS) {

        String mCardType = "";

        if (pS.charAt(0) == '4') {

            mCardType = "visa";
        } else if (pS.charAt(0) == '5' && (pS.charAt(1) == '1' || pS.charAt(1) == '2' || pS.charAt(1) == '3' || pS.charAt(1) == '4'
                || pS.charAt(1) == '5')) {

            mCardType = "mastercard";
        } else if (pS.substring(0, 4).equals("9112")) {

            mCardType = "belcard";
        } else if ((pS.charAt(0) == '5' && (pS.charAt(1) == '0' || pS.charAt(1) == '6' || pS.charAt(1) == '7' || pS.charAt(1) == '8'))
                || (pS.charAt(0) == '6' && (pS.charAt(1) == '3' || pS.charAt(1) == '7'))) {

            mCardType = "maestro";
        }

        return mCardType;
    }
}
