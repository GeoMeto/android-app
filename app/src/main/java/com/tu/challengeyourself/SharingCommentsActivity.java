package com.tu.challengeyourself;

import static com.tu.challengeyourself.constants.Keys.DELETE_COMMENT_URL;
import static com.tu.challengeyourself.constants.Keys.GET_COMMENTS_URL;
import static com.tu.challengeyourself.constants.Keys.LIKE_SHARING_URL;
import static com.tu.challengeyourself.constants.Keys.POST_COMMENT_URL;
import static com.tu.challengeyourself.constants.Keys.REPORT_COMMENT_URL;
import static com.tu.challengeyourself.constants.Keys.TOKEN;

import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.reflect.TypeToken;
import com.tu.challengeyourself.adapters.CommentAdapter;
import com.tu.challengeyourself.models.dto.LikesDTO;
import com.tu.challengeyourself.models.dto.UserCommentDTO;
import com.tu.challengeyourself.requests.AuthorizedJsonArrayRequest;
import com.tu.challengeyourself.requests.AuthorizedJsonRequest;
import com.tu.challengeyourself.requests.VolleyManager;
import com.tu.challengeyourself.utils.InflaterUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.List;

public class SharingCommentsActivity extends AppCompatActivity {

    private TextView nameTxt, descTxt, measurementTxt, targetTxt, resultTxt, commentTxt, likesTxt;
    private Button addCommentBtn;
    private ImageButton likeBtn;
    private Bundle bundle;
    private int sharingId;

    private ListView commentsListView;
    private CommentAdapter commentAdapter;
    private Gson gson;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharing_comments);

        bundle = getIntent().getExtras();
        sharingId = bundle.getInt("id");
        gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, type, jsonDeserializationContext) ->
                LocalDateTime.parse(json.getAsJsonPrimitive().getAsString())).create();

        initViewContent();
        initCommentsList(sharingId);
        initLikeButton();
        initCommentButton();
    }

    private void initCommentsList(int sharingId) {
        commentsListView = findViewById(R.id.commentsList);
        String token = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(TOKEN, "");

        VolleyManager.getInstance().addToRequestQueue(
                new AuthorizedJsonArrayRequest(Request.Method.GET, String.format(GET_COMMENTS_URL, sharingId), token,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                Type typeToken = new TypeToken<List<UserCommentDTO>>() {
                                }.getType();
                                List<UserCommentDTO> comments = gson.fromJson(response.toString(), typeToken);

                                commentAdapter = new CommentAdapter(SharingCommentsActivity.this, comments);
                                commentsListView.setAdapter(commentAdapter);
                                initOnCommentClick();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        InflaterUtils.showToast(SharingCommentsActivity.this, "There was a problem loading comments!");
                    }
                }).getRequest());
    }

    private void initOnCommentClick() {
        commentsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String action = ((TextView) view.findViewById(R.id.reportDeleteCommentBtn)).getText().toString();
                UserCommentDTO userCommentDTO = ((UserCommentDTO) parent.getItemAtPosition(position));
                if (action.equals("DELETE")) {
                    deleteComment(userCommentDTO);
                } else if (action.equals("REPORT")) {
                    reportComment(userCommentDTO.getId());
                }
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void deleteComment(UserCommentDTO comment) {
        String token = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(TOKEN, "");

        VolleyManager.getInstance().addToRequestQueue(
                new AuthorizedJsonRequest(Request.Method.DELETE, String.format(DELETE_COMMENT_URL, comment.getId()), token,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                InflaterUtils.showToast(SharingCommentsActivity.this, "Your comment was deleted.");
                                commentAdapter.remove(comment);
                                commentAdapter.notifyDataSetChanged();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        InflaterUtils.showToast(SharingCommentsActivity.this, "There was a problem with reporting this item!");
                    }
                }).getRequest());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void reportComment(int commentId) {
        String token = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(TOKEN, "");

        VolleyManager.getInstance().addToRequestQueue(
                new AuthorizedJsonRequest(Request.Method.POST, String.format(REPORT_COMMENT_URL, commentId), token,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                InflaterUtils.showToast(SharingCommentsActivity.this, "Your report was received.");
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        InflaterUtils.showToast(SharingCommentsActivity.this, "There was a problem with reporting this item!");
                    }
                }).getRequest());
    }

    private void initViewContent() {
        nameTxt = findViewById(R.id.sharingName);
        descTxt = findViewById(R.id.sharingDesc);
        commentTxt = findViewById(R.id.sharingComment);
        measurementTxt = findViewById(R.id.sharingMeasurement);
        targetTxt = findViewById(R.id.sharingTargetCounter);
        resultTxt = findViewById(R.id.sharingCompleteCounter);
        likesTxt = findViewById(R.id.likeCounterTxt);

        nameTxt.setText(bundle.getString("name"));
        descTxt.setText(bundle.getString("description"));
        commentTxt.setText(bundle.getString("comment"));
        measurementTxt.setText(bundle.getString("measurement"));
        targetTxt.setText(bundle.getInt("target") + "");
        resultTxt.setText(bundle.getInt("result") + "");
        likesTxt.setText(bundle.getInt("likes") + "");
    }

    private void initCommentButton() {
        addCommentBtn = findViewById(R.id.commentSharingBtn);

        addCommentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = InflaterUtils.showCommentAlert(SharingCommentsActivity.this, R.layout.comment_alert_box);
                Button dismissBtn = alertDialog.findViewById(R.id.noCommentBtn);
                Button confirmBtn = alertDialog.findViewById(R.id.yesCommentBtn);
                EditText contentEdit = alertDialog.findViewById(R.id.commentEdit);

                confirmBtn.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(View v) {
                        String content = contentEdit.getText().toString();
                        if (content.length() < 3) {
                            InflaterUtils.showToast(SharingCommentsActivity.this, "No point to post empty comments.");
                            return;
                        } else if (content.length() > 100) {
                            InflaterUtils.showToast(SharingCommentsActivity.this, "Comment should be maximum 100 chars long!");
                            return;
                        }
                        String token = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(TOKEN, "");

                        UserCommentDTO commentDTO = new UserCommentDTO();
                        commentDTO.setContent(content);

                        VolleyManager.getInstance().addToRequestQueue(
                                new AuthorizedJsonRequest(Request.Method.POST, String.format(POST_COMMENT_URL, sharingId), token, commentDTO,
                                        new Response.Listener<JSONObject>() {
                                            @Override
                                            public void onResponse(JSONObject response) {
                                                Type typeToken = new TypeToken<UserCommentDTO>() {
                                                }.getType();
                                                UserCommentDTO dto = gson.fromJson(response.toString(), typeToken);
                                                commentAdapter.add(dto);
                                                commentAdapter.notifyDataSetChanged();
                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        InflaterUtils.showToast(SharingCommentsActivity.this, "There was a problem on sharing of challenge!");
                                    }
                                }).getRequest());
                        alertDialog.dismiss();
                    }
                });

                dismissBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

            }
        });
    }

    private void initLikeButton() {
        likeBtn = findViewById(R.id.likeCommentsBtn);
        initLikeImages(bundle.getBoolean("isLiked"));

        likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(TOKEN, "");

                VolleyManager.getInstance().addToRequestQueue(
                        new AuthorizedJsonRequest(Request.Method.POST, String.format(LIKE_SHARING_URL, sharingId), token,
                                new Response.Listener<JSONObject>() {
                                    @RequiresApi(api = Build.VERSION_CODES.O)
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, type, jsonDeserializationContext) ->
                                                LocalDateTime.parse(json.getAsJsonPrimitive().getAsString())).create();
                                        LikesDTO likesDTO = gson.fromJson(response.toString(), new TypeToken<LikesDTO>() {
                                        }.getType());

                                        likesTxt.setText(likesDTO.getLikesCount() + "");
                                        initLikeImages(likesDTO.isLiked());
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                InflaterUtils.showToast(SharingCommentsActivity.this, "There was a problem with like functionality!");
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
}