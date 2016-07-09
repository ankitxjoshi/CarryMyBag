package com.carrymybag.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.carrymybag.R;

public class ContactusActivity extends android.support.v4.app.Fragment {

    private EditText editName, editContactNumber, editEmail, editMsg;
    public String stringName, stringContact, stringEmail, stringMsg;

    private Button btnSend;

    private TextView t;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.activity_contactus,null);

        editName = (EditText) v.findViewById(R.id.name);
        editContactNumber = (EditText) v.findViewById(R.id.contact);
        editEmail = (EditText) v.findViewById(R.id.email);
        editMsg = (EditText) v.findViewById(R.id.msg);

        t = (TextView)v.findViewById(R.id.text_name);

        btnSend = (Button) v.findViewById(R.id.btn_send);
        btnSend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                stringName = editName.getText().toString();
                if (TextUtils.isEmpty(stringName)) {
                    editName.setError("Name can not be empty");
                    return;
                }

                stringEmail = editEmail.getText().toString();
                if (TextUtils.isEmpty(stringEmail)) {
                    editEmail.setError("Email can not be empty");
                    return;
                }

                stringContact = editContactNumber.getText().toString();
                if (TextUtils.isEmpty(stringContact)) {
                    editContactNumber.setError("Contact Number can not be empty");
                    return;
                }

                stringMsg = editMsg.getText().toString();
                if (TextUtils.isEmpty(stringMsg)) {
                    editMsg.setError("Message can not be empty");
                    return;
                }

            }

        });
        return v;

    }
}
