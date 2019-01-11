package com.example.android.myfitnessapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.myfitnessapp.Database.ExerciseEntity;
import com.example.android.myfitnessapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

public class exerciseAdapter extends   RecyclerView.Adapter<exerciseAdapter.exerciseViewHolder>  {
    private static final String TAG = "exerciseAdapter";
    Context mContext;
    List<ExerciseEntity> exerciseList;
    private View mView;
    private static final int MAX_WIDTH = 100;
    private static final int MAX_HEIGHT = 100;

    public exerciseAdapter(Context context ) {
        this.mContext = context;
        notifyDataSetChanged();
    }

    public void setExerciseList(List<ExerciseEntity> exerciseList) {
        this.exerciseList = exerciseList;
    }


    @Override
    public exerciseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item, parent, false);
        return new exerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(exerciseViewHolder holder, int position) {

        Integer duration = exerciseList.get(position).getMinutes();
        Integer reps =  exerciseList.get(position).getReps();
        Integer weight = exerciseList.get(position).getIntWeight();

        if (duration != 0) {
            holder.repsOrMin.setText(duration + " min");
        } else {
            holder.repsOrMin.setText(reps + " reps");
        }

        if (weight != 0) {
            holder.weightView.setText(weight + " lb");
            holder.weightView.setVisibility(View.VISIBLE);
        }

        holder.exerciseName.setText(exerciseList.get(position).getName());
        holder.calories.setText(String.format(Locale.US, "%d Calories", exerciseList.get(position).getIntCalories()));

        String muscle = exerciseList.get(position).getMuscle();

        int muscleDrawable;

        if (muscle.equals("Abs")) {
            muscleDrawable = R.drawable.abs;
        } else if (muscle.equals("Arms")) {
            muscleDrawable = R.drawable.arms;
        }else if (muscle.equals("Back")) {
            muscleDrawable = R.drawable.back;
        }else if (muscle.equals("Cardio")) {
            muscleDrawable = R.drawable.cardio;
        }else if (muscle.equals("Chest")) {
            muscleDrawable = R.drawable.chest;
        }else if (muscle.equals("Legs")) {
            muscleDrawable = R.drawable.legs;
        }else if (muscle.equals("Shoulders")) {
            muscleDrawable = R.drawable.shoulders;
        } else {
            muscleDrawable = R.drawable.other;
        }

        Picasso.get()
                .load(muscleDrawable)
                .resize(MAX_WIDTH, MAX_HEIGHT)
                .centerCrop()
                .into(holder.imageView);


    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    public class exerciseViewHolder extends RecyclerView.ViewHolder{
        TextView exerciseName;
        TextView calories;
        TextView repsOrMin;
        TextView weightView;
        ImageView imageView;



        public exerciseViewHolder(View itemView) {
            super(itemView);

            exerciseName =  itemView.findViewById(R.id.name);
            calories = itemView.findViewById(R.id.calories);
            repsOrMin =  itemView.findViewById(R.id.repsOrDuration);
            weightView =  itemView.findViewById(R.id.weight);
            imageView = itemView.findViewById(R.id.muscleImage);

        }
    }

}
