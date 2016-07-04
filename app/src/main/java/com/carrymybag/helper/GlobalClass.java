package com.carrymybag.helper;

import android.app.Application;
import android.widget.Toast;

import java.util.ArrayList;

public class GlobalClass extends Application {

    private double qtySmall1, qtyMed1, qtyLarge1, priceSmall1, priceMed1, priceLarge1;
    private double qtySmall2, qtyMed2, qtyLarge2, priceSmall2, priceMed2, priceLarge2;
    private double totalPrice;
    boolean twoWay;
    private String pickupDate1, pickupDate2, deliveryDate1, deliveryDate2;


    public void setTwoWay(boolean w)
    {
        twoWay = w;
    }
    public boolean isTwoWay()
    {
        return twoWay;
    }
    public void setQtySmall1(double q)
    {
        qtySmall1 = q;
    }

    public double getQtySmall1()
    {
        return qtySmall1;
    }

    public void setQtyMed1(double q)
    {
        qtyMed1 = q;
    }

    public double getQtyMed1()
    {
        return qtyMed1;
    }

    public void setQtyLarge1(double q)
    {
        qtyLarge1 = q;
    }

    public double getQtyLarge1()
    {
        return qtyLarge1;
    }

    public void setQtySmall2(double q)
    {
        qtySmall2 = q;
    }

    public double getQtySmall2()
    {
        return qtySmall2;
    }

    public void setQtyMed2(double q)
    {
        qtyMed2 = q;
    }

    public double getQtyMed2()
    {
        return qtyMed2;
    }

    public void setQtyLarge2(double q)
    {
        qtyLarge2 = q;
    }

    public double getQtyLarge2()
    {
        return qtyLarge2;
    }


    public void setPriceSmall1(double p)
    {
        priceSmall1 = p;
    }

    public double getPriceSmall1()
    {
        return priceSmall1;
    }

    public void setPriceMed1(double p)
    {
        priceMed1 = p;
    }

    public double getPriceMed1()
    {
        return priceMed1;
    }

    public void setPriceLarge1(double p)
    {
        priceLarge1 = p;
    }

    public double getPriceLarge1()
    {
        return priceLarge1;
    }

    public void setPriceSmall2(double p)
    {
        priceSmall2 = p;
    }

    public double getPriceSmall2()
    {
        return priceSmall2;
    }

    public void setPriceMed2(double p)
    {
        priceMed2 = p;
    }

    public double getPriceMed2()
    {
        return priceMed2;
    }

    public void setPriceLarge2(double p)
    {
        priceLarge2 = p;
    }

    public double getPriceLarge2()
    {
        return priceLarge2;
    }

    public double getTotalPrice()
    {
        return totalPrice;
    }

    public void setTotalPrice(double price)
    {
        totalPrice = price;
    }

    public void setPickupDate1(String date)
    {
        pickupDate1 = date;
    }

    public String getPickupDate1()
    {
        return pickupDate1;
    }

    public void setPickupDate2(String date)
    {
        pickupDate2 = date;
    }

    public String getPickupDate2()
    {
        return pickupDate2;
    }

    public void setDeliveryDate1(String date)
    {
        deliveryDate1 = date;
    }

    public String getDeliveryDate1()
    {
        return deliveryDate1;
    }

    public void setDeliveryDate2(String date)
    {
        deliveryDate2 = date;
    }

    public String getDeliveryDate2()
    {
        return deliveryDate2;
    }

    public String contactOrigin, contactDest, pinOrigin, pinDest, addressOrigin, addressDest;  //Stored

    public String addrTypeOrigin, addrTypeDest;
    public String cityOrigin, cityDest;
    public String stateOrigin, stateDest;

    public String contactOrigin2, contactDest2, pinOrigin2, pinDest2, addressOrigin2, addressDest2;

    public String addrTypeOrigin2, addrTypeDest2;
    public String cityOrigin2, cityDest2;
    public String stateOrigin2, stateDest2;                                         //uptil here.

    public String getContactOrigin()
    {
        return contactOrigin;
    }

    public void setContactOrigin(String contact)
    {
        contactOrigin = contact;
    }

    public String getContactDest()
    {
        return contactDest;
    }
    public void setContactDest(String contact)

    {
        contactDest = contact;
    }

    public String getContactOrigin2()
    {
        return contactOrigin2;
    }

    public void setContactOrigin2(String contact)
    {
        contactOrigin2 = contact;
    }

    public String getContactDest2()
    {
        return contactDest2;
    }

    public void setContactDest2(String contact)
    {
        contactDest2 = contact;
    }
    public void setPinOrigin(String pin)

    {
        pinOrigin = pin;
    }

    public String getPinOrigin()
    {
        return pinOrigin;
    }

    public void setPinDest(String pin)
    {
        pinDest = pin;
    }

    public String getPinDest()
    {
        return pinDest;
    }

    public void setPinOrigin2(String pin)
    {
        pinOrigin2 = pin;
    }

    public String getPinOrigin2()
    {
        return pinOrigin2;
    }

    public void setPinDest2(String pin)
    {
        pinDest2 = pin;
    }

    public String getPinDest2()
    {
        return pinDest2;
    }

    public String getAddressOrigin()
    {
        return addressOrigin;
    }

    public void setAddressOrigin(String address)
    {
        addressOrigin = address;
    }

    public String getAddressDest()
    {
        return addressDest;
    }
    public void setAddressDest(String address)

    {
        addressDest = address;
    }

    public String getAddressOrigin2()
    {
        return addressOrigin2;
    }

    public void setAddressOrigin2(String address)
    {
        addressOrigin2 = address;
    }

    public String getAddressDest2()
    {
        return addressDest2;
    }

    public void setAddressDest2(String address)
    {
        addressDest2 = address;
    }

    public String getCityOrigin()
    {
        return cityOrigin;
    }
    public void setCityOrigin(String city)

    {
        cityOrigin = city;
    }

    public String getCityDest()
    {
        return cityDest;
    }

    public void setCityDest(String city)
    {
        cityDest = city;
    }

    public String getCityOrigin2()
    {
        return cityOrigin2;
    }

    public void setCityOrigin2(String city)
    {
        cityOrigin2 = city;
    }

    public String getCityDest2()
    {
        return cityDest2;
    }

    public void setCityDest2(String city)
    {
        cityDest2 = city;
    }

//    public String getStateOrigin()
//    {
//        return stateOrigin;
//    }
//
//    public void setStateOrigin(String state)
//    {
//        stateOrigin = state;
//    }
//
//    public String getStateDest()
//    {
//        return stateDest;
//    }
//
//    public void setStateDest(String state)
//    {
//        stateDest = state;
//    }
//
//    public String getStateOrigin2()
//    {
//        return stateOrigin2;
//    }
//
//    public void setStateOrigin2(String state)
//    {
//        stateOrigin2 = state;
//    }
//
//    public String getStateDest2()
//    {
//        return stateDest2;
//    }
//
//    public void setStateDest2(String state)
//    {
//        stateDest2 = state;
//    }

    public String getAddrTypeOrigin()
    {
        return addrTypeOrigin;
    }

    public void setAddrTypeOrigin(String address)
    {
        addrTypeOrigin = address;
    }

    public String getAddrTypeDest()
    {
        return addrTypeDest;
    }

    public void setAddrTypeDest(String address)
    {
        addrTypeDest = address;
    }

    public String getAddrTypeOrigin2()
    {
        return addrTypeOrigin2;
    }

    public void setAddrTypeOrigin2(String address)
    {
        addrTypeOrigin2 = address;
    }

    public String getAddrTypeDest2()
    {
        return addrTypeDest2;
    }

    public void setAddrTypeDest2(String address)
    {
        addrTypeDest2 = address;
    }

//    int NumSmallBagsOrigin, NumMediumBagsOrigin, NumLargeBagsOrigin;
//    int NumSmallBagsDest, NumMediumBagsDest, NumLargeBagsDest;
//
//    public void setNumSmallBagsOrigin(int n)
//    {
//        NumSmallBagsOrigin = n;
//    }
//
//    public int getNumSmallBagsOrigin()
//    {
//        return NumSmallBagsOrigin;
//    }
//
//    public void setNumMediumBagsOrigin(int n)
//    {
//        NumMediumBagsOrigin = n;
//    }
//
//    public int getNumMediumBagsOrigin()
//    {
//        return NumMediumBagsOrigin;
//    }
//
//    public void setNumLargeBagsOrigin(int n)
//    {
//        NumLargeBagsOrigin = n;
//    }
//
//    public int getNumLargeBagsOrigin()
//    {
//        return NumLargeBagsOrigin;
//    }
//
//    public void setNumSmallBagsDest(int n)
//    {
//        NumSmallBagsDest = n;
//    }
//
//    public int getNumSmallBagsDest()
//    {
//        return NumSmallBagsDest;
//    }
//
//    public void setNumMediumBagsDest(int n)
//    {
//        NumMediumBagsDest = n;
//    }
//
//    public int getNumMediumBagsDest()
//    {
//        return NumMediumBagsDest;
//    }
//
//    public void setNumLargeBagsDest(int n)
//    {
//        NumLargeBagsDest = n;
//    }
//
//    public int getNumLargeBagsDest()
//    {
//        return NumLargeBagsDest;
//    }



    private String colorSmallOrigin[];
    private String colorMediumOrigin[];
    private String colorLargeOrigin[];
    private String colorSmallDest[];
    private String colorMediumDest[];
    private String colorLargeDest[];

    public void setColorSmallOrigin(String[] c)
    {
        try{
            colorSmallOrigin = c;
        }
        catch(IndexOutOfBoundsException e)
        {
            Toast.makeText(getApplicationContext(),"Wrong Index",Toast.LENGTH_LONG).show();
        }
    }

    public String getColorSmallOrigin(int i)
    {
        try{
            return colorSmallOrigin[i];
        }
        catch(IndexOutOfBoundsException e)
        {
            Toast.makeText(getApplicationContext(),"Wrong Index",Toast.LENGTH_LONG).show();
        }
        return null;
    }

    public void setColorMediumOrigin(String[] c)
    {
        try{
            colorMediumOrigin = c;
        }
        catch(IndexOutOfBoundsException e)
        {
            Toast.makeText(getApplicationContext(),"Wrong Index",Toast.LENGTH_LONG).show();
        }
    }

    public String getColorMediumOrigin(int i)
    {
        try{
            return colorMediumOrigin[i];
        }
        catch(IndexOutOfBoundsException e)
        {
            Toast.makeText(getApplicationContext(),"Wrong Index",Toast.LENGTH_LONG).show();
        }
        return null;
    }

    public void setColorLargeOrigin(String[] c)
    {
        try{
            colorLargeOrigin = c;
        }
        catch(IndexOutOfBoundsException e)
        {
            Toast.makeText(getApplicationContext(),"Wrong Index", Toast.LENGTH_LONG).show();
        }
    }

    public String getColorLargeOrigin(int i)
    {
        try{
            return colorLargeOrigin[i];
        }
        catch(IndexOutOfBoundsException e)
        {
            Toast.makeText(getApplicationContext(),"Wrong Index",Toast.LENGTH_LONG).show();
        }
        return null;
    }

    public void setColorSmallDest(String[] c)
    {
        try{
            colorSmallDest = c;
        }
        catch(IndexOutOfBoundsException e)
        {
            Toast.makeText(getApplicationContext(),"Wrong Index",Toast.LENGTH_LONG).show();
        }
    }

    public String getColorSmallDest(int i)
    {
        try{
            return colorSmallDest[i];
        }
        catch(IndexOutOfBoundsException e)
        {
            Toast.makeText(getApplicationContext(),"Wrong Index",Toast.LENGTH_LONG).show();
        }
        return null;
    }

    public void setColorMediumDest(String[] c)
    {
        try{
            colorMediumDest = c;
        }
        catch(IndexOutOfBoundsException e)
        {
            Toast.makeText(getApplicationContext(),"Wrong Index",Toast.LENGTH_LONG).show();
        }

    }

    public String getColorMediumDest(int i)
    {
        try{
            return colorMediumDest[i];
        }
        catch(IndexOutOfBoundsException e)
        {
            Toast.makeText(getApplicationContext(),"Wrong Index",Toast.LENGTH_LONG).show();
        }
        return null;
    }

    public void setColorLargeDest(String[] c)
    {
        try{
            colorLargeDest = c;
        }
        catch(IndexOutOfBoundsException e)
        {
            Toast.makeText(getApplicationContext(),"Wrong Index",Toast.LENGTH_LONG).show();
        }
    }

    public String getColorLargeDest(int i)
    {
        try{
            return colorLargeDest[i];
        }
        catch(IndexOutOfBoundsException e)
        {
            Toast.makeText(getApplicationContext(),"Wrong Index",Toast.LENGTH_LONG).show();
        }
        return null;
    }

    private ArrayList<String> StringSmallBagTypeO;
    private ArrayList<String> StringMediumBagTypeO;
    private ArrayList<String> StringLargeBagTypeO;
    private ArrayList<String> StringSmallBagTypeD;
    private ArrayList<String> StringMediumBagTypeD;
    private ArrayList<String> StringLargeBagTypeD;
}
