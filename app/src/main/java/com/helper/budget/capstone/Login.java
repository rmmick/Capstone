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

/**
 * allows the user to log in
 * created by Bryan and Rachel
 */
public class Login extends AppCompatActivity {
    userSelectTask task;
    userInsertTask task1;

    /**
     * creates a new userSelect task
     */
    private void resetSelectTask(){
        task = new userSelectTask(this);
    }

    /**
     * creates a new userInsert task
     */
    private void resetInsertTask(){
        task1 = new userInsertTask(this);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //sets the layout and gets all the XML components required
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button log = (Button) findViewById(R.id.loginButton);
        setTitle("");
        final EditText tvUser = (EditText) findViewById(R.id.editText3);
        final EditText tvPassword = (EditText) findViewById(R.id.editText4);

        //sets the login button listener to reset the user select task
        //check the required components and execute the task with the parameters given
        log.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                resetSelectTask();
                if(task != null && tvUser != null && tvPassword != null) {
                    //allows the user to have ''' in the username and password
                    //SQL syntax only allows ''' if it is '\'', so it is replaced in the username before task is executed
                    task.execute(tvUser.getText().toString().replaceAll("'", "\\\\'")
                            , tvPassword.getText().toString().replaceAll("'", "\\\\'"));
                }else{
                    Toast.makeText(v.getContext(), "null error", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //creates a new layout for adding a user
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

        //sets the onclick listenr for new user to create a dialog to add a new user
        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                //first reset the task
                resetInsertTask();
                //if there is a layout in the linear layout, remove it
                if(layout.getParent()!=null)
                    ((ViewGroup)layout.getParent()).removeView(layout);

                //create a dialog that allows the user to add a new user with the layout created earlier
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Enter in a Username and Password:")
                        .setCancelable(true)
                        .setView(layout)
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            //if canceled, dismisses dialog
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                        .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //other wise, tries to add a user with the data given
                                //allows the user to have ''' in the username and password
                                //SQL syntax only allows ''' if it is '\'', so it is replaced in the username before task is executed
                                if(task1 != null && newUsername != null && newPassword != null) {
                                    task1.execute(newUsername.getText().toString().replaceAll("'", "\\\\'")
                                            , newPassword.getText().toString().replaceAll("'", "\\\\'"));
                                }else{
                                    Toast.makeText(v.getContext(), "null error", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                //creates and shows the dialog
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

}
