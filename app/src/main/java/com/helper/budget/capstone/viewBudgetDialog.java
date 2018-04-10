package com.helper.budget.capstone;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.Calendar;

/**
 * Created by Rachel on 3/19/2018.
 */

public class viewBudgetDialog extends Dialog implements
        android.view.View.OnClickListener {

    entryDatabase e;

    public viewBudgetDialog(Activity a, entryDatabase EDB){
        super(a);
        e = EDB;
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.budget_dialog);
        String title = "Monthly Report for " + (Calendar.getInstance().get(Calendar.MONTH) + 1) + "/" + Calendar.getInstance().get(Calendar.YEAR);
        double[] monthTotals = e.getTotalsForMonth(Calendar.getInstance().get(Calendar.MONTH) + 1, Calendar.getInstance().get(Calendar.YEAR));
        TextView tv = (TextView)findViewById(R.id.vehicleTotal);
        tv.setText(Double.toString(monthTotals[0]));
        tv = (TextView)findViewById(R.id.petsTotal);
        tv.setText(Double.toString(monthTotals[1]));
        tv = (TextView)findViewById(R.id.houseTotal);
        tv.setText(Double.toString(monthTotals[2]));
        tv = (TextView)findViewById(R.id.foodTotal);
        tv.setText(Double.toString(monthTotals[3]));
        tv = (TextView)findViewById(R.id.entertainmentTotal);
        tv.setText(Double.toString(monthTotals[4]));
        tv = (TextView)findViewById(R.id.otherTotal);
        tv.setText(Double.toString(monthTotals[5]));
        double[] budgets = e.getBudgets();
        if(budgets[0] != 0) {
            tv = (TextView) findViewById(R.id.vehicleLimit);
            tv.setText(Double.toString(budgets[0]));
        }
        if(budgets[1] != 0) {
            tv = (TextView) findViewById(R.id.petsLimit);
            tv.setText(Double.toString(budgets[1]));
        }
        if(budgets[2] != 0) {
            tv = (TextView) findViewById(R.id.houseLimit);
            tv.setText(Double.toString(budgets[2]));
        }
        if(budgets[3] != 0) {
            tv = (TextView) findViewById(R.id.foodLimit);
            tv.setText(Double.toString(budgets[3]));
        }
        if(budgets[4] != 0) {
            tv = (TextView) findViewById(R.id.entertainmentLimit);
            tv.setText(Double.toString(budgets[4]));
        }
        if(budgets[5] != 0) {
            tv = (TextView) findViewById(R.id.otherLimit);
            tv.setText(Double.toString(budgets[5]));
        }

        tv = (TextView) findViewById(R.id.textView);
        tv.setText(title);


    }

    @Override
    public void onClick(View view) {

    }
}
