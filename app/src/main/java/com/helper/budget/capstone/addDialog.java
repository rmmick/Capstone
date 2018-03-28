package com.helper.budget.capstone;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.helper.budget.capstone.AsyncTasks.entryInsertTask;

import java.text.DecimalFormat;
import java.util.ArrayList;

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
        setContentView(R.layout.dialog_add);
        confirmAdd = (Button) findViewById(R.id.addEntryButton);
        cancel = (Button) findViewById(R.id.cancel);
        confirmAdd.setOnClickListener(this);
        cancel.setOnClickListener(this);

        name = (EditText) findViewById(R.id.entryName);
        cost = (EditText) findViewById(R.id.entryCost);
        description = (EditText) findViewById(R.id.entryDescription);
        date = (EditText) findViewById(R.id.entryDate);

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

                DecimalFormat decim = new DecimalFormat(".##");
                Double d = Double.parseDouble(cost.getText().toString());
                String aD = decim.format(d);
                Double cost2 = Double.parseDouble(aD);
                String dateStr = date.getText().toString();
                String[] dates = dateStr.split("/");
                if(dates.length != 3){
                    Toast.makeText(this.getContext(), "Invalid Date Format", Toast.LENGTH_SHORT).show();
                    break;
                }
                if(dates[0].length() > 2 || dates[1].length() > 2 || dates[2].length() > 4){
                    Toast.makeText(this.getContext(), "Invalid Date Format, Fields too long", Toast.LENGTH_SHORT).show();
                    break;
                }
                entryInsertTask task = new entryInsertTask(c, EDB);
                task.execute(EDB.username,catChoice,dates[2], dates[0],dates[1],
                        description.getText().toString(), cost2.toString(), name.getText().toString());
                /*Entry e = new Entry(name.getText().toString(),
                        description.getText().toString(),
                        cost2,
                        date.getText().toString(),
                        catChoice);
                EDB.addEntry(e);*/
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