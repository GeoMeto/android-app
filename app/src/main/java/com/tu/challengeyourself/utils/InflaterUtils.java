package com.tu.challengeyourself.utils;

import android.content.Context;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.tu.challengeyourself.R;

public class InflaterUtils {

    public static AlertDialog showCommentAlert(Context context, int resourceId) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View alertInflater = inflater.inflate(resourceId, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.Theme_ChallengeYourself));
        builder.setView(alertInflater);
        AlertDialog offlineAlert = builder.create();
        offlineAlert.show();
        return offlineAlert;
    }

    public static void showToast(Context context,String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
