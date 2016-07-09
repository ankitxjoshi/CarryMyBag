package com.carrymybag.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.carrymybag.R;
import com.carrymybag.app.AppConfig;
import com.carrymybag.app.AppController;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FirstFragment extends android.support.v4.app.Fragment {

    public AutoCompleteTextView fromCityAuto;
    public AutoCompleteTextView fromCityTo;
    private ProgressDialog pDialog;
    public static ArrayList<String> list;
    private TextView estimatePrice;
    private Button bookNowBtn;
    boolean flagFrom;
    boolean flagTo;
    RequestQueue requestQueue;
    public AppController globalVariable;

    public static final String KEY_FROMCITY = "from_city";
    public static final String KEY_TOCITY = "to_city";
    public static final String KEY_OPTION = "do_option";
    public static final String KEY_TYPE = "bag_id";



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_first_fragment,null);
        globalVariable = (AppController) getActivity().getApplicationContext();
        // Progress dialog
        pDialog = new ProgressDialog(getContext());
        pDialog.setCancelable(false);
        list = new ArrayList<>();
        if(list==null)
        {
            list.add("Ahmedabad");
        }
        estimatePrice = (TextView)v.findViewById(R.id.estimateView);
        new JSONTask().execute(AppConfig.URL_CityList);
        fromCityAuto = (AutoCompleteTextView)v.findViewById(R.id.fromcityauto);
        fromCityTo = (AutoCompleteTextView)v.findViewById(R.id.tocityauto);
        ArrayAdapter<String> tocityadapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_expandable_list_item_1,list);
        final ArrayAdapter<String> fromcityadapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_expandable_list_item_1,list);
        fromCityAuto.setAdapter(fromcityadapter);
        fromCityTo.setAdapter(tocityadapter);
        bookNowBtn = (Button)v.findViewById(R.id.bookNow);
        bookNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),
                        BookDetails.class);
                startActivity(intent);
            }
        });
        fromCityAuto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
                flagFrom = true;
                if(flagTo)
                {
                    globalVariable.setFromCity(fromCityAuto.getText().toString());
                    globalVariable.setToCity(fromCityTo.getText().toString());
                    getPrices();
                }
            }
        });
        fromCityTo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
                flagTo = true;
                if(flagFrom)
                {
                    globalVariable.setFromCity(fromCityAuto.getText().toString());
                    globalVariable.setToCity(fromCityTo.getText().toString());
                    getPrices();
                }
            }
        });

        return v;

    }

    private class JSONTask extends AsyncTask<String,String,String> {

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
                    list.add(finalObject.getString("city_name"));

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
    private void getPrices()
    {
        requestQueue = Volley.newRequestQueue(getActivity());
        final String fromCity = globalVariable.getFromCity();
        final String toCity = globalVariable.getToCity();
        final String doOption = "Standard";
        final String bagtype = "Small";
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
                                estimatePrice.setText("Starting from Rs "+val);

                            } else {
                                // Error in login. Get the error message
                                String errorMsg = jObj.getString("error_msg");
                                estimatePrice.setText("We don't offer service across these cities");
                            }
                        } catch (JSONException e) {
                            // JSON error
                            e.printStackTrace();
                            estimatePrice.setText("We don't offer service across these cities");
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
