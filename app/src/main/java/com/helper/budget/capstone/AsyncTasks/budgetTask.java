//package com.helper.budget.capstone.AsyncTasks;
//
//import android.app.ProgressDialog;
//import android.content.Intent;
//import android.os.AsyncTask;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//import android.widget.Button;
//import android.widget.Toast;
//
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
//import com.helper.budget.capstone.R;
//import com.helper.budget.capstone.budgetGenerator;
//import com.helper.budget.capstone.entryDatabase;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * Created by Bryan on 3/26/2018.
// */
//
//public class budgetTask extends AsyncTask<String, Void, String>{
//    private final String SELECT = "select";
//    private final String DELETE = "delete";
//    private final String INSERT = "user";
//    private budgetGenerator main;
//    private RequestQueue queue;
//    private String fromWeb;
//    private final String ServerURL = "http://default-environment.rfemrggswx.us-west-2.elasticbeanstalk.com/";
//    private ProgressDialog myProgressDialog;
//    private entryDatabase EDB;
//    private int pos;
//
//    budgetTask(budgetGenerator activity, entryDatabase e, int p) {
//
//        attach(activity);
//        EDB = e;
//        pos = p;
//    }
//
//    @Override
//    protected void onPreExecute() {
//        super.onPreExecute();
//        progressDialog_start();
//    }
//    private void progressDialog_start() {
//        myProgressDialog = new ProgressDialog(main);
//        myProgressDialog.setTitle("Please wait");
//        myProgressDialog.setMessage("Checking login...");
//        myProgressDialog.setCancelable(false);
//        myProgressDialog.show();
//    }
//
//    private void progressDialog_stop(){
//        if(myProgressDialog != null) {
//            myProgressDialog.dismiss();
//        }
//    }
//
//    void storeResponse(String response){
//        Log.d("MAIN TASK", response);
//        fromWeb = response;
//        Toast.makeText(main, fromWeb, Toast.LENGTH_SHORT).show();
//        Log.d("MAIN TASK", fromWeb);
//        progressDialog_stop();
//    }
//    void detach() {
//        main = null;
//    }
//
//    void attach(budgetGenerator activity) {
//        main = activity;
//    }
//
//    @Override
//    protected void onPostExecute(String s) {
//        super.onPostExecute(s);
//        /*if (main != null) {
//            Toast.makeText(main, s, Toast.LENGTH_SHORT).show();
//        }
//        detach();*/
//    }
//
//    @Override
//    protected void onCancelled() {
//        super.onCancelled();
//    }
//
//    @Override
//    protected String doInBackground(String... strings) {
//        fromWeb = "";
//        String key;
//        try {
//            key = strings[0];
//        } catch (Exception e) {
//            return "no key given";
//        }
//        if (key.equals(SELECT)) {
//            final String NameHolder;
//            final String CatHolder;
//            try {
//                NameHolder = strings[1];
//                CatHolder = strings[2];
//            } catch (Exception e) {
//                return "Not Enough Information Given";
//            }
//            StringRequest postRequest = new StringRequest(Request.Method.POST, ServerURL + "selectBudget.php",
//                    new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//                            // response
//                            storeResponse(response);
//                            Log.d("Response", response);
//                            //EDB.getEntriesList().remove(pos);
//                            Button r = main.findViewById(R.id.mainRefresh);
//                            r.performClick();
//                        }
//                    },
//                    new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            // error
//                            storeResponse(error.toString()+" From Entry");
//                            Log.d("Error.Response", error.toString());
//                        }
//                    }
//            ) {
//                @Override
//                protected Map<String, String> getParams() {
//                    Map<String, String> params = new HashMap<String, String>();
//                    params.put("username", NameHolder);
//                    params.put("category", CatHolder);
//                    return params;
//                }
//            };
//            if (main != null) {
//                queue = Volley.newRequestQueue(main);
//                queue.add(postRequest);
//                return fromWeb;
//            }
//        } else if (key.equals(DELETE)) {
//            final String NameHolder;
//            final String CatHolder;
//
//            try {
//                NameHolder = strings[1];
//                CatHolder = strings[2];
//            } catch (Exception e) {
//                return "Not Enough Information Given";
//            }
//            StringRequest postRequest = new StringRequest(Request.Method.POST, ServerURL + "deleteBudget.php",
//                    new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//                            // response
//                            storeResponse(response+" From Budget");
//                            Log.d("Response", response);
//                        }
//                    },
//                    new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            // error
//                            storeResponse(error.toString()+" From Budget");
//                            Log.d("Error.Response", error.toString());
//                        }
//                    }
//            ) {
//                @Override
//                protected Map<String, String> getParams() {
//                    Map<String, String> params = new HashMap<String, String>();
//                    params.put("username", NameHolder);
//                    params.put("category", CatHolder);
//                    return params;
//                }
//            };
//            if (main != null) {
//                queue = Volley.newRequestQueue(main);
//                queue.add(postRequest);
//                return fromWeb+" From Budget";
//            }
//        } else if (key.equals(USER)) {
//            final String NameHolder;
//            try {
//                NameHolder = strings[1];
//            } catch (Exception e) {
//                return "Not Enough Information Given";
//            }
//            StringRequest postRequest = new StringRequest(Request.Method.POST, ServerURL + "deleteUser.php",
//                    new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//                            // response
//                            storeResponse(response+" From User");
//                            Log.d("Response", response);
//                        }
//                    },
//                    new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            // error
//                            storeResponse(error.toString()+" From User");
//                            Log.d("Error.Response", error.toString());
//                        }
//                    }
//            ) {
//                @Override
//                protected Map<String, String> getParams() {
//                    Map<String, String> params = new HashMap<String, String>();
//                    params.put("username", NameHolder);
//                    return params;
//                }
//            };
//            if (main != null) {
//                queue = Volley.newRequestQueue(main);
//                queue.add(postRequest);
//                return fromWeb+" From User";
//            }
//        }
//        return "Problem Deleted Data";
//    }
//}
