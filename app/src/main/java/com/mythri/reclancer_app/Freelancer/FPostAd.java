package com.mythri.reclancer_app.Freelancer;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mythri.reclancer_app.Globals;
import com.mythri.reclancer_app.Model.FreePostAd;
import com.mythri.reclancer_app.Model.TokenJsonObject;
import com.mythri.reclancer_app.Network.APIFLogout;
import com.mythri.reclancer_app.Network.APIFreePost;
import com.mythri.reclancer_app.Network.ApiUtil;
import com.mythri.reclancer_app.R;
import com.mythri.reclancer_app.Session;
import com.mythri.reclancer_app.Validation.FreePostVal;

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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FPostAd extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String[] gender = {"Select Gender","Male", "Female"};
    Button select_sDate,select_eDate,submit,upload_file;
    TextView date;
    DatePickerDialog datePickerDialog;

    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;

    Spinner statename,cityname,categry,work,gen;
    int st,g,location,ct,status;
    String cat;

    EditText new_cat;

     private APIFreePost mAPIService;

    int serverResponseCode = 0;
    ProgressDialog dialog = null;

    String upLoadServerUri = null;
    TextInputEditText newcatedit;
    private APIFLogout lAPIService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_ad);

        final String token = ((Globals) this.getApplication()).getToken();
        Toast.makeText(getApplicationContext(),token,Toast.LENGTH_SHORT).show();

        final Spinner spin = (Spinner) findViewById(R.id.fgender);
        spin.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, gender);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);

        select_sDate = findViewById(R.id.sdate);
        select_eDate = findViewById(R.id.edate);
        submit = findViewById(R.id.postad);
        //upload_file = findViewById(R.id.upload);

        select_sDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(FPostAd.this, R.style.TimePickerTheme,
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
                datePickerDialog = new DatePickerDialog(FPostAd.this, R.style.TimePickerTheme,
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

        final EditText name = (EditText) findViewById(R.id.name);

        //Validate Name Field
        if(name.getText().toString().length() == 0 )
        {
            name.setError( "required!" );
        }
        name.addTextChangedListener(new TextWatcher() {
            // after every change has been made to this editText, we would like to check validity
            public void afterTextChanged(Editable s) {
                FreePostVal.isName(name, true);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        final EditText phone = (EditText) findViewById(R.id.mobile);

        //Validate Mobile field
        if(phone.getText().toString().length() == 0 )
        {
            phone.setError( "required!" );
        }
        phone.addTextChangedListener(new TextWatcher() {
            // after every change has been made to this editText, we would like to check validity
            public void afterTextChanged(Editable s) {
                FreePostVal.isMobile(phone, true);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });


        final EditText mail = (EditText) findViewById(R.id.email);

        //Validate Email Field
        if(mail.getText().toString().length() == 0 )
        {
            mail.setError( "required!" );
        }
        mail.addTextChangedListener(new TextWatcher() {
            // after every change has been made to this editText, we would like to check validity
            public void afterTextChanged(Editable s) {
                FreePostVal.isEmail(mail, true);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        final EditText p_title = (EditText) findViewById(R.id.ptitle);

        statename = findViewById(R.id.state);
        cityname = findViewById(R.id.city);

        categry = findViewById(R.id.category);
        //newcatedit =  findViewById(R.id.t_cat);
        new_cat = (EditText) findViewById(R.id.newcat);
        work = findViewById(R.id.worktype);

        getJSON("https://reclancer.com/getstate");
        getCat("https://reclancer.com/getcatgry");
        getWork("https://reclancer.com/getworktype");

        final EditText subcategory = (EditText) findViewById(R.id.subcat);

        gen = findViewById(R.id.fgender);


        final EditText skills = (EditText) findViewById(R.id.pskills);
        if(skills.getText().toString().length() == 0 )
        {
            skills.setError( "required!" );
        }
        skills.addTextChangedListener(new TextWatcher() {
            // after every change has been made to this editText, we would like to check validity
            public void afterTextChanged(Editable s) {
                FreePostVal.isPskills(skills, true);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });


        final EditText adrs = (EditText) findViewById(R.id.address);
        //Validate Address Field

        if(adrs.getText().toString().length() == 0 )
        {
            adrs.setError( "required!" );
        }
        adrs.addTextChangedListener(new TextWatcher() {
            // after every change has been made to this editText, we would like to check validity
            public void afterTextChanged(Editable s) {
                FreePostVal.isAddress(adrs, true);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        final EditText exps = (EditText) findViewById(R.id.exp);
        //Validate Experience Field

        if(exps.getText().toString().length() == 0 )
        {
            exps.setError( "required!" );
        }
        exps.addTextChangedListener(new TextWatcher() {
            // after every change has been made to this editText, we would like to check validity
            public void afterTextChanged(Editable s) {
                FreePostVal.isExp(exps, true);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        final EditText s_skills = (EditText) findViewById(R.id.sskills);
        final EditText prates = (EditText)findViewById(R.id.rates);
                //Validate Rates Field
        /*if(prates.getText().toString().length() == 0 )
        {
            prates.setError( "required!" );
        }
        prates.addTextChangedListener(new TextWatcher() {
            // after every change has been made to this editText, we would like to check validity
            public void afterTextChanged(Editable s) {
                FreePostVal.isPrjRts(prates, true);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });*/



        final EditText pref = (EditText)findViewById(R.id.ref);
      /*  if(pref.getText().toString().length() == 0 )
        {
            pref.setError( "required!" );
        }
        pref.addTextChangedListener(new TextWatcher() {
            // after every change has been made to this editText, we would like to check validity
            public void afterTextChanged(Editable s) {
                FreePostVal.isPrjRef(pref, true);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });*/
        //final Button s_date = (Button) findViewById(R.id.sdate);
        //final Button e_date = (Button) findViewById(R.id.edate);
        Button btn = (Button) findViewById(R.id.postad);

        mAPIService = ApiUtil.getFreepostad();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent myIntent = getIntent();
                String userId = myIntent.getStringExtra("Id");
                Toast.makeText(getApplicationContext(),userId,Toast.LENGTH_SHORT).show();
                String T = myIntent.getStringExtra("token");
                Toast.makeText(getApplicationContext(),T,Toast.LENGTH_SHORT).show();

                st = statename.getSelectedItemPosition();
                if (statename.getSelectedItemPosition() > 0) {
                    // get selected item value
                    //String itemvalue = String.valueOf(spinner.getSelectedItem());
                    //int state = spinner.getSelectedItemPosition();
                   //st = statename.getSelectedItemPosition();


                } else {
                    // set error message on spinner
                    TextView errorTextview = (TextView)statename.getSelectedView();
                    errorTextview.setError("");
                    errorTextview.setTextColor(Color.RED);
                    errorTextview.setError("required");
                }

                Toast tst;

                st = statename.getSelectedItemPosition();
                tst = Toast.makeText(getApplicationContext(),""+st,Toast.LENGTH_SHORT);
                tst.show();

                //statename.setOnItemSelectedListener(this);

                String fname = name.getText().toString().trim();
                tst = Toast.makeText(getApplicationContext(), fname, Toast.LENGTH_SHORT);
                tst.show();

                String mobile = phone.getText().toString().trim();
                tst = Toast.makeText(getApplicationContext(), mobile, Toast.LENGTH_SHORT);
                tst.show();

                String email = mail.getText().toString().trim();
                tst = Toast.makeText(getApplicationContext(), email, Toast.LENGTH_SHORT);
                tst.show();

                String ptitle = p_title.getText().toString().trim();
                tst = Toast.makeText(getApplicationContext(), ptitle, Toast.LENGTH_SHORT);
                tst.show();

               /* String state = st.getText().toString().trim();
                tst = Toast.makeText(getApplicationContext(), state, Toast.LENGTH_SHORT);
                tst.show();*/


                ct = cityname.getSelectedItemPosition();
                tst = Toast.makeText(getApplicationContext(),""+ct,Toast.LENGTH_SHORT);
                tst.show();

               /* if (cityname.getSelectedItemPosition() > 0) {
                    // get selected item value
                    //String itemvalue = String.valueOf(spinner.getSelectedItem());
                    //int state = spinner.getSelectedItemPosition();

                } else {
                    // set error message on spinner
                    TextView errorTextview = (TextView)cityname.getSelectedView();
                    errorTextview.setError("");
                    errorTextview.setTextColor(Color.RED);
                    errorTextview.setError("required");
                }*/

                String ci = cityname.getSelectedItem().toString();
                tst = Toast.makeText(getApplicationContext(), ci, Toast.LENGTH_SHORT);
                tst.show();


                if (gen.getSelectedItemPosition() > 0) {
                    // get selected item value
                    //String itemvalue = String.valueOf(spinner.getSelectedItem());


                } else {
                    // set error message on spinner
                    TextView errorTextview = (TextView)gen.getSelectedView();
                    errorTextview.setError("");
                    errorTextview.setTextColor(Color.RED);
                    errorTextview.setError("required");
                }

                g = gen.getSelectedItemPosition();
                tst = Toast.makeText(getApplicationContext(),""+ g, Toast.LENGTH_SHORT);
                tst.show();


                //String st = String.valueOf(state.getSelectedItem());

               /* String city = city.getText().toString().trim();
                tst = Toast.makeText(getApplicationContext(), city, Toast.LENGTH_SHORT);
                tst.show();*/

                /*String cat = category.getText().toString().trim();
                tst = Toast.makeText(getApplicationContext(), cat, Toast.LENGTH_SHORT);
                tst.show();*/


                int cat_item =  categry.getSelectedItemPosition();
                if (categry.getSelectedItemPosition() > 0) {
                    // get selected item value
                    // final String cat = String.valueOf(spin.getSelectedItem());



                } else {
                    // set error message on spinner
                    TextView errorTextview = (TextView)categry.getSelectedView();
                    errorTextview.setError("");
                    errorTextview.setTextColor(Color.RED);
                    errorTextview.setError("required");
                }
                final String cat = String.valueOf(categry.getSelectedItem());
                tst = Toast.makeText(getApplicationContext(), cat, Toast.LENGTH_SHORT);
                tst.show();

                String newcat = new_cat.getText().toString().trim();
                tst = Toast.makeText(getApplicationContext(), newcat, Toast.LENGTH_SHORT);
                tst.show();
                if(newcat.equals(""))
                {
                    newcat = "NA";
                    tst = Toast.makeText(getApplicationContext(), newcat, Toast.LENGTH_SHORT);
                    tst.show();
                }



                String subcat = subcategory.getText().toString().trim();
                tst = Toast.makeText(getApplicationContext(), subcat, Toast.LENGTH_SHORT);
                tst.show();



                if (work.getSelectedItemPosition() > 0) {
                    // get selected item value
                    //String itemvalue = String.valueOf(spinner.getSelectedItem());

                } else {
                    // set error message on spinner
                    TextView errorTextview = (TextView)work.getSelectedView();
                    errorTextview.setError("");
                    errorTextview.setTextColor(Color.RED);
                    errorTextview.setError("required");
                }

                location = work.getSelectedItemPosition();
                tst = Toast.makeText(getApplicationContext(), ""+location, Toast.LENGTH_SHORT);
                tst.show();

                String pskills = skills.getText().toString().trim();
                tst = Toast.makeText(getApplicationContext(), pskills, Toast.LENGTH_SHORT);
                tst.show();

                String secskills = s_skills.getText().toString().trim();
                tst = Toast.makeText(getApplicationContext(), secskills, Toast.LENGTH_SHORT);
                tst.show();

                String exp = exps.getText().toString().trim();
                tst = Toast.makeText(getApplicationContext(), exp, Toast.LENGTH_SHORT);
                tst.show();

                String address = adrs.getText().toString().trim();
                tst = Toast.makeText(getApplicationContext(), address, Toast.LENGTH_SHORT);
                tst.show();

                String prjrates = prates.getText().toString().trim();
                tst = Toast.makeText(getApplicationContext(), prjrates, Toast.LENGTH_SHORT);
                tst.show();

                /*String startdate = sdate.getText().toString().trim();
                tst = Toast.makeText(getApplicationContext(), startdate, Toast.LENGTH_SHORT);
                tst.show();

                String enddate = edate.getText().toString().trim();
                tst = Toast.makeText(getApplicationContext(), enddate, Toast.LENGTH_SHORT);
                tst.show();*/

                String p_ref = pref.getText().toString().trim();
                tst = Toast.makeText(getApplicationContext(), p_ref, Toast.LENGTH_SHORT);
                tst.show();

                String start_date = select_sDate.getText().toString();
                tst = Toast.makeText(getApplicationContext(), start_date, Toast.LENGTH_SHORT);
                tst.show();

                String end_date = select_eDate.getText().toString();
                tst = Toast.makeText(getApplicationContext(), end_date, Toast.LENGTH_SHORT);
                tst.show();

              /*  try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                    Date date1 = sdf.parse(start_date);
                    Date date2 = sdf.parse(end_date);
                    if((date2.compareTo(date1)<0) || (date2.compareTo(date1) == 0))
                    {
                        status = 0;
                        Toast.makeText(getApplicationContext(),"End date should not be before start date or End date and start date should not be equal",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        status =1;
                        Toast.makeText(getApplicationContext(),"Inside else",Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


              if(status != 0) {

                  if ((!TextUtils.isEmpty(fname)) && (!TextUtils.isEmpty(mobile)) && (!TextUtils.isEmpty(email)) && (!TextUtils.isEmpty(address)) && (!TextUtils.isEmpty(pskills)) && (!TextUtils.isEmpty(exp)) && (cat_item != 0) && (g != 0) && (st != 0) && (ct != 0) && (status !=0)) {

                      FreePost(userId, token, fname, mobile, email, ptitle, g, st, ci, cat, newcat, subcat, location, pskills, secskills, exp, address, prjrates, p_ref, start_date, end_date);
                  } else {

                      Snackbar snackbar = Snackbar.make(view, "Please enter required fields in the form", Snackbar.LENGTH_LONG);
                      snackbar.show();
                  }
              }
              else
              {
                  Snackbar snackbar = Snackbar.make(view, "End Date is before start date or end date is equal to start date.Please check!!", Snackbar.LENGTH_LONG);
                  snackbar.show();
              }

            }*/


                if ((!TextUtils.isEmpty(fname)) && (!TextUtils.isEmpty(mobile)) && (!TextUtils.isEmpty(email)) && (!TextUtils.isEmpty(address)) && (!TextUtils.isEmpty(pskills)) && (!TextUtils.isEmpty(exp)) && (cat_item != 0) && (g != 0) && (st != 0) && (ct != 0) ) {

                    FreePost(userId, token, fname, mobile, email, ptitle, g, st, ci, cat, newcat, subcat, location, pskills, secskills, exp, address, prjrates, p_ref, start_date, end_date);
                }
                else {

                    Snackbar snackbar = Snackbar.make(view, "Please enter required fields in the form", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }


        });

    }




  public void FreePost(String userId,String token,String name, String mobile, String email, String ptitle, int gender, int state, String city, String cat, String newcat, String subcat, int work, String skills, String sskills, String exp, String address, String prjrates, String pref ,String start_date,String end_date) {
        mAPIService.FreePostad(userId,token,name, email, mobile, ptitle, gender, state, city, cat,newcat, subcat, work, skills, sskills, exp, address, prjrates, pref,start_date,end_date).enqueue(new Callback<FreePostAd>() {
            @Override
            public void onResponse(Call<FreePostAd> call, Response<FreePostAd> response)  {
               // String c = response.body().getCode();
               // Toast.makeText(getApplicationContext(),c,Toast.LENGTH_SHORT).show();

               /* if(c=="401")
                {
                    Toast.makeText(getApplicationContext(),"Token Expiry",Toast.LENGTH_SHORT).show();
                }*/
                if (response.isSuccessful()) {

                   // String c =  response.body().getCode();
                   // Toast.makeText(getApplicationContext(),c,Toast.LENGTH_SHORT).show();

                    Intent myIntent = getIntent();
                    final String Id = myIntent.getStringExtra("Id");
                    final String Token = myIntent.getStringExtra("token");
                    Intent su = new Intent(FPostAd.this,Fsuccess.class);
                    su.putExtra("Id",Id);
                    startActivity(su);

                }



            }

            @Override
            public void onFailure(Call<FreePostAd> call, Throwable t) {
                //Log.e(TAG, "Unable to submit post to API.");


                Toast tst = Toast.makeText(getApplicationContext(), "Server Error Retry registering", Toast.LENGTH_SHORT);
                tst.show();

                Intent h = new Intent(FPostAd.this,Ffailure.class);
                startActivity(h);


            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        // TODO Auto-generated method stub
        /*String sp1 = String.valueOf(statename.getSelectedItemId());
       // int sp1 = statename.getSelectedItemPosition();
        Toast.makeText(this, sp1, Toast.LENGTH_SHORT).show();
        JSONObject post_dict = new JSONObject();

       try {
            post_dict.put("stateid", sp1);

       } catch (JSONException e) {
            e.printStackTrace();
        }


        if(!sp1.equals("0"))
        {
            //do something
            new FPostAd.SendJsonDataToServer().execute(String.valueOf(post_dict));
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Select State", Toast.LENGTH_SHORT).show();
        }*/


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
                if(s != null) {
                    loadCityList(s);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"No City",Toast.LENGTH_SHORT).show();
                    // String n = "0";
                    // loadcityNull(n);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
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
        statename.setAdapter(arrayAdapter);
        //statename.setOnItemSelectedListener(this);

        statename.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {


// TODO Auto-generated method stub
                String sp1 = String.valueOf(statename.getSelectedItemId());
                // int sp1 = statename.getSelectedItemPosition();
                Toast.makeText(getApplicationContext(), sp1, Toast.LENGTH_SHORT).show();
                JSONObject post_dict = new JSONObject();

                try {
                    post_dict.put("stateid", sp1);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                if(!sp1.equals("0"))
                {
                    //do something
                    new FPostAd.SendJsonDataToServer().execute(String.valueOf(post_dict));
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Select State", Toast.LENGTH_SHORT).show();
                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
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
        //categry.setOnItemSelectedListener(this);

       categry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                String cat_name =  categry.getItemAtPosition(position).toString();

                Toast.makeText(FPostAd.this, cat_name, Toast.LENGTH_LONG).show();

                if(cat_name.equals("Others"))
                {
                    Toast.makeText(FPostAd.this, "Inside Others", Toast.LENGTH_LONG).show();

                    new_cat.setHint("Enter New Category");
                    new_cat.setVisibility(View.VISIBLE);

                }
                else
                {
                    new_cat.setHint("");
                    new_cat.setVisibility(View.INVISIBLE);

                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });

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
                        Intent home = new Intent(FPostAd.this, FHome.class);
                        home.putExtra("Id",Id);
                        startActivity(home);
                        return true;

                    case R.id.postad:
                        Intent post = new Intent(FPostAd.this, FPostAd.class);
                        post.putExtra("Id",Id);
                        post.putExtra("token",Token);
                        startActivity(post);
                        return true;

                    case R.id.mfjobs:
                        Intent manage = new Intent(FPostAd.this, ManageFJobs.class);
                        manage.putExtra("Id",Id);
                        manage.putExtra("token",Token);
                        startActivity(manage);
                        return true;

                    case R.id.fdash:
                        Intent dash = new Intent(FPostAd.this, F_Dash.class);
                        dash.putExtra("Id",Id);
                        dash.putExtra("token",Token);
                        startActivity(dash);
                        return true;

                    case R.id.fRdash:
                        Intent Rdash = new Intent(FPostAd.this, FRDash.class);
                        Rdash.putExtra("Id",Id);
                        Rdash.putExtra("token",Token);
                        startActivity(Rdash);
                        return true;

                    case R.id.psettings:
                        Intent pset = new Intent(FPostAd.this, F_PSettigs.class);
                        pset.putExtra("Id",Id);
                        startActivity(pset);
                        return true;

                    case R.id.rsearch:
                        Intent search = new Intent(FPostAd.this, RSearch.class);
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

        Intent intent = new Intent(FPostAd.this, FLogin.class);
        finish();
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        overridePendingTransition(0, 0);
        startActivity(intent);
    }


}

