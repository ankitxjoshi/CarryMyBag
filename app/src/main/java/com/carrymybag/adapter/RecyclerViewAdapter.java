package com.carrymybag.adapter;

/**
 * Created by Ankit on 5/28/2016.
 */
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.carrymybag.activity.CustomVolleyRequest;
import com.carrymybag.activity.FirstFragment;
import com.carrymybag.activity.LoginActivity;
import com.carrymybag.activity.MainActivity;

import com.carrymybag.R;
import com.carrymybag.activity.SecondFragment;
import com.carrymybag.activity.ThirdFragment;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    String[] titles;
    TypedArray icons;
    Context context;
    public TextView textViewName;
    public TextView textViewEmail;
    //TextViews
    public static NetworkImageView profilePhoto;

    //Image Loader
    public ImageLoader imageLoader;

    // The default constructor to receive titles,icons and context from MainActivity.
    public RecyclerViewAdapter(String[] titles, TypedArray icons, Context context){

        this.titles = titles;
        this.icons = icons;
        this.context = context;
    }

    /**
     *Its a inner class to RecyclerViewAdapter Class.
     *This ViewHolder class implements View.OnClickListener to handle click events.
     *If the itemType==1 ; it implies that the view is a single row_item with TextView and ImageView.
     *This ViewHolder describes an item view with respect to its place within the RecyclerView.
     *For every item there is a ViewHolder associated with it .
     */

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

        TextView  navTitle;
        ImageView navIcon;
        Context context;

        public ViewHolder(View drawerItem , int itemType , Context context){

            super(drawerItem);
            this.context = context;
            drawerItem.setOnClickListener(this);
            if(itemType==1){
                navTitle = (TextView) itemView.findViewById(R.id.tv_NavTitle);
                navIcon = (ImageView) itemView.findViewById(R.id.iv_NavIcon);
            }
        }

        /**
         *This defines onClick for every item with respect to its position.
         */

        @Override
        public void onClick(View v) {

            MainActivity mainActivity = (MainActivity)context;
            mainActivity.drawerLayout.closeDrawers();
            FragmentTransaction fragmentTransaction = mainActivity.getSupportFragmentManager().beginTransaction();

            switch (getPosition()){
                case 1:
                    Fragment firstFragment = new FirstFragment();
                    fragmentTransaction.replace(R.id.containerView,firstFragment);
                    fragmentTransaction.commit();
                    break;
                case 2:
                    Fragment secondFragment = new SecondFragment();
                    fragmentTransaction.replace(R.id.containerView,secondFragment);
                    fragmentTransaction.commit();
                    break;
                case 3:
                    Fragment thirdFragment = new ThirdFragment();
                    fragmentTransaction.replace(R.id.containerView,thirdFragment);
                    fragmentTransaction.commit();
                    break;
            }
        }
    }

    /**
     *This is called every time when we need a new ViewHolder and a new ViewHolder is required for every item in RecyclerView.
     *Then this ViewHolder is passed to onBindViewHolder to display items.
     */

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(viewType==1){
            View itemLayout =   layoutInflater.inflate(R.layout.drawer_item_layout,null);
            return new ViewHolder(itemLayout,viewType,context);
        }
        else if (viewType==0) {
            View itemHeader = layoutInflater.inflate(R.layout.header_layout,null);
            textViewName = (TextView)itemHeader.findViewById(R.id.textViewName);
            textViewEmail = (TextView)itemHeader.findViewById(R.id.textViewEmail);
            textViewName.setText(LoginActivity.User_name);
            textViewEmail.setText(LoginActivity.User_email);
            profilePhoto = (NetworkImageView)itemHeader.findViewById(R.id.profileImage);
            imageLoader = CustomVolleyRequest.getInstance(context.getApplicationContext())
                    .getImageLoader();
            String noImageURL = "http://static.sgv2.com/img/185242/no-image.png";
            if(LoginActivity.User_photourl!=null)
            {

                //Initializing image loader


                imageLoader.get(LoginActivity.User_photourl,
                        ImageLoader.getImageListener(profilePhoto,
                                R.mipmap.ic_launcher,
                                R.mipmap.ic_launcher));

                //Loading image
                profilePhoto.setImageUrl(LoginActivity.User_photourl, imageLoader);
            }
            else
            {
                imageLoader.get(noImageURL,
                        ImageLoader.getImageListener(profilePhoto,
                                R.mipmap.ic_launcher,
                                R.mipmap.ic_launcher));
                profilePhoto.setImageUrl(noImageURL, imageLoader);
            }

            return new ViewHolder(itemHeader,viewType,context);
        }



        return null;
    }

    /**
     *This method is called by RecyclerView.Adapter to display the data at the specified position.
     *This method should update the contents of the itemView to reflect the item at the given position.
     *So here , if position!=0 it implies its a row_item and we set the title and icon of the view.
     */

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder holder, int position) {

        if(position!=0){
            holder.navTitle.setText(titles[position - 1]);
            holder.navIcon.setImageResource(icons.getResourceId(position-1,-1));
        }

    }

    /**
     *It returns the total no. of items . We +1 count to include the header view.
     *So , it the total count is 5 , the method returns 6.
     *This 6 implies that there are 5 row_items and 1 header view with header at position zero.
     */

    @Override
    public int getItemCount() {
        return titles.length+1;
    }


    /**
     *This methods returns 0 if the position of the item is '0'.
     *If the position is zero its a header view and if its anything else
     *its a row_item with a title and icon.
     */

    @Override
    public int getItemViewType(int position) {
        if(position==0)return 0;
        else return 1;
    }



}
