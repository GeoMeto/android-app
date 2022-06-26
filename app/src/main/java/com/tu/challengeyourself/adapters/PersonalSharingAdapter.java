package com.tu.challengeyourself.adapters;

import static com.tu.challengeyourself.constants.Keys.DELETE_SHARING_URL;
import static com.tu.challengeyourself.constants.Keys.LIKE_SHARING_URL;
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
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.reflect.TypeToken;
import com.tu.challengeyourself.R;
import com.tu.challengeyourself.SharingCommentsActivity;
import com.tu.challengeyourself.models.dto.CompletedChallengeDTO;
import com.tu.challengeyourself.models.dto.LikesDTO;
import com.tu.challengeyourself.models.dto.SharedChallengeDTO;
import com.tu.challengeyourself.models.dto.UserCommentDTO;
import com.tu.challengeyourself.requests.AuthorizedJsonRequest;
import com.tu.challengeyourself.requests.VolleyManager;

import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class PersonalSharingAdapter extends ArrayAdapter<SharedChallengeDTO> implements Filterable {

    private ImageButton likeBtn;
    private TextView likesCounterTxt;
    private Context context;

    public PersonalSharingAdapter(Context context, List<SharedChallengeDTO> sharings) {
        super(context, R.layout.sharing_item, sharings);
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        SharedChallengeDTO sharing = getItem(position);
        CompletedChallengeDTO challenge = sharing.getCompletedChallengeDTO();
        View view = layoutInflater.inflate(R.layout.sharing_item, null);

        TextView sharingNameTxt = view.findViewById(R.id.sharingName);
        TextView sharingDescTxt = view.findViewById(R.id.sharingDesc);
        TextView sharingCommentTxt = view.findViewById(R.id.sharingComment);
        TextView measurementTxt = view.findViewById(R.id.sharingMeasurement);
        TextView targetCountTxt = view.findViewById(R.id.sharingTargetCounter);
        TextView completeCountTxt = view.findViewById(R.id.sharingCompleteCounter);
        TextView commentsTxt = view.findViewById(R.id.commentsTxt);
        likesCounterTxt = view.findViewById(R.id.likeCounterTxt);
        likeBtn = view.findViewById(R.id.likeSharingBtn);

        initLikeImages(sharing.getLicked());

        sharingNameTxt.setText(challenge.getName());
        sharingDescTxt.setText(challenge.getDescription());
        sharingCommentTxt.setText(challenge.getComment());
        measurementTxt.setText(challenge.getMeasurement());
        completeCountTxt.setText(challenge.getResult() + "");
        targetCountTxt.setText(challenge.getTarget() + "");
        likesCounterTxt.setText(sharing.getLikeCount() + "");
        commentsTxt.setText(formatComments(sharing.getUserCommentDTO()));

        initDeleteButton(sharing);
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private String formatComments(List<UserCommentDTO> userComments) {
        return userComments.stream().map(x -> String.format("%s: %s", x.getUsername(), x.getContent())).collect(Collectors.joining("\n"));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initDeleteButton(SharedChallengeDTO sharing) {
        likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext()).getString(TOKEN, "");

                VolleyManager.getInstance().addToRequestQueue(
                        new AuthorizedJsonRequest(Request.Method.DELETE, String.format(DELETE_SHARING_URL, sharing.getId()), token,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, type, jsonDeserializationContext) ->
                                                LocalDateTime.parse(json.getAsJsonPrimitive().getAsString())).create();
                                        LikesDTO likesDTO = gson.fromJson(response.toString(), new TypeToken<LikesDTO>() {
                                        }.getType());
                                        remove(sharing);
                                        notifyDataSetChanged();
                                        likesCounterTxt.setText(likesDTO.getLikesCount() + "");
                                        initLikeImages(likesDTO.isLiked());
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

    private void initLikeImages(boolean isLiked) {
        if (isLiked) {
            likeBtn.setImageResource(R.drawable.ic_like_24);
        } else {
            likeBtn.setImageResource(R.drawable.ic_empty_like_24);
        }
    }

    private AlertDialog showCommentAlert() {
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