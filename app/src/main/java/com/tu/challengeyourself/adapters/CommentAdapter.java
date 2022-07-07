package com.tu.challengeyourself.adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.tu.challengeyourself.R;
import com.tu.challengeyourself.models.responses.UserCommentDTO;

import java.util.List;

public class CommentAdapter extends ArrayAdapter<UserCommentDTO> {

    private Context context;

    public CommentAdapter(Context context, List<UserCommentDTO> comments) {
        super(context, R.layout.comment_item, comments);
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        UserCommentDTO comment = getItem(position);
        View view = layoutInflater.inflate(R.layout.comment_item, null);

        TextView usernameTxt = view.findViewById(R.id.commentUsernameTxt);
        TextView contentTxt = view.findViewById(R.id.commentContentTxt);
        TextView reportBtn = view.findViewById(R.id.reportDeleteCommentBtn);

        usernameTxt.setText(comment.getUsername());
        contentTxt.setText(comment.getContent());

        if (comment.isOwner()) {
            reportBtn.setText("DELETE");
        } else {
            reportBtn.setText("REPORT");
        }
        return view;
    }
}