package com.tu.challengeyourself.requests;

import static com.tu.challengeyourself.constants.Keys.AUTH;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AuthorizedJsonRequest {

    private JsonObjectRequest request;

    public AuthorizedJsonRequest(int requestMethod, String url, String token, Object body, Response.Listener listener, Response.ErrorListener errorListener) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(new GsonBuilder().create().toJson(body));
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
}
