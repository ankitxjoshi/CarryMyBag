package com.carrymybag.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.carrymybag.R;
import com.carrymybag.adapter.RecyclerViewAdapter;
import com.carrymybag.helper.SQLiteHandler;
import com.carrymybag.helper.SessionManager;

import java.util.HashMap;


import android.content.res.TypedArray;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;


public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    public DrawerLayout drawerLayout;
    RecyclerView recyclerView;
    String navTitles[];
    TypedArray navIcons;
    RecyclerView.Adapter recyclerViewAdapter;
    ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Let's first set up toolbar
        setupToolbar();

        //Initialize Views
        recyclerView  = (RecyclerView) findViewById(R.id.recyclerView);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerMainActivity);

        //Setup Titles and Icons of Navigation Drawer
        navTitles = getResources().getStringArray(R.array.navDrawerItems);
        navIcons = getResources().obtainTypedArray(R.array.navDrawerIcons);


        /**
         *Here , pass the titles and icons array to the adapter .
         *Additionally , pass the context of 'this' activity .
         *So that , later we can use the fragmentManager of this activity to add/replace fragments.
         */

        recyclerViewAdapter = new RecyclerViewAdapter(navTitles,navIcons,this);
        recyclerView.setAdapter(recyclerViewAdapter);

        /**
         *It is must to set a Layout Manager For Recycler View
         *As per docs ,
         *RecyclerView allows client code to provide custom layout arrangements for child views.
         *These arrangements are controlled by the RecyclerView.LayoutManager.
         *A LayoutManager must be provided for RecyclerView to function.
         */

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Finally setup ActionBarDrawerToggle
        setupDrawerToggle();


        //Add the Very First i.e Squad Fragment to the Container
        Fragment FirstFragment = new FirstFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.containerView, FirstFragment, null);
        fragmentTransaction.commit();


    }

    void setupToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    void setupDrawerToggle(){
        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_name,R.string.app_name);
        //This is necessary to change the icon of the Drawer Toggle upon state change.
        drawerToggle.syncState();
    }

}