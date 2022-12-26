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
import com.example.wildlifetracker.Entity.MonthlyEntity;
import com.example.wildlifetracker.Entity.ReportEntity;
import com.example.wildlifetracker.R;

import java.util.Date;
import java.util.List;

public class MonthlyAdapter extends RecyclerView.Adapter<MonthlyAdapter.MonthlyViewHolder>{
    class MonthlyViewHolder extends RecyclerView.ViewHolder {
        private final TextView monthlyItemView;

        private MonthlyViewHolder (View itemView) {
            super (itemView);
            monthlyItemView = itemView.findViewById(R.id.monthlyList);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final MonthlyEntity currentReport = mMonthly.get(position);
                    Intent intent = new Intent(reportContext, ReportActivity.class);
                    intent.putExtra("reportID", currentReport.getReportID());
                    intent.putExtra("animalName", currentReport.getAnimalName());
                    intent.putExtra("animalType", currentReport.getAnimalType());
                    intent.putExtra("dateCreated", Converter.toTimestamp(currentReport.getDateCreated()));
                    intent.putExtra("animalMonthlyTravel", currentReport.getAnimalMonthlyTravel());
                }
            });
        }
    }

    private List<MonthlyEntity> mMonthly;
    private final Context reportContext;
    private final LayoutInflater mInflator;

    public MonthlyAdapter (Context context) {
        mInflator = LayoutInflater.from(context);
        this.reportContext = context;
    }

    @NonNull
    @Override
    public MonthlyAdapter.MonthlyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflator.inflate(R.layout.monthly_list_items, parent, false);
        return new MonthlyAdapter.MonthlyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MonthlyAdapter.MonthlyViewHolder holder, int position) {
        if (mMonthly != null) {
            MonthlyEntity current = mMonthly.get(position);
            String name = current.getAnimalName();
            String type = current.getAnimalType();
            Date date = current.getDateCreated();
            float monthlyDistance = current.getAnimalMonthlyTravel();
            String animalInfo = "Name: " + name + "   Type: " + type + "\nDate Created: " + date
                    + "\nMonthly Distance: " + monthlyDistance + "\n\n";
            holder.monthlyItemView.setText(animalInfo);
        } else {
            holder.monthlyItemView.setText("No Monthly Reports");
        }
    }

    public void setMonthlyReports (List<MonthlyEntity> reports) {
        mMonthly = reports;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mMonthly != null) {
            return mMonthly.size();
        } else {
            return 0;
        }
    }
}
