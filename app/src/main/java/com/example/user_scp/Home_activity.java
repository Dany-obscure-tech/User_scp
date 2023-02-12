package com.example.user_scp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Home_activity extends AppCompatActivity {

    Button subscription_Button;
    Button refresh_Button;
    Button logout_Button;
    DatabaseReference slotReference;
    DatabaseReference walletReference;
    RecyclerView parkingSlots_recyclerView;
    Adapter_parkingSlotsRecyclerView adapter_parkingSlotsRecyclerView;

    List<String> parkingSlotsName_List;
    List<String> parkingSlots_List;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setTitle("Home");

        parkingSlotsName_List = new ArrayList<>();
        parkingSlots_List = new ArrayList<>();

        walletReference = FirebaseDatabase.getInstance().getReference("Wallet/"+UserDetails_class.getInstance().parking);
        slotReference = FirebaseDatabase.getInstance().getReference("Slots/"+UserDetails_class.getInstance().parking);

        parkingSlots_recyclerView = findViewById(R.id.parkingSlots_recyclerView);
        parkingSlots_recyclerView.setLayoutManager(new GridLayoutManager(Home_activity.this, 2));
        slotReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1:snapshot.getChildren()){
                    parkingSlotsName_List.add(snapshot1.getKey());
                    parkingSlots_List.add(snapshot1.getValue().toString());
                }
                adapter_parkingSlotsRecyclerView = new Adapter_parkingSlotsRecyclerView(Home_activity.this,parkingSlotsName_List,parkingSlots_List);
                parkingSlots_recyclerView.setAdapter(adapter_parkingSlotsRecyclerView);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        logout_Button = findViewById(R.id.logout_Button);
        logout_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        refresh_Button = findViewById(R.id.refresh_Button);
        refresh_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recreate();
            }
        });


        subscription_Button = findViewById(R.id.subscription_Button);
        subscription_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home_activity.this,Subscription_activity.class);
                startActivity(intent);
            }
        });

    }
}