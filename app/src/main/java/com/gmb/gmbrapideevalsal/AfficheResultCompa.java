package com.gmb.gmbrapideevalsal;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.crash.FirebaseCrash;

import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.Iterator;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.TreeMap;


public class AfficheResultCompa extends Activity implements TextToSpeech.OnInitListener  {

    //Notez qu'on utilise Menu.FIRST pour indiquer le premier �l�ment 	d'un menu
    private  final static int MENU_DESACTIVER = Menu.FIRST;
    private  final static int MENU_RETOUR = Menu.FIRST + 1;
    private static final int RESULT_SETTINGS = 1;

    public static final String MODE_COMPARE_YES="compa:1";
    public static final String MODE_COMPARE_NO="compa:0";

    String result;
    TextView txtPayType,txtPayFreq,txtPayRate,txtPayNet,frequencyTab,totalPerYear,grossFreqVal;


    /*General Infos views*/
    TextView txtTabFreq, txtTabTotal,txtGrossInTitle,txtGrossFreq,txtGrossTotal, txtDeducTitle,txtDeducFreq,txtDeducTotal,txtTaxWageTitle,
            txtTaxWageFreq,txtTaxWageTotal,txtTaxTitle,txtTaxFreq,txtTaxTotal,txtNetTitle,txtNetFreq,txtNetTotal;

    /*General Infos Earning*/
    TextView txtTabGainFreq, txtTabGainTotal,txtGainTitle,txtGainFreq,txtGainTotal, txtOtTitle,txtOtFreq,txtOtTotal,txtOtherInTitle,
            txtOtherInFreq,txtOtherInTotal;

    /*General Infos Deductions*/
    TextView txtTabDeducFreq, txtTabDeducTotal,txtDeducStandTitle,txtDeducStandFreq,txtDeducStandTotal, txtDeducPersoTitle,txtDeducPersoFreq,txtDeducPersoTotal,txtDeducOtherTitle,
            txtDeducOtherFreq,txtDeducOtherTotal;


    /*General Infos Taxes*/
    TextView txtTabTaxFreq, txtTabTaxTotal,txtTaxFedTitle,txtTaxFedFreq,txtTaxFedTotal, txtTaxSsecuTitle,txtTaxSsecuFreq,txtTaxSsecuTotal,txtTaxMcareTitle,
            txtTaxMcareFreq,txtTaxMcareTotal,txtTaxOtherTitle,
            txtTaxOtherFreq,txtTaxOtherTotal;

    Button newEval = null;
    ImageView imgBack;


    private TextToSpeech txtSpeaker;
    private AdView adView,adView2;
   // private Button btnSpeak;
    private TextView txtToSpeak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affiche_result_compa);

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        //request the ads
        adView=(AdView) findViewById(R.id.adView);
        AdRequest request=new AdRequest.Builder().build();
        adView.loadAd(request);

        adView2=(AdView) findViewById(R.id.adView2);
        AdRequest request2=new AdRequest.Builder().build();
        adView2.loadAd(request2);

        SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(this);

        Intent intent=getIntent();

        boolean show_compare=shared.getBoolean(getString(R.string.show_compare_mode),true);

        String content=shared.getString("resultToShow",result);
        StringTokenizer tok=new StringTokenizer(content,"_");

        Log.e("ResultFrag","onCreateView avec rawData->"+content);
        String show=tok.nextToken();
        String mode=tok.nextToken();
        String compa=tok.nextToken();
        result=tok.nextToken();
        final String part2=tok.nextToken();

        if("mode:1".equalsIgnoreCase(mode)){

            Snackbar snackbar = Snackbar
                    .make(findViewById(android.R.id.content), getString(R.string.save_result_lbl), Snackbar.LENGTH_LONG)
                    .setAction(getString(R.string.save_result), new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            saveCalcul(HistoriqueFragment.TYPE_HISTO_SAVED,result+"_"+part2);

                            Snackbar.make(findViewById(android.R.id.content), getString(R.string.completed),
                                    Snackbar.LENGTH_SHORT).show();
                        }
                    });
            snackbar.show();
        }

        if(result!=null){

            txtPayType = (TextView)findViewById(R.id.payType);
            txtPayFreq = (TextView)findViewById(R.id.payFreq);
            frequencyTab = (TextView)findViewById(R.id.frequencyTab);
            txtPayRate = (TextView)findViewById(R.id.payRate);
            txtPayNet = (TextView)findViewById(R.id.payNet);
            //totalPerYear = (TextView)findViewById(R.id.totalPerYear);

            imgBack=(ImageView) findViewById(R.id.img_back);
            imgBack.setOnClickListener(backToEval);

            newEval=(Button) findViewById(R.id.other_eval);
            newEval.setOnClickListener(backToEval);

            /****************************************************************************Load General Infos views*/
            txtTabFreq = (TextView)findViewById(R.id.tab_freq);
            txtTabTotal = (TextView)findViewById(R.id.tab_total);
            txtGrossInTitle = (TextView)findViewById(R.id.gross_in_title);
            txtGrossFreq = (TextView)findViewById(R.id.gross_in_freq);
            txtGrossTotal = (TextView)findViewById(R.id.gross_in_total);
            txtDeducTitle = (TextView)findViewById(R.id.deductionTab);
            txtDeducFreq = (TextView)findViewById(R.id.deductionFreqVal);
            txtDeducTotal = (TextView)findViewById(R.id.deductionInYear);
            txtTaxWageTitle = (TextView)findViewById(R.id.taxableWageTab);
            txtTaxWageFreq = (TextView)findViewById(R.id.taxableFreqVal);
            txtTaxWageTotal = (TextView)findViewById(R.id.TaxableInYearVal);
            txtTaxTitle = (TextView)findViewById(R.id.TaxeTab);
            txtTaxFreq = (TextView)findViewById(R.id.taxeFreqVal);
            txtTaxTotal = (TextView)findViewById(R.id.taxeInYear);
            txtNetTitle = (TextView)findViewById(R.id.netPayTab);
            txtNetFreq = (TextView)findViewById(R.id.netPayFreqVal);
            txtNetTotal = (TextView)findViewById(R.id.netPayInYear);



            /****************************************************************************Load Earning Infos views*/
            txtTabGainFreq = (TextView)findViewById(R.id.earnings_freg);
            txtTabGainTotal = (TextView)findViewById(R.id.earnings_total);
            txtGainTitle= (TextView)findViewById(R.id.regular_pay_title);
            txtGainFreq= (TextView)findViewById(R.id.standard_pay_freg);
            txtGainTotal= (TextView)findViewById(R.id.standard_pay_total);
            txtOtTitle= (TextView)findViewById(R.id.ot_pay_title);
            txtOtFreq= (TextView)findViewById(R.id.ot_pay_freq);
            txtOtTotal= (TextView)findViewById(R.id.ot_pay_total);
            txtOtherInTitle= (TextView)findViewById(R.id.other_pay_title);
            txtOtherInFreq= (TextView)findViewById(R.id.other_pay_freq);
            txtOtherInTotal = (TextView)findViewById(R.id.other_pay_total);



            /****************************************************************************Load Deductions Infos views*/

            txtTabDeducFreq = (TextView)findViewById(R.id.deduc_freg);
            txtTabDeducTotal= (TextView)findViewById(R.id.deduc_total);
            txtDeducStandTitle= (TextView)findViewById(R.id.standardDeduction);
            txtDeducStandFreq= (TextView)findViewById(R.id.standard_deduction_freg);
            txtDeducStandTotal = (TextView)findViewById(R.id.standard_deduction_total);
            txtDeducPersoTitle = (TextView)findViewById(R.id.PersonnalDeduc);
            txtDeducPersoFreq = (TextView)findViewById(R.id.Personnal_deduction_freq);
            txtDeducPersoTotal = (TextView)findViewById(R.id.Personnal_deduction_total);
            txtDeducOtherTitle = (TextView)findViewById(R.id.otherDeduc);
            txtDeducOtherFreq = (TextView)findViewById(R.id.other_deduction_freq);
            txtDeducOtherTotal= (TextView)findViewById(R.id.other_deduction_total);






            /****************************************************************************Load Deductions Infos views*/
            txtTabTaxFreq= (TextView)findViewById(R.id.tax_freg);
            txtTabTaxTotal= (TextView)findViewById(R.id.tax_total);
            txtTaxFedTitle= (TextView)findViewById(R.id.federalTaxes);
            txtTaxFedFreq= (TextView)findViewById(R.id.federalTaxesVal_freq);
            txtTaxFedTotal= (TextView)findViewById(R.id.federalTaxesVal_total);
            txtTaxSsecuTitle= (TextView)findViewById(R.id.social_security_taxes);
            txtTaxSsecuFreq= (TextView)findViewById(R.id.social_security_taxes_val_freq);
            txtTaxSsecuTotal= (TextView)findViewById(R.id.social_security_taxes_val_total);
            txtTaxMcareTitle= (TextView)findViewById(R.id.EmployeeMedic);
            txtTaxMcareFreq= (TextView)findViewById(R.id.employee_medicare_Val_freq);
            txtTaxMcareTotal= (TextView)findViewById(R.id.employee_medicare_Val_total);
            txtTaxOtherTitle= (TextView)findViewById(R.id.othersTaxes);
            txtTaxOtherFreq= (TextView)findViewById(R.id.others_taxes_Val_freq);
            txtTaxOtherTotal= (TextView)findViewById(R.id.others_taxes_Val_total);







            /****************************************************************************Load Taxe Infos views*/
            txtTabTaxFreq= (TextView)findViewById(R.id.tax_freg);
            txtTabTaxTotal= (TextView)findViewById(R.id.tax_total);
            txtTaxFedTitle= (TextView)findViewById(R.id.federalTaxes);
            txtTaxFedFreq= (TextView)findViewById(R.id.federalTaxesVal_freq);
            txtTaxFedTotal= (TextView)findViewById(R.id.federalTaxesVal_total);
            txtTaxSsecuTitle= (TextView)findViewById(R.id.social_security_taxes);
            txtTaxSsecuFreq= (TextView)findViewById(R.id.social_security_taxes_val_freq);
            txtTaxSsecuTotal= (TextView)findViewById(R.id.social_security_taxes_val_total);
            txtTaxMcareTitle= (TextView)findViewById(R.id.EmployeeMedic);
            txtTaxMcareFreq= (TextView)findViewById(R.id.employee_medicare_Val_freq);
            txtTaxMcareTotal= (TextView)findViewById(R.id.employee_medicare_Val_total);
            txtTaxOtherTitle= (TextView)findViewById(R.id.othersTaxes);
            txtTaxOtherFreq= (TextView)findViewById(R.id.others_taxes_Val_freq);
            txtTaxOtherTotal= (TextView)findViewById(R.id.others_taxes_Val_total);

             JSONObject json;
                String test="0";
                try{
                    Log.e("ResultFrag","onCreateView avec rawData 01->"+content);


                    String freqPay = (shared.getString(getString(R.string.pay_frequency_name), "2"));

                    int facto=(freqPay.equalsIgnoreCase("1"))? 1:(freqPay.equalsIgnoreCase("2"))? 2:4;

                    String frequenPay=(freqPay.equalsIgnoreCase("1"))? getString(R.string.pay_weekly) :
                            (freqPay.equalsIgnoreCase("2"))? getString(R.string.pay_two_week):getString(R.string.pay_monthly);



                    //String getPay=(intent!=null)?intent.getStringExtra("typePay"):getString(R.string.pay_type_hour);


                    String getPay=getString(R.string.pay_type_hour);
                    String payTyp=getPay.equalsIgnoreCase("hourly")? getString(R.string.pay_type_hour): getString(R.string.pay_type_year);

                    NumberFormat numfor=NumberFormat.getCurrencyInstance();
                    NumberFormat numfor2=NumberFormat.getInstance();
                    numfor.setGroupingUsed(true);

                    json = new JSONObject(result);


                    Log.e("ResultFrag","onCreateView avec rawData pos 2->"+content);



                    System.out.println("\n\nAfficheResult Activity on create Apres recup vue");
                    //attribution des listners aux diff�rents �l�ments
                    //valide.setOnClickListener(resultat);


                    txtPayType.setText(getString(R.string.pay_type) +payTyp);
                    txtPayFreq.setText(getString(R.string.pay_frequency)+frequenPay);
                    txtPayRate.setText(getString(R.string.pay_rate)+numfor.format(json.getDouble("hourRate")));
                    txtPayNet.setText(getString(R.string.pay_net)+numfor.format(json.getDouble("payNet")*facto));


                    //SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    String hDay = (shared.getString(getString(R.string.hour_per_day_name), "8"));
                    String dWeek = (shared.getString(getString(R.string.day_per_week_name), "5"));
                    String maxHweekStr=shared.getString(getString(R.string.max_hour_week_name),"40");

                    double regularHour=40;
                    double maxHweek=40;

                    try{

                        regularHour=Double.parseDouble(hDay)*Double.parseDouble(dWeek);
                        maxHweek=Double.parseDouble(maxHweekStr);

                        regularHour=regularHour>maxHweek? maxHweek:regularHour;

                    }
                    catch (Exception ept){


                    }

                    /**************************************************************Create General Infos*/

                    String gross=numfor.format(json.getDouble("gross")*facto);
                    String annualGross=numfor.format(json.getDouble("annualGros"));

                    String deducFreq=numfor.format(json.getDouble("totalDeduction")*facto);
                    String deducTotal=numfor.format(json.getDouble("totalDeduction")*52);

                    String TaxWageFreg=numfor.format(json.getDouble("taxableRage")*facto);
                    String TaxWageTotal=numfor.format(json.getDouble("taxableRage")*52);
                    String taxFreg=numfor.format(json.getDouble("totalTaxe")*facto);
                    String taxTotal=numfor.format(json.getDouble("totalTaxe")*52);

                    String netFreq=numfor.format(json.getDouble("payNet")*facto);
                    String netTotal=numfor.format(json.getDouble("payNet")*52);

                    String tabFreg=getString(R.string.freq_pay)+"\n("+frequenPay+")";
                    String tabTotal=getString(R.string.total_annual);

                    /******************************************************Create Earning Infos*/

                    String gainFreq=numfor.format(json.getDouble("regularPay")*facto);
                    String gainTotal=numfor.format(json.getDouble("regularPay")*52);

                    String otFreq=numfor.format(json.getDouble("overtimePay")*facto);
                    String otTotal=numfor.format(json.getDouble("overtimePay")*52);

                    String otherFreq=numfor.format(json.getDouble("otherOverTimePay")*facto);
                    String otherTotal=numfor.format(json.getDouble("otherOverTimePay")*52);

                    /**************************************************************Create Deduction Infos*/

                    String standDeducFreq=numfor.format(json.getDouble("standardDeduction")*facto);
                    String standDeducTotal=numfor.format(json.getDouble("standardDeduction"));

                    String persoFreq=numfor.format(json.getDouble("personalDeduction")*facto);
                    String persoTotal=numfor.format(json.getDouble("personalDeduction")*52);

                    String otherDeducFreg=numfor.format(json.getDouble("otherDeduction")*facto);
                    String otherDeducTotal=numfor.format(json.getDouble("otherDeduction")*52);

                    /**************************************************************Create Tax Infos*/

                    String fedTax=numfor.format(json.getDouble("federalTaxe")*facto);
                    String fedTaxTotal=numfor.format(json.getDouble("federalTaxe"));

                    String secuTax=numfor.format(json.getDouble("socialSecuTaxe")*facto);
                    String secuTaxTotal=numfor.format(json.getDouble("socialSecuTaxe")*52);

                    String mCareTax=numfor.format(json.getDouble("medicareTaxe")*facto);
                    String mCareTaxTotal=numfor.format(json.getDouble("medicareTaxe")*52);

                    String otherTax=numfor.format(json.getDouble("otherTaxe")*facto);
                    String otherTaxTotal=numfor.format(json.getDouble("otherTaxe")*52);



                    if(show_compare) { //On Affiche un seul element sans comparaison

                        JSONObject jsonOld=new JSONObject(part2);

                         tabFreg=getString(R.string.old_tax_rules);
                         tabTotal=getString(R.string.new_tax_cut);

                        /**************************************************************Update General Infos*/

                        gross=numfor.format(jsonOld.getDouble("gross")*facto)+"\n"+numfor.format(jsonOld.getDouble("annualGros"));
                        annualGross=numfor.format(json.getDouble("gross")*facto)+"\n"+numfor.format(json.getDouble("annualGros"));

                        deducFreq=numfor.format(jsonOld.getDouble("totalDeduction")*facto)+"\n"+numfor.format(jsonOld.getDouble("totalDeduction")*52);
                        deducTotal=numfor.format(json.getDouble("totalDeduction")*facto)+"\n"+numfor.format(json.getDouble("totalDeduction")*52);

                        TaxWageFreg=numfor.format(jsonOld.getDouble("taxableRage")*facto)+"\n"+numfor.format(jsonOld.getDouble("taxableRage")*52);
                        TaxWageTotal=numfor.format(json.getDouble("taxableRage")*facto)+"\n"+numfor.format(json.getDouble("taxableRage")*52);

                        taxFreg=numfor.format(jsonOld.getDouble("totalTaxe")*facto)+"\n"+numfor.format(jsonOld.getDouble("totalTaxe")*52);
                        taxTotal=numfor.format(json.getDouble("totalTaxe")*facto)+"\n"+numfor.format(json.getDouble("totalTaxe")*52);

                        netFreq=numfor.format(jsonOld.getDouble("payNet")*facto)+"\n"+numfor.format(jsonOld.getDouble("payNet")*52);
                        netTotal=numfor.format(json.getDouble("payNet")*facto)+"\n"+numfor.format(json.getDouble("payNet")*52);



                        /**************************************************************Update Earning Infos*/

                        gainFreq=numfor.format(jsonOld.getDouble("regularPay")*facto)+"\n"+numfor.format(jsonOld.getDouble("regularPay")*52);
                        gainTotal=numfor.format(json.getDouble("regularPay")*facto)+"\n"+numfor.format(json.getDouble("regularPay")*52);

                        otFreq=numfor.format(jsonOld.getDouble("overtimePay")*facto)+"\n"+numfor.format(jsonOld.getDouble("overtimePay")*52);
                        otTotal=numfor.format(json.getDouble("overtimePay")*facto)+"\n"+numfor.format(json.getDouble("overtimePay")*52);

                        otherFreq=numfor.format(jsonOld.getDouble("otherOverTimePay")*facto)+"\n"+numfor.format(jsonOld.getDouble("otherOverTimePay")*52);
                        otherTotal=numfor.format(json.getDouble("otherOverTimePay")*facto)+"\n"+numfor.format(json.getDouble("otherOverTimePay")*52);






                        /**************************************************************Update Deduction Infos*/

                        standDeducFreq=numfor.format(jsonOld.getDouble("standardDeduction")*facto)+"\n"+numfor.format(jsonOld.getDouble("standardDeduction")*52);
                        standDeducTotal=numfor.format(json.getDouble("standardDeduction")*facto)+"\n"+numfor.format(json.getDouble("standardDeduction")*52);

                        persoFreq=numfor.format(jsonOld.getDouble("personalDeduction")*facto)+"\n"+numfor.format(jsonOld.getDouble("personalDeduction")*52);
                        persoTotal=numfor.format(json.getDouble("personalDeduction")*facto)+"\n"+numfor.format(json.getDouble("personalDeduction")*52);

                        otherDeducFreg=numfor.format(jsonOld.getDouble("otherDeduction")*facto)+"\n"+numfor.format(jsonOld.getDouble("otherDeduction")*52);
                        otherDeducTotal=numfor.format(json.getDouble("otherDeduction")*facto)+"\n"+numfor.format(json.getDouble("otherDeduction")*52);





                        /**************************************************************Update Tax Infos*/

                        fedTax=numfor.format(jsonOld.getDouble("federalTaxe")*facto)+"\n"+numfor.format(jsonOld.getDouble("federalTaxe")*52);
                        fedTaxTotal=numfor.format(json.getDouble("federalTaxe")*facto)+"\n"+numfor.format(json.getDouble("federalTaxe")*52);

                        secuTax=numfor.format(jsonOld.getDouble("socialSecuTaxe")*facto)+"\n"+numfor.format(jsonOld.getDouble("socialSecuTaxe")*52);
                        secuTaxTotal=numfor.format(json.getDouble("socialSecuTaxe")*facto)+"\n"+numfor.format(json.getDouble("socialSecuTaxe")*52);

                        mCareTax=numfor.format(jsonOld.getDouble("medicareTaxe")*facto)+"\n"+numfor.format(jsonOld.getDouble("medicareTaxe")*52);
                        mCareTaxTotal=numfor.format(json.getDouble("medicareTaxe")*facto)+"\n"+numfor.format(json.getDouble("medicareTaxe")*52);

                        otherTax=numfor.format(jsonOld.getDouble("otherTaxe")*facto)+"\n"+numfor.format(jsonOld.getDouble("otherTaxe")*52);
                        otherTaxTotal=numfor.format(json.getDouble("otherTaxe")*facto)+"\n"+numfor.format(json.getDouble("otherTaxe")*52);


                    }





                        /******************************************************General infos*/

                        txtTabFreq.setText(tabFreg);
                        txtTabTotal.setText(tabTotal);

                        //txtGrossInTitle.setText();
                        txtGrossFreq.setText(gross);
                        txtGrossTotal.setText(annualGross);

                        //txtDeducTitle.setText();
                        txtDeducFreq.setText(deducFreq);
                        txtDeducTotal.setText(deducTotal);

                        //txtTaxWageTitle.setText();
                        txtTaxWageFreq.setText(TaxWageFreg);
                        txtTaxWageTotal.setText(TaxWageTotal);

                        //txtTaxTitle.setText();
                        txtTaxFreq.setText(taxFreg);
                        txtTaxTotal.setText(taxTotal);

                        //txtNetTitle.setText();
                        txtNetFreq.setText(netFreq);
                        txtNetTotal.setText(netTotal);







                        /******************************************************Earning*/
                        txtTabGainFreq.setText(tabFreg);
                        txtTabGainTotal.setText(tabTotal);

                        //txtGainTitle.setText();
                        txtGainFreq.setText(gainFreq);
                        txtGainTotal.setText(gainTotal);

                        //txtOtTitle.setText();
                        txtOtFreq.setText(otFreq);
                        txtOtTotal.setText(otTotal);

                        //txtOtherInTitle.setText();
                        txtOtherInFreq.setText(otherFreq);
                        txtOtherInTotal.setText(otherTotal);








                        /******************************************************Deductions*/

                        txtTabDeducFreq.setText(tabFreg);
                        txtTabDeducTotal.setText(tabTotal);

                        //txtDeducStandTitle.setText();
                        txtDeducStandFreq.setText(standDeducFreq);
                        txtDeducStandTotal.setText(standDeducTotal);

                        //txtDeducPersoTitle.setText();
                        txtDeducPersoFreq.setText(persoFreq);
                        txtDeducPersoTotal.setText(persoTotal);

                        //txtDeducOtherTitle.setText();
                        txtDeducOtherFreq.setText(otherDeducFreg);
                        txtDeducOtherTotal.setText(otherDeducTotal);








                        /******************************************************Taxes*/

                        txtTabTaxFreq.setText(tabFreg);
                        txtTabTaxTotal.setText(tabTotal);

                        //txtTaxFedTitle.setText();
                        txtTaxFedFreq.setText(fedTax);
                        txtTaxFedTotal.setText(fedTaxTotal);

                        //txtTaxSsecuTitle.setText();
                        txtTaxSsecuFreq.setText(secuTax);
                        txtTaxSsecuTotal.setText(secuTaxTotal);

                        //txtTaxMcareTitle.setText();
                        txtTaxMcareFreq.setText(mCareTax);
                        txtTaxMcareTotal.setText(mCareTaxTotal);

                        //txtTaxOtherTitle.setText();
                        txtTaxOtherFreq.setText(otherTax);
                        txtTaxOtherTotal.setText(otherTaxTotal);


                        //frequencyTab.setText(" Frequency\n("+json.getString("payFrequency")+")");frequenPay









                    /*************************Reception des elements indispensable pour parler au user
                     * s'il a cocher les fonctions vocales******************************************************/

                    txtSpeaker = new TextToSpeech(this, this);

                    //btnSpeak = (Button) findViewById(R.id.btnSpeak);

                    /*txtToSpeak = (TextView) findViewById(R.id.txtToSpeak);

                    shared = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    boolean useSpeech = (shared.getBoolean(getString(R.string.speech_util_name), false));

                    if(useSpeech){

                        try{
                            String txtResult="With informations provided, such as pay rate :"+numfor.format(json.getDouble("hourRate"))+"." +
                                    " Pay frequency : " +frequenPay+" and other options"+
                                    ".  Your net pay is : "+numfor.format(json.getDouble("payNet")*facto)+"." +
                                    "Take a look below for more details.";

                            txtToSpeak.setText(txtResult);

                            speakOut();

                        }
                        catch (Exception ex){


                        }
                    }*/


                }
                catch(Exception ex){

                    ex.printStackTrace();

                }

        }
        else{//on return a la page d'accueil


             Intent intent2 = new Intent(AfficheResultCompa.this, MainWithTabs.class);
            //intent.putExtra("listArt", result);
            startActivity(intent2);
        }
    }


    private void saveCalcul(String typeHisto,String result){

        SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        SharedPreferences.Editor edit=shared.edit();


        try{

            String lstStr=shared.getString(typeHisto,"");
            TreeMap<Long,String> map=new TreeMap<>();

            if(lstStr!=null && !lstStr.isEmpty()){

                StringTokenizer tok=new StringTokenizer(lstStr,"#");



                while (tok.hasMoreTokens()){

                    String buf=tok.nextToken();
                    StringTokenizer token=new StringTokenizer(buf,"_");
                    //Log.e("MainFragment","saveHistorique was in ->"+token.toString());
                    String tokenDate=token.nextToken();
                    map.put(0-Long.valueOf(tokenDate),buf);


                }


            }

            //Log.e("MainFragment","saveHistorique to put in ->"+result);
            StringTokenizer tok=new StringTokenizer(result,"_");
            JSONObject js=new JSONObject(tok.nextToken());
            map.put(0-js.getLong("timeCalcul"),js.getLong("timeCalcul")+"_"+result);



            Iterator<String> it=map.values().iterator();
            int i=0;
            StringBuilder build=new StringBuilder();

            while (it.hasNext() && i<10){

                build.append(it.next()+"#");

                i++;

            }

            edit.putString(typeHisto,build.toString());
            //edit.putString("resultToShow","show:0_mode:2_"+result);
            edit.commit();
        }
        catch (Exception ex){

            FirebaseCrash.logcat(Log.ERROR, "TAG", "crash while saving historique");
        }




    }

    private View.OnClickListener backToEval = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent(AfficheResultCompa.this, MainWithTabs.class);
            //intent.putExtra("listArt", result);
            startActivity(intent);

        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_affiche_result, menu);
        return true;
    }


    @Override
    public void onDestroy() {
        // Don't forget to shutdown tts!
        if (txtSpeaker != null) {
            txtSpeaker.stop();
            txtSpeaker.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public void onInit(int status) {

        if (status == TextToSpeech.SUCCESS) {

            int result = txtSpeaker.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                //Log.e("TTS", "This Language is not supported");
            } else {
                //btnSpeak.setEnabled(true);
                //speakOut();
            }

        } else {
            //Log.e("TTS", "Initilization Failed!");
        }

    }

    private void speakOut() {

        String text = txtToSpeak.getText().toString();

        txtSpeaker.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }


    @SuppressLint("NewApi")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        switch(item.getItemId())
        {


            /*case R.id.action_settings:
                Intent i = new Intent(this, UserSettingsActivity.class);
                startActivityForResult(i, RESULT_SETTINGS);
                return true;*/

            case R.id.action_settings:
                Intent in = new Intent(this, UserSettingsActivity.class);
                startActivityForResult(in, RESULT_SETTINGS);
                return true;

            case MENU_DESACTIVER:
                //item.getMenuInfo().targetView.setEnabled(false);
                item.getActionView().setEnabled(false);
                return true;
            case MENU_RETOUR:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
