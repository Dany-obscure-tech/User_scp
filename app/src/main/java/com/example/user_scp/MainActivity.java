package com.example.user_scp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    EditText username_editText;
    EditText pin_editText;
    Button login_Button;
    Button forgotPass_Button;
    DatabaseReference userRef;

    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("User Login");

        mAuth = FirebaseAuth.getInstance();
        userRef = FirebaseDatabase.getInstance().getReference("Users");

        username_editText = findViewById(R.id.username_editText);
        pin_editText = findViewById(R.id.pin_editText);

        login_Button = findViewById(R.id.login_Button);
        forgotPass_Button = findViewById(R.id.forgotPass_Button);

        login_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean valid = true;
                if (username_editText.getText().toString().isEmpty()){
                    username_editText.setError("Enter email");
                    valid = false;
                }

                if (pin_editText.getText().toString().isEmpty()){
                    pin_editText.setError("Enter pin");
                    valid = false;
                }


                if (valid){
                    userRef.child(username_editText.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {


                            if (snapshot.exists()){
                                mAuth.signInWithEmailAndPassword(snapshot.child("email").getValue().toString(),pin_editText.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()){
                                            userRef.child(username_editText.getText().toString()).child("pin").setValue(pin_editText.getText().toString());
                                            Intent intent = new Intent(MainActivity.this,Home_activity.class);
                                            UserDetails_class.getInstance().setUsername(snapshot.getKey().toString());
                                            UserDetails_class.getInstance().setName(snapshot.child("name").getValue().toString());
                                            UserDetails_class.getInstance().setParking(snapshot.child("parking").getValue().toString());
                                            UserDetails_class.getInstance().setPh_no(snapshot.child("ph_no").getValue().toString());
                                            UserDetails_class.getInstance().setCar_no(snapshot.child("car_no").getValue().toString());
                                            startActivity(intent);

                                        }else {
                                            Toast.makeText(MainActivity.this, String.valueOf(task.getException()), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                            }else {
                                Toast.makeText(MainActivity.this, "Invalid Username!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

            }
        });

        forgotPass_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean valid = true;
                if (username_editText.getText().toString().isEmpty()){
                    username_editText.setError("Enter email");
                    valid = false;
                }

                if (valid){
                    userRef.child(username_editText.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if (snapshot.exists()){
                                FirebaseAuth.getInstance().sendPasswordResetEmail(snapshot.child("email").getValue().toString())
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(MainActivity.this, "Check your email", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }else {
                                Toast.makeText(MainActivity.this, "Invalid Username!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });



    }
}