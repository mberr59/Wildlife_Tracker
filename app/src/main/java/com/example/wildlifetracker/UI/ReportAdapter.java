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
import com.example.wildlifetracker.Entity.AnimalEntity;
import com.example.wildlifetracker.Entity.DailyEntity;
import com.example.wildlifetracker.Entity.MonthlyEntity;
import com.example.wildlifetracker.Entity.ReportEntity;
import com.example.wildlifetracker.R;

import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportViewHolder> {
    class ReportViewHolder extends RecyclerView.ViewHolder {
        private final TextView reportItemView;

        private ReportViewHolder (View itemView) {
            super (itemView);
            reportItemView = itemView.findViewById(R.id.animalList);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final ReportEntity currentReport = mReports.get(position);
                    Intent intent = new Intent(reportContext, AnimalDetail.class);
                    intent.putExtra("reportID", currentReport.getReportID());
                    intent.putExtra("animalName", currentReport.getAnimalName());
                    intent.putExtra("animalType", currentReport.getAnimalType());
                    intent.putExtra("dateCreated", Converter.toTimestamp(currentReport.getDateCreated()));
                }
            });
        }
    }

    private List<ReportEntity> mReports;
    private final Context reportContext;
    private final LayoutInflater mInflator;

    public ReportAdapter (Context context) {
        mInflator = LayoutInflater.from(context);
        this.reportContext = context;
    }

    @NonNull
    @Override
    public ReportAdapter.ReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflator.inflate(R.layout.monthly_list_items, parent, false);
        return new ReportAdapter.ReportViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportAdapter.ReportViewHolder holder, int position) {
        if (mReports != null) {
            ReportEntity current = mReports.get(position);
            String name = current.getAnimalName();
            String type = current.getAnimalType();
            String animalInfo = name + " " + type;
            holder.reportItemView.setText(animalInfo);
        } else {
            holder.reportItemView.setText("No Animal Name or Type.");
        }
    }

    public void setAnimals (List<ReportEntity> reports) {
        mReports = reports;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mReports != null) {
            return mReports.size();
        } else {
            return 0;
        }
    }
}
