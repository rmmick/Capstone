package com.helper.budget.capstone.AsyncTasks;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.helper.budget.capstone.Login;
import com.helper.budget.capstone.Main_Activity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bryan on 3/26/2018.
 */
public class userInsertTask extends AsyncTask<String, Void, String> {
    private Login main;
    private RequestQueue queue;
    private final String ServerURL = "http://default-environment.rfemrggswx.us-west-2.elasticbeanstalk.com/";
    private ProgressDialog myProgressDialog;
    private String password;

    /**
     * attach the activity for context
     *
     * @param activity the context of the task
     */
    public userInsertTask(Login activity) {
        attach(activity);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog_start();
    }

    /**
     * Starts the  progress dialog
     */
    private void progressDialog_start() {
        myProgressDialog = new ProgressDialog(main);
        myProgressDialog.setTitle("Please wait");
        myProgressDialog.setMessage("Attempting to add user...");
        myProgressDialog.setCancelable(false);
        myProgressDialog.show();
    }

    /**
     * dismisses the dialog
     */
    private void progressDialog_stop(){
        if(myProgressDialog != null) {
            myProgressDialog.dismiss();
        }
    }

    /**
     * processes the response received from the webserver
     *
     * @param response message received from the webserver
     */
    private void storeResponse(String response){
        //if there was an error, inform the user
        if(response.equals("Error Inserting Data")){
            Toast.makeText(main, response, Toast.LENGTH_SHORT).show();
        }
        //other user, inform the user that the user was entered successfully
        else{
            Toast.makeText(main, "User added successfully", Toast.LENGTH_SHORT).show();
        }
        //stop the progress dialog to allow user interaction
        progressDialog_stop();
    }

    /**
     * sets the main activity context
     *
     * @param activity activity context given
     */
    void attach(Login activity) {
        main = activity;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    @Override
    protected String doInBackground(String... strings) {
        final String NameHolder;
        try {//try to get the require values
            NameHolder = strings[0];
            password = strings[1];
        } catch (Exception e) {
            return "Not Enough Information Given";
        }
        //create a new web request for inserting the new budget
        StringRequest postRequest = new StringRequest(Request.Method.POST, ServerURL + "insertUser.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //on response from webserver, handle it
                        storeResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //if error, inform the user
                        Toast.makeText(main,"Problem accessing the web server", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                //send the parameters to the webserver for the SQL
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", NameHolder);
                params.put("password", password);
                return params;
            }
        };
        //add the request to the queue to be picked up by the next network thread
        if (main != null) {
            queue = Volley.newRequestQueue(main);
            queue.add(postRequest);
        }

        return "Success";
    }
}