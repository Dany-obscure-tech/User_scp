package com.example.user_scp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Subscription_activity extends AppCompatActivity {
    TextView wallet_textView;
    TextView date_textView;
    TextView duedate_textView;
    TextView subscription_textView;
    DatabaseReference databaseReference;
    Button oneDaySubscription_Button;
    Button weekSubscription_Button;
    Button monthSubscription_Button;

    LinearLayout linearLayout1;
    LinearLayout linearLayout2;
    LinearLayout linearLayout3;

    Calendar calendar;
    SimpleDateFormat dateFormat;
    String date;

    int wallet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);
        setTitle("Subscription");

        linearLayout1 = findViewById(R.id.linearLayout1);
        linearLayout2 = findViewById(R.id.linearLayout2);
        linearLayout3 = findViewById(R.id.linearLayout3);

        oneDaySubscription_Button = findViewById(R.id.oneDaySubscription_Button);
        weekSubscription_Button = findViewById(R.id.weekSubscription_Button);
        monthSubscription_Button = findViewById(R.id.monthSubscription_Button);

        duedate_textView = findViewById(R.id.duedate_textView);
        date_textView = findViewById(R.id.date_textView);
        subscription_textView = findViewById(R.id.subscription_textView);

        wallet_textView = findViewById(R.id.wallet_textView);
        databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Wallet").child(UserDetails_class.getInstance().getToken()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                wallet_textView.setText(snapshot.getValue().toString());
                wallet = Integer.parseInt(snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseReference.child("Subscription").child(UserDetails_class.getInstance().getToken()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("valid").getValue().toString().equals("0")){
                    try {
                        subscription();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }else{
                    linearLayout1.setVisibility(View.VISIBLE);
                    linearLayout2.setVisibility(View.VISIBLE);
                    linearLayout3.setVisibility(View.VISIBLE);

                    date_textView.setText(snapshot.child("time").getValue().toString());
                    subscription_textView.setText(snapshot.child("Duration").getValue().toString());
                    duedate_textView.setText(snapshot.child("due_date").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void subscription() throws ParseException {


        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        date = dateFormat.format(calendar.getTime());

        oneDaySubscription_Button.setVisibility(View.VISIBLE);
        weekSubscription_Button.setVisibility(View.VISIBLE);
        monthSubscription_Button.setVisibility(View.VISIBLE);

        oneDaySubscription_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.child("Subscription").child(UserDetails_class.getInstance().getToken()).child("Duration").setValue("1 Day");
                databaseReference.child("Subscription").child(UserDetails_class.getInstance().getToken()).child("valid").setValue("1");
                databaseReference.child("Subscription").child(UserDetails_class.getInstance().getToken()).child("time").setValue(String.valueOf(date));
                wallet = wallet - 50;
                databaseReference.child("Wallet").child(UserDetails_class.getInstance().getToken()).setValue(wallet);
                calendar.add(Calendar.DAY_OF_YEAR, 1);
                dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
                Date resultDate = calendar.getTime();
                String dueDate  = dateFormat.format(resultDate);

                databaseReference.child("Subscription").child(UserDetails_class.getInstance().getToken()).child("due_date").setValue(String.valueOf(dueDate));
                finish();
            }
        });

        weekSubscription_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.child("Subscription").child(UserDetails_class.getInstance().getToken()).child("Duration").setValue("week");
                databaseReference.child("Subscription").child(UserDetails_class.getInstance().getToken()).child("valid").setValue("1");
                databaseReference.child("Subscription").child(UserDetails_class.getInstance().getToken()).child("time").setValue(String.valueOf(date));
                wallet = wallet - 200;
                databaseReference.child("Wallet").child(UserDetails_class.getInstance().getToken()).setValue(wallet);
                calendar.add(Calendar.DAY_OF_YEAR, 7);
                dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:MM:ss");
                Date resultDate = calendar.getTime();
                String dueDate  = dateFormat.format(resultDate);

                databaseReference.child("Subscription").child(UserDetails_class.getInstance().getToken()).child("due_date").setValue(String.valueOf(dueDate));
                finish();
            }
        });

        monthSubscription_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.child("Subscription").child(UserDetails_class.getInstance().getToken()).child("Duration").setValue("month");
                databaseReference.child("Subscription").child(UserDetails_class.getInstance().getToken()).child("valid").setValue("1");
                databaseReference.child("Subscription").child(UserDetails_class.getInstance().getToken()).child("time").setValue(String.valueOf(date));
                wallet = wallet - 500;
                databaseReference.child("Wallet").child(UserDetails_class.getInstance().getToken()).setValue(wallet);

                calendar.add(Calendar.DAY_OF_YEAR, 30);
                dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
                Date resultDate = calendar.getTime();
                String dueDate  = dateFormat.format(resultDate);

                databaseReference.child("Subscription").child(UserDetails_class.getInstance().getToken()).child("due_date").setValue(String.valueOf(dueDate));

                finish();
            }
        });
    }
}