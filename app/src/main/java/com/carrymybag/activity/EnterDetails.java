package com.carrymybag.activity;

/**
 * Created by Ankit on 6/13/2016.
 */

 import android.os.Bundle;
 import android.support.v7.app.AppCompatActivity;
 import android.view.View;
 import android.widget.AdapterView;
 import android.widget.AdapterView.OnItemSelectedListener;
 import android.widget.ArrayAdapter;
 import android.widget.Button;
 import android.widget.EditText;
 import android.widget.Spinner;
 import android.widget.TextView;
 import android.widget.Toast;

 import com.carrymybag.R;

public class EnterDetails extends AppCompatActivity implements OnItemSelectedListener {

    public EditText ContactOrigin, AddressOrigin, PinCodeOrigin;
    public EditText ContactDest, AddressDest, PinCodeDest;
    public TextView StateOrigin, StateDest;
    public EditText ColorSmallBag, ColorMedBag, ColorLargeBag;

    public Button btnSubmit;

    public String contactOrigin, contactDest, pinOrigin, pinDest, addressOrigin, addressDest;
    public String colorSmall = null, colorMed = null, colorLage = null;

    public String addrTypeOrigin = null, addrTypeDest = null;
    public String cityOrigin = null, cityDest = null;
    public String bagTypeSmall = null, bagTypeMed = null, bagTypeLarge = null;
    public String stateOrigin = null, stateDest = null;

    Spinner AddressTypeOrigin, AddressTypeDest, CityOrigin, CityDest, BagTypeSmall, BagTypeMed, BagTypeLarge;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_details);

        String[] AddressTypes = {"Residential", "Business", "Golf Course", "School", "Hotel"};
        String[] Citites = {"Delhi", "Mumbai", "Chennai", "Bangalore"};
        String[] BagType = {"Roller", "Duffle", "Upright", "Other"};

        ArrayAdapter<String> addressAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, AddressTypes);

        AddressTypeOrigin = (Spinner)findViewById(R.id.address_type_origin);
        AddressTypeOrigin.setAdapter(addressAdapter);
        AddressTypeOrigin.setOnItemSelectedListener(this);

        AddressTypeDest = (Spinner)findViewById(R.id.address_type_dest);
        AddressTypeDest.setAdapter(addressAdapter);
        AddressTypeDest.setOnItemSelectedListener(this);

        ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Citites);

        CityOrigin = (Spinner)findViewById(R.id.city_origin);
        CityOrigin.setAdapter(cityAdapter);
        CityOrigin.setOnItemSelectedListener(this);

        CityDest = (Spinner)findViewById(R.id.city_dest);
        CityDest.setAdapter(cityAdapter);
        CityDest.setOnItemSelectedListener(this);

        ArrayAdapter<String> bagTypeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, BagType);

        BagTypeSmall = (Spinner)findViewById(R.id.type_small);
        BagTypeSmall.setAdapter(bagTypeAdapter);
        BagTypeSmall.setOnItemSelectedListener(this);

        BagTypeMed = (Spinner)findViewById(R.id.type_medium);
        BagTypeMed.setAdapter(bagTypeAdapter);
        BagTypeMed.setOnItemSelectedListener(this);

        BagTypeLarge = (Spinner)findViewById(R.id.type_large);
        BagTypeLarge.setAdapter(bagTypeAdapter);
        BagTypeLarge.setOnItemSelectedListener(this);

        ContactOrigin = (EditText)findViewById(R.id.contact_origin);
        AddressOrigin = (EditText)findViewById(R.id.address_origin);
        PinCodeOrigin = (EditText)findViewById(R.id.pin_code_origin);
        StateOrigin = (TextView)findViewById(R.id.state_origin);

        ContactDest = (EditText)findViewById(R.id.contact_dest);
        AddressDest = (EditText)findViewById(R.id.address_dest);
        PinCodeDest = (EditText)findViewById(R.id.pin_code_dest);
        StateDest = (TextView)findViewById(R.id.state_dest);

        ColorSmallBag = (EditText)findViewById(R.id.color_small);
        ColorMedBag = (EditText)findViewById(R.id.color_medium);
        ColorLargeBag = (EditText)findViewById(R.id.color_large);

        btnSubmit = (Button)findViewById(R.id.submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    contactOrigin = ContactOrigin.getText().toString();
                }
                catch(NullPointerException e)
                {
                    Toast.makeText(getApplicationContext(),"Contact in Origin Details not entered",Toast.LENGTH_LONG).show();
                }

                try {
                    contactDest = ContactDest.getText().toString();
                }
                catch(NullPointerException e){
                    Toast.makeText(getApplicationContext(),"Contact in Destination Details not entered",Toast.LENGTH_LONG).show();
                }

                try {
                    pinOrigin = PinCodeOrigin.getText().toString();
                }
                catch(NullPointerException e){
                    Toast.makeText(getApplicationContext(),"Pin Code in Origin Details not entered",Toast.LENGTH_LONG).show();
                }

                try {
                    pinDest = PinCodeDest.getText().toString();
                }
                catch(NullPointerException e){
                    Toast.makeText(getApplicationContext(),"Pin Code in Destination Details not entered",Toast.LENGTH_LONG).show();
                }

                try {
                    addressOrigin = AddressOrigin.getText().toString();
                }
                catch(NullPointerException e){
                    Toast.makeText(getApplicationContext(),"Address in Origin Details not entered",Toast.LENGTH_LONG).show();
                }

                try {
                    addressDest = AddressDest.getText().toString();
                }
                catch(NullPointerException e){
                    Toast.makeText(getApplicationContext(),"Address in Destination Details not entered",Toast.LENGTH_LONG).show();
                }

                colorSmall = ColorSmallBag.getText().toString();
                colorMed = ColorMedBag.getText().toString();
                colorLage = ColorLargeBag.getText().toString();

                addrTypeOrigin = AddressTypeOrigin.getSelectedItem().toString();
                addrTypeDest = AddressTypeDest.getSelectedItem().toString();

                bagTypeSmall = BagTypeSmall.getSelectedItem().toString();
                bagTypeMed = BagTypeMed.getSelectedItem().toString();
                bagTypeLarge = BagTypeLarge.getSelectedItem().toString();

                cityOrigin = CityOrigin.getSelectedItem().toString();
                cityDest = CityDest.getSelectedItem().toString();

                switch(cityOrigin){
                    case "Delhi":
                        stateOrigin = "Delhi";
                        break;
                    case "Mumbai":
                        stateOrigin = "Maharashtra";
                        break;
                    case "Chennai":
                        stateOrigin = "Tamil Nadu";
                        break;
                    case "Bangalore":
                        stateOrigin = "Karnataka";
                        break;
                }
                StateOrigin.setText(stateOrigin);

                switch(cityDest){
                    case "Delhi":
                        stateDest = "Delhi";
                        break;
                    case "Mumbai":
                        stateDest = "Maharashtra";
                        break;
                    case "Chennai":
                        stateDest = "Tamil Nadu";
                        break;
                    case "Bangalore":
                        stateDest = "Karnataka";
                        break;
                }
                StateDest.setText(stateDest);


            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String item = null;
        switch (parent.getId()){
            case R.id.address_type_origin:
                item = parent.getItemAtPosition(position).toString();
                break;
            case R.id.address_type_dest:
                item = parent.getItemAtPosition(position).toString();
                break;
            case R.id.city_origin:
                item = parent.getItemAtPosition(position).toString();
                break;
            case R.id.city_dest:
                item = parent.getItemAtPosition(position).toString();
                break;
            case R.id.type_small:
                item = parent.getItemAtPosition(position).toString();
                break;
            case R.id.type_medium:
                item = parent.getItemAtPosition(position).toString();
                break;
            case R.id.type_large:
                item = parent.getItemAtPosition(position).toString();
                break;
        }
        // Showing selected spinner item
        //Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
