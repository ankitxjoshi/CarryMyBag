package com.carrymybag.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.carrymybag.R;

public class TransactionSuccessful extends AppCompatActivity implements View.OnClickListener {

    private TextView textPrice;
    String totalPrice = "Rs. 1000";

    private Button btnBookAnother;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_successful);

        textPrice = (TextView)findViewById(R.id.text_price);
        textPrice.setText(totalPrice);

        btnBookAnother = (Button)findViewById(R.id.book_another);
        btnBookAnother.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
}
