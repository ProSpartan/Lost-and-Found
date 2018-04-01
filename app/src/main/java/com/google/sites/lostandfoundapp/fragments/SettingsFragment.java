package com.google.sites.lostandfoundapp.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.sites.lostandfoundapp.R;
import com.google.sites.lostandfoundapp.database.AppDatabase;
import com.google.sites.lostandfoundapp.database.entities.UserEntity;
import com.google.sites.lostandfoundapp.log.ToastLog;

/**
 * Created by Isaac on 3/7/2018.
 */

public class SettingsFragment extends Fragment{
    private static SettingsFragment INSTANCE;

    private static final int USERID = 1;
    private static final ToastLog TOAST_LOG = ToastLog.getInstance();

    private EditText lastName;
    private EditText firstName;
    private EditText email;
    private Button updateButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings_layout, container, false);

        final Context context = view.getContext().getApplicationContext();
        final AppDatabase appDatabase = AppDatabase.getINSTANCE(context);

        lastName = view.findViewById(R.id.settings_text_name_last);
        firstName = view.findViewById(R.id.settings_text_name_first);
        email = view.findViewById(R.id.settings_text_email);
        updateButton = view.findViewById(R.id.settings_update_button);
        updateButton.setOnClickListener(mOnClickListener);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                final UserEntity user = appDatabase.userDao().getUser();
                if (null != user) {
                    lastName.setText(user.getLastName());
                    firstName.setText(user.getFirstName());
                    email.setText(user.getEmail());
                }
            }
        });

        return view;
    }

    private Button.OnClickListener mOnClickListener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            final Context context = v.getContext().getApplicationContext();
            final AppDatabase appDatabase = AppDatabase.getINSTANCE(context);

            final UserEntity user = new UserEntity(USERID,
                    firstName.getText().toString(),
                    lastName.getText().toString(),
                    email.getText().toString());
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    if (null == appDatabase.userDao().findById(USERID)) {
                        appDatabase.userDao().insertUser(user);
                    } else {
                        appDatabase.userDao().updateUser(user);
                    }
                }
            });
        }
    };

    public static SettingsFragment getInstance() {
        if (null == INSTANCE) {
            INSTANCE = new SettingsFragment();
        }
        return INSTANCE;
    }
}
