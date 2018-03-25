package com.helper.budget.capstone;

/**
 * Created by Rachel on 2/18/2018.
 */

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.AbstractQueue;
import java.util.Collections;
import java.util.List;

/**
 * Created by Rachel on 2/6/2018.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter. imageHolder> {

    private entryDatabase eDB;
    private String mURL = "";

    public class imageHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mName;
        private TextView mDescription;
        private TextView mCost;
        private TextView mDate;
        private TextView mCategory;
        private Entry mEntry;
        private ImageView deleteButton;

        public  imageHolder(View v, String URL) {
            super(v);
            mName = (TextView) v.findViewById(R.id.name);
            mDate = (TextView) v.findViewById(R.id.date);
            mDescription = (TextView) v.findViewById(R.id.description);
            mCost = (TextView) v.findViewById(R.id.cost);
            mCategory = (TextView) v.findViewById(R.id.category);
            v.setOnClickListener(this);
        }

        public void bindData(Entry uEntry) {

            mEntry = uEntry;
            mName.setText(uEntry.Name);
            mDate.setText(uEntry.Date);
            mDescription.setText(uEntry.Description);
            mCategory.setText(uEntry.Category);
            mCost.setText(uEntry.Cost.toString());
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

    RecyclerAdapter(entryDatabase newEntries, String myURL) {
        eDB = newEntries;
        mURL = myURL;
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

    private void delete(int p){
        int position = p;
        eDB.getEntriesList().remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, eDB.getEntriesList().size());
    }

    public void addEntry(Activity a, entryDatabase EDB){

        addDialog cdd=new addDialog(a, EDB);
        cdd.show();
        this.notifyDataSetChanged();

    }

    void sortList(String sort) {
        switch (sort) {
            case "Cost":
                Collections.sort(eDB.getEntriesList(), new CompareCost());
                break;
            case "Entry Name":
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