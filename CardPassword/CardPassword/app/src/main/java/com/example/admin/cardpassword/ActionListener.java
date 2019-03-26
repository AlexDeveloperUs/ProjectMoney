//package com.example.admin.cardpassword;
//
//import android.view.KeyEvent;
//import android.view.inputmethod.EditorInfo;
//import android.widget.TextView;
//
//import com.example.admin.cardpassword.ui.activity.create.CreateActivity;
//
//import java.lang.ref.WeakReference;
//
//public class ActionListener implements TextView.OnEditorActionListener {
//
//    private final WeakReference<CreateActivity> mCreateActivityWeakReference;
//
//    public static ActionListener newInstance(CreateActivity pCreateActivity) {
//
//        WeakReference<CreateActivity> createActivityWeakReference = new WeakReference<>(pCreateActivity);
//        return new ActionListener(createActivityWeakReference);
//    }
//
//    private ActionListener(WeakReference<CreateActivity> pCreateActivityWeakReference) {
//
//        mCreateActivityWeakReference = pCreateActivityWeakReference;
//    }
//
//    @Override
//    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//
//        CreateActivity createActivity = mCreateActivityWeakReference.get();
//        if (createActivity != null) {
//
//            if (actionId == EditorInfo.IME_ACTION_GO && createActivity.shouldShowError()) {
//
//                createActivity.showError();
//            } else {
//
//                createActivity.hideError();
//            }
//        }
//
//        return true;
//    }
//}
