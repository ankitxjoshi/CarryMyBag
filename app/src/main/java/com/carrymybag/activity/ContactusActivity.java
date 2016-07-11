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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.carrymybag.R;
import com.carrymybag.app.AppConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ContactusActivity extends android.support.v4.app.Fragment {

    private EditText editName, editContactNumber, editEmail, editMsg;
    public String stringName, stringContact, stringEmail, stringMsg;

    private Button btnSend;

    private TextView t;

    public static final String KEY_USER = "contact_name";
    public static final String KEY_EMAIL = "contact_email";
    public static final String KEY_MESSAGE = "contact_text";
    RequestQueue requestQueue;

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
                sendEmail();

//                Intent email = new Intent(Intent.ACTION_SEND);
//                email.putExtra(Intent.EXTRA_EMAIL, new String[]{ "ajankit2304@gmail.com"});
//                //email.putExtra(Intent.EXTRA_CC, new String[]{ to});
//                //email.putExtra(Intent.EXTRA_BCC, new String[]{to});
//                email.putExtra(Intent.EXTRA_SUBJECT, "Query");
//                email.putExtra(Intent.EXTRA_TEXT, stringMsg);
//
//                //need this to prompts email client only
//                email.setType("message/rfc822");
//
//                startActivity(Intent.createChooser(email, "Choose an Email client :"));

            }

        });
        return v;

    }
    private void sendEmail() {

        final String user = stringName;
        final String email = stringEmail;
        final String message = stringMsg + "<br>Contact info is : " + stringContact;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_CONTACTUS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getActivity(), response, Toast.LENGTH_LONG).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(KEY_USER,user);
                params.put(KEY_EMAIL,email);
                params.put(KEY_MESSAGE, message);


                return params;
            }

        };
        requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }
}
