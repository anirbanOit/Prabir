package com.oit.test.getdata;

/**
 * Created by OPTLPT049 on 9/6/2017.
 */


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @GET("/users")
    Call<ArrayList<Model>> doGetListResources();
    @POST("/addUser")
    Call<Model>savePost(@Body Model post);

}
