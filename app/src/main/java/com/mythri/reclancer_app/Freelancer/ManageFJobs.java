package com.mythri.reclancer_app.Freelancer;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.mythri.reclancer_app.Globals;
import com.mythri.reclancer_app.Model.FMAds;
import com.mythri.reclancer_app.Model.TokenJsonObject;
import com.mythri.reclancer_app.Network.APIFLogout;
import com.mythri.reclancer_app.Network.ApiUtil;
import com.mythri.reclancer_app.R;
import com.mythri.reclancer_app.Recruiter.ManageRJobs;
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
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageFJobs extends AppCompatActivity {

    TextView cat_name, mailid,adid;
    private CardView card_view;

    private FManageadsAdapter retrofitAdapter;
    private RecyclerView recyclerView;

    FManageadsAdapter adapter;

    private APIFLogout lAPIService;
    List<FMAds> DAds = new ArrayList<>();

    LinearLayoutManager llm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f_mjobs);
                //(R.layout.activity_recycle_fsearch);

        final String token = ((Globals) this.getApplication()).getToken();
        Toast.makeText(getApplicationContext(),token,Toast.LENGTH_SHORT).show();

        recyclerView = (RecyclerView) findViewById(R.id.recycler);

        Intent myIntent = getIntent();
        final String userId = myIntent.getStringExtra("Id");
        Toast.makeText(getApplicationContext(), userId, Toast.LENGTH_SHORT).show();

        JSONObject post_dict = new JSONObject();

        try {
            post_dict.put("userid", userId);
            post_dict.put("token", token);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (post_dict.length() > 0) {
            new ManageFJobs.SendDataToServer().execute(String.valueOf(post_dict));

        }

        /*cat_name = (TextView) findViewById(R.id.category);
        mailid = (TextView) findViewById(R.id.email);
        adid = (TextView)findViewById(R.id.ad_id);
        String adId = adid.getText().toString().trim();
        Toast.makeText(getApplicationContext(),adId,Toast.LENGTH_SHORT).show();

        Button editbtn = (Button)findViewById(R.id.editad);
         editbtn.setOnClickListener(new View.OnClickListener() {

             @Override
             public void onClick(View view) {
                 //adid = (TextView)findViewById(R.id.ad_id);
                 String adId = adid.getText().toString().trim();
                 Toast.makeText(getApplicationContext(),adId,Toast.LENGTH_SHORT).show();
             }


             });*/
         }



   /* public void editAd(View view) {
        adid = (TextView)findViewById(R.id.ad_id);
        String adId = adid.getText().toString().trim();
        Toast.makeText(getApplicationContext(),adId,Toast.LENGTH_SHORT).show();
        Intent edit_ad = new Intent(ManageFJobs.this,EditF_ads.class);
        edit_ad.putExtra("ad_id",adId);
        startActivity(edit_ad);

    }

    public void adstatus(View view) {
        adid = (TextView)findViewById(R.id.ad_id);
        String adId = adid.getText().toString().trim();
        Toast.makeText(getApplicationContext(),adId,Toast.LENGTH_SHORT).show();
        Intent status = new Intent(ManageFJobs.this,F_Adstatus.class);
        status.putExtra("ad_id",adId);
        startActivity(status);
    }*/


    class SendDataToServer extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String JsonResponse = null;
            String JsonDATA = params[0];
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL("https://reclancer.com/appfree_manageads.php");
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

            try {
                displayData(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private void displayData(String json) throws JSONException {


        /*JSONArray responseArray = new JSONArray(json);
        if (responseArray.length() > 0) {

            for (int i = 0; i < responseArray.length(); i++) {
                JSONObject Obj = responseArray.getJSONObject(i);

                //creating object of model class(ModelWarDetails)
                //ModelWarDetails modelWarDetails = new ModelWarDetails();

                FMAds fm = new FMAds();

                fm.setName(Obj.optString("email"));

                //adding data into List
                DAds.add(fm);

            }

            //calling RecyclerViewAdapter constructor by passing context and list
            adapter = new FManageadsAdapter(getApplicationContext(), DAds);

            //setting adapter on recyclerView
            recyclerView.setAdapter(adapter);

            // to notify adapter about changes in list data(if changes)
            adapter.notifyDataSetChanged();

        }*/



        ArrayList<FMAds> modelRecyclerArrayList = new ArrayList<>();
        JSONArray dataArray = new JSONArray(json);

        for (int i = 0; i < dataArray.length(); i++) {

            FMAds modelRecycler = new FMAds();
            JSONObject dataobj = dataArray.getJSONObject(i);

            modelRecycler.setid(dataobj.getString("id"));
            modelRecycler.setEmail(dataobj.getString("email"));
            modelRecycler.setAd_id(dataobj.getString("ad_id"));
            modelRecycler.setCategory(dataobj.getString("category"));


            modelRecyclerArrayList.add(modelRecycler);

        }

        retrofitAdapter = new FManageadsAdapter(this,modelRecyclerArrayList);
        recyclerView.setAdapter(retrofitAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

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
                        Intent home = new Intent(ManageFJobs.this, FHome.class);
                        home.putExtra("Id",Id);
                        home.putExtra("token",Token);
                        startActivity(home);
                        return true;

                    case R.id.postad:
                        Intent post = new Intent(ManageFJobs.this, FPostAd.class);
                        post.putExtra("Id",Id);
                        post.putExtra("token",Token);
                        startActivity(post);
                        return true;

                    case R.id.mfjobs:
                        Intent manage = new Intent(ManageFJobs.this, ManageFJobs.class);
                        manage.putExtra("Id",Id);
                        manage.putExtra("token",Token);
                        startActivity(manage);
                        return true;

                    case R.id.fdash:
                        Intent dash = new Intent(ManageFJobs.this, F_Dash.class);
                        dash.putExtra("Id",Id);
                        dash.putExtra("token",Token);
                        startActivity(dash);
                        return true;

                    case R.id.fRdash:
                        Intent Rdash = new Intent(ManageFJobs.this, FRDash.class);
                        Rdash.putExtra("Id",Id);
                        Rdash.putExtra("token",Token);
                        startActivity(Rdash);
                        return true;

                    case R.id.psettings:
                        Intent pset = new Intent(ManageFJobs.this, F_PSettigs.class);
                        pset.putExtra("Id",Id);
                        pset.putExtra("token",Token);
                        startActivity(pset);
                        return true;

                    case R.id.rsearch:
                        Intent search = new Intent(ManageFJobs.this, RSearch.class);
                        search.putExtra("Id",Id);
                        search.putExtra("token",Token);
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

        Intent intent = new Intent(ManageFJobs.this, FLogin.class);
        finish();
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        overridePendingTransition(0, 0);
        startActivity(intent);
    }

}


