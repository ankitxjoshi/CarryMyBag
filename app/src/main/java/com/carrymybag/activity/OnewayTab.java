package com.carrymybag.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.carrymybag.helper.GlobalClass;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.carrymybag.R;
import com.carrymybag.app.AppConfig;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;



public class OnewayTab extends Fragment implements View.OnClickListener {


    private EditText editTextQtySmall, editTextQtyMed, editTextQtyLarge, editaddr_pickup, editaddr_dest, PicupDate;

    private TextView textPriceSmall, textPriceMed, textPriceLarge, DateOfDelivery;

    double qtySmall, qtyMed, qtyLarge, priceSmall, priceMed, priceLarge;

    private Button buttonSubmit, buttonViewPrices;

    private RadioGroup option;

    private RadioButton one_day, fast, standard;





    private DatePickerDialog DatePickerDialog;

    private SimpleDateFormat dateFormatter;

    public GlobalClass globalVariable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.content_oneway_tab, container, false);
        globalVariable = (GlobalClass) getActivity().getApplicationContext();

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);


        editTextQtySmall = (EditText) v.findViewById(R.id.qty_small);
        editTextQtyMed = (EditText) v.findViewById(R.id.qty_medium);
        editTextQtyLarge = (EditText) v.findViewById(R.id.qty_large);

        textPriceSmall = (TextView) v.findViewById(R.id.price_small);
        textPriceMed = (TextView) v.findViewById(R.id.price_medium);
        textPriceLarge = (TextView) v.findViewById(R.id.price_large);

        buttonSubmit = (Button) v.findViewById(R.id.btnSubmit);

        editaddr_pickup = (EditText) v.findViewById(R.id.pickup1);
        editaddr_dest = (EditText) v.findViewById(R.id.destination1);

        option = (RadioGroup) v.findViewById(R.id.delivery_options);
        fast = (RadioButton) v.findViewById(R.id.radio_fast);
        one_day = (RadioButton) v.findViewById(R.id.radio_1day);
        standard = (RadioButton) v.findViewById(R.id.radio_standard);

        DateOfDelivery = (TextView) v.findViewById(R.id.date_delivery);

        PicupDate = (EditText) v.findViewById(R.id.date_pickup);
        PicupDate.setInputType(InputType.TYPE_NULL);
        PicupDate.requestFocus();
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getPricesAndQuantity();
                double totalPrice = priceSmall * qtySmall + priceMed * qtyMed + priceLarge * qtyLarge;
                String price = Double.toString(totalPrice);
                globalVariable.setTwoWay(false);
                Toast.makeText(getActivity(), "The total Price is Rs. " + price, Toast.LENGTH_LONG).show();
                Intent i = new Intent(getContext(),
                        EnterDetails.class);
//                i.putExtra("ValSmall",qtySmall);
//                i.putExtra("ValMed",qtyMed);
//                i.putExtra("ValBig",qtyLarge);
//                i.putExtra("whichWay",false);
                startActivity(i);

            }
        });

        buttonViewPrices = (Button) v.findViewById(R.id.btnViewPrices);
        buttonViewPrices.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });

        setDateTimeField();




        return v;
    }



    private void setDateTimeField() {
        PicupDate.setOnClickListener(this);


        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog = new DatePickerDialog(getContext(), new android.app.DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                PicupDate.setText(dateFormatter.format(newDate.getTime()));
                globalVariable.setPickupDate1(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }

    @Override
    public void onClick(View v) {

        if(v == PicupDate) {
            DatePickerDialog.show();
        }
    }

    void getPricesAndQuantity()
    {
        try {
            qtySmall = Double.parseDouble(editTextQtySmall.getText().toString());
        } catch (final NumberFormatException e) {
            qtySmall = 0.0;
        }
        globalVariable.setQtySmall1(qtySmall);
        try {
            qtyMed = Double.parseDouble(editTextQtyMed.getText().toString());
        } catch (final NumberFormatException e) {
            qtyMed = 0.0;
        }
        globalVariable.setQtyMed1(qtyMed);
        try {
            qtyLarge = Double.parseDouble(editTextQtyLarge.getText().toString());
        } catch (final NumberFormatException e) {
            qtyLarge = 0.0;
        }
        globalVariable.setQtyLarge1(qtyLarge);
        try {
            priceSmall = Double.parseDouble(textPriceSmall.getText().toString());
        } catch (final NumberFormatException e) {
            priceSmall = 0.0;
        }
        globalVariable.setPriceSmall1(priceSmall);
        try {
            priceMed = Double.parseDouble(textPriceMed.getText().toString());
        } catch (final NumberFormatException e) {
            priceMed = 0.0;
        }
        globalVariable.setPriceMed1(priceMed);
        try {
            priceLarge = Double.parseDouble(textPriceLarge.getText().toString());
        } catch (final NumberFormatException e) {
            priceLarge = 0.0;
        }
        globalVariable.setPriceLarge1(priceLarge);
    }
}
