package com.example.user_scp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.collection.LLRBNode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Subscription_activity extends AppCompatActivity {
    Boolean e = false;
    Boolean j = false;
    Boolean b = false;
    Boolean topup = false;
    EditText topup_editText;
    EditText phone_editText;
    EditText phone2_editText;
    EditText iban_editText;
    ImageView easypaisa_imageView;
    ImageView jazzcash_imageView;
    ImageView bank_imageView;
    RadioGroup payment_radioGroup;
    RadioButton wallet_radioButton;
    RadioButton online_radioButton;
    TextView package_textView;
    TextView wallet_textView;
    TextView date_textView;
    TextView duedate_textView;
    TextView subscription_textView;
    DatabaseReference databaseReference;
    TextView cancel_textView;
    Button oneDaySubscription_Button;
    Button weekSubscription_Button;
    Button monthSubscription_Button;
    Button topup_Button;
    Button done_Button;
    Button done2_Button;

    RelativeLayout onlinePaymentAlert_relativeLayout;
    LinearLayout easyPaisa_linearLayout;
    LinearLayout jazzcash_linearLayout;
    LinearLayout bank_linearLayout;
    LinearLayout linearLayout1;
    LinearLayout linearLayout2;
    LinearLayout linearLayout3;
    LinearLayout easypaisaFields_linearLayout;
    LinearLayout bankFields_linearLayout;

    Calendar calendar;
    SimpleDateFormat dateFormat;
    String date;

    int wallet;
    int topupAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);
        setTitle("Subscription");

        topup_editText = findViewById(R.id.topup_editText);

        phone_editText = findViewById(R.id.phone_editText);
        phone2_editText = findViewById(R.id.phone2_editText);
        iban_editText = findViewById(R.id.iban_editText);

        easypaisa_imageView = findViewById(R.id.easypaisa_imageView);
        jazzcash_imageView = findViewById(R.id.jazzcash_imageView);
        bank_imageView = findViewById(R.id.bank_imageView);

        payment_radioGroup = findViewById(R.id.payment_radioGroup);
        wallet_radioButton = findViewById(R.id.wallet_radioButton);
        online_radioButton = findViewById(R.id.online_radioButton);

        onlinePaymentAlert_relativeLayout = findViewById(R.id.onlinePaymentAlert_relativeLayout);
        easyPaisa_linearLayout = findViewById(R.id.easyPaisa_linearLayout);
        jazzcash_linearLayout = findViewById(R.id.jazzcash_linearLayout);
        bank_linearLayout = findViewById(R.id.bank_linearLayout);

        linearLayout1 = findViewById(R.id.linearLayout1);
        linearLayout2 = findViewById(R.id.linearLayout2);
        linearLayout3 = findViewById(R.id.linearLayout3);

        easypaisaFields_linearLayout = findViewById(R.id.easypaisaFields_linearLayout);
        bankFields_linearLayout = findViewById(R.id.bankFields_linearLayout);

        cancel_textView = findViewById(R.id.cancel_textView);
        oneDaySubscription_Button = findViewById(R.id.oneDaySubscription_Button);
        weekSubscription_Button = findViewById(R.id.weekSubscription_Button);
        monthSubscription_Button = findViewById(R.id.monthSubscription_Button);

        topup_Button = findViewById(R.id.topup_Button);

        done_Button = findViewById(R.id.done_Button);
        done2_Button = findViewById(R.id.done2_Button);

        duedate_textView = findViewById(R.id.duedate_textView);
        date_textView = findViewById(R.id.date_textView);
        subscription_textView = findViewById(R.id.subscription_textView);

        package_textView = findViewById(R.id.package_textView);

        wallet_textView = findViewById(R.id.wallet_textView);
        databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Wallet").child(UserDetails_class.getInstance().getUsername()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                wallet_textView.setText(snapshot.getValue().toString());
                wallet = Integer.parseInt(snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseReference.child("Subscription").child(UserDetails_class.getInstance().getUsername()).addListenerForSingleValueEvent(new ValueEventListener() {
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
                    payment_radioGroup.setVisibility(View.GONE);

                    date_textView.setText(snapshot.child("time").getValue().toString());
                    subscription_textView.setText(snapshot.child("Duration").getValue().toString());
                    duedate_textView.setText(snapshot.child("due_date").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        topup_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                topup_editText.setVisibility(View.VISIBLE);
                package_textView.setText("Wallet Topup");
                topup=true;
                showOnlinePayment(4);
            }
        });

        easypaisa_imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (e==false){
                    e = true;
                    easyPaisa_linearLayout.setBackgroundColor(Color.parseColor("#7F7FD3"));
                    easypaisaFields_linearLayout.setVisibility(View.VISIBLE);

                    j = false;
                    jazzcash_linearLayout.setBackgroundColor(Color.parseColor("#F6F6F6"));

                    b = false;
                    bank_linearLayout.setBackgroundColor(Color.parseColor("#F6F6F6"));
                    bankFields_linearLayout.setVisibility(View.GONE);


                }

            }
        });

        jazzcash_imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (j==false){
                    j = true;
                    jazzcash_linearLayout.setBackgroundColor(Color.parseColor("#7F7FD3"));
                    easypaisaFields_linearLayout.setVisibility(View.VISIBLE);

                    e = false;
                    easyPaisa_linearLayout.setBackgroundColor(Color.parseColor("#F6F6F6"));

                    b = false;
                    bank_linearLayout.setBackgroundColor(Color.parseColor("#F6F6F6"));
                    bankFields_linearLayout.setVisibility(View.GONE);


                }

            }
        });

        bank_imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (b==false){
                    b = true;
                    bank_linearLayout.setBackgroundColor(Color.parseColor("#7F7FD3"));
                    bankFields_linearLayout.setVisibility(View.VISIBLE);

                    j = false;
                    jazzcash_linearLayout.setBackgroundColor(Color.parseColor("#F6F6F6"));

                    e = false;
                    easyPaisa_linearLayout.setBackgroundColor(Color.parseColor("#F6F6F6"));
                    easypaisaFields_linearLayout.setVisibility(View.GONE);


                }

            }
        });


        cancel_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onlinePaymentAlert_relativeLayout.setVisibility(View.GONE);
                oneDaySubscription_Button.setClickable(true);
                monthSubscription_Button.setClickable(true);
                weekSubscription_Button.setClickable(true);
                topup_Button.setClickable(true);
                wallet_radioButton.setClickable(true);
                online_radioButton.setClickable(true);
                topup_editText.setVisibility(View.GONE);
                topup = false;
                easyPaisa_linearLayout.setBackgroundColor(Color.parseColor("#F6F6F6"));
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
                if (radioButtonCheck()){
                    if (online_radioButton.isChecked()){
                        package_textView.setText("1 Day 50/-");
                        showOnlinePayment(1);

                    }else {
                        oneDaySubscription();
                    }

                }
            }
        });

        weekSubscription_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (radioButtonCheck()){
                    if (online_radioButton.isChecked()){
                        package_textView.setText("1 Week 200/-");
                        showOnlinePayment(2);

                    }else {
                        weekSubscription();
                    }

                }

            }
        });

        monthSubscription_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (radioButtonCheck()){
                    if (online_radioButton.isChecked()){
                        package_textView.setText("1 Month 500/-");
                        showOnlinePayment(3);

                    }else {
                        monthSubscription();
                    }

                }

            }
        });

    }

    private void monthSubscription() {
        databaseReference.child("Subscription").child(UserDetails_class.getInstance().getUsername()).child("Duration").setValue("month");
        databaseReference.child("Subscription").child(UserDetails_class.getInstance().getUsername()).child("valid").setValue("1");
        databaseReference.child("Subscription").child(UserDetails_class.getInstance().getUsername()).child("time").setValue(String.valueOf(date));
        wallet = wallet - 500;
        databaseReference.child("Wallet").child(UserDetails_class.getInstance().getUsername()).setValue(wallet);

        calendar.add(Calendar.DAY_OF_YEAR, 30);
        dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        Date resultDate = calendar.getTime();
        String dueDate  = dateFormat.format(resultDate);

        databaseReference.child("Subscription").child(UserDetails_class.getInstance().getUsername()).child("due_date").setValue(String.valueOf(dueDate));

        finish();
    }

    private void weekSubscription() {
        databaseReference.child("Subscription").child(UserDetails_class.getInstance().getUsername()).child("Duration").setValue("week");
        databaseReference.child("Subscription").child(UserDetails_class.getInstance().getUsername()).child("valid").setValue("1");
        databaseReference.child("Subscription").child(UserDetails_class.getInstance().getUsername()).child("time").setValue(String.valueOf(date));
        wallet = wallet - 200;
        databaseReference.child("Wallet").child(UserDetails_class.getInstance().getUsername()).setValue(wallet);
        calendar.add(Calendar.DAY_OF_YEAR, 7);
        dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:MM:ss");
        Date resultDate = calendar.getTime();
        String dueDate  = dateFormat.format(resultDate);

        databaseReference.child("Subscription").child(UserDetails_class.getInstance().getUsername()).child("due_date").setValue(String.valueOf(dueDate));
        finish();
    }

    private void oneDaySubscription() {
        databaseReference.child("Subscription").child(UserDetails_class.getInstance().getUsername()).child("Duration").setValue("1 Day");
        databaseReference.child("Subscription").child(UserDetails_class.getInstance().getUsername()).child("valid").setValue("1");
        databaseReference.child("Subscription").child(UserDetails_class.getInstance().getUsername()).child("time").setValue(String.valueOf(date));
        wallet = wallet - 50;
        databaseReference.child("Wallet").child(UserDetails_class.getInstance().getUsername()).setValue(wallet);
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        Date resultDate = calendar.getTime();
        String dueDate  = dateFormat.format(resultDate);

        databaseReference.child("Subscription").child(UserDetails_class.getInstance().getUsername()).child("due_date").setValue(String.valueOf(dueDate));
        finish();
    }

    private void showOnlinePayment(int type) {
        onlinePaymentAlert_relativeLayout.setVisibility(View.VISIBLE);
        oneDaySubscription_Button.setClickable(false);
        monthSubscription_Button.setClickable(false);
        weekSubscription_Button.setClickable(false);
        topup_Button.setClickable(false);
        wallet_radioButton.setClickable(false);
        online_radioButton.setClickable(false);

        done_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate1()){
                    if (type==1){
                        oneDaySubscription();
                    }
                    if (type==2){
                        weekSubscription();
                    }
                    if (type==3){
                        monthSubscription();
                    }
                    if (type==4){
                        walletTopup();
                    }
                }

            }
        });

        done2_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate2()){
                    if (type==1){
                        oneDaySubscription();
                    }
                    if (type==2){
                        weekSubscription();
                    }
                    if (type==3){
                        monthSubscription();
                    }
                    if (type==4){
                        walletTopup();
                    }
                }
            }
        });

    }

    private void walletTopup() {
        databaseReference.child("Wallet").child(UserDetails_class.getInstance().getUsername()).setValue(topupAmount);
        finish();
    }

    public boolean validate1(){
        boolean valid = true;

        if (phone_editText.getText().toString().isEmpty()){
            phone_editText.setError("Enter phone number");
            valid = false;
        }
        if (phone_editText.getText().toString().length()<13){
            phone_editText.setError("Enter valid number");
            valid = false;
        }
        if (topup){
            if (topup_editText.getText().toString().isEmpty()){
                topup_editText.setError("Enter Amount");
                valid = false;
            }else {
                int amount = Integer.parseInt(topup_editText.getText().toString());
                if (amount<=0){
                    topup_editText.setError("Enter Valid Amount");
                }else {
                    topupAmount = amount+wallet;

                }
            }
        }

        return valid;
    }

    public boolean validate2(){
        boolean valid = true;

        if (phone2_editText.getText().toString().isEmpty()){
            phone2_editText.setError("Enter phone number");
            valid = false;
        }
        if (phone2_editText.getText().toString().length()<13){
            phone2_editText.setError("Enter valid number");
            valid = false;
        }
        if (iban_editText.getText().toString().isEmpty()){
            iban_editText.setError("Enter iban number");
            valid = false;
        }
        if (iban_editText.getText().toString().length()<24){
            iban_editText.setError("Enter valid iban");
            valid = false;
        }
        if (topup){
            if (topup_editText.getText().toString().isEmpty()){
                topup_editText.setError("Enter Amount");
                valid = false;
            }else {
                int amount = Integer.parseInt(topup_editText.getText().toString());
                if (amount<=0){
                    topup_editText.setError("Enter Valid Amount");
                }else {
                    topupAmount = amount+wallet;

                }
            }
        }

        return valid;
    }


    public boolean radioButtonCheck(){
        if (online_radioButton.isChecked()||wallet_radioButton.isChecked()){
            return true;
        }else {
            Toast.makeText(Subscription_activity.this, "Payment method not selected", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}