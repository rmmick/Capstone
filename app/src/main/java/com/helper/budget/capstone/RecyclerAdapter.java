package com.helper.budget.capstone;

/**
 * Created by Rachel on 2/18/2018.
 */

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by Rachel on 2/6/2018.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter. imageHolder> {

    private List<Entry> mEntries;
    private String mURL = "";

    public static class imageHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mName;
        private TextView mDescription;
        private TextView mCost;
        private TextView mDate;
        private Entry mEntry;

        public  imageHolder(View v, String URL) {
            super(v);
            mName = (TextView) v.findViewById(R.id.name);
            mDate = (TextView) v.findViewById(R.id.date);
            mDescription = (TextView) v.findViewById(R.id.description);
            mCost = (TextView) v.findViewById(R.id.cost);
            v.setOnClickListener(this);
        }

        public void bindData(Entry uEntry) {

            mEntry = uEntry;

            mName.setText(uEntry.NAME);
            mDate.setText(uEntry.DATE);
            mDescription.setText(uEntry.DESCRIPTION);
            mCost.setText(uEntry.COST.toString());
        }

        @Override
        public void onClick(View v) {

            Context context = itemView.getContext();
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage(mEntry.toString())
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {}
                    });
            AlertDialog alert = builder.create();
            alert.show();

        }
    }

    RecyclerAdapter(List<Entry> newEntries, String myURL) {
        mEntries = newEntries;
        mURL = myURL;
    }

    @Override
    public RecyclerAdapter.imageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_entry_layout, parent, false);
        return new  imageHolder(inflatedView, mURL);
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.imageHolder holder, int position) {
        Entry currEntry = mEntries.get(position);
        holder.bindData(currEntry);
    }

    @Override
    public int getItemCount() {
        if(mEntries == null){
            return 0;
        }
        return mEntries.size();
    }

    /**
     void sortList(String sort) {
     switch (sort) {
     case "Company":
     //Collections.sort(mEntries, new ComparatorCompany());
     break;
     case "Location":
     //.sort(mEntries, new ComparatorLocation());
     break;
     case "Price":
     //Collections.sort(mEntries, new ComparatorPrice());
     break;
     case "Model":
     //Collections.sort(mEntries, new ComparatorModel());
     break;
     default:
     break;
     }
     notifyDataSetChanged();
     }
     **/
}