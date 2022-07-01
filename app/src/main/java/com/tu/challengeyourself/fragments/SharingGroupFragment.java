package com.tu.challengeyourself.fragments;

import static com.tu.challengeyourself.constants.Keys.GET_ALL_SHARING_URL;
import static com.tu.challengeyourself.constants.Keys.GET_HOT_SHARING_URL;
import static com.tu.challengeyourself.constants.Keys.GET_USER_SHARING_URL;
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
import com.tu.challengeyourself.R;
import com.tu.challengeyourself.SharingCommentsActivity;
import com.tu.challengeyourself.adapters.PersonalSharingAdapter;
import com.tu.challengeyourself.adapters.SharingAdapter;
import com.tu.challengeyourself.models.dto.ChallengeDTO;
import com.tu.challengeyourself.models.dto.CompletedChallengeDTO;
import com.tu.challengeyourself.models.dto.SharedChallengeDTO;
import com.tu.challengeyourself.requests.AuthorizedJsonArrayRequest;
import com.tu.challengeyourself.requests.VolleyManager;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.List;

public class SharingGroupFragment extends Fragment {
    private ListView challengesListView;
    private static final String[] options = {"ALL", "HOT", "MINE"};
    private Spinner dropdown;
    private List<SharedChallengeDTO> sharings;
    private SharingAdapter sharingAdapter;
    private PersonalSharingAdapter personalSharingAdapter;
    private Context context;

    public SharingGroupFragment(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sharing_group, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dropdown = view.findViewById(R.id.sharingDropdown);
        dropdown.setAdapter(new ArrayAdapter<>(context, R.layout.custom_spinner_option, options));
        challengesListView = view.findViewById(R.id.sharingList);
        setFilterDisplayedChallenges();
        initSharingNavigation();
    }

    private void initSharingNavigation() {
        challengesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SharedChallengeDTO sharing = ((SharedChallengeDTO) parent.getItemAtPosition(position));
                CompletedChallengeDTO challenge = sharing.getCompletedChallengeDTO();

                Intent intent = new Intent(context, SharingCommentsActivity.class);
                intent.putExtra("id", sharing.getId());
                intent.putExtra("name", challenge.getName());
                intent.putExtra("description", challenge.getDescription());
                intent.putExtra("comment", challenge.getComment());
                intent.putExtra("measurement", challenge.getMeasurement());
                intent.putExtra("target", challenge.getTarget());
                intent.putExtra("result", challenge.getResult());
                intent.putExtra("likes", sharing.getLikeCount());
                intent.putExtra("isLiked", sharing.getLiked());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.getApplicationContext().startActivity(intent);
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setFilterDisplayedChallenges() {
        String token = PreferenceManager.getDefaultSharedPreferences(getContext().getApplicationContext()).getString(TOKEN, "");
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, type, jsonDeserializationContext) ->
                LocalDateTime.parse(json.getAsJsonPrimitive().getAsString())).create();
        Type typeToken = new TypeToken<List<SharedChallengeDTO>>() {
        }.getType();

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: {
                        VolleyManager.getInstance().addToRequestQueue(
                                new AuthorizedJsonArrayRequest(Request.Method.GET, GET_ALL_SHARING_URL, token,
                                        new Response.Listener<JSONArray>() {
                                            @Override
                                            public void onResponse(JSONArray response) {
                                                sharings = gson.fromJson(response.toString(), typeToken);

                                                sharingAdapter = new SharingAdapter(context, sharings);
                                                challengesListView.setAdapter(sharingAdapter);
                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        showToast("There was a problem loading challenges!");
                                    }
                                }).getRequest());
                        break;
                    }
                    case 1: {
                        VolleyManager.getInstance().addToRequestQueue(
                                new AuthorizedJsonArrayRequest(Request.Method.GET, GET_HOT_SHARING_URL, token,
                                        new Response.Listener<JSONArray>() {
                                            @Override
                                            public void onResponse(JSONArray response) {
                                                sharings = gson.fromJson(response.toString(), typeToken);

                                                sharingAdapter = new SharingAdapter(context, sharings);
                                                challengesListView.setAdapter(sharingAdapter);
                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        showToast("There was a problem loading challenges!");
                                    }
                                }).getRequest());
                        break;
                    }
                    case 2: {
                        VolleyManager.getInstance().addToRequestQueue(
                                new AuthorizedJsonArrayRequest(Request.Method.GET, GET_USER_SHARING_URL, token,
                                        new Response.Listener<JSONArray>() {
                                            @Override
                                            public void onResponse(JSONArray response) {
                                                sharings = gson.fromJson(response.toString(), typeToken);

                                                personalSharingAdapter = new PersonalSharingAdapter(context, sharings);
                                                challengesListView.setAdapter(personalSharingAdapter);
                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        showToast("There was a problem loading challenges!");
                                    }
                                }).getRequest());
                        break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }
}