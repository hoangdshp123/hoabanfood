package com.example.hoang.hoabanfood1.Activity;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.example.hoang.hoabanfood1.Connect.Server;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hoang on 10/21/2017.
 */

public class LoginRequest extends StringRequest{
    private final static String url = Server.duongdanlogin;
    private Map<String, String> params;

    public LoginRequest(String username, String password, Response.Listener<String> listener) {
        super(Method.POST, url, listener, null);
        params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);


    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
