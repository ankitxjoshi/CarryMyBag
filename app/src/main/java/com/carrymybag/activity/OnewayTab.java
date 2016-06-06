package com.carrymybag.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.carrymybag.R;

//import info.androidhive.materialtabs.R;

public class OnewayTab extends Fragment implements View.OnClickListener {

    public OnewayTab() {
        // Required empty public constructor
    }

    private EditText editTextQtySmall;
    private EditText editTextQtyMed;
    private EditText editTextQtyLarge;

    private TextView textSmallBag;
    private TextView textMedBag;
    private TextView textLargeBag;

    private TextView textPriceSmall;
    private TextView textPriceMed;
    private TextView textPriceLarge;

    private Button buttonSubmit;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.layout_tab_oneway,container,false);

         editTextQtySmall = (EditText) v.findViewById(R.id.qty_small);
         editTextQtyMed = (EditText) v.findViewById(R.id.qty_medium);
         editTextQtyLarge = (EditText) v.findViewById(R.id.qty_large);

        textSmallBag = (TextView) v.findViewById(R.id.bag_small);
        textMedBag = (TextView) v.findViewById(R.id.bag_medium);
        textLargeBag = (TextView) v.findViewById(R.id.bag_large);

        textPriceSmall = (TextView) v.findViewById(R.id.price_small);
        textPriceMed = (TextView) v.findViewById(R.id.price_medium);
        textPriceLarge = (TextView) v.findViewById(R.id.price_large);

        buttonSubmit = (Button) v.findViewById(R.id.btnSubmit);
        buttonSubmit.setOnClickListener(this);


        return v;
    }
    public void onClick(View v) {

        Float qtySmall = Float.valueOf(editTextQtySmall.getText().toString());
        Float qtyMed = Float.valueOf(editTextQtySmall.getText().toString());
        Float qtyLarge = Float.valueOf(editTextQtySmall.getText().toString());

        Float priceSmall = Float.valueOf(textPriceSmall.getText().toString());
        Float priceMed = Float.valueOf(textPriceMed.getText().toString());
        Float priceLarge = Float.valueOf(textPriceLarge.getText().toString());

        Float totalPriceSmall = priceSmall * qtySmall;
        Float totalPriceMed = priceMed * qtyMed;
        Float totalPriceLarge = priceLarge * qtyLarge;

        float price = totalPriceLarge + totalPriceMed + totalPriceSmall;

        Toast.makeText(getActivity(), "Price is " + price, Toast.LENGTH_LONG).show();
    }


}
