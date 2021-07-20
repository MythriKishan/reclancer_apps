package com.mythri.reclancer_app.Freelancer;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
import com.mythri.reclancer_app.Model.FDashAds;
import com.mythri.reclancer_app.Model.F_RDashAds;
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
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FRDash extends Activity {

    TextView cat_name, mailid,adid;
    private CardView card_view;

    private FDashRAdapter retrofitAdapter;
    private RecyclerView recyclerView;

    FDashboardAdapter adapter;

    private APIFLogout lAPIService;
    List<F_RDashAds> DAds = new ArrayList<>();

    LinearLayoutManager llm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f_rdash);
        final String token = ((Globals) this.getApplication()).getToken();
        Toast.makeText(getApplicationContext(), token, Toast.LENGTH_SHORT).show();

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
            new FRDash.SendDataToServer().execute(String.valueOf(post_dict));

        }
    }
        class SendDataToServer extends AsyncTask<String, String, String> {

            @Override
            protected String doInBackground(String... params) {
                String JsonResponse = null;
                String JsonDATA = params[0];
                HttpURLConnection urlConnection = null;
                BufferedReader reader = null;
                try {
                    URL url = new URL("https://reclancer.com/appfree_Rdashads.php");
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


            ArrayList<F_RDashAds> modelRecyclerArrayList = new ArrayList<>();
            JSONArray dataArray = new JSONArray(json);

            for (int i = 0; i < dataArray.length(); i++) {

                F_RDashAds modelRecycler = new F_RDashAds();
                JSONObject dataobj = dataArray.getJSONObject(i);

                modelRecycler.setName(dataobj.getString("name"));
                modelRecycler.setEmail(dataobj.getString("email"));
                modelRecycler.setAd_id(dataobj.getString("ad_id"));
                modelRecycler.setmainAd_id(dataobj.getString("mainad_id"));

                modelRecyclerArrayList.add(modelRecycler);

            }

            retrofitAdapter = new FDashRAdapter(this,modelRecyclerArrayList);
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
                            Intent home = new Intent(FRDash.this, FHome.class);
                            home.putExtra("Id",Id);
                            startActivity(home);
                            return true;

                        case R.id.postad:
                            Intent post = new Intent(FRDash.this, FPostAd.class);
                            post.putExtra("Id",Id);
                            post.putExtra("token",Token);
                            startActivity(post);
                            return true;

                        case R.id.mfjobs:
                            Intent manage = new Intent(FRDash.this, ManageFJobs.class);
                            manage.putExtra("Id",Id);
                            manage.putExtra("token",Token);
                            startActivity(manage);
                            return true;

                        case R.id.fdash:
                            Intent dash = new Intent(FRDash.this, F_Dash.class);
                            dash.putExtra("Id",Id);
                            dash.putExtra("token",Token);
                            startActivity(dash);
                            return true;

                        case R.id.fRdash:
                            Intent Rdash = new Intent(FRDash.this, FRDash.class);
                            Rdash.putExtra("Id",Id);
                            Rdash.putExtra("token",Token);
                            startActivity(Rdash);
                            return true;

                        case R.id.psettings:
                            Intent pset = new Intent(FRDash.this, F_PSettigs.class);
                            pset.putExtra("Id",Id);
                            startActivity(pset);
                            return true;

                        case R.id.rsearch:
                            Intent search = new Intent(FRDash.this, RSearch.class);
                            search.putExtra("Id",Id);
                            startActivity(search);
                            return true;

                        case R.id.flogout:

                            lAPIService = ApiUtil.getAPI_Flogout();

                            logOutMethod();



                        default:
                            return false;
                    }



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

            Intent intent = new Intent(FRDash.this, FLogin.class);
            finish();
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            overridePendingTransition(0, 0);
            startActivity(intent);
        }


}
