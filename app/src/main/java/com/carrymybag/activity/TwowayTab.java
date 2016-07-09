package com.carrymybag.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class TwowayTab extends Fragment implements View.OnClickListener {

    private EditText editTextQtySmall1, editTextQtyMed1, editTextQtyLarge1, editaddr_pickup1, editaddr_dest1, PicupDate1;
    private EditText editTextQtySmall2, editTextQtyMed2, editTextQtyLarge2, editaddr_pickup2, editaddr_dest2, PicupDate2;

    private TextView textPriceSmall1, textPriceMed1, textPriceLarge1;
    private TextView textPriceSmall2, textPriceMed2, textPriceLarge2;

    private TextView DateOfDelivery1, DateOfDelivery2,estimateView;

    double qtySmall1 = 0, qtyMed1 = 0, qtyLarge1 = 0, priceSmall, priceMed, priceLarge,totalPrice = 0;
    double qtySmall2 = 0,qtyMed2 = 0,qtyLarge2 = 0;

    private Button buttonSubmit;

    private RadioGroup option1, option2;

    private RadioButton one_day1, fast1, standard1, one_day2, fast2, standard2;

    String Pickup1, Pickup2;




    private DatePickerDialog DatePickerDialog1;
    private DatePickerDialog DatePickerDialog2;
    private SimpleDateFormat dateFormatter;

    public AppController globalVariable;

    RequestQueue requestQueue;

    public static final String KEY_FROMCITY = "from_city";
    public static final String KEY_TOCITY = "to_city";
    public static final String KEY_OPTION = "do_option";
    public static final String KEY_TYPE = "bag_id";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.layout_tab_twoway, container, false);
        globalVariable = (AppController) getActivity().getApplicationContext();

        editTextQtySmall1 = (EditText) v.findViewById(R.id.qty_small1);
        editTextQtyMed1 = (EditText) v.findViewById(R.id.qty_medium1);
        editTextQtyLarge1 = (EditText) v.findViewById(R.id.qty_large1);

        textPriceSmall1 = (TextView) v.findViewById(R.id.price_small1);
        textPriceMed1 = (TextView) v.findViewById(R.id.price_medium1);
        textPriceLarge1 = (TextView) v.findViewById(R.id.price_large1);

        buttonSubmit = (Button) v.findViewById(R.id.btnSubmit);
        buttonSubmit.setOnClickListener(this);


        editaddr_pickup1 = (EditText) v.findViewById(R.id.pickup1);
        editaddr_dest1 = (EditText) v.findViewById(R.id.destination1);

        option1 = (RadioGroup) v.findViewById(R.id.delivery_options1);
        fast1 = (RadioButton) v.findViewById(R.id.radio_fast1);
        one_day1 = (RadioButton) v.findViewById(R.id.radio_1day1);
        standard1 = (RadioButton) v.findViewById(R.id.radio_standard1);


        PicupDate1 = (EditText) v.findViewById(R.id.date_pickup1);
        PicupDate1.setInputType(InputType.TYPE_NULL);
        PicupDate1.requestFocus();

        editTextQtySmall2 = (EditText) v.findViewById(R.id.qty_small2);
        editTextQtyMed2 = (EditText) v.findViewById(R.id.qty_medium2);
        editTextQtyLarge2 = (EditText) v.findViewById(R.id.qty_large2);

        textPriceSmall2 = (TextView) v.findViewById(R.id.price_small2);
        textPriceMed2 = (TextView) v.findViewById(R.id.price_medium2);
        textPriceLarge2 = (TextView) v.findViewById(R.id.price_large2);


        editaddr_pickup2 = (EditText) v.findViewById(R.id.pickup2);
        editaddr_dest2 = (EditText) v.findViewById(R.id.destination2);

        option2 = (RadioGroup) v.findViewById(R.id.delivery_options2);
        fast2 = (RadioButton) v.findViewById(R.id.radio_fast2);
        one_day2 = (RadioButton) v.findViewById(R.id.radio_1day2);
        standard2 = (RadioButton) v.findViewById(R.id.radio_standard2);

        DateOfDelivery1 = (TextView) v.findViewById(R.id.date_delivery1);
        DateOfDelivery2 = (TextView) v.findViewById(R.id.date_delivery2);

        PicupDate2 = (EditText) v.findViewById(R.id.date_pickup2);
        PicupDate2.setInputType(InputType.TYPE_NULL);
        PicupDate2.requestFocus();

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        estimateView = (TextView)v.findViewById(R.id.estimateView);



        setDateTimeField();

        editTextQtySmall1.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try{
                    qtySmall1 = Double.valueOf(s.toString());
                }catch (NumberFormatException e)
                {
                    qtySmall1 = 0;
                }
                totalPrice = priceSmall*(qtySmall1 + qtySmall2) + priceMed*(qtyMed1 + qtyMed2) + priceLarge*(qtyLarge2 + qtyLarge1);
                String stringPrice = String.valueOf(totalPrice);
                if(stringPrice=="")
                    stringPrice = "0";
                estimateView.setText("Trip total : Rs" + stringPrice);
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editTextQtyMed1.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try{
                    qtyMed1 = Double.valueOf(s.toString());
                }catch (NumberFormatException e)
                {
                    qtyMed1 = 0;
                }
                totalPrice = priceSmall*(qtySmall1 + qtySmall2) + priceMed*(qtyMed1 + qtyMed2) + priceLarge*(qtyLarge2 + qtyLarge1);
                String stringPrice = String.valueOf(totalPrice);
                if(stringPrice=="")
                    stringPrice = "0";
                estimateView.setText("Trip total : Rs" + stringPrice);
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editTextQtyLarge1.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try{
                    qtyLarge1 = Double.valueOf(s.toString());
                }catch (NumberFormatException e)
                {
                    qtyLarge1 = 0;
                }
                totalPrice = priceSmall*(qtySmall1 + qtySmall2) + priceMed*(qtyMed1 + qtyMed2) + priceLarge*(qtyLarge2 + qtyLarge1);
                String stringPrice = String.valueOf(totalPrice);
                if(stringPrice=="")
                    stringPrice = "0";
                estimateView.setText("Trip total : Rs" + stringPrice);
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editTextQtySmall2.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try{
                    qtySmall2 = Double.valueOf(s.toString());
                }catch (NumberFormatException e)
                {
                    qtySmall2 = 0;
                }
                totalPrice = priceSmall*(qtySmall1 + qtySmall2) + priceMed*(qtyMed1 + qtyMed2) + priceLarge*(qtyLarge2 + qtyLarge1);
                String stringPrice = String.valueOf(totalPrice);
                if(stringPrice=="")
                    stringPrice = "0";
                estimateView.setText("Trip total : Rs" + stringPrice);
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editTextQtyMed2.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try{
                    qtyMed2 = Double.valueOf(s.toString());
                }catch (NumberFormatException e)
                {
                    qtyMed2 = 0;
                }
                totalPrice = priceSmall*(qtySmall1 + qtySmall2) + priceMed*(qtyMed1 + qtyMed2) + priceLarge*(qtyLarge2 + qtyLarge1);
                String stringPrice = String.valueOf(totalPrice);
                if(stringPrice=="")
                    stringPrice = "0";
                estimateView.setText("Trip total : Rs" + stringPrice);
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editTextQtyLarge2.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try{
                    qtyLarge2 = Double.valueOf(s.toString());
                }catch (NumberFormatException e)
                {
                    qtyLarge2 = 0;
                }
                totalPrice = priceSmall*(qtySmall1 + qtySmall2) + priceMed*(qtyMed1 + qtyMed2) + priceLarge*(qtyLarge2 + qtyLarge1);
                String stringPrice = String.valueOf(totalPrice);
                if(stringPrice=="")
                    stringPrice = "0";
                estimateView.setText("Trip total : Rs" + stringPrice);
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        setPrices();
        editaddr_pickup1.setText(globalVariable.getFromCity());
        editaddr_dest1.setText(globalVariable.getToCity());
        editaddr_pickup1.setFocusable(false);
        editaddr_dest1.setFocusable(false);
        editaddr_pickup2.setText(globalVariable.getToCity());
        editaddr_dest2.setText(globalVariable.getFromCity());
        editaddr_pickup2.setFocusable(false);
        editaddr_dest2.setFocusable(false);
        return v;
    }
        public void onClick(View v) {

        if(v == PicupDate1) {
            DatePickerDialog1.show();
        } else if(v == PicupDate2) {
            DatePickerDialog2.show();
        }

        switch (v.getId()) {

            case R.id.btnSubmit:
                getPricesAndQuantity1();
                Intent i = new Intent(getContext(),
                        EnterDetails.class);
                getPricesAndQuantity2();
                globalVariable.setTwoWay(true);
                totalPrice = priceSmall*(qtySmall1 + qtySmall2) + priceMed*(qtyMed1 + qtyMed2) + priceLarge*(qtyLarge2 + qtyLarge1);
                String price = Double.toString(totalPrice);
                globalVariable.setTotalPrice(totalPrice);
                Toast.makeText(getActivity(), "The total Price is Rs. " + price, Toast.LENGTH_LONG).show();
                startActivity(i);
                break;
        }

    }



    private void setDateTimeField() {
        PicupDate1.setOnClickListener(this);
        PicupDate2.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog1 = new DatePickerDialog(getContext(), new android.app.DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                PicupDate1.setText(dateFormatter.format(newDate.getTime()));
                globalVariable.setPickupDate1(dateFormatter.format(newDate.getTime()));
            }


        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        DatePickerDialog2 = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                PicupDate2.setText(dateFormatter.format(newDate.getTime()));
                globalVariable.setPickupDate2(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }

    void getPricesAndQuantity1()
    {
        try {
            qtySmall1 = Double.parseDouble(editTextQtySmall1.getText().toString());
            globalVariable.setQtySmall1(qtySmall1);
        } catch (final NumberFormatException e) {
            qtySmall2 = 0.0;
        }
        try {
            qtyMed1 = Double.parseDouble(editTextQtyMed1.getText().toString());
            globalVariable.setQtyMed1(qtyMed1);
        } catch (final NumberFormatException e) {
            qtyMed2 = 0.0;
        }
        try {
            qtyLarge1 = Double.parseDouble(editTextQtyLarge1.getText().toString());
            globalVariable.setQtyLarge1(qtyLarge1);
        } catch (final NumberFormatException e) {
            qtyLarge1 = 0.0;
        }
        try {
            priceSmall = Double.parseDouble(textPriceSmall1.getText().toString());
        } catch (final NumberFormatException e) {
            priceSmall = 0.0;
        }
        try {
            priceMed = Double.parseDouble(textPriceMed1.getText().toString());
        } catch (final NumberFormatException e) {
            priceMed = 0.0;
        }
        try {
            priceLarge = Double.parseDouble(textPriceLarge1.getText().toString());
        } catch (final NumberFormatException e) {
            priceLarge = 0.0;
        }

        globalVariable.setQtySmall1(qtySmall1);
        globalVariable.setQtyMed1(qtyMed1);
        globalVariable.setQtyLarge1(qtyLarge1);
        globalVariable.setPriceSmall1(priceSmall);
        globalVariable.setPriceMed1(priceMed);
        globalVariable.setPriceLarge1(priceLarge);




    }
    void getPricesAndQuantity2()
    {

        try {
            qtySmall2 = Double.parseDouble(editTextQtySmall2.getText().toString());
            globalVariable.setQtySmall2(qtySmall2);
        } catch (final NumberFormatException e) {
            qtySmall2 = 0.0;
        }
        try {
            qtyMed2 = Double.parseDouble(editTextQtyMed2.getText().toString());
            globalVariable.setQtyMed2(qtyMed2);
        } catch (final NumberFormatException e) {
            qtyMed2 = 0.0;
        }
        try {
            qtyLarge2 = Double.parseDouble(editTextQtyLarge2.getText().toString());
            globalVariable.setQtyLarge2(qtyLarge2);
        } catch (final NumberFormatException e) {
            qtyLarge2 = 0.0;
        }
        try {
            priceSmall = Double.parseDouble(textPriceSmall2.getText().toString());
        } catch (final NumberFormatException e) {
            priceSmall = 0.0;
        }
        try {
            priceMed = Double.parseDouble(textPriceMed2.getText().toString());
        } catch (final NumberFormatException e) {
            priceMed = 0.0;
        }
        try {
            priceLarge = Double.parseDouble(textPriceLarge2.getText().toString());
        } catch (final NumberFormatException e) {
            priceLarge = 0.0;
        }

        globalVariable.setQtySmall2(qtySmall2);
        globalVariable.setQtyMed2(qtyMed2);
        globalVariable.setQtyLarge2(qtyLarge2);
        globalVariable.setPriceSmall2(priceSmall);
        globalVariable.setPriceMed2(priceMed);
        globalVariable.setPriceLarge2(priceLarge);
    }

    private void getPrices(final VolleyCallback callback, String type)
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
                                callback.onSuccess(val);


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
    private interface VolleyCallback{
        void onSuccess(String result);
    }
    private void setPrices()
    {
        getPrices(new VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                priceSmall = Double.parseDouble(result);
                textPriceSmall1.setText(result);
                textPriceSmall2.setText(result);
            }
        },"Small");
        getPrices(new VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                priceMed = Double.parseDouble(result);
                textPriceMed1.setText(result);
                textPriceMed2.setText(result);
            }
        },"Medium");
        getPrices(new VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                priceLarge = Double.parseDouble(result);
                textPriceLarge1.setText(result);
                textPriceLarge2.setText(result);
            }
        },"Large");
    }
}