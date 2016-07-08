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
import com.carrymybag.app.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
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

    double qtySmall, qtyMed, qtyLarge, priceSmall, priceMed, priceLarge,totalPrice;

    private Button buttonSubmit, buttonViewPrices;

    private RadioGroup option;

    private RadioButton one_day, fast, standard;

    RequestQueue requestQueue;

    public static final String KEY_FROMCITY = "from_city";
    public static final String KEY_TOCITY = "to_city";
    public static final String KEY_OPTION = "do_option";
    public static final String KEY_TYPE = "bag_id";





    private DatePickerDialog DatePickerDialog;

    private SimpleDateFormat dateFormatter;

    public AppController globalVariable;
    RadioButton radioButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.content_oneway_tab, container, false);
        globalVariable = (AppController) getActivity().getApplicationContext();

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
                startActivity(i);

            }
        });

        buttonViewPrices = (Button) v.findViewById(R.id.btnViewPrices);
        buttonViewPrices.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                totalPrice = priceSmall * qtySmall + priceMed * qtyMed + priceLarge * qtyLarge;
                globalVariable.setTotalPrice(totalPrice);
            }
        });

        setDateTimeField();

        final RadioGroup radioGroup = (RadioGroup) v.findViewById(R.id.delivery_options);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int selectedId= radioGroup.getCheckedRadioButtonId();
                radioButton=(RadioButton)v.findViewById(selectedId);
                Toast.makeText(getActivity(),radioButton.getText(),Toast.LENGTH_SHORT).show();
                if(selectedId==R.id.radio_1day)
                {
                    globalVariable.setdoOption("Single");
                }
                if(selectedId==R.id.radio_fast)
                {
                    globalVariable.setdoOption("Fast");
                }
                if(selectedId==R.id.radio_standard)
                {
                    globalVariable.setdoOption("Standard");
                }
            }
        });




        return v;
    }



    private void setDateTimeField() {
        PicupDate.setOnClickListener(this);


        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog = new DatePickerDialog(getContext(), new android.app.DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                String sdate = dateFormatter.format(newDate.getTime());
                Date date = null;
                SimpleDateFormat dateFormatter = new SimpleDateFormat(
                        "yyyy-MM-dd");
                try {
                    date = dateFormatter.parse(sdate);
                } catch (ParseException e) {
                    // handle exception here !
                }
                PicupDate.setText(DateFormat.getDateInstance(DateFormat.MEDIUM).format(date));
                globalVariable.setPickupDate1(DateFormat.getDateInstance(DateFormat.SHORT).format(date));
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

    private void getPrices(String type)
    {
        requestQueue = Volley.newRequestQueue(getActivity());
        final String fromCity = globalVariable.getFromCity();
        final String toCity = globalVariable.getToCity();
        final String bagtype = type;
        final String doOption = "Standard";
        String val = "0";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_GetPrice,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jObj = new JSONObject(response);
                            boolean error = jObj.getBoolean("error");

                            // Check for error node in json
                            if (!error) {

                                JSONArray result = jObj.getJSONArray("result");
                                //JSONObject price = result.getJSONObject("price");
                                JSONObject price = result.getJSONObject(0);
                                String val = price.getString("price");


                            } else {
                                // Error in login. Get the error message
                                String errorMsg = jObj.getString("error_msg");

                            }
                        } catch (JSONException e) {
                            // JSON error
                            e.printStackTrace();

                        }
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
                params.put(KEY_FROMCITY,fromCity);
                params.put(KEY_TOCITY,toCity);
                params.put(KEY_OPTION,doOption);
                params.put(KEY_TYPE,bagtype);


                return params;
            }

        };
        requestQueue.add(stringRequest);
    }
}
