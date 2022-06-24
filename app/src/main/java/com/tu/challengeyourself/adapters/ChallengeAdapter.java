package com.tu.challengeyourself.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tu.challengeyourself.CreateChallengeActivity;
import com.tu.challengeyourself.R;
import com.tu.challengeyourself.models.dto.CompletedChallengeDTO;

import java.util.List;

public class ChallengeAdapter extends ArrayAdapter<CompletedChallengeDTO> {

    private final String completedTemplate = "Completed: ";
    private final String targetTemplate = "Target: ";

    private Context context;

    public ChallengeAdapter(Context context, List<CompletedChallengeDTO> challenges) {
        super(context, R.layout.challenge_item, challenges);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        CompletedChallengeDTO challenge = getItem(position);
        View view = layoutInflater.inflate(R.layout.challenge_item, null);

        TextView challengeNameTxt = view.findViewById(R.id.itemChallengeName);
        TextView challengeDescTxt = view.findViewById(R.id.itemChallengeDesc);
        TextView completeCountTxt = view.findViewById(R.id.itemCompleteCounter);
        TextView measurementTxt = view.findViewById(R.id.itemMeasurement);
        TextView targetCountTxt = view.findViewById(R.id.itemTargetCounter);
        Button completeBtn = view.findViewById(R.id.completeChallenge);

        challengeNameTxt.setText(challenge.getName());
        challengeDescTxt.setText(challenge.getDescription());
        measurementTxt.setText(challenge.getMeasurement());
        completeCountTxt.setText(challenge.getResult() + "");
        targetCountTxt.setText(challenge.getTarget() + "");

        completeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CreateChallengeActivity.class);
                intent.putExtra("id", challenge.getId());
                context.startActivity(intent);
            }
        });
        return view;
    }
}
