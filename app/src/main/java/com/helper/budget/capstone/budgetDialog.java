package com.helper.budget.capstone;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.helper.budget.capstone.R;
import com.helper.budget.capstone.entryDatabase;

import java.text.DecimalFormat;

/**
 * Created by Rachel on 3/19/2018.
 */

public class budgetDialog extends Dialog implements
        android.view.View.OnClickListener {

    public budgetDialog(Activity a, entryDatabase EDB){
        super(a);

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
    }

    @Override
    public void onClick(View view) {

    }
}
