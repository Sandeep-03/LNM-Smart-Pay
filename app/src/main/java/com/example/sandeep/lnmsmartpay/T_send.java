package com.example.sandeep.lnmsmartpay;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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


public class T_send extends Fragment {
    private LinearLayoutManager linearLayoutManager;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    DatabaseReference myRef1 = database.getReference();
    DatabaseReference db = database.getReference();

    Calendar calander;
    SimpleDateFormat simpledateformat;
    String Date;




    public EditText pay;
    public EditText amount;
    public EditText des;
    public String paye;
    public String sender,receiver;
    long x,y;
    long balance,balance1;
    long balanceuser,balanceuser1;
    long payamount;
    Button send;
    boolean ru=true,tu=true;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.t_send, container, false);
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        pay = (EditText) v.findViewById(R.id.pay);
        amount = (EditText) v.findViewById(R.id.amount);
        des = (EditText) v.findViewById(R.id.des);
        send=(Button) v.findViewById(R.id.send);


        //retrieving data from database
        String userId = "";
        if (user != null)
            userId = user.getUid();

        final String finalUserId = userId;

        send.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

        payamount = Long.parseLong(amount.getText().toString());
                calander = Calendar.getInstance();
                simpledateformat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                Date = simpledateformat.format(calander.getTime());




                db.child("userid").child(pay.getText().toString()).child("uid").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String value = dataSnapshot.getValue(String.class);
                        paye = value;

                        if (paye != null) {
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

                            myRef.child("users").child(paye).addValueEventListener(new ValueEventListener() {
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
                                        myRef.child("users").child(paye).child("transaction").child(String.valueOf(x)).child("name").setValue(sender);
                                        myRef.child("users").child(paye).child("balance").setValue(balance1);
                                        myRef.child("users").child(paye).child("tra_num").setValue(x);
                                        myRef.child("users").child(paye).child("transaction").child(String.valueOf(x)).child("amount").setValue(payamount);
                                        myRef.child("users").child(paye).child("transaction").child(String.valueOf(x)).child("des").setValue(des.getText().toString());
                                        myRef.child("users").child(paye).child("transaction").child(String.valueOf(x)).child("type").setValue("Received");
                                        myRef.child("users").child(paye).child("transaction").child(String.valueOf(x)).child("date").setValue(Date);

                                        ru = false;
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError error) {
                                    // Failed to read value
                                    //Log.w(TAG, "Failed to read value.", error.toException());
                                }
                            });




                        myRef1.child("users").child(paye).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Personaldetails value = dataSnapshot.getValue(Personaldetails.class);
                                receiver = value.getname();
                                //x = value.getTra_num();
                                //x++;



                                myRef.child("users").child(finalUserId).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        Personaldetails value = dataSnapshot.getValue(Personaldetails.class);
                                        balanceuser=value.getbalance();
                                        y=value.getTra_num();
                                        y++;
                                        balanceuser1=balanceuser-payamount;
                                        if(tu) {
                                            myRef.child("users").child(finalUserId).child("transaction").child(String.valueOf(y)).child("name").setValue(receiver);
                                            myRef.child("users").child(finalUserId).child("balance").setValue(balanceuser1);
                                            myRef.child("users").child(finalUserId).child("tra_num").setValue(y);
                                            myRef.child("users").child(finalUserId).child("transaction").child(String.valueOf(y)).child("amount").setValue(payamount);
                                            myRef.child("users").child(finalUserId).child("transaction").child(String.valueOf(y)).child("des").setValue(des.getText().toString());
                                            myRef.child("users").child(finalUserId).child("transaction").child(String.valueOf(y)).child("type").setValue("Sent");
                                            myRef.child("users").child(finalUserId).child("transaction").child(String.valueOf(y)).child("date").setValue(Date);
                                            tu=false;
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError error) {
                                        // Failed to read value
                                    }
                                });

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });

                            startActivity(new Intent(getActivity(),DashboardActivity.class));
                            Toast.makeText(getContext(), "Transaction Completed Successfully", Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(getContext(), "User Doesn't Exsist", Toast.LENGTH_LONG).show();
                    }

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });



            }
        });
        return v;
    }
}