package com.tu.challengeyourself.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.tu.challengeyourself.R;
import com.tu.challengeyourself.SharingCommentsActivity;
import com.tu.challengeyourself.models.dto.CompletedChallengeDTO;
import com.tu.challengeyourself.models.dto.SharedChallengeDTO;
import com.tu.challengeyourself.models.dto.UserCommentDTO;

import java.util.List;
import java.util.stream.Collectors;

public class SharingAdapter extends ArrayAdapter<SharedChallengeDTO> {

    private Context context;
    public Button openCommentsBtn;
    private ImageButton likeBtn;
    private List<SharedChallengeDTO> sharings;

    public SharingAdapter(Context context, List<SharedChallengeDTO> sharings) {
        super(context, R.layout.sharing_item, sharings);
        this.context = context;
        this.sharings = sharings;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        SharedChallengeDTO sharing = getItem(position);
        CompletedChallengeDTO challenge = sharing.getCompletedChallengeDTO();
        View row = layoutInflater.inflate(R.layout.sharing_item, null);

        TextView sharingNameTxt = row.findViewById(R.id.sharingName);
        TextView sharingDescTxt = row.findViewById(R.id.sharingDesc);
        TextView sharingCommentTxt = row.findViewById(R.id.sharingComment);
        TextView measurementTxt = row.findViewById(R.id.sharingMeasurement);
        TextView targetCountTxt = row.findViewById(R.id.sharingTargetCounter);
        TextView completeCountTxt = row.findViewById(R.id.sharingCompleteCounter);
        TextView commentsTxt = row.findViewById(R.id.commentsTxt);
        TextView likesCounterTxt = row.findViewById(R.id.likeCounterSharingTxt);
        likeBtn = row.findViewById(R.id.likeSharingBtn);
        openCommentsBtn = row.findViewById(R.id.commentSharingBtn);

        setLikeImages(sharing.getLiked());

        sharingNameTxt.setText(challenge.getName());
        sharingDescTxt.setText(challenge.getDescription());
        sharingCommentTxt.setText(challenge.getComment());
        measurementTxt.setText(challenge.getMeasurement());
        completeCountTxt.setText(challenge.getResult() + "");
        targetCountTxt.setText(challenge.getTarget() + "");
        likesCounterTxt.setText(sharing.getLikeCount() + "");
        commentsTxt.setText(formatComments(sharing.getUserCommentDTO()));

        initCommentButton(sharing);

        return row;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private String formatComments(List<UserCommentDTO> userComments) {
        return userComments.stream().map(x -> String.format("%s: %s", x.getUsername(), x.getContent())).collect(Collectors.joining("\n"));
    }

    private void setLikeImages(boolean isLiked) {
        if (isLiked) {
            likeBtn.setImageResource(R.drawable.ic_like_24);
        } else {
            likeBtn.setImageResource(R.drawable.ic_empty_like_24);
        }
    }

    private void initCommentButton(SharedChallengeDTO sharing) {
        CompletedChallengeDTO challenge = sharing.getCompletedChallengeDTO();

        openCommentsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
}