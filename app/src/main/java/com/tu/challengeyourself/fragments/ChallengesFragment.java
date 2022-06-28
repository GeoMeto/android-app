package com.tu.challengeyourself.fragments;

import static com.tu.challengeyourself.constants.Keys.GET_CHALLENGES_URL;
import static com.tu.challengeyourself.constants.Keys.TOKEN;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.reflect.TypeToken;
import com.tu.challengeyourself.CreateChallengeActivity;
import com.tu.challengeyourself.R;
import com.tu.challengeyourself.adapters.ChallengeAdapter;
import com.tu.challengeyourself.models.dto.CompletedChallengeDTO;
import com.tu.challengeyourself.requests.AuthorizedJsonArrayRequest;
import com.tu.challengeyourself.requests.VolleyManager;

import org.json.JSONArray;

import java.time.LocalDateTime;
import java.util.List;

public class ChallengesFragment extends Fragment {

    private ListView challengesListView;
    private static final String[] options = {"ACTIVE", "COMPLETED"};
    private Spinner dropdown;
    private List<CompletedChallengeDTO> challenges;
    private ChallengeAdapter challengeAdapter;
    private Context context;

    public ChallengesFragment(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_challenges, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String token = PreferenceManager.getDefaultSharedPreferences(getContext().getApplicationContext()).getString(TOKEN, "");
        dropdown = view.findViewById(R.id.challengesDropdown);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this.getContext(), R.layout.custom_spinner_option, options);
        dropdown.setAdapter(spinnerAdapter);

        challengesListView = view.findViewById(R.id.challengesList);
        VolleyManager.getInstance().addToRequestQueue(
                new AuthorizedJsonArrayRequest(Request.Method.GET, GET_CHALLENGES_URL, token,
                        new Response.Listener<JSONArray>() {
                            @RequiresApi(api = Build.VERSION_CODES.O)
                            @Override
                            public void onResponse(JSONArray response) {
                                Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, type, jsonDeserializationContext) ->
                                        LocalDateTime.parse(json.getAsJsonPrimitive().getAsString())).create();
                                challenges = gson.fromJson(response.toString(), new TypeToken<List<CompletedChallengeDTO>>() {
                                }.getType());

                                challengeAdapter = new ChallengeAdapter(context, challenges);
                                challengesListView.setAdapter(challengeAdapter);
                                filterDisplayedChallenges();
                                challengeAdapter.getFilter().filter(Boolean.FALSE.toString());
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        showToast("There was a problem loading challenges!");
                    }
                }).getRequest());


        view.findViewById(R.id.btnCreateChallenge).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToCreateChallengeActivity();
            }
        });
    }

    private void filterDisplayedChallenges() {
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: {
                        challengeAdapter.getFilter().filter(Boolean.FALSE.toString());
                        break;
                    }
                    case 1: {
                        challengeAdapter.getFilter().filter(Boolean.TRUE.toString());
                        break;
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void navigateToCreateChallengeActivity() {
        Intent intent = new Intent(getContext(), CreateChallengeActivity.class);
        startActivity(intent);
    }

    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }
}