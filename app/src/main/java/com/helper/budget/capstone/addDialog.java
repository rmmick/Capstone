package com.helper.budget.capstone;

import android.app.Dialog;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.helper.budget.capstone.AsyncTasks.entryInsertTask;
import com.helper.budget.capstone.Main_Activity;
import com.helper.budget.capstone.R;
import com.helper.budget.capstone.entryDatabase;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Rachel on 2/22/2018.
 */

public class addDialog extends Dialog implements
        android.view.View.OnClickListener {

    public Main_Activity c;
    public Dialog d;
    private Button confirmAdd, cancel;
    private EditText name, cost, description, date;
    private entryDatabase EDB;
    private Spinner spinner;
    private String catChoice;

    public addDialog(Main_Activity a, entryDatabase EDB) {
        super(a);
        this.c = a;
        this.EDB = EDB;
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.add_entry_dialog);
        confirmAdd = (Button) findViewById(R.id.addEntryButton);
        cancel = (Button) findViewById(R.id.cancel);
        confirmAdd.setOnClickListener(this);
        cancel.setOnClickListener(this);

        name = (EditText) findViewById(R.id.entryName);
        cost = (EditText) findViewById(R.id.entryCost);
        description = (EditText) findViewById(R.id.entryDescription);
        date = (EditText) findViewById(R.id.entryDate);

        Calendar c = Calendar.getInstance();
        String today = (c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.DAY_OF_MONTH) + "/" + c.get(Calendar.YEAR);
        date.setText(today);
        catChoice = "Other";
        setupSpinner();

    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Button r = c.findViewById(R.id.mainRefresh);
        r.performClick();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addEntryButton:

                Double d = 0.0;
                Double cost2 = 0.0;
                String dateStr = "";
                String[] dates = new String[1];

                if(date.getText().toString().equals("") || cost.getText().toString().equals("")||name.getText().toString().equals("")||description.getText().toString().equals("")){
                    Toast.makeText(this.getContext(), "Cannot leave any fields blank", Toast.LENGTH_SHORT).show();
                    break;
                } else {
                    DecimalFormat decim = new DecimalFormat(".##");
                    d = Double.parseDouble(cost.getText().toString());
                    String aD = decim.format(d);
                    cost2 = Double.parseDouble(aD);
                    dateStr = date.getText().toString();
                    dates = dateStr.split("/");
                }

                if(dates.length != 3){
                    Toast.makeText(this.getContext(), "Invalid Date Format", Toast.LENGTH_SHORT).show();
                    break;
                }
                if(dates[0].length() > 2 || dates[1].length() > 2 || dates[2].length() > 4){
                    Toast.makeText(this.getContext(), "Invalid Date Format, Fields too long", Toast.LENGTH_SHORT).show();
                    break;
                }
                if(Integer.parseInt(dates[0]) > 12  || Integer.parseInt(dates[0]) < 0){
                    Toast.makeText(this.getContext(), "Invalid Date Format, Invalid Month", Toast.LENGTH_SHORT).show();
                    break;
                }
                if(Integer.parseInt(dates[1])<0){
                    Toast.makeText(this.getContext(), "Invalid Date Format, Invalid Day", Toast.LENGTH_SHORT).show();
                    break;
                }
                if(Integer.parseInt(dates[0]) == 2){
                    GregorianCalendar gc = new GregorianCalendar();
                    if(gc.isLeapYear(Integer.parseInt(dates[2]))){
                        if(Integer.parseInt(dates[1])>29){
                            Toast.makeText(this.getContext(), "Invalid Date Format,Invalid Day", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }else{
                        if(Integer.parseInt(dates[1])>28){
                            Toast.makeText(this.getContext(), "Invalid Date Format,Invalid Day", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }

                }
                else if((Integer.parseInt(dates[0]) == 4)
                        ||(Integer.parseInt(dates[0]) == 6)
                        ||(Integer.parseInt(dates[0]) == 8)
                        ||(Integer.parseInt(dates[0]) == 11)){
                    if(Integer.parseInt(dates[1])>30){
                        Toast.makeText(this.getContext(), "Invalid Date Format,Invalid Day", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }else{
                    if(Integer.parseInt(dates[1])>31){
                        Toast.makeText(this.getContext(), "Invalid Date Format,Invalid Day", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
                //check the username and desciption for ','',*, etc
                String descript = description.getText().toString().replaceAll("'", "\\\\'");
                String place = name.getText().toString().replaceAll("'", "\\\\'");
                entryInsertTask task = new entryInsertTask(c, EDB);
                task.execute(EDB.getUsername(),catChoice,dates[2], dates[0],dates[1],
                      descript, cost2.toString(), place );
                dismiss();

                break;
            case R.id.cancel:
                cancel();
                break;
            default:
                cancel();
                break;
        }
    }

    private void setupSpinner(){

        spinner = (Spinner) findViewById(R.id.categorySpinner);
        spinner.setAdapter(null);

        final ArrayList<String> s = new ArrayList<>();
        s.add("Vehicle");
        s.add("Pets");
        s.add("Home");
        s.add("Food");
        s.add("Entertainment");
        s.add("Other");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, s);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setEnabled(true);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                catChoice = spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }
}