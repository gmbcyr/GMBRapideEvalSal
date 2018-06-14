package com.gmb.gmbrapideevalsal;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class ConnectionFailure extends ActionBarActivity {

    Button retry = null;

    TextView affiche=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection_failure);

        retry = (Button)findViewById(R.id.retry);

        affiche = (TextView)findViewById(R.id.noConnection);


        retry.setOnClickListener(testConnection);



        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

    }




    //Calcule le resultat
    private View.OnClickListener testConnection = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            //CHeck the connection
            //boolean conec=InternetConnectionChecker.checkNetwork(getApplicationContext(),true);
            boolean conec=InternetConnectionChecker.isNetworkAvaillable(getApplicationContext());

            //if not, we inform the user and go to the retry activity.
            if(conec){

               /* Toast toast=Toast.makeText(getApplicationContext(),"CONNECTION FOUND!",Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();*/

                Intent intent = new Intent(ConnectionFailure.this, MainWithTabs.class);
                //intent.putExtra("listArt", result);
                startActivity(intent);



            }
            else{

                Toast toast=Toast.makeText(getApplicationContext(),"SORRY, CONNECTION NOT FOUND!",Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();

            }

        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_connection_failure, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
