package com.helper.budget.capstone.AsyncTasks;

import android.app.ProgressDialog;
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
import com.helper.budget.capstone.Entry;
import com.helper.budget.capstone.Main_Activity;
import com.helper.budget.capstone.entryDatabase;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bryan on 3/26/2018.
 */

public class entrySelectTask extends AsyncTask<String,Void,String>{
    private Main_Activity main;
    private RequestQueue queue;
    private final String ServerURL = "http://default-environment.rfemrggswx.us-west-2.elasticbeanstalk.com/";
    private ProgressDialog myProgressDialog;
    private entryDatabase eDB;

    /**
     * attach the activity for context and database to alter
     *
     * @param activity the context of the task
     * @param e local database to update
     */
    public entrySelectTask(Main_Activity activity, entryDatabase e) {
        attach(activity);
        eDB = e;
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
        myProgressDialog.setMessage("Attempting to select data...");
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
        //if the response does not start with '[', then it is not JSON and an error occured
        if(response.charAt(0) != '['){
            //stop the dialog to allow user interaction and return
            Toast.makeText(main, response, Toast.LENGTH_SHORT).show();
            progressDialog_stop();
            return;
        }try {//otherwise, try to parse the JSON
            JSONArray jA = new JSONArray(response);
            JSONObject jO;
            Entry e;
            //for each object found, store it locally
            for(int i = 0; i<jA.length();i++){
                jO = jA.getJSONObject(i);
                String placeSpent = jO.getString("placeSpent");
                String category = jO.getString("category_Name");
                String date = jO.getString("dateEntered");
                String description = jO.getString("description");
                String cost = jO.getString("cost");
                Double amount = Double.parseDouble(cost);
                String[] dates = date.split("-");//split date into month,day, and year
                //only add the entry if it is valid and has a valid date
                if(dates.length == 3){
                    e = new Entry(placeSpent,description,amount, dates[1]+"/"+dates[2]+"/"+dates[0], category);
                    e.useDate();
                    eDB.addEntry(e);
                }
            }
            //after all is added, load the recycler view
            main.loadRecyclerView();
            //stop the dialog to allow user interaction and return
            progressDialog_stop();
            return;
        }catch(Exception e){
            //otherwise, inform user error parsing data
            Toast.makeText(main, "error parsing data", Toast.LENGTH_SHORT).show();
        }
        //stop the dialog to allow user interaction and return
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
            try {//try to get the require values
                NameHolder = strings[0];
            } catch (Exception e) {
                return "Not Enough Information Given";
            }
            //create a new web request for inserting the new budget
            StringRequest postRequest = new StringRequest(Request.Method.POST, ServerURL + "selectEntry.php",
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
