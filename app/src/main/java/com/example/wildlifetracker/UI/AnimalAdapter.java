package com.example.wildlifetracker.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wildlifetracker.Entity.AnimalEntity;
import com.example.wildlifetracker.R;

import java.util.List;

public class AnimalAdapter extends RecyclerView.Adapter<AnimalAdapter.AnimalViewHolder> {
    class AnimalViewHolder extends RecyclerView.ViewHolder {
        private final TextView animalItemView;

        private AnimalViewHolder (View itemView) {
            super (itemView);
            animalItemView = itemView.findViewById(R.id.animalList);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final AnimalEntity currentAnimal = mAnimals.get(position);
                    Intent intent = new Intent(animalContext, AnimalDetail.class);
                    intent.putExtra("animalID", currentAnimal.getAnimalID());
                    intent.putExtra("name", currentAnimal.getName());
                    intent.putExtra("type", currentAnimal.getType());
                    intent.putExtra("notes", currentAnimal.getNotes());
                    animalContext.startActivity(intent);
                }
            });
        }
    }

    private List<AnimalEntity> mAnimals;
    private final Context animalContext;
    private final LayoutInflater mInflator;

    public AnimalAdapter (Context context) {
        mInflator = LayoutInflater.from(context);
        this.animalContext = context;
    }

    @NonNull
    @Override
    public AnimalAdapter.AnimalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflator.inflate(R.layout.animal_list_items, parent, false);
        return new AnimalViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimalAdapter.AnimalViewHolder holder, int position) {
        if (mAnimals != null) {
            AnimalEntity current = mAnimals.get(position);
            String name = current.getName();
            String type = current.getType();
            String animalInfo = name + " " + type;
            holder.animalItemView.setText(animalInfo);
        } else {
            holder.animalItemView.setText("No Animal Name or Type.");
        }
    }

    public void setAnimals (List<AnimalEntity> animals) {
        mAnimals = animals;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mAnimals != null) {
            return mAnimals.size();
        } else {
            return 0;
        }
    }
}
