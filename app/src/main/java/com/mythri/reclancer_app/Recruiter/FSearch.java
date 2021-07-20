package com.mythri.reclancer_app.Recruiter;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mythri.reclancer_app.Freelancer.RSearch;
import com.mythri.reclancer_app.Globals;
import com.mythri.reclancer_app.Model.TokenJsonObject;
import com.mythri.reclancer_app.Network.APIFSearch;
import com.mythri.reclancer_app.Network.APIRLogout;
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

public class FSearch extends AppCompatActivity {

    Spinner state,categry,work;
    TextView pskill;
    String P_skills,cat;
    int stateName;
    int worktype;

    private APIFSearch mAPIService;

    private APIRLogout LAPIService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fsearch);

        final String token = ((Globals) this.getApplication()).getToken();
        Toast.makeText(getApplicationContext(),token,Toast.LENGTH_SHORT).show();

        Intent myIntent = getIntent();
        final String user_id = myIntent.getStringExtra("Id");
        Toast.makeText(getApplicationContext(),user_id,Toast.LENGTH_SHORT).show();


        state = findViewById(R.id.state);
        categry = findViewById(R.id.category);
        work = findViewById(R.id.worktype);

        getJSON("https://reclancer.com/getstate");
        getCat("https://reclancer.com/getcatgry");
        getWork("https://reclancer.com/getworktype");

        pskill = (TextView) findViewById(R.id.skills);


    }



    private void getJSON(final String urlWebService) {

        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                try {
                    loadIntoListView(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void loadIntoListView(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);

        String[] recresults = new String[jsonArray.length()+1];
        recresults[0]="Select State"; //item at pos 0
        int j=1;
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            recresults[j++] = obj.getString("name");
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, recresults);
        state.setAdapter(arrayAdapter);
    }


    private void getCat(final String urlWebService) {

        class GetCat extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                try {
                    loadCategory(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }
        GetCat getCaty = new GetCat();
        getCaty.execute();
    }

    private void loadCategory(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);

        String[] recresults = new String[jsonArray.length()+1];
        recresults[0]="Select Category"; //item at pos 0
        int j=1;
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            recresults[j++] = obj.getString("name");
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, recresults);
        categry.setAdapter(arrayAdapter);
    }


    private void getWork(final String urlWebService) {

        class GetWork extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                try {
                    loadWork(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }

        GetWork getWork = new GetWork();
        getWork.execute();
    }

    private void loadWork(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);

        String[] workresults = new String[jsonArray.length()+1];
        workresults[0]="Select Work Type"; //item at pos 0
        int j=1;
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            workresults[j++] = obj.getString("type");
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, workresults);
        work.setAdapter(arrayAdapter);
    }

    public void sendsearchdata(View view) {
        Intent myIntent = getIntent();
        final String user_id = myIntent.getStringExtra("Id");
        cat = categry.getSelectedItem().toString();
        P_skills = pskill.getText().toString();
        stateName = state.getSelectedItemPosition();
        worktype = work.getSelectedItemPosition();
        final String token = ((Globals) this.getApplication()).getToken();

        JSONObject post_dict = new JSONObject();

        try {
            post_dict.put("categoryname", cat);
            post_dict.put("skillsname", P_skills);
            post_dict.put("statename", stateName);
            post_dict.put("workname", worktype);
            post_dict.put("user_id", user_id);
            post_dict.put("token", token);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (post_dict.length() > 0) {
            new FSearch.SendJsonDataToServer().execute(String.valueOf(post_dict));

        }



    }

    class SendJsonDataToServer extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String JsonResponse = null;
            String JsonDATA = params[0];
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL("https://reclancer.com/appfree_search.php");
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

            loadData(s);

        }
    }


    private void loadData(String json) {

        Intent myIntent = getIntent();
        final String user_id = myIntent.getStringExtra("Id");

        Intent intent = new Intent(FSearch.this, FResult.class);
        intent.putExtra("resp",json);
        intent.putExtra("id",user_id);
        startActivity(intent);
    }


    public void ShowMenu(View view) {

        PopupMenu popup = new PopupMenu(this, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.main, popup.getMenu());
        popup.show();

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                Intent myIntent = getIntent();
                final String Id = myIntent.getStringExtra("Id");
                final String Token = myIntent.getStringExtra("token");

                switch (item.getItemId()) {
                    case R.id.home:
                        Intent home = new Intent(FSearch.this, RHome.class);
                        home.putExtra("Id",Id);
                        startActivity(home);
                        return true;

                    case R.id.postad:
                        Intent post = new Intent(FSearch.this, RPostAd.class);
                        post.putExtra("Id",Id);
                        post.putExtra("token",Token);
                        startActivity(post);
                        return true;

                    case R.id.mjobs:
                        Intent manage = new Intent(FSearch.this, ManageRJobs.class);
                        manage.putExtra("Id",Id);
                        manage.putExtra("token",Token);
                        startActivity(manage);
                        return true;

                    case R.id.Rdash:
                        Intent rfdash = new Intent(FSearch.this, R_Dash.class);
                        rfdash.putExtra("Id",Id);
                        rfdash.putExtra("token",Token);
                        startActivity(rfdash);
                        return true;

                    case R.id.Rfdash:
                        Intent rdash = new Intent(FSearch.this, RFDash.class);
                        rdash.putExtra("Id",Id);
                        rdash.putExtra("token",Token);
                        startActivity(rdash);
                        return true;

                    case R.id.recsetting:
                        Intent pset = new Intent(FSearch.this, R_PSettings.class);
                        pset.putExtra("Id",Id);
                        startActivity(pset);
                        return true;

                    case R.id.fsearch:
                        Intent search = new Intent(FSearch.this, FSearch.class);
                        search.putExtra("Id",Id);
                        startActivity(search);
                        return true;

                    case R.id.rlogout:

                        LAPIService = ApiUtil.getAPI_Rlogout();

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

        Call<TokenJsonObject> logOut = LAPIService.logOutUser(((Globals) this.getApplication()).getToken(),userId);
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

        Intent intent = new Intent(FSearch.this, RLogin.class);
        finish();
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        overridePendingTransition(0, 0);
        startActivity(intent);
    }


}
