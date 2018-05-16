package com.example.sandeep.lnmsmartpay;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
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


public class T_request extends Fragment {
    private LinearLayoutManager linearLayoutManager;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    DatabaseReference myRef1 = database.getReference();
    DatabaseReference db = database.getReference();

    Calendar calander;
    SimpleDateFormat simpledateformat;
    String Date,paye,sender;

    boolean ru=true;
    public EditText pay;
    public EditText amount;
    public EditText des;
    Button request;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.t_request, container, false);
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        pay = (EditText) v.findViewById(R.id.pay);
        amount = (EditText) v.findViewById(R.id.amount);
        des = (EditText) v.findViewById(R.id.des);
        request=(Button) v.findViewById(R.id.request);


        //retrieving data from database
        String userId = "";
        if (user != null)
            userId = user.getUid();

        final String finalUserId = userId;

            request.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
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
                                            long x = value.getReq_num();
                                            x++;

                                            if (ru) {
                                                myRef.child("users").child(paye).child("request").child(String.valueOf(x)).child("name").setValue(sender);
                                                myRef.child("users").child(paye).child("req_num").setValue(x);
                                                myRef.child("users").child(paye).child("request").child(String.valueOf(x)).child("amount").setValue(Long.parseLong(amount.getText().toString()));
                                                myRef.child("users").child(paye).child("request").child(String.valueOf(x)).child("des").setValue(des.getText().toString());
                                                myRef.child("users").child(paye).child("request").child(String.valueOf(x)).child("date").setValue(Date);
                                                myRef.child("users").child(paye).child("request").child(String.valueOf(x)).child("uid").setValue(finalUserId);
                                                myRef.child("users").child(paye).child("request").child(String.valueOf(x)).child("num").setValue(x);

                                                ru = false;
                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError error) {
                                            // Failed to read value
                                            //Log.w(TAG, "Failed to read value.", error.toException());
                                        }
                                    });
                                    startActivity(new Intent(getActivity(), DashboardActivity.class));
                                    Toast.makeText(getContext(), "Requested Successfully", Toast.LENGTH_LONG).show();

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