package com.example.user_scp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    EditText token_editText;
    EditText pin_editText;
    Button login_Button;
    DatabaseReference subAdminRef;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("User Login");

        subAdminRef = FirebaseDatabase.getInstance().getReference("Users");

        token_editText = findViewById(R.id.token_editText);
        pin_editText = findViewById(R.id.pin_editText);

        login_Button = findViewById(R.id.login_Button);

        login_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean valid = true;
                if (token_editText.getText().toString().isEmpty()){
                    token_editText.setError("Enter email");
                    valid = false;
                }

                if (pin_editText.getText().toString().isEmpty()){
                    pin_editText.setError("Enter pin");
                    valid = false;
                }


                if (valid){
                    subAdminRef.child(token_editText.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {


                            if (snapshot.exists()){
                                if (pin_editText.getText().toString().equals(snapshot.child("pin").getValue().toString())){
                                    Intent intent = new Intent(MainActivity.this,Home_activity.class);
                                    UserDetails_class.getInstance().setToken(snapshot.getKey().toString());
                                    UserDetails_class.getInstance().setName(snapshot.child("name").getValue().toString());
                                    UserDetails_class.getInstance().setParking(snapshot.child("parking").getValue().toString());
                                    UserDetails_class.getInstance().setPh_no(snapshot.child("ph_no").getValue().toString());
                                    UserDetails_class.getInstance().setCar_no(snapshot.child("car_no").getValue().toString());
                                    startActivity(intent);
                                }else {
                                    Toast.makeText(MainActivity.this, "Wrong pin", Toast.LENGTH_SHORT).show();
                                }

                            }else {
                                Toast.makeText(MainActivity.this, "Invalid token!", Toast.LENGTH_SHORT).show();
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