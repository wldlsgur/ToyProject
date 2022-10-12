package com.example.issueproject.retrofit

interface RetrofitCallback<T> {
    fun onError(t:Throwable)
    fun onSuccess(code:Int, responseData:T)
    fun onFailure(code:Int)
}