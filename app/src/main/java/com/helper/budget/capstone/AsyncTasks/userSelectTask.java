package com.helper.budget.capstone.AsyncTasks;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
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

public class userSelectTask extends AsyncTask<String, Void, String>{
    private Login main;
    private RequestQueue queue;
    private final String ServerURL = "http://default-environment.rfemrggswx.us-west-2.elasticbeanstalk.com/";
    private ProgressDialog myProgressDialog;
    private String password, username;

    /**
     * attach the activity for context
     *
     * @param activity the context of the task
     */
    public userSelectTask(Login activity) {
        attach(activity);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //start a progress dialog to stop to user from interacting with the UI
        progressDialog_start();
    }

    /**
     * Starts the  progress dialog
     */
    private void progressDialog_start() {
        myProgressDialog = new ProgressDialog(main);
        myProgressDialog.setTitle("Please wait");
        myProgressDialog.setMessage("Attempting to login...");
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
        //if the response does not start with '[', then it is not JSON and an error occurred
        if(response.charAt(0) != '['){
            //stop the dialog to allow user interaction and return
            Toast.makeText(main, response, Toast.LENGTH_SHORT).show();
            progressDialog_stop();
            return;
        }
        //if the response is [] then there was no entry returned
        if(response.equals("[]")){
            //inform the user they are not in the database
            Toast.makeText(main, "user does not exist in database", Toast.LENGTH_SHORT).show();
            //stop the dialog to allow user interaction and return
            progressDialog_stop();
            return;
        }
        try{
            //parse the JSON
            JSONArray jA = new JSONArray(response);
            JSONObject jO = jA.getJSONObject(0);
            //check to see if user is there and if password is correct
            if(password.equals(jO.getString("password"))){
                //if so start intent
                progressDialog_stop();
                Intent intent = new Intent(main, Main_Activity.class);
                intent.putExtra("username", username);
                main.startActivity(intent);
            }else{
                //the password was incorrect, inform the user
                Toast.makeText(main, "incorrect username or password", Toast.LENGTH_SHORT).show();
                //stop the dialog to allow user interaction and return
                progressDialog_stop();
            }
        }catch(Exception e){
            //if here, there was an error parsing, inform the user of the error
            Toast.makeText(main, "Error Parsing data", Toast.LENGTH_SHORT).show();
            //stop the dialog to allow user interaction and return
            progressDialog_stop();
        }
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
            try {//try to get the require values
                username = strings[0];
                password = strings[1];
            } catch (Exception e) {
                return "Not Enough Information Given";
            }
            //create a new web request for selecting the budgets
            StringRequest postRequest = new StringRequest(Request.Method.POST, ServerURL + "selectUser.php",
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
                            storeResponse("Problem accessing The web server");
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() {
                    //send the parameters to the webserver for the SQL
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("username", username);
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
