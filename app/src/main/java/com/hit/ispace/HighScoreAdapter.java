/*This class controls the high score board*/
package com.hit.ispace;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class HighScoreAdapter extends RecyclerView.Adapter<HighScoreAdapter.HighScoreViewHolder>
{

    private List<UserScore> highScores;

    public HighScoreAdapter( List<UserScore> highScores) {

        this.highScores = highScores;
    }
    //Inflate layout in view holder
    @NonNull
    @Override
    public HighScoreViewHolder onCreateViewHolder(@NonNull ViewGroup parant, int i) {

        View view = LayoutInflater.from(parant.getContext()).inflate(R.layout.pattern_high_score, parant, false);
        HighScoreViewHolder highScoreViewHolder = new HighScoreViewHolder(view);
        return highScoreViewHolder;
    }

    //Set display values of view objects.
    @Override
    public void onBindViewHolder(@NonNull HighScoreViewHolder holder, int i) {
        UserScore userScore = highScores.get(i);
        holder.rate.setText(String.format("%02d",i+1));
        holder.user_value.setText(userScore.getName());
        holder.score_value.setText(String.format("%02d",userScore.getScore()));
    }

    @Override
    public int getItemCount() {
        return highScores.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    class HighScoreViewHolder extends RecyclerView.ViewHolder
    {
        private TextView rate;
        private TextView user_value;
        private TextView score_value;

        public HighScoreViewHolder(@NonNull View itemView) {
            super(itemView);
            rate = itemView.findViewById(R.id.rate);
            user_value = itemView.findViewById(R.id.user_name_value);
            score_value = itemView.findViewById(R.id.score_value);
        }
    }
}
