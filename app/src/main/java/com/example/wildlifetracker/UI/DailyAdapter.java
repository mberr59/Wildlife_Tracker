package com.example.wildlifetracker.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wildlifetracker.Database.Converter;
import com.example.wildlifetracker.Entity.DailyEntity;
import com.example.wildlifetracker.R;

import java.util.Date;
import java.util.List;

public class DailyAdapter extends RecyclerView.Adapter<DailyAdapter.DailyViewHolder>{
    class DailyViewHolder extends RecyclerView.ViewHolder {
        private final TextView dailyItemView;

        private DailyViewHolder (View itemView) {
            super (itemView);
            dailyItemView = itemView.findViewById(R.id.dailyList);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final DailyEntity currentReport = mDaily.get(position);
                    Intent intent = new Intent(reportContext, ReportActivity.class);
                    intent.putExtra("reportID", currentReport.getReportID());
                    intent.putExtra("animalName", currentReport.getAnimalName());
                    intent.putExtra("animalType", currentReport.getAnimalType());
                    intent.putExtra("dateCreated", Converter.toTimestamp(currentReport.getDateCreated()));
                    intent.putExtra("animalDailyTravel", currentReport.getAnimalDailyTravel());
                }
            });
        }
    }

    private List<DailyEntity> mDaily;
    private final Context reportContext;
    private final LayoutInflater mInflator;

    public DailyAdapter (Context context) {
        mInflator = LayoutInflater.from(context);
        this.reportContext = context;
    }

    @NonNull
    @Override
    public DailyAdapter.DailyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflator.inflate(R.layout.daily_list_items, parent, false);
        return new DailyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DailyAdapter.DailyViewHolder holder, int position) {
        if (mDaily != null) {
            DailyEntity current = mDaily.get(position);
            String name = current.getAnimalName();
            String type = current.getAnimalType();
            Date date = current.getDateCreated();
            float dailyDistance = current.getAnimalDailyTravel();
            String animalInfo = "Name: " + name + "   Type: " + type + "   Date Created: " + date
                    + "   Daily Distance: " + dailyDistance;
            holder.dailyItemView.setText(animalInfo);
        } else {
            holder.dailyItemView.setText("No Daily Reports.");
        }
    }

    public void setDailyReport (List<DailyEntity> reports) {
        mDaily = reports;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mDaily != null) {
            return mDaily.size();
        } else {
            return 0;
        }
    }
}
