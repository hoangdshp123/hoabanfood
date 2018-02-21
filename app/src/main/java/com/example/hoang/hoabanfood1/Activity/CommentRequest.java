package com.example.hoang.hoabanfood1.Activity;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.example.hoang.hoabanfood1.Connect.Server;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hoang on 1/26/2018.
 */

public class CommentRequest extends StringRequest {
    private static final String url = Server.duongdansendcoment;
    private Map<String, String> params;

    public CommentRequest(int makhachhang, int masp,String username, String noidung, Response.Listener<String> listener) {
        super(Method.POST, url, listener, null);
        params = new HashMap<>();
        params.put("makhachhang", String.valueOf(makhachhang));
        params.put("masp", String.valueOf(masp));
        params.put("username", username);
        params.put("noidung", noidung);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
