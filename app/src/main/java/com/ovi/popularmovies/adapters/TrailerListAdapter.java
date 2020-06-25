package com.ovi.popularmovies.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ovi.popularmovies.R;
import com.ovi.popularmovies.interfaces.TrailerInterface;
import com.ovi.popularmovies.modelClass.detailModel.ResultsItem;

import java.util.List;

public class TrailerListAdapter extends RecyclerView.Adapter<TrailerListAdapter.MyViewHolder> {

    Context context;
    List<ResultsItem> dataList;
    TrailerInterface trailerInterface;

    public TrailerListAdapter(Context context, List<ResultsItem> dataList, TrailerInterface trailerInterface) {
        this.context = context;
        this.dataList = dataList;
        this.trailerInterface = trailerInterface;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.custom_trailer, viewGroup, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.trailerName.setText(dataList.get(position).getName());

        holder.play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trailerInterface.passTrailerKey(dataList.get(position).getKey());
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView play;
        TextView trailerName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            play = itemView.findViewById(R.id.playBtn);
            trailerName = itemView.findViewById(R.id.trailerName);


        }
    }
}
