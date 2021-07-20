package com.mythri.reclancer_app.Recruiter;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.mythri.reclancer_app.Freelancer.EditF_ads;
import com.mythri.reclancer_app.Freelancer.ManageFJobs;
import com.mythri.reclancer_app.Globals;
import com.mythri.reclancer_app.Model.Recupdatead;
import com.mythri.reclancer_app.Model.TokenJsonObject;
import com.mythri.reclancer_app.Network.APIRLogout;
import com.mythri.reclancer_app.Network.APIRecad_update;
import com.mythri.reclancer_app.Network.ApiUtil;
import com.mythri.reclancer_app.R;
import com.mythri.reclancer_app.Session;
import com.mythri.reclancer_app.Validation.RecPostVal;

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

public class EditR_ads extends AppCompatActivity {

    EditText j_title,name,statename,cat,pskills,exp,sskills,ptitle,period,prjrates,adr,mobile,sub,email,
            gen,type,desc,cdate,app_email;

    private APIRLogout LAPIService;

    private APIRecad_update mAPIService;
    Button select_cDate,select_sDate,select_eDate,startdate,enddate,lastdate;

    DatePickerDialog datePickerDialog;
    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;
    int s_id,c_id;

    Spinner cityname;

    String token,c_name;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rm_editads);

        token = ((Globals) this.getApplication()).getToken();
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
            new EditR_ads.SendDataToServer().execute(String.valueOf(post_dict));

        }


        select_sDate = findViewById(R.id.sdate);
        select_eDate = findViewById(R.id.edate);
        select_cDate =  findViewById(R.id.ldate);

        select_sDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(EditR_ads.this, R.style.TimePickerTheme,
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
                datePickerDialog = new DatePickerDialog(EditR_ads.this, R.style.TimePickerTheme,
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

        select_cDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(EditR_ads.this, R.style.TimePickerTheme,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                //select_eDate.setText(day + "/" + (month + 1) + "/" + year)
                                select_cDate.setText(year + "_" + (month + 1) + "_" + day);
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();


            }
        });

        j_title = (EditText)findViewById(R.id.jtitle);
        j_title.setEnabled(false);

        name = (EditText) findViewById(R.id.name);
        name.setEnabled(false);

        mobile = (EditText) findViewById(R.id.mobile);
        mobile.setEnabled(false);

        email = (EditText) findViewById(R.id.email);
        email.setEnabled(false);

        statename = (EditText) findViewById(R.id.state);
        statename.setEnabled(false);

        cityname = (Spinner) findViewById(R.id.city);

        cat = (EditText) findViewById(R.id.category);
        cat.setEnabled(false);

        sub = (EditText) findViewById(R.id.subcat);

        type = (EditText)findViewById(R.id.worktype);
        type.setEnabled(false);

        pskills = (EditText) findViewById(R.id.pskills);
        pskills.setEnabled(false);

        exp = (EditText) findViewById(R.id.exp);
        exp.setEnabled(false);

        sskills = (EditText) findViewById(R.id.sskills);
        sskills.setEnabled(false);

        adr = (EditText) findViewById(R.id.address);
        adr.setEnabled(false);

        period = (EditText) findViewById(R.id.pperiod);

        startdate = (Button) findViewById(R.id.sdate);
        enddate = (Button) findViewById(R.id.edate);
        lastdate = (Button) findViewById(R.id.ldate);

        prjrates = (EditText) findViewById(R.id.prates);
        if(prjrates.getText().toString().length() == 0 )
        {
            prjrates.setError( "required!" );
        }
        prjrates.addTextChangedListener(new TextWatcher() {
            // after every change has been made to this editText, we would like to check validity
            public void afterTextChanged(Editable s) {
                RecPostVal.isPrjRts(prjrates, true);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        desc = (EditText)findViewById(R.id.des);
        if(desc.getText().toString().length() == 0 )
        {
            desc.setError( "required!" );
        }
        desc.addTextChangedListener(new TextWatcher() {
            // after every change has been made to this editText, we would like to check validity
            public void afterTextChanged(Editable s) {
                RecPostVal.isDesp(desc, true);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        app_email = (EditText)findViewById(R.id.appemail);
        if(app_email.getText().toString().length() == 0 )
        {
            app_email.setError( "required!" );
        }
        app_email.addTextChangedListener(new TextWatcher() {
            // after every change has been made to this editText, we would like to check validity
            public void afterTextChanged(Editable s) {
                RecPostVal.isEmailAddress(app_email, true);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        Button updatebtn = (Button) findViewById(R.id.editad);

        mAPIService = (APIRecad_update) ApiUtil.getAPIRecadupdate();
        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Toast tst;

                //String city_name = cityname.getText().toString().trim();
                String sub_cat = sub.getText().toString().trim();
                String rates =  prjrates.getText().toString().trim();
                String pperiod =  period.getText().toString().trim();
                String res = desc.getText().toString().trim();
                String compemail = app_email.getText().toString().trim();
                String city_name = cityname.getSelectedItem().toString();
                String st = statename.getText().toString().trim();

                String s_date = startdate.getText().toString();
                String e_date = enddate.getText().toString();
                String la_date = lastdate.getText().toString();
                //int stateid = statename.getId();




                if((!TextUtils.isEmpty(rates)) && (!TextUtils.isEmpty(res)) && (!TextUtils.isEmpty(compemail))) {
                    RecUpdatead(ad_id, s_id,city_name, sub_cat, rates, pperiod, res, compemail,s_date,e_date,la_date);
                }
                else
                {
                    Toast toast = Toast.makeText(getApplicationContext(),"Fill the required Fields",Toast.LENGTH_SHORT);
                    toast.show();
                }


            }
        });

    }

    public void RecUpdatead(String ad_id,int s_id,String city_name,String sub_cat,String rates,String period,String desc,String comp_email,String start_date,String end_date,String last_date) {
        mAPIService.RecUpdatead(ad_id,s_id,city_name,sub_cat,rates,period,desc,comp_email,start_date,end_date,last_date).enqueue(new Callback<Recupdatead>() {

            @Override
            public void onResponse(Call<Recupdatead> call, Response<Recupdatead> response) {

                if (response.isSuccessful()) {
                    //showResponse(response.body().toString());
                    //Log.i(TAG, "post submitted to API." + response.body().toString());
                    //Toast tst = Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_SHORT);
                   // tst.show();

                    Intent myIntent = getIntent();
                    final String Id = myIntent.getStringExtra("Id");
                    Toast tst = Toast.makeText(getApplicationContext(), Id, Toast.LENGTH_SHORT);
                    tst.show();
                    Intent su = new Intent(EditR_ads.this, ManageRJobs.class);
                    su.putExtra("Id",Id);
                    startActivity(su);
                }

            }

            @Override
            public void onFailure(Call<Recupdatead> call, Throwable t) {
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
                URL url = new URL("https://reclancer.com/apprec_editad.php");
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

            String adid = c.getString("ad_id");
            String jobtitle = c.getString("jtitle");
            Toast.makeText(getApplicationContext(),jobtitle,Toast.LENGTH_SHORT).show();
            j_title.setText(jobtitle);
            String rname = c.getString("name");
            name.setText(rname);
            String mail = c.getString("email");
            email.setText(mail);
            String contact = c.getString("mobile");
            mobile.setText(contact);
            String stateName = c.getString("state_name");
            statename.setText(stateName);
            s_id = c.getInt("stateid");
            JSONObject post_dict = new JSONObject();

            try {
                post_dict.put("stateid", s_id);


            } catch (JSONException e) {
                e.printStackTrace();
            }


            //do something
            new EditR_ads.SendJsonDataToServer().execute(String.valueOf(post_dict));

            String categoryName = c.getString("category_name");
            cat.setText(categoryName);
            String subName = c.getString("subcatName");
            sub.setText(subName);
            String w_type = c.getString("work_type");
            type.setText(w_type);
            String p_skills = c.getString("p_skills");
            pskills.setText(p_skills);
            String s_skills = c.getString("s_skills");
            sskills.setText(s_skills);
            String exps = c.getString("exp");
            exp.setText(exps);
            String addrs = c.getString("add");
            adr.setText(addrs);
            Toast.makeText(getApplicationContext(),addrs,Toast.LENGTH_SHORT).show();
            String prj_rates = c.getString("prj_rates");
            prjrates.setText(prj_rates);
            String prj_period = c.getString("prj_period");
            period.setText(prj_period);
            String start_date = c.getString("start_date");
            startdate.setText(start_date);
            String end_date = c.getString("end_date");
            enddate.setText(end_date);
            String desp = c.getString("resp");
            desc.setText(desp);
            String c_date = c.getString("closedate");
            lastdate.setText(c_date);
            String comp_email = c.getString("compemail");
            app_email.setText(comp_email);

            c_name = c.getString("city_name");


            /*c_id = c.getInt("cityid");
            Toast.makeText(getApplicationContext(),""+c_id,Toast.LENGTH_SHORT).show();
            cityname.setSelection(c_id);*/




            /**j_title.setText(jobtitle);
            name.setText(rname);
            mobile.setText(contact);
            email.setText(mail);
            statename.setText(stateName);

            cat.setText(categoryName);
            sub.setText(subName);
            type.setText(w_type);
            pskills.setText(p_skills);
            sskills.setText(s_skills);
            exp.setText(exps);
            adr.setText(addrs);
            prjrates.setText(prj_rates);
            period.setText(prj_period);
            startdate.setText(start_date);
            enddate.setText(end_date);
            desc.setText(desp);
            //cdate.setText(c_date);
            app_email.setText(comp_email);**/


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
        int selectionPosition= arrayAdapter.getPosition(c_name);
        //Toast.makeText(getApplicationContext(), "" + selectionPosition, Toast.LENGTH_SHORT).show();
        cityname.setSelection(selectionPosition);
    }

    /**
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
                        Intent home = new Intent(EditR_ads.this, RHome.class);
                        home.putExtra("Id",Id);
                        startActivity(home);
                        return true;

                    case R.id.postad:
                        Intent post = new Intent(EditR_ads.this, RPostAd.class);
                        post.putExtra("Id",Id);
                        post.putExtra("token",Token);
                        startActivity(post);
                        return true;

                    case R.id.mjobs:
                        Intent manage = new Intent(EditR_ads.this, ManageRJobs.class);
                        manage.putExtra("Id",Id);
                        manage.putExtra("token",Token);
                        startActivity(manage);
                        return true;

                    case R.id.recsetting:
                        Intent pset = new Intent(EditR_ads.this, R_PSettings.class);
                        pset.putExtra("Id",Id);
                        startActivity(pset);
                        return true;

                    case R.id.fsearch:
                        Intent search = new Intent(EditR_ads.this, RSearch.class);
                        search.putExtra("Id",Id);
                        startActivity(search);
                        return true;

                    case R.id.rlogout:

                        LAPIService = ApiUtil.getAPI_Rlogout();

                        logOutMethod();



                    default:
                        return false;
                }

                //return(super.onMenuItemSelected(1,item));

            }
        });
    }*/


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

        Intent intent = new Intent(EditR_ads.this, RLogin.class);
        finish();
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        overridePendingTransition(0, 0);
        startActivity(intent);
    }


}
