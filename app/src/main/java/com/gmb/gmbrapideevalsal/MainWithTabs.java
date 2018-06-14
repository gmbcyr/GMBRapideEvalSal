package com.gmb.gmbrapideevalsal;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;

import com.gmb.gmbrapideevalsal.tools.BothYearResult;
import com.gmb.gmbrapideevalsal.tools.ViewPageAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainWithTabs extends AppCompatActivity implements MainFragment.OnFragmentInteractionListener,
        HistoriqueFragment.OnListFragmentInteractionListener{

    private static final int RESULT_SETTINGS = 1;
    private static final int REQUEST_INVITE = 5;
    private static final String TAG = "ASC_START_ACT";

    private ImageButton btnSpeak;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    List<String[]> dataSpeech = new ArrayList<String[]>();
    int speechNo=0;
    int nbreSpeechFail=0;
    List<String> lstVal;
    String speakInput;
    // Firebase instance variables
    private FirebaseRemoteConfig mFirebaseRemoteConfig;

    private FirebaseAnalytics mFirebaseAnalytics;

    private Menu m = null;
    private AdView adView;

    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_with_tabs);

        try{



            mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);


            // Initialize Firebase Remote Config.
            mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();



// Define Firebase Remote Config Settings.
            FirebaseRemoteConfigSettings firebaseRemoteConfigSettings =
                    new FirebaseRemoteConfigSettings.Builder()
                            .setDeveloperModeEnabled(true)
                            .build();

// Define default config values. Defaults are used when fetched config values are not
// available. Eg: if an error occurred fetching values from the server.
            Map<String, Object> defaultConfigMap = new HashMap<>();
            defaultConfigMap.put("friendly_msg_length", 10L);

// Apply config settings and default values.
            mFirebaseRemoteConfig.setConfigSettings(firebaseRemoteConfigSettings);
            mFirebaseRemoteConfig.setDefaults(defaultConfigMap);


            //request the ads
            adView = (AdView) findViewById(R.id.adView);
            AdRequest request = new AdRequest.Builder().build();
            adView.loadAd(request);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);



        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_calcul)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_histo)));
        //tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_result)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_saves)));
        //tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_settings)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = (ViewPager) findViewById(R.id.pager);
        final ViewPageAdapter adapter = new ViewPageAdapter(getSupportFragmentManager());
        //adapter.getItem(0);

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        Log.e(TAG,"onCreate this is viewPager->"+viewPager);

        final ViewPager vp=viewPager;
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vp.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

                hideSoftInput(getApplication(),tabLayout,false);
               // viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

                vp.setCurrentItem(tab.getPosition());
            }
        });



        }
        catch (Exception e){

            FirebaseCrash.logcat(Log.ERROR, TAG, "crash caused");
        }
    }



    private void sendInvitation() {
        Intent intent = new AppInviteInvitation.IntentBuilder(getString(R.string.invitation_title))
                .setMessage(getString(R.string.invitation_message))
                .setCallToActionText(getString(R.string.invitation_cta))
                .build();
        startActivityForResult(intent, REQUEST_INVITE);
    }


    /**
     * Receiving speech input
     * */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        //Log.e("MainActivity"," OnActivityResult voici ce que je recois->"+requestCode+"_resultCode->"+resultCode+"_data->"+data);

        speakInput="";

        try{

            switch (requestCode) {
                /*case REQ_CODE_SPEECH_INPUT: {
                    if (resultCode == RESULT_OK && null != data) {

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


                            new AlertDialog.Builder(this)
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .setTitle(R.string.onback_quit)
                                    .setMessage(R.string.onback_really_quit)
                                    .setPositiveButton(R.string.onback_yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            //Stop the activity
                                            finish();
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
                                Toast toast=Toast.makeText(getApplicationContext(),getString(R.string.commande_unknown),Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
                                toast.show();
                            }
                            else{

                                Toast toast=Toast.makeText(getApplicationContext(),getString(R.string.no_commande_in_speech)+" '"+speakInput+"'",Toast.LENGTH_LONG);
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

                }*/
                case REQUEST_INVITE: {
                    if (resultCode == RESULT_OK) {
                        Bundle payload = new Bundle();
                        payload.putString(FirebaseAnalytics.Param.VALUE, "sent");
                        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SHARE,
                                payload);
                        // Check how many invitations were sent and log.
                        String[] ids = AppInviteInvitation.getInvitationIds(resultCode,
                                data);
                        Log.d(TAG, "Invitations sent: " + ids.length);
                    } else {
                        Bundle payload = new Bundle();
                        payload.putString(FirebaseAnalytics.Param.VALUE, "not sent");
                        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SHARE,
                                payload);
                        // Sending failed or it was canceled, show failure message to
                        // the user
                        Log.d(TAG, "Failed to send invitation.");
                    }
                }

            }



        }
        catch (Exception e){
            FirebaseCrash.logcat(Log.ERROR, TAG, "crash caused");

            Log.e("MainActivity"," Main ActivityResult error->");
            e.printStackTrace();
        }

    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //Handle the back button
        if(keyCode == KeyEvent.KEYCODE_BACK && isTaskRoot()) {
            //Ask the user if they want to quit
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle(R.string.onback_quit)
                    .setMessage(R.string.onback_really_quit)
                    .setPositiveButton(R.string.onback_yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //Stop the activity
                            finish();
                        }
                    })
                    .setNegativeButton(R.string.onback_no, null)
                    .show();

            return true;
        }
        else {
            return super.onKeyDown(keyCode, event);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        m = menu;
        return true;
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


            case R.id.invite_menu:
                sendInvitation();
                return true;

            case R.id.menu_settings:
                Intent in = new Intent(this, UserSettingsActivity.class);
                startActivityForResult(in, RESULT_SETTINGS);
                return true;


        }


        if (id == R.id.private_policy) {
            showPrivatePolicy();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /*@Override
    public void onCreateContextMenu(ContextMenu menu, View vue, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, vue, menuInfo);
        menu.add(Menu.NONE, MENU_DESACTIVER, Menu.NONE, "Supprimer cet	element");
        menu.add(Menu.NONE, MENU_RETOUR, Menu.NONE, "Retour");
    }*/


    public void showPrivatePolicy(){


        /*MenuItem mi=(MenuItem) navigationView.findViewById(R.id.nav_draw_expense);
        String title=(mi!=null)?mi.getTitle().toString():titleCur;
        launchFragment(loadFragmentLayout(R.id.nav_draw_expense,title),true);*/

        Intent in = new Intent(this, ShowWebview.class);
        startActivityForResult(in, 2);
    }


    //String transforme en choix 1,2,3 en string
    public String getChoixEnText(String choix){

        String res="";

        switch (choix){

            case "1":
                res="single";
                break;

            case "2":
                res="marrieds";
                break;

            case "3":
                res="marriedj";
                break;

            case "4":
                res="headh";
                break;

        }

        return res;
    }


    @Override
    public void onFragmentInteraction(Uri uri) {


    }

    @Override
    public void launchFragment(final Fragment newFragment, boolean addToBackStack,int tabNum){

           /*Bundle args = new Bundle();
            args.putInt(AddStatementFragment.ARG_POSITION, position);*/



        /*if(newFragment==null) return;


        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );

        hideSoftInput(getApplicationContext(),newFragment.getView(),false);


        //titleCur=((newFragment.getArguments()!=null && newFragment.getArguments().getString("titleFragment")!=null)?
         //       newFragment.getArguments().getString("titleFragment"):titleCur);

        //setBarTitle(titleCur,null);

        //Log.e("Accueil2","Voici fragment to launch->"+newFragment+"_et son title->"+titleCur);

        FragmentManager fM = getSupportFragmentManager();
        //if(addToBackStack) fragmentManager.popBackStack(MY_FRAGMENT_BACK_STACK, FragmentManager.POP_BACK_STACK_INCLUSIVE);


        FragmentTransaction transaction = fM.beginTransaction();


        //Fragment myFragment = (Fragment)getSupportFragmentManager().findFragmentByTag(newFragment.getClass().getName());


        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // only for gingerbread and newer versions
           // transaction.setCustomAnimations(R.anim.anim_left_right,0);
        }


        String fragmentTag=newFragment.getClass().getSimpleName();
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.pager, newFragment,fragmentTag);
        //transaction.replace(R.id.my_main_containt, newFragment);

        if(addToBackStack){

            transaction.addToBackStack(fragmentTag);
            //myBackStack.push(fragmentTag);
        }*/
        //if(addToBackStack) transaction.addToBackStack(Accueil2Activity.MY_FRAGMENT_BACK_STACK);

        /*if (myFragment != null ) {
            Log.e("Accueil2","LaunchFragment found existait deja->"+newFragment+"_et son title->"+titleCur);
            transaction.remove(myFragment);
            List<Fragment> lst=getSupportFragmentManager().getFragments();
            if(lst!=null && lst.contains(myFragment)) lst.remove(myFragment);
        }*/

        // Commit the transaction
        //transaction.commit();

       /* TabLayout.Tab tab = tabLayout.getTabAt(2);

        tab.select();*/

       /*FragmentManager fm = getSupportFragmentManager();
//fragment class name : DFragment
DFragment dFragment = new DFragment();
            // Show DialogFragment
            dFragment.show(fm, "Dialog Fragment");*/

       final int i=tabNum;
        new Handler().postDelayed(
                new Runnable(){
                    @Override
                    public void run() {
                       tabLayout.getTabAt(i).select();

                    }
                }, 100);


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

    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    @Override
    public void onListFragmentInteraction(BothYearResult item) {

        showDetail(item.getRawData());
    }

    private void showDetail(String result){


        try{



            SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

            SharedPreferences.Editor edit=shared.edit();

            edit.putString("resultToShow","show:0_mode:2_"+AfficheResultCompa.MODE_COMPARE_YES+"_"+result);
            edit.commit();


            //launchFragment(null,false,2);


            Intent intent = new Intent(MainWithTabs.this, AfficheResultCompa.class);
            intent.putExtra("result", "show:0_mode:2_"+AfficheResultCompa.MODE_COMPARE_YES+"_"+result);

            startActivity(intent);
        }
        catch (Exception ex){

            FirebaseCrash.logcat(Log.ERROR, TAG, "crash while saving historique");
        }




    }
}
