package com.carrymybag.helper;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import android.content.Context;

import com.carrymybag.app.AppConfig;

public class UserFunctions {

    private JSONParser jsonParser;

    //URL of the PHP API

    private static String forpassURL = AppConfig.URL_FORGOTPASS;



    // constructor
    public UserFunctions(){
        jsonParser = new JSONParser();
    }

    /**
     * Function to reset the password
     **/

    public JSONObject forPass(String forgotpassword){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("forgotpassword", forgotpassword));
        JSONObject json = jsonParser.getJSONFromUrl(forpassURL, params);
        return json;
    }










}