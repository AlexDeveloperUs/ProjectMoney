package com.example.admin.cardpassword.ui.activity.submit;

import com.example.admin.cardpassword.data.models.Card;

public class SubmitCardPresenter implements SubmitCardContract.Presenter {

    private SubmitCardContract.Model mModel;
    private SubmitCardContract.View mView;

    SubmitCardPresenter(SubmitCardActivity pActivity) {

        mView = pActivity;
        mModel = new SubmitCardModel();
    }

    @Override
    public void createCard(Card pCard) {

        mModel.createCard(pCard);
    }

    @Override
    public void updateCard(Card pCard) {

        mModel.editCard(pCard);
    }

    @Override
    public boolean checkCardExistence(long pNumber) {

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

        if (!(count % 10 == 0)) {

            mView.showToastCardExistence();
        }

        return count % 10 == 0;
    }

    @Override
    public String checkCardType(String pS) {

        pS = String.valueOf(Long.parseLong(pS.replaceAll("[^0-9]", "")));

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

    @Override
    public boolean checkByLuhnAlgorithm(String pS) {

        if (pS.equals("")) {

            mView.showToastNumber();
            return false;
        } else {

            return checkCardExistence(Long.parseLong(pS.replaceAll("[^0-9]", "")));
        }
    }

    @Override
    public boolean checkCVC(int pI) {

        if (pI == 0) {

            mView.showToastCVC();
            return false;
        } else if ((int) (Math.log10(pI) + 1) < 3) {

            mView.showToastCVC();
            return false;
        } else return true;
    }

    @Override
    public boolean checkPin(int pI) {

        if (pI == 0) {

            mView.showToastPin();
            return false;
        } else if ((int) (Math.log10(pI) + 1) < 4) {

            mView.showToastPin();
            return false;
        } else return true;
    }

    @Override
    public String appendVoid(String pS) {

        String firstSub = pS.substring(0, 4);
        String secondSub = pS.substring(4, 8);
        String thirdSub = pS.substring(8, 12);
        String fourthSub = pS.substring(12, 16);

        return firstSub + " " + secondSub + " " + thirdSub + " " + fourthSub;
    }
}
