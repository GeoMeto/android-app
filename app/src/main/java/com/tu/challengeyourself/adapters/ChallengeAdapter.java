package com.tu.challengeyourself.adapters;

import static com.tu.challengeyourself.constants.Keys.CREATE_SHARING_URL;
import static com.tu.challengeyourself.constants.Keys.DELETE_CHALLENGES_URL;
import static com.tu.challengeyourself.constants.Keys.TOKEN;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.preference.PreferenceManager;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.tu.challengeyourself.CompleteChallengeActivity;
import com.tu.challengeyourself.R;
import com.tu.challengeyourself.models.dto.CompletedChallengeDTO;
import com.tu.challengeyourself.models.dto.SharedChallengeDTO;
import com.tu.challengeyourself.requests.AuthorizedJsonRequest;
import com.tu.challengeyourself.requests.VolleyManager;

import org.json.JSONObject;

import java.util.List;
import java.util.stream.Collectors;

public class ChallengeAdapter extends ArrayAdapter<CompletedChallengeDTO> implements Filterable {

    private List<CompletedChallengeDTO> challenges;
    private List<CompletedChallengeDTO> filteredData;
    private Button completeBtn, shareBtn, deleteBtn;
    private ImageView sharedImage;

    private Context context;

    public ChallengeAdapter(Context context, List<CompletedChallengeDTO> challenges) {
        super(context, R.layout.challenge_item);
        this.context = context;
        this.challenges = challenges;
        this.filteredData = challenges;
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
        sharedImage = view.findViewById(R.id.sharedChallengeImage);
        completeBtn = view.findViewById(R.id.completeChallengeBtn);
        shareBtn = view.findViewById(R.id.shareChallengeBtn);
        deleteBtn = view.findViewById(R.id.deleteChallenge);

        initDeleteButton(challenge.getId());

        if (challenge.getCompleted()) {
            completeBtn.setVisibility(View.GONE);

            if (challenge.getShared()) {
                shareBtn.setVisibility(View.INVISIBLE);
            } else {
                sharedImage.setVisibility(View.INVISIBLE);
                initShareButton(challenge);
            }
        } else {
            shareBtn.setVisibility(View.INVISIBLE);
            sharedImage.setVisibility(View.INVISIBLE);
            initCompleteButton(challenge);
        }
        challengeNameTxt.setText(challenge.getName());
        challengeDescTxt.setText(challenge.getDescription());
        measurementTxt.setText(challenge.getMeasurement());
        completeCountTxt.setText(challenge.getResult() + "");
        targetCountTxt.setText(challenge.getTarget() + "");
        return view;
    }

    private void initDeleteButton(int id) {
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext()).getString(TOKEN, "");

                VolleyManager.getInstance().addToRequestQueue(
                        new AuthorizedJsonRequest(Request.Method.DELETE, String.format(DELETE_CHALLENGES_URL, id) , token,
                                new Response.Listener<JSONObject>() {
                                    @RequiresApi(api = Build.VERSION_CODES.N)
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        filteredData = filteredData.stream().filter(x-> x.getId() != id).collect(Collectors.toList());
                                        challenges = challenges.stream().filter(x-> x.getId() != id).collect(Collectors.toList());
                                        notifyDataSetChanged();
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                showToast("There was a problem with deleting of challenge!");
                            }
                        }).getRequest());
            }
        });
    }

    private void initShareButton(CompletedChallengeDTO challenge) {
        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog shareAlert = showShareAlert();
                Button dismissBtn = shareAlert.findViewById(R.id.noAlertBtn);
                Button confirmBtn = shareAlert.findViewById(R.id.yesAlertBtn);

                confirmBtn.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(View v) {
                        String token = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext()).getString(TOKEN, "");

                        SharedChallengeDTO sharedChallenge = new SharedChallengeDTO();
                        sharedChallenge.setCompletedChallengeDTO(challenge);

                        VolleyManager.getInstance().addToRequestQueue(
                                new AuthorizedJsonRequest(Request.Method.POST, CREATE_SHARING_URL, token, sharedChallenge,
                                        new Response.Listener<JSONObject>() {
                                            @Override
                                            public void onResponse(JSONObject response) {
                                                challenge.setShared(true);
                                                shareBtn.setVisibility(View.GONE);
                                                sharedImage.setVisibility(View.VISIBLE);
                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        showToast("There was a problem on sharing of challenge!");
                                    }
                                }).getRequest());
                        shareAlert.dismiss();
                    }
                });

                dismissBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        shareAlert.dismiss();
                    }
                });
            }
        });
    }

    private void initCompleteButton(CompletedChallengeDTO challenge) {

        completeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(), CompleteChallengeActivity.class);
                intent.putExtra("id", challenge.getId());
                intent.putExtra("name", challenge.getName());
                intent.putExtra("description", challenge.getDescription());
                intent.putExtra("measurement", challenge.getMeasurement());
                intent.putExtra("target", challenge.getTarget());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                results.values = challenges.stream().filter(x -> x.getCompleted().toString().equals(constraint)).collect(Collectors.toList());
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredData = (List<CompletedChallengeDTO>) results.values;
                clear();
                addAll(filteredData);
                ChallengeAdapter.this.notifyDataSetChanged();
            }
        };
    }

    private AlertDialog showShareAlert() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View alertInflater = inflater.inflate(R.layout.share_alert_box, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.Theme_ChallengeYourself));
        builder.setView(alertInflater);
        AlertDialog offlineAlert = builder.create();
        offlineAlert.show();
        return offlineAlert;
    }

    private void showToast(String message) {
        Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
}