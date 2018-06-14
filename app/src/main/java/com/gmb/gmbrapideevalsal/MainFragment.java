package com.gmb.gmbrapideevalsal;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.speech.RecognizerIntent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gmb.gmbrapideevalsal.tools.Trait;
import com.gmb.gmbrapideevalsal.tools.convert;
import com.google.firebase.crash.FirebaseCrash;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.TreeMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    static String TAG="ASC_MAIN";

    private String result;
    String speakInput;

    Spinner cmbFreq=null;

    RelativeLayout contentVal,contentProgres;
    ProgressDialog progress;
    EditText amount=null;
    Button valide = null;

    TextView getSpeak=null;
    TextView txtMore=null;

    private ImageButton btnSpeak;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    List<String[]> dataSpeech = new ArrayList<String[]>();
    int speechNo=0;
    int nbreSpeechFail=0;
    List<String> lstVal;


    private  List<String> EXIT_CMD_LIST;
    private  List<String> CANCEL_CMD_LIST;
    private  List<String> EVALUATE_CMD_LIST;
    private  List<String> YEARLY_RATE_CMD_LIST;
    private  List<String> HOURLY_RATE_CMD_LIST;

    private  List<String> WEEKLY_RATE_CMD_LIST;
    private  List<String> WEEKLY_2_RATE_CMD_LIST;
    private  List<String> MONTHLY_RATE_CMD_LIST;

    private OnFragmentInteractionListener mListener;

    public Handler handler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            Log.e("MainActivity"," MainActivity je suis dans le Handler ");
            switch (msg.what) {

                case 0:
                    Log.e("MainActivity"," MainActivity je suis dans le Handler cas 1 avec result->"+result);
                    if(result!=null && result.length()>0){

                        JSONObject json;
                        String test="0";
                        try{

                            StringTokenizer tok=new StringTokenizer(result,"_");


                            json = new JSONObject(tok.nextToken());
                            test=json.getString("calculSucces");
                        }
                        catch(Exception ex){


                        }

                        if(test.startsWith("1")){//BOn calcul dans ce cas


                            String act=lstVal.get(cmbFreq.getSelectedItemPosition());

                            if(getString(R.string.call_method_from_y).equalsIgnoreCase(act)){

                                act="gros";
                            }
                            else{

                                act="hourly";

                            }


                            /*Intent intent = new Intent(getActivity(), AfficheResult.class);
                            intent.putExtra("result", result);
                            intent.putExtra("typePay",act);
                            startActivity(intent);*/

                            /*Snackbar snackbar = Snackbar
                                    .make(getActivity().findViewById(android.R.id.content), getString(R.string.save_result_lbl), Snackbar.LENGTH_LONG)
                                    .setAction(getString(R.string.save_result), new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                            saveHistorique(HistoriqueFragment.TYPE_HISTO_SAVED,result);

                                            Snackbar.make(getActivity().findViewById(android.R.id.content), getString(R.string.completed),
                                                    Snackbar.LENGTH_SHORT).show();
                                        }
                                    });
                            snackbar.show();*/


                            //ResultFragment frag=ResultFragment.newInstance(result,act);
                            Log.e("MainActivity"," MainActivity je suis dans le Handler cas 1 avec launch->"+test);
                            //mListener.launchFragment(null,false,2);
                            //prepareAndSendNotif(result);

                            Intent intent = new Intent(getActivity(), AfficheResultCompa.class);
                            intent.putExtra("result", "show:0_mode:1_"+result);

                            startActivity(intent);

                        }
                        else{

                            Toast toast=Toast.makeText(getActivity(),"SORRY, UNABLE TO GET EVALUATION WITH INFORMATIONS PROVIDED!",Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
                            toast.show();
                            //affiche.setText("Sorry unable to contact the server");
                            break;

                        }



                        /*StringTokenizer tok=new StringTokenizer(result,"_");
                        String affiche="";

                        tok.nextToken();

                        Log.e("MainActivity"," MainActivity je suis dans le Handler ->"+result);

                        while(tok.hasMoreTokens()){

                            affiche=affiche+tok.nextToken()+"\n";
                        }

                        result=affiche;*/
                    }

            }
            return false;
        }
    });

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    //Calcule le resultat
    private View.OnClickListener resultat = new View.OnClickListener() {
        @Override
        public void onClick(View v) {



            makeCalculation();

        }
    };


    private void makeCalculation(){

        String entry=amount.getText().toString();


        SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String hDay = (shared.getString(getString(R.string.hour_per_day_name), "8"));
        String dWeek = (shared.getString(getString(R.string.day_per_week_name), "5"));

        String dependParam = (shared.getString(getString(R.string.nbre_dependant), "1"));
        String depen="1";
            /*
            * N.B. will be change when reload ServerGMB. value will be
            * 1=>year
            * 2=>month
            * 3=>week*/
        String perYear=shared.getBoolean(getString(R.string.month_or_year_name),true)? "1":"0";

        String perYear2=shared.getString(getString(R.string.year_to_day_option_name),"1");


        String applicTaxe=shared.getBoolean(getString(R.string.applic_taxe_name),true)? "1":"0";

        String matStatus=shared.getString(getString(R.string.mat_status_name),"1");

        switch (matStatus){

            case "1":
                matStatus="single";
                break;

            case "2":
                matStatus="marriedj";
                depen="2";
                break;

            case "3":
                matStatus="marrieds";
                break;

            case "4":
                matStatus="headh";
                break;
        }

        int dependant=Integer.valueOf(dependParam);

        if(dependant<2) dependant=Integer.valueOf(depen);

        String maxHweek=shared.getString(getString(R.string.max_hour_week_name),"40");

        String hSupRate=shared.getString(getString(R.string.hour_sup_rate_name),"1.5");

        String nOT2=shared.getString(getString(R.string.hour_OT2_name),"0");

        String OT2rate=shared.getString(getString(R.string.rate_OT2_name),"0");


        Log.e("MainActivResultat","\n\n Dans MainActivity click sur resultat, voici ce que je send->"+entry+" " +
                "hDay->"+hDay+" dWeek->"+dWeek+" perYear->"+perYear2);
        //saisie.setText(calcul(init+buf,0));
            /*getResult(entry,hDay,dWeek,perYear2,applicTaxe,matStatus,maxHweek,hSupRate,nOT2,OT2rate);*/

        String act=lstVal.get(cmbFreq.getSelectedItemPosition());


        new MaTacheDeFond().execute(act,dependant+"", matStatus, applicTaxe,hDay,dWeek,
                entry,maxHweek, hSupRate, nOT2,OT2rate);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_main, container, false);

        CANCEL_CMD_LIST = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.cmd_list_cancel)));

        EXIT_CMD_LIST = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.cmd_list_exit)));

        EVALUATE_CMD_LIST = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.cmd_list_evalue)));

        YEARLY_RATE_CMD_LIST = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.cmd_list_yearly)));

        HOURLY_RATE_CMD_LIST = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.cmd_list_hourly)));

        WEEKLY_RATE_CMD_LIST = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.cmd_list_weekly)));

        WEEKLY_2_RATE_CMD_LIST = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.cmd_list_semiweek)));

        MONTHLY_RATE_CMD_LIST = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.cmd_list_monthly)));


        speakInput = CANCEL_CMD_LIST.get(0);

        /*Recup�ration des vues*/
                valide = (Button) v.findViewById(R.id.go);

        cmbFreq = (Spinner) v.findViewById(R.id.cmb_type_pay);
        List<String> list = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.freq_title)));

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.my_custom_spinner_design, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cmbFreq.setAdapter(dataAdapter);

        lstVal = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.freq_values)));

        txtMore = (TextView) v.findViewById(R.id.more);


        getSpeak = (TextView) v.findViewById(R.id.getSpeak);
        getSpeak.setVisibility(View.GONE);
        btnSpeak = (ImageButton) v.findViewById(R.id.btnSpeak);

        // hide the action bar
        //getActionBar().hide();

        btnSpeak.setOnClickListener(this);

        PreferenceManager.setDefaultValues(getActivity(), R.xml.preferences, false);
        SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(getActivity());
        boolean useSpeech = (shared.getBoolean(getString(R.string.speech_util_name), false));

        if (!useSpeech) {
            btnSpeak.setVisibility(View.GONE);
        }

        amount = (EditText) v.findViewById(R.id.amount);

        amount.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    makeCalculation();
                    getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                }
                return handled;
            }
        });


        amount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideSoftInput(getContext(),amount,false);
                }
            }
        });

        contentVal = (RelativeLayout) v.findViewById(R.id.contentField);
        contentProgres = (RelativeLayout) v.findViewById(R.id.contentProgressBar);


        progress = new ProgressDialog(getActivity());
        progress.setMessage("Calculation in progress...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setProgress(0);

        //attribution des listners aux diff�rents �l�ments
        valide.setOnClickListener(resultat);

        txtMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), UserSettingsActivity.class);
                startActivity(intent);
            }
        });


        PreferenceManager.setDefaultValues(getContext(), R.xml.preferences, false);


        //CHeck the connection
        //boolean conec=InternetConnectionChecker.checkNetwork(getApplicationContext(),false);
        boolean conec = InternetConnectionChecker.isNetworkAvaillable(getContext());

        //if not, we inform the user and go to the retry activity.
        if (!conec) {


            Intent intent = new Intent(getContext(), ConnectionFailure.class);
            intent.putExtra("listArt", result);
            startActivity(intent);


        }

        contentProgres.setVisibility(View.GONE);
        contentVal.setVisibility(View.VISIBLE);

        valide.requestFocus();


        return v;
    }

    public  static   void hideSoftInput(Context context, View view, boolean showKeyboard){

        if(showKeyboard){

            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        }
        else{

            if (view != null) {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    private void saveHistorique(String typeHisto,String result){

        SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(getContext());

        SharedPreferences.Editor edit=shared.edit();

        edit.putString("resultToShow","show:0_mode:1_"+AfficheResultCompa.MODE_COMPARE_YES+"_"+result);
        edit.commit();
        try{

            String lstStr=shared.getString(typeHisto,"");
            TreeMap<Long,String> map=new TreeMap<>();

            if(lstStr!=null && !lstStr.isEmpty()){

                StringTokenizer tok=new StringTokenizer(lstStr,"#");



                while (tok.hasMoreTokens()){

                    String buf=tok.nextToken();
                    StringTokenizer token=new StringTokenizer(buf,"_");
                    Log.e("MainFragment","saveHistorique was in ->"+token.toString());
                    String tokenDate=token.nextToken();
                    map.put(0-Long.valueOf(tokenDate),buf);


                }


            }

            Log.e("MainFragment","saveHistorique to put in ->"+result);
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

            //edit.putString(HistoriqueFragment.TYPE_HISTO_SAVED,"");
            //edit.putString(HistoriqueFragment.TYPE_HISTO_HISTO,"");
            edit.putString(typeHisto,build.toString());
            if(i==0){
                edit.putString(HistoriqueFragment.TYPE_HISTO_SAVED,build.toString());
            }
            //edit.putString("resultToShow","show:0_mode:2_"+result);
            edit.commit();
        }
        catch (Exception ex){

            FirebaseCrash.logcat(Log.ERROR, TAG, "crash while saving historique");
        }




    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btnSpeak:

                speechNo=0;
                dataSpeech=new ArrayList<String[]>();
                promptSpeechInput();

                break;
        }
    }

    /**
     * Showing google speech input dialog
     * */
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));

        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            FirebaseCrash.logcat(Log.ERROR, TAG, "crash caused");
            Toast.makeText(getContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);

        public void launchFragment(Fragment newFragment, boolean addToBackStack,int tabNum);
    }


    /**
     * Receiving speech input
     * */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        //Log.e("MainActivity"," OnActivityResult voici ce que je recois->"+requestCode+"_resultCode->"+resultCode+"_data->"+data);

        speakInput="";

        try{

            switch (requestCode) {
                case REQ_CODE_SPEECH_INPUT: {
                    if (resultCode == Activity.RESULT_OK && null != data) {

                        ArrayList<String> result = data
                                .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                        speakInput=result.get(0);

                        getSpeak.setText(speakInput);


                        if(isNumber(speakInput)){// un nombre a ete entre

                            amount.setText(Double.toString(getNumber(speakInput)) );


                        }

                        else if(speakInput==null || speakInput.equalsIgnoreCase("")){

                            speakInput=CANCEL_CMD_LIST.get(0);
                        }

                        //on demande a l'application de changer le mode de calcul
                        else if(YEARLY_RATE_CMD_LIST.contains(speakInput)){

                            cmbFreq.setSelection(lstVal.indexOf(getString(R.string.call_method_from_y)));



                        }

                        //on demande a l'application de changer le mode de calcul pour hourly
                        else if(HOURLY_RATE_CMD_LIST.contains(speakInput)){

                            cmbFreq.setSelection(lstVal.indexOf(getString(R.string.call_method_from_h)));

                        }


                        else if(WEEKLY_RATE_CMD_LIST.contains(speakInput)){

                            cmbFreq.setSelection(lstVal.indexOf(getString(R.string.call_method_from_w)));

                        }


                        else if(WEEKLY_2_RATE_CMD_LIST.contains(speakInput)){

                            cmbFreq.setSelection(lstVal.indexOf(getString(R.string.call_method_from_2w)));

                        }


                        else if(MONTHLY_RATE_CMD_LIST.contains(speakInput)){

                            cmbFreq.setSelection(lstVal.indexOf(getString(R.string.call_method_from_m)));

                        }

                        //on demande a l'application de passer a l'evaluation
                        else if(EVALUATE_CMD_LIST.contains(speakInput)){

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
                                valide.callOnClick();
                            }


                        }

                        //on annule la prochaine prompt de speech.
                        else if(CANCEL_CMD_LIST.contains(speakInput)){


                        }

                        //on ferme l'aplication.
                        else if(EXIT_CMD_LIST.contains(speakInput)){


                            new AlertDialog.Builder(getContext())
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .setTitle(R.string.onback_quit)
                                    .setMessage(R.string.onback_really_quit)
                                    .setPositiveButton(R.string.onback_yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            //Stop the activity
                                            getActivity().finish();
                                        }
                                    })
                                    .setNegativeButton(R.string.onback_no, null)
                                    .show();
                        }

                        else{

                            nbreSpeechFail++;

                            if(nbreSpeechFail>=3){

                                nbreSpeechFail=0;
                                speakInput=CANCEL_CMD_LIST.get(0);
                                Toast toast=Toast.makeText(getContext(),getString(R.string.commande_unknown),Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
                                toast.show();
                            }
                            else{

                                Toast toast=Toast.makeText(getContext(),getString(R.string.no_commande_in_speech)+" '"+speakInput+"'",Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
                                toast.show();
                            }

                        }





                        if(!CANCEL_CMD_LIST.contains(speakInput) && !EVALUATE_CMD_LIST.contains(speakInput)

                                && !EXIT_CMD_LIST.contains(speakInput) && speakInput!=null) {


                            speechNo++;
                            dataSpeech.add(new String[] {"Command No "+speechNo, speakInput});

                            promptSpeechInput();


                            //interactiveSpeech(speakInput);

                        }
                    }
                    break;

                }


            }



        }
        catch (Exception e){
            FirebaseCrash.logcat(Log.ERROR, TAG, "crash caused");

            Log.e("MainActivity"," Main ActivityResult error->");
            e.printStackTrace();
        }

    }

    public boolean isNumber(String txt){

        if(txt.matches("\\d+(?:\\.\\d+)?"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public double getNumber(String txt){

        double res=0;

        try{

            res=Double.parseDouble(txt);

        }
        catch (Exception ex){

            //Log.e("MainActivity"," getNumber parse error->"+ex.getMessage());
        }

        return res;
    }

    /**
     * Use les param dans l'odre suivant
     * final String matStatus,final String applicTaxe,final String hDay,final String dWeek,
     * final String entry,final String maxHweek,final String hSupRate,final String nOT2,final String OT2rate
     */
    private class MaTacheDeFond extends AsyncTask<String, String, String> {

        private Exception exception;


        @Override
        protected void onPreExecute() {
            /*contentProgres.setVisibility(View.VISIBLE);
            contentVal.setVisibility(View.GONE);*/

            try {

                progress.show();
            }
            catch (Exception ex){


            }
            super.onPreExecute();
        }


        @Override
        protected String doInBackground(String... params) {
            try {


                //Log.e("MainActivity"," SygesJour MaTacheDeFond DoInBackGround  AVEC params->"+params);
                //CHeck the connection
                //boolean conec=InternetConnectionChecker.checkNetwork(getApplicationContext(),true);
                /*boolean conec=InternetConnectionChecker.isNetworkAvaillable(getApplicationContext());

                //if not, we inform the user and go to the retry activity.
                if(!conec){



                    Intent intent = new Intent(MainActivity.this, ConnectionFailure.class);
                    intent.putExtra("listArt", result);
                    startActivity(intent);


                    return "0";
                }*/

                Log.e("MainActivity"," SygesJour MaTacheDeFond DoInBackGround pos 2  AVEC params->"+params);

                String action=params[0];

                String[] paramsDbl=new String[params.length];
                String[] paramsDblNewTax=new String[params.length];

                for(int i=1;i<params.length;i++){

                    //Log.e("MainActivity"," SygesJour MaTacheDeFond DoInBackGround pos 21  AVEC params->"+params[i]);

                    try{
                        String buf=params[i];
                        paramsDbl[i]=(buf);
                        paramsDblNewTax[i]=(buf);
                    }
                    catch (Exception e){

                        //Log.e("MainActivity"," SygesJour MaTacheDeFond DoInBackGround  AVEC ERROR PARSOR->"+e.getMessage());
                    }
                }

                Log.e("MainActivity"," SygesJour MaTacheDeFond DoInBackGround pos 3  AVEC params->"+params);

                paramsDbl[0]=2017+"";
                paramsDblNewTax[0]=2018+"";


                String newResult="";


                convert cal=new Trait();

                if(action.equalsIgnoreCase(getString(R.string.call_method_from_y))){


                    Log.e("MainActivity"," SygesJour MaTacheDeFond DoInBackGround call YtH  AVEC params->"+params);
                    newResult=cal.payFromYear(paramsDblNewTax);
                    result=cal.payFromYear(paramsDbl);
                }
                else if(action.equalsIgnoreCase(getString(R.string.call_method_from_h))){

                    Log.e("MainActivity"," SygesJour MaTacheDeFond DoInBackGround call HtY  AVEC params->"+params);
                    newResult=cal.payFromHour(paramsDblNewTax);
                    result=cal.payFromHour(paramsDbl);

                }
                else if(action.equalsIgnoreCase(getString(R.string.call_method_from_w))){

                    Log.e("MainActivity"," SygesJour MaTacheDeFond DoInBackGround call HtY  AVEC params->"+params);
                    newResult=cal.payFromWeek(paramsDblNewTax);
                    result=cal.payFromHour(paramsDbl);

                }
                else if(action.equalsIgnoreCase(getString(R.string.call_method_from_2w))){

                    Log.e("MainActivity"," SygesJour MaTacheDeFond DoInBackGround call HtY  AVEC params->"+params);
                    newResult=cal.payFrom2Weeks(paramsDblNewTax);
                    result=cal.payFromHour(paramsDbl);

                }
                else if(action.equalsIgnoreCase(getString(R.string.call_method_from_m))){

                    Log.e("MainActivity"," SygesJour MaTacheDeFond DoInBackGround call HtY  AVEC params->"+params);
                    newResult=cal.payFromMonth(paramsDblNewTax);
                    result=cal.payFromHour(paramsDbl);

                }

                Log.e("MainActivity"," SygesJour MaTacheDeFond DoInBackGround  AVEC result 1->"+result);


                //result=RestRequest.GET(params);
                //handler.sendEmptyMessage(0);

                result=newResult+"_"+result;


                //Log.e("MainActivity"," SygesJour MaTacheDeFond DoInBackGround  AVEC result 2->"+result);
                return result;
            } catch (Exception e) {
                FirebaseCrash.logcat(Log.ERROR, TAG, "crash occured");
                this.exception = e;
                Log.e("MainActivity"," SygesJour MaTacheDeFond DoInBackGround  AVEC ERROR->"+e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(String res) {

            result=res;

            contentProgres.setVisibility(View.GONE);
            contentVal.setVisibility(View.VISIBLE);

            saveHistorique(HistoriqueFragment.TYPE_HISTO_HISTO,res);

            try {

                progress.hide();
            }
            catch (Exception ex){


            }

            Log.e("MainActivity"," SygesJour MaTacheDeFond onPostExecute pos 0 AVEC result->"+result);
            handler.sendEmptyMessage(0);


        }
    }


}
