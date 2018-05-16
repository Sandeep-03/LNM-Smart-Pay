package com.example.sandeep.lnmsmartpay;

/**
 * Created by Sandeep on 4/23/2018.
 */

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;

        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;

        import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.MyViewHolder> {

    private List<Transaction> transactionList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView date,name,des,amount,s_r;
        public CardView cardview;

        public MyViewHolder(View view) {
            super(view);
            cardview= (CardView) view.findViewById(R.id.card_view);
            name = (TextView) view.findViewById(R.id.name);
            date = (TextView) view.findViewById(R.id.date);
            des = (TextView) view.findViewById(R.id.des);
            amount = (TextView) view.findViewById(R.id.amount);
            s_r = (TextView) view.findViewById(R.id.s_r);

        }
    }


    public TransactionAdapter(List<Transaction> transactionList) {
        this.transactionList = transactionList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.transaction_list_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Transaction transaction = transactionList.get(position);
        holder.name.setText(transaction.getName());
        holder.des.setText(transaction.getDes());
        holder.date.setText(transaction.getDate());
        holder.s_r.setText(transaction.getType());
        if("Sent".equals(transaction.getType())){
            holder.s_r.setTextColor(Color.parseColor("#F60717"));
        }
        if(transaction.getType().equals("Received")){
            holder.s_r.setTextColor(Color.parseColor("#0CB817"));
        }

        holder.amount.setText(String.valueOf(transaction.getAmount()));
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }
}
