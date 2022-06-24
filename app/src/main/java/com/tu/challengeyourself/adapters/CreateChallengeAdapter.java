package com.tu.challengeyourself.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tu.challengeyourself.R;
import com.tu.challengeyourself.models.dto.ChallengeDTO;

import java.util.List;

public class CreateChallengeAdapter extends ArrayAdapter<ChallengeDTO> {

    private final String targetTemplate = "Target: %d";

    private Context context;

    public CreateChallengeAdapter(Context context, List<ChallengeDTO> challenges) {
        super(context, R.layout.challenge_item, challenges);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        ChallengeDTO challenge = getItem(position);
        View view = layoutInflater.inflate(R.layout.create_challenge_item, null);

        TextView challengeNameTxt = view.findViewById(R.id.challengeNameTxt);
        TextView challengeDescTxt = view.findViewById(R.id.challengeDescTxt);
        TextView targetCountTxt = view.findViewById(R.id.targetTxt);
        TextView measurementTxt = view.findViewById(R.id.measurementTxt);

        challengeNameTxt.setText(challenge.getName());
        challengeDescTxt.setText(challenge.getDescription());
        measurementTxt.setText(challenge.getMeasurement());
        targetCountTxt.setText(String.format(targetTemplate, challenge.getTarget()));
        return view;
    }
}
