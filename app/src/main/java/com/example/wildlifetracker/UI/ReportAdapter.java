package com.example.wildlifetracker.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wildlifetracker.Entity.AnimalEntity;
import com.example.wildlifetracker.Entity.DailyEntity;
import com.example.wildlifetracker.Entity.MonthlyEntity;
import com.example.wildlifetracker.Entity.ReportEntity;
import com.example.wildlifetracker.R;

import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    class ViewHolder0 extends RecyclerView.ViewHolder {
        private final TextView dailyItemView;

        public ViewHolder0(View itemView){
        super(itemView);
        dailyItemView = itemView.findViewById(R.id.dailyList);
        }
    }

    class ViewHolder2 extends RecyclerView.ViewHolder {
        private final TextView monthlyItemView;

        public ViewHolder2(View itemView) {
            super(itemView);
            monthlyItemView = itemView.findViewById(R.id.monthlyList);
        }
    }

    private List<DailyEntity> mDaily;
    private List<MonthlyEntity> mMonthly;
    private final Context animalContext;
    private final LayoutInflater mInflator;

    public ReportAdapter (Context context) {
        mInflator = LayoutInflater.from(context);
        this.animalContext = context;
    }

    public int getItemViewType(int position) {
        // Just as an example, return 0 or 2 depending on position
        // Note that unlike in ListView adapters, types don't have to be contiguous
        return position % 2 * 2;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        switch (viewType) {
            case 0:
                View dailyItemView = mInflator.inflate(R.layout.daily_list_items, parent, false);
                viewHolder = new ViewHolder0(dailyItemView);
                break;
            case 2:
                View monthlyItemView = mInflator.inflate(R.layout.monthly_list_items, parent, false);
                viewHolder = new ViewHolder2(monthlyItemView);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + viewType);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        switch (holder.getItemViewType()) {
            case 0:
                ViewHolder0 viewHolder0 = (ViewHolder0)holder;
                if (mDaily != null) {
                    DailyEntity current = mDaily.get(position);
                    String name = current.getAnimalName();
                    String type = current.getAnimalType();
                    float dailyDistance = current.getAnimalDailyTravel();
                    String animalInfo = name + "   " + type + "-------------" + dailyDistance;
                    viewHolder0.dailyItemView.setText(animalInfo);
                } else {
                    viewHolder0.dailyItemView.setText("No Daily Reports");
                }
                break;

            case 2:
                ViewHolder2 viewHolder2 = (ViewHolder2)holder;
                if (mDaily != null) {
                    MonthlyEntity current = mMonthly.get(position);
                    String name = current.getAnimalName();
                    String type = current.getAnimalType();
                    float monthlyDistance = current.getAnimalMonthlyTravel();
                    String animalInfo = name + "   " + type + "-------------" + monthlyDistance;
                    viewHolder2.monthlyItemView.setText(animalInfo);
                } else {
                    viewHolder2.monthlyItemView.setText("No Monthly Reports");
                }
                break;
        }
    }

    public void setDailyReport (List<DailyEntity> dailyReport) {
        mDaily = dailyReport;
        notifyDataSetChanged();
    }

    public void setMonthlyReport (List<MonthlyEntity> monthlyReport) {
        mMonthly = monthlyReport;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mDaily != null) {
            return mDaily.size();
        } else if (mMonthly != null) {
            return mMonthly.size();
        } else {
            return 0;
        }
    }
}
