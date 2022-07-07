package com.tu.challengeyourself;

import static com.tu.challengeyourself.constants.Keys.BASE_CHALLENGES_URL;
import static com.tu.challengeyourself.constants.Keys.CREATE_CHALLENGES_URL;
import static com.tu.challengeyourself.constants.Keys.TOKEN;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tu.challengeyourself.adapters.CreateChallengeAdapter;
import com.tu.challengeyourself.models.responses.ChallengeDTO;
import com.tu.challengeyourself.models.responses.CompletedChallengeDTO;
import com.tu.challengeyourself.requests.AuthorizedJsonArrayRequest;
import com.tu.challengeyourself.requests.AuthorizedJsonRequest;
import com.tu.challengeyourself.requests.VolleyManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class CreateChallengeActivity extends AppCompatActivity {

    private EditText challengeNameEdit, challengeDescEdit, measurementEdit, targetEdit;
    private Button completeBtn;
    private LinearLayout challengeContainer;
    private ListView challengesListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_challenge);

        challengeNameEdit = findViewById(R.id.editChallengeName);
        challengeDescEdit = findViewById(R.id.editChallengeDescription);
        measurementEdit = findViewById(R.id.editChallengeMeasurement);
        targetEdit = findViewById(R.id.editChallengeTarget);
        completeBtn = findViewById(R.id.btnFinalizeChallenge);
        challengeContainer = findViewById(R.id.finalizeChallengeContainer);
        challengeContainer.setVisibility(View.INVISIBLE);

        String token = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(TOKEN, "");
        challengesListView = findViewById(R.id.challengeTemplatesList);
        VolleyManager.getInstance().addToRequestQueue(
                new AuthorizedJsonArrayRequest(Request.Method.GET, BASE_CHALLENGES_URL, token,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                List<ChallengeDTO> challenges = new Gson().fromJson(response.toString(), new TypeToken<List<ChallengeDTO>>() {
                                }.getType());
                                CreateChallengeAdapter adapter = new CreateChallengeAdapter(CreateChallengeActivity.this, challenges);
                                challengesListView.setAdapter(adapter);
                                setListViewClickListener();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        showToast("There was a problem loading challenge templates!");
                        navigateToMain();
                    }
                }).getRequest());
    }

    private void setListViewClickListener() {
        challengesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ChallengeDTO dto = ((ChallengeDTO) parent.getItemAtPosition(position));
                challengesListView.setVisibility(View.GONE);
                initChallengeView(dto);
            }
        });
    }

    private void initChallengeView(ChallengeDTO challengeDTO) {
        challengeContainer.setVisibility(View.VISIBLE);

        challengeNameEdit.setText(challengeDTO.getName());
        challengeDescEdit.setText(challengeDTO.getDescription());
        measurementEdit.setText(challengeDTO.getMeasurement());
        targetEdit.setText(challengeDTO.getTarget() + "");

        completeBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                String token = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(TOKEN, "");

                VolleyManager.getInstance().addToRequestQueue(
                        new AuthorizedJsonRequest(Request.Method.POST, CREATE_CHALLENGES_URL, token, generateViewChallenge(),
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        navigateToMain();
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                showToast("There was a problem on challenge creation!");
                                navigateToMain();
                            }
                        }).getRequest());
            }
        });
    }

    private CompletedChallengeDTO generateViewChallenge() {
        CompletedChallengeDTO dto = new CompletedChallengeDTO();
        dto.setName(challengeNameEdit.getText().toString());
        dto.setTarget(Integer.parseInt(targetEdit.getText().toString()));
        dto.setDescription(challengeDescEdit.getText().toString());
        dto.setMeasurement(measurementEdit.getText().toString());
        return dto;
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void navigateToMain() {
        Intent mainIntent = new Intent(CreateChallengeActivity.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }
}