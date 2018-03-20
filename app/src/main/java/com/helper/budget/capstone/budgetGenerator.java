package com.helper.budget.capstone;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class budgetGenerator extends AppCompatActivity {

    Button newBud;
    Button delBud;
    Button editBud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_generator);

        newBud = (Button) findViewById(R.id.newBudget);
        delBud = (Button) findViewById(R.id.deleteBudget);
        editBud = (Button) findViewById(R.id.editBudget);

        newBud.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Context context = v.getContext();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("How would you like to generate a new budget?")
                        .setCancelable(true)
                        .setNegativeButton("System", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //TODO Have the System create a new budget
                            }
                        })
                        .setPositiveButton("Manual", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //TODO Have the user manually enter in values
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        delBud.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Context context = v.getContext();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Delete the Current Budget Permanently?")
                        .setCancelable(true)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //TODO Delete current budget limits here
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        editBud.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO Launch Edit Budget Activity Dialog
            }
        });

    }
}
