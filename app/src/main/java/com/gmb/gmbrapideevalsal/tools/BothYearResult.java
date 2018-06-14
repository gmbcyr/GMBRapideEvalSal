package com.gmb.gmbrapideevalsal.tools;

import android.util.Log;

import com.google.firebase.crash.FirebaseCrash;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by GMB on 1/24/2018.
 */

public class BothYearResult {

    ResultSalCal currentYear;
    ResultSalCal oldYear;
    String rawData;

    static String TAG="BothYearResult";

    public BothYearResult() {
    }

    public BothYearResult(ResultSalCal currentYear, ResultSalCal oldYear) {
        this.currentYear = currentYear;
        this.oldYear = oldYear;
    }

    public ResultSalCal getCurrentYear() {
        return currentYear;
    }

    public void setCurrentYear(ResultSalCal currentYear) {
        this.currentYear = currentYear;
    }

    public ResultSalCal getOldYear() {
        return oldYear;
    }

    public void setOldYear(ResultSalCal oldYear) {
        this.oldYear = oldYear;
    }

    public String getRawData() {
        return rawData;
    }

    public void setRawData(String rawData) {
        this.rawData = rawData;
    }

    public static BothYearResult getDataFromString(String data){

        BothYearResult res=new BothYearResult();


        try{
            StringTokenizer tok=new StringTokenizer(data,"_");

            Log.e(TAG,"getDataFromString une ligne->"+data);

            String timeCal=tok.nextToken();

            String cal1=tok.nextToken();

            String cal2=tok.nextToken();

            res=new BothYearResult(ResultSalCal.getObjFromString(cal1),ResultSalCal.getObjFromString(cal2));

            res.setRawData(cal1+"_"+cal2);
        }
        catch (Exception ex){

            FirebaseCrash.logcat(Log.ERROR, TAG, "crash caused on getDataFromString");
        }

        return res;
    }


    public static ArrayList<BothYearResult> getListFromString(String data){

        StringTokenizer tok=new StringTokenizer(data,"#");

        ArrayList<BothYearResult> lst=new ArrayList<>();


        while (tok.hasMoreTokens()){

            lst.add(getDataFromString(tok.nextToken()));
        }


        return lst;
    }



}
