package com.carrymybag.activity;

/**
 * Created by Ankit on 6/3/2016.
 */

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.carrymybag.R;
import com.carrymybag.helper.SQLiteHandler;
import com.carrymybag.helper.SessionManager;
import com.facebook.login.LoginManager;

import java.util.HashMap;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.carrymybag.R;
import com.carrymybag.helper.SQLiteHandler;
import com.carrymybag.helper.SessionManager;
import com.facebook.login.LoginManager;

import java.util.HashMap;

public class SixthFragment extends android.support.v4.app.Fragment {

    private TextView txtName;
    private TextView txtEmail;
    private Button btnLogout;

    private SQLiteHandler db;
    private SessionManager session;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_sixth_fragment, container, false);

        txtName = (TextView)view.findViewById(R.id.name);
        txtEmail = (TextView)view.findViewById(R.id.email);
        btnLogout = (Button)view.findViewById(R.id.btnLogout);

        db = new SQLiteHandler(getActivity().getApplicationContext());

        // session manager
        session = new SessionManager(getActivity().getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        // Fetching user details from SQLite
        HashMap<String, String> user = db.getUserDetails();

        String name = user.get("name");
        String email = user.get("email");

        // Displaying the user details on the screen
        txtName.setText(name);
        txtEmail.setText(email);

        // Logout button click event
        btnLogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });
        return view;
    }


    /**
     * Logging out the user. Will set isLoggedIn flag to false in shared
     * preferences Clears the user data from sqlite users table
     */
    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        if(LoginActivity.facebookFlag) {
            LoginManager.getInstance().logOut();
            LoginActivity.facebookFlag = false;
        }
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}





