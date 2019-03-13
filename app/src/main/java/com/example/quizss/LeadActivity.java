package com.example.quizss;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class LeadActivity extends AppCompatActivity implements PlayersAdapter.ItemClickListener{

    private ArrayList<Player> mPlayers;
    private PlayersAdapter adapter;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;

    public void addItem() {
        String key = mRef.push().getKey();
        mRef.child(key).child("score").setValue(0);
        mRef.child(key).child("name").setValue("sagar");
        //adapter.notifyDataSetChanged();
    }

    public void getRankings(int limit){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead);
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("players");
        addItem();
        getRankings(20);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_players);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PlayersAdapter(this, mPlayers);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItem(position).getName() + " on row number " + position, Toast.LENGTH_SHORT).show();
    }

    private void addChildEventListener() {
        ChildEventListener childListener = new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //adapter.add((String) dataSnapshot.child("description").getValue());
                //listKeys.add(dataSnapshot.getKey());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                /*String key = dataSnapshot.getKey();
                int index = listKeys.indexOf(key);

                if (index != -1) {
                    listItems.remove(index);
                    listKeys.remove(index);
                    adapter.notifyDataSetChanged();
                }*/
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        mRef.addChildEventListener(childListener);
    }

}
