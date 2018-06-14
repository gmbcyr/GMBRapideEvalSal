package com.gmb.gmbrapideevalsal;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gmb.gmbrapideevalsal.HistoriqueFragment.OnListFragmentInteractionListener;
import com.gmb.gmbrapideevalsal.dummy.DummyContent.DummyItem;
import com.gmb.gmbrapideevalsal.tools.BothYearResult;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    Context context;
    private final List<BothYearResult> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyItemRecyclerViewAdapter(Context context,List<BothYearResult> items, OnListFragmentInteractionListener listener) {
        this.context=context;
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.histo_line, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        SimpleDateFormat datFor=new SimpleDateFormat(context.getString(R.string.date_pattern));
        SimpleDateFormat timeFor=new SimpleDateFormat("HH:mm");
        NumberFormat numFor=NumberFormat.getCurrencyInstance();
        numFor.setGroupingUsed(true);
        numFor.setMaximumFractionDigits(2);
        holder.mItem = mValues.get(position);
        holder.txtDate.setText(datFor.format(new Date(Long.valueOf(mValues.get(position).getCurrentYear().getTimeCalcul()))));
        holder.txtTime.setText(timeFor.format(new Date(Long.valueOf(mValues.get(position).getCurrentYear().getTimeCalcul()))));

        holder.txtTaux.setText(numFor.format(holder.mItem.getCurrentYear().getHourRate()));
        holder.txtGros.setText(numFor.format(holder.mItem.getCurrentYear().getAnnualGros()));
        holder.txtNet.setText(numFor.format(holder.mItem.getCurrentYear().getPayNet()*52));
        holder.txtNetOld.setText(numFor.format(holder.mItem.getOldYear().getPayNet()*52));


        if(position%2==0){

            holder.lytAll.setBackgroundColor(Color.parseColor(context.getString(R.string.col_pair)));
        }
        else{

            holder.lytAll.setBackgroundColor(Color.parseColor(context.getString(R.string.col_impair)));
        }

        holder.mContentView.setText(mValues.get(position).getCurrentYear().getPayNet()+"_"+mValues.get(position).getOldYear().getPayNet());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Context context;
        public final View mView;
        public final TextView txtDate;
        public final TextView txtTime;
        public final TextView txtTaux;
        public final TextView txtGros;
        public final TextView txtNet;
        public final TextView txtNetOld;
        public final TextView mContentView;
        public final LinearLayout lytAll;
        public final LinearLayout lytNetOld;
        public BothYearResult mItem;

        public ViewHolder(View view) {
            super(view);
            context=view.getContext();
            mView = view;
            txtDate = (TextView) view.findViewById(R.id.txt_date);
            txtTime = (TextView) view.findViewById(R.id.txt_time);
            txtTaux = (TextView) view.findViewById(R.id.txt_taux);
            txtGros = (TextView) view.findViewById(R.id.txt_gros);
            txtNet = (TextView) view.findViewById(R.id.txt_net);
            txtNetOld = (TextView) view.findViewById(R.id.txt_net_old);
            lytNetOld = (LinearLayout) view.findViewById(R.id.lyt_net_old);
            lytAll = (LinearLayout) view.findViewById(R.id.lyt_content_all);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }


}
