package com.example.hoang.hoabanfood1.Activity;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.example.hoang.hoabanfood1.Connect.Server;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hoang on 10/21/2017.
 */

public class RegisterRequest extends StringRequest {
    private static final String url = Server.duongdanregister;
    private Map<String, String> params;

    public RegisterRequest(String username, String password, String name, String phonenumber, Response.Listener<String> listener) {
        super(Method.POST, url, listener, null);
        params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        params.put("tenkh", name);
        params.put("sdt", phonenumber);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
