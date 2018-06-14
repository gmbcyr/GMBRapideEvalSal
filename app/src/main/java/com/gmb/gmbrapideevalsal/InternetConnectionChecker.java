package com.gmb.gmbrapideevalsal;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by cchoudja on 7/7/2015.
 */
public class InternetConnectionChecker {

    public static boolean checkNetworkOld(Context con,boolean deepTest) {

        boolean wifiDataAvailable = false;
        boolean mobileDataAvailable = false;
        boolean otherAvailable = false;

        ConnectivityManager conManager = (ConnectivityManager) con.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfo = conManager.getAllNetworkInfo();
        for (NetworkInfo netInfo : networkInfo) {
            if (netInfo.getTypeName().equalsIgnoreCase("WIFI")) {
                if (netInfo.isConnected()) {

                    wifiDataAvailable = true;
                    break;
                }
            }

            if (netInfo.getTypeName().equalsIgnoreCase("MOBILE")) {
                if (netInfo.isConnected()) {

                    mobileDataAvailable = true;
                    break;
                }
            }

            //Other generic test.
            if (netInfo.getState() == NetworkInfo.State.CONNECTED)
            {
                otherAvailable = true;
                break;
            }
        }

        if(deepTest && (wifiDataAvailable || mobileDataAvailable || otherAvailable)){

            String res=RestRequest.TestConnecServer("test","testngmb");

            System.out.println("InternetConnectionChecker checkNetwork avec res->"+res);


            if(!res.startsWith("-1.5")){

                wifiDataAvailable=false;
                mobileDataAvailable=false;
                otherAvailable=false;
            }
        }


        return wifiDataAvailable || mobileDataAvailable || otherAvailable;
    }


    public static boolean isNetworkAvaillable(Context context){

        ConnectivityManager cm=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=cm.getActiveNetworkInfo();

        if(networkInfo!=null && networkInfo.isConnected()){

            Log.e("SplashActivity","isNetworAvaillable-> AVAILLABLE");
            return true;
        }

        Log.e("SplashActivity","isNetworAvaillable-> NOT AVAILLABLE");
        return false;
    }
}
