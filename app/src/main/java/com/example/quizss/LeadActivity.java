package com.example.quizss;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class LeadActivity extends AppCompatActivity implements PlayersAdapter.ItemClickListener{

    private ArrayList<Player> mPlayers;
    private PlayersAdapter adapter;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;

    private RecyclerView mRecyclerView;

    public void addItem(Player player) {
        String key = mRef.push().getKey();
        mRef.child(key).setValue(player);
        //adapter.notifyDataSetChanged();
    }

    public void getRankings(int limit){
        Collections.sort(mPlayers, new Comparator<Player>() {
            @Override
            public int compare(Player p1, Player p2) {
                return p1.compareTo(p2);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead);
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("players");
        mPlayers = new ArrayList<Player>();
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_players);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //addItem(new Player("Sagar", 0.0));
        mRef.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        mPlayers = new ArrayList<Player>();
                        Map<String,Object> players = (Map<String,Object>) dataSnapshot.getValue();
                        for(String key:players.keySet()){
                           Map<String,Object> player = (Map<String,Object>)  players.get(key);
                           Log.d("DB Out",player.toString());
                           mPlayers.add(new Player(player.get("name").toString(), Double.parseDouble(player.get("score").toString())));
                        }
                        Log.d("players",mPlayers.toString());
                        getRankings(5);
                        adapter = new PlayersAdapter(LeadActivity.this, mPlayers);
                        adapter.setClickListener(LeadActivity.this);
                        mRecyclerView.setAdapter(adapter);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });
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
