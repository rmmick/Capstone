package com.helper.budget.capstone;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.helper.budget.capstone.AsyncTasks.entryDeleteTask;

import java.text.DecimalFormat;
import java.util.Collections;

/**
 * Created by Rachel on 2/6/2018.
 */

/**
 * Custom adapter to hold the user's available entires in Main Activity
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter. imageHolder> {

    private entryDatabase eDB;
    private String mURL = "";
    private Main_Activity a;

    public class imageHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mName;
        private TextView mDescription;
        private TextView mCost;
        private TextView mDate;
        private TextView mCategory;
        private Entry mEntry;
        private ImageView deleteButton;

        /**
         * stores the xml values in their respective variables
         * @param v
         * @param URL
         */
        public  imageHolder(View v, String URL) {
            super(v);
            mName = (TextView) v.findViewById(R.id.name);
            mDate = (TextView) v.findViewById(R.id.date);
            mDescription = (TextView) v.findViewById(R.id.description);
            mCost = (TextView) v.findViewById(R.id.cost);
            mCategory = (TextView) v.findViewById(R.id.category);
            v.setOnClickListener(this);
        }

        /**
         * Binds entry data to a new entry in the list
         * @param uEntry
         */
        public void bindData(Entry uEntry) {

            DecimalFormat df = new DecimalFormat("#.00");

            mEntry = uEntry;
            mName.setText(uEntry.Name);
            mDate.setText(uEntry.Date);
            mDescription.setText(uEntry.Description);
            mCategory.setText(uEntry.Category);
            mCost.setText(df.format(uEntry.Cost));
        }

        @Override
        public void onClick(View v) {

            Context context = itemView.getContext();
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage(mEntry.toString())
                    .setCancelable(false)
                    .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            delete(getAdapterPosition());
                        }
                    })
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    /**
     * Constructor
     * @param newEntries
     * @param myURL
     * @param ab
     */
    RecyclerAdapter(entryDatabase newEntries, String myURL, Main_Activity ab) {
        eDB = newEntries;
        mURL = myURL;
        a = ab;
    }

    @Override
    public RecyclerAdapter.imageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_entry_layout, parent, false);
        return new  imageHolder(inflatedView, mURL);

    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.imageHolder holder, int position) {
        Entry currEntry = eDB.getEntriesList().get(position);
        holder.bindData(currEntry);
    }

    @Override
    public int getItemCount() {
        if(eDB.getEntriesList() == null){
            return 0;
        }
        return eDB.getEntriesList().size();
    }

    /**
     * Deletes an item from the adapter and the DB
     * @param p
     */
    private void delete(int p){
        int position = p;
        entryDeleteTask task = new entryDeleteTask(a, eDB, position, this);
        Entry e = eDB.getEntriesList().get(position);
        task.execute(eDB.username, e.Category, e.year, e.month, e.day, e.Description, e.Cost.toString(), e.Name);
        //notifyDeletion(position);
        //eDB.getEntriesList().remove(position);
        //if(task.getStatus())
    }

    /**
     * Notifies the adapter that an item has been deleted
     * @param position
     */
    public void notifyDeletion(int position){
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, eDB.getEntriesList().size());
    }

    /**
     * Displays the add entry dialog
     */
    public void addEntry(){

        addDialog cdd=new addDialog(a, eDB);
        cdd.show();
        this.notifyDataSetChanged();

    }

    /**
     * Sorts recyclerview entries based on user's choice
     * @param sort
     */
    void sortList(String sort) {
        switch (sort) {
            case "Cost":
                Collections.sort(eDB.getEntriesList(), new CompareCost());
                break;
            case "Name":
                Collections.sort(eDB.getEntriesList(), new CompareName());
                break;
            case "Date":
                Collections.sort(eDB.getEntriesList(), new CompareDate());
                break;
            default:
                break;
        }
        notifyDataSetChanged();
    }

}