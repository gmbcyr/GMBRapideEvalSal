package com.gmb.gmbrapideevalsal;

/**
 * Created by GMB on 1/8/2015.
 */

import android.util.Log;

import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.IOException;
import java.net.Proxy;
import java.net.SocketTimeoutException;
import java.util.List;

/**
 * Created by Junior on 12/24/2014.
 */
public class SoapRequests {


    private static final String NAMESPACE ="http://www.w3schools.com/webservices/" ;
    private static final java.lang.String MAIN_REQUEST_URL = "http://www.w3schools.com/webservices/tempconvert.asmx";
    private static final String SOAP_ACTION = "http://www.w3schools.com/webservices/FahrenheitToCelsius";
    private static  String SESSION_ID = "123";


   /* private static final String NAMESPACE2 ="http://192.168.0.13:8080/GMBserver/services/Trait";
    private static final java.lang.String MAIN_REQUEST_URL2 = "http://192.168.0.13:8080/GMBserver/services/Trait?WSDL";
    private static final String SOAP_ACTION2 = "http://192.168.0.13:8080/GMBserver/services/Trait/calc";
    //private static final String SOAP_ACTION2 = "http://192.168.0.13:8080/Devise/services/Convertisseur/";
    private static  String SESSION_ID2 = "1234";*/

   /* private static final String NAMESPACE2 ="http://server-gmbcyr.rhcloud.com/TestWS/Trait";
    private static final java.lang.String MAIN_REQUEST_URL2 = "http://server-gmbcyr.rhcloud.com/TestWS/Trait?WSDL";
    private static final String SOAP_ACTION_2 = "http://server-gmbcyr.rhcloud.com/TestWS/Trait/";
    //private static final String SOAP_ACTION2 = "http://192.168.0.13:8080/Devise/services/Convertisseur/";
    private static  String SESSION_ID2 = "1234";*/

    private static final String NAMESPACE2 ="http://deploybytom-gmbcyr.rhcloud.com/GMBserver/Trait";
    private static final java.lang.String MAIN_REQUEST_URL2 = "http://deploybytom-gmbcyr.rhcloud.com/GMBserver/Trait?WSDL";
    private static final String SOAP_ACTION_2 = "http://deploybytom-gmbcyr.rhcloud.com/GMBserver/Trait/";
    //private static final String SOAP_ACTION2 = "http://192.168.0.13:8080/Devise/services/Convertisseur/";
    private static  String SESSION_ID2 = "1234";

    public String getCelsiusConversion(String fValue) {
        String data = null;
        String methodname = "calc";

        SoapObject request = new SoapObject(NAMESPACE, methodname);
        request.addProperty("Fahrenheit", fValue);

        SoapSerializationEnvelope envelope = getSoapSerializationEnvelope(request);

        HttpTransportSE ht = getHttpTransportSE();
        try {
            ht.call(SOAP_ACTION, envelope);
            // testHttpResponse(ht);
            SoapPrimitive resultsString = (SoapPrimitive)envelope.getResponse();

            List<HeaderProperty> COOKIE_HEADER = (List<HeaderProperty>)   ht.getServiceConnection().getResponseProperties();

            for (int i = 0; i < COOKIE_HEADER.size(); i++) {
                String key = COOKIE_HEADER.get(i).getKey();
                String value = COOKIE_HEADER.get(i).getValue();

                if (key != null && key.equalsIgnoreCase("set-cookie")) {
                    SoapRequests.SESSION_ID = value.trim();
                    Log.v("SOAP RETURN", "Cookie :" + SoapRequests.SESSION_ID);
                    break;
                }
            }
            data = resultsString.toString();

        } catch (SocketTimeoutException t) {
            t.printStackTrace();
        } catch (IOException i) {
            i.printStackTrace();
        } catch (Exception q) {
            q.printStackTrace();
        }
        return data;
    }


    public String getResult(String fValue,boolean isYearToDay) {
        System.out.println("MainActivity je suis dans SoapRequest getResult  avec toConvert->"+fValue);
        String data = null;
        String methodname = isYearToDay? "payYearToHour":"payHourToYear";

        String SOAP_ACTION2=SOAP_ACTION_2+methodname;

        SoapObject request = new SoapObject(NAMESPACE2, methodname);
        request.addProperty("entry", fValue);


        SoapSerializationEnvelope envelope = getSoapSerializationEnvelope2(request);

        HttpTransportSE ht = getHttpTransportSE2();
        try {
            ht.call(SOAP_ACTION2, envelope);
            // testHttpResponse(ht);
            SoapPrimitive resultsString = (SoapPrimitive)envelope.getResponse();

            List<HeaderProperty> COOKIE_HEADER = (List<HeaderProperty>)   ht.getServiceConnection().getResponseProperties();

            for (int i = 0; i < COOKIE_HEADER.size(); i++) {
                String key = COOKIE_HEADER.get(i).getKey();
                String value = COOKIE_HEADER.get(i).getValue();

                if (key != null && key.equalsIgnoreCase("set-cookie")) {
                    SoapRequests.SESSION_ID2 = value.trim();
                    Log.v("SOAP RETURN", "Cookie :" + SoapRequests.SESSION_ID2);
                    break;
                }
            }
            data = resultsString.toString();

        } catch (SocketTimeoutException t) {
            t.printStackTrace();
        } catch (IOException i) {
            i.printStackTrace();
        } catch (Exception q) {
            q.printStackTrace();
        }

        System.out.println("MainActivity je suis dans SoapRequest getResult resul->"+data);
        return data;
    }


    private final HttpTransportSE getHttpTransportSE() {
        HttpTransportSE ht = new HttpTransportSE(Proxy.NO_PROXY,MAIN_REQUEST_URL,60000);
        ht.debug = true;
        ht.setXmlVersionTag("<!--?xml version=\"1.0\" encoding= \"UTF-8\" ?-->");
        return ht;
    }


    private final HttpTransportSE getHttpTransportSE2() {
        HttpTransportSE ht = new HttpTransportSE(Proxy.NO_PROXY,MAIN_REQUEST_URL2,60000);
        ht.debug = true;
        ht.setXmlVersionTag("<!--?xml version=\"1.0\" encoding= \"UTF-8\" ?-->");
        return ht;
    }


    private final SoapSerializationEnvelope getSoapSerializationEnvelope(SoapObject request) {
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.implicitTypes = true;
        envelope.setAddAdornments(false);
        envelope.setOutputSoapObject(request);

        return envelope;
    }

    private final SoapSerializationEnvelope getSoapSerializationEnvelope2(SoapObject request) {

        System.out.println("MainActivity je suis dans SoapRequest getSoapSerializationEnvelope2");
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        //envelope.dotNet = false;
        envelope.implicitTypes = true;
        envelope.setAddAdornments(false);
        envelope.setOutputSoapObject(request);

        return envelope;
    }
}

