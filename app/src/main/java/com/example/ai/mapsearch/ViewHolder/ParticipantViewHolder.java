package com.example.ai.mapsearch.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.ai.mapsearch.R;

/**
 * Created by A.I on 10/12/2017.
 */

public class ParticipantViewHolder extends RecyclerView.ViewHolder{

    public TextView tvDistance, tvName, tvTime, tvEta;

    public ParticipantViewHolder(View itemView) {
        super(itemView);
        tvDistance = (TextView) itemView.findViewById(R.id.tvDistance);
        tvName = (TextView) itemView.findViewById(R.id.tvName);
        tvTime = (TextView) itemView.findViewById(R.id.tvTime);
        tvEta = (TextView) itemView.findViewById(R.id.tvEta);

    }
}
