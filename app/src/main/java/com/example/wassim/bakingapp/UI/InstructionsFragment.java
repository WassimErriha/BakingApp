package com.example.wassim.bakingapp.UI;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.wassim.bakingapp.R;

public class InstructionsFragment extends Fragment {

    public InstructionsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String stepDescription = getActivity().getIntent().getExtras().getString("step_description");
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_instructions, container, false);
        TextView stepInstructionsTextView = rootView.findViewById(R.id.step_instruction_text_view);
        stepInstructionsTextView.setText(stepDescription);
        return rootView;
    }
}
