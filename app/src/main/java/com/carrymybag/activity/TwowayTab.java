package com.carrymybag.activity;

import android.net.ParseException;
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

import com.carrymybag.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class TwowayTab extends Fragment implements View.OnClickListener {

    private EditText editTextQtySmall1, editTextQtyMed1, editTextQtyLarge1, addr_pickup1, addr_dest1, PicupDate1;
    private EditText editTextQtySmall2, editTextQtyMed2, editTextQtyLarge2, addr_pickup2, addr_dest2, PicupDate2;

    private TextView textPriceSmall1, textPriceMed1, textPriceLarge1;
    private TextView textPriceSmall2, textPriceMed2, textPriceLarge2;

    private TextView DateOfDelivery1, DateOfDelivery2;

    double qtySmall, qtyMed, qtyLarge, priceSmall, priceMed, priceLarge;

    private Button buttonSubmit, buttonView1, buttonView2;

    private RadioGroup option1, option2;

    private RadioButton one_day1, fast1, standard1, one_day2, fast2, standard2;

    String Pickup1, Pickup2;

    public TwowayTab() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.layout_tab_twoway, container, false);

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

        addr_pickup1 = (EditText) v.findViewById(R.id.pickup1);
        addr_dest1 = (EditText) v.findViewById(R.id.destination1);

        option1 = (RadioGroup) v.findViewById(R.id.delivery_options1);
        fast1 = (RadioButton) v.findViewById(R.id.radio_fast1);
        one_day1 = (RadioButton) v.findViewById(R.id.radio_1day1);
        standard1 = (RadioButton) v.findViewById(R.id.radio_standard1);


        PicupDate1 = (EditText) v.findViewById(R.id.date_pickup1);

        editTextQtySmall2 = (EditText) v.findViewById(R.id.qty_small2);
        editTextQtyMed2 = (EditText) v.findViewById(R.id.qty_medium2);
        editTextQtyLarge2 = (EditText) v.findViewById(R.id.qty_large2);

        textPriceSmall2 = (TextView) v.findViewById(R.id.price_small2);
        textPriceMed2 = (TextView) v.findViewById(R.id.price_medium2);
        textPriceLarge2 = (TextView) v.findViewById(R.id.price_large2);

        buttonView2 = (Button) v.findViewById(R.id.btnViewPrices2);
        buttonView2.setOnClickListener(this);

        addr_pickup2 = (EditText) v.findViewById(R.id.pickup2);
        addr_dest2 = (EditText) v.findViewById(R.id.destination2);

        option2 = (RadioGroup) v.findViewById(R.id.delivery_options2);
        fast2 = (RadioButton) v.findViewById(R.id.radio_fast2);
        one_day2 = (RadioButton) v.findViewById(R.id.radio_1day2);
        standard2 = (RadioButton) v.findViewById(R.id.radio_standard2);

        DateOfDelivery1 = (TextView) v.findViewById(R.id.date_delivery1);
        DateOfDelivery2 = (TextView) v.findViewById(R.id.date_delivery2);

        PicupDate2 = (EditText) v.findViewById(R.id.date_pickup2);
        return v;
    }

    public String getNextDate(String Pickup)
    {
        final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        format.setLenient(false);
        Date date = null;
        try {
            try {
                date = format.parse(Pickup);
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }
            final Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(date);
            calendar1.add(Calendar.DAY_OF_MONTH,1);
            return format.format(calendar1.getTime());

        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(),"Invalid Pickup Date",Toast.LENGTH_LONG).show();
        }
        return ("Please correct the pickup date");
    }

    public String showExpectedDate(String date,int c)
    {
        String next = date;
        if(c==1)
        {
            next = getNextDate(next);
        }
        else if(c==2)
        {
            for(int i=0; i<2; i++)
                next = getNextDate(next);
        }
        else
        {
            for(int i=0;i<3;i++)
                next = getNextDate(next);
        }
        return next;
    }


    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btnViewPrices1:

                try {
                    Pickup1 = PicupDate1.getText().toString();
                }
                catch(NullPointerException e)
                {
                    Toast.makeText(getActivity(),"No Pickup date entered",Toast.LENGTH_LONG).show();
                }
                int choice=3;
                String Delivery1 = null;
                if(Pickup1.matches("\\d{2}/\\d{2}/\\d{4}")) {
                    if (option1.getCheckedRadioButtonId() == -1) {
                        Toast.makeText(getActivity(), "No delivery option selected", Toast.LENGTH_LONG).show();
                    } else {
                        if (fast1.isChecked()) {
                            textPriceSmall1.setText("1000");
                            textPriceMed1.setText("1100");
                            textPriceLarge1.setText("1200");
                            choice = 2;
                            Delivery1 = showExpectedDate(Pickup1, choice);
                        }
                        if (standard1.isChecked()) {
                            textPriceSmall1.setText("500");
                            textPriceMed1.setText("600");
                            textPriceLarge1.setText("700");
                            choice = 3;
                            Delivery1 = showExpectedDate(Pickup1, choice);
                        }
                        if (one_day1.isChecked()) {
                            textPriceSmall1.setText("1500");
                            textPriceLarge1.setText("1700");
                            textPriceMed1.setText("1600");
                            choice = 1;
                            Delivery1 = showExpectedDate(Pickup1, choice);
                        }
                        DateOfDelivery1.setText(Delivery1);
                    }
                }
                else
                {
                    Toast.makeText(getActivity(),"Invalid pickup date",Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.btnViewPrices2:

                try {
                    Pickup2 = PicupDate2.getText().toString();
                }
                catch(NullPointerException e)
                {
                    Toast.makeText(getActivity(),"No Pickup date entered",Toast.LENGTH_LONG).show();
                }
                choice = 3;
                String Delivery2 = null;
                if(Pickup2.matches("\\d{2}/\\d{2}/\\d{4}")) {
                    if (option2.getCheckedRadioButtonId() == -1) {
                        Toast.makeText(getActivity(), "No delivery option selected", Toast.LENGTH_LONG).show();
                    } else {
                        if (fast2.isChecked()) {
                            textPriceSmall2.setText("1000");
                            textPriceMed2.setText("1100");
                            textPriceLarge2.setText("1200");
                            choice = 2;
                            Delivery2 = showExpectedDate(Pickup2, choice);
                        }
                        if (standard2.isChecked()) {
                            textPriceSmall2.setText("500");
                            textPriceMed2.setText("600");
                            textPriceLarge2.setText("700");
                            choice = 3;
                            Delivery2 = showExpectedDate(Pickup2, choice);
                        }
                        if (one_day2.isChecked()) {
                            textPriceSmall2.setText("1500");
                            textPriceLarge2.setText("1700");
                            textPriceMed2.setText("1600");
                            choice = 1;
                            Delivery2 = showExpectedDate(Pickup2, choice);
                        }
                        DateOfDelivery2.setText(Delivery2);
                    }
                }
                else
                {
                    Toast.makeText(getActivity(),"Invalid pickup date",Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.btnSubmit:
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
                double totalPrice1 = priceSmall * qtySmall + priceMed * qtyMed + priceLarge * qtyLarge;

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

                double totalPrice2 = priceSmall * qtySmall + priceMed * qtyMed + priceLarge * qtyLarge;
                double totalPrice = totalPrice1 + totalPrice2;
                String price = Double.toString(totalPrice);

                Toast.makeText(getActivity(), "The total Price is Rs. " + price, Toast.LENGTH_LONG).show();
                break;
        }
    }

}