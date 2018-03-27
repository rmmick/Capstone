package com.helper.budget.capstone;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.helper.budget.capstone.AsyncTasks.*;
public class Login extends AppCompatActivity {
    userSelectTask task;
    userInsertTask task1;
    private void resetSelectTask(){
        task = new userSelectTask(this);
    }
    private void resetInsertTask(){
        task1 = new userInsertTask(this);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button log = (Button) findViewById(R.id.loginButton);
        setTitle("");
        final EditText tvUser = (EditText) findViewById(R.id.editText3);
        final EditText tvPassword = (EditText) findViewById(R.id.editText4);
        log.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                resetSelectTask();
                if(task != null && tvUser != null && tvPassword != null) {
                    task.execute(tvUser.getText().toString(), tvPassword.getText().toString());
                }else{
                    Toast.makeText(v.getContext(), "null error", Toast.LENGTH_SHORT).show();
                }
            }
        });

        final Context context = this;
        Button newUser = (Button) findViewById(R.id.button4);

        final LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText newUsername = new EditText(context);
        newUsername.setHint("Username");
        layout.addView(newUsername);

        final EditText newPassword = new EditText(context);
        newPassword.setHint("Password");
        layout.addView(newPassword);

        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
            resetInsertTask();

                if(layout.getParent()!=null)
                    ((ViewGroup)layout.getParent()).removeView(layout);

                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Enter in a Username and Password:")
                        .setCancelable(true)
                        .setView(layout)
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                        .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if(task1 != null && newUsername != null && newPassword != null) {
                                    task1.execute(newUsername.getText().toString(), newPassword.getText().toString());
                                }else{
                                    Toast.makeText(v.getContext(), "null error", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

//                if(task1 != null && tvUser != null && tvPassword != null) {
//                    task1.execute(tvUser.getText().toString(), tvPassword.getText().toString());
//                }else{
//                    Toast.makeText(v.getContext(), "null error", Toast.LENGTH_SHORT).show();
//                }
            }
        });
    }

}
