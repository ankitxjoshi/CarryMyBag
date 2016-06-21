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

    String Pickup;

    public static final String KEY_QTYSMALL = "qtySmall";
    public static final String KEY_QTYMED = "qtyMed";
    public static final String KEY_QTYLARGE = "qtyLarge";
    public static final String KEY_PRICESMALL = "priceSmall";
    public static final String KEY_PRICEMED = "priceMed";
    public static final String KEY_PRICELARGE = "priceLarge";
    public static final String KEY_ADDR_PICKUP = " addr_pickup";
    public static final String KEY_ADDR_DEST = "addr_dest";
    public static final String KEY_USER = "userId";
    public static final String KEY_PICKUP = "pickUp";
    public static final String KEY_DROPDOWN = "dropDown";

    private DatePickerDialog DatePickerDialog;

    private SimpleDateFormat dateFormatter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.content_oneway_tab, container, false);

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

                registerUser();
                getPricesAndQuantity();
                double totalPrice = priceSmall * qtySmall + priceMed * qtyMed + priceLarge * qtyLarge;
                String price = Double.toString(totalPrice);
                Toast.makeText(getActivity(), "The total Price is Rs. " + price, Toast.LENGTH_LONG).show();
                Intent i = new Intent(getContext(),
                        EnterDetails.class);
                i.putExtra("ValSmall",qtySmall);
                i.putExtra("ValMed",qtyMed);
                i.putExtra("ValBig",qtyLarge);
                i.putExtra("whichWay",false);
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

    private void registerUser(){
        final String qtySmall =  editTextQtySmall.getText().toString().trim();
        final String qtyMed =  editTextQtyMed.getText().toString().trim();
        final String qtyLarge =  editTextQtyLarge.getText().toString().trim();
        final String priceSmall =  textPriceSmall.getText().toString().trim();
        final String priceMed =  textPriceMed.getText().toString().trim();
        final String priceLarge =  textPriceLarge.getText().toString().trim();
        final String addr_pickup =  editaddr_pickup.getText().toString().trim();
        final String addr_dest =  editaddr_dest.getText().toString().trim();
        final String userId = LoginActivity.User_email;
        final String pickUp =  PicupDate.getText().toString().trim();
        final String dropDown =  DateOfDelivery.getText().toString().trim();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_Storedata,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getActivity(),response,Toast.LENGTH_LONG).show();
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
                params.put(KEY_QTYSMALL,qtySmall);
                params.put(KEY_QTYMED,qtyMed);
                params.put(KEY_QTYLARGE,qtyLarge);
                params.put(KEY_PRICESMALL,priceSmall);
                params.put(KEY_PRICEMED,priceMed);
                params.put(KEY_PRICELARGE,priceLarge);
                params.put(KEY_ADDR_PICKUP,addr_pickup);
                params.put(KEY_ADDR_DEST,addr_dest);
                params.put(KEY_USER,userId);
                params.put(KEY_PICKUP,pickUp);
                params.put(KEY_DROPDOWN,dropDown);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void setDateTimeField() {
        PicupDate.setOnClickListener(this);


        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog = new DatePickerDialog(getContext(), new android.app.DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                PicupDate.setText(dateFormatter.format(newDate.getTime()));
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
        try {
            qtyMed = Double.parseDouble(editTextQtyMed.getText().toString());
        } catch (final NumberFormatException e) {
            qtyMed = 0.0;
        }
        try {
            qtyLarge = Double.parseDouble(editTextQtyLarge.getText().toString());
        } catch (final NumberFormatException e) {
            qtyLarge = 0.0;
        }
        try {
            priceSmall = Double.parseDouble(textPriceSmall.getText().toString());
        } catch (final NumberFormatException e) {
            priceSmall = 0.0;
        }
        try {
            priceMed = Double.parseDouble(textPriceMed.getText().toString());
        } catch (final NumberFormatException e) {
            priceMed = 0.0;
        }
        try {
            priceLarge = Double.parseDouble(textPriceLarge.getText().toString());
        } catch (final NumberFormatException e) {
            priceLarge = 0.0;
        }
    }
}
