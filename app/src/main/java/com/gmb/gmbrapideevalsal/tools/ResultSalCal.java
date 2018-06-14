package com.gmb.gmbrapideevalsal.tools;

import android.util.Log;

import com.google.firebase.crash.FirebaseCrash;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by GMB on 11/27/2016.
 */

public class ResultSalCal {

    static final String TAG="ASC_Resul";

    String calculSucces="0";
    String typepay;
    String status;
    String payFrequency;
    String taxableRage;
    double gross;
    double hourRate;
    double totalTaxe;
    double totalDeduction;
    double standardDeduction;
    double personalDeduction;
    double otherDeduction;
    double federalTaxe;
    double socialSecuTaxe;
    double medicareTaxe;
    double otherTaxe;
    double payNet;
    double regularHour;
    double regularPay;
    double overtimeHour;
    double overtimePay;
    double otherOverTimeHour;
    double otherOverTimePay;
    double annualGros;

    long timeCalcul=System.currentTimeMillis();

    public ResultSalCal() {
    }

    public ResultSalCal(String typepay, String status, String payFrequency,
                        String taxableRage, double gross, double hourRate,
                        double totalTaxe, double totalDeduction, double standardDeduction,
                        double personalDeduction, double otherDeduction,
                        double federalTaxe, double socialSecuTaxe, double medicareTaxe,
                        double otherTaxe, double payNet, double regularHour,
                        double regularPay, double overtimeHour, double overtimePay,
                        double otherOverTimeHour, double otherOverTimePay, double annualGros) {
        super();

        calculSucces="1";

        this.typepay = typepay;
        this.status = status;
        this.payFrequency = payFrequency;
        this.taxableRage = taxableRage;
        this.gross = gross;
        this.hourRate = hourRate;
        this.totalTaxe = totalTaxe;
        this.totalDeduction = totalDeduction;
        this.standardDeduction = standardDeduction;
        this.personalDeduction = personalDeduction;
        this.otherDeduction = otherDeduction;
        this.federalTaxe = federalTaxe;
        this.socialSecuTaxe = socialSecuTaxe;
        this.medicareTaxe = medicareTaxe;
        this.otherTaxe = otherTaxe;
        this.payNet = payNet;
        this.regularHour = regularHour;
        this.regularPay = regularPay;
        this.overtimeHour = overtimeHour;
        this.overtimePay = overtimePay;
        this.otherOverTimeHour = otherOverTimeHour;
        this.otherOverTimePay = otherOverTimePay;
        this.annualGros=annualGros;

        timeCalcul=System.currentTimeMillis();
    }


    public ResultSalCal(String typepay, String status, String payFrequency,
                              String taxableRage) {
        super();

        calculSucces="1";
        this.typepay = typepay;
        this.status = status;
        this.payFrequency = payFrequency;
        this.taxableRage = taxableRage;

        timeCalcul=System.currentTimeMillis();
    }


    public ResultSalCal(String calculSucces, String typepay, String status) {
        super();
        this.calculSucces = calculSucces;
        this.typepay = typepay;
        this.status = status;

        timeCalcul=System.currentTimeMillis();
    }


    public String getCalculSucces() {
        return calculSucces;
    }

    public void setCalculSucces(String calculSucces) {
        this.calculSucces = calculSucces;
    }

    public String getTypepay() {
        return typepay;
    }

    public void setTypepay(String typepay) {
        this.typepay = typepay;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPayFrequency() {
        return payFrequency;
    }

    public void setPayFrequency(String payFrequency) {
        this.payFrequency = payFrequency;
    }

    public String getTaxableRage() {
        return taxableRage;
    }

    public void setTaxableRage(String taxableRage) {
        this.taxableRage = taxableRage;
    }

    public double getGross() {
        return gross;
    }

    public void setGross(double gross) {
        this.gross = gross;
    }

    public double getHourRate() {
        return hourRate;
    }

    public void setHourRate(double hourRate) {
        this.hourRate = hourRate;
    }

    public double getTotalTaxe() {
        return totalTaxe;
    }

    public void setTotalTaxe(double totalTaxe) {
        this.totalTaxe = totalTaxe;
    }

    public double getTotalDeduction() {
        return totalDeduction;
    }

    public void setTotalDeduction(double totalDeduction) {
        this.totalDeduction = totalDeduction;
    }

    public double getStandardDeduction() {
        return standardDeduction;
    }

    public void setStandardDeduction(double standardDeduction) {
        this.standardDeduction = standardDeduction;
    }

    public double getPersonalDeduction() {
        return personalDeduction;
    }

    public void setPersonalDeduction(double personalDeduction) {
        this.personalDeduction = personalDeduction;
    }

    public double getOtherDeduction() {
        return otherDeduction;
    }

    public void setOtherDeduction(double otherDeduction) {
        this.otherDeduction = otherDeduction;
    }

    public double getFederalTaxe() {
        return federalTaxe;
    }

    public void setFederalTaxe(double federalTaxe) {
        this.federalTaxe = federalTaxe;
    }

    public double getSocialSecuTaxe() {
        return socialSecuTaxe;
    }

    public void setSocialSecuTaxe(double socialSecuTaxe) {
        this.socialSecuTaxe = socialSecuTaxe;
    }

    public double getMedicareTaxe() {
        return medicareTaxe;
    }

    public void setMedicareTaxe(double medicareTaxe) {
        this.medicareTaxe = medicareTaxe;
    }

    public double getOtherTaxe() {
        return otherTaxe;
    }

    public void setOtherTaxe(double otherTaxe) {
        this.otherTaxe = otherTaxe;
    }

    public double getPayNet() {
        return payNet;
    }

    public void setPayNet(double payNet) {
        this.payNet = payNet;
    }

    public double getRegularHour() {
        return regularHour;
    }

    public void setRegularHour(double regularHour) {
        this.regularHour = regularHour;
    }

    public double getRegularPay() {
        return regularPay;
    }

    public void setRegularPay(double regularPay) {
        this.regularPay = regularPay;
    }

    public double getOvertimeHour() {
        return overtimeHour;
    }

    public void setOvertimeHour(double overtimeHour) {
        this.overtimeHour = overtimeHour;
    }

    public double getOvertimePay() {
        return overtimePay;
    }

    public void setOvertimePay(double overtimePay) {
        this.overtimePay = overtimePay;
    }

    public double getOtherOverTimeHour() {
        return otherOverTimeHour;
    }

    public void setOtherOverTimeHour(double otherOverTimeHour) {
        this.otherOverTimeHour = otherOverTimeHour;
    }

    public double getOtherOverTimePay() {
        return otherOverTimePay;
    }

    public void setOtherOverTimePay(double otherOverTimePay) {
        this.otherOverTimePay = otherOverTimePay;
    }

    public double getAnnualGros() {
        return annualGros;
    }

    public void setAnnualGros(double annualGros) {
        this.annualGros = annualGros;
    }

    public long getTimeCalcul() {
        return timeCalcul;
    }

    public void setTimeCalcul(long timeCalcul) {
        this.timeCalcul = timeCalcul;
    }

    @Override
    public String toString(){


        JSONObject js=new JSONObject();

        try {
            js.put("annualGros",getAnnualGros());
            js.put("calculSucces",getCalculSucces());
            js.put("federalTaxe",getFederalTaxe());
            js.put("gross",getGross());
            js.put("hourRate",getHourRate());
            js.put("medicareTaxe",getMedicareTaxe());
            js.put("otherDeduction",getOtherDeduction());
            js.put("otherOverTimeHour",getOtherOverTimeHour());
            js.put("otherOverTimePay",getOtherOverTimePay());
            js.put("otherTaxe",getOtherTaxe());
            js.put("overtimeHour",getOvertimeHour());
            js.put("overtimePay",getOvertimePay());
            js.put("payFrequency",getPayFrequency());
            js.put("payNet",getPayNet());
            js.put("personalDeduction",getPersonalDeduction());
            js.put("regularHour",getRegularHour());
            js.put("regularPay",getRegularPay());
            js.put("socialSecuTaxe",getSocialSecuTaxe());
            js.put("standardDeduction",getStandardDeduction());
            js.put("status",getStatus());
            js.put("taxableRage",getTaxableRage());
            js.put("totalDeduction",getTotalDeduction());
            js.put("totalTaxe",getTotalTaxe());
            js.put("typepay",getTypepay());
            js.put("timeCalcul",getTimeCalcul());
        } catch (JSONException e) {


            return null;
        }

        return js.toString();
    }


    public static ResultSalCal getObjFromString(String data){

        ResultSalCal res=new ResultSalCal();

        try{

            JSONObject js=new JSONObject(data);

            res.setAnnualGros(js.getDouble("annualGros"));
            res.setCalculSucces(js.getString("calculSucces"));
            res.setFederalTaxe(js.getDouble("federalTaxe"));
            res.setGross(js.getDouble("gross"));
            res.setHourRate(js.getDouble("hourRate"));
            res.setMedicareTaxe(js.getDouble("medicareTaxe"));
            res.setOtherDeduction(js.getDouble("otherDeduction"));
            res.setOtherOverTimeHour(js.getDouble("otherOverTimeHour"));
            res.setOtherOverTimePay(js.getDouble("otherOverTimePay"));
            res.setOtherTaxe(js.getDouble("otherTaxe"));
            res.setOvertimeHour(js.getDouble("overtimeHour"));
            res.setOvertimePay(js.getDouble("overtimePay"));
            res.setPayFrequency(js.getString("payFrequency"));
            res.setPayNet(js.getDouble("payNet"));
            res.setPersonalDeduction(js.getDouble("personalDeduction"));
            res.setRegularHour(js.getDouble("regularHour"));
            res.setRegularPay(js.getDouble("regularPay"));
            res.setSocialSecuTaxe(js.getDouble("socialSecuTaxe"));
            res.setStandardDeduction(js.getDouble("standardDeduction"));
            res.setStatus(js.getString("status"));
            res.setTaxableRage(js.getString("taxableRage"));
            res.setTotalDeduction(js.getDouble("totalDeduction"));
            res.setTotalTaxe(js.getDouble("totalTaxe"));
            res.setTypepay(js.getString("typepay"));
            res.setTimeCalcul(js.getLong("timeCalcul"));

        }
        catch (Exception ex){

            FirebaseCrash.logcat(Log.ERROR, TAG, "crash caused");
        }




        return res;
    }
}
