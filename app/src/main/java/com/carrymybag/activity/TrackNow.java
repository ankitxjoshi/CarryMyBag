package com.carrymybag.activity;
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

public class TrackNow extends android.support.v4.app.Fragment {

    private EditText editTransactionId;
    String stringTransactionId;


    private Button btnTrack;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.activity_track_now,null);

        editTransactionId = (EditText)v.findViewById(R.id.transaction_id);

        btnTrack = (Button)v.findViewById(R.id.btn_track);
        btnTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stringTransactionId = editTransactionId.getText().toString();
                if(TextUtils.isEmpty(stringTransactionId))
                {
                    editTransactionId.setError("Transaction Id can not be empty");
                }
            }
        });




        return v;

    }


}
