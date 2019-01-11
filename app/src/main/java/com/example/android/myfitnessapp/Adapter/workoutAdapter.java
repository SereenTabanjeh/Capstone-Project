package com.example.android.myfitnessapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.myfitnessapp.Database.workoutEntity;
import com.example.android.myfitnessapp.R;
import com.example.android.myfitnessapp.listener.ItemClickListenerObject;

import java.util.List;

public class workoutAdapter extends RecyclerView.Adapter<workoutAdapter.workoutViewHolder> {
    private static final String TAG = "workoutAdapter";
    Context mContext;
    List<workoutEntity> workoutList;
    ItemClickListenerObject mItemClickLitenerObject;

    public workoutAdapter(Context context ,ItemClickListenerObject mItemClickLitenerObject) {
        this.mContext = context;
        this.mItemClickLitenerObject=mItemClickLitenerObject;
        notifyDataSetChanged();
    }



    public void setWorkoutList(List<workoutEntity> workoutList) {

        this.workoutList = workoutList;
    }

    @NonNull
    @Override
    public workoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_workout_item, parent, false);
        return new workoutAdapter.workoutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull workoutViewHolder holder, int position) {

        String name = workoutList.get(position).getName();
        Integer id =  workoutList.get(position).getId();
        String exeId = workoutList.get(position).getExeIds();

        holder.workoutName.setText(name );
    }

    @Override
    public int getItemCount() {
        return workoutList.size();
    }
    public List<workoutEntity> getWorkoutList() {
        return workoutList;
    }
    public class workoutViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView workoutName;

        public workoutViewHolder(View itemView) {
            super(itemView);
            workoutName =  itemView.findViewById(R.id.name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mItemClickLitenerObject.onClickItemObject(getAdapterPosition());
        }
    }
}
