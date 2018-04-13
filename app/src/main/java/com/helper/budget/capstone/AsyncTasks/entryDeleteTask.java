package com.helper.budget.capstone.AsyncTasks;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.helper.budget.capstone.Main_Activity;
import com.helper.budget.capstone.R;
import com.helper.budget.capstone.RecyclerAdapter;
import com.helper.budget.capstone.entryDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bryan on 3/27/2018.
 */

public class entryDeleteTask extends AsyncTask<String, Void, String> {

    private AppCompatActivity main;
    private RequestQueue queue;
    private final String ServerURL = "http://default-environment.rfemrggswx.us-west-2.elasticbeanstalk.com/";
    private ProgressDialog myProgressDialog;
    private entryDatabase EDB;
    private int pos;
    RecyclerAdapter recyclerAdapter;
    private String place;
    private String descript;

    /**
     * attach all of the elements needed to update the list of entries
     *
     * @param activity the context of the request
     * @param e the database to update
     * @param p the position of the entry to update
     * @param recyclerAdapter the recycler view that holds the list of entries
     */
    public entryDeleteTask(Main_Activity activity, entryDatabase e, int p, RecyclerAdapter recyclerAdapter) {
        attach(activity);
        EDB = e;
        pos = p;
        this.recyclerAdapter = recyclerAdapter;
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
            final String CatHolder;
            final String YearHolder;
            final String MonthHolder;
            final String DayHolder;
            final String DescriptionHolder;
            final String AmountHolder;
            final String PlaceHolder;
            try {//try to get the require values
                NameHolder = strings[0];
                CatHolder = strings[1];
                YearHolder = strings[2];
                MonthHolder = strings[3];
                DayHolder = strings[4];
                DescriptionHolder = strings[5];
                AmountHolder = strings[6];
                PlaceHolder = strings[7];
                descript = DescriptionHolder.replaceAll("'", "\\\\'");
                place = PlaceHolder.replaceAll("'", "\\\\'");
            }  catch (Exception e) {
                return "Not Enough Information Given";
            }
            //create a new web request for inserting the new budget
            StringRequest postRequest = new StringRequest(Request.Method.POST, ServerURL + "deleteEntry.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //on response from webserver, handle it
                            Toast.makeText(main, response+" From Entry", Toast.LENGTH_SHORT).show();
                            response = response.substring(0,4);
                            //if a success, delete the entry and refresh the list
                            if(response.equals("Data")){
                                EDB.getEntriesList().remove(pos);
                                Main_Activity m = (Main_Activity) main;
                                m.getAdapter().notifyDeletion(pos);
                                Button r = main.findViewById(R.id.mainRefresh);
                                r.performClick();
                            }
                            //stop the dialog to allow for user interaction
                            progressDialog_stop();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //if error, inform the user
                            //stop te dialog to allow for user interaction
                            progressDialog_stop();
                            Toast.makeText(main, "Error accessing the web server", Toast.LENGTH_SHORT).show();
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
                    params.put("description", descript);
                    params.put("amount", AmountHolder);
                    params.put("place", place);
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
