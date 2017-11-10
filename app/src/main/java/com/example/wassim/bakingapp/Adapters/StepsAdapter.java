package com.example.wassim.bakingapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.wassim.bakingapp.Objects.Step;
import com.example.wassim.bakingapp.R;
import java.util.ArrayList;

/**
 * Created by Wassim on 2017-11-07
 */

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Step> steps;
    public OnStepInstructionClickListener onStepInstructionClickListener;


    public interface OnStepInstructionClickListener {
        void onStepClick(String videoUrl, String stepDescription, String thumbnailUrl);
    }

    public StepsAdapter(Context context, ArrayList<Step> data, OnStepInstructionClickListener clickListener ) {
        mContext = context;
        steps = data;
        onStepInstructionClickListener = clickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.step_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Step step = steps.get(position);
        String description = step.getDescription();
        holder.stepTextView.setText(description);
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {

        TextView stepTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            stepTextView =  itemView.findViewById(R.id.step_description);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Step step = steps.get(position);
            String videoUrl = step.getVideoUrl();
            String stepDescription = step.getShortDiscription();
            String thumbnailUrl = step.getThumbnailUrl();
            onStepInstructionClickListener.onStepClick(videoUrl,stepDescription,thumbnailUrl);
        }
    }

}
