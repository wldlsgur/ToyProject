package com.example.issueproject.retrofit

import com.example.issueproject.api.ResponseApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitBuilder {
    var servers = "http://49.50.164.199:3000"
    var api: ResponseApi

    init {
        // Gson 객체 생성 - setLenient 속성 추가
        val gson : Gson = GsonBuilder()
            .setLenient()
            .create()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://49.50.164.199:3000")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        api = retrofit.create(ResponseApi::class.java)
    }
}