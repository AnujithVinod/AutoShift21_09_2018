package com.miniprosg.andgeeks.autoshift;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.miniprosg.andgeeks.autoshift.helper.PredifValues;

import org.json.JSONException;
import org.json.JSONObject;

public class UpdateProfile_Agent extends AppCompatActivity {
    EditText selectName,selectMobile,selectLocation,selectSecans,selectAgent;
    TextView selectEmail;
    TextView selectBrand;
    String sUname;
    String sPwd;
    String sPhone,sAgentName;
    String slocation;
    String sSecAns,SID;
    PredifValues predifValues=new PredifValues();
    String base_url=predifValues.returnipaddressurl();
    private ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile_agent);

        findViewById(R.id.agentLayout).setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent)
            {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                return false;
            }
        });
        MaterialSpinner spinner = (MaterialSpinner) findViewById(R.id.spinnsecans);
        spinner.setItems("What is your Nick Name?", "Where is your birth place?", "What is you pets name?", "What is your Fathers Name?", "What is the name of your Favorite Teacher?");
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
            }
        });

        Bundle extras = getIntent().getExtras();
        SID=extras.getString("s_id");

        selectEmail=(TextView) findViewById(R.id.userEmailId);
        selectLocation=(EditText)findViewById(R.id.saddress);
        selectMobile=(EditText)findViewById(R.id.mobileNumber);
        selectName=(EditText)findViewById(R.id.fullName);
        selectAgent=(EditText)findViewById(R.id.agentName);
        selectSecans=(EditText)findViewById(R.id.secans);
        selectBrand=(TextView)findViewById(R.id.sbrand);
        fillContentView();
    }

    public void fillContentView() {

        JSONObject request = new JSONObject();
        try {

            request.put("s_id",SID);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.POST, base_url+"extras/getAgentProfile_Details.php", request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            if (response.getInt("status") == 1) {
                                selectEmail.setText(response.getString("semail"));
                                selectLocation.setText(response.getString("saddress"));
                                selectMobile.setText(response.getString("sphone"));
                                selectName.setText(response.getString("sname"));
                                selectSecans.setText(response.getString("ssecans"));
                                selectBrand.setText(response.getString("sbrand"));
                                selectAgent.setText(response.getString("sagent"));
                            }else{
                                Toast.makeText(getApplicationContext(),
                                        response.getString("message"), Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                        //Display error message whenever an error occurs
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsArrayRequest);


        Toast.makeText(getApplicationContext(),"Welcome",Toast.LENGTH_LONG).show();


    }

    private void displayLoader() {
        pDialog = new ProgressDialog(UpdateProfile_Agent.this);
        pDialog.setMessage("Logging In.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

    }

    public void passwordReset()
    {
        Intent i = new Intent(getApplicationContext(), ResetPassword.class);
        startActivity(i);
    }

    public void emailresend()
    {
        final EditText input;
        AlertDialog.Builder builder1 = new AlertDialog.Builder(UpdateProfile_Agent.this);
        builder1.setMessage("Enter your Email Address");
        builder1.setCancelable(true);
        input=new EditText(this);
        //String inpemail= input.getText().toString();
        // Toast.makeText(getApplicationContext(),inpemail,Toast.LENGTH_LONG).show();
        builder1.setView(input);
        builder1.setPositiveButton(
                "Send",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String inpemail= input.getText().toString();

                        if("".equals(inpemail)) {
                            input.setError("Email Address cannot be empty");
                            input.requestFocus();

                        }
                        else
                        {
                            displayLoader();
                            JSONObject request = new JSONObject();
                            try {
                                //Populate the request parameters
                                //Toast.makeText(getApplicationContext(),"HIHIHI",Toast.LENGTH_LONG).show();
                                request.put("uemail", inpemail);
                                //request.put(KEY_PASSWORD, password);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                                    (Request.Method.POST, base_url+"index.php", request, new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            pDialog.dismiss();
                                            try {

                                                Toast.makeText(getApplicationContext(),response.getString("message"), Toast.LENGTH_SHORT).show();

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }, new Response.ErrorListener() {

                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            pDialog.dismiss();

                                            //Display error message whenever an error occurs
                                            Toast.makeText(getApplicationContext(),
                                                    error.getMessage(), Toast.LENGTH_SHORT).show();

                                        }
                                    });

                            // Access the RequestQueue through your singleton class.
                            MySingleton.getInstance(UpdateProfile_Agent.this).addToRequestQueue(jsArrayRequest);

                            dialog.cancel();

                        }
                    }
                });

        builder1.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public void changePassword(View view) {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(UpdateProfile_Agent.this);
        builder1.setMessage("How would you like to get your password?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Email Password",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        emailresend();

                    }
                });

        builder1.setNegativeButton(
                "Reset Password",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        passwordReset();

                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();

    }

    public void updateProfile(View view) {

        sUname=selectName.getText().toString();
        sPhone=selectMobile.getText().toString();
        slocation=selectLocation.getText().toString();
        sSecAns=selectSecans.getText().toString();
        sAgentName=selectAgent.getText().toString();

        if (validateInputs()) {

            updateMe();

        }

    }

    public void updateMe()
    {
        displayLoader();
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put("sid",SID);
            request.put("sname", sUname);
            request.put("semail", selectEmail.getText().toString());
            request.put("sphone", sPhone);
            request.put("saddress", slocation);
            request.put("ssecans", sSecAns);
            request.put("sagent", sAgentName);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.POST, base_url+"root_functions/updateShowroom.php", request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.dismiss();
                        try {
                            //Check if user got logged in successfully
                            //Toast.makeText(getApplicationContext(),"HIHIHI",Toast.LENGTH_LONG).show();
                            if (response.getInt("status") == 1) {
                                SharaedPrefernceConfig config;
                                config=new SharaedPrefernceConfig(getApplicationContext());
                                config.writeLoggedEmpty();
                                //ARE YOU LOGGED IN ALREADY? USING SHARED PREFERENCES
                                if(config.readStatus())
                                {
                                    config.writeLoginStatus(false);
                                    config.writeLoggedEmpty();
                                    Toast.makeText(getApplicationContext(),"You have been logged out",Toast.LENGTH_SHORT).show();
                                    Intent i= new Intent(getApplicationContext(),MainActivity.class);
                                    startActivity(i);
                                }

                                Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                                finish();


                            }else{
                                Toast.makeText(getApplicationContext(),
                                        response.getString("message"), Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.dismiss();

                        //Display error message whenever an error occurs
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsArrayRequest);

    }
    private boolean validateInputs() {
        if("".equals(sUname)){
            selectName.setError("Name cannot be empty");
            selectName.requestFocus();
            return false;
        }
        if("".equals(sPhone)){
            selectMobile.setError("Phone Number cannot be empty");
            selectMobile.requestFocus();
            return false;
        }
        if("".equals(slocation)){
            selectLocation.setError("Location cannot be empty");
            selectLocation.requestFocus();
            return false;
        }
        if("".equals(sSecAns)){
            selectSecans.setError("Security Answer cannot be empty");
            selectSecans.requestFocus();
            return false;
        }
        if("".equals(sAgentName)){
            selectAgent.setError("Agent Name cannot be empty");
            selectAgent.requestFocus();
            return false;
        }
        return true;
    }

    public void toFinishCancel(View view) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(UpdateProfile_Agent.this);
        builder1.setMessage("Discard Changes and Go Back?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                        dialog.cancel();

                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();

                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public void onBackPressed() {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(UpdateProfile_Agent.this);
        builder1.setMessage("Are you sure to Exit to Previous Menu? Any unsaved changes will be lost!");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        UpdateProfile_Agent.super.onBackPressed();
                        //ARE YOU LOGGED IN ALREADY? USING SHARED PREFERENCES

                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();

    }


}