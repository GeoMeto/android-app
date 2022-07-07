package com.tu.challengeyourself;

import static com.tu.challengeyourself.constants.Keys.COMPLETE_CHALLENGES_URL;
import static com.tu.challengeyourself.constants.Keys.TOKEN;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.tu.challengeyourself.fragments.SharingGroupFragment;
import com.tu.challengeyourself.models.responses.CompletedChallengeDTO;
import com.tu.challengeyourself.requests.AuthorizedJsonRequest;
import com.tu.challengeyourself.requests.VolleyManager;

import org.json.JSONObject;

public class CompleteChallengeActivity extends AppCompatActivity {

    private int id, target;
    private String name, description, measurement;

    private TextView nameTxt, descTxt, measurementTxt, targetTxt;
    private EditText resultEdit, commentEdit;
    private Button completeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_challenge);

        initIntentVariables();
        initTextViews();

        completeBtn = findViewById(R.id.btnCompleteChallenge);
        completeBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                try {
                    if(Integer.parseInt(resultEdit.getText().toString()) < 0) {
                        showToast("Please put a positive number for result!");
                        return;
                    }
                } catch (Exception e) {
                    showToast("Please put a positive number for result!");
                    return;
                }

                String token = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(TOKEN, "");

                VolleyManager.getInstance().addToRequestQueue(
                        new AuthorizedJsonRequest(Request.Method.POST, COMPLETE_CHALLENGES_URL, token, generateViewChallenge(),
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        navigateToMain();
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                showToast("There was a problem on completion of challenge!");
                                navigateToMain();
                            }
                        }).getRequest());
            }
        });
    }

    private CompletedChallengeDTO generateViewChallenge() {
        CompletedChallengeDTO dto = new CompletedChallengeDTO();
        dto.setId(id);
        dto.setTarget(target);
        dto.setResult(Integer.parseInt(resultEdit.getText().toString()));
        dto.setName(name);
        dto.setDescription(description);
        dto.setMeasurement(measurement);
        dto.setComment(commentEdit.getText().toString());
        return dto;
    }

    private void initTextViews() {
        nameTxt = findViewById(R.id.completeNameTxt);
        descTxt = findViewById(R.id.completeDescriptionTxt);
        measurementTxt = findViewById(R.id.completeMeasurementTxt);
        targetTxt = findViewById(R.id.completeTargetTxt);

        resultEdit = findViewById(R.id.editCompleteResult);
        commentEdit = findViewById(R.id.editCompleteComment);

        nameTxt.setText(name);
        descTxt.setText(description);
        measurementTxt.setText(measurement);
        targetTxt.setText(target + "");
    }

    private void initIntentVariables() {
        Bundle bundle = getIntent().getExtras();
        id = Integer.parseInt(bundle.get("id").toString());
        target = Integer.parseInt(bundle.get("target").toString());
        name = bundle.get("name").toString();
        description = bundle.get("description").toString();
        measurement = bundle.get("measurement").toString();
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void navigateToMain() {
        Intent mainIntent = new Intent(this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mainIntent);
        finish();
    }
}