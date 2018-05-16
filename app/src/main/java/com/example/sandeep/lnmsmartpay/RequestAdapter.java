package com.example.sandeep.lnmsmartpay;

/**
 * Created by Sandeep on 4/23/2018.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import static java.security.AccessController.getContext;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.MyViewHolder> {

    private List<Request> requestList;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    DatabaseReference myRef1 = database.getReference();
    DatabaseReference db = database.getReference();
    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    Calendar calander;
    SimpleDateFormat simpledateformat;
    String Date;
    private Context context;
    long balance,balance1;
    long balanceuser,balanceuser1;
    long payamount;
    long x,y,z;
    String sender;
    boolean ru=true,tu=true;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView date,name,des,amount;
        public Button settle;
        public CardView cardview;

        public MyViewHolder(View view) {
            super(view);
            context=view.getContext();
            cardview= (CardView) view.findViewById(R.id.card_view);
            name = (TextView) view.findViewById(R.id.name);
            date = (TextView) view.findViewById(R.id.date);
            des = (TextView) view.findViewById(R.id.des);
            amount = (TextView) view.findViewById(R.id.amount);
            settle = (Button) view.findViewById(R.id.settle);

        }
    }


    public RequestAdapter(List<Request> requestList) {
        this.requestList = requestList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.request_list_row, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Request request = requestList.get(position);
        holder.name.setText(request.getName());
        holder.des.setText(request.getDes());
        holder.date.setText(request.getDate());
        holder.amount.setText(String.valueOf(request.getAmount()));
        holder.settle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                payamount = request.getAmount();
                calander = Calendar.getInstance();
                simpledateformat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                Date = simpledateformat.format(calander.getTime());
                String userId = "";
                if (user != null)
                    userId = user.getUid();

                final String finalUserId = userId;

                    myRef1.child("users").child(finalUserId).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Personaldetails value = dataSnapshot.getValue(Personaldetails.class);
                            sender = value.getname();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });

                    myRef.child("users").child(request.getUid()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // This method is called once with the initial value and again
                            // whenever data at this location is updated.
                            Personaldetails value = dataSnapshot.getValue(Personaldetails.class);
                            //sender=info.send(paye);
                            x= value.getTra_num();
                            x++;
                            balance = value.getbalance();
                            balance1 = balance + payamount;
                            if (ru) {
                                myRef.child("users").child(request.getUid()).child("transaction").child(String.valueOf(x)).child("name").setValue(sender);
                                myRef.child("users").child(request.getUid()).child("balance").setValue(balance1);
                                myRef.child("users").child(request.getUid()).child("tra_num").setValue(x);
                                myRef.child("users").child(request.getUid()).child("transaction").child(String.valueOf(x)).child("amount").setValue(payamount);
                                myRef.child("users").child(request.getUid()).child("transaction").child(String.valueOf(x)).child("des").setValue(request.getDes());
                                myRef.child("users").child(request.getUid()).child("transaction").child(String.valueOf(x)).child("type").setValue("Received");
                                myRef.child("users").child(request.getUid()).child("transaction").child(String.valueOf(x)).child("date").setValue(Date);

                                ru = false;
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            // Failed to read value
                            //Log.w(TAG, "Failed to read value.", error.toException());
                        }
                    });






                                myRef.child("users").child(finalUserId).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        Personaldetails value = dataSnapshot.getValue(Personaldetails.class);
                                        balanceuser=value.getbalance();
                                        z=value.getReq_num();
                                        y=value.getTra_num();
                                        y++;
                                        balanceuser1=balanceuser-payamount;
                                        if(tu) {
                                            myRef.child("users").child(finalUserId).child("transaction").child(String.valueOf(y)).child("name").setValue(request.getName());
                                            myRef.child("users").child(finalUserId).child("balance").setValue(balanceuser1);
                                            myRef.child("users").child(finalUserId).child("tra_num").setValue(y);
                                            myRef.child("users").child(finalUserId).child("req_num").setValue(z);
                                            myRef.child("users").child(finalUserId).child("transaction").child(String.valueOf(y)).child("amount").setValue(payamount);
                                            myRef.child("users").child(finalUserId).child("transaction").child(String.valueOf(y)).child("des").setValue(request.getDes());
                                            myRef.child("users").child(finalUserId).child("transaction").child(String.valueOf(y)).child("type").setValue("Sent");
                                            myRef.child("users").child(finalUserId).child("transaction").child(String.valueOf(y)).child("date").setValue(Date);
                                            myRef.child("users").child(finalUserId).child("request").child(String.valueOf(request.getNum())).setValue(null);
                                            tu=false;
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError error) {
                                        // Failed to read value
                                    }
                                });


                    Intent i = new Intent(context,Pending_requests.class);
                    context.startActivity(i);

                    Toast.makeText(context, "Transaction Completed Successfully", Toast.LENGTH_LONG).show();



            }
        });
    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }
}
