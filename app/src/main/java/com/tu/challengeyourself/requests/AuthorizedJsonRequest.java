package com.tu.challengeyourself.requests;

import static com.tu.challengeyourself.constants.Keys.AUTH;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class AuthorizedJsonRequest {

    private JsonObjectRequest request;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public AuthorizedJsonRequest(int requestMethod, String url, String token, Object body, Response.Listener listener, Response.ErrorListener errorListener) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().toJson(body));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.request = new JsonObjectRequest(requestMethod, url, jsonObject, listener, errorListener) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put(AUTH, token);
                return params;
            }
        };
    }

    public AuthorizedJsonRequest(int requestMethod, String url, String token, Response.Listener listener, Response.ErrorListener errorListener) {

        this.request = new JsonObjectRequest(requestMethod, url, null, listener, errorListener) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put(AUTH, token);
                return params;
            }
        };
    }

    public JsonObjectRequest getRequest() {
        return request;
    }

    private class LocalDateAdapter implements JsonSerializer<LocalDateTime> {

        @RequiresApi(api = Build.VERSION_CODES.O)
        public JsonElement serialize(LocalDateTime date, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(date.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        }
    }
}
