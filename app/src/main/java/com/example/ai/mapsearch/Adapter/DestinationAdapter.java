package com.example.ai.mapsearch.Adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ai.mapsearch.Model.Destination;
import com.example.ai.mapsearch.R;
import com.example.ai.mapsearch.RouteActivity;
import com.example.ai.mapsearch.Utils.Constant;

import java.util.List;

/**
 * Created by A.I on 10/12/2017.
 */

public class DestinationAdapter extends RecyclerView.Adapter<DestinationAdapter.DestinationViewHolder>{

    public List<Destination> destinationList;



    public class DestinationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView tvDestinationName, tvDestinationBy, tvDestinationLongitude, tvDestinationLatitude;
        public LinearLayout llDestination;

        public DestinationViewHolder(View itemView) {
            super(itemView);
            tvDestinationBy = (TextView) itemView.findViewById(R.id.tvDestinationBy);
            tvDestinationLatitude = (TextView) itemView.findViewById(R.id.tvDestinationLatitude);
            tvDestinationLongitude = (TextView) itemView.findViewById(R.id.tvDestinationLongitude);
            tvDestinationName = (TextView) itemView.findViewById(R.id.tvDestinationName);
            llDestination = (LinearLayout) itemView.findViewById(R.id.llDestination);

            llDestination.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), RouteActivity.class);
            int position = getAdapterPosition();
            Destination destination = destinationList.get(position);
            Log.d(Constant.TAG, "destination id adapter " + destination.getDestinationId());
            Bundle bundle = new Bundle();
            bundle.putSerializable("destination", destination);
            intent.putExtras(bundle);
            view.getContext().startActivity(intent);
        }
    }

    public DestinationAdapter(List<Destination> destinationList){
        this.destinationList = destinationList;
    }

    @Override
    public DestinationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.destination_list, parent, false);

        return new DestinationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DestinationViewHolder holder, int position) {
        Destination destination = destinationList.get(position);

        holder.tvDestinationName.setText("Name: " + destination.getDestinationName());
        holder.tvDestinationLongitude.setText("Longitude: " + destination.getDestinationLongitude());
        holder.tvDestinationLatitude.setText("Latitude: " + destination.getDestinationLatitude());
        holder.tvDestinationBy.setText("Made by: " + destination.getDestinationBy());
    }

    @Override
    public int getItemCount() {
        return destinationList.size();
    }

    public List<Destination> getDestinationList(){
        return destinationList;
    }
}
