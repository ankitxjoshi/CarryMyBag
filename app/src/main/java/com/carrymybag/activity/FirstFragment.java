package com.carrymybag.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.carrymybag.R;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

public class FirstFragment extends android.support.v4.app.Fragment {

    public AutoCompleteTextView fromCityAuto;
    public AutoCompleteTextView fromCityTo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_first_fragment,null);
        fromCityAuto = (AutoCompleteTextView)v.findViewById(R.id.fromcityauto);
        fromCityTo = (AutoCompleteTextView)v.findViewById(R.id.fromcityauto);
        String[] cityfrom = {"Surat","Ahmedabad"};
        String[] cityto = {"Surat","Ahmedabad"};
        ArrayAdapter<String> tocityadapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_expandable_list_item_1,cityto);
        ArrayAdapter<String> fromcityadapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_expandable_list_item_1,cityfrom);
        fromCityAuto.setAdapter(fromcityadapter);
        fromCityTo.setAdapter(tocityadapter);
        return v;

    }
}
