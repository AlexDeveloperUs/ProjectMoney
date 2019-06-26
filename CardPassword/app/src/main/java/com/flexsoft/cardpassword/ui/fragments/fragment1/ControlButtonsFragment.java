package com.flexsoft.cardpassword.ui.fragments.fragment1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flexsoft.cardpassword.R;
import com.flexsoft.cardpassword.ui.activity.list.ListActivity;
import com.flexsoft.cardpassword.ui.activity.settings.SettingsActivity;
import com.flexsoft.cardpassword.ui.activity.submit.SubmitCardActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ControlButtonsFragment extends Fragment implements View.OnClickListener {

    @OnClick({R.id.image_add_view_stub, R.id.image_back_view_stub, R.id.image_settings_view_stub})
    @Override
    public void onClick(View v) {

        Intent intent;
        switch (v.getId()) {

            case R.id.image_add_view_stub:
                intent = new Intent(getContext(), SubmitCardActivity.class);
                startActivity(intent);
                break;
            case R.id.image_back_view_stub:
                toActivity();
                break;
            case R.id.image_settings_view_stub:
                intent = new Intent(getContext(), SettingsActivity.class);
                intent.putExtra("key", 1);
                startActivity(intent);
                break;
        }
    }

    private void toActivity() {

        Activity activity = getActivity();
        if (activity != null && !activity.isFinishing() && activity instanceof ListActivity) {

            ((ListActivity) activity).removeFromFragment();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_action_buttons, container, false);
        ButterKnife.bind(this, view);

        return view;
    }
}
