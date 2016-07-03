package com.carrymybag.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.carrymybag.R;

public class ConfirmPage extends AppCompatActivity {

    public String travellerName = "Traveller Name", travellerContact = "Traveller Contact";
    public String addressOrigin1 = "Address Origin", addressDest1 = "Address Destination";
    public String addressOrigin2 = "Address Origin", addressDest2 = "Address Destination";
    public String stateOrigin1 = "State Origin", stateDest1 = "State Destination";
    public String stateOrigin2 = "State Origin", stateDest2 = "State Destination";
    public String pinOrigin1 = "Pin Origin", pinDest1 = "Pin Destinaion";
    public String pinOrigin2 = "Pin Origin", getPinDest2 = "Pin Destinaion";
    public String datePickup1 = "Pickup Date", dateDelivery1 = "Delivery Date";
    public String datePickup2 = "Pickup Date", dateDelivery2 = "Delivery Date";

    public TextView nameTraveller, contactTraveller;
    public TextView originAddress1, originAddress2, destAddress1, destAddress2;
    public TextView originState1, originState2, destState1, destState2;
    public TextView originPin1, originPin2, destPin1, destPin2;
    public TextView pickupDate1, pickupDate2,  deliveryDate1, deliveryDate2;

    public LinearLayout layoutLeg2;
    public Button buttonSubmit, buttonEdit;

    Boolean isTwoWay = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_page);

        buttonEdit = (Button)findViewById(R.id.btn_edit);
        buttonSubmit = (Button)findViewById(R.id.btn_submit);

        nameTraveller = (TextView)findViewById(R.id.text_traveller_name);
        contactTraveller = (TextView)findViewById(R.id.text_traveller_contact);

        originAddress1 = (TextView)findViewById(R.id.text_address_origin1);
        destAddress1 = (TextView)findViewById(R.id.text_address_dest1);

        originState1 = (TextView)findViewById(R.id.text_state_origin1);
        destState1 = (TextView)findViewById(R.id.text_state_dest1);

        originPin1 = (TextView)findViewById(R.id.text_pin_origin1);
        destPin1 = (TextView)findViewById(R.id.text_pin_dest1);

        pickupDate1 = (TextView)findViewById(R.id.text_date_pickup1);
        deliveryDate1 = (TextView)findViewById(R.id.text_date_delivery1);

        nameTraveller.setText(travellerName);
        contactTraveller.setText(travellerContact);

        originAddress1.setText(addressOrigin1);
        destAddress1.setText(addressDest1);

        originState1.setText(stateOrigin1);
        destState1.setText(stateDest1);

        originPin1.setText(pinOrigin1);
        destPin1.setText(pinDest1);

        pickupDate1.setText(datePickup1);
        deliveryDate1.setText(dateDelivery1);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfirmPage.this,
                        PaymentActivity.class);
                startActivity(intent);
                finish();
            }
        });

        layoutLeg2 = (LinearLayout)findViewById(R.id.layout_leg2);
        if(isTwoWay==false) {
            layoutLeg2.setVisibility(View.GONE);
        }
        else
        {
            originAddress2 = (TextView)findViewById(R.id.text_address_origin2);
            destAddress2 = (TextView)findViewById(R.id.text_address_dest2);

            originState2 = (TextView)findViewById(R.id.text_state_origin2);
            destState2 = (TextView)findViewById(R.id.text_state_dest2);

            originPin2 = (TextView)findViewById(R.id.text_pin_origin2);
            destPin2 = (TextView)findViewById(R.id.text_pin_dest2);

            pickupDate2 = (TextView)findViewById(R.id.text_date_pickup2);
            deliveryDate2 = (TextView)findViewById(R.id.text_date_delivery2);

            originAddress2.setText(addressOrigin2);
            destAddress2.setText(addressDest2);

            originState2.setText(stateOrigin2);
            destState2.setText(stateDest2);

            originPin2.setText(pinOrigin2);
            destPin2.setText(getPinDest2);

            pickupDate2.setText(datePickup2);
            deliveryDate2.setText(dateDelivery2);

            buttonEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*
                    Send back to the previous activity for changes.
                     */
                }
            });

            buttonSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*
                    Move on to the next activity for payment.
                     */
                }
            });
        }
    }
}
