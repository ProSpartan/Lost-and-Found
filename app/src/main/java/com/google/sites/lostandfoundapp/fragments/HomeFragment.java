package com.google.sites.lostandfoundapp.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.sites.lostandfoundapp.R;
import com.google.sites.lostandfoundapp.database.AppDatabase;
import com.google.sites.lostandfoundapp.database.entities.UserEntity;

/**
 * Created by Isaac on 3/6/2018.
 */

public class HomeFragment extends Fragment{
    private static HomeFragment INSTANCE;

    private TextView homeTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_layout, container, false);

        final Context context = view.getContext().getApplicationContext();
        final AppDatabase appDatabase = AppDatabase.getINSTANCE(context);

        homeTextView = view.findViewById(R.id.home_textview_welcome);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                final UserEntity user = appDatabase.userDao().getUser();
                final StringBuilder sb = new StringBuilder();
                if (null != user) {
                    sb.append("Hello, ")
                            .append(user.getFirstName())
                            .append(' ')
                            .append(user.getLastName())
                            .append("\n")
                            .append("This app was initially created for a class project")
                            .append("\n")
                            .append("I hope you will enjoy its use");

                } else {
                    sb.append("Hello,")
                            .append("\n")
                            .append("This app was initially created for a class project")
                            .append("\n")
                            .append("I hope you will enjoy its use");
                }
                homeTextView.setText(sb.toString());
            }
        });

        return view;
    }

    public static HomeFragment getInstance() {
        if(null == INSTANCE) {
            INSTANCE = new HomeFragment();
        }
        return INSTANCE;
    }
}
