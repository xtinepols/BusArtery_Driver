package com.example.christinebpolesti.busartery_driver.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.christinebpolesti.busartery_driver.Interface.ItemClickListener;
import com.example.christinebpolesti.busartery_driver.Model.History;
import com.example.christinebpolesti.busartery_driver.R;

import java.util.List;

/**
 * Created by christine B. Polesti on 11/21/2017.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private List<History> historyList;
    private ItemClickListener clickListener;

    public HistoryAdapter(List<History> historyList) {
        this.historyList = historyList;
    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HistoryAdapter.HistoryViewHolder holder, int position) {
        History history = historyList.get(position);

        holder.timeStarted.setText(history.getTimeStarted());
        holder.timeEnded.setText(history.getTimeEnded());
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView timeStarted;
        TextView timeEnded;

        public HistoryViewHolder(View itemView) {
            super(itemView);
            timeStarted = (TextView) itemView.findViewById(R.id.edtItem1);
            timeEnded = (TextView) itemView.findViewById(R.id.edtItem2);
            itemView.setTag(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                clickListener.onClick(v, getAdapterPosition());
            }
        }
    }
}
