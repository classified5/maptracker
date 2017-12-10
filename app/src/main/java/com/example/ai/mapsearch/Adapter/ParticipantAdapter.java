package com.example.ai.mapsearch.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ai.mapsearch.Model.Participant;
import com.example.ai.mapsearch.R;
import com.example.ai.mapsearch.ViewHolder.ParticipantViewHolder;

import java.util.List;

/**
 * Created by A.I on 10/12/2017.
 */

public class ParticipantAdapter extends RecyclerView.Adapter<ParticipantViewHolder> {

    private List<Participant> participantList;

    public ParticipantAdapter(List<Participant> participantList) {
        this.participantList = participantList;
    }

    @Override
    public ParticipantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.participant_list, parent, false);

        return new ParticipantViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ParticipantViewHolder holder, int position) {
        Participant participant = participantList.get(position);
        holder.tvDistance.setText("Distance: " + participant.getDistance());
        holder.tvName.setText("Participant Name: " + participant.getParticipantName());
        holder.tvTime.setText("Duration: " + participant.getTime());
        holder.tvEta.setText("ETA: " + participant.getEta());
    }

    @Override
    public int getItemCount() {
        return participantList.size();
    }
}
