package com.mythri.reclancer_app.Freelancer;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;

import com.mythri.reclancer_app.Globals;
import com.mythri.reclancer_app.Model.Freeupdatead;
import com.mythri.reclancer_app.Model.TokenJsonObject;
import com.mythri.reclancer_app.Network.APIFLogout;
import com.mythri.reclancer_app.Network.APIFreead_update;
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
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditF_ads extends AppCompatActivity {

    EditText name,state,city,cat,pskills,exp,sskills,ptitle,prjrates,adr,mobile,sub,email,p_ref,
            gen,wtype,desc,cdate,app_email;
    //ArrayList<HashMap<String, String>> displayList;
    Button startdate,enddate,select_sDate,select_eDate;
    Spinner statename,cityname;
    private APIFLogout lAPIService;
    int c_id,s_id;
    String cname,c_name;
    DatePickerDialog datePickerDialog;
    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;
    private APIFreead_update mAPIService;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fm_editads);

        final String token = ((Globals) this.getApplication()).getToken();
        Toast.makeText(getApplicationContext(),token,Toast.LENGTH_SHORT).show();
        Intent myIntent = getIntent();
        final String ad_id = myIntent.getStringExtra("ad_id");
        Toast.makeText(getApplicationContext(),ad_id,Toast.LENGTH_SHORT).show();
        final String id = myIntent.getStringExtra("Id");
        Toast.makeText(getApplicationContext(),id,Toast.LENGTH_SHORT).show();

        JSONObject post_dict = new JSONObject();


            try {
                post_dict.put("ad_id", ad_id);
                post_dict.put("token", token);
            } catch (JSONException e) {
                e.printStackTrace();           }



        if (post_dict.length() > 0) {
            new EditF_ads.SendDataToServer().execute(String.valueOf(post_dict));

        }

        select_sDate = findViewById(R.id.sdate);
        select_eDate = findViewById(R.id.edate);

        select_sDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(EditF_ads.this, R.style.TimePickerTheme,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                select_sDate.setText(year + "_" + (month + 1) + "_" + day);
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });


        select_eDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(EditF_ads.this, R.style.TimePickerTheme,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                select_eDate.setText(year + "_" + (month + 1) + "_" + day);
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

        name = (EditText) findViewById(R.id.name);
        name.setEnabled(false);

        mobile = (EditText) findViewById(R.id.mobile);
        mobile.setEnabled(false);

        email = (EditText) findViewById(R.id.email);
        email.setEnabled(false);

        state = (EditText) findViewById(R.id.state);
        state.setEnabled(false);

        cityname = (Spinner) findViewById(R.id.city);

        //city = (EditText) findViewById(R.id.city);

        cat = (EditText) findViewById(R.id.category);
        cat.setEnabled(false);

        sub = (EditText) findViewById(R.id.subcat);

        wtype = (EditText)findViewById(R.id.worktype);
        wtype.setEnabled(false);

        adr = (EditText) findViewById(R.id.address);
        adr.setEnabled(false);

        p_ref = (EditText)findViewById(R.id.ref);
        //p_ref.setEnabled(false);

        pskills = (EditText) findViewById(R.id.pskills);
        //pskills.setEnabled(false);


        exp = (EditText) findViewById(R.id.exp);
        //exp.setEnabled(false);


        sskills = (EditText) findViewById(R.id.sskills);
        //sskills.setEnabled(false);


        adr = (EditText) findViewById(R.id.address);
        //adr.setEnabled(false);


        ptitle = (EditText) findViewById(R.id.ptitle);
        //ptitle.setEnabled(false);

        prjrates = (EditText) findViewById(R.id.rates);


        startdate = (Button) findViewById(R.id.sdate);
        enddate = (Button) findViewById(R.id.edate);


        Button updatebtn = (Button) findViewById(R.id.editad);

        mAPIService = (APIFreead_update) ApiUtil.getAPIFreeadupdate();
        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast tst;

               // String city_name = city.getText().toString().trim();
                int ct = cityname.getSelectedItemPosition();
                cname =  cityname.getSelectedItem().toString();
                Toast.makeText(getApplicationContext(),cname,Toast.LENGTH_SHORT).show();
                String st = state.getText().toString().trim();
                Toast.makeText(getApplicationContext(),st,Toast.LENGTH_SHORT).show();
                String sub_cat = sub.getText().toString().trim();
                String prates = prjrates.getText().toString().trim();
                String start_date = select_sDate.getText().toString();
                String end_date = select_eDate.getText().toString();
                String p_t =  ptitle.getText().toString();
                String pref = p_ref.getText().toString();


                if((!TextUtils.isEmpty(prates))) {
                    FreeUpdatead(ad_id, token,s_id,cname, sub_cat,start_date,end_date,p_t,prates,pref);
                }
                else {
                    Toast toast = Toast.makeText(getApplicationContext(),"Fill the required Fields",Toast.LENGTH_SHORT);
                    toast.show();
                }


            }
        });

    }

    public void  FreeUpdatead(String ad_id,String token,int s_id,String cname,String sub_cat,String start_date,String end_date,String p_t,String prates,String pref) {
        mAPIService.FreeUpdatead(ad_id,token,s_id,cname,sub_cat,start_date,end_date,p_t,prates,pref).enqueue(new Callback<Freeupdatead>() {

            @Override
            public void onResponse(Call<Freeupdatead> call, Response<Freeupdatead> response) {

                if (response.isSuccessful()) {
                    //showResponse(response.body().toString());
                    //Log.i(TAG, "post submitted to API." + response.body().toString());
                   // Toast tst = Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_SHORT);
                   // tst.show();

                    Intent myIntent = getIntent();
                    final String Id = myIntent.getStringExtra("Id");
                    Toast tst = Toast.makeText(getApplicationContext(), Id, Toast.LENGTH_SHORT);
                    tst.show();

                    Intent su = new Intent(EditF_ads.this,ManageFJobs.class);
                    su.putExtra("Id",Id);
                    startActivity(su);




                }

            }

            @Override
            public void onFailure(Call<Freeupdatead> call, Throwable t) {
                //Log.e(TAG, "Unable to submit post to API.");

                Toast tst = Toast.makeText(getApplicationContext(),"Server Error Retry registering",Toast.LENGTH_SHORT);
                tst.show();


            }


        });


    }




    class SendDataToServer extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String JsonResponse = null;
            String JsonDATA = params[0];
            HttpURLConnection urlConnection = null;

            BufferedReader reader = null;
            try {
                URL url = new URL("https://reclancer.com/appfree_editad.php");
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

            try {
                displayData(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

    private void displayData(String json) throws JSONException {
        JSONArray jsonarray = new JSONArray(json);

        for (int i = 0; i < jsonarray.length(); i++) {


            JSONObject c = jsonarray.getJSONObject(i);
            //String id = c.getString("id");
            String fname = c.getString("name");
            name.setText(fname);
            String mail = c.getString("email");
            email.setText(mail);
            String contact = c.getString("mobile");
            mobile.setText(contact);
            String prjtitle = c.getString("ptitle");
            ptitle.setText(prjtitle);


            String stateName = c.getString("state_name");
           state.setText(stateName);
            s_id = c.getInt("stateid");


            /*c_id = c.getInt("cityid");
            Toast.makeText(getApplicationContext(),""+c_id,Toast.LENGTH_SHORT).show();
            cityname.setSelection(c_id);*/

            c_name = c.getString("city_name");
            Toast.makeText(getApplicationContext(),c_name,Toast.LENGTH_SHORT).show();

            JSONObject post_dict = new JSONObject();

            try {
                post_dict.put("stateid", s_id);


            } catch (JSONException e) {
                e.printStackTrace();
            }


            //do something
            new EditF_ads.SendJsonDataToServer().execute(String.valueOf(post_dict));



            //String cityName = c.getString("city_name");

            String categoryName = c.getString("category_name");
            cat.setText(categoryName);
            String subName = c.getString("subcatName");
            sub.setText(subName);
            String w_type = c.getString("work_type");
            wtype.setText(w_type);
            String addr = c.getString("address");
            adr.setText(addr);
            String prjr = c.getString("prj_rates");
            prjrates.setText(prjr);
            String start_date = c.getString("start_date");
            startdate.setText(start_date);
            String end_date = c.getString("end_date");
            enddate.setText(end_date);
            String refs = c.getString("pref");
            p_ref.setText(refs);



            //city_id = c.getInt("cityid");
            //cityname.setSelection(city_id);
            //String prj_period = c.getString("prj_period");
            /*String p_skills = c.getString("p_skills");
            String s_skills = c.getString("s_skills");
            String exps = c.getString("exp");
            String address = c.getString("address");

            //Toast.makeText(getBaseContext(),prj_rates,Toast.LENGTH_SHORT).show();



            String desp = c.getString("resp");
            String c_date = c.getString("closedate");
            String appemail = c.getString("appemail");*/


            // Toast.makeText(getBaseContext(),stateName,Toast.LENGTH_SHORT).show();

            //prjprd.setText(prj_period);

           /* pskills.setText(p_skills);
            sskills.setText(s_skills);
            exp.setText(exps);
            adr.setText(address);
            //prjrate.setText(prj_rates);
           // prjprd.setText(prj_period);
            /*
            worktype.setText(work_type);
            desc.setText(desp);
            cdate.setText(c_date);
            app_email.setText(appemail);*/



            //name.setBackgroundColor(Color.parseColor("#D3D3D3"));
            //String email = c.getString("email");
            //String address = c.getString("address");


            /*HashMap<String, String> display = new HashMap<>();

            // adding each child node to HashMap key => value

            display.put("name", name);


            // adding contact to contact list
            displayList.add(display);*/
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
                URL url = new URL("https://reclancer.com/appcity_list.php");
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
                loadCityList(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            /*try {
                loadIntoListView(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }*/
        }

    }
    private void loadCityList(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);

        String[] citylist = new String[jsonArray.length() + 1];
        citylist[0] = "Select City"; //item at pos 0
        int j = 1;
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            citylist[j++] = obj.getString("city_name");
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, citylist);
        cityname.setAdapter(arrayAdapter);
       // cityname.setSelection(c_id);

        int selectionPosition= arrayAdapter.getPosition(c_name);
        //Toast.makeText(getApplicationContext(), "" + selectionPosition, Toast.LENGTH_SHORT).show();
        cityname.setSelection(selectionPosition);
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
                        Intent home = new Intent(EditF_ads.this, FHome.class);
                        home.putExtra("Id",Id);
                        home.putExtra("token",Token);
                        startActivity(home);
                        return true;

                    case R.id.postad:
                        Intent post = new Intent(EditF_ads.this, FPostAd.class);
                        post.putExtra("Id",Id);
                        post.putExtra("token",Token);
                        startActivity(post);
                        return true;

                    case R.id.mfjobs:
                        Intent manage = new Intent(EditF_ads.this, ManageFJobs.class);
                        manage.putExtra("Id",Id);
                        manage.putExtra("token",Token);
                        startActivity(manage);
                        return true;

                    case R.id.fdash:
                        Intent dash = new Intent(EditF_ads.this, F_Dash.class);
                        dash.putExtra("Id",Id);
                        dash.putExtra("token",Token);
                        startActivity(dash);
                        return true;

                    case R.id.fRdash:
                        Intent Rdash = new Intent(EditF_ads.this, FRDash.class);
                        Rdash.putExtra("Id",Id);
                        Rdash.putExtra("token",Token);
                        startActivity(Rdash);
                        return true;

                    case R.id.psettings:
                        Intent pset = new Intent(EditF_ads.this, F_PSettigs.class);
                        pset.putExtra("Id",Id);
                        pset.putExtra("token",Token);
                        startActivity(pset);
                        return true;

                    case R.id.rsearch:
                        Intent search = new Intent(EditF_ads.this, RSearch.class);
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

        Intent intent = new Intent(EditF_ads.this, FLogin.class);
        finish();
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        overridePendingTransition(0, 0);
        startActivity(intent);
    }

}
