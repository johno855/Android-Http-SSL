package com.example.johanordin.lab4httpsslapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.message.BasicHttpResponse;

import java.io.InputStream;
import java.net.URL;
import java.security.KeyStore;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;


public class MainActivity extends Activity {

    public ProgressDialog loadingdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("Http/SSL log", "Programmet har startat");

        //Knappar
        Button knapp1 = (Button) findViewById(R.id.knapp1);
        knapp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonAction("https://www.liu.se/");
            }
        });
        Button knapp2 = (Button) findViewById(R.id.knapp2);
        knapp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonAction("https://tal-front.itn.liu.se/");
            }
        });
        Button knapp3 = (Button) findViewById(R.id.knapp3);
        knapp3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonAction("https://tal-front.itn.liu.se:4002/");
            }
        });
        Button knapp4 = (Button) findViewById(R.id.knapp4);
        knapp4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonAction("https://tal-front.itn.liu.se:4023/");
            }
        });

    }

/*    private SSLSocketFactory newSSLSocketFactory() {

        try{
            KeyStore trustStore = KeyStore.getInstance("BKS");
            InputStream in = getResources().openRawResource(R.raw.keystore_lab4);

            try {
                trustStore.load(in, "johan123".toCharArray());
            }finally {
                in.close();
            }


        return new SSLSocketFactory(trustStore);

        }catch (Exception e) {
            throw new AssertionError(e);
        }

    }
    */

    public void buttonAction(final String s_url) {

        loadingdialog = ProgressDialog.show(MainActivity.this, "", "Loading...", true);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //kod ska in här

                    //-------------------------------------------------
                    // Task 1
                    ///*

                    URL url = new URL(s_url);

                    HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                    urlConnection.setConnectTimeout(15000);
                    //urlConnection.setSSLSocketFactory(newSSLSocketFactory());
                    int statusCode;
                    statusCode = urlConnection.getResponseCode();

                    showAlert(s_url, "HTTP Status: " + statusCode);
                    //*/

                    //--------------------------------------------------
                    // Task 2
                    /*
                    KeyStore trustStore = KeyStore.getInstance("BKS");
                    InputStream is = getResources().openRawResource(R.raw.keystore_lab4);

                    trustStore.load(is, "johan123".toCharArray());
                    is.close();

                    TrustManagerFactory tmf = TrustManagerFactory.getInstance("X509");
                    tmf.init(trustStore);

                    SSLContext context = SSLContext.getInstance("TLS");
                    context.init(null, tmf.getTrustManagers(), null);       //original

                    */
                    //--------------------------------------------------

                    // För att acceptera alla certificat
                    /*
                    context.init(null, new TrustManager[] {
                            new X509TrustManager() {
                                public void checkClientTrusted(X509Certificate[] chain, String authType) {}
                                public void checkServerTrusted(X509Certificate[] chain, String authType) {}
                                public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[]{}; }
                            }
                    }, null);
                    */

                    //HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());


                    //--------------------------------------------------
                    /*
                    URL request = new URL(s_url);
                    HttpsURLConnection urlConnection = (HttpsURLConnection) request.openConnection();

                    //urlConnection.setHostnameVerifier(new StrictHostnameVerifier());
                    urlConnection.setHostnameVerifier(new AllowAllHostnameVerifier());
                    urlConnection.setSSLSocketFactory(context.getSocketFactory());
                    urlConnection.setConnectTimeout(15000);

                    InputStream in = urlConnection.getInputStream();
                    //I don't want to change my function's return type (laziness) so I'm building an HttpResponse
                    BasicHttpEntity entity = new BasicHttpEntity();

                    entity.setContent(in);
                    HttpResponse response = new BasicHttpResponse(HttpVersion.HTTP_1_1, urlConnection.getResponseCode(), "");
                    response.setEntity(entity);

                    showAlert(s_url, "HTTP Status: " + response.getStatusLine());

                    */
                    //--------------------------------------------------
                    /*
                    HttpEntity responseEntity = getResponse.getEntity();
-                   //showAlert(s_url, "HTTP Status: " + responseEntity.getContent());

                    BufferedReader in = new BufferedReader(new InputStreamReader(responseEntity.getContent()));

                    String line;
                    while ((line = in.readLine()) != null)
                        System.out.println(line);
                    in.close();

                    */

                    /*
                    // Instantiate the custom HttpClient
                    DefaultHttpClient client = new MyHttpClient(getApplicationContext());

                    HttpGet get = new HttpGet(s_url);

                    // Execute the GET call and obtain the response
                    HttpResponse getResponse = client.execute(get);

                    //HttpEntity responseEntity = getResponse.getEntity();
                    StatusLine responseStatus = getResponse.getStatusLine();

                    showAlert(s_url, "HTTP Status: " + responseStatus.toString());
                    */
                    //--------------------------------------------------




                } catch (Exception e) {
                    e.printStackTrace();
                    showAlert(s_url, "ERROR: " + e.getMessage());
                }
                loadingdialog.dismiss();
            }
        });
        thread.start();
    }

    public void showToast(final String toast) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, toast, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showAlert(final String header, final String message) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle(header);
                alertDialog.setMessage(message);
                alertDialog.setCancelable(false);
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                alertDialog.show();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

