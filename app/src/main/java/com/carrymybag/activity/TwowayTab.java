package com.carrymybag.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.RelativeLayout;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
    RadioButton radioButton;

    int flag1 = 1, flag2 = 1;
    SimpleDateFormat df;

    Date dateDelivery1, dateDelivery2;
    String currentDate,currentDate2;


    private DatePickerDialog DatePickerDialog1;
    private DatePickerDialog DatePickerDialog2;
    private SimpleDateFormat dateFormatter;
    Date datePickup;

    public AppController globalVariable;

    RequestQueue requestQueue;
    private  int priceFactor1 = 1;
    private int delDateFactor1 = 5;
    private  int priceFactor2 = 1;
    private int delDateFactor2 = 5;
    boolean dateEntered1,dateEntered2;

    public static final String KEY_FROMCITY = "from_city";
    public static final String KEY_TOCITY = "to_city";
    public static final String KEY_OPTION = "do_option";
    public static final String KEY_TYPE = "bag_id";

    boolean isRadio1,isRadio2;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.content_twoway_tab, container, false);
        globalVariable = (AppController) getActivity().getApplicationContext();
        df = new SimpleDateFormat("yyyy-MM-dd");

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

        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
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
                totalPrice = priceSmall*(qtySmall1*priceFactor1 + qtySmall2*priceFactor2) + priceMed*(qtyMed1*priceFactor1 + qtyMed2*priceFactor2) + priceLarge*(qtyLarge2*priceFactor1 + qtyLarge1*priceFactor2);
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
                totalPrice = priceSmall*(qtySmall1*priceFactor1 + qtySmall2*priceFactor2) + priceMed*(qtyMed1*priceFactor1 + qtyMed2*priceFactor2) + priceLarge*(qtyLarge2*priceFactor1 + qtyLarge1*priceFactor2);
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
                totalPrice = priceSmall*(qtySmall1*priceFactor1 + qtySmall2*priceFactor2) + priceMed*(qtyMed1*priceFactor1 + qtyMed2*priceFactor2) + priceLarge*(qtyLarge2*priceFactor1 + qtyLarge1*priceFactor2);
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
                totalPrice = priceSmall*(qtySmall1*priceFactor1 + qtySmall2*priceFactor2) + priceMed*(qtyMed1*priceFactor1 + qtyMed2*priceFactor2) + priceLarge*(qtyLarge2*priceFactor1 + qtyLarge1*priceFactor2);
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
                totalPrice = priceSmall*(qtySmall1*priceFactor1 + qtySmall2*priceFactor2) + priceMed*(qtyMed1*priceFactor1 + qtyMed2*priceFactor2) + priceLarge*(qtyLarge2*priceFactor1 + qtyLarge1*priceFactor2);
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
                totalPrice = priceSmall*(qtySmall1*priceFactor1 + qtySmall2*priceFactor2) + priceMed*(qtyMed1*priceFactor1 + qtyMed2*priceFactor2) + priceLarge*(qtyLarge2*priceFactor1 + qtyLarge1*priceFactor2);
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


        final RadioGroup radioGroup1 = (RadioGroup) v.findViewById(R.id.delivery_options1);
        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int selectedId= radioGroup1.getCheckedRadioButtonId();
                radioButton=(RadioButton)v.findViewById(selectedId);
                if(dateEntered1)
                {
                    isRadio1 = true;
                    if(selectedId==R.id.radio_1day1)
                    {
                        globalVariable.setdoOption("Single");
                        modifyPrice1(new VolleyCallback() {
                            @Override
                            public void onSuccess(String result) {

                                dateDelivery1 = addDays(datePickup,delDateFactor1);
                                globalVariable.setDeliveryDate1(String .valueOf(df.format(dateDelivery1)));
                                DateOfDelivery1.setText(String .valueOf(df.format(dateDelivery1)));

                            }
                        });
                    }
                    if(selectedId==R.id.radio_fast1)
                    {
                        globalVariable.setdoOption("Fast");
                        modifyPrice1(new VolleyCallback() {
                            @Override
                            public void onSuccess(String result) {

                                dateDelivery1 = addDays(datePickup,delDateFactor1);
                                globalVariable.setDeliveryDate1(String .valueOf(df.format(dateDelivery1)));
                                DateOfDelivery1.setText(String .valueOf(df.format(dateDelivery1)));

                            }
                        });
                    }
                    if(selectedId==R.id.radio_standard1)
                    {
                        globalVariable.setdoOption("Standard");
                        modifyPrice1(new VolleyCallback() {
                            @Override
                            public void onSuccess(String result) {

                                dateDelivery1 = addDays(datePickup,delDateFactor1);
                                globalVariable.setDeliveryDate1(String .valueOf(df.format(dateDelivery1)));
                                DateOfDelivery1.setText(String .valueOf(df.format(dateDelivery1)));

                            }
                        });
                    }
                }
                else {
                    Toast.makeText(getActivity(), "Please mention the date first", Toast.LENGTH_SHORT).show();
                    radioButton.setChecked(false);
                }


            }
        });
        final RadioGroup radioGroup2 = (RadioGroup) v.findViewById(R.id.delivery_options2);
        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int selectedId= radioGroup2.getCheckedRadioButtonId();
                radioButton=(RadioButton)v.findViewById(selectedId);
                if(dateEntered2)
                {
                    isRadio2 = true;
                    if(selectedId==R.id.radio_1day2)
                    {
                        globalVariable.setdoOption("Single");
                        modifyPrice2(new VolleyCallback() {
                            @Override
                            public void onSuccess(String result) {

                                dateDelivery2 = addDays(datePickup,delDateFactor2);
                                globalVariable.setDeliveryDate2(String .valueOf(df.format(dateDelivery2)));
                                DateOfDelivery2.setText(String .valueOf(df.format(dateDelivery2)));

                            }
                        });
                    }
                    if(selectedId==R.id.radio_fast2)
                    {
                        globalVariable.setdoOption("Fast");
                        modifyPrice2(new VolleyCallback() {
                            @Override
                            public void onSuccess(String result) {

                                dateDelivery2 = addDays(datePickup,delDateFactor2);
                                globalVariable.setDeliveryDate2(String .valueOf(df.format(dateDelivery2)));
                                DateOfDelivery2.setText(String .valueOf(df.format(dateDelivery2)));

                            }
                        });
                    }
                    if(selectedId==R.id.radio_standard2)
                    {
                        globalVariable.setdoOption("Standard");
                        modifyPrice2(new VolleyCallback() {
                            @Override
                            public void onSuccess(String result) {

                                dateDelivery2 = addDays(datePickup,delDateFactor2);
                                globalVariable.setDeliveryDate2(String .valueOf(df.format(dateDelivery2)));
                                DateOfDelivery2.setText(String .valueOf(df.format(dateDelivery2)));

                            }
                        });
                    }
                }
                else {
                    Toast.makeText(getActivity(), "Please mention the date first", Toast.LENGTH_SHORT).show();
                    radioButton.setChecked(false);
                }

            }
        });
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
                getPricesAndQuantity2();
                globalVariable.setTwoWay(true);
                totalPrice = priceSmall*(qtySmall1*priceFactor1 + qtySmall2*priceFactor2) + priceMed*(qtyMed1*priceFactor1 + qtyMed2*priceFactor2) + priceLarge*(qtyLarge2*priceFactor1 + qtyLarge1*priceFactor2);
                String price = Double.toString(totalPrice);
                globalVariable.setTotalPrice(totalPrice);
                if(flag1==1 && isRadio1 && (qtyLarge1>0 || qtyMed1>0 || qtySmall1>0)) {
                    if (flag2 == 1 && isRadio2 && (qtyLarge2>0 || qtyMed2>0 || qtySmall2>0)) {
                        Intent i = new Intent(getContext(),
                                EnterDetails.class);
                        startActivity(i);
                    }
                    else if(flag2==0)
                    {
                        Toast.makeText(getActivity(),"Pickup Date for Leg 2 less than today's date",Toast.LENGTH_LONG).show();
                    }
                    else if(qtyLarge2==0 && qtySmall2==0 && qtyMed2==0){
                        Toast.makeText(getActivity(),"Atleast one bag needed to place an order for Leg 2",Toast.LENGTH_LONG).show();
                    }
                    else if(!isRadio2)
                    {
                        Toast.makeText(getActivity(),"Select one delivery option for return trip",Toast.LENGTH_LONG).show();
                    }
                }

                else if(flag1==0)
                {
                    Toast.makeText(getActivity(),"Pickup Date for Leg 1 less than today's date",Toast.LENGTH_LONG).show();
                }



                else if(qtyLarge1==0 && qtySmall1==0 && qtyMed1==0){
                    Toast.makeText(getActivity(),"Atleast one bag needed to place an order for Leg 1",Toast.LENGTH_LONG).show();
                }


                else if(!isRadio1)
                {
                    Toast.makeText(getActivity(),"Select one delivery option for first trip",Toast.LENGTH_LONG).show();
                }


                break;
        }

    }


    private void modifyPrice1(final VolleyCallback callback) {

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
                                priceFactor1 = jObj.getInt("price_factor");
                                delDateFactor1 = jObj.getInt("del_date");
                                totalPrice = priceSmall*(qtySmall1*priceFactor1 + qtySmall2*priceFactor2) + priceMed*(qtyMed1*priceFactor1 + qtyMed2*priceFactor2) + priceLarge*(qtyLarge2*priceFactor1 + qtyLarge1*priceFactor2);
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
    private void modifyPrice2(final VolleyCallback callback) {

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
                                priceFactor2 = jObj.getInt("price_factor");
                                delDateFactor2 = jObj.getInt("del_date");
                                totalPrice = priceSmall*(qtySmall1*priceFactor1 + qtySmall2*priceFactor2) + priceMed*(qtyMed1*priceFactor1 + qtyMed2*priceFactor2) + priceLarge*(qtyLarge2*priceFactor1 + qtyLarge1*priceFactor2);
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
        PicupDate1.setOnClickListener(this);
        PicupDate2.setOnClickListener(this);


        Calendar newCalendar = Calendar.getInstance();

        final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        currentDate = df.format(newCalendar.getTime());
        PicupDate1.setText(currentDate);

        DatePickerDialog1 = new DatePickerDialog(getContext(), new android.app.DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                String pickupDate = df.format(newDate.getTime());
                globalVariable.setPickupDate1(pickupDate);
                PicupDate1.setText(pickupDate);
                Date dateCurrent;
                try {
                    datePickup = df.parse(pickupDate);
                    dateCurrent = df.parse(currentDate);
                    dateEntered1 = true;
                    if (datePickup.compareTo(dateCurrent) < 1) {
                        flag1 = 0;
                        Toast.makeText(getActivity(), "Pickup date should be greater than today's date", Toast.LENGTH_LONG).show();
                    }
                    else {
                        flag1 = 1;
                        dateDelivery1 = addDays(datePickup,delDateFactor1);
                        globalVariable.setDeliveryDate1(String .valueOf(df.format(dateDelivery1)));
                        DateOfDelivery1.setText(String .valueOf(df.format(dateDelivery1)));
                    }

                } catch (ParseException e) {

                }

            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        currentDate2 = df.format(newCalendar.getTime());
        PicupDate2.setText(currentDate2);

        DatePickerDialog2 = new DatePickerDialog(getContext(), new android.app.DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                String pickupDate = df.format(newDate.getTime());
                globalVariable.setPickupDate2(pickupDate);
                PicupDate2.setText(pickupDate);
                dateEntered2 = true;
                Date dateCurrent2;
                try {
                    datePickup = df.parse(pickupDate);
                    dateCurrent2 = df.parse(currentDate2);
                    if (datePickup.compareTo(dateCurrent2) < 1) {
                        flag2 = 0;
                        Toast.makeText(getActivity(), "Pickup date should be greater than today's date", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        flag2 = 1;
                        dateDelivery2 = addDays(datePickup,delDateFactor2);
                        globalVariable.setDeliveryDate2(String .valueOf(df.format(dateDelivery2)));
                        DateOfDelivery2.setText(String .valueOf(df.format(dateDelivery2)));
                    }

                } catch (ParseException e) {

                }

            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

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

    public static Date addDays(Date date, int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE,days);
        return cal.getTime();
    }
}