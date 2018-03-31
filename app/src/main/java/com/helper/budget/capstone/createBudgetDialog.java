package com.helper.budget.capstone;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

public class createBudgetDialog extends Dialog implements
        android.view.View.OnClickListener {

    entryDatabase mEDB;
    int mType;
    final private int SYSTEM = 0;
    final private int MANUAL = 1;
    EditText vehicle;
    EditText pets;
    EditText home;
    EditText food;
    EditText entertainment;
    EditText other;

    public createBudgetDialog(Activity a, entryDatabase EDB, int type){
        super(a);
        mEDB = EDB;
        mType = type;
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_budget_dialog);


        vehicle = (EditText) findViewById(R.id.vCalc);
        pets = (EditText) findViewById(R.id.pCalc);
        home = (EditText) findViewById(R.id.hCalc);
        food = (EditText) findViewById(R.id.fCalc);
        entertainment = (EditText) findViewById(R.id.eCalc);
        other = (EditText) findViewById(R.id.oCalc);
        Button cancel = (Button) findViewById(R.id.cancelSys);
        Button ok = (Button) findViewById(R.id.acceptSys);

        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dismiss();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO Save limit values here
                dismiss();
            }
        });

        if(mType == SYSTEM){
            TextView tv = (TextView) findViewById(R.id.calcTitle);
            tv.setText("Calculated Budget Limits:");
            systemGenerated();
        } else if (mType == MANUAL){
            TextView tv = (TextView) findViewById(R.id.calcTitle);
            tv.setText("Enter in Budget Limits:");
            manualGenerated();
        }

    }

    private void manualGenerated() {
        //TODO Save Users entered Limits
    }

    private void systemGenerated(){

        mEDB.calcSysBudget();
        double [] averages = mEDB.getAverages();
        DecimalFormat df = new DecimalFormat("#.00");

        vehicle.setText(df.format(averages[0]));
        pets.setText(df.format(averages[1]));
        home.setText(df.format(averages[2]));
        food.setText(df.format(averages[3]));
        entertainment.setText(df.format(averages[4]));
        other.setText(df.format(averages[5]));

    }

    @Override
    public void onClick(View view) {

    }
}
