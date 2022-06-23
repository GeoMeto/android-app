package com.tu.challengeyourself.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tu.challengeyourself.R;
import com.tu.challengeyourself.adapters.ChallengeAdapter;
import com.tu.challengeyourself.models.Challenge;

import java.util.ArrayList;
import java.util.List;

public class ChallengesFragment extends Fragment {

    private List<Challenge> challenges = new ArrayList<Challenge>(List.of(
            new Challenge("Go home", "I want to go places", 2, 10),
            new Challenge("Go home", "I want to go places", 2, 10),
            new Challenge("Go home", "I want to go places", 2, 10),
            new Challenge("Go home", "I want to go places", 2, 10),
            new Challenge("Go home", "I want to go places", 2, 10)
    ));

    public ChallengesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_challenges, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ChallengeAdapter adapter = new ChallengeAdapter(getContext(), challenges);
        ListView challengesListView = view.findViewById(R.id.challengesList);
        challengesListView.setAdapter(adapter);
    }
}