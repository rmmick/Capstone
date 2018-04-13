package com.helper.budget.capstone.AsyncTasks;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.helper.budget.capstone.Main_Activity;
import com.helper.budget.capstone.entryDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bryan on 4/4/2018.
 */

public class budgetInsertTask extends AsyncTask<String, Void, String> {
    private Main_Activity main;
    private RequestQueue queue;
    private final String ServerURL = "http://default-environment.rfemrggswx.us-west-2.elasticbeanstalk.com/";
    private ProgressDialog myProgressDialog;
    private entryDatabase EDB;
    private double newAmount;
    private String CatHolder;

    /**
     * attach the activity for context and database to alter
     *
     * @param activity the context of the task
     * @param e local database to update
     */
    public budgetInsertTask(Main_Activity activity, entryDatabase e) {
        attach(activity);
        EDB = e;
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
        myProgressDialog.setMessage("Attempting to delete data...");
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
    void storeResponse(String response){
        //if the budget was inserted successfully, set the budget locally
        if(response.equals("Data Inserted Successfully")){
            EDB.setBudgets(CatHolder,newAmount);
        }else{
            //otherwise report the error
            Toast.makeText(main, response, Toast.LENGTH_SHORT).show();
        }
        //stop the progress dialog to allow UI interaction
        progressDialog_stop();
    }

    /**
     * sets the main activity context
     *
     * @param activity activity context given
     */
    void attach(Main_Activity activity) {
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
        final String YearHolder;
        final String DayHolder;
        final String MonthHolder;
        final String AmountHolder;
        final String userGenHolder;
        try {//try to get the require values
            NameHolder = strings[0];
            CatHolder = strings[1];
            YearHolder = strings[2];
            MonthHolder = strings[3];
            DayHolder = strings[4];
            userGenHolder = strings[5];
            AmountHolder = strings[6];
            newAmount = Double.parseDouble(AmountHolder);
        }  catch (Exception e) {
            return "Not Enough Information Given";
        }
        //create a new web request for inserting the new budget
        StringRequest postRequest = new StringRequest(Request.Method.POST, ServerURL + "insertBudget.php",
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
                        storeResponse("Problem accessing the web server");
                    }
                }

        ) {
            @Override
            protected Map<String, String> getParams() {
                //send the parameters to the webserver for the SQL
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", NameHolder);
                params.put("category", CatHolder);
                params.put("year", YearHolder);
                params.put("month", MonthHolder);
                params.put("day", DayHolder);
                params.put("userGenerated", userGenHolder);
                params.put("amount", AmountHolder);
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
