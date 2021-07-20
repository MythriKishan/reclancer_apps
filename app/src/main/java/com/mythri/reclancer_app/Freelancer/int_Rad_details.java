package com.mythri.reclancer_app.Freelancer;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.mythri.reclancer_app.Globals;
import com.mythri.reclancer_app.HomePage;
import com.mythri.reclancer_app.Model.TokenJsonObject;
import com.mythri.reclancer_app.Network.APIFLogout;
import com.mythri.reclancer_app.Network.ApiUtil;
import com.mythri.reclancer_app.R;
import com.mythri.reclancer_app.Session;

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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class int_Rad_details extends Activity {

    TextView skill, exps,fname,mail,phone,pr,paddrs,d,sdate,edate,wtype,s,ci;
    String user_id;

    private APIFLogout lAPIService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fad_details);

        String token = ((Globals) this.getApplication()).getToken();
        Toast.makeText(getApplicationContext(),token,Toast.LENGTH_SHORT).show();

        Intent myIntent = getIntent();
        final String ad_id = myIntent.getStringExtra("adId");
        final String mad_id = myIntent.getStringExtra("madId");
        final String id = myIntent.getStringExtra("Id");
        Toast.makeText(getApplicationContext(),ad_id,Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(),mad_id,Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(),id,Toast.LENGTH_SHORT).show();

        JSONObject post_dict = new JSONObject();

        try {
            post_dict.put("adid", ad_id);
            post_dict.put("mad_id", mad_id);
            post_dict.put("user_id", id);
            post_dict.put("token",token);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (post_dict.length() > 0) {
            new int_Rad_details.SendJsonDataToServer().execute(String.valueOf(post_dict));

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


        /*try {
            details();
        } catch (JSONException e) {
            e.printStackTrace();
        }*/

    }


   /* class SendData extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String JsonResponse = null;
            String JsonDATA = params[0];
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL("http://reclancer.com/app_recruiter_addetails.php");
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
                }reader = new BufferedReader(new InputStreamReader(inputStream));

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
            }
            catch (UnsupportedEncodingException e) {
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



        }

    }*/



    class SendJsonDataToServer extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String JsonResponse = null;
            String JsonDATA = params[0];
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL("https://reclancer.com/app_recruiter_addetails.php");
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
                }reader = new BufferedReader(new InputStreamReader(inputStream));

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
            }
            catch (UnsupportedEncodingException e) {
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

    private void details(String json) throws JSONException{


        JSONArray jsonarray = new JSONArray(json);

        for (int i = 0; i < jsonarray.length(); i++) {

            JSONObject c = jsonarray.getJSONObject(i);

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

    public void ShowMenu(View view) {

        PopupMenu popup = new PopupMenu(this, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.main_free, popup.getMenu());
        popup.show();

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                Intent myIntent = getIntent();
                final String Id = myIntent.getStringExtra("Id");
                final String Token = myIntent.getStringExtra("token");

                switch (item.getItemId()) {
                    case R.id.fhome:
                        Intent home = new Intent(int_Rad_details.this, FHome.class);
                        home.putExtra("Id",Id);
                        startActivity(home);
                        return true;

                    case R.id.postad:
                        Intent post = new Intent(int_Rad_details.this, FPostAd.class);
                        post.putExtra("Id",Id);
                        post.putExtra("token",Token);
                        startActivity(post);
                        return true;

                    case R.id.mfjobs:
                        Intent manage = new Intent(int_Rad_details.this, ManageFJobs.class);
                        manage.putExtra("Id",Id);
                        manage.putExtra("token",Token);
                        startActivity(manage);
                        return true;

                    case R.id.psettings:
                        Intent pset = new Intent(int_Rad_details.this, F_PSettigs.class);
                        pset.putExtra("Id",Id);
                        startActivity(pset);
                        return true;

                    case R.id.rsearch:
                        Intent search = new Intent(int_Rad_details.this, RSearch.class);
                        search.putExtra("Id",Id);
                        startActivity(search);
                        return true;

                    case R.id.flogout:

                        lAPIService = ApiUtil.getAPI_Flogout();

                        logOutMethod();

                       /*getLogout("http://reclancer.com/appflogout.php");
                       Session session= new Session(getApplicationContext());
                       session.destroySession();
                       Intent logout = new Intent(FHome.this,FLogin.class);
                       startActivity(logout);
                       return true;*/

                    default:
                        return false;
                }

                //return(super.onMenuItemSelected(1,item));

            }
        });
    }

    private void logOutMethod() {

        Intent myIntent = getIntent();
        final String userId = myIntent.getStringExtra("Id");

        Call<TokenJsonObject> logOut = lAPIService.logOutUser(((Globals) this.getApplication()).getToken(),userId);
        logOut.enqueue(new Callback<TokenJsonObject>() {
            @Override
            public void onResponse(Call<TokenJsonObject> call, Response<TokenJsonObject> response) {

                if (response.isSuccessful()) {

                    Toast tst = Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_SHORT);
                    tst.show();

                    //unset( $GLOBALS['Stoken'] );

                    goToLogInActivity();
                }
                  /*  TokenJsonObject result = response.body();
                    if (result.getData() != null) {
                        Snackbar.make(findViewById(android.R.id.content), result.getMsg(), Snackbar.LENGTH_LONG).show();
                    } else {
                        goToLogInActivity();
                        Snackbar.make(findViewById(android.R.id.content), result.getMsg(), Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(findViewById(android.R.id.content), response.message(), Snackbar.LENGTH_LONG).show();
                }*/
            }

            @Override
            public void onFailure(Call<TokenJsonObject> call, Throwable t) {
                //Log.e(TAG, "Unable to submit post to API.");

                Toast tst = Toast.makeText(getApplicationContext(), "Server Error Retry registering", Toast.LENGTH_SHORT);
                tst.show();

                goToLogInActivity();


            }
        });
    }

    private void goToLogInActivity() {
        //getPreference().removeLoginPreferences();

        ((Globals) this.getApplication()).setToken("");

        String tk = ((Globals)this.getApplicationContext()).Stoken;

        Toast.makeText(getApplicationContext(),tk,Toast.LENGTH_SHORT).show();


        Session session= new Session(getApplicationContext());
        session.destroySession();

        Intent intent = new Intent(int_Rad_details.this, FLogin.class);
        finish();
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        overridePendingTransition(0, 0);
        startActivity(intent);
    }

    public void Rclose(View view) {

    }
}