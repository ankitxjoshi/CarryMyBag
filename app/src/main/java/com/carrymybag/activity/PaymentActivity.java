package com.carrymybag.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.View;
import android.os.Bundle;
import android.widget.Toast;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.carrymybag.R;
import com.carrymybag.app.AppConfig;
import com.carrymybag.app.AppController;
import com.crashlytics.android.Crashlytics;
import com.razorpay.Checkout;
import io.fabric.sdk.android.Fabric;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PaymentActivity extends Activity
{

    boolean flagS;
    boolean flagM;
    boolean flagL;


    //Register Details....................................................................................

    // 1.For luggage.php

    public static final String KEY_USERID = "user_id";
    public static final String KEY_BAGSIZE = "bag_size";
    public static final String KEY_BAGTYPE = "bag_type";
    public static final String KEY_BAGCOLOR = "bag_color";
    public static final String KEY_PRICEID = "price_id";

    //2.order_details.php
    public static final String KEY_ORDERID = "order_id";
    public static final String KEY_TOTPRICE = "total_price";
    public static final String KEY_PICDATE = "pick_up_date";
    public static final String KEY_DELDATE = "delivery_date";
    public static final String KEY_PICKADD = "pickup_add";
    public static final String KEY_DELADD = "delivery_add";

    //3.user_details.php

    public static final String KEY_NAME = "name";
    public static final String KEY_PHONE = "phone_no";

    //....................................................................................................
    public boolean luggageflag;
    public boolean userflag;
    public boolean orderflag;
    public boolean isQuerySucc;

    public AppController globalVariable;
    RequestQueue requestQueue;

    ArrayList<String> smallBagTypeO;
    ArrayList<String> mediumBagTypeO;
    ArrayList<String> largeBagTypeO;


    public PaymentActivity(){}

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_payment);
        startPayment();
    }

    public void startPayment(){

        globalVariable = (AppController) getApplicationContext();
        /**
         * Replace with your public key
         */
        final String public_key =getResources().getString(R.string.razorpay_id);

        /**
         * You need to pass current activity in order to let razorpay create CheckoutActivity
         */
        final Activity activity = this;

        final Checkout co = new Checkout();
        co.setPublicKey(public_key);
        co.setImage(R.drawable.logo);

        try{
            JSONObject options = new JSONObject("{" +
                    "description: 'Demoing Charges'," +
                    "image: 'https://rzp-mobile.s3.amazonaws.com/images/rzp.png'," +
                    "currency: 'INR'}"
            );

            options.put("amount", "5000");
            options.put("name", "CarryMyBag");
            options.put("prefill", new JSONObject("{email: 'ajankit2304@gmail.com', contact: '9876543210'}"));
            options.put("theme",new JSONObject("{color: '#114148'}"));

            co.open(activity, options);

        } catch(Exception e){
            Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    /**
     * The name of the function has to be
     *   onPaymentSuccess
     * Wrap your code in try catch, as shown, to ensure that this method runs correctly
     */
    public void onPaymentSuccess(String razorpayPaymentID){
        try {
            Toast.makeText(this, "Payment Successful: " + razorpayPaymentID, Toast.LENGTH_SHORT).show();
            final ProgressDialog dialog = new ProgressDialog(PaymentActivity.this);
            dialog.setMessage("Transaction in progress do not close the screen");
            globalVariable.setRazorId(razorpayPaymentID);
            dialog.show();
            if(globalVariable.isTwoWay())
            {
                register1(new PaymentActivity.VolleyCallback() {
                    @Override
                    public void onSuccess(boolean result) {
                        if(result)
                        {
                            register2(new PaymentActivity.VolleyCallback() {
                                @Override
                                public void onSuccess(boolean result) {
                                    if(result)
                                    {
                                        dialog.dismiss();
                                        Intent intent = new Intent(PaymentActivity.this,TransactionSuccessful.class);
                                        startActivity(intent);
                                    }
                                    else
                                    {
                                        Toast.makeText(PaymentActivity.this,"Server error please try later",Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(PaymentActivity.this,TransactionFailed.class);
                                        startActivity(intent);
                                    }
                                }
                            });
                        }
                        else
                        {
                            Toast.makeText(PaymentActivity.this,"Server error please try later",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(PaymentActivity.this,TransactionFailed.class);
                            startActivity(intent);
                        }
                    }
                });

            }
            else
            {
                register1(new PaymentActivity.VolleyCallback() {
                    @Override
                    public void onSuccess(boolean result) {
                        if(result)
                        {
                            dialog.dismiss();
                            Intent intent = new Intent(PaymentActivity.this,TransactionSuccessful.class);
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(PaymentActivity.this,"Server error please try later",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(PaymentActivity.this,TransactionFailed.class);
                            startActivity(intent);
                        }
                    }
                });
            }

        }
        catch (Exception e){
            Log.e("com.merchant", e.getMessage(), e);
        }
    }

    /**
     * The name of the function has to be
     *   onPaymentError
     * Wrap your code in try catch, as shown, to ensure that this method runs correctly
     */
    public void onPaymentError(int code, String response){
        try {
            Toast.makeText(this, "Payment failed: " + Integer.toString(code) + " " + response, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,TransactionFailed.class);
            startActivity(intent);
        }
        catch (Exception e){
            Log.e("com.merchant", e.getMessage(), e);
        }
    }

    private void register1(final VolleyCallback callback)
    {
        isQuerySucc = true;
        registerOrder(new VolleyCallback(){
            @Override
            public void onSuccess(boolean result) {
                isQuerySucc&=result;
                if(!result)
                {
                    callback.onSuccess(result);
                }
                else
                {
                    for(int i=0;i<(int)globalVariable.getQtySmall1();i++)
                    {
                        registerLuggageSmall(new VolleyCallback(){
                            @Override
                            public void onSuccess(boolean result) {
                                isQuerySucc&=result;
                                if(!result)
                                {
                                    callback.onSuccess(result);
                                }
                                else if(!flagM)
                                {
                                    flagM = true;
                                    for(int i=0;i<(int)globalVariable.getQtyMed1();i++)
                                    {
                                        registerLuggageMed(new VolleyCallback(){
                                            @Override
                                            public void onSuccess(boolean result) {
                                                isQuerySucc&=result;
                                                if(!result)
                                                {
                                                    callback.onSuccess(result);
                                                }
                                                else if(!flagL)
                                                {
                                                    flagL = true;
                                                    for(int i=0;i<(int)globalVariable.getQtyLarge1();i++)
                                                    {
                                                        registerLuggageLarge(new VolleyCallback(){
                                                            @Override
                                                            public void onSuccess(boolean result) {
                                                                isQuerySucc&=result;
                                                                if(!result)
                                                                {
                                                                    callback.onSuccess(result);
                                                                }
                                                            }
                                                        },i);
                                                    }
                                                }

                                            }
                                        },i);
                                    }
                                }

                            }
                        },i);
                    }

                    registerUser(new VolleyCallback(){
                        @Override
                        public void onSuccess(boolean result) {
                            isQuerySucc&=result;
                            callback.onSuccess(isQuerySucc);
                        }
                    });
                }

            }
        });
    }
    private void register2(final VolleyCallback callback)
    {
        isQuerySucc = true;
        flagM = false;
        flagL = false;
        flagS = false;
        registerOrder2(new VolleyCallback(){
            @Override
            public void onSuccess(boolean result) {
                isQuerySucc&=result;
                if(!result)
                {
                    callback.onSuccess(result);
                }
                else
                {
                    for(int i=0;i<(int)globalVariable.getQtySmall2();i++)
                    {
                        registerLuggageSmall2(new VolleyCallback(){
                            @Override
                            public void onSuccess(boolean result) {
                                isQuerySucc&=result;
                                if(!result)
                                {
                                    callback.onSuccess(result);
                                }
                                else if(!flagM)
                                {
                                    flagM = true;
                                    for(int i=0;i<(int)globalVariable.getQtyMed2();i++)
                                    {
                                        registerLuggageMed2(new VolleyCallback(){
                                            @Override
                                            public void onSuccess(boolean result) {
                                                isQuerySucc&=result;
                                                if(!result)
                                                {
                                                    callback.onSuccess(result);
                                                }
                                                else if(!flagL)
                                                {
                                                    flagL = true;
                                                    for(int i=0;i<(int)globalVariable.getQtyLarge2();i++)
                                                    {
                                                        registerLuggageLarge2(new VolleyCallback(){
                                                            @Override
                                                            public void onSuccess(boolean result) {
                                                                isQuerySucc&=result;
                                                                if(!result)
                                                                {
                                                                    callback.onSuccess(result);
                                                                }
                                                            }
                                                        },i);
                                                    }
                                                }

                                            }
                                        },i);
                                    }
                                }

                            }
                        },i);
                    }
                    registerUser(new VolleyCallback(){
                        @Override
                        public void onSuccess(boolean result) {
                            isQuerySucc&=result;
                            callback.onSuccess(isQuerySucc);
                        }
                    });

                }

            }
        });
    }
    private void registerLuggageSmall(final VolleyCallback callback, int i) {

        requestQueue = Volley.newRequestQueue(PaymentActivity.this);
        final String order_id = globalVariable.getRazorId();
        final String bagSize = "Small";
        final String bagType = globalVariable.getStringSmallBagTypeO(i);
        final String bagColor = globalVariable.getColorSmallOrigin(i);
        final String priceID = "1";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_StoreLuggage,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(PaymentActivity.this,response,Toast.LENGTH_LONG).show();
                        luggageflag = true;
                        JSONObject jObj = null;
                        boolean error = false;
                        try {
                            jObj = new JSONObject(response);
                            error = jObj.getBoolean("success");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        callback.onSuccess(error);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PaymentActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                        luggageflag = false;
                        callback.onSuccess(false);
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(KEY_ORDERID,order_id);
                params.put(KEY_USERID,globalVariable.getUserEmail());
                params.put(KEY_BAGSIZE,bagSize);
                params.put(KEY_BAGTYPE,bagType);
                params.put(KEY_BAGCOLOR,bagColor);
                params.put(KEY_PRICEID,priceID);

                return params;
            }

        };
        requestQueue.add(stringRequest);

    }
    private void registerLuggageSmall2(final VolleyCallback callback, int i) {

        requestQueue = Volley.newRequestQueue(PaymentActivity.this);
        final String order_id = globalVariable.getRazorId();
        final String bagSize = "Small";
        final String bagType = globalVariable.getStringSmallBagTypeD(i);
        final String bagColor = globalVariable.getColorSmallDest(i);
        final String priceID = "1";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_StoreLuggage,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(PaymentActivity.this,response,Toast.LENGTH_LONG).show();
                        luggageflag = true;
                        JSONObject jObj = null;
                        boolean error = false;
                        try {
                            jObj = new JSONObject(response);
                            error = jObj.getBoolean("success");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        callback.onSuccess(error);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PaymentActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                        luggageflag = false;
                        callback.onSuccess(false);
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(KEY_ORDERID,order_id);
                params.put(KEY_USERID,globalVariable.getUserEmail());
                params.put(KEY_BAGSIZE,bagSize);
                params.put(KEY_BAGTYPE,bagType);
                params.put(KEY_BAGCOLOR,bagColor);
                params.put(KEY_PRICEID,priceID);

                return params;
            }

        };
        requestQueue.add(stringRequest);

    }
    private void registerLuggageMed(final VolleyCallback callback, int i) {

        requestQueue = Volley.newRequestQueue(PaymentActivity.this);
        final String order_id = globalVariable.getRazorId();
        final String bagSize = "Medium";
        final String bagType = globalVariable.getStringMediumBagTypeO(i);
        final String bagColor = globalVariable.getColorMediumOrigin(i);
        final String priceID = "1";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_StoreLuggage,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(PaymentActivity.this,response,Toast.LENGTH_LONG).show();
                        luggageflag = true;
                        JSONObject jObj = null;
                        boolean error = false;
                        try {
                            jObj = new JSONObject(response);
                            error = jObj.getBoolean("success");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        callback.onSuccess(error);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PaymentActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                        luggageflag = false;
                        callback.onSuccess(false);
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(KEY_ORDERID,order_id);
                params.put(KEY_USERID,globalVariable.getUserEmail());
                params.put(KEY_BAGSIZE,bagSize);
                params.put(KEY_BAGTYPE,bagType);
                params.put(KEY_BAGCOLOR,bagColor);
                params.put(KEY_PRICEID,priceID);

                return params;
            }

        };
        requestQueue.add(stringRequest);

    }
    private void registerLuggageMed2(final VolleyCallback callback, int i) {

        requestQueue = Volley.newRequestQueue(PaymentActivity.this);
        final String order_id = globalVariable.getRazorId();
        final String bagSize = "Medium";
        final String bagType = globalVariable.getStringMediumBagTypeD(i);
        final String bagColor = globalVariable.getColorMediumDest(i);
        final String priceID = "1";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_StoreLuggage,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(PaymentActivity.this,response,Toast.LENGTH_LONG).show();
                        luggageflag = true;
                        JSONObject jObj = null;
                        boolean error = false;
                        try {
                            jObj = new JSONObject(response);
                            error = jObj.getBoolean("success");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        callback.onSuccess(error);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PaymentActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                        luggageflag = false;
                        callback.onSuccess(false);
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(KEY_ORDERID,order_id);
                params.put(KEY_USERID,globalVariable.getUserEmail());
                params.put(KEY_BAGSIZE,bagSize);
                params.put(KEY_BAGTYPE,bagType);
                params.put(KEY_BAGCOLOR,bagColor);
                params.put(KEY_PRICEID,priceID);

                return params;
            }

        };
        requestQueue.add(stringRequest);

    }
    private void registerLuggageLarge(final VolleyCallback callback, int i) {

    requestQueue = Volley.newRequestQueue(PaymentActivity.this);
    final String order_id = globalVariable.getRazorId();
    final String bagSize = "Large";
    final String bagType = globalVariable.getStringLargeBagTypeO(i);
    final String bagColor = globalVariable.getColorLargeOrigin(i);
    final String priceID = "1";
    StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_StoreLuggage,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(PaymentActivity.this,response,Toast.LENGTH_LONG).show();
                    luggageflag = true;
                    JSONObject jObj = null;
                    boolean error = false;
                    try {
                        jObj = new JSONObject(response);
                        error = jObj.getBoolean("success");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    callback.onSuccess(error);
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(PaymentActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                    luggageflag = false;
                    callback.onSuccess(false);
                }
            }){
        @Override
        protected Map<String,String> getParams(){
            Map<String,String> params = new HashMap<String, String>();
            params.put(KEY_ORDERID,order_id);
            params.put(KEY_USERID,globalVariable.getUserEmail());
            params.put(KEY_BAGSIZE,bagSize);
            params.put(KEY_BAGTYPE,bagType);
            params.put(KEY_BAGCOLOR,bagColor);
            params.put(KEY_PRICEID,priceID);

            return params;
        }

    };
    requestQueue.add(stringRequest);

}
    private void registerLuggageLarge2(final VolleyCallback callback, int i) {

        requestQueue = Volley.newRequestQueue(PaymentActivity.this);
        final String order_id = globalVariable.getRazorId();
        final String bagSize = "Large";
        final String bagType = globalVariable.getStringLargeBagTypeD(i);
        final String bagColor = globalVariable.getColorLargeDest(i);
        final String priceID = "1";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_StoreLuggage,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(PaymentActivity.this,response,Toast.LENGTH_LONG).show();
                        luggageflag = true;
                        JSONObject jObj = null;
                        boolean error = false;
                        try {
                            jObj = new JSONObject(response);
                            error = jObj.getBoolean("success");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        callback.onSuccess(error);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PaymentActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                        luggageflag = false;
                        callback.onSuccess(false);
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(KEY_ORDERID,order_id);
                params.put(KEY_USERID,globalVariable.getUserEmail());
                params.put(KEY_BAGSIZE,bagSize);
                params.put(KEY_BAGTYPE,bagType);
                params.put(KEY_BAGCOLOR,bagColor);
                params.put(KEY_PRICEID,priceID);

                return params;
            }

        };
        requestQueue.add(stringRequest);

    }
    private void registerUser(final VolleyCallback callback) {

        final String userEmail = globalVariable.getUserEmail();
        final String userName = globalVariable.getUserName();
        final String userPhone = globalVariable.getContactOrigin();
        final String userAdd = globalVariable.getAddress1Origin();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_StoreUser,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(PaymentActivity.this,response,Toast.LENGTH_LONG).show();
                        userflag = true;
                        JSONObject jObj = null;
                        boolean error = false;
                        try {
                            jObj = new JSONObject(response);
                            error = jObj.getBoolean("success");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        callback.onSuccess(error);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PaymentActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                        userflag = false;
                        callback.onSuccess(false);
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(KEY_USERID,userEmail);
                params.put(KEY_NAME,userName);
                params.put(KEY_PHONE,userPhone);
                params.put(KEY_PICKADD,userAdd);

                return params;
            }


        };
        requestQueue = Volley.newRequestQueue(PaymentActivity.this);
        requestQueue.add(stringRequest);

    }
    private void registerOrder(final VolleyCallback callback) {

        final String user = LoginActivity.User_name;
        final String order_id = globalVariable.getRazorId();
        final String totprice = String.valueOf(globalVariable.getTotalPrice());
        final String picDate = globalVariable.getPickupDate1();
        final String delDate = globalVariable.getDeliveryDate1();    //Problem Here
        final String picAdd = globalVariable.getAddress1Origin();
        final String delAdd = globalVariable.getAddress1Dest();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_StoreOrder,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(PaymentActivity.this, response, Toast.LENGTH_LONG).show();
                        orderflag = true;
                        JSONObject jObj = null;
                        boolean error = false;
                        try {
                            jObj = new JSONObject(response);
                            error = jObj.getBoolean("success");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        callback.onSuccess(error);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PaymentActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                        orderflag = false;
                        callback.onSuccess(false);
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(KEY_ORDERID,order_id);
                params.put(KEY_USERID,user);
                params.put(KEY_TOTPRICE, totprice);
                params.put(KEY_PICDATE,picDate);
                params.put(KEY_DELDATE,delDate);
                params.put(KEY_PICKADD,picAdd);
                params.put(KEY_DELADD,delAdd);

                return params;
            }

        };
        requestQueue = Volley.newRequestQueue(PaymentActivity.this);
        requestQueue.add(stringRequest);

    }
    private void registerOrder2(final VolleyCallback callback) {

        final String user = LoginActivity.User_name;
        final String order_id = globalVariable.getRazorId();
        final String totprice = String.valueOf(globalVariable.getTotalPrice());
        final String picDate = globalVariable.getPickupDate2();
        final String delDate = globalVariable.getDeliveryDate2();    //Problem Here
        final String picAdd = globalVariable.getAddress2Origin();
        final String delAdd = globalVariable.getAddress2Dest();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_StoreOrder,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(PaymentActivity.this, response, Toast.LENGTH_LONG).show();
                        orderflag = true;
                        JSONObject jObj = null;
                        boolean error = false;
                        try {
                            jObj = new JSONObject(response);
                            error = jObj.getBoolean("success");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        callback.onSuccess(error);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PaymentActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                        orderflag = false;
                        callback.onSuccess(false);
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(KEY_ORDERID,order_id);
                params.put(KEY_USERID,user);
                params.put(KEY_TOTPRICE, totprice);
                params.put(KEY_PICDATE,picDate);
                params.put(KEY_DELDATE,delDate);
                params.put(KEY_PICKADD,picAdd);
                params.put(KEY_DELADD,delAdd);

                return params;
            }

        };
        requestQueue = Volley.newRequestQueue(PaymentActivity.this);
        requestQueue.add(stringRequest);

    }

    public interface VolleyCallback{
        void onSuccess(boolean result);
    }

}
