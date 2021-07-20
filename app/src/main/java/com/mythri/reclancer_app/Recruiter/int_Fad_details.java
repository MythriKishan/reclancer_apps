package com.mythri.reclancer_app.Recruiter;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.mythri.reclancer_app.Globals;
import com.mythri.reclancer_app.Network.APIFLogout;
import com.mythri.reclancer_app.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class int_Fad_details extends Activity {
    TextView skill, exps,fname,mail,phone,pr,paddrs,d,sdate,edate,wtype,s,ci;
    String user_id;

    private APIFLogout lAPIService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_details);
        Intent myIntent = getIntent();
        String ad_id = myIntent.getStringExtra("adId");
        String mad_id = myIntent.getStringExtra("madId");

        final String id = myIntent.getStringExtra("Id");
        Toast.makeText(getApplicationContext(), ad_id, Toast.LENGTH_SHORT).show();

        String token = ((Globals) this.getApplication()).getToken();
        Toast.makeText(getApplicationContext(),token,Toast.LENGTH_SHORT).show();

        JSONObject post_dict = new JSONObject();

        try {
            post_dict.put("adid", ad_id);
            post_dict.put("mad_id", mad_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (post_dict.length() > 0) {
            new int_Fad_details.SendJsonDataToServer().execute(String.valueOf(post_dict));

        }

        fname =(TextView) findViewById(R.id.name);
        mail = (TextView) findViewById(R.id.email);
        phone = (TextView) findViewById(R.id.mobile);
        paddrs =(TextView) findViewById(R.id.paddress);
        pr = (TextView) findViewById(R.id.prates);
        s = (TextView) findViewById(R.id.state);
        ci = (TextView) findViewById(R.id.city);
        sdate = (TextView) findViewById(R.id.psdate);
        edate = (TextView) findViewById(R.id.pedate);
        wtype = (TextView) findViewById(R.id.pwtype);

        skill = (TextView) findViewById(R.id.skills);
        exps = (TextView) findViewById(R.id.exp);



        //pr = (TextView)findViewById(R.id.pref);

        /*try {
            details();
        } catch (JSONException e) {
            e.printStackTrace();
        }*/

    }





    class SendJsonDataToServer extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String JsonResponse = null;
            String JsonDATA = params[0];
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL("https://reclancer.com/app_Fad_details.php");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setDoOutput(true);
                // is output buffer writter
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Accept", "application/json");
                //set headers and method
                Writer writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8"));
                writer.write(JsonDATA);
                // json data
                writer.close();

                InputStream inputStream = urlConnection.getInputStream();
                //input stream
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String inputLine;
                while ((inputLine = reader.readLine()) != null)
                    buffer.append(inputLine + "\n");
                if (buffer.length() == 0) {
                    // Stream was empty. No point in parsing.
                    return null;
                }
                JsonResponse = buffer.toString();
                //response data

                // Log.i(TAG,JsonResponse);

                //Toast.makeText(getApplicationContext(),JsonResponse,Toast.LENGTH_SHORT).show();

                //send to post execute
                return JsonResponse;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        //Log.e(TAG, "Error closing stream", e);
                    }
                }
            }
            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();

            //loadData(s);

            try {
                details(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private void details(String json) throws JSONException {


        JSONArray jsonarray = new JSONArray(json);

        for (int i = 0; i < jsonarray.length(); i++) {

            JSONObject c = jsonarray.getJSONObject(i);

           /* final String sk = c.getString("skills");
            Toast.makeText(getApplicationContext(),sk,Toast.LENGTH_SHORT).show();
            final String ex = c.getString("exp");
            Toast.makeText(getApplicationContext(),ex,Toast.LENGTH_SHORT).show();
            final String t = c.getString("job_title");
            Toast.makeText(getApplicationContext(),t,Toast.LENGTH_SHORT).show();
            final String n = c.getString("name");

            final String e_mail = c.getString("email");
            final String contact = c.getString("mobile");
            final String ssk = c.getString("sec_skills");
            final String p_address = c.getString("address");
            final String p_period = c.getString("period");
            final String s_date = c.getString("start_date");
            final String e_date = c.getString("end_date");
            final String resp = c.getString("des");

            //user_id = c.getString("id");

            p_skill.setText(sk);
            s_skill.setText(ssk);
            exps.setText(ex);
            jtitle.setText(n);
            mail.setText(e_mail);
            phone.setText(contact);
            padd.setText(p_address);
            pper.setText(p_period);
            sd.setText(s_date);
            ed.setText(e_date);
            d.setText(resp);*/


            final String sk = c.getString("skills");
            Toast.makeText(getApplicationContext(), sk, Toast.LENGTH_SHORT).show();
            final String ex = c.getString("exp");
            Toast.makeText(getApplicationContext(), ex, Toast.LENGTH_SHORT).show();
            final String n = c.getString("name");
            final String e_mail = c.getString("email");
            final String contact = c.getString("mobile");
            final String p_address = c.getString("project_address");
            final String p_rates = c.getString("project_rates");
            final String ps = c.getString("state");
            final String pc = c.getString("city");
            final String wt = c.getString("wtype");
            final String s_date = c.getString("sdate");
            Toast.makeText(getApplicationContext(), s_date, Toast.LENGTH_SHORT).show();
            final String e_date = c.getString("edate");
            //final String p_ref = c.getString("pref");

            fname.setText(n);
            mail.setText(e_mail);
            phone.setText(contact);
            paddrs.setText(p_address);
            pr.setText(p_rates);
            s.setText(ps);
            ci.setText(pc);
            wtype.setText(wt);
            sdate.setText(s_date);
            edate.setText(e_date);

            skill.setText(sk);
            exps.setText(ex);




        }

    }
}
