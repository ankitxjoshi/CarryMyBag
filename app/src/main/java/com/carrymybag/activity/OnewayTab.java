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

    private TextView textPriceSmall, textPriceMed, textPriceLarge, DateOfDelivery,estimateView;

    double qtySmall = 0, qtyMed = 0, qtyLarge = 0, priceSmall = 0, priceMed = 0, priceLarge = 0,totalPrice = 0;

    int flag = 1;

    private Button buttonSubmit, buttonViewPrices;

    private RadioGroup option;

    private RadioButton one_day, fast, standard;

    Date dateDelivery;

    RequestQueue requestQueue;

    public static final String KEY_FROMCITY = "from_city";
    public static final String KEY_TOCITY = "to_city";
    public static final String KEY_OPTION = "do_option";
    public static final String KEY_TYPE = "bag_id";
    Date datePickup;
    SimpleDateFormat df;
    String currentDate;



    private DatePickerDialog DatePickerDialog;

    private SimpleDateFormat dateFormatter;

    public AppController globalVariable;
    RadioButton radioButton;
    private  int priceFactor = 1;
    private int delDateFactor = 5;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.content_oneway_tab, container, false);
        globalVariable = (AppController) getActivity().getApplicationContext();

        dateFormatter = new SimpleDateFormat("yyyy-mm-dd", Locale.US);
        df = new SimpleDateFormat("yyyy-MM-dd");


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
        estimateView = (TextView)v.findViewById(R.id.estimateView);

        PicupDate = (EditText) v.findViewById(R.id.date_pickup);
        PicupDate.setInputType(InputType.TYPE_NULL);
        PicupDate.requestFocus();
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getPricesAndQuantity();
                totalPrice = priceSmall * qtySmall + priceMed * qtyMed + priceLarge * qtyLarge;
                String price = Double.toString(totalPrice);
                globalVariable.setTotalPrice(totalPrice);
                globalVariable.setTwoWay(false);
                if(flag==1 && qtySmall>0 && qtyMed>0 && qtyLarge>0) {
                    Intent i = new Intent(getContext(),
                            EnterDetails.class);
                    startActivity(i);
                }
                else if(flag==0)
                {
                    Toast.makeText(getActivity(),"Pickup Date cannot be less than today's date",Toast.LENGTH_LONG).show();
                }
                else if(qtyLarge==0 && qtySmall==0 && qtyMed==0)
                {
                    Toast.makeText(getActivity(),"Atleast one bag needed to place an order",Toast.LENGTH_LONG).show();
                }

            }
        });
        editTextQtySmall.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try{
                    qtySmall = Double.valueOf(s.toString());
                }catch (NumberFormatException e)
                {
                    qtySmall = 0;
                }
                totalPrice = priceSmall * qtySmall + priceMed * qtyMed + priceLarge * qtyLarge;
                String stringPrice = String.valueOf(totalPrice);
                estimateView.setText("Trip total : Rs" + stringPrice);
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editTextQtyMed.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try{
                    qtyMed = Double.valueOf(s.toString());
                }catch (NumberFormatException e)
                {
                    qtyMed = 0;
                }
                totalPrice = priceSmall * qtySmall + priceMed * qtyMed + priceLarge * qtyLarge;
                String stringPrice = String.valueOf(totalPrice);
                estimateView.setText("Trip total : Rs" + stringPrice);
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editTextQtyLarge.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try{
                    qtyLarge = Double.valueOf(s.toString());
                }catch (NumberFormatException e)
                {
                    qtyLarge = 0;
                }
                totalPrice = priceSmall * qtySmall + priceMed * qtyMed + priceLarge * qtyLarge;
                String stringPrice = String.valueOf(totalPrice);
                estimateView.setText("Trip total : Rs" + stringPrice);
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

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
                if(selectedId==R.id.radio_1day)
                {
                    globalVariable.setdoOption("Single");
                    modifyPrice(new VolleyCallback() {
                        @Override
                        public void onSuccess(String result) {

                            dateDelivery = addDays(datePickup,delDateFactor);
                            globalVariable.setDeliveryDate1(String .valueOf(df.format(dateDelivery)));
                            DateOfDelivery.setText(String .valueOf(df.format(dateDelivery)));

                        }
                    });
                }
                if(selectedId==R.id.radio_fast)
                {
                    globalVariable.setdoOption("Fast");
                    modifyPrice(new VolleyCallback() {
                        @Override
                        public void onSuccess(String result) {

                            dateDelivery = addDays(datePickup,delDateFactor);
                            globalVariable.setDeliveryDate1(String .valueOf(df.format(dateDelivery)));
                            DateOfDelivery.setText(String .valueOf(df.format(dateDelivery)));

                        }
                    });
                }
                if(selectedId==R.id.radio_standard)
                {
                    globalVariable.setdoOption("Standard");
                    modifyPrice(new VolleyCallback() {
                        @Override
                        public void onSuccess(String result) {

                            dateDelivery = addDays(datePickup,delDateFactor);
                            globalVariable.setDeliveryDate1(String .valueOf(df.format(dateDelivery)));
                            DateOfDelivery.setText(String .valueOf(df.format(dateDelivery)));

                        }
                    });
                }
            }
        });
        setPrices();
        editaddr_pickup.setText(globalVariable.getFromCity());
        editaddr_dest.setText(globalVariable.getToCity());
        editaddr_pickup.setFocusable(false);
        editaddr_dest.setFocusable(false);

        return v;
    }

    private void modifyPrice(final VolleyCallback callback) {

        requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_PRICEFACTOR,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jObj = null;
                        boolean error = false;
                        try {
                            jObj = new JSONObject(response);
                            error = jObj.getBoolean("error");
                            if(!error)
                            {
                                priceFactor = jObj.getInt("price_factor");
                                delDateFactor = jObj.getInt("del_date");
                                totalPrice = (priceSmall*qtySmall+priceMed*qtyMed+priceLarge*qtyLarge)*priceFactor;
                                globalVariable.setTotalPrice(totalPrice);
                                estimateView.setText("Trip total : Rs" + String.valueOf(totalPrice));
                                callback.onSuccess("1");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        callback.onSuccess(String.valueOf(error));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onSuccess(String.valueOf(false));
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(KEY_FROMCITY,globalVariable.getFromCity());
                params.put(KEY_TOCITY,globalVariable.getToCity());
                params.put(KEY_OPTION,globalVariable.getDoOption());
                return params;
            }

        };
        requestQueue.add(stringRequest);

    }



    private void setDateTimeField() {

        PicupDate.setOnClickListener(this);


        Calendar newCalendar = Calendar.getInstance();

        currentDate = df.format(newCalendar.getTime());
        PicupDate.setText(currentDate);

        DatePickerDialog = new DatePickerDialog(getContext(), new android.app.DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year,monthOfYear,dayOfMonth);
                String pickupDate = df.format(newDate.getTime());
                globalVariable.setPickupDate1(pickupDate);
                PicupDate.setText(pickupDate);
                Date dateCurrent;
                try {
                    datePickup = df.parse(pickupDate);
                    dateCurrent = df.parse(currentDate);
                    if(datePickup.compareTo(dateCurrent)<1)
                    {
                        Toast.makeText(getActivity(),"Pickup date should be greater than today's date",Toast.LENGTH_LONG).show();
                        flag = 0;
                    }
                    else
                    {
                        flag = 1;
                        dateDelivery = addDays(datePickup,delDateFactor);
                        globalVariable.setDeliveryDate1(String .valueOf(df.format(dateDelivery)));
                        DateOfDelivery.setText(String .valueOf(df.format(dateDelivery)));
                    }

                }
                catch (ParseException e)
                {

                }

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
                textPriceSmall.setText(result);
            }
        },"Small");
        getPrices(new VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                priceMed = Double.parseDouble(result);
                textPriceMed.setText(result);
            }
        },"Medium");
        getPrices(new VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                priceLarge = Double.parseDouble(result);
                textPriceLarge.setText(result);
            }
        },"Large");
    }

    public static Date addDays(Date date, int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE,days);
        return cal.getTime();
    }


}
