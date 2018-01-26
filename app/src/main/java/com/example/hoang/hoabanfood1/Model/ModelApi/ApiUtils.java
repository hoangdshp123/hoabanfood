package com.example.hoang.hoabanfood1.Model.ModelApi;

public class ApiUtils {

    private ApiUtils() {}
    public static APIServices getApiService() {
        return RetrofitClient.getRetrofitInstance().create(APIServices.class);
    }
}
