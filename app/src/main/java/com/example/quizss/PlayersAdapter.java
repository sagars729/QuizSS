package com.example.quizss;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class PlayersAdapter  extends RecyclerView.Adapter<PlayersAdapter.ViewHolder>  {
    private List<Player> mPlayers;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Player player;
    private boolean flag;

    // data is passed into the constructor
    PlayersAdapter(Context context, List<Player> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mPlayers = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_player, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        player = mPlayers.get(position);
        holder.mNameTextView.setText(player.getName());
        holder.mScoreTextView.setText("" + player.getScore());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mPlayers.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mNameTextView;
        TextView mScoreTextView;

        ViewHolder(View itemView) {
            super(itemView);
            mNameTextView = itemView.findViewById(R.id.playerName);
            mScoreTextView = itemView.findViewById(R.id.score);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());

        }

    }

    // convenience method for getting data at click position
    Player getItem(int id) {
        return mPlayers.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
