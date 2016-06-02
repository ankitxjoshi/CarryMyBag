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

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_first_fragment,null);
        // Progress dialog
        pDialog = new ProgressDialog(getContext());
        pDialog.setCancelable(false);
        list = new ArrayList<>();
        if(list==null)
        {
            list.add("Ahmedabad");
        }
        new JSONTask().execute(AppConfig.URL_CityList);
        fromCityAuto = (AutoCompleteTextView)v.findViewById(R.id.fromcityauto);
        fromCityTo = (AutoCompleteTextView)v.findViewById(R.id.tocityauto);
        ArrayAdapter<String> tocityadapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_expandable_list_item_1,list);
        ArrayAdapter<String> fromcityadapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_expandable_list_item_1,list);
        fromCityAuto.setAdapter(fromcityadapter);
        fromCityTo.setAdapter(tocityadapter);
        return v;

    }


    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
    public class JSONTask extends AsyncTask<String,String,String> {

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
                    list.add(finalObject.getString("city"));

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
