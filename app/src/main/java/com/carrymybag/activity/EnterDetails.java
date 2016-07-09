package com.carrymybag.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import com.carrymybag.R;
import com.carrymybag.app.AppConfig;
import com.carrymybag.app.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EnterDetails extends AppCompatActivity implements OnItemSelectedListener {

    public EditText ContactOrigin, Address1Origin, Address2Origin, PinCodeOrigin;
    public EditText ContactDest, Address1Dest, Address2Dest, PinCodeDest;
    public TextView StateOrigin, StateDest;

    public EditText ContactOrigin2, Address1Origin2, Address2Origin2, PinCodeOrigin2;
    public EditText ContactDest2, Address1Dest2, Address2Dest2, PinCodeDest2;
    public TextView StateOrigin2, StateDest2;

    public Button btnSubmit;

    public String contactOrigin, contactDest, pinOrigin, pinDest, address1Origin, address1Dest, address2Origin, address2Dest;

    public String addrTypeOrigin = null, addrTypeDest = null;
    public String cityOrigin = null, cityDest = null;
    public String stateOrigin = null, stateDest = null;

    public String contactOrigin2, contactDest2, pinOrigin2, pinDest2, address1Origin2, address2Origin2, address1Dest2, address2Dest2;

    public String addrTypeOrigin2 = null, addrTypeDest2 = null;
    public String cityOrigin2 = null, cityDest2 = null;
    public String stateOrigin2 = null, stateDest2 = null;

    Spinner AddressTypeOrigin, AddressTypeDest, CityOrigin, CityDest;
    Spinner AddressTypeOrigin2, AddressTypeDest2, CityOrigin2, CityDest2;

    int NumSmallBagsOrigin = 2, NumMediumBagsOrigin = 0, NumLargeBagsOrigin = 0;        //Change these variable values to alter the
    int NumSmallBagsDest = 0, NumMediumBagsDest = 0, NumLargeBagsDest = 0;              //number of bags dynamically.
    int i;

    LinearLayout LayoutSmall1, LayoutSmall2, LayoutMedium1, LayoutMedium2, LayoutLarge1, LayoutLarge2;

    public TextView SmallBagOrigin[];
    public TextView MediumBagOrigin[];
    public TextView LargeBagOrigin[];
    public TextView SmallBagDest[];
    public TextView MediumBagDest[];
    public TextView LargeBagDest[];

    public EditText ColorSmallBagOrigin[];
    public EditText ColorMediumBagOrigin[];
    public EditText ColorLargeBagOrigin[];
    public EditText ColorSmallBagDest[];
    public EditText ColorMediumBagDest[];
    public EditText ColorLargeBagDest[];

    public Spinner TypeSmallBagOrigin[];
    public Spinner TypeMediumBagOrigin[];
    public Spinner TypeLargeBagOrigin[];
    public Spinner TypeSmallBagDest[];
    public Spinner TypeMediumBagDest[];
    public Spinner TypeLargeBagDest[];

    ArrayList<String> StringSmallBagTypeO;          //Ye saare variables bhi store karane h list aur array wale.
    ArrayList<String> StringMediumBagTypeO;
    ArrayList<String> StringLargeBagTypeO;
    ArrayList<String> StringSmallBagTypeD;
    ArrayList<String> StringMediumBagTypeD;
    ArrayList<String> StringLargeBagTypeD;

    public String colorSmallOrigin[];               //These too are to be stored.
    public String colorMediumOrigin[];
    public String colorLargeOrigin[];
    public String colorSmallDest[];
    public String colorMediumDest[];
    public String colorLargeDest[];

    boolean isTwoWay = true;

    public static ArrayList<String> Citites;
    public static ArrayList<String> BagType;

    public AppController globalVariable;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_enter_details);
        globalVariable = (AppController) getApplicationContext();

        Intent intent = getIntent();
//        isTwoWay = intent.getBooleanExtra("whichWay",false);
        isTwoWay = globalVariable.isTwoWay();

        Citites = new ArrayList<>();
        Citites.add("Delhi");
        BagType = new ArrayList<>();
        BagType.add("Duffle");
        new JSONTask().execute(AppConfig.URL_CityList);
        new JSONTaskBag().execute(AppConfig.URL_BagList);
        if (!isTwoWay) {

            NumSmallBagsOrigin = (int) globalVariable.getQtySmall1();
            NumMediumBagsOrigin = (int) globalVariable.getQtyMed1();
            NumLargeBagsOrigin = (int) globalVariable.getQtyLarge1();
        } else {

            NumSmallBagsOrigin = (int) globalVariable.getQtySmall1();
            NumMediumBagsOrigin = (int) globalVariable.getQtyMed1();
            NumLargeBagsOrigin = (int) globalVariable.getQtyLarge1();
            NumSmallBagsDest = (int) globalVariable.getQtySmall2();
            NumMediumBagsDest = (int) globalVariable.getQtyMed2();
            NumLargeBagsDest = (int) globalVariable.getQtyLarge2();
        }


        String[] AddressTypes = {"Residential", "Business", "Golf Course", "School", "Hotel"};

        SmallBagOrigin = new TextView[NumSmallBagsOrigin];
        MediumBagOrigin = new TextView[NumMediumBagsOrigin];
        LargeBagOrigin = new TextView[NumLargeBagsOrigin];
        SmallBagDest = new TextView[NumSmallBagsDest];
        MediumBagDest = new TextView[NumMediumBagsDest];
        LargeBagDest = new TextView[NumLargeBagsDest];

        ColorSmallBagOrigin = new EditText[NumSmallBagsOrigin];
        ColorMediumBagOrigin = new EditText[NumMediumBagsOrigin];
        ColorLargeBagOrigin = new EditText[NumLargeBagsOrigin];
        ColorSmallBagDest = new EditText[NumSmallBagsDest];
        ColorMediumBagDest = new EditText[NumMediumBagsDest];
        ColorLargeBagDest = new EditText[NumLargeBagsDest];

        TypeSmallBagOrigin = new Spinner[NumSmallBagsOrigin];
        TypeMediumBagOrigin = new Spinner[NumMediumBagsOrigin];
        TypeLargeBagOrigin = new Spinner[NumLargeBagsOrigin];
        TypeSmallBagDest = new Spinner[NumSmallBagsDest];
        TypeMediumBagDest = new Spinner[NumMediumBagsDest];
        TypeLargeBagDest = new Spinner[NumLargeBagsDest];

        StringSmallBagTypeO = new ArrayList<String>();
        StringMediumBagTypeO = new ArrayList<String>();
        StringLargeBagTypeO = new ArrayList<String>();
        StringSmallBagTypeD = new ArrayList<String>();
        StringMediumBagTypeD = new ArrayList<String>();
        StringLargeBagTypeD = new ArrayList<String>();

        colorSmallOrigin = new String[NumSmallBagsOrigin];
        colorMediumOrigin = new String[NumMediumBagsOrigin];
        colorLargeOrigin = new String[NumLargeBagsOrigin];
        colorSmallDest = new String[NumSmallBagsDest];
        colorMediumDest = new String[NumMediumBagsDest];
        colorLargeDest = new String[NumLargeBagsDest];

        for (i = 0; i < NumSmallBagsOrigin; i++) {
            LayoutSmall1 = (LinearLayout) findViewById(R.id.details_small_origin);
            SmallBagOrigin[i] = new TextView(this);
            SmallBagOrigin[i].setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            SmallBagOrigin[i].setText("Small Bag" + (i + 1));
            SmallBagOrigin[i].setTextSize(20);
            SmallBagOrigin[i].setTypeface(null, Typeface.BOLD);
            LayoutSmall1.addView(SmallBagOrigin[i]);

            ColorSmallBagOrigin[i] = new EditText(this);
            ColorSmallBagOrigin[i].setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            ColorSmallBagOrigin[i].setHint("Bag Color");
            ColorSmallBagOrigin[i].setTextSize(20);
            LayoutSmall1.addView(ColorSmallBagOrigin[i]);

            TypeSmallBagOrigin[i] = new Spinner(this);
            TypeSmallBagOrigin[i].setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, BagType));
            TypeSmallBagOrigin[i].setOnItemSelectedListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String item = parent.getItemAtPosition(position).toString();
                    Toast.makeText(getApplicationContext(), item, Toast.LENGTH_LONG).show();
                    StringSmallBagTypeO.add(item);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            LayoutSmall1.addView(TypeSmallBagOrigin[i]);
        }

        for (i = 0; i < NumMediumBagsOrigin; i++) {
            LayoutMedium1 = (LinearLayout) findViewById(R.id.details_medium_origin);
            MediumBagOrigin[i] = new TextView(this);
            MediumBagOrigin[i].setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            MediumBagOrigin[i].setText("Medium Bag" + (i + 1));
            MediumBagOrigin[i].setTextSize(20);
            MediumBagOrigin[i].setTypeface(null, Typeface.BOLD);
            LayoutMedium1.addView(MediumBagOrigin[i]);

            ColorMediumBagOrigin[i] = new EditText(this);
            ColorMediumBagOrigin[i].setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            ColorMediumBagOrigin[i].setHint("Bag Color");
            ColorMediumBagOrigin[i].setTextSize(20);
            LayoutMedium1.addView(ColorMediumBagOrigin[i]);

            TypeMediumBagOrigin[i] = new Spinner(this);
            TypeMediumBagOrigin[i].setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, BagType));
            TypeMediumBagOrigin[i].setOnItemSelectedListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String item = parent.getItemAtPosition(position).toString();
                    StringMediumBagTypeO.add(item);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            LayoutMedium1.addView(TypeMediumBagOrigin[i]);
        }

        for (i = 0; i < NumLargeBagsOrigin; i++) {
            LayoutLarge1 = (LinearLayout) findViewById(R.id.details_large_origin);
            LargeBagOrigin[i] = new TextView(this);
            LargeBagOrigin[i].setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            LargeBagOrigin[i].setText("Large Bag" + (i + 1));
            LargeBagOrigin[i].setTextSize(20);
            LargeBagOrigin[i].setTypeface(null, Typeface.BOLD);
            LayoutLarge1.addView(LargeBagOrigin[i]);

            ColorLargeBagOrigin[i] = new EditText(this);
            ColorLargeBagOrigin[i].setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            ColorLargeBagOrigin[i].setHint("Bag Color");
            ColorLargeBagOrigin[i].setTextSize(20);
            LayoutLarge1.addView(ColorLargeBagOrigin[i]);

            TypeLargeBagOrigin[i] = new Spinner(this);
            TypeLargeBagOrigin[i].setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, BagType));
            TypeLargeBagOrigin[i].setOnItemSelectedListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String item = parent.getItemAtPosition(position).toString();
                    StringLargeBagTypeO.add(item);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            LayoutLarge1.addView(TypeLargeBagOrigin[i]);
        }

        ArrayAdapter<String> addressAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, AddressTypes);

        AddressTypeOrigin = (Spinner) findViewById(R.id.address_type_origin);
        AddressTypeOrigin.setAdapter(addressAdapter);
        AddressTypeOrigin.setOnItemSelectedListener(this);

        AddressTypeDest = (Spinner) findViewById(R.id.address_type_dest);
        AddressTypeDest.setAdapter(addressAdapter);
        AddressTypeDest.setOnItemSelectedListener(this);

        ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Citites);

        CityOrigin = (Spinner) findViewById(R.id.city_origin);
        CityOrigin.setAdapter(cityAdapter);
        CityOrigin.setOnItemSelectedListener(this);

        CityDest = (Spinner) findViewById(R.id.city_dest);
        CityDest.setAdapter(cityAdapter);
        CityDest.setOnItemSelectedListener(this);

        ContactOrigin = (EditText) findViewById(R.id.contact_origin);
        Address1Origin = (EditText) findViewById(R.id.address1_origin);
        Address2Origin = (EditText) findViewById(R.id.address2_origin);
        PinCodeOrigin = (EditText) findViewById(R.id.pin_code_origin);
        StateOrigin = (TextView) findViewById(R.id.state_origin);

        ContactDest = (EditText) findViewById(R.id.contact_dest);
        Address1Dest = (EditText) findViewById(R.id.address1_dest);
        Address2Dest = (EditText) findViewById(R.id.address2_dest);
        PinCodeDest = (EditText) findViewById(R.id.pin_code_dest);
        StateDest = (TextView) findViewById(R.id.state_dest);

        if (isTwoWay == false) {
            LinearLayout LayoutLeg2 = (LinearLayout) findViewById(R.id.layout_leg2);
            LayoutLeg2.setVisibility(View.GONE);
        } else {
            for (i = 0; i < NumSmallBagsDest; i++) {
                LayoutSmall2 = (LinearLayout) findViewById(R.id.details_small_dest);
                SmallBagDest[i] = new TextView(this);
                SmallBagDest[i].setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
                SmallBagDest[i].setText("Small Bag" + (i + 1));
                SmallBagDest[i].setTextSize(20);
                SmallBagDest[i].setTypeface(null, Typeface.BOLD);
                LayoutSmall2.addView(SmallBagDest[i]);

                ColorSmallBagDest[i] = new EditText(this);
                ColorSmallBagDest[i].setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
                ColorSmallBagDest[i].setHint("Bag Color");
                ColorSmallBagDest[i].setTextSize(20);
                LayoutSmall2.addView(ColorSmallBagDest[i]);

                TypeSmallBagDest[i] = new Spinner(this);
                TypeSmallBagDest[i].setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, BagType));
                TypeSmallBagDest[i].setOnItemSelectedListener(new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String item = parent.getItemAtPosition(position).toString();
                        StringSmallBagTypeD.add(item);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                LayoutSmall2.addView(TypeSmallBagDest[i]);
            }

            for (i = 0; i < NumMediumBagsDest; i++) {
                LayoutMedium2 = (LinearLayout) findViewById(R.id.details_medium_dest);
                MediumBagDest[i] = new TextView(this);
                MediumBagDest[i].setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
                MediumBagDest[i].setText("Medium Bag" + (i + 1));
                MediumBagDest[i].setTextSize(20);
                MediumBagDest[i].setTypeface(null, Typeface.BOLD);
                LayoutMedium2.addView(MediumBagDest[i]);

                ColorMediumBagDest[i] = new EditText(this);
                ColorMediumBagDest[i].setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
                ColorMediumBagDest[i].setHint("Bag Color");
                ColorMediumBagDest[i].setTextSize(20);
                LayoutMedium2.addView(ColorMediumBagDest[i]);

                TypeMediumBagDest[i] = new Spinner(this);
                TypeMediumBagDest[i].setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, BagType));
                TypeMediumBagDest[i].setOnItemSelectedListener(new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String item = parent.getItemAtPosition(position).toString();
                        StringMediumBagTypeD.add(item);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                LayoutMedium2.addView(TypeMediumBagDest[i]);
            }

            for (i = 0; i < NumLargeBagsDest; i++) {
                LayoutLarge2 = (LinearLayout) findViewById(R.id.details_large_dest);
                LargeBagDest[i] = new TextView(this);
                LargeBagDest[i].setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
                LargeBagDest[i].setText("Large Bag" + (i + 1));
                LargeBagDest[i].setTextSize(20);
                LargeBagDest[i].setTypeface(null, Typeface.BOLD);
                LayoutLarge2.addView(LargeBagDest[i]);

                ColorLargeBagDest[i] = new EditText(this);
                ColorLargeBagDest[i].setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
                ColorLargeBagDest[i].setHint("Bag Color");
                ColorLargeBagDest[i].setTextSize(20);
                LayoutLarge2.addView(ColorLargeBagDest[i]);

                TypeLargeBagDest[i] = new Spinner(this);
                TypeLargeBagDest[i].setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, BagType));
                TypeLargeBagDest[i].setOnItemSelectedListener(new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String item = parent.getItemAtPosition(position).toString();
                        StringLargeBagTypeD.add(item);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                LayoutLarge2.addView(TypeLargeBagDest[i]);
            }

            AddressTypeOrigin2 = (Spinner) findViewById(R.id.address_type_origin2);
            AddressTypeOrigin2.setAdapter(addressAdapter);
            AddressTypeOrigin2.setOnItemSelectedListener(this);

            AddressTypeDest2 = (Spinner) findViewById(R.id.address_type_dest2);
            AddressTypeDest2.setAdapter(addressAdapter);
            AddressTypeDest2.setOnItemSelectedListener(this);

            CityOrigin2 = (Spinner) findViewById(R.id.city_origin2);
            CityOrigin2.setAdapter(cityAdapter);
            CityOrigin2.setOnItemSelectedListener(this);

            CityDest2 = (Spinner) findViewById(R.id.city_dest2);
            CityDest2.setAdapter(cityAdapter);
            CityDest2.setOnItemSelectedListener(this);

            ContactOrigin2 = (EditText) findViewById(R.id.contact_origin2);
            Address1Origin2 = (EditText) findViewById(R.id.address1_origin2);
            Address2Origin2 = (EditText) findViewById(R.id.address2_origin2);
            PinCodeOrigin2 = (EditText) findViewById(R.id.pin_code_origin2);
            StateOrigin2 = (TextView) findViewById(R.id.state_origin2);

            ContactDest2 = (EditText) findViewById(R.id.contact_dest2);
            Address1Dest2 = (EditText) findViewById(R.id.address1_dest2);
            Address2Dest2 = (EditText) findViewById(R.id.address2_dest2);
            PinCodeDest2 = (EditText) findViewById(R.id.pin_code_dest2);
            StateDest2 = (TextView) findViewById(R.id.state_dest2);
        }

        for (i = 0; i < NumSmallBagsOrigin; i++) {
            colorSmallOrigin[i] = null;
        }

        for (i = 0; i < NumMediumBagsOrigin; i++) {
            colorMediumOrigin[i] = null;
        }

        for (i = 0; i < NumLargeBagsOrigin; i++) {
            colorLargeOrigin[i] = null;
        }

        for (i = 0; i < NumSmallBagsDest; i++) {
            colorSmallDest[i] = null;
        }

        for (i = 0; i < NumMediumBagsDest; i++) {
            colorMediumDest[i] = null;
        }

        for (i = 0; i < NumLargeBagsDest; i++) {
            colorLargeDest[i] = null;
        }

        btnSubmit = (Button) findViewById(R.id.submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                contactOrigin = ContactOrigin.getText().toString();

                if (TextUtils.isEmpty(contactOrigin)) {
                    ContactOrigin.setError("Contact can not be empty");
                    return;
                }

                globalVariable.setContactOrigin(contactOrigin);

                address1Origin = Address1Origin.getText().toString();

                if (TextUtils.isEmpty(address1Origin)) {
                    Address1Origin.setError("Address can not be empty");
                    return;
                }
                globalVariable.setAddress1Origin(address1Origin);

                address2Origin = Address2Origin.getText().toString();

                if (TextUtils.isEmpty(address2Origin)) {
                    Address2Origin.setError("Address can not be empty");
                    return;
                }
                globalVariable.setAddress2Origin(address2Origin);

                pinOrigin = PinCodeOrigin.getText().toString();

                if (TextUtils.isEmpty(pinOrigin)) {
                    PinCodeOrigin.setError("Pin code can not be empty");
                    return;
                }

                globalVariable.setPinOrigin(pinOrigin);

                contactDest = ContactDest.getText().toString();

                if (TextUtils.isEmpty(contactDest)) {
                    ContactDest.setError("Contact can not be empty");
                    return;
                }

                globalVariable.setContactDest(contactDest);

                address1Dest = Address1Dest.getText().toString();

                if (TextUtils.isEmpty(address1Dest)) {
                    Address1Dest.setError("Address can not be empty");
                    return;
                }

                globalVariable.setAddress1Dest(address1Dest);

                address2Dest = Address2Dest.getText().toString();

                if (TextUtils.isEmpty(address1Dest)) {
                    Address1Dest.setError("Address can not be empty");
                    return;
                }

                globalVariable.setAddress2Dest(address2Dest);

                pinDest = PinCodeDest.getText().toString();

                if (TextUtils.isEmpty(pinDest)) {
                    PinCodeDest.setError("Pin code can not be empty");
                    return;
                }

                globalVariable.setPinDest(pinDest);

                for (i = 0; i < NumSmallBagsOrigin; i++) {
                    colorSmallOrigin[i] = ColorSmallBagOrigin[i].getText().toString();
                    if (TextUtils.isEmpty(colorSmallOrigin[i])) {
                        ColorSmallBagOrigin[i].setError("Bag color can not be empty");
                        return;
                    }
                }
                globalVariable.setColorSmallOrigin(colorSmallOrigin);

                for (i = 0; i < NumMediumBagsOrigin; i++) {
                    colorMediumOrigin[i] = ColorMediumBagOrigin[i].getText().toString();
                    if (TextUtils.isEmpty(colorMediumOrigin[i])) {
                        ColorMediumBagOrigin[i].setError("Bag color can not be empty");
                        return;
                    }
                }
                globalVariable.setColorMediumOrigin(colorMediumOrigin);

                for (i = 0; i < NumLargeBagsOrigin; i++) {
                    colorLargeOrigin[i] = ColorLargeBagOrigin[i].getText().toString();
                    if (TextUtils.isEmpty(colorLargeOrigin[i])) {
                        ColorLargeBagOrigin[i].setError("Bag color can not be empty");
                        return;
                    }
                }
                globalVariable.setColorLargeOrigin(colorLargeOrigin);
                addrTypeOrigin = AddressTypeOrigin.getSelectedItem().toString();
                addrTypeDest = AddressTypeDest.getSelectedItem().toString();

                cityOrigin = CityOrigin.getSelectedItem().toString();
                cityDest = CityDest.getSelectedItem().toString();
                globalVariable.setAddrTypeOrigin(addrTypeOrigin);
                globalVariable.setAddrTypeDest(addrTypeDest);
                globalVariable.setCityOrigin(cityOrigin);
                globalVariable.setCityDest(cityDest);

                switch (cityOrigin) {
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

                switch (cityDest) {
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

                if (isTwoWay == true) {
                    contactOrigin2 = ContactOrigin2.getText().toString();

                    if (TextUtils.isEmpty(contactOrigin2)) {
                        ContactOrigin2.setError("Contact can not be empty");
                        return;
                    }

                    globalVariable.setContactOrigin2(contactOrigin2);

                    address1Origin2 = Address1Origin2.getText().toString();

                    if (TextUtils.isEmpty(address1Origin2)) {
                        Address1Origin2.setError("Address can not be empty");
                        return;
                    }
                    globalVariable.setAddressOrigin2(address1Origin2);

                    address2Origin2 = Address2Origin2.getText().toString();

                    if (TextUtils.isEmpty(address2Origin2)) {
                        Address2Origin2.setError("Address can not be empty");
                        return;
                    }
                    globalVariable.setAddress2Origin2(address2Origin2);

                    pinOrigin2 = PinCodeOrigin2.getText().toString();

                    if (TextUtils.isEmpty(pinOrigin2)) {
                        PinCodeOrigin2.setError("Pin code can not be empty");
                        return;
                    }

                    globalVariable.setPinOrigin2(pinOrigin2);

                    contactDest2 = ContactDest2.getText().toString();

                    if (TextUtils.isEmpty(contactDest2)) {
                        ContactDest2.setError("Contact can not be empty");
                        return;
                    }

                    globalVariable.setContactDest2(contactDest2);

                    address1Dest2 = Address1Dest2.getText().toString();

                    if (TextUtils.isEmpty(address1Dest2)) {
                        Address1Dest2.setError("Address can not be empty");
                        return;
                    }

                    globalVariable.setAddressDest2(address1Dest2);

                    address2Dest2 = Address2Dest2.getText().toString();

                    if (TextUtils.isEmpty(address2Dest2)) {
                        Address2Dest2.setError("Address can not be empty");
                        return;
                    }

                    globalVariable.setAddress2Dest2(address2Dest2);

                    pinDest2 = PinCodeDest2.getText().toString();

                    if (TextUtils.isEmpty(pinDest2)) {
                        PinCodeDest2.setError("Pin code can not be empty");
                        return;
                    }

                    globalVariable.setPinDest2(pinDest2);

                    for (i = 0; i < NumSmallBagsDest; i++) {
                        colorSmallDest[i] = ColorSmallBagDest[i].getText().toString();
                        if (TextUtils.isEmpty(colorSmallDest[i])) {
                            ColorSmallBagDest[i].setError("Bag color can not be empty");
                            return;
                        }
                    }
                    globalVariable.setColorSmallDest(colorSmallDest);

                    for (i = 0; i < NumMediumBagsDest; i++) {
                        colorMediumDest[i] = ColorMediumBagDest[i].getText().toString();
                        if (TextUtils.isEmpty(colorMediumDest[i])) {
                            ColorMediumBagDest[i].setError("Bag color can not be empty");
                            return;
                        }
                        globalVariable.setColorMediumDest(colorMediumDest);

                        for (i = 0; i < NumLargeBagsDest; i++) {
                            colorLargeDest[i] = ColorLargeBagDest[i].getText().toString();
                            if (TextUtils.isEmpty(colorLargeDest[i])) {
                                ColorLargeBagDest[i].setError("Bag color can not be empty");
                                return;
                            }
                        }
                        globalVariable.setColorLargeDest(colorLargeDest);

                        addrTypeOrigin2 = AddressTypeOrigin2.getSelectedItem().toString();
                        addrTypeDest2 = AddressTypeDest2.getSelectedItem().toString();

                        cityOrigin2 = CityOrigin2.getSelectedItem().toString();
                        cityDest2 = CityDest2.getSelectedItem().toString();

                        globalVariable.setAddrTypeOrigin2(addrTypeOrigin2);
                        globalVariable.setAddrTypeDest2(addrTypeDest2);
                        globalVariable.setCityOrigin2(cityOrigin2);
                        globalVariable.setCityDest2(cityDest2);


                        switch (cityOrigin2) {
                            case "Delhi":
                                stateOrigin2 = "Delhi";
                                break;
                            case "Mumbai":
                                stateOrigin2 = "Maharashtra";
                                break;
                            case "Chennai":
                                stateOrigin2 = "Tamil Nadu";
                                break;
                            case "Bangalore":
                                stateOrigin2 = "Karnataka";
                                break;
                        }
                        StateOrigin2.setText(stateOrigin2);

                        switch (cityDest) {
                            case "Delhi":
                                stateDest2 = "Delhi";
                                break;
                            case "Mumbai":
                                stateDest2 = "Maharashtra";
                                break;
                            case "Chennai":
                                stateDest2 = "Tamil Nadu";
                                break;
                            case "Bangalore":
                                stateDest2 = "Karnataka";
                                break;
                        }
                        StateDest2.setText(stateDest2);
                    }
                }
                Intent intent = new Intent(EnterDetails.this, ConfirmPage.class);
                startActivity(intent);
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
                case R.id.address_type_origin2:
                    item = parent.getItemAtPosition(position).toString();
                    break;
                case R.id.address_type_dest2:
                    item = parent.getItemAtPosition(position).toString();
                    break;
                case R.id.city_origin2:
                    item = parent.getItemAtPosition(position).toString();
                    break;
                case R.id.city_dest2:
                    item = parent.getItemAtPosition(position).toString();
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
        class JSONTask extends AsyncTask<String,String,String> {

            @Override
            protected String doInBackground(String... params) {
                HttpURLConnection connection = null;
                BufferedReader reader = null;

                try {
                    URL url = new URL(params[0]);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.connect();
                    InputStream stream = connection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(stream));
                    StringBuffer buffer = new StringBuffer();
                    String line ="";
                    while ((line = reader.readLine()) != null){
                        buffer.append(line);
                    }

                    String finalJson = buffer.toString();

                    JSONObject parentObject = new JSONObject(finalJson);
                    JSONArray parentArray = parentObject.getJSONArray("result");

                    for(int i=0; i<parentArray.length(); i++) {
                        JSONObject finalObject = parentArray.getJSONObject(i);
                        /**
                         * below single line of code from Gson saves you from writing the json parsing yourself which is commented below
                         */
                        Citites.add(finalObject.getString("city_name"));

                    }
                    return null;

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    if(connection != null) {
                        connection.disconnect();
                    }
                    try {
                        if(reader != null) {
                            reader.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return  null;
            }


        }

        class JSONTaskBag extends AsyncTask<String,String,String> {

            @Override
            protected String doInBackground(String... params) {
                HttpURLConnection connection = null;
                BufferedReader reader = null;

                try {
                    URL url = new URL(params[0]);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.connect();
                    InputStream stream = connection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(stream));
                    StringBuffer buffer = new StringBuffer();
                    String line ="";
                    while ((line = reader.readLine()) != null){
                        buffer.append(line);
                    }

                    String finalJson = buffer.toString();

                    JSONObject parentObject = new JSONObject(finalJson);
                    JSONArray parentArray = parentObject.getJSONArray("result");

                    for(int i=0; i<parentArray.length(); i++) {
                        JSONObject finalObject = parentArray.getJSONObject(i);
                        /**
                         * below single line of code from Gson saves you from writing the json parsing yourself which is commented below
                         */
                        BagType.add(finalObject.getString("bag_type"));

                    }
                    return null;

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    if(connection != null) {
                        connection.disconnect();
                    }
                    try {
                        if(reader != null) {
                            reader.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return  null;
            }


        }

    }

