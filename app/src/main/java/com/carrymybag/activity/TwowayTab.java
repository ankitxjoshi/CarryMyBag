package com.carrymybag.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.ParseException;
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
import com.carrymybag.helper.GlobalClass;

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

    private TextView DateOfDelivery1, DateOfDelivery2;

    double qtySmall, qtyMed, qtyLarge, priceSmall, priceMed, priceLarge;

    private Button buttonSubmit, buttonView1, buttonView2;

    private RadioGroup option1, option2;

    private RadioButton one_day1, fast1, standard1, one_day2, fast2, standard2;

    String Pickup1, Pickup2;




    private DatePickerDialog DatePickerDialog1;
    private DatePickerDialog DatePickerDialog2;
    private SimpleDateFormat dateFormatter;

    public GlobalClass globalVariable;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.layout_tab_twoway, container, false);
        globalVariable = (GlobalClass) getActivity().getApplicationContext();

        editTextQtySmall1 = (EditText) v.findViewById(R.id.qty_small1);
        editTextQtyMed1 = (EditText) v.findViewById(R.id.qty_medium1);
        editTextQtyLarge1 = (EditText) v.findViewById(R.id.qty_large1);

        textPriceSmall1 = (TextView) v.findViewById(R.id.price_small1);
        textPriceMed1 = (TextView) v.findViewById(R.id.price_medium1);
        textPriceLarge1 = (TextView) v.findViewById(R.id.price_large1);

        buttonSubmit = (Button) v.findViewById(R.id.btnSubmit);
        buttonSubmit.setOnClickListener(this);

        buttonView1 = (Button) v.findViewById(R.id.btnViewPrices1);
        buttonView1.setOnClickListener(this);

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

        buttonView2 = (Button) v.findViewById(R.id.btnViewPrices2);
        buttonView2.setOnClickListener(this);

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

        setDateTimeField();
        return v;
    }
        public void onClick(View v) {

        if(v == PicupDate1) {
            DatePickerDialog1.show();
        } else if(v == PicupDate2) {
            DatePickerDialog2.show();
        }

        switch (v.getId()) {

            case R.id.btnViewPrices1:



            case R.id.btnViewPrices2:



            case R.id.btnSubmit:
                getPricesAndQuantity1();
                Intent i = new Intent(getContext(),
                        EnterDetails.class);
                double totalPrice1 = priceSmall * qtySmall + priceMed * qtyMed + priceLarge * qtyLarge;
                getPricesAndQuantity2();
                double totalPrice2 = priceSmall * qtySmall + priceMed * qtyMed + priceLarge * qtyLarge;
                globalVariable.setTwoWay(true);
                double totalPrice = totalPrice1 + totalPrice2;
                String price = Double.toString(totalPrice);
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
            qtySmall = Double.parseDouble(editTextQtySmall1.getText().toString());
        } catch (final NumberFormatException e) {
            qtySmall = 0.0;
        }
        try {
            qtyMed = Double.parseDouble(editTextQtyMed1.getText().toString());
        } catch (final NumberFormatException e) {
            qtyMed = 0.0;
        }
        try {
            qtyLarge = Double.parseDouble(editTextQtyLarge1.getText().toString());
        } catch (final NumberFormatException e) {
            qtyLarge = 0.0;
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

        globalVariable.setQtySmall1(qtySmall);
        globalVariable.setQtyMed1(qtyMed);
        globalVariable.setQtyLarge1(qtyLarge);
        globalVariable.setPriceSmall1(priceSmall);
        globalVariable.setPriceMed1(priceMed);
        globalVariable.setPriceLarge1(priceLarge);




    }
    void getPricesAndQuantity2()
    {

        try {
            qtySmall = Double.parseDouble(editTextQtySmall2.getText().toString());
        } catch (final NumberFormatException e) {
            qtySmall = 0.0;
        }
        try {
            qtyMed = Double.parseDouble(editTextQtyMed2.getText().toString());
        } catch (final NumberFormatException e) {
            qtyMed = 0.0;
        }
        try {
            qtyLarge = Double.parseDouble(editTextQtyLarge2.getText().toString());
        } catch (final NumberFormatException e) {
            qtyLarge = 0.0;
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

        globalVariable.setQtySmall2(qtySmall);
        globalVariable.setQtyMed2(qtyMed);
        globalVariable.setQtyLarge2(qtyLarge);
        globalVariable.setPriceSmall2(priceSmall);
        globalVariable.setPriceMed2(priceMed);
        globalVariable.setPriceLarge2(priceLarge);
    }
}