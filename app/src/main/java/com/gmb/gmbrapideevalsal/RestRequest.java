package com.gmb.gmbrapideevalsal;


import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by gmb on 3/29/2015.
 */
public class    RestRequest {

    /*private static final String NAMESPACE2 ="http://deploybytom-gmbcyr.rhcloud.com/GMBserver/Trait";
    private static final java.lang.String MAIN_REQUEST_URL2 = "http://deploybytom-gmbcyr.rhcloud.com/GMBserver/Trait?WSDL";
    private static final String SOAP_ACTION_2 = "http://deploybytom-gmbcyr.rhcloud.com/GMBserver/Trait/";
    //private static final String SOAP_ACTION2 = "http://192.168.0.13:8080/Devise/services/Convertisseur/";
    private static  String SESSION_ID2 = "1234";*/


    private static final String BASE_URL ="http://deploybytom-gmbcyr.rhcloud.com/ServerGMB";

    //private static final String BASE_URL ="http://192.168.1.106:8080/ServerGMB";

    public static String GET(String... params){



        String url=BASE_URL;

        for(int i=0;i<params.length;i++){

            url=url+"/"+params[i];
        }

        InputStream inputStream = null;
        String result = "";

        try {
            URL lien = new URL(url);
            HttpURLConnection urlConnection = (HttpURLConnection) lien.openConnection();
            inputStream = new BufferedInputStream(urlConnection.getInputStream());
        } catch (Exception e ) {
            System.out.println("RestRequest Error lors de getImputStream"+e.getMessage());
            return e.getMessage();
        }

        try {

            /*System.err.println("RestRequest Get pos 0->"+url);
            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            System.err.println("RestRequest Get pos 1->"+url);
            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));

            System.err.println("RestRequest Get pos 2->"+url);

            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            System.err.println("RestRequest Get pos 3->"+url);*/
            // convert inputstream to string
            if(inputStream != null){

                System.err.println("RestRequest Get pos 4->"+url);
                result = convertInputStreamToString(inputStream);
                System.err.println("RestRequest Get pos 5->"+result);

                //result=convertStringToJson(result);
                System.err.println("RestRequest Get pos 6->"+result);
            }

            else
                result = "0-Did not work!";

            System.err.println("RestRequest Get pos 7->"+result);

        } catch (Exception e) {
            //Log.d("InputStream", e.getLocalizedMessage());
            System.err.println("RestRequest Get Error->"+e.getMessage());
            result = "0-Did not work!";
        }

        return result;
    }


    public static String TestConnecServer(String... params){



        String url=BASE_URL;

        for(int i=0;i<params.length;i++){

            url=url+"/"+params[i];
        }

        InputStream inputStream = null;
        String result = "";

        try {
            URL lien = new URL(url);
            HttpURLConnection urlConnection = (HttpURLConnection) lien.openConnection();
            inputStream = new BufferedInputStream(urlConnection.getInputStream());
        } catch (Exception e ) {
            System.out.println("RestRequest Error lors de getImputStream"+e.getMessage());
            return e.getMessage();
        }
        try {

           /* System.err.println("RestRequest TestConnecServer pos 0->"+url);
            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            System.err.println("RestRequest TestConnecServer pos 1->"+url);
            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));

            System.err.println("RestRequest TestConnecServer pos 2->"+url);

            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            System.err.println("RestRequest TestConnecServer pos 3->"+url);
            // convert inputstream to string*/
            if(inputStream != null){

                System.err.println("RestRequest TestConnecServer pos 4 stream->"+inputStream);
                result = convertInputStreamToString(inputStream);
                System.err.println("RestRequest TestConnecServer pos 41 avec result->"+result);
                result=convertStringToJson(result);
                System.err.println("RestRequest TestConnecServer pos 5->"+result);

                //result=convertStringToJson(result);
                System.err.println("RestRequest TestConnecServer pos 6->"+result);
            }

            else
                result = "0-Did not work!";

            System.err.println("RestRequest Get pos 7->"+result);

        } catch (Exception e) {
            //Log.d("InputStream", e.getLocalizedMessage());
            System.err.println("RestRequest Get Error->"+e.getMessage());
            result = "0-Did not work!";
        }

        return result;
    }

    public static boolean Test(boolean deepTest){



        String url= deepTest? "https://www.google.com/?gws_rd=ssl#q=test":"https://www.google.com/?gws_rd=ssl";



        InputStream inputStream = null;
        boolean result = true;
        try {

            System.out.println("\n\n ResRequest Test Pos 0");
            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            System.out.println("\n\n ResRequest Test Pos 1");
            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));

            System.out.println("\n\n ResRequest Test Pos 2");
            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            System.out.println("\n\n ResRequest Test Pos 3");
            // convert inputstream to string
            if(inputStream != null){

                System.out.println("\n\n ResRequest Test Pos 4");
                String res = convertInputStreamToString(inputStream);

                System.out.println("\n\n ResRequest Test Pos 5");
                res=convertStringToJson(res);
                System.out.println("\n\n ResRequest Test Pos 6");
            }

            else{
                result = false;
                System.out.println("\n\n ResRequest Test Pos 7");
            }


        } catch (Exception e) {
           // Log.d("InputStream", e.getLocalizedMessage());
           // Log.d("InputStream", e.getMessage());
            result =false;
            System.out.println("\n\n ResRequest Test Pos 8");
        }

        System.out.println("\n\n ResRequest Test Pos 9");
        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }


    private static String convertStringToJson(String strIn) throws JSONException {

        String line = "";
        String result = "";

        JSONObject json = new JSONObject(strIn); // convert String to JSONObject
        /*JSONArray articles = json.getJSONArray("articleList"); // get articles array
        articles.length(); // --> 2
        articles.getJSONObject(0) // get first article in the array
        articles.getJSONObject(0).names() // get first article keys [title,url,categories,tags]
        articles.getJSONObject(0).getString("url") // return an article url*/

        result=json.getString("strOut");


        return result;

    }

   /* public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }*/




   /* private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            return GET(urls[0]);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), "Received!", Toast.LENGTH_LONG).show();
            etResponse.setText(result);
        }
    }*/

   /* public void verifyEmail(View view) {

        EditText emailEditText = (EditText) findViewById(R.id.email_address);
        String email = emailEditText.getText().toString();

        if( email != null && !email.isEmpty()) {

            String urlString = apiURL + "LicenseInfo.RegisteredUser.UserID=" + strikeIronUserName + "&LicenseInfo.RegisteredUser.Password=" + strikeIronPassword + "&VerifyEmail.Email=" + email + "&VerifyEmail.Timeout=30";

            new CallAPI().execute(urlString);

        }
    }*/
}
