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
import com.tu.challengeyourself.models.Challenge;

import java.util.List;

public class ChallengeAdapter extends ArrayAdapter<Challenge> {

    private final String completedTemplate = "Completed:";
    private final String targetTemplate = "Target: ";

    private Context context;

    public ChallengeAdapter(Context context, List<Challenge> challenges) {
        super(context, R.layout.challenge_item, challenges);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        Challenge challenge = getItem(position);
        View view = layoutInflater.inflate(R.layout.challenge_item, null);

        TextView challengeNameTxt = view.findViewById(R.id.itemChallengeName);
        TextView challengeDescTxt = view.findViewById(R.id.itemChallengeDesc);
        TextView completeCountTxt = view.findViewById(R.id.itemCompleteCounter);
        TextView targetCountTxt = view.findViewById(R.id.itemTargetCounter);

        challengeNameTxt.setText(challenge.getChallengeName());
        challengeDescTxt.setText(challenge.getDescription());
        completeCountTxt.setText(completedTemplate + challenge.getCompletedCount());
        targetCountTxt.setText(targetTemplate + challenge.getTargetCount());
        return view;
    }
}
