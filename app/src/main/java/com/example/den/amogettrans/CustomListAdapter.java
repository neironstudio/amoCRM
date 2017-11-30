package com.example.den.amogettrans;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;



public class CustomListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Transaction.Leads> transItems;
    public CustomListAdapter(Activity activity, List<Transaction.Leads> transItems) {
        this.activity = activity;
        this.transItems = transItems;
    }@Override
     public int getCount() {
        return transItems.size();
    }

    @Override
    public Object getItem(int location) {
        return transItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        }else{
            convertView = inflater.inflate(R.layout.list_row, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }

        Transaction.Leads t = transItems.get(position);

        holder.viewName.setText(t.name);
        long d = Long.parseLong(t.date_create+"000");
        Timestamp stamp = new Timestamp(d);
        Date date = new Date(stamp.getTime());
        holder.viewDate.setText(date.toString());

        holder.viewBudjet.setText(t.price);
        holder.viewStat.setText(t.status_id);
        return convertView;
    }
    static class ViewHolder {
        @BindView(R.id.viewName) TextView viewName;
        @BindView(R.id.viewDate) TextView viewDate;
        @BindView(R.id.viewBudjet) TextView viewBudjet;
        @BindView(R.id.viewStat) TextView viewStat;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
