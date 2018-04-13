package com.helper.budget.capstone;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.helper.budget.capstone.AsyncTasks.budgetDeleteTask;
import com.helper.budget.capstone.AsyncTasks.budgetInsertTask;
import com.helper.budget.capstone.Main_Activity;
import com.helper.budget.capstone.R;
import com.helper.budget.capstone.entryDatabase;

import java.text.DecimalFormat;
import java.util.Calendar;

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
    Main_Activity main;

    /**
     * gives the context of the dialog, the database to alter and the type of budget
     * @param a context of the dialog
     * @param EDB database to alter
     * @param type type of budget created
     */
    public createBudgetDialog(Main_Activity a, entryDatabase EDB, int type){
        super(a);
        mEDB = EDB;
        mType = type;
        main = a;
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_budget_dialog);

        //set the layout and initialize all of the XML components
        vehicle = (EditText) findViewById(R.id.vCalc);
        pets = (EditText) findViewById(R.id.pCalc);
        home = (EditText) findViewById(R.id.hCalc);
        food = (EditText) findViewById(R.id.fCalc);
        entertainment = (EditText) findViewById(R.id.eCalc);
        other = (EditText) findViewById(R.id.oCalc);
        Button cancel = (Button) findViewById(R.id.cancelSys);
        Button ok = (Button) findViewById(R.id.acceptSys);

        //if the user cancels, the dialog is dismissed
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dismiss();
            }
        });

        //if the user selected system generated, calculations are done for each category
        if(mType == SYSTEM){
            //sets up the dialog and calculates the budgets
            TextView tv = (TextView) findViewById(R.id.calcTitle);
            tv.setText("Calculated Budget Limits:");
            systemGenerated();
            //if the user accepts, a task is executed for each category to update the budgets
            //the userGen option is an F for system gen because when userGen is checked to see if its valid on the website
            //and userGen if equal to 0, it will always be false because if(0) is always false in PHP
            //because of this, F is used as an alternative
            ok.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //for vehicle
                    if(!vehicle.getText().toString().equals(".00")){
                        //if budget is empty, insert a new budget
                        if(mEDB.getBudgets()[0] == 0.00 ){
                            budgetInsertTask task = new budgetInsertTask(main,mEDB);
                            task.execute(mEDB.username,
                                    "Vehicle",
                                    Integer.toString(Calendar.getInstance().get(Calendar.YEAR)),
                                    Integer.toString(Calendar.getInstance().get(Calendar.MONTH) + 1),
                                    Integer.toString(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)),
                                    "F",
                                    vehicle.getText().toString());
                        }else{//otherwise delete the old budget
                            budgetDeleteTask task = new budgetDeleteTask(main, mEDB);
                            task.execute(mEDB.username,
                                    "Vehicle",
                                    vehicle.getText().toString(),
                                    "F");
                        }
                    }

                    //for pets
                    if(!pets.getText().toString().equals(".00")){
                        if(mEDB.getBudgets()[1] == 0.00) {
                            //if budget is empty, insert a new budget
                            budgetInsertTask task = new budgetInsertTask(main, mEDB);
                            task.execute(mEDB.username,
                                    "Pets",
                                    Integer.toString(Calendar.getInstance().get(Calendar.YEAR)),
                                    Integer.toString(Calendar.getInstance().get(Calendar.MONTH) + 1),
                                    Integer.toString(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)),
                                    "F",
                                    pets.getText().toString());
                        }else{
                            //otherwise delete the old budget
                            budgetDeleteTask task = new budgetDeleteTask(main, mEDB);
                            task.execute(mEDB.username,
                                    "Pets",
                                    pets.getText().toString(),
                                    "F");
                        }
                    }

                    //for home
                    if(!home.getText().toString().equals(".00")){
                        if(mEDB.getBudgets()[2] == 0.00 ) {
                            //if budget is empty, insert a new budget
                            budgetInsertTask task = new budgetInsertTask(main, mEDB);
                            task.execute(mEDB.username,
                                    "Home",
                                    Integer.toString(Calendar.getInstance().get(Calendar.YEAR)),
                                    Integer.toString(Calendar.getInstance().get(Calendar.MONTH) + 1),
                                    Integer.toString(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)),
                                    "F",
                                    home.getText().toString());
                        }else{
                            //otherwise delete the old budget
                            budgetDeleteTask task = new budgetDeleteTask(main, mEDB);
                            task.execute(mEDB.username,
                                    "Home",
                                    home.getText().toString(),
                                    "F");
                        }
                    }

                    //for food
                    if(!food.getText().toString().equals(".00")){
                        if(mEDB.getBudgets()[3] == 0.00 ){
                            //if budget is empty, insert a new budget
                            budgetInsertTask task = new budgetInsertTask(main, mEDB);
                            task.execute(mEDB.username,
                                    "Food",
                                    Integer.toString(Calendar.getInstance().get(Calendar.YEAR)),
                                    Integer.toString(Calendar.getInstance().get(Calendar.MONTH) + 1),
                                    Integer.toString(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)),
                                    "F",
                                    food.getText().toString());
                        }else{
                            //otherwise delete the old budget
                            budgetDeleteTask task = new budgetDeleteTask(main, mEDB);
                            task.execute(mEDB.username,
                                    "Food",
                                    food.getText().toString(),
                                    "F");
                        }
                    }

                    //for entertainment
                    if(!entertainment.getText().toString().equals(".00")){
                        if(mEDB.getBudgets()[4] == 0.00 ) {
                            //if budget is empty, insert a new budget
                            budgetInsertTask task = new budgetInsertTask(main, mEDB);
                            task.execute(mEDB.username,
                                    "Entertainment",
                                    Integer.toString(Calendar.getInstance().get(Calendar.YEAR)),
                                    Integer.toString(Calendar.getInstance().get(Calendar.MONTH) + 1),
                                    Integer.toString(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)),
                                    "F",
                                    entertainment.getText().toString());
                        }else{
                            //otherwise delete the old budget
                            budgetDeleteTask task = new budgetDeleteTask(main, mEDB);
                            task.execute(mEDB.username,
                                    "Entertainment",
                                    entertainment.getText().toString(),
                                    "F");
                        }
                    }

                    //for other
                    if(!other.getText().toString().equals(".00")){
                        if(mEDB.getBudgets()[5] == 0.00) {
                            //if budget is empty, insert a new budget
                            budgetInsertTask task = new budgetInsertTask(main, mEDB);
                            task.execute(mEDB.username,
                                    "Other",
                                    Integer.toString(Calendar.getInstance().get(Calendar.YEAR)),
                                    Integer.toString(Calendar.getInstance().get(Calendar.MONTH) + 1),
                                    Integer.toString(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)),
                                    "F",
                                    other.getText().toString());
                        }else{
                            //otherwise delete the old budget
                            budgetDeleteTask task = new budgetDeleteTask(main, mEDB);
                            task.execute(mEDB.username,
                                    "Other",
                                    other.getText().toString(),
                                    "F");
                        }
                    }
                    dismiss();
                }
            });
        }
        //otherwise, the user manually input the budget, the userGen field is a 1 instead of an F
        else if (mType == MANUAL){
            TextView tv = (TextView) findViewById(R.id.calcTitle);
            tv.setText("Enter in Budget Limits:");
            ok.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //Save limit values here
                    if(!vehicle.getText().toString().isEmpty()
                            && Double.parseDouble(vehicle.getText().toString()) != 0.0){
                        if(mEDB.getBudgets()[0] == 0.00 ){
                            //if budget is empty, insert a new budget
                            budgetInsertTask task = new budgetInsertTask(main, mEDB);
                            task.execute(mEDB.username,
                                    "Vehicle",
                                    Integer.toString(Calendar.getInstance().get(Calendar.YEAR)),
                                    Integer.toString(Calendar.getInstance().get(Calendar.MONTH) + 1),
                                    Integer.toString(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)),
                                    "1",
                                    vehicle.getText().toString());
                        }else{
                            //otherwise delete the old budget
                            budgetDeleteTask task = new budgetDeleteTask(main, mEDB);
                            task.execute(mEDB.username,
                                    "Vehicle",
                                    vehicle.getText().toString(),
                                    "1");
                        }
                    }

                    //for pets
                    if(!pets.getText().toString().isEmpty()
                            && Double.parseDouble(pets.getText().toString()) != 0.0) {
                                if(mEDB.getBudgets()[1] == 0.00 ) {
                                    //if budget is empty, insert a new budget
                                    budgetInsertTask task = new budgetInsertTask(main, mEDB);
                                    task.execute(mEDB.username,
                                            "Pets",
                                            Integer.toString(Calendar.getInstance().get(Calendar.YEAR)),
                                            Integer.toString(Calendar.getInstance().get(Calendar.MONTH) + 1),
                                            Integer.toString(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)),
                                            "1",
                                            pets.getText().toString());
                                }else{
                                    //otherwise delete the old budget
                                    budgetDeleteTask task = new budgetDeleteTask(main, mEDB);
                                    task.execute(mEDB.username,
                                            "Pets",
                                            pets.getText().toString(),
                                            "1");
                                }
                    }

                    //for home
                    if(!home.getText().toString().isEmpty()
                            && Double.parseDouble(home.getText().toString()) != 0.0 ){
                                if(mEDB.getBudgets()[2] == 0.00 ) {
                                    //if budget is empty, insert a new budget
                                    budgetInsertTask task = new budgetInsertTask(main, mEDB);
                                    task.execute(mEDB.username,
                                            "Home",
                                            Integer.toString(Calendar.getInstance().get(Calendar.YEAR)),
                                            Integer.toString(Calendar.getInstance().get(Calendar.MONTH) + 1),
                                            Integer.toString(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)),
                                            "1",
                                            home.getText().toString());
                                }else{
                                    //otherwise delete the old budget
                                    budgetDeleteTask task = new budgetDeleteTask(main, mEDB);
                                    task.execute(mEDB.username,
                                            "Home",
                                            home.getText().toString(),
                                            "1");
                                }
                    }

                    //for food
                    if(!food.getText().toString().isEmpty()
                            && Double.parseDouble(food.getText().toString()) != 0.0){
                                if(mEDB.getBudgets()[3] == 0.00 ) {
                                    //if budget is empty, insert a new budget
                                    budgetInsertTask task = new budgetInsertTask(main, mEDB);
                                    task.execute(mEDB.username,
                                            "Food",
                                            Integer.toString(Calendar.getInstance().get(Calendar.YEAR)),
                                            Integer.toString(Calendar.getInstance().get(Calendar.MONTH) + 1),
                                            Integer.toString(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)),
                                            "1",
                                            food.getText().toString());
                                }else{
                                    //otherwise delete the old budget
                                    budgetDeleteTask task = new budgetDeleteTask(main, mEDB);
                                    task.execute(mEDB.username,
                                            "Food",
                                            food.getText().toString(),
                                            "1");
                                }
                    }


                    //for entertainment
                    if(!entertainment.getText().toString().isEmpty()
                            && Double.parseDouble(entertainment.getText().toString()) != 0.0) {
                        if (mEDB.getBudgets()[4] == 0.00) {
                            //if budget is empty, insert a new budget
                            budgetInsertTask task = new budgetInsertTask(main, mEDB);
                            task.execute(mEDB.username,
                                    "Entertainment",
                                    Integer.toString(Calendar.getInstance().get(Calendar.YEAR)),
                                    Integer.toString(Calendar.getInstance().get(Calendar.MONTH) + 1),
                                    Integer.toString(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)),
                                    "1",
                                    entertainment.getText().toString());
                        }
                        else{
                            //otherwise delete the old budget
                            budgetDeleteTask task = new budgetDeleteTask(main, mEDB);
                            task.execute(mEDB.username,
                                    "Entertainment",
                                    entertainment.getText().toString(),
                                    "1");
                        }
                    }

                    //for other
                    if(!other.getText().toString().isEmpty()
                            && Double.parseDouble(other.getText().toString()) != 0.0){
                                if( mEDB.getBudgets()[5] == 0.00) {
                                    //if budget is empty, insert a new budget
                                    budgetInsertTask task = new budgetInsertTask(main, mEDB);
                                    task.execute(mEDB.username,
                                            "Other",
                                            Integer.toString(Calendar.getInstance().get(Calendar.YEAR)),
                                            Integer.toString(Calendar.getInstance().get(Calendar.MONTH) + 1),
                                            Integer.toString(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)),
                                            "1",
                                            other.getText().toString());
                                }else{
                                    //otherwise delete the old budget
                                    budgetDeleteTask task = new budgetDeleteTask(main, mEDB);
                                    task.execute(mEDB.username,
                                            "Other",
                                            other.getText().toString(),
                                            "1");
                                }
                    }
                    dismiss();
                }
            });
        }

    }

    /**
     * generates the user budgets based on the averages spent
     * then sets the edit texts that show the budgets to these values
     * it also makes the edit texts non editable to ensure that system and manual budgets are not mixed
     */
    private void systemGenerated(){

        //gets the averages
        mEDB.calcSysBudget();
        double [] averages = mEDB.getAverages();
        DecimalFormat df = new DecimalFormat("#.00");

        //sets the averages
        vehicle.setText(df.format(averages[0]));
        pets.setText(df.format(averages[1]));
        home.setText(df.format(averages[2]));
        food.setText(df.format(averages[3]));
        entertainment.setText(df.format(averages[4]));
        other.setText(df.format(averages[5]));

        //locks the edit texts
        vehicle.setInputType(0);
        pets.setInputType(0);
        home.setInputType(0);
        food.setInputType(0);
        entertainment.setInputType(0);
        other.setInputType(0);
    }

    @Override
    public void onClick(View view) {

    }
}
