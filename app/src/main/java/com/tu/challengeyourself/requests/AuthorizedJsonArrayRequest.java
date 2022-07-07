package com.tu.challengeyourself.requests;

import static com.tu.challengeyourself.constants.Keys.AUTH;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;

import java.util.HashMap;
import java.util.Map;

public class AuthorizedJsonArrayRequest {

    private JsonArrayRequest request;

    public AuthorizedJsonArrayRequest(int requestMethod, String url, String token,
                                      Response.Listener listener, Response.ErrorListener errorListener) {

        this.request = new JsonArrayRequest(requestMethod, url, null, listener, errorListener) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put(AUTH, token);
                return params;
            }
        };
    }

    public JsonArrayRequest getRequest() {
        return request;
    }
}

