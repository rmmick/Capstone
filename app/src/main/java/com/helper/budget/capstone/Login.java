package com.helper.budget.capstone;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
        Button newUser = (Button) findViewById(R.id.button4);
        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            resetInsertTask();
                if(task1 != null && tvUser != null && tvPassword != null) {
                    task1.execute(tvUser.getText().toString(), tvPassword.getText().toString());
                }else{
                    Toast.makeText(v.getContext(), "null error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
