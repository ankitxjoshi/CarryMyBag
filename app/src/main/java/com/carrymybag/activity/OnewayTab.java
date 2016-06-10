package com.carrymybag.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import java.util.Map;

//import info.androidhive.materialtabs.R;

public class OnewayTab extends Fragment {

    public OnewayTab() {
        // Required empty public constructor
    }

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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.content_oneway_tab, container, false);

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
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        /*
                Values to be stored in this case
                qtySmall, qtyMed, qtyLarge, priceSmall, priceMed, priceLarge, totalPrice, PickupAddr, DestAddr
                 */
                registerUser();
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
                double totalPrice = priceSmall * qtySmall + priceMed * qtyMed + priceLarge * qtyLarge;



                String price = Double.toString(totalPrice);

                Toast.makeText(getActivity(), "The total Price is Rs. " + price, Toast.LENGTH_LONG).show();
            }
        });

        buttonViewPrices = (Button) v.findViewById(R.id.btnViewPrices);
        buttonViewPrices.setOnClickListener(new View.OnClickListener() {

            /*
                Values to be stored in db in this case
                Variable - stores what value
                Pickup - pickup date
                Delivery - delivery date
                Delivery option selected - fast, 1day or standard, corresponding to option selected set true/false in db.
                 */

            /* Values to be fetched from another db storing prices for specific type of bag from one city to another for each delivery option
             Jo values set ki h textPriceSmall, textPriceMed aur textPriceLarge ki wo db se utha k set honi h
              */
            @Override
            public void onClick(View v) {
                try {
                    Pickup = PicupDate.getText().toString();
                } catch (NullPointerException e) {
                    Toast.makeText(getActivity(), "No Pickup date entered", Toast.LENGTH_LONG).show();
                }

                int choice = 3;
                String Delivery = null;
                if (Pickup.matches("\\d{2}/\\d{2}/\\d{4}")) {
                    if (option.getCheckedRadioButtonId() == -1) {
                        Toast.makeText(getActivity(), "No delivery option selected", Toast.LENGTH_LONG).show();
                    } else {
                        if (fast.isChecked()) {
                            textPriceSmall.setText("1000");
                            textPriceMed.setText("1100");
                            textPriceLarge.setText("1200");
                            choice = 2;
                            Delivery = showExpectedDate(Pickup, choice);
                        }
                        if (standard.isChecked()) {
                            textPriceSmall.setText("500");
                            textPriceMed.setText("600");
                            textPriceLarge.setText("700");
                            choice = 3;
                            Delivery = showExpectedDate(Pickup, choice);
                        }
                        if (one_day.isChecked()) {
                            textPriceSmall.setText("1500");
                            textPriceLarge.setText("1700");
                            textPriceMed.setText("1600");
                            choice = 1;
                            Delivery = showExpectedDate(Pickup, choice);
                        }
                    }
                } else {
                    Toast.makeText(getActivity(), "Invalid pickup date", Toast.LENGTH_LONG).show();
                }
            }
        });




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
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }


    public String getNextDate(String Pickup) {
        final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        format.setLenient(false);
        Date date = null;
        try {
            date = format.parse(Pickup);
            final Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            return format.format(calendar.getTime());

        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Invalid Pickup Date", Toast.LENGTH_LONG).show();
        }
        return ("Please correct the pickup date");
    }

    public String showExpectedDate(String date, int c) {
        String next = date;
        if (c == 1) {
            next = getNextDate(next);
        } else if (c == 2) {
            for (int i = 0; i < 2; i++)
                next = getNextDate(next);
        } else {
            for (int i = 0; i < 3; i++)
                next = getNextDate(next);
        }
        DateOfDelivery.setText(next);
        return next;
    }




}
