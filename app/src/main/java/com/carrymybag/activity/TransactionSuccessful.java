package com.carrymybag.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringDef;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.carrymybag.R;
import com.carrymybag.app.AppController;

public class TransactionSuccessful extends AppCompatActivity implements View.OnClickListener {

    private TextView textPrice,textID;
    double totalPrice;
    public AppController globalVariable;

    private Button btnBookAnother;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_successful);
        globalVariable = (AppController) getApplicationContext();
        totalPrice = globalVariable.getTotalPrice();
        String price = String.valueOf(totalPrice);
        textPrice = (TextView)findViewById(R.id.text_price);
        textPrice.setText(price);
        textID = (TextView)findViewById(R.id.transaction_id);
        String tranId = globalVariable.getRazorId();
        textID.setText(tranId);

        btnBookAnother = (Button)findViewById(R.id.book_another);
        btnBookAnother.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
